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

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_DOC_AUTORIZACION")
    @Column(name = "ID_DOCUMENTO_AUTORIZACION")
    private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name = "ID_DOCUMENTO", referencedColumnName = "ID_DOCUMENTO")
    private Documento documento;

    @Id
    @ManyToOne
    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL")
    private Rol rol;

    @Id
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    private Usuario usuarioSeguridad;

    @ManyToOne
    @JoinColumn(name = "ID_UNIDAD_EJECUTORA", referencedColumnName = "ID")
    private UnidadEjecutora unidadEjecutora;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 29 * hash + (this.documento != null ? this.documento.hashCode() : 0);
        hash = 29 * hash + (this.rol != null ? this.rol.hashCode() : 0);
        hash = 29 * hash + (this.usuarioSeguridad != null ? this.usuarioSeguridad.hashCode() : 0);
        hash = 29 * hash + (this.unidadEjecutora != null ? this.unidadEjecutora.hashCode() : 0);
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
        if (this.documento != other.documento && (this.documento == null || !this.documento.equals(other.documento))) {
            return false;
        }
        if (this.rol != other.rol && (this.rol == null || !this.rol.equals(other.rol))) {
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
}