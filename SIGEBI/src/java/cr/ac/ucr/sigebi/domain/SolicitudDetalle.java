/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
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

/**
 *
 * @author alvaro.cascante
 */
@Entity(name = "SolicitudDetalle")
@Table(name = "SIGEBI_OAF.SIGB_SOLICITUD_DETALLE")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "discriminator")
@SequenceGenerator(name = "SGB_SQ_SOLICITUDES_DETA", sequenceName = "SIGEBI_OAF.SGB_SQ_SOLICITUDES_DETA", initialValue = 1, allocationSize = 1)
public class SolicitudDetalle extends ObjetoBase implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_SOLICITUDES_DETA")
    @Column(name = "ID_SOLICITUD_DETALLE")
    private Long id;

    @JoinColumn(name = "ID_SOLICITUD", referencedColumnName = "ID_SOLICITUD")
    @ManyToOne(fetch = FetchType.EAGER)
    private Solicitud solicitud;

    @JoinColumn(name = "ID_BIEN", referencedColumnName = "ID_BIEN")
    @ManyToOne(fetch = FetchType.EAGER)
    private Bien bien;

    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne
    private Estado estado;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public SolicitudDetalle() {
    }

    public SolicitudDetalle(Solicitud solicitud, Bien bien, Estado estado) {
        this.solicitud = solicitud;
        this.bien = bien;
        this.estado = estado;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public Bien getBien() {
        return bien;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 59 * hash + (this.solicitud != null ? this.solicitud.hashCode() : 0);
        hash = 59 * hash + (this.bien != null ? this.bien.hashCode() : 0);
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
        final SolicitudDetalle other = (SolicitudDetalle) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.solicitud != other.solicitud && (this.solicitud == null || !this.solicitud.equals(other.solicitud))) {
            return false;
        }
        if (this.bien != other.bien && (this.bien == null || !this.bien.equals(other.bien))) {
            return false;
        }
        return true;
    }

    //</editor-fold>
}
