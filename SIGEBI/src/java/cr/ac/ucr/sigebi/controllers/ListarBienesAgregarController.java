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
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author aocc
 */
public class ListarBienesAgregarController extends BaseController {

    @Resource private BienModel bienModel;
    
    private ListarBienesCommand command;
    private Estado estadoInternoNormal;
    private Map<Long, Bien> bienes;
    private Map<Long, Boolean> bienesSeleccionados;

    public ListarBienesAgregarController() {
        this.command = new ListarBienesCommand();
        this.bienes = new HashMap<Long, Bien>();
        this.bienesSeleccionados = new HashMap<Long, Boolean>();
        inicializarListado();            
    }

    private void inicializarListado() {
        this.estadoInternoNormal = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_NORMAL);
        this.setPrimerRegistro(1);
        this.contarBienes();
        this.listarBienes();
    }

    private void contarBienes() {
        try {
            Long contador = bienModel.contar(this.command.getFltIdCodigo(), this.unidadEjecutora, this.command.getFltIdentificacion(), this.command.getFltDescripcion(), this.command.getFltMarca(), this.command.getFltModelo(), this.command.getFltSerie(), this.estadoInternoNormal);
            this.setCantidadRegistros(contador.intValue());
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarNotificaciones.contarNotificaciones"));
        }
    }

    private void listarBienes() {
        try {
           List<Bien> itemsBienes = bienModel.listar(this.getPrimerRegistro() - 1, this.getUltimoRegistro(), this.command.getFltIdCodigo(), this.unidadEjecutora , command.getFltIdentificacion(), command.getFltDescripcion(), command.getFltMarca(), command.getFltModelo(), command.getFltSerie(), this.estadoInternoNormal);
            for (Bien item : itemsBienes) {
                this.bienes.put(item.getId(), item);
            }
       } catch (FWExcepcion e) {
           Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
       } catch (NumberFormatException e) {
           Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarNotificaciones.listarNotificaciones"));
       } catch (Exception e) {
           Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarNotificaciones.listarNotificaciones"));
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

    // <editor-fold defaultstate="collapsed" desc="Get's Set's">
    public List<Bien> getItemsBienes() {
        List<Bien> list = new ArrayList<Bien>(bienes.values());
        return list;
    }

    public ListarBienesCommand getCommand() {
        return command;
    }

    public void setCommand(ListarBienesCommand command) {
        this.command = command;
    }

    public Estado getEstadoInternoNormal() {
        return estadoInternoNormal;
    }

    public void setEstadoInternoNormal(Estado estadoInternoNormal) {
        this.estadoInternoNormal = estadoInternoNormal;
    }

    public Map<Long, Bien> getBienes() {
        return bienes;
    }

    public void setBienes(Map<Long, Bien> bienes) {
        this.bienes = bienes;
    }

    public Map<Long, Boolean> getBienesSeleccionados() {
        return bienesSeleccionados;
    }

    public void setBienesSeleccionados(Map<Long, Boolean> bienesSeleccionados) {
        this.bienesSeleccionados = bienesSeleccionados;
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
