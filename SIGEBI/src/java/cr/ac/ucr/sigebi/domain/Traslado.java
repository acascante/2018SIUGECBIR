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
@Entity(name = "Traslado")
@Table(name = "SIGEBI_OAF.SGB_TRASLADO")
@SequenceGenerator(name="SGB_SQ_TRASLADO", sequenceName = "SIGEBI_OAF.SGB_SQ_TRASLADO", initialValue=1, allocationSize=1)
public class Traslado  extends ObjetoBase implements Serializable  {
    
    private static final long serialVersionUID = 1L;
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SGB_SQ_TRASLADO")
    @Column(name = "ID_TRASLADO") // NUMBER SGB_SQ_TRASLADO
    private Integer idTraslado;
    
    @Column(name = "FECHA") // DATE 
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    
    @ManyToOne
    @JoinColumn(name = "NUM_UNIDAD_EJEC", referencedColumnName = "ID")
    private UnidadEjecutora numUnidadOrigen;
    
            
    @ManyToOne
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_USUARIO")
    private Usuario idPersona;
    

    @ManyToOne
    @JoinColumn(name = "NUM_UNIDAD_EJEC_RECIBE", referencedColumnName = "ID")
    private UnidadEjecutora numUnidadDestino;
    
    @ManyToOne
    @JoinColumn(name = "ID_PERSONA_RECIBE", referencedColumnName = "ID_USUARIO")
    private Usuario idPersonaRecibe;
    
    @ManyToOne
    @JoinColumn(name = "ID_UBICACION", referencedColumnName = "ID_UBICACION")
    private Ubicacion idUbicacion;
    
    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private  Estado idEstado;
    
    @Column(name = "OBSERVACIONES") // VARCHAR2 (500 Byte) 
    private String observaciones;

    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Sets & Gets">
    public Integer getIdTraslado() {
        return idTraslado;
    }

    public void setIdTraslado(Integer idTraslado) {
        this.idTraslado = idTraslado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public UnidadEjecutora getNumUnidadOrigen() {
        return numUnidadOrigen;
    }

    public void setNumUnidadOrigen(UnidadEjecutora numUnidadOrigen) {
        this.numUnidadOrigen = numUnidadOrigen;
    }

    public Usuario getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Usuario idPersona) {
        this.idPersona = idPersona;
    }

    public UnidadEjecutora getNumUnidadDestino() {
        return numUnidadDestino;
    }

    public void setNumUnidadDestino(UnidadEjecutora numUnidadDestino) {
        this.numUnidadDestino = numUnidadDestino;
    }

    public Usuario getIdPersonaRecibe() {
        return idPersonaRecibe;
    }

    public void setIdPersonaRecibe(Usuario idPersonaRecibe) {
        this.idPersonaRecibe = idPersonaRecibe;
    }

    public Ubicacion getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Ubicacion idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public  Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado( Estado idEstado) {
        this.idEstado = idEstado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">

    public Traslado() {
        fecha = new Date();
    }

    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTraslado != null ? idTraslado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Traslado)) {
            return false;
        }
        Traslado other = (Traslado) object;
        if ((this.idTraslado == null && other.idTraslado != null) || (this.idTraslado != null && !this.idTraslado.equals(other.idTraslado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.ucr.sigebi.entities.Traslado[ id=" + idTraslado + " ]";
    }
    //</editor-fold>
    
    
}
