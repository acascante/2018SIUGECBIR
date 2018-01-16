/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.entities.ViewBienEntity;
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
@Repository(value = "viewBienDao")
@Scope("request")
public class ViewBienDao  extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;
    
    public ViewBienEntity traerPorId(Integer id){
        ViewBienEntity resp = new ViewBienEntity();
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "  from ViewBienEntity p where p.idBien = :pIdBien";

            Query query = session.createQuery(sql);
            query.setParameter("pIdBien", id);
            //query.setE(PersonaEntity.class);

            resp = (ViewBienEntity) query.list().get(0);
            session.close();
            
            return resp;
        
        } catch (Exception e) {
            session.close();
            return null;
        }
    }
    
    @Transactional
    public List<ViewBienEntity> listarBienes(Integer unidEjecutora,
            String fltIdBien,
            String fltDescripcion,
            String fltMarca,
            String fltModelo,
            String fltSerie,
            Integer[] fltEstadosBienes,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ) {
        Session session = dao.getSessionFactory().openSession();
        try {

            //Se genera el query para la busqueda de los bienes
            Query q = this.creaQueryListarBienes(unidEjecutora, fltIdBien, fltDescripcion, fltMarca, fltModelo, fltSerie, fltEstadosBienes, false, session);
            
            //Paginacion
            if(!(pPrimerRegistro.equals(1)&& pUltimoRegistro.equals(1))){
                q.setFirstResult(pPrimerRegistro);
                q.setMaxResults(pUltimoRegistro - pPrimerRegistro);
            }
            //Se obtienen los resutltados
            return (List<ViewBienEntity>) q.list();   

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getCause());
            throw new FWExcepcion("sigebi.error.dao.bien.listarBienes",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }        
    }
    
    @Transactional(readOnly = true)
    public Long contarBienes(Integer unidEjecutora,
            String fltIdBien,
            String fltDescripcion,
            String fltMarca,
            String fltModelo,
            String fltSerie,
            Integer[] fltEstadosBienes
    ) {
        Session session = dao.getSessionFactory().openSession();
        try {
            
            //Se genera el query para la busqueda de los bienes
            Query q = this.creaQueryListarBienes(unidEjecutora, fltIdBien, fltDescripcion, fltMarca, fltModelo, fltSerie, fltEstadosBienes, true, session);

            //Se obtienen los resutltados
            return (Long)q.uniqueResult();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new FWExcepcion("sigebi.error.dao.bien.contarBienes",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }        
    }
    
    private Query creaQueryListarBienes(Integer unidEjecutora,
            String fltIdBien,
            String fltDescripcion,
            String fltMarca,
            String fltModelo,
            String fltSerie,
            Integer[] fltEstadosBienes,
            Boolean contar, Session session
    ) {
        String sql;
        if (contar) {
            sql = "SELECT count(s) FROM ViewBienEntity s ";
        } else {
            sql = "SELECT s FROM ViewBienEntity s ";
        }
        //Select
        sql = sql + " WHERE s.numUnidadEjec = :pnumUnidadEjec ";
        if(fltIdBien != null && fltIdBien.length() > 0){
           sql = sql +  " AND upper(s.idPlaca.placa) like upper(:fltIdBien) ";
        }
        if(fltDescripcion != null && fltDescripcion.length() > 0){
            sql = sql + " AND upper(s.descripcion) like upper(:fltDescripcion) ";
        }
        if(fltMarca != null && fltMarca.length() > 0){
            sql = sql + " AND upper(s.marca) like upper(:fltMarca) ";
        }
        if(fltModelo != null && fltModelo.length() > 0){
            sql = sql + " AND upper(s.modelo) like upper(:fltModelo) ";
        }
        if(fltSerie != null && fltSerie.length() > 0){
            sql = sql + " AND upper(s.serie) like upper(:fltSerie) ";
        }
        if(fltEstadosBienes != null && fltEstadosBienes.length > 0){
            sql = sql + " AND s.idEstado.estado in (:fltEstadosBienes)";
        }
        
        Query q = session.createQuery(sql);
        q.setParameter("pnumUnidadEjec", unidEjecutora);
        if(fltIdBien != null && fltIdBien.length() > 0){
            q.setParameter("fltIdBien", '%' + fltIdBien + '%');
        }
        if(fltDescripcion != null && fltDescripcion.length() > 0){
            q.setParameter("fltDescripcion", '%' + fltDescripcion + '%');
        }
        if(fltMarca != null && fltMarca.length() > 0){
            q.setParameter("fltMarca", '%' + fltMarca + '%');
        }
        if(fltModelo != null && fltModelo.length() > 0){
            q.setParameter("fltModelo", '%' + fltModelo + '%');
        }
        if(fltSerie != null && fltSerie.length() > 0){
            q.setParameter("fltSerie", '%' + fltSerie + '%');
        }
        if(fltEstadosBienes != null && fltEstadosBienes.length > 0){
            q.setParameterList("fltEstadosBienes", fltEstadosBienes);
        }
        return q;
    }
    
    
    
    
}
