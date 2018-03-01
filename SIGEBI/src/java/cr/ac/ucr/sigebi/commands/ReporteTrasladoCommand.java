/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import javax.faces.model.SelectItem;

/**
 *
 * @author jorge.serrano
 */
public class ReporteTrasladoCommand {
    
    //<editor-fold defaultstate="collapsed" desc="Constantes">
    
    public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getDefault();
    
    //</editor-fold>
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    private Date fechaInicio;
    private Date fechaFin;
    
    private String identificacion;
    private String descripcion;
    private String marca;
    private String modelo;
    private String serie;
    
    private String unidadOrigen;
    private String unidadDestino;
    private String estado;
    
    
    private Map<Integer, SelectItem> itemsColumnas;
    
    //</editor-fold>

    public ReporteTrasladoCommand() {
        fechaInicio = getDefaultDate();
        fechaFin = getDefaultDate();
        
        this.itemsColumnas = new HashMap<Integer, SelectItem>();
        
        itemsColumnas.put(1,new SelectItem("1","Identificación"));
        itemsColumnas.put(2,new SelectItem("2","Descripción"));
        
        itemsColumnas.put(3,new SelectItem("3", "Marca"));
        itemsColumnas.put(4,new SelectItem("4", "Modelo"));
        itemsColumnas.put(5,new SelectItem("5","Serie"));
        
        itemsColumnas.put(6,new SelectItem("6","Unidad Origen"));
        itemsColumnas.put(7,new SelectItem("7","Unidad Destino"));
        itemsColumnas.put(8,new SelectItem("8","Estado"));
        
    }
    
    
    
    public Map<Integer, SelectItem> getItemsColumnas() {
        return itemsColumnas;
    }

    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public void setItemsColumnas(Map<Integer, SelectItem> itemsColumnas) {
        this.itemsColumnas = itemsColumnas;
    }

    private Date getDefaultDate() {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance(Constantes.DEFAULT_TIME_ZONE);
        calendar.setTime(today);
        return calendar.getTime();
    }
    
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getUnidadOrigen() {
        return unidadOrigen;
    }

    public void setUnidadOrigen(String unidadOrigen) {
        this.unidadOrigen = unidadOrigen;
    }

    public String getUnidadDestino() {
        return unidadDestino;
    }

    public void setUnidadDestino(String unidadDestino) {
        this.unidadDestino = unidadDestino;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
    
    
    
    
    //</editor-fold>


}
