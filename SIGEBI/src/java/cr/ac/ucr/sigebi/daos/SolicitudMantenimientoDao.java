/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.SolicitudMantenimiento;
import cr.ac.ucr.sigebi.domain.SolicitudDetalle;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import java.util.Date;
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
 * @author alvaro.cascante
 */

@Repository(value = "solicitudMantenimientoDao")

public class SolicitudMantenimientoDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;

    @Transactional(readOnly = true)
    public List<SolicitudMantenimiento> listar() throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT sol FROM SolicitudMantenimiento sol";
            Query query = session.createQuery(sql);
            return (List<SolicitudMantenimiento>) query.list();
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.mantenimiento.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public List<SolicitudMantenimiento> listar(UnidadEjecutora unidadEjecutora) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT sol FROM SolicitudMantenimiento sol WHERE sol.unidadEjecutora = :unidadEjecutora";
            Query query = session.createQuery(sql);
            query.setParameter("unidadEjecutora", unidadEjecutora);
            return (List<SolicitudMantenimiento>) query.list();
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.mantenimiento.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public SolicitudMantenimiento buscarPorId(Long id) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT sol FROM SolicitudMantenimiento sol WHERE sol.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);
            return (SolicitudMantenimiento) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.mantenimiento.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional
    public void salvar(SolicitudMantenimiento solicitudMantenimiento) throws FWExcepcion {
        try {
            persist(solicitudMantenimiento);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.mantenimiento.error.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public Long contar(UnidadEjecutora unidadEjecutora, Long id, Date fecha, Long estado) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.queryListar(session, true, unidadEjecutora, id, fecha, estado);
            return (Long)query.uniqueResult();

        } catch (FWExcepcion e) {
            throw new FWExcepcion("sigebi.label.mantenimiento.error.listar", "Error contando los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }        
    }
    
    @Transactional(readOnly = true)
    public List<SolicitudMantenimiento> listar(Integer primerRegistro, Integer ultimoRegistro, UnidadEjecutora unidadEjecutora, Long id, Date fecha, Long estado) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.queryListar(session, false, unidadEjecutora, id, fecha, estado);
            if(!(primerRegistro.equals(1) && ultimoRegistro.equals(1))) {
                query.setFirstResult(primerRegistro);
                query.setMaxResults(ultimoRegistro - primerRegistro);
            }
            return (List<SolicitudMantenimiento>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.mantenimiento.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }        
    }
    
    private Query queryListar(Session session, Boolean contar, UnidadEjecutora unidadEjecutora, Long id, Date fecha, Long idEstado) {
        StringBuilder sql = new StringBuilder("SELECT ");
        if (contar) {
            sql.append("COUNT(sol) FROM SolicitudMantenimiento sol ");
        } else {
            sql.append("exc FROM SolicitudMantenimiento sol ");
        }
    
        sql.append("WHERE sol.unidadEjecutora = :unidadEjecutora "); 
        if(id != null && id > 0){
           sql.append("AND sol.id = :id ");
        } else {
            if(fecha != null){
                sql.append("AND sol.fecha = :fecha ");
            }
            if(idEstado != null && idEstado > 0){
                sql.append(" AND sol.estado.id = :idEstado ");
            }
        }
        sql.append(" ORDER BY sol.id asc");
        
        Query query = session.createQuery(sql.toString());
        query.setParameter("unidadEjecutora", unidadEjecutora);
        if(id != null && id > 0){
            query.setParameter("id", id);
        } else {
            if(fecha != null){
                query.setParameter("fecha", fecha);
            }
            if(idEstado != null && idEstado > 0){
                query.setParameter("idEstado", idEstado);
            }
        }
        return query;
    }
    
    @Transactional
    public void salvarDetalles(List<SolicitudDetalle> detalles) throws FWExcepcion {
        try {
            persist(detalles.toArray());
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.mantenimiento.error.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional
    public void eliminarDetalles(List<SolicitudDetalle> detalles) throws FWExcepcion {
        try {
            delete(detalles.toArray());
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.mantenimiento.error.eliminar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public Long contarDetalles(SolicitudMantenimiento solicitudMantenimiento) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.queryListarDetalles(session, true, solicitudMantenimiento);
            return (Long)query.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.mantenimiento.error.listar", "Error contando los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }        
    }
    
    @Transactional(readOnly = true)
    public List<SolicitudDetalle> listarDetalles(SolicitudMantenimiento solicitudMantenimiento) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.queryListarDetalles(session, false, solicitudMantenimiento);
            return (List<SolicitudDetalle>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.mantenimiento.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }        
    }
    
    private Query queryListarDetalles(Session session, Boolean contar, SolicitudMantenimiento solicitudMantenimiento) throws FWExcepcion {
        StringBuilder sql = new StringBuilder("SELECT ");
        if (contar) {
            sql.append("COUNT(det) FROM SolicitudDetalle det ");
        } else {
            sql.append("det FROM SolicitudDetalle det ");
        }
        //Select
        sql.append("where det.solicitud = :solicitud");
        sql.append(" ORDER BY det.solicitud.id asc");
        
        Query query = session.createQuery(sql.toString());
        query.setParameter("solicitud", solicitudMantenimiento);
        return query;
    }
}