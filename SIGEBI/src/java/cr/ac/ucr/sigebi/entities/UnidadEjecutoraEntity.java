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
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author jorge.serrano
 */
@Entity(name = "UnidadEjecutoraEntity")
@Table(name = "SEGURIDAD_UNIDAD_EJECUTORA")
public class UnidadEjecutoraEntity  extends ObjetoBase implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    @Id
    @Basic(optional = false)
    @Column(name = "ID_UNIDAD_EJECUTORA") // NUMBER 
    private Integer idUnidadEjec;
    @Column(name = "DSC_UNIDAD_EJECUTORA") // VARCHAR 
    private String dscUnidadEjecutora;
    
    @Id
    @Basic(optional = false)
    @Column(name = "ID_EMPRESA") // NUMBER 
    private Integer idEmpresa;
    @Column(name = "ID_TIPO_UNIDAD") // VARCHAR 
    private String idTipoUnidad;
    @Column(name = "IND_HABILITADO") // VARCHAR 
    private String indHabilitado;
    @Column(name = "NUM_UNIDAD_EJEC_SIAF") // CHAR 
    private Character nuUnidEjecSIAF;
    @Column(name = "COD_REFERENCIA") // VARCHAR 
    private String codReferencia;
    @Column(name = "ID_UNIDAD_EJECUTORA_PADRE") // NUMBER 
    private Integer idUnidadPadre;
    @Column(name = "ABREVIATURA") // VARCHAR 
    private String abreviatura;
    @Column(name = "ORDEN") // NUMBER 
    private Integer orden;

    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Contructor">
    
    
    public UnidadEjecutoraEntity() {
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">

    public Integer getIdUnidadEjec() {
        return idUnidadEjec;
    }

    public void setIdUnidadEjec(Integer idUnidadEjec) {
        this.idUnidadEjec = idUnidadEjec;
    }

    public String getDscUnidadEjecutora() {
        return dscUnidadEjecutora;
    }

    public void setDscUnidadEjecutora(String dscUnidadEjecutora) {
        this.dscUnidadEjecutora = dscUnidadEjecutora;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getIdTipoUnidad() {
        return idTipoUnidad;
    }

    public void setIdTipoUnidad(String idTipoUnidad) {
        this.idTipoUnidad = idTipoUnidad;
    }

    public String getIndHabilitado() {
        return indHabilitado;
    }

    public void setIndHabilitado(String indHabilitado) {
        this.indHabilitado = indHabilitado;
    }

    public Character  getNuUnidEjecSIAF() {
        return nuUnidEjecSIAF;
    }

    public void setNuUnidEjecSIAF(Character  nuUnidEjecSIAF) {
        this.nuUnidEjecSIAF = nuUnidEjecSIAF;
    }

    public String getCodReferencia() {
        return codReferencia;
    }

    public void setCodReferencia(String codReferencia) {
        this.codReferencia = codReferencia;
    }

    public Integer getIdUnidadPadre() {
        return idUnidadPadre;
    }

    public void setIdUnidadPadre(Integer idUnidadPadre) {
        this.idUnidadPadre = idUnidadPadre;
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
    
    
    
    
    
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUnidadEjec != null ? idUnidadEjec.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UnidadEjecutoraEntity)) {
            return false;
        }
        UnidadEjecutoraEntity other = (UnidadEjecutoraEntity) object;
        if ((this.idUnidadEjec == null && other.idUnidadEjec != null) || (this.idUnidadEjec != null && !this.idUnidadEjec.equals(other.idUnidadEjec))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.tipoEntity[idTipo=" + idUnidadEjec + "]";
    }
    
    //</editor-fold>
    
}
