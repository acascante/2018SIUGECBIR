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
@Entity(name = "Ubicacion")
@Table(name = "SIGEBI_OAF.SIGB_UBICACION")
@SequenceGenerator(name = "SGB_SQ_UBICACION", sequenceName = "SIGEBI_OAF.SGB_SQ_UBICACION", initialValue = 1, allocationSize = 1)
public class Ubicacion extends ObjetoBase implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_UBICACION")
    @Column(name = "ID_UBICACION")
    private Long id;

    @Column(name = "DETALLE")
    private String detalle;

    @ManyToOne
    @JoinColumn(name = "PERTENECE", referencedColumnName = "ID_UBICACION")
    private Ubicacion pertenece;

    @ManyToOne
    @JoinColumn(name = "RESPONSABLE", referencedColumnName = "ID_USUARIO")
    private Usuario responsable;

    @ManyToOne
    @JoinColumn(name = "ID_UNIDAD_EJECUTORA", referencedColumnName = "ID")
    private UnidadEjecutora unidadEjecutora;

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

    public Usuario getResponsable() {
        return responsable;
    }

    public void setResponsable(Usuario responsable) {
        this.responsable = responsable;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Ubicacion getPertenece() {
        return pertenece;
    }

    public void setPertenece(Ubicacion pertenece) {
        this.pertenece = pertenece;
    }

    public String getDetallePertenece(Ubicacion ubicacion) {        
        if(ubicacion.getPertenece() != null){
            return ubicacion.getPertenece().getDetallePertenece(ubicacion.getPertenece()) + " --> " + ubicacion.getDetalle();
        }else{
            return ubicacion.getDetalle();
        }
    }

    public String getDescripcionCompleta() {                
        return this.getDetallePertenece(this);
    }
    
    public UnidadEjecutora getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(UnidadEjecutora unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
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
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 59 * hash + (this.detalle != null ? this.detalle.hashCode() : 0);
        hash = 59 * hash + (this.pertenece != null ? this.pertenece.hashCode() : 0);
        hash = 59 * hash + (this.unidadEjecutora != null ? this.unidadEjecutora.hashCode() : 0);
        hash = 59 * hash + (this.estado != null ? this.estado.hashCode() : 0);
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
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.pertenece != other.pertenece && (this.pertenece == null || !this.pertenece.equals(other.pertenece))) {
            return false;
        }
        if (this.unidadEjecutora != other.unidadEjecutora && (this.unidadEjecutora == null || !this.unidadEjecutora.equals(other.unidadEjecutora))) {
            return false;
        }
        if (this.estado != other.estado && (this.estado == null || !this.estado.equals(other.estado))) {
            return false;
        }
        return true;
    }
    //</editor-fold>
}