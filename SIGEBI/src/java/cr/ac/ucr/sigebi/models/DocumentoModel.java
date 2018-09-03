/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.DocumentoDao;
import cr.ac.ucr.sigebi.daos.UnidadEjecutoraDao;
import cr.ac.ucr.sigebi.domain.AutorizacionRol;
import cr.ac.ucr.sigebi.domain.AutorizacionRolPersona;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Documento;
import cr.ac.ucr.sigebi.domain.DocumentoAprobacionExclusion;
import cr.ac.ucr.sigebi.domain.DocumentoAutorizacion;
import cr.ac.ucr.sigebi.domain.DocumentoDetalle;
import cr.ac.ucr.sigebi.domain.DocumentoInformeTecnico;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.domain.Usuario;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "documentoModel")

public class DocumentoModel {

    @Resource
    private DocumentoDao documentoDao;

    @Resource
    private EstadoModel estadoModel;

    @Resource
    private AutorizacionRolModel autorizacionRolModel;

    @Resource
    private AutorizacionRolPersonaModel autorizacionRolPersonaModel;

    @Resource
    private DocumentoAutorizacionModel documentoAutorizacionModel;

    @Resource
    private BienModel bienModel;

    @Resource
    private UnidadEjecutoraDao unidadEjecutoraDao;


    public void agregar(Documento documento) throws FWExcepcion {
        documentoDao.agregar(documento);
    }

    public void agregarDetallesDocumento(ArrayList<DocumentoDetalle> detalles) throws FWExcepcion {
        for (Iterator iterator = detalles.iterator(); iterator.hasNext();) {
            documentoDao.agregarDetalle((DocumentoDetalle) iterator.next());
        }
    }

    public List<DocumentoDetalle> listarDetalles(Documento documento) throws FWExcepcion {
        return documentoDao.listarDetalles(documento);
    }

    public void modificar(Documento documento) throws FWExcepcion {
        documentoDao.agregar(documento);
    }

    public void modificarEstadoBienDetalle(DocumentoInformeTecnico documento, Estado estadoBien, Estado estadoInterno) throws FWExcepcion {
        if (estadoBien != null) {
            documento.getBien().setEstado(estadoBien);
        }
        if (estadoInterno != null) {
            documento.getBien().setEstadoInterno(estadoInterno);
        }
        bienModel.actualizar(documento.getBien());
    }

