/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.models.EstadoModel;
import cr.ac.ucr.sigebi.models.NotificacionModel;
import cr.ac.ucr.sigebi.commands.NotificacionCommand;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.validator.ValidatorException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author alvaro.cascante
 */
@Controller(value = "controllerAgregarNotificaciones")
@Scope("session")
public class AgregarNotificaionController {

    @Resource
    private NotificacionModel notificacionModel;
    
    @Resource
    private EstadoModel estadoModel;
    
    private NotificacionCommand command;
    
    private String mensajeExito;
    private String mensaje;
    private String vistaOrigen;
  
    public AgregarNotificaionController() {
        super();
    }
  
    private void inicializar() {
        this.mensajeExito = "";
        this.mensaje = "";
        this.command = new NotificacionCommand();
    }
    
    public void guardarDatos() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            UIViewRoot root = context.getViewRoot();
            UIInput component =  new UIInput();
            String messageValidacion = validarForm(root, component);
            if (Constantes.OK.equals(messageValidacion)) {
                command.setEstado(estadoModel.buscarPorDominioNombre(Constantes.DOMINIO_NOTIFICACION, Constantes.ESTADO_NOTIFICACION_CREADA_DESC));
                if (command.getIdNotificacion() == null || command.getIdNotificacion() == 0) {
                    notificacionModel.salvar(command.getNotificacion());
                    mensajeExito = "Los datos se salvaron con éxito.";
                } else {
                    notificacionModel.salvar(command.getNotificacion());
                    mensajeExito = "Los datos se actualizaron con éxito.";
                }
            } else {
                component.setValid(false);
                Mensaje.agregarErrorAdvertencia(messageValidacion);
            }
        } catch (FWExcepcion err) {
            mensaje = err.getMessage();
        }
    }
    
    public void nuevoRegistro(ActionEvent event) {
        try{
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            inicializar();
            this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
            Util.navegar(Constantes.VISTA_NOTIFICACION_NUEVA);
        } catch (FWExcepcion err) {
            mensaje = err.getMessage();
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Validaciones">
    public void validarDestinatario(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String destinatario = (String) value;
        
        if (destinatario.isEmpty()) {
            ((UIInput) component).setValid(false);
            FacesMessage msg = new FacesMessage("Campo requerido");
            context.addMessage(component.getClientId(context), msg);
        } 
        
        if (!context.getMessages().hasNext()) {
            String[] correos = destinatario.split(",");
            for (String correo : correos) {
                if (!validarCorreo(correo.trim())) {
                    Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerAgregarNotificaciones.error.correo.formato"));
                    ((UIInput) component).setValid(false);
                    break;
                }
            }
        }
    }
     
    private boolean validarCorreo(String correo) {
        Pattern pattern = Pattern.compile(Constantes.PATTERN_EMAIL);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }
    
    public void validarFecha(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try {
            Date fecha = (Date)value;
            
            Date today = new Date();
            Calendar calendar = Calendar.getInstance(Constantes.DEFAULT_TIME_ZONE);
            calendar.setTime(today);
            calendar.add(Calendar.DATE, -1);

            if (fecha.before(calendar.getTime())) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerAgregarNotificaciones.error.fecha.anterior"));
                ((UIInput) component).setValid(false); 
            } 
        } catch (Exception e ) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerAgregarNotificaciones.error.fecha.formato"));
            ((UIInput) component).setValid(false);
        }
    }
    
    public String validarForm(UIViewRoot root, UIInput component) {
        if (command.getAsunto().isEmpty()) {
            component = (UIInput) root. findComponent("frmDetalleNotificacion:txtAsunto");
            return Util.getEtiquetas("sigebi.error.controllerAgregarNotificaciones.error.asunto.nulo");
        }
        
        if (command.getDestinatario().isEmpty()) {
            component = (UIInput) root. findComponent("frmDetalleNotificacion:txtDestinatario");
            return Util.getEtiquetas("sigebi.error.controllerAgregarNotificaciones.error.correo.nulo");
        }
        
        if (command.getMensajeCorreo().isEmpty()) {
            component = (UIInput) root. findComponent("frmDetalleNotificacion:txtMensajeCorreo");
            return Util.getEtiquetas("sigebi.error.controllerAgregarNotificaciones.error.mensaje.nulo");
        }
        
        if (command.getFecha() == null) {
            component = (UIInput) root. findComponent("frmDetalleNotificacion:txtFecha");
            return Util.getEtiquetas("sigebi.error.controllerAgregarNotificaciones.error.fecha.nulo");
        }
        return Constantes.OK;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public NotificacionCommand getCommand() {
        return command;
    }

    public void setCommand(NotificacionCommand notificacionCommand) {
        this.command = notificacionCommand;
    }

    public String getMensajeExito() {
        return mensajeExito;
    }

    public void setMensajeExito(String mensajeExito) {
        this.mensajeExito = mensajeExito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getVistaOrigen() {
        return vistaOrigen;
    }

    public void setVistaOrigen(String vistaOrigen) {
        this.vistaOrigen = vistaOrigen;
    }
    //</editor-fold>
}