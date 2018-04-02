/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Persona;
import java.util.Date;
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
@Repository(value = "personaDao")

public class PersonaDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;
    
    @Transactional(readOnly = true)
    public List<Persona> listar() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("from Persona p ORDER BY p.primerApellido"); 
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.personas.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public Long contar(Boolean estudiante, Boolean funcionario, Long id, String nombre, String primerApellido, String segundoApellido) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQuery(session, true, estudiante, funcionario, id, nombre, primerApellido, segundoApellido);
            return (Long)query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.personas.error.listar", "Error contando los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();        
        }        
    }
    
    @Transactional(readOnly = true)
    public List<Persona> listar(Integer primerRegistro, Integer ultimoRegistro, Boolean estudiante, Boolean funcionario, Long id, String nombre, String primerApellido, String segundoApellido) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQuery(session, false, estudiante, funcionario, id, nombre, primerApellido, segundoApellido);
            
            if(!(primerRegistro.equals(1) && ultimoRegistro.equals(1))) {
                query.setFirstResult(primerRegistro);
                query.setMaxResults(ultimoRegistro - primerRegistro);
            }
            return (List<Persona>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.personas.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();        
        }
    }
    
    private Query creaQuery(Session session, Boolean contar, Boolean estudiante, Boolean funcionario, Long id, String nombre, String primerApellido, String segundoApellido) {
        StringBuilder sql = new StringBuilder("SELECT ");
        if (contar) {
            sql.append("COUNT(entity) FROM Persona entity ");
        } else {
            sql.append("entity FROM Persona entity ");
        }
        
        sql.append("WHERE 1=1 "); 
        if(estudiante) {
             sql.append("AND entity.esEstudiante = 'S' ");
        }
        if(funcionario) {
             sql.append("AND entity.esFuncionario = 'S' ");
        }
        if(id != null && id > 0) {
           sql.append(" AND entity.id = :id ");
        } else {
            if(nombre != null && nombre.length() > 0){
                sql.append(" AND UPPER(entity.nombre) LIKE UPPER(:nombre) ");
            }
            if(primerApellido != null && primerApellido.length() > 0){
                sql.append(" AND UPPER(entity.primerApellido) like upper(:primerApellido) ");
            }
            if(segundoApellido != null && segundoApellido.length() > 0){
                sql.append(" AND UPPER(entity.segundoApellido) like upper(:segundoApellido) ");
            }
        }
        sql.append(" ORDER BY entity.primerApellido asc");
        
        Query query = session.createQuery(sql.toString());
        if(id != null && id > 0){
            query.setParameter("id", id);
        } else {
            if(nombre != null && nombre.length() > 0){
                query.setParameter("nombre", '%' + nombre + '%');
            }
            if(primerApellido != null && primerApellido.length() > 0){
                query.setParameter("primerApellido", '%' + primerApellido + '%');
            }
            if(segundoApellido != null && segundoApellido.length() > 0){
                query.setParameter("segundoApellido", '%' + segundoApellido + '%');
            }
        }
        return query;
    }
}