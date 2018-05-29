/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.*;
import java.io.Serializable;

/**
 *
 * @author alvaro.cascante
 */
public class ReporteAsignacionPlacaCommand implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private String identificacion;
    private String estado;
    private String descripcion;
    private String serie;
    private String marca;
    private String modelo;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ReporteAsignacionPlacaCommand() {
        super();
    }

    public ReporteAsignacionPlacaCommand(Bien bien, Estado estadoIdentificacion) {
        this.identificacion = bien.getIdentificacion().getIdentificacion();
        this.descripcion = bien.getDescripcion();
        this.marca =  bien.getResumenBien().getMarca();
        this.modelo = bien.getResumenBien().getModelo();
        this.serie =  bien.getResumenBien().getSerie();
        this.estado = estadoIdentificacion.getNombre();
    }

    public ReporteAsignacionPlacaCommand(Identificacion identificacion, Estado estadoIdentificacion) {
        this.identificacion = identificacion.getIdentificacion();
        this.descripcion = "";
        this.marca =  "";
        this.modelo = "";
        this.serie =  "";
        this.estado = estadoIdentificacion.getNombre();
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
     public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
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
    
    //</editor-fold>  
}