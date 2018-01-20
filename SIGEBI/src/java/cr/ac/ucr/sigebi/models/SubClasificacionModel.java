/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.FaltaSubClasificacionDao;
import cr.ac.ucr.sigebi.entities.SubClasificacionEntity;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "subClasificacionModel")
@Scope("request")
public class SubClasificacionModel {
    
    @Resource
    private FaltaSubClasificacionDao subClasificacionDao;

    public SubClasificacionEntity obtenerValor(Integer pId) {
        SubClasificacionEntity respAux = new SubClasificacionEntity();
        try {
            respAux = subClasificacionDao.traerPorId(pId);

        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.obteneClasificacion",
                    "Error obtener los datos de un tipo, error generado en la clase " + this.getClass()
                    + " en el método obtenerTipo(Integer pIdTipo)", ex);
        }
        return respAux;
    }
    
    
    public List<SubClasificacionEntity> traerTodo(int valor){
    
    try {
            return subClasificacionDao.traerTodo(valor);

        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.SubClasificacionEntity.traerTodo",
                    "Error obtener los datos de un tipo, error generado en la clase " + this.getClass()
                    + " en el método obtenerTipo(Integer pIdTipo)", ex);
        }
    }
    
    
}
