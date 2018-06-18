/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.commands.SolicitudSalidaCommand;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Persona;
import cr.ac.ucr.sigebi.domain.RegistroMovimientoSolicitud;
import cr.ac.ucr.sigebi.domain.SolicitudDetalle;
import cr.ac.ucr.sigebi.domain.SolicitudDetalleSalida;
import cr.ac.ucr.sigebi.domain.SolicitudSalida;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.PersonaModel;
import cr.ac.ucr.sigebi.models.RegistroMovimientoModel;
import cr.ac.ucr.sigebi.models.SolicitudModel;
import java.util.Date;
import javax.annotation.Resource;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author jairo.cisneros
 */
@Controller(value = "controllerSolicitudSalida")
@Scope("session")
public class SolicitudSalidaController extends BaseController {

    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    @Resource
    private SolicitudModel solicitudModel;

    @Resource
    private RegistroMovimientoModel registroMovimientoModel;

    @Resource
    private BienModel bienModel;

    @Resource
    private PersonaModel personaModel;

    SolicitudSalidaCommand command;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">
    public SolicitudSalidaCommand getCommand() {
        return command;
    }

    public void setCommand(SolicitudSalidaCommand command) {
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

        this.prepararCreacionSolicitudDonacion();

        this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
        Util.navegar(Constantes.KEY_VISTA_SOLICITUD_SALIDA_DETALLE);
    }

    public void modificarRegistro(ActionEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }

        this.prepararModificacionSolicitudDonacion((SolicitudSalida) event.getComponent().getAttributes().get("solicitudSeleccionada"));

