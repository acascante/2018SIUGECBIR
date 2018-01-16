/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.entities.RolEntity;
import java.util.List;
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
@Repository(value = "rolDao")
@Scope("request")
public class RolDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional
    public void agregar(RolEntity obj) {
        try {
            this.persist(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.rol.agregar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void modificar(RolEntity obj) {
        try {
            this.persist(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.rol.modificar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public RolEntity buscarPorId(Long idRol) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM RolEntity obj WHERE obj.idRol = :idRol";
            Query query = session.createQuery(sql);
            query.setParameter("idRol", idRol);

            //Se obtienen los resutltados
            return (RolEntity) query.list().get(0);

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.rol.buscarPorId",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional
    public void eliminar(RolEntity obj) {
        try {
            this.delete(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.rol.eliminar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public List<RolEntity> listarTodos() {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM RolEntity obj ";
            Query query = session.createQuery(sql);

            //Se obtienen los resutltados
            return (List<RolEntity>) query.list();

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.rol.listarTodos",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public List<RolEntity> listarRolesNoAsociados(Long idDocumento) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM RolEntity obj ";
            sql = sql + " where 0 = (select count(docu.idRol) from DocumentoRolEntity docu where docu.idRol.idRol = obj.idRol and docu.idDocumento.idDocumento = :idDocumento)";
            Query query = session.createQuery(sql);
            query.setParameter("idDocumento", idDocumento);
            
            //Se obtienen los resutltados
            return (List<RolEntity>) query.list();

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.rol.listarRolesNoAsociados",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public List<RolEntity> listarRoles(Integer codigo,
            String nombre,
            Integer estado,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ) {
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
            return (List<RolEntity>) q.list();

        } catch (Exception e) {
            e.printStackTrace();
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
    ) {
        Session session = dao.getSessionFactory().openSession();
        try {

            //Se genera el query para la busqueda
            Query q = this.creaQueryListar(codigo, nombre, estado, true, session);

            //Se obtienen los resutltados
            return (Long) q.uniqueResult();

        } catch (Exception e) {
            e.printStackTrace();
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
            sql = "SELECT count(obj) FROM RolEntity obj ";
        } else {
            sql = "SELECT obj FROM RolEntity obj ";
        }
        //Select
        sql = sql + " WHERE  1 = 1 ";
        if (codigo != null && codigo > 0) {
            sql = sql + " AND upper(obj.idTipo.idTipo) = :codigo ";
        }
        if (nombre != null && nombre.length() > 0) {
            sql = sql + " AND upper(obj.nombre) like upper(:nombre) ";
        }
        if (estado != null && estado > 0) {
            sql = sql + " AND obj.idEstado.idEstado = :estado";
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
    public Long contarRolesValidator(Long idRolDiferente, String codigo, String nombre) {
        Session session = dao.getSessionFactory().openSession();
        try {

            //Se genera el query para la busqueda
            Query q = this.creaQueryContarValidator(idRolDiferente, codigo, nombre, session);

            //Se obtienen los resutltados
            return (Long) q.uniqueResult();

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.documento.contarDocumentos",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    private Query creaQueryContarValidator(Long idRolDiferente, String codigo, String nombre,Session session) {
        String sql = "SELECT count(obj) FROM RolEntity obj ";        
        //Select
        sql = sql + " WHERE  1 = 1 ";
        if (idRolDiferente != null && idRolDiferente> 0) {
            sql = sql + " AND obj.idRol != :idRolDiferente ";
        }
        if (codigo != null && codigo.length() > 0) {
            sql = sql + " AND upper(obj.codigo) = upper(:codigo) ";
        }
        if (nombre != null && nombre.length() > 0) {
            sql = sql + " AND upper(obj.nombre) = upper(:nombre) ";
        }
        
        Query q = session.createQuery(sql);
        if (idRolDiferente != null && idRolDiferente> 0) {
            q.setParameter("idRolDiferente", idRolDiferente);
        }
        if (codigo != null && codigo.length() > 0) {
            q.setParameter("codigo", codigo);
        }        
        if (nombre != null && nombre.length() > 0) {
            q.setParameter("nombre", nombre);
        }
        return q;
    }
    
    @Transactional(readOnly = true)
    public Long verificaRolUso(Long idRol) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT COUNT(*) FROM RolEntity obj";
                   sql = sql + " WHERE obj.idRol.idRol = :idRol and ((SELECT COUNT(obj1.idRol) from DocumentoRolEntity obj1 where obj1.idRol.idRol = :idRol) > 0 ";
                   sql = sql + " or (SELECT COUNT(obj1.idRol) from DocumentoRolEstadoEntity obj1 where obj1.idRol.idRol = :idRol) > 0 ";
                   sql = sql + " or (SELECT COUNT(obj1.idRol) from DocumentoRolPersonaEntity obj1 where obj1.idRol.idRol = :idRol) > 0) ";
            Query query = session.createQuery(sql);
            query.setParameter("idRol", idRol);
            
            //Se obtienen los resutltados
             return (Long) query.uniqueResult();

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.documentoRol.contarPorDocumento",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
}
