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
public class ListarNotificacionesCommand {
        
    //<editor-fold defaultstate="collapsed" desc="Constantes">
    public static final String KEY_NOTIFICACION = "keyNotificacion";
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private static String fltId = "";
    private static String fltAsunto = "";
    private static String fltDestinatario = "";
    private static String fltMensaje = "";
    private static Long fltEstado = -1L;
    private static Date fltFecha = null;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ListarNotificacionesCommand() { 
        super();
    }
    //</editor-fold>
 
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public String getDatePattern() {
        return Constantes.DEFAULT_DATE_PATTERN;
    }
    
    public String getKey() {
        return KEY_NOTIFICACION; 
    }
    
    public String getKeyVistaOrigen() {
        return Constantes.KEY_VISTA_ORIGEN; 
    }
    
    public TimeZone getTimeZone() {
        return Constantes.DEFAULT_TIME_ZONE;
    }
    
    public Long getFltIdCodigo() {
        if (ListarNotificacionesCommand.fltId.isEmpty()) {
            return 0L;
        }
        return Long.valueOf(fltId);
    }
    
    public String getFltId() {
        return fltId;
    }

    public void setFltId(String fltId) {
        ListarNotificacionesCommand.fltId = fltId;
    }

    public String getFltAsunto() {
        return fltAsunto;
    }

    public void setFltAsunto(String fltAsunto) {
        ListarNotificacionesCommand.fltAsunto = fltAsunto;
    }

    public String getFltDestinatario() {
        return fltDestinatario;
    }

    public void setFltDestinatario(String fltDestinatario) {
        ListarNotificacionesCommand.fltDestinatario = fltDestinatario;
    }

    public String getFltMensaje() {
        return fltMensaje;
    }

    public void setFltMensaje(String fltMensaje) {
        ListarNotificacionesCommand.fltMensaje = fltMensaje;
    }

    public Long getFltEstado() {
        return fltEstado;
    }

    public void setFltEstado(Long fltEstado) {
        ListarNotificacionesCommand.fltEstado = fltEstado;
    }

    public Date getFltFecha() {
        return fltFecha;
    }

    public void setFltFecha(Date fltFecha) {
        ListarNotificacionesCommand.fltFecha = fltFecha;
    }
    //</editor-fold>
}