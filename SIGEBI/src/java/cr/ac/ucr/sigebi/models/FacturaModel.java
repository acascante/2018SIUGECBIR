/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.FacturaDao;
import cr.ac.ucr.sigebi.domain.Factura;
import cr.ac.ucr.sigebi.domain.SolicitudDonacion;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "facturaModel")

public class FacturaModel {

    @Resource
    private FacturaDao facturaDao;

    public Factura buscarPorId(Long pId) throws FWExcepcion {
        return facturaDao.buscarPorId(pId);
    }

    public List<Factura> listar(SolicitudDonacion solicitudDonacion) throws FWExcepcion {
        return facturaDao.listar(solicitudDonacion);
    }

    public void guardar(Factura factura) throws FWExcepcion {
        facturaDao.agregar(factura);
    }

    public void eliminar(Factura factura) throws FWExcepcion {
        facturaDao.eliminar(factura);
    }
}
