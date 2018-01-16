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
@Entity(name = "Accesorio")
@Table(name = "SIGEBI_OAF.SIGB_ACCESORIO")
@SequenceGenerator(name = "SGB_SQ_ACCESORIO",  sequenceName = "SIGEBI_OAF.SGB_SQ_ACCESORIO", initialValue = 1, allocationSize = 1)
public class Accesorio  extends ObjetoBase implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_ACCESORIO")
    @Column(name = "ID_ACCESORIO")    
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ID_BIEN", referencedColumnName = "ID_BIEN")
    private Bien idBien;
    
    @Column(name = "DETALLE")
    private String detalle;
    
    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado idEstado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bien getIdBien() {
        return idBien;
    }

    public void setIdBien(Bien idBien) {
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 23 * hash + (this.idBien != null ? this.idBien.hashCode() : 0);
        hash = 23 * hash + (this.detalle != null ? this.detalle.hashCode() : 0);
        hash = 23 * hash + (this.idEstado != null ? this.idEstado.hashCode() : 0);
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
        final Accesorio other = (Accesorio) obj;
        if ((this.detalle == null) ? (other.detalle != null) : !this.detalle.equals(other.detalle)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.idBien != other.idBien && (this.idBien == null || !this.idBien.equals(other.idBien))) {
            return false;
        }
        if (this.idEstado != other.idEstado && (this.idEstado == null || !this.idEstado.equals(other.idEstado))) {
            return false;
        }
        return true;
    }
    
    
    
}
