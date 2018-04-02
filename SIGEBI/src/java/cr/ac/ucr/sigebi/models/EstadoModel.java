/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.EstadoDao;
import cr.ac.ucr.sigebi.domain.Estado;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "estadoModel")
public class EstadoModel {
    
    @Resource
    private EstadoDao estadoDao;
    
    public List<Estado> listar() throws FWExcepcion{
        return estadoDao.listar();
    }
    
    public List<Estado> listarPorDominio(String dominio) throws FWExcepcion {
        return estadoDao.listarPorDominio(dominio);
    }    
    
    public Estado buscarPorDominioEstado(String dominio, Integer estado) throws FWExcepcion {
        return estadoDao.buscarPorDominioEstado(dominio, estado);
    }
        
    public Estado buscarPorDominioNombre(String dominio, String nombre) throws FWExcepcion {
        return estadoDao.buscarPorDominioNombre(dominio, nombre);
    }
}