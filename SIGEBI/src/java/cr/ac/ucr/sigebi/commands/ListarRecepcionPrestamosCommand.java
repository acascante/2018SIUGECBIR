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
public class ListarRecepcionPrestamosCommand {
        
    //<editor-fold defaultstate="collapsed" desc="Constantes">
    public static final String KEY_RECEPCION_PRESTAMO = "keyRecepcionPrestamo";
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private static String fltId = "";
    private static String fltDescripcion = "";
    private static String fltIdentificacion = "";
    private static Long fltEstado = -1L;
    private static Long fltConvenio = -1L;
    private static Date fltFechaIngreso = null;
    private static Date fltFechaDevolucion = null;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ListarRecepcionPrestamosCommand() { 
        super();
    }
    //</editor-fold>
 
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public String getDatePattern() {
        return Constantes.DEFAULT_DATE_PATTERN;
    }
    
    public String getKey() {
        return KEY_RECEPCION_PRESTAMO; 
    }
    
    public String getKeyVistaOrigen() {
        return Constantes.KEY_VISTA_ORIGEN; 
    }
    
    public TimeZone getTimeZone() {
        return Constantes.DEFAULT_TIME_ZONE;
    }
    
    public Long getFltIdCodigo() {
        if (ListarRecepcionPrestamosCommand.fltId.isEmpty()) {
            return 0L;
        }
        return Long.valueOf(fltId);
    }
    
    public String getFltId() {
        return fltId;
    }

    public void setFltId(String fltId) {
        ListarRecepcionPrestamosCommand.fltId = fltId;
    }

    public String getFltDescripcion() {
        return fltDescripcion;
    }

    public void setFltDescripcion(String fltDescripcion) {
        ListarRecepcionPrestamosCommand.fltDescripcion = fltDescripcion;
    }

    public String getFltIdentificacion() {
        return fltIdentificacion;
    }

    public void setFltIdentificacion(String fltIdentificacion) {
        ListarRecepcionPrestamosCommand.fltIdentificacion = fltIdentificacion;
    }

    public Long getFltEstado() {
        return fltEstado;
    }

    public void setFltEstado(Long fltEstado) {
        ListarRecepcionPrestamosCommand.fltEstado = fltEstado;
    }

    public Long getFltConvenio() {
        return fltConvenio;
    }

    public void setFltConvenio(Long fltConvenio) {
        ListarRecepcionPrestamosCommand.fltConvenio = fltConvenio;
    }

    public Date getFltFechaIngreso() {
        return fltFechaIngreso;
    }

    public void setFltFechaIngreso(Date fltFechaIngreso) {
        ListarRecepcionPrestamosCommand.fltFechaIngreso = fltFechaIngreso;
    }

    public Date getFltFechaDevolucion() {
        return fltFechaDevolucion;
    }

    public void setFltFechaDevolucion(Date fltFechaDevolucion) {
        ListarRecepcionPrestamosCommand.fltFechaDevolucion = fltFechaDevolucion;
    }
    //</editor-fold>
}