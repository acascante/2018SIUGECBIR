/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.entities.SubCategoriaEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jorge.serrano
 */
@Repository(value = "SubCategoriaDao")
@Scope("request")
public class SubCategoriaDao  extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;

    
    
    @Transactional
    public SubCategoriaEntity traerPorId(String codCateg, String codSubCateg) {
        SubCategoriaEntity tipoAux = new SubCategoriaEntity();
        try {
            String[] nameParams = new String[2];
            nameParams[0] = "PCODIGO_CATEGORIA";
            nameParams[1] = "PCODIGO_SUB_CATEGORIA";
            
            Object[] params = new Object[2];
            params[0] = codCateg;
            params[1] = codSubCateg;
            tipoAux = (SubCategoriaEntity) dao.getHibernateTemplate().findByNamedQueryAndNamedParam("SubCategoriaEntity.findById", nameParams, params).get(0);
        } catch (Exception e) {
            throw new FWExcepcion ("sigebi.error.buscarClasificacion",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
        return tipoAux;
    }
    
    @Transactional
    public List<SubCategoriaEntity> traerTodo(String codCat) {
        try {
            
            String[] nameParams = new String[1];
            nameParams[0] = "PCODIGO_CATEGORIA";
            
            Object[] params = new Object[1];
            params[0] = codCat;
            return (List<SubCategoriaEntity>) dao.getHibernateTemplate().findByNamedQueryAndNamedParam("SubCategoriaEntity.findAll", nameParams, params);
        
        } catch (Exception e) {
            throw new FWExcepcion ("sigebi.error.listaClasificacion",
                    "Error obtener los registros de tipo " +e.getMessage() );
        }

    }
    
    
}
