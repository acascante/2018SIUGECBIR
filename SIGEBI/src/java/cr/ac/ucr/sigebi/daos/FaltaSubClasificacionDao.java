/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.entities.SubClasificacionEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jorge.serrano
 */
@Repository(value = "subClasificacionDao")
@Scope("request")
public class FaltaSubClasificacionDao {
    
    @Autowired
    private DaoHelper dao;

    
    
    @Transactional
    public SubClasificacionEntity traerPorId(Integer pId) {
        SubClasificacionEntity tipoAux = new SubClasificacionEntity();
        try {
            String[] nameParams = new String[1];
            nameParams[0] = "PID_SUB_CLASIFICACION";
            Object[] params = new Object[1];
            params[0] = pId;
            tipoAux = (SubClasificacionEntity) dao.getHibernateTemplate().findByNamedQueryAndNamedParam("SubClasificacionEntity.findById", nameParams, params).get(0);
        } catch (Exception e) {
            throw new FWExcepcion ("sigebi.error.buscarClasificacion",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
        return tipoAux;
    }
    
    @Transactional
    public List<SubClasificacionEntity> traerTodo(int valor) {
        try {
            String[] nameParams = new String[1];
            nameParams[0] = "PID_CLASIFICACION";
            Object[] params = new Object[1];
            params[0] = valor;
            return (List<SubClasificacionEntity>) dao.getHibernateTemplate().findByNamedQueryAndNamedParam("SubClasificacionEntity.findAll", nameParams, params);
        
        } catch (Exception e) {
            throw new FWExcepcion ("sigebi.error.listaClasificacion",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }

    }
    
}
