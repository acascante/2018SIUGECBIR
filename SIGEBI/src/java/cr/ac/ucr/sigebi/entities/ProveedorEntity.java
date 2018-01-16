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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author jorge.serrano
 */
@Entity(name = "ProveedorEntity")
@Table(name = "SF_PROVEEDOR")
@NamedQueries({ 
    @NamedQuery(name = "ProveedorEntity.findAll", query =  "SELECT s FROM ProveedorEntity s" ),
    @NamedQuery(name = "ProveedorEntity.findById", query = "SELECT s FROM ProveedorEntity s WHERE s.idProveedor.numPersona = :ID_PROVEEDOR")
    
}
)
public class ProveedorEntity extends ObjetoBase implements Serializable  {
    
    private static final long serialVersionUID = 1L;
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    @Id
    @Basic(optional = false)
    @Column(name = "NUM_EMPRESA")
    private Integer numEmpresa;
    
    @JoinColumn(name = "ID_PROVEEDOR", referencedColumnName = "NUM_PERSONA")
    @ManyToOne(fetch = FetchType.EAGER)
    private PersonaEntity idProveedor;
    
    @Column(name = "ID_CLASE_PROVEEDOR")
    private String idClaseProveedor;
    
    @Column(name = "ID_PLAZO")
    private Integer idPlazo;
    
    @Column(name = "ESTADO")
    private String estado;
    
    @Column(name = "FEC_CAMBIO_ESTADO")
    private Integer fecCambioEstado;
    
    @Column(name = "COMENTARIOS")
    private Integer comentarios;
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    
    public ProveedorEntity() {
    }

    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="SET's y GET's ">

    public Integer getNumEmpresa() {
        return numEmpresa;
    }

    public void setNumEmpresa(Integer numEmpresa) {
        this.numEmpresa = numEmpresa;
    }

    public PersonaEntity getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(PersonaEntity idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getIdClaseProveedor() {
        return idClaseProveedor;
    }

    public void setIdClaseProveedor(String idClaseProveedor) {
        this.idClaseProveedor = idClaseProveedor;
    }

    public Integer getIdPlazo() {
        return idPlazo;
    }

    public void setIdPlazo(Integer idPlazo) {
        this.idPlazo = idPlazo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getFecCambioEstado() {
        return fecCambioEstado;
    }

    public void setFecCambioEstado(Integer fecCambioEstado) {
        this.fecCambioEstado = fecCambioEstado;
    }

    public Integer getComentarios() {
        return comentarios;
    }

    public void setComentarios(Integer comentarios) {
        this.comentarios = comentarios;
    }
    
    
    
    
    
    //</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProveedor != null ? idProveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProveedorEntity)) {
            return false;
        }
        ProveedorEntity other = (ProveedorEntity) object;
        if ((this.idProveedor == null && other.idProveedor != null) || (this.idProveedor != null && !this.idProveedor.equals(other.idProveedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.ProveedorEntity[idProveedor=" + idProveedor + "]";
    }
    
    //</editor-fold>
    
    
}
