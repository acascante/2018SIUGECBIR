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
import javax.persistence.Table;

/**
 *
 * @author jorge.serrano
 */
@Entity(name = "AccesoriosEntity")
@Table(name = "SIGEBI_OAF.SGB_ACCESORIO")
public class AccesoriosEntity  extends ObjetoBase implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ACCESORIO")
    private Integer idAccesorio;
    
    
    @Column(name = "ID_BIEN")
    private Integer idBien;
    
    @Column(name = "DETALLE")
    private String detalle;
    
    @Column(name = "ID_ESTADO")
    private Integer idEstado;
    
    //</editor-fold>
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    
    public AccesoriosEntity() {
    }



    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Sets y Gets">

    public Integer getIdAccesorio() {
        return idAccesorio;
    }

    public void setIdAccesorio(Integer idAccesorio) {
        this.idAccesorio = idAccesorio;
    }

    public Integer getIdBien() {
        return idBien;
    }

    public void setIdBien(Integer idBien) {
        this.idBien = idBien;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
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
        hash += (idAccesorio != null ? idAccesorio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccesoriosEntity)) {
            return false;
        }
        AccesoriosEntity other = (AccesoriosEntity) object;
        if ( (this.idAccesorio == null && other.idAccesorio != null) 
         || (this.idAccesorio != null && !this.idAccesorio.equals(other.idAccesorio) )
                ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return " proveedores.DatoBienEntity[idDato = " + idAccesorio + "] ";
    }
    
    //</editor-fold>
    
    
    
}
