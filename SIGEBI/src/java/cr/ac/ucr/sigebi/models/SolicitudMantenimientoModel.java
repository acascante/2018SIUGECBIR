/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.SolicitudMantenimientoDao;
import cr.ac.ucr.sigebi.daos.UnidadEjecutoraDao;
import cr.ac.ucr.sigebi.domain.Evento;
import cr.ac.ucr.sigebi.domain.SolicitudDetalle;
import cr.ac.ucr.sigebi.domain.SolicitudMantenimiento;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import cr.ac.ucr.sigebi.utils.Constantes;
import org.springframework.stereotype.Service;

/**
 *
 * @author alvaro.cascante
 */
@Service(value = "solicitudMantenimientoModel")

public class SolicitudMantenimientoModel {
    
    @Resource
    private SolicitudMantenimientoDao solicitudMantenimientoDao;

    @Resource
    private UnidadEjecutoraDao unidadEjecutoraDao;

    public List<SolicitudMantenimiento> listar() throws FWExcepcion {
        return solicitudMantenimientoDao.listar();
    }
    
    public void salvar(SolicitudMantenimiento solicitudMantenimiento) throws FWExcepcion {
        solicitudMantenimientoDao.salvar(solicitudMantenimiento);
        solicitudMantenimientoDao.salvarDetalles(solicitudMantenimiento.getDetalles());
    }
    
    public void eliminarDetalles(List<SolicitudDetalle> detalles) throws FWExcepcion {
        solicitudMantenimientoDao.eliminarDetalles(detalles);
    }
    
    public SolicitudMantenimiento buscarPorId(Long id) throws FWExcepcion {
        return solicitudMantenimientoDao.buscarPorId(id);
    }
            
    public Long contar(Long idUnidadEjecutora, Long id, Date fecha, Long idEstado) throws FWExcepcion {
        if (idUnidadEjecutora.equals(Constantes.DEFAULT_ID)) {
            return solicitudMantenimientoDao.contar(null, id, fecha, idEstado);
        }
        else {
            return solicitudMantenimientoDao.contar(unidadEjecutoraDao.buscarPorId(idUnidadEjecutora), id, fecha, idEstado);
        }

    }
    
    public List<SolicitudMantenimiento> listar(Integer primerRegistro, Integer ultimoRegistro, Long idUnidadEjecutora, Long id, Date fecha, Long idEstado) throws FWExcepcion {
        if (idUnidadEjecutora.equals(Constantes.DEFAULT_ID)) {
            return solicitudMantenimientoDao.listar(primerRegistro, ultimoRegistro, null, id, fecha, idEstado);
        }
        else {
            return solicitudMantenimientoDao.listar(primerRegistro, ultimoRegistro, unidadEjecutoraDao.buscarPorId(idUnidadEjecutora), id, fecha, idEstado);
        }
    }
    
    public Long contarDetalles(SolicitudMantenimiento solicitudMantenimiento) throws FWExcepcion {
        return solicitudMantenimientoDao.contarDetalles(solicitudMantenimiento);
    }
    
    public List<SolicitudDetalle> listarDetalles(SolicitudMantenimiento solicitudMantenimiento) throws FWExcepcion {
        return solicitudMantenimientoDao.listarDetalles(solicitudMantenimiento);
    }
}