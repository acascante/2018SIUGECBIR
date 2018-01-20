/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Moneda;
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
@Repository(value = "monedaDao")
@Scope("request")
public class MonedaDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional(readOnly = true)
    public Moneda traerPorId(String idMoneda) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM Moneda obj WHERE obj.id = :idMoneda";
            Query query = session.createQuery(sql);
            query.setParameter("idMoneda", idMoneda);
            return (Moneda) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.monedaDao.buscarPorId", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public List<Moneda> traerTodo() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("from Moneda");
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.monedaDao.traerTodo",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

}
