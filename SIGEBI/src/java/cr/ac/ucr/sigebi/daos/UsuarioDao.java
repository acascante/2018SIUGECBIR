/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.entities.UsuarioEntity;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jorge.serrano
 */
@Repository(value = "UsuarioDao")
@Scope("request")
public class UsuarioDao  extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;
    
    
    @Transactional(readOnly = true)
    public UsuarioEntity buscarPorId(String idUsuario) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM UsuarioEntity obj WHERE obj.idUsuario = :idUsuario";
            Query query = session.createQuery(sql);
            query.setParameter("idUsuario", idUsuario);

            //Se obtienen los resutltados
            return (UsuarioEntity) query.list().get(0);

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.adjunto.buscarPorReferencia",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public List<UsuarioEntity> buscarPorUnidad(Integer idUnidadEjec) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT obj FROM AdjuntoEntity obj WHERE obj.idReferencia = :idReferencia";
            Query query = session.createQuery(sql);
            query.setParameter("idReferencia", idUnidadEjec);

            //Se obtienen los resutltados
            return (List<UsuarioEntity>) query.list();

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.adjunto.buscarPorReferencia",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    
    
    
}
