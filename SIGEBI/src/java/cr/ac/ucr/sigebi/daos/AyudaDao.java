/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Ayuda;
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
 * @author oscar.acuna
 */
@Repository(value = "ayudaDao")
public class AyudaDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional(readOnly = true)
    public List<Ayuda> listar() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("from Ayuda");
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.ayuda.dao.traerTodo", "Error obtener los registros de estado " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public List<Ayuda> listarPorRegla(String reglaNavegacion) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT a FROM Ayuda a WHERE a.reglaNavegacion = :reglaNavegacion";
            Query query = session.createQuery(sql);
            query.setParameter("reglaNavegacion", reglaNavegacion);

            return (List<Ayuda>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.ayudaDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
}
