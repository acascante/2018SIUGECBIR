/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Accesorio;
import cr.ac.ucr.sigebi.domain.Bien;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jorge.serrano
 */
@Repository(value = "AccesorioDao")
@Scope("request")
public class AccesorioDao  extends GenericDaoImpl{
    
    @Autowired
    private DaoHelper dao;
    
    @Transactional(readOnly = true)
    public List<Accesorio> listar() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("from Accesorio"); 
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public List<Accesorio> listarPorBien(Bien bien) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String hql = "SELECT a FROM Accesorio a WHERE a.bien = :bien";
            return find(hql);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();        
        } 
    }
    
    @Transactional(readOnly = true)
    public Accesorio buscarPorId(Long id) throws FWExcepcion {
        return load(Accesorio.class, id);
    }
    
    @Transactional
    public void almacenar(Accesorio accesorio) throws FWExcepcion {
        try {
            persist(accesorio);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void eliminar(Accesorio accesorio)throws FWExcepcion {
        try {
            delete(accesorio);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
}