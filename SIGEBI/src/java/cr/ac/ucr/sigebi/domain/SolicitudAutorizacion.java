/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "SolicitudAutorizacion")
@Table(name = "SIGEBI_OAF.SIGB_SOLICITUD_AUTORIZACION")
@SequenceGenerator(name = "SGB_SQ_SOL_AUTORIZACION", sequenceName = "SIGEBI_OAF.SGB_SQ_SOL_AUTORIZACION", initialValue = 1, allocationSize = 1)
public class SolicitudAutorizacion extends ObjetoBase implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_SOL_AUTORIZACION")
    @Column(name = "ID_SOLICITUD_AUTORIZACION")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_SOLICITUD", referencedColumnName = "ID_SOLICITUD")
    private Solicitud solicitud;

    @ManyToOne
    @JoinColumn(name = "ID_AUTORIZACION_ROL", referencedColumnName = "ID_AUTORIZACION_ROL")
    private AutorizacionRol autorizacionRol;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    private Usuario usuarioSeguridad;

    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;

    @Column(name = "FECHA_HORA")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public SolicitudAutorizacion() {

    }

    public SolicitudAutorizacion(Solicitud solicitud,
             AutorizacionRol autorizacionRol,
             Usuario usuarioSeguridad,
             Estado estado) {
        this.solicitud = solicitud;
        this.autorizacionRol = autorizacionRol;
        this.usuarioSeguridad = usuarioSeguridad;
        this.estado = estado;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AutorizacionRol getAutorizacionRol() {
        return autorizacionRol;
    }

    public void setAutorizacionRol(AutorizacionRol autorizacionRol) {
        this.autorizacionRol = autorizacionRol;
    }

    public Usuario getUsuarioSeguridad() {
        return usuarioSeguridad;
    }

    public void setUsuarioSeguridad(Usuario usuarioSeguridad) {
        this.usuarioSeguridad = usuarioSeguridad;
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
        int hash = 3;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final SolicitudAutorizacion other = (SolicitudAutorizacion) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    

    //</editor-fold>

}
