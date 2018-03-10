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
@Entity(name = "RegistroMovimientoDocumento")
@Table(name = "SIGEBI_OAF.SIGB_REGISTRO_MOVI_DOCUMENTO")
@DiscriminatorValue("2")
@PrimaryKeyJoinColumn(name = "ID_REGISTRO_MOVIMIENTO", referencedColumnName = "ID_REGISTRO_MOVIMIENTO")
public class RegistroMovimientoDocumento extends RegistroMovimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @ManyToOne
    @JoinColumn(name = "ID_DOCUMENTO", referencedColumnName = "ID_DOCUMENTO")
    private Documento documento;

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public RegistroMovimientoDocumento() {
    }

    public RegistroMovimientoDocumento(Tipo tipo, String observacion, Integer telefono, Date fecha, Usuario usuario, Estado estado, Documento documento) {
        super(tipo, observacion, telefono, fecha, usuario, estado, Constantes.DISCRIMINATOR_REGISTRO_MOVIMIENTO_SOLICITUD);
        this.documento = documento;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.documento != null ? this.documento.hashCode() : 0);
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
        final RegistroMovimientoDocumento other = (RegistroMovimientoDocumento) obj;
        if (this.documento != other.documento && (this.documento == null || !this.documento.equals(other.documento))) {
            return false;
        }
        return true;
    }    
    //</editor-fold>

}
