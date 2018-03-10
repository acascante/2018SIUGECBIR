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
import cr.ac.ucr.sigebi.domain.InterfazBien;
import cr.ac.ucr.sigebi.models.InterfazBienModel;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

/**
 *
 * @author jairo.cisneros
 */
@Controller(value = "controllerListarInterfazBien")
@Scope("session")
public class ListarInterfazBienController extends BaseController {

    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    @Resource
    private InterfazBienModel interfazBienModel;

    // Lista de las solicitudes
    List<InterfazBien> interfazBienes;

    // Filtros para listar las solicitudes 
    String fltId = "";
    String fltEstado = "-1";
    String fltUnidadEjecutora = "";
    String fltMarca = "";
    String fltModelo = "";
    String fltSerie = "";
    String fltDescripcion = "";

    // comboBox estados
    List<SelectItem> estadosOptions;
    
    //Estado pendiente
    Estado estadoBienPendiente = this.estadoPorDominioValor(Constantes.DOMINIO_INTERFAZ_BIEN, Constantes.ESTADO_INTERFAZ_BIEN_PENDIENTE);
    Estado estadoBienErroneo = this.estadoPorDominioValor(Constantes.DOMINIO_INTERFAZ_BIEN, Constantes.ESTADO_INTERFAZ_BIEN_ERRONEO);
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">
    public List<InterfazBien> getInterfazBienes() {
        return interfazBienes;
    }

    public Estado getEstadoBienPendiente() {
        return estadoBienPendiente;
    }

    public void setEstadoBienPendiente(Estado estadoBienPendiente) {
        this.estadoBienPendiente = estadoBienPendiente;
    }

    public Estado getEstadoBienErroneo() {
        return estadoBienErroneo;
    }

    public void setEstadoBienErroneo(Estado estadoBienErroneo) {
        this.estadoBienErroneo = estadoBienErroneo;
    }

    public void setInterfazBienes(List<InterfazBien> interfazBienes) {
        this.interfazBienes = interfazBienes;
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

    public String getFltUnidadEjecutora() {
        return fltUnidadEjecutora;
    }

    public void setFltUnidadEjecutora(String fltUnidadEjecutora) {
        this.fltUnidadEjecutora = fltUnidadEjecutora;
    }

    public String getFltMarca() {
        return fltMarca;
    }

    public void setFltMarca(String fltMarca) {
        this.fltMarca = fltMarca;
    }

    public String getFltModelo() {
        return fltModelo;
    }

    public void setFltModelo(String fltModelo) {
        this.fltModelo = fltModelo;
    }

    public String getFltSerie() {
        return fltSerie;
    }

    public void setFltSerie(String fltSerie) {
        this.fltSerie = fltSerie;
    }

    public String getFltDescripcion() {
        return fltDescripcion;
    }

    public void setFltDescripcion(String fltDescripcion) {
        this.fltDescripcion = fltDescripcion;
    }

    public List<SelectItem> getEstadosOptions() {
        return estadosOptions;
    }

    public void setEstadosOptions(List<SelectItem> estadosOptions) {
        this.estadosOptions = estadosOptions;
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public ListarInterfazBienController() {
        super();
    }

    @PostConstruct
    public final void inicializar() {

        //Se consultan los estados por dominio
        estadosOptions = new ArrayList<SelectItem>();
        for (Estado estado : this.estadosPorDominio(Constantes.DOMINIO_INTERFAZ_BIEN)) {
            estadosOptions.add(new SelectItem(estado.getId().toString(), estado.getNombre()));
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
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerListarInterfazBien.cambioFiltro"));
        }

    }

    /**
     * Contabiliza las donaciones
     */
    private void contar() {
        try {

            //Se cuenta la cantidad de registros
            Long contador = interfazBienModel.contar(fltId, fltMarca, fltModelo, fltSerie, fltDescripcion, fltUnidadEjecutora, this.estadoPorId(Long.parseLong(fltEstado)));

            //Se actualiza la cantidad de registros segun los filtros
            this.setCantidadRegistros(contador.intValue());

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerListarInterfazBien.contar"));
        }
    }

    /**
     * Lista las donaciones
     */
    private void listar() {
        try {

            this.interfazBienes = interfazBienModel.listar(fltId, fltMarca, fltModelo, fltSerie, fltDescripcion, fltUnidadEjecutora,
                     this.estadoPorId(Long.parseLong(fltEstado)), this.getPrimerRegistro() - 1, this.getUltimoRegistro());
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
