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
import cr.ac.ucr.sigebi.domain.Exclusion;
import cr.ac.ucr.sigebi.entities.ExclusionEntity;
import java.util.List;
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
public class AgregarExclusionController extends ListarBienesController {

    @Resource
    private ExclusionModel exclusionModel;
    
    private ExclusionCommand command;
    
    private String mensajeExito;
    private String mensaje;
  
    public AgregarExclusionController() {
        super();
    }
    
    private void inicializar(Exclusion exclusion) {
        this.command = new ExclusionCommand(exclusion, exclusionModel.listarDetalles(exclusion));
        this.init();
    }
    
    private void inicializar() {
        this.command = new ExclusionCommand();
        this.init();
    }
    
    @Override
    protected final void init() {
        super.init();
        this.mensajeExito = "";
        this.mensaje = "";
    }
    
    public void guardarDatos() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            UIViewRoot root = context.getViewRoot();
            UIInput component =  new UIInput();
            String messageValidacion = validarForm(root, component);
            if (Constantes.OK.equals(messageValidacion)) {
                //TODO verificar estado de exclusion, y estados de detalles de exclusion
                command.setEstado(estadoModel.buscarPorDominioNombre(Constantes.DOMINI0_EXCLUSION, Constantes.ESTADO_EXCLUSION_CREADA_DESC));
                exclusionModel.salvar(command.getExclusion(command.getEstado()));
                //TODO Actualizar bienes con nuevo estado en Solicitud de Exclusion
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
        }
    }
    
    public void nuevoRegistro(ActionEvent event) {
        try{
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            inicializar();
            this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
            Util.navegar(Constantes.VISTA_EXCLUSION_NUEVA);
        } catch (FWExcepcion err) {
            mensaje = err.getMessage();
        }
    }
    
    public void detalleRegistro(ActionEvent event) {
        try{
            Exclusion exclusion = (Exclusion) event.getComponent().getAttributes().get(ListarExclusionesCommand.KEY_EXCLUSION);
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            inicializar(exclusion);
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
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public ExclusionCommand getCommand() {
        return command;
    }

    public void setCommand(ExclusionCommand command) {
        this.command = command;
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
    //</editor-fold>
}