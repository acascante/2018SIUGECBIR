/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.CategDao;
import cr.ac.ucr.sigebi.entities.CategEntity;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "categModel")
@Scope("request")
public class CategModel {
    
    @Resource
    private CategDao categDao;

    
    public CategEntity obtenerValor(String pCodCateg) {
        CategEntity respAux = new CategEntity();
        try {
            respAux = categDao.traerPorId(pCodCateg);

        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.categDao.obtenerValor: " + ex.getMessage(),
                    "Error obtener los datos de un tipo, error generado en la clase " + this.getClass()
                    + " en el método obtenerTipo(Integer pIdTipo)", ex);
        }
        return respAux;
    }
    
    
    public List<CategEntity> traerTodo() {

        try {

            return categDao.traerTodo();

        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.categDao.traerTodo: "+ ex.getMessage(),
                    "Error obtener los datos de un tipo, error generado en la clase " + this.getClass()
                    + " en el método obtenerTipo(Integer pIdTipo)", ex);
        }
    }
    
}
