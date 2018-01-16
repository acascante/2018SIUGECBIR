/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.entities.ViewBienEntity;
import cr.ac.ucr.sigebi.models.ViewBienModel;
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
@Controller(value = "controllerListarBien")
@Scope("session")
public class ListadoBienesGeneralController extends BaseController{
    
    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    @Resource
    private ViewBienModel viewBienMod;

    @Resource
    private EstadoModel estadoModel;

    // Lista de los Bienes
    List<ViewBienEntity> bienes;
    Map<Integer, ViewBienEntity> bienesSeleccionados;
    List<ViewBienEntity> bienesAsociados;
    
    
    // Filtros para listar los Bienes 
    String fltIdBien = "";
    String fltDescripcion = "";
    String fltMarca = "";
    String fltModelo = "";
    String fltSerie = "";
    String fltEstado = "-1";
    Integer[] estadosBienes = null;//{Constantes.ESTADO_BIEN_PENDIENTE, Constantes.ESTADO_BIEN_PENDIENTE_SINCRONIZAR};

    
    boolean permiteSeleccionar = false;
    boolean mostrarDetalle = true;
    
    // comboBox subCategorias
    List<SelectItem> estadosOptions;
    Integer consultaBienes = 1;

      
    boolean mostrarDialogBienes;
    
   
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">

    public boolean isPermiteSeleccionar() {
        return permiteSeleccionar;
    }

    public void setPermiteSeleccionar(boolean permiteSeleccionar) {
        this.permiteSeleccionar = permiteSeleccionar;
    }

    public List<ViewBienEntity> getBienesAsociados() {
        return bienesAsociados;
    }

    public void setBienesAsociados(List<ViewBienEntity> bienesAsociados) {
        this.bienesAsociados = bienesAsociados;
    }

    
    public boolean isMostrarDialogBienes() {
        return mostrarDialogBienes;
    }

    public void setMostrarDialogBienes(boolean mostrarDialogBienes) {
        this.mostrarDialogBienes = mostrarDialogBienes;
    }
    
    
    public List<ViewBienEntity> getBienes() {
        return bienes;
    }

    public void setBienesCommand(List<ViewBienEntity> bienes) {
        this.bienes = bienes;
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
    //</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public ListadoBienesGeneralController() {
        super();
    }

    @PostConstruct
    public final void inicializar() {

        bienes = new ArrayList<ViewBienEntity>();
        bienesSeleccionados = new HashMap<Integer, ViewBienEntity>();
        bienesAsociados = new ArrayList<ViewBienEntity>();
        
        //Cargar Estados Filtros
        List<Estado> estados = new ArrayList<Estado>();
        estados = estadoModel.listarPorDominio(Constantes.DOMINI0_ESTADO_BIEN);
        estadosOptions = new ArrayList<SelectItem>();
        for (Estado item : estados) {
            estadosOptions.add(new SelectItem(item.getIdEstado().toString(), item.getNombre()));
        }
        mostrarDialogBienes = false;
        
        inicializaBuscarBienes();
    }
    

    
    
    protected void inicializaBuscarBienes(){
        try{
            bienes = new ArrayList<ViewBienEntity>();
            bienesSeleccionados = new HashMap<Integer, ViewBienEntity>();
            bienesAsociados = new ArrayList<ViewBienEntity>();
            
            iniciaListadoBienes();
            
        }catch(Exception err){
            Mensaje.agregarErrorAdvertencia("Error al inicializar el modal de los bienes");
        }
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
    
    public void cambioFiltroEstado(ValueChangeEvent event) {
        try {
            
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            
            String valor = event.getNewValue().toString();
            
            estadosBienes = new Integer[1];
            if(!fltEstado.equals("-1"))
                estadosBienes[0] = Integer.parseInt(valor);
            else
                estadosBienes = null;
            
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
            Long contador;
            
            contador = viewBienMod.contarBienes(unidadEjecutora,
                    fltIdBien,
                    fltDescripcion,
                    fltMarca,
                    fltModelo,
                    fltSerie,
                    estadosBienes
            );
            
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
                case 1: listadoRegistro();break;  
                case 2: listadoActas();break;   
                default:listadoRegistro();  
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
        List<ViewBienEntity> resp  = viewBienMod.listarBienes(unidadEjecutora,
                    fltIdBien,
                    fltDescripcion,
                    fltMarca,
                    fltModelo,
                    fltSerie,
                    estadosBienes,
                    this.getPrimerRegistro()-1, 
                    this.getUltimoRegistro()
            );

        for(ViewBienEntity valor : resp) {     // foreach grade in grades
            //revisa que el bien este seleccionado
            valor.setSeleccionado(bienesSeleccionados.containsKey(valor.getIdBien()));
            bienes.add(valor);
        }
    }
    
    
    //Listado para El registro
    private void listadoRegistro(){
        
        
        bienes = viewBienMod.listarBienes(unidadEjecutora,
                fltIdBien,
                fltDescripcion,                        
                fltMarca,

                fltModelo,
                fltSerie,
                estadosBienes,
                this.getPrimerRegistro()-1, 
                this.getUltimoRegistro()
        );
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
            
            //bienesAsociados = new ArrayList<ViewBienEntity>();
            ViewBienEntity bien = (ViewBienEntity) pEvent.getComponent().getAttributes().get("bienSeleccionado");
            if(bienesSeleccionados.containsKey(bien.getIdBien())){
                bienesSeleccionados.remove(bien.getIdBien());
            }else{
                bienesSeleccionados.put(bien.getIdBien(), bien);
            }
            //bienesAsociados = new Array<>(bienesSeleccionados.values());
            bienesAsociados = new ArrayList<ViewBienEntity>( bienesSeleccionados.values() );
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
