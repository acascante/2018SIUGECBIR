/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author alvaro.cascante
 */
@Entity(name = "SolicitudPrestamo")
@Table(name = "SIGEBI_OAF.SIGB_SOLICITUD_PRESTAMO")
@PrimaryKeyJoinColumn(name = "ID_SOLICITUD", referencedColumnName = "ID_SOLICITUD")
@DiscriminatorValue("4")
public class SolicitudPrestamo extends Solicitud {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Column(name = "OBSERVACION")
    private String observacion;
    
    @Column(name = "ENTIDAD")
    private String entidad;    
    
    @JoinColumn(name = "ID_TIPO_ENTIDAD", referencedColumnName = "ID_TIPO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Tipo tipo;
    
    @Transient
    private List<SolicitudDetallePrestamo> detalles;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
    
    public List<SolicitudDetallePrestamo> getDetallesPrestamo() {
        return detalles;
    }

    public void setDetallesPrestamo(List<SolicitudDetallePrestamo> detalles) {
        this.detalles = detalles;
    }
    //</editor-fold>
}