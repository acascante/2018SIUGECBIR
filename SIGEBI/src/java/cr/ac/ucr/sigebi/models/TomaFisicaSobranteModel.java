/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.TomaFisicaSobranteDao;
import cr.ac.ucr.sigebi.domain.TomaFisica;
import cr.ac.ucr.sigebi.domain.TomaFisicaSobrante;
import java.util.List;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "tomaFisicaSobranteModel")

public class TomaFisicaSobranteModel {

    @Resource
    private TomaFisicaSobranteDao tomaFisicaSobranteDao;

    public void agregar(TomaFisicaSobrante tomaFisicaSobrante) throws FWExcepcion {
        tomaFisicaSobranteDao.agregar(tomaFisicaSobrante);
    }

    public void modificar(TomaFisicaSobrante tomaFisicaSobrante) throws FWExcepcion {
        tomaFisicaSobranteDao.modificar(tomaFisicaSobrante);
    }

    public void eliminar(TomaFisicaSobrante obj) throws FWExcepcion {
        tomaFisicaSobranteDao.eliminar(obj);
    }
    
    public TomaFisicaSobrante buscarPorId(Long id) throws FWExcepcion {
        return tomaFisicaSobranteDao.buscarPorId(id);
    }

    public TomaFisicaSobrante buscarPorIdentificacion(TomaFisica tomaFisica, String identificacion) throws FWExcepcion{
        return tomaFisicaSobranteDao.buscarPorIdentificacion(tomaFisica, identificacion);
    }
            
    public List<TomaFisicaSobrante> listar(String id,
            TomaFisica tomaFisica,
            String identificacion,
            String descripcion,
            String serie,
            String marca,
            String modelo,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ) throws FWExcepcion {
        return tomaFisicaSobranteDao.listar(id, tomaFisica, identificacion, descripcion, serie, marca, modelo, pPrimerRegistro, pUltimoRegistro);
    }

    public Long contar(String id,
            TomaFisica tomaFisica,
            String identificacion,
            String descripcion,
            String serie,
            String marca,
            String modelo
    ) throws FWExcepcion {
        return tomaFisicaSobranteDao.contar(id, tomaFisica, identificacion, descripcion, serie, marca, modelo);
    }
    
    public List<TomaFisicaSobrante> listarReporte(TomaFisica tomaFisica, String identificacion, String ubicacion, String descripcion, String serie, String marca, String modelo, String orden, String orden1, String orden2, String orden3) throws FWExcepcion {
        return tomaFisicaSobranteDao.listarReporte(tomaFisica, identificacion, ubicacion, descripcion, serie, marca, modelo, orden, orden1, orden2, orden3);
    }
}
