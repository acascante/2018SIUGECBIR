/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.TomaFisicaDao;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.TomaFisica;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "tomaFisicaModel")

public class TomaFisicaModel {

    @Resource
    private TomaFisicaDao tomaFisicaDao;

    public void agregar(TomaFisica tomaFisica) throws FWExcepcion {
        tomaFisicaDao.agregar(tomaFisica);
    }

    public void modificar(TomaFisica tomaFisica) throws FWExcepcion {
        tomaFisicaDao.modificar(tomaFisica);
    }

    public TomaFisica buscarPorId(Long id) throws FWExcepcion {
        return tomaFisicaDao.buscarPorId(id);
    }

    public List<TomaFisica> listar(String id,
            Tipo tipo,
            String ubicacion,
            String descripcion,
            Tipo tipoMotivo,
            Estado estado,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ) throws FWExcepcion {
        return tomaFisicaDao.listar(id, tipo, ubicacion, descripcion, tipoMotivo, estado, pPrimerRegistro, pUltimoRegistro);
    }

    public Long contar(String id,
            Tipo tipo,
            String ubicacion,
            String descripcion,
            Tipo tipoMotivo,
            Estado estado
    ) throws FWExcepcion {
        return tomaFisicaDao.contar(id, tipo, ubicacion, descripcion, tipoMotivo, estado);
    }

}
