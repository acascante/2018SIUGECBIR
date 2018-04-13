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
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "ubicacionModel")

public class UbicacionModel {

    @Resource
    private UbicacionDao ubicacionDao;

    public List<Ubicacion> listar(UnidadEjecutora unidadEjecutora) throws FWExcepcion {
        return ubicacionDao.listar(unidadEjecutora);
    }
    
    public List<Ubicacion> listarUbicacionPadre(Ubicacion ubicacion) throws FWExcepcion {
        return ubicacionDao.listarUbicacionPadre(ubicacion);        
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
    
    public Long contar(String descripcion) throws FWExcepcion {
        return ubicacionDao.contar(descripcion);
    }

    public List<Ubicacion> listar(String descripcion, Integer pPrimerRegistro, Integer pUltimoRegistro) throws FWExcepcion {
        return ubicacionDao.listar(descripcion, pPrimerRegistro, pUltimoRegistro);
    }

}
