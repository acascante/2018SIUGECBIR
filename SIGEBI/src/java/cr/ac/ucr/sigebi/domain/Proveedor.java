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
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "Proveedor")
@Table(name = "SIGEBI_OAF.V_SIGB_PROVEEDOR")
public class Proveedor extends ObjetoBase implements Serializable{
    
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "NUM_EMPRESA")
    private Integer numEmpresa;
    
    @Column(name = "ID_CLASE_PROVEEDOR")
    private String idClaseProveedor;
    
    @Column(name = "ID_PLAZO")
    private Integer idPlazo;
    
    @Column(name = "ESTADO")
    private String estado;
    
    @Column(name = "FEC_CAMBIO_ESTADO")
    private String fecCambioEstado;
    
    @Column(name = "COMENTARIOS")
    private String comentarios;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumEmpresa() {
        return numEmpresa;
    }

    public void setNumEmpresa(Integer numEmpresa) {
        this.numEmpresa = numEmpresa;
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

    public String getFecCambioEstado() {
        return fecCambioEstado;
    }

    public void setFecCambioEstado(String fecCambioEstado) {
        this.fecCambioEstado = fecCambioEstado;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 59 * hash + (this.numEmpresa != null ? this.numEmpresa.hashCode() : 0);
        hash = 59 * hash + (this.idClaseProveedor != null ? this.idClaseProveedor.hashCode() : 0);
        hash = 59 * hash + (this.idPlazo != null ? this.idPlazo.hashCode() : 0);
        hash = 59 * hash + (this.estado != null ? this.estado.hashCode() : 0);
        hash = 59 * hash + (this.fecCambioEstado != null ? this.fecCambioEstado.hashCode() : 0);
        hash = 59 * hash + (this.comentarios != null ? this.comentarios.hashCode() : 0);
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
        final Proveedor other = (Proveedor) obj;
        if ((this.idClaseProveedor == null) ? (other.idClaseProveedor != null) : !this.idClaseProveedor.equals(other.idClaseProveedor)) {
            return false;
        }
        if ((this.estado == null) ? (other.estado != null) : !this.estado.equals(other.estado)) {
            return false;
        }
        if ((this.fecCambioEstado == null) ? (other.fecCambioEstado != null) : !this.fecCambioEstado.equals(other.fecCambioEstado)) {
            return false;
        }
        if ((this.comentarios == null) ? (other.comentarios != null) : !this.comentarios.equals(other.comentarios)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.numEmpresa != other.numEmpresa && (this.numEmpresa == null || !this.numEmpresa.equals(other.numEmpresa))) {
            return false;
        }
        if (this.idPlazo != other.idPlazo && (this.idPlazo == null || !this.idPlazo.equals(other.idPlazo))) {
            return false;
        }
        return true;
    }
    
}
