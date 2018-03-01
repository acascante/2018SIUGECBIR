/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.ExclusionDao;
import cr.ac.ucr.sigebi.domain.SolicitudDetalle;
import cr.ac.ucr.sigebi.domain.SolicitudExclusion;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author alvaro.cascante
 */
@Service(value = "exclusionModel")

public class ExclusionModel {
    
    @Resource
    private ExclusionDao exclusionDao;
    
    public List<SolicitudExclusion> listar() throws FWExcepcion {
        return exclusionDao.listar();
    }
    
    public void salvar(SolicitudExclusion exclusion) throws FWExcepcion {
        exclusionDao.salvar(exclusion);
        exclusionDao.salvarDetalles(exclusion.getDetalles());
    }
    
    public void eliminarDetalles(List<SolicitudDetalle> detalles) throws FWExcepcion {
        exclusionDao.eliminarDetalles(detalles);
    }
    
    public SolicitudExclusion buscarPorId(Long id) throws FWExcepcion {
        return exclusionDao.buscarPorId(id);
    }
            
    public Long contar(UnidadEjecutora unidadEjecutora, Long id, Date fecha, Integer idEstado, Integer idTipoExclusion) throws FWExcepcion {
        return exclusionDao.contar(unidadEjecutora, id, fecha, idEstado, idTipoExclusion);
    }
    
    public List<SolicitudExclusion> listar(Integer primerRegistro, Integer ultimoRegistro, UnidadEjecutora unidadEjecutora, Long id, Date fecha, Integer idEstado, Integer idTipoExclusion) throws FWExcepcion {
        return exclusionDao.listar(primerRegistro, ultimoRegistro, unidadEjecutora, id, fecha, idEstado, idTipoExclusion);
    }
    
    public Long contarDetalles(SolicitudExclusion exclusion) throws FWExcepcion {
        return exclusionDao.contarDetalles(exclusion);
    }
    
    public List<SolicitudDetalle> listarDetalles(SolicitudExclusion exclusion) throws FWExcepcion {
        return exclusionDao.listarDetalles(exclusion);
    }
}