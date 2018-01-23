/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.sigebi.models.EstadoModel;
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
import cr.ac.ucr.sigebi.models.InformeTecnicoModel;
import cr.ac.ucr.sigebi.entities.InformeTecnicoEntity;
import cr.ac.ucr.sigebi.models.TipoModel;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

/**
 *
 * @author jairo.cisneros
 */
@Controller(value = "controllerListarInformesTecnicos")
@Scope("session")
public class ListarInformesTecnicosController extends BaseController {

    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    @Resource
    private InformeTecnicoModel informeTecnicoModel;

    @Resource
    private EstadoModel estadoModel;

    @Resource
    private TipoModel tipoModel;

    // Lista de los informes
    List<InformeTecnicoEntity> informes;

    // Filtros para listar los Informes 
    String fltIdTipo = "-1";
    String fltIdBien = "";
    String fltDescripcion = "";
    String fltEstado = "-1";

    // comboBox estados
    List<SelectItem> estadosOptions;
    List<Estado> estados;

    // comboBox tipos
    List<SelectItem> tipoOptions;
    List<Tipo> tipos;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">

    public InformeTecnicoModel getInformeTecnicoModel() {
        return informeTecnicoModel;
    }

    public void setInformeTecnicoModel(InformeTecnicoModel informeTecnicoModel) {
        this.informeTecnicoModel = informeTecnicoModel;
    }

    public EstadoModel getEstadoModel() {
        return estadoModel;
    }

    public void setEstadoModel(EstadoModel estadoModel) {
        this.estadoModel = estadoModel;
    }

    public List<InformeTecnicoEntity> getInformes() {
        return informes;
    }

    public void setInformes(List<InformeTecnicoEntity> informes) {
        this.informes = informes;
    }

    public String getFltIdTipo() {
        return fltIdTipo;
    }

    public void setFltIdTipo(String fltIdTipo) {
        this.fltIdTipo = fltIdTipo;
    }

    public String getFltIdBien() {
        return fltIdBien;
    }

    public void setFltIdBien(String fltIdBien) {
        this.fltIdBien = fltIdBien;
    }

    public String getFltDescripcion() {
        return fltDescripcion;
    }

    public void setFltDescripcion(String fltDescripcion) {
        this.fltDescripcion = fltDescripcion;
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

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public List<SelectItem> getTipoOptions() {
        return tipoOptions;
    }

    public void setTipoOptions(List<SelectItem> tipoOptions) {
        this.tipoOptions = tipoOptions;
    }

    public List<Tipo> getTipos() {
        return tipos;
    }

    public void setTipos(List<Tipo> tipos) {
        this.tipos = tipos;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public ListarInformesTecnicosController() {
        super();
    }

    @PostConstruct
    public final void inicializar() {

        //Se consultan los estados por dominio
        estados = estadoModel.listarPorDominio(Constantes.DOMINIO_INFORME_TECNICO);        
        estadosOptions = new ArrayList<SelectItem>();
        for (Estado item : estados) {
            estadosOptions.add(new SelectItem(item.getId().toString(), item.getNombre()));
        }

        //Se consultan los tipos por dominio
        tipos = tipoModel.listarPorDominio(Constantes.DOMINIO_INFORME_TECNICO);
        tipoOptions = new ArrayList<SelectItem>();
        for (Tipo item : tipos) {
            tipoOptions.add(new SelectItem(item.getId().toString(), item.getNombre()));
        }
        
        // Se cuenta la cantidad de informes
        this.setPrimerRegistro(1);
        this.contarInformes();

        //Se consulta la lista de informes
        this.listarInformes();
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
            this.contarInformes();

            this.setPrimerRegistro(1);

            this.listarInformes();
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarInformesTecnicos.cambioFiltro"));
        }

    }
    
    /**
     * Contabiliza los informes
     */
    private void contarInformes() {
        try {

            //Se cuenta la cantidad de registros
            Long contador = informeTecnicoModel.consultaCantidadRegistros(unidadEjecutoraId, 
                    fltIdTipo, fltIdBien, fltDescripcion, fltEstado);
            

            //Se actualiza la cantidad de registros segun los filtros
            this.setCantidadRegistros(contador.intValue());

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarInformesTecnicos.contarBienes"));
        }
    }
    /**
     * Lista los informes
     */
    private void listarInformes() {
        try {
            this.informes = informeTecnicoModel.listarInformes(unidadEjecutoraId, 
                    fltIdTipo, fltIdBien, fltDescripcion, fltEstado, this.getPrimerRegistro() - 1, this.getUltimoRegistro());
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarInformesTecnicos.listarBienes"));
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
        this.listarInformes();
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
        this.listarInformes();
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
        this.listarInformes();
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
        this.listarInformes();
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
        this.listarInformes();
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
        this.listarInformes();

    }

// </editor-fold>  
}
