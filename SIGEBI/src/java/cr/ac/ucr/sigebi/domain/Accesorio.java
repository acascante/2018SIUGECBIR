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
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_ACCESORIO")
    @Column(name = "ID_ACCESORIO")    
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ID_BIEN", referencedColumnName = "ID_BIEN")
    private Bien bien;
    
    @Column(name = "DETALLE")
    private String detalle;
    
    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public Accesorio() {}

    public Accesorio(Long id, Bien bien, String detalle, Estado estado) {
        this.id = id;
        this.bien = bien;
        this.detalle = detalle;
        this.estado = estado;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bien getBien() {
        return bien;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 23 * hash + (this.bien != null ? this.bien.hashCode() : 0);
        hash = 23 * hash + (this.detalle != null ? this.detalle.hashCode() : 0);
        hash = 23 * hash + (this.estado != null ? this.estado.hashCode() : 0);
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
        if (!this.id.equals(other.id) && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.bien != other.bien && (this.bien == null || !this.bien.equals(other.bien))) {
            return false;
        }
        if (this.estado != other.estado && (this.estado == null || !this.estado.equals(other.estado))) {
            return false;
        }
        return true;
    }
    //</editor-fold>
}