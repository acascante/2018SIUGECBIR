/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.BienDao;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Identificacion;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.entities.BienEntity;
import cr.ac.ucr.sigebi.entities.EstadoEntity;
import cr.ac.ucr.sigebi.entities.LoteEntity;
import cr.ac.ucr.sigebi.entities.RegistroMovimientoEntity;
import cr.ac.ucr.sigebi.entities.UbicacionBien;
import cr.ac.ucr.sigebi.entities.ViewBienEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "bienModel")
@Scope("request")
public class BienModel {
    
    @Resource
    private BienDao bienDao;
    
    @Resource
    RegistroMovimientoModel registroMovimientoModel;
    
    public List<Bien> listar() throws FWExcepcion {
        return bienDao.listar();
    }
    
    public List<Bien> listarPorUnidadEjecutora(UnidadEjecutora unidadejecutora) throws FWExcepcion {
        return bienDao.listarPorUnidadEjecutora(unidadejecutora);
    }
    
    public List<Bien> listarPorUnidadEjecutoraEstado(UnidadEjecutora unidadejecutora, Estado estado) throws FWExcepcion {
        return bienDao.listarPorUnidadEjecutoraEstado(unidadejecutora, estado);
    }
    
    public Bien buscarPorId(Long id) throws FWExcepcion {
        return bienDao.buscarPorId(id);
    }
    
    public List<Bien> listar(Integer primerRegistro, Integer ultimoRegistro, UnidadEjecutora unidadejecutora, Identificacion identificacion, String descripcion, String marca, String modelo, String serie, Estado... estados) throws FWExcepcion {
        return bienDao.listar(primerRegistro, ultimoRegistro, unidadejecutora, identificacion, descripcion, marca, modelo, serie, estados);
    }
    
    public Long contar(UnidadEjecutora unidadejecutora, Identificacion identificacion, String descripcion, String marca, String modelo, String serie, Estado... estados) throws FWExcepcion {
        return bienDao.contar(unidadejecutora, identificacion, descripcion, marca, modelo, serie, estados);
    }
    
    public void almacenar(Bien bien) throws FWExcepcion {
        bienDao.almacenar(bien);
    }
    
    public void actualizar(BienEntity bien) throws FWExcepcion {
        bienDao.actualizar(bien);
    }
}