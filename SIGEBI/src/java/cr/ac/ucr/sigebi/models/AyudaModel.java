/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.AyudaDao;
import cr.ac.ucr.sigebi.domain.Ayuda;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "ayudaModel")
public class AyudaModel {
    
    @Resource
    private AyudaDao ayudaDao;
    
    public AyudaModel(){
        ayudaDao = new AyudaDao();
    }
    
    public List<Ayuda> listar() throws FWExcepcion{
        return ayudaDao.listar();
    }
    
    public List<Ayuda> listarPorRegla(String regla) throws FWExcepcion {
        return ayudaDao.listarPorRegla(regla);
    }   
}