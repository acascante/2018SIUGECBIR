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
@Entity(name = "NotaEntity")
@Table(name = "SIGEBI_OAF.SGB_NOTA")
@NamedQueries({ 
    @NamedQuery(name = "NotaEntity.findAll", query =  "SELECT s FROM NotaEntity s WHERE s.idBien = :PID_BIEN"),
    @NamedQuery(name = "NotaEntity.findById", query = "SELECT s FROM NotaEntity s WHERE s.idNota = :PID_NOTA"),
    @NamedQuery(name = "NotaEntity.getId", query = "SELECT COUNT(s), MAX(s.idNota) FROM NotaEntity s")
}
)
public class NotaEntity extends ObjetoBase implements Serializable  {
    
    private static final long serialVersionUID = 1L;
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    @Id
    @Basic(optional = false)
    @Column(name = "ID_NOTA")
    private Integer idNota;
    
    @Column(name = "ID_BIEN")
    private Integer idBien;
    
    @Column(name = "DETALLE")
    private String detalle;
    
    @Column(name = "ID_ESTADO")
    private Integer idEstado;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="SET's y GET's ">
    
    
    
    public Integer getIdNota() {
        return idNota;
    }

    public void setIdNota(Integer idNota) {
        this.idNota = idNota;
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

    
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public NotaEntity(Integer idNota, Integer idBien, String detalle, Integer idEstado) {    
        this.idNota = idNota;
        this.idBien = idBien;
        this.detalle = detalle;
        this.idEstado = idEstado;
    }

    public NotaEntity() {
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNota != null ? idNota.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotaEntity)) {
            return false;
        }
        NotaEntity other = (NotaEntity) object;
        if ((this.idNota == null && other.idNota != null) || (this.idNota != null && !this.idNota.equals(other.idNota))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.NotaEntity[idTipo=" + idNota + "]";
    }
    
    //</editor-fold>
    
    
}
