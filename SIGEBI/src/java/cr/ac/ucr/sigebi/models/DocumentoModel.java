/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.DocumentoDao;
import cr.ac.ucr.sigebi.domain.AutorizacionRol;
import cr.ac.ucr.sigebi.domain.AutorizacionRolPersona;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Documento;
import cr.ac.ucr.sigebi.domain.DocumentoAutorizacion;
import cr.ac.ucr.sigebi.domain.DocumentoDetalle;
import cr.ac.ucr.sigebi.domain.DocumentoInformeTecnico;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.domain.Usuario;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.ArrayList;
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

    public void agregar(Documento documento) throws FWExcepcion {
        documentoDao.agregar(documento);
    }

    public void agregarDetallesDocumento(ArrayList<DocumentoDetalle> detalles) throws FWExcepcion {
        for (Iterator iterator = detalles.iterator(); iterator.hasNext();) {
            documentoDao.agregarDetalle((DocumentoDetalle) iterator.next());
        }
    }

    public void modificar(Documento documento) throws FWExcepcion {
        documentoDao.agregar(documento);
    }

    public void agregarAprobacionSolicitudExclusion(ArrayList<Bien> bienes) {

        //Se crea el Documento Informe Tecnico        
        DocumentoInformeTecnico informe;
        ArrayList<DocumentoDetalle> detalles;

        //Se busca el estado informe tecnico    
        Estado estadoInfome = estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_INFORME_TECNICO, Constantes.ESTADO_INFORME_TECNICO_NUEVO);

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
        }
    }

    public Long consultaCantidadRegistros(UnidadEjecutora unidadEjecutora, Tipo tipoInforme, String identificacionBien, String descripcionBien, String marcaBien, String modeloBien, Estado estado, Integer tipoDocumento) throws FWExcepcion {
        return documentoDao.contar(unidadEjecutora, tipoInforme, identificacionBien, descripcionBien, marcaBien, modeloBien, estado, tipoDocumento);
    }

    public List<Documento> listarInformes(UnidadEjecutora unidadEjecutora
            , Tipo tipoInforme,
            String identificacionBien,
            String descripcionBien,
            String marcaBien,
            String modeloBien,
            Estado estado,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro, Integer tipoDocumento) throws FWExcepcion {
        return documentoDao.listar(unidadEjecutora, tipoInforme, identificacionBien, descripcionBien, marcaBien, modeloBien, estado, pPrimerRegistro, pUltimoRegistro, tipoDocumento);
    }


    public HashMap<String, DocumentoAutorizacion> obtenerDocumentosAutorizacionPorRolGeneral(Integer codigoAutorizacion, Documento documento, Usuario usuario, UnidadEjecutora unidadEjecutora) {

        HashMap<String, DocumentoAutorizacion> documentoAutorizaciones = new HashMap();
        Estado estadoAutorizacionProceso = estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_PENDIENTE);

        //Paso 1: Se obtienen los roles que deben aprobar el documento
        List<AutorizacionRol> rolesDocumento = autorizacionRolModel.buscarPorCodigoAutorizacion(codigoAutorizacion);

        for (AutorizacionRol autorizacionRol : rolesDocumento) {

            //Solo se contemplan los roles distintos al administrador
            if (!autorizacionRol.getRol().getCodigo().equals(Constantes.CODIGO_ROL_ADMINISTRADOR)) {
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
        }

        return documentoAutorizaciones;
    }

}
