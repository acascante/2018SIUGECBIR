/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.AsignacionPlaca;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Identificacion;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
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
@Repository(value = "identificacionDao")

public class IdentificacionDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;
        
    @Transactional(readOnly = true)
    public Identificacion siguienteDisponible(Estado estado, UnidadEjecutora unidadEjecutora
    ) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            StringBuilder sql = new StringBuilder("SELECT i FROM Identificacion i");
            sql.append(" WHERE i.estado = :estado ");
            if(unidadEjecutora != null){
                sql.append(" and i.unidadEjecutora = :unidadEjecutora "); 
            }else{
                sql.append(" and i.unidadEjecutora is null "); 
            }
            Query query = session.createQuery(sql.toString());
            query.setParameter("estado", estado);
            if(unidadEjecutora != null){
                query.setParameter("unidadEjecutora", unidadEjecutora);
            }    
            query.setMaxResults(1); 
            return (Identificacion) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.identificacionDao.siguienteDisponible", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public List<Identificacion> listar(Estado estado, UnidadEjecutora unidadEjecutora, String identificacion) {
        Session session = dao.getSessionFactory().openSession();
        try {
            StringBuilder sql = new StringBuilder("SELECT obj FROM Identificacion obj ");
            sql.append(" WHERE obj.estado = :estado ");
            sql.append(" and obj.unidadEjecutora = :unidadEjecutora ");            
            if(identificacion != null && identificacion.length() > 0){
                sql.append(" and UPPER(obj.identificacion) LIKE UPPER(:identificacion) ");
            }
            sql.append(" ORDER BY obj.identificacion desc");
        
            Query query = session.createQuery(sql.toString());
            query.setParameter("estado", estado);
            query.setParameter("unidadEjecutora", unidadEjecutora);
            if(identificacion != null && identificacion.length() > 0){
                query.setParameter("identificacion", '%' + identificacion + '%');
            }
            query.setFirstResult(0);
            query.setMaxResults(15);                
            return (List<Identificacion>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.identificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }
    }
    
    
    @Transactional(readOnly = true)
    public List<Identificacion> listar(AsignacionPlaca asignacionPlaca, Estado estadoIdentificacion) {
        Session session = dao.getSessionFactory().openSession();
        try {
            StringBuilder sql = new StringBuilder("SELECT obj FROM Identificacion obj ");
            sql.append(" WHERE obj.asignacionPlaca = :asignacionPlaca ");        
            sql.append(" and obj.estado = :estadoIdentificacion ");        
            Query query = session.createQuery(sql.toString());
            query.setParameter("asignacionPlaca", asignacionPlaca);
            query.setParameter("estadoIdentificacion", estadoIdentificacion);
            return (List<Identificacion>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.identificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }
    }

    @Transactional
    public void actualizar(Identificacion identificacion) {
        try {
            persist(identificacion);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.identificacionDao.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    
    @Transactional(readOnly = true)
    public Identificacion buscarPorIdentificacion(String identificacion) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM Identificacion obj WHERE upper(obj.identificacion) = upper(:identificacion)";
            Query query = session.createQuery(sql);
            query.setParameter("identificacion", identificacion);
            return (Identificacion) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.identificacionDao.buscarPorIdentificacion", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    
    @Transactional(readOnly = true)
    public Long cantidadDisponibles(UnidadEjecutora unidadEjecutora, Estado estado) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT count(obj) FROM Identificacion obj WHERE obj.estado = :estado ";
            if(unidadEjecutora != null){
                sql = sql  + " and obj.unidadEjecutora = upper(:unidadEjecutora)";
            }else{
                sql = sql  + " and obj.unidadEjecutora is null";
            }
            Query query = session.createQuery(sql);
            query.setParameter("estado", estado);
            if(unidadEjecutora != null){
                query.setParameter("unidadEjecutora", unidadEjecutora);
            }
            return (Long) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.identificacionDao.cantidadDisponibles", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public void reservarIdentificaciones(Long cantidadSolicitada, Long idUnidadEjecutora, Integer estado, Long idAsignacion) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = session.createSQLQuery("CALL SIGEBI_OAF.CAMBIA_ESTADO_IDENTIFICACIONES(:cantidad, :unidadEjecutora, :estado, :idAsignacion)");
            query.setParameter("cantidad", cantidadSolicitada);
            query.setParameter("unidadEjecutora", idUnidadEjecutora);
            query.setParameter("estado", estado);            
            query.setParameter("idAsignacion", idAsignacion);
            query.executeUpdate();
            
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.identificacionDao.reservarIdentificaciones", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
}
