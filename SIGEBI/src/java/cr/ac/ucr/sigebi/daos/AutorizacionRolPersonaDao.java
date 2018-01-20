/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.AutorizacionRolPersona;
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
@Repository(value = "autorizacionRolPersonaDao")
@Scope("request")
public class AutorizacionRolPersonaDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional
    public void agregar(AutorizacionRolPersona obj) throws FWExcepcion {
        try {
            this.persist(obj);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRolPersona.agregar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void modificar(AutorizacionRolPersona obj) throws FWExcepcion {
        try {
            this.persist(obj);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRolPersona.modificar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void eliminar(AutorizacionRolPersona obj) throws FWExcepcion {
        try {
            this.delete(obj);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRolPersona.eliminar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public List<AutorizacionRolPersona> buscarPorAutorizacionRol(Long idAutorizacion, Long idRol) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM AutorizacionRolPersona obj "
                    + " WHERE obj.autorizacion.id = :idAutorizacion"
                    + " and obj.rol.id = :idRol";
            Query query = session.createQuery(sql);
            query.setParameter("idAutorizacion", idAutorizacion);
            query.setParameter("idRol", idRol);

            //Se obtienen los resutltados
            return (List<AutorizacionRolPersona>) query.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRolPersona.buscarPorAutorizacionRol",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public List<AutorizacionRolPersona> buscarUsuariosPorAutorizacion(Long idAutorizacion, Long numUnidadEjec) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM AutorizacionRolPersona obj "
                    + " WHERE obj.autorizacion.id = :idAutorizacion"
                    + " and obj.unidadEjecutora.id = :numUnidadEjec";
            Query query = session.createQuery(sql);
            query.setParameter("idAutorizacion", idAutorizacion);
            query.setParameter("numUnidadEjec", numUnidadEjec);

            //Se obtienen los resutltados
            return (List<AutorizacionRolPersona>) query.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRolPersona.buscarPorAutorizacionRol",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Long contarPorAutorizacionRol(Long idAutorizacion, Long idRol) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT count(obj.idAutorizacion) FROM AutorizacionRolPersona obj "
                    + " WHERE obj.autorizacion.id = :idAutorizacion"
                    + " and obj.rol.id = :idRol";
            Query query = session.createQuery(sql);
            query.setParameter("idAutorizacion", idAutorizacion);
            query.setParameter("idRol", idRol);

            //Se obtienen los resutltados
            return (Long) query.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRolPersona.contarPorAutorizacionRol",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public AutorizacionRolPersona buscarPorRolAutorizacionUsuario(Long idRol
                                                                , Long idAutorizacion
                                                                , String idUsuario
                                                        ) throws FWExcepcion {
        try {
            Session session = this.dao.getSessionFactory().openSession();
            String sql = "select obj from AutorizacionRolPersona obj ";
            sql = sql + " where obj.rol.id = :idRol";
            sql = sql + " and obj.autorizacion.id = :idAutorizacion";
            sql = sql + " and obj.usuarioSeguridad.id = :idUsuario";
            Query query = session.createQuery(sql);
            query.setParameter("idAutorizacion", idAutorizacion);
            query.setParameter("idRol", idRol);
            query.setParameter("idUsuario", idUsuario);
            List<AutorizacionRolPersona> results = query.list();
            if (!results.isEmpty()) {
                return (AutorizacionRolPersona) results.get(0);
            } else {
                return null;
            }
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRolPersona.RolAutorizacionUsuario",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public AutorizacionRolPersona buscarAutorizacionPorAutorizacionRolUsuario(String codigoRol
                                                                            , Long idAutorizacion
                                                                            , String idUsuario
                                                                    ) throws FWExcepcion {
        try {
            Session session = this.dao.getSessionFactory().openSession();
            String sql = "select obj from AutorizacionRolPersona obj ";
            sql = sql + " where obj.rol.codigo = :codigoRol";
            sql = sql + " and obj.autorizacion.id = :idAutorizacion";
            sql = sql + " and obj.usuarioSeguridad.id = :idUsuario";
            Query query = session.createQuery(sql);
            query.setParameter("idAutorizacion", idAutorizacion);
            query.setParameter("codigoRol", codigoRol);
            query.setParameter("idUsuario", idUsuario);
            List<AutorizacionRolPersona> results = query.list();
            if (!results.isEmpty()) {
                return (AutorizacionRolPersona) results.get(0);
            } else {
                return null;
            }
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRolPersona.buscarAutorizacionAutorizacionRolUsuario",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public List<AutorizacionRolPersona> buscarRolesProAutorizacionUsuario(
                                                    Long idAutorizacion
                                                    , String idUsuario
                                                    , Long unidadEjecutora
                                            ) throws FWExcepcion {
        try {
            Session session = this.dao.getSessionFactory().openSession();
            String sql = "select obj from AutorizacionRolPersona obj ";
            sql = sql + " where obj.autorizacion.id = :idAutorizacion";
            sql = sql + " and obj.usuarioSeguridad.id = :idUsuario";
            sql = sql + " and obj.unidadEjecutora.id = :unidadEjecutora";
            Query query = session.createQuery(sql);
            query.setParameter("idAutorizacion", idAutorizacion);
            query.setParameter("idUsuario", idUsuario);
            query.setParameter("unidadEjecutora", unidadEjecutora);

            List<AutorizacionRolPersona> results = query.list();

            return results;
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRolPersona.buscarRolesProAutorizacionUsuario",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

}
