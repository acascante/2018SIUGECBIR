/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.ClasificacionDao;
import cr.ac.ucr.sigebi.domain.Clasificacion;
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

    public Clasificacion traerPorId(String idClasificacion) throws FWExcepcion {
        return clasificacionDao.traerPorId(idClasificacion);
    }

    public List<Clasificacion> traerTodo(String codSubCateg) {
        return clasificacionDao.listarPorCodigoSubCategoria(codSubCateg);
    }

}
