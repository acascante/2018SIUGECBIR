/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import com.icesoft.faces.component.inputfile.FileInfo;
import com.icesoft.faces.component.inputfile.InputFile;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.commands.ConvenioCommand;
import cr.ac.ucr.sigebi.domain.Adjunto;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.models.AdjuntoModel;
import cr.ac.ucr.sigebi.models.ConvenioModel;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.Resource;
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
@Controller(value = "controllerAgregarConvenios")
@Scope("session")
public class AgregarConvenioController extends BaseController {

    @Resource private ConvenioModel convenioModel;
    @Resource private AdjuntoModel adjuntoModel;
    
    private ConvenioCommand command;
    
    private String mensajeExito;
    private String mensaje;
  
    public AgregarConvenioController() {
        super();
    }
  
    private void inicializar() {
        this.mensajeExito = "";
        this.mensaje = "";
        this.command = new ConvenioCommand();
    }
    
    public void guardarDatos() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            UIViewRoot root = context.getViewRoot();
            UIInput component =  new UIInput();

            String messageValidacion = validarForm(root, component);
            if (Constantes.OK.equals(messageValidacion)) {
                Estado estadoActivo = this.estadoPorDominioValor(Constantes.DOMINIO_CONVENIO, Constantes.ESTADO_CONVENIO_ACTIVO);
                command.setEstado(estadoActivo);
                if (command.getId() == null || command.getId() == 0) {
                    convenioModel.salvar(command.getConvenio());
                    mensajeExito = "Los datos se salvaron con éxito.";
                } else {
                    convenioModel.salvar(command.getConvenio());
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
    
    //<editor-fold defaultstate="collapsed" desc="Adjuntar Archivo">  
    public void agregarAdjunto(ActionEvent pEvent) {
        try {
            InputFile inputFile = (InputFile) pEvent.getSource();
            FileInfo fileInfo = inputFile.getFileInfo();
            if (fileInfo.getFileName() != null) {
                // TODO Buscar tipo correcto
                Tipo tipoAdjunto = this.tipoPorDominioValor(Constantes.DOMINIO_ADJUNTO, Constantes.TIPO_ADJUNTO_DOCUMENTO);
                String detalleAdjunto = new String();

                Adjunto adjunto = new Adjunto();
                adjunto.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO));
                adjunto.setTipo(tipoAdjunto);
//                adjunto.setIdReferencia(informe.getId());
                adjunto.setUrl("upload/informesTecnicos/" + fileInfo.getFileName());
                if (detalleAdjunto != null && detalleAdjunto.length() > 0) {
                    adjunto.setDetalle(detalleAdjunto);
                } else {
                    adjunto.setDetalle(fileInfo.getFileName());
                }
                adjuntoModel.agregar(adjunto);

                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.error.informeTecnicoController.adjunto.agregar.exitosamente"));
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.informeTecnicoController.agregarAdjunto"));
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Validaciones">  
    public String validarForm(UIViewRoot root, UIInput component) {
        // TODO definir validaciones para convenios
        if (!command.getPrestar() && !command.getRecibirPrestamo()) {
            component = (UIInput) root. findComponent("frmDetalleConvenio:txtRecibir");
            return Util.getEtiquetas("sigebi.error.controllerAgregarConvenios.error.asunto.nulo");
        }
        
        if (command.getInstitucion().isEmpty()) {
            component = (UIInput) root. findComponent("frmDetalleConvenio:txtInstitucion");
            return Util.getEtiquetas("sigebi.error.controllerAgregarConvenios.error.correo.nulo");
        }        
        return Constantes.OK;
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

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public ConvenioCommand getCommand() {
        return command;
    }

    public void setCommand(ConvenioCommand convenioCommand) {
        this.command = convenioCommand;
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