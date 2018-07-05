/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.IdentificacionDao;
import cr.ac.ucr.sigebi.domain.AsignacionPlaca;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Identificacion;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.utils.Constantes;
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

    @Resource
    private EstadoModel estadoModel;
    
    public List<Identificacion> listar(Estado estado, UnidadEjecutora unidadEjecutora, String identificacion){
        return identificacionDao.listar(estado, unidadEjecutora, identificacion);
    }

    public List<Identificacion> listar(AsignacionPlaca asignacionPlaca, Estado estadoIdentificacion){
        return identificacionDao.listar(asignacionPlaca, estadoIdentificacion);
    }

    public Identificacion siguienteDisponible(UnidadEjecutora unidadEjecutora, Boolean capitalizable) throws FWExcepcion {

        Estado estado = null;
        if (capitalizable != null && capitalizable) {
            if (unidadEjecutora.getIdTipoUnidad().equals(Constantes.TIPO_UNIDAD_EJECUTORA_COMPRA)) {

                //Se busca las identificaciones asociadas a la unidad y en estado de reserva
                estado = estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_RESERVADA_UNIDAD);
                return identificacionDao.siguienteDisponible(estado, unidadEjecutora);
            } else {

                //Se busca las identificaciones sin unidad y en estado disponible
                estado = estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_DISPONIBLE);
                return identificacionDao.siguienteDisponible(estado, unidadEjecutora);
            }
        } else {
            
            //Se busca las identificaciones sin unidad y en estado disponible
            estado = estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_DISPONIBLE);
            return identificacionDao.siguienteDisponible(estado, null);
        }
    }

    public Identificacion buscarUltimoRegistro() throws FWExcepcion {
        return identificacionDao.buscarUltimoRegistro();
    }
    
    public void actualizar(Identificacion identificacion) {
        identificacionDao.actualizar(identificacion);
    }
    
    public Boolean almacenar(Identificacion identificacion) {
        return identificacionDao.almacenar(identificacion);
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