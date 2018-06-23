/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import cr.ac.ucr.framework.vista.util.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author alvaro.cascante
 */
@Entity(name = "Solicitud")
@Table(name = "SIGEBI_OAF.SIGB_SOLICITUD")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "discriminator")
@SequenceGenerator(name = "SGB_SQ_SOLICITUDES", sequenceName = "SIGEBI_OAF.SGB_SQ_SOLICITUDES", initialValue = 1, allocationSize = 1)
public abstract class Solicitud extends ObjetoBase implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_SOLICITUDES")
    @Column(name = "ID_SOLICITUD")
    private Long id;

    @Column(name = "FECHA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;

    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Estado estado;

    @JoinColumn(name = "ID_UNIDAD_EJECUTORA", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private UnidadEjecutora unidadEjecutora;

    @Column(name = "DISCRIMINATOR")
    private Integer discriminator;

    @Column(name = "OBSERVACION") // VARCHAR2 (500 Byte) 
    private String observacion;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    private Usuario usuario;
    
    @Transient
    private List<SolicitudDetalle> detalles;
    
    @Transient
    private String tipoMovimiento;
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public Solicitud() {
    }

    public Solicitud(Integer discriminator, UnidadEjecutora  unidadEjecutora) {
        this.fecha = new Date();
        this.discriminator = discriminator;
        this.unidadEjecutora = unidadEjecutora;
    }
    
    public Solicitud(Integer discriminator, UnidadEjecutora  unidadEjecutora, Estado estado) {
        this.fecha = new Date();
        this.discriminator = discriminator;
        this.unidadEjecutora = unidadEjecutora;
        this.estado = estado;
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public UnidadEjecutora getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(UnidadEjecutora unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }

    public Integer getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(Integer discriminator) {
        this.discriminator = discriminator;
    }

    public List<SolicitudDetalle> getDetalles() {
        if (this.detalles == null) {
            this.detalles = new ArrayList<SolicitudDetalle>();
        }
        return detalles;
    }

    public void setDetalles(List<SolicitudDetalle> detalles) {
        this.detalles = detalles;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getTipoMovimiento() {
        switch (discriminator){
            case 0:
                return Util.getEtiquetas("sigebi.TipoMovimiento.Ingreso");
            case 1:
                return Util.getEtiquetas("sigebi.TipoMovimiento.Exclusion");
            case 2:
                return Util.getEtiquetas("sigebi.TipoMovimiento.SolicitudDonacion");
            case 3:
                return Util.getEtiquetas("sigebi.TipoMovimiento.Traslado");
            case 4:
                return Util.getEtiquetas("sigebi.TipoMovimiento.Prestamo");
            case 5:
                return Util.getEtiquetas("sigebi.TipoMovimiento.Salida");
            case 6:
                return Util.getEtiquetas("sigebi.TipoMovimiento.Mantenimiento");
        }
        return "";
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Solicitud other = (Solicitud) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    

    //</editor-fold>

}
