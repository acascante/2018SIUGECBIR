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
public class SubCategoria  extends ObjetoBase implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "NUM_EMPRESA")
    private Integer numEmpresa;
    
    @Column(name = "CODIGO_SUB_CATEGORIA")
    private String codSubCateg;
    
    @Column(name = "CODIGO_CATEGORIA")
    private String codCategoria;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "CTA_CAPITALIZACION_CAT")
    private String ctaCapitalizacionCat;
    
    @Column(name = "CTA_PATRIMONIO_COMPRAS_CAT")
    private String ctaPatrimonioComprasCat;
    
    @Column(name = "CTA_PATRIMONIO_DONACIONES_CAT")
    private String ctaPatrimonioDonacionesCat;
    
    @Column(name = "CTA_DEPRECIACION_CAT")
    private String ctaDepreciacionCat;
    
    @Column(name = "CTA_REVALUACION_CAT")
    private String ctaRevaluacioCat;

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

    public String getCodSubCateg() {
        return codSubCateg;
    }

    public void setCodSubCateg(String codSubCateg) {
        this.codSubCateg = codSubCateg;
    }

    public String getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(String codCategoria) {
        this.codCategoria = codCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCtaCapitalizacionCat() {
        return ctaCapitalizacionCat;
    }

    public void setCtaCapitalizacionCat(String ctaCapitalizacionCat) {
        this.ctaCapitalizacionCat = ctaCapitalizacionCat;
    }

    public String getCtaPatrimonioComprasCat() {
        return ctaPatrimonioComprasCat;
    }

    public void setCtaPatrimonioComprasCat(String ctaPatrimonioComprasCat) {
        this.ctaPatrimonioComprasCat = ctaPatrimonioComprasCat;
    }

    public String getCtaPatrimonioDonacionesCat() {
        return ctaPatrimonioDonacionesCat;
    }

    public void setCtaPatrimonioDonacionesCat(String ctaPatrimonioDonacionesCat) {
        this.ctaPatrimonioDonacionesCat = ctaPatrimonioDonacionesCat;
    }

    public String getCtaDepreciacionCat() {
        return ctaDepreciacionCat;
    }

    public void setCtaDepreciacionCat(String ctaDepreciacionCat) {
        this.ctaDepreciacionCat = ctaDepreciacionCat;
    }

    public String getCtaRevaluacioCat() {
        return ctaRevaluacioCat;
    }

    public void setCtaRevaluacioCat(String ctaRevaluacioCat) {
        this.ctaRevaluacioCat = ctaRevaluacioCat;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 79 * hash + (this.numEmpresa != null ? this.numEmpresa.hashCode() : 0);
        hash = 79 * hash + (this.codSubCateg != null ? this.codSubCateg.hashCode() : 0);
        hash = 79 * hash + (this.codCategoria != null ? this.codCategoria.hashCode() : 0);
        hash = 79 * hash + (this.descripcion != null ? this.descripcion.hashCode() : 0);
        hash = 79 * hash + (this.ctaCapitalizacionCat != null ? this.ctaCapitalizacionCat.hashCode() : 0);
        hash = 79 * hash + (this.ctaPatrimonioComprasCat != null ? this.ctaPatrimonioComprasCat.hashCode() : 0);
        hash = 79 * hash + (this.ctaPatrimonioDonacionesCat != null ? this.ctaPatrimonioDonacionesCat.hashCode() : 0);
        hash = 79 * hash + (this.ctaDepreciacionCat != null ? this.ctaDepreciacionCat.hashCode() : 0);
        hash = 79 * hash + (this.ctaRevaluacioCat != null ? this.ctaRevaluacioCat.hashCode() : 0);
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
        if ((this.codSubCateg == null) ? (other.codSubCateg != null) : !this.codSubCateg.equals(other.codSubCateg)) {
            return false;
        }
        if ((this.codCategoria == null) ? (other.codCategoria != null) : !this.codCategoria.equals(other.codCategoria)) {
            return false;
        }
        if ((this.descripcion == null) ? (other.descripcion != null) : !this.descripcion.equals(other.descripcion)) {
            return false;
        }
        if ((this.ctaCapitalizacionCat == null) ? (other.ctaCapitalizacionCat != null) : !this.ctaCapitalizacionCat.equals(other.ctaCapitalizacionCat)) {
            return false;
        }
        if ((this.ctaPatrimonioComprasCat == null) ? (other.ctaPatrimonioComprasCat != null) : !this.ctaPatrimonioComprasCat.equals(other.ctaPatrimonioComprasCat)) {
            return false;
        }
        if ((this.ctaPatrimonioDonacionesCat == null) ? (other.ctaPatrimonioDonacionesCat != null) : !this.ctaPatrimonioDonacionesCat.equals(other.ctaPatrimonioDonacionesCat)) {
            return false;
        }
        if ((this.ctaDepreciacionCat == null) ? (other.ctaDepreciacionCat != null) : !this.ctaDepreciacionCat.equals(other.ctaDepreciacionCat)) {
            return false;
        }
        if ((this.ctaRevaluacioCat == null) ? (other.ctaRevaluacioCat != null) : !this.ctaRevaluacioCat.equals(other.ctaRevaluacioCat)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.numEmpresa != other.numEmpresa && (this.numEmpresa == null || !this.numEmpresa.equals(other.numEmpresa))) {
            return false;
        }
        return true;
    }
    
}
