/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Notificacion;
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

@Repository(value = "notificacionDao")
public class NotificacionDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;
    
    @Transactional(readOnly = true)
    public List<Notificacion> listar() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("from Notificacion"); 
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional
    public void salvar(Notificacion notificacion) throws FWExcepcion {
        try {
            persist(notificacion);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public Long contar(Long id, String asunto, String destinatario, String mensaje, Date fecha, Integer estado) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQuery(session, true, id, asunto, destinatario, mensaje, fecha, estado);
            return (Long)query.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.contarNotificaciones", "Error contando los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }        
    }
    
    @Transactional(readOnly = true)
    public List<Notificacion> listar(Integer primerRegistro, Integer ultimoRegistro, Long id, String asunto, String destinatario, String mensaje, Date fecha, Integer estado) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQuery(session, false, id, asunto, destinatario, mensaje, fecha, estado);
            
            if(!(primerRegistro.equals(1) && ultimoRegistro.equals(1))) {
                query.setFirstResult(primerRegistro);
                query.setMaxResults(ultimoRegistro - primerRegistro);
            }
            //Se obtienen los resutltados
            return (List<Notificacion>) query.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }
    }
    
    @Transactional(readOnly = true)
    public List<Notificacion> listar(Date fecha, String dominio, Integer... estados) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT ne FROM Notificacion ne WHERE ne.estado.valor IN ( :estado ) AND ne.estado.dominio = :dominio AND ne.fecha <= :fecha";
            Query query = session.createQuery(sql);
            query.setParameterList("estado", estados);
            query.setParameter("dominio", dominio);
            query.setParameter("fecha", fecha);
            
            return (List<Notificacion>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }
    }
    
    private Query creaQuery(Session session, Boolean contar, Long id, String asunto, String destinatario, String mensaje, Date fecha, Integer estado) {
        StringBuilder sql = new StringBuilder("SELECT ");
        if (contar) {
            sql.append("COUNT(ne) FROM Notificacion ne ");
        } else {
            sql.append("ne FROM Notificacion ne ");
        }
        
        sql.append(" WHERE 1 = 1 ");
        if(id != null && id > 0){
           sql.append(" AND ne.id = :id ");
        } else {
            if(asunto != null && asunto.length() > 0){
                sql.append(" AND UPPER(ne.asunto) LIKE UPPER(:asunto) ");
            }
            if(destinatario != null && destinatario.length() > 0){
                sql.append(" AND UPPER(ne.destinatario) like upper(:destinatario) ");
            }
            if(mensaje != null && mensaje.length() > 0){
                sql.append(" AND UPPER(ne.mensaje) like upper(:mensaje) ");
            }
            if(fecha != null){
                sql.append(" AND ne.fecha = :fecha ");
            }
            if(estado != null && estado > 0){
                sql.append(" AND ne.estado.id = :estado ");
            }
        }
        sql.append(" ORDER BY ne.fecha desc");
        
        Query query = session.createQuery(sql.toString());
        if(id != null && id > 0){
            query.setParameter("id", id);
        } else {
            if(asunto != null && asunto.length() > 0){
                query.setParameter("asunto", '%' + asunto + '%');
            }
            if(destinatario != null && destinatario.length() > 0){
                query.setParameter("destinatario", '%' + destinatario + '%');
            }
            if(mensaje != null && mensaje.length() > 0){
                query.setParameter("mensaje", '%' + mensaje + '%');
            }
            if(fecha != null){
                query.setParameter("fecha", fecha);
            }
            if(estado != null && estado > 0){
                query.setParameter("estado", estado);
            }
        }
        return query;
    }
}