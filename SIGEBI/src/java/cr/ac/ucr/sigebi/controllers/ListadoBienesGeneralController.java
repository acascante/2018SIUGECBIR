/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.EstadoModel;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author jorge.serrano
 */
@Controller(value = "ListadoBienesGeneralController")
@Scope("session")
public class ListadoBienesGeneralController extends BaseController{
    
    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    
    @Resource
    private BienModel bienModel;

    @Resource
    private EstadoModel estadoModel;
    
    List<Bien> bienes;
    Map<Long, Bien> bienesSeleccionados;
    List<Bien> bienesAsociados;
    
    List<SelectItem> estadosOptions;
    Integer consultaBienes = 1;
    
    boolean permiteSeleccionar = false;
    boolean mostrarDetalle = true;
    boolean mostrarDialogBienes;
    
    
    static String fltIdBien;
    static String fltDescripcion;
    static String fltMarca;
    static String fltModelo;
    static String fltSerie;
    static Tipo fltTipo;
    
    Estado estadoIntInfTecAprobado;
    Tipo tipoInfTecDonacion;
    Tipo tipoInfTecDesecho;
    
    Estado estadoBienActivo;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">

    public void setFltTipo(Tipo fltTipo) {
        this.fltTipo = fltTipo;
    }

    public Tipo getTipoInfTecDonacion() {
        return tipoInfTecDonacion;
    }

    public Tipo getTipoInfTecDesecho() {
        return tipoInfTecDesecho;
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
    
    public boolean isPermiteSeleccionar() {
        return permiteSeleccionar;
    }

    public void setPermiteSeleccionar(boolean permiteSeleccionar) {
        this.permiteSeleccionar = permiteSeleccionar;
    }

    public boolean isMostrarDialogBienes() {
        return mostrarDialogBienes;
    }

    public void setMostrarDialogBienes(boolean mostrarDialogBienes) {
        this.mostrarDialogBienes = mostrarDialogBienes;
    }
    
    public List<SelectItem> getEstadosOptions() {
        return estadosOptions;
    }

    public void setEstadosOptions(List<SelectItem> estadosOptions) {
        this.estadosOptions = estadosOptions;
    }

    public List<Bien> getBienes() {
        return bienes;
    }

    public void setBienes(List<Bien> bienes) {
        this.bienes = bienes;
    }

    public Map<Long, Bien> getBienesSeleccionados() {
        return bienesSeleccionados;
    }

    public void setBienesSeleccionados(Map<Long, Bien> bienesSeleccionados) {
        this.bienesSeleccionados = bienesSeleccionados;
    }

    public List<Bien> getBienesAsociados() {
        return bienesAsociados;
    }

    public void setBienesAsociados(List<Bien> bienesAsociados) {
        this.bienesAsociados = bienesAsociados;
    }

    public Integer getConsultaBienes() {
        return consultaBienes;
    }

    public void setConsultaBienes(Integer consultaBienes) {
        this.consultaBienes = consultaBienes;
    }

    public boolean isMostrarDetalle() {
        return mostrarDetalle;
    }

    public void setMostrarDetalle(boolean mostrarDetalle) {
        this.mostrarDetalle = mostrarDetalle;
    }
    //</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public ListadoBienesGeneralController() {
        super();
    }

    @PostConstruct
    public final void inicializar() {

        bienes = new ArrayList<Bien>();
        bienesSeleccionados = new HashMap<Long, Bien>();
        bienesAsociados = new ArrayList<Bien>();
        
        List<Estado> estados = estadoModel.listarPorDominio(Constantes.DOMINIO_BIEN);
        estadosOptions = new ArrayList<SelectItem>();
        for (Estado item : estados) {
            estadosOptions.add(new SelectItem(item.getId().intValue(), item.getNombre()));
        }
        mostrarDialogBienes = false;
        
        estadoBienActivo = estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_ACTIVO);
            
