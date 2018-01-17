/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

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
@Entity(name = "Autorizacion")
@Table(name = "SIGEBI_OAF.SIGB_AUTORIZACION")
@SequenceGenerator(name = "SGB_SQ_AUTORIZACION",  sequenceName = "SIGEBI_OAF.SGB_SQ_AUTORIZACION", initialValue = 1, allocationSize = 1)
public class Autorizacion extends ObjetoBase implements Serializable {       

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_AUTORIZACION")
    @Column(name = "ID_AUTORIZACION")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CODIGO_PROCESO", referencedColumnName = "ID_TIPO")
    private Tipo idTipoProceso;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ORDEN")
    private Integer orden;

    @Column(name = "DETALLE")
    private String detalle;
    
    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado idEstado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tipo getIdTipoProceso() {
        return idTipoProceso;
    }

    public void setIdTipoProceso(Tipo idTipoProceso) {
        this.idTipoProceso = idTipoProceso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 71 * hash + (this.idTipoProceso != null ? this.idTipoProceso.hashCode() : 0);
        hash = 71 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        hash = 71 * hash + (this.orden != null ? this.orden.hashCode() : 0);
        hash = 71 * hash + (this.detalle != null ? this.detalle.hashCode() : 0);
        hash = 71 * hash + (this.idEstado != null ? this.idEstado.hashCode() : 0);
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
        final Autorizacion other = (Autorizacion) obj;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        if ((this.detalle == null) ? (other.detalle != null) : !this.detalle.equals(other.detalle)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.idTipoProceso != other.idTipoProceso && (this.idTipoProceso == null || !this.idTipoProceso.equals(other.idTipoProceso))) {
            return false;
        }
        if (this.orden != other.orden && (this.orden == null || !this.orden.equals(other.orden))) {
            return false;
        }
        if (this.idEstado != other.idEstado && (this.idEstado == null || !this.idEstado.equals(other.idEstado))) {
            return false;
        }
        return true;
    }

}
