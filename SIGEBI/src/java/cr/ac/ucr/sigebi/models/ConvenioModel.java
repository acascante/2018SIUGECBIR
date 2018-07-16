/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.ConvenioDao;
import cr.ac.ucr.sigebi.domain.Convenio;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author alvaro.cascante
 */
@Service("convenioModel")
public class ConvenioModel {

    @Resource
    private ConvenioDao convenioDao;

    public List<Convenio> listar() throws FWExcepcion {
        return convenioDao.listar();
    }

    public Convenio buscarPorId(Long id) throws FWExcepcion {
        return convenioDao.buscarPorId(id);
    }
    
    public List<Convenio> listar(Integer primerRegistro, Integer ultimoRegistro, Long id, String institucion, String responsable, String oficio, Long idEstado) throws FWExcepcion {
        return convenioDao.listar(primerRegistro, ultimoRegistro, id, institucion, responsable, oficio, idEstado);
    }
    
    public List<Convenio> listarActivos(UnidadEjecutora unidadEjecutora, Date fecha) throws FWExcepcion {
        return convenioDao.listarActivos(unidadEjecutora, fecha);
    }
    
    public List<Convenio> listarActivosRecibir(UnidadEjecutora unidadEjecutora) throws FWExcepcion {
        return convenioDao.listarActivosRecibir(unidadEjecutora, new Date());
    }
    
    public void salvar(Convenio convenio) throws FWExcepcion {
        convenioDao.salvar(convenio);
    }

    public Long contar(Long id, String institucion, String responsable, String oficio, Long idEstado) throws FWExcepcion {
        return convenioDao.contar(id, institucion, responsable, oficio, idEstado);
    }
}