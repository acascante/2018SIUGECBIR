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
import cr.ac.ucr.sigebi.domain.Documento;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.models.DocumentoModel;
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
    private DocumentoModel documentoModel;

    // Lista de los informes
    List<Documento> informes;

    // Filtros para listar los Informes 
    static String fltIdTipo = "-1";
    static String fltEstado = "-1";
    static String fltIdentificacionBien = "";
    static String fltDescripcionBien = "";
    static String fltMarcaBien = "";
    static String fltModeloBien = "";

    // comboBox estados
    List<SelectItem> estadosOptions;

    // comboBox tipos
    List<SelectItem> tipoOptions;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">
    public DocumentoModel getDocumentoModel() {
        return documentoModel;
    }

    public void setDocumentoModel(DocumentoModel documentoModel) {
        this.documentoModel = documentoModel;
    }

    public List<Documento> getInformes() {
        return informes;
    }

    public void setInformes(List<Documento> informes) {
        this.informes = informes;
    }

    public String getFltIdTipo() {
        return fltIdTipo;
    }

    public void setFltIdTipo(String fltIdTipo) {
        this.fltIdTipo = fltIdTipo;
    }

    public String getFltEstado() {
        return fltEstado;
    }

    public void setFltEstado(String fltEstado) {
        this.fltEstado = fltEstado;
    }

    public String getFltIdentificacionBien() {
        return fltIdentificacionBien;
    }

    public void setFltIdentificacionBien(String fltIdentificacionBien) {
        this.fltIdentificacionBien = fltIdentificacionBien;
    }

    public String getFltDescripcionBien() {
        return fltDescripcionBien;
    }

    public void setFltDescripcionBien(String fltDescripcionBien) {
        this.fltDescripcionBien = fltDescripcionBien;
    }

    public String getFltMarcaBien() {
        return fltMarcaBien;
    }

    public void setFltMarcaBien(String fltMarcaBien) {
        this.fltMarcaBien = fltMarcaBien;
    }

    public String getFltModeloBien() {
        return fltModeloBien;
    }

    public void setFltModeloBien(String fltModeloBien) {
        this.fltModeloBien = fltModeloBien;
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
    public ListarInformesTecnicosController() {
        super();
    }

    @PostConstruct
    public final void inicializar() {

        //Se consultan los estados por dominio
        estadosOptions = new ArrayList<SelectItem>();
        tipoOptions = new ArrayList<SelectItem>();
        for (Estado estado : this.estadosPorDominio(Constantes.DOMINIO_INFORME_TECNICO)) {
            estadosOptions.add(new SelectItem(estado.getId().toString(), estado.getNombre()));
        }
        //Se consultan los tipos por dominio
        for (Tipo tipo : this.tiposPorDominio(Constantes.DOMINIO_INFORME_TECNICO)) {
             tipoOptions.add(new SelectItem(tipo.getId().toString(), tipo.getNombre()));
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
            // Se cuenta la cantidad de informes
            this.contarInformes();
            this.setPrimerRegistro(1);

            this.listarInformes();

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerListarInformesTecnicos.cambioFiltro"));
        }

    }

    /**
     * Contabiliza los informes
     */
    private void contarInformes() {
        try {
            //Se cuenta la cantidad de registros
            Long contador = documentoModel.consultaCantidadRegistros(unidadEjecutora, 
                    this.tipoPorId(Long.parseLong(fltIdTipo)),
                    fltIdentificacionBien, 
                    fltDescripcionBien, 
                    fltMarcaBien, 
                    fltModeloBien, 
                    this.estadoPorId(Long.parseLong(fltEstado)),
                    Constantes.DISCRIMINATOR_DOCUMENTO_INFORME_TECNICO);

            //Se actualiza la cantidad de registros segun los filtros
            this.setCantidadRegistros(contador.intValue());

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerListarInformesTecnicos.contarBienes"));
        }
    }

    /**
     * Lista los informes
     */
    private void listarInformes() {
        try {
            this.informes = documentoModel.listarInformes(unidadEjecutora,
                    this.tipoPorId(Long.parseLong(fltIdTipo)) ,
                    fltIdentificacionBien,
                    fltDescripcionBien,
                    fltMarcaBien,
                    fltModeloBien,
                    this.estadoPorId(Long.parseLong(fltEstado)),
                    this.getPrimerRegistro() - 1,
                    this.getUltimoRegistro(),
                    Constantes.DISCRIMINATOR_DOCUMENTO_INFORME_TECNICO);
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerListarInformesTecnicos.listarBienes"));
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
        this.contarInformes();
        this.listarInformes();

    }

// </editor-fold>  
}
