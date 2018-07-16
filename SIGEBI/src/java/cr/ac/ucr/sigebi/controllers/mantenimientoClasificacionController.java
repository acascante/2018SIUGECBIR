/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.commands.MantenClasificCommand;
import cr.ac.ucr.sigebi.domain.Categoria;
import cr.ac.ucr.sigebi.domain.Clasificacion;
import cr.ac.ucr.sigebi.domain.SubCategoria;
import cr.ac.ucr.sigebi.domain.SubClasificacion;
import cr.ac.ucr.sigebi.models.BienCaracteristicaModel;
import cr.ac.ucr.sigebi.models.CategoriaModel;
import cr.ac.ucr.sigebi.models.ClasificacionModel;
import cr.ac.ucr.sigebi.models.SubCategoriaModel;
import cr.ac.ucr.sigebi.models.SubClasificacionModel;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author jorge.serrano
 */
@Controller(value = "mantClasifController")
@Scope("session")
public class mantenimientoClasificacionController  extends BaseController {

    
    //<editor-fold defaultstate="collapsed" desc="Variables de la Clase">
    private List<SelectItem> itemsCategoria;
    private List<SelectItem> itemsClasificacion;
    private List<SelectItem> itemsSubCategoria;
    private List<SelectItem> itemsSubClasificacion;
    
    
    private MantenClasificCommand command;
    
    private boolean disableSubCategorias;
    private boolean disableClasificaciones;
    private boolean disableSubClasificaciones;
    
    private boolean mostrarVentClasificacion;
    private boolean mostrarVentSubClasificacion;
    
    @Resource
    private CategoriaModel modelCategoria;
    @Resource
    private ClasificacionModel modelClasificacion;
    @Resource
    private SubCategoriaModel modelSubCategoria;
    @Resource
    private SubClasificacionModel modelSubClasificacion;
    //</editor-fold>

    
    
    public mantenimientoClasificacionController() {
        super();
    }
    
    
    
    @PostConstruct
    private void cargarCategorias(){
        
        command = new MantenClasificCommand();
        
        itemsCategoria = new ArrayList<SelectItem>();
        itemsSubCategoria = new ArrayList<SelectItem>();
        itemsSubClasificacion = new ArrayList<SelectItem>();
        itemsClasificacion = new ArrayList<SelectItem>();
            
        List<Categoria> categorias = modelCategoria.listar();
        if (!categorias.isEmpty()) {
            itemsCategoria = new ArrayList<SelectItem>();
            for (Categoria item : categorias) {
                itemsCategoria.add(new SelectItem(item.getId(), item.getDescripcion()));
                command.getItemsCategoria().put(item.getId(), item);
            }
        }
        mostrarVentClasificacion = false;
        mostrarVentSubClasificacion = false;
        
        this.setDisableSubCategorias(true);
        this.setDisableClasificaciones(true);
        this.setDisableSubClasificaciones(true);
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="Sets & Gets ">

    public boolean isMostrarVentClasificacion() {
        return mostrarVentClasificacion;
    }

    public void setMostrarVentClasificacion(boolean mostrarVentClasificacion) {
        this.mostrarVentClasificacion = mostrarVentClasificacion;
    }

    public boolean isMostrarVentSubClasificacion() {
        return mostrarVentSubClasificacion;
    }

    public void setMostrarVentSubClasificacion(boolean mostrarVentSubClasificacion) {
        this.mostrarVentSubClasificacion = mostrarVentSubClasificacion;
    }

    
    
    public List<SelectItem> getItemsCategoria() {
        return itemsCategoria;
    }

    public void setItemsCategoria(List<SelectItem> itemsCategoria) {
        this.itemsCategoria = itemsCategoria;
    }

    public List<SelectItem> getItemsClasificacion() {
        return itemsClasificacion;
    }

    public void setItemsClasificacion(List<SelectItem> itemsClasificacion) {
        this.itemsClasificacion = itemsClasificacion;
    }

    public List<SelectItem> getItemsSubCategoria() {
        return itemsSubCategoria;
    }

    public void setItemsSubCategoria(List<SelectItem> itemsSubCategoria) {
        this.itemsSubCategoria = itemsSubCategoria;
    }

    public List<SelectItem> getItemsSubClasificacion() {
        return itemsSubClasificacion;
    }

    public void setItemsSubClasificacion(List<SelectItem> itemsSubClasificacion) {
        this.itemsSubClasificacion = itemsSubClasificacion;
    }

    public MantenClasificCommand getCommand() {
        return command;
    }

    public void setCommand(MantenClasificCommand command) {
        this.command = command;
    }

    public boolean isDisableSubCategorias() {
        return disableSubCategorias;
    }

    public void setDisableSubCategorias(boolean disableSubCategorias) {
        this.disableSubCategorias = disableSubCategorias;
    }

    public boolean isDisableClasificaciones() {
        return disableClasificaciones;
    }

    public void setDisableClasificaciones(boolean disableClasificaciones) {
        this.disableClasificaciones = disableClasificaciones;
    }

    public boolean isDisableSubClasificaciones() {
        return disableSubClasificaciones;
    }

    public void setDisableSubClasificaciones(boolean disableSubClasificaciones) {
        this.disableSubClasificaciones = disableSubClasificaciones;
    }
    
    
    
    
    
    //</editor-fold>
    
    
    public void btnNuevaClasificacion(){
        if(command.getIdSubCategoria() > 0){
            SubCategoria categ = command.getItemsSubCategoria().get(command.getIdSubCategoria());
            Clasificacion valor = new Clasificacion();
            valor.setSubCategoria(categ);
            valor.setEstado(this.estadoPorId(1L));
            command.setClasificacionSeleccionada( valor );
            mostrarVentClasificacion = true;
        }
        else{
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.MantClasific.ErrorSeleccSubCateg"));
        }
    }
    
    public void editarClasificacion(){
        try{
            if(command.getIdSubCategoria() > 0){
                Clasificacion valor = command.getItemsClasificacion().get(command.getIdClasificacion());
                command.setClasificacionSeleccionada( valor );
                mostrarVentClasificacion = true;
            }
            else{
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.MantClasific.ErrorSeleccSubCateg"));
            }
        }
        catch(Exception e){
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.MantClasific.ErrorClasificacion"));
        }
    }
    
    public void cerrarVentClasificacion(){
        mostrarVentClasificacion = false;
    }
    
    
    public void guardarClasificacion(){
        try{
            Clasificacion valor = command.getClasificacionSeleccionada();
            
            if(valor.getNombre().trim().length() < 2){
                Mensaje.agregarErrorAdvertencia( Util.getEtiquetas("sigebi.MantClasific.ErrorTamanno"));
                return;
            }
            modelClasificacion.guardar(valor);
            mostrarVentClasificacion = false;
            
            SubCategoria categ = command.getItemsSubCategoria().get(command.getIdSubCategoria());
            cargarClasificaciones(categ);
        }
        catch(Exception e){
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.MantClasific.ErrorClasificacion"));
        }
    }
    
    public void editarSubClasificacion(){
        try{
            if(command.getIdSubClasificacion() > 0){
                SubClasificacion valor = command.getItemsSubClasificacion().get(command.getIdSubClasificacion());
                command.setSubClasificacionSeleccionada( valor );
                mostrarVentSubClasificacion = true;
            }
            else{
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.MantClasific.ErrorSeleccSubClasific"));
            }
        }
        catch(Exception e){
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.MantClasific.ErrorSeleccSubClasific"));
        }
    }
    
    
    public void btnNuevaSubClasificacion(){
        if(command.getIdClasificacion() > 0){
            Clasificacion clasif = command.getItemsClasificacion().get(command.getIdClasificacion());
            SubClasificacion valor = new SubClasificacion();
            valor.setClasificacion(clasif);
            valor.setEstado(this.estadoPorId(1L));
            command.setSubClasificacionSeleccionada( valor );
            mostrarVentSubClasificacion = true;
        }
        else{
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.MantClasific.ErrorSeleccClasific"));
        }
    }
    
    public void cerrarVentSubClasificacion(){
        mostrarVentSubClasificacion = false;
    }
    
    
    public void guardarSubClasificacion(){
        try{
            SubClasificacion valor = command.getSubClasificacionSeleccionada();
            
            if(valor.getNombre().trim().length() < 2){
                Mensaje.agregarErrorAdvertencia( Util.getEtiquetas("sigebi.MantClasific.ErrorTamanno"));
                return;
            }
            
            modelSubClasificacion.guardar(valor);
            mostrarVentSubClasificacion = false;
            
            Clasificacion clasif = command.getItemsClasificacion().get(command.getIdClasificacion());
            cargarSubClasificaciones(clasif);
        }
        catch(Exception e){
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.MantClasific.ErrorSubClasificacion"));
        }
    }
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Categorias y Clasificaciones">
    
    
    public void cambioCategoria(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        cargarSubCategorias(command.getItemsCategoria().get(command.getIdCategoria()));
    }

    public void cambioSubCategoria(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        cargarClasificaciones(command.getItemsSubCategoria().get(command.getIdSubCategoria()));
    }

    public void cambioClasificacion(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        cargarSubClasificaciones(command.getItemsClasificacion().get(command.getIdClasificacion()));
    }

    public void cambioSubClasificacion(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        Long id = command.getIdSubClasificacion();
        //Mensaje.agregarInfo("Seleccionó Sub Clasificación id = "+id);
    }

    private void cargarSubCategorias(Categoria categoria) {
        try {
            //Se limpian subcategoria, clasificacion y  sub clasificacion
            if (itemsSubCategoria != null) {
                itemsSubCategoria.clear();
                
            }
                command.setIdSubCategoria(Constantes.DEFAULT_ID);
            if (command.getItemsClasificacion() != null) {
                command.getItemsClasificacion().clear();
                this.setDisableClasificaciones(true);
                command.setIdClasificacion(Constantes.DEFAULT_ID);
            }
            if (itemsSubClasificacion != null) {
                itemsSubClasificacion.clear();
                this.setDisableSubClasificaciones(true);
                command.setIdSubClasificacion(Constantes.DEFAULT_ID);
            }

            if (Constantes.DEFAULT_ID.equals(command.getIdCategoria())) {
                command.setIdSubCategoria(Constantes.DEFAULT_ID);
                this.setDisableSubCategorias(true);
                cargarClasificaciones(command.getItemsSubCategoria().get(command.getIdSubCategoria()));
            } else {
                List<SubCategoria> subCategorias = modelSubCategoria.listar(categoria);
                if (!subCategorias.isEmpty()) {
                    this.setDisableSubCategorias(false);
                    itemsSubCategoria = new ArrayList<SelectItem>();
                    command.getItemsSubCategoria().clear();
                    for (SubCategoria item : subCategorias) {
                        itemsSubCategoria.add(new SelectItem(item.getId(), item.getDescripcion()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                        command.getItemsSubCategoria().put(item.getId(), item);
                    }
                }
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.cargarSubCategorias"));
        }

    }

    private void cargarClasificaciones(SubCategoria subCategoria) {
        try {
            //Se limpian clasificacion y  sub clasificacion
            if (itemsClasificacion != null) 
                itemsClasificacion.clear();
            command.setIdClasificacion(Constantes.DEFAULT_ID);
            
            if (itemsSubClasificacion != null) {
                itemsSubClasificacion.clear();
                this.setDisableSubClasificaciones(true);
                command.setIdSubClasificacion(Constantes.DEFAULT_ID);
            }
            if (Constantes.DEFAULT_ID.equals(command.getIdSubCategoria())) {
                command.setIdClasificacion(Constantes.DEFAULT_ID);
                this.setDisableClasificaciones(true);
                //cargarSubClasificaciones(null);
            } else {
                List<Clasificacion> clasificaciones = modelClasificacion.listarPorSubCategoria(subCategoria);
                if (!clasificaciones.isEmpty()) {
                    this.setDisableClasificaciones(false);
                    this.itemsClasificacion.clear();
                    command.setItemsClasificacion( new HashMap<Long, Clasificacion>() ) ;
                    for (Clasificacion item : clasificaciones) {
                        this.itemsClasificacion.add(new SelectItem(item.getId(), item.getNombre())); 
                        command.getItemsClasificacion().put(item.getId(), item);
                    }
                }
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.cargarClasificaciones"));
        }

    }

    private void cargarSubClasificaciones(Clasificacion clasificacion) {
        try {
            //Se limpian sub clasificacion
            if (itemsSubClasificacion != null) {
                itemsSubClasificacion.clear();
            }
            if (Constantes.DEFAULT_ID.equals(command.getIdClasificacion())) {
                command.setIdSubClasificacion(Constantes.DEFAULT_ID);
                this.setDisableSubClasificaciones(true);
            } else {
                List<SubClasificacion> subClasificaciones = modelSubClasificacion.listar(clasificacion);
                if (!subClasificaciones.isEmpty()) {
                    itemsSubClasificacion = new ArrayList<SelectItem>();
                    command.getItemsSubClasificacion().clear();
                    for (SubClasificacion item : subClasificaciones) {
                        this.setDisableSubClasificaciones(false);
                        itemsSubClasificacion.add(new SelectItem(item.getId(), item.getNombre()));
                        command.getItemsSubClasificacion().put(item.getId(), item);
                    }
                }
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.cargarSubClasificaciones"));
        }

    }
    
    
    //</editor-fold>
    
    
    
    
    
}
