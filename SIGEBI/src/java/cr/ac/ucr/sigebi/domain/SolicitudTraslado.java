/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.sigebi.utils.Constantes;
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
@Entity(name = "SolicitudTraslado")
@Table(name = "SIGEBI_OAF.SIGB_SOLICITUD_TRASLADO")
@PrimaryKeyJoinColumn(name = "ID_SOLICITUD", referencedColumnName = "ID_SOLICITUD")
@DiscriminatorValue("3")
public class SolicitudTraslado extends Solicitud {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @ManyToOne
    @JoinColumn(name = "ID_UNIDAD_EJECU_DEST", referencedColumnName = "ID")
    private UnidadEjecutora unidadEjecutoraDestino;

    @ManyToOne
    @JoinColumn(name = "ID_UBICACION", referencedColumnName = "ID_UBICACION")
    private Ubicacion ubicacion;
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    /*
    public DocumentoTraslado() {
        super();
        this.setFecha(new Date());
        this.setDiscriminator(Constantes.DISCRIMINATOR_DOCUMENTO_TRASLADO);
    }
    */
    public SolicitudTraslado(Estado estado
            , UnidadEjecutora unidadEjecutora) {
        super(Constantes.DISCRIMINATOR_SOLICITUD_TRASLADO, unidadEjecutora, estado);
    }
    
    //</editor-fold>

    public SolicitudTraslado() {
       super();
      this.setDiscriminator(Constantes.DISCRIMINATOR_SOLICITUD_TRASLADO);
    }
    
    public UnidadEjecutora getUnidadEjecutoraDestino() {
        return unidadEjecutoraDestino;
    }

    public void setUnidadEjecutoraDestino(UnidadEjecutora unidadEjecutoraDestino) {
        this.unidadEjecutoraDestino = unidadEjecutoraDestino;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.unidadEjecutoraDestino != null ? this.unidadEjecutoraDestino.hashCode() : 0);
        hash = 89 * hash + (this.ubicacion != null ? this.ubicacion.hashCode() : 0);
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
        final SolicitudTraslado other = (SolicitudTraslado) obj;
        if (this.unidadEjecutoraDestino != other.unidadEjecutoraDestino && (this.unidadEjecutoraDestino == null || !this.unidadEjecutoraDestino.equals(other.unidadEjecutoraDestino))) {
            return false;
        }
        if (this.ubicacion != other.ubicacion && (this.ubicacion == null || !this.ubicacion.equals(other.ubicacion))) {
            return false;
        }
        return true;
    }

    //</editor-fold> 
}
