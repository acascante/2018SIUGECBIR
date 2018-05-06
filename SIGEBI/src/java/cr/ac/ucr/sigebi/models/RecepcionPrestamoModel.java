/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.RecepcionPrestamoDao;
import cr.ac.ucr.sigebi.domain.Convenio;
import cr.ac.ucr.sigebi.domain.RecepcionPrestamo;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author alvaro.cascante
 */
@Service("recepcionPrestamoModel")
public class RecepcionPrestamoModel {

    @Resource
    private RecepcionPrestamoDao recepcionPrestamoDao;

    public List<RecepcionPrestamo> listar() throws FWExcepcion {
        return recepcionPrestamoDao.listar();
    }

    public RecepcionPrestamo buscarPorId(Long id) throws FWExcepcion {
        return recepcionPrestamoDao.buscarPorId(id);
    }
    
    public void salvar(RecepcionPrestamo recepcionPrestamo) throws FWExcepcion {
        recepcionPrestamoDao.salvar(recepcionPrestamo);
    }
    
    public Long contar(Long id, Long convenio, String descripcion, String identificacion, Date fechaIngreso, Date fechaDevolucion, Long estado) throws FWExcepcion {
        return recepcionPrestamoDao.contar(id, convenio, descripcion, identificacion, fechaIngreso, fechaDevolucion, estado);
    }
    
    public List<RecepcionPrestamo> listar(Integer primerRegistro, Integer ultimoRegistro, Long id, Long convenio, String descripcion, String identificacion, Date fechaIngreso, Date fechaDevolucion, Long estado) throws FWExcepcion {
        return recepcionPrestamoDao.listar(primerRegistro, ultimoRegistro, id, convenio, descripcion, identificacion, fechaIngreso, fechaDevolucion, estado);
    }
}