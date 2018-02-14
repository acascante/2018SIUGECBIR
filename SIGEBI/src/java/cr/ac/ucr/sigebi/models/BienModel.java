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
import cr.ac.ucr.sigebi.domain.Sincronizar;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.domain.Usuario;
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

    public List<Bien> listar(Integer primerRegistro
                            , Integer ultimoRegistro
                            , UnidadEjecutora unidadejecutora
                            , Long id
                            , String identificacion
                            , String descripcion
                            , String marca
                            , String modelo
                            , String serie
                            , Tipo tipo
                            , String nombUnidad
                            , Estado... estados) throws FWExcepcion {
        return bienDao.listar(primerRegistro
                            , ultimoRegistro
                            , unidadejecutora
                            , id
                            , identificacion
                            , descripcion
                            , marca
                            , modelo
                            , serie
                            , tipo
                            , nombUnidad
                            , estados);
    }

    public Long contar(UnidadEjecutora unidadejecutora
                        , Long id
                        , String identificacion
                        , String descripcion
                        , String marca
                        , String modelo
                        , String serie
                        , Tipo tipo
                        , String nombUnidad
                        , Estado... estados) throws FWExcepcion {
        return bienDao.contar(unidadejecutora
                            , id
                            , identificacion
                            , descripcion
                            , marca
                            , modelo
                            , serie
                            , tipo
                            , nombUnidad
                            , estados);
    }

    public void almacenar(Bien bien) throws FWExcepcion {
        bienDao.almacenar(bien);
    }

    public void actualizar(Bien bien) throws FWExcepcion {
        bienDao.actualizar(bien);
    }

    public void cambiaEstadoBien(Collection<Bien> bienes, Estado estado, String observacion, Integer telefono, Usuario usuario) {
        for (Bien bien : bienes) {
            this.cambiaEstadoBien(bien, estado, observacion, telefono, usuario);
        }
    }

    public void cambiaEstadoBien(Bien bien, Estado estado, String observacion, Integer telefono, Usuario usuario) {
        //Se registra el movimiento
        RegistroMovimiento regisMov = new RegistroMovimiento();
        regisMov.setBien(bien);
        regisMov.setFecha(new Date());
        regisMov.setEstado(estado);
        regisMov.setTipo(bien.getTipo());
        regisMov.setObservacion(observacion);
        regisMov.setUsuario(usuario);
        registroMovimientoModel.agregar(regisMov);
        
        //Se actualiza el estado del bien
        bien.setEstado(estado);
        bien.setSeleccionado(false);
        this.actualizar(bien);
    }

    public void sincronizarBien(Bien bien, String usaurioSincro) throws FWExcepcion, Exception {

        //Se almacena la sincronizacion
        Sincronizar bienSinc = new Sincronizar(bien);
        Date today = new Date();
        bienSinc.setFechaAdicion(today);
        bienSinc.setAdicionadoPor(usaurioSincro);
        bienDao.sincronizarBien(bienSinc);
        
        //Se actualiza el bien para cambiar su estado
        this.actualizar(bien);
    }
}
