/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.SolicitudDao;
import cr.ac.ucr.sigebi.daos.UnidadEjecutoraDao;
import cr.ac.ucr.sigebi.domain.AutorizacionRol;
import cr.ac.ucr.sigebi.domain.AutorizacionRolPersona;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Solicitud;
import cr.ac.ucr.sigebi.domain.SolicitudAutorizacion;
import cr.ac.ucr.sigebi.domain.SolicitudDetalle;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.SolicitudSalida;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.domain.Usuario;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "solicitudModel")

public class SolicitudModel {

    @Resource
    private SolicitudDao solicitudDao;

    @Resource
    private EstadoModel estadoModel;

    @Resource
    private AutorizacionRolModel autorizacionRolModel;

    @Resource
    private AutorizacionRolPersonaModel autorizacionRolPersonaModel;

    @Resource
    private SolicitudAutorizacionModel solicitudAutorizacionModel;

    @Resource
    private UnidadEjecutoraDao unidadEjecutoraDao;

    public void agregar(Solicitud solicitud) throws FWExcepcion {
        solicitudDao.agregar(solicitud);
    }

    public void modificar(Solicitud solicitud) throws FWExcepcion {
        solicitudDao.agregar(solicitud);
    }
    
    public void modificar(SolicitudDetalle solicitudDetalle) throws FWExcepcion {
        solicitudDao.modificar(solicitudDetalle);        
    }

    public Solicitud buscarPorId(Long id) throws FWExcepcion {
        return solicitudDao.buscarPorId(id);
    }

    public void agregarDetallesSolicitud(ArrayList<SolicitudDetalle> detalles) throws FWExcepcion {
        for (Iterator iterator = detalles.iterator(); iterator.hasNext();) {
            this.agregarDetalleSolicitud((SolicitudDetalle) iterator.next());
        }
    }

    public void agregarDetalleSolicitud(SolicitudDetalle solicitudDetalle) throws FWExcepcion {
        solicitudDao.agregarDetalleSolicitud(solicitudDetalle);
    }

    public Long contarDonaciones(String id, Long idUnidadEjecutora, Estado estado, Integer tipoSolicitud, Tipo tipoDonacion, String unidadReceptora, String donante) throws FWExcepcion {
        if (idUnidadEjecutora.equals(Constantes.DEFAULT_ID)) {
            return solicitudDao.contarDonaciones(id, null, estado, tipoSolicitud, tipoDonacion, unidadReceptora, donante);
        }
        else {
            return solicitudDao.contarDonaciones(id, unidadEjecutoraDao.buscarPorId(idUnidadEjecutora), estado, tipoSolicitud, tipoDonacion, unidadReceptora, donante);
        }
    }

    public List<Solicitud> listarDonaciones(String id, Long idUnidadEjecutora, Estado estado, Integer tipoSolicitud, Tipo tipoDonacion, String unidadReceptora, String donante,
            Integer pPrimerRegistro, Integer pUltimoRegistro) throws FWExcepcion {
        if (idUnidadEjecutora.equals(Constantes.DEFAULT_ID)) {
            return solicitudDao.listarDonaciones(id, null, estado, tipoSolicitud, tipoDonacion, unidadReceptora, donante, pPrimerRegistro, pUltimoRegistro);
        }
        else {
            return solicitudDao.listarDonaciones(id, unidadEjecutoraDao.buscarPorId(idUnidadEjecutora), estado, tipoSolicitud, tipoDonacion, unidadReceptora, donante, pPrimerRegistro, pUltimoRegistro);
        }
    }

    public HashMap<String, SolicitudAutorizacion> obtenerSolicitudsAutorizacionPorRol(Integer codigoAutorizacion, Solicitud solicitud, Usuario usuario, UnidadEjecutora unidadEjecutora) {

        HashMap<String, SolicitudAutorizacion> solicitudAutorizaciones = new HashMap();
        Estado estadoAutorizacionProceso = estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_PENDIENTE);

