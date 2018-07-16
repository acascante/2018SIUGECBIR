/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.TimeZone;

/**
 *
 * @author alvaro.cascante
 */
public class ListarBienesCommand {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private static String fltIdBien = "";
    private static String fltIdentificacion = "";
    private static String fltDescripcion = "";
    private static String fltMarca = "";
    private static String fltModelo = "";
    private static String fltSerie = "";
    private static Long fltEstado = -1L;
    private static Long fltTipo = -1L;
    private static String fltUnidadEjecutoraAdmin = "";
    private Boolean usuarioAdmnistrador;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ListarBienesCommand() {
        super();
        this.usuarioAdmnistrador = false;
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
    
    public Long getFltIdCodigo() {
        if (ListarBienesCommand.fltIdBien.isEmpty()) {
            return 0L; 
        }
        return Long.valueOf(fltIdBien);
    }
    
    public String getFltIdBien() {
        return fltIdBien;
    }

    public void setFltIdBien(String fltIdBien) {
        ListarBienesCommand.fltIdBien = fltIdBien;
    }

    public String getFltIdentificacion() {
        return fltIdentificacion;
    }

    public void setFltIdentificacion(String fltIdentificacion) {
        ListarBienesCommand.fltIdentificacion = fltIdentificacion;
    }

    public String getFltDescripcion() {
        return fltDescripcion;
    }

    public void setFltDescripcion(String fltDescripcion) {
        ListarBienesCommand.fltDescripcion = fltDescripcion;
    }

    public String getFltMarca() {
        return fltMarca;
    }

    public void setFltMarca(String fltMarca) {
        ListarBienesCommand.fltMarca = fltMarca;
    }

    public String getFltModelo() {
        return fltModelo;
    }

    public void setFltModelo(String fltModelo) {
        ListarBienesCommand.fltModelo = fltModelo;
    }

    public String getFltSerie() {
        return fltSerie;
    }

    public void setFltSerie(String fltSerie) {
        ListarBienesCommand.fltSerie = fltSerie;
    }

    public Long getFltEstado() {
        return fltEstado;
    }

    public void setFltEstado(Long fltEstado) {
        ListarBienesCommand.fltEstado = fltEstado;
    }
    
    public Long getFltTipo() {
        return fltTipo;
    }

    public void setFltTipo(Long fltTipo) {
        ListarBienesCommand.fltTipo = fltTipo;
    }

    public Boolean getUsuarioAdmnistrador() {
        return usuarioAdmnistrador;
    }

    public void setUsuarioAdmnistrador(Boolean usuarioAdmnistrador) {
        this.usuarioAdmnistrador = usuarioAdmnistrador;
    }

    public String getFltUnidadEjecutoraAdmin() {
        return fltUnidadEjecutoraAdmin;
    }

    public void setFltUnidadEjecutoraAdmin(String fltUnidadEjecutoraAdmin) {
        ListarBienesCommand.fltUnidadEjecutoraAdmin = fltUnidadEjecutoraAdmin;
    }
    //</editor-fold>
}