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

/**
 *
 * @author jorge.serrano
 */
@Entity(name = "Justificacion")
@Table(name = "SIGEBI_OAF.SGB_JUSTIFICACION")
@SequenceGenerator(name="SGB_SQ_JUSTIFICACIONES", sequenceName = "SIGEBI_OAF.SGB_SQ_JUSTIFICACIONES", initialValue=1, allocationSize=1)
public class Justificacion  extends ObjetoBase implements Serializable  {
    
    private static final long serialVersionUID = 1L;
    
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SGB_SQ_JUSTIFICACIONES")
    @Column(name = "ID_JUSTIFICACION") // NUMBER SGB_SQ_TRASLADO
    private Long id;
    
    @Column(name = "TIPO_DOCUMENTO") // VARCHAR2 (500 Byte) 
    private String documentoTipo;
    
    @Column(name = "ID_DOCUMENTO") 
    private Long idDocumento;
    
    @Column(name = "TITULO") 
    private String encabezado;
    
    //@Column(name = "REFERECIA_BIEN") 
    
    @ManyToOne
    @JoinColumn(name = "REFERECIA_BIEN", referencedColumnName = "ID_BIEN")
    private Bien bien;
    
    @Column(name = "OBSERVACIONES") // VARCHAR2 (500 Byte) 
    private String observaciones;
    
    @ManyToOne
    @JoinColumn(name = "USUARIO_REGISTRA", referencedColumnName = "ID_USUARIO")
    private Usuario usuarioRegistra;
    
    @Column(name = "FECHA") // DATE 
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    

    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Contructores">
    
    
    public Justificacion(Long id
                            , String documentoTipo
                            , Long idDocumento
                            , String encabezado
                            , Bien bien
                            , String observaciones
                            , Usuario usuarioRegistra
                            , Date fecha) {    
        this.id = id;
        this.documentoTipo = documentoTipo;
        this.idDocumento = idDocumento;
        this.encabezado = encabezado;
        this.bien = bien;
        this.observaciones = observaciones;
        this.usuarioRegistra = usuarioRegistra;
        this.fecha = fecha;
    }

    
    public Justificacion(){
        fecha = new Date();
    }
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Sets & Gets">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Bien getBien() {
        return bien;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Usuario getUsuarioRegistra() {
        return usuarioRegistra;
    }

    public void setUsuarioRegistra(Usuario usuarioRegistra) {
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Justificacion)) {
            return false;
        }
        Justificacion other = (Justificacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.ucr.sigebi.entities.Justificacion[ id=" + id + " ]";
    }
    //</editor-fold>
    
    
}
