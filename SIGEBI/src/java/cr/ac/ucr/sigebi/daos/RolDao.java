/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Rol;
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
@Repository(value = "rolDao")

public class RolDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional
    public void agregar(Rol obj) throws FWExcepcion {
        try {
            this.persist(obj);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.rol.agregar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void modificar(Rol obj) throws FWExcepcion {
        try {
            this.persist(obj);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.rol.modificar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public Rol buscarPorId(Long idRol) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM Rol obj WHERE obj.id = :idRol";
            Query query = session.createQuery(sql);
            query.setParameter("idRol", idRol);

            //Se obtienen los resutltados
            return (Rol) query.list().get(0);

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.rol.buscarPorId",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional
    public void eliminar(Rol obj) throws FWExcepcion {
        try {
            this.delete(obj);
        } catch (Exception e) {
            throw new FWExcepcion("sigebi.error.dao.rol.eliminar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public List<Rol> listarTodos() throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM Rol obj ";
            Query query = session.createQuery(sql);

            //Se obtienen los resutltados
            return (List<Rol>) query.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.rol.listarTodos",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public List<Rol> listarRolesNoAsociados(Long idDocumento) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM Rol obj ";
            sql = sql + " where 0 = (select count(docu.rol) from DocumentoAutorizacion docu "
                    + "where docu.autorizacionRol.rol.id = obj.id and docu.documento.id = :idDocumento)";
            Query query = session.createQuery(sql);
            query.setParameter("idDocumento", idDocumento);

            //Se obtienen los resutltados
            return (List<Rol>) query.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.rol.listarRolesNoAsociados",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public List<Rol> listarRoles(Integer codigo,
            String nombre,
            Integer estado,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            //Se genera el query para la busqueda
            Query q = this.creaQueryListar(codigo, nombre, estado, false, session);

            //Paginacion
            if (!(pPrimerRegistro.equals(1) && pUltimoRegistro.equals(1))) {
                q.setFirstResult(pPrimerRegistro);
                q.setMaxResults(pUltimoRegistro - pPrimerRegistro);
            }
            //Se obtienen los resutltados
            return (List<Rol>) q.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.rol.listarRoles",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Long contarRoles(Integer codigo,
            String nombre,
            Integer estado
    ) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {

            //Se genera el query para la busqueda
            Query q = this.creaQueryListar(codigo, nombre, estado, true, session);

            //Se obtienen los resutltados
            return (Long) q.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.rol.contarRoles",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    private Query creaQueryListar(Integer codigo,
            String nombre,
            Integer estado,
            Boolean contar,
            Session session
    ) {
        String sql;
        if (contar) {
            sql = "SELECT count(obj) FROM Rol obj ";
        } else {
            sql = "SELECT obj FROM Rol obj ";
        }
        //Select
        sql = sql + " WHERE  1 = 1 ";
        if (codigo != null && codigo > 0) {
            sql = sql + " AND upper(obj.codigo) = :codigo ";
        }
        if (nombre != null && nombre.length() > 0) {
            sql = sql + " AND upper(obj.nombre) like upper(:nombre) ";
        }
        if (estado != null && estado > 0) {
            sql = sql + " AND obj.estado.idEstado = :estado";
        }

        Query q = session.createQuery(sql);
        if (codigo != null && codigo > 0) {
            q.setParameter("codigo", codigo);
        }
        if (nombre != null && nombre.length() > 0) {
            q.setParameter("nombre", '%' + nombre + '%');
        }
        if (estado != null && estado > 0) {
            q.setParameter("estado", estado);
        }
        return q;
    }

    @Transactional(readOnly = true)
    public Long contarRolesValidator(Long idRolDiferente, Integer codigo, String nombre) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {

            //Se genera el query para la busqueda
            Query q = this.creaQueryContarValidator(idRolDiferente, codigo, nombre, session);

            //Se obtienen los resutltados
            return (Long) q.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.documento.contarDocumentos",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    private Query creaQueryContarValidator(Long idRolDiferente, Integer codigo, String nombre, Session session) {
        String sql = "SELECT count(obj) FROM Rol obj ";
        //Select
        sql = sql + " WHERE  1 = 1 ";
        if (idRolDiferente != null && idRolDiferente > 0) {
            sql = sql + " AND obj.id != :idRolDiferente ";
        }
        if (codigo != null && codigo > 0) {
            sql = sql + " AND upper(obj.codigo) = upper(:codigo) ";
        }
        if (nombre != null && nombre.length() > 0) {
            sql = sql + " AND upper(obj.nombre) = upper(:nombre) ";
        }

        Query q = session.createQuery(sql);
        if (idRolDiferente != null && idRolDiferente > 0) {
            q.setParameter("idRolDiferente", idRolDiferente);
        }
        if (codigo != null && codigo > 0) {
            q.setParameter("codigo", codigo);
        }
        if (nombre != null && nombre.length() > 0) {
            q.setParameter("nombre", nombre);
        }
        return q;
    }

    @Transactional(readOnly = true)
    public Long verificaRolUso(Long idRol) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT COUNT(*) FROM Rol obj";
            sql = sql + " WHERE obj.id = :idRol and ";
            sql = sql + "((SELECT COUNT(obj1.id) from DocumentoAutorizacion obj1 where obj1.autorizacionRol.rol.id = :idRol) > 0 ";
            sql = sql + " or (SELECT COUNT(obj1.id) from AutorizacionRolPersona obj1 where obj1.autorizacionRol.rol.id = :idRol) > 0) ";
            Query query = session.createQuery(sql);
            query.setParameter("idRol", idRol);

            //Se obtienen los resutltados
            return (Long) query.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.rol.verificaRolUso",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
}
