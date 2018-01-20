/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.entities;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author alvaro.cascante
 */
@Entity(name = "ExclusionEntity")
@Table(name = "SIGEBI_OAF.SGB_EXCLUSION")
@SequenceGenerator(name="sqExclusiones", sequenceName = "SIGEBI_OAF.SGB_SQ_EXCLUSION", initialValue=1, allocationSize=1)
public class ExclusionEntity extends ObjetoBase implements Serializable {

    private static final long serialVersionUID = 4390042407425103650L;
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sqExclusiones")
    @Column(name = "ID_EXCLUSION")
    private Integer idExclusion;
    
    @Column(name = "NUM_UNIDAD_EJEC")
    private Long unidadEjecutora;
    
    @Column(name = "FECHA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;

    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(fetch = FetchType.EAGER)
    private EstadoEntity estado;
    
    @JoinColumn(name = "ID_TIPO", referencedColumnName = "ID_TIPO")
    @ManyToOne(fetch = FetchType.EAGER)
    private TipoEntity tipo;
    
    @Transient
    private List<Bien> bienes;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ExclusionEntity() {}

    public ExclusionEntity(Integer idExclusion, Long unidadEjecutora, Date fecha, EstadoEntity estado, TipoEntity tipo, List<Bien> bienes) {
        this.idExclusion = idExclusion;
        this.unidadEjecutora = unidadEjecutora;
        this.fecha = fecha;
        this.estado = estado;
        this.tipo = tipo;
        this.bienes = bienes;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public Integer getIdExclusion() {
        return idExclusion;
    }

    public void setIdExclusion(Integer idExclusion) {
        this.idExclusion = idExclusion;
    }

    public Long getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(Long unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public EstadoEntity getEstado() {
        return estado;
    }

    public void setEstado(EstadoEntity estado) {
        this.estado = estado;
    }

    public TipoEntity getTipo() {
        return tipo;
    }

    public void setTipo(TipoEntity tipo) {
        this.tipo = tipo;
    }

    public List<Bien> getBienes() {
        return bienes;
    }

    public void setBienes(List<Bien> bienes) {
        this.bienes = bienes;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idExclusion != null ? idExclusion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ExclusionEntity)) {
            return false;
        }
        return this.idExclusion.equals(((ExclusionEntity)object).idExclusion);
    }

    @Override
    public String toString() {
        return "entidades.ExclusionEntity[id=" + this.idExclusion + "]";
    }
    //</editor-fold>
}