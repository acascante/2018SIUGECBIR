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
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "Usuario")
@Table(name = "SEGURIDAD_USUARIO")
public class Usuario extends ObjetoBase implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id    
    @Column(name = "ID_USUARIO")
    private String id;

    @Column(name = "DSC_NOMBRE_COMPLETO")
    private String nombreCompleto;

    @Column(name = "COD_CLAVE")
    private String codigoClave;

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
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCodigoClave() {
        return codigoClave;
    }

    public void setCodigoClave(String codigoClave) {
        this.codigoClave = codigoClave;
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
    //</editor-fold>

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 13 * hash + (this.nombreCompleto != null ? this.nombreCompleto.hashCode() : 0);
        hash = 13 * hash + (this.codigoClave != null ? this.codigoClave.hashCode() : 0);
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
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        if ((this.nombreCompleto == null) ? (other.nombreCompleto != null) : !this.nombreCompleto.equals(other.nombreCompleto)) {
            return false;
        }
        if ((this.codigoClave == null) ? (other.codigoClave != null) : !this.codigoClave.equals(other.codigoClave)) {
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