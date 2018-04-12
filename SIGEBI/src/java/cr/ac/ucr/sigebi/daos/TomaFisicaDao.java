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
import cr.ac.ucr.sigebi.domain.TomaFisica;
import cr.ac.ucr.sigebi.domain.Tipo;
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
@Repository(value = "tomaFisicaDao")

public class TomaFisicaDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional
    public void agregar(TomaFisica tomaFisica) throws FWExcepcion {
        try {
            this.persist(tomaFisica);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaDao.agregar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void modificar(TomaFisica tomaFisica) throws FWExcepcion {
        try {
            this.persist(tomaFisica);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaDao.modificar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public TomaFisica buscarPorId(Long id) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT b FROM TomaFisica b WHERE b.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);

            return (TomaFisica) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaDao.buscarPorId", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public List<TomaFisica> listar(String id,
            Tipo tipo,
            String ubicacion,            
            String descripcion,
            Tipo tipoMotivo,
            Estado estado,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            //Se genera el query para la busqueda
            Query q = this.creaQueryListar(id,tipo,ubicacion,descripcion,tipoMotivo, estado, false, session);

            //Paginacion
            if (!(pPrimerRegistro.equals(1) && pUltimoRegistro.equals(1))) {
                q.setFirstResult(pPrimerRegistro);
                q.setMaxResults(pUltimoRegistro - pPrimerRegistro);
            }
            //Se obtienen los resutltados
            return (List<TomaFisica>) q.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaDao.listar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Long contar(String id,
            Tipo tipo,
            String ubicacion,            
            String descripcion,
            Tipo tipoMotivo,
            Estado estado
    ) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {

            //Se genera el query para la busqueda de los bienes
            Query q = this.creaQueryListar(id,tipo,ubicacion,descripcion,tipoMotivo, estado, true, session);

            //Se obtienen los resutltados
            return (Long) q.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaDao.contar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    private Query creaQueryListar(String id,
            Tipo tipo,
            String ubicacion,            
            String descripcion,
            Tipo tipoMotivo,
            Estado estado,
            Boolean contar,
            Session session
    ) {

        StringBuilder sql = new StringBuilder(" ");
        if (contar) {
            sql.append("SELECT count(obj) FROM TomaFisica obj ");
        } else {
            sql.append("SELECT obj FROM TomaFisica obj");
        }

        //Select
        sql.append(" WHERE 1 = 1 ");

        if (id != null && id.length() > 0) {
            sql.append(" AND upper(obj.id) like upper(:id)");
        } else {

            if (tipo != null) {
                sql.append(" AND obj.tipo = :tipo");
            }

            if (ubicacion != null && ubicacion.length() > 0) {
                sql.append(" and upper(obj.ubicacion.detalle) like upper(:ubicacion)");
            }

            if (descripcion != null && descripcion.length() > 0) {
                sql.append(" and upper(obj.descripcion) like upper(:descripcion)");
            }

            if (tipoMotivo != null) {
                sql.append(" AND obj.motivo = :motivo");
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
            if (tipo != null) {
                q.setParameter("tipo", tipo);
            }

            if (ubicacion != null && ubicacion.length() > 0) {
                q.setParameter("ubicacion",  '%' + ubicacion + '%');
            }

            if (descripcion != null && descripcion.length() > 0) {
                q.setParameter("descripcion", '%' + descripcion + '%');
            }

            if (tipoMotivo != null) {
                q.setParameter("motivo", tipoMotivo);
            }

            if (estado != null) {
                q.setParameter("estado", estado);
            }
        }

        return q;
    }   
}
