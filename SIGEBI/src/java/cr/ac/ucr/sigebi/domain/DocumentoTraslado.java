/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author jorge.serrano
 */
@Entity(name = "DocumentoTraslado")
@Table(name = "SIGEBI_OAF.SIGB_DOCUMENTO_TRASLADO")
@DiscriminatorValue("3")
@PrimaryKeyJoinColumn(name = "ID_DOCUMENTO", referencedColumnName = "ID_DOCUMENTO")
public class DocumentoTraslado  extends Documento implements Serializable  {
    
    private static final long serialVersionUID = 1L;
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    
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
    
    @Column(name = "OBSERVACIONES") // VARCHAR2 (500 Byte) 
    private String observaciones;

    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Sets & Gets">

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


    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">

    public DocumentoTraslado() {
        super();
        this.setFecha(new Date());
    }

    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocumentoTraslado)) {
            return false;
        }
        DocumentoTraslado other = (DocumentoTraslado) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.ucr.sigebi.entities.Traslado[ id=" + getId() + " ]";
    }
    //</editor-fold>
    
    
}
