/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.CampoBien;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author alvaro.cascante
 */

@Repository(value = "campoBienDao")
public class CampoBienDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;
    
    @Transactional(readOnly = true)
    public List<CampoBien> listar() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("from CampoBien"); 
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.campoBien.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public List<CampoBien> listar(Integer primerRegistro, Integer ultimoRegistro) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("from CampoBien");
            if (!(primerRegistro.equals(1) && ultimoRegistro.equals(1))) {
                query.setFirstResult(primerRegistro);
                query.setMaxResults(ultimoRegistro - primerRegistro);
            }    
            return query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.campoBien.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public CampoBien buscarPorId(Long id) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT entity FROM CampoBien entity WHERE entity.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);

            return (CampoBien) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.campoBien.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    public Long contar() throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("SELECT count(entity) FROM CampoBien entity ");
            return (Long) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.campoBien.error.listar", "Error contando los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
}