/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Estado;
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
 * @author jorge.serrano
 */
@Repository(value = "estadoDao")

public class EstadoDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional(readOnly = true)
    public List<Estado> listar() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("from Estado");
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.estado.dao.traerTodo", "Error obtener los registros de estado " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public List<Estado> listarPorDominio(String dominio) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT est FROM Estado est WHERE est.dominio = :dominio";
            Query query = session.createQuery(sql);
            query.setParameter("dominio", dominio);

            return (List<Estado>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Estado buscarPorDominioEstado(String dominio, Integer estado) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT est FROM Estado est WHERE est.dominio = :dominio AND est.valor = :estado";
            Query query = session.createQuery(sql);
            query.setParameter("dominio", dominio);
            query.setParameter("estado", estado);

            return (Estado) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Estado buscarPorDominioNombre(String dominio, String nombre) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT est FROM Estado est WHERE est.dominio = :dominio AND est.nombre = :nombre";
            Query query = session.createQuery(sql);
            query.setParameter("dominio", dominio);
            query.setParameter("nombre", nombre);

            return (Estado) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
}
