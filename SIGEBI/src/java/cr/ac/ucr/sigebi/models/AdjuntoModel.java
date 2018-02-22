/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.sigebi.daos.AdjuntoDao;
import cr.ac.ucr.sigebi.domain.Adjunto;
import cr.ac.ucr.sigebi.domain.Tipo;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "adjuntoModel")

public class AdjuntoModel {
    
    @Resource
    private AdjuntoDao adjuntoDao;

    public void agregar(Adjunto adjunto){
        adjuntoDao.agregar(adjunto);
    }
    
    public void eliminar(Adjunto adjunto){
        adjuntoDao.eliminar(adjunto);
    }
    
    
    public List<Adjunto> buscarPorReferencia(Tipo tipoDocumento, Long idReferencia) {
        return adjuntoDao.buscarPorReferencia(tipoDocumento, idReferencia);
    }
      
}
