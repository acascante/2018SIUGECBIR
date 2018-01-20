
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.entities;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import cr.ac.ucr.sigebi.domain.Bien;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author alvaro.cascante
 */
@Entity(name = "ExclusionDetalleEntity")
@Table(name = "SIGEBI_OAF.SGB_EXCLUSION_DETALLE")
@SequenceGenerator(name="sqExclusionDetalles", sequenceName = "SIGEBI_OAF.SGB_SQ_EXCLUSION_DETALLES", initialValue=1, allocationSize=1)
public class ExclusionDetalleEntity extends ObjetoBase implements Serializable {

    private static final long serialVersionUID = -5306414890561536739L;
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sqExclusionDetalles")
    @Column(name = "ID_EXCLUSION_DETALLE")
    private Integer idExclusionDetalle;
    
    @JoinColumn(name = "ID_EXCLUSION", referencedColumnName = "ID_EXCLUSION")
    @ManyToOne(fetch = FetchType.EAGER)
    private ExclusionEntity exclusion;
    
    @JoinColumn(name = "ID_BIEN", referencedColumnName = "ID_BIEN")
    @ManyToOne(fetch = FetchType.EAGER)
    private Bien bien;
    
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(fetch = FetchType.EAGER)
    private EstadoEntity estado;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ExclusionDetalleEntity() {}

    public ExclusionDetalleEntity(Integer idExclusionDetalle, ExclusionEntity exclusion, Bien bien, EstadoEntity estado) {
        this.idExclusionDetalle = idExclusionDetalle;
        this.exclusion = exclusion;
        this.bien = bien;
        this.estado = estado;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">

    public Integer getIdExclusionDetalle() {
        return idExclusionDetalle;
    }

    public void setIdExclusionDetalle(Integer idExclusionDetalle) {
        this.idExclusionDetalle = idExclusionDetalle;
    }

    public ExclusionEntity getExclusion() {
        return exclusion;
    }

    public void setExclusion(ExclusionEntity exclusion) {
        this.exclusion = exclusion;
    }

    public Bien getBien() {
        return bien;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
    }

    public EstadoEntity getEstado() {
        return estado;
    }

    public void setEstado(EstadoEntity estado) {
        this.estado = estado;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idExclusionDetalle != null ? idExclusionDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ExclusionDetalleEntity)) {
            return false;
        }
        return this.idExclusionDetalle.equals(((ExclusionDetalleEntity)object).idExclusionDetalle);
    }

    @Override
    public String toString() {
        return "entidades.ExclusionDetalleEntity[id=" + this.idExclusionDetalle + "]";
    }
    //</editor-fold>
}