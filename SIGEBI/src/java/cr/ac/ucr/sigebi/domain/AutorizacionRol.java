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
@Entity(name = "AutorizacionRol")
@Table(name = "SIGEBI_OAF.SIGB_AUTORIZACION_ROL")
@SequenceGenerator(name = "SGB_SQ_AUTORIZACION_ROL",  sequenceName = "SIGEBI_OAF.SGB_SQ_AUTORIZACION_ROL", initialValue = 1, allocationSize = 1)
public class AutorizacionRol extends ObjetoBase implements Serializable {
   
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_AUTORIZACION")
    @Column(name = "ID_AUTORIZACION_ROL")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ID_AUTORIZACION", referencedColumnName = "ID_AUTORIZACION")
    private Autorizacion idAutorizacion;
    
    @ManyToOne
    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL")
    private Rol idRol;

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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 71 * hash + (this.idAutorizacion != null ? this.idAutorizacion.hashCode() : 0);
        hash = 71 * hash + (this.idRol != null ? this.idRol.hashCode() : 0);
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
        if (this.idAutorizacion != other.idAutorizacion && (this.idAutorizacion == null || !this.idAutorizacion.equals(other.idAutorizacion))) {
            return false;
        }
        if (this.idRol != other.idRol && (this.idRol == null || !this.idRol.equals(other.idRol))) {
            return false;
        }
        return true;
    }
   

}
