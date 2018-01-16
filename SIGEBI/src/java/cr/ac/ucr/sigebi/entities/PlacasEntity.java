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
@Entity(name = "PlacasEntity")
@Table(name = "SIGEBI_OAF.SGB_IDENTIFICACION")
public class PlacasEntity extends ObjetoBase implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    @Id
    @Basic(optional = false)
    @Column(name = "ID_IDENTIFICACION")
    private Integer idPlaca;
    
    @Column(name = "ID_BIEN")
    private Integer idBien;
    
    @Column(name = "ID_TIPO")
    private Integer tipoPlaca;
    
    @Column(name = "NUMERO_IDENTIFICACION")
    private String placa;
    
    
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(fetch = FetchType.EAGER)
    private EstadoEntity estado;
    
    @Column(name = "NUM_UNIDAD_EJEC")
    private Integer unidadEjecutora;
    
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    
    public PlacasEntity() {
        idPlaca = 0;
        idBien = 0;
        
    }

    

    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Sets y Gets">

    public Integer getIdPlaca() {
        return idPlaca;
    }

    public void setIdPlaca(Integer idPlaca) {
        this.idPlaca = idPlaca;
    }

    public Integer getIdBien() {
        return idBien;
    }

    public void setIdBien(Integer idBien) {
        this.idBien = idBien;
    }

    public Integer getTipoPlaca() {
        return tipoPlaca;
    }

    public void setTipoPlaca(Integer tipoPlaca) {
        this.tipoPlaca = tipoPlaca;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public EstadoEntity getEstado() {
        return estado;
    }

    public void setEstado(EstadoEntity estado) {
        this.estado = estado;
    }
    
    public Integer getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(Integer unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }
   
    
    
    
    
    
    //</editor-fold>

    
    
    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlaca != null ? idPlaca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlacasEntity)) {
            return false;
        }
        PlacasEntity other = (PlacasEntity) object;
        if ( (this.idPlaca == null && other.idPlaca != null) 
         || (this.idPlaca != null && !this.idPlaca.equals(other.idPlaca) )
                ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proveedores.PlacasEntity[idPlaca=" + idPlaca + "]";
    }
    //</editor-fold>
    
    
}
