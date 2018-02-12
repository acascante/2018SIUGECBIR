/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

    @ManyToOne
    @JoinColumn(name = "ID_DOCUMENTO", referencedColumnName = "ID_DOCUMENTO")
    private Documento documento;

    @ManyToOne
    @JoinColumn(name = "ID_AUTORIZACION_ROL", referencedColumnName = "ID_AUTORIZACION_ROL")
    private AutorizacionRol autorizacionRol;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    private Usuario usuarioSeguridad;

    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;

    @Column(name = "FECHA_HORA")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    
    public DocumentoAutorizacion() {
    
    }

    public DocumentoAutorizacion(Documento documento
            , AutorizacionRol autorizacionRol
            , Usuario usuarioSeguridad
            , Estado estado) {
        this.documento = documento;
        this.autorizacionRol = autorizacionRol;
        this.usuarioSeguridad = usuarioSeguridad;
        this.estado = estado;
    }    
    
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 97 * hash + (this.documento != null ? this.documento.hashCode() : 0);
        hash = 97 * hash + (this.autorizacionRol != null ? this.autorizacionRol.hashCode() : 0);
        hash = 97 * hash + (this.usuarioSeguridad != null ? this.usuarioSeguridad.hashCode() : 0);
        hash = 97 * hash + (this.estado != null ? this.estado.hashCode() : 0);
        hash = 97 * hash + (this.fecha != null ? this.fecha.hashCode() : 0);
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
        if (this.autorizacionRol != other.autorizacionRol && (this.autorizacionRol == null || !this.autorizacionRol.equals(other.autorizacionRol))) {
            return false;
        }
        if (this.usuarioSeguridad != other.usuarioSeguridad && (this.usuarioSeguridad == null || !this.usuarioSeguridad.equals(other.usuarioSeguridad))) {
            return false;
        }
        if (this.estado != other.estado && (this.estado == null || !this.estado.equals(other.estado))) {
            return false;
        }
        if (this.fecha != other.fecha && (this.fecha == null || !this.fecha.equals(other.fecha))) {
            return false;
        }
        return true;
    }

    //</editor-fold>
}
