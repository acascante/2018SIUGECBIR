/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.ExclusionDao;
import cr.ac.ucr.sigebi.domain.Exclusion;
import cr.ac.ucr.sigebi.domain.ExclusionDetalle;
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
    
    public List<Exclusion> listar() throws FWExcepcion {
        return exclusionDao.listar();
    }
    
    public void salvar(Exclusion exclusion) throws FWExcepcion {
        exclusionDao.salvar(exclusion);
        exclusionDao.salvarDetalles(exclusion.getDetalles());
    }
        
    public Long contar(Integer unidadEjecutora, Long id, Date fecha, Integer estado, Integer tipoExclusion) throws FWExcepcion {
        return exclusionDao.contar(unidadEjecutora, id, fecha, estado, tipoExclusion);
    }
    
    public List<Exclusion> listar(Integer primerRegistro, Integer ultimoRegistro, Integer unidadEjecutora, Long id, Date fecha, Integer estado, Integer tipo) throws FWExcepcion {
        return exclusionDao.listar(primerRegistro, ultimoRegistro, unidadEjecutora, id, fecha, estado, tipo);
    }
    
    public Long contarDetalles(Exclusion exclusion) throws FWExcepcion {
        return exclusionDao.contarDetalles(exclusion);
    }
    
    public List<ExclusionDetalle> listarDetalles(Exclusion exclusion) throws FWExcepcion {
        return exclusionDao.listarDetalles(exclusion);
    }
}