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
import cr.ac.ucr.sigebi.models.ExclusionModel;
import cr.ac.ucr.sigebi.commands.ExclusionCommand;
import cr.ac.ucr.sigebi.commands.ListarExclusionesCommand;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.SolicitudExclusion;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.models.BienModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.model.SelectItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author alvaro.cascante
 */
@Controller(value = "controllerAgregarExclusiones")
@Scope("session")
public class AgregarExclusionController extends BaseController {

    ListarBienesAgregarController listadoBienes;
    
    @Resource private BienModel bienModel;
    @Resource private ExclusionModel exclusionModel;
    
    private ExclusionCommand command;
    
    private List<SelectItem> itemsTipo;
    
    private String mensajeExito;
    private String mensaje;
    
    private boolean visiblePanelBienes;
    private boolean visibleBotonSolicitar;
  
    public AgregarExclusionController() {
        super();
    }
    
    private void inicializarNuevo() {
        Estado estado = this.estadoPorDominioValor(Constantes.DOMINIO_EXCLUSION, Constantes.ESTADO_EXCLUSION_CREADA);
        this.command = new ExclusionCommand(this.unidadEjecutora, estado);
        this.listadoBienes = new ListarBienesAgregarController();
        this.visibleBotonSolicitar = false;
        inicializarDatos();
    }
    
    private void inicializarDetalle(SolicitudExclusion exclusion) {
        this.command = new ExclusionCommand(exclusion);
        this.visibleBotonSolicitar = true;
        this.listadoBienes = new ListarBienesAgregarController();
        inicializarDatos();
    }

    private void inicializarDatos() {
        this.mensajeExito = new String();
        this.mensaje = new String();
        
        List<Tipo> tipos = this.tiposPorDominio(Constantes.DOMINIO_EXCLUSION);
        if (!tipos.isEmpty()) {
            itemsTipo = new ArrayList<SelectItem>();
            for (Tipo item : tipos) {
                this.itemsTipo.add(new SelectItem(item.getId(), item.getNombre()));
            }
        }
    }
    
