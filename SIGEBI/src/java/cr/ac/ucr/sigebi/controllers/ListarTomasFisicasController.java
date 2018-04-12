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
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.TomaFisica;
import cr.ac.ucr.sigebi.models.TomaFisicaModel;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

/**
 *
 * @author jairo.cisneros
 */
@Controller(value = "controllerListarTomasFisicas")
@Scope("session")
public class ListarTomasFisicasController extends BaseController {

    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    @Resource
    private TomaFisicaModel tomaFisicaModel;

    // Lista de las solicitudes
    List<TomaFisica> tomasFisicas;

    // Filtros para listar las solicitudes 
    String fltId = "";
    String fltTipo = "-1";
    String fltUbicacion = "";
    String fltDescripcion = "";
    String fltTipoMotivo = "-1";
    String fltEstado = "-1";
    
    // comboBox estados
    List<SelectItem> estadosOptions;
    List<SelectItem> tiposOptions;
    List<SelectItem> tiposMotivoOptions;

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">

    public List<TomaFisica> getTomasFisicas() {
        return tomasFisicas;
    }

    public void setTomasFisicas(List<TomaFisica> tomasFisicas) {
        this.tomasFisicas = tomasFisicas;
    }

    public String getFltId() {
        return fltId;
    }

    public void setFltId(String fltId) {
        this.fltId = fltId;
    }

    public String getFltTipo() {
        return fltTipo;
    }

    public void setFltTipo(String fltTipo) {
        this.fltTipo = fltTipo;
    }

    public String getFltUbicacion() {
        return fltUbicacion;
    }

    public void setFltUbicacion(String fltUbicacion) {
        this.fltUbicacion = fltUbicacion;
    }

    public String getFltDescripcion() {
        return fltDescripcion;
    }

    public void setFltDescripcion(String fltDescripcion) {
        this.fltDescripcion = fltDescripcion;
    }

    public String getFltTipoMotivo() {
        return fltTipoMotivo;
    }

    public void setFltTipoMotivo(String fltTipoMotivo) {
        this.fltTipoMotivo = fltTipoMotivo;
    }

    public String getFltEstado() {
        return fltEstado;
    }

    public void setFltEstado(String fltEstado) {
        this.fltEstado = fltEstado;
    }

    public List<SelectItem> getEstadosOptions() {
        return estadosOptions;
    }

    public void setEstadosOptions(List<SelectItem> estadosOptions) {
        this.estadosOptions = estadosOptions;
    }

    public List<SelectItem> getTiposOptions() {
        return tiposOptions;
    }

    public void setTiposOptions(List<SelectItem> tiposOptions) {
        this.tiposOptions = tiposOptions;
    }

    public List<SelectItem> getTiposMotivoOptions() {
        return tiposMotivoOptions;
    }

    public void setTiposMotivoOptions(List<SelectItem> tiposMotivoOptions) {
        this.tiposMotivoOptions = tiposMotivoOptions;
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public ListarTomasFisicasController() {
        super();
    }

    @PostConstruct
    public final void inicializar() {

        //Se consultan los estados por dominio
        estadosOptions = new ArrayList<SelectItem>();
        for (Estado estado : this.estadosPorDominio(Constantes.DOMINIO_TOMA_FISICA)) {
            estadosOptions.add(new SelectItem(estado.getId().toString(), estado.getNombre()));
        }

        //Se consultan los tipos por dominio
        tiposOptions  = new ArrayList<SelectItem>();
        for (Tipo tipo : this.tiposPorDominio(Constantes.DOMINIO_TOMA_FISICA)) {
            tiposOptions.add(new SelectItem(tipo.getId().toString(), tipo.getNombre()));
        }

        //Se consultan los tipos por dominio
        tiposMotivoOptions  = new ArrayList<SelectItem>();
        for (Tipo tipo : this.tiposPorDominio(Constantes.DOMINIO_TOMA_FISICA_MOTIVO)) {
            tiposMotivoOptions.add(new SelectItem(tipo.getId().toString(), tipo.getNombre()));
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
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerListarTomasFisicas.cambioFiltro"));
        }

    }

    /**
     * Contabiliza las donaciones
     */
    private void contar() {
        try {

            //Se cuenta la cantidad de registros
            Long contador = tomaFisicaModel.contar(fltId, this.tipoPorId(Long.parseLong(fltTipo)), fltUbicacion, fltDescripcion, this.tipoPorId(Long.parseLong(fltTipoMotivo)), this.estadoPorId(Long.parseLong(fltEstado)));

            //Se actualiza la cantidad de registros segun los filtros
            this.setCantidadRegistros(contador.intValue());

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerListarTomasFisicas.contar"));
        }
    }

    /**
     * Lista las donaciones
     */
    private void listar() {
        try {

            this.tomasFisicas = tomaFisicaModel.listar(fltId, this.tipoPorId(Long.parseLong(fltTipo)), fltUbicacion, fltDescripcion, this.tipoPorId(Long.parseLong(fltTipoMotivo)), this.estadoPorId(Long.parseLong(fltEstado)), this.getPrimerRegistro() - 1, this.getUltimoRegistro());
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
