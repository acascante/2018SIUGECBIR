/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.CategoriaDao;
import cr.ac.ucr.sigebi.domain.SubCategoria;
import cr.ac.ucr.sigebi.domain.Categoria;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "categModel")
@Scope("request")
public class CategoriaModel {
    
    @Resource
    private CategoriaDao categoriaDao;

    public List<Categoria> listar() throws FWExcepcion {
        return categoriaDao.listar();
    }
    
    public Categoria buscarPorId(Long id) throws FWExcepcion {
        return categoriaDao.buscarPorId(id);
    }
    
    public Categoria buscarPorCodigoCategoria(String codigoCategoria) throws FWExcepcion {
        return categoriaDao.buscarPorCodigoCategoria(codigoCategoria);
    }
}