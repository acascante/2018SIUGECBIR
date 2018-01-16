/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.entities.CategEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jorge.serrano
 */
@Repository(value = "categDao")
@Scope("request")
public class CategDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;

    
    @Transactional
    public CategEntity traerPorId(String pCodCateg) {
        CategEntity tipoAux = new CategEntity();
        try {
            String[] nameParams = new String[1];
            nameParams[0] = "pcodCategoria";
            Object[] params = new Object[1];
            params[0] = pCodCateg;
            tipoAux = (CategEntity) dao.getHibernateTemplate().findByNamedQueryAndNamedParam("CategEntity.findById", nameParams, params).get(0);
        } catch (Exception e) {
            throw new FWExcepcion ("sigebi.error.CategEntity.findById :" + e.getMessage(),
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
        return tipoAux;
    }
    
    @Transactional
    public List<CategEntity> traerTodo() {
        try {
            
            return (List<CategEntity>) dao.getHibernateTemplate().findByNamedQuery("CategEntity.findAll");
        
        } catch (Exception e) {
            throw new FWExcepcion ("sigebi.error.CategEntity.findAll",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }

    }
    
    
    
    
}
