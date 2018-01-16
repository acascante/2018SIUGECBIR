/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.sigebi.daos.UnidadEjecutoraDao;
import cr.ac.ucr.sigebi.entities.UnidadEjecutoraEntity;
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
    
    
    
    public UnidadEjecutoraEntity traerPorId(Integer pId) {
        return unidadDao.traerPorId(pId);
    }
    
    
    public List<UnidadEjecutoraEntity> listarUnidades(String idUnidad, String nombUnidad) {
        return unidadDao.listarUnidades(idUnidad, nombUnidad);
    }
    
    
    
}
