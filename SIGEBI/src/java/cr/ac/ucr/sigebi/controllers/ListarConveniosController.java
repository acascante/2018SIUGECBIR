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
import cr.ac.ucr.sigebi.commands.ListarConveniosCommand;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Convenio;
import cr.ac.ucr.sigebi.models.ConvenioModel;
import java.util.ArrayList;
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
@Controller(value = "controllerListarConvenios")
@Scope("session")
public class ListarConveniosController extends BaseController {
    
    @Resource private ConvenioModel convenioModel;
    
    private List<Convenio> convenios;
    
    private List<SelectItem> itemsEstado;
    
    private ListarConveniosCommand command;
   
    public ListarConveniosController() {
        super();
        this.inicializarDatos();
    }
    
    @PostConstruct
    public final void inicializar() {
        this.inicializarListado();
        
        List<Estado> listEstados = this.estadosPorDominio(Constantes.DOMINIO_CONVENIO);
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
        this.vistaOrigen = Constantes.VISTA_CONVENIO_LISTADO;
        this.command = new ListarConveniosCommand();
    }
    
    private void inicializarListado() {
        this.setPrimerRegistro(1);
        this.contarConvenios();
        this.listarConvenios();
    }
    
    private void contarConvenios() {
        try {
            Long contador = convenioModel.contar(command.getFltIdCodigo(), command.getFltInstitucion(), command.getFltResponsable(), command.getFltOficio(), command.getFltEstado());
            this.setCantidadRegistros(contador.intValue());
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        }
    }
    
    private void listarConvenios() {
        try {
            this.convenios = convenioModel.listar(this.getPrimerRegistro()-1, this.getUltimoRegistro(), command.getFltIdCodigo(), command.getFltInstitucion(), command.getFltResponsable(), command.getFltOficio(), command.getFltEstado());
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
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.label.convenios.error.cambioFiltro.id"));
            ((UIInput) component).setValid(false);
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public ListarConveniosCommand getCommand() {
        return command;
    }

    public void setCommand(ListarConveniosCommand command) {
        this.command = command;
    }

    public List<Convenio> getConvenios() {
        return convenios;
    }

    public void setConvenios(List<Convenio> convenios) {
        this.convenios = convenios;
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
        this.listarConvenios();
    }

    public void siguiente(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getSiguientePagina();
        this.listarConvenios();
    }

    public void anterior(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getPaginaAnterior();
        this.listarConvenios();
    }

    public void primero(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.setPrimerRegistro(1);
        this.listarConvenios();
    }

    public void ultimo(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getPrimerRegistroUltimaPagina();
        this.listarConvenios();
    }

    public void cambioRegistrosPorPagina(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.setCantRegistroPorPagina(Integer.parseInt(pEvent.getNewValue().toString()));          
        this.setPrimerRegistro(1);
        this.listarConvenios();
    }
    // </editor-fold>
}
