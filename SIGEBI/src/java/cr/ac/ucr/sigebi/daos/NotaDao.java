/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Autorizacion;
import cr.ac.ucr.sigebi.domain.Nota;
import java.util.List;
import org.hibernate.HibernateException;
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
    public Nota traerPorId(Long pId) {
        
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM Nota obj WHERE obj.id = :idNota";
            Query query = session.createQuery(sql);
            query.setParameter("idNota", pId);

            //Se obtienen los resutltados
            return (Nota) query.list().get(0);

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.nota.buscarPorId",
                    "Error Nota traer por id " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional
    public List<Nota> traerTodo(Long idBien) {
        
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM Nota obj WHERE obj.idBien = :idBien";
            Query query = session.createQuery(sql);
            query.setParameter("idBien", idBien);

            //Se obtienen los resutltados
            return (List<Nota>) query.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.Nota.traerTodo",
                    "Error Nota traer Todo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }

    }
    
    public void eliminarNota(Nota nota){
        try{
            this.delete(nota);
        }catch(Exception e){
            throw new FWExcepcion("sigebi.error.dao.Nota.eliminar",
                    "Error eliminarNota " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional
    public void guardarNota(Nota nota ) {
        try {
            persist(nota);
        } catch (Exception e) {
            throw new FWExcepcion("sigebi.error.dao.Nota.guardar",
                    "Error guardarNota " + this.getClass(), e.getCause());
        }

    }
    
}
