/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.sigebi.commands.ListarNotificacionesCommand;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Notificacion;
import cr.ac.ucr.sigebi.models.NotificacionModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author alvaro.cascante
 */
@Controller(value = "controllerListarNotificaciones")
@Scope("session")
public class ListarNotificacionesController extends BaseController {
    
    @Resource private NotificacionModel notificacionModel;
    
    private List<Notificacion> notificaciones;
    private List<SelectItem> itemsEstado;
    private ListarNotificacionesCommand command;
   
    public ListarNotificacionesController() {
        super();
        this.inicializarDatos();
    }
    
    @PostConstruct
    public final void inicializar() {
        this.inicializarListado();
        List<Estado> listEstados = this.estadosPorDominio(Constantes.DOMINIO_NOTIFICACION);
        if (!listEstados.isEmpty()) {
            this.itemsEstado = new ArrayList<SelectItem>();
            for (Estado item : listEstados) {
                this.itemsEstado.add(new SelectItem(item.getId(), item.getNombre()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
            }
        }
    }
    
    public void regresarListado() {
        Util.navegar(vistaOrigen);
        this.inicializarDatos();
        this.inicializarListado();
    }
    
    private void inicializarDatos() {
        this.vistaOrigen = Constantes.KEY_VISTA_NOTIFICACION_LISTADO;
        this.command = new ListarNotificacionesCommand();
    }
    
    private void inicializarListado() {
        this.setPrimerRegistro(1);
        this.contarNotificaciones();
        this.listarNotificaciones();
    }
    
    private void contarNotificaciones() {
        try {
            Long contador = this.notificacionModel.contar(command.getFltIdCodigo(), command.getFltAsunto(), command.getFltDestinatario(), command.getFltMensaje(), command.getFltFecha(), command.getFltEstado());
            this.setCantidadRegistros(contador.intValue());
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        }
    }
    
    private void listarNotificaciones() {
        try {
            this.notificaciones = this.notificacionModel.listar(this.getPrimerRegistro()-1, this.getUltimoRegistro(), command.getFltIdCodigo(), command.getFltAsunto(), command.getFltDestinatario(), command.getFltMensaje(), command.getFltFecha(), command.getFltEstado());
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        }
    }
    
    public void enviarNotificacion(ActionEvent event) {
        Notificacion notificacion = (Notificacion) event.getComponent().getAttributes().get(ListarNotificacionesCommand.KEY_NOTIFICACION);
        try {
            this.notificacionModel.enviarCorreo(notificacion);
            notificacion.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_NOTIFICACION, Constantes.ESTADO_NOTIFICACION_ENVIADA));
            notificacion.setPrioridad(Constantes.PRIORIDAD_NOTIFICACION_URGENTE);
            Mensaje.agregarInfo(Util.getEtiquetas("sigebi.label.notificaciones.enviarNotificacion.ok"));
        } catch (FWExcepcion err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.label.notificaciones.error.envia"));
            notificacion.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_NOTIFICACION, Constantes.ESTADO_NOTIFICACION_ENVIO_FALLIDO));
        } finally {
            this.notificacionModel.salvar(notificacion);
        }
    }
    
    public void cambioFiltro(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        this.inicializarListado();
    }

    public void validarFiltroId(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try {
            Integer.parseInt(value.toString());
        } catch (NumberFormatException e){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.label.notificaciones.error.cambioFiltro.id"));
            ((UIInput) component).setValid(false);
        }
    }
    
    public void validarFiltroFecha(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((Date) value);
            calendar.getTime();
        } catch (Exception e){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.label.notificaciones.error.cambioFiltro.fecha"));
            ((UIInput) component).setValid(false);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public ListarNotificacionesCommand getCommand() {
        return command;
    }

    public void setCommand(ListarNotificacionesCommand command) {
        this.command = command;
    }

    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public List<SelectItem> getItemsEstado() {
        return itemsEstado;
    }

    public void setItemsEstado(List<SelectItem> itemsEstado) {
        this.itemsEstado = itemsEstado;
    }
    // </editor-fold>
   
    // <editor-fold defaultstate="collapsed" desc="Paginacion">
    public void irPagina(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        int numeroPagina = Integer.parseInt(Util.getRequestParameter("numPag"));
        this.getPrimerRegistroPagina(numeroPagina);
        this.listarNotificaciones();
    }

    public void siguiente(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getSiguientePagina();
        this.listarNotificaciones();
    }

    public void anterior(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getPaginaAnterior();
        this.listarNotificaciones();
    }

    public void primero(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.setPrimerRegistro(1);
        this.listarNotificaciones();
    }

    public void ultimo(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getPrimerRegistroUltimaPagina();
        this.listarNotificaciones();
    }

    public void cambioRegistrosPorPagina(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.setCantRegistroPorPagina(Integer.parseInt(pEvent.getNewValue().toString()));          
        this.setPrimerRegistro(1);
        this.listarNotificaciones();
    }
    // </editor-fold>
}
