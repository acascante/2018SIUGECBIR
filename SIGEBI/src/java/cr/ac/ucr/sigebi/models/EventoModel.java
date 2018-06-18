/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.EventoDao;
import cr.ac.ucr.sigebi.domain.Evento;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author alvaro.cascante
 */
@Service("eventoModel")
public class EventoModel {

    @Resource
    private EventoDao eventoDao;
    
    public List<Evento> listar() throws FWExcepcion {
        return eventoDao.listar();
    }

    public void salvar(Evento evento) throws FWExcepcion {
        eventoDao.salvar(evento);
    }

    public Double totalCosto(Long idDetalle) throws FWExcepcion {
        return eventoDao.totalCosto(idDetalle);
    }
    
    public Long contar(Long idBien) throws FWExcepcion {
        return eventoDao.contar(idBien);
    }
    
    public List<Evento> listar(Integer primerRegistro, Integer ultimoRegistro, Long idBien) throws FWExcepcion {
        return eventoDao.listar(primerRegistro, ultimoRegistro, idBien);
    }
    
    public Long contar(Long id, String descripcion, Date fecha, Double costo) throws FWExcepcion {
        return eventoDao.contar(id, descripcion, fecha, costo);
    }

    public List<Evento> listar(Integer primerRegistro, Integer ultimoRegistro, Long id, String descripcion, Date fecha, Double costo) throws FWExcepcion {
        return eventoDao.listar(primerRegistro, ultimoRegistro, id, descripcion, fecha, costo);
    }
}