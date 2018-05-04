/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.Ubicacion;
import cr.ac.ucr.sigebi.domain.Usuario;
import cr.ac.ucr.sigebi.utils.TreeSIGEBI;
import java.util.List;
import javax.faces.model.SelectItem;

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
    private List<SelectItem> responsablesOptions;
    private Usuario usuarioResponsable;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public UbicacionCommand() {
        this.ubicacion = new Ubicacion();
        this.usuarioResponsable = new Usuario();        
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

    public Usuario getUsuarioResponsable() {
        return usuarioResponsable;
    }

    public void setUsuarioResponsable(Usuario usuarioResponsable) {
        this.usuarioResponsable = usuarioResponsable;
    }

    public List<SelectItem> getResponsablesOptions() {
        return responsablesOptions;
    }

    public void setResponsablesOptions(List<SelectItem> responsablesOptions) {
        this.responsablesOptions = responsablesOptions;
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
