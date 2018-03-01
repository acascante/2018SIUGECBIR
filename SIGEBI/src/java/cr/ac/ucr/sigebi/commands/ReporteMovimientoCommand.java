/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import java.util.TimeZone;

/**
 *
 * @author jorge.serrano
 */
public class ReporteMovimientoCommand {
    
    //<editor-fold defaultstate="collapsed" desc="Constantes">
    
    public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getDefault();
    
    //</editor-fold>
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    private String identificacion;
    private String descripcion;
    
    //</editor-fold>

    public ReporteMovimientoCommand() {
    }
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">    
    
    
    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    //</editor-fold>

    
    
    

}
