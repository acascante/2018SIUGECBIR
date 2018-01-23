/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
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
@Repository(value = "categoriaDao")
@Scope("request")
public class CategoriaDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional(readOnly = true)
    public SubCategoria traerPorCodigo(Integer codigoCategoria) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM SubCategoria obj WHERE obj.codigoCategoria = :codigoCategoria";
            Query query = session.createQuery(sql);
            query.setParameter("codigoCategoria", codigoCategoria);

            return (SubCategoria) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.subCategoriaDao.traerPorCodigo", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional
    public List<SubCategoria> traerTodo() {
        try {
            return dao.getHibernateTemplate().find("from SubCategoria");
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.subCategoriaDao.traerTodo",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }

    }

}
