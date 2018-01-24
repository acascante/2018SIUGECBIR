/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.SubClasificacion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jorge.serrano
 */
@Repository(value = "subClasificacionDao")
@Scope("request")
public class SubClasificacionDao extends GenericDaoImpl{
    
    @Autowired
    private DaoHelper dao;

    @Transactional(readOnly = true)
    public List<SubClasificacion> listar() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("from SubClasificacion"); 
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public SubClasificacion buscarPorId(Long id) throws FWExcepcion {
        return load(SubClasificacion.class, id);
    }
}