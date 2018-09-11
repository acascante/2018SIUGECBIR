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
import cr.ac.ucr.sigebi.commands.NotificacionCommand;
import cr.ac.ucr.sigebi.domain.Notificacion;
import cr.ac.ucr.sigebi.models.NotificacionModel;
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
public class AgregarNotificaionController extends BaseController {

    @Resource private NotificacionModel notificacionModel;
    
    private NotificacionCommand command;
    
    private String mensajeExito;
    private String mensaje;
    private Boolean notificacionRegistrada;
  
    public AgregarNotificaionController() {
        super();
    }
  
    private void inicializar() {
        this.mensajeExito = "";
        this.mensaje = "";
        this.command = new NotificacionCommand();
        this.notificacionRegistrada = false;
    }
    
    public void guardarDatos() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            UIViewRoot root = context.getViewRoot();
            String messageValidacion = validarForm(root);
            if (Constantes.OK.equals(messageValidacion)) {
                this.command.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_NOTIFICACION, Constantes.ESTADO_NOTIFICACION_CREADA));
                Notificacion notificacion = this.command.getNotificacion();
                if (!this.notificacionRegistrada) {
                    this.notificacionModel.salvar(notificacion);
                    this.notificacionRegistrada = true;
                    this.mensajeExito = "Los datos se salvaron con éxito.";
                    this.command.setIdNotificacion(notificacion.getId());
                } else {
                    this.notificacionModel.salvar(notificacion);
                    this.mensajeExito = "Los datos se actualizaron con éxito.";
                }
            } else {
                Mensaje.agregarErrorAdvertencia(messageValidacion);
            }
        } catch (FWExcepcion err) {
            this.mensaje = err.getMessage();
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
            Util.navegar(Constantes.KEY_VISTA_NOTIFICACION_NUEVA);
        } catch (FWExcepcion err) {
            this.mensaje = err.getMessage();
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
                    Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.label.notificaciones.error.correo.formato"));
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
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.label.notificaciones.error.fecha.anterior"));
                ((UIInput) component).setValid(false); 
            } 
        } catch (Exception e ) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.label.notificaciones.error.fecha.formato"));
            ((UIInput) component).setValid(false);
        }
    }
    
    public String validarForm(UIViewRoot root) {
        if (command.getAsunto().isEmpty()) {
            return Util.getEtiquetas("sigebi.label.notificaciones.error.asunto.nulo");
        }
        
        if (command.getDestinatario().isEmpty()) {
            return Util.getEtiquetas("sigebi.label.notificaciones.error.correo.nulo");
        }
        
        if (command.getMensajeCorreo().isEmpty()) {
            return Util.getEtiquetas("sigebi.label.notificaciones.error.mensaje.nulo");
        }
        
        if (command.getFecha() == null) {
            return Util.getEtiquetas("sigebi.label.notificaciones.error.fecha.nulo");
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
    //</editor-fold>
}