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
    private boolean selected;
    private String fltId;
    private String fltCantidad;
    private Integer fltTipo;
    private Integer fltEstado;
    private Date fltFecha;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ListarBienesCommand() { 
        super();
        this.fltId = new String();
        this.fltCantidad = new String();
        this.fltTipo = -1;
        this.fltEstado = -1;
        this.fltFecha = null;
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
    
    public Integer getFltIdCodigo() {
        if (this.fltId.isEmpty()) {
            return 0;
        }
        return Integer.valueOf(fltId);
    }
    
    public String getFltId() {
        return fltId;
    }

    public void setFltId(String fltId) {
        this.fltId = fltId;
    }

    public Integer getFltEstado() {
        return fltEstado;
    }

    public void setFltEstado(Integer fltEstado) {
        this.fltEstado = fltEstado;
    }

    public Date getFltFecha() {
        return fltFecha;
    }

    public void setFltFecha(Date fltFecha) {
        this.fltFecha = fltFecha;
    }
    
    public Integer getCantidad() {
        if (this.fltCantidad.isEmpty()) {
            return 0;
        }
        return Integer.valueOf(fltCantidad);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getFltCantidad() {
        return fltCantidad;
    }

    public void setFltCantidad(String fltCantidad) {
        this.fltCantidad = fltCantidad;
    }

    public Integer getFltTipo() {
        return fltTipo;
    }

    public void setFltTipo(Integer fltTipo) {
        this.fltTipo = fltTipo;
    }
    //</editor-fold>
}