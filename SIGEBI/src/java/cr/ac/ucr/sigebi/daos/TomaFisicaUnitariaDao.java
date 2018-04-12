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
import cr.ac.ucr.sigebi.domain.TomaFisicaUnitaria;
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
@Repository(value = "tomaFisicaUnitariaDao")

public class TomaFisicaUnitariaDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional
    public void agregar(TomaFisicaUnitaria tomaFisicaUnitaria) throws FWExcepcion {
        try {
            this.persist(tomaFisicaUnitaria);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaUnitariaDao.agregar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void modificar(TomaFisicaUnitaria tomaFisicaUnitaria) throws FWExcepcion {
        try {
            this.persist(tomaFisicaUnitaria);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaUnitariaDao.modificar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional
    public void eliminar(TomaFisicaUnitaria obj) throws FWExcepcion {
        try {
            this.delete(obj);
        } catch (Exception e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaSobranteDao.eliminar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public TomaFisicaUnitaria buscarPorBien(TomaFisica tomaFisica, Bien bien) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT b FROM TomaFisicaUnitaria b WHERE b.tomaFisica = :tomaFisica and b.bien = :bien";
            Query query = session.createQuery(sql);
            query.setParameter("tomaFisica", tomaFisica);
            query.setParameter("bien", bien);
            return (TomaFisicaUnitaria) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaUnitariaDao.buscarPorBien", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public List<TomaFisicaUnitaria> listar(String id,
            String descripcion,
            String identificacion,
            TomaFisica tomaFisica,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            //Se genera el query para la busqueda
            Query q = this.creaQueryListar(id,descripcion,identificacion, tomaFisica, false, session);

            //Paginacion
            if (!(pPrimerRegistro.equals(1) && pUltimoRegistro.equals(1))) {
                q.setFirstResult(pPrimerRegistro);
                q.setMaxResults(pUltimoRegistro - pPrimerRegistro);
            }
            //Se obtienen los resutltados
            return (List<TomaFisicaUnitaria>) q.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaUnitariaDao.listar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Long contar(String id,
            String descripcion,
            String identificacion,
            TomaFisica tomaFisica
    ) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {

            //Se genera el query para la busqueda de los bienes
            Query q = this.creaQueryListar(id,descripcion,identificacion, tomaFisica, true, session);

            //Se obtienen los resutltados
            return (Long) q.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaUnitariaDao.contar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    private Query creaQueryListar(String id,
            String descripcion,
            String identificacion,
            TomaFisica tomaFisica,
            Boolean contar,
            Session session
    ) {

        StringBuilder sql = new StringBuilder(" ");
        if (contar) {
            sql.append("SELECT count(obj) FROM TomaFisicaUnitaria obj ");
        } else {
            sql.append("SELECT obj FROM TomaFisicaUnitaria obj");
        }

        //Select
        sql.append(" WHERE obj.tomaFisica = :tomaFisica ");

        if (id != null && id.length() > 0) {
            sql.append(" AND upper(obj.id) like upper(:id)");
        } else {

            if (descripcion != null && descripcion.length() > 0) {
                sql.append(" and upper(obj.bien.descripcion) like upper(:descripcion)");
            }

            if (identificacion != null && identificacion.length() > 0) {
                sql.append(" and upper(obj.bien.identificacion.identificacion) like upper(:identificacion)");
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
        }
        return q;
    }   
}
