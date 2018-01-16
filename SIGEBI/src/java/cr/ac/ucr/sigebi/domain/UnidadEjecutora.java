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
@Entity(name = "UnidadEjecutora")
@Table(name = "SIGEBI_OAF.V_SIGB_UNIDAD_EJECUTORA")
public class UnidadEjecutora  extends ObjetoBase implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID") 
    private Long id;

    @Column(name = "DSC_UNIDAD_EJECUTORA")  
    private String descripcion;
    
    @Column(name = "ID_EMPRESA")
    private Integer idEmpresa;

    @Column(name = "NUM_UNIDAD_EJEC_SIAF") 
    private Character nuUnidEjecSIAF;
    
    @Column(name = "COD_REFERENCIA") 
    private Integer codReferencia;
     
    @Column(name = "ID_TIPO_UNIDAD")
    private String idTipoUnidad;
    
    @Column(name = "ID_UNIDAD_EJECUTORA_PADRE") 
    private Long idUnidadPadre;

    @Column(name = "IND_HABILITADO")
    private String indHabilitado;

    @Column(name = "ABREVIATURA")
    private String abreviatura;
 
    @Column(name = "ORDEN")
    private Integer orden;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Character getNuUnidEjecSIAF() {
        return nuUnidEjecSIAF;
    }

    public void setNuUnidEjecSIAF(Character nuUnidEjecSIAF) {
        this.nuUnidEjecSIAF = nuUnidEjecSIAF;
    }

    public Integer getCodReferencia() {
        return codReferencia;
    }

    public void setCodReferencia(Integer codReferencia) {
        this.codReferencia = codReferencia;
    }

    public String getIdTipoUnidad() {
        return idTipoUnidad;
    }

    public void setIdTipoUnidad(String idTipoUnidad) {
        this.idTipoUnidad = idTipoUnidad;
    }

    public Long getIdUnidadPadre() {
        return idUnidadPadre;
    }

    public void setIdUnidadPadre(Long idUnidadPadre) {
        this.idUnidadPadre = idUnidadPadre;
    }

    public String getIndHabilitado() {
        return indHabilitado;
    }

    public void setIndHabilitado(String indHabilitado) {
        this.indHabilitado = indHabilitado;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 67 * hash + (this.descripcion != null ? this.descripcion.hashCode() : 0);
        hash = 67 * hash + (this.idEmpresa != null ? this.idEmpresa.hashCode() : 0);
        hash = 67 * hash + (this.nuUnidEjecSIAF != null ? this.nuUnidEjecSIAF.hashCode() : 0);
        hash = 67 * hash + (this.codReferencia != null ? this.codReferencia.hashCode() : 0);
        hash = 67 * hash + (this.idTipoUnidad != null ? this.idTipoUnidad.hashCode() : 0);
        hash = 67 * hash + (this.idUnidadPadre != null ? this.idUnidadPadre.hashCode() : 0);
        hash = 67 * hash + (this.indHabilitado != null ? this.indHabilitado.hashCode() : 0);
        hash = 67 * hash + (this.abreviatura != null ? this.abreviatura.hashCode() : 0);
        hash = 67 * hash + (this.orden != null ? this.orden.hashCode() : 0);
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
        final UnidadEjecutora other = (UnidadEjecutora) obj;
        if ((this.descripcion == null) ? (other.descripcion != null) : !this.descripcion.equals(other.descripcion)) {
            return false;
        }
        if ((this.codReferencia == null) ? (other.codReferencia != null) : !this.codReferencia.equals(other.codReferencia)) {
            return false;
        }
        if ((this.idTipoUnidad == null) ? (other.idTipoUnidad != null) : !this.idTipoUnidad.equals(other.idTipoUnidad)) {
            return false;
        }
        if ((this.indHabilitado == null) ? (other.indHabilitado != null) : !this.indHabilitado.equals(other.indHabilitado)) {
            return false;
        }
        if ((this.abreviatura == null) ? (other.abreviatura != null) : !this.abreviatura.equals(other.abreviatura)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.idEmpresa != other.idEmpresa && (this.idEmpresa == null || !this.idEmpresa.equals(other.idEmpresa))) {
            return false;
        }
        if (this.nuUnidEjecSIAF != other.nuUnidEjecSIAF && (this.nuUnidEjecSIAF == null || !this.nuUnidEjecSIAF.equals(other.nuUnidEjecSIAF))) {
            return false;
        }
        if (this.idUnidadPadre != other.idUnidadPadre && (this.idUnidadPadre == null || !this.idUnidadPadre.equals(other.idUnidadPadre))) {
            return false;
        }
        if (this.orden != other.orden && (this.orden == null || !this.orden.equals(other.orden))) {
            return false;
        }
        return true;
    }
    
}
