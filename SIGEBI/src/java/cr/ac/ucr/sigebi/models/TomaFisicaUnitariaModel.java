/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.TomaFisicaUnitariaDao;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.TomaFisica;
import cr.ac.ucr.sigebi.domain.TomaFisicaUnitaria;
import java.util.List;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "tomaFisicaUnitariaModel")

public class TomaFisicaUnitariaModel {

    @Resource
    private TomaFisicaUnitariaDao tomaFisicaUnitariaDao;

    public void agregar(TomaFisicaUnitaria tomaFisicaUnitaria) throws FWExcepcion {
        tomaFisicaUnitariaDao.agregar(tomaFisicaUnitaria);
    }

    public void modificar(TomaFisicaUnitaria tomaFisicaUnitaria) throws FWExcepcion {
        tomaFisicaUnitariaDao.modificar(tomaFisicaUnitaria);
    }

    public TomaFisicaUnitaria buscarPorBien(TomaFisica tomaFisica, Bien bien) throws FWExcepcion {
        return tomaFisicaUnitariaDao.buscarPorBien(tomaFisica, bien);
    }

    public void eliminar(TomaFisicaUnitaria tomaFisicaUnitaria) throws FWExcepcion {
        tomaFisicaUnitariaDao.eliminar(tomaFisicaUnitaria);
    }

    public List<TomaFisicaUnitaria> listar(String id,
            String descripcion,
            String identificacion,
            TomaFisica tomaFisica,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ) throws FWExcepcion {
        return tomaFisicaUnitariaDao.listar(id, descripcion, identificacion, tomaFisica, pPrimerRegistro, pUltimoRegistro);
    }

    public Long contar(String id,
            String descripcion,
            String identificacion,
            TomaFisica tomaFisica
    ) throws FWExcepcion {
        return tomaFisicaUnitariaDao.contar(id, descripcion, identificacion, tomaFisica);
    }
}
