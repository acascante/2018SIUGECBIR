/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.sigebi.daos.FaltaJustificacionDao;
import cr.ac.ucr.sigebi.entities.JustificacionEntity;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "justificacionModel")
@Scope("request")
public class JustificacionModel {
    
    @Resource
    FaltaJustificacionDao justificacionDao;
    
    public void guardar(JustificacionEntity valor){
        justificacionDao.guardar(valor);
    }
    
    public JustificacionEntity traerPorId(Integer idJustificacion) {
        return justificacionDao.traerPorId(idJustificacion);
    }
    
    public List<JustificacionEntity> listarPorDocumento(String documentoTipo, Long idDocumento) {
        return justificacionDao.listarPorDocumento(documentoTipo, idDocumento);
    }
}
