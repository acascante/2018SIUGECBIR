/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.IdentificacionDao;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Identificacion;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author alvaro.cascante
 */
@Service(value = "identificacionModel")
@Scope("request")
public class IdentificacionModel {
    
    @Resource
    private IdentificacionDao identificacionDao;
    
    public Identificacion siguienteDisponible(Estado estado) throws FWExcepcion {
        return identificacionDao.siguienteDisponible(estado);
    }

    public void actualizar(Identificacion identificacion) {
        identificacionDao.actualizar(identificacion);
    }
}