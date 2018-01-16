/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author jorge.serrano
 */
@Entity(name = "UbicacionBien")
@Table(name = "SIGEBI_OAF.SGB_BIEN_UBICACION")
public class UbicacionBien implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @Basic(optional = false)
    @Column(name = "ID_BIEN")
    private Integer idBien;

    @Column(name = "ID_UBICACION")
    private Integer idUbicacion;
    
    @Column(name = "DETALLE")
    private String detalle;
    
    //</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="Constructores">

    public UbicacionBien() {
    }
    
    //</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="SETS & GETS">

    public Integer getIdBien() {
        return idBien;
    }

    public void setIdBien(Integer idBien) {
        this.idBien = idBien;
    }

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
    
    //</editor-fold>

    
    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBien != null ? idBien.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UbicacionBien)) {
            return false;
        }
        UbicacionBien other = (UbicacionBien) object;
        if ((this.idBien == null && other.idBien != null) || (this.idBien != null && !this.idBien.equals(other.idBien))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.ucr.sigebi.entities.UbicacionBien[ id=" + idBien + " ]";
    }
    
    //</editor-fold>
}
