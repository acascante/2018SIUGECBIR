/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.Identificacion;
import cr.ac.ucr.sigebi.domain.Preliminar;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import java.util.List;

/**
 *
 * @author jorge.serrano
 */
public class PreliminarCommand {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    private List<Preliminar> preliminares;
    private UnidadEjecutora unidadEjecutora;
    private String unidadString;
    
    private Preliminar preliminar;
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    
    public PreliminarCommand(){
        preliminar = new Preliminar();
    }
    
    
    //</editor-fold>
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    
    public Preliminar getPreliminar(){
        
        return preliminar;
    }

    public void setPreliminar(Preliminar preliminar) {
        this.preliminar = preliminar;
    }
    
    
    private void validarPreliminar(){
        
    }
    
    
    //</editor-fold>

    
    
    //<editor-fold defaultstate="collapsed" desc="Set's & Get's">
    

    public List<Preliminar> getPreliminares() {
        return preliminares;
    }

    public void setPreliminares(List<Preliminar> preliminares) {
        this.preliminares = preliminares;
    }

    public UnidadEjecutora getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(UnidadEjecutora unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }

    public String getUnidadString() {
        
        if( unidadString != null && unidadString.length() > 0)
            return unidadString;
        
        if(preliminar != null && preliminar.getUnidad() != null){
            unidadString = preliminar.getUnidad().getId()+ "-" + preliminar.getUnidad().getDescripcion();
        }
        else{
            unidadString = "";
        }
        return unidadString;
    }

    public void setUnidadString(String unidadString) {
        this.unidadString = unidadString;
    }
    
    //</editor-fold>

    
    
}
