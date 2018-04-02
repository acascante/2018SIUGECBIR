/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Proveedor;
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
 * @author jorge.serrano
 */
@Repository(value = "proveedorDao")

public class ProveedorDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;
    
    @Transactional(readOnly = true)
    public List<Proveedor> listar() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("from Proveedor"); 
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public List<Proveedor> listar(String identificacion, String nombre,int primerRegistro, int cantRegistroPorPagina ) {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQuery(session, false, identificacion, nombre);
            
            query.setFirstResult(primerRegistro);
            query.setMaxResults( cantRegistroPorPagina );
                
            return (List<Proveedor>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }
    }
    
    
    @Transactional(readOnly = true)
    public Long contar(String identificacion, String nombre){
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQuery(session, true, identificacion, nombre);
            return (Long)query.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.prestamos.error.listar", "Error contando los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }  
    }
    
    private Query creaQuery(Session session, Boolean contar, String identificacion, String nombre) {
        StringBuilder sql = new StringBuilder("SELECT ");
        
        if (contar) {
            sql.append("COUNT(obj) FROM Persona obj ");
        } else {
            sql.append("obj FROM Persona obj ");
        }
        
        sql.append(" WHERE UPPER(obj.esProveedor) = 'S' ");
        if(identificacion != null && identificacion.length() > 0){
            sql.append(" AND UPPER(obj.identificacion) LIKE UPPER(:identificacion) ");
        }
        if(nombre != null && nombre.length() > 0){
            sql.append(" AND concat(UPPER(obj.nombre), ' '"
                                + ", UPPER(obj.primerApellido), ' '"
                                + ", UPPER(obj.segundoApellido) ) LIKE UPPER(:nombre) ");
        }
        sql.append(" ORDER BY obj.nombre desc");
        
        Query query = session.createQuery(sql.toString());
        if(identificacion != null && identificacion.length() > 0){
            query.setParameter("identificacion", '%' + identificacion + '%');
        }
        if(nombre != null && nombre.length() > 0){
            String nomb = nombre.replace(' ', '%');
            query.setParameter("nombre", '%' + nomb + '%');
        }
        return query;
    }
    
    @Transactional(readOnly = true)
    public Proveedor buscarPorId(Long id) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM Proveedor obj WHERE obj.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);
            return (Proveedor) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.proveedor.dao.buscarPorId", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public Proveedor buscarPorCedula(String cedula) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM Proveedor obj WHERE obj.identificacion = :cedula";
            Query query = session.createQuery(sql);
            query.setParameter("cedula", cedula);
            return (Proveedor) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.proveedor.dao.buscarPorId", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
}