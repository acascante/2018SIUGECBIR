/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Ubicacion;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author jorge.serrano
 */
@Repository(value = "ubicacionDao")
@Scope("request")
public class UbicacionDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional(readOnly = true)
    public List<Ubicacion> listar(Long idUnidadEjec) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            
            String sql = "SELECT ub FROM Ubicacion ub WHERE ub.unidadEjecutora.id = :unidad ";
            Query query = session.createQuery(sql);
            query.setParameter("unidad", idUnidadEjec);
            
            return (List<Ubicacion>) query.list();

            //return dao.getHibernateTemplate().find("from Ubicacion s WHERE s.idUnidadEjecutora = :idUnidadEjec "); 
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public Ubicacion buscarPorId(Long id) throws FWExcepcion {
        return load(Ubicacion.class, id);
    }
    
    @Transactional
    public void almacenar(Ubicacion ubicacion) throws FWExcepcion {
        try {
            persist(ubicacion);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void eliminar(Ubicacion ubicacion)throws FWExcepcion {
        try {
            delete(ubicacion);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
}