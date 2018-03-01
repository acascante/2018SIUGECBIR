/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.RegistroMovimientoDao;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.RegistroMovimiento;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "registroMovimientoModel")

public class RegistroMovimientoModel {

    @Resource
    private RegistroMovimientoDao registroMovimientoDao;

    public void agregar(RegistroMovimiento registroMovimientoEntity) throws FWExcepcion {
        registroMovimientoDao.agregar(registroMovimientoEntity);
    }

    
    public List<RegistroMovimiento> movimientosPorBien(Bien bien){
        return registroMovimientoDao.movimientosPorBien(bien);
    }
    
}
