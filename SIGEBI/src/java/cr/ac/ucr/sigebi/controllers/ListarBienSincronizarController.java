/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.EstadoModel;
import cr.ac.ucr.sigebi.entities.EstadoEntity;
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
import cr.ac.ucr.sigebi.entities.BienEntity;
import cr.ac.ucr.sigebi.entities.ViewBienEntity;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

/**
 *
 * @author jairo.cisneros
 */
@Controller(value = "controllerListarBienSincronizar")
@Scope("session")
public class ListarBienSincronizarController extends BaseController {

    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    @Resource
    private BienModel bienMod;

    @Resource
    private EstadoModel estadoModel;

    // Lista de los Bienes
    List<ViewBienEntity> bienes;

    // Filtros para listar los Bienes 
    String fltIdBien = "";
    String fltDescripcion = "";
    String fltMarca = "";
    String fltModelo = "";
    String fltSerie = "";
    String fltEstado = "-1";
    Integer[] estadosBienes = {Constantes.ESTADO_BIEN_PENDIENTE, Constantes.ESTADO_BIEN_PENDIENTE_SINCRONIZAR};

    // comboBox subCategorias
    List<SelectItem> estadosOptions;
    
    Map<Integer, ViewBienEntity> bienesPorSincronizar;
    Map<Integer, ViewBienEntity> bienesPorRechazar;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">
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
    public ListarBienSincronizarController() {
        super();
    }

    @PostConstruct
    public final void inicializar() {

        // Mapas para los bienese seleccionados en pantalla
        bienesPorSincronizar = new HashMap<Integer, ViewBienEntity>();
        bienesPorRechazar = new HashMap<Integer, ViewBienEntity>();
        bienesEnviarSincronizar = new HashMap<Integer, ViewBienEntity>();

        //Cargar Estados Filtros
        List<Estado> estados = new ArrayList<Estado>();
        estados.add(estadoModel.buscarPorDominioEstado(Constantes.DOMINI0_ESTADO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE));
        estados.add(estadoModel.buscarPorDominioEstado(Constantes.DOMINI0_ESTADO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE_SINCRONIZAR));

        estadosOptions = new ArrayList<SelectItem>();
        for (Estado item : estados) {
            estadosOptions.add(new SelectItem(item.getId().toString(), item.getNombre()));
        }

        // Se cuenta la cantidad de bienes
        this.setPrimerRegistro(1);
        this.contarBienes();

        //Se consulta la lista de los bienes
        this.listarBienes();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">


    /**
     * check bien para sincronizar
     *
     * @param pEvent
     */
    public void checkBienPorSincronizar(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            
            ViewBienEntity bienSinco = (ViewBienEntity) pEvent.getComponent().getAttributes().get("bienSeleccionado");
            if(bienesPorSincronizar.containsKey(bienSinco.getIdBien())){
                bienesPorSincronizar.remove(bienSinco.getIdBien());
            }else{
                bienesPorSincronizar.put(bienSinco.getIdBien(), bienSinco);
            }
            
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.checkBienPorSincronizar"));
        }
    }

