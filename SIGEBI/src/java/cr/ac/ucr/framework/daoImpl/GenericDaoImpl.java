/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.framework.daoImpl;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.utils.FWExcepcion;

import java.io.Serializable;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = "dao_framework")

public class GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional
    public void persist(Object entity) {
        try {
            dao.getHibernateTemplate().saveOrUpdate(entity);
            // necesario para que de error si hay registros asociados
            dao.getHibernateTemplate().flush();
        } catch (Exception e) {
            throw new FWExcepcion(e);
        }
    }

    @Transactional
    public void persist(Object[] entities){
        try {
            for (int i = 0; i < entities.length; i++) {
                persist(entities[i]);
                // necesario para que de error si hay registros asociados
                dao.getHibernateTemplate().flush();
            }
        } catch (Exception e) {
            throw new FWExcepcion(e);
        }
    }

    @Transactional(readOnly = true)
    public <T> List<T> find(Class<T> entityClass) {
        final List<T> entities = dao.getHibernateTemplate().loadAll(entityClass);
        return entities;
    }

    @Transactional(readOnly = true)
    public <T> T load(Class<T> entityClass, Serializable id) {
        try {
            final T entity = dao.getHibernateTemplate().load(entityClass, id);
            return entity;
        } catch (Exception e) {
            throw new FWExcepcion(e);
        }
    }

    @Transactional(readOnly = true)
    public <T> List<T> find(String hql) {
        @SuppressWarnings("unchecked")
        final List<T> entities = dao.getHibernateTemplate().find(hql);
        return entities;
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public <T> T encontrarSequencia(Class<T> entidad) {
        return (T) dao.getHibernateTemplate().findByNamedQuery(entidad.getSimpleName() + ".obtenerSequencia").get(0);

    }

    @Transactional
    public void delete(Object entidad) {
        dao.getHibernateTemplate().delete(entidad);
    }

    @Transactional
    public void delete(Object[] entities){
        try {
            for (Object entitie : entities) {
                delete(entitie);
                dao.getHibernateTemplate().flush();
            }
        } catch (Exception e) {
            throw new FWExcepcion(e);
        }
    }

    @Transactional
    public Integer obtenerId(String entityName, String nameId) {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT MAX(s." + nameId + ") FROM " + entityName + " s";
            Query q = session.createQuery(sql);

            Integer id = (Integer) q.uniqueResult();
            if (id == null) {
                return 1;
            } else {
                return id + 1;
            }
        } catch (Exception e) {
            return 1;
        } finally {
            session.close();
        }
    }
}
