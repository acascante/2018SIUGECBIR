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
import cr.ac.ucr.sigebi.domain.AutorizacionRol;
import cr.ac.ucr.sigebi.domain.AutorizacionRolPersona;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.domain.Usuario;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jairo.cisneros
 */
@Repository(value = "autorizacionRolPersonaDao")

public class AutorizacionRolPersonaDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional
    public void agregar(AutorizacionRolPersona obj) throws FWExcepcion {
        try {
            this.persist(obj);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRolPersonaDao.agregar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void modificar(AutorizacionRolPersona obj) throws FWExcepcion {
        try {
            this.persist(obj);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRolPersonaDao.modificar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void eliminar(AutorizacionRolPersona obj) throws FWExcepcion {
        try {
            this.delete(obj);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRolPersonaDao.eliminar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public List<AutorizacionRolPersona> buscar(AutorizacionRol autorizacionRol, UnidadEjecutora unidadEjecutora) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM AutorizacionRolPersona obj "
                    + " WHERE obj.autorizacionRol = :autorizacionRol"
                    + " and obj.unidadEjecutora = :unidadEjecutora";
            Query query = session.createQuery(sql);
            query.setParameter("autorizacionRol", autorizacionRol);
            query.setParameter("unidadEjecutora", unidadEjecutora);

            //Se obtienen los resutltados
            return (List<AutorizacionRolPersona>) query.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRolPersonaDao.buscar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    // Busco las personas registradas en una Autorización por ROLES
    @Transactional(readOnly = true)
    public List<AutorizacionRolPersona> buscar(Autorizacion autorizacion, UnidadEjecutora unidadEjecutora) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM AutorizacionRolPersona obj "
                    + " WHERE obj.autorizacionRol.autorizacion = :autorizacion"
                    + " and obj.unidadEjecutora = :unidadEjecutora";
            Query query = session.createQuery(sql);
            query.setParameter("autorizacion", autorizacion);
            query.setParameter("unidadEjecutora", unidadEjecutora);

            //Se obtienen los resutltados
            return (List<AutorizacionRolPersona>) query.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRolPersonaDao.buscarPorAutorizacion",
                    "Error obtener los registros de buscarPorAutorizacion " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    // Busco si la persona registrada en una Autorización por ROLES
    @Transactional(readOnly = true)
    public List<AutorizacionRolPersona> buscar(Autorizacion autorizacion, UnidadEjecutora unidadEjecutora, Usuario usuario) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM AutorizacionRolPersona obj "
                    + " WHERE obj.autorizacionRol.autorizacion = :autorizacion"
                    + " and obj.usuarioSeguridad = :usuario"
                    + " and obj.unidadEjecutora = :unidadEjecutora";
            Query query = session.createQuery(sql);
            query.setParameter("autorizacion", autorizacion);
            query.setParameter("usuario", usuario);
            query.setParameter("unidadEjecutora", unidadEjecutora);

            //Se obtienen los resutltados
            return (List<AutorizacionRolPersona>) query.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRolPersonaDao.buscarPorAutorizacion",
                    "Error obtener los registros de buscarPorAutorizacion " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public boolean buscarAutorizacion(Autorizacion autorizacion, UnidadEjecutora unidadEjecutora, Usuario usuario) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            StringBuilder sql = new StringBuilder("SELECT obj FROM AutorizacionRolPersona obj");
                    sql.append(" WHERE obj.autorizacionRol.autorizacion = :autorizacion");
                    sql.append(" AND obj.usuarioSeguridad = :usuario");
                    sql.append(" AND obj.unidadEjecutora = :unidadEjecutora");

            Query query = session.createQuery(sql.toString());
            query.setParameter("autorizacion", autorizacion);
            query.setParameter("usuario", usuario);
            query.setParameter("unidadEjecutora", unidadEjecutora);

            //Se obtienen los resutltados
            List<AutorizacionRolPersona> items  = query.list();
            return !items.isEmpty();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRolPersonaDao.buscarPorAutorizacion",
                    "Error obtener los registros de buscarPorAutorizacion " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public AutorizacionRolPersona buscar(AutorizacionRol autorizacionRol, Usuario usuario, UnidadEjecutora unidadEjecutora) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM AutorizacionRolPersona obj "
                    + " WHERE obj.autorizacionRol = :autorizacionRol"
                    + " and obj.unidadEjecutora = :unidadEjecutora"
                    + " and obj.usuarioSeguridad = :usuario";
            Query query = session.createQuery(sql);
            query.setParameter("autorizacionRol", autorizacionRol);
            query.setParameter("usuario", usuario);
            query.setParameter("unidadEjecutora", unidadEjecutora);
            List<AutorizacionRolPersona> results = query.list();
            if (!results.isEmpty()) {
                return (AutorizacionRolPersona) results.get(0);
            } else {
                return null;
            }
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRolPersona.buscar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Long contar(AutorizacionRol autorizacionRol) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT count(obj.id) FROM AutorizacionRolPersona obj "
                    + " WHERE obj.autorizacionRol = :autorizacionRol";
            Query query = session.createQuery(sql);
            query.setParameter("autorizacionRol", autorizacionRol);

            //Se obtienen los resutltados
            return (Long) query.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRolPersona.contar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public AutorizacionRolPersona buscar(Integer codigoAutorizacion, Integer codigoRol, Usuario usuario, UnidadEjecutora unidadEjecutora) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM AutorizacionRolPersona obj "
                    + " WHERE obj.autorizacionRol.rol.codigo = :codigoRol"
                    + " and obj.autorizacionRol.autorizacion.codigo = :codigoAutorizacion"
                    + " and obj.unidadEjecutora = :unidadEjecutora"
                    + " and obj.usuarioSeguridad = :usuario";
            Query query = session.createQuery(sql);
            query.setParameter("codigoAutorizacion", codigoAutorizacion);
            query.setParameter("codigoRol", codigoRol);
            query.setParameter("usuario", usuario);
            query.setParameter("unidadEjecutora", unidadEjecutora);
            List<AutorizacionRolPersona> results = query.list();
            if (!results.isEmpty()) {
                return (AutorizacionRolPersona) results.get(0);
            } else {
                return null;
            }
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRolPersona.buscar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }

    }

    public AutorizacionRolPersona buscar(Integer codigoRol, Usuario usuario) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM AutorizacionRolPersona obj "
                    + " WHERE obj.autorizacionRol.rol.codigo = :codigoRol"
                    + " and obj.usuarioSeguridad = :usuario";
            Query query = session.createQuery(sql);
            query.setParameter("codigoRol", codigoRol);
            query.setParameter("usuario", usuario);
            List<AutorizacionRolPersona> results = query.list();
            if (!results.isEmpty()) {
                return (AutorizacionRolPersona) results.get(0);
            } else {
                return null;
            }
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.autorizacionRolPersona.buscar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

}
