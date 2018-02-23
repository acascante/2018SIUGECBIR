/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Convenio;
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

@Repository(value = "convenioDao")
public class ConvenioDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;
    
    @Transactional(readOnly = true)
    public List<Convenio> listar() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("from Convenio"); 
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.convenioDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional
    public void salvar(Convenio convenio) throws FWExcepcion {
        try {
            persist(convenio);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.convenioDao.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public Long contar(Long id, String institucion, String responsable, String oficio, Integer estado) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQuery(session, true, id, institucion, responsable, oficio, estado);
            return (Long)query.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.convenioDao.contarConvenioes", "Error contando los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }        
    }
    
    @Transactional(readOnly = true)
    public List<Convenio> listar(Integer primerRegistro, Integer ultimoRegistro, Long id, String institucion, String responsable, String oficio, Integer estado) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQuery(session, false, id, institucion, responsable, oficio, estado);
            
            if(!(primerRegistro.equals(1) && ultimoRegistro.equals(1))) {
                query.setFirstResult(primerRegistro);
                query.setMaxResults(ultimoRegistro - primerRegistro);
            }
            //Se obtienen los resutltados
            return (List<Convenio>) query.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.convenioDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }
    }
    
    private Query creaQuery(Session session, Boolean contar, Long id, String institucion, String responsable, String oficio, Integer estado) {
        StringBuilder sql = new StringBuilder("SELECT ");
        if (contar) {
            sql.append("COUNT(entity) FROM Convenio entity ");
        } else {
            sql.append("entity FROM Convenio entity ");
        }
        
        sql.append(" WHERE 1 = 1 ");
        if(id != null && id > 0){
           sql.append(" AND entity.id = :id ");
        } else {
            if(institucion != null && institucion.length() > 0){
                sql.append(" AND UPPER(entity.institucion) LIKE UPPER(:institucion) ");
            }
            if(responsable != null && responsable.length() > 0){
                sql.append(" AND UPPER(entity.responsable) LIKE UPPER(:responsable) ");
            }
            if(oficio != null && oficio.length() > 0){
                sql.append(" AND UPPER(entity.oficio) like upper(:oficio) ");
            }
            if(estado != null && estado > 0){
                sql.append(" AND entity.estado.id = :estado ");
            }
        }
        sql.append(" ORDER BY entity.id asc");
        
        Query query = session.createQuery(sql.toString());
        if(id != null && id > 0){
            query.setParameter("id", id);
        } else {
            if(institucion != null && institucion.length() > 0){
                query.setParameter("institucion", '%' + institucion + '%');
            }
            if(responsable != null && responsable.length() > 0){
                query.setParameter("responsable", '%' + responsable + '%');
            }
            if(oficio != null && oficio.length() > 0){
                query.setParameter("oficio", '%' + oficio + '%');
            }
            if(estado != null && estado > 0){
                query.setParameter("estado", estado);
            }
        }
        return query;
    }
}