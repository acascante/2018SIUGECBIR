/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.sigebi.daos.RolDao;
import cr.ac.ucr.sigebi.entities.RolEntity;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "rolModel")
@Scope("request")
public class RolModel {
    
    @Resource
    private RolDao rolDao;

    public void agregar(RolEntity obj) {
        rolDao.agregar(obj);
    }

    public void modificar(RolEntity obj) {       
        rolDao.modificar(obj);
    }
    
    public void eliminar(RolEntity obj) {       
        rolDao.eliminar(obj);
    }

    public RolEntity buscarPorId(Long idRol){
        return rolDao.buscarPorId(idRol);
    }
            
    public List<RolEntity> listarTodos() {

       return rolDao.listarTodos();
    }
    
    public List<RolEntity> listarRolesNoAsociados(Long idDocumento) {
       return rolDao.listarRolesNoAsociados(idDocumento);
    }

    public List<RolEntity> listarRoles(Integer codigo,
            String nombre,
            Integer estado,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ) {
        return rolDao.listarRoles(codigo, nombre, estado, pPrimerRegistro, pUltimoRegistro);
    }

    public Long contarRoles(Integer codigo,
            String nombre,
            Integer estado
    ) {
        return rolDao.contarRoles(codigo, nombre, estado);
    }
    
    public Long contarRolesValidator(Long idRolDiferente, String codigo, String nombre){
        return rolDao.contarRolesValidator(idRolDiferente, codigo, nombre);
    }
    
    public Long verificaRolUso(Long idRol) {
        return rolDao.verificaRolUso(idRol);
    }
}
