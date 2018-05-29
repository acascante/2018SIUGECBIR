/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Lote;
import cr.ac.ucr.sigebi.domain.TomaFisicaLote;
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
@Repository(value = "tomaFisicaLoteDao")

public class TomaFisicaLoteDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional
    public void agregar(TomaFisicaLote tomaFisicaLote) throws FWExcepcion {
        try {
            this.persist(tomaFisicaLote);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaLoteDao.agregar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void modificar(TomaFisicaLote tomaFisicaLote) throws FWExcepcion {
        try {
            this.persist(tomaFisicaLote);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaLoteDao.modificar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
     @Transactional
    public void eliminar(TomaFisicaLote obj) throws FWExcepcion {
        try {
            this.delete(obj);
        } catch (Exception e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaSobranteDao.eliminar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public TomaFisicaLote buscarPorLote(TomaFisica tomaFisica, Lote lote) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT b FROM TomaFisicaLote b WHERE b.tomaFisica = :tomaFisica and b.lote = :lote";
            Query query = session.createQuery(sql);
            query.setParameter("tomaFisica", tomaFisica);
            query.setParameter("lote", lote);
            return (TomaFisicaLote) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaUnitariaDao.buscarPorLote", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public TomaFisicaLote buscarPorId(Long id) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT b FROM TomaFisicaLote b WHERE b.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);

            return (TomaFisicaLote) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaLoteDao.buscarPorId", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public List<TomaFisicaLote> listar(String id,
            String lote,
            TomaFisica tomaFisica,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            //Se genera el query para la busqueda
            Query q = this.creaQueryListar(id,lote, tomaFisica, false, session);

            //Paginacion
            if (!(pPrimerRegistro.equals(1) && pUltimoRegistro.equals(1))) {
                q.setFirstResult(pPrimerRegistro);
                q.setMaxResults(pUltimoRegistro - pPrimerRegistro);
            }
            //Se obtienen los resutltados
            return (List<TomaFisicaLote>) q.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaLoteDao.listar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Long contar(String id,
            String lote,
            TomaFisica tomaFisica
    ) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {

            //Se genera el query para la busqueda de los bienes
            Query q = this.creaQueryListar(id,lote, tomaFisica, true, session);

            //Se obtienen los resutltados
            return (Long) q.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.tomaFisicaLoteDao.contar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    private Query creaQueryListar(String id,
            String lote,
            TomaFisica tomaFisica,
            Boolean contar,
            Session session
    ) {

        StringBuilder sql = new StringBuilder(" ");
        if (contar) {
            sql.append("SELECT count(obj) FROM TomaFisicaLote obj ");
        } else {
            sql.append("SELECT obj FROM TomaFisicaLote obj");
        }

        //Select
        sql.append(" WHERE obj.tomaFisica = :tomaFisica ");

        if (id != null && id.length() > 0) {
            sql.append(" AND upper(obj.id) like upper(:id)");
        } else {

            if (lote != null && lote.length() > 0) {
                sql.append(" and upper(obj.lote.descripcion) like upper(:descripcion)");
            }
        }

        sql.append(" ORDER BY obj.id desc ");

        Query q = session.createQuery(sql.toString());
        q.setParameter("tomaFisica", tomaFisica);

        if (id != null && id.length() > 0) {
            q.setParameter("id", '%' + id + '%');
        } else {

            if (lote != null && lote.length() > 0) {
                q.setParameter("lote", lote);
            }
        }
        return q;
    }   
}
