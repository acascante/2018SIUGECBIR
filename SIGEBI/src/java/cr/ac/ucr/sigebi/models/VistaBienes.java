/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;

/**
 *
 * @author jorge.serrano
 */
public class VistaBienes extends ObjetoBase implements Serializable  {
    
    private static final long serialVersionUID = 1L;
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    Integer idBien;
    String placa;
    String descripcion;
    
    String marca;
    String modelo;
    String serie;
    
    String estado;
    Integer idEstado;
    Integer numRow;
    //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">

    public VistaBienes() {
    }
    
    
    public VistaBienes(Integer idBien
            , String placa
            , String descripcion
            
            , String marca
            , String modelo
            , String serie
            , String estado
            
            , Integer idEstado
            , Integer numRow) {
        this.idBien = idBien;
        this.placa = placa;
        this.descripcion = descripcion;
        this.marca = marca;
        this.modelo = modelo;
        this.serie = serie;
        this.estado = estado;
        this.idEstado = idEstado;
        this.numRow = numRow;
    }

    
    
    
    
    
    //</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="SET's & GET's">
    public Integer getIdBien() {
        return idBien;
    }

    public void setIdBien(Integer idBien) {
        this.idBien = idBien;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public Integer getNumRow() {
        return numRow;
    }

    public void setNumRow(Integer numRow) {
        this.numRow = numRow;
    }
    
    //</editor-fold>
    
    
    
}
