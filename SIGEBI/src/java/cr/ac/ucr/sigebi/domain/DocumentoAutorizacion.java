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
@Entity(name = "DocumentoAutorizacion")
@Table(name = "SIGEBI_OAF.SIGB_DOCUMENTO_AUTORIZACION")
@SequenceGenerator(name = "SGB_SQ_DOC_AUTORIZACION", sequenceName = "SIGEBI_OAF.SGB_SQ_DOC_AUTORIZACION", initialValue = 1, allocationSize = 1)
public class DocumentoAutorizacion extends ObjetoBase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_DOC_AUTORIZACION")
    @Column(name = "ID_DOCUMENTO_AUTORIZACION")
    private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name = "ID_DOCUMENTO", referencedColumnName = "ID_DOCUMENTO")
    private Documento idDocumento;

    @Id
    @ManyToOne
    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL")
    private Rol idRol;

    @Id
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

    public Documento getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Documento idDocumento) {
        this.idDocumento = idDocumento;
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
        hash = 29 * hash + (this.idDocumento != null ? this.idDocumento.hashCode() : 0);
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
        final DocumentoAutorizacion other = (DocumentoAutorizacion) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.idDocumento != other.idDocumento && (this.idDocumento == null || !this.idDocumento.equals(other.idDocumento))) {
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
