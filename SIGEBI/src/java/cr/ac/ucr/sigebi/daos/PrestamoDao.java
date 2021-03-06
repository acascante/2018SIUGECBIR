/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.SolicitudPrestamo;
import cr.ac.ucr.sigebi.domain.SolicitudDetalle;
import cr.ac.ucr.sigebi.domain.SolicitudDetallePrestamo;
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

@Repository(value = "prestamoDao")

public class PrestamoDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;

    @Transactional(readOnly = true)
    public List<SolicitudPrestamo> listar() throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT entity FROM Prestamo entity";
            Query query = session.createQuery(sql);
            return (List<SolicitudPrestamo>) query.list();
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.prestamos.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public List<SolicitudPrestamo> listar(UnidadEjecutora unidadEjecutora) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT entity FROM Prestamo entity WHERE entity.unidadEjecutora = :unidadEjecutora";
            Query query = session.createQuery(sql);
            query.setParameter("unidadEjecutora", unidadEjecutora);
            return (List<SolicitudPrestamo>) query.list();
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.prestamos.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public SolicitudPrestamo buscarPorId(Long id) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT entity FROM SolicitudPrestamo entity WHERE entity.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);

            return (SolicitudPrestamo) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.prestamos.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional
    public void salvar(SolicitudPrestamo prestamo) throws FWExcepcion {
        try {
            persist(prestamo);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.prestamos.error.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public Long contar(UnidadEjecutora unidadEjecutora, Long id, Date fecha, Long estado, Long idTipoEntidad, String entidad) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.queryListar(session, true, unidadEjecutora, id, fecha, estado, idTipoEntidad, entidad);
            return (Long)query.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.prestamos.error.listar", "Error contando los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }        
    }
    
    @Transactional(readOnly = true)
    public List<SolicitudPrestamo> listar(Integer primerRegistro, Integer ultimoRegistro, UnidadEjecutora unidadEjecutora, Long id, Date fecha, Long estado, Long idTipoEntidad, String entidad) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.queryListar(session, false, unidadEjecutora, id, fecha, estado, idTipoEntidad, entidad);
            if(!(primerRegistro.equals(1) && ultimoRegistro.equals(1))) {
                query.setFirstResult(primerRegistro);
                query.setMaxResults(ultimoRegistro - primerRegistro);
            }
            return (List<SolicitudPrestamo>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.prestamos.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }        
    }
    
    private Query queryListar(Session session, Boolean contar, UnidadEjecutora unidadEjecutora, Long id, Date fecha, Long idEstado, Long idTipoEntidad, String entidad) {
        StringBuilder sql = new StringBuilder("SELECT ");
        if (contar) {
            sql.append("COUNT(entity) FROM SolicitudPrestamo entity ");
        } else {
            sql.append("entity FROM SolicitudPrestamo entity ");
        }

        if (unidadEjecutora == null) {
            sql.append("WHERE 1 = 1 ");
        } else {
            sql.append("WHERE entity.unidadEjecutora = :unidadEjecutora ");
        }

        if(id != null && id > 0){
           sql.append("AND entity.id = :id ");
        } else {
            if(fecha != null){
                sql.append("AND entity.fecha = :fecha ");
            }
            if(idEstado != null && idEstado > 0){
                sql.append(" AND entity.estado.id = :idEstado ");
            }
            if(idTipoEntidad != null && idTipoEntidad > 0){
                sql.append(" AND entity.tipo.id = :idTipoEntidad ");
            }
            if(entidad != null && entidad.length() > 0){
                sql.append(" AND UPPER(entity.entidad) LIKE UPPER(:entidad) ");
            }
            
        }
        sql.append(" ORDER BY entity.id asc");
        
        Query query = session.createQuery(sql.toString());
        if (unidadEjecutora != null) {
            query.setParameter("unidadEjecutora", unidadEjecutora);
        }

        if(id != null && id > 0){
            query.setParameter("id", id);
        } else {
            if(fecha != null){
                query.setParameter("fecha", fecha);
            }
            if(idEstado != null && idEstado > 0){
                query.setParameter("idEstado", idEstado);
            }
            if(idTipoEntidad != null && idTipoEntidad > 0){
                query.setParameter("idTipoEntidad", idTipoEntidad);
            }
            if(entidad != null && entidad.length() > 0){
                query.setParameter("entidad", entidad);
            }
        }
        return query;
    }
    
    @Transactional
    public void salvarDetalles(List<SolicitudDetallePrestamo> detalles) throws FWExcepcion {
        try {
            persist(detalles.toArray());
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.prestamos.error.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional
    public void eliminarDetalles(List<SolicitudDetallePrestamo> detalles) throws FWExcepcion {
        try {
            delete(detalles.toArray());
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.prestamos.error.eliminar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public Long contarDetalles(SolicitudPrestamo prestamo) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.queryListarDetalles(session, true, prestamo);
            return (Long)query.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.prestamos.error.listar", "Error contando los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }        
    }
    
    @Transactional(readOnly = true)
    public List<SolicitudDetallePrestamo> listarDetalles(SolicitudPrestamo prestamo) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.queryListarDetalles(session, false, prestamo);
            return (List<SolicitudDetallePrestamo>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.prestamos.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();        
        }        
    }
    
    private Query queryListarDetalles(Session session, Boolean contar, SolicitudPrestamo solicitud) throws FWExcepcion {
        StringBuilder sql = new StringBuilder("SELECT ");
        if (contar) {
            sql.append("COUNT(entity) FROM SolicitudDetallePrestamo entity ");
        } else {
            sql.append("entity FROM SolicitudDetallePrestamo entity ");
        }
        //Select
        sql.append("where entity.solicitud = :solicitud");
        sql.append(" ORDER BY entity.solicitud.id asc");
        
        Query query = session.createQuery(sql.toString());
        query.setParameter("solicitud", solicitud);
        return query;
    }
}