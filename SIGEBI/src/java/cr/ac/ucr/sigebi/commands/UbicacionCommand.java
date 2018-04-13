/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.Ubicacion;
import cr.ac.ucr.sigebi.utils.TreeSIGEBI;

/**
 *
 * @author jairo.cisneros
 */
public class UbicacionCommand {

    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    private Ubicacion ubicacion;
    private Ubicacion ubicacionPadre;
    private String fltDescripcionUbicacion;
    private TreeSIGEBI treeSIGEBI;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public UbicacionCommand() {
        this.ubicacion = new Ubicacion();
        this.fltDescripcionUbicacion = "";
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get and Set">
    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFltDescripcionUbicacion() {
        return fltDescripcionUbicacion;
    }

    public void setFltDescripcionUbicacion(String fltDescripcionUbicacion) {
        this.fltDescripcionUbicacion = fltDescripcionUbicacion;
    }

    public Ubicacion getUbicacionPadre() {
        return ubicacionPadre;
    }

    public void setUbicacionPadre(Ubicacion ubicacionPadre) {
        this.ubicacionPadre = ubicacionPadre;
    }
    
    public TreeSIGEBI getTreeSIGEBI() {
        return treeSIGEBI;
    }

    public void setTreeSIGEBI(TreeSIGEBI treeSIGEBI) {
        this.treeSIGEBI = treeSIGEBI;
    }
    
    //</editor-fold>

    
}
