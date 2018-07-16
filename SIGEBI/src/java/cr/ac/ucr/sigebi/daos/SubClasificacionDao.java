/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Clasificacion;
import cr.ac.ucr.sigebi.domain.SubClasificacion;
import java.util.List;
import org.hibernate.HibernateException;
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
@Repository(value = "subClasificacionDao")

public class SubClasificacionDao extends GenericDaoImpl{
    
    @Autowired
    private DaoHelper dao;

    @Transactional(readOnly = true)
    public List<SubClasificacion> listar() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("from SubClasificacion sc ORDER BY sc.nombre"); 
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public SubClasificacion buscarPorId(Long id) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM SubClasificacion obj WHERE obj.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);
            return (SubClasificacion) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.clasificacion.dao.buscarPorId", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    public List<SubClasificacion> listar(Clasificacion clasificacion) {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT sc FROM SubClasificacion sc WHERE sc.clasificacion = :clasificacion ORDER BY sc.nombre";
            Query query = session.createQuery(sql);
            query.setParameter("clasificacion", clasificacion);
            return (List<SubClasificacion>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    public void guardar(SubClasificacion item){
        Session session = dao.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(item);
            session.getTransaction().commit();
            
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.SubClasificacionDao.guardar", "Error al guardar elemento " + this.getClass(), e.getCause());
        }
        catch(Exception e){
            throw new FWExcepcion("sigebi.error.SubClasificacionDao.guardar", "Error al guardar elemento " + this.getClass(), e.getCause());
        }
        finally {
            session.close();
        }
    }
}