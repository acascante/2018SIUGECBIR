/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.sigebi.entities.UnidadEjecutoraEntity;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jorge.serrano
 */
@Repository(value = "UnidadEjecutoraDao")
@Scope("request")
public class UnidadEjecutoraDao  extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;
    
    @Transactional
    public UnidadEjecutoraEntity traerPorId(Integer pId) {
        Session session = dao.getSessionFactory().openSession();
        UnidadEjecutoraEntity resp = new UnidadEjecutoraEntity();
        try {
            // De momento utilizamos la referencia como el id del bien
            String sql = "FROM UnidadEjecutoraEntity s "
                    + "WHERE s.idUnidadEjec = :pidUnidadEjec";
            Query q = session.createQuery(sql);
            q.setParameter("pidUnidadEjec",pId);
            
            List l = q.list();
            resp = (UnidadEjecutoraEntity) l.get(0);
            
            session.close();
            return resp;
        } catch (Exception e) {
            if(session.isOpen())
                session.close();
            return resp;
        }
    }
    
    
    public List<UnidadEjecutoraEntity> listarUnidades(String idUnidad, String nombUnidad) {
        Session session = dao.getSessionFactory().openSession();
        List<UnidadEjecutoraEntity> resp = new ArrayList<UnidadEjecutoraEntity>();
        try {
            // De momento utilizamos la referencia como el id del bien
            String sql = " SELECT UN.* \n" +
                         " FROM SEGURIDAD_UNIDAD_EJECUTORA UN \n" +
                         " WHERE TO_CHAR(ID_UNIDAD_EJECUTORA) LIKE '%"+idUnidad+"%'\n" +
                         "   AND UPPER(DSC_UNIDAD_EJECUTORA) LIKE '%"+nombUnidad+"%'\n"+
                         "   AND ROWNUM <= 10";
            SQLQuery q = session.createSQLQuery(sql);
            q.addEntity(UnidadEjecutoraEntity.class);
            
            List tipos = q.list();
            resp = tipos;
            
            session.close();
            return resp;
        } catch (Exception e) {
            if(session.isOpen())
                session.close();
            return resp;
        }
    }
    
    
}
