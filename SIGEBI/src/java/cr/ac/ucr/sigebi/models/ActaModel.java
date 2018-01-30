/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.FaltaActaDao;
import cr.ac.ucr.sigebi.domain.Acta;
import cr.ac.ucr.sigebi.domain.ActaDetalle;
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
    private FaltaActaDao actaDao;
    
    
    public void guardar(Acta actaEntity){
        actaDao.guardar(actaEntity);
    }
    
    public void guardarBienes(List<ActaDetalle> valores){
        actaDao.guardarBienes(valores);
    }
    
    public Acta traerActa(Integer actaId) {
        return actaDao.traerPorId(actaId);
    }
    
    public List<ViewBienEntity> traerBienesActa(Integer actaId) {
        return actaDao.traerBienesActa(actaId);
    }
    
    
    public Long consultaCantidadRegistros(Long unidadEjecutora,
                                        String fltIdActa,
                                        String fltAutorizacion,
                                        String fltEstado ,
                                        String fltFecha         
    ){
        try{
            return actaDao.contarActas(unidadEjecutora
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
    
    public List<Acta> listarActas(Long unidadEjecutora,
                                        String fltIdTipo,
                                        String fltAutorizacion,
                                        String fltEstado,
                                        String fltFecha,
                                        Integer pPrimerRegistro,
                                        Integer pUltimoRegistro
    ){
        try {
            return actaDao.listarActas(unidadEjecutora,
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
