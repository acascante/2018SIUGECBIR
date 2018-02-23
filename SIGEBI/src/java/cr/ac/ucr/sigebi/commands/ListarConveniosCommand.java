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
    private String fltId;
    private String fltInstitucion;
    private String fltResponsable;
    private String fltOficio;
    private Integer fltEstado;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ListarConveniosCommand() { 
        super();
        this.fltId = new String();
        this.fltInstitucion = new String();
        this.fltResponsable = new String();
        this.fltOficio = new String();
        this.fltEstado = -1;
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

    public String getFltInstitucion() {
        return fltInstitucion;
    }

    public void setFltInstitucion(String fltInstitucion) {
        this.fltInstitucion = fltInstitucion;
    }

    public String getFltResponsable() {
        return fltResponsable;
    }

    public void setFltResponsable(String fltResponsable) {
        this.fltResponsable = fltResponsable;
    }

    public String getFltOficio() {
        return fltOficio;
    }

    public void setFltOficio(String fltOficio) {
        this.fltOficio = fltOficio;
    }

    public Integer getFltEstado() {
        return fltEstado;
    }

    public void setFltEstado(Integer fltEstado) {
        this.fltEstado = fltEstado;
    }
    //</editor-fold>
}