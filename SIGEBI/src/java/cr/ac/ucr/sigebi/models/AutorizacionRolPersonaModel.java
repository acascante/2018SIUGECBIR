/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.AutorizacionRolPersonaDao;
import cr.ac.ucr.sigebi.domain.AutorizacionRolPersona;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "autorizacionRolPersonaModel")
@Scope("request")
public class AutorizacionRolPersonaModel {

    @Resource
    private AutorizacionRolPersonaDao autorizacionRolPersonaDao;

    public void agregar(AutorizacionRolPersona obj) throws FWExcepcion{
        autorizacionRolPersonaDao.agregar(obj);
    }

    public void modificar(AutorizacionRolPersona obj) throws FWExcepcion{
        autorizacionRolPersonaDao.modificar(obj);
    }

    public void eliminar(AutorizacionRolPersona obj) throws FWExcepcion{
        autorizacionRolPersonaDao.eliminar(obj);
    }

    public List<AutorizacionRolPersona> buscarPorAutorizacionRol(Long idAutorizacion, Long idRol) throws FWExcepcion{
        return autorizacionRolPersonaDao.buscarPorAutorizacionRol(idAutorizacion, idRol);
    }

    public List<AutorizacionRolPersona> buscarUsuariosPorAutorizacion(Long idAutorizacion, Long numUnidadEjec) throws FWExcepcion{
        return autorizacionRolPersonaDao.buscarUsuariosPorAutorizacion(idAutorizacion, numUnidadEjec);
    }

    public Long contarPorAutorizacionRol(Long idAutorizacion, Long idRol) throws FWExcepcion{
        return autorizacionRolPersonaDao.contarPorAutorizacionRol(idAutorizacion, idRol);
    }

    public AutorizacionRolPersona buscarPorRolAutorizacionUsuario(Long idRol, Long idAutorizacion, String idUsuario) throws FWExcepcion{
        return autorizacionRolPersonaDao.buscarPorRolAutorizacionUsuario(idRol, idAutorizacion, idUsuario);
    }

    public AutorizacionRolPersona buscarAutorizacionPorAutorizacionRolUsuario(String codigoRol, Long idAutorizacion, String idUsuario) throws FWExcepcion {
        return autorizacionRolPersonaDao.buscarAutorizacionPorAutorizacionRolUsuario(codigoRol, idAutorizacion, idUsuario);
    }

    public List<AutorizacionRolPersona> buscarRolesPorAutorizacionUsuario(Long idAutorizacion, String idUsuario, Long unidadEjecutora) throws FWExcepcion{
        return autorizacionRolPersonaDao.buscarRolesProAutorizacionUsuario(idAutorizacion, idUsuario, unidadEjecutora);
    }
}