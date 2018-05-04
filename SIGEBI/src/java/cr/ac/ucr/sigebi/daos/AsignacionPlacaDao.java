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
import cr.ac.ucr.sigebi.domain.AsignacionPlaca;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
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
@Repository(value = "asignacionPlacaDao")

public class AsignacionPlacaDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional
    public void agregar(AsignacionPlaca asignacionPlaca) throws FWExcepcion {
        try {
            this.persist(asignacionPlaca);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.asignacionPlacaDao.agregar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void modificar(AsignacionPlaca asignacionPlaca) throws FWExcepcion {
        try {
            this.persist(asignacionPlaca);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.asignacionPlacaDao.modificar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public AsignacionPlaca buscarPorId(Long id) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT b FROM AsignacionPlaca b WHERE b.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);

            return (AsignacionPlaca) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.asignacionPlacaDao.buscarPorId", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public List<AsignacionPlaca> listar(String id,
            UnidadEjecutora unidadEjecutora,
            Estado estado,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            //Se genera el query para la busqueda
            Query q = this.creaQueryListar(id,unidadEjecutora, estado, false, session);

            //Paginacion
            if (!(pPrimerRegistro.equals(1) && pUltimoRegistro.equals(1))) {
                q.setFirstResult(pPrimerRegistro);
                q.setMaxResults(pUltimoRegistro - pPrimerRegistro);
            }
            //Se obtienen los resutltados
            return (List<AsignacionPlaca>) q.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.asignacionPlacaDao.listar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Long contar(String id,
            UnidadEjecutora unidadEjecutora,
            Estado estado
    ) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {

            //Se genera el query para la busqueda de los bienes
            Query q = this.creaQueryListar(id, unidadEjecutora, estado, true, session);

            //Se obtienen los resutltados
            return (Long) q.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.asignacionPlacaDao.contar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    private Query creaQueryListar(String id,
            UnidadEjecutora unidadEjecutora,
            Estado estado,
            Boolean contar,
            Session session
    ) {

        StringBuilder sql = new StringBuilder(" ");
        if (contar) {
            sql.append("SELECT count(obj) FROM AsignacionPlaca obj ");
        } else {
            sql.append("SELECT obj FROM AsignacionPlaca obj");
        }

        //Select
        sql.append(" WHERE 1 = 1 ");

        if (id != null && id.length() > 0) {
            sql.append(" AND upper(obj.id) like upper(:id)");
        } else {

            if (unidadEjecutora != null) {
                sql.append(" AND obj.unidadEjecutora = :unidadEjecutora");
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
            if (unidadEjecutora != null) {
                q.setParameter("unidadEjecutora", unidadEjecutora);
            }

            if (estado != null) {
                q.setParameter("estado", estado);
            }
        }

        return q;
    }   
}
