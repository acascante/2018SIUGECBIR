/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.commands.ListarExclusionesCommand;
import cr.ac.ucr.sigebi.domain.*;
import cr.ac.ucr.sigebi.models.AutorizacionRolPersonaModel;
import cr.ac.ucr.sigebi.models.ExclusionModel;
import cr.ac.ucr.sigebi.models.UnidadEjecutoraModel;
import cr.ac.ucr.sigebi.utils.Constantes;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author alvaro.cascante
 */
@Controller(value = "controllerListarExclusiones")
@Scope("session")
public class ListarExclusionesController extends BaseController {

    @Resource private AutorizacionRolPersonaModel autorizacionRolPersonaModel;
    @Resource private ExclusionModel exclusionModel;
    @Resource private UnidadEjecutoraModel unidadEjecutoraModel;
    
    private List<SolicitudExclusion> exclusiones;
    
    private ListarExclusionesCommand command;
   
    private List<SelectItem> itemsEstado;
    
    private List<SelectItem> itemsTipo;

    private List<SelectItem> itemsUnidadEjecutora;

    private boolean usuarioAdministrador;

    public ListarExclusionesController() {
        super();
        this.inicializarDatos();
    }
    
    @PostConstruct
    public final void inicializar() {
        List<Estado> estados = this.estadosPorDominio(Constantes.DOMINIO_EXCLUSION);
        if (!estados.isEmpty()) {
            this.itemsEstado = new ArrayList<SelectItem>();
            for (Estado item : estados) {
                this.itemsEstado.add(new SelectItem(item.getId(), item.getNombre()));
            }
        }
        
        List<Tipo> tipos = this.tiposPorDominio(Constantes.DOMINIO_EXCLUSION);
        if (!tipos.isEmpty()) {
            this.itemsTipo = new ArrayList<SelectItem>();
            for (Tipo item : tipos) {
                this.itemsTipo.add(new SelectItem(item.getId(), item.getNombre()));
            }
        }

        AutorizacionRolPersona administrador = autorizacionRolPersonaModel.buscar(Constantes.CODIGO_AUTORIZACION_ADMINISTRADOR, Constantes.CODIGO_ROL_ADMINISTRADOR_AUTORIZACION_ADMINISTRADOR, usuarioSIGEBI, unidadEjecutora);
        usuarioAdministrador = administrador == null ? false : true;

        if (usuarioAdministrador) {
            List<UnidadEjecutora> unidadesEjecutoras = unidadEjecutoraModel.listar();
            if (!unidadesEjecutoras.isEmpty()) {
                this.itemsUnidadEjecutora = new ArrayList<SelectItem>();
                for (UnidadEjecutora item : unidadesEjecutoras) {
                    this.itemsUnidadEjecutora.add(new SelectItem(item.getId(), item.getDescripcionSmall()));
                }
            }
        } else {
            this.command.setFltUnidadEjecutora(unidadEjecutora.getId());
        }
        
        this.inicializarListado();        
    }
    
    private void inicializarDatos() {
        this.command = new ListarExclusionesCommand();
        this.vistaOrigen = Constantes.KEY_VISTA_EXCLUSION_LISTADO;
    }
    
    private void inicializarListado() {
        this.setPrimerRegistro(1);
        this.contarExclusiones();
        this.listarExclusiones();  
    }
    
    private void contarExclusiones() {
        try {
            Long contador = exclusionModel.contar(command.getFltUnidadEjecutora(), command.getFltIdCodigo(), command.getFltFecha(), command.getFltEstado(), command.getFltTipo());
            this.setCantidadRegistros(contador.intValue());
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        }
    }
    
    private void listarExclusiones() {
        try {
            this.exclusiones = exclusionModel.listar(this.getPrimerRegistro()-1, this.getUltimoRegistro(), command.getFltUnidadEjecutora(), command.getFltIdCodigo(), command.getFltFecha(), command.getFltEstado(), command.getFltTipo());
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        }
    }
    
    public void cambioFiltro(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.inicializarListado();
    }


    public void validarFiltroId(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try {
            Integer.parseInt(value.toString());
        } catch (NumberFormatException e){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.label.exclusiones.error.cambioFiltro.id"));
            ((UIInput) component).setValid(false);
        }
    }
    
    public void validarFiltroFecha(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((Date) value);
            calendar.getTime();
        } catch (Exception e){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("ssigebi.label.exclusiones.error.cambioFiltro.fecha"));
            ((UIInput) component).setValid(false);
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public List<SolicitudExclusion> getExclusiones() {
        return exclusiones;
    }

    public void setExclusiones(List<SolicitudExclusion> exclusiones) {
        this.exclusiones = exclusiones;
    }

    public ListarExclusionesCommand getCommand() {
        return command;
    }

    public void setCommand(ListarExclusionesCommand command) {
        this.command = command;
    }

    public List<SelectItem> getItemsEstado() {
        return itemsEstado;
    }

    public void setItemsEstado(List<SelectItem> itemsEstado) {
        this.itemsEstado = itemsEstado;
    }

    public List<SelectItem> getItemsTipo() {
        return itemsTipo;
    }

    public void setItemsTipo(List<SelectItem> itemsTipo) {    
        this.itemsTipo = itemsTipo;
    }

    public boolean isUsuarioAdministrador() {
        return usuarioAdministrador;
    }

    public void setUsuarioAdministrador(boolean usuarioAdministrador) {
        this.usuarioAdministrador = usuarioAdministrador;
    }

    public List<SelectItem> getItemsUnidadEjecutora() {
        return itemsUnidadEjecutora;
    }

    public void setItemsUnidadEjecutora(List<SelectItem> itemsUnidadEjecutora) {
        this.itemsUnidadEjecutora = itemsUnidadEjecutora;
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
        this.listarExclusiones();
    }

    public void siguiente(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getSiguientePagina();
        this.listarExclusiones();
    }

    public void anterior(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getPaginaAnterior();
        this.listarExclusiones();
    }

    public void primero(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.setPrimerRegistro(1);
        this.listarExclusiones();
    }

    public void ultimo(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getPrimerRegistroUltimaPagina();
        this.listarExclusiones();
    }

    public void cambioRegistrosPorPagina(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.setCantRegistroPorPagina(Integer.parseInt(pEvent.getNewValue().toString()));          
        this.setPrimerRegistro(1);
        this.listarExclusiones();
    }
    // </editor-fold>
}
