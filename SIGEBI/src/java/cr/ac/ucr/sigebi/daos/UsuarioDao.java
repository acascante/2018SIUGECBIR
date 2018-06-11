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
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.domain.Usuario;
import cr.ac.ucr.sigebi.domain.ViewAutorizacionRolUsuarioUnidad;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jorge.serrano
 */
@Repository(value = "usuarioDao")

public class UsuarioDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional(readOnly = true)
    public Usuario buscarPorId(String idUsuario) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM Usuario obj WHERE obj.id = :idUsuario";
            Query query = session.createQuery(sql);
            query.setParameter("idUsuario", idUsuario);

            //Se obtienen los resutltados
            return (Usuario) query.list().get(0);

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.usuarioDao.buscarPorId","Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios(String idUsuario, String nombreCompleto, String correo, Integer pPrimerRegistro, Integer pUltimoRegistro) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            //Se genera el query para la busqueda
            Query q = this.creaQueryListar(idUsuario, nombreCompleto, correo, false, session);

            //Paginacion
            if (!(pPrimerRegistro.equals(1) && pUltimoRegistro.equals(1))) {
                q.setFirstResult(pPrimerRegistro);
                q.setMaxResults(pUltimoRegistro - pPrimerRegistro);
            }
            //Se obtienen los resutltados
            return (List<Usuario>) q.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.usuarioDao.listarUsuarios","Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Long contarUsuarios(String idUsuario, String nombreCompleto, String correo) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {

            //Se genera el query para la busqueda
            Query q = this.creaQueryListar(idUsuario, nombreCompleto, correo, true, session);

            //Se obtienen los resutltados
            return (Long) q.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.usuarioDao.contarUsuarios", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    private Query creaQueryListar(String idUsuario, String nombreCompleto, String correo, Boolean contar, Session session) {
        String sql;
        if (contar) {
            sql = "SELECT count(obj) FROM Usuario obj ";
        } else {
            sql = "SELECT obj FROM Usuario obj ";
        }
        //Select
        sql = sql + " WHERE  1 = 1 ";
        if (idUsuario != null && idUsuario.length() > 0) {
            sql = sql + " AND upper(obj.id) like upper(:idUsuario) ";
        }
        if (nombreCompleto != null && nombreCompleto.length() > 0) {
            sql = sql + " AND upper(obj.nombreCompleto) like upper(:nombreCompleto) ";
        }
        if (correo != null && correo.length() > 0) {
            sql = sql + " AND upper(obj.correo) like upper(:correo) ";
        }

        Query q = session.createQuery(sql);

        if (idUsuario != null && idUsuario.length() > 0) {
            q.setParameter("idUsuario", '%' + idUsuario + '%');
        }
        if (nombreCompleto != null && nombreCompleto.length() > 0) {
            q.setParameter("nombreCompleto", '%' + nombreCompleto + '%');
        }
        if (correo != null && correo.length() > 0) {
            q.setParameter("correo", '%' + correo + '%');
        }
        return q;
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuariosUnidad(UnidadEjecutora unidadEjecutora) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            //Se genera el query para la busqueda
            String sql = "SELECT obj.usuario FROM UnidadEjecutoraUsuario obj ";
            sql = sql + " WHERE  obj.unidadEjecutora = :unidadEjecutora ";
            Query q = session.createQuery(sql);
            q.setParameter("unidadEjecutora", unidadEjecutora);
        
            //Se obtienen los resutltados
            return (List<Usuario>) q.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.usuarioDao.listarUsuariosUnidad","Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
     @Transactional(readOnly = true)
    public List<ViewAutorizacionRolUsuarioUnidad> listarUsuariosGestionProceso(String idUsuario, String nombreCompleto, String correo, AutorizacionRol autorizacionRol, UnidadEjecutora unidadEjecutora, Integer pPrimerRegistro, Integer pUltimoRegistro) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            //Se genera el query para la busqueda
            Query q = this.creaQueryListarGestionProceso(idUsuario, nombreCompleto, correo, autorizacionRol, unidadEjecutora, false, session);

            //Paginacion
            if (!(pPrimerRegistro.equals(1) && pUltimoRegistro.equals(1))) {
                q.setFirstResult(pPrimerRegistro);
                q.setMaxResults(pUltimoRegistro - pPrimerRegistro);
            }
            //Se obtienen los resutltados
            return (List<ViewAutorizacionRolUsuarioUnidad>) q.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.usuarioDao.listarUsuariosGestionProceso","Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Long contarUsuariosGestionProceso(String idUsuario, String nombreCompleto, String correo, AutorizacionRol autorizacionRol, UnidadEjecutora unidadEjecutora) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {

            //Se genera el query para la busqueda
            Query q = this.creaQueryListarGestionProceso(idUsuario, nombreCompleto, correo, autorizacionRol, unidadEjecutora, true, session);

            //Se obtienen los resutltados
            return (Long) q.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.usuarioDao.contarUsuariosGestionProceso", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    } 

    
    private Query creaQueryListarGestionProceso(String idUsuario, String nombreCompleto, String correo, AutorizacionRol autorizacionRol, UnidadEjecutora unidadEjecutora, Boolean contar, Session session) {
        String sql;
        if (contar) {
            sql = "SELECT count(obj) FROM ViewAutorizacionRolUsuarioUnidad obj ";
        } else {
            sql = "SELECT obj FROM ViewAutorizacionRolUsuarioUnidad obj ";
        }
        //Select
        sql = sql + " WHERE obj.autorizacionRol = :autorizacionRol and obj.unidadEjecutora = :unidadEjecutora";
        if (idUsuario != null && idUsuario.length() > 0) {
            sql = sql + " AND upper(obj.usuarioSeguridad.id) like upper(:idUsuario) ";
        }
        if (nombreCompleto != null && nombreCompleto.length() > 0) {
            sql = sql + " AND upper(obj.usuarioSeguridad.nombreCompleto) like upper(:nombreCompleto) ";
        }
        if (correo != null && correo.length() > 0) {
            sql = sql + " AND upper(obj.usuarioSeguridad.correo) like upper(:correo) ";
        }
        
        sql = sql + " order by obj.tieneAutorizacionRol desc";

        Query q = session.createQuery(sql);
        q.setParameter("autorizacionRol", autorizacionRol);        
        q.setParameter("unidadEjecutora", unidadEjecutora);
        
        if (idUsuario != null && idUsuario.length() > 0) {
            q.setParameter("idUsuario", '%' + idUsuario + '%');
        }
        if (nombreCompleto != null && nombreCompleto.length() > 0) {
            q.setParameter("nombreCompleto", '%' + nombreCompleto + '%');
        }
        if (correo != null && correo.length() > 0) {
            q.setParameter("correo", '%' + correo + '%');
        }        
        return q;
    }
}
