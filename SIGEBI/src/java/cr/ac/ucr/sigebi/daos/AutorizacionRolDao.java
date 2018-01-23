/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.AutorizacionRol;
import cr.ac.ucr.sigebi.entities.ViewDocumAprobEntity;
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
@Repository(value = "autorizacionRolDao")
@Scope("request")
public class AutorizacionRolDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional
    public void agregar(AutorizacionRol obj) throws FWExcepcion {
        try {
            this.persist(obj);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRol.agregar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void modificar(AutorizacionRol obj) throws FWExcepcion {
        try {
            this.persist(obj);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRol.modificar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void eliminar(AutorizacionRol obj) throws FWExcepcion {
        try {
            this.delete(obj);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRol.eliminar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public List<AutorizacionRol> buscarPorAutorizacion(Long idAutorizacion) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM AutorizacionRol obj WHERE obj.autorizacion.id = :idAutorizacion";
            Query query = session.createQuery(sql);
            query.setParameter("idAutorizacion", idAutorizacion);

            //Se obtienen los resutltados
            return (List<AutorizacionRol>) query.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRol.buscarPorAutorizacion",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Long contarPorAutorizacion(Long idAutorizacion) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT count(obj.id) FROM AutorizacionRol obj WHERE obj.autorizacion.id = :idAutorizacion";
            Query query = session.createQuery(sql);
            query.setParameter("idAutorizacion", idAutorizacion);

            //Se obtienen los resutltados
            return (Long) query.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRol.contarPorAutorizacion",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public AutorizacionRol buscarPorRolAutorizacion(Long idRol, Long idAutorizacion) throws FWExcepcion {
        try {
            Session session = this.dao.getSessionFactory().openSession();
            String sql = "select obj from AutorizacionRol obj ";
            sql = sql + " where obj.rol.id = :idRol";
            sql = sql + " and obj.autorizacion.id = :idAutorizacion";
            Query query = session.createQuery(sql);
            query.setParameter("idAutorizacion", idAutorizacion);
            query.setParameter("idRol", idRol);
            List<AutorizacionRol> results = query.list();
            if (!results.isEmpty()) {
                return (AutorizacionRol) results.get(0);
            } else {
                return null;
            }
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRol.buscarPorRolAutorizacion",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    //FIXME JAIRO se debe coordinar con JORGE si se elimina la vista
    @Transactional(readOnly = true)
    public List<ViewDocumAprobEntity> buscarRolDocumId(int idTipoAutorizacion, int idAutorizacion) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM ViewDocumAprobEntity obj "
                    + " WHERE obj.idAutorizacion = :idTipoDocum "
                    + " AND (obj.idReferencia = :idAutorizacion "
                    + "     OR obj.idReferencia IS NULL) ";
            Query query = session.createQuery(sql);
            query.setParameter("idTipoDocum", idTipoAutorizacion);
            query.setParameter("idAutorizacion", idAutorizacion);

            //Se obtienen los resutltados
            return (List<ViewDocumAprobEntity>) query.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRol.buscarRolDocumId",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

}
