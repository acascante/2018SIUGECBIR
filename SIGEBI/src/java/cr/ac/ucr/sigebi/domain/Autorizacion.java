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

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_AUTORIZACION")
    @Column(name = "ID_AUTORIZACION")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CODIGO_PROCESO", referencedColumnName = "ID_TIPO")
    private Tipo tipoProceso;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ORDEN")
    private Integer orden;

    @Column(name = "DETALLE")
    private String detalle;
    
    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tipo getTipoProceso() {
        return tipoProceso;
    }

    public void setTipoProceso(Tipo tipoProceso) {
        this.tipoProceso = tipoProceso;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 71 * hash + (this.tipoProceso != null ? this.tipoProceso.hashCode() : 0);
        hash = 71 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        hash = 71 * hash + (this.orden != null ? this.orden.hashCode() : 0);
        hash = 71 * hash + (this.detalle != null ? this.detalle.hashCode() : 0);
        hash = 71 * hash + (this.estado != null ? this.estado.hashCode() : 0);
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
        if (this.tipoProceso != other.tipoProceso && (this.tipoProceso == null || !this.tipoProceso.equals(other.tipoProceso))) {
            return false;
        }
        if (this.orden != other.orden && (this.orden == null || !this.orden.equals(other.orden))) {
            return false;
        }
        if (this.estado != other.estado && (this.estado == null || !this.estado.equals(other.estado))) {
            return false;
        }
        return true;
    }
    //</editor-fold>
}