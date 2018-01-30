/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Adjunto;
import cr.ac.ucr.sigebi.domain.Tipo;
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
@Repository(value = "adjuntoDao")
@Scope("request")
public class AdjuntoDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional
    public void agregar(Adjunto adjunto) {
        try {
            this.persist(adjunto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.adjunto.agregar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void eliminar(Adjunto adjunto) {       
        try {
             this.delete(adjunto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.adjunto.eliminar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public List<Adjunto> buscarPorDocumento(Tipo tipoDocumento, Long idDocumento) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM Adjunto obj WHERE obj.idTipo.id = :idTipo and obj.idDocumento = :idDocumento";
            Query query = session.createQuery(sql);
            query.setParameter("idTipo", tipoDocumento.getId());
            query.setParameter("idDocumento", idDocumento);

            //Se obtienen los resutltados
            return (List<Adjunto>) query.list();

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.adjunto.buscarPorReferencia",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    
    @Transactional(readOnly = true)
    public List<Adjunto> buscarPorReferencia(Long idReferencia) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM Adjunto obj WHERE obj.idReferencia = : idReferencia";
            Query query = session.createQuery(sql);
            query.setParameter("idReferencia", idReferencia);

            //Se obtienen los resutltados
            return (List<Adjunto>) query.list();

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.adjunto.buscarPorReferencia",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
}
