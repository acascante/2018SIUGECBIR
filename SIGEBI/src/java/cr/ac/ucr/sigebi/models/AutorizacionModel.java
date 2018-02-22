/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.AutorizacionDao;
import cr.ac.ucr.sigebi.domain.Autorizacion;
import cr.ac.ucr.sigebi.domain.Tipo;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "autorizacionModel")

public class AutorizacionModel {

    @Resource
    private AutorizacionDao autorizacionDao;

    public void agregar(Autorizacion obj) throws FWExcepcion {
        autorizacionDao.agregar(obj);
    }

    public void modificar(Autorizacion obj) throws FWExcepcion {
        autorizacionDao.modificar(obj);
    }

    public void eliminar(Autorizacion obj) throws FWExcepcion {
        autorizacionDao.eliminar(obj);
    }

    public Autorizacion buscarPorId(Long idAutorizacion) throws FWExcepcion {
        return autorizacionDao.buscarPorId(idAutorizacion);
    }
    
    public Autorizacion buscarPorCodigo(Integer idCodigo){
        return autorizacionDao.buscarPorCodigo(idCodigo);
    }

    public List<Autorizacion> buscarPorTipoProceso(Tipo tipoProceso) throws FWExcepcion {
        return autorizacionDao.buscarPorTipoProceso(tipoProceso);
    }

    public Long contarAutorizacionsValidator(Long idAutorizacionDiferente, Long idProceso, Integer orden, String nombre, Integer codigo) throws FWExcepcion {
        return autorizacionDao.contarAutorizacionsValidator(idAutorizacionDiferente, idProceso, orden, nombre, codigo);
    }

}
