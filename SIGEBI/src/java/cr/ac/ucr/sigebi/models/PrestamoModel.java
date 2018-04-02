/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.PrestamoDao;
import cr.ac.ucr.sigebi.domain.SolicitudDetalle;
import cr.ac.ucr.sigebi.domain.SolicitudPrestamo;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author alvaro.cascante
 */
@Service(value = "prestamoModel")

public class PrestamoModel {
    
    @Resource
    private PrestamoDao prestamoDao;
    
    public List<SolicitudPrestamo> listar() throws FWExcepcion {
        return prestamoDao.listar();
    }
    
    public void salvar(SolicitudPrestamo prestamo) throws FWExcepcion {
        prestamoDao.salvar(prestamo);
        prestamoDao.salvarDetalles(prestamo.getDetalles());
    }
    
    public void eliminarDetalles(List<SolicitudDetalle> detalles) throws FWExcepcion {
        prestamoDao.eliminarDetalles(detalles);
    }
    
    public SolicitudPrestamo buscarPorId(Long id) throws FWExcepcion {
        return prestamoDao.buscarPorId(id);
    }
            
    public Long contar(UnidadEjecutora unidadEjecutora, Long id, Date fecha, Long idEstado, Long idTipoEntidad, String entidad) throws FWExcepcion {
        return prestamoDao.contar(unidadEjecutora, id, fecha, idEstado, idTipoEntidad, entidad);
    }
    
    public List<SolicitudPrestamo> listar(Integer primerRegistro, Integer ultimoRegistro, UnidadEjecutora unidadEjecutora, Long id, Date fecha, Long idEstado, Long idTipoEntidad, String entidad) throws FWExcepcion {
        return prestamoDao.listar(primerRegistro, ultimoRegistro, unidadEjecutora, id, fecha, idEstado, idTipoEntidad, entidad);
    }
    
    public Long contarDetalles(SolicitudPrestamo prestamo) throws FWExcepcion {
        return prestamoDao.contarDetalles(prestamo);
    }
    
    public List<SolicitudDetalle> listarDetalles(SolicitudPrestamo prestamo) throws FWExcepcion {
        return prestamoDao.listarDetalles(prestamo);
    }
}