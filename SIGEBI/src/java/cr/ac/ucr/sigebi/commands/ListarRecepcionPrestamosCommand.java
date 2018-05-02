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
    private String fltId;
    private String fltDescripcion;
    private String fltIdentificacion;
    private Long fltEstado;
    private Long fltConvenio;
    private Date fltFechaIngreso;
    private Date fltFechaDevolucion;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ListarRecepcionPrestamosCommand() { 
        super();
        this.fltId = new String();
        this.fltDescripcion = new String();
        this.fltIdentificacion = new String();
        this.fltEstado = -1L;
        this.fltConvenio = -1L;
        this.fltFechaIngreso = null;
        this.fltFechaDevolucion = null;
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

    public String getFltDescripcion() {
        return fltDescripcion;
    }

    public void setFltDescripcion(String fltDescripcion) {
        this.fltDescripcion = fltDescripcion;
    }

    public String getFltIdentificacion() {
        return fltIdentificacion;
    }

    public void setFltIdentificacion(String fltIdentificacion) {
        this.fltIdentificacion = fltIdentificacion;
    }

    public Long getFltEstado() {
        return fltEstado;
    }

    public void setFltEstado(Long fltEstado) {
        this.fltEstado = fltEstado;
    }

    public Long getFltConvenio() {
        return fltConvenio;
    }

    public void setFltConvenio(Long fltConvenio) {
        this.fltConvenio = fltConvenio;
    }

    public Date getFltFechaIngreso() {
        return fltFechaIngreso;
    }

    public void setFltFechaIngreso(Date fltFechaIngreso) {
        this.fltFechaIngreso = fltFechaIngreso;
    }

    public Date getFltFechaDevolucion() {
        return fltFechaDevolucion;
    }

    public void setFltFechaDevolucion(Date fltFechaDevolucion) {
        this.fltFechaDevolucion = fltFechaDevolucion;
    }
    //</editor-fold>
}