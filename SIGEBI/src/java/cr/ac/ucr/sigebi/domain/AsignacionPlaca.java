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
@Entity(name = "AsignacionPlaca")
@Table(name = "SIGEBI_OAF.SIGB_ASIGNACION_PLACA")
@SequenceGenerator(name = "SGB_SQ_ASIGNACION_PLACA",  sequenceName = "SIGEBI_OAF.SGB_SQ_ASIGNACION_PLACA", initialValue = 1, allocationSize = 1)
public class AsignacionPlaca  extends ObjetoBase implements Serializable{
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_ASIGNACION_PLACA")
    @Column(name = "ID_ASIGNACION")    
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ID_UNIDAD_EJECUTORA", referencedColumnName = "ID")
    private UnidadEjecutora unidadEjecutora;
    
    @Column(name = "CANTIDAD_SOLICITADA")
    private Long cantidadSolicitada;
    
    @Column(name = "JUSTIFICACION")
    private String justificacion;

    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public AsignacionPlaca() {
    
    }

    public AsignacionPlaca(UnidadEjecutora unidadEjecutora, Long cantidadSolicitada, String justificacion, Estado estado) {
        this.unidadEjecutora = unidadEjecutora;
        this.cantidadSolicitada = cantidadSolicitada;
        this.justificacion = justificacion;
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

    public UnidadEjecutora getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(UnidadEjecutora unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }

    public Long getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(Long cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
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
        int hash = 3;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 89 * hash + (this.unidadEjecutora != null ? this.unidadEjecutora.hashCode() : 0);
        hash = 89 * hash + (this.cantidadSolicitada != null ? this.cantidadSolicitada.hashCode() : 0);
        hash = 89 * hash + (this.justificacion != null ? this.justificacion.hashCode() : 0);
        hash = 89 * hash + (this.estado != null ? this.estado.hashCode() : 0);
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
        final AsignacionPlaca other = (AsignacionPlaca) obj;
        if ((this.justificacion == null) ? (other.justificacion != null) : !this.justificacion.equals(other.justificacion)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.unidadEjecutora != other.unidadEjecutora && (this.unidadEjecutora == null || !this.unidadEjecutora.equals(other.unidadEjecutora))) {
            return false;
        }
        if (this.cantidadSolicitada != other.cantidadSolicitada && (this.cantidadSolicitada == null || !this.cantidadSolicitada.equals(other.cantidadSolicitada))) {
            return false;
        }
        if (this.estado != other.estado && (this.estado == null || !this.estado.equals(other.estado))) {
            return false;
        }
        return true;
    }

    //</editor-fold>

   
}