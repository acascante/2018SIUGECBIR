/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.entities;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author jorge.serrano
 */
@Entity(name = "UbicacionEntity")
@Table(name = "SIGEBI_OAF.SGB_UBICACION")
@NamedQueries({ 
      @NamedQuery(name = "UbicacionEntity.findAll", query =  "SELECT s FROM UbicacionEntity s")
    , @NamedQuery(name = "UbicacionEntity.findById", query = "SELECT s FROM UbicacionEntity s WHERE s.idUbicacion = :PID_UBICACION")
}
)
public class UbicacionEntity extends ObjetoBase implements Serializable {
    
    private static final long serialVersionUID = 1L;

    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    @Id
    @Basic(optional = false)
    @Column(name = "ID_UBICACION")
    private Integer idUbicacion;

    @Column(name = "DETALLE")
    private String detalle;

    @Column(name = "PERTENECE")
    private Integer pertenece;
    
    @Column(name = "ID_PERSONA")
    private Integer idPersona;
    
    @Column(name = "NUM_UNIDAD_EJEC")
    private Integer numUnidEjecutora;
    
    @Column(name = "ID_ESTADO")
    private Integer idEstado;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">

    public UbicacionEntity() {
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">

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

    public Integer getNumUnidEjecutora() {
        return numUnidEjecutora;
    }

    public void setNumUnidEjecutora(Integer numUnidEjecutora) {
        this.numUnidEjecutora = numUnidEjecutora;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }
    
    
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstado != null ? idEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoEntity)) {
            return false;
        }
        UbicacionEntity other = (UbicacionEntity) object;
        if ((this.idEstado == null && other.idUbicacion != null) || (this.idEstado != null && !this.idEstado.equals(other.idUbicacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.UbicacionEntity[idUbicacion=" + idUbicacion + "]";
    }
    //</editor-fold>
    
    
}
