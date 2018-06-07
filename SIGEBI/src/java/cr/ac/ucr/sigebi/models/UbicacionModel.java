/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.UbicacionDao;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Ubicacion;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "ubicacionModel")
public class UbicacionModel {

    @Resource
    private UbicacionDao ubicacionDao;

//    public List<Ubicacion> listar(UnidadEjecutora unidadEjecutora) throws FWExcepcion {
//        return ubicacionDao.listar(unidadEjecutora);
//    }
    
    public List<Ubicacion> listarUbicacionPadre(Ubicacion ubicacion,  Estado estado) throws FWExcepcion {
        return ubicacionDao.listarUbicacionPadre(ubicacion, estado);        
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
    
    public Long contar(String descripcion, UnidadEjecutora unidadEjecutora, Estado estado) throws FWExcepcion {
        return ubicacionDao.contar(descripcion, unidadEjecutora, estado);
    }

    public List<Ubicacion> listar(String descripcion, UnidadEjecutora unidadEjecutora, Estado estado, Integer pPrimerRegistro, Integer pUltimoRegistro) throws FWExcepcion {
        return ubicacionDao.listar(descripcion, unidadEjecutora, estado, pPrimerRegistro, pUltimoRegistro);
    }

}
