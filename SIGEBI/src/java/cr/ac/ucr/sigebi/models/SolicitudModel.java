/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.SolicitudDao;
import cr.ac.ucr.sigebi.domain.AutorizacionRol;
import cr.ac.ucr.sigebi.domain.AutorizacionRolPersona;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Solicitud;
import cr.ac.ucr.sigebi.domain.SolicitudAutorizacion;
import cr.ac.ucr.sigebi.domain.SolicitudDetalle;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.domain.Usuario;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

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

    public void agregar(Solicitud solicitud) throws FWExcepcion {
        solicitudDao.agregar(solicitud);
    }

    public void modificar(Solicitud solicitud) throws FWExcepcion {
        solicitudDao.agregar(solicitud);
    }

    public Solicitud buscarPorId(Long id) throws FWExcepcion{
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

    public Long contarDonaciones(String id, UnidadEjecutora unidadEjecutora, Estado estado, Integer tipoSolicitud, Tipo tipoDonacion, String unidadReceptora, String donante) throws FWExcepcion {
        return solicitudDao.contarDonaciones(id, unidadEjecutora, estado, tipoSolicitud, tipoDonacion, unidadReceptora, donante);
    }

    public List<Solicitud> listarDonaciones(String id, UnidadEjecutora unidadEjecutora, Estado estado, Integer tipoSolicitud, Tipo tipoDonacion, String unidadReceptora, String donante,
            Integer pPrimerRegistro, Integer pUltimoRegistro) throws FWExcepcion {
        return solicitudDao.listarDonaciones(id, unidadEjecutora, estado, tipoSolicitud, tipoDonacion, unidadReceptora, donante, pPrimerRegistro, pUltimoRegistro);
    }

    public HashMap<String, SolicitudAutorizacion> obtenerSolicitudsAutorizacionPorRol(Integer codigoAutorizacion, Solicitud solicitud, Usuario usuario, UnidadEjecutora unidadEjecutora) {

        HashMap<String, SolicitudAutorizacion> solicitudAutorizaciones = new HashMap();
        Estado estadoAutorizacionProceso = estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_PENDIENTE);

        //Paso 1: Se obtienen los roles que deben aprobar ls solicitud
        List<AutorizacionRol> rolesSolicitud = autorizacionRolModel.buscarPorCodigoAutorizacion(codigoAutorizacion);

        for (AutorizacionRol autorizacionRol : rolesSolicitud) {

            //Solo se contemplan los roles distintos al administrador
            if (!autorizacionRol.getRol().getCodigo().equals(Constantes.CODIGO_ROL_ADMINISTRADOR)) {

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
        }

        return solicitudAutorizaciones;
    }

    public List<SolicitudDetalle> listarDetallesSolicitud(Solicitud solicitud) throws FWExcepcion {
        return solicitudDao.listarDetallesSolicitud(solicitud);
    }
    
    public void eliminarDetalleSolicitud(SolicitudDetalle solicitud) throws FWExcepcion {
        solicitudDao.eliminarDetalleSolicitud(solicitud);
    }
    
    public List<Solicitud> movimientosPorBien(Bien bien){
        return solicitudDao.movimientosPorBien(bien);
    }
    
    
}
