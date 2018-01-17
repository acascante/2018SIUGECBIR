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
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "NUM_EMPRESA")
    private Integer numeroEmpresa;
    
    @Column(name = "ID_CLASE_PROVEEDOR")
    private String claseProveedor;
    
    @Column(name = "ID_PLAZO")
    private Integer plazo;
    
    @Column(name = "ESTADO")
    private String estado;
    
    @Column(name = "FEC_CAMBIO_ESTADO")
    private String fechaCambioEstado;
    
    @Column(name = "COMENTARIOS")
    private String comentarios;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroEmpresa() {
        return numeroEmpresa;
    }

    public void setNumeroEmpresa(Integer numeroEmpresa) {
        this.numeroEmpresa = numeroEmpresa;
    }

    public String getClaseProveedor() {
        return claseProveedor;
    }

    public void setClaseProveedor(String claseProveedor) {
        this.claseProveedor = claseProveedor;
    }

    public Integer getPlazo() {
        return plazo;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaCambioEstado() {
        return fechaCambioEstado;
    }

    public void setFechaCambioEstado(String fechaCambioEstado) {
        this.fechaCambioEstado = fechaCambioEstado;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    //</editor-fold>

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 59 * hash + (this.numeroEmpresa != null ? this.numeroEmpresa.hashCode() : 0);
        hash = 59 * hash + (this.claseProveedor != null ? this.claseProveedor.hashCode() : 0);
        hash = 59 * hash + (this.plazo != null ? this.plazo.hashCode() : 0);
        hash = 59 * hash + (this.estado != null ? this.estado.hashCode() : 0);
        hash = 59 * hash + (this.fechaCambioEstado != null ? this.fechaCambioEstado.hashCode() : 0);
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
        if ((this.claseProveedor == null) ? (other.claseProveedor != null) : !this.claseProveedor.equals(other.claseProveedor)) {
            return false;
        }
        if ((this.estado == null) ? (other.estado != null) : !this.estado.equals(other.estado)) {
            return false;
        }
        if ((this.fechaCambioEstado == null) ? (other.fechaCambioEstado != null) : !this.fechaCambioEstado.equals(other.fechaCambioEstado)) {
            return false;
        }
        if ((this.comentarios == null) ? (other.comentarios != null) : !this.comentarios.equals(other.comentarios)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.numeroEmpresa != other.numeroEmpresa && (this.numeroEmpresa == null || !this.numeroEmpresa.equals(other.numeroEmpresa))) {
            return false;
        }
        if (this.plazo != other.plazo && (this.plazo == null || !this.plazo.equals(other.plazo))) {
            return false;
        }
        return true;
    }   
}