/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.sigebi.daos.UsuarioDao;
import cr.ac.ucr.sigebi.entities.UsuarioEntity;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "UsuarioModel")
@Scope("request")
public class UsuarioModel {
    
    @Resource
    private UsuarioDao usuarioDao;
    
    public UsuarioEntity buscarPorId(String idUsuario) {
        return usuarioDao.buscarPorId(idUsuario);
    }
    
    
}
