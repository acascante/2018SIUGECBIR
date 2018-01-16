/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.entities;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author jorge.serrano
 */
@Entity(name = "UsuarioEntity")
@Table(name = "SEGURIDAD_USUARIO")
public class UsuarioEntity  extends ObjetoBase implements Serializable {
    private static final long serialVersionUID = 1L;


    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    @Id
    @Basic(optional = false)
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

    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Contructor">
    
    
    public UsuarioEntity() {
    }
    
    //</editor-fold>
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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

    //</editor-fold>
    
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioEntity)) {
            return false;
        }
        UsuarioEntity other = (UsuarioEntity) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.tipoEntity[idTipo=" + idUsuario + "]";
    }
    //</editor-fold>
    
}
