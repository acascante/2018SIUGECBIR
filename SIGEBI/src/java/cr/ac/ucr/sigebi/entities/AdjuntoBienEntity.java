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
@Entity(name = "AdjuntoBienEntity")
@Table(name = "SIGEBI_OAF.SGB_ADJUNTO_BIEN")
public class AdjuntoBienEntity  extends ObjetoBase implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ADJUNTO_BIEN")
    private Integer idAdjunto;
    
    @Column(name = "ID_TIPO")
    private Integer idTipo;
    
    //usamos esta como el ID_BIEN
    @Column(name = "ID_BIEN")
    private Integer idBien;
    
    @Column(name = "DETALLE")
    private String detalle;
    
    @Column(name = "URL")
    private String url;
    
    @Column(name = "ID_ESTADO")
    private Integer idEstado;
     
    
    @Column(name = "TIPO_MIME")
    private String tipoMime;

    @Column(name = "TAMANNO")
    private float tamano;
    
    @Column(name = "EXTENSION")
    private String extension;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    
    public AdjuntoBienEntity() {
    }



    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Sets y Gets">

    public String getTipoMime() {
        return tipoMime;
    }

    public void setTipoMime(String tipoMime) {
        this.tipoMime = tipoMime;
    }

    public float getTamano() {
        return tamano;
    }

    public void setTamano(float tamano) {
        this.tamano = tamano;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
    
    
    
    
    
    
    public Integer getIdAdjunto() {
        return idAdjunto;
    }

    public void setIdAdjunto(Integer idAdjunto) {
        this.idAdjunto = idAdjunto;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public Integer getIdBien() {
        return idBien;
    }

    public void setIdBien(Integer idBien) {
        this.idBien = idBien;
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

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    
    
    
    
    
    
    //</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAdjunto != null ? idAdjunto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdjuntoBienEntity)) {
            return false;
        }
        AdjuntoBienEntity other = (AdjuntoBienEntity) object;
        if ( (this.idAdjunto == null && other.idAdjunto != null) 
         || (this.idAdjunto != null && !this.idAdjunto.equals(other.idAdjunto) )
                ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return " proveedores.AdjuntoEntity[idAdjunto = " + idAdjunto + "] ";
    }
    
    //</editor-fold>
    
    
    
    
    
    
    
    
}
