/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Factura;
import cr.ac.ucr.sigebi.domain.SolicitudDonacion;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jorge.serrano
 */
@Repository(value = "facturaDao")

public class FacturaDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;
    
    @Transactional(readOnly = true) 
    public Factura buscarPorId(Long id) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM Factura obj WHERE obj.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);
            return (Factura) query.list().get(0);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.facturaDao.buscarPorId", "Error Factura traer por id " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true) 
    public List<Factura> listar(SolicitudDonacion solicitudDonacion) throws FWExcepcion {
        
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM Factura obj WHERE obj.solicitudDonacion = :solicitudDonacion";
            Query query = session.createQuery(sql);
            query.setParameter("solicitudDonacion", solicitudDonacion);
            return (List<Factura>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.facturaDao.listar", "Error Factura traer Todo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    public void eliminar(Factura factura) throws FWExcepcion {
        try {
            this.delete(factura);
        } catch(Exception e) {
            throw new FWExcepcion("sigebi.error.facturaDao.eliminar", "Error eliminarFactura " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional
    public void agregar(Factura factura) throws FWExcepcion {
        try {
            persist(factura);
        } catch (Exception e) {
            throw new FWExcepcion("sigebi.error.facturaDao.agregar", "Error guardarFactura " + this.getClass(), e.getCause());
        }
    }
}