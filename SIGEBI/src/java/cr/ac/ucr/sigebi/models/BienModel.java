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
import cr.ac.ucr.sigebi.domain.RegistroMovimiento;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
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

    public List<Bien> listar(Integer primerRegistro, Integer ultimoRegistro, UnidadEjecutora unidadejecutora, Integer id, String identificacion, String descripcion, String marca, String modelo, String serie, Estado... estados) throws FWExcepcion {
        return bienDao.listar(primerRegistro, ultimoRegistro, unidadejecutora, identificacion, descripcion, marca, modelo, serie, estados);
    }

    public Long contar(UnidadEjecutora unidadejecutora, Integer id, String identificacion, String descripcion, String marca, String modelo, String serie, Estado... estados) throws FWExcepcion {
        return bienDao.contar(unidadejecutora, identificacion, descripcion, marca, modelo, serie, estados);
    }

    public List<Bien> listar(Integer primerRegistro, Integer ultimoRegistro, UnidadEjecutora unidadejecutora, Integer id, String identificacion, String descripcion, String marca, String modelo, String serie, Estado estado) throws FWExcepcion {
        return bienDao.listar(primerRegistro, ultimoRegistro, unidadejecutora, identificacion, descripcion, marca, modelo, serie, estados);
    }

    public Long contar(UnidadEjecutora unidadejecutora, Integer id, String identificacion, String descripcion, String marca, String modelo, String serie, Estado estado) throws FWExcepcion {
        return bienDao.contar(unidadejecutora, identificacion, descripcion, marca, modelo, serie, estados);
    }

    public void almacenar(Bien bien) throws FWExcepcion {
        bienDao.almacenar(bien);
    }

    public void actualizar(Bien bien) throws FWExcepcion {
        bienDao.actualizar(bien);
    }

    public void cambiaEstadoBien(Collection<Bien> bienes, Estado estado, String observacion, Integer telefono) {
        for (Bien bien : bienes) {
            this.cambiaEstadoBien(bien, estado, observacion, telefono);
        }
    }

    public void cambiaEstadoBien(Bien bien, Estado estado, String observacion, Integer telefono) {
        //Se registra el movimiento
        RegistroMovimiento regisMov = new RegistroMovimiento();
        regisMov.setBien(bien);
        regisMov.setFecha(new Date());
        regisMov.setEstado(estado);
        regisMov.setTipo(bien.getTipoBien());
        regisMov.setObservacion(observacion);
        regisMov.setNumeroPersona(telefono);
        registroMovimientoModel.agregar(regisMov);

        //Se actualiza el estado del bien
        bien.setEstado(estado);
        bien.setSeleccionado(false);
        this.actualizar(bien);
    }
}
