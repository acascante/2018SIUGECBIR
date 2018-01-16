/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.entities.NotaEntity;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jorge.serrano
 */
@Repository(value = "notaDao")
@Scope("request")
public class NotaDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;

    
    @Transactional
    public NotaEntity traerPorId(Integer pId) {
        NotaEntity tipoAux = new NotaEntity();
        try {
            String[] nameParams = new String[1];
            nameParams[0] = "PID_NOTA";
            Object[] params = new Object[1];
            params[0] = pId;
            tipoAux = (NotaEntity) dao.getHibernateTemplate().findByNamedQueryAndNamedParam("NotaEntity.findById", nameParams, params).get(0);
        } catch (Exception e) {
            throw new FWExcepcion ("sigebi.error.NotaEntity.findById :" + e.getMessage(),
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
        return tipoAux;
    }
    
    @Transactional
    public List<NotaEntity> traerTodo(Integer idBien) {
        try {
            String[] nameParams = new String[1];
            nameParams[0] = "PID_BIEN";
            Object[] params = new Object[1];
            params[0] = idBien;
            //tipoAux = (NotaEntity) dao.getHibernateTemplate().findByNamedQueryAndNamedParam("NotaEntity.findById", nameParams, params).get(0);
            
            return (List<NotaEntity>) dao.getHibernateTemplate().findByNamedQueryAndNamedParam("NotaEntity.findAll", nameParams, params);
        
        } catch (Exception e) {
            throw new FWExcepcion ("sigebi.error.NotaEntity.findAll",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }

    }
    
    public String eliminarNota(NotaEntity nota){
        try{
            
            Session session = dao.getSessionFactory().openSession();
            
            session.beginTransaction();
            
            session.delete(nota);
            
            session.getTransaction().commit();
            session.close();
            
            return "";
        }catch(Exception err){
            return err.getMessage();
        }
    }
    
    @Transactional
    public Integer obtenerId() {
        try {
            Session session = dao.getSessionFactory().openSession();
            String sql = "SELECT COUNT(s), MAX(s.idNota) FROM NotaEntity s";
            Query q = session.createQuery(sql);
            
            List l = q.list();

            Object result[] = (Object[]) l.get(0);
            Long cant = (Long) result[0];
            Integer max = (Integer) result[1];
            
            session.close();
            Integer cant2 = cant.intValue();
            
            if(cant2 > max){
                return cant2+1;
            }
            return max+1;
        
        } catch (Exception e) {
            return 1;
        }
    }
    
    
    @Transactional
    public String guardarNota(NotaEntity nota ) {
        try {
            
            Session session = dao.getSessionFactory().openSession();
            
            session.beginTransaction();
            
            session.save(nota);
            
            session.getTransaction().commit();
            session.close();
            
            return "";
        
        } catch (Exception e) {
            return e.getMessage();
        }

    }
    
}
