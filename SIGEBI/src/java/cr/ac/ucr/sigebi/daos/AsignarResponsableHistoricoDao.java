/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.AsignarResponsableHistorico;
import cr.ac.ucr.sigebi.domain.Bien;
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
 * @author jorge.serrano
 */
@Repository(value = "asignarResponsableHistoricoDao")
public class AsignarResponsableHistoricoDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional(readOnly = true)
    public List<AsignarResponsableHistorico> listarRespoansables(Bien bien, UnidadEjecutora unidadEjecutora)  throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT hist FROM AsignarResponsableHistorico hist "
                    + "WHERE hist.unidadEjecutora = :unidadEjecutora "
                    + " AND hist.bien = :bien "; 
            
            Query query = session.createQuery(sql);
            query.setParameter("unidadEjecutora", unidadEjecutora);
            query.setParameter("bien", bien);
            return (List<AsignarResponsableHistorico>) query.list();
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.AsignarResponsableHistoricoDao.listarMisBienes", "Error obtener los registros " + this.getClass(), e.getCause());
        }finally {
            session.close();
        }
    }
    
    @Transactional
    public void guardar(AsignarResponsableHistorico asigHist) throws FWExcepcion {
        try {
            persist(asigHist);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.AsignarResponsableHistoricoDao.guardar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }

    
    @Transactional(readOnly = true)
    public AsignarResponsableHistorico getHistoricoActivo(Bien bien)  throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT hist FROM AsignarResponsableHistorico hist "
                    + "WHERE hist.bien = :bien "
                    + " AND hist.fechaHasta is null"; 
            
            Query query = session.createQuery(sql);
            query.setParameter("bien", bien);
            List<AsignarResponsableHistorico> historicos = (List<AsignarResponsableHistorico> ) query.list();
            if(historicos.size() > 0)
                return ( AsignarResponsableHistorico ) query.list().get(0);
            else
                return new AsignarResponsableHistorico();
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.AsignarResponsableHistoricoDao.listarMisBienes", "Error obtener los registros " + this.getClass(), e.getCause());
        }finally {
            session.close();
        }
    }
    
    
    
    
    
}
