/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.entities.ClasificacionEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jorge.serrano
 */
@Repository(value = "clasificacionDao")
@Scope("request")
public class ClasificacionDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;

    
    
    @Transactional
    public ClasificacionEntity traerPorId(Integer pId) {
        ClasificacionEntity tipoAux = new ClasificacionEntity();
        try {
            String[] nameParams = new String[1];
            nameParams[0] = "PID_CLASIFICACION";
            Object[] params = new Object[1];
            params[0] = pId;
            tipoAux = (ClasificacionEntity) dao.getHibernateTemplate().findByNamedQueryAndNamedParam("ClasificacionEntity.findById", nameParams, params).get(0);
        } catch (Exception e) {
            throw new FWExcepcion ("sigebi.error.buscarClasificacion",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
        return tipoAux;
    }
    
    @Transactional
    public List<ClasificacionEntity> traerTodo(String codSubCateg) {
        try {
            String[] nameParams = new String[1];
            nameParams[0] = "PCODIGO_SUB_CATEGORIA";
            Object[] params = new Object[1];
            params[0] = codSubCateg;
            return (List<ClasificacionEntity>) dao.getHibernateTemplate().findByNamedQueryAndNamedParam("ClasificacionEntity.findAll", nameParams, params);
        
        } catch (Exception e) {
            throw new FWExcepcion ("sigebi.error.listaClasificacion",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }

    }
    
}
/*

            
*/