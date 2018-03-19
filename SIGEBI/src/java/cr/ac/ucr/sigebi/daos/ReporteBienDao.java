/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.ReporteBien;
import cr.ac.ucr.sigebi.domain.Usuario;
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

@Repository(value = "reporteBienDao")
public class ReporteBienDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;
    
    @Transactional(readOnly = true)
    public List<ReporteBien> listar() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("from ReporteBien"); 
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.reporteBien.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public ReporteBien buscarPorId(Long id) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT entity FROM ReporteBien entity WHERE entity.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);

            return (ReporteBien) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.reporteBien.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public List<ReporteBien> listarPorUsuario(Usuario usuario) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            StringBuilder sql = new StringBuilder("SELECT entity FROM ReporteBien entity WHERE ");
            sql.append("entity.usuario = :usuario");
            Query query = session.createQuery(sql.toString());
            query.setParameter("usuario", usuario);
            
            return (List<ReporteBien>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.reporteBien.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }
    }
    
    @Transactional
    public void salvar(ReporteBien reporteBien) throws FWExcepcion {
        try {
            persist(reporteBien);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.reporteBien.error.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional
    public void eliminar(ReporteBien reporteBien) throws FWExcepcion {
        try {
            delete(reporteBien);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.reporteBien.error.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }   
    }
}