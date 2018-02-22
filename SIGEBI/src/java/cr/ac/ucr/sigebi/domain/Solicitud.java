/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author alvaro.cascante
 */
@Entity(name = "Solicitud")
@Table(name = "SIGEBI_OAF.SIGB_SOLICITUD")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "discriminator")
@SequenceGenerator(name = "SGB_SQ_SOLICITUDES", sequenceName = "SIGEBI_OAF.SGB_SQ_SOLICITUDES", initialValue = 1, allocationSize = 1)
public abstract class Solicitud extends ObjetoBase implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_SOLICITUDES")
    @Column(name = "ID_SOLICITUD")
    private Long id;

    @Column(name = "FECHA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;

    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Estado estado;

    @JoinColumn(name = "ID_UNIDAD_EJECUTORA", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private UnidadEjecutora unidadEjecutora;

    @Column(name = "DISCRIMINATOR")
    private Integer discriminator;

    @Transient
    private List<SolicitudDetalle> detalles;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public UnidadEjecutora getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(UnidadEjecutora unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }

    public Integer getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(Integer discriminator) {
        this.discriminator = discriminator;
    }

    public List<SolicitudDetalle> getDetalles() {
        if (this.detalles == null) {
            this.detalles = new ArrayList<SolicitudDetalle>();
        }
        return detalles;
    }

    public void setDetalles(List<SolicitudDetalle> detalles) {
        this.detalles = detalles;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 37 * hash + (this.fecha != null ? this.fecha.hashCode() : 0);
        hash = 37 * hash + (this.estado != null ? this.estado.hashCode() : 0);
        hash = 37 * hash + (this.unidadEjecutora != null ? this.unidadEjecutora.hashCode() : 0);
        hash = 37 * hash + (this.discriminator != null ? this.discriminator.hashCode() : 0);
        hash = 37 * hash + (this.detalles != null ? this.detalles.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Solicitud other = (Solicitud) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.fecha != other.fecha && (this.fecha == null || !this.fecha.equals(other.fecha))) {
            return false;
        }
        if (this.estado != other.estado && (this.estado == null || !this.estado.equals(other.estado))) {
            return false;
        }
        if (this.unidadEjecutora != other.unidadEjecutora && (this.unidadEjecutora == null || !this.unidadEjecutora.equals(other.unidadEjecutora))) {
            return false;
        }
        if (this.discriminator != other.discriminator && (this.discriminator == null || !this.discriminator.equals(other.discriminator))) {
            return false;
        }
        if (this.detalles != other.detalles && (this.detalles == null || !this.detalles.equals(other.detalles))) {
            return false;
        }
        return true;
    }

    //</editor-fold>
}
