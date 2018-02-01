/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.UbicacionDao;
import cr.ac.ucr.sigebi.domain.Ubicacion;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "ubicacionModel")
@Scope("request")
public class UbicacionModel {

    @Resource
    private UbicacionDao ubicacionDao;

    public List<Ubicacion> listar(UnidadEjecutora unidadEjecutora) throws FWExcepcion {
        return ubicacionDao.listar(unidadEjecutora);
    }
    
    public Ubicacion buscarPorId(Long id) throws FWExcepcion {
        return ubicacionDao.buscarPorId(id);
    }
    
    public void almacenar(Ubicacion ubicacion) throws FWExcepcion {
        ubicacionDao.almacenar(ubicacion);
    }

    public void eliminar(Ubicacion ubicacion)throws FWExcepcion {
        ubicacionDao.eliminar(ubicacion);
    }
}
