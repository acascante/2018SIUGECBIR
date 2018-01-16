/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.UbicacionDao;
import cr.ac.ucr.sigebi.entities.UbicacionEntity;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "ubicacionModel")
@Scope("request")
public class UbicacionModel {

    @Resource
    private UbicacionDao ubicDao;

    public UbicacionEntity obtenerValor(Integer pId) {
        UbicacionEntity respAux = new UbicacionEntity();
        try {
            respAux = ubicDao.traerPorId(pId);

        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.obteneClasificacion",
                    "Error obtener los datos de un tipo, error generado en la clase " + this.getClass()
                    + " en el método obtenerTipo(Integer pIdTipo)", ex);
        }
        return respAux;
    }

    public List<UbicacionEntity> traerTodo() {

        try {
            return ubicDao.traerTodo();

        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.notaDao.traerTodo ",
                    "Error obtener los datos de un tipo, error generado en la clase " + this.getClass()
                    + " en el método obtenerTipo(Integer pIdTipo)", ex);
        }
    }
}
