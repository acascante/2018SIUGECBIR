/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.UnidadEjecutoraDao;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "unidadEjecutoraModel")

public class UnidadEjecutoraModel {

    @Resource
    private UnidadEjecutoraDao unidadDao;

    
    public List<UnidadEjecutora> listar() throws FWExcepcion{
        return unidadDao.listar();
    }

    public List<UnidadEjecutora> listar(String idUnidad, String nombreUnidad) throws FWExcepcion{
        return unidadDao.listar(idUnidad, nombreUnidad);
    }
    
    public UnidadEjecutora buscarPorId(Long id) throws FWExcepcion{
        return unidadDao.buscarPorId(id);
    }
}