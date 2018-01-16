/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.seguridad.entidades.SegUsuario;
import cr.ac.ucr.sigebi.daos.SegUsuarioDao;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "segUsuarioModel")
@Scope("request")
public class SegUsuarioModel {

    @Resource
    private SegUsuarioDao segUsuarioDao;

    public List<SegUsuario> listarUsuarios(String idUsuario,
            String nombreCompleto,
            String correo,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ) {
        return segUsuarioDao.listarUsuarios(idUsuario, nombreCompleto, correo, pPrimerRegistro, pUltimoRegistro);
    }

    public Long contarUsuarios(String idUsuario, String nombreCompleto,String correo ) {
        return segUsuarioDao.contarUsuarios(idUsuario, nombreCompleto, correo);
    }

}
