/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.SolicitudExclusion;
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

@Repository(value = "exclusionDao")

public class ExclusionDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;

    @Transactional(readOnly = true)
    public List<SolicitudExclusion> listar() throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT exc FROM SolicitudExclusion exc";
            Query query = session.createQuery(sql);
            return (List<SolicitudExclusion>) query.list();
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.exclusiones.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public List<SolicitudExclusion> listar(UnidadEjecutora unidadEjecutora) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT exc FROM SolicitudExclusion exc WHERE exc.unidadEjecutora = :unidadEjecutora";
            Query query = session.createQuery(sql);
            query.setParameter("unidadEjecutora", unidadEjecutora);
            return (List<SolicitudExclusion>) query.list();
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.exclusiones.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public SolicitudExclusion buscarPorId(Long id) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT se FROM SolicitudExclusion se WHERE se.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);

            return (SolicitudExclusion) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.exclusiones.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional
    public void salvar(SolicitudExclusion exclusion) throws FWExcepcion {
        try {
            persist(exclusion);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.exclusiones.error.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public Long contar(UnidadEjecutora unidadEjecutora, Long id, Date fecha, Long estado, Long tipoExclusion) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.queryListar(session, true, unidadEjecutora, id, fecha, estado, tipoExclusion);
            return (Long)query.uniqueResult();

        } catch (FWExcepcion e) {
            throw new FWExcepcion("sigebi.label.exclusiones.error.listar", "Error contando los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }        
    }
    
    @Transactional(readOnly = true)
    public List<SolicitudExclusion> listar(Integer primerRegistro, Integer ultimoRegistro, UnidadEjecutora unidadEjecutora, Long id, Date fecha, Long estado, Long tipo) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.queryListar(session, false, unidadEjecutora, id, fecha, estado, tipo);
            if(!(primerRegistro.equals(1) && ultimoRegistro.equals(1))) {
                query.setFirstResult(primerRegistro);
                query.setMaxResults(ultimoRegistro - primerRegistro);
            }
            return (List<SolicitudExclusion>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.exclusiones.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }        
    }
    
    private Query queryListar(Session session, Boolean contar, UnidadEjecutora unidadEjecutora, Long id, Date fecha, Long idEstado, Long idTipoExclusion) {
        StringBuilder sql = new StringBuilder("SELECT ");
        if (contar) {
            sql.append("COUNT(exc) FROM SolicitudExclusion exc ");
        } else {
            sql.append("exc FROM SolicitudExclusion exc ");
        }
        
        if (unidadEjecutora == null) {
            sql.append("WHERE 1 = 1 "); 
        } else {
            sql.append("WHERE exc.unidadEjecutora = :unidadEjecutora "); 
        }
        if(id != null && id > 0){
           sql.append("AND exc.id = :id ");
        } else {
            if(fecha != null){
                sql.append("AND exc.fecha = :fecha ");
            }
            if(idEstado != null && idEstado > 0){
                sql.append(" AND exc.estado.id = :idEstado ");
            }
            if(idTipoExclusion != null && idTipoExclusion > 0){
                sql.append(" AND exc.tipoExclusion.id = :idTipoExclusion ");
            }
        }
        sql.append(" ORDER BY exc.id asc");
        
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
            if(idTipoExclusion != null && idTipoExclusion > 0){
                query.setParameter("idTipoExclusion", idTipoExclusion);
            }
        }
        return query;
    }
    
    @Transactional
    public void salvarDetalles(List<SolicitudDetalle> detalles) throws FWExcepcion {
        try {
            persist(detalles.toArray());
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.exclusiones.error.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional
    public void eliminarDetalles(List<SolicitudDetalle> detalles) throws FWExcepcion {
        try {
            delete(detalles.toArray());
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.exclusiones.error.eliminar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public Long contarDetalles(SolicitudExclusion exclusion) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.queryListarDetalles(session, true, exclusion);
            return (Long)query.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.exclusiones.error.listar", "Error contando los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }        
    }
    
    @Transactional(readOnly = true)
    public List<SolicitudDetalle> listarDetalles(SolicitudExclusion exclusion) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.queryListarDetalles(session, false, exclusion);
            return (List<SolicitudDetalle>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.exclusiones.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }        
    }
    
    private Query queryListarDetalles(Session session, Boolean contar, SolicitudExclusion solicitud) throws FWExcepcion {
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
        query.setParameter("solicitud", solicitud);
        return query;
    }
}