/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.ViewResumenBien;
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
 * @author alvaro.cascante
 */
@Repository(value = "bienCaracteristicaDetalleDao")

public class ViewResumenBienDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional(readOnly = true)
    public List<ViewResumenBien> listar() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("from ViewResumenBien");
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.bienCaracteristica.dao.traerTodo", "Error obtener los registros de bienCaracteristica " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public ViewResumenBien buscarPorId(Long id) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT carac FROM ViewResumenBien carac WHERE carac.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);
            return (ViewResumenBien) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
}