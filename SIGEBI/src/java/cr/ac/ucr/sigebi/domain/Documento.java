/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "Documento")
@Table(name = "SIGEBI_OAF.SIGB_DOCUMENTO")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="discriminator")
@SequenceGenerator(name = "SGB_SQ_DOCUMENTO", sequenceName = "SIGEBI_OAF.SGB_SQ_DOCUMENTO", initialValue = 1, allocationSize = 1)
public class Documento extends ObjetoBase implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_DOCUMENTO")
    @Column(name = "ID_DOCUMENTO")
    private Long id;

    @Column(name = "FECHA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;

    @Column(name = "DISCRIMINATOR")
    private Integer discriminator;

    @ManyToOne
    @JoinColumn(name = "ID_UNIDAD_EJECUTORA", referencedColumnName = "ID")
    private UnidadEjecutora unidadEjecutora;
    
    @Transient
    private List<DocumentoDetalle> detallesDocumento;

    @Transient
    private List<DocumentoAutorizacion> autorizacionesDocumento;

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructores">

    public Documento() {
        this.fecha = new Date();
    }
    
    public Documento(Estado estado
            , Integer discriminator
            , UnidadEjecutora unidadEjecutora
    ) {
        this.estado = estado;
        this.discriminator = discriminator;
        this.fecha = new Date();
        this.unidadEjecutora = unidadEjecutora;
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    
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

    public Integer getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(Integer discriminator) {
        this.discriminator = discriminator;
    }

    public List<DocumentoDetalle> getDetallesDocumento() {
        return detallesDocumento;
    }

    public void setDetallesDocumento(List<DocumentoDetalle> detallesDocumento) {
        this.detallesDocumento = detallesDocumento;
    }

    public UnidadEjecutora getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(UnidadEjecutora unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }

    public List<DocumentoAutorizacion> getAutorizacionesDocumento() {
        return autorizacionesDocumento;
    }

    public void setAutorizacionesDocumento(List<DocumentoAutorizacion> autorizacionesDocumento) {
        this.autorizacionesDocumento = autorizacionesDocumento;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 29 * hash + (this.fecha != null ? this.fecha.hashCode() : 0);
        hash = 29 * hash + (this.estado != null ? this.estado.hashCode() : 0);
        hash = 29 * hash + (this.discriminator != null ? this.discriminator.hashCode() : 0);
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
        final Documento other = (Documento) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.fecha != other.fecha && (this.fecha == null || !this.fecha.equals(other.fecha))) {
            return false;
        }
        if (this.estado != other.estado && (this.estado == null || !this.estado.equals(other.estado))) {
            return false;
        }
        if (this.discriminator != other.discriminator && (this.discriminator == null || !this.discriminator.equals(other.discriminator))) {
            return false;
        }
        return true;
    }
    //</editor-fold>
}
