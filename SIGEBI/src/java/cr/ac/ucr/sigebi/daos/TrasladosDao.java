/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.entities.EstadoEntity;
import cr.ac.ucr.sigebi.entities.TrasladoDetalleEntity;
import cr.ac.ucr.sigebi.entities.TrasladoEntity;
import cr.ac.ucr.sigebi.entities.UnidadEjecutoraEntity;
import java.util.List;
import javax.annotation.Resource;
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
@Repository(value = "trasladoDao")
@Scope("request")
public class TrasladosDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;
    
    @Resource
    private ViewBienDao viewBienDao;
    
    @Transactional
    public void guardar(TrasladoEntity obj) {
        try {
            this.persist(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.rol.agregar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional
    public void guardarBienes(List<TrasladoDetalleEntity> bienes){
        try {
            for(TrasladoDetalleEntity valor : bienes) {
                persist(valor);
            }
            
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.TrasladosDao.guardarBienes", "Error guardar el registro de tipo " + this.getClass(), e.getCause());
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.TrasladosDao.guardarBienes", "Error guardar el registro de tipo " + this.getClass(), ex.getCause());
        }
    }
    
    @Transactional
    public void guardarBien(TrasladoDetalleEntity bien){
        try {
            persist(bien);
            
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.TrasladosDao.guardarBienes", "Error guardar el registro de tipo " + this.getClass(), e.getCause());
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.TrasladosDao.guardarBienes", "Error guardar el registro de tipo " + this.getClass(), ex.getCause());
        }
    }
    
    @Transactional
    public void eliminarBienes(TrasladoEntity traslado, EstadoEntity estado){
        Session session = dao.getSessionFactory().openSession();
        try {
            //idTraslado
            String sql = "delete from TrasladoDetalleEntity s where s.idTraslado = :ptraslado and  s.idEstado = :pEstado";
            Query q = session.createQuery(sql);
            q.setParameter("ptraslado",traslado);
            q.setParameter("pEstado",estado);
            
            int resp = q.executeUpdate();
            
            session.close();
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.TrasladosDao.guardarBienes", "Error guardar el registro de tipo " + this.getClass(), e.getCause());
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.TrasladosDao.guardarBienes", "Error guardar el registro de tipo " + this.getClass(), ex.getCause());
        }
    }
    
    public TrasladoEntity traerPorId(Integer pId) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM TrasladoEntity obj WHERE obj.idTraslado = :idTraslado";
            Query query = session.createQuery(sql);
            query.setParameter("idTraslado", pId);

            //Se obtienen los resutltados
            return (TrasladoEntity) query.list().get(0);

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.TrasladoEntity.traerPorId",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional
    public List<TrasladoEntity> traerTodo(UnidadEjecutoraEntity unidadEjecutora) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM TrasladoEntity obj "
                        + " WHERE obj.numUnidadOrigen = :numUnidadOrigen "
                        + " OR obj.numUnidadDestino = :numUnidadOrigen";
            Query query = session.createQuery(sql);
            query.setParameter("numUnidadOrigen", unidadEjecutora);

            //Se obtienen los resutltados
            return (List<TrasladoEntity>) query.list();

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.TrasladoEntity.traerPorId",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }

    }
    
    @Transactional
    public List<TrasladoDetalleEntity> traerBienesTraslado(Integer trasladoId) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM TrasladoDetalleEntity obj "
                        + " WHERE obj.idTraslado.idTraslado = :trasladoId ";
            Query query = session.createQuery(sql);
            query.setParameter("trasladoId", trasladoId);

            //Se obtienen los resutltados
            return (List<TrasladoDetalleEntity>) query.list();

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.TrasladoDetalleEntity.traerBienesTraslado",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    
    @Transactional
    public List<TrasladoEntity> trasladosListado(
              UnidadEjecutoraEntity unidadEjecutora
            , String fltIdTraslado
            , String fltUnidadOrigen
            , String fltUnidadDestino
            , String fltFecha
            , String fltEstados
            , Integer primerRegistro
            , Integer ultimoRegistro
            ) 
    {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            Query q = prepararConsultaListado( 
                      unidadEjecutora
                    , fltIdTraslado
                    , fltUnidadOrigen
                    , fltUnidadDestino
                    , fltFecha
                    
                    , fltEstados
                    , false
                    , session
                    );
            
            
            //Paginacion
            if(!(primerRegistro.equals(1)&& ultimoRegistro.equals(1))){
                q.setFirstResult(primerRegistro);
                q.setMaxResults(ultimoRegistro - primerRegistro);
            }

            //Se obtienen los resutltados
            return (List<TrasladoEntity>) q.list();

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.TrasladosDao.ConsultarTraslados",
                    "Error al obtener traslados: " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }

    }
    
    
    @Transactional
    public Long contarTrasladosListado(
              UnidadEjecutoraEntity unidadEjecutora
            , String fltIdTraslado
            , String fltUnidadOrigen
            , String fltUnidadDestino
            , String fltFecha
            , String fltEstados
    ){
        
        Session session = this.dao.getSessionFactory().openSession();
        try {
            Query q = prepararConsultaListado( 
                      unidadEjecutora
                    , fltIdTraslado
                    , fltUnidadOrigen
                    , fltUnidadDestino
                    , fltFecha
                    
                    , fltEstados
                    , true
                    , session
                    );
            
            return (Long)q.uniqueResult();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.TrasladosDao.contarTrasladosListado",
                    "Error al obtener traslados: " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    private Query prepararConsultaListado(
              UnidadEjecutoraEntity unidadEjecutora
            , String fltIdTraslado
            , String fltUnidadOrigen
            , String fltUnidadDestino
            , String fltFecha
            
            , String fltEstados
            , boolean contar
            , Session session
    ){
        
        try{
            String sql;
            
            if (contar) 
                sql = "SELECT count(s) FROM TrasladoEntity s ";
             else 
                sql = "SELECT s FROM TrasladoEntity s ";
            
            sql +=  " WHERE (s.numUnidadOrigen = :numUnidadOrigen "
                  + " OR s.numUnidadDestino = :numUnidadOrigen) ";
            
            if(fltIdTraslado != null && fltIdTraslado.length() > 0)
               sql = sql +  " AND upper(s.idTraslado) like upper(:fltIdTraslado) ";
            
            if(fltUnidadOrigen != null && fltUnidadOrigen.length() > 0)
               sql = sql +  " AND upper(s.numUnidadOrigen.dscUnidadEjecutora) like upper(:fltUnidadOrigen) ";
            
            if(fltUnidadDestino != null && fltUnidadDestino.length() > 0)
               sql = sql +  " AND upper(s.numUnidadDestino.dscUnidadEjecutora) like upper(:fltUnidadDestino) ";
            if(fltFecha != null && fltFecha.length() > 0)
               sql = sql +  " AND upper(s.fecha) like upper(:fltFecha) ";
            if( ! fltEstados.equals("-1") )
               sql = sql +  " AND s.idEstado.idEstado = :fltEstados ";
            
            
            Query q = session.createQuery(sql);
            q.setParameter("numUnidadOrigen", unidadEjecutora);
            
            if(fltIdTraslado != null && fltIdTraslado.length() > 0)
                q.setParameter("fltIdTraslado", '%' + fltIdTraslado + '%');
            if(fltUnidadOrigen != null && fltUnidadOrigen.length() > 0)
                q.setParameter("fltUnidadOrigen", '%' + fltUnidadOrigen + '%');
            if(fltUnidadDestino != null && fltUnidadDestino.length() > 0)
                q.setParameter("fltUnidadDestino", '%' + fltUnidadDestino + '%');
            if(fltFecha != null && fltFecha.length() > 0)
                q.setParameter("fltFecha", '%' + fltFecha + '%');
            if( ! fltEstados.equals("-1") )
                q.setParameter("fltEstados",  Integer.parseInt(fltEstados) );
            
            
            return q;
        }
        catch(Exception e){
            throw new FWExcepcion("sigebi.error.dao.TrasladosDao.prepararConsultaListado",
                    "Error al preparar la consulta " + this.getClass(), e.getCause());
            
        }
        
    }
    
}
