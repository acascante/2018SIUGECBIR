/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Exclusion;
import cr.ac.ucr.sigebi.domain.ExclusionDetalle;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import java.util.Date;
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

@Repository(value = "exclusionDao")
@Scope("request")
public class ExclusionDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;

    @Transactional(readOnly = true)
    public List<Exclusion> listar() throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT exc FROM Exclusion exc";
            Query query = session.createQuery(sql);
            return (List<Exclusion>) query.list();
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.exclusionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public List<Exclusion> listar(UnidadEjecutora unidadEjecutora) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT exc FROM Exclusion exc WHERE exc.unidadEjecutora = :unidadEjecutora";
            Query query = session.createQuery(sql);
            query.setParameter("unidadEjecutora", unidadEjecutora);
            return (List<Exclusion>) query.list();
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.exclusionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional
    public void salvar(Exclusion exclusion) throws FWExcepcion {
        try {
            persist(exclusion);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.exclusionDao.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public Long contar(Integer unidadEjecutora, Long id, Date fecha, Integer estado, Integer tipoExclusion) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.queryListar(session, true, unidadEjecutora, id, fecha, estado, tipoExclusion);
            return (Long)query.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.exclusionDao.contarsExclusiones", "Error contando los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }        
    }
    
    @Transactional(readOnly = true)
    public List<Exclusion> listar(Integer primerRegistro, Integer ultimoRegistro, Integer unidadEjecutora, Long id, Date fecha, Integer estado, Integer tipo) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.queryListar(session, false, unidadEjecutora, id, fecha, estado, tipo);
            if(!(primerRegistro.equals(1) && ultimoRegistro.equals(1))) {
                query.setFirstResult(primerRegistro);
                query.setMaxResults(ultimoRegistro - primerRegistro);
            }
            return (List<Exclusion>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.exclusionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }        
    }
    
    private Query queryListar(Session session, Boolean contar, Integer unidadEjecutora, Long id, Date fecha, Integer estado, Integer tipoExclusion) {
        StringBuilder sql = new StringBuilder("SELECT ");
        if (contar) {
            sql.append("COUNT(exc) FROM Exclusion exc ");
        } else {
            sql.append("exc FROM Exclusion exc ");

        }
    //    sql.append("WHERE exc.unidadEjecutora.codReferencia = :unidadEjecutora "); 
        sql.append("WHERE 1 = 1 ");
        if(id != null && id > 0){
           sql.append("AND exc.id = :id ");
        } else {
            if(fecha != null){
                sql.append("AND exc.fecha = :fecha ");
            }
            if(estado != null && estado > 0){
                sql.append(" AND exc.estado.idEstado = :estado ");
            }
            if(tipoExclusion != null && tipoExclusion > 0){
                sql.append(" AND exc.tipoExclusion.idTipo = :tipoExclusion ");
            }
        }
        sql.append(" ORDER BY exc.id asc");
        
        Query query = session.createQuery(sql.toString());
//        query.setParameter("unidadEjecutora", unidadEjecutora);
//        if (contar) {exc
//            query.setParameter("tipo", tipo);
//        }
        if(id != null && id > 0){
            query.setParameter("id", id);
        } else {
            if(fecha != null){
                query.setParameter("fecha", fecha);
            }
            if(estado != null && estado > 0){
                query.setParameter("estado", estado);
            }
            if(tipoExclusion != null && tipoExclusion > 0){
                query.setParameter("tipoExclusion", tipoExclusion);
            }
        }
        return query;
    }
    
    @Transactional
    public void salvarDetalles(List<ExclusionDetalle> detalles) throws FWExcepcion {
        try {
            dao.getHibernateTemplate().saveOrUpdateAll(detalles);
            dao.getHibernateTemplate().flush();
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.exclusionDao.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public Long contarDetalles(Exclusion exclusion) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.queryListarDetalles(session, true, exclusion);
            return (Long)query.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.exclusionDao.contarExclusiones", "Error contando los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }        
    }
    
    @Transactional(readOnly = true)
    public List<ExclusionDetalle> listarDetalles(Exclusion exclusion) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.queryListarDetalles(session, false, exclusion);
            return (List<ExclusionDetalle>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.exclusionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }        
    }
    
    private Query queryListarDetalles(Session session, Boolean contar, Exclusion exclusion) throws FWExcepcion {
        StringBuilder sql = new StringBuilder("SELECT ");
        if (contar) {
            sql.append("COUNT(det) FROM ExclusionDetalle det ");
        } else {
            sql.append("det FROM ExclusionDetalle det ");
        }
        //Select
        sql.append("where det.exclusion = :exclusion");
        sql.append(" ORDER BY det.exclusion.id asc");
        
        Query query = session.createQuery(sql.toString());
        query.setParameter("exclusion", exclusion);
        return query;
    }
}