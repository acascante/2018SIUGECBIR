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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author jorge.serrano
 */
//SF_SUB_CATEGORIA_ACTIVOF
@Entity(name = "SubCategoriaEntity")
@Table(name = "SF_SUB_CATEGORIA_ACTIVOF")
@NamedQueries({ 
    @NamedQuery(name = "SubCategoriaEntity.findAll", 
            query = "SELECT s FROM SubCategoriaEntity s "
                    + "WHERE s.codCategoria = :PCODIGO_CATEGORIA "),
    @NamedQuery(name = "SubCategoriaEntity.findById"
            , query = "SELECT s FROM SubCategoriaEntity s "
                    + "WHERE s.codSubCateg = :PCODIGO_SUB_CATEGORIA "
                    + "AND s.codCategoria = :PCODIGO_CATEGORIA ")
}
)
public class SubCategoriaEntity  extends ObjetoBase implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @Basic(optional = false)
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
    
    @Column(name = "NUM_EMPRESA")
    private Integer numEmpresa;
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">

    public SubCategoriaEntity() {
    }

    public SubCategoriaEntity(String codSubCateg, String codCategoria, String descripcion, String ctaCapitalizacionCat, String ctaPatrimonioComprasCat, String ctaPatrimonioDonacionesCat, String ctaDepreciacionCat, String ctaRevaluacioCat, Integer numEmpresa) {
        this.codSubCateg = codSubCateg;
        this.codCategoria = codCategoria;
        this.descripcion = descripcion;
        this.ctaCapitalizacionCat = ctaCapitalizacionCat;
        this.ctaPatrimonioComprasCat = ctaPatrimonioComprasCat;
        this.ctaPatrimonioDonacionesCat = ctaPatrimonioDonacionesCat;
        this.ctaDepreciacionCat = ctaDepreciacionCat;
        this.ctaRevaluacioCat = ctaRevaluacioCat;
        this.numEmpresa = numEmpresa;
    }

    

    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="SET's y GET's ">
    
    
    
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

    public Integer getNumEmpresa() {
        return numEmpresa;
    }

    public void setNumEmpresa(Integer numEmpresa) {
        this.numEmpresa = numEmpresa;
    }
 
    
    
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codSubCateg != null ? codSubCateg.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubCategoriaEntity)) {
            return false;
        }
        SubCategoriaEntity other = (SubCategoriaEntity) object;
        if ((this.codSubCateg == null && other.codSubCateg != null) || (this.codSubCateg != null && !this.codSubCateg.equals(other.codSubCateg))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.SubCategoriaEntity[idSubCateg=" + codSubCateg + "]";
    }
    //</editor-fold>
    
}
