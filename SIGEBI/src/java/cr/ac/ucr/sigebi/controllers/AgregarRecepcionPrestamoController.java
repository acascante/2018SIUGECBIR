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
import cr.ac.ucr.sigebi.commands.ListarRecepcionPrestamosCommand;
import cr.ac.ucr.sigebi.commands.RecepcionPrestamoCommand;
import cr.ac.ucr.sigebi.domain.Adjunto;
import cr.ac.ucr.sigebi.domain.Convenio;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.RecepcionPrestamo;
import cr.ac.ucr.sigebi.domain.RegistroMovimientoRecepcionPrestamo;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.models.AdjuntoModel;
import cr.ac.ucr.sigebi.models.ConvenioModel;
import cr.ac.ucr.sigebi.models.RecepcionPrestamoModel;
import cr.ac.ucr.sigebi.models.RegistroMovimientoModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author alvaro.cascante
 */
@Controller(value = "controllerAgregarRecepcionPrestamos")
@Scope("session")
public class AgregarRecepcionPrestamoController extends BaseController {

    @Resource private AdjuntoModel adjuntoModel;
    @Resource private ConvenioModel convenioModel;
    @Resource private RecepcionPrestamoModel recepcionPrestamoModel;
    @Resource private RegistroMovimientoModel registroMovimientoModel;
    
    private RecepcionPrestamoCommand command;
    
    private List<SelectItem> itemsConvenio;
    private Map<Long, Convenio> convenios;
    
    private String mensajeExito;
    private String mensaje;
    private boolean prestamoRegistrado;
    
    public AgregarRecepcionPrestamoController() {
        super();
    }
  
    private void inicializarNuevo() {
        this.command = new RecepcionPrestamoCommand();
        this.prestamoRegistrado = false;
    }
        
    private void inicializarDetalle(RecepcionPrestamo prestamo) {
        this.command = new RecepcionPrestamoCommand(prestamo);
        this.prestamoRegistrado = true;
        inicializarDatos();
    }
    
    private void inicializarDatos() {
        this.mensajeExito = "";
        this.mensaje = "";
        
        List<Convenio> listConvenios = convenioModel.listarActivos(unidadEjecutora, new Date());
        if (!listConvenios.isEmpty()) {
            this.itemsConvenio = new ArrayList<SelectItem>();
            this.convenios = new HashMap<Long, Convenio> ();
            for (Convenio item : listConvenios) {
                this.itemsConvenio.add(new SelectItem(item.getId(), item.getInstitucion()));
                this.convenios.put(item.getId(), item);
            }
        }
    }
    
    public void guardarDatos() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            UIViewRoot root = context.getViewRoot();
            String messageValidacion = validarForm(root);
            if (Constantes.OK.equals(messageValidacion)) {
                Estado estadoCreado = this.estadoPorDominioValor(Constantes.DOMINIO_RECEPCION_PRESTAMO, Constantes.ESTADO_PRESTAMO_CREADO);
                this.command.setEstado(estadoCreado);
                
                Convenio convenio = convenios.get(this.command.getIdConvenio());
                RecepcionPrestamo prestamo = this.command.getRecepcionPrestamo(convenio);
                this.recepcionPrestamoModel.salvar(prestamo);
                if (!prestamoRegistrado) {
                    this.mensajeExito = "Los datos se salvaron con éxito.";
                    this.prestamoRegistrado = true;
                    this.command.setId(prestamo.getId());
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
            Util.navegar(Constantes.VISTA_RECEPCION_PRESTAMO_NUEVO);
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

        Long id = (Long)event.getComponent().getAttributes().get(ListarRecepcionPrestamosCommand.KEY_RECEPCION_PRESTAMO);
        RecepcionPrestamo prestamo = this.recepcionPrestamoModel.buscarPorId(id);
        inicializarDetalle(prestamo);
        this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
        Util.navegar(Constantes.VISTA_RECEPCION_PRESTAMO_NUEVO);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Almacenar Observacion">  
    private void almacenarObservacion() {
        if (!this.command.getObservacion().isEmpty()) {
            Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;

            RegistroMovimientoRecepcionPrestamo registroMovimiento = new RegistroMovimientoRecepcionPrestamo(
                this.tipoPorDominioValor(Constantes.DOMINIO_REGISTRO_MOVIMIENTO, Constantes.TIPO_REGISTRO_MOVIMIENTO_CAMBIO_RECEPCION_PRESTAMO), 
                this.command.getObservacion(),  telefono, new Date(), usuarioSIGEBI, this.command.getEstado(), 
                this.command.getRecepcionPrestamo(convenios.get(this.command.getIdConvenio())));
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
                Tipo tipoAdjunto = this.tipoPorDominioValor(Constantes.DOMINIO_ADJUNTO, Constantes.TIPO_ADJUNTO_RECEPCION_PRESTAMO);
                Adjunto adjunto = new Adjunto();
                adjunto.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO));
                adjunto.setTipo(tipoAdjunto);
                adjunto.setIdReferencia(this.command.getId());
                adjunto.setUrl("upload/recepcionPrestamos/" + fileInfo.getFileName());
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
        if (command.getIdConvenio().equals(Constantes.DEFAULT_ID)) {
            return Util.getEtiquetas("sigebi.label.recepcionPrestamo.error.validacion.convenio");
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

            if (fecha.before(calendar.getTime()) && !this.prestamoRegistrado) {
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
    public RecepcionPrestamoCommand getCommand() {
        return command;
    }

    public void setCommand(RecepcionPrestamoCommand command) {
        this.command = command;
    }

    public List<SelectItem> getItemsConvenio() {
        return itemsConvenio;
    }

    public void setItemsConvenio(List<SelectItem> itemsConvenio) {
        this.itemsConvenio = itemsConvenio;
    }

    public Map<Long, Convenio> getConvenios() {
        return convenios;
    }

    public void setConvenios(Map<Long, Convenio> convenios) {
        this.convenios = convenios;
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

    public boolean isPrestamoRegistrado() {
        return prestamoRegistrado;
    }

    public void setPrestamoRegistrado(boolean prestamoRegistrado) {
        this.prestamoRegistrado = prestamoRegistrado;
    }
    //</editor-fold>
}