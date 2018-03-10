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
@Entity(name = "RegistroMovimientoSolicitud")
@Table(name = "SIGEBI_OAF.SIGB_REGISTRO_MOVI_SOLICITUD")
@DiscriminatorValue("3")
@PrimaryKeyJoinColumn(name = "ID_REGISTRO_MOVIMIENTO", referencedColumnName = "ID_REGISTRO_MOVIMIENTO")
public class RegistroMovimientoSolicitud extends RegistroMovimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @ManyToOne
    @JoinColumn(name = "ID_SOLICITUD", referencedColumnName = "ID_SOLICITUD")
    private Solicitud solicitud;

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public RegistroMovimientoSolicitud() {
    }

    public RegistroMovimientoSolicitud(Tipo tipo, String observacion, Integer telefono, Date fecha, Usuario usuario, Estado estado, Solicitud solicitud) {
        super(tipo, observacion, telefono, fecha, usuario, estado, Constantes.DISCRIMINATOR_REGISTRO_MOVIMIENTO_SOLICITUD);
        this.solicitud = solicitud;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    
    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + (this.solicitud != null ? this.solicitud.hashCode() : 0);
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
        final RegistroMovimientoSolicitud other = (RegistroMovimientoSolicitud) obj;
        if (this.solicitud != other.solicitud && (this.solicitud == null || !this.solicitud.equals(other.solicitud))) {
            return false;
        }
        return true;
    }
    //</editor-fold>

}
