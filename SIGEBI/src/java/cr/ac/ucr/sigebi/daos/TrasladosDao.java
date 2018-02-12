/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.domain.TrasladoDetalle;
import cr.ac.ucr.sigebi.domain.DocumentoTraslado;
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
    private FaltaViewBienDao viewBienDao;
    
    @Transactional
    public void guardar(DocumentoTraslado obj) {
        try {
            this.persist(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.rol.agregar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional
    public void guardarBienes(List<TrasladoDetalle> bienes){
        try {
            for(TrasladoDetalle valor : bienes) {
                persist(valor);
            }
            
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.TrasladosDao.guardarBienes", "Error guardar el registro de tipo " + this.getClass(), e.getCause());
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.TrasladosDao.guardarBienes", "Error guardar el registro de tipo " + this.getClass(), ex.getCause());
        }
    }
    
    
    @Transactional
    public void eliminarBienes(List<TrasladoDetalle> bienes){
        try {
            for(TrasladoDetalle valor : bienes) {
                delete(valor);
            }
            
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.TrasladosDao.guardarBienes", "Error guardar el registro de tipo " + this.getClass(), e.getCause());
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.TrasladosDao.guardarBienes", "Error guardar el registro de tipo " + this.getClass(), ex.getCause());
        }
    }
    
    
    @Transactional
    public void guardarBien(TrasladoDetalle bien){
        try {
            persist(bien);
            
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.TrasladosDao.guardarBienes", "Error guardar el registro de tipo " + this.getClass(), e.getCause());
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.TrasladosDao.guardarBienes", "Error guardar el registro de tipo " + this.getClass(), ex.getCause());
        }
    }
    
    public DocumentoTraslado traerPorId(Integer pId) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM DocumentoTraslado obj WHERE obj.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", pId);

            //Se obtienen los resutltados
            return (DocumentoTraslado) query.list().get(0);

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.Traslado.traerPorId",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional
    public List<DocumentoTraslado> traerTodo(UnidadEjecutoraEntity unidadEjecutora) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM DocumentoTraslado obj "
                        + " WHERE obj.unidadEjecutora = :unidadEjecutora "
                        + " OR obj.numUnidadDestino = :unidadEjecutora";
            Query query = session.createQuery(sql);
            query.setParameter("unidadEjecutora", unidadEjecutora);

            //Se obtienen los resutltados
            return (List<DocumentoTraslado>) query.list();

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.Traslado.traerPorId",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }

    }
    
    @Transactional
    public List<TrasladoDetalle> traerBienesTraslado(DocumentoTraslado trasladoId) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM TrasladoDetalle obj "
                        + " WHERE obj.documento.id = :traslado ";
            Query query = session.createQuery(sql);
            query.setParameter("traslado", trasladoId.getId());

            //Se obtienen los resutltados
            return (List<TrasladoDetalle>) query.list();

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.TrasladoDetalle.traerBienesTraslado",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    
    @Transactional
    public List<DocumentoTraslado> trasladosListado(
              UnidadEjecutora unidadEjecutora
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
            return (List<DocumentoTraslado>) q.list();

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
              UnidadEjecutora unidadEjecutora
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
              UnidadEjecutora unidadEjecutora
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
                sql = "SELECT count(s) FROM DocumentoTraslado s ";
             else 
                sql = "SELECT s FROM DocumentoTraslado s ";
            
            sql +=  " WHERE (s.unidadEjecutora = :unidadEjecutora "
                  + " OR s.numUnidadDestino = :unidadEjecutora) ";
            
            if(fltIdTraslado != null && fltIdTraslado.length() > 0)
               sql = sql +  " AND upper(s.id) like upper(:fltIdTraslado) ";
            
            if(fltUnidadOrigen != null && fltUnidadOrigen.length() > 0)
               sql = sql +  " AND upper(s.unidadEjecutora.dscUnidadEjecutora) like upper(:fltUnidadOrigen) ";
            
            if(fltUnidadDestino != null && fltUnidadDestino.length() > 0)
               sql = sql +  " AND upper(s.numUnidadDestino.dscUnidadEjecutora) like upper(:fltUnidadDestino) ";
            if(fltFecha != null && fltFecha.length() > 0)
               sql = sql +  " AND upper(s.fecha) like upper(:fltFecha) ";
            if( ! fltEstados.equals("-1") )
               sql = sql +  " AND s.estado.id = :fltEstados ";
            
            
            Query q = session.createQuery(sql);
            q.setParameter("unidadEjecutora", unidadEjecutora);
            
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
