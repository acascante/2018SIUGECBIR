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
 * @author jorge.serrano
 */
@Entity(name = "Adjunto")
@Table(name = "SIGEBI_OAF.SIGB_ADJUNTO")
@SequenceGenerator(name = "SGB_SQ_ADJUNTO",  sequenceName = "SIGEBI_OAF.SGB_SQ_ADJUNTO", initialValue = 1, allocationSize = 1)
public class Adjunto  extends ObjetoBase implements Serializable{
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_ADJUNTO")
    @Column(name = "ID_ADJUNTO")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "ID_TIPO", referencedColumnName = "ID_TIPO")
    private Tipo tipo;
    
    //usamos esta como el ID_BIEN
    @Column(name = "ID_DOCUMENTO")
    private Long idDocumento;
    
    @Column(name = "DETALLE")
    private String detalle;
    
    @Column(name = "URL")
    private String url;
    
    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;
    
    @Column(name = "TIPO_MIME")
    private String tipoMime;

    @Column(name = "TAMANNO")
    private float tamano;
    
    @Column(name = "EXTENSION")
    private String extension;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "REFERENCIA")
    private Long idReferencia;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public Adjunto() {}
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sets y Gets">

    //</editor-fold>
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Long getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Long idDocumento) {
        this.idDocumento = idDocumento;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

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

    public Long getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(Long idReferencia) {
        this.idReferencia = idReferencia;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Adjunto)) {
            return false;
        }
        Adjunto other = (Adjunto) object;
        if ( (this.id == null && other.id != null) 
         || (this.id != null && !this.id.equals(other.id) )
                ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return " proveedores.Adjunto[idAdjunto = " + id + "] ";
    }
    //</editor-fold>
}