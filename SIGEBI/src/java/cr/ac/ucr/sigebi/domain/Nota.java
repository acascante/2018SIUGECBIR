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
 * @author jorge.serrano
 */
@Entity(name = "Nota")
@Table(name = "SIGEBI_OAF.SGB_NOTA")
@SequenceGenerator(name = "SGB_SQ_NOTAS", sequenceName = "SIGEBI_OAF.SGB_SQ_NOTAS", initialValue = 1, allocationSize = 1)
public class Nota extends ObjetoBase implements Serializable  {
    
    private static final long serialVersionUID = 1L;
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_NOTAS")
    @Column(name = "ID_NOTA")
    private Long id;
    
    @Column(name = "ID_BIEN")
    private Long idBien;
    
    @Column(name = "DETALLE")
    private String detalle;
    
    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado idEstado;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="SET's y GET's ">
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdBien() {
        return idBien;
    }

    public void setIdBien(Long idBien) {
        this.idBien = idBien;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }
    
    
    
    //</editor-fold>

    
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public Nota(Long id, Long idBien, String detalle, Estado idEstado) {    
        this.id = id;
        this.idBien = idBien;
        this.detalle = detalle;
        this.idEstado = idEstado;
    }

    public Nota() {
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nota)) {
            return false;
        }
        Nota other = (Nota) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Nota[idTipo=" + id + "]";
    }
    
    //</editor-fold>
    
    
}
