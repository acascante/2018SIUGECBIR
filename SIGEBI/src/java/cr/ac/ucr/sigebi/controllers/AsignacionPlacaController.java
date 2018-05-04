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
import cr.ac.ucr.sigebi.commands.AsignacionPlacaCommand;
import cr.ac.ucr.sigebi.domain.AsignacionPlaca;
import cr.ac.ucr.sigebi.domain.RegistroMovimientoAsignacionPlaca;
import cr.ac.ucr.sigebi.models.AsignacionPlacaModel;
import cr.ac.ucr.sigebi.models.IdentificacionModel;
import cr.ac.ucr.sigebi.models.RegistroMovimientoModel;
import java.util.Date;
import javax.annotation.Resource;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author jairo.cisneros
 */
@Controller(value = "controllerAsignacionPlaca")
@Scope("session")
public class AsignacionPlacaController extends BaseController {

    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    @Resource
    private AsignacionPlacaModel asignacionPlacaModel;

    @Resource
    private IdentificacionModel identificacionModel;

    @Resource
    private RegistroMovimientoModel registroMovimientoModel;
            
    // Se usan en el jsp

    AsignacionPlacaCommand asignacionPlacaCommand;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">
    
    public AsignacionPlacaCommand getAsignacionPlacaCommand() {
        return asignacionPlacaCommand;
    }

