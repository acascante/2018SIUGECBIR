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
import cr.ac.ucr.sigebi.commands.ListarExclusionesCommand;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Exclusion;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.models.EstadoModel;
import cr.ac.ucr.sigebi.models.ExclusionModel;
import cr.ac.ucr.sigebi.models.TipoModel;
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
@Controller(value = "controllerListarExclusiones")
@Scope("session")
public class ListarExclusionesController extends BaseController {

    @Resource
    private ExclusionModel exclusionModel;
    
    @Resource
    private EstadoModel estadoModel;
    
    @Resource
    private TipoModel tipoModel;
    
    private List<Exclusion> exclusiones;
    
    private ListarExclusionesCommand command;
   
    private List<Estado> estados;
    private List<SelectItem> itemsEstado;
    
    private List<Tipo> tipos;
    private List<SelectItem> itemsTipo;
    
    public ListarExclusionesController() {
        super();
    }
    
    @PostConstruct
    public final void inicializar() {
        command = new ListarExclusionesCommand();
        
        this.setPrimerRegistro(1);
        this.contarExclusiones();
        this.listarExclusiones();  
        
        this.vistaOrigen = Constantes.VISTA_EXCLUSION_LISTADO;
        
        estados = estadoModel.listarPorDominio(Constantes.DOMINI0_EXCLUSION);
        if (!estados.isEmpty()) {
            itemsEstado = new ArrayList<SelectItem>();
            for (Estado item : estados) {
                itemsEstado.add(new SelectItem(item.getIdEstado(), item.getNombre()));
            }
        }
        
        tipos = tipoModel.listarPorDominio(Constantes.DOMINI0_EXCLUSION);
        if (!tipos.isEmpty()) {
            itemsTipo = new ArrayList<SelectItem>();
            for (Tipo item : tipos) {
                itemsTipo.add(new SelectItem(item.getIdTipo(), item.getNombre()));
            }
        }
    }
    
    public void regresarListado() {
        Util.navegar(vistaOrigen);
        inicializar();
    }
    
    private void contarExclusiones() {
        try {
            //TODO definir si se va a utilizar solo el numero de la unidad ejecutora o todo el objeto
            Long contador = exclusionModel.contar(unidadEjecutora, command.getFltIdCodigo(), command.getFltFecha(), command.getFltEstado(), command.getFltTipo());
            this.setCantidadRegistros(contador.intValue());
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarExclusiones.contarNotificaciones"));
        }
    }
    
    private void listarExclusiones() {
        try {
            this.exclusiones = exclusionModel.listar(this.getPrimerRegistro()-1, this.getUltimoRegistro(), unidadEjecutora, command.getFltIdCodigo(), command.getFltFecha(), command.getFltEstado(), command.getFltTipo());
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (NumberFormatException e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarExclusiones.contarNotificaciones"));
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarExclusiones.contarNotificaciones"));
        }
    }
    
    public void cambioFiltro(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            this.contarExclusiones();
            this.setPrimerRegistro(1);
            this.listarExclusiones();
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarExclusiones.contarNotificaciones"));
        }
    }

    public void validarFiltroId(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try {
            Integer.parseInt(value.toString());
        } catch (NumberFormatException e){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarExclusiones.cambioFiltro.id"));
            ((UIInput) component).setValid(false);
        }
    }
    
    public void validarFiltroFecha(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((Date) value);
            calendar.getTime();
        } catch (Exception e){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarExclusiones.cambioFiltro.fecha"));
            ((UIInput) component).setValid(false);
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public List<Exclusion> getExclusiones() {
        return exclusiones;
    }

    public void setExclusiones(List<Exclusion> exclusiones) {
        this.exclusiones = exclusiones;
    }

    public ListarExclusionesCommand getCommand() {
        return command;
    }

    public void setCommand(ListarExclusionesCommand command) {
        this.command = command;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public List<SelectItem> getItemsEstado() {
        return itemsEstado;
    }

    public void setItemsEstado(List<SelectItem> itemsEstado) {
        this.itemsEstado = itemsEstado;
    }

    public List<Tipo> getTipos() {
        return tipos;
    }

    public void setTipos(List<Tipo> tipos) {
        this.tipos = tipos;
    }

    public List<SelectItem> getItemsTipo() {
        return itemsTipo;
    }

    public void setItemsTipo(List<SelectItem> itemsTipo) {    
        this.itemsTipo = itemsTipo;
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
