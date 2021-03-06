/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Justificacion;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jorge.serrano
 */
@Repository(value = "JustificacionDao")

public class JustificacionDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;
    
    
    @Transactional
    public void guardar(Justificacion valor){
        try {
            persist(valor);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.ActaDao.guardar", "Error guardar el registro de tipo " + this.getClass(), e.getCause());
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.ActaDao.guardar", "Error guardar el registro de tipo " + this.getClass(), ex.getCause());
        }
    }
    
    @Transactional
    public Justificacion traerPorId(Integer idJustificacion) {
        Session session = dao.getSessionFactory().openSession();
        Justificacion resp = new Justificacion();
        try {
            // De momento utilizamos la referencia como el id del bien
            String sql = "from Justificacion s where s.idJustificacion = :pidJustificacion";
            Query q = session.createQuery(sql);
            q.setParameter("pidJustificacion",idJustificacion);
            
            List l = q.list();
            resp = (Justificacion) l.get(0);
            
            return resp;
        } catch (Exception e) {
            return resp;
        }
        finally{
           session.close();
       }
    }
    
    
    public List<Justificacion> listarPorDocumento(String documentoTipo, Long idDocumento) {
        Session session = dao.getSessionFactory().openSession();
        try {
            // De momento utilizamos la referencia como el id del bien
            String sql = "from Justificacion s "
                        + "where s.documentoTipo = :documentoTipo "
                        + "and s.idDocumento = :idDocumento";
            Query q = session.createQuery(sql);
            q.setParameter("documentoTipo",documentoTipo);
            q.setParameter("idDocumento",idDocumento);
            
            return (List<Justificacion>) q.list();
        } catch (Exception e) {
            return null;
        }
        finally{
            session.close();
        }
    }
    
    
}