        estadoIntInfTecAprobado = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO
                                                , Constantes.ESTADO_INTERNO_BIEN_APROBACION_APLICADA);
        tipoInfTecDonacion = this.tipoPorDominioValor(Constantes.DOMINIO_INFORME_TECNICO, Constantes.TIPO_INFORME_TECNICO_DONAR);
        tipoInfTecDesecho = this.tipoPorDominioValor(Constantes.DOMINIO_INFORME_TECNICO, Constantes.TIPO_INFORME_TECNICO_DESECHAR);
        
        iniciaListadoBienes();
    }
    //</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="Metodos Listado">
    public void cambioFiltro(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            iniciaListadoBienes();
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.cambioFiltro"));
        }
    }

    protected void iniciaListadoBienes(){
        this.setPrimerRegistro(1);
        this.contarBienes();
        this.listarBienes();
    }
    
    private void contarBienes() {       
        try {
            Long contador = 0l;
            
            switch(consultaBienes){  
                case 1: contador = bienModel.contar(unidadEjecutora
                                                        , 0l
                                                        , fltIdBien
                                                        , fltDescripcion
                                                        , fltMarca
                                                        , fltModelo
                                                        , fltSerie
                                                        , null
                                                        , null
                                                        , estadoBienActivo);
                    //, (Estado[]) null);
                break;  
                case 2: contador = bienModel.contarListadoActas(unidadEjecutora
                                                                , 0l
                                                                , fltIdBien
                                                                , fltDescripcion
                                                                , fltMarca
                                                                , fltModelo
                                                                , fltSerie
                                                                , fltTipo
                                                                , estadoIntInfTecAprobado);
                ;break; 
                case 3: contador = bienModel.contarAsignar(null
                                                        , unidadEjecutora
                                                        , fltIdBien
                                                        , fltDescripcion
                                                        , fltMarca
                        
                                                        , fltModelo
                                                        , fltSerie
                                                        , null
                                                        , null
                                                        , true
                                                        );
                ;break; 
            }  
            
            
            //Se actualiza la cantidad de registros segun los filtros
            this.setCantidadRegistros(contador.intValue());
            
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.contarBienes"));
        }
    }
    
    public void listarBienes() {
        try {
            switch(consultaBienes){  
                case 1: listadoTraslados();break;  
                case 2: listadoActas();break; 
                case 3: listadoResponsable();break; 
            }  
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.listarBienes"));
        }
    }
    
    
    //Listado para Sincronización
    private void listadoActas(){
        bienes.clear();
        
        List<Bien> resp  = bienModel.listadoActas(
                this.getPrimerRegistro()-1
                , getUltimoRegistro()
                , this.unidadEjecutora
                , 0l
                , fltIdBien
                , fltDescripcion
                , fltMarca
                , fltModelo
                , fltSerie
                , fltTipo
                , estadoIntInfTecAprobado
            );

        for(Bien valor : resp) {     // foreach grade in grades
            //revisa que el bien este seleccionado
            valor.setSeleccionado(bienesSeleccionados.containsKey(valor.getId()));
            bienes.add(valor);
        }
    }
    
    
    //Listado para Sincronización
    private void listadoResponsable(){
        bienes.clear();
        
        List<Bien> resp  = bienModel.listarAsignar(
                                            this.getPrimerRegistro()-1
                                            , getUltimoRegistro()
                                            , null
                                            , this.unidadEjecutora
                                            , fltIdBien
                
                                            , fltDescripcion
                                            , fltMarca
                                            , fltModelo
                                            , fltSerie
                                            , estadoBienActivo
                
                                            , null
                                            , true
                                                );

        for(Bien valor : resp) {     // foreach grade in grades
            //revisa que el bien este seleccionado
            valor.setSeleccionado(bienesSeleccionados.containsKey(valor.getId()));
            bienes.add(valor);
        }
    }
    
    
    
    //Listado para El registro
    private void listadoTraslados(){
        bienes.clear();
        List<Bien> resp = bienModel.listar(
                                    this.getPrimerRegistro()-1
                                    , getUltimoRegistro()
                                    , this.unidadEjecutora
                                    , 0l
                                    , fltIdBien
                                    , fltDescripcion
                                    , fltMarca
                                    , fltModelo
                                    , fltSerie
                                    , null
                                    , null
                , estadoBienActivo);
                //, (Estado[]) null);
           
        
        for(Bien valor : resp) {     // foreach grade in grades
            //revisa que el bien este seleccionado
            valor.setSeleccionado(bienesSeleccionados.containsKey(valor.getId()));
            bienes.add(valor);
        }
    }
    
    //</editor-fold>


    //<editor-fold defaultstate="collapsed" desc="Metodos Seleccionar Lista">

    public void checkBienSeleccionado(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            
            //bienesAsociados = new ArrayList<Bien>();
            Bien bien = (Bien) pEvent.getComponent().getAttributes().get("bienSeleccionado");
            if(bienesSeleccionados.containsKey(bien.getId())){
                bienesSeleccionados.remove(bien.getId());
            }else{
                bienesSeleccionados.put(bien.getId(), bien);
            }
            //bienesAsociados = new Array<>(bienesSeleccionados.values());
            bienesAsociados = new ArrayList<Bien>( bienesSeleccionados.values() );
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.checkBienPorSincronizar"));
        }
    }

    
    
    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="Paginacion Bienes Asociados">
    
    
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
        this.listarBienes();
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
        this.listarBienes();
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
        this.listarBienes();
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
        this.listarBienes();
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
        this.listarBienes();
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
        this.listarBienes();

    }

    public void mostrarBienes(){
        
        this.setCantRegistroPorPagina(getCantRegistroPorPagina());
        this.setPrimerRegistro(1);
        this.listarBienes();
    }
    
// </editor-fold>  
    
}
