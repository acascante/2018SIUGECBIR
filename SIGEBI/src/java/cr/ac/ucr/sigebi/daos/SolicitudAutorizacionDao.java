/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.AutorizacionRol;
import cr.ac.ucr.sigebi.domain.Solicitud;
import cr.ac.ucr.sigebi.domain.SolicitudAutorizacion;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jairo.cisneros
 */
@Repository(value = "solicitudAutorizacionDao")

public class SolicitudAutorizacionDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional
    public void agregar(SolicitudAutorizacion obj) throws FWExcepcion {
        try {
            this.persist(obj);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.solicitudAutorizacionDao.agregar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void modificar(SolicitudAutorizacion obj) throws FWExcepcion {
        try {
            this.persist(obj);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.solicitudAutorizacionDao.modificar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public SolicitudAutorizacion buscar(AutorizacionRol autorizacionRol, Solicitud solicitud) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM SolicitudAutorizacion obj "
                    + " WHERE obj.autorizacionRol = :autorizacionRol"
                    + " and obj.solicitud = :solicitud";
            Query query = session.createQuery(sql);
            query.setParameter("autorizacionRol", autorizacionRol);
            query.setParameter("solicitud", solicitud);

            List<SolicitudAutorizacion> results = query.list();
            if (!results.isEmpty()) {
                return (SolicitudAutorizacion) results.get(0);
            } else {
                return null;
            }

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.solicitudAutorizacionDao.buscar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
}
