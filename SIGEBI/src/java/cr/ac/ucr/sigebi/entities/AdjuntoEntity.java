/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.entities;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Tipo;
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
@Entity(name = "AdjuntoEntity")
@Table(name = "SIGEBI_OAF.SGB_ADJUNTO")
@SequenceGenerator(name = "SGB_SQ_ADJUNTO",  sequenceName = "SIGEBI_OAF.SGB_SQ_ADJUNTO", initialValue = 1, allocationSize = 1)
public class AdjuntoEntity  extends ObjetoBase implements Serializable{
    
    private static final long serialVersionUID = 1L;

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_ADJUNTO")
    @Column(name = "ID_ADJUNTO")
    private Long idAdjunto;
    
    @ManyToOne
    @JoinColumn(name = "ID_TIPO", referencedColumnName = "ID_TIPO")
    private Tipo idTipo;
    
    @ManyToOne
    @JoinColumn(name = "DOCUMENTO", referencedColumnName = "ID_TIPO")
    private Tipo idTipoDocumento;
    
    @Column(name = "REFERENCIA")
    private Long idReferencia;

    @Column(name = "DETALLE")
    private String detalle;
    
    @Column(name = "URL")
    private String url;
    
    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado idEstado;
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    
    public AdjuntoEntity() {
    }

    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Sets y Gets">

    public Long getIdAdjunto() {
        return idAdjunto;
    }

    public void setIdAdjunto(Long idAdjunto) {
        this.idAdjunto = idAdjunto;
    }

    public Tipo getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Tipo idTipo) {
        this.idTipo = idTipo;
    }

    public Tipo getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(Tipo idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public Long getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(Long idReferencia) {
        this.idReferencia = idReferencia;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
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
        final AdjuntoEntity other = (AdjuntoEntity) obj;
        if ((this.detalle == null) ? (other.detalle != null) : !this.detalle.equals(other.detalle)) {
            return false;
        }
        if ((this.url == null) ? (other.url != null) : !this.url.equals(other.url)) {
            return false;
        }
        if (this.idAdjunto != other.idAdjunto && (this.idAdjunto == null || !this.idAdjunto.equals(other.idAdjunto))) {
            return false;
        }
        if (this.idTipo != other.idTipo && (this.idTipo == null || !this.idTipo.equals(other.idTipo))) {
            return false;
        }
        if (this.idTipoDocumento != other.idTipoDocumento && (this.idTipoDocumento == null || !this.idTipoDocumento.equals(other.idTipoDocumento))) {
            return false;
        }
        if (this.idReferencia != other.idReferencia && (this.idReferencia == null || !this.idReferencia.equals(other.idReferencia))) {
            return false;
        }
        if (this.idEstado != other.idEstado && (this.idEstado == null || !this.idEstado.equals(other.idEstado))) {
            return false;
        }
        return true;
    }
    
    //</editor-fold>

}
