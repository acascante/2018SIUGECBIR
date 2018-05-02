/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Convenio;
import cr.ac.ucr.sigebi.domain.RecepcionPrestamo;
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

@Repository(value = "recepcionPrestamoDao")
public class RecepcionPrestamoDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;
    
    @Transactional(readOnly = true)
    public List<RecepcionPrestamo> listar() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("from RecepcionPrestamo"); 
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.recepcionPrestamo.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public RecepcionPrestamo buscarPorId(Long id) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT entity FROM RecepcionPrestamo entity WHERE entity.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);

            return (RecepcionPrestamo) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.recepcionPrestamo.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional
    public void salvar(RecepcionPrestamo recepcionPrestamo) throws FWExcepcion {
        try {
            persist(recepcionPrestamo);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.recepcionPrestamo.error.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public Long contar(Long id, Long convenio, String descripcion, String identificacion, Date fechaIngreso, Date fechaDevolucion, Long estado) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQuery(session, Boolean.TRUE, id, convenio, descripcion, identificacion, fechaIngreso, fechaDevolucion, estado);
            return (Long)query.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.recepcionPrestamo.error.listar", "Error contando los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }        
    }
    
    @Transactional(readOnly = true)
    public List<RecepcionPrestamo> listar(Integer primerRegistro, Integer ultimoRegistro, Long id, Long convenio, String descripcion, String identificacion, Date fechaIngreso, Date fechaDevolucion, Long estado) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQuery(session, Boolean.FALSE, id, convenio, descripcion, identificacion, fechaIngreso, fechaDevolucion, estado);
            
            if(!(primerRegistro.equals(1) && ultimoRegistro.equals(1))) {
                query.setFirstResult(primerRegistro);
                query.setMaxResults(ultimoRegistro - primerRegistro);
            }
            return (List<RecepcionPrestamo>) query.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.recepcionPrestamo.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }
    }
    
    private Query creaQuery(Session session, Boolean contar, Long id, Long convenio, String descripcion, String identificacion, Date fechaIngreso, Date fechaDevolucion, Long estado) {
        StringBuilder sql = new StringBuilder("SELECT ");
        if (contar) {
            sql.append("COUNT(entity) FROM RecepcionPrestamo entity ");
        } else {
            sql.append("entity FROM RecepcionPrestamo entity ");
        }
        
        sql.append(" WHERE 1 = 1 ");
        if(id != null && id > 0){
           sql.append(" AND entity.id = :id ");
        } else {
            if(convenio != null){
                sql.append(" AND entity.convenio.id = :convenio ");
            }
            if(descripcion != null && descripcion.length() > 0){
                sql.append(" AND UPPER(entity.descripcion) LIKE UPPER(:descripcion) ");
            }
            if(identificacion != null && identificacion.length() > 0){
                sql.append(" AND UPPER(entity.identificacion) like upper(:identificacion) ");
            }
            if(fechaIngreso != null){
                sql.append(" AND entity.fechaIngreso = :fechaIngreso ");
            }
            if(fechaDevolucion != null){
                sql.append(" AND entity.fechaDevolucion like :fechaDevolucion ");
            }
            if(estado != null && estado > 0){
                sql.append(" AND entity.estado.id = :estado ");
            }
        }
        sql.append(" ORDER BY entity.id asc");
        
        Query query = session.createQuery(sql.toString());
        if(id != null && id > 0){
            query.setParameter("id", id);
        } else {
            if(convenio != null){
                query.setParameter("convenio", convenio);
            }            
            if(descripcion != null && descripcion.length() > 0){
                query.setParameter("descripcion", '%' + descripcion + '%');
            }
            if(identificacion != null && identificacion.length() > 0){
                query.setParameter("identificacion", '%' + identificacion + '%');
            }
            if(fechaIngreso != null){
                query.setParameter("fechaIngreso", fechaIngreso);
            }
            if(fechaDevolucion != null){
                query.setParameter("fechaDevolucion", fechaDevolucion);
            }
            if(estado != null && estado > 0){
                query.setParameter("estado", estado);
            }
        }
        return query;
    }
}