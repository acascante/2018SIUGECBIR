/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Solicitud;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.models.SolicitudModel;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

/**
 *
 * @author jairo.cisneros
 */
@Controller(value = "controllerListarDonaciones")
@Scope("session")
public class ListarDonacionesController extends BaseController {

    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    @Resource
    private SolicitudModel solicitudModel;

    // Lista de las solicitudes
    List<Solicitud> solicitudes;

    // Filtros para listar las solicitudes 
    String fltId = "";
    String fltEstado = "-1";
    String fltIdTipo = "-1";
    String fltUnidadReceptora = "";
    String fltDonante = "";
    
    // comboBox estados
    List<SelectItem> estadosOptions;

    // comboBox tipos
    List<SelectItem> tipoOptions;

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">
    public SolicitudModel getSolicitudModel() {
        return solicitudModel;
    }

    public void setSolicitudModel(SolicitudModel solicitudModel) {
        this.solicitudModel = solicitudModel;
    }

    public List<Solicitud> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<Solicitud> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public String getFltId() {
        return fltId;
    }

    public void setFltId(String fltId) {
        this.fltId = fltId;
    }

    public String getFltEstado() {
        return fltEstado;
    }

    public void setFltEstado(String fltEstado) {
        this.fltEstado = fltEstado;
    }

    public String getFltIdTipo() {
        return fltIdTipo;
    }

    public void setFltIdTipo(String fltIdTipo) {
        this.fltIdTipo = fltIdTipo;
    }

    public String getFltUnidadReceptora() {
        return fltUnidadReceptora;
    }

    public void setFltUnidadReceptora(String fltUnidadReceptora) {
        this.fltUnidadReceptora = fltUnidadReceptora;
    }

    public String getFltDonante() {
        return fltDonante;
    }

    public void setFltDonante(String fltDonante) {
        this.fltDonante = fltDonante;
    }

    public List<SelectItem> getEstadosOptions() {
        return estadosOptions;
    }

    public void setEstadosOptions(List<SelectItem> estadosOptions) {
        this.estadosOptions = estadosOptions;
    }

    public List<SelectItem> getTipoOptions() {
        return tipoOptions;
    }
    
    public void setTipoOptions(List<SelectItem> tipoOptions) {    
        this.tipoOptions = tipoOptions;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public ListarDonacionesController() {
        super();
    }

    @PostConstruct
    public final void inicializar() {

        //Se consultan los estados por dominio
        estadosOptions = new ArrayList<SelectItem>();
        tipoOptions = new ArrayList<SelectItem>();
        for (Estado estado : this.estadosPorDominio(Constantes.DOMINIO_SOLI_DONACION)) {
            estadosOptions.add(new SelectItem(estado.getId().toString(), estado.getNombre()));
        }
        //Se consultan los tipos por dominio
        for (Tipo tipo : this.tiposPorDominio(Constantes.DOMINIO_SOLI_DONACION)) {
            tipoOptions.add(new SelectItem(tipo.getId().toString(), tipo.getNombre()));
        }

        // Se cuenta la cantidad de donaciones
        this.setPrimerRegistro(1);
        this.contar();

        //Se consulta la lista de donaciones
        this.listar();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    /**
     * Cambia el valor de alguno de los filtros
     *
     * @param pEvent
     */
    public void cambioFiltro(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            // Se cuenta la cantidad de donaciones
            this.contar();
            this.setPrimerRegistro(1);
            this.listar();

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerListarDonaconesController.cambioFiltro"));
        }

    }

    /**
     * Contabiliza las donaciones
     */
    private void contar() {
        try {

            //Se cuenta la cantidad de registros
            Long contador = solicitudModel.contarDonaciones(fltId, unidadEjecutora, this.estadoPorId(Long.parseLong(fltEstado)), 
                            Constantes.DISCRIMINATOR_SOLICITUD_DONACION, this.tipoPorId(Long.parseLong(fltIdTipo)), fltUnidadReceptora, fltDonante);

            //Se actualiza la cantidad de registros segun los filtros
            this.setCantidadRegistros(contador.intValue());

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerListarDonaconesController.contar"));
        }
    }

    /**
     * Lista las donaciones
     */
    private void listar() {
        try {

            this.solicitudes = solicitudModel. listarDonaciones(fltId, unidadEjecutora, this.estadoPorId(Long.parseLong(fltEstado)), 
                            Constantes.DISCRIMINATOR_SOLICITUD_DONACION, this.tipoPorId(Long.parseLong(fltIdTipo)), fltUnidadReceptora, fltDonante, 
                            this.getPrimerRegistro() - 1, this.getUltimoRegistro());
            
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerListarDonaconesController.listar"));
        }
    }

    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Paginacion">
    /**
     * Pasa a la pagina sub-set
     *
     * @param pEvent
     */
    public void irPagina(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        int numeroPagina = Integer.parseInt(Util.getRequestParameter("numPag"));
        this.getPrimerRegistroPagina(numeroPagina);
        this.listar();
    }

    /**
     * Pasa al siguiente sub-set
     *
     * @param pEvent
     */
    public void siguiente(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getSiguientePagina();
        this.listar();
    }

    /**
     * Pasa al anterior sub-set
     *
     * @param pEvent
     */
    public void anterior(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getPaginaAnterior();
        this.listar();
    }

    /**
     * Pasa al primero sub-set
     *
     * @param pEvent
     */
    public void primero(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.setPrimerRegistro(1);
        this.listar();
    }

    /**
     * Pasa al ultimo sub-set
     *
     * @param pEvent
     */
    public void ultimo(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getPrimerRegistroUltimaPagina();
        this.listar();
    }

    /**
     * Cambia la cantidad de registros por página
     *
     * @param pEvent
     */
    public void cambioRegistrosPorPagina(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.setCantRegistroPorPagina(Integer.parseInt(pEvent.getNewValue().toString()));
        this.setPrimerRegistro(1);
        this.contar();
        this.listar();

    }

// </editor-fold>  
}