    public void guardarDatos() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            UIViewRoot root = context.getViewRoot();
            UIInput component =  new UIInput();
            String messageValidacion = validarForm(root, component);
            if (Constantes.OK.equals(messageValidacion)) {
                Tipo tipo = this.tipoPorId(command.getIdTipo());
                
                // Almaceno o actualizo Solicitud
                this.exclusionModel.salvar(command.getExclusion(tipo));
                this.exclusionModel.eliminarDetalles(command.getDetallesEliminar());

                List<Bien> listBienes = new ArrayList<Bien>(command.getBienes().values());
                List<Bien> listBienesEliminar = new ArrayList<Bien>(command.getBienesEliminar());
                this.bienModel.actualizar(listBienes);
                this.bienModel.actualizar(listBienesEliminar);                
                
                if (command.getIdExclusion() == null || command.getIdExclusion() == 0) {
                    mensajeExito = "Los datos se salvaron con éxito.";
                } else {
                    mensajeExito = "Los datos se actualizaron con éxito.";
                }
            } else {
                component.setValid(false);
                Mensaje.agregarErrorAdvertencia(messageValidacion);
            }
        } catch (FWExcepcion err) {
            mensaje = err.getMessage();
        } catch (Exception ex) {
            Logger.getLogger(AgregarExclusionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void solicitarExclusion() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            UIViewRoot root = context.getViewRoot();
            UIInput component = new UIInput();
            String messageValidacion = validarForm(root, component);
            if (Constantes.OK.equals(messageValidacion)) {
                Tipo tipo = this.tipoPorId(command.getIdTipo());
                Estado estadoSolicitar = this.estadoPorDominioValor(Constantes.DOMINIO_EXCLUSION, Constantes.ESTADO_EXCLUSION_SOLICITADA);
                this.command.setEstado(estadoSolicitar);
                this.exclusionModel.salvar(command.getExclusion(tipo));
                mensajeExito = "Exclusion solicitada.";
            } else {
                component.setValid(false);
                Mensaje.agregarErrorAdvertencia(messageValidacion);
            }
        } catch (FWExcepcion err) {
            mensaje = err.getMessage();
        } catch (Exception ex) {
            Logger.getLogger(AgregarExclusionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void nuevoRegistro(ActionEvent event) {
        try{
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            inicializarNuevo();
            this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
            Util.navegar(Constantes.VISTA_EXCLUSION_NUEVA);
        } catch (FWExcepcion err) {
            mensaje = err.getMessage();
        }
    }
    
    public void detalleRegistro(ActionEvent event) {
        try{
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            
            Long id = (Long)event.getComponent().getAttributes().get(ListarExclusionesCommand.KEY_EXCLUSION);
            SolicitudExclusion exclusion = exclusionModel.buscarPorId(id);
            exclusion.setDetalles(exclusionModel.listarDetalles(exclusion));
            inicializarDetalle(exclusion);
            this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
            Util.navegar(Constantes.VISTA_EXCLUSION_NUEVA);
        } catch (FWExcepcion err) {
            mensaje = err.getMessage();
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Validaciones">
    public String validarForm(UIViewRoot root, UIInput component) {
        if (command.getBienes().isEmpty()) {
            component = (UIInput) root. findComponent("frmDetalleExclusion:lstBienes");
            return Util.getEtiquetas("sigebi.error.controllerAgregarExclusiones.error.bienes.nulo");
        }
        return Constantes.OK;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Bienes">
    public void mostarPanelAgregarBienes() {
        this.setVisiblePanelBienes(true);
    }

    public void cerrarPanelAgregarBienes() {
        this.setVisiblePanelBienes(false);
        Estado estadoEnSolicitud = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_EXCLUSION );
        
        for (Map.Entry<Long, Boolean> entry : this.listadoBienes.getBienesSeleccionados().entrySet()) {
            if (entry.getValue()) {
                Bien bien = this.listadoBienes.getBienes().get(entry.getKey());
                bien.setEstadoInterno(estadoEnSolicitud);
                this.command.getBienes().put(bien.getId(), bien);
                this.command.getBienesAgregar().add(bien);
            }
        }
    }
    
    public void eliminarBien(ActionEvent event) {
        Long idBien = (Long) event.getComponent().getAttributes().get("bienSeleccionado");
        Bien bien = this.command.getBienes().get(idBien);
        
        Estado estadoInternoNormal = estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_NORMAL);
        bien.setEstadoInterno(estadoInternoNormal);
        this.command.getBienesEliminar().add(bien); // Lo agrego a la lista de bienes a eliminar
        this.command.getBienes().remove(idBien);    // Lo saco de la lista de bienes que se muestran en pantalla
        if (this.command.getDetalles().containsKey(bien.getId())) { // Si esta en la lista de detalles, es xq se trata de un detalle existente en la BD
            this.command.getDetallesEliminar().add(this.command.getDetalles().get(bien.getId()));   // Lo agrego a la lista de detalles a eliminar
            this.command.getDetalles().remove(bien.getId());    // Lo elimino de la lista de detalles, esto ahorita no sirve
        }                                                       // xq la version de hibernate no permite actualizar colecciones
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public ExclusionCommand getCommand() {
        return command;
    }

    public void setCommand(ExclusionCommand command) {
        this.command = command;
    }

    public List<SelectItem> getItemsTipo() {
        return itemsTipo;
    }

    public void setItemsTipo(List<SelectItem> itemsTipo) {
        this.itemsTipo = itemsTipo;
    }
    
    public String getMensajeExito() {
        return mensajeExito;
    }

    public void setMensajeExito(String mensajeExito) {
        this.mensajeExito = mensajeExito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isVisiblePanelBienes() {
        return visiblePanelBienes;
    }

    public void setVisiblePanelBienes(boolean visiblePanelBienes) {
        this.visiblePanelBienes = visiblePanelBienes;
    }

    public boolean isVisibleBotonSolicitar() {
        return visibleBotonSolicitar;
    }

    public void setVisibleBotonSolicitar(boolean visibleBotonSolicitar) {
        this.visibleBotonSolicitar = visibleBotonSolicitar;
    }
    
    public ListarBienesAgregarController getListadoBienes() {
        return listadoBienes;
    }

    public void setListadoBienes(ListarBienesAgregarController listadoBienes) {
        this.listadoBienes = listadoBienes;
    }
    //</editor-fold>
}