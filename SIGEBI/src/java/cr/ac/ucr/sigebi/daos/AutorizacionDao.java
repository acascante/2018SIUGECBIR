/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Autorizacion;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jairo.cisneros
 */
@Repository(value = "autorizacionDao")
@Scope("request")
public class AutorizacionDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional
    public void agregar(Autorizacion obj) throws FWExcepcion{
        try {
            this.persist(obj);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacion.agregar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void modificar(Autorizacion obj) throws FWExcepcion {    
        try {
             this.persist(obj);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacion.modificar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional
    public void eliminar(Autorizacion obj) throws FWExcepcion {  
        try {
             this.delete(obj);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacion.eliminar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public Autorizacion buscarPorId(Long idAutorizacion) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM Autorizacion obj WHERE obj.id = :idAutorizacion";
            Query query = session.createQuery(sql);
            query.setParameter("idAutorizacion", idAutorizacion);

            //Se obtienen los resutltados
            return (Autorizacion) query.list().get(0);

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacion.buscarPorId",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public List<Autorizacion> buscarPorTipoProceso(Integer idTipoProceso) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM Autorizacion obj WHERE obj.tipoProceso.id = :idTipoProceso";
            Query query = session.createQuery(sql);
            query.setParameter("idTipoProceso", idTipoProceso);

            //Se obtienen los resutltados
            return (List<Autorizacion>) query.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacion.buscarPorAutorizacion",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Long contarAutorizacionsValidator(Long idAutorizacion, Integer idProceso,Integer orden,String nombre, Integer codigo) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {

            //Se genera el query para la busqueda
            Query q = this.creaQueryContar(idAutorizacion, idProceso, orden, nombre, codigo, session);

            //Se obtienen los resutltados
            return (Long) q.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacion.contarAutorizacions",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    private Query creaQueryContar(Long idAutorizacion, Integer idProceso,Integer orden,String nombre, Integer codigo, Session session) {
        String sql = "SELECT count(obj) FROM Autorizacion obj ";        
        //Select
        sql = sql + " WHERE  1 = 1 ";
        if (idAutorizacion != null && idAutorizacion> 0) {
            sql = sql + " AND obj.id != :idAutorizacion ";
        }
        if (idProceso != null && idProceso> 0) {
            sql = sql + " AND obj.tipoProceso.id = :idProceso ";
        }
        if (orden != null && orden> 0) {
            sql = sql + " AND obj.orden = :orden ";
        }
        if (codigo != null && codigo> 0) {
            sql = sql + " AND obj.codigo = :codigo ";
        }
        if (nombre != null && nombre.length() > 0) {
            sql = sql + " AND upper(obj.nombre) = upper(:nombre) ";
        }
        
        Query q = session.createQuery(sql);
        if (idAutorizacion != null && idAutorizacion> 0) {
            q.setParameter("idAutorizacion", idAutorizacion);
        }
        if (idProceso != null && idProceso> 0) {
            q.setParameter("idProceso", idProceso);
        }
        if (orden != null && orden> 0) {
            q.setParameter("orden", orden);
        }
        if (codigo != null && codigo> 0) {
            q.setParameter("codigo", codigo);
        }
        if (nombre != null && nombre.length() > 0) {
            q.setParameter("nombre", nombre);
        }
        return q;
    }
    
}
