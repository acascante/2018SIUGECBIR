/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import com.icesoft.faces.component.inputfile.FileInfo;
import com.icesoft.faces.component.inputfile.InputFile;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.commands.SolicitudDonacionCommand;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.Adjunto;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Factura;
import cr.ac.ucr.sigebi.domain.Pais;
import cr.ac.ucr.sigebi.domain.SolicitudAutorizacion;
import cr.ac.ucr.sigebi.domain.SolicitudDetalle;
import cr.ac.ucr.sigebi.domain.SolicitudDonacion;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.models.AdjuntoModel;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.FacturaModel;
import cr.ac.ucr.sigebi.models.PaisModel;
import cr.ac.ucr.sigebi.models.SolicitudAutorizacionModel;
import cr.ac.ucr.sigebi.models.SolicitudModel;
import cr.ac.ucr.sigebi.models.UnidadEjecutoraModel;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author jairo.cisneros
 */
@Controller(value = "controllerSolicitudDonacion")
@Scope("session")
public class SolicitudDonacionController extends BaseController {

    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    @Resource
    private SolicitudModel solicitudModel;

    @Resource
    private SolicitudAutorizacionModel solicitudAutorizacionModel;

    @Resource
    private FacturaModel facturaModel;

    @Resource
    private PaisModel paisModel;

    @Resource
    private AdjuntoModel adjuntoModel;

    @Resource
    private BienModel bienModel;

    @Resource
    private UnidadEjecutoraModel unidadEjecutoraModel;

    // Se usan en el jsp
    boolean aprobacionRealizada = false;
    boolean yaRegistrada = false;
    boolean rolPermiteModificar = false;
    boolean rolPermiteAplicar = false;
    boolean rolPermiteAnular = false;
    boolean rolPermiteRechazar = false;

    SolicitudDonacionCommand command;

