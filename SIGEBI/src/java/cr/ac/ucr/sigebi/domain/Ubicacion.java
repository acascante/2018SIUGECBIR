/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.sigebi.entities.*;
import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "Ubicacion")
@Table(name = "SIGEBI_OAF.SIGB_UBICACION")
@SequenceGenerator(name = "SGB_SQ_UBICACION", sequenceName = "SIGEBI_OAF.SGB_SQ_UBICACION", initialValue = 1, allocationSize = 1)
public class Ubicacion extends ObjetoBase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_UBICACION")
    @Column(name = "ID_UBICACION")
    private Integer idUbicacion;

    @Column(name = "DETALLE")
    private String detalle;

    @Column(name = "PERTENECE")
    private Integer pertenece;

    @Column(name = "ID_PERSONA")//FIXME VERIFICAR
    private Integer idPersona;

    @ManyToOne
    @JoinColumn(name = "ID_UNIDAD_EJECUTORA", referencedColumnName = "ID")
    private UnidadEjecutora idUnidadEjecutora;

    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private EstadoEntity idEstado;

    public Integer getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Integer idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Integer getPertenece() {
        return pertenece;
    }

    public void setPertenece(Integer pertenece) {
        this.pertenece = pertenece;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public UnidadEjecutora getIdUnidadEjecutora() {
        return idUnidadEjecutora;
    }

    public void setIdUnidadEjecutora(UnidadEjecutora idUnidadEjecutora) {
        this.idUnidadEjecutora = idUnidadEjecutora;
    }

    public EstadoEntity getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(EstadoEntity idEstado) {
        this.idEstado = idEstado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.idUbicacion != null ? this.idUbicacion.hashCode() : 0);
        hash = 59 * hash + (this.detalle != null ? this.detalle.hashCode() : 0);
        hash = 59 * hash + (this.pertenece != null ? this.pertenece.hashCode() : 0);
        hash = 59 * hash + (this.idPersona != null ? this.idPersona.hashCode() : 0);
        hash = 59 * hash + (this.idUnidadEjecutora != null ? this.idUnidadEjecutora.hashCode() : 0);
        hash = 59 * hash + (this.idEstado != null ? this.idEstado.hashCode() : 0);
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
        final Ubicacion other = (Ubicacion) obj;
        if ((this.detalle == null) ? (other.detalle != null) : !this.detalle.equals(other.detalle)) {
            return false;
        }
        if (this.idUbicacion != other.idUbicacion && (this.idUbicacion == null || !this.idUbicacion.equals(other.idUbicacion))) {
            return false;
        }
        if (this.pertenece != other.pertenece && (this.pertenece == null || !this.pertenece.equals(other.pertenece))) {
            return false;
        }
        if (this.idPersona != other.idPersona && (this.idPersona == null || !this.idPersona.equals(other.idPersona))) {
            return false;
        }
        if (this.idUnidadEjecutora != other.idUnidadEjecutora && (this.idUnidadEjecutora == null || !this.idUnidadEjecutora.equals(other.idUnidadEjecutora))) {
            return false;
        }
        if (this.idEstado != other.idEstado && (this.idEstado == null || !this.idEstado.equals(other.idEstado))) {
            return false;
        }
        return true;
    }
}
