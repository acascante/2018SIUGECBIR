/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain.reportes;

import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.ViewAutorizacionRolUsuarioUnidad;
import java.io.Serializable;

/**
 *
 * @author alvaro.cascante
 */
public class ReporteUsuarioBienes implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private String idUsuario;
    private String nombreUsuario;
    
    private String identificacion;
    private String descripcion;
    private String estado;
    private String unidadEjecutora;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ReporteUsuarioBienes() {
        super();
    }

    
    public ReporteUsuarioBienes(String idUsuario, String nombreUsuario, String identificacion, String descripcion, String estado, String unidadEjecutora) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.identificacion = identificacion;
        this.descripcion = descripcion;
        this.estado = estado;
        this.unidadEjecutora = unidadEjecutora;
    }
    
    public ReporteUsuarioBienes(Bien bien) {
        this.idUsuario = bien.getUsuarioResponsable().getId();
        this.nombreUsuario = bien.getUsuarioResponsable().getNombreCompleto();
        this.identificacion = bien.getIdentificacion().getIdentificacion();
        this.descripcion = bien.getDescripcion();
        this.estado = bien.getEstado().getNombre();
        this.unidadEjecutora = bien.getUnidadEjecutora().getDescripcion();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(String unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }    
    //</editor-fold>
}