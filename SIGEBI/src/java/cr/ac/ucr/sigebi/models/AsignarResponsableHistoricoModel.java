/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.AsignarResponsableHistoricoDao;
import cr.ac.ucr.sigebi.domain.AsignarResponsableHistorico;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "AsignarResponsableHistoricoModel")
public class AsignarResponsableHistoricoModel {
    
    
    @Resource
    private AsignarResponsableHistoricoDao asignacionDao;
    
    public void guargar(AsignarResponsableHistorico obj) throws FWExcepcion {
        asignacionDao.guardar(obj);
    }
    
    
    public List<AsignarResponsableHistorico> listarRespoansables(Bien bien, UnidadEjecutora unidad) throws FWExcepcion {
        return asignacionDao.listarRespoansables(bien, unidad);
    }
    
    public AsignarResponsableHistorico getHistoricoActivo(Bien bien)  throws FWExcepcion {
        return asignacionDao.getHistoricoActivo(bien);
    }
    
    
}
