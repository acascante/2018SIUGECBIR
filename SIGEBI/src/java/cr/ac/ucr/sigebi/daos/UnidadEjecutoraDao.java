/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
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
 * @author jairo.cisneros
 */
@Repository(value = "UnidadEjecutoraDao")

public class UnidadEjecutoraDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional(readOnly = true)
    public List<UnidadEjecutora> listar() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("from UnidadEjecutora where idTipoUnidad = 'UCO'"); 
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public List<UnidadEjecutora> listar(String idUnidad, String nombreUnidad, Long unidadActual) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            //StringBuilder sql = new StringBuilder ("select obj from UnidadEjecutora obj where idTipoUnidad = 'UCO'  ");
            StringBuilder sql = new StringBuilder ("select obj from UnidadEjecutora obj where idTipoUnidad = 'PR'  ");
            
            if(idUnidad != null && idUnidad.length() > 0){
                sql.append(" and to_char(obj.id) like :idUnidad");
            }
            if(nombreUnidad != null && nombreUnidad.length() > 0){
                sql.append(" and UPPER(obj.descripcion) LIKE upper(:nombreUnidad)");
            }
            sql.append(" and UPPER(obj.id) != upper(:unidadActual)");
            
            Query query = session.createQuery(sql.toString());
            
            query.setParameter("unidadActual", unidadActual );
            if(idUnidad != null && idUnidad.length() > 0){
                query.setParameter("idUnidad", '%'+ idUnidad +'%');
            }
            if(nombreUnidad != null && nombreUnidad.length() > 0){
                query.setParameter("nombreUnidad", '%'+ nombreUnidad +'%');
            }

            query.setFirstResult(0);
            query.setMaxResults(5);
                
            return (List<UnidadEjecutora>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.unidadEejecutora.dao.listarUnidades", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public UnidadEjecutora buscarPorId(Long id) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM UnidadEjecutora obj WHERE obj.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);
            return (UnidadEjecutora) query.list().get(0);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.unidadEejecutora.dao.traerPorId", "Error obtener el registro de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
}