        //Paso 1: Se obtienen los roles que deben aprobar ls solicitud
        List<AutorizacionRol> rolesSolicitud = autorizacionRolModel.buscarPorCodigoAutorizacion(codigoAutorizacion);

        for (AutorizacionRol autorizacionRol : rolesSolicitud) {

            //Para cada rol se verifica si el usuario tiene permisos para aplicar o rechazar la autorizacion
            AutorizacionRolPersona autorizacionRolPersona = autorizacionRolPersonaModel.buscar(autorizacionRol, usuario, unidadEjecutora);
            Boolean permiteModificar = autorizacionRolPersona != null;

            //Para cada rol se verifica si el solicitud ya tiene alguna otra aprobacion
            SolicitudAutorizacion solicitudAutorizacion = solicitudAutorizacionModel.buscar(autorizacionRol, solicitud);
            if (solicitudAutorizacion == null) {
                solicitudAutorizacion = new SolicitudAutorizacion(solicitud, autorizacionRol, null, estadoAutorizacionProceso);
            }
            solicitudAutorizacion.setMarcado(permiteModificar);
            solicitudAutorizaciones.put(autorizacionRol.getRol().getNombre(), solicitudAutorizacion);

        }

        return solicitudAutorizaciones;
    }

    public List<SolicitudDetalle> listarDetallesSolicitud(Solicitud solicitud) throws FWExcepcion {
        return solicitudDao.listarDetallesSolicitud(solicitud);
    }

    public SolicitudDetalle listarDetallesSolicitud(Solicitud solicitud, Bien bien) throws FWExcepcion {
        return solicitudDao.listarDetallesSolicitud(solicitud, bien);        
    }
    
    public void eliminarDetalleSolicitud(SolicitudDetalle solicitud) throws FWExcepcion {
        solicitudDao.eliminarDetalleSolicitud(solicitud);
    }

    public List<Solicitud> movimientosPorBien(Bien bien) {
        return solicitudDao.movimientosPorBien(bien);
    }
    
    public List<SolicitudDetalle> listarDetallesSalidas(Long id, String identificacionBien, Date fechaInicio, Date fechaFin, String orden, String orden1, String orden2, String orden3) throws FWExcepcion {
        return solicitudDao.listarDetallesSalidas(id, identificacionBien, fechaInicio, fechaFin, orden, orden1, orden2, orden3);
    }
    
    @Transactional(readOnly = true)
    public List<SolicitudSalida> listarSalidas(String id,
            Long idUnidadEjecutora,
            Estado estado,
            String cedula,
            String nombre,
            Tipo tipo,
            Integer tipoSolicitud,
            Date fecha,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ) throws FWExcepcion {
        if (idUnidadEjecutora.equals(Constantes.DEFAULT_ID)) {
            return solicitudDao.listarSalidas(id, null, estado, cedula, nombre, tipo, tipoSolicitud, fecha, pPrimerRegistro, pUltimoRegistro);
        }
        else {
            return solicitudDao.listarSalidas(id, unidadEjecutoraDao.buscarPorId(idUnidadEjecutora), estado, cedula, nombre, tipo, tipoSolicitud, fecha, pPrimerRegistro, pUltimoRegistro);
        }

    }
    
    @Transactional(readOnly = true)
    public Long contarSalidas(String id,
            Long idUnidadEjecutora,
            Estado estado,
            String cedula,
            String nombre,
            Tipo tipo,
            Integer tipoSolicitud,
            Date fecha            
    ) throws FWExcepcion {
        if (idUnidadEjecutora.equals(Constantes.DEFAULT_ID)) {
            return solicitudDao.contarSalidas(id, null, estado, cedula, nombre, tipo, tipoSolicitud, fecha);
        }
        else {
            return solicitudDao.contarSalidas(id, unidadEjecutoraDao.buscarPorId(idUnidadEjecutora), estado, cedula, nombre, tipo, tipoSolicitud, fecha);
        }
    }
}
