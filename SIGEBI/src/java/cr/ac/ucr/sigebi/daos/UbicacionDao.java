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
import cr.ac.ucr.sigebi.domain.Ubicacion;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import java.util.List;
import org.hibernate.HibernateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author jorge.serrano
 */
@Repository(value = "ubicacionDao")
public class UbicacionDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional(readOnly = true)
    public List<Ubicacion> listar(UnidadEjecutora unidadEjecutora) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT ub FROM Ubicacion ub WHERE ub.unidadEjecutora = :unidadEjecutora ";
            Query query = session.createQuery(sql);
            query.setParameter("unidadEjecutora", unidadEjecutora);

            return (List<Ubicacion>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.ubicacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public List<Ubicacion> listarUbicacionPadre(Ubicacion ubicacion, Estado estado) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT ub FROM Ubicacion ub WHERE ub.pertenece = :ubicacion";
            if(estado != null){
                sql = sql + " and estado = :estado";
            }
            Query query = session.createQuery(sql);
            query.setParameter("ubicacion", ubicacion);
            if(estado != null){
                query.setParameter("estado", estado);
            }
            return (List<Ubicacion>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.ubicacionDao.listarUbicacionPadre", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public Ubicacion buscarPorId(Long id) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM Ubicacion obj WHERE obj.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);
            return (Ubicacion) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.ubicacionDao.buscarPorId", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional
    public void almacenar(Ubicacion ubicacion) throws FWExcepcion {
        try {
            persist(ubicacion);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.ubicacionDao.almacenar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void eliminar(Ubicacion ubicacion)throws FWExcepcion {
        try {
            delete(ubicacion);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.ubicacionDao.eliminar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    
    @Transactional(readOnly = true)
    public List<Ubicacion> listar(String descripcion, UnidadEjecutora unidadEjecutora, Estado estado, Integer pPrimerRegistro, Integer pUltimoRegistro) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            //Se genera el query para la busqueda
            Query q = this.creaQueryListar(descripcion, unidadEjecutora, estado, false, session);

            //Paginacion
            if (!(pPrimerRegistro.equals(1) && pUltimoRegistro.equals(1))) {
                q.setFirstResult(pPrimerRegistro);
                q.setMaxResults(pUltimoRegistro - pPrimerRegistro);
            }
            //Se obtienen los resutltados
            return (List<Ubicacion>) q.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Long contar(String descripcion, UnidadEjecutora unidadEjecutora, Estado estado) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {

            //Se genera el query para la busqueda de los bienes
            Query q = this.creaQueryListar(descripcion, unidadEjecutora, estado, true, session);

            //Se obtienen los resutltados
            return (Long) q.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.ubicacionDao.contar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    private Query creaQueryListar(String descripcion, UnidadEjecutora unidadEjecutora, Estado estado, Boolean contar,Session session) {

        StringBuilder sql = new StringBuilder(" "); 
        if (contar) {
            sql.append("SELECT count(obj) FROM Ubicacion obj ");
        } else {
            sql.append("SELECT obj FROM Ubicacion obj");
        }

        //Select
        sql.append(" WHERE obj.pertenece is null ");

        if (descripcion != null && descripcion.length() > 0) {
           sql.append(" and upper(obj.detalle) like upper(:detalle)");
        }
        if(unidadEjecutora != null){
           sql.append(" and obj.unidadEjecutora = :unidadEjecutora");
        }
        if (estado != null ) {
           sql.append(" and obj.estado = :estado");
        }

        sql.append(" ORDER BY obj.id desc ");

        Query q = session.createQuery(sql.toString());

        if (descripcion != null && descripcion.length() > 0) {
            q.setParameter("detalle", '%' + descripcion + '%');
        }
        if(unidadEjecutora != null){
            q.setParameter("unidadEjecutora", unidadEjecutora);
        }
        if(estado != null){
            q.setParameter("estado", estado);
        }
        return q;
    }   
}