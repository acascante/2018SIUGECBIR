/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.commands.ListarBienesCommand;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.EstadoModel;
import cr.ac.ucr.sigebi.models.TipoModel;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.ArrayList;
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
 * @author jorge.serrano
 */
@Controller(value = "controllerListarBienes")
@Scope("session")
public class ListarBienesController extends BaseController {
 
    @Resource protected EstadoModel estadoModel;
    @Resource protected TipoModel tipoModel;
    @Resource protected BienModel bienModel;
    
    List<Bien> bienes;
    List<SelectItem> itemsEstado;
    List<SelectItem> itemsTipo;
    Map<Integer, Estado> mapEstados;
    Map<Integer, Tipo> mapTipos;
    
    ListarBienesCommand command;
   
    public ListarBienesController() {
        super();
        inicializarDatos();
    }
    
    @PostConstruct
    protected void inicializar() {
        this.inicializarListado();
        
        List<Estado> listEstados = estadoModel.listarPorDominio(Constantes.DOMINIO_BIEN);
        if (!listEstados.isEmpty()) {
            mapEstados = new HashMap<Integer, Estado>();
            itemsEstado = new ArrayList<SelectItem>();
            for (Estado item : listEstados) {
                itemsEstado.add(new SelectItem(item.getValor(), item.getNombre()));
                mapEstados.put(item.getValor(), item);
            }
        }
        List<Tipo> listTipos = tipoModel.listarPorDominio(Constantes.DOMINIO_BIEN);
        if (!listTipos.isEmpty()) {
            mapTipos = new HashMap<Integer, Tipo>();
            itemsTipo = new ArrayList<SelectItem>();
            for (Tipo item : listTipos) {
                itemsTipo.add(new SelectItem(item.getValor(), item.getNombre()));
                mapTipos.put(item.getValor(), item);
            }
        }
    }

    private void inicializarDatos() {
        this.vistaOrigen = Constantes.KEY_VISTA_LISTAR_BIENES;
        this.command = new ListarBienesCommand();
    }
    
    private void inicializarListado() {
        this.setPrimerRegistro(1);
        this.contarBienes();
        this.listarBienes();
    }
    
    private void contarBienes() {
        try {
            Long contador = bienModel.contar(unidadEjecutora, command.getFltIdCodigo(), command.getFltIdentificacion(), command.getFltDescripcion(), command.getFltMarca(), command.getFltModelo(), command.getFltSerie(), getFltTipo(), getFltEstadoArray());
            this.setCantidadRegistros(contador.intValue());
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (NumberFormatException e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarNotificaciones.contarNotificaciones"));
        }
    }
    
    private void listarBienes() {
        try {
            this.bienes = bienModel.listar(this.getPrimerRegistro()-1, this.getUltimoRegistro(), unidadEjecutora, command.getFltIdCodigo(), command.getFltIdentificacion(), command.getFltDescripcion(), command.getFltMarca(), command.getFltModelo(), command.getFltSerie(), getFltTipo(), getFltEstadoArray());
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (NumberFormatException e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarNotificaciones.listarNotificaciones"));
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarNotificaciones.listarNotificaciones"));
        }
    }
    
    private Estado[] getFltEstadoArray() {
        if (command.getFltEstado() > 0) {
            Estado[] array = {mapEstados.get(command.getFltEstado())};
            return array;
        }
        return null;
    }
    
    private Tipo getFltTipo() {
        if (command.getFltTipo() > 0) {
            return mapTipos.get(command.getFltTipo());
        }
        return null;
    }
    
    public void cambioFiltro(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            this.inicializarListado();
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

    // <editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public List<Bien> getBienes() {
        return bienes;
    }

    public void setBienes(List<Bien> bienes) {
        this.bienes = bienes;
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

    public ListarBienesCommand getCommand() {
        return command;
    }

    public void setCommand(ListarBienesCommand command) {
        this.command = command;
    }
    
    public void setUnidadEjecutora(UnidadEjecutora unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
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
        this.listarBienes();
    }

    public void siguiente(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getSiguientePagina();
        this.listarBienes();
    }

    public void anterior(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getPaginaAnterior();
        this.listarBienes();
    }

    public void primero(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.setPrimerRegistro(1);
        this.listarBienes();
    }

    public void ultimo(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getPrimerRegistroUltimaPagina();
        this.listarBienes();
    }

    public void cambioRegistrosPorPagina(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.setCantRegistroPorPagina(Integer.parseInt(pEvent.getNewValue().toString()));          
        this.setPrimerRegistro(1);
        this.listarBienes();
    }
    // </editor-fold>
 }
