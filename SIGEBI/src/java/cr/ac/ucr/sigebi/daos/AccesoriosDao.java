/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.sigebi.entities.AccesoriosEntity;
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
@Repository(value = "accesoriosDao")
@Scope("request")
public class AccesoriosDao  extends GenericDaoImpl{
    
    @Autowired
    private DaoHelper dao;
    
    
    public String guardarAccesorio( AccesoriosEntity dato ){
        
        Session session = dao.getSessionFactory().openSession();
        try {
            //Obtiene el ID
            String sql = "SELECT COUNT( s ), nvl( MAX(s.idAccesorio), -1) FROM AccesoriosEntity s";
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
            dato.setIdAccesorio(resp);
            
            
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
    
    
    public List<AccesoriosEntity> traerAccesorios( int idBien){
        Session session = dao.getSessionFactory().openSession();
        List<AccesoriosEntity> resp = new ArrayList<AccesoriosEntity>();
        try {
            String sql = "from AccesoriosEntity s where s.idBien = :pidDato";
            Query q = session.createQuery(sql);
            q.setParameter("pidDato",idBien);
            resp = (List<AccesoriosEntity>) q.list();
            session.close();
            return resp;
        } catch (Exception e) {
            if(session.isOpen())
                session.close();
            return resp;
        }
    }
    
    
    public String eliminarAccesorio(AccesoriosEntity dato){
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "delete from AccesoriosEntity s where s.idAccesorio = :pidDato";
            Query q = session.createQuery(sql);
            q.setParameter("pidDato",dato.getIdAccesorio());
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
