/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.commands.InterfazeBienCommand;
import cr.ac.ucr.sigebi.domain.InterfazAccesorio;
import cr.ac.ucr.sigebi.domain.InterfazAdjunto;
import cr.ac.ucr.sigebi.domain.InterfazBien;
import cr.ac.ucr.sigebi.models.InterfazBienModel;
import javax.annotation.Resource;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author jairo.cisneros
 */
@Controller(value = "controllerInterfazBien")
@Scope("session")
public class InterfazeBienController extends BaseController {

    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    @Resource
    private InterfazBienModel interfazBienModel;

    InterfazeBienCommand command;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">

    public InterfazeBienCommand getCommand() {
        return command;
    }

    public void setCommand(InterfazeBienCommand command) {
        this.command = command;
    }
   
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Navegación del MENÚ">

    public void detalle(ActionEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        
        command = new InterfazeBienCommand((InterfazBien) event.getComponent().getAttributes().get("interfazSeleccionada"));

        //Se consultan los adjuntos        
        for (InterfazAdjunto item : interfazBienModel.listarInterfazAdjuntos(command.getInterfazBien().getIdentificacionOrigen(), command.getInterfazBien().getIdOrigenTecnico())) {
            command.getAdjuntos().put(item.getId(), item);
        }
        
        //Se consultan los accesorios
        for (InterfazAccesorio item : interfazBienModel.listarInterfazAccesorios(command.getInterfazBien().getIdentificacionOrigen(), command.getInterfazBien().getIdOrigenTecnico())) {
            command.getAccesorios().put(item.getId(), item);
        }
        
        this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
        Util.navegar(Constantes.KEY_VISTA_INTERFAZ_BIEN_DETALLE);
    }

    public void regresarListado() {
        if (vistaOrigen != null) {
            Util.navegar(vistaOrigen);
        } else {
            Util.navegar(Constantes.KEY_VISTA_INTERFAZ_BIEN_LISTADO);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public InterfazeBienController() {
        super();        
    }

    //</editor-fold>

}
