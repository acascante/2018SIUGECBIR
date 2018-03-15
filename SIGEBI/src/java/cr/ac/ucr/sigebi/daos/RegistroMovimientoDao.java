/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.RegistroMovimiento;
import cr.ac.ucr.sigebi.domain.Solicitud;
import cr.ac.ucr.sigebi.domain.SolicitudDetalle;
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
 * @author jairo.cisneros
 */
@Repository(value = "registroMovimientoDao")

public class RegistroMovimientoDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;
    
    @Transactional
    public void agregar(RegistroMovimiento registro) throws FWExcepcion {
        try {
            this.persist(registro);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.registroMovimientoDao.agregar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    
    @Transactional(readOnly = true)
    public List<Solicitud> movimientosPorBien(Bien bien) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT m.solicitud FROM SolicitudDetalle m WHERE m.bien = :bien";
//            String sql;
//            sql = "SELECT m FROM Solicitud m WHERE m.detalles.bien = :bien";
            Query query = session.createQuery(sql);
            query.setParameter("bien", bien);
            return (List<Solicitud>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

}
