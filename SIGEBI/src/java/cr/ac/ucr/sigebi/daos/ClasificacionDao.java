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
import cr.ac.ucr.sigebi.domain.SubCategoria;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jorge.serrano
 */
@Repository(value = "clasificacionDao")

public class ClasificacionDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional(readOnly = true)
    public List<Clasificacion> listar() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("from Clasificacion");
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public Clasificacion buscarPorId(Long id) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM Clasificacion obj WHERE obj.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);
            return (Clasificacion) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.clasificacion.dao.buscarPorId", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public List<Clasificacion> listarPorSubCategoria(SubCategoria subCategoria) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM Clasificacion obj WHERE obj.subCategoria = :subCategoria";
            Query query = session.createQuery(sql);
            query.setParameter("subCategoria", subCategoria);

            return (List<Clasificacion>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.clasificacionDao.listarPorDominio", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
}
