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
 * @author oscar_acuna
 */
public class ListarAyudaCommand {
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private static String fltIdAyuda = "";
    private static String fltRegla = "";
    private static String fltTitulo = "";
    private static String fltCuerpo = "";

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ListarAyudaCommand() {
        super();
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

    /**
     * @return the fltIdAyuda
     */
    public String getFltIdAyuda() {
        return fltIdAyuda;
    }

    /**
     * @param aFltIdAyuda the fltIdAyuda to set
     */
    public void setFltIdAyuda(String aFltIdAyuda) {
        fltIdAyuda = aFltIdAyuda;
    }

    /**
     * @return the fltRegla
     */
    public String getFltRegla() {
        return fltRegla;
    }

    /**
     * @param aFltRegla the fltRegla to set
     */
    public void setFltRegla(String aFltRegla) {
        fltRegla = aFltRegla;
    }

    /**
     * @return the fltTitulo
     */
    public String getFltTitulo() {
        return fltTitulo;
    }

    /**
     * @param aFltTitulo the fltTitulo to set
     */
    public void setFltTitulo(String aFltTitulo) {
        fltTitulo = aFltTitulo;
    }

    /**
     * @return the fltCuerpo
     */
    public String getFltCuerpo() {
        return fltCuerpo;
    }

    /**
     * @param aFltCuerpo the fltCuerpo to set
     */
    public void setFltCuerpo(String aFltCuerpo) {
        fltCuerpo = aFltCuerpo;
    }
    
    
}
