/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.entities;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import cr.ac.ucr.sigebi.domain.Estado;
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
@Entity(name = "DocumentoRolEstadoEntity")
@Table(name = "SIGEBI_OAF.SGB_DOCUMENTO_ROL_ESTADO")
@SequenceGenerator(name = "SGB_SQ_DOCUMENTO_ROL_EST",  sequenceName = "SIGEBI_OAF.SGB_SQ_DOCUMENTO_ROL_EST", initialValue = 1, allocationSize = 1)
public class DocumentoRolEstadoEntity extends ObjetoBase implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_DOCUMENTO_ROL_EST")
    @Column(name = "ID_DOCUMENTO_ROL_ESTADO")
    private Long idDocumentoRolEstado;

    //Id o consecutivo del documento que se está consultando
    @Column(name = "ID_REFERENCIA")
    private Long idReferencia;
    
    @ManyToOne
    @JoinColumn(name = "ID_DOCUMENTO", referencedColumnName = "ID_DOCUMENTO")
    private DocumentoEntity idDocumento;
    
    @ManyToOne
    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL")
    private RolEntity idRol;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    private UsuarioEntity idUsuarioSeguridad;
            
    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado idEstado;

    @Column(name = "FECHA_HORA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
       
    //</editor-fold>
 
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    
    public DocumentoRolEstadoEntity() {       
    }
    
    public DocumentoRolEstadoEntity(Long idReferencia, DocumentoEntity documentoEntity, RolEntity rolEntity, Estado estado ) {  
        this.idReferencia = idReferencia;
        this.idDocumento = documentoEntity;
        this.idRol = rolEntity;
        this.idEstado = estado;
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SET's y GET's ">

    public Long getIdDocumentoRolEstado() {
        return idDocumentoRolEstado;
    }

    public void setIdDocumentoRolEstado(Long idDocumentoRolEstado) {
        this.idDocumentoRolEstado = idDocumentoRolEstado;
    }

    public Long getIdReferncia() {
        return idReferencia;
    }

    public void setIdReferencia(Long idReferencia) {
        this.idReferencia = idReferencia;
    }

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

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
        final DocumentoRolEstadoEntity other = (DocumentoRolEstadoEntity) obj;
        if ((this.idUsuarioSeguridad == null) ? (other.idUsuarioSeguridad != null) : !this.idUsuarioSeguridad.equals(other.idUsuarioSeguridad)) {
            return false;
        }
        if (this.idDocumentoRolEstado != other.idDocumentoRolEstado && (this.idDocumentoRolEstado == null || !this.idDocumentoRolEstado.equals(other.idDocumentoRolEstado))) {
            return false;
        }
        if (this.idReferencia != other.idReferencia && (this.idReferencia == null || !this.idReferencia.equals(other.idReferencia))) {
            return false;
        }
        if (this.idDocumento != other.idDocumento && (this.idDocumento == null || !this.idDocumento.equals(other.idDocumento))) {
            return false;
        }
        if (this.idRol != other.idRol && (this.idRol == null || !this.idRol.equals(other.idRol))) {
            return false;
        }
        if (this.idEstado != other.idEstado && (this.idEstado == null || !this.idEstado.equals(other.idEstado))) {
            return false;
        }
        if (this.fecha != other.fecha && (this.fecha == null || !this.fecha.equals(other.fecha))) {
            return false;
        }
        return true;
    }
    
    
    //</editor-fold>


}
