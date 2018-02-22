/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.RegistroMovimiento;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jairo.cisneros
 */
@Repository(value = "registroMovimientoDao")

public class RegistroMovimientoDao extends GenericDaoImpl {

    @Transactional
    public void agregar(RegistroMovimiento registro) throws FWExcepcion {
        try {
            this.persist(registro);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.registroMovimientoDao.agregar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
}
