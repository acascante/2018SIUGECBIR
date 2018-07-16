/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.ClasificacionDao;
import cr.ac.ucr.sigebi.domain.Clasificacion;
import cr.ac.ucr.sigebi.domain.SubCategoria;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "clasificacionModel")

public class ClasificacionModel {

    @Resource
    private ClasificacionDao clasificacionDao;
    
    public List<Clasificacion> listar() throws FWExcepcion {
        return clasificacionDao.listar();   
    }

    public Clasificacion buscarPorId(Long id) throws FWExcepcion {
        return clasificacionDao.buscarPorId(id);
    }

    public List<Clasificacion> listarPorSubCategoria(SubCategoria subCategoria) throws FWExcepcion {
        return clasificacionDao.listarPorSubCategoria(subCategoria);
    }

    
    public void guardar(Clasificacion valor) throws FWExcepcion {
        clasificacionDao.guardar(valor);
    }

}