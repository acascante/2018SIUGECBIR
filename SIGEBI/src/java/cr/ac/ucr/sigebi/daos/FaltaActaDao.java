/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.entities.ActaDetalleEntity;
import cr.ac.ucr.sigebi.entities.ActaEntity;
import cr.ac.ucr.sigebi.entities.ViewBienEntity;
import java.util.ArrayList;
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
@Repository(value = "ActaDao")
@Scope("request")
public class FaltaActaDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;
    
    @Resource
    private FaltaViewBienDao viewBienDao;
    
    @Transactional
    public ActaEntity traerPorId(Integer pId) {
        Session session = dao.getSessionFactory().openSession();
        ActaEntity resp = new ActaEntity();
        try {
            // De momento utilizamos la referencia como el id del bien
            String sql = "from ActaEntity s where s.idActa = :pidActa";
            Query q = session.createQuery(sql);
            q.setParameter("pidActa",pId);
            
            List l = q.list();
            resp = (ActaEntity) l.get(0);
            
            session.close();
            return resp;
        } catch (Exception e) {
            if(session.isOpen())
                session.close();
            return resp;
        }
    }
    
    
    public List<ActaEntity> listar(Long unidadEjecutora) {
        try {
            return dao.getHibernateTemplate().find("from ActaEntity"); 
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.ActaDao.listar", "Error obtener los registros " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional
    public List<ViewBienEntity> traerBienesActa(Integer actaId) {
        Session session = dao.getSessionFactory().openSession();
        List<ViewBienEntity> detalle = new ArrayList<ViewBienEntity>();
        try {
            
            
            // De momento utilizamos la referencia como el id del bien
            //String sql = "from ActaEntity s where s.idActa = :pidActa";
            String sql = "        SELECT BIEN.* \n" +
                        "        FROM SIGEBI_OAF.SGB_ACTA_DETALLE DET\n" +
                        "            INNER JOIN SIGEBI_OAF.V_SGB_BIEN BIEN\n" +
                        "            ON(DET.ID_BIEN = BIEN.ID_BIEN)\n" +
                        "        WHERE DET.ID_ACTA = :pidActa ";
            
            Query q = session.createSQLQuery(sql);
            q.setParameter("pidActa",actaId);
            
            List l = (List<ViewBienEntity> )q.list();
            
            for ( Object result :  l) {
                Object[] val = (Object[]) result;
                ViewBienEntity valor = viewBienDao.traerPorId(Integer.parseInt(val[0].toString()));
                detalle.add(valor);
            }
            //resp = (ActaEntity) l.get(0);
            
            return detalle;
        } catch (Exception e) {
            return null;
        }
        finally{
            if(session.isOpen())
                session.close();
        }
    }
    
    
    @Transactional
    public void guardar(ActaEntity valor) {
        try {
            persist(valor);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.ActaDao.guardar", "Error guardar el registro de tipo " + this.getClass(), e.getCause());
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.ActaDao.guardar", "Error guardar el registro de tipo " + this.getClass(), ex.getCause());
        }
    }
    
    
    @Transactional
    public void guardarBienes(List<ActaDetalleEntity> valores) {
        try {
            EliminarBienesEnActa(valores.get(0).getIdActa());
            for(ActaDetalleEntity valor : valores) {
                persist(valor);
            }
            
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.ActaDao.guardarBienes", "Error guardar el registro de tipo " + this.getClass(), e.getCause());
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.ActaDao.guardarBienes", "Error guardar el registro de tipo " + this.getClass(), ex.getCause());
        }
    }
    
    
    @Transactional
    public void EliminarBienesEnActa(Integer pIdActa) {
        Session session = dao.getSessionFactory().openSession();
        ActaEntity resp = new ActaEntity();
        try {
            // De momento utilizamos la referencia como el id del bien
            String sql = "delete  from ActaDetalleEntity s where s.idActa = :pidActa";
            Query q = session.createQuery(sql);
            q.setParameter("pidActa",pIdActa);
            
            int rsp = q.executeUpdate();
            
            
        } catch (Exception e) {
            throw new java.lang.Error("Bad.");
        }
        finally{
            if(session.isOpen())
                session.close();
        }
    }
    
    
    @Transactional(readOnly = true)
    public Long contarActas(Long unidadEjecutora,
                                        String fltIdTipo,
                                        String fltAutorizacion,
                                        String fltEstado,
                                        String fltFecha
    ) {
        Session session = dao.getSessionFactory().openSession();
        try {

            //Se genera el query para la busqueda de los bienes
            Query q = this.creaQueryActasConsultar(unidadEjecutora
                                                , fltIdTipo
                                                , fltAutorizacion
                                                , fltEstado
                                                , fltFecha
                                                , true
                                                , session);

            //Se obtienen los resutltados
            return (Long) q.uniqueResult();

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.informeTecnico.contarInformes",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    
    @Transactional(readOnly = true)
    public List<ActaEntity> listarActas(Long unidadEjecutora,
                                        String fltIdTipo,
                                        String fltAutorizacion,
                                        String fltEstado,
                                        String fltFecha,
                                        Integer pPrimerRegistro,
                                        Integer pUltimoRegistro
    ) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            //Se genera el query para la busqueda
            Query q = this.creaQueryActasConsultar(unidadEjecutora
                                                , fltIdTipo
                                                , fltAutorizacion
                                                , fltEstado
                                                , fltFecha
                                                , false
                                                , session);

            //Paginacion
            if (!(pPrimerRegistro.equals(1) && pUltimoRegistro.equals(1))) {
                q.setFirstResult(pPrimerRegistro);
                q.setMaxResults(pUltimoRegistro - pPrimerRegistro);
            }
            //Se obtienen los resutltados
            return (List<ActaEntity>) q.list();

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.informeTecnico.listarInformes",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    
    private Query creaQueryActasConsultar(Long unidadEjecutora,
                                        String fltIdTipo,
                                        String fltAutorizacion,
                                        String fltEstado,
                                        String fltFecha,
                                        Boolean contar,
                                        Session session
    ) {
        String sql;
        if (contar) 
            sql = "SELECT count(s) FROM ActaEntity s ";
         else 
            sql = "SELECT s FROM ActaEntity s ";
        
        //Select
        sql = sql + " WHERE s.unidadEjecutora = :pnumUnidadEjec ";
        if (fltIdTipo != null && fltIdTipo.length() > 0) 
            sql = sql + " AND s.idTipo like :fltIdTipo ";
        if (fltAutorizacion != null && fltAutorizacion.length() > 0) 
            sql = sql + " AND upper(s.autorizacion) like upper(:fltAutorizacion) ";
        if (fltEstado != null && fltEstado.length() > 0) 
            sql = sql + " AND s.idEstado.idEstado = :fltEstado";
        if(fltFecha != null && fltFecha.length() > 0)
               sql = sql +  " AND upper(s.fecha) like upper(:fltFecha) ";

        Query q = session.createQuery(sql);
        q.setParameter("pnumUnidadEjec", unidadEjecutora);
        if (fltIdTipo != null && fltIdTipo.length() > 0) 
            q.setParameter("fltIdTipo", '%' + fltIdTipo + '%');
        if (fltAutorizacion != null && fltAutorizacion.length() > 0) 
            q.setParameter("fltAutorizacion", '%' + fltAutorizacion + '%');
        if (fltEstado != null && fltEstado.length() > 0) 
            q.setParameter("fltEstado", Integer.parseInt(fltEstado));
        if(fltFecha != null && fltFecha.length() > 0)
            q.setParameter("fltFecha", '%' + fltFecha + '%');
        
        return q;
    }

    
    
}
