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
    private String fltId;
    private String fltAsunto;
    private String fltDestinatario;
    private String fltMensaje;
    private Integer fltEstado;
    private Date fltFecha;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ListarNotificacionesCommand() { 
        super();
        this.fltId = new String();
        this.fltAsunto = new String();
        this.fltDestinatario = new String();
        this.fltMensaje = new String();
        this.fltEstado = -1;
        this.fltFecha = null;
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
        if (this.fltId.isEmpty()) {
            return 0L;
        }
        return Long.valueOf(fltId);
    }
    
    public String getFltId() {
        return fltId;
    }

    public void setFltId(String fltId) {
        this.fltId = fltId;
    }

    public String getFltAsunto() {
        return fltAsunto;
    }

    public void setFltAsunto(String fltAsunto) {
        this.fltAsunto = fltAsunto;
    }

    public String getFltDestinatario() {
        return fltDestinatario;
    }

    public void setFltDestinatario(String fltDestinatario) {
        this.fltDestinatario = fltDestinatario;
    }

    public String getFltMensaje() {
        return fltMensaje;
    }

    public void setFltMensaje(String fltMensaje) {
        this.fltMensaje = fltMensaje;
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
    //</editor-fold>
}