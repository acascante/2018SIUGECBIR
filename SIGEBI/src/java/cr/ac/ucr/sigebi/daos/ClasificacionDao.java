/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Clasificacion;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
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

    @Transactional(readOnly = true)
    public List<Clasificacion> listar() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("from Clasificacion"); 
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public Clasificacion buscarPorId(Long id) throws FWExcepcion {
        return load(Clasificacion.class, id);
    }

    @Transactional(readOnly = true)
    public List<Clasificacion> listarPorCodigoSubCategoria(String codigoSubCategoria) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM Clasificacion obj WHERE obj.codigoSubCategoria = :codigoSubCategoria";
            Query query = session.createQuery(sql);
            query.setParameter("codigoSubCategoria", codigoSubCategoria);

            return (List<Clasificacion>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.clasificacionDao.listarPorDominio", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
}