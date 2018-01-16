/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.entities.PersonaEntity;
import cr.ac.ucr.sigebi.entities.ProveedorEntity;
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
@Repository(value = "proveedorDao")
@Scope("request")
public class ProveedorDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;
    
    
    @Transactional
    public ProveedorEntity traerPorId(Integer pIdProveedor) {
        ProveedorEntity resp = new ProveedorEntity();
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "  from PersonaEntity p where p.numPersona = :pIdProveedor";

            Query query = session.createQuery(sql);
            query.setParameter("pIdProveedor", pIdProveedor);
            //query.setE(PersonaEntity.class);

            PersonaEntity resp1 = (PersonaEntity) query.list().get(0);
            
            session.close();
            resp.setIdProveedor(resp1);
            return resp;
        
        } catch (Exception e) {
            session.close();
            return null;
        }
    }
    
    @Transactional
    public List<PersonaEntity> traerTodo() {
        List<PersonaEntity> resp = new ArrayList<PersonaEntity>();
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "        SELECT PR.* " +
                         "        FROM SF_PROVEEDOR PV " +
                         "            LEFT JOIN SF_PERSONA PR " +
                         "            ON( PV.ID_PROVEEDOR = PR.NUM_PERSONA )"+
                         "        WHERE ROWNUM < 20";

            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(PersonaEntity.class);

            List tipos = query.list();
            
            resp = tipos;
            session.close();
            
            return resp;
        
        } catch (Exception e) {
            session.close();
            throw new FWExcepcion ("sigebi.error.listaProbeedores",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }

    }
     
    @Transactional
    public List<PersonaEntity> filtroProveedores(String identificacion
                                                  ,String nombre) {
        
            List<PersonaEntity> resp = new ArrayList<PersonaEntity>();
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "        SELECT PR.* " +
                         "        FROM SF_PROVEEDOR PV " +
                         "            LEFT JOIN SF_PERSONA PR " +
                         "            ON( PV.ID_PROVEEDOR = PR.NUM_PERSONA )"+
                         "        WHERE PR.IDETIFICACION LIKE '%"+identificacion+"%'" +
                         "            AND UPPER( CONCAT( PR.NOMBRE, PR.PRIMER_APELLIDO) ) LIKE UPPER( '%"+nombre+"%' ) " +
                         "            AND ROWNUM < 20";

            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(PersonaEntity.class);

            List tipos = query.list();
            
            resp = tipos;
            session.close();
            
            return resp;
        
        } catch (Exception e) {
            throw new FWExcepcion ("sigebi.error.listaProbeedores",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }

    }
     
    
    /*
    
    SELECT s FROM BienEntity s WHERE s.numUnidadEjec = :pnumUnidadEjec "
                            + " AND str(s.idBien) like :fltIdBien "
                            + " AND upper(s.descripcion) like upper(:fltDescripcion) "
                            + " AND str(s.idEstado) like :fltEstado 
    
   */ 
}
