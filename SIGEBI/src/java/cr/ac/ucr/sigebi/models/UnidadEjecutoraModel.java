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
@Service(value = "UnidadEjecutoraModel")
@Scope("request")
public class UnidadEjecutoraModel {

    @Resource
    private UnidadEjecutoraDao unidadDao;

    public UnidadEjecutora traerPorId(Long id) throws FWExcepcion{
        return unidadDao.traerPorId(id);
    }

    public List<UnidadEjecutora> listarUnidades(String idUnidad, String nombUnidad) throws FWExcepcion{
        return unidadDao.listarUnidades(idUnidad, nombUnidad);
    }

}
