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
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author alvaro.cascante
 */
@Service(value = "exclusionModel")
@Scope("request")
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
        
    public Long contar(Long unidadEjecutora, Long id, Date fecha, Integer estado, Integer tipoExclusion) throws FWExcepcion {
        return exclusionDao.contar(unidadEjecutora, id, fecha, estado, tipoExclusion);
    }
    
    public List<SolicitudExclusion> listar(Integer primerRegistro, Integer ultimoRegistro, Long unidadEjecutora, Long id, Date fecha, Integer estado, Integer tipo) throws FWExcepcion {
        return exclusionDao.listar(primerRegistro, ultimoRegistro, unidadEjecutora, id, fecha, estado, tipo);
    }
    
    public Long contarDetalles(SolicitudExclusion exclusion) throws FWExcepcion {
        return exclusionDao.contarDetalles(exclusion);
    }
    
    public List<SolicitudDetalle> listarDetalles(SolicitudExclusion exclusion) throws FWExcepcion {
        return exclusionDao.listarDetalles(exclusion);
    }
}