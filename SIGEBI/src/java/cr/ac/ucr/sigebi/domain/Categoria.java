/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author jorge.serrano
 */
@Entity(name = "Categoria")
@Table(name = "SIGEBI_OAF.V_SIGB_CATEGORIA")
public class Categoria extends ObjetoBase implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODIGO_CATEGORIA")
    private String codigoCategoria;

    @Column(name = "NUMERO_EMPRESA")
    private Integer numeroEmpresa;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "FECHA_ULTIMO_PROCESO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaUltimoProceso;

    @Column(name = "OBSERVACIONES")
    private String observaciones;

    @Transient
    private List<SubCategoria> subCategorias;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructores">

    public Categoria() {}

    public Categoria(Long id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="STE's & GET's">
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaUltimoProceso() {
        return fechaUltimoProceso;
    }

    public void setFechaUltimoProceso(Date fechaUltimoProceso) {
        this.fechaUltimoProceso = fechaUltimoProceso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<SubCategoria> getSubCategorias() {
        return subCategorias;
    }

    public void setSubCategorias(List<SubCategoria> subCategorias) {
        this.subCategorias = subCategorias;
    }

    public String getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(String codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    //</editor-fold>  
    
    //<editor-fold defaultstate="collapsed" desc="Hash Equals">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 59 * hash + (this.numeroEmpresa != null ? this.numeroEmpresa.hashCode() : 0);
        hash = 59 * hash + (this.descripcion != null ? this.descripcion.hashCode() : 0);
        hash = 59 * hash + (this.fechaUltimoProceso != null ? this.fechaUltimoProceso.hashCode() : 0);
        hash = 59 * hash + (this.observaciones != null ? this.observaciones.hashCode() : 0);
        hash = 59 * hash + (this.subCategorias != null ? this.subCategorias.hashCode() : 0);
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
        final Categoria other = (Categoria) obj;
        if ((this.descripcion == null) ? (other.descripcion != null) : !this.descripcion.equals(other.descripcion)) {
            return false;
        }
        if ((this.observaciones == null) ? (other.observaciones != null) : !this.observaciones.equals(other.observaciones)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.numeroEmpresa != other.numeroEmpresa && (this.numeroEmpresa == null || !this.numeroEmpresa.equals(other.numeroEmpresa))) {
            return false;
        }
        if (this.fechaUltimoProceso != other.fechaUltimoProceso && (this.fechaUltimoProceso == null || !this.fechaUltimoProceso.equals(other.fechaUltimoProceso))) {
            return false;
        }
        if (this.subCategorias != other.subCategorias && (this.subCategorias == null || !this.subCategorias.equals(other.subCategorias))) {
            return false;
        }
        return true;
    }
//</editor-fold>
}
