/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.InformeTecnicoDao;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.entities.BienEntity;
import cr.ac.ucr.sigebi.entities.DocumentoRolEntity;
import cr.ac.ucr.sigebi.entities.DocumentoRolEstadoEntity;
import cr.ac.ucr.sigebi.entities.InformeTecnicoEntity;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "informeTecnicoModel")
@Scope("request")
public class InformeTecnicoModel {
    
    @Resource
    private InformeTecnicoDao informeTecnicoDao;

    @Resource
    private EstadoModel estadoModel;
    
    @Resource
    private DocumentoRolModel documentoRolModel;

    @Resource
    private DocumentoRolEstadoModel documentoRolEstadoModel;
    
    public void agregar(InformeTecnicoEntity informeTecnicoEntity){
        informeTecnicoDao.agregar(informeTecnicoEntity);
    }

    public void modificar(InformeTecnicoEntity informeTecnicoEntity){
        informeTecnicoDao.agregar(informeTecnicoEntity);
    }

    public void agregarAprobacionSolicitudExclusion(ArrayList<BienEntity> bienes){
        InformeTecnicoEntity informe = null;
        List<DocumentoRolEntity> documentos = documentoRolModel.buscarPorDocumento(Constantes.ID_DOCUMENTO_INFORME_TECNICO);
        Estado estadoInfome = estadoModel.buscarPorDominioEstado(Constantes.DOMINI0_ESTADO_INFORME_TECNICO, Constantes.ESTADO_INFORME_TECNICO_NUEVO);
        Estado estadoDocumento = estadoModel.buscarPorDominioEstado(Constantes.DOMINI0_ESTADO_INFORME_TECNICO, Constantes.ESTADO_INFORME_TECNICO_PROCESO);
        
        for (BienEntity bien : bienes) {
            
            informe = new InformeTecnicoEntity(bien, estadoInfome);
            this.agregar(informe);
            
            for (DocumentoRolEntity documentoRol : documentos) {
                documentoRolEstadoModel.agregar(
                   new DocumentoRolEstadoEntity(informe.getIdInformeTecnico(), documentoRol.getIdDocumento(), documentoRol.getIdRol(), estadoDocumento)
                );
            }
        }
    }
    
    public Long consultaCantidadRegistros(int unidEjecutora,
            String fltIdTipo,
            String fltIdBien,
            String fltDescripcion,
            String fltEstado            
    ){
        try{
            return informeTecnicoDao.contarInformes(unidEjecutora, Integer.parseInt(fltIdTipo), fltIdBien, fltDescripcion, Integer.parseInt(fltEstado));
        }catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.model.informeTecnico.contarInformes",
                    "Error al obtener los datos, error generado en la clase " + this.getClass(), ex);
        }
    }
    
    public List<InformeTecnicoEntity> listarInformes(Integer unidEjecutora,
            String fltIdTipo,
            String fltIdBien,
            String fltDescripcion,
            String fltEstado,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ){
        try {
            return informeTecnicoDao.listarInformes(unidEjecutora, Integer.parseInt(fltIdTipo), fltIdBien, fltDescripcion, Integer.parseInt(fltEstado), pPrimerRegistro, pUltimoRegistro);
        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.model.informeTecnico.listarInformes.por.estado",
                    "Error al obtener los datos, error generado en la clase " + this.getClass(), ex);
        }
    }
    
}
