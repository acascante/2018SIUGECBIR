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
    
    private Map<Long, Tipo> tiposModificables;
    private List<SelectItem> itemsTipo;
    private List<Tipo> tipos;
    
    private TipoCommand command;
    
    private String mensaje;

    private boolean visiblePanelConfirmacion;
  
    public AgregarTiposController() {
        super();
        this.inicializarDatos();
    }

    @PostConstruct
    public final void inicializar() {
        //Cargar Tipos de Reporte
        List<Tipo> tiposDominio = this.tiposPorDominio(Constantes.DOMINIO_TIPOS_MODIFICAR);
        if (!tiposDominio.isEmpty()) {
            this.tiposModificables = new HashMap<Long, Tipo>();
            this.itemsTipo = new ArrayList<SelectItem>();
            for (Tipo tipo : tiposDominio) {
                this.tiposModificables.put(tipo.getId(), tipo);
                this.itemsTipo.add(new SelectItem(tipo.getId(), tipo.getNombre()));
            }
        }
    }
    
    private void inicializarDatos() {
        this.mensaje = new String();
        this.command = new TipoCommand();
    }
    
    public void cargarTipos(ValueChangeEvent event) {
        this.tipos.clear();
        this.command.setDominio(this.tiposModificables.get(this.command.getId()).getDominio());
        this.tipos = this.tiposPorDominio(this.command.getDominio());
    }      
    
    public void guardarDatos() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            UIViewRoot root = context.getViewRoot();
            String messageValidacion = validarForm(root);
            if (Constantes.OK.equals(messageValidacion)) {
                Tipo tipo = this.command.getTipo();
                tipo.setValor(this.tipos.size());
                this.tipoModel.salvar(tipo);
                this.tipos.add(tipo);
                Mensaje.agregarInfo("Datos almacenados exitosamente");
            } else {
                Mensaje.agregarErrorAdvertencia(messageValidacion);
            }
        } catch (FWExcepcion err) {
            this.mensaje = err.getMessage();
        }
    }
    
    public String validarForm(UIViewRoot root) {
        if (this.command.getNombre().isEmpty()) {
            return Util.getEtiquetas("sigebi.label.tipo.error.nombre");
        }
        return Constantes.OK;
    }
    
    public void eliminarTipo() {
        if (this.tipos.size() == 1) {
            Mensaje.agregarErrorAdvertencia("Debe existir almenos un tipo");
        } else {
            this.visiblePanelConfirmacion = true;
        }
    }
    
    public void eliminarTipoCancelar(ActionEvent event) {
        this.visiblePanelConfirmacion = false;
    }
    
    public void eliminarBien(ActionEvent event) {
        Long id = (Long) event.getComponent().getAttributes().get("tipoSeleccionado");
        try {
            this.tipoModel.salvar(this.tipoPorId(id));
            Mensaje.agregarInfo("Datos eliminados exitosamente");
        } catch (FWExcepcion err) {
            this.mensaje = err.getMessage();
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public Map<Long, Tipo> getTiposModificables() {
        return tiposModificables;
    }

    public void setTiposModificables(Map<Long, Tipo> tiposModificables) {
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

    public boolean isVisiblePanelConfirmacion() {
        return visiblePanelConfirmacion;
    }

    public void setVisiblePanelConfirmacion(boolean visiblePanelConfirmacion) {
        this.visiblePanelConfirmacion = visiblePanelConfirmacion;
    }
    //</editor-fold>
}
