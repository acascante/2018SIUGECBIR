/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.utils.Constantes;

/**
 *
 * @author alvaro.cascante
 */
public class ListarConveniosCommand {
        
    //<editor-fold defaultstate="collapsed" desc="Constantes">
    public static final String KEY_CONVENIO = "keyConvenio";
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private static String fltId = "";
    private static String fltInstitucion = "";
    private static String fltResponsable = "";
    private static String fltOficio = "";
    private static Long fltEstado = -1L;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ListarConveniosCommand() { 
        super();
    }
    //</editor-fold>
 
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public String getKey() {
        return KEY_CONVENIO; 
    }
    
    public String getKeyVistaOrigen() {
        return Constantes.KEY_VISTA_ORIGEN; 
    }
    
    public Long getFltIdCodigo() {
        if (ListarConveniosCommand.fltId.isEmpty()) {
            return 0L;
        }
        return Long.valueOf(fltId);
    }    
    
    public String getFltId() {
        return fltId;
    }

    public void setFltId(String fltId) {
        ListarConveniosCommand.fltId = fltId;
    }

    public String getFltInstitucion() {
        return fltInstitucion;
    }

    public void setFltInstitucion(String fltInstitucion) {
        ListarConveniosCommand.fltInstitucion = fltInstitucion;
    }

    public String getFltResponsable() {
        return fltResponsable;
    }

    public void setFltResponsable(String fltResponsable) {
        ListarConveniosCommand.fltResponsable = fltResponsable;
    }

    public String getFltOficio() {
        return fltOficio;
    }

    public void setFltOficio(String fltOficio) {
        ListarConveniosCommand.fltOficio = fltOficio;
    }

    public Long getFltEstado() {
        return fltEstado;
    }

    public void setFltEstado(Long fltEstado) {
        ListarConveniosCommand.fltEstado = fltEstado;
    }
    //</editor-fold>
}