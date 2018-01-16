/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.entities.DocumentoRolPersonaEntity;
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
@Repository(value = "documentoRolPersonaDao")
@Scope("request")
public class DocumentoRolPersonaDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional
    public void agregar(DocumentoRolPersonaEntity obj) {
        try {
            this.persist(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.documentoRolPersona.agregar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void modificar(DocumentoRolPersonaEntity obj) {       
        try {
             this.persist(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.documentoRolPersona.modificar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional
    public void eliminar(DocumentoRolPersonaEntity obj) {       
        try {
             this.delete(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.documentoRolPersona.eliminar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public List<DocumentoRolPersonaEntity> buscarPorDocumentoRol(Long idDocumento, Long idRol) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM DocumentoRolPersonaEntity obj "
                    + " WHERE obj.idDocumento.idDocumento = :idDocumento"
                    + " and obj.idRol.idRol = :idRol";
            Query query = session.createQuery(sql);
            query.setParameter("idDocumento", idDocumento);
            query.setParameter("idRol", idRol);

            //Se obtienen los resutltados
            return (List<DocumentoRolPersonaEntity>) query.list();

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.documentoRolPersona.buscarPorDocumentoRol",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    
    
    @Transactional(readOnly = true)
    public List<DocumentoRolPersonaEntity> buscarUsuaiosPorDocumento(Long idDocumento, Integer numUnidadEjec) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM DocumentoRolPersonaEntity obj "
                    + " WHERE obj.idDocumento.idDocumento = :idDocumento"
                    + " and obj.numUnidadEjec = :numUnidadEjec";
            Query query = session.createQuery(sql);
            query.setParameter("idDocumento", idDocumento);
            query.setParameter("numUnidadEjec", numUnidadEjec);

            //Se obtienen los resutltados
            return (List<DocumentoRolPersonaEntity>) query.list();

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.documentoRolPersona.buscarPorDocumentoRol",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    
    
    @Transactional(readOnly = true)
    public Long contarPorDocumentoRol(Long idDocumento, Long idRol) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT count(obj.idDocumento) FROM DocumentoRolPersonaEntity obj "
                    + " WHERE obj.idDocumento.idDocumento = :idDocumento"
                    + " and obj.idRol.idRol = :idRol";
            Query query = session.createQuery(sql);
            query.setParameter("idDocumento", idDocumento);
            query.setParameter("idRol", idRol);

            //Se obtienen los resutltados
             return (Long) query.uniqueResult();

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.documentoRolPersona.contarPorDocumentoRol",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    
    @Transactional(readOnly = true)
    public DocumentoRolPersonaEntity buscarPorRolDocumentoUsuario(Long idRol, Long idDocumento, String idUsuario) {
        try {
            Session session = this.dao.getSessionFactory().openSession();
            String sql = "select obj from DocumentoRolPersonaEntity obj ";
            sql = sql + " where obj.idRol.idRol = :idRol";
            sql = sql + " and obj.idDocumento.idDocumento = :idDocumento";
            sql = sql + " and obj.idUsuarioSeguridad.idUsuario = :idUsuario";
            Query query = session.createQuery(sql);
            query.setParameter("idDocumento", idDocumento);
            query.setParameter("idRol", idRol);
            query.setParameter("idUsuario", idUsuario);
            List<DocumentoRolPersonaEntity> results = query.list();
            if (!results.isEmpty()) {
                return (DocumentoRolPersonaEntity) results.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.documentoRolPersona.RolDocumentoUsuario",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public DocumentoRolPersonaEntity buscarAutorizacionDocumentoRolUsuario(String codigoRol, Long idDocumento, String idUsuario) {
        try {
            Session session = this.dao.getSessionFactory().openSession();
            String sql = "select obj from DocumentoRolPersonaEntity obj ";
            sql = sql + " where obj.idRol.codigo = :codigoRol";
            sql = sql + " and obj.idDocumento.idDocumento = :idDocumento";
            sql = sql + " and obj.idUsuarioSeguridad.idUsuario = :idUsuario";
            Query query = session.createQuery(sql);
            query.setParameter("idDocumento", idDocumento);
            query.setParameter("codigoRol", codigoRol);
            query.setParameter("idUsuario", idUsuario);
            List<DocumentoRolPersonaEntity> results = query.list();
            if (!results.isEmpty()) {
                return (DocumentoRolPersonaEntity) results.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.documentoRolPersona.buscarAutorizacionDocumentoRolUsuario",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    
    @Transactional(readOnly = true)
    public List<DocumentoRolPersonaEntity> buscarRolesProDocumentoUsuario(Long idDocumento, String idUsuario, int unidadEjecutora) {
        try {
            Session session = this.dao.getSessionFactory().openSession();
            String sql = "select obj from DocumentoRolPersonaEntity obj ";
            sql = sql + " where obj.idDocumento.idDocumento = :idDocumento";
            sql = sql + " and obj.idUsuarioSeguridad.idUsuario = :idUsuario";
            sql = sql + " and obj.numUnidadEjec = :unidadEjecutora";
            Query query = session.createQuery(sql);
            query.setParameter("idDocumento", idDocumento);
            query.setParameter("idUsuario", idUsuario);
            query.setParameter("unidadEjecutora", unidadEjecutora);
            
            
            List<DocumentoRolPersonaEntity> results = query.list();
            
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.documentoRolPersona.buscarAutorizacionDocumentoRolUsuario",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
}
