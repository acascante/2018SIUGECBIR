/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.BienDao;
import cr.ac.ucr.sigebi.domain.AsignacionPlaca;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.RegistroMovimientoBien;
import cr.ac.ucr.sigebi.domain.Sincronizar;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.domain.Usuario;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "bienModel")

public class BienModel {

    @Resource
    private BienDao bienDao;

    @Resource
    RegistroMovimientoModel registroMovimientoModel;

    @Resource
    TipoModel tipoModel;

    public List<Bien> listar() throws FWExcepcion {
        return bienDao.listar();
    }
    
    public List<Bien> listarPorResponsable(String usuarioResponsable, String identificacionBien, String orden, String orden1, String orden2, String orden3) throws FWExcepcion {
        return bienDao.listarPorResponsable(usuarioResponsable, identificacionBien, orden, orden1, orden2, orden3);
    }
    
    public List<Bien> listar(String sql, Map<String, Object> parametros) throws FWExcepcion {
        return bienDao.listar(sql, parametros);
    }

    public List<Bien> listarPorUnidadEjecutora(UnidadEjecutora unidadejecutora) throws FWExcepcion {
        return bienDao.listarPorUnidadEjecutora(unidadejecutora);
    }

    public List<Bien> listarPorAsignacionPlaca(AsignacionPlaca asignacionPlaca) throws FWExcepcion {
        return bienDao.listarPorAsignacionPlaca(asignacionPlaca);
    }

    public List<Bien> listarPorUnidadEjecutoraEstado(UnidadEjecutora unidadejecutora, Estado estado) throws FWExcepcion {
        return bienDao.listarPorUnidadEjecutoraEstado(unidadejecutora, estado);
    }

    public List<Bien> listarPorUnidadEjecutoraEstado(Integer primerRegistro, Integer ultimoRegistro, UnidadEjecutora unidadEjecutora, Estado estado, String identificacion,
            String descripcion, String marca, String modelo, String serie) throws FWExcepcion {
        return bienDao.listarPorUnidadEjecutoraEstado(unidadEjecutora, estado);
    }

    public Bien buscarPorId(Long id) throws FWExcepcion {
        return bienDao.buscarPorId(id);
    }

    public Bien buscarPorIdentificacion(String identificacion) throws FWExcepcion {
        return bienDao.buscarPorIdentificacion(identificacion);
    }

    public List<Bien> listar(Integer primerRegistro,
            Integer ultimoRegistro,
            UnidadEjecutora unidadejecutora,
            Long id,
            String identificacion,
            String descripcion,
            String marca,
            String modelo,
            String serie,
            Tipo tipo,
            String nombUnidad,
            Estado... estados) throws FWExcepcion {
        return bienDao.listar(primerRegistro,
                ultimoRegistro,
                unidadejecutora,
                id,
                identificacion,
                descripcion,
                marca,
                modelo,
                serie,
                tipo,
                nombUnidad,
                estados);
    }

    public Long contar(UnidadEjecutora unidadejecutora,
            Long id,
            String identificacion,
            String descripcion,
            String marca,
            String modelo,
            String serie,
            Tipo tipo,
            String nombUnidad,
            Estado... estados) throws FWExcepcion {
        return bienDao.contar(unidadejecutora,
                id,
                identificacion,
                descripcion,
                marca,
                modelo,
                serie,
                tipo,
                nombUnidad,
                estados);
    }

    public List<Bien> listadoActas(Integer primerRegistro,
            Integer ultimoRegistro,
            UnidadEjecutora unidadejecutora,
            Long id,
            String identificacion,
            String descripcion,
            String marca,
            String modelo,
            String serie,
            Tipo tipo,
            Estado... estados) throws FWExcepcion {
        return bienDao.listadoActas(primerRegistro,
                ultimoRegistro,
                unidadejecutora,
                id,
                identificacion,
                descripcion,
                marca,
                modelo,
                serie,
                tipo,
                estados);
    }

    public Long contarListadoActas(UnidadEjecutora unidadejecutora,
            Long id,
            String identificacion,
            String descripcion,
            String marca,
            String modelo,
            String serie,
            Tipo tipo,
            Estado... estados) throws FWExcepcion {
        return bienDao.contarListadoActas(unidadejecutora,
                id,
                identificacion,
                descripcion,
                marca,
                modelo,
                serie,
                tipo,
                estados);
    }

    public void almacenar(Bien bien) throws FWExcepcion {
        bienDao.almacenar(bien);
    }

    public void actualizar(Bien bien) throws FWExcepcion {
        bienDao.actualizar(bien);
    }

    public void eliminar(Bien bien) throws FWExcepcion {
        bienDao.eliminar(bien);
    }

    public void cambiaEstadoBien(Collection<Bien> bienes, Estado estado, String observacion, Integer telefono, Usuario usuario, Tipo tipoMovimiento) {
        for (Bien bien : bienes) {
            this.cambiaEstadoBien(bien, estado, observacion, telefono, usuario, tipoMovimiento);
        }
    }

    public void cambiaEstadoBien(Bien bien, Estado estado, String observacion, Integer telefono, Usuario usuario, Tipo tipoMovimiento) {

        //Se registra el movimiento
        RegistroMovimientoBien regisMov = new RegistroMovimientoBien(tipoMovimiento, observacion, telefono, new Date(), usuario, estado, bien);
        registroMovimientoModel.agregar(regisMov);

        //Se actualiza el estado del bien
        bien.setEstado(estado);
        bien.setSeleccionado(false);
        this.actualizar(bien);
    }

