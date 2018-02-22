/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.SubClasificacionDao;
import cr.ac.ucr.sigebi.domain.Clasificacion;
import cr.ac.ucr.sigebi.domain.SubClasificacion;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "subClasificacionModel")

public class SubClasificacionModel {
    
    @Resource
    private SubClasificacionDao subClasificacionDao;

    public List<SubClasificacion> listar() throws FWExcepcion {
        return subClasificacionDao.listar();
    }
    
    public SubClasificacion buscarPorId(Long id) throws FWExcepcion {
        return subClasificacionDao.buscarPorId(id);
    }

    public List<SubClasificacion> listar(Clasificacion clasificacion) {
        return subClasificacionDao.listar(clasificacion);
    }
}