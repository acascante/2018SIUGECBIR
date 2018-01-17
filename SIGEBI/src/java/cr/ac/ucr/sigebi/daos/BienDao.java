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
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Identificacion;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.entities.BienEntity;
import cr.ac.ucr.sigebi.entities.ClasificacionEntity;
import cr.ac.ucr.sigebi.entities.PlacasEntity;
import cr.ac.ucr.sigebi.entities.SincronizarEntity;
import cr.ac.ucr.sigebi.entities.SubClasificacionEntity;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jorge.serrano
 */
@Repository(value = "bienDao")
@Scope("request")
public class BienDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;
    
    @Resource
    SubClasificacionDao subClasDao;
    
    @Resource
    ClasificacionDao clasfDao;
    
    @Transactional(readOnly = true)
    public List<Bien> listar() throws FWExcepcion {
        try {
            return (List<Bien>) dao.getHibernateTemplate().findByNamedQuery("Bien.findAll");
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.bienCaracteristica.dao.traerTodo", "Error obtener los registros de bien " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public List<Bien> listarPorUnidadEjecutora(UnidadEjecutora unidadejecutora) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT b FROM Bien b WHERE b.unidadejecutora = :unidadejecutora";
            Query query = session.createQuery(sql);
            query.setParameter("unidadejecutora", unidadejecutora);
            
            return (List<Bien>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();        
        } 
    }
    
    @Transactional(readOnly = true)
    public List<Bien> listarPorUnidadEjecutoraEstado(UnidadEjecutora unidadejecutora, Estado estado) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT b FROM Bien b WHERE b.unidadejecutora = :unidadejecutora AND b.estadoInterno = :estado";
            Query query = session.createQuery(sql);
            query.setParameter("unidadejecutora", unidadejecutora);
            query.setParameter("estado", estado);
            return (List<Bien>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();        
        } 
    }
    
    @Transactional(readOnly = true)
    public Bien buscarPorId(Long id) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT b FROM Bien b WHERE b.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);
            
            return (Bien) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();        
        } 
    }
    
    @Transactional(readOnly = true)
    public List<Bien> listar(Integer primerRegistro, Integer ultimoRegistro, UnidadEjecutora unidadejecutora, Identificacion identificacion, String descripcion, String marca, String modelo, String serie, Estado... estados) throws FWExcepcion{
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQuery(Boolean.FALSE, session, unidadejecutora, identificacion, descripcion, marca, modelo, serie, estados);
            if(!(primerRegistro.equals(1) && ultimoRegistro.equals(1))) {
                query.setFirstResult(primerRegistro);
                query.setMaxResults(ultimoRegistro - primerRegistro);
            }            
            return (List<Bien>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }
    }

    public Long contar(UnidadEjecutora unidadejecutora, Identificacion identificacion, String descripcion, String marca, String modelo, String serie, Estado... estados) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQuery(Boolean.TRUE, session, unidadejecutora, identificacion, descripcion, marca, modelo, serie, estados);
            return (Long)query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.contarNotificaciones", "Error contando los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();        
        }
    }

    public void almacenar(Bien bien) throws FWExcepcion {
        try {
            persist(bien);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }

    public void actualizar(BienEntity bien) throws FWExcepcion {
        try {
            persist(bien);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }

    private Query creaQuery(Boolean contar, Session session, UnidadEjecutora unidadEjecutora, Identificacion identificacion, String descripcion, String marca, String modelo,  String serie, Estado... estados) {
        StringBuilder sql = new StringBuilder("SELECT ");
        if (contar) {
            sql.append("SELECT count(b) FROM Bien b ");
        } else {
            sql.append("SELECT b FROM Bien b ");
        }
        
        sql.append("WHERE b.unidadEjecutora = :unidadEjecutora ");
        if(identificacion != null){
           sql.append(" AND b.identificacion = :identificacion ");
        }
        if(descripcion != null && descripcion.length() > 0){
            sql.append(" AND upper(b.descripcion) like upper(:descripcion) ");
        }
        if(marca != null && marca.length() > 0){
            sql.append(" AND upper(b.marca) like upper(:marca) ");
        }
        if(modelo != null && modelo.length() > 0){
            sql.append(" AND upper(b.modelo) like upper(:modelo) ");
        }
        if(serie != null && serie.length() > 0){
            sql.append(" AND upper(b.serie) like upper(:serie) ");
        }
        if(estados != null && estados.length > 0){
            sql.append(" AND b.estado in (:estados)");
        }
        
        Query q = session.createQuery(sql.toString());
        q.setParameter("unidadEjecutora", unidadEjecutora);
        if(identificacion != null){
            q.setParameter("identificacion", identificacion);
        }
        if(descripcion != null && descripcion.length() > 0){
            q.setParameter("descripcion", '%' + descripcion + '%');
        }
        if(marca != null && marca.length() > 0){
            q.setParameter("marca", '%' + marca + '%');
        }
        if(modelo != null && modelo.length() > 0){
            q.setParameter("modelo", '%' + modelo + '%');
        }
        if(serie != null && serie.length() > 0){
            q.setParameter("serie", '%' + serie + '%');
        }
        if(estados != null && estados.length > 0){
            q.setParameterList("estados", estados);
        }
        return q;
    }
    
    @Transactional
    public void sincronizarBien( BienEntity bien,  String usaurioSincro ) throws FWExcepcion {
        // TODO revisar implementacion de sincronizacion
        // TODO Crear domain SincronizarEntity
    
//        try {
//            SincronizarEntity bienSinc = new SincronizarEntity(bien);
//            Session session = dao.getSessionFactory().openSession();
//            
//            // Agrego Usuario Fecha y Placa
//            Date today = new Date();
//            bienSinc.setFechaAdicion(today);
//            bienSinc.setAdicionadoPor(usaurioSincro);
//            PlacasEntity placa = getPlaca(bien.getIdBien());
//            bienSinc.setPlaca(placa.getPlaca());
//            
//            // Busco la Clasificación 
//            if(bien.getIdSubClasificacion() != null){
//                SubClasificacionEntity subClasif =  subClasDao.traerPorId(bien.getIdSubClasificacion());
//                ClasificacionEntity clasif =        clasfDao.traerPorId(subClasif.getIdClasificacion());
//                bienSinc.setDescripcion(clasif.getNombre());
//            }
//            
//            session.beginTransaction();
//            session.save(bienSinc);
//            
//            session.getTransaction().commit();
//            session.close();
//            
//            actualizar(bien);
//            
//            return "";
//        
//        }
//        catch(JDBCException  err){
//            SQLException cause = (SQLException) err.getCause();
//            return cause.getMessage();
//        }
//        catch (NullPointerException e) {
//            return e.getMessage();
//        }
//        catch (Exception e) {
//            return e.getMessage();
//        }
    }
}