    public void setAsignacionPlacaCommand(AsignacionPlacaCommand asignacionPlacaCommand) {
        this.asignacionPlacaCommand = asignacionPlacaCommand;
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Navegación del MENÚ">
    public void nuevoRegistro(ActionEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }

        //Se busca la cantidad disponible para la unidad
        Long disponibles = identificacionModel.cantidadDisponibles(unidadEjecutora, this.estadoPorDominioValor(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_RESERVADA_UNIDAD));
        this.asignacionPlacaCommand = new AsignacionPlacaCommand(disponibles);
        this.asignacionPlacaCommand.getAsignacionPlaca().setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_ASIGNACION_PLACA, Constantes.ESTADO_ASIGNACION_PLACA_PENDIENTE));
        this.asignacionPlacaCommand.setPermiteModificar(true);
        this.asignacionPlacaCommand.setYaRegistrada(false);

        this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
        Util.navegar(Constantes.KEY_VISTA_ASIGNACION_PLACA_DETALLE);
    }

    public void modificarRegistro(ActionEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }

        //Se busca la cantidad disponible para la unidad
        Long disponibles = identificacionModel.cantidadDisponibles(unidadEjecutora, this.estadoPorDominioValor(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_RESERVADA_UNIDAD));
        this.asignacionPlacaCommand = new AsignacionPlacaCommand((AsignacionPlaca) event.getComponent().getAttributes().get("asignacionPlacaSeleccionada"), disponibles);
        this.asignacionPlacaCommand.setYaRegistrada(true);

        this.permiteModificar();

        this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
        Util.navegar(Constantes.KEY_VISTA_ASIGNACION_PLACA_DETALLE);

    }

    private void permiteModificar() {
        if (this.asignacionPlacaCommand.getAsignacionPlaca().getEstado().getValor().equals(Constantes.ESTADO_ASIGNACION_PLACA_PENDIENTE)) {
           this.asignacionPlacaCommand.setPermiteModificar(true);
        }else{
           this.asignacionPlacaCommand.setPermiteModificar(false);        
        }
    }

    public void regresarListado() {
        if (vistaOrigen != null) {
            Util.navegar(vistaOrigen);
        } else {
            Util.navegar(Constantes.KEY_VISTA_LISTAR_ASIGNACION_PLACA);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public AsignacionPlacaController() {
        super();
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    
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
            asignacionPlacaCommand.setAccion(Integer.parseInt(accion));
            asignacionPlacaCommand.setPresentarPanel(Boolean.TRUE);
            if (asignacionPlacaCommand.getPresentarPanelConfirmar()) {
                asignacionPlacaCommand.setMensajeConfirmacion(Util.getEtiquetas("sigebi.controllerAsignacionPlaca.mensaje.confirmar"));
                asignacionPlacaCommand.setObservacionConfirmacion("");
            }else if (asignacionPlacaCommand.getPresentarPanelRechazar()){
                asignacionPlacaCommand.setMensajeConfirmacion(Util.getEtiquetas("sigebi.controllerAsignacionPlaca.mensaje.rechazar"));
                asignacionPlacaCommand.setObservacionConfirmacion("");
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerAsignacionPlaca.mostrarPanel"));
        }
    }

    public void cerrarPanel() {
        try {
            asignacionPlacaCommand.setPresentarPanel(Boolean.FALSE);
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerAsignacionPlaca.cerrarPanel"));
        }
    }
    
    /**
     * Almacena la informacion de la toma fisica
     */
    public void guardaAsignacionPlaca() {
        try {
            if (validarForm()) {

                this.registrarDatos();

                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.controllerAsignacionPlaca.modificado.exitosamente"));
            }

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerAsignacionPlaca.guardarDatos"));
        }
    }

    private void registrarDatos() {
        if (!asignacionPlacaCommand.isYaRegistrada()) {

            asignacionPlacaCommand.getAsignacionPlaca().setUnidadEjecutora(unidadEjecutora);
            asignacionPlacaModel.agregar(asignacionPlacaCommand.getAsignacionPlaca());
            
            //Se marca como registrada
            asignacionPlacaCommand.setYaRegistrada(true);

        } else {

            asignacionPlacaModel.modificar(asignacionPlacaCommand.getAsignacionPlaca());
        }

        this.permiteModificar();
    }

    public boolean validarForm() {

        AsignacionPlaca asignacionPlaca = asignacionPlacaCommand.getAsignacionPlaca();

        if (asignacionPlaca.getCantidadSolicitada() == null || asignacionPlaca.getCantidadSolicitada() <= 0) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerAsignacionPlaca.cantidadSolicitada.requerido"));
            return false;
        }

        if (asignacionPlaca.getJustificacion().isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerAsignacionPlaca.justificacion.requerido"));
            return false;
        }

        return true;
    }

    public void aprobarSolicitud() {

        try {
            Long identificacionesLibres = identificacionModel.cantidadDisponibles(null, this.estadoPorDominioValor(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_DISPONIBLE));
            if (identificacionesLibres >= asignacionPlacaCommand.getAsignacionPlaca().getCantidadSolicitada()) {

                //Se asigna las identificaciones a la unidad
                identificacionModel.reservarIdentificaciones(asignacionPlacaCommand.getAsignacionPlaca().getCantidadSolicitada(), unidadEjecutora.getId(), Constantes.IDENTIFICACION_ESTADO_RESERVADA_UNIDAD, asignacionPlacaCommand.getAsignacionPlaca().getId());

                //Se modifica la solicitud
                this.asignacionPlacaCommand.getAsignacionPlaca().setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_ASIGNACION_PLACA, Constantes.ESTADO_ASIGNACION_PLACA_FINALIZADA));
                this.registrarDatos();

                asignacionPlacaCommand.setPresentarPanel(false);

                this.permiteModificar();

                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.controllerAsignacionPlaca.aprobar.exitosamente"));
            } else {
                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.controllerAsignacionPlaca.aprobar.sin.cantidadDisponible"));
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerAsignacionPlaca.guardarDatos"));
        }
    }

    public void rechazarSolicitud() {
        
        try {
            
            if (validarConfirmacion()) {
                //Se modifica la solicitud
                this.asignacionPlacaCommand.getAsignacionPlaca().setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_ASIGNACION_PLACA, Constantes.ESTADO_ASIGNACION_PLACA_ANULADA));
                this.registrarDatos();

                //Se registra la auditoria
                Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;
                RegistroMovimientoAsignacionPlaca registroMovimiento = new RegistroMovimientoAsignacionPlaca(
                    this.tipoPorDominioValor(Constantes.DOMINIO_REGISTRO_MOVIMIENTO, Constantes.TIPO_REGISTRO_MOVIMIENTO_RECHAZO_ASIGNACION_PLACA), 
                    this.asignacionPlacaCommand.getObservacionConfirmacion(),  telefono, new Date(), usuarioSIGEBI, asignacionPlacaCommand.getAsignacionPlaca().getEstado(), this.asignacionPlacaCommand.getAsignacionPlaca());

                registroMovimientoModel.agregar(registroMovimiento);

                //Se oculta el panel
                asignacionPlacaCommand.setPresentarPanel(false);

                this.permiteModificar();

                //Se asignan las identificaciones a la unidad
                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.controllerAsignacionPlaca.anular.exitosamente"));                
            }            

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerAsignacionPlaca.guardarDatos"));
        }
    }

    public boolean validarConfirmacion() {

        if (asignacionPlacaCommand.getObservacionConfirmacion().isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerAsignacionPlaca.observacion.requerido"));
            return false;
        }
        
        return true;
    }
    //</editor-fold>
}
