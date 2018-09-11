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
import cr.ac.ucr.sigebi.commands.ListarConveniosCommand;
import cr.ac.ucr.sigebi.domain.Adjunto;
import cr.ac.ucr.sigebi.domain.Convenio;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.RegistroMovimientoConvenio;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.models.AdjuntoModel;
import cr.ac.ucr.sigebi.models.ConvenioModel;
import cr.ac.ucr.sigebi.models.RegistroMovimientoModel;
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

    @Resource private AdjuntoModel adjuntoModel;
    @Resource private ConvenioModel convenioModel;
    @Resource private RegistroMovimientoModel registroMovimientoModel;
    
    private ConvenioCommand command;
    
    private String mensajeExito;
    private String mensaje;
    private boolean convenioRegistrado;
    
    public AgregarConvenioController() {
        super();
    }
  
    private void inicializarNuevo() {
        this.command = new ConvenioCommand(this.unidadEjecutora);
        this.convenioRegistrado = false;
        inicializarDatos();
    }
        
    private void inicializarDetalle(Convenio convenio) {
        this.command = new ConvenioCommand(convenio);
        this.convenioRegistrado = true;
        inicializarDatos();
    }
    
    private void inicializarDatos() {
        this.mensajeExito = "";
        this.mensaje = "";    
    }
    
    public void guardarDatos() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            UIViewRoot root = context.getViewRoot();
            String messageValidacion = validarForm(root);
            if (Constantes.OK.equals(messageValidacion)) {
                Estado estadoActivo = this.estadoPorDominioValor(Constantes.DOMINIO_CONVENIO, Constantes.ESTADO_CONVENIO_ACTIVO);
                this.command.setEstado(estadoActivo);
                Convenio convenio = this.command.getConvenio();
                this.convenioModel.salvar(convenio);
                if (!convenioRegistrado) {
                    this.mensajeExito = "Los datos se salvaron con éxito.";
                    this.convenioRegistrado = true;
                    this.command.setId(convenio.getId());
                } else {
                    almacenarObservacion();
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
            inicializarNuevo();
            this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
            Util.navegar(Constantes.KEY_VISTA_CONVENIO_NUEVO);
        } catch (FWExcepcion err) {
            this.mensaje = err.getMessage();
        }
    }
    
    public void detalleRegistro(ActionEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }

        Long id = (Long)event.getComponent().getAttributes().get(ListarConveniosCommand.KEY_CONVENIO);
        Convenio convenio = this.convenioModel.buscarPorId(id);
        inicializarDetalle(convenio);
        this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
        Util.navegar(Constantes.KEY_VISTA_CONVENIO_NUEVO);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Almacenar Observacion">  
    private void almacenarObservacion() {
        if (!this.command.getObservacion().isEmpty()) {
            Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;

            RegistroMovimientoConvenio registroMovimiento = new RegistroMovimientoConvenio(
                this.tipoPorDominioValor(Constantes.DOMINIO_REGISTRO_MOVIMIENTO, Constantes.TIPO_REGISTRO_MOVIMIENTO_CAMBIO_CONVENIO), 
                this.command.getObservacion(),  telefono, new Date(), usuarioSIGEBI, this.command.getEstado(), this.command.getConvenio());
                this.registroMovimientoModel.agregar(registroMovimiento);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Adjuntar Archivo">  
    public void agregarAdjunto(ActionEvent event) {
        try {
            InputFile inputFile = (InputFile) event.getSource();
            FileInfo fileInfo = inputFile.getFileInfo();
            if (fileInfo.getFileName() != null) {
                // TODO Buscar tipo correcto
                Tipo tipoAdjunto = this.tipoPorDominioValor(Constantes.DOMINIO_ADJUNTO, Constantes.TIPO_ADJUNTO_CONVENIO);
                Adjunto adjunto = new Adjunto();
                adjunto.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO));
                adjunto.setTipo(tipoAdjunto);
                adjunto.setIdReferencia(this.command.getId());
                adjunto.setUrl("upload/convenios/" + fileInfo.getFileName());
                adjunto.setDetalle(fileInfo.getFileName());
                this.adjuntoModel.agregar(adjunto);
                this.mensajeExito = "Los archivo se adjunto con exito.";
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Validaciones">  
    public String validarForm(UIViewRoot root) {
        if (!this.command.getPrestar() && !this.command.getRecibirPrestamo()) {
            return Util.getEtiquetas("sigebi.label.convenios.error.prestar.recibir");
        }
        
        if (this.command.getInstitucion().isEmpty()) {
            return Util.getEtiquetas("sigebi.label.convenios.error.institucion");
        }        

        if (this.command.getResponsable().isEmpty()) {
            return Util.getEtiquetas("sigebi.label.convenios.error.responsable");
        }        

        if (this.command.getFechaFin().before(this.command.getFechaInicio())) {
            return Util.getEtiquetas("sigebi.label.convenios.error.fechas");
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

            if (fecha.before(calendar.getTime()) && !this.convenioRegistrado) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.label.convenios.error.fecha.menor.hoy"));
                ((UIInput) component).setValid(false); 
            } 
        } catch (Exception e ) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.label.convenios.error.fecha.invalida"));
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

    public boolean isConvenioRegistrado() {
        return convenioRegistrado;
    }

    public void setConvenioRegistrado(boolean convenioRegistrado) {
        this.convenioRegistrado = convenioRegistrado;
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