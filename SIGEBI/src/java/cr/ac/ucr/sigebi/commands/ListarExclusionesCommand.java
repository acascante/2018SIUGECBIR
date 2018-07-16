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
public class ListarExclusionesCommand {
        
    //<editor-fold defaultstate="collapsed" desc="Constantes">
    public static final String KEY_EXCLUSION = "keyExclusion";
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private static String fltId = "";
    private static Long fltEstado = -1L;
    private static Long fltTipo = -1L;
    private static Date fltFecha = null;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ListarExclusionesCommand() { 
        super();
    }
    //</editor-fold>
 
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public String getKey() {
        return KEY_EXCLUSION; 
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
        if (ListarExclusionesCommand.fltId.isEmpty()) {
            return 0l;
        }
        return Long.valueOf(fltId);
    }
        
    public String getFltId() {
        return fltId;
    }

    public void setFltId(String fltId) {
        ListarExclusionesCommand.fltId = fltId;
    }
    
    public Long getFltEstado() {
        return fltEstado;
    }

    public void setFltEstado(Long fltEstado) {
        ListarExclusionesCommand.fltEstado = fltEstado;
    }

    public Long getFltTipo() {
        return fltTipo;
    }

    public void setFltTipo(Long fltTipo) {
        ListarExclusionesCommand.fltTipo = fltTipo;
    }

    public Date getFltFecha() {
        return fltFecha;
    }

    public void setFltFecha(Date fltFecha) {
        ListarExclusionesCommand.fltFecha = fltFecha;
    }
    //</editor-fold>
}