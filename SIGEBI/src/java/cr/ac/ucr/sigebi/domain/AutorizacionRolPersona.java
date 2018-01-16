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
@Entity(name = "AutorizacionRolPersona")
@Table(name = "SIGEBI_OAF.SIGB_AUTORIZACION_ROL_PERSONA")
@SequenceGenerator(name = "SGB_SQ_AUTORI_ROL_PER",  sequenceName = "SIGEBI_OAF.SGB_SQ_AUTORI_ROL_PER", initialValue = 1, allocationSize = 1)
public class AutorizacionRolPersona extends ObjetoBase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_AUTORI_ROL_PER")
    @Column(name = "ID_AUTORIZACION_ROL_PERSONA")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ID_AUTORIZACION", referencedColumnName = "ID_AUTORIZACION")
    private Autorizacion idAutorizacion;
    
    @ManyToOne
    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL")
    private Rol idRol;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    private Usuario idUsuarioSeguridad;

    @ManyToOne
    @JoinColumn(name = "ID_UNIDAD_EJECUTORA", referencedColumnName = "ID")
    private UnidadEjecutora idUnidadEjecutora;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Autorizacion getIdAutorizacion() {
        return idAutorizacion;
    }

    public void setIdAutorizacion(Autorizacion idAutorizacion) {
        this.idAutorizacion = idAutorizacion;
    }

    public Rol getIdRol() {
        return idRol;
    }

    public void setIdRol(Rol idRol) {
        this.idRol = idRol;
    }

    public Usuario getIdUsuarioSeguridad() {
        return idUsuarioSeguridad;
    }

    public void setIdUsuarioSeguridad(Usuario idUsuarioSeguridad) {
        this.idUsuarioSeguridad = idUsuarioSeguridad;
    }

    public UnidadEjecutora getIdUnidadEjecutora() {
        return idUnidadEjecutora;
    }

    public void setIdUnidadEjecutora(UnidadEjecutora idUnidadEjecutora) {
        this.idUnidadEjecutora = idUnidadEjecutora;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 29 * hash + (this.idAutorizacion != null ? this.idAutorizacion.hashCode() : 0);
        hash = 29 * hash + (this.idRol != null ? this.idRol.hashCode() : 0);
        hash = 29 * hash + (this.idUsuarioSeguridad != null ? this.idUsuarioSeguridad.hashCode() : 0);
        hash = 29 * hash + (this.idUnidadEjecutora != null ? this.idUnidadEjecutora.hashCode() : 0);
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
        final AutorizacionRolPersona other = (AutorizacionRolPersona) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.idAutorizacion != other.idAutorizacion && (this.idAutorizacion == null || !this.idAutorizacion.equals(other.idAutorizacion))) {
            return false;
        }
        if (this.idRol != other.idRol && (this.idRol == null || !this.idRol.equals(other.idRol))) {
            return false;
        }
        if (this.idUsuarioSeguridad != other.idUsuarioSeguridad && (this.idUsuarioSeguridad == null || !this.idUsuarioSeguridad.equals(other.idUsuarioSeguridad))) {
            return false;
        }
        if (this.idUnidadEjecutora != other.idUnidadEjecutora && (this.idUnidadEjecutora == null || !this.idUnidadEjecutora.equals(other.idUnidadEjecutora))) {
            return false;
        }
        return true;
    }
    
    
}
