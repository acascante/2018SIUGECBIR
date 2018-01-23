/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.UsuarioDao;
import cr.ac.ucr.sigebi.domain.Usuario;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "usuarioModel")
@Scope("request")
public class UsuarioModel {

    @Resource
    private UsuarioDao usuarioDao;

    public Usuario buscarPorId(String idUsuario) throws FWExcepcion {
        return usuarioDao.buscarPorId(idUsuario);
    }
}
