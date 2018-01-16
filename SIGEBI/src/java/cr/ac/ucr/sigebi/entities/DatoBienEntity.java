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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author jorge.serrano
 */
@Entity(name = "DatoBienEntity")
@Table(name = "SIGEBI_OAF.SGB_DATO_BIEN")
public class DatoBienEntity  extends ObjetoBase implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DATO_BIEN")
    private Integer idDato;
    
    @Column(name = "DETALLE")
    private String detalle;
    
    @Column(name = "ID_BIEN")
    private Integer idBien;
    
    @JoinColumn(name = "ID_TIPO", referencedColumnName = "ID_TIPO")
    @ManyToOne(fetch = FetchType.EAGER)
    private TipoEntity tipoCaracteristica;
    
    
    @Column(name = "ID_ESTADO")
    private Integer estado;
    
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    
    public DatoBienEntity() {
    }



    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Sets y Gets">
    
    
    public Integer getIdDato() {
        return idDato;
    }

    public void setIdDato(Integer idDato) {
        this.idDato = idDato;
    }

    public String getDetalle() {
        return detalle;
    }
    
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Integer getIdBien() {
        return idBien;
    }

    public void setIdBien(Integer idBien) {
        this.idBien = idBien;
    }

    public TipoEntity getTipoCaracteristica() {
        return tipoCaracteristica;
    }

    public void setTipoCaracteristica(TipoEntity tipoCaracteristica) {
        this.tipoCaracteristica = tipoCaracteristica;
    }

    
    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    
    
    //</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDato != null ? idDato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DatoBienEntity)) {
            return false;
        }
        DatoBienEntity other = (DatoBienEntity) object;
        if ( (this.idDato == null && other.idDato != null) 
         || (this.idDato != null && !this.idDato.equals(other.idDato) )
                ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return " proveedores.DatoBienEntity[idDato = " + idDato + "] ";
    }
    
    //</editor-fold>
    
    
}
