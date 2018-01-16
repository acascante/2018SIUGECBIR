/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.sigebi.entities.*;
import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "Usuario")
@Table(name = "SEGURIDAD_USUARIO")
public class Usuario extends ObjetoBase implements Serializable {
    
    @Id    
    @Column(name = "ID_USUARIO")
    private String idUsuario;

    @Column(name = "DSC_NOMBRE_COMPLETO")
    private String nombre_completo;

    @Column(name = "COD_CLAVE")
    private String codClave;

    @Column(name = "NUM_TELEFONO1")
    private String telefono1;

    @Column(name = "NUM_TELEFONO2")
    private String telefono2;

    @Column(name = "NUM_EXTENSION1")
    private String extension1;

    @Column(name = "NUM_EXTENSION2")
    private String extension2;

    @Column(name = "DSC_EMAIL")
    private String correo;

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getCodClave() {
        return codClave;
    }

    public void setCodClave(String codClave) {
        this.codClave = codClave;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getExtension1() {
        return extension1;
    }

    public void setExtension1(String extension1) {
        this.extension1 = extension1;
    }

    public String getExtension2() {
        return extension2;
    }

    public void setExtension2(String extension2) {
        this.extension2 = extension2;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.idUsuario != null ? this.idUsuario.hashCode() : 0);
        hash = 13 * hash + (this.nombre_completo != null ? this.nombre_completo.hashCode() : 0);
        hash = 13 * hash + (this.codClave != null ? this.codClave.hashCode() : 0);
        hash = 13 * hash + (this.telefono1 != null ? this.telefono1.hashCode() : 0);
        hash = 13 * hash + (this.telefono2 != null ? this.telefono2.hashCode() : 0);
        hash = 13 * hash + (this.extension1 != null ? this.extension1.hashCode() : 0);
        hash = 13 * hash + (this.extension2 != null ? this.extension2.hashCode() : 0);
        hash = 13 * hash + (this.correo != null ? this.correo.hashCode() : 0);
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
        final Usuario other = (Usuario) obj;
        if ((this.idUsuario == null) ? (other.idUsuario != null) : !this.idUsuario.equals(other.idUsuario)) {
            return false;
        }
        if ((this.nombre_completo == null) ? (other.nombre_completo != null) : !this.nombre_completo.equals(other.nombre_completo)) {
            return false;
        }
        if ((this.codClave == null) ? (other.codClave != null) : !this.codClave.equals(other.codClave)) {
            return false;
        }
        if ((this.telefono1 == null) ? (other.telefono1 != null) : !this.telefono1.equals(other.telefono1)) {
            return false;
        }
        if ((this.telefono2 == null) ? (other.telefono2 != null) : !this.telefono2.equals(other.telefono2)) {
            return false;
        }
        if ((this.extension1 == null) ? (other.extension1 != null) : !this.extension1.equals(other.extension1)) {
            return false;
        }
        if ((this.extension2 == null) ? (other.extension2 != null) : !this.extension2.equals(other.extension2)) {
            return false;
        }
        if ((this.correo == null) ? (other.correo != null) : !this.correo.equals(other.correo)) {
            return false;
        }
        return true;
    }
    
}
