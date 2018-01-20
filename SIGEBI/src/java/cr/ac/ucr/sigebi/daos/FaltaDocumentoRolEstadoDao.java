/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.entities.DocumentoRolEstadoEntity;
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
@Repository(value = "documentoRolEstadoDao")
@Scope("request")
public class FaltaDocumentoRolEstadoDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional
    public void agregar(DocumentoRolEstadoEntity documento) {
        this.persist(documento);
    }
    
    @Transactional
    public void modificar(DocumentoRolEstadoEntity documento) {
        this.persist(documento);
    }
    
    @Transactional(readOnly = true)
    public List<DocumentoRolEstadoEntity> buscarDocumentosUsuario(String idUsuario
                                                                , Long numUnidadEjec
                                                                , Long idReferencia
                                                                , Long idDocumento
    ) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM DocumentoRolEstadoEntity obj, DocumentoRolPersonaEntity per "
                    + " WHERE per.idUsuarioSeguridad.idUsuario = :idUsuario "
                    + " and per.numUnidadEjec = :numUnidadEjec "
                    + " and per.idDocumento.idDocumento = :idDocumento"
                    + " and obj.idReferencia = :idReferencia"
                    + " and obj.idRol.idRol = per.idRol.idRol "
                    + " and obj.idDocumento.idDocumento = per.idDocumento.idDocumento ";
   
            Query query = session.createQuery(sql);
            query.setParameter("idUsuario", idUsuario);
            query.setParameter("idDocumento", idDocumento);
            query.setParameter("numUnidadEjec", numUnidadEjec);
            query.setParameter("idReferencia", idReferencia);

            //Se obtienen los resutltados
            return (List<DocumentoRolEstadoEntity>) query.list();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getCause());
            throw new FWExcepcion("sigebi.error.dao.documentoRolEstado.buscarDocumentosUsuario",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    
    @Transactional(readOnly = true)
    public List<DocumentoRolEstadoEntity> buscarDocumentosTipoDocumento(Long idReferencia, Long idDocumento) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM DocumentoRolEstadoEntity obj "
                    + " WHERE obj.idReferencia = :idReferencia "
                    + " and obj.idDocumento.idDocumento = :idDocumento order by obj.idDocumento.orden";
   
            Query query = session.createQuery(sql);
            query.setParameter("idDocumento", idDocumento);
            query.setParameter("idReferencia", idReferencia);
            
            //Se obtienen los resutltados
            return (List<DocumentoRolEstadoEntity>) query.list();

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.documentoRolEstado.buscarDocumentosTipoDocumento",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
}
