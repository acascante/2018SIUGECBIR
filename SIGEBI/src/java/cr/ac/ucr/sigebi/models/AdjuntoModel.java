/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.sigebi.daos.AdjuntoDao;
import cr.ac.ucr.sigebi.entities.AdjuntoEntity;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "adjuntoModel")
@Scope("request")
public class AdjuntoModel {
    
    @Resource
    private AdjuntoDao adjuntoDao;

    public void agregar(AdjuntoEntity adjunto){
        adjuntoDao.agregar(adjunto);
    }
    
    public void eliminar(AdjuntoEntity adjunto){
        adjuntoDao.eliminar(adjunto);
    }
    
    public List<AdjuntoEntity> buscarPorReferencia(Long idReferencia){
        return adjuntoDao.buscarPorReferencia(idReferencia);
    }    
}
