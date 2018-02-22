/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.AutorizacionRol;
import cr.ac.ucr.sigebi.domain.Documento;
import cr.ac.ucr.sigebi.domain.DocumentoAutorizacion;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jairo.cisneros
 */
@Repository(value = "documentoAutorizacionDao")

public class DocumentoAutorizacionDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional
    public void agregar(DocumentoAutorizacion obj) throws FWExcepcion {
        try {
            this.persist(obj);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.documentoAutorizacionDao.agregar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void modificar(DocumentoAutorizacion obj) throws FWExcepcion {
        try {
            this.persist(obj);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.documentoAutorizacionDao.modificar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public DocumentoAutorizacion buscar(AutorizacionRol autorizacionRol, Documento documento) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM DocumentoAutorizacion obj "
                    + " WHERE obj.autorizacionRol = :autorizacionRol"
                    + " and obj.documento = :documento";
            Query query = session.createQuery(sql);
            query.setParameter("autorizacionRol", autorizacionRol);
            query.setParameter("documento", documento);

            List<DocumentoAutorizacion> results = query.list();
            if (!results.isEmpty()) {
                return (DocumentoAutorizacion) results.get(0);
            } else {
                return null;
            }

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.documentoAutorizacionDao.buscar",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
}
