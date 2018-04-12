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
@Entity(name = "TomaFisicaUnitaria")
@Table(name = "SIGEBI_OAF.SIGB_TOMA_FISICA_UNITARIA")
@SequenceGenerator(name = "SGB_SQ_TOMA_FISICA_UNITARIA", sequenceName = "SIGEBI_OAF.SGB_SQ_TOMA_FISICA_UNITARIA", initialValue = 1, allocationSize = 1)
public class TomaFisicaUnitaria extends ObjetoBase implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_TOMA_FISICA_UNITARIA")
    @Column(name = "ID_TOMA_FISICA_UNITARIA")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_TOMA_FISICA", referencedColumnName = "ID_TOMA_FISICA")
    private TomaFisica tomaFisica;

    @ManyToOne
    @JoinColumn(name = "ID_BIEN", referencedColumnName = "ID_BIEN")
    private Bien bien;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">

    public TomaFisicaUnitaria(TomaFisica tomaFisica, Bien bien) {
        this.tomaFisica = tomaFisica;
        this.bien = bien;
    }

    public TomaFisicaUnitaria() {
        super();
    }
    
    //</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TomaFisica getTomaFisica() {
        return tomaFisica;
    }

    public void setTomaFisica(TomaFisica tomaFisica) {
        this.tomaFisica = tomaFisica;
    }

    public Bien getBien() {
        return bien;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 71 * hash + (this.tomaFisica != null ? this.tomaFisica.hashCode() : 0);
        hash = 71 * hash + (this.bien != null ? this.bien.hashCode() : 0);
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
        final TomaFisicaUnitaria other = (TomaFisicaUnitaria) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.tomaFisica != other.tomaFisica && (this.tomaFisica == null || !this.tomaFisica.equals(other.tomaFisica))) {
            return false;
        }
        if (this.bien != other.bien && (this.bien == null || !this.bien.equals(other.bien))) {
            return false;
        }
        return true;
    }

    //</editor-fold>
}
