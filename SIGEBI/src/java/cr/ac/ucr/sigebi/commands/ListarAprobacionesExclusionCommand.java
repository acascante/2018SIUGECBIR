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
public class ListarAprobacionesExclusionCommand {
        
    //<editor-fold defaultstate="collapsed" desc="Constantes">
    public static final String KEY_DOCUMENTO = "keyDocumento";
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private static String fltId = "";
    private static String fltAutorizacion = "";
    private static Long fltEstado = -1L;
    private static Date fltFecha = null;
    private static Long fltUnidadEjecutora = -1L;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ListarAprobacionesExclusionCommand() { 
        super();
    }
    public ListarAprobacionesExclusionCommand(Long idUnidadEjecutora) {
        this();
        this.fltUnidadEjecutora = idUnidadEjecutora;
    }
    //</editor-fold>
 
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public String getKey() {
        return KEY_DOCUMENTO; 
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
        if (ListarAprobacionesExclusionCommand.fltId.isEmpty()) {
            return 0l;
        }
        return Long.valueOf(fltId);
    }
        
    public String getFltId() {
        return fltId;
    }

    public void setFltId(String fltId) {
        ListarAprobacionesExclusionCommand.fltId = fltId;
    }
    
    public Long getFltEstado() {
        return fltEstado;
    }

    public void setFltEstado(Long fltEstado) {
        ListarAprobacionesExclusionCommand.fltEstado = fltEstado;
    }

    public Date getFltFecha() {
        return fltFecha;
    }

    public void setFltFecha(Date fltFecha) {
        ListarAprobacionesExclusionCommand.fltFecha = fltFecha;
    }
    
    public Long getFltUnidadEjecutora() {
        return fltUnidadEjecutora;
    }

    public void setFltUnidadEjecutora(Long fltUnidadEjecutora) {
        ListarAprobacionesExclusionCommand.fltUnidadEjecutora = fltUnidadEjecutora;
    }
    
    public String getFltAutorizacion() {
        return fltAutorizacion;
    }

    public void setFltAutorizacion(String fltAutorizacion) {
        ListarAprobacionesExclusionCommand.fltAutorizacion = fltAutorizacion;
    }
    //</editor-fold>
}