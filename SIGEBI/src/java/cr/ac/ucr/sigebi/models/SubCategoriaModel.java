/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.SubCategoriaDao;
import cr.ac.ucr.sigebi.domain.SubCategoria;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "subCategoriaModel")
@Scope("request")
public class SubCategoriaModel {
    
    @Resource
    private SubCategoriaDao subCategoriaDao;

    public List<SubCategoria> listar() throws FWExcepcion {
        return subCategoriaDao.listar();
    }
    
    public SubCategoria buscarPorId(Long id) throws FWExcepcion {
        return subCategoriaDao.buscarPorId(id);
    }
}