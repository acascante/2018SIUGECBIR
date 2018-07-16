/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.Categoria;
import cr.ac.ucr.sigebi.domain.Clasificacion;
import cr.ac.ucr.sigebi.domain.SubCategoria;
import cr.ac.ucr.sigebi.domain.SubClasificacion;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jorge.serrano
 */
public class MantenClasificCommand {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    private Map<Long, Categoria> itemsCategoria;
    private Map<Long, Clasificacion> itemsClasificacion;
    private Map<Long, SubCategoria> itemsSubCategoria;
    private Map<Long, SubClasificacion> itemsSubClasificacion;

    private Long idCategoria;
    private Long idClasificacion;
    private Long idSubCategoria;
    private Long idSubClasificacion;
    
    Clasificacion clasificacionSeleccionada;
    SubClasificacion subClasificacionSeleccionada;
    
    //</editor-fold>
    
    
    
    public MantenClasificCommand() {
        
        this.itemsCategoria = new HashMap<Long, Categoria>();
        this.itemsClasificacion = new HashMap<Long, Clasificacion>();
        this.itemsSubCategoria = new HashMap<Long, SubCategoria>();
        this.itemsSubClasificacion = new HashMap<Long, SubClasificacion>();
        
        this.clasificacionSeleccionada = new Clasificacion();
        this.subClasificacionSeleccionada = new SubClasificacion();
    }

    //<editor-fold defaultstate="collapsed" desc="Set & Gets">

    public SubClasificacion getSubClasificacionSeleccionada() {
        return subClasificacionSeleccionada;
    }

    public void setSubClasificacionSeleccionada(SubClasificacion subClasificacionSeleccionada) {
        this.subClasificacionSeleccionada = subClasificacionSeleccionada;
    }

    public Clasificacion getClasificacionSeleccionada() {
        return clasificacionSeleccionada;
    }

    public void setClasificacionSeleccionada(Clasificacion clasificacionSeleccionada) {
        this.clasificacionSeleccionada = clasificacionSeleccionada;
    }

    
    
    public Map<Long, Categoria> getItemsCategoria() {
        return itemsCategoria;
    }

    public void setItemsCategoria(Map<Long, Categoria> itemsCategoria) {
        this.itemsCategoria = itemsCategoria;
    }

    
 
    public Map<Long, Clasificacion> getItemsClasificacion() {
        return itemsClasificacion;
    }

    public void setItemsClasificacion(Map<Long, Clasificacion> itemsClasificacion) {
        this.itemsClasificacion = itemsClasificacion;
    }

    public Map<Long, SubCategoria> getItemsSubCategoria() {
        return itemsSubCategoria;
    }

    public void setItemsSubCategoria(Map<Long, SubCategoria> itemsSubCategoria) {
        this.itemsSubCategoria = itemsSubCategoria;
    }

    public Map<Long, SubClasificacion> getItemsSubClasificacion() {
        return itemsSubClasificacion;
    }

    public void setItemsSubClasificacion(Map<Long, SubClasificacion> itemsSubClasificacion) {
        this.itemsSubClasificacion = itemsSubClasificacion;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Long getIdClasificacion() {
        return idClasificacion;
    }

    public void setIdClasificacion(Long idClasificacion) {
        this.idClasificacion = idClasificacion;
    }

    public Long getIdSubCategoria() {
        return idSubCategoria;
    }

    public void setIdSubCategoria(Long idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
    }

    public Long getIdSubClasificacion() {
        return idSubClasificacion;
    }

    public void setIdSubClasificacion(Long idSubClasificacion) {
        this.idSubClasificacion = idSubClasificacion;
    }

    
    //</editor-fold>
    
}
