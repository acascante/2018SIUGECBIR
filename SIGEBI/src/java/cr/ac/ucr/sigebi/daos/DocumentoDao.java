/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.entities.DocumentoEntity;
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
@Repository(value = "documentoDao")
@Scope("request")
public class DocumentoDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional
    public void agregar(DocumentoEntity obj) {
        try {
            this.persist(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.documento.agregar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void modificar(DocumentoEntity obj) {       
        try {
             this.persist(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.documento.modificar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional
    public void eliminar(DocumentoEntity obj) {       
        try {
             this.delete(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.documento.eliminar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public DocumentoEntity buscarPorId(Long idDocumento) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM DocumentoEntity obj WHERE obj.idDocumento = :idDocumento";
            Query query = session.createQuery(sql);
            query.setParameter("idDocumento", idDocumento);

            //Se obtienen los resutltados
            return (DocumentoEntity) query.list().get(0);

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.documento.buscarPorId",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    
    @Transactional(readOnly = true)
    public List<DocumentoEntity> buscarPorTipoProceso(Integer idTipoProceso) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM DocumentoEntity obj WHERE obj.idProceso.idTipo = :idTipoProceso";
            Query query = session.createQuery(sql);
            query.setParameter("idTipoProceso", idTipoProceso);

            //Se obtienen los resutltados
            return (List<DocumentoEntity>) query.list();

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.documento.buscarPorDocumento",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Long contarDocumentosValidator(Long idDocumento, Integer idProceso,Integer orden,String nombre) {
        Session session = dao.getSessionFactory().openSession();
        try {

            //Se genera el query para la busqueda
            Query q = this.creaQueryContar(idDocumento, idProceso, orden, nombre, session);

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

    private Query creaQueryContar(Long idDocumento, Integer idProceso,Integer orden,String nombre,Session session) {
        String sql = "SELECT count(obj) FROM DocumentoEntity obj ";        
        //Select
        sql = sql + " WHERE  1 = 1 ";
        if (idDocumento != null && idDocumento> 0) {
            sql = sql + " AND obj.idDocumento != :idDocumento ";
        }
        if (idProceso != null && idProceso> 0) {
            sql = sql + " AND obj.idProceso.idTipo = :idProceso ";
        }
        if (orden != null && orden> 0) {
            sql = sql + " AND obj.orden = :orden ";
        }
        if (nombre != null && nombre.length() > 0) {
            sql = sql + " AND upper(obj.nombre) = upper(:nombre) ";
        }
        
        Query q = session.createQuery(sql);
        if (idDocumento != null && idDocumento> 0) {
            q.setParameter("idDocumento", idDocumento);
        }
        if (idProceso != null && idProceso> 0) {
            q.setParameter("idProceso", idProceso);
        }
        if (orden != null && orden> 0) {
            q.setParameter("orden", orden);
        }
        if (nombre != null && nombre.length() > 0) {
            q.setParameter("nombre", nombre);
        }
        return q;
    }
    
}
