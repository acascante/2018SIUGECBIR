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
import cr.ac.ucr.sigebi.commands.ListarRecepcionPrestamosCommand;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Convenio;
import cr.ac.ucr.sigebi.domain.RecepcionPrestamo;
import cr.ac.ucr.sigebi.models.ConvenioModel;
import cr.ac.ucr.sigebi.models.RecepcionPrestamoModel;
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
@Controller(value = "controllerListarRecepcionPrestamos")
@Scope("session")
public class ListarRecepcionPrestamosController extends BaseController {
    
    @Resource private ConvenioModel convenioModel;
    @Resource private RecepcionPrestamoModel recepcionPrestamoModel;
    
    private List<SelectItem> itemsConvenio;
    private List<SelectItem> itemsEstado;
    private List<RecepcionPrestamo> prestamos;
    
    private ListarRecepcionPrestamosCommand command;
   
    public ListarRecepcionPrestamosController() {
        super();
        this.inicializarDatos();
    }
    
    @PostConstruct
    public final void inicializar() {
        this.inicializarListado();
        
        List<Estado> listEstados = this.estadosPorDominio(Constantes.DOMINIO_RECEPCION_PRESTAMO);
        if (!listEstados.isEmpty()) {
            this.itemsEstado = new ArrayList<SelectItem>();
            for (Estado item : listEstados) {
                this.itemsEstado.add(new SelectItem(item.getId(), item.getNombre()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
            }
        }
        
        List<Convenio> listConvenios = this.convenioModel.listarActivos(unidadEjecutora, new Date());
        if (!listConvenios.isEmpty()) {
            this.itemsConvenio = new ArrayList<SelectItem>();
            for (Convenio item : listConvenios) {
                this.itemsConvenio.add(new SelectItem(item.getId(), item.getInstitucion()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
            }
        }
    }
    
    public void regresarListado() {
        Util.navegar(vistaOrigen);
        this.inicializarDatos();
        this.inicializarListado();
    }
    
    private void inicializarDatos() {
        this.vistaOrigen = Constantes.VISTA_RECEPCION_PRESTAMO_LISTADO;
        this.command = new ListarRecepcionPrestamosCommand();
    }

    private void inicializarListado() {
        this.setPrimerRegistro(1);
        this.contarPrestamos();
        this.listarPrestamos();
    }
    
    private void contarPrestamos() {
        try {
            Long contador = recepcionPrestamoModel.contar(command.getFltIdCodigo(), command.getFltConvenio(), command.getFltDescripcion(), command.getFltIdentificacion(), command.getFltFechaIngreso(), command.getFltFechaDevolucion(), command.getFltEstado());
            this.setCantidadRegistros(contador.intValue());
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        }
    }
    
    private void listarPrestamos() {
        try {
            this.prestamos = recepcionPrestamoModel.listar(this.getPrimerRegistro()-1, this.getUltimoRegistro(), command.getFltIdCodigo(), command.getFltConvenio(), command.getFltDescripcion(), command.getFltIdentificacion(), command.getFltFechaIngreso(), command.getFltFechaDevolucion(), command.getFltEstado());
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
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.label.recepcionPrestamo.error.filtro.id"));
            ((UIInput) component).setValid(false);
        }
    }
    
    public void validarFiltroFecha(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((Date) value);
            calendar.getTime();
        } catch (Exception e){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.label.recepcionPrestamo.error.filtro.fecha"));
            ((UIInput) component).setValid(false);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public List<SelectItem> getItemsConvenio() {
        return itemsConvenio;
    }

    public void setItemsConvenio(List<SelectItem> itemsConvenio) {
        this.itemsConvenio = itemsConvenio;
    }

    public List<SelectItem> getItemsEstado() {
        return itemsEstado;
    }

    public void setItemsEstado(List<SelectItem> itemsEstado) {
        this.itemsEstado = itemsEstado;
    }

    public List<RecepcionPrestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<RecepcionPrestamo> prestamos) {
        this.prestamos = prestamos;
    }

    public ListarRecepcionPrestamosCommand getCommand() {
        return command;
    }

    public void setCommand(ListarRecepcionPrestamosCommand command) {
        this.command = command;
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
        this.listarPrestamos();
    }

    public void siguiente(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getSiguientePagina();
        this.listarPrestamos();
    }

    public void anterior(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getPaginaAnterior();
        this.listarPrestamos();
    }

    public void primero(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.setPrimerRegistro(1);
        this.listarPrestamos();
    }

    public void ultimo(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getPrimerRegistroUltimaPagina();
        this.listarPrestamos();
    }

    public void cambioRegistrosPorPagina(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.setCantRegistroPorPagina(Integer.parseInt(pEvent.getNewValue().toString()));          
        this.setPrimerRegistro(1);
        this.listarPrestamos();
    }
    // </editor-fold>
}