    public void agregarAprobacionSolicitudExclusion(ArrayList<Bien> bienes) {

        //Se crea el Documento Informe Tecnico        
        DocumentoInformeTecnico informe;
        ArrayList<DocumentoDetalle> detalles;

        //Se busca el estado informe tecnico    
        Estado estadoInfome = estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_INFORME_TECNICO, Constantes.ESTADO_INFORME_TECNICO_NUEVO);
        Estado estadoInternoBien = estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_INFORME_TECNICO_CREADO);

        for (Bien bien : bienes) {

            //Se crea el informe
            informe = new DocumentoInformeTecnico(estadoInfome, null, bien, "", bien.getUnidadEjecutora());

            //Se le asocian los detalles
            detalles = new ArrayList<DocumentoDetalle>();
            detalles.add(new DocumentoDetalle(informe, bien));
            informe.setDetallesDocumento(detalles);

            //Se agrega el informe
            this.agregar(informe);

            //Se agregan los detalles por problema de JPA1
            this.agregarDetallesDocumento(detalles);

            //Se modifica el estado interno del bien
            bien.setEstadoInterno(estadoInternoBien);
            bienModel.actualizar(bien);
        }
    }

    public Long consultaCantidadRegistros(Long idUnidadEjecutora, Tipo tipoInforme, String identificacionBien, String descripcionBien, String marcaBien, String modeloBien, Estado estado, Integer tipoDocumento) throws FWExcepcion {
        if (idUnidadEjecutora.equals(Constantes.DEFAULT_ID)) {
            return documentoDao.contar(null, tipoInforme, identificacionBien, descripcionBien, marcaBien, modeloBien, estado, tipoDocumento);
        }
        else {
            return documentoDao.contar(unidadEjecutoraDao.buscarPorId(idUnidadEjecutora), tipoInforme, identificacionBien, descripcionBien, marcaBien, modeloBien, estado, tipoDocumento);
        }
    }

    public List<Documento> listarInformes(Long idUnidadEjecutora,
            Tipo tipoInforme,
            String identificacionBien,
            String descripcionBien,
            String marcaBien,
            String modeloBien,
            Estado estado,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro, Integer tipoDocumento) throws FWExcepcion {
        if (idUnidadEjecutora.equals(Constantes.DEFAULT_ID)) {
            return documentoDao.listar(null, tipoInforme, identificacionBien, descripcionBien, marcaBien, modeloBien, estado, pPrimerRegistro, pUltimoRegistro, tipoDocumento);
        }
        else {
            return documentoDao.listar(unidadEjecutoraDao.buscarPorId(idUnidadEjecutora), tipoInforme, identificacionBien, descripcionBien, marcaBien, modeloBien, estado, pPrimerRegistro, pUltimoRegistro, tipoDocumento);
        }

    }

    public HashMap<String, DocumentoAutorizacion> obtenerDocumentosAutorizacionPorRolGeneral(Integer codigoAutorizacion, Documento documento, Usuario usuario, UnidadEjecutora unidadEjecutora) {

        HashMap<String, DocumentoAutorizacion> documentoAutorizaciones = new HashMap();
        Estado estadoAutorizacionProceso = estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_PENDIENTE);

        //Paso 1: Se obtienen los roles que deben aprobar el documento
        List<AutorizacionRol> rolesDocumento = autorizacionRolModel.buscarPorCodigoAutorizacion(codigoAutorizacion);

        for (AutorizacionRol autorizacionRol : rolesDocumento) {

            //Para cada rol se verifica si el usuario tiene permisos para aplicar o rechazar la autorizacion
            AutorizacionRolPersona autorizacionRolPersona = autorizacionRolPersonaModel.buscar(autorizacionRol, usuario, unidadEjecutora);
            Boolean permiteModificar = autorizacionRolPersona != null;

            //Para cada rol se verifica si el documento ya tiene alguna otra aprobacion
            DocumentoAutorizacion documentoAutorizacion = documentoAutorizacionModel.buscar(autorizacionRol, documento);
            if (documentoAutorizacion == null) {
                documentoAutorizacion = new DocumentoAutorizacion(documento, autorizacionRol, null, estadoAutorizacionProceso);
            }
            documentoAutorizacion.setMarcado(permiteModificar);
            documentoAutorizaciones.put(autorizacionRol.getRol().getNombre(), documentoAutorizacion);

        }

        return documentoAutorizaciones;
    }

    public Long contarAprobacionesExclusion(Long idUnidadEjecutora, Long id, String autorizacion, Date fecha, Long idEstado) throws FWExcepcion {
        if (idUnidadEjecutora.equals(Constantes.DEFAULT_ID)) {
            return documentoDao.contarAprobacionesExclusion(null, id, autorizacion, fecha, idEstado);
        }
        else {
            return documentoDao.contarAprobacionesExclusion(unidadEjecutoraDao.buscarPorId(idUnidadEjecutora), id, autorizacion, fecha, idEstado);
        }
    }

    public List<Documento> listarAprobacionesExclusion(Long idUnidadEjecutora, Long id, String autorizacion, Date fecha, Long idEstado, Integer primerRegistro, Integer ultimoRegistro) throws FWExcepcion {
        if (idUnidadEjecutora.equals(Constantes.DEFAULT_ID)) {
            return documentoDao.listarAprobacionesExclusion(null, id, autorizacion, fecha, idEstado, primerRegistro, ultimoRegistro);
        }        
        else {
            return documentoDao.listarAprobacionesExclusion(unidadEjecutoraDao.buscarPorId(idUnidadEjecutora), id, autorizacion, fecha, idEstado, primerRegistro, ultimoRegistro);
        }
    }
    
    public void eliminarDetalles(List<DocumentoDetalle> detalles) throws FWExcepcion {
        documentoDao.eliminarDetalles(detalles);
    }
    
    public DocumentoAprobacionExclusion buscarPorId(Long id) throws FWExcepcion {
        return documentoDao.buscarPorId(id);
    }
}