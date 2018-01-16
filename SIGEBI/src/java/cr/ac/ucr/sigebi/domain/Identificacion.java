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
@Entity(name = "Identificacion")
@Table(name = "SIGEBI_OAF.SIGB_IDENTIFICACION")
@SequenceGenerator(name = "SGB_SQ_IDENTIFICACION", sequenceName = "SIGEBI_OAF.SGB_SQ_IDENTIFICACION", initialValue = 1, allocationSize = 1)
public class Identificacion extends ObjetoBase implements Serializable {
    
    @Id    
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_IDENTIFICACION")
    @Column(name = "ID_IDENTIFICACION")
    private Integer id;
   
    @ManyToOne
    @JoinColumn(name = "ID_BIEN", referencedColumnName = "ID_BIEN")
    private Bien idBien;
        
    @ManyToOne
    @JoinColumn(name = "ID_TIPO", referencedColumnName = "ID_TIPO")
    private Tipo idTipoPlaca;
    
    @Column(name = "NUMERO_IDENTIFICACION")
    private String identificacion;
    
    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private EstadoEntity idEstado;
  
    @ManyToOne
    @JoinColumn(name = "ID_UNIDAD_EJECUTORA", referencedColumnName = "ID")
    private UnidadEjecutora idUnidadEjecutora;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Bien getIdBien() {
        return idBien;
    }

    public void setIdBien(Bien idBien) {
        this.idBien = idBien;
    }

    public Tipo getIdTipoPlaca() {
        return idTipoPlaca;
    }

    public void setIdTipoPlaca(Tipo idTipoPlaca) {
        this.idTipoPlaca = idTipoPlaca;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public EstadoEntity getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(EstadoEntity idEstado) {
        this.idEstado = idEstado;
    }

    public UnidadEjecutora getIdUnidadEjecutora() {
        return idUnidadEjecutora;
    }

    public void setIdUnidadEjecutora(UnidadEjecutora idUnidadEjecutora) {
        this.idUnidadEjecutora = idUnidadEjecutora;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 29 * hash + (this.idBien != null ? this.idBien.hashCode() : 0);
        hash = 29 * hash + (this.idTipoPlaca != null ? this.idTipoPlaca.hashCode() : 0);
        hash = 29 * hash + (this.identificacion != null ? this.identificacion.hashCode() : 0);
        hash = 29 * hash + (this.idEstado != null ? this.idEstado.hashCode() : 0);
        hash = 29 * hash + (this.idUnidadEjecutora != null ? this.idUnidadEjecutora.hashCode() : 0);
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
        if (this.idBien != other.idBien && (this.idBien == null || !this.idBien.equals(other.idBien))) {
            return false;
        }
        if (this.idTipoPlaca != other.idTipoPlaca && (this.idTipoPlaca == null || !this.idTipoPlaca.equals(other.idTipoPlaca))) {
            return false;
        }
        if (this.idEstado != other.idEstado && (this.idEstado == null || !this.idEstado.equals(other.idEstado))) {
            return false;
        }
        if (this.idUnidadEjecutora != other.idUnidadEjecutora && (this.idUnidadEjecutora == null || !this.idUnidadEjecutora.equals(other.idUnidadEjecutora))) {
            return false;
        }
        return true;
    }
    
    
}