    Estado estadoBienInactivo = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_INACTIVO);
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">
    public boolean isAprobacionRealizada() {
        return aprobacionRealizada;
    }

    public void setAprobacionRealizada(boolean aprobacionRealizada) {
        this.aprobacionRealizada = aprobacionRealizada;
    }

    public Estado getEstadoBienInactivo() {
        return estadoBienInactivo;
    }

    public void setEstadoBienInactivo(Estado estadoBienInactivo) {
        this.estadoBienInactivo = estadoBienInactivo;
    }

    public boolean isYaRegistrada() {
        return yaRegistrada;
    }

    public void setYaRegistrada(boolean yaRegistrada) {
        this.yaRegistrada = yaRegistrada;
    }

    public boolean isRolPermiteModificar() {
        return rolPermiteModificar;
    }

    public void setRolPermiteModificar(boolean rolPermiteModificar) {
        this.rolPermiteModificar = rolPermiteModificar;
    }

    public boolean isRolPermiteAplicar() {
        return rolPermiteAplicar;
    }

    public void setRolPermiteAplicar(boolean rolPermiteAplicar) {
        this.rolPermiteAplicar = rolPermiteAplicar;
    }

    public boolean isRolPermiteAnular() {
        return rolPermiteAnular;
    }

    public void setRolPermiteAnular(boolean rolPermiteAnular) {
        this.rolPermiteAnular = rolPermiteAnular;
    }

    public boolean isRolPermiteRechazar() {
        return rolPermiteRechazar;
    }

    public void setRolPermiteRechazar(boolean rolPermiteRechazar) {
        this.rolPermiteRechazar = rolPermiteRechazar;
    }

    public SolicitudDonacionCommand getCommand() {
        return command;
    }

    public void setCommand(SolicitudDonacionCommand command) {
        this.command = command;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Navegación del MENÚ">
    public void nuevoRegistro(ActionEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }

        aprobacionRealizada = false;
        yaRegistrada = false;
        rolPermiteModificar = false;
        rolPermiteAplicar = false;
        rolPermiteRechazar = false;
        rolPermiteAnular = false;

        this.prepararCreacionSolicitudDonacion();

        this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
        Util.navegar(Constantes.KEY_VISTA_SOLICITUD_DONACION_DETALLE);
    }

    public void modificarRegistro(ActionEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        aprobacionRealizada = false;
        yaRegistrada = true;
        rolPermiteModificar = false;
        rolPermiteAplicar = false;
        rolPermiteRechazar = false;
        rolPermiteAnular = false;
        this.prepararModificacionSolicitudDonacion((SolicitudDonacion) event.getComponent().getAttributes().get("solicitudSeleccionada"));

        this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
        Util.navegar(Constantes.KEY_VISTA_SOLICITUD_DONACION_DETALLE);

    }

    public void regresarListado() {
        if (vistaOrigen != null) {
            Util.navegar(vistaOrigen);
        } else {
            Util.navegar(Constantes.KEY_VISTA_SOLICITUD_DONACION_LISTADO);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public SolicitudDonacionController() {
        super();
    }

    private void prepararCreacionSolicitudDonacion() {

        command = new SolicitudDonacionCommand(unidadEjecutora);
        command.getSolicitudDonacion().setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_SOLI_DONACION, Constantes.ESTADO_SOLITUD_DONACION_NUEVO));
        rolPermiteModificar = true;
        this.cargaDatosGenerales();
    }

    private void prepararModificacionSolicitudDonacion(SolicitudDonacion solicitudDonacion) {

        command = new SolicitudDonacionCommand(solicitudDonacion);
        this.cargaDatosGenerales();

        //Constantes
        //Tipo de adjunto
        command.setTipoAdjunto(this.tipoPorDominioValor(Constantes.DOMINIO_ADJUNTO, Constantes.TIPO_ADJUNTO_DOCUMENTO));

        // Listas asociadas a la donacion
        //Bienes asociados
        for (SolicitudDetalle item : solicitudModel.listarDetallesSolicitud(solicitudDonacion)) {
            command.getBienesDonacion().put(item.getId(), item);
        }

        //Facturas asociadas
        for (Factura facturaItem : facturaModel.listar(solicitudDonacion)) {
            command.getFacturasDonacion().put(facturaItem.getId(), facturaItem);
        }

        //Adjuntos asociados
        List<Adjunto> adjuntos = adjuntoModel.buscarPorReferencia(command.getTipoAdjunto(), command.getSolicitudDonacion().getId());
        for (Adjunto adjuntoItem : adjuntos) {
            command.getAdjuntosDonacion().put(adjuntoItem.getId(), adjuntoItem);
        }

        //Aprobaciones
        this.consultaAutorizaciones();

        //Se actuaizan banderas
        cambiaEstadoSolicitud();
    }

    private void cargaDatosGenerales() {

        //Se asocian los tipos de la pantalla
        for (Tipo item : this.tiposPorDominio(Constantes.DOMINIO_SOLI_DONACION)) {
            command.getTipoDonacionOptions().add(new SelectItem(item.getId().toString(), item.getNombre()));
        }

        //Se asocian los paises
        for (Pais item : paisModel.listar()) {
            command.getPaisOptions().add(new SelectItem(item.getId().toString(), item.getNombre()));
            command.getPaisesDonacion().put(item.getId(), item);
        }
    }

    private void consultaAutorizaciones() {
        try {

            //Se listan las autorizaciones permitidas para el solicitud, se agrupan por rol. 
            command.setSolicitudesAutorizacionesPorRol(solicitudModel.obtenerSolicitudsAutorizacionPorRol(Constantes.CODIGO_AUTORIZACION_DONACION, command.getSolicitudDonacion(), this.usuarioSIGEBI, unidadEjecutora));

            //Elimina la marca de los solicitudes, por reglas internas, no se permite aceptar o rechazar
            eliminaMarcaSolicitudAceptarRechazar();

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudDonacionController.consultaAutorizaciones"));
        }
    }

    private void eliminaMarcaSolicitudAceptarRechazar() {
        try {
            for (Iterator<String> iterator = command.getSolicitudesAutorizacionesPorRol().keySet().iterator(); iterator.hasNext();) {

                //Si el solicitud no esta aprobado o anulado se permite modificarlo si es que tiene permisos, solo para los casos marcados en true
                SolicitudAutorizacion solicitudAutorizacion = command.getSolicitudesAutorizacionesPorRol().get((String) iterator.next());
                if (solicitudAutorizacion != null && solicitudAutorizacion.isMarcado()
                        && (command.getSolicitudDonacion().getEstado().getValor().equals(Constantes.ESTADO_SOLITUD_DONACION_APROBADA)
                        || command.getSolicitudDonacion().getEstado().getValor().equals(Constantes.ESTADO_SOLITUD_DONACION_ANULADA)
                        || command.getSolicitudDonacion().getEstado().getValor().equals(Constantes.ESTADO_SOLITUD_DONACION_NUEVO))) {
                    solicitudAutorizacion.setMarcado(false);
                }
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudDonacionController.eliminaMarcaSolicitudsAceptarRechazar"));
        }
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Pantalla Detalle">
    /**
     * Retorna la lista de autorizaciones
     *
     * @return
     */
    public Collection<SolicitudAutorizacion> getListaSolicitudsAutorizacionesPorRol() {
        return command.getSolicitudesAutorizacionesPorRol() != null ? new ArrayList(command.getSolicitudesAutorizacionesPorRol().values()) : new ArrayList();
    }

    /**
     * Metodo que cambia el estado de la solicitud para los casos en que todos
     * sus autorizaciones se aprueben o se rechacen
     */
    private void cambiaEstadoSolicitud() {

        try {

            boolean rechazar = true;
            boolean aprobar = true;
            aprobacionRealizada = false;
            SolicitudDonacion solicitud = command.getSolicitudDonacion();

            if (solicitud.getEstado().getValor().equals(Constantes.ESTADO_SOLITUD_DONACION_NUEVO)) {
                rolPermiteModificar = true;
                if (yaRegistrada) {
                    rolPermiteAplicar = true;
                    rolPermiteAnular = true;
                }
            } else if (solicitud.getEstado().getValor().equals(Constantes.ESTADO_SOLITUD_DONACION_PROCESO)) {
                rolPermiteModificar = true;
                if (yaRegistrada) {
                    rolPermiteAnular = true;
                    rolPermiteRechazar = true;
                }
            } else if (solicitud.getEstado().getValor().equals(Constantes.ESTADO_SOLITUD_DONACION_RECHAZADA)) {
                rolPermiteModificar = true;
                if (yaRegistrada) {
                    rolPermiteAnular = true;
                }
            }

            //Se determina las reglas segun las autorizaciones para los casos de solicitudes pendientes o rezhazadas
            //Se verifica que no este anulada, aprobada o nueva
            if (!solicitud.getEstado().getValor().equals(Constantes.ESTADO_SOLITUD_DONACION_APROBADA)
                    && !solicitud.getEstado().getValor().equals(Constantes.ESTADO_SOLITUD_DONACION_ANULADA)
                    && !solicitud.getEstado().getValor().equals(Constantes.ESTADO_SOLITUD_DONACION_NUEVO)) {

                for (Iterator<String> iterator = command.getSolicitudesAutorizacionesPorRol().keySet().iterator(); iterator.hasNext();) {

                    //Si el solicitud no esta aprobado o anulado se permite modificarlo si es que tiene permisos
                    SolicitudAutorizacion solicitudAutorizacion = command.getSolicitudesAutorizacionesPorRol().get((String) iterator.next());
                    if (solicitudAutorizacion != null) {

                        //Se verifica si alguno no esta aprobado
                        if (!solicitudAutorizacion.getEstado().getValor().equals(Constantes.ESTADO_GENERAL_APROBADO)) {
                            aprobar = false;
                        }
                        //Se verifica si alguno no esta rechazado
                        if (!solicitudAutorizacion.getEstado().getValor().equals(Constantes.ESTADO_GENERAL_RECHAZADO)) {
                            rechazar = false;
                        }
                        //Se verifica si alguno se encuentra pendiente de aprobar
                        if (solicitudAutorizacion.getEstado().getValor().equals(Constantes.ESTADO_GENERAL_APROBADO)) {
                            aprobacionRealizada = true;
                        }
                    }
                }

                //Si todos estan aprobados se aprueba todo la solicitud
                if (aprobar) {
                    solicitud.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_SOLI_DONACION, Constantes.ESTADO_SOLITUD_DONACION_APROBADA));
                    solicitudModel.modificar(solicitud);
                    this.consultaAutorizaciones();
                } else {

                    //Si todos estan rechazadas se rechaza todo la solicitud
                    if (rechazar) {
                        solicitud.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_SOLI_DONACION, Constantes.ESTADO_SOLITUD_DONACION_RECHAZADA));
                        solicitudModel.modificar(solicitud);
                    }

                    //Solo aplica si la solicitud no tiene aprobaciones
                    rolPermiteModificar = !aprobacionRealizada; //Aqui se incluye la verificacion por rol
                }
            }

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudDonacionController.cambiaEstadoSolicitud"));
        }
    }

    /**
     * Metodo que busca y asigna al command el tipo de donacion que se cambia
     * por pantalla
     *
     * @param event
     */
    public void cambiarTipoDonacion(ValueChangeEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }

            // Se obtiene el id del tipoDonacion
            Long valor = command.getSolicitudDonacion().getTipoDonacion().getIdTemporal();
            if (valor > 0) {
                command.getSolicitudDonacion().setTipoDonacion(this.tipoPorId(valor));
                command.getSolicitudDonacion().getTipoDonacion().setIdTemporal(valor);
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudDonacionController.cambiarTipoDonacion"));
        }
    }

    /**
     * Metodo que busca y asigna al command el pais que se cambia por pantalla
     *
     * @param event
     */
    public void cambiarPais(ValueChangeEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }

            // Se obtiene el id del tipoDonacion
            Long valor = command.getSolicitudDonacion().getPais().getIdTemporal();
            if (valor > 0) {
                command.getSolicitudDonacion().setPais(command.getPaisesDonacion().get(valor));
                command.getSolicitudDonacion().getPais().setIdTemporal(valor);
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudDonacionController.cambiarPais"));
        }
    }

    /**
     * Almacena la informacion del solicitud
     */
    public void guardarDatos() {
        try {
            if (validarForm()) {

                //Se almacena la informacion
                solicitudModel.modificar(command.getSolicitudDonacion());

                //Si existe alguna aprobacion en tramite se debe eliminar las aprobaciones ya que se cambio el solicitud
                if (aprobacionRealizada) {
                    this.eliminarAprobaciones();
                }

                //Se cargan los datos nuevamente
                //Aprobaciones
                this.consultaAutorizaciones();

                //Se actuaizan banderas
                this.cambiaEstadoSolicitud();

                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.solicitudDonacionController.modificado.exitosamente"));
            }

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudDonacionController.guardarDatos"));
        }
    }

    private void eliminarAprobaciones() {
        //Se actualizan todas las autorizaciones asociadas a la solicitud
        Estado estadoPendienteModi = this.estadoPorDominioValor(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_PROCESO);

        for (Iterator<String> iterator = command.getSolicitudesAutorizacionesPorRol().keySet().iterator(); iterator.hasNext();) {
            SolicitudAutorizacion solicitudAutorizacion = command.getSolicitudesAutorizacionesPorRol().get((String) iterator.next());

            //Se verifica si la solicitud ya esta incluida en la BD
            if (solicitudAutorizacion != null && solicitudAutorizacion.getId() != null && solicitudAutorizacion.getId() > 0) {
                solicitudAutorizacion.setEstado(estadoPendienteModi);
                solicitudAutorizacion.setFecha(new Date());
                solicitudAutorizacionModel.modificar(solicitudAutorizacion);
            }
        }
    }

    /**
     * Aplica la solicitud para permitir realizar las autorizaciones
     */
    public void aplicarSolicitud() {
        try {

            if (command.getBienesDetalles() != null && command.getBienesDetalles().size() > 0) {

                //Se almacena la informacion
                command.getSolicitudDonacion().setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_SOLI_DONACION, Constantes.ESTADO_SOLITUD_DONACION_PROCESO));
                solicitudModel.modificar(command.getSolicitudDonacion());

                //Se cargan los datos nuevamente
                //Aprobaciones
                this.consultaAutorizaciones();

                //Se actuaizan banderas
                this.cambiaEstadoSolicitud();

                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.solicitudDonacionController.modificado.exitosamente"));
            } else {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.solicitudDonacionController.bienes.requeridos"));
            }

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudDonacionController.guardarDatos"));
        }
    }

    /**
     * Anula las solicitud por completo Los bienes se deben dejar en estado
     * inactivo y las identificaciones se pierden
     */
    public void anularSolicitud() {
        try {

            //Se cambia el estado a la solicitud
            command.getSolicitudDonacion().setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_SOLI_DONACION, Constantes.ESTADO_SOLITUD_DONACION_ANULADA));
            solicitudModel.modificar(command.getSolicitudDonacion());

            //Se cambia el estado a todos los bienes asociados a la solicitud
            ArrayList<Bien> bienes = new ArrayList<Bien>();
            for (SolicitudDetalle bienesDetalle : command.getBienesDetalles()) {
                bienes.add(bienesDetalle.getBien());
            }
            Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;
            bienModel.cambiaEstadoBien(bienes, estadoBienInactivo, command.getObservacion(), telefono, usuarioSIGEBI);

            //Se cargan los datos nuevamente
            //Aprobaciones
            this.consultaAutorizaciones();

            //Se actuaizan banderas
            this.cambiaEstadoSolicitud();

            Mensaje.agregarInfo(Util.getEtiquetas("sigebi.solicitudDonacionController.modificado.exitosamente"));

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudDonacionController.guardarDatos"));
        }
    }

    /**
     * Rechaza una solicitud,  las autorizaciones se deben eliminar
     */
    public void rechazarSolicitud() {
        try {

            //Se cambia el estado a la solicitud
            command.getSolicitudDonacion().setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_SOLI_DONACION, Constantes.ESTADO_SOLITUD_DONACION_RECHAZADA));
            solicitudModel.modificar(command.getSolicitudDonacion());

            //Si existe alguna aprobacion en tramite se debe eliminar las aprobaciones ya que se cambio el solicitud
            if (aprobacionRealizada) {
                this.eliminarAprobaciones();
            }
            
            //Se cargan los datos nuevamente
            
            //Aprobaciones
            this.consultaAutorizaciones();

            //Se actuaizan banderas
            this.cambiaEstadoSolicitud();

            Mensaje.agregarInfo(Util.getEtiquetas("sigebi.solicitudDonacionController.modificado.exitosamente"));

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudDonacionController.guardarDatos"));
        }
    }

    public boolean validarForm() {

        SolicitudDonacion solicitud = command.getSolicitudDonacion();

        if (solicitud.getTipoDonacion() == null || solicitud.getTipoDonacion().getId() == null || solicitud.getTipoDonacion().getId() <= 0) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.solicitudDonacionController.tipo.requerido"));
            return false;
        }
        if (solicitud.getUnidadReceptora() == null || solicitud.getUnidadReceptora().getId() == null || solicitud.getUnidadReceptora().getId() <= 0) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.solicitudDonacionController.unidadReceptora.requerido"));
            return false;
        }
        if (solicitud.getFecha() == null) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.solicitudDonacionController.fecha.requerido"));
            return false;
        }
        if (solicitud.getDonante().isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.solicitudDonacionController.donante.requerido"));
            return false;
        }
        if (solicitud.getPais() == null || solicitud.getPais().getId() == null || solicitud.getPais().getId() <= 0) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.solicitudDonacionController.pais.requerido"));
            return false;
        }
        if (solicitud.getCiudad().isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.solicitudDonacionController.ciudad.requerido"));
            return false;
        }
        return true;
    }

    /**
     * Muestra el panel de acuerdo a la accion
     *
     * @param pEvent
     */
    public void mostrarPanel(ActionEvent pEvent) {

        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            //Se muestra el panel de acuerdo a la accion
            String accion = (String) pEvent.getComponent().getAttributes().get("accion");
            command.setAccion(Integer.parseInt(accion));
            command.setPresentarPanel(Boolean.TRUE);
            if (command.getPresentarPanelBuscarReceptor()) {
                buscarUnidadesReceptoras();
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudDonacionController.mostrarPanel"));
        }
    }

    public void cerrarPanel() {
        try {
            command.setPresentarPanel(Boolean.FALSE);
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudDonacionController.cerrarPanel"));
        }
    }

    //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="Aprobaciones y rechazos">
    /**
     * Aprueba la solicitud
     *
     * @param pEvent
     */
    public void aprobar(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            //Se obtiene el solicitud a modificar
            SolicitudAutorizacion solicitud = (SolicitudAutorizacion) pEvent.getComponent().getAttributes().get("solicitudSelApro");
            solicitud.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_APROBADO));
            solicitud.setFecha(new Date());
            solicitud.setUsuarioSeguridad(this.usuarioSIGEBI);
            if (solicitud.getId() != null && solicitud.getId() > 0) {
                solicitudAutorizacionModel.modificar(solicitud);
            } else {
                solicitudAutorizacionModel.agregar(solicitud);
            }

            //Se modifica en el solicitud en los casos que aplique
            cambiaEstadoSolicitud();

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e, e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudDonacionController.aprobar"));
        }
    }

    /**
     * Rechaza el solicitud
     *
     * @param pEvent
     */
    public void rechazar(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            //Se obtiene el solicitud a modificar
            SolicitudAutorizacion solicitud = (SolicitudAutorizacion) pEvent.getComponent().getAttributes().get("solicitudSelRech");
            solicitud.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_RECHAZADO));
            solicitud.setFecha(new Date());
            solicitud.setUsuarioSeguridad(this.usuarioSIGEBI);
            if (solicitud.getId() != null && solicitud.getId() > 0) {
                solicitudAutorizacionModel.modificar(solicitud);
            } else {
                solicitudAutorizacionModel.agregar(solicitud);
            }

            //Se modifica en el solicitud en los casos que aplique
            cambiaEstadoSolicitud();

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.solicitudDonacionController.rechazar"));
        }

    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Adjuntos del solicitud">
    /**
     * Agrega un adjunto al solicitud
     *
     * @param pEvent
     */
    public void agregarAdjunto(ActionEvent pEvent) {
        try {
            InputFile inputFile = (InputFile) pEvent.getSource();
            FileInfo fileInfo = inputFile.getFileInfo();
            if (fileInfo.getFileName() == null) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.solicitudDonacionController.adjunto.requerido"));
            } else {
                Adjunto adjunto = command.getAdjunto();
                adjunto.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO));
                adjunto.setTipo(command.getTipoAdjunto());
                adjunto.setIdReferencia(command.getSolicitudDonacion().getId());
                adjunto.setUrl("upload/solicitudDonacion/" + fileInfo.getFileName());
                if (adjunto.getDetalle() == null || (adjunto.getDetalle() != null && adjunto.getDetalle().length() == 0)) {
                    adjunto.setDetalle(fileInfo.getFileName());
                }

                adjuntoModel.agregar(adjunto);
                command.getAdjuntosDonacion().put(adjunto.getId(), adjunto);
                command.setAdjunto(new Adjunto());
                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.error.solicitudDonacionController.adjunto.agregar.exitosamente"));
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudDonacionController.agregarAdjunto"));
        }
    }

    /**
     * Eliminar adjunto al solicitud
     *
     * @param pEvent
     */
    public void eliminarAdjunto(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            //Se obtiene el adjunto
            Adjunto adjunto = (Adjunto) pEvent.getComponent().getAttributes().get("adjuntoEliminarSel");

            //Se Busca el archivo en disco
            File file = new File(adjunto.getUrl());
            file.delete();

            //Se elimina del mapa
            command.getAdjuntosDonacion().remove(adjunto.getId());

            //Se elimina de la base de datos 
            adjuntoModel.eliminar(adjunto);

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudDonacionController.eliminarAdjunto"));
        }
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Busqueda Unidad Receptora">  
    public void selecionarUnidad(ActionEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            command.getSolicitudDonacion().setUnidadReceptora((UnidadEjecutora) event.getComponent().getAttributes().get("unidadSeleccionada"));
            command.setPresentarPanel(false);

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudDonacionController.selecionarUnidad"));
        }
    }

    private void buscarUnidadesReceptoras() {
        //Se presentan las primeras 15
        command.setUnidadEjecutoras(unidadEjecutoraModel.listar(command.getFltIdUnidad(), command.getFltDescripcionUnidad()));
    }

    public void filtroUnidadesReceptoras(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            buscarUnidadesReceptoras();
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.solicitudDonacionController.filtroUnidadesReceptoras"));
        }

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Facturas de la solicitud">
    /**
     * Agrega una factura a la solicitud
     *
     * @param pEvent
     */
    public void agregarFactura() {
        try {

            if (validarFormFactura()) {

                Factura factura = command.getFactura();
                factura.setSolicitudDonacion(command.getSolicitudDonacion());
                facturaModel.guardar(factura);

                command.getFacturasDonacion().put(factura.getId(), factura);
                command.setFactura(new Factura());
                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.error.solicitudDonacionController.factura.agregar.exitosamente"));
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudDonacionController.agregarFactura"));
        }
    }

    /**
     * Eliminar adjunto al solicitud
     *
     * @param pEvent
     */
    public void eliminarFactura(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            //Se obtiene la factura
            Factura factura = (Factura) pEvent.getComponent().getAttributes().get("facturaEliminarSel");

            //Se elimina del mapa
            command.getFacturasDonacion().remove(factura.getId());

            //Se elimina de la base de datos 
            facturaModel.eliminar(factura);

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudDonacionController.eliminarFactura"));
        }
    }

    public boolean validarFormFactura() {

        Factura factura = command.getFactura();

        if (factura.getFecha() == null) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.solicitudDonacionController.factura.fecha"));
            return false;
        }
        if (factura.getNumero().isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.solicitudDonacionController.factura.numero.requerido"));
            return false;
        }
        return true;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Bienes de la solicitud">

    /**
     * Elimina un bien a la solicitud
     *
     * @param pEvent
     */
    public void rechazarDetalleBien(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            //Se obtiene el detalle
            SolicitudDetalle detalle = (SolicitudDetalle) pEvent.getComponent().getAttributes().get("detalleSeleccionado");

            //Se elimina del mapa
            command.getBienesDonacion().remove(detalle.getId());
            
            //Se marca el bien como rechazado
            Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;
            bienModel.cambiaEstadoBien(detalle.getBien(), estadoBienInactivo, command.getObservacion(), telefono, usuarioSIGEBI);

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudDonacionController.eliminarBien"));
        }
    }
    //</editor-fold>

}