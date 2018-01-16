/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.entities.MonedaEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jorge.serrano
 */
@Repository(value = "monedaDao")
@Scope("request")
public class MonedaDao  extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;

    
    @Transactional
    public MonedaEntity traerPorId(String pCodCateg) {
        MonedaEntity tipoAux = new MonedaEntity();
        try {
            String[] nameParams = new String[1];
            nameParams[0] = "pcodCategoria";
            Object[] params = new Object[1];
            params[0] = pCodCateg;
            tipoAux = (MonedaEntity) dao.getHibernateTemplate().findByNamedQueryAndNamedParam("MonedaEntity.findById", nameParams, params).get(0);
        } catch (Exception e) {
            throw new FWExcepcion ("sigebi.error.MonedaEntity.findById :" + e.getMessage(),
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
        return tipoAux;
    }
    
    @Transactional
    public List<MonedaEntity> traerTodo() {
        try {
            
            return (List<MonedaEntity>) dao.getHibernateTemplate().findByNamedQuery("MonedaEntity.findAll");
        
        } catch (Exception e) {
            throw new FWExcepcion ("sigebi.error.MonedaEntity.findAll",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }

    }
    
    
    
    
}