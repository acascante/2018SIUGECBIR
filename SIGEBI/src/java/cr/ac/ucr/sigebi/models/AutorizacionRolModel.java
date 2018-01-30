/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.AutorizacionRolDao;
import cr.ac.ucr.sigebi.domain.AutorizacionRol;
import cr.ac.ucr.sigebi.entities.ViewDocumAprobEntity;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "autorizacionRolModel")
@Scope("request")
public class AutorizacionRolModel {

    @Resource
    private AutorizacionRolDao autorizacionRolDao;

    public void agregar(AutorizacionRol obj) throws FWExcepcion {
        autorizacionRolDao.agregar(obj);
    }

    public void modificar(AutorizacionRol obj) throws FWExcepcion {
        autorizacionRolDao.modificar(obj);
    }

    public void eliminar(AutorizacionRol obj) throws FWExcepcion {
        autorizacionRolDao.eliminar(obj);
    }

    public List<AutorizacionRol> buscarPorAutorizacion(Long idAutorizacion) throws FWExcepcion {
        return autorizacionRolDao.buscarPorAutorizacion(idAutorizacion);
    }

    public List<AutorizacionRol> buscarPorCodigoAutorizacion(Integer codigoAutorizacion) throws FWExcepcion {
        return autorizacionRolDao.buscarPorCodigoAutorizacion(codigoAutorizacion);
    }

    public Long contarPorAutorizacion(Long idAutorizacion) throws FWExcepcion {
        return autorizacionRolDao.contarPorAutorizacion(idAutorizacion);
    }

    public AutorizacionRol buscarPorRolAutorizacion(Long idRol, Long idAutorizacion) throws FWExcepcion {
        return autorizacionRolDao.buscarPorRolAutorizacion(idRol, idAutorizacion);
    }

    public List<ViewDocumAprobEntity> buscarRolDocumId(int idTipoAutorizacion, int idAutorizacion) throws FWExcepcion {
        return autorizacionRolDao.buscarRolDocumId(idTipoAutorizacion, idAutorizacion);
    }

}
