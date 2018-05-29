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
import cr.ac.ucr.sigebi.domain.BienCaracteristica;
import cr.ac.ucr.sigebi.domain.Tipo;
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
 * @author alvaro.cascante
 */
@Repository(value = "bienCaracteristicaDao")

public class BienCaracteristicaDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional(readOnly = true)
    public List<BienCaracteristica> listar() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("from BienCaracteristica");
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.bienCaracteristica.dao.traerTodo", "Error obtener los registros de bienCaracteristica " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public List<BienCaracteristica> listarPorBien(Bien bien) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT carac FROM BienCaracteristica carac WHERE carac.bien = :bien";
            Query query = session.createQuery(sql);
            query.setParameter("bien", bien);
            
            return (List<BienCaracteristica>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();        
        } 
    }
    
    @Transactional(readOnly = true)
    public BienCaracteristica buscarPorId(Long id) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT carac FROM BienCaracteristica carac WHERE carac.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);
            
            return (BienCaracteristica) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();        
        } 
    }

    @Transactional(readOnly = true)
    public BienCaracteristica buscarPorBienTipo(Bien bien, Tipo tipo) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT carac FROM BienCaracteristica carac WHERE carac.bien = :bien AND carac.tipo = := tipo";
            Query query = session.createQuery(sql);
            query.setParameter("bien", bien);
            query.setParameter("tipo", tipo);
            return (BienCaracteristica) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();        
        } 
    }
    
    @Transactional
    public void almacenar(BienCaracteristica caracteristica) throws FWExcepcion {
        try {
            persist(caracteristica);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void eliminar(BienCaracteristica caracteristica)throws FWExcepcion {
        try {
            delete(caracteristica);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
}
