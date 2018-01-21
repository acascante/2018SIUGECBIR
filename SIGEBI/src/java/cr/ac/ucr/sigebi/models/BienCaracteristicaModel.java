/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.BienCaracteristicaDao;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.BienCaracteristica;
import cr.ac.ucr.sigebi.domain.Tipo;
import java.util.List;
import javax.annotation.Resource;

/**
 *
 * @author alvaro.cascante
 */
public class BienCaracteristicaModel {
    
    @Resource
    private BienCaracteristicaDao bienCaracteristicaDao;
    
    public List<BienCaracteristica> listarPorBien(Bien bien) throws FWExcepcion {
        return bienCaracteristicaDao.listarPorBien(bien);
    }
    
    public BienCaracteristica buscarPorId(Long id) throws FWExcepcion {
        return bienCaracteristicaDao.buscarPorId(id);
    }

    public BienCaracteristica buscarPorBienTipo(Bien bien, Tipo tipo) throws FWExcepcion {
        return bienCaracteristicaDao.buscarPorBienTipo(bien, tipo);
    }

    public void almacenar(BienCaracteristica caracteristica) throws FWExcepcion {
        bienCaracteristicaDao.almacenar(caracteristica);
    }

    public void eliminar(BienCaracteristica caracteristica)throws FWExcepcion {
        bienCaracteristicaDao.eliminar(caracteristica);
    }
}