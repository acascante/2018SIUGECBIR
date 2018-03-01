/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.PaisDao;
import cr.ac.ucr.sigebi.domain.Pais;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "paisModel")

public class PaisModel {

    @Resource
    private PaisDao paisDao;

    public List<Pais> listar() throws FWExcepcion {
        return paisDao.listar();
    }
    
    public Pais buscarPorId(Long id) throws FWExcepcion {
        return paisDao.buscarPorId(id);
    }
}