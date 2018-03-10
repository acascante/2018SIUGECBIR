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
import cr.ac.ucr.sigebi.domain.InterfazAccesorio;
import cr.ac.ucr.sigebi.domain.InterfazAdjunto;
import cr.ac.ucr.sigebi.domain.InterfazBien;
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
@Repository(value = "interfazBienDao")

public class InterfazBienDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional
    public void modificar(InterfazBien interfazBien) throws FWExcepcion {
        try {
            this.persist(interfazBien);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.interfazDao.modificar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public InterfazBien buscarPorId(Long id) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT b FROM InterfazBien b WHERE b.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);

            return (InterfazBien) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.interfazDao.buscarPorId", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public List<InterfazBien> listar(String id,
            String marca,
            String modelo,
            String serie,
            String descripcion,
            String unidadEjecutora,
            Estado estado,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro) throws FWExcepcion {

        Session session = this.dao.getSessionFactory().openSession();
        try {
            //Se genera el query para la busqueda
            Query q = this.creaQueryListar(id, marca, modelo, serie, descripcion, unidadEjecutora, estado, false, session);

            //Paginacion
            if (!(pPrimerRegistro.equals(1) && pUltimoRegistro.equals(1))) {
                q.setFirstResult(pPrimerRegistro);
                q.setMaxResults(pUltimoRegistro - pPrimerRegistro);
            }
            //Se obtienen los resutltados
            return (List<InterfazBien>) q.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.interfazDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Long contar(String id,
            String marca,
            String modelo,
            String serie,
            String descripcion,
            String unidadEjecutora,
            Estado estado) throws FWExcepcion {
        
        Session session = dao.getSessionFactory().openSession();
        try {
            //Se genera el query para la busqueda de los bienes
            Query q = this.creaQueryListar(id, marca, modelo, serie, descripcion, unidadEjecutora, estado, true, session);

            //Se obtienen los resutltados
            return (Long) q.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.interfazDao.contar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    private Query creaQueryListar(String id,
            String marca,
            String modelo,
            String serie,
            String descripcion,
            String unidadEjecutora,
            Estado estado,
            Boolean contar,
            Session session
    ) {

        StringBuilder sql = new StringBuilder(" ");
        if (contar) {
            sql.append("SELECT count(obj) FROM InterfazBien obj ");
        } else {
            sql.append("SELECT obj FROM InterfazBien obj");
        }

        //Select
        sql.append(" WHERE 1 = 1 ");

        if (id != null && id.length() > 0) {
            sql.append(" AND upper(obj.id) like upper(:id)");
        } else {

            if (marca != null && marca.length() > 0) {
                sql.append(" and upper(obj.marca) like upper(:marca)");
            }

            if (modelo != null && modelo.length() > 0) {
                sql.append(" and upper(obj.modelo) like upper(:modelo)");
            }

            if (serie != null && serie.length() > 0) {
                sql.append(" and upper(obj.serie) like upper(:serie)");
            }

            if (descripcion != null && descripcion.length() > 0) {
                sql.append(" and upper(obj.descripcion) like upper(:descripcion)");
            }

            if (unidadEjecutora != null && unidadEjecutora.length() > 0) {
                sql.append(" and upper(obj.unidadEjecutora) like upper(:unidadEjecutora)");
            }

            if (estado != null) {
                sql.append(" AND obj.estado = :estado");
            }
        }

        sql.append(" ORDER BY obj.id desc ");

        Query q = session.createQuery(sql.toString());
        if (id != null && id.length() > 0) {
            q.setParameter("id", '%' + id + '%');
        } else {

            if (marca != null && marca.length() > 0) {
                q.setParameter("marca", '%' + marca + '%');
            }

            if (modelo != null && modelo.length() > 0) {
                q.setParameter("modelo", '%' + modelo + '%');
            }

            if (serie != null && serie.length() > 0) {
                q.setParameter("serie", '%' + serie + '%');
            }

            if (descripcion != null && descripcion.length() > 0) {
                q.setParameter("descripcion", '%' + descripcion + '%');
            }

            if (unidadEjecutora != null && unidadEjecutora.length() > 0) {
                q.setParameter("unidadEjecutora", '%' + unidadEjecutora + '%');
            }

            if (estado != null) {
                q.setParameter("estado", estado);
            }
        }

        return q;
    }

    @Transactional(readOnly = true)
    public List<InterfazAdjunto> listarInterfazAdjuntos(String identificacionBien) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT b FROM InterfazAdjunto b WHERE b.identificacionBien = :identificacionBien";
            Query query = session.createQuery(sql);
            query.setParameter("identificacionBien", identificacionBien);
            return (List<InterfazAdjunto>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.solicitudDao.listarInterfazAdjuntos", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public List<InterfazAccesorio> listarInterfazAccesorios(String identificacionBien) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT b FROM InterfazAccesorio b WHERE b.identificacionBien = :identificacionBien";
            Query query = session.createQuery(sql);
            query.setParameter("identificacionBien", identificacionBien);
            return (List<InterfazAccesorio>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.solicitudDao.listarInterfazAccesorios", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

}
