/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.entities.DocumentoRolEntity;
import cr.ac.ucr.sigebi.entities.ViewDocumAprobEntity;
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
@Repository(value = "documentoRolDao")
@Scope("request")
public class DocumentoRolDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional
    public void agregar(DocumentoRolEntity obj) {
        try {
            this.persist(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.documentoRol.agregar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void modificar(DocumentoRolEntity obj) {
        try {
            this.persist(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.documentoRol.modificar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void eliminar(DocumentoRolEntity obj) {
        try {
            this.delete(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.documentoRol.eliminar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public List<DocumentoRolEntity> buscarPorDocumento(Long idDocumento) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM DocumentoRolEntity obj WHERE obj.idDocumento.idDocumento = :idDocumento";
            Query query = session.createQuery(sql);
            query.setParameter("idDocumento", idDocumento);

            //Se obtienen los resutltados
            return (List<DocumentoRolEntity>) query.list();

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.documentoRol.buscarPorDocumento",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Long contarPorDocumento(Long idDocumento) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT count(obj.idDocumento) FROM DocumentoRolEntity obj WHERE obj.idDocumento.idDocumento = :idDocumento";
            Query query = session.createQuery(sql);
            query.setParameter("idDocumento", idDocumento);
            
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
    
    @Transactional(readOnly = true)
    public DocumentoRolEntity buscarPorRolDocumento(Long idRol, Long idDocumento) {
        try {
            Session session = this.dao.getSessionFactory().openSession();
            String sql = "select obj from DocumentoRolEntity obj ";
            sql = sql + " where obj.idRol.idRol = :idRol";
            sql = sql + " and obj.idDocumento.idDocumento = :idDocumento";
            Query query = session.createQuery(sql);
            query.setParameter("idDocumento", idDocumento);
            query.setParameter("idRol", idRol);
            List<DocumentoRolEntity> results = query.list();
            if (!results.isEmpty()) {
                return (DocumentoRolEntity) results.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.documentoRol.buscarPorRolDocumento",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    
    @Transactional(readOnly = true)
    public List<ViewDocumAprobEntity> buscarRolDocumId( int idTipoDocumento, int idDocumento) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM ViewDocumAprobEntity obj "
                        + " WHERE obj.idDocumento = :idTipoDocum "
                        + " AND (obj.idReferencia = :idDocumento "
                        + "     OR obj.idReferencia IS NULL) ";
            Query query = session.createQuery(sql);
            query.setParameter("idTipoDocum", idTipoDocumento);
            query.setParameter("idDocumento", idDocumento);

            //Se obtienen los resutltados
            return (List<ViewDocumAprobEntity>) query.list();

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.documentoRol.buscarRolDocumId",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    
}
