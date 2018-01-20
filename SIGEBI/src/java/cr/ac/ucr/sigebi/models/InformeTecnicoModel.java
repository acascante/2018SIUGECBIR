/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.FaltaInformeTecnicoDao;
import cr.ac.ucr.sigebi.domain.AutorizacionRol;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Estado;
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
    private FaltaInformeTecnicoDao informeTecnicoDao;

    @Resource
    private EstadoModel estadoModel;
    
    @Resource
    private AutorizacionRolModel documentoRolModel;

    @Resource
    private DocumentoRolEstadoModel documentoRolEstadoModel;
    
    public void agregar(InformeTecnicoEntity informeTecnicoEntity){
        informeTecnicoDao.agregar(informeTecnicoEntity);
    }

    public void modificar(InformeTecnicoEntity informeTecnicoEntity){
        informeTecnicoDao.agregar(informeTecnicoEntity);
    }

    public void agregarAprobacionSolicitudExclusion(ArrayList<Bien> bienes){
        InformeTecnicoEntity informe = null;
        List<AutorizacionRol> documentos = documentoRolModel.buscarPorAutorizacion(Constantes.ID_DOCUMENTO_INFORME_TECNICO);
        Estado estadoInfome = estadoModel.buscarPorDominioEstado(Constantes.DOMINI0_ESTADO_INFORME_TECNICO, Constantes.ESTADO_INFORME_TECNICO_NUEVO);
        Estado estadoDocumento = estadoModel.buscarPorDominioEstado(Constantes.DOMINI0_ESTADO_INFORME_TECNICO, Constantes.ESTADO_INFORME_TECNICO_PROCESO);
        
        for (Bien bien : bienes) {
            
            informe = new InformeTecnicoEntity(bien, estadoInfome);
            this.agregar(informe);
            
            for (AutorizacionRol documentoRol : documentos) {
                documentoRolEstadoModel.agregar(
                   //FIXME Jairo se debe verificar     
                   //new DocumentoRolEstadoEntity(informe.getIdInformeTecnico(), documentoRol.getId(), documentoRol.getId(), estadoDocumento)
                   null
                );
            }
        }
    }
    
    public Long consultaCantidadRegistros(Long unidadEjecutora,
            String fltIdTipo,
            String fltIdBien,
            String fltDescripcion,
            String fltEstado            
    ){
        try{
            return informeTecnicoDao.contarInformes(unidadEjecutora, Integer.parseInt(fltIdTipo), fltIdBien, fltDescripcion, Integer.parseInt(fltEstado));
        }catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.model.informeTecnico.contarInformes",
                    "Error al obtener los datos, error generado en la clase " + this.getClass(), ex);
        }
    }
    
    public List<InformeTecnicoEntity> listarInformes(Long unidadEjecutora,
            String fltIdTipo,
            String fltIdBien,
            String fltDescripcion,
            String fltEstado,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ){
        try {
            return informeTecnicoDao.listarInformes(unidadEjecutora, Integer.parseInt(fltIdTipo), fltIdBien, fltDescripcion, Integer.parseInt(fltEstado), pPrimerRegistro, pUltimoRegistro);
        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.model.informeTecnico.listarInformes.por.estado",
                    "Error al obtener los datos, error generado en la clase " + this.getClass(), ex);
        }
    }
    
}
