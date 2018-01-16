/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.entities.RegistroMovimientoEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jairo.cisneros
 */
@Repository(value = "registroMovimientoDao")
@Scope("request")
public class RegistroMovimientoDao extends GenericDaoImpl {

    @Transactional
    public void agregar(RegistroMovimientoEntity registro) {
        try {
            this.persist(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
