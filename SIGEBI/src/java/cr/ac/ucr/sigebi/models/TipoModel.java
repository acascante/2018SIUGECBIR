/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.sigebi.daos.TipoDao;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Tipo;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author oscar_acuna
 */
@Service(value = "tipoModel")
@Scope("request")
public class TipoModel {
    
    @Resource
    private TipoDao tipoDao;
    
    public List<Tipo> listar() throws FWExcepcion {
        return tipoDao.listar();
    }
    
    public List<Tipo> listarPorDominio(String dominio) throws FWExcepcion {
        return tipoDao.listarPorDominio(dominio);
    }

    public Tipo buscarPorId(Long id) {
        return tipoDao.buscarPorId(id);
    }
    
    public Tipo buscarPorDominioTipo(String dominio, Integer valor) throws FWExcepcion {
        return tipoDao.buscarPorDominioTipo(dominio, valor);
    }

    public Tipo buscarPorDominioNombre(String dominio, String nombre) throws FWExcepcion {
        return tipoDao.buscarPorDominioNombre(dominio, nombre);
    }
}