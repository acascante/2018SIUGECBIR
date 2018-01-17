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
@Entity(name = "SubCategoria")
@Table(name = "SIGEBI_OAF.V_SIGB_SUB_CATEGORIA")
public class SubCategoria extends ObjetoBase implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "NUM_EMPRESA")
    private Integer numeroEmpresa;
    
    @Column(name = "CODIGO_SUB_CATEGORIA")
    private String codigoSubCategoria;
    
    @Column(name = "CODIGO_CATEGORIA")
    private String codigoCategoria;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "CTA_CAPITALIZACION_CAT")
    private String cuentaCapitalizacion;
    
    @Column(name = "CTA_PATRIMONIO_COMPRAS_CAT")
    private String cuentaPatrimonioCompras;
    
    @Column(name = "CTA_PATRIMONIO_DONACIONES_CAT")
    private String cuentaPatrimonioDonaciones;
    
    @Column(name = "CTA_DEPRECIACION_CAT")
    private String cuentaDepreciacion;
    
    @Column(name = "CTA_REVALUACION_CAT")
    private String cuentaRevaluacion;
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

    public String getCodigoSubCategoria() {
        return codigoSubCategoria;
    }

    public void setCodigoSubCategoria(String codigoSubCategoria) {
        this.codigoSubCategoria = codigoSubCategoria;
    }

    public String getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(String codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCuentaCapitalizacion() {
        return cuentaCapitalizacion;
    }

    public void setCuentaCapitalizacion(String cuentaCapitalizacion) {
        this.cuentaCapitalizacion = cuentaCapitalizacion;
    }

    public String getCuentaPatrimonioCompras() {
        return cuentaPatrimonioCompras;
    }

    public void setCuentaPatrimonioCompras(String cuentaPatrimonioCompras) {
        this.cuentaPatrimonioCompras = cuentaPatrimonioCompras;
    }

    public String getCuentaPatrimonioDonaciones() {
        return cuentaPatrimonioDonaciones;
    }

    public void setCuentaPatrimonioDonaciones(String cuentaPatrimonioDonaciones) {
        this.cuentaPatrimonioDonaciones = cuentaPatrimonioDonaciones;
    }

    public String getCuentaDepreciacion() {
        return cuentaDepreciacion;
    }

    public void setCuentaDepreciacion(String cuentaDepreciacion) {
        this.cuentaDepreciacion = cuentaDepreciacion;
    }

    public String getCuentaRevaluacion() {
        return cuentaRevaluacion;
    }

    public void setCuentaRevaluacion(String cuentaRevaluacion) {
        this.cuentaRevaluacion = cuentaRevaluacion;
    }
    //</editor-fold>
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 79 * hash + (this.numeroEmpresa != null ? this.numeroEmpresa.hashCode() : 0);
        hash = 79 * hash + (this.codigoSubCategoria != null ? this.codigoSubCategoria.hashCode() : 0);
        hash = 79 * hash + (this.codigoCategoria != null ? this.codigoCategoria.hashCode() : 0);
        hash = 79 * hash + (this.descripcion != null ? this.descripcion.hashCode() : 0);
        hash = 79 * hash + (this.cuentaCapitalizacion != null ? this.cuentaCapitalizacion.hashCode() : 0);
        hash = 79 * hash + (this.cuentaPatrimonioCompras != null ? this.cuentaPatrimonioCompras.hashCode() : 0);
        hash = 79 * hash + (this.cuentaPatrimonioDonaciones != null ? this.cuentaPatrimonioDonaciones.hashCode() : 0);
        hash = 79 * hash + (this.cuentaDepreciacion != null ? this.cuentaDepreciacion.hashCode() : 0);
        hash = 79 * hash + (this.cuentaRevaluacion != null ? this.cuentaRevaluacion.hashCode() : 0);
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
        final SubCategoria other = (SubCategoria) obj;
        if ((this.codigoSubCategoria == null) ? (other.codigoSubCategoria != null) : !this.codigoSubCategoria.equals(other.codigoSubCategoria)) {
            return false;
        }
        if ((this.codigoCategoria == null) ? (other.codigoCategoria != null) : !this.codigoCategoria.equals(other.codigoCategoria)) {
            return false;
        }
        if ((this.descripcion == null) ? (other.descripcion != null) : !this.descripcion.equals(other.descripcion)) {
            return false;
        }
        if ((this.cuentaCapitalizacion == null) ? (other.cuentaCapitalizacion != null) : !this.cuentaCapitalizacion.equals(other.cuentaCapitalizacion)) {
            return false;
        }
        if ((this.cuentaPatrimonioCompras == null) ? (other.cuentaPatrimonioCompras != null) : !this.cuentaPatrimonioCompras.equals(other.cuentaPatrimonioCompras)) {
            return false;
        }
        if ((this.cuentaPatrimonioDonaciones == null) ? (other.cuentaPatrimonioDonaciones != null) : !this.cuentaPatrimonioDonaciones.equals(other.cuentaPatrimonioDonaciones)) {
            return false;
        }
        if ((this.cuentaDepreciacion == null) ? (other.cuentaDepreciacion != null) : !this.cuentaDepreciacion.equals(other.cuentaDepreciacion)) {
            return false;
        }
        if ((this.cuentaRevaluacion == null) ? (other.cuentaRevaluacion != null) : !this.cuentaRevaluacion.equals(other.cuentaRevaluacion)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.numeroEmpresa != other.numeroEmpresa && (this.numeroEmpresa == null || !this.numeroEmpresa.equals(other.numeroEmpresa))) {
            return false;
        }
        return true;
    }
}