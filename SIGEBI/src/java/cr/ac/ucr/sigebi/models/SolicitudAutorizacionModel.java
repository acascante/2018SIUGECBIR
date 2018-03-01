/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.SolicitudAutorizacionDao;
import cr.ac.ucr.sigebi.domain.AutorizacionRol;
import cr.ac.ucr.sigebi.domain.Solicitud;
import cr.ac.ucr.sigebi.domain.SolicitudAutorizacion;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "solicitudAutorizacionModel")

public class SolicitudAutorizacionModel {

    @Resource
    private SolicitudAutorizacionDao solicitudAutorizacionDao;

    public void agregar(SolicitudAutorizacion obj) throws FWExcepcion {
        solicitudAutorizacionDao.agregar(obj);
    }
    
    public void modificar(SolicitudAutorizacion obj) throws FWExcepcion {
        solicitudAutorizacionDao.modificar(obj);
    }

    public SolicitudAutorizacion buscar(AutorizacionRol autorizacionRol, Solicitud solicitud) throws FWExcepcion {
        return solicitudAutorizacionDao.buscar(autorizacionRol, solicitud);
    }

}
