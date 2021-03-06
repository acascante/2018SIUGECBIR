/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Tipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author jairo.cisneros
 */
@Repository(value = "tipoDao")

public class TipoDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;
    
    @Transactional
    public void salvar(Tipo tipo) throws FWExcepcion {
        try {
            persist(tipo);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.tipo.error.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional
    public void eliminar(Tipo tipo) throws FWExcepcion {
        try {
            delete(tipo);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.tipo.error.eliminar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public List<Tipo> listar() throws FWExcepcion {
        try {
            return dao.getHibernateTemplate().find("SELECT entity FROM Tipo entity ORDER BY entity.valor");
        } catch (Exception e) {
            throw new FWExcepcion("sigebi.label.tipo.error.listar", "Error obtener los registros de estado " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public List<Tipo> listarPorDominio(String dominio) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT tip FROM Tipo tip WHERE tip.dominio = :dominio";
            Query query = session.createQuery(sql);
            query.setParameter("dominio", dominio);

            return (List<Tipo>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.tipo.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Tipo buscarPorId(Long idTipo) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT tip FROM Tipo tip WHERE tip.id = :idTipo";
            Query query = session.createQuery(sql);
            query.setParameter("idTipo", idTipo);

            return (Tipo) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.tipo.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Tipo buscarPorDominioTipo(String dominio, Integer valor) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT tip FROM Tipo tip WHERE tip.dominio = :dominio AND tip.valor = :valor";
            Query query = session.createQuery(sql);
            query.setParameter("dominio", dominio);
            query.setParameter("valor", valor);

            return (Tipo) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.tipo.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Tipo buscarPorDominioNombre(String dominio, String nombre) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT tip FROM Tipo tip WHERE tip.dominio = :dominio AND tip.nombre = :nombre";
            Query query = session.createQuery(sql);
            query.setParameter("dominio", dominio);
            query.setParameter("nombre", nombre);

            return (Tipo) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.tipo.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
}
