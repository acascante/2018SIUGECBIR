/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.sigebi.daos.FaltaAdjuntoBienDao;
import cr.ac.ucr.sigebi.entities.AdjuntoBienEntity;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "adjuntoBienModel")
@Scope("request")
public class AdjuntoBienModel {
     
    @Resource
    private FaltaAdjuntoBienDao adjuntoBienDao;
    
    public String guardarAdjunto( AdjuntoBienEntity dato ){
        return adjuntoBienDao.guardarAdjunto(dato);
    }
    
    public List<AdjuntoBienEntity> traerAdjuntos( int idBien){
        return adjuntoBienDao.traerAdjuntos(idBien);
    }
    
    public String eliminarAdjunto(AdjuntoBienEntity dato){
        return adjuntoBienDao.eliminarAdjunto(dato);
    }
}
