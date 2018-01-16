/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.entities;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "DocumentoRolPersonaEntity")
@Table(name = "SIGEBI_OAF.SGB_DOCUMENTO_ROL_PERSONA")
public class DocumentoRolPersonaEntity extends ObjetoBase implements Serializable {

    private static final long serialVersionUID = 1L;

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @ManyToOne
    @JoinColumn(name = "ID_DOCUMENTO", referencedColumnName = "ID_DOCUMENTO")
    private DocumentoEntity idDocumento;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL")
    private RolEntity idRol;

    @Id 
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    private UsuarioEntity idUsuarioSeguridad;

    @Column(name = "NUM_UNIDAD_EJEC")
    private Integer numUnidadEjec;
    //</editor-fold>
 
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    
    public DocumentoRolPersonaEntity() {       
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SET's y GET's ">

    public DocumentoEntity getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(DocumentoEntity idDocumento) {
        this.idDocumento = idDocumento;
    }

    public RolEntity getIdRol() {
        return idRol;
    }

    public void setIdRol(RolEntity idRol) {
        this.idRol = idRol;
    }

    public UsuarioEntity getIdUsuarioSeguridad() {
        return idUsuarioSeguridad;
    }

    public void setIdUsuarioSeguridad(UsuarioEntity idUsuarioSeguridad) {
        this.idUsuarioSeguridad = idUsuarioSeguridad;
    }

    public Integer getNumUnidadEjec() {
        return numUnidadEjec;
    }

    public void setNumUnidadEjec(Integer numUnidadEjec) {
        this.numUnidadEjec = numUnidadEjec;
    }
    
    
    //</editor-fold>
     
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    
    @Override
    public int hashCode() {
        int hash = 7;
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
        final DocumentoRolPersonaEntity other = (DocumentoRolPersonaEntity) obj;
        if ((this.idUsuarioSeguridad == null) ? (other.idUsuarioSeguridad != null) : !this.idUsuarioSeguridad.equals(other.idUsuarioSeguridad)) {
            return false;
        }
        if (this.idDocumento != other.idDocumento && (this.idDocumento == null || !this.idDocumento.equals(other.idDocumento))) {
            return false;
        }
        if (this.idRol != other.idRol && (this.idRol == null || !this.idRol.equals(other.idRol))) {
            return false;
        }
        return true;
    }
    //</editor-fold>
}
