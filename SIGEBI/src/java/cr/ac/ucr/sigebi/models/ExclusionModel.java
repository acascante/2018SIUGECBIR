/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.ExclusionDao;
import cr.ac.ucr.sigebi.daos.UnidadEjecutoraDao;
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

    @Resource
    private UnidadEjecutoraDao unidadEjecutoraDao;

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
            
    public Long contar(Long idUnidadEjecutora, Long id, Date fecha, Long idEstado, Long idTipoExclusion) throws FWExcepcion {
        return exclusionDao.contar(unidadEjecutoraDao.buscarPorId(idUnidadEjecutora), id, fecha, idEstado, idTipoExclusion);
    }
    
    public List<SolicitudExclusion> listar(Integer primerRegistro, Integer ultimoRegistro, Long idUnidadEjecutora, Long id, Date fecha, Long idEstado, Long idTipoExclusion) throws FWExcepcion {
        return exclusionDao.listar(primerRegistro, ultimoRegistro, unidadEjecutoraDao.buscarPorId(idUnidadEjecutora), id, fecha, idEstado, idTipoExclusion);
    }
    
    public Long contarDetalles(SolicitudExclusion exclusion) throws FWExcepcion {
        return exclusionDao.contarDetalles(exclusion);
    }
    
    public List<SolicitudDetalle> listarDetalles(SolicitudExclusion exclusion) throws FWExcepcion {
        return exclusionDao.listarDetalles(exclusion);
    }
}