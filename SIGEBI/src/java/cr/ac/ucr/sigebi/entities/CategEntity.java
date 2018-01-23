/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.entities;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author jorge.serrano
 */
@Entity(name = "CategEntity")
@Table(name = "SF_CATEGORIA_ACTIVOF")
@NamedQueries({ 
      @NamedQuery(name = "CategEntity.findAll", query =  "SELECT s FROM CategEntity s")
    , @NamedQuery(name = "CategEntity.findById", query = "SELECT s FROM CategEntity s WHERE s.codCategoria = :pcodCategoria")
}
)
public class CategEntity  extends ObjetoBase implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    @Id
    @Basic(optional = false)
    @Column(name = "NUM_EMPRESA")
    private Integer numEmpresa;
    
    @Id
    @Column(name = "CODIGO_CATEGORIA")
    private String codCategoria;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "FECHA_ULTIMO_PROCESO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecUltimProc;
    
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    
    //</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="STE's & GET's">
    
    public Integer getNumEmpresa() {
        return numEmpresa;
    }

    public void setNumEmpresa(Integer numEmpresa) {
        this.numEmpresa = numEmpresa;
    }

    public String getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(String codCategoria) {
        this.codCategoria = codCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecUltimProc() {
        return fecUltimProc;
    }

    public void setFecUltimProc(Date fecUltimProc) {
        this.fecUltimProc = fecUltimProc;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">

    public CategEntity() {
    }
    
    public CategEntity(Integer numEmpresa, String codCategoria, String descripcion, Date fecUltimProc, String observaciones) {
        this.numEmpresa = numEmpresa;
        this.codCategoria = codCategoria;
        this.descripcion = descripcion;
        this.fecUltimProc = fecUltimProc;
        this.observaciones = observaciones;
    }
    
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="SobreCargas">
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCategoria != null ? codCategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategEntity)) {
            return false;
        }
        CategEntity other = (CategEntity) object;
        if ((this.codCategoria == null && other.codCategoria != null) || (this.codCategoria != null && !this.codCategoria.equals(other.codCategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.CategoriaEntity[codCategoria=" + codCategoria + "]";
    }
    
    //</editor-fold>


}
