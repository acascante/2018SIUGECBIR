/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.InterfazBienDao;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.InterfazAccesorio;
import cr.ac.ucr.sigebi.domain.InterfazAdjunto;
import cr.ac.ucr.sigebi.domain.InterfazBien;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "interfazBienModel")

public class InterfazBienModel {
    
    @Resource
    private InterfazBienDao interfazBienDao;
    
    public void modificar(InterfazBien interfazBien) throws FWExcepcion {
        interfazBienDao.modificar(interfazBien);
    }
    
    public InterfazBien buscarPorId(Long id) throws FWExcepcion {
        return interfazBienDao.buscarPorId(id);
    }
    
    public List<InterfazBien> listar(String id,
            String marca,
            String modelo,
            String serie,
            String descripcion,
            String unidadEjecutora,
            Estado estado,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro, String fltIdentificacionBien) throws FWExcepcion {
        return interfazBienDao.listar(id, marca, modelo, serie, descripcion, unidadEjecutora, estado, pPrimerRegistro, pUltimoRegistro, fltIdentificacionBien);
    }
    
    public Long contar(String id,
            String marca,
            String modelo,
            String serie,
            String descripcion,
            String unidadEjecutora,
            Estado estado, String fltIdentificacionBien) throws FWExcepcion {
        
        return interfazBienDao.contar(id, marca, modelo, serie, descripcion, unidadEjecutora, estado, fltIdentificacionBien);
    }
    
    public List<InterfazAdjunto> listarInterfazAdjuntos(String identificacionOrigen, Integer idOrigenTecnico) throws FWExcepcion {
        return interfazBienDao.listarInterfazAdjuntos(identificacionOrigen, idOrigenTecnico);
    }
    
    public List<InterfazAccesorio> listarInterfazAccesorios(String identificacionOrigen, Integer idOrigenTecnico) throws FWExcepcion {
        return interfazBienDao.listarInterfazAccesorios(identificacionOrigen, idOrigenTecnico);
    }
    
}
