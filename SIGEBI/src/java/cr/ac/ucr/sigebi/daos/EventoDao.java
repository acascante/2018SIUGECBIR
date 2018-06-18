/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Evento;
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
@Repository(value = "eventoDao")
public class EventoDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;
    
    @Transactional(readOnly = true)
    public List<Evento> listar() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("from Evento"); 
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.mantenimiento.evento.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional
    public void salvar(Evento evento) throws FWExcepcion {
        try {
            persist(evento);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.mantenimiento.evento.error.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public Long contar(Long id, String descripcion, Date fecha, Double costo) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQuery(session, true, id, descripcion, fecha, costo);
            return (Long)query.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.mantenimiento.evento.error.listar", "Error contando los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();        
        }        
    }
    
    @Transactional(readOnly = true)
    public Long contar(Long idBien) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQuery(session, true, idBien);
            return (Long)query.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.mantenimiento.evento.error.listar", "Error contando los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();        
        }        
    }
    
    public Double totalCosto(Long idBien) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT SUM(entity.costo) FROM Evento entity WHERE entity.detalle.bien.id = :idBien ";
            Query query = session.createQuery(sql);
            query.setParameter("idBien", idBien);
            return (Double)query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.mantenimiento.evento.error.listar", "Error contando los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();        
        }        
    }
    
    @Transactional(readOnly = true)
    public List<Evento> listar(Integer primerRegistro, Integer ultimoRegistro, Long id, String descripcion, Date fecha, Double costo) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQuery(session, false, id, descripcion, fecha, costo);
            
            if(!(primerRegistro.equals(1) && ultimoRegistro.equals(1))) {
                query.setFirstResult(primerRegistro);
                query.setMaxResults(ultimoRegistro - primerRegistro);
            }
            return (List<Evento>) query.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.mantenimiento.evento.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();        
        }
    }
    
    @Transactional(readOnly = true)
    public List<Evento> listar(Integer primerRegistro, Integer ultimoRegistro, Long idBien) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQuery(session, false, idBien);
            if(!(primerRegistro.equals(1) && ultimoRegistro.equals(1))) {
                query.setFirstResult(primerRegistro);
                query.setMaxResults(ultimoRegistro - primerRegistro);
            }
            return (List<Evento>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.mantenimiento.evento.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();        
        }
    }
    
    private Query creaQuery(Session session, Boolean contar, Long id, String descripcion, Date fecha, Double costo) {
        StringBuilder sql = new StringBuilder("SELECT ");
        if (contar) {
            sql.append("COUNT(entity) FROM Evento entity ");
        } else {
            sql.append("entity FROM Evento entity ");
        }
        
        sql.append(" WHERE 1 = 1 ");
        if(id != null && id > 0){
           sql.append(" AND entity.id = :id ");
        } else {
            if(descripcion != null && descripcion.length() > 0){
                sql.append(" AND UPPER(entity.descripcion) LIKE UPPER(:descripcion) ");
            }
            if(fecha != null){
                sql.append(" AND entity.fecha = :fecha ");
            }
            if(costo != null && costo > 0){
                sql.append(" AND entity.costo = :costo ");
            }
        }
        sql.append(" ORDER BY entity.fecha asc");
        
        Query query = session.createQuery(sql.toString());
        if(id != null && id > 0){
            query.setParameter("id", id);
        } else {
            if(descripcion != null && descripcion.length() > 0){
                query.setParameter("descripcion", '%' + descripcion + '%');
            }
            if(fecha != null){
                query.setParameter("fecha", fecha);
            }
            if(costo != null && costo > 0){
                query.setParameter("costo", costo);
            }
        }
        return query;
    }
    
    private Query creaQuery(Session session, Boolean contar, Long idBien) {
        StringBuilder sql = new StringBuilder("SELECT ");
        if (contar) {
            sql.append("COUNT(entity) FROM Evento entity ");
        } else {
            sql.append("entity FROM Evento entity ");
        }
        
        sql.append(" WHERE entity.detalle.bien.id = :idBien ");
        sql.append(" ORDER BY entity.fecha asc");
        
        Query query = session.createQuery(sql.toString());
        query.setParameter("idBien", idBien);
        return query;
    }
}