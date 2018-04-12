/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.TomaFisicaLoteDao;
import cr.ac.ucr.sigebi.domain.Lote;
import cr.ac.ucr.sigebi.domain.TomaFisica;
import cr.ac.ucr.sigebi.domain.TomaFisicaLote;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "tomaFisicaLoteModel")

public class TomaFisicaLoteModel {

    @Resource
    private TomaFisicaLoteDao tomaFisicaLoteDao;

    public void agregar(TomaFisicaLote tomaFisicaLote) throws FWExcepcion {
        tomaFisicaLoteDao.agregar(tomaFisicaLote);
    }

    public void modificar(TomaFisicaLote tomaFisicaLote) throws FWExcepcion {
        tomaFisicaLoteDao.modificar(tomaFisicaLote);
    }

    public void eliminar(TomaFisicaLote obj) throws FWExcepcion {
        tomaFisicaLoteDao.eliminar(obj);
    }
    
    public TomaFisicaLote buscarPorLote(TomaFisica tomaFisica, Lote lote) throws FWExcepcion {
        return tomaFisicaLoteDao.buscarPorLote(tomaFisica, lote);
    }
    
    public TomaFisicaLote buscarPorId(Long id) throws FWExcepcion {
        return tomaFisicaLoteDao.buscarPorId(id);
    }

    public List<TomaFisicaLote> listar(String id,
            String lote,
            TomaFisica tomaFisica,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ) throws FWExcepcion {
        return tomaFisicaLoteDao.listar(id, lote, tomaFisica, pPrimerRegistro, pUltimoRegistro);
    }

    public Long contar(String id,
            String lote,
            TomaFisica tomaFisica
    ) throws FWExcepcion {
        return tomaFisicaLoteDao.contar(id, lote, tomaFisica);
    }

}
