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
@Entity(name = "TomaFisicaLote")
@Table(name = "SIGEBI_OAF.SIGB_TOMA_FISICA_LOTE")
@SequenceGenerator(name = "SGB_SQ_TOMA_FISICA_LOTE",  sequenceName = "SIGEBI_OAF.SGB_SQ_TOMA_FISICA_LOTE", initialValue = 1, allocationSize = 1)
public class TomaFisicaLote extends ObjetoBase implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_TOMA_FISICA_LOTE")
    @Column(name = "ID_TOMA_FISICA_LOTE")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_TOMA_FISICA", referencedColumnName = "ID_TOMA_FISICA")
    private TomaFisica tomaFisica;

    @ManyToOne
    @JoinColumn(name = "ID_LOTE", referencedColumnName = "ID")
    private Lote lote;

    @Column(name = "CANTIDAD")
    private Long cantidad;
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">

    public TomaFisicaLote(TomaFisica tomaFisica, Lote lote, Long cantidad) {
        this.tomaFisica = tomaFisica;
        this.lote = lote;
        this.cantidad = cantidad;
    }

    public TomaFisicaLote() {
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

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
      
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
   
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 17 * hash + (this.tomaFisica != null ? this.tomaFisica.hashCode() : 0);
        hash = 17 * hash + (this.lote != null ? this.lote.hashCode() : 0);
        hash = 17 * hash + (this.cantidad != null ? this.cantidad.hashCode() : 0);
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
        final TomaFisicaLote other = (TomaFisicaLote) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.tomaFisica != other.tomaFisica && (this.tomaFisica == null || !this.tomaFisica.equals(other.tomaFisica))) {
            return false;
        }
        if (this.lote != other.lote && (this.lote == null || !this.lote.equals(other.lote))) {
            return false;
        }
        if (this.cantidad != other.cantidad && (this.cantidad == null || !this.cantidad.equals(other.cantidad))) {
            return false;
        }
        return true;
    }

    //</editor-fold>

}
