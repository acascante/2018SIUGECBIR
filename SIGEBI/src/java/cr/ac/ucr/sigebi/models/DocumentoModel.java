/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.DocumentoDao;
import cr.ac.ucr.sigebi.domain.AutorizacionRol;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Documento;
import cr.ac.ucr.sigebi.domain.DocumentoDetalle;
import cr.ac.ucr.sigebi.domain.DocumentoInformeTecnico;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "documentoModel")
@Scope("request")
public class DocumentoModel {

    @Resource
    private DocumentoDao documentoDao;

    @Resource
    private EstadoModel estadoModel;

    @Resource
    private AutorizacionRolModel autorizacionRolModel;

    @Resource
    private DocumentoRolEstadoModel documentoRolEstadoModel;

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
        DocumentoInformeTecnico informe = null;
        ArrayList<DocumentoDetalle> detalles = null;

        //Se busca el estado informe tecnico    
        Estado estadoInfome = estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_INFORME_TECNICO, Constantes.ESTADO_INFORME_TECNICO_NUEVO);
        
        //FIXME Jairo valorar si se utiliza la vista o no
        //Se buscan los roles por autorizacion
        List<AutorizacionRol> autorizacionesRolDocumento = autorizacionRolModel.buscarPorCodigoAutorizacion(Constantes.CODIGO_AUTORIZACION_INFORME_TECNICO);

        for (Bien bien : bienes) {

            //Se crea el informe
            informe = new DocumentoInformeTecnico(estadoInfome, null, bien, "");

            //Se le asocian los detalles
            detalles = new ArrayList<DocumentoDetalle>();
            detalles.add(new DocumentoDetalle(informe, bien));
            informe.setDetallesDocumento(detalles);

            //Se agrega el informe
            this.agregar(informe);

            //Se agregan los detalles por problema de JPA1
            this.agregarDetallesDocumento(detalles);

            //Para cada documento se crean las autorizaciones del documento
//            for (AutorizacionRol autorizacionRol : autorizacionesRolDocumento) {
//                
//                new DocumentoAutorizacion();
//                
//                documentoRolEstadoModel.agregar(
//                        //FIXME Jairo se debe verificar     
//                        //new DocumentoRolEstadoEntity(informe.getIdInformeTecnico(), documentoRol.getId(), documentoRol.getId(), estadoDocumento)
//                        null
//                );
//            }
        }
    }

    public Long consultaCantidadRegistros(Long unidadEjecutora,
            Integer idTipoInforme,
            String identificacionBien,
            String descripcionBien,
            String marcaBien,
            String modeloBien,
            Integer idEstado
    ) throws FWExcepcion {
        return documentoDao.contar(unidadEjecutora, idTipoInforme, identificacionBien, descripcionBien, marcaBien, modeloBien, idEstado);
    }

    public List<Documento> listarInformes(Long unidadEjecutora,
            Integer idTipoInforme,
            String identificacionBien,
            String descripcionBien,
            String marcaBien,
            String modeloBien,
            Integer idEstado,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ) throws FWExcepcion {
        return documentoDao.listar(unidadEjecutora, idTipoInforme, identificacionBien, descripcionBien, marcaBien, modeloBien, idEstado, pPrimerRegistro, pUltimoRegistro);
    }

}
