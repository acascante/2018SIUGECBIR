/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.AccesorioDao;
import cr.ac.ucr.sigebi.domain.Accesorio;
import cr.ac.ucr.sigebi.domain.Bien;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "AccesorioModel")
@Scope("request")
public class AccesorioModel {
    
    @Resource
    private AccesorioDao accesorioDao;
    
    public List<Accesorio> listar() throws FWExcepcion {
        return accesorioDao.listar();
    }
    
    public List<Accesorio> listarPorBien(Bien bien) throws FWExcepcion {
        return accesorioDao.listarPorBien(bien);
    }
    
    public Accesorio buscarPorId(Long id) throws FWExcepcion {
        return accesorioDao.buscarPorId(id);
    }
    
    public void almacenar(Accesorio accesorio) throws FWExcepcion {
        accesorioDao.almacenar(accesorio);
    }

    public void eliminar(Accesorio accesorio)throws FWExcepcion {
        accesorioDao.eliminar(accesorio);
    }
}
