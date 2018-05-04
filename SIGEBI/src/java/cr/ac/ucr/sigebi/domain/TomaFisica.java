/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "TomaFisica")
@Table(name = "SIGEBI_OAF.SIGB_TOMA_FISICA")
@SequenceGenerator(name = "SGB_SQ_TOMA_FISICA", sequenceName = "SIGEBI_OAF.SGB_SQ_TOMA_FISICA", initialValue = 1, allocationSize = 1)
public class TomaFisica extends ObjetoBase implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_TOMA_FISICA")
    @Column(name = "ID_TOMA_FISICA")
    private Long id;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO_MOTIVO", referencedColumnName = "ID_TIPO")
    private Tipo motivo;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO", referencedColumnName = "ID_TIPO")
    private Tipo tipo;

    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne
    private Estado estado;

    @JoinColumn(name = "ID_UBICACION", referencedColumnName = "ID_UBICACION", nullable = true)
    @ManyToOne
    private Ubicacion ubicacion;

    @ManyToOne
    @JoinColumn(name = "ID_UNIDAD_EJECUTORA", referencedColumnName = "ID")
    private UnidadEjecutora unidadEjecutora;

    @Transient
    private List<TomaFisicaUnitaria> tomasUnitarias;

    @Transient
    private List<TomaFisicaLote> tomasLote;

    @Transient
    private List<TomaFisicaSobrante> tomasSobrantes;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Tipo getMotivo() {
        return motivo;
    }

    public void setMotivo(Tipo motivo) {
        this.motivo = motivo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public UnidadEjecutora getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(UnidadEjecutora unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }

    public List<TomaFisicaUnitaria> getTomasUnitarias() {
        return tomasUnitarias;
    }

    public void setTomasUnitarias(List<TomaFisicaUnitaria> tomasUnitarias) {
        this.tomasUnitarias = tomasUnitarias;
    }

    public List<TomaFisicaLote> getTomasLote() {
        return tomasLote;
    }

    public void setTomasLote(List<TomaFisicaLote> tomasLote) {
        this.tomasLote = tomasLote;
    }

    public List<TomaFisicaSobrante> getTomasSobrantes() {
        return tomasSobrantes;
    }

    public void setTomasSobrantes(List<TomaFisicaSobrante> tomasSobrantes) {
        this.tomasSobrantes = tomasSobrantes;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 37 * hash + (this.descripcion != null ? this.descripcion.hashCode() : 0);
        hash = 37 * hash + (this.motivo != null ? this.motivo.hashCode() : 0);
        hash = 37 * hash + (this.tipo != null ? this.tipo.hashCode() : 0);
        hash = 37 * hash + (this.estado != null ? this.estado.hashCode() : 0);
        hash = 37 * hash + (this.ubicacion != null ? this.ubicacion.hashCode() : 0);
        hash = 37 * hash + (this.tomasUnitarias != null ? this.tomasUnitarias.hashCode() : 0);
        hash = 37 * hash + (this.tomasLote != null ? this.tomasLote.hashCode() : 0);
        hash = 37 * hash + (this.tomasSobrantes != null ? this.tomasSobrantes.hashCode() : 0);
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
        final TomaFisica other = (TomaFisica) obj;
        if ((this.descripcion == null) ? (other.descripcion != null) : !this.descripcion.equals(other.descripcion)) {
            return false;
        }
        if ((this.motivo == null) ? (other.motivo != null) : !this.motivo.equals(other.motivo)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.tipo != other.tipo && (this.tipo == null || !this.tipo.equals(other.tipo))) {
            return false;
        }
        if (this.estado != other.estado && (this.estado == null || !this.estado.equals(other.estado))) {
            return false;
        }
        if (this.ubicacion != other.ubicacion && (this.ubicacion == null || !this.ubicacion.equals(other.ubicacion))) {
            return false;
        }
        if (this.tomasUnitarias != other.tomasUnitarias && (this.tomasUnitarias == null || !this.tomasUnitarias.equals(other.tomasUnitarias))) {
            return false;
        }
        if (this.tomasLote != other.tomasLote && (this.tomasLote == null || !this.tomasLote.equals(other.tomasLote))) {
            return false;
        }
        if (this.tomasSobrantes != other.tomasSobrantes && (this.tomasSobrantes == null || !this.tomasSobrantes.equals(other.tomasSobrantes))) {
            return false;
        }
        return true;
    }

    //</editor-fold>
}
