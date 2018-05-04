/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.UsuarioDao;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.domain.Usuario;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "usuarioModel")

public class UsuarioModel {

    @Resource
    private UsuarioDao usuarioDao;

    public Usuario buscarPorId(String idUsuario) throws FWExcepcion {
        return usuarioDao.buscarPorId(idUsuario);
    }

    public List<Usuario> listarUsuarios(String idUsuario, String nombreCompleto, String correo, Integer pPrimerRegistro, Integer pUltimoRegistro) throws FWExcepcion {
        return usuarioDao.listarUsuarios(idUsuario, nombreCompleto, correo, pPrimerRegistro, pUltimoRegistro);
    }

    public Long contarUsuarios(String idUsuario, String nombreCompleto, String correo) throws FWExcepcion {
        return usuarioDao.contarUsuarios(idUsuario, nombreCompleto, correo);
    }
    
    public List<Usuario> listarUsuariosUnidad(UnidadEjecutora unidadEjecutora) throws FWExcepcion{
        return usuarioDao.listarUsuariosUnidad(unidadEjecutora);
    }
            
}