    public void checkBienPorRechazar(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            
            ViewBienEntity bienSinco = (ViewBienEntity) pEvent.getComponent().getAttributes().get("bienSeleccionado");
            if(bienesPorRechazar.containsKey(bienSinco.getIdBien())){
                bienesPorRechazar.remove(bienSinco.getIdBien());
            }else{
                bienesPorRechazar.put(bienSinco.getIdBien(), bienSinco);
            }
            
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.checkBienPorRechazar"));
        }
    }
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
            this.contarBienes();

            this.setPrimerRegistro(1);

            this.listarBienes();
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.cambioFiltro"));
        }

    }

    private void contarBienes() {       
        try {
            Long contador;
            if (fltEstado == null || (fltEstado != null && fltEstado.equals("-1"))) {
                contador = bienMod.contarBienes(unidadEjecutora,
                        fltIdBien,
                        fltDescripcion,
                        fltMarca,
                        fltModelo,
                        fltSerie,
                        estadosBienes
                );
            } else {
                contador = bienMod.contarBienes(unidadEjecutora,
                        fltIdBien,
                        fltDescripcion,
                        fltMarca,
                        fltModelo,
                        fltSerie,
                        fltEstado
                );
            } 
            
            //Se actualiza la cantidad de registros segun los filtros
            this.setCantidadRegistros(contador.intValue());
            
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.contarBienes"));
        }
    }
    
    private void listarBienes() {
        try {
            if (fltEstado == null || (fltEstado != null && fltEstado.equals("-1"))) {
                this.bienes = bienMod.listarBienes(unidadEjecutora,
                        fltIdBien,
                        fltDescripcion,
                        fltMarca,
                        fltModelo,
                        fltSerie,
                        estadosBienes,
                        this.getPrimerRegistro()-1, 
                        this.getUltimoRegistro()
                );
            } else {
                this.bienes = bienMod.listarBienes(unidadEjecutora,
                        fltIdBien,
                        fltDescripcion,
                        fltMarca,
                        fltModelo,
                        fltSerie,
                        fltEstado,
                        this.getPrimerRegistro()-1, 
                        this.getUltimoRegistro()
                );
            }
            for (ViewBienEntity bienEntity : this.bienes) {
                bienEntity.setSeleccionado(bienesPorSincronizar.containsKey(bienEntity.getIdBien()) || bienesPorRechazar.containsKey(bienEntity.getIdBien()));
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.listarBienes"));
        }
    }
    
    
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Paginacion">
    
    /**
     * Pasa a la pagina sub-set de estudiantes
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
     * Pasa al siguiente sub-set de estudiantes
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
     * Pasa al anterior sub-set de estudiantes
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
     * Pasa al primero sub-set de estudiantes
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
     * Pasa al ultimo sub-set de Estudiantes
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
    

// </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Panel Observacion Sincronizar o Rechazar">
    
    boolean panelObservaVisible = false;
    String observacionCliente = "";
    
    public void rechazarBien() {
        try{
        if (this.observacionCliente == null || this.observacionCliente.isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.rechazarBien.sin.observacion"));
        } else {
            Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;
            // TODO pendiente actualizar model y dao de bien
            //bienMod.cambiaEstadoBien(this.bienesEnviarSincronizar.values(), estadoModel.buscarPorDominioEstado(Constantes.DOMINI0_ESTADO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE), observacionCliente, telefono);
            this.bienesEnviarSincronizar.clear();

            observacionCliente = "";

            //Se consulta la vista nuevamente
            this.listarBienes();

            //Se oculta el panel
            this.cerrarPanelObserva();
        }
        }catch(Exception err){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.rechazarBien.error"));
        }
    }

    public void solicitarSincronizacion() {       
        if (this.bienesPorSincronizar.isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.solicitarSincronizacion.sin.bienes.sincronizar"));
        } else {
            Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;
            //bienMod.cambiaEstadoBien(this.bienesPorSincronizar.values(), estadoModel.buscarPorDominioEstado(Constantes.ESTADO_BIEN_PENDIENTE_SINCRONIZAR, Constantes.DOMINI0_ESTADO_BIEN), observacionCliente, telefono);
            this.bienesPorSincronizar.clear();
            //Se consulta la vista nuevamente
            this.listarBienes();
        }
    }

    public boolean mostrarPanelObserva(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return false;
        }

        if(this.bienesEnviarSincronizar.isEmpty()){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.mostrarPanelObserva.sin.bienes.rechazar"));
            return false;
        }else{
            //Se presenta el panel de obervacion
            panelObservaVisible = true;
            return true;
        }
    }

    public boolean cerrarPanelObserva() {
        panelObservaVisible = false;
        return false;
    }

    public boolean isPanelObservaVisible() {
        return panelObservaVisible;
    }

    public void setPanelObservaVisible(boolean panelObservaVisible) {
        this.panelObservaVisible = panelObservaVisible;
    }

    public String getObservacionCliente() {
        return observacionCliente;
    }

    public void setObservacionCliente(String observacionCliente) {
        this.observacionCliente = observacionCliente;
    }

    //</editor-fold>

    
    
    
    //<editor-fold defaultstate="collapsed" desc="Sincronizar">
    
    
    // Mapas para los bienese seleccionados en pantalla
    HashMap<Integer, ViewBienEntity> bienesEnviarSincronizar;// = new HashMap<Integer, VistaBienes>();
    
    //int estadoPendiente = 3;
    boolean sincronizar;

    public boolean isSincronizar() {
        return (bienesEnviarSincronizar.isEmpty());
    }

    public void setSincronizar(boolean sincronizar) {
        this.sincronizar = sincronizar;
    }

    
    
    
    @Resource
    EstadoModel modelEstado;
    
    public void checkBienEnviarSincronizar(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            
            ViewBienEntity bienSincro = (ViewBienEntity) pEvent.getComponent().getAttributes().get("bienSeleccionado");
            if(bienesEnviarSincronizar.containsKey(bienSincro.getIdBien())){
                bienesEnviarSincronizar.remove(bienSincro.getIdBien());
                //Mensaje.agregarInfo("Se agregó el bien tamaño " + bienesEnviarSincronizar.size() );
            }else{
                bienesEnviarSincronizar.put(bienSincro.getIdBien(), bienSincro);
                //Mensaje.agregarInfo("Se agregó el bien tamaño " + bienesEnviarSincronizar.size() );
            }
            
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }

    public void sincronizarTodo(ActionEvent pEvent) {
        try{
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            
            if(bienesEnviarSincronizar.isEmpty()){
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.sincronizarBien.Lista.ListaVacia"));
                return;
            }
            
            // TODO verificar el dominio de este estado
            //Estado estadoSinc = modelEstado.obtenerPorId(4);
            Estado estadoSinc = new Estado();
            String resp = "";
            for(Map.Entry<Integer, ViewBienEntity> bienSincro : bienesEnviarSincronizar.entrySet()){
                BienEntity bien = bienMod.traerPorId(bienSincro.getKey());
                //bien.setIdEstado(estadoSinc);
                resp += sincronizar(bien);
                bienesEnviarSincronizar.remove(bienSincro.getKey());
            }
            bienesPorSincronizar.clear();
            
            this.listarBienes();
            
            if(resp.equals("")){
                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.sincronizarBienes.Exito"));
            }
            else{
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.sincronizarBien.Lista.ErrorEnviarSincronizar"));
            }
            
            //Actuali
            listarBienes();
            
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.checkBienPorSincronizar"));
        }
    }

    
    private String sincronizar(BienEntity bien){
        try
        {
            return bienMod.sincronizarBien(bien, lVistaUsuario.getgUsuarioActual().getIdUsuario());
        }
        catch(NullPointerException err)
        {
            return "Error por valor nulo.";
        }
        catch(Exception err){
            return err.getMessage();
        }
    } 
    
    
    
    //</editor-fold>

    
    
    
}
