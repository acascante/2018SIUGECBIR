/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.AsignacionPlaca;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Sincronizar;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.domain.Usuario;
import java.util.List;
import java.util.Map;
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
@Repository(value = "bienDao")

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
    public List<Bien> listar(String sql, Map<String, Object> parametros) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = session.createQuery(sql);
            for (Map.Entry<String, Object> entry : parametros.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                query.setParameter(key, value);    
            }            
            return (List<Bien>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.bienCaracteristica.dao.traerTodo", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    
    @Transactional(readOnly = true)
    public List<Bien> listarPorUnidadEjecutora(UnidadEjecutora unidadEjecutora) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT b FROM Bien b WHERE b.unidadEjecutora = :unidadejecutora";
            Query query = session.createQuery(sql);
            query.setParameter("unidadEjecutora", unidadEjecutora);
            return (List<Bien>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    
    @Transactional(readOnly = true)
    public List<Bien> listarPorAsignacionPlaca(AsignacionPlaca asignacionPlaca) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT b FROM Bien b WHERE b.identificacion.asignacionPlaca = :asignacionPlaca";
            Query query = session.createQuery(sql);
            query.setParameter("asignacionPlaca", asignacionPlaca);
            return (List<Bien>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }


    @Transactional(readOnly = true)
    public List<Bien> listarPorUnidadEjecutoraEstado(UnidadEjecutora unidadEjecutora, Estado estado) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT b FROM Bien b WHERE b.unidadEjecutora = :unidadEjecutora AND b.estadoInterno = :estado";
            Query query = session.createQuery(sql);
            query.setParameter("unidadEjecutora", unidadEjecutora);
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
    public Bien buscarPorIdentificacion(String identificacion) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT b FROM Bien b WHERE UPPER(b.identificacion.identificacion) = UPPER(:identificacion)";
            Query query = session.createQuery(sql);
            query.setParameter("identificacion", identificacion);

            return (Bien) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }


    @Transactional(readOnly = true)
    public List<Bien> listar(Integer primerRegistro,
             Integer ultimoRegistro,
             UnidadEjecutora unidadejecutora,
             Long id,
             String identificacion,
             String descripcion,
             String marca,
             String modelo,
             String serie,
             Tipo tipo,
             String nombUnidad,
             Estado... estados) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQuery(Boolean.FALSE,
                     session,
                     unidadejecutora,
                     id,
                     identificacion,
                     descripcion,
                     marca,
                     modelo,
                     serie,
                     tipo,
                     nombUnidad,
                     estados);
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

    @Transactional(readOnly = true)
    public Long contar(UnidadEjecutora unidadejecutora,
             Long id,
             String identificacion,
             String descripcion,
             String marca,
             String modelo,
             String serie,
             Tipo tipo,
             String nombUnidad,
             Estado... estados) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQuery(Boolean.TRUE,
                     session,
                     unidadejecutora,
                     id,
                     identificacion,
                     descripcion,
                     marca,
                     modelo,
                     serie,
                     tipo,
                     nombUnidad,
                     estados);
            return (Long) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.contarNotificaciones", "Error contando los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public List<Bien> listadoActas(Integer primerRegistro,
             Integer ultimoRegistro,
             UnidadEjecutora unidadejecutora,
             Long id,
             String identificacion,
             String descripcion,
             String marca,
             String modelo,
             String serie,
             Tipo tipo,
             Estado... estados) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQueryBienesActa(Boolean.FALSE,
                     session,
                     unidadejecutora,
                     id,
                     identificacion,
                     descripcion,
                     marca,
                     modelo,
                     serie,
                     tipo, 
                     estados);
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

    @Transactional(readOnly = true)
    public Long contarListadoActas(UnidadEjecutora unidadejecutora,
             Long id,
             String identificacion,
             String descripcion,
             String marca,
             String modelo,
             String serie,
             Tipo tipo,
             Estado... estados) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQueryBienesActa(Boolean.TRUE,
                     session,
                     unidadejecutora,
                     id,
                     identificacion,
                     descripcion,
                     marca,
                     modelo,
                     serie,
                     tipo, 
                     estados);
            return (Long) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.contarNotificaciones", "Error contando los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    
    
    @Transactional
    public void almacenar(Bien bien) throws FWExcepcion {
        try {
            persist(bien);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void actualizar(Bien bien) throws FWExcepcion {
        try {
            persist(bien);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void eliminar(Bien bien) throws FWExcepcion {
        try {
            this.delete(bien);
        } catch (Exception e) {
            throw new FWExcepcion("sigebi.error.bienDao.eliminar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    private Query creaQuery(Boolean contar,
             Session session,
             UnidadEjecutora unidadEjecutora,
             Long id,
             String identificacion,
             String descripcion,
             String marca,
             String modelo,
             String serie,
             Tipo tipo,
             String nombUnidad,
             Estado... estados) {
        StringBuilder sql = new StringBuilder(" ");
        if (contar) {
            sql.append("SELECT count(b) FROM Bien b ");
        } else {
            sql.append("SELECT b FROM Bien b ");
        }
        if (unidadEjecutora != null) {
            sql.append("WHERE b.unidadEjecutora = :unidadEjecutora ");
        } else {
            sql.append("WHERE 1 = 1 ");
        }
        if (id != null && id > 0) {
            sql.append(" AND str( b.id ) like :id ");
        } else {
            if (identificacion != null && identificacion.length() > 0) {
                sql.append(" AND UPPER(b.identificacion.identificacion) LIKE UPPER(:identificacion) ");
            }
            if (descripcion != null && descripcion.length() > 0) {
                sql.append(" AND UPPER(b.descripcion) LIKE UPPER(:descripcion) ");
            }
            if (marca != null && marca.length() > 0) {
                sql.append(" AND UPPER(b.resumenBien.marca) LIKE UPPER(:marca) ");
            }
            if (modelo != null && modelo.length() > 0) {
                sql.append(" AND UPPER(b.resumenBien.modelo) LIKE UPPER(:modelo) ");
            }
            if (serie != null && serie.length() > 0) {
                sql.append(" AND UPPER(b.resumenBien.serie) LIKE UPPER(:serie) ");
            }
            if (estados != null) {
                sql.append(" AND b.estado IN (:estados) ");
            }
            if (tipo != null) {
                sql.append(" AND b.tipo IN (:tipo) ");
            }
            if (nombUnidad != null && nombUnidad.length() > 0) {
                sql.append(" AND UPPER(b.unidadEjecutora.descripcion) LIKE UPPER(:nombUnidad) ");
            }
        }
        sql.append(" ORDER BY b.id ASC ");
        Query q = session.createQuery(sql.toString());
        if (unidadEjecutora != null) {
            q.setParameter("unidadEjecutora", unidadEjecutora);
        }
        if (id != null && id > 0) {
            q.setParameter("id", '%' + id.toString() + '%');
        } else {
            if (identificacion != null && identificacion.length() > 0) {
                q.setParameter("identificacion", '%' + identificacion + '%');
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
            if (nombUnidad != null && nombUnidad.length() > 0) {
                q.setParameter("nombUnidad", '%' + nombUnidad + '%');
            }
        }
        return q;
    }
    
    private Query creaQueryBienesActa(Boolean contar,
             Session session,
             UnidadEjecutora unidadEjecutora,
             Long id,
             String identificacion,
             String descripcion,
             String marca,
             String modelo,
             String serie,
             Tipo tipo,
             Estado... estados) {
        StringBuilder sql = new StringBuilder(" ");
        if (contar) {
            sql.append("SELECT count(b) FROM Bien b ");
        } else {
            sql.append("SELECT b FROM Bien b ");
        }
        
        sql.append("WHERE b.estadoInterno IN (:estados) ");
        //sql.append(" AND b.tipoInforme = :tipoInforme ");
        
        if (unidadEjecutora != null) {
            sql.append(" AND b.unidadEjecutora = :unidadEjecutora ");
        }
        if (id != null && id > 0) {
            sql.append(" AND str( b.id ) like :id ");
        } else {
            if (identificacion != null && identificacion.length() > 0) {
                sql.append(" AND UPPER(b.identificacion.identificacion) LIKE UPPER(:identificacion) ");
            }
            if (descripcion != null && descripcion.length() > 0) {
                sql.append(" AND UPPER(b.descripcion) LIKE UPPER(:descripcion) ");
            }
            if (marca != null && marca.length() > 0) {
                sql.append(" AND UPPER(b.resumenBien.marca) LIKE UPPER(:marca) ");
            }
            if (modelo != null && modelo.length() > 0) {
                sql.append(" AND UPPER(b.resumenBien.modelo) LIKE UPPER(:modelo) ");
            }
            if (serie != null && serie.length() > 0) {
                sql.append(" AND UPPER(b.resumenBien.serie) LIKE UPPER(:serie) ");
            }
        }
        sql.append(" ORDER BY b.identificacion.identificacion ASC ");
        Query q = session.createQuery(sql.toString());
        //
        q.setParameterList("estados", estados);
        //q.setParameter("tipoInforme", tipo);
            
        if (unidadEjecutora != null) {
            q.setParameter("unidadEjecutora", unidadEjecutora);
        }
        if (id != null && id > 0) {
            q.setParameter("id", '%' + id.toString() + '%');
        } 
        else {
            if (identificacion != null && identificacion.length() > 0) {
                q.setParameter("identificacion", '%' + identificacion + '%');
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
            
        }
        return q;
    }

    
    private Query creaQueryBienesActa_OLD(Boolean contar,
             Session session,
             UnidadEjecutora unidadEjecutora,
             Long id,
             String identificacion,
             String descripcion,
             String marca,
             String modelo,
             String serie,
             Tipo tipo,
             Estado... estados) {
        StringBuilder sql = new StringBuilder(" ");
        if (contar) {
            sql.append("SELECT count(b) FROM DocumentoInformeTecnico b ");
        } else {
            sql.append("SELECT b.bien FROM DocumentoInformeTecnico b ");
        }
        
        sql.append("WHERE b.bien.estadoInterno IN (:estados) ");
        sql.append(" AND b.tipoInforme = :tipoInforme ");
        
        if (unidadEjecutora != null) {
            sql.append(" AND b.bien.unidadEjecutora = :unidadEjecutora ");
        }
        if (id != null && id > 0) {
            sql.append(" AND str( b.bien.id ) like :id ");
        } else {
            if (identificacion != null && identificacion.length() > 0) {
                sql.append(" AND UPPER(b.bien.identificacion.identificacion) LIKE UPPER(:identificacion) ");
            }
            if (descripcion != null && descripcion.length() > 0) {
                sql.append(" AND UPPER(b.bien.descripcion) LIKE UPPER(:descripcion) ");
            }
            if (marca != null && marca.length() > 0) {
                sql.append(" AND UPPER(b.bien.resumenBien.marca) LIKE UPPER(:marca) ");
            }
            if (modelo != null && modelo.length() > 0) {
                sql.append(" AND UPPER(b.bien.resumenBien.modelo) LIKE UPPER(:modelo) ");
            }
            if (serie != null && serie.length() > 0) {
                sql.append(" AND UPPER(b.bien.resumenBien.serie) LIKE UPPER(:serie) ");
            }
        }
        sql.append(" ORDER BY b.bien.identificacion.identificacion ASC ");
        Query q = session.createQuery(sql.toString());
        //
        q.setParameterList("estados", estados);
        q.setParameter("tipoInforme", tipo);
            
        if (unidadEjecutora != null) {
            q.setParameter("unidadEjecutora", unidadEjecutora);
        }
        if (id != null && id > 0) {
            q.setParameter("id", '%' + id.toString() + '%');
        } 
        else {
            if (identificacion != null && identificacion.length() > 0) {
                q.setParameter("identificacion", '%' + identificacion + '%');
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
            
        }
        return q;
    }

    
    
    @Transactional
    public void sincronizarBien(Sincronizar sincronizar) throws FWExcepcion, Exception {
        try {
            persist(sincronizar);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.bien.sincronizarBien", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void actualizar(List<Bien> bienes) throws FWExcepcion, Exception {
        try {
            persist(bienes.toArray());
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.bien.sincronizarBien", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<Bien> listar(Integer primerRegistro
                            , Integer ultimoRegistro
                            , Long id
                            , UnidadEjecutora unidadejecutora
                            , String identificacion
            
                            , String descripcion
                            , String marca
                            , String modelo
                            , String serie
                            , Estado estado
            
                            , Estado estadoInterno
                            , Boolean listadoAsignacion
                    ) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQuery(Boolean.FALSE
                                        , session
                                        , id
                                        , unidadejecutora
                                        , identificacion
                    
                                        , descripcion
                                        , marca
                                        , modelo
                                        , serie
                                        , estado
                    
                                        , estadoInterno
                                        , listadoAsignacion
                                        );
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

    public Long contar(Long id
                    , UnidadEjecutora unidadejecutora
                    , String identificacion
                    , String descripcion
                    , String marca

                    , String modelo
                    , String serie
                    , Estado estado
                    , Estado estadoInterno
                    , Boolean listadoAsignacion
    ) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQuery(Boolean.TRUE
                                        , session
                                        , id
                                        , unidadejecutora
                                        , identificacion
                    
                                        , descripcion
                                        , marca
                                        , modelo
                                        , serie
                                        , estado
                    
                                        , estadoInterno
                                        ,listadoAsignacion
                                        );
            return (Long) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.contarNotificaciones", "Error contando los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    private Query creaQuery(Boolean contar
                            , Session session
                            , Long id
                            , UnidadEjecutora unidadEjecutora
                            , String identificacion
            
                            , String descripcion
                            , String marca
                            , String modelo
                            , String serie
                            , Estado estado
            
                            , Estado estadoInterno
                            , Boolean listadoAsignacion
    ) {
        StringBuilder sql = new StringBuilder("SELECT ");
        if (contar) {
            sql.append("count(entity) FROM Bien entity ");
        } else {
            sql.append("entity FROM Bien entity ");
        }

        sql.append("WHERE entity.unidadEjecutora = :unidadEjecutora ");
        if(id != null && id > 0) {
            sql.append("AND entity.id = :id ");
        } else {
            if (identificacion != null && identificacion.length() > 0) {
                sql.append("AND UPPER(entity.identificacion.identificacion) LIKE UPPER(:identificacion) ");
            }
            if(descripcion != null && descripcion.length() > 0){
                sql.append("AND UPPER(entity.descripcion) LIKE UPPER(:descripcion) ");
            }
            if(marca != null && marca.length() > 0){
                sql.append("AND UPPER(entity.resumenBien.marca) LIKE UPPER(:marca) ");
            }
            if(modelo != null && modelo.length() > 0){
                sql.append("AND UPPER(entity.resumenBien.modelo) LIKE UPPER(:modelo) ");
            }
            if(serie != null && serie.length() > 0){
                sql.append("AND UPPER(entity.resumenBien.serie) LIKE UPPER(:serie) ");
            }
            if(estado != null)
                sql.append("AND entity.estado = :estado ");
            if(estadoInterno != null)
                sql.append("AND entity.estadoInterno = :estadoInterno ");
            if(listadoAsignacion)
                sql.append("AND entity.usuarioResponsable is null ");
                
     }
        sql.append("ORDER BY entity.id ASC ");
        
        Query q = session.createQuery(sql.toString());
        q.setParameter("unidadEjecutora", unidadEjecutora);
        
        if(id != null && id > 0) {
            q.setParameter("id", id);
        } else {
            if (estado != null)
                q.setParameter("estado", estado);
            if (estadoInterno != null)
                q.setParameter("estadoInterno", estadoInterno);
            
            if (identificacion != null && identificacion.length() > 0) 
                q.setParameter("identificacion",  '%'+ identificacion + '%');
                
            if (descripcion != null && descripcion.length() > 0) 
                q.setParameter("descripcion", '%' + descripcion + '%');
            
            if (marca != null && marca.length() > 0) {
                q.setParameter("marca", '%' + marca + '%');
            }
            if (modelo != null && modelo.length() > 0) {
                q.setParameter("modelo", '%' + modelo + '%');
            }
            if (serie != null && serie.length() > 0) {
                q.setParameter("serie", '%' + serie + '%');
            }
        }
        return q;
    }
    
    
    // <editor-fold defaultstate="collapsed" desc=" MisBienes ">

    
    
    @Transactional(readOnly = true)
    public List<Bien> listarMisBienes(Integer primerRegistro
                            , Integer ultimoRegistro
                            , UnidadEjecutora unidadejecutora
                            , String identificacion
            
                            , String descripcion
                            , String marca
                            , String modelo
                            , String serie
                            , Estado estadoAsignacion
                            , Estado estadoBien            
                            , Usuario usuario
                    ) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQueryMisBienes(Boolean.FALSE
                                        , session
                                        , unidadejecutora
                                        , identificacion                    
                                        , descripcion
                                        , marca
                                        , modelo
                                        , serie
                                        , estadoAsignacion
                                        , estadoBien                    
                                        , usuario);
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

    public Long contarMisBienes(UnidadEjecutora unidadejecutora
                    , String identificacion
                    , String descripcion
                    , String marca            
                    , String modelo
                    , String serie
                    , Estado estadoAsignacion
                    , Estado estadoBien
                    , Usuario usuario) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQueryMisBienes(Boolean.TRUE
                                        , session
                                        , unidadejecutora
                                        , identificacion                    
                                        , descripcion
                                        , marca
                                        , modelo
                                        , serie
                                        , estadoAsignacion
                                        , estadoBien                    
                                        , usuario);
            return (Long) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.contarNotificaciones", "Error contando los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    private Query creaQueryMisBienes(Boolean contar
                            , Session session
                            , UnidadEjecutora unidadEjecutora
                            , String identificacion
                            , String descripcion
                            , String marca
                            , String modelo
                            , String serie
                            , Estado estadoAsignacion
                            , Estado estadoBien            
                            , Usuario usuario) {
        StringBuilder sql = new StringBuilder("SELECT ");
        if (contar) {
            sql.append("count(entity) FROM Bien entity ");
        } else {
            sql.append("entity FROM Bien entity ");
        }

        sql.append("WHERE entity.unidadEjecutora = :unidadEjecutora ");
        sql.append("AND entity.usuarioResponsable = :usuarioResponsable ");
        
        if (identificacion != null && identificacion.length() > 0) {
            sql.append("AND UPPER(entity.identificacion.identificacion) LIKE UPPER(:identificacion) ");
        }
        if(descripcion != null && descripcion.length() > 0){
            sql.append("AND UPPER(entity.descripcion) LIKE UPPER(:descripcion) ");
        }
        if(marca != null && marca.length() > 0){
            sql.append("AND UPPER(entity.resumenBien.marca) LIKE UPPER(:marca) ");
        }
        if(modelo != null && modelo.length() > 0){
            sql.append("AND UPPER(entity.resumenBien.modelo) LIKE UPPER(:modelo) ");
        }
        if(serie != null && serie.length() > 0){
            sql.append("AND UPPER(entity.resumenBien.serie) LIKE UPPER(:serie) ");
        }
        if(estadoAsignacion != null)
            sql.append("AND entity.estadoAsignacion = :estadoAsignacion ");
        
        if(estadoBien != null)
            sql.append("AND entity.estado = :estadoBien ");

        sql.append("ORDER BY entity.estadoAsignacion ASC ");
        
        
        
        Query q = session.createQuery(sql.toString());
        q.setParameter("unidadEjecutora", unidadEjecutora);
        q.setParameter("usuarioResponsable", usuario);
        
        if (estadoAsignacion != null)
            q.setParameter("estadoAsignacion", estadoAsignacion);
        
        if (estadoBien != null)
            q.setParameter("estadoBien", estadoBien);
        
        if (identificacion != null && identificacion.length() > 0) 
            q.setParameter("identificacion",  '%'+ identificacion + '%');

        if (descripcion != null && descripcion.length() > 0) 
            q.setParameter("descripcion", '%' + descripcion + '%');

        if (marca != null && marca.length() > 0) {
            q.setParameter("marca", '%' + marca + '%');
        }
        if (modelo != null && modelo.length() > 0) {
            q.setParameter("modelo", '%' + modelo + '%');
        }
        if (serie != null && serie.length() > 0) {
            q.setParameter("serie", '%' + serie + '%');
        }
        return q;
    }
    
    
    // </editor-fold>
    
    
    @Transactional(readOnly = true)
    public List<Bien> listarReporteSobrantes(String identificacion, String descripcion, String marca, String modelo, String serie, String usuario,  Estado estado) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.queryReporteSobrantes(session, identificacion, descripcion, marca, modelo, serie, usuario, estado);
            return (List<Bien>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    private Query queryReporteSobrantes(Session session, String identificacion, String descripcion, String marca, String modelo, String serie, String usuario,  Estado estado) {
        StringBuilder sql = new StringBuilder(" ");
        sql.append("SELECT b FROM Bien b ");
        sql.append("WHERE 1 = 1 ");
        if (identificacion != null && identificacion.length() > 0) {
            sql.append(" AND UPPER(b.identificacion.identificacion) LIKE UPPER(:identificacion) ");
        }
        if (descripcion != null && descripcion.length() > 0) {
            sql.append(" AND UPPER(b.descripcion) LIKE UPPER(:descripcion) ");
        }
        if (marca != null && marca.length() > 0) {
            sql.append(" AND UPPER(b.resumenBien.marca) LIKE UPPER(:marca) ");
        }
        if (modelo != null && modelo.length() > 0) {
            sql.append(" AND UPPER(b.resumenBien.modelo) LIKE UPPER(:modelo) ");
        }
        if (serie != null && serie.length() > 0) {
            sql.append(" AND UPPER(b.resumenBien.serie) LIKE UPPER(:serie) ");
        }
        if (estado != null) {
            sql.append(" AND b.estado IN (:estado) ");
        }
        // TODO determinar como buscar usuario asignado
//        if (usuario != null && usuario.length() > 0) {
//            sql.append(" AND UPPER(b.unidadEjecutora.descripcion) LIKE UPPER(:nombUnidad) ");
//        }
        
        sql.append(" ORDER BY b.unidadEjecutora.descripcion ASC ");
        Query q = session.createQuery(sql.toString());
        if (identificacion != null && identificacion.length() > 0) {
            q.setParameter("identificacion", '%' + identificacion + '%');
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
        if (estado != null) {
            q.setParameter("estado", estado);
        }
//        if (usuario != null && usuario.length() > 0) {
//            q.setParameter("usuario", '%' + usuario + '%');
//        }
    
        return q;
    }
}