    public void sincronizarBien(Collection<Bien> bienes,
             String observacion,
             Integer telefono,
             Usuario usuario,
             Estado estado
    ) throws FWExcepcion, Exception {

        for (Bien bien : bienes) {
            Sincronizar bienSinc;
            String tipoMovimiento = Constantes.TIPO_MOVIMIENTO_SINCRONIZAR_MODIFICAR;
            //Se almacena el tipo de la sincronizacion
            if (bien.getEstado().getId().intValue() == Constantes.ESTADO_BIEN_PENDIENTE_SINCRONIZAR) {
                tipoMovimiento = Constantes.TIPO_MOVIMIENTO_SINCRONIZAR_AGREGAR;
            }
            if (bien.getEstado().getId().intValue() == Constantes.ESTADO_BIEN_TRASLADO) {
                tipoMovimiento = Constantes.TIPO_MOVIMIENTO_SINCRONIZAR_TRASLADAR;
            }
            if (bien.getEstado().getId().intValue() == Constantes.ESTADO_BIEN_EXCLUSION_APROBADA) {
                tipoMovimiento = Constantes.TIPO_MOVIMIENTO_SINCRONIZAR_EXCLUIR;
            }

            bienSinc = new Sincronizar(bien, tipoMovimiento);
            Date today = new Date();
            bienSinc.setFechaAdicion(today);
            bienSinc.setAdicionadoPor(usuario.getId());
            bienDao.sincronizarBien(bienSinc);

            //Se actualiza el bien y cambia su estado
            Tipo tipoMovimento = tipoModel.buscarPorDominioTipo(Constantes.DOMINIO_REGISTRO_MOVIMIENTO, Constantes.TIPO_REGISTRO_MOVIMIENTO_CAMBIO_ESTADO_BIEN);
            this.cambiaEstadoBien(bien, estado, observacion, telefono, usuario, tipoMovimento);
        }
    }

    public void actualizar(List<Bien> bienes) throws FWExcepcion, Exception {
        bienDao.actualizar(bienes);
    }

    public List<Bien> listarAsignar(Integer primerRegistro,
             Integer ultimoRegistro,
             Long id,
             UnidadEjecutora unidadejecutora,
             String identificacion,
             String descripcion,
             String marca,
             String modelo,
             String serie,
             Estado estado,
             Estado estadoInterno,
             Boolean listadoAsignar
    ) throws FWExcepcion {
        return bienDao.listar(primerRegistro,
                 ultimoRegistro,
                 id,
                 unidadejecutora,
                 identificacion,
                 descripcion,
                 marca,
                 modelo,
                 serie,
                 estado,
                 estadoInterno,
                 listadoAsignar
        );
    }

    public Long contarAsignar(Long id,
             UnidadEjecutora unidadejecutora,
             String identificacion,
             String descripcion,
             String marca,
             String modelo,
             String serie,
             Estado estado,
             Estado estadoInterno,
             Boolean listadoAsignar
    ) throws FWExcepcion {
        return bienDao.contar(id,
                 unidadejecutora,
                 identificacion,
                 descripcion,
                 marca,
                 modelo,
                 serie,
                 estado,
                 estadoInterno,
                 listadoAsignar
        );
    }

    public List<Bien> listar(Integer primerRegistro,
             Integer ultimoRegistro,
             Long id,
             UnidadEjecutora unidadejecutora,
             String identificacion,
             String descripcion,
             String marca,
             String modelo,
             String serie,
             Estado estado,
             Estado estadoInterno
    ) throws FWExcepcion {
        return bienDao.listar(primerRegistro,
                 ultimoRegistro,
                 id,
                 unidadejecutora,
                 identificacion,
                 descripcion,
                 marca,
                 modelo,
                 serie,
                 estado,
                 estadoInterno,
                 false
        );
    }

    public Long contar(Long id,
             UnidadEjecutora unidadejecutora,
             String identificacion,
             String descripcion,
             String marca,
             String modelo,
             String serie,
             Estado estado,
             Estado estadoInterno
    ) throws FWExcepcion {
        return bienDao.contar(id,
                 unidadejecutora,
                 identificacion,
                 descripcion,
                 marca,
                 modelo,
                 serie,
                 estado,
                 estadoInterno,
                 false
        );
    }

    public List<Bien> listarMisBienes(Integer primerRegistro,
             Integer ultimoRegistro,
             UnidadEjecutora unidadejecutora,
             String identificacion,
             String descripcion,
             String marca,
             String modelo,
             String serie,
             Estado estadoAsignacion,
             Estado estadoBien,
             Usuario usuario) throws FWExcepcion {
        return bienDao.listarMisBienes(primerRegistro,
                 ultimoRegistro,
                 unidadejecutora,
                 identificacion,
                 descripcion,
                 marca,
                 modelo,
                 serie,
                 estadoAsignacion,
                 estadoBien,
                 usuario);
    }

    public Long contarMisBienes(UnidadEjecutora unidadejecutora,
             String identificacion,
             String descripcion,
             String marca,
             String modelo,
             String serie,
             Estado estadoAsignacion,
             Estado estadoBien,
             Usuario usuario) throws FWExcepcion {
        return bienDao.contarMisBienes(unidadejecutora, identificacion, descripcion, marca, modelo, serie, estadoAsignacion, estadoBien, usuario);
    }

    public List<Bien> listarReporteSobrantes(String identificacion, String descripcion, String marca, String modelo, String serie, String usuario, Estado estado) throws FWExcepcion {
        return bienDao.listarReporteSobrantes(identificacion, descripcion, marca, modelo, serie, usuario, estado);
    }
}
