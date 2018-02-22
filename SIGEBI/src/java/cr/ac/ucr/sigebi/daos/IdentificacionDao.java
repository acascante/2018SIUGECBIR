/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Identificacion;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
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
@Repository(value = "IdentificacionDao")

public class IdentificacionDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;
    
    @Transactional(readOnly = true)
    public List<Identificacion> listar() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("from Identificacion"); 
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public Identificacion siguienteDisponible(Estado estado
                                        , UnidadEjecutora unidadEjecutora
    ) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT i FROM Identificacion i "
                        + "WHERE i.estado = :estado "
                        + "     and i.unidadEjecutora = :unidadEjecutora";
            Query query = session.createQuery(sql);
            query.setParameter("estado", estado);
            query.setParameter("unidadEjecutora", unidadEjecutora);
            query.setMaxResults(1); 
            return (Identificacion) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    public void actualizar(Identificacion identificacion) {
        try {
            persist(identificacion);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
}
