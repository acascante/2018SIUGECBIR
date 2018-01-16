/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.ClasificacionDao;
import cr.ac.ucr.sigebi.entities.ClasificacionEntity;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "clasificacionModel")
@Scope("request")
public class ClasificacionModel {
    
    @Resource
    private ClasificacionDao clasificacionDao;

    
    public ClasificacionEntity obtenerValor(Integer pId) {
        ClasificacionEntity respAux = new ClasificacionEntity();
        try {
            respAux = clasificacionDao.traerPorId(pId);

        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.obteneClasificacion",
                    "Error obtener los datos de un tipo, error generado en la clase " + this.getClass()
                    + " en el método obtenerTipo(Integer pIdTipo)", ex);
        }
        return respAux;
    }
    
    
    public List<ClasificacionEntity> traerTodo(String codSubCateg){
    
    try {
            return clasificacionDao.traerTodo(codSubCateg);

        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.obtenerTodoCategoria",
                    "Error obtener los datos de un tipo, error generado en la clase " + this.getClass()
                    + " en el método obtenerTipo(Integer pIdTipo)", ex);
        }
    }
    
}
