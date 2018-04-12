/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.RolDao;
import cr.ac.ucr.sigebi.domain.Rol;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "rolModel")

public class RolModel {

    @Resource
    private RolDao rolDao;

    public void agregar(Rol obj) throws FWExcepcion {
        rolDao.agregar(obj);
    }

    public void modificar(Rol obj) throws FWExcepcion {
        rolDao.modificar(obj);
    }

    public void eliminar(Rol obj) throws FWExcepcion {
        rolDao.eliminar(obj);
    }

    public Rol buscarPorId(Long idRol) throws FWExcepcion {
        return rolDao.buscarPorId(idRol);
    }

    public List<Rol> listarTodos() throws FWExcepcion {

        return rolDao.listarTodos();
    }

    public List<Rol> listarRolesNoAsociados(Long idDocumento) throws FWExcepcion {
        return rolDao.listarRolesNoAsociados(idDocumento);
    }

    public List<Rol> listarRoles(Integer codigo, String nombre, Integer estado, Integer pPrimerRegistro, Integer pUltimoRegistro) throws FWExcepcion {
        return rolDao.listarRoles(codigo, nombre, estado, pPrimerRegistro, pUltimoRegistro);
    }

    public Long contarRoles(Integer codigo, String nombre, Integer estado) throws FWExcepcion {
        return rolDao.contarRoles(codigo, nombre, estado);
    }

    public Long contarRolesValidator(Long idRolDiferente, Integer codigo, String nombre) {
        return rolDao.contarRolesValidator(idRolDiferente, codigo, nombre);
    }

    public Long verificaRolUso(Long idRol) {
        return rolDao.verificaRolUso(idRol);
    }
}
