/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Sincronizar;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
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
@Repository(value = "bienDao")
@Scope("request")
public class BienDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional(readOnly = true)
    public List<Bien> listar() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("from Bien");
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.bienCaracteristica.dao.traerTodo", "Error obtener los registros de bien " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public List<Bien> listarPorUnidadEjecutora(UnidadEjecutora unidadejecutora) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT b FROM Bien b WHERE b.unidadejecutora = :unidadejecutora";
            Query query = session.createQuery(sql);
            query.setParameter("unidadejecutora", unidadejecutora);

            return (List<Bien>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public List<Bien> listarPorUnidadEjecutoraEstado(UnidadEjecutora unidadejecutora, Estado estado) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT b FROM Bien b WHERE b.unidadejecutora = :unidadejecutora AND b.estadoInterno = :estado";
            Query query = session.createQuery(sql);
            query.setParameter("unidadejecutora", unidadejecutora);
            query.setParameter("estado", estado);
            return (List<Bien>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Bien buscarPorId(Long id) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT b FROM Bien b WHERE b.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);

            return (Bien) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public List<Bien> listar(Integer primerRegistro, Integer ultimoRegistro, UnidadEjecutora unidadejecutora, Long id, String identificacion, String descripcion, String marca, String modelo, String serie, Tipo tipo, Estado... estados) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQuery(Boolean.FALSE, session, unidadejecutora, id, identificacion, descripcion, marca, modelo, serie, tipo, estados);
            if (!(primerRegistro.equals(1) && ultimoRegistro.equals(1))) {
                query.setFirstResult(primerRegistro);
                query.setMaxResults(ultimoRegistro - primerRegistro);
            }
            return (List<Bien>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    public Long contar(UnidadEjecutora unidadejecutora, Long id, String identificacion, String descripcion, String marca, String modelo, String serie, Tipo tipo, Estado... estados) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQuery(Boolean.TRUE, session, unidadejecutora, id, identificacion, descripcion, marca, modelo, serie, tipo, estados);
            return (Long) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.contarNotificaciones", "Error contando los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    public void almacenar(Bien bien) throws FWExcepcion {
        try {
            persist(bien);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }

    public void actualizar(Bien bien) throws FWExcepcion {
        try {
            persist(bien);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }

    private Query creaQuery(Boolean contar, Session session, UnidadEjecutora unidadEjecutora, Long id, String identificacion, String descripcion, String marca, String modelo,  String serie, Tipo tipo, Estado... estados) {
        StringBuilder sql = new StringBuilder(" ");
        if (contar) {
            sql.append("SELECT count(b) FROM Bien b ");
        } else {
            sql.append("SELECT b FROM Bien b ");
        }
        
        sql.append("WHERE b.unidadEjecutora = :unidadEjecutora ");
        if(id != null && id > 0) {
           sql.append(" AND b.id = :id ");
        } else {
            if(identificacion != null && identificacion.length() > 0){
               sql.append(" AND UPPER(b.identificacion.identificacion) LIKE UPPER(:identificacion) ");
            }
            if(descripcion != null && descripcion.length() > 0){
                sql.append(" AND UPPER(b.descripcion) LIKE UPPER(:descripcion) ");
            }
            if(marca != null && marca.length() > 0){
                sql.append(" AND UPPER(b.resumenBien.marca) LIKE UPPER(:marca) ");
            }
            if(modelo != null && modelo.length() > 0){
                sql.append(" AND UPPER(b.resumenBien.modelo) LIKE UPPER(:modelo) ");
            }
            if(serie != null && serie.length() > 0){
                sql.append(" AND UPPER(b.resumenBien.serie) LIKE UPPER(:serie) ");
            }
            if(estados != null){
                sql.append(" AND b.estado IN (:estados) ");
            }
            if (tipo != null) {
                sql.append(" AND b.tipo IN (:tipo) ");
            }
        }
        sql.append(" ORDER BY b.id ASC ");
        Query q = session.createQuery(sql.toString());
        q.setParameter("unidadEjecutora", unidadEjecutora);
        if(id != null && id > 0) {
            q.setParameter("id", id);
        } else {
            if (identificacion != null && identificacion.length() > 0) {
                q.setParameter("identificacion", identificacion);
            }
            if (descripcion != null && descripcion.length() > 0) {
                q.setParameter("descripcion", '%' + descripcion + '%');
            }
            if (marca != null && marca.length() > 0) {
                q.setParameter("marca", '%' + marca + '%');
            }
            if (modelo != null && modelo.length() > 0) {
                q.setParameter("modelo", '%' + modelo + '%');
            }
            if (serie != null && serie.length() > 0) {
                q.setParameter("serie", '%' + serie + '%');
            }
            if (estados != null) {
                q.setParameterList("estados", estados);
            }
            if (tipo != null) {
                q.setParameter("tipo", tipo);
            }
        }
        return q;
    }

    @Transactional
    public void sincronizarBien(Sincronizar sincronizar) throws FWExcepcion {
        try {
            persist(sincronizar);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.bienDao.sincronizarBien", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
}
