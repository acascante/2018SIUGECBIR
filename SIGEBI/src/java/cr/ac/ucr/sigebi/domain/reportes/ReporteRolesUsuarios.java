/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain.reportes;

import cr.ac.ucr.sigebi.domain.ViewAutorizacionRolUsuarioUnidad;
import java.io.Serializable;

/**
 *
 * @author alvaro.cascante
 */
public class ReporteRolesUsuarios implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private String rol;
    private String idUsuario;
    private String nombreUsuario;
    private String autorizacion;
    private String proceso;
    private String unidadEjecutora;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ReporteRolesUsuarios() {
        super();
    }

    public ReporteRolesUsuarios(ViewAutorizacionRolUsuarioUnidad usuarioRol) {
        this.rol = usuarioRol.getAutorizacionRol().getRol().getNombre();
        this.idUsuario = usuarioRol.getId();
        this.autorizacion = usuarioRol.getAutorizacionRol().getAutorizacion().getNombre();
        this.proceso = usuarioRol.getAutorizacionRol().getAutorizacion().getTipoProceso().getNombre();
        this.unidadEjecutora = usuarioRol.getUnidadEjecutora().getDescripcion();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(String unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }    
    //</editor-fold>
}