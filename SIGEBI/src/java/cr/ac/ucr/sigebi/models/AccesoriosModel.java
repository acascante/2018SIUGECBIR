/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.sigebi.daos.AccesoriosDao;
import cr.ac.ucr.sigebi.entities.AccesoriosEntity;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "AccesoriosModel")
@Scope("request")
public class AccesoriosModel {
    
    @Resource
    private AccesoriosDao accesorioDao;
    
    
    public String guardarAccesorio( AccesoriosEntity dato ){
        return accesorioDao.guardarAccesorio(dato);
    }
    
    public List<AccesoriosEntity> traerAccesorios( int idBien){
        return accesorioDao.traerAccesorios(idBien);
    }
    
    public String eliminarAccesorio(AccesoriosEntity dato){
        return accesorioDao.eliminarAccesorio(dato);
    }
}
