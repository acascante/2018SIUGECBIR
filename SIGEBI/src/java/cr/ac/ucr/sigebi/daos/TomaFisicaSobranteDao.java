/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.TomaFisicaSobrante;
import cr.ac.ucr.sigebi.domain.TomaFisica;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jairo.cisneros
 */
@Repository(value = "TomaFisicaSobranteDao")

public class TomaFisicaSobranteDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional
    public void agregar(TomaFisicaSobrante tomaFisicaSobrante) throws FWExcepcion {
        try {
            this.persist(tomaFisicaSobrante);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaSobranteDao.agregar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void modificar(TomaFisicaSobrante tomaFisicaSobrante) throws FWExcepcion {
        try {
            this.persist(tomaFisicaSobrante);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaSobranteDao.modificar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void eliminar(TomaFisicaSobrante obj) throws FWExcepcion {
        try {
            this.delete(obj);
        } catch (Exception e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaSobranteDao.eliminar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public TomaFisicaSobrante buscarPorId(Long id) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT b FROM TomaFisicaSobrante b WHERE b.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);

            return (TomaFisicaSobrante) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaSobranteDao.buscarPorId", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public TomaFisicaSobrante buscarPorIdentificacion(TomaFisica tomaFisica, String identificacion) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT b FROM TomaFisicaSobrante b WHERE b.tomaFisica = :tomaFisica and b.identificacion = :identificacion";
            Query query = session.createQuery(sql);
            query.setParameter("tomaFisica", tomaFisica);
            query.setParameter("identificacion", identificacion);
            return (TomaFisicaSobrante) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaUnitariaDao.buscarPorIdentificacion", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public List<TomaFisicaSobrante> listar(String id,
            TomaFisica tomaFisica,
            String identificacion,
            String descripcion,
            String serie,
            String marca,
            String modelo,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            //Se genera el query para la busqueda
            Query q = this.creaQueryListar(id, tomaFisica, identificacion, descripcion, serie, marca, modelo, false, session);

            //Paginacion
            if (!(pPrimerRegistro.equals(1) && pUltimoRegistro.equals(1))) {
                q.setFirstResult(pPrimerRegistro);
                q.setMaxResults(pUltimoRegistro - pPrimerRegistro);
            }
            //Se obtienen los resutltados
            return (List<TomaFisicaSobrante>) q.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaSobranteDao.listar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Long contar(String id,
            TomaFisica tomaFisica,
            String identificacion,
            String descripcion,
            String serie,
            String marca,
            String modelo
    ) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {

            //Se genera el query para la busqueda de los bienes
            Query q = this.creaQueryListar(id, tomaFisica, identificacion, descripcion, serie, marca, modelo, true, session);

            //Se obtienen los resutltados
            return (Long) q.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaSobranteDao.contar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    private Query creaQueryListar(String id,
            TomaFisica tomaFisica,
            String identificacion,
            String descripcion,
            String serie,
            String marca,
            String modelo,
            Boolean contar,
            Session session
    ) {

        StringBuilder sql = new StringBuilder(" ");
        if (contar) {
            sql.append("SELECT count(obj) FROM TomaFisicaSobrante obj ");
        } else {
            sql.append("SELECT obj FROM TomaFisicaSobrante obj");
        }

        //Select
        sql.append(" WHERE obj.tomaFisica = :tomaFisica ");

        if (id != null && id.length() > 0) {
            sql.append(" AND upper(obj.id) like upper(:id)");
        } else {

            if (identificacion != null && identificacion.length() > 0) {
                sql.append(" and upper(obj.identificacion) like upper(:identificacion)");
            }

            if (descripcion != null && descripcion.length() > 0) {
                sql.append(" and upper(obj.descripcion) like upper(:descripcion)");
            }

            if (serie != null && serie.length() > 0) {
                sql.append(" and upper(obj.serie) like upper(:serie)");
            }

            if (marca != null && marca.length() > 0) {
                sql.append(" and upper(obj.marca) like upper(:marca)");
            }

            if (modelo != null && modelo.length() > 0) {
                sql.append(" and upper(obj.modelo) like upper(:modelo)");
            }

        }

        sql.append(" ORDER BY obj.id desc ");

        Query q = session.createQuery(sql.toString());
        q.setParameter("tomaFisica", tomaFisica);

        if (id != null && id.length() > 0) {
            q.setParameter("id", '%' + id + '%');
        } else {
            
            if (descripcion != null && descripcion.length() > 0) {
                q.setParameter("descripcion", '%' + descripcion + '%');
            }
            
            if (identificacion != null && identificacion.length() > 0) {
                q.setParameter("identificacion", '%' + identificacion + '%');
            }

            if (descripcion != null && descripcion.length() > 0) {
                q.setParameter("descripcion", '%' + descripcion + '%');
            }

            if (serie != null && serie.length() > 0) {
                q.setParameter("serie", '%' + serie + '%');
            }

            if (marca != null && marca.length() > 0) {
                q.setParameter("marca", '%' + marca + '%');
            }

            if (modelo != null && modelo.length() > 0) {
                q.setParameter("modelo", '%' + modelo + '%');
            }
        }

        return q;
    }
    
    @Transactional(readOnly = true)
    public List<TomaFisicaSobrante> listarReporte(TomaFisica tomaFisica, String identificacion, String ubicacion, String descripcion, String serie, String marca, String modelo, String orden, String orden1, String orden2, String orden3) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            Query q = this.creaQueryListarReporte(session, tomaFisica, identificacion, descripcion, serie, marca, modelo, ubicacion, orden, orden1, orden2, orden3);
            return (List<TomaFisicaSobrante>) q.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaSobranteDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
     private Query creaQueryListarReporte(Session session, TomaFisica tomaFisica, String identificacion, String descripcion, String serie, String marca, String modelo, String ubicacion, String orden, String orden1, String orden2, String orden3) {
        StringBuilder sql = new StringBuilder(" ");
        sql.append("SELECT obj FROM TomaFisicaSobrante obj ");
        sql.append("WHERE obj.tomaFisica = :tomaFisica ");

        if (identificacion != null && identificacion.length() > 0) {
            sql.append(" AND UPPER(obj.identificacion) LIKE UPPER(:identificacion)");
        }

        if (descripcion != null && descripcion.length() > 0) {
            sql.append(" AND UPPER(obj.descripcion) LIKE UPPER(:descripcion)");
        }

        if (serie != null && serie.length() > 0) {
            sql.append(" AND UPPER(obj.serie) LIKE UPPER(:serie)");
        }

        if (marca != null && marca.length() > 0) {
            sql.append(" AND UPPER(obj.marca) LIKE UPPER(:marca)");
        }

        if (modelo != null && modelo.length() > 0) {
            sql.append(" AND UPPER(obj.modelo) LIKE UPPER(:modelo)");
        }
        
        if (ubicacion != null && ubicacion.length() > 0) {
            sql.append(" AND UPPER(obj.ubicacion) LIKE UPPER(:ubicacion)");
        }

        if (orden1 != null && orden1.length() > 0) {
            sql.append(" ORDER BY obj.:orden1 ");
            if (orden2 != null && orden2.length() > 0) {
               sql.append(", obj.:orden2 ");
                if (orden3 != null && orden3.length() > 0) {
                    sql.append(", obj.:orden3 ");
                }
            }
            sql.append(" :orden ");
        } else {
            sql.append(" ORDER BY obj.id asc ");
        }
        Query q = session.createQuery(sql.toString());
        q.setParameter("tomaFisica", tomaFisica);

        if (identificacion != null && identificacion.length() > 0) {
            q.setParameter("identificacion", '%' + identificacion + '%');
        }

        if (descripcion != null && descripcion.length() > 0) {
            q.setParameter("descripcion", '%' + descripcion + '%');
        }

        if (serie != null && serie.length() > 0) {
            q.setParameter("serie", '%' + serie + '%');
        }

        if (marca != null && marca.length() > 0) {
            q.setParameter("marca", '%' + marca + '%');
        }

        if (modelo != null && modelo.length() > 0) {
            q.setParameter("modelo", '%' + modelo + '%');
        }
        
        if (ubicacion != null && ubicacion.length() > 0) {
            q.setParameter("ubicacion", '%' + ubicacion + '%');
        }
        
        if (orden1 != null && orden1.length() > 0) {
            q.setParameter("modelo", '%' + orden1 + '%');
            if (orden2 != null && orden2.length() > 0) {
               q.setParameter("modelo", '%' + orden2 + '%');
                if (orden3 != null && orden3.length() > 0) {
                    q.setParameter("modelo", '%' + orden3 + '%');
                }
            }
            q.setParameter("modelo", '%' + orden + '%');
        }

        return q;
    }
}
