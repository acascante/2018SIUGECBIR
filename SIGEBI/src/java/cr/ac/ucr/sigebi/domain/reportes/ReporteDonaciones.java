/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain.reportes;

import cr.ac.ucr.sigebi.domain.SolicitudDetalle;
import cr.ac.ucr.sigebi.domain.SolicitudDonacion;
import java.io.Serializable;

/**
 *
 * @author alvaro.cascante
 */
public class ReporteDonaciones implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private Long idSolicitud;
    private String donante;
    private String identificacion;
    private String descripcion;
    private String estado;
    private String unidadEjecutora;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ReporteDonaciones() {
        super();
    }
    
    public ReporteDonaciones(SolicitudDetalle detalle, SolicitudDonacion solicitud) {
        this.idSolicitud = solicitud.getId();
        this.donante = solicitud.getDonante();
        this.identificacion = detalle.getBien().getIdentificacion()!= null ? detalle.getBien().getIdentificacion().getIdentificacion() : null;
        this.descripcion = detalle.getBien().getDescripcion();
        this.estado = detalle.getBien().getEstado().getNombre();
        this.unidadEjecutora = detalle.getBien().getUnidadEjecutora().getDescripcion();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    public Long getIdSolicitud() {    
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getDonante() {
        return donante;
    }

    public void setDonante(String donante) {
        this.donante = donante;
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