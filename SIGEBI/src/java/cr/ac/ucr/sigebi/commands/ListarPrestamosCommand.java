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
public class ListarPrestamosCommand {
        
    //<editor-fold defaultstate="collapsed" desc="Constantes">
    public static final String KEY_PRESTAMO = "keyPrestamo";
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private String fltId;
    private Integer fltEstado;
    private Integer fltTipoEntidad;
    private String fltEntidad;
    private Date fltFecha;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ListarPrestamosCommand() { 
        super();
        this.fltId = new String();
        this.fltEstado = -1;
        this.fltTipoEntidad = -1;
        this.fltFecha = null;
        this.fltEntidad = new String();
    }
    //</editor-fold>
 
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public String getKey() {
        return KEY_PRESTAMO; 
    }
    
    public String getKeyVistaOrigen() {
        return Constantes.KEY_VISTA_ORIGEN; 
    }
    
    public String getDatePattern() {
        return Constantes.DEFAULT_DATE_PATTERN;
    }
    
    public TimeZone getTimeZone() {
        return Constantes.DEFAULT_TIME_ZONE;
    }
    
    public Long getFltIdCodigo() {
        if (this.fltId.isEmpty()) {
            return 0l;
        }
        return Long.valueOf(fltId);
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
    
    public Integer getFltTipoEntidad() {
        return fltTipoEntidad;
    }

    public void setFltTipoEntidad(Integer fltTipoEntidad) {
        this.fltTipoEntidad = fltTipoEntidad;
    }

    public String getFltEntidad() {
        return fltEntidad;
    }

    public void setFltEntidad(String fltEntidad) {
        this.fltEntidad = fltEntidad;
    }
    //</editor-fold>
}