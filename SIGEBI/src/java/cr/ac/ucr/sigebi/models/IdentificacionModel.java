/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.IdentificacionDao;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Identificacion;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author alvaro.cascante
 */
@Service(value = "identificacionModel")

public class IdentificacionModel {
    
    @Resource
    private IdentificacionDao identificacionDao;
    
    public List<Identificacion> listar(Estado estado, UnidadEjecutora unidadEjecutora, String identificacion){
        return identificacionDao.listar(estado, unidadEjecutora, identificacion);
    }
            
    public Identificacion siguienteDisponible(Estado estado, UnidadEjecutora unidadEjecutora) throws FWExcepcion {
        return identificacionDao.siguienteDisponible(estado, unidadEjecutora);
    }

    public void actualizar(Identificacion identificacion) {
        identificacionDao.actualizar(identificacion);
    }
    
    public Identificacion buscarPorIdentificacion(String identificacion) throws FWExcepcion{
        return identificacionDao.buscarPorIdentificacion(identificacion);
    }
    
    public Long cantidadDisponibles(UnidadEjecutora unidadEjecutora, Estado estado) throws FWExcepcion {
        return identificacionDao.cantidadDisponibles(unidadEjecutora, estado);
    }
    
    public void reservarIdentificaciones(Long cantidadSolicitada, Long idUnidadEjecutora, Integer estado, Long idAsignacion) throws FWExcepcion {
        identificacionDao.reservarIdentificaciones(cantidadSolicitada, idUnidadEjecutora, estado, idAsignacion);
    }
}