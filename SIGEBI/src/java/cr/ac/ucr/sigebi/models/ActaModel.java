/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.ActaDao;
import cr.ac.ucr.sigebi.entities.ActaDetalleEntity;
import cr.ac.ucr.sigebi.entities.ActaEntity;
import cr.ac.ucr.sigebi.entities.ViewBienEntity;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "actaModel")
@Scope("request")
public class ActaModel {
    
    @Resource
    private ActaDao actaDao;
    
    
    public void guardar(ActaEntity actaEntity){
        actaDao.guardar(actaEntity);
    }
    
    public void guardarBienes(List<ActaDetalleEntity> valores){
        actaDao.guardarBienes(valores);
    }
    
    public ActaEntity traerActa(Integer actaId) {
        return actaDao.traerPorId(actaId);
    }
    
    public List<ViewBienEntity> traerBienesActa(Integer actaId) {
        return actaDao.traerBienesActa(actaId);
    }
    
    
    public Long consultaCantidadRegistros(int unidEjecutora,
                                        String fltIdActa,
                                        String fltAutorizacion,
                                        String fltEstado ,
                                        String fltFecha         
    ){
        try{
            return actaDao.contarActas(unidEjecutora
                                    , fltIdActa
                                    , fltAutorizacion
                                    , fltEstado
                                    , fltFecha);
        }catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.model.Actas.Consultar Cant Reg",
                    "Error al obtener los datos, error generado en la clase " + this.getClass(), ex);
        }
    }
    
    public List<ActaEntity> listarActas(Integer unidEjecutora,
                                        String fltIdTipo,
                                        String fltAutorizacion,
                                        String fltEstado,
                                        String fltFecha,
                                        Integer pPrimerRegistro,
                                        Integer pUltimoRegistro
    ){
        try {
            return actaDao.listarActas(unidEjecutora,
                                         fltIdTipo,
                                         fltAutorizacion,
                                         fltEstado,
                                         fltFecha, 
                                         pPrimerRegistro,
                                         pUltimoRegistro
            );
        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.model.Actas.listar",
                    "Error al obtener los datos, error generado en la clase " + this.getClass(), ex);
        }
    }
    
}
