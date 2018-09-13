/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Preliminar;
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
@Repository(value = "preliminarDao")
public class PreliminarDao extends GenericDaoImpl  {
    
    @Autowired
    private DaoHelper dao;
    
    
    @Transactional(readOnly = true)
    public List<Preliminar> listar() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("from Preliminar"); 
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.Preliminar.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    
    
    @Transactional(readOnly = true)
    public Preliminar buscarPorId(Long id) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT c FROM Preliminar c WHERE c.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);

            return (Preliminar) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.Preliminar.error.buscarPorId", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    
    @Transactional
    public void guardar(Preliminar preliminar) throws FWExcepcion {
        try {
            persist(preliminar);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.preliminars.error.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    
    
    @Transactional(readOnly = true)
    public Long contar(Long id
            , String identificacion
            , String descripcion
            , String unidad
            , String marca
            , String modelo
            , String serie
            , String orden
            , String factura
            , Long idEstado ) throws FWExcepcion {
        
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQuery(
                                        session
                                        , true
                                        , id
                                        , identificacion
                                        , descripcion
                                        , unidad
                                        , marca
                                        , modelo
                                        , serie
                                        , orden
                                        , factura
                                        , idEstado);
            return (Long)query.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.Preliminar.error.listar", "Error contando los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }        
    }
    
    @Transactional(readOnly = true)
    public List<Preliminar> listar(
                                    Integer primerRegistro
                                  , Integer ultimoRegistro
                                  , Long id
                                  , String identificacion
                                  , String descripcion

                                  , String unidad
                                  , String marca
                                  , String modelo
                                  , String serie
                                  , String orden

                                  , String factura
                                  , Long idEstado) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQuery(session
                                        , false
                                        , id
                                        , identificacion
                                        , descripcion
                    
                                        , unidad
                                        , marca
                                        , modelo
                                        , serie
                                        , orden
                    
                                        , factura
                                        , idEstado);
            
            if(!(primerRegistro.equals(1) && ultimoRegistro.equals(1))) {
                query.setFirstResult(primerRegistro);
                query.setMaxResults(ultimoRegistro - primerRegistro);
            }
            //Se obtienen los resutltados
            return (List<Preliminar>) query.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.Preliminar.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }
    }
    
    private Query creaQuery(
                            Session session
                            , Boolean contar
                            , Long id
                            , String identificacion
                            , String descripcion
            
                            , String unidad
                            , String marca
                            , String modelo
                            , String serie
                            , String orden
            
                            , String factura
                            , Long idEstado) {
        StringBuilder sql = new StringBuilder("SELECT ");
        if (contar) {
            sql.append("COUNT(entity) FROM Preliminar entity ");
        } else {
            sql.append("entity FROM Preliminar entity ");
        }
        
        sql.append(" WHERE 1 = 1 ");
        if(id != null && id > 0){
           sql.append(" AND entity.id = :id ");
        } else {
            if(identificacion != null && identificacion.length() > 0){
                sql.append(" AND UPPER(entity.identificacion) LIKE UPPER(:identificacion) ");
            }
            if(descripcion != null && descripcion.length() > 0){
                sql.append(" AND UPPER(entity.descripcion) LIKE UPPER(:descripcion) ");
            }
            if(marca != null && marca.length() > 0){
                sql.append(" AND UPPER(entity.marca) like upper(:marca) ");
            }
            if(factura != null && factura.length() > 0){
                sql.append(" AND entity.factura like upper(:factura) ");
            }
            if(unidad != null && unidad.length() > 0){
                sql.append(" AND entity.unidadEjecutora.descripcion like upper(:unidad) ");
            }
        }
        //sql.append(" ORDER BY entity.id asc");
        
        Query query = session.createQuery(sql.toString());
        if(id != null && id > 0){
            query.setParameter("id", id);
        } else {
            if(identificacion != null && identificacion.length() > 0){
                query.setParameter("identificacion", '%' + identificacion + '%');
            }
            if(descripcion != null && descripcion.length() > 0){
                query.setParameter("descripcion", '%' + descripcion + '%');
            }
            if(marca != null && marca.length() > 0){
                query.setParameter("marca", '%' + marca + '%');
            }
            if(factura != null && factura.length() > 0){
                query.setParameter("factura", '%' + factura + '%');
            }
            if(unidad != null && unidad.length() > 0){
                query.setParameter("unidad", '%' + unidad + '%');
            }
        }
        return query;
    }
    
    
    
    
}
