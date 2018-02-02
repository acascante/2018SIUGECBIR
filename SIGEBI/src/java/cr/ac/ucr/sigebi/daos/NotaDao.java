/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Bien;
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
    
    @Transactional(readOnly = true) 
    public Nota buscarPorId(Long id) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM Nota obj WHERE obj.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);
            return (Nota) query.list().get(0);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.nota.buscarPorId", "Error Nota traer por id " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true) 
    public List<Nota> listar(Bien bien) {
        
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM Nota obj WHERE obj.bien = :bien";
            Query query = session.createQuery(sql);
            query.setParameter("bien", bien);
            return (List<Nota>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.Nota.traerTodo", "Error Nota traer Todo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    public void eliminar(Nota nota){
        try {
            this.delete(nota);
        } catch(Exception e) {
            throw new FWExcepcion("sigebi.error.dao.Nota.eliminar", "Error eliminarNota " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional
    public void guardar(Nota nota) {
        try {
            persist(nota);
        } catch (Exception e) {
            throw new FWExcepcion("sigebi.error.dao.Nota.guardar", "Error guardarNota " + this.getClass(), e.getCause());
        }
    }
}