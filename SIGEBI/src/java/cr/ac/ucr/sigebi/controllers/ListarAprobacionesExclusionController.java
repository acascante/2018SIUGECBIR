/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.sigebi.domain.*;
import cr.ac.ucr.sigebi.models.AutorizacionRolPersonaModel;
import cr.ac.ucr.sigebi.models.UnidadEjecutoraModel;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.commands.ListarAprobacionesExclusionCommand;
import cr.ac.ucr.sigebi.models.DocumentoModel;
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
@Controller(value = "controllerListarAprobaciones")
@Scope("session")
public class ListarAprobacionesExclusionController extends BaseController {

    @Resource private AutorizacionRolPersonaModel autorizacionRolPersonaModel;
    @Resource private DocumentoModel documentoModel;
    @Resource private UnidadEjecutoraModel unidadEjecutoraModel;
    
    private List<DocumentoAprobacionExclusion> documentos;
    
    private ListarAprobacionesExclusionCommand command;
   
    private List<SelectItem> itemsEstado;

    private List<SelectItem> itemsUnidadEjecutora;

    private boolean usuarioAdministrador;
    
    public ListarAprobacionesExclusionController() {
        super();
        this.inicializarDatos();
    }
    
    @PostConstruct
    public final void inicializar() {
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
        
        List<Estado> estados = this.estadosPorDominio(Constantes.DOMINIO_APROBACION_EXCLUSION);
        if (!estados.isEmpty()) {
            this.itemsEstado = new ArrayList<SelectItem>();
            for (Estado item : estados) {
                this.itemsEstado.add(new SelectItem(item.getId(), item.getNombre()));
            }
        }
    }
    
    private void inicializarDatos() {
        this.command = new ListarAprobacionesExclusionCommand();
        this.vistaOrigen = Constantes.KEY_VISTA_APROBACION_LISTADO;
    }
    
    private void inicializarListado() {
        this.setPrimerRegistro(1);
        this.contarRegistros();
        this.listarRegistros();  
    }
    
    private void contarRegistros() {
        try {
            Long contador = documentoModel.contarAprobacionesExclusion(command.getFltUnidadEjecutora(), command.getFltIdCodigo(), command.getFltAutorizacion(), command.getFltFecha(), command.getFltEstado());
            this.setCantidadRegistros(contador.intValue());
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        }
    }
    
    private void listarRegistros() {
        try {
            this.documentos = documentoModel.listarAprobacionesExclusion(command.getFltUnidadEjecutora(), command.getFltIdCodigo(), command.getFltAutorizacion(), command.getFltFecha(), command.getFltEstado(), this.getPrimerRegistro()-1, this.getUltimoRegistro());
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
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.label.aprobacion.error.id"));
            ((UIInput) component).setValid(false);
        }
    }
    
    public void validarFiltroFecha(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((Date) value);
            calendar.getTime();
        } catch (Exception e){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.label.aprobacion.error.fecha"));
            ((UIInput) component).setValid(false);
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public List<DocumentoAprobacionExclusion> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentoAprobacionExclusion> documentos) {
        this.documentos = documentos;
    }

    public ListarAprobacionesExclusionCommand getCommand() {
        return command;
    }

    public void setCommand(ListarAprobacionesExclusionCommand command) {
        this.command = command;
    }

    public List<SelectItem> getItemsEstado() {
        return itemsEstado;
    }

    public void setItemsEstado(List<SelectItem> itemsEstado) {
        this.itemsEstado = itemsEstado;
    }

    public List<SelectItem> getItemsUnidadEjecutora() {
        return itemsUnidadEjecutora;
    }

    public void setItemsUnidadEjecutora(List<SelectItem> itemsUnidadEjecutora) {
        this.itemsUnidadEjecutora = itemsUnidadEjecutora;
    }

    public boolean isUsuarioAdministrador() {
        return usuarioAdministrador;
    }

    public void setUsuarioAdministrador(boolean usuarioAdministrador) {
        this.usuarioAdministrador = usuarioAdministrador;
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
        this.listarRegistros();
    }

    public void siguiente(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getSiguientePagina();
        this.listarRegistros();
    }

    public void anterior(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getPaginaAnterior();
        this.listarRegistros();
    }

    public void primero(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.setPrimerRegistro(1);
        this.listarRegistros();
    }

    public void ultimo(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getPrimerRegistroUltimaPagina();
        this.listarRegistros();
    }

    public void cambioRegistrosPorPagina(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.setCantRegistroPorPagina(Integer.parseInt(pEvent.getNewValue().toString()));          
        this.setPrimerRegistro(1);
        this.listarRegistros();
    }
    // </editor-fold>
}
