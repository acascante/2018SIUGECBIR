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
@SequenceGenerator(name = "SGB_SQ_AUTORI_ROL_PER", sequenceName = "SIGEBI_OAF.SGB_SQ_AUTORI_ROL_PER", initialValue = 1, allocationSize = 1)
public class AutorizacionRolPersona extends ObjetoBase implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_AUTORI_ROL_PER")
    @Column(name = "ID_AUTORIZACION_ROL_PERSONA")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_AUTORIZACION_ROL", referencedColumnName = "ID_AUTORIZACION_ROL")
    private AutorizacionRol autorizacionRol;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    private Usuario usuarioSeguridad;

    @ManyToOne
    @JoinColumn(name = "ID_UNIDAD_EJECUTORA", referencedColumnName = "ID")
    private UnidadEjecutora unidadEjecutora;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
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

    public UnidadEjecutora getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(UnidadEjecutora unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 23 * hash + (this.autorizacionRol != null ? this.autorizacionRol.hashCode() : 0);
        hash = 23 * hash + (this.usuarioSeguridad != null ? this.usuarioSeguridad.hashCode() : 0);
        hash = 23 * hash + (this.unidadEjecutora != null ? this.unidadEjecutora.hashCode() : 0);
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
        if (this.autorizacionRol != other.autorizacionRol && (this.autorizacionRol == null || !this.autorizacionRol.equals(other.autorizacionRol))) {
            return false;
        }
        if (this.usuarioSeguridad != other.usuarioSeguridad && (this.usuarioSeguridad == null || !this.usuarioSeguridad.equals(other.usuarioSeguridad))) {
            return false;
        }
        if (this.unidadEjecutora != other.unidadEjecutora && (this.unidadEjecutora == null || !this.unidadEjecutora.equals(other.unidadEjecutora))) {
            return false;
        }
        return true;
    }
    //</editor-fold>

}
