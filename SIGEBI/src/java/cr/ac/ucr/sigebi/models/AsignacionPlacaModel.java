/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.AsignacionPlacaDao;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.AsignacionPlaca;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "asignacionPlacaModel")

public class AsignacionPlacaModel {

    @Resource
    private AsignacionPlacaDao asignacionPlacaDao;

    public void agregar(AsignacionPlaca asignacionPlaca) throws FWExcepcion {
        asignacionPlacaDao.agregar(asignacionPlaca);
    }

    public void modificar(AsignacionPlaca asignacionPlaca) throws FWExcepcion {
        asignacionPlacaDao.modificar(asignacionPlaca);
    }

    public AsignacionPlaca buscarPorId(Long id) throws FWExcepcion {
        return asignacionPlacaDao.buscarPorId(id);
    }

    public List<AsignacionPlaca> listar(String id, UnidadEjecutora unidadEjecutora, Estado estado, Integer pPrimerRegistro,Integer pUltimoRegistro) throws FWExcepcion {
        return asignacionPlacaDao.listar(id, unidadEjecutora, estado, pPrimerRegistro, pUltimoRegistro);
    }

    @Transactional(readOnly = true)
    public Long contar(String id, UnidadEjecutora unidadEjecutora,Estado estado) throws FWExcepcion {
        return asignacionPlacaDao.contar(id, unidadEjecutora, estado);
    }

}