        this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
        Util.navegar(Constantes.KEY_VISTA_SOLICITUD_SALIDA_DETALLE);

    }

    public void verDetalle(SolicitudSalida movimiento, String vistaOrigen) {
        this.prepararModificacionSolicitudDonacion(movimiento);

        this.vistaOrigen = vistaOrigen;
        Util.navegar(Constantes.KEY_VISTA_SOLICITUD_SALIDA_DETALLE);

    }
    
    public void regresarListado() {
        if (vistaOrigen != null) {
            Util.navegar(vistaOrigen, true);
        } else {
            Util.navegar(Constantes.KEY_VISTA_SOLICITUD_SALIDA_LISTADO, true);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public SolicitudSalidaController() {
        super();

    }

    private void prepararCreacionSolicitudDonacion() {
        command = new SolicitudSalidaCommand(unidadEjecutora,
                this.estadoPorDominioValor(Constantes.DOMINIO_SOLI_SALIDAS, Constantes.ESTADO_SOLITUD_SALIDA_NUEVO),
                this.tipoPorDominioValor(Constantes.DOMINIO_SOLI_SALIDAS, Constantes.TIPO_SOLICITUD_SALIDA_NACIONAL),
                this.tipoPorDominioValor(Constantes.DOMINIO_SOLI_SALIDAS, Constantes.TIPO_SOLICITUD_SALIDA_INTERNACIONAL));
    }

    private void prepararModificacionSolicitudDonacion(SolicitudSalida solicitudSalida) {
        command = new SolicitudSalidaCommand(solicitudSalida,
                this.tipoPorDominioValor(Constantes.DOMINIO_SOLI_SALIDAS, Constantes.TIPO_SOLICITUD_SALIDA_NACIONAL),
                this.tipoPorDominioValor(Constantes.DOMINIO_SOLI_SALIDAS, Constantes.TIPO_SOLICITUD_SALIDA_INTERNACIONAL));

        // Listas asociadas a la donacion
        //Bienes asociados
        for (SolicitudDetalle item : solicitudModel.listarDetallesSolicitud(solicitudSalida)) {
            command.getBienesSalidas().put(item.getId(), item);
        }

        //Se actuaizan banderas
        cambiaEstadoSolicitud();
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Pantalla Detalle">
    
    /**
     * Metodo que cambia el estado de la solicitud en el caso de que todos los
     * bienes pasa a un mismo estado
     */
    private void cambiaEstadoSolicitud() {
        try {

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudSalidaController.cambiaEstadoSolicitud"));
        }
    }

    /**
     * Metodo que busca y asigna al command el tipo de salida que se cambia por
     * pantalla
     *
     * @param event
     */
    public void cambiarTipo(ValueChangeEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }

            // Se obtiene el id del tipo
            Long valor = command.getIdTipo();
            if (valor > 0) {
                if (command.getIdTipoInternacional().getId().equals(valor)) {
                    command.getSolicitudSalida().setTipo(command.getIdTipoInternacional());
                } else if (command.getIdTipoNacional().getId().equals(valor)) {
                    command.getSolicitudSalida().setTipo(command.getIdTipoNacional());
                }
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudSalidaController.cambiarTipo"));
        }
    }

    /**
     * Almacena la informacion de la solicitud
     */
    public void guardarDatos() {
        try {
            if (validarForm()) {

                //Se almacena la informacion
                solicitudModel.modificar(command.getSolicitudSalida());
                command.setYaRegistrada(Boolean.TRUE);

                //Se actualizan banderas
                this.cambiaEstadoSolicitud();

                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.solicitudSalidaController.modificado.exitosamente"));
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudSalidaController.guardarDatos"));
        }
    }

    /**
     * Aplica la solicitud para permitir realizar las autorizaciones
     */
    public void aplicarSolicitud() {
        try {

            if (command.getBienesDetalles() != null && command.getBienesDetalles().size() > 0) {
                
                if(verificarFechasDetallesBien()){
                    //Se almacena la informacion
                    command.getSolicitudSalida().setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_SOLI_SALIDAS, Constantes.ESTADO_SOLITUD_SALIDA_PROCESO));
                    solicitudModel.modificar(command.getSolicitudSalida());

                    //Se actualizan banderas
                    this.cambiaEstadoSolicitud();

                    Mensaje.agregarInfo(Util.getEtiquetas("sigebi.solicitudSalidaController.modificado.exitosamente"));       
                    
                } else {
                    Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.solicitudSalidaController.bienes.sinFecha"));
                }

            } else {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.solicitudSalidaController.bienes.requeridos"));
            }

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudSalidaController.guardarDatos"));
        }
    }

    public Boolean verificarFechasDetallesBien() {
        for (SolicitudDetalle bienDeta : command.getBienesDetalles()) {
            if(((SolicitudDetalleSalida) bienDeta).getFechaIngreso() == null || ((SolicitudDetalleSalida) bienDeta).getFechaSalida() == null){
                return false;
            }
        }
        
        return true;
    }

    /**
     * Anula las solicitud por completo
     */
    public void anularSolicitud() {
        try {
            
            boolean aprobado = false;
            for (SolicitudDetalle bienesDetalle : command.getBienesDetalles()) {
                if(bienesDetalle.getEstado().getValor().equals(Constantes.ESTADO_GENERAL_APROBADO)){
                    aprobado = true;
                    break;
                }
            }            
            if(!aprobado){
                //Se cambia el estado a la solicitud
                command.getSolicitudSalida().setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_SOLI_SALIDAS, Constantes.ESTADO_SOLITUD_SALIDA_ANULADA));
                solicitudModel.modificar(command.getSolicitudSalida());

                //Se registra un movimiento para la solicitud
                Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;
                RegistroMovimientoSolicitud registroMovimientoSolicitud = new RegistroMovimientoSolicitud(this.tipoPorDominioValor(Constantes.DOMINIO_REGISTRO_MOVIMIENTO, Constantes.TIPO_REGISTRO_MOVIMIENTO_CAMBIO_ESTADO_SOLICITUD), command.getObservacionConfirmacion(), telefono, new Date(),
                        usuarioSIGEBI, command.getSolicitudSalida().getEstado(), command.getSolicitudSalida());
                registroMovimientoModel.agregar(registroMovimientoSolicitud);

                //Se actuaLizan banderas
                this.cambiaEstadoSolicitud();

                command.setPresentarPanel(Boolean.FALSE);
                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.solicitudSalidaController.modificado.exitosamente"));
            }else{
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.solicitudSalidaController.bien.aprobado"));               
            }

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudSalidaController.guardarDatos"));
        }
    }
    
    /**
     * Anula las solicitud por completo
     */
    public void corregirSolicitud() {
        try {

            //Se cambia el estado a la solicitud
            command.getSolicitudSalida().setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_SOLI_SALIDAS, Constantes.ESTADO_SOLITUD_SALIDA_CORRECION));
            solicitudModel.modificar(command.getSolicitudSalida());

            //Se registra un movimiento para la solicitud
            Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;
            RegistroMovimientoSolicitud registroMovimientoSolicitud = new RegistroMovimientoSolicitud(this.tipoPorDominioValor(Constantes.DOMINIO_REGISTRO_MOVIMIENTO, Constantes.TIPO_REGISTRO_MOVIMIENTO_CAMBIO_ESTADO_SOLICITUD), command.getObservacionConfirmacion(), telefono, new Date(),
                    usuarioSIGEBI, command.getSolicitudSalida().getEstado(), command.getSolicitudSalida());
            registroMovimientoModel.agregar(registroMovimientoSolicitud);

            //Se actuaLizan banderas
            this.cambiaEstadoSolicitud();

            command.setPresentarPanel(Boolean.FALSE);
            Mensaje.agregarInfo(Util.getEtiquetas("sigebi.solicitudSalidaController.modificado.exitosamente"));

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudSalidaController.guardarDatos"));
        }
    }


    /**
     * Rechaza una solicitud
     */
    public void rechazarSolicitud() {
        try {

            //Se cambia el estado a la solicitud
            command.getSolicitudSalida().setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_SOLI_SALIDAS, Constantes.ESTADO_SOLITUD_SALIDA_RECHAZADA));
            solicitudModel.modificar(command.getSolicitudSalida());

            //Se registra un movimiento para la solicitud
            Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;
            RegistroMovimientoSolicitud registroMovimientoSolicitud = new RegistroMovimientoSolicitud(this.tipoPorDominioValor(Constantes.DOMINIO_REGISTRO_MOVIMIENTO, Constantes.TIPO_REGISTRO_MOVIMIENTO_CAMBIO_ESTADO_SOLICITUD), command.getObservacionConfirmacion(), telefono, new Date(),
                    usuarioSIGEBI, command.getSolicitudSalida().getEstado(), command.getSolicitudSalida());
            registroMovimientoModel.agregar(registroMovimientoSolicitud);

            
            //Se actuaizan banderas
            this.cambiaEstadoSolicitud();

            command.setPresentarPanel(Boolean.FALSE);
            Mensaje.agregarInfo(Util.getEtiquetas("sigebi.solicitudSalidaController.modificado.exitosamente"));

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudSalidaController.guardarDatos"));
        }
    }
    
    /**
     * Rechaza una solicitud
     */
    public void aceptarSolicitud() {
        try {

            //Se cambia el estado a la solicitud
            command.getSolicitudSalida().setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_SOLI_SALIDAS, Constantes.ESTADO_SOLITUD_SALIDA_APROBADA));
            solicitudModel.modificar(command.getSolicitudSalida());

            //Se actuaizan banderas
            this.cambiaEstadoSolicitud();

            Mensaje.agregarInfo(Util.getEtiquetas("sigebi.solicitudSalidaController.modificado.exitosamente"));

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudSalidaController.guardarDatos"));
        }
    }

    public boolean validarForm() {

        SolicitudSalida solicitud = command.getSolicitudSalida();

        if (solicitud.getTipo() == null || solicitud.getTipo().getId() == null || solicitud.getTipo().getId() <= 0) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.solicitudSalidaController.tipo.requerido"));
            return false;
        }
        if (solicitud.getFecha() == null) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.solicitudSalidaController.fecha.requerido"));
            return false;
        }
        if (solicitud.getPersona() == null || solicitud.getPersona().getNumPersona() == null || solicitud.getPersona().getNumPersona() <= 0) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.solicitudSalidaController.persona.requerido"));
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
            if (command.getPresentarPanelBuscarBienes()) {
                buscarBienes();
            } else if (command.getPresentarPanelBuscarPersonas()) {
                buscarPersonas();                
            } else if (command.getPresentarPanelAnularConfirmar()) {
                command.setMensajeConfirmacion(Util.getEtiquetas("sigebi.solicitudSalidaController.mensaje.anular"));
                command.setObservacionConfirmacion("");
            } else if (command.getPresentarPanelRechazarConfirmar()) {
                command.setMensajeConfirmacion(Util.getEtiquetas("sigebi.solicitudSalidaController.mensaje.rechazar"));
                command.setObservacionConfirmacion("");
            }else if (command.getPresentarPanelCorregirConfirmar()) {
                command.setMensajeConfirmacion(Util.getEtiquetas("sigebi.solicitudSalidaController.mensaje.corregir"));
                command.setObservacionConfirmacion("");
            }
            
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudSalidaController.mostrarPanel"));
        }
    }

    public void cerrarPanel() {
        try {
            command.setPresentarPanel(Boolean.FALSE);
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.solicitudSalidaController.cerrarPanel"));
        }
    }

    //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="Busqueda y modificacion de los bienes asociados">
    
    private void buscarBienes() {
        command.getBienCommand().setPrimerRegistro(1);
        this.contarBienes();
        this.listarBienes();    
    }
    
    public void selecionarBien(ActionEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            Bien bien = (Bien) event.getComponent().getAttributes().get("bienSeleccionado");
            
            if(solicitudModel.listarDetallesSolicitud(command.getSolicitudSalida(), bien) == null){
                //Se crea el detalle del bien y se incluye en el mapa
                SolicitudDetalleSalida solicitudDetalleSalida = new SolicitudDetalleSalida(command.getSolicitudSalida(), bien, this.estadoPorDominioValor(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_PENDIENTE));
                solicitudModel.agregarDetalleSolicitud(solicitudDetalleSalida);            
                command.getBienesSalidas().put(solicitudDetalleSalida.getId(), solicitudDetalleSalida);            
            }
            //Se cierra el panel
            command.setPresentarPanel(Boolean.FALSE);
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.selecionarBien"));
        }
    }
    
    public void eliminarBien(ActionEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            
            SolicitudDetalleSalida solicitudDetalleSalida = (SolicitudDetalleSalida) event.getComponent().getAttributes().get("detalleSeleccionado");

            //Se elimina del mapa
            command.getBienesSalidas().remove(solicitudDetalleSalida.getId());

            //Se elimina de la BD
            solicitudModel.eliminarDetalleSolicitud(solicitudDetalleSalida);
            
            //Se cierra el panel
            command.setPresentarPanel(Boolean.FALSE);
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.eliminarBien"));
        }
    }
    
    public void aprobarBien(ActionEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            
            //Se modifica el detalle
            SolicitudDetalleSalida solicitudDetalleSalida = (SolicitudDetalleSalida) event.getComponent().getAttributes().get("detalleSeleccionado");
            solicitudDetalleSalida.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_APROBADO));
            solicitudModel.modificar(solicitudDetalleSalida);           
            command.getBienesSalidas().put(solicitudDetalleSalida.getId(), solicitudDetalleSalida);
            
            //Se cierra el panel
            command.setPresentarPanel(Boolean.FALSE);
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.aprobarBien"));
        }
    }
    
    public void rechazarBien(ActionEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            
            //Se modifica el detalle
            SolicitudDetalleSalida solicitudDetalleSalida = (SolicitudDetalleSalida) event.getComponent().getAttributes().get("detalleSeleccionado");
            solicitudDetalleSalida.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_RECHAZADO));
            solicitudModel.modificar(solicitudDetalleSalida);           
            command.getBienesSalidas().put(solicitudDetalleSalida.getId(), solicitudDetalleSalida);
            
            //Se cierra el panel
            command.setPresentarPanel(Boolean.FALSE);
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.rechazarBien"));
        }
    }
    
    public void cambioFecha(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }

        SolicitudDetalleSalida solicitudDetalleSalida = (SolicitudDetalleSalida) pEvent.getComponent().getAttributes().get("detalleSeleccionado");
        solicitudModel.modificar(solicitudDetalleSalida);           
    }
    
    private void contarBienes() {
        
        Long cantReg = bienModel.contarMisBienes(unidadEjecutora, command.getBienCommand().getFltBienIdentificacion(), command.getBienCommand().getFltBienDescripcion(), 
                command.getBienCommand().getFltBienMarca(), command.getBienCommand().getFltBienModelo(), command.getBienCommand().getFltBienSerie(), 
                null, this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_ACTIVO), usuarioSIGEBI);        
        command.getBienCommand().setPrimerRegistro(1);
        command.getBienCommand().setCantidadRegistros(cantReg.intValue());
    }

    private void listarBienes() {
        try {            
         command.getBienCommand().setBienes(bienModel.listarMisBienes(command.getBienCommand().getPrimerRegistro() - 1, command.getBienCommand().getUltimoRegistro(), 
                unidadEjecutora, command.getBienCommand().getFltBienIdentificacion(), command.getBienCommand().getFltBienDescripcion(), command.getBienCommand().getFltBienMarca(), 
                command.getBienCommand().getFltBienModelo(), command.getBienCommand().getFltBienSerie(), 
                null, this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_ACTIVO), usuarioSIGEBI));
            
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerListarSolicitudSalidas.listarBienes"));
        }
    }

    public void cambioFiltroBien(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            // Se cuenta la cantidad de donaciones
            this.contarBienes();
            command.getBienCommand().setPrimerRegistro(1);
            this.listarBienes();

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerListarSolicitudSalidas.cambioFiltroBien"));
        }
    }
    
    /**
     * Pasa a la pagina sub-set
     *
     * @param pEvent
     */
    public void irPaginaBien(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        int numeroPagina = Integer.parseInt(Util.getRequestParameter("numPagBien"));
        command.getBienCommand().getPrimerRegistroPagina(numeroPagina);
        this.listarBienes();
    }

    /**
     * Pasa al siguiente sub-set
     *
     * @param pEvent
     */
    public void siguienteBien(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        command.getBienCommand().getSiguientePagina();
        this.listarBienes();
    }

    /**
     * Pasa al anterior sub-set
     *
     * @param pEvent
     */
    public void anteriorBien(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        command.getBienCommand().getPaginaAnterior();
        this.listarBienes();
    }

    /**
     * Pasa al primero sub-set
     *
     * @param pEvent
     */
    public void primeroBien(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        command.getBienCommand().setPrimerRegistro(1);
        this.listarBienes();
    }

    /**
     * Pasa al ultimo sub-set
     *
     * @param pEvent
     */
    public void ultimoBien(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        command.getBienCommand().getPrimerRegistroUltimaPagina();
        this.listarBienes();
    }

    /**
     * Cambia la cantidad de registros por página
     *
     * @param pEvent
     */
    public void cambioRegistrosPorPaginaBien(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        command.getBienCommand().setCantRegistroPorPagina(Integer.parseInt(pEvent.getNewValue().toString()));
        command.getBienCommand().setPrimerRegistro(1);
        this.contarBienes();
        this.listarBienes();
    }
    //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="Busqueda de personas">
    
    private void buscarPersonas() {
        command.getPersonaCommand().setPrimerRegistro(1);
        this.contarPersonas();
        this.listarPersonas();    
    }
    
    public void selecionarPersona(ActionEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            
            //Se asigna la persona
            Persona persona = (Persona) event.getComponent().getAttributes().get("personaSeleccionado");
            command.getSolicitudSalida().setPersona(persona);

            //Se cierra el panel
            command.setPresentarPanel(Boolean.FALSE);
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.selecionarPersona"));
        }
    }
        
    private void contarPersonas() {
        
        Long cantReg = personaModel.contar(Boolean.FALSE, Boolean.TRUE, null, command.getPersonaCommand().getFltPersonaIdentificacion(), command.getPersonaCommand().getFltPersonaNombre(), null, null); 
        command.getPersonaCommand().setPrimerRegistro(1);
        command.getPersonaCommand().setCantidadRegistros(cantReg.intValue());
    }

    private void listarPersonas() {
        try {            
         
            command.getPersonaCommand().setPersonas(
            personaModel.listar(command.getPersonaCommand().getPrimerRegistro() - 1, command.getPersonaCommand().getUltimoRegistro(), 
                   Boolean.FALSE, Boolean.TRUE, null, command.getPersonaCommand().getFltPersonaIdentificacion(), command.getPersonaCommand().getFltPersonaNombre(), null, null)
            );            
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerListarSolicitudSalidas.listarPersonas"));
        }
    }

    public void cambioFiltroPersonas(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            // Se cuenta la cantidad de donaciones
            this.contarPersonas();
            command.getPersonaCommand().setPrimerRegistro(1);
            this.listarPersonas();

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerListarSolicitudSalidas.cambioFiltroPersonas"));
        }
    }
    
    /**
     * Pasa a la pagina sub-set
     *
     * @param pEvent
     */
    public void irPaginaPersona(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        int numeroPagina = Integer.parseInt(Util.getRequestParameter("numPagPersona"));
        command.getPersonaCommand().getPrimerRegistroPagina(numeroPagina);
        this.listarPersonas();
    }

    /**
     * Pasa al siguiente sub-set
     *
     * @param pEvent
     */
    public void siguientePersona(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        command.getPersonaCommand().getSiguientePagina();
        this.listarPersonas();
    }

    /**
     * Pasa al anterior sub-set
     *
     * @param pEvent
     */
    public void anteriorPersona(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        command.getPersonaCommand().getPaginaAnterior();
        this.listarPersonas();
    }

    /**
     * Pasa al primero sub-set
     *
     * @param pEvent
     */
    public void primeroPersona(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        command.getPersonaCommand().setPrimerRegistro(1);
        this.listarPersonas();
    }

    /**
     * Pasa al ultimo sub-set
     *
     * @param pEvent
     */
    public void ultimoPersona(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        command.getPersonaCommand().getPrimerRegistroUltimaPagina();
        this.listarPersonas();
    }

    /**
     * Cambia la cantidad de registros por página
     *
     * @param pEvent
     */
    public void cambioRegistrosPorPaginaPersona(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        command.getPersonaCommand().setCantRegistroPorPagina(Integer.parseInt(pEvent.getNewValue().toString()));
        command.getPersonaCommand().setPrimerRegistro(1);
        this.contarPersonas();
        this.listarPersonas();
    }
    //</editor-fold> 
}
 