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
@Entity(name = "Identificacion")
@Table(name = "SIGEBI_OAF.SIGB_IDENTIFICACION")
@SequenceGenerator(name = "SGB_SQ_IDENTIFICACION", sequenceName = "SIGEBI_OAF.SGB_SQ_IDENTIFICACION", initialValue = 1, allocationSize = 1)
public class Identificacion extends ObjetoBase implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id    
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_IDENTIFICACION")
    @Column(name = "ID_IDENTIFICACION")
    private Integer id;
   
    @ManyToOne
    @JoinColumn(name = "ID_BIEN", referencedColumnName = "ID_BIEN")
    private Bien bien;
        
    @ManyToOne
    @JoinColumn(name = "ID_TIPO", referencedColumnName = "ID_TIPO")
    private Tipo tipoPlaca;
    
    @Column(name = "NUMERO_IDENTIFICACION")
    private String identificacion;
    
    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;
  
    @ManyToOne
    @JoinColumn(name = "ID_UNIDAD_EJECUTORA", referencedColumnName = "ID")
    private UnidadEjecutora unidadEjecutora;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Bien getBien() {
        return bien;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
    }

    public Tipo getTipoPlaca() {
        return tipoPlaca;
    }

    public void setTipoPlaca(Tipo tipoPlaca) {
        this.tipoPlaca = tipoPlaca;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public UnidadEjecutora getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(UnidadEjecutora unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }
    //</editor-fold>

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 29 * hash + (this.bien != null ? this.bien.hashCode() : 0);
        hash = 29 * hash + (this.tipoPlaca != null ? this.tipoPlaca.hashCode() : 0);
        hash = 29 * hash + (this.identificacion != null ? this.identificacion.hashCode() : 0);
        hash = 29 * hash + (this.estado != null ? this.estado.hashCode() : 0);
        hash = 29 * hash + (this.unidadEjecutora != null ? this.unidadEjecutora.hashCode() : 0);
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
        final Identificacion other = (Identificacion) obj;
        if ((this.identificacion == null) ? (other.identificacion != null) : !this.identificacion.equals(other.identificacion)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.bien != other.bien && (this.bien == null || !this.bien.equals(other.bien))) {
            return false;
        }
        if (this.tipoPlaca != other.tipoPlaca && (this.tipoPlaca == null || !this.tipoPlaca.equals(other.tipoPlaca))) {
            return false;
        }
        if (this.estado != other.estado && (this.estado == null || !this.estado.equals(other.estado))) {
            return false;
        }
        if (this.unidadEjecutora != other.unidadEjecutora && (this.unidadEjecutora == null || !this.unidadEjecutora.equals(other.unidadEjecutora))) {
            return false;
        }
        return true;
    }
}