/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author jorge.serrano
 */
@Entity(name = "TrasladoDetalle")
@Table(name = "SIGEBI_OAF.SIGB_TRASLADO_DETALLE")
@PrimaryKeyJoinColumn(name = "ID_SOLICITUD_DETALLE", referencedColumnName = "ID_SOLICITUD_DETALLE")
public class TrasladoDetalle  extends SolicitudDetalle implements Serializable  {
    
    private static final long serialVersionUID = 1L;
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    @ManyToOne
    @JoinColumn(name = "ID_PERSONA_RECIBE", referencedColumnName = "ID_USUARIO")
    private Usuario usuarioRecibe;
    
    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;

    @Column(name = "FECHA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    
    //</editor-fold>

    public TrasladoDetalle() {
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">

    public TrasladoDetalle(Solicitud solicitud, Bien bien, Estado estado) {
        super(solicitud, bien, estado);
        this.estado = estado;
    }
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">

    public Usuario getUsuarioRecibe() {
        return usuarioRecibe;
    }

    public void setUsuarioRecibe(Usuario usuarioRecibe) {
        this.usuarioRecibe = usuarioRecibe;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }


    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.usuarioRecibe.getId() != null ? this.usuarioRecibe.getId().hashCode() : 0);
        hash = 79 * hash + (this.estado.getId() != null ? this.estado.getId().hashCode() : 0);
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
        final TrasladoDetalle other = (TrasladoDetalle) obj;
        if ((this.usuarioRecibe.getId() == null) ? (other.usuarioRecibe.getId() != null) : !this.usuarioRecibe.getId().equals(other.usuarioRecibe.getId())) {
            return false;
        }
        if ((this.estado.getId() == null) ? (other.estado.getId() != null) : !this.estado.getId().equals(other.estado.getId())) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.ucr.sigebi.entities.TrasladoDetalle[ id=" + this.getId() + " ]";
    }
    //</editor-fold>
    
}
