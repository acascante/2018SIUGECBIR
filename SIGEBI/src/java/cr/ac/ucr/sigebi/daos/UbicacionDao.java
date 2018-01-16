/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.entities.UbicacionEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jorge.serrano
 */
@Repository(value = "ubicacionDao")
@Scope("request")
public class UbicacionDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;

    
    @Transactional
    public UbicacionEntity traerPorId(int pCodCateg) {
        UbicacionEntity tipoAux = new UbicacionEntity();
        try {
            String[] nameParams = new String[1];
            nameParams[0] = "PID_UBICACION";
            Object[] params = new Object[1];
            params[0] = pCodCateg;
            tipoAux = (UbicacionEntity) dao.getHibernateTemplate().findByNamedQueryAndNamedParam("UbicacionEntity.findById", nameParams, params).get(0);
        } catch (Exception e) {
            throw new FWExcepcion ("sigebi.error.UbicacionEntity.findById :" + e.getMessage(),
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
        return tipoAux;
    }
    
    @Transactional
    public List<UbicacionEntity> traerTodo() {
        try {
            
            return (List<UbicacionEntity>) dao.getHibernateTemplate().findByNamedQuery("UbicacionEntity.findAll");
        
        } catch (Exception e) {
            throw new FWExcepcion ("sigebi.error.UbicacionEntity.findAll",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }

    }
    
    
    
    
}
