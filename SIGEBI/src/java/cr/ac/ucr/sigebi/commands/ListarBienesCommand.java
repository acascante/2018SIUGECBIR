/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author alvaro.cascante
 */
public class ListarBienesCommand {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private String fltIdBien;
    private String fltDescripcion;
    private String fltMarca;
    private String fltModelo;
    private String fltSerie;
    private Integer fltEstado;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ListarBienesCommand() {}

    public ListarBienesCommand(String fltIdBien, String fltDescripcion, String fltMarca, String fltSerie, Integer fltEstado) {
        this.fltIdBien = fltIdBien;
        this.fltDescripcion = fltDescripcion;
        this.fltMarca = fltMarca;
        this.fltSerie = fltSerie;
        this.fltEstado = fltEstado;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public String getDatePattern() {
        return Constantes.DEFAULT_DATE_PATTERN;
    }
    
    public String getKeyVistaOrigen() {
        return Constantes.KEY_VISTA_ORIGEN; 
    }
    
    public TimeZone getTimeZone() {
        return Constantes.DEFAULT_TIME_ZONE;
    }
    
        
    public Long getFltIdCodigo() {
        if (this.fltIdBien.isEmpty()) {
            return 0L; 
        }
        return Long.valueOf(fltIdBien);
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

    public Integer getFltEstado() {
        return fltEstado;
    }

    public void setFltEstado(Integer fltEstado) {
        this.fltEstado = fltEstado;
    }
    //</editor-fold>
}