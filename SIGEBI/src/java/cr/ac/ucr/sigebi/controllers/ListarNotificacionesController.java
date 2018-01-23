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
import cr.ac.ucr.sigebi.models.NotificacionModel;
import cr.ac.ucr.sigebi.commands.ListarNotificacionesCommand;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Notificacion;
import cr.ac.ucr.sigebi.models.AccesorioModel;
import cr.ac.ucr.sigebi.models.EstadoModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    
    @Resource
    private NotificacionModel notificacionModel;
    @Resource
    private EstadoModel estadoModel;
    
    private List<Notificacion> notificaciones;
    private Map<Integer, Estado> estados;
    private List<SelectItem> itemsEstado;
    private ListarNotificacionesCommand command;
   
    public ListarNotificacionesController() {
        super();
    }
    
    @PostConstruct
    public final void inicializar() {
        command = new ListarNotificacionesCommand();
        this.setPrimerRegistro(1);
        this.contarNotificaciones();
        this.listarNotificaciones();
        this.vistaOrigen = Constantes.VISTA_NOTIFICACION_LISTADO;
        
        List<Estado> listEstados = estadoModel.listarPorDominio(Constantes.DOMINIO_NOTIFICACION);
        if (!listEstados.isEmpty()) {
            estados = new HashMap<Integer, Estado>();
            itemsEstado = new ArrayList<SelectItem>();
            for (Estado item : listEstados) {
                itemsEstado.add(new SelectItem(item.getId(), item.getNombre()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                estados.put(item.getValor(), item); // Valor + Objeto Estado -- Usado para obtener un estado al enviar una notificacion y modificar el estado de la misma
            }
        }
    }
    
    public void regresarListado() {
        Util.navegar(vistaOrigen);
        inicializar();
    }
    
    private void contarNotificaciones() {
        try {
            Long contador = notificacionModel.contar(command.getFltIdCodigo(), command.getFltAsunto(), command.getFltDestinatario(), command.getFltMensaje(), command.getFltFecha(), command.getFltEstado());
            this.setCantidadRegistros(contador.intValue());
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (NumberFormatException e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarNotificaciones.contarNotificaciones"));
        }
    }
    
    private void listarNotificaciones() {
        try {
            this.notificaciones = notificacionModel.listar(this.getPrimerRegistro()-1, this.getUltimoRegistro(), command.getFltIdCodigo(), command.getFltAsunto(), command.getFltDestinatario(), command.getFltMensaje(), command.getFltFecha(), command.getFltEstado());
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (NumberFormatException e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarNotificaciones.listarNotificaciones"));
        }
    }
    
    public void enviarNotificacion(ActionEvent event) {
        Notificacion notificacion = (Notificacion) event.getComponent().getAttributes().get(ListarNotificacionesCommand.KEY_NOTIFICACION);
        try {
            notificacionModel.enviarCorreo(notificacion);
            notificacion.setEstado(estados.get(Constantes.ESTADO_NOTIFICACION_ENVIADA));
            notificacion.setPrioridad(Constantes.PRIORIDAD_NOTIFICACION_URGENTE);
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.ok.controllerListarNotificaciones.enviarNotificacion"));
        } catch (FWExcepcion err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarNotificaciones.enviarNotificacion"));
            notificacion.setEstado(estados.get(Constantes.ESTADO_NOTIFICACION_ENVIO_FALLIDO));
        } finally {
            notificacionModel.salvar(notificacion);
        }
    }
    
    public void cambioFiltro(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            this.contarNotificaciones();
            this.setPrimerRegistro(1);
            this.listarNotificaciones();
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarNotificaciones.cambioFiltro"));
        }
    }

    public void validarFiltroId(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try {
            Integer.parseInt(value.toString());
        } catch (NumberFormatException e){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarNotificaciones.cambioFiltro.id"));
            ((UIInput) component).setValid(false);
        }
    }
    
    public void validarFiltroFecha(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((Date) value);
            calendar.getTime();
        } catch (Exception e){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarNotificaciones.cambioFiltro.fecha"));
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
