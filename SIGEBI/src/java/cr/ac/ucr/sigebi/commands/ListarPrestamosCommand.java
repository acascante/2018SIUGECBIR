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
    private static String fltId = "";
    private static Long fltEstado = -1L;
    private static Long fltTipoEntidad = -1L;
    private static String fltEntidad = "";
    private static Date fltFecha = null;
    private static Long fltUnidadEjecutora = -1L;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ListarPrestamosCommand() { 
        super();
    }
    public ListarPrestamosCommand(Long idUnidadEjecutora) {
        this();
        this.fltUnidadEjecutora = idUnidadEjecutora;
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
        if (ListarPrestamosCommand.fltId.isEmpty()) {
            return 0l;
        }
        return Long.valueOf(fltId);
    }
        
    public String getFltId() {
        return fltId;
    }

    public void setFltId(String fltId) {
        ListarPrestamosCommand.fltId = fltId;
    }
    
    public Long getFltEstado() {
        return fltEstado;
    }

    public void setFltEstado(Long fltEstado) {
        ListarPrestamosCommand.fltEstado = fltEstado;
    }

    public Date getFltFecha() {
        return fltFecha;
    }

    public void setFltFecha(Date fltFecha) {
        ListarPrestamosCommand.fltFecha = fltFecha;
    }
    
    public Long getFltTipoEntidad() {
        return fltTipoEntidad;
    }

    public void setFltTipoEntidad(Long fltTipoEntidad) {
        ListarPrestamosCommand.fltTipoEntidad = fltTipoEntidad;
    }

    public String getFltEntidad() {
        return fltEntidad;
    }

    public void setFltEntidad(String fltEntidad) {
        ListarPrestamosCommand.fltEntidad = fltEntidad;
    }

    public Long getFltUnidadEjecutora() {
        return fltUnidadEjecutora;
    }

    public void setFltUnidadEjecutora(Long fltUnidadEjecutora) {
        ListarPrestamosCommand.fltUnidadEjecutora = fltUnidadEjecutora;
    }
    //</editor-fold>
}