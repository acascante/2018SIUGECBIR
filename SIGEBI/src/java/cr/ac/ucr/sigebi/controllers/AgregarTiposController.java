/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.commands.TipoCommand;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.models.TipoModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author alvaro.cascante
 */
@Controller(value = "controllerAgregarTipos")
@Scope("session")
public class AgregarTiposController extends BaseController {
    
    @Resource private TipoModel tipoModel;    
    
    private Map<Long, String> tiposModificables;
    private List<SelectItem> itemsTipo;
    private List<Tipo> tipos;
    private Long idTipo;
    private TipoCommand command;
    
    private String mensaje;

    private boolean visiblePanelConfirmacion;
    private boolean visiblePanelTipo;
  
    public AgregarTiposController() {
        super();
        this.inicializarDatos();
    }

    @PostConstruct
    public final void inicializar() {
        //Cargar Tipos de Reporte
        List<Tipo> tiposDominio = this.tiposPorDominio(Constantes.DOMINIO_TIPOS_MODIFICAR);
        if (!tiposDominio.isEmpty()) {
            this.tiposModificables = new HashMap<Long, String>();
            this.itemsTipo = new ArrayList<SelectItem>();
            for (Tipo tipo : tiposDominio) {
                this.tiposModificables.put(tipo.getId(), tipo.getNombre());
                this.itemsTipo.add(new SelectItem(tipo.getId(), tipo.getNombre()));
            }
        }
    }
    
    private void inicializarDatos() {
        this.mensaje = new String();
        this.command = new TipoCommand();
        this.tipos = new ArrayList<Tipo>();
    }
    
    public void cargarTipos(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        if (Constantes.DEFAULT_ID.equals(command.getId())) {
            this.visiblePanelTipo = false;
        } else {
            this.visiblePanelTipo = true;
        }
        this.tipos.clear();
        this.command.setDominio(this.tiposModificables.get(this.command.getId()));
        this.tipos = this.tiposPorDominio(this.command.getDominio());
    }      
    
    public void agregarTipo() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            UIViewRoot root = context.getViewRoot();
            String messageValidacion = validarForm(root);
            if (Constantes.OK.equals(messageValidacion)) {
                Tipo tipo = this.command.getTipo();
                tipo.setValor(this.tipos.size() + 1);
                this.tipoModel.salvar(tipo);
                this.tiposGenerales.add(tipo);
                this.tipos.add(tipo);
                this.command.setNuevoTipo("");
                Mensaje.agregarInfo("Datos almacenados exitosamente");
            } else {
                Mensaje.agregarErrorAdvertencia(messageValidacion);
            }
        } catch (FWExcepcion err) {
            this.mensaje = err.getMessage();
        }
    }
    
    public String validarForm(UIViewRoot root) {
        if (this.command.getNuevoTipo().isEmpty()) {
            return Util.getEtiquetas("sigebi.label.tipo.error.nombre");
        }
        return Constantes.OK;
    }
    
    public void eliminarTipo(ActionEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        
        if (this.tipos.size() == 1) {
            Mensaje.agregarErrorAdvertencia("Debe existir almenos un tipo");
        } else {
            this.idTipo = (Long) event.getComponent().getAttributes().get("tipoSeleccionado");
            this.visiblePanelConfirmacion = true;
        }
    }
    
    public void eliminarTipoCancelar() {
        this.visiblePanelConfirmacion = false;
    }
    
    public void eliminarTipoAceptar() {
        try {
            Tipo tipo = this.tipoPorId(this.idTipo);
            this.tipoModel.eliminar(tipo);
            this.tiposGenerales.remove(tipo);
            this.tipos.remove(tipo);
            this.visiblePanelConfirmacion = false;
            Mensaje.agregarInfo("Datos eliminados exitosamente");
        } catch (FWExcepcion err) {
            this.mensaje = err.getMessage();
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public Map<Long, String> getTiposModificables() {
        return tiposModificables;
    }

    public void setTiposModificables(Map<Long, String> tiposModificables) {
        this.tiposModificables = tiposModificables;
    }

    public List<SelectItem> getItemsTipo() {
        return itemsTipo;
    }

    public void setItemsTipo(List<SelectItem> itemsTipo) {
        this.itemsTipo = itemsTipo;
    }

    public List<Tipo> getTipos() {
        return tipos;
    }

    public void setTipos(List<Tipo> tipos) {
        this.tipos = tipos;
    }

    public TipoCommand getCommand() {
        return command;
    }

    public void setCommand(TipoCommand command) {
        this.command = command;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isVisiblePanelTipo() {
        return visiblePanelTipo;
    }

    public void setVisiblePanelTipo(boolean visiblePanelTipo) {
        this.visiblePanelTipo = visiblePanelTipo;
    }
    
    public boolean isVisiblePanelConfirmacion() {
        return visiblePanelConfirmacion;
    }

    public void setVisiblePanelConfirmacion(boolean visiblePanelConfirmacion) {
        this.visiblePanelConfirmacion = visiblePanelConfirmacion;
    }
    //</editor-fold>
}
