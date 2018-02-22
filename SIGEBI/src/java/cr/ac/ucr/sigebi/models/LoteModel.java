/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.LoteDao;
import cr.ac.ucr.sigebi.domain.Lote;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author aocc
 */
@Service(value = "loteModel")

public class LoteModel {
    
    @Resource
    private LoteDao monedaDao;

    public List<Lote> listar() throws FWExcepcion {
        return monedaDao.listar();
    }
    
    public Lote buscarPorId(Long id) throws FWExcepcion {
        return monedaDao.buscarPorId(id);
    }
}