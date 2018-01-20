/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.seguridad.entidades.SegUsuario;
import cr.ac.ucr.framework.utils.FWExcepcion;
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
@Repository(value = "segUsuarioDao")
@Scope("request")
public class SegUsuarioDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional(readOnly = true)
    public List<SegUsuario> listarUsuarios(String idUsuario,
            String nombreCompleto,
            String correo,            
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ) throws FWExcepcion{
        Session session = this.dao.getSessionFactory().openSession();
        try {
            //Se genera el query para la busqueda
            Query q = this.creaQueryListar(idUsuario, nombreCompleto, correo, false, session);

            //Paginacion
            if (!(pPrimerRegistro.equals(1) && pUltimoRegistro.equals(1))) {
                q.setFirstResult(pPrimerRegistro);
                q.setMaxResults(pUltimoRegistro - pPrimerRegistro);
            }
            //Se obtienen los resutltados
            return (List<SegUsuario>) q.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.segUsuario.listarUsuarios",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Long contarUsuarios(String idUsuario,
            String nombreCompleto,
            String correo
    ) throws FWExcepcion{
        Session session = dao.getSessionFactory().openSession();
        try {

            //Se genera el query para la busqueda
            Query q = this.creaQueryListar(idUsuario, nombreCompleto, correo, true, session);

            //Se obtienen los resutltados
            return (Long) q.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.segUsuario.contarUsuarios",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    private Query creaQueryListar(String idUsuario,
            String nombreCompleto,
            String correo,
            Boolean contar,
            Session session
    ) {
        String sql;
        if (contar) {
            sql = "SELECT count(obj) FROM SegUsuario obj ";
        } else {
            sql = "SELECT obj FROM SegUsuario obj ";
        }
        //Select
        sql = sql + " WHERE  1 = 1 ";       
        if (idUsuario != null && idUsuario.length() > 0) {
            sql = sql + " AND upper(obj.idUsuario) like upper(:idUsuario) ";
        }
        if (nombreCompleto != null && nombreCompleto.length() > 0) {
            sql = sql + " AND upper(obj.nombre_completo) like upper(:nombreCompleto) ";
        }
        if (correo != null && correo.length() > 0) {
            sql = sql + " AND upper(obj.correo) like upper(:correo) ";
        }
        
        Query q = session.createQuery(sql);
        
        if (idUsuario != null && idUsuario.length() > 0) {
            q.setParameter("idUsuario", '%' + idUsuario + '%');
        }
        if (nombreCompleto != null && nombreCompleto.length() > 0) {
            q.setParameter("nombreCompleto", '%' + nombreCompleto + '%');
        }
        
        return q;
    }
    
}
