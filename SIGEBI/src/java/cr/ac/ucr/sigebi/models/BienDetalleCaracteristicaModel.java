/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.BienDetalleCaracteristicaDao;
import cr.ac.ucr.sigebi.domain.BienDetalleCaracteristica;
import java.util.List;
import javax.annotation.Resource;

/**
 *
 * @author alvaro.cascante
 */
public class BienDetalleCaracteristicaModel {
    
    @Resource
    private BienDetalleCaracteristicaDao bienDetaleCaracteristicaDao;
    
    public List<BienDetalleCaracteristica> listar() throws FWExcepcion {
        return bienDetaleCaracteristicaDao.listar();
    }
    
    public BienDetalleCaracteristica buscarPorId(Long id) throws FWExcepcion {
        return bienDetaleCaracteristicaDao.buscarPorId(id);
    }
}