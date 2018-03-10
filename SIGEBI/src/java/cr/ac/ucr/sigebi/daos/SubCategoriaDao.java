/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Categoria;
import cr.ac.ucr.sigebi.domain.SubCategoria;
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
@Repository(value = "SubCategoriaDao")

public class SubCategoriaDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional(readOnly = true)
    public List<SubCategoria> listar() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("from SubCategoria sc ORDER BY sc.descripcion");
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public List<SubCategoria> listar(Categoria categoria) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT sc FROM SubCategoria sc WHERE sc.categoria = :categoria ORDER BY sc.descripcion";
            Query query = session.createQuery(sql);
            query.setParameter("categoria", categoria);

            return (List<SubCategoria>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public SubCategoria buscarPorId(Long id) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM SubCategoria obj WHERE obj.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);
            return (SubCategoria) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.subcategoria.dao.buscarPorId", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
}
