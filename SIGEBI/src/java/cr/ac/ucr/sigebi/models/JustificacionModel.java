/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.sigebi.daos.JustificacionDao;
import cr.ac.ucr.sigebi.domain.Justificacion;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "justificacionModel")

public class JustificacionModel {
    
    @Resource
    JustificacionDao justificacionDao;
    
    public void guardar(Justificacion valor){
        justificacionDao.guardar(valor);
    }
    
    public Justificacion traerPorId(Integer idJustificacion) {
        return justificacionDao.traerPorId(idJustificacion);
    }
    
    public List<Justificacion> listarPorDocumento(String documentoTipo, Long idDocumento) {
        return justificacionDao.listarPorDocumento(documentoTipo, idDocumento);
    }
}
