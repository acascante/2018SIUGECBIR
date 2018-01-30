/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.AutorizacionDao;
import cr.ac.ucr.sigebi.domain.Autorizacion;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "autorizacionModel")
@Scope("request")
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

    public List<Autorizacion> buscarPorTipoProceso(Integer idTipoProceso) throws FWExcepcion {
        return autorizacionDao.buscarPorTipoProceso(idTipoProceso);
    }

    public Long contarAutorizacionsValidator(Long idAutorizacionDiferente, Integer idProceso, Integer orden, String nombre, Integer codigo) throws FWExcepcion {
        return autorizacionDao.contarAutorizacionsValidator(idAutorizacionDiferente, idProceso, orden, nombre, codigo);
    }

}
