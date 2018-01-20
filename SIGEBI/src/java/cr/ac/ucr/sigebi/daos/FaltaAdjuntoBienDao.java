/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.sigebi.entities.AdjuntoBienEntity;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jorge.serrano
 */
@Repository(value = "adjuntoBienDao")
@Scope("request")
public class FaltaAdjuntoBienDao  extends GenericDaoImpl{
    
    @Autowired
    private DaoHelper dao;
    
    public String guardarAdjunto( AdjuntoBienEntity dato ){
        
        Session session = dao.getSessionFactory().openSession();
        try {
            //Obtiene el ID
            String sql = "SELECT COUNT( s ), nvl( MAX(s.idAdjunto), -1) FROM AdjuntoBienEntity s";
            Query q = session.createQuery(sql);

            List l = q.list();

            Object result[] = (Object[]) l.get(0);
            Long cant = (Long) result[0];
            Integer max = (Integer) result[1];
            Integer resp;
            Integer cant2 = cant.intValue();
            if (cant2 > max) 
                resp =  cant2 + 1;
            else
                resp = max + 1;
            dato.setIdAdjunto(resp);
            
            
            //El ID_BIEN lo guardamos en la columna referencia
            session.beginTransaction();
            session.save(dato);
            session.getTransaction().commit();
            
            session.close();
            return "";
        } catch (Exception e) {
            if(session.isOpen())
                session.close();
            return e.getMessage();
        }
    }
    
    public List<AdjuntoBienEntity> traerAdjuntos( int idBien){
        Session session = dao.getSessionFactory().openSession();
        List<AdjuntoBienEntity> resp = new ArrayList<AdjuntoBienEntity>();
        try {
            // De momento utilizamos la referencia como el id del bien
            String sql = "from AdjuntoBienEntity s where s.idBien = :pidBien";
            Query q = session.createQuery(sql);
            q.setParameter("pidBien",idBien);
            resp = (List<AdjuntoBienEntity>) q.list();
            session.close();
            return resp;
        } catch (Exception e) {
            if(session.isOpen())
                session.close();
            return resp;
        }
    }
    
    
    public String eliminarAdjunto(AdjuntoBienEntity dato){
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "delete from AdjuntoBienEntity s where s.idAdjunto = :pidAdjunto";
            Query q = session.createQuery(sql);
            q.setParameter("pidAdjunto",dato.getIdAdjunto());
            int res = q.executeUpdate();
            session.close();
            return "";
        } catch (Exception e) {
            if(session.isOpen())
                session.close();
            return e.getMessage();
        }
    }
      
    
}
