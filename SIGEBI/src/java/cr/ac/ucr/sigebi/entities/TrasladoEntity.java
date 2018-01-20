/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.entities;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author jorge.serrano
 */
@Entity(name = "TrasladoEntity")
@Table(name = "SIGEBI_OAF.SGB_TRASLADO")
@SequenceGenerator(name="SGB_SQ_TRASLADO", sequenceName = "SIGEBI_OAF.SGB_SQ_TRASLADO", initialValue=1, allocationSize=1)
public class TrasladoEntity  extends ObjetoBase implements Serializable  {
    
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
    @JoinColumns ({
        @JoinColumn(name="NUM_UNIDAD_EJEC", referencedColumnName = "ID"),
        @JoinColumn(name="EMPRESA_ORIGEN", referencedColumnName = "ID_EMPRESA")
    })
    private UnidadEjecutora numUnidadOrigen;
//    @Column(name = "NUM_UNIDAD_EJEC") // VARCHAR2 (500 Byte)
//    private Integer numUnidadOrigen;
//    @Column(name = "EMPRESA_ORIGEN") // VARCHAR2 (500 Byte)
//    private Integer empresaDestino;
    
            
    @ManyToOne
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_USUARIO")
    private UsuarioEntity idPersona;
    

    @ManyToOne
    @JoinColumns ({
        @JoinColumn(name="NUM_UNIDAD_EJEC_RECIBE", referencedColumnName = "ID_UNIDAD_EJECUTORA"),
        @JoinColumn(name="EMPRESA_DESTINO", referencedColumnName = "ID_EMPRESA")
    })
    private UnidadEjecutora numUnidadDestino;
//    @Column(name = "NUM_UNIDAD_EJEC_RECIBE") // VARCHAR2 (500 Byte)
//    private Integer numUnidadDestino;
//    @Column(name = "EMPRESA_DESTINO") // VARCHAR2 (500 Byte)
//    private Integer empresaDestino;
//    
    
    @ManyToOne
    @JoinColumn(name = "ID_PERSONA_RECIBE", referencedColumnName = "ID_USUARIO")
    private UsuarioEntity idPersonaRecibe;
    
    @ManyToOne
    @JoinColumn(name = "ID_UBICACION", referencedColumnName = "ID_UBICACION")
    private UbicacionEntity idUbicacion;
    
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

    public UsuarioEntity getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(UsuarioEntity idPersona) {
        this.idPersona = idPersona;
    }

    public UnidadEjecutora getNumUnidadDestino() {
        return numUnidadDestino;
    }

    public void setNumUnidadDestino(UnidadEjecutora numUnidadDestino) {
        this.numUnidadDestino = numUnidadDestino;
    }

    public UsuarioEntity getIdPersonaRecibe() {
        return idPersonaRecibe;
    }

    public void setIdPersonaRecibe(UsuarioEntity idPersonaRecibe) {
        this.idPersonaRecibe = idPersonaRecibe;
    }

    public UbicacionEntity getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(UbicacionEntity idUbicacion) {
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

    public TrasladoEntity() {
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
        if (!(object instanceof TrasladoEntity)) {
            return false;
        }
        TrasladoEntity other = (TrasladoEntity) object;
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
