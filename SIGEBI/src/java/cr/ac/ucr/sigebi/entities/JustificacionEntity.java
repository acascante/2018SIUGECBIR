/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.entities;

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

/**
 *
 * @author jorge.serrano
 */
@Entity(name = "JustificacionEntity")
@Table(name = "SIGEBI_OAF.SGB_JUSTIFICACION")
@SequenceGenerator(name="SGB_SQ_JUSTIFICACIONES", sequenceName = "SIGEBI_OAF.SGB_SQ_JUSTIFICACIONES", initialValue=1, allocationSize=1)
public class JustificacionEntity  extends ObjetoBase implements Serializable  {
    
    private static final long serialVersionUID = 1L;
    
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SGB_SQ_JUSTIFICACIONES")
    @Column(name = "ID_JUSTIFICACION") // NUMBER SGB_SQ_TRASLADO
    private Long idJustificacion;
    
    @Column(name = "TIPO_DOCUMENTO") // VARCHAR2 (500 Byte) 
    private String documentoTipo;
    
    @Column(name = "ID_DOCUMENTO") 
    private Long idDocumento;
    
    @Column(name = "TITULO") 
    private String encabezado;
    
    //@Column(name = "REFERECIA_BIEN") 
    
    @ManyToOne
    @JoinColumn(name = "REFERECIA_BIEN", referencedColumnName = "ID_BIEN")
    private ViewBienEntity idBien;
    
    @Column(name = "OBSERVACIONES") // VARCHAR2 (500 Byte) 
    private String observaciones;
    
    @ManyToOne
    @JoinColumn(name = "USUARIO_REGISTRA", referencedColumnName = "ID_USUARIO")
    private UsuarioEntity usuarioRegistra;
    
    @Column(name = "FECHA") // DATE 
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    

    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Contructores">
    
    
    public JustificacionEntity(Long idJustificacion
                            , String documentoTipo
                            , Long idDocumento
                            , String encabezado
                            , ViewBienEntity idBien
                            , String observaciones
                            , UsuarioEntity usuarioRegistra
                            , Date fecha) {    
        this.idJustificacion = idJustificacion;
        this.documentoTipo = documentoTipo;
        this.idDocumento = idDocumento;
        this.encabezado = encabezado;
        this.idBien = idBien;
        this.observaciones = observaciones;
        this.usuarioRegistra = usuarioRegistra;
        this.fecha = fecha;
    }

    
    public JustificacionEntity(){
        fecha = new Date();
    }
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Sets & Gets">
    public Long getIdJustificacion() {
        return idJustificacion;
    }

    public void setIdJustificacion(Long idJustificacion) {
        this.idJustificacion = idJustificacion;
    }

    public String getDocumentoTipo() {
        return documentoTipo;
    }

    public void setDocumentoTipo(String documentoTipo) {
        this.documentoTipo = documentoTipo;
    }

    public Long getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Long idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getEncabezado() {
        return encabezado;
    }

    public void setEncabezado(String encabezado) {
        this.encabezado = encabezado;
    }

    public ViewBienEntity getIdBien() {
        return idBien;
    }

    public void setIdBien(ViewBienEntity idBien) {
        this.idBien = idBien;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public UsuarioEntity getUsuarioRegistra() {
        return usuarioRegistra;
    }

    public void setUsuarioRegistra(UsuarioEntity usuarioRegistra) {
        this.usuarioRegistra = usuarioRegistra;
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
        int hash = 0;
        hash += (idJustificacion != null ? idJustificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JustificacionEntity)) {
            return false;
        }
        JustificacionEntity other = (JustificacionEntity) object;
        if ((this.idJustificacion == null && other.idJustificacion != null) || (this.idJustificacion != null && !this.idJustificacion.equals(other.idJustificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.ucr.sigebi.entities.Justificacion[ id=" + idJustificacion + " ]";
    }
    //</editor-fold>
    
    
}
