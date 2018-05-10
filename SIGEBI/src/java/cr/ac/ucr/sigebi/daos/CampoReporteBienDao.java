/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.CampoReporteBien;
import cr.ac.ucr.sigebi.domain.ReporteBien;
import java.util.List;
import java.util.Set;
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

@Repository(value = "campoReporteBienDao")
public class CampoReporteBienDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;
    
    @Transactional(readOnly = true)
    public List<CampoReporteBien> listar() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("from CampoReporteBien"); 
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.campoReporteBien.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public CampoReporteBien buscarPorId(Long id) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT entity FROM CampoReporteBien entity WHERE entity.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);

            return (CampoReporteBien) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.campoReporteBien.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public Set<CampoReporteBien> listarPorReporte(ReporteBien reporteBien) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            StringBuilder sql = new StringBuilder("SELECT entity FROM CampoReporteBien entity WHERE ");
            sql.append("entity.reporteBien = :reporteBien " );
            Query query = session.createQuery(sql.toString());
            query.setParameter("reporteBien", reporteBien);
            return (Set<CampoReporteBien>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.campoReporteBien.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }
    }
    
    @Transactional
    public void salvar(CampoReporteBien campoReporteBien) throws FWExcepcion {
        try {
            persist(campoReporteBien);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.campoReporteBien.error.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional
    public void salvar(Set<CampoReporteBien> campos) throws FWExcepcion {
        try {
            persist(campos.toArray());
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.exclusiones.error.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional
    public void eliminar(CampoReporteBien campoReporteBien) throws FWExcepcion {
        try {
            delete(campoReporteBien);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.campoReporteBien.error.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }   
    }
    
    @Transactional
    public void eliminar(Set<CampoReporteBien> campos) throws FWExcepcion {
        try {
            delete(campos.toArray());
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.campoReporteBien.error.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }   
    }
}