/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "AutorizacionRol")
@Table(name = "SIGEBI_OAF.SIGB_AUTORIZACION_ROL")
@SequenceGenerator(name = "SGB_SQ_AUTORIZACION_ROL",  sequenceName = "SIGEBI_OAF.SGB_SQ_AUTORIZACION_ROL", initialValue = 1, allocationSize = 1)
public class AutorizacionRol extends ObjetoBase implements Serializable {
   
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_AUTORIZACION_ROL")
    @Column(name = "ID_AUTORIZACION_ROL")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ID_AUTORIZACION", referencedColumnName = "ID_AUTORIZACION")
    private Autorizacion autorizacion;
    
    @ManyToOne
    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL")
    private Rol rol;
    
    @Transient
    private List<AutorizacionRolPersona> autorizacionesRolPersona;
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Autorizacion getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(Autorizacion autorizacion) {
        this.autorizacion = autorizacion;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<AutorizacionRolPersona> getAutorizacionesRolPersona() {
        return autorizacionesRolPersona;
    }

    public void setAutorizacionesRolPersona(List<AutorizacionRolPersona> autorizacionesRolPersona) {
        this.autorizacionesRolPersona = autorizacionesRolPersona;
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 71 * hash + (this.autorizacion != null ? this.autorizacion.hashCode() : 0);
        hash = 71 * hash + (this.rol != null ? this.rol.hashCode() : 0);
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
        final AutorizacionRol other = (AutorizacionRol) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.autorizacion != other.autorizacion && (this.autorizacion == null || !this.autorizacion.equals(other.autorizacion))) {
            return false;
        }
        if (this.rol != other.rol && (this.rol == null || !this.rol.equals(other.rol))) {
            return false;
        }
        return true;
    }
    //</editor-fold>
}