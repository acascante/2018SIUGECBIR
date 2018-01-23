/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "AdjuntoBien")
@Table(name = "SIGEBI_OAF.SIGB_ADJUNTO_BIEN")
@DiscriminatorValue("1")
@PrimaryKeyJoinColumn(name = "ID_ADJUNTO_BIEN", referencedColumnName = "ID_ADJUNTO")
public class AdjuntoBien extends Adjunto implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @ManyToOne
    @JoinColumn(name = "ID_BIEN", referencedColumnName = "ID_BIEN")
    private Bien bien;

    @Column(name = "TIPO_MIME")
    private String tipoMime;

    @Column(name = "TAMANNO")
    private float tamano;

    @Column(name = "EXTENSION")
    private String extension;

    @Column(name = "NOMBRE")
    private String nombre;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public AdjuntoBien() {
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sets y Gets">
    public Bien getBien() {
        return bien;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
    }

    public String getTipoMime() {
        return tipoMime;
    }

    public void setTipoMime(String tipoMime) {
        this.tipoMime = tipoMime;
    }

    public float getTamano() {
        return tamano;
    }

    public void setTamano(float tamano) {
        this.tamano = tamano;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.bien != null ? this.bien.hashCode() : 0);
        hash = 53 * hash + (this.tipoMime != null ? this.tipoMime.hashCode() : 0);
        hash = 53 * hash + Float.floatToIntBits(this.tamano);
        hash = 53 * hash + (this.extension != null ? this.extension.hashCode() : 0);
        hash = 53 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
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
        final AdjuntoBien other = (AdjuntoBien) obj;
        if (Float.floatToIntBits(this.tamano) != Float.floatToIntBits(other.tamano)) {
            return false;
        }
        if ((this.tipoMime == null) ? (other.tipoMime != null) : !this.tipoMime.equals(other.tipoMime)) {
            return false;
        }
        if ((this.extension == null) ? (other.extension != null) : !this.extension.equals(other.extension)) {
            return false;
        }
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        if (this.bien != other.bien && (this.bien == null || !this.bien.equals(other.bien))) {
            return false;
        }
        return true;
    }
    //</editor-fold>

}
