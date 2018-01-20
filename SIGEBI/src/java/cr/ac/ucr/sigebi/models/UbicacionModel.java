/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.UbicacionDao;
import cr.ac.ucr.sigebi.domain.Ubicacion;
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

    public Ubicacion obtenerValor(Integer id) throws FWExcepcion {
        return ubicDao.traerPorId(id);
    }

    public List<Ubicacion> traerTodo() {
        return ubicDao.traerTodo();
    }
}
