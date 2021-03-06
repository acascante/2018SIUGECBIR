/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.PrestamoDao;
import cr.ac.ucr.sigebi.daos.UnidadEjecutoraDao;
import cr.ac.ucr.sigebi.domain.SolicitudDetallePrestamo;
import cr.ac.ucr.sigebi.domain.SolicitudPrestamo;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import cr.ac.ucr.sigebi.utils.Constantes;
import org.springframework.stereotype.Service;

/**
 *
 * @author alvaro.cascante
 */
@Service(value = "prestamoModel")

public class PrestamoModel {
    
    @Resource private PrestamoDao prestamoDao;

    @Resource private UnidadEjecutoraDao unidadEjecutoraDao;
    
    public List<SolicitudPrestamo> listar() throws FWExcepcion {
        return prestamoDao.listar();
    }
    
    public void salvar(SolicitudPrestamo prestamo) throws FWExcepcion {
        prestamoDao.salvar(prestamo);
        prestamoDao.salvarDetalles(prestamo.getDetallesPrestamo());
    }
    
    public void eliminarDetalles(List<SolicitudDetallePrestamo> detalles) throws FWExcepcion {
        prestamoDao.eliminarDetalles(detalles);
    }
    
    public SolicitudPrestamo buscarPorId(Long id) throws FWExcepcion {
        return prestamoDao.buscarPorId(id);
    }
            
    public Long contar(Long idUnidadEjecutora, Long id, Date fecha, Long idEstado, Long idTipoEntidad, String entidad) throws FWExcepcion {
        if (idUnidadEjecutora.equals(Constantes.DEFAULT_ID)) {
            return prestamoDao.contar(null, id, fecha, idEstado, idTipoEntidad, entidad);
        }
        else {
            return prestamoDao.contar(unidadEjecutoraDao.buscarPorId(idUnidadEjecutora), id, fecha, idEstado, idTipoEntidad, entidad);
        }

    }
    
    public List<SolicitudPrestamo> listar(Integer primerRegistro, Integer ultimoRegistro, Long idUnidadEjecutora, Long id, Date fecha, Long idEstado, Long idTipoEntidad, String entidad) throws FWExcepcion {
        if (idUnidadEjecutora.equals(Constantes.DEFAULT_ID)) {
            return prestamoDao.listar(primerRegistro, ultimoRegistro, null, id, fecha, idEstado, idTipoEntidad, entidad);
        }
        else {
            return prestamoDao.listar(primerRegistro, ultimoRegistro, unidadEjecutoraDao.buscarPorId(idUnidadEjecutora), id, fecha, idEstado, idTipoEntidad, entidad);
        }

    }
    
    public Long contarDetalles(SolicitudPrestamo prestamo) throws FWExcepcion {
        return prestamoDao.contarDetalles(prestamo);
    }
    
    public List<SolicitudDetallePrestamo> listarDetalles(SolicitudPrestamo prestamo) throws FWExcepcion {
        return prestamoDao.listarDetalles(prestamo);
    }
}