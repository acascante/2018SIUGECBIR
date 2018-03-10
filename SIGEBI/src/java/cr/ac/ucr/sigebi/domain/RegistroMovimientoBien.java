/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.sigebi.utils.Constantes;
import java.io.Serializable;
import java.util.Date;
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
@Entity(name = "RegistroMovimientoBien")
@Table(name = "SIGEBI_OAF.SIGB_REGISTRO_MOVIMIENTO_BIEN")
@DiscriminatorValue("1")
@PrimaryKeyJoinColumn(name = "ID_REGISTRO_MOVIMIENTO", referencedColumnName = "ID_REGISTRO_MOVIMIENTO")
public class RegistroMovimientoBien extends RegistroMovimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @ManyToOne
    @JoinColumn(name = "ID_BIEN", referencedColumnName = "ID_BIEN")
    private Bien bien;

    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">

    public RegistroMovimientoBien() {
    }

    public RegistroMovimientoBien(Tipo tipo, String observacion, Integer telefono, Date fecha, Usuario usuario, Estado estado, Bien bien) {
        super(tipo, observacion, telefono, fecha, usuario, estado, Constantes.DISCRIMINATOR_REGISTRO_MOVIMIENTO_BIEN);
        this.bien = bien;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
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
        int hash = 5;
        hash = 23 * hash + (this.bien != null ? this.bien.hashCode() : 0);
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
        final RegistroMovimientoBien other = (RegistroMovimientoBien) obj;
        if (this.bien != other.bien && (this.bien == null || !this.bien.equals(other.bien))) {
            return false;
        }
        return true;
    }

    //</editor-fold>
}
