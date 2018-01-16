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
@Entity(name = "MonedaEntity")
@Table(name = "SF_MONEDA")
@NamedQueries({ 
      @NamedQuery(name = "MonedaEntity.findAll", query =  "SELECT s FROM MonedaEntity s")
    , @NamedQuery(name = "MonedaEntity.findById", query = "SELECT s FROM MonedaEntity s WHERE s.numEmpresa = :pnumEmpresa AND s.idMoneda = :pidMoneda")
}
)
public class MonedaEntity  extends ObjetoBase implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @Basic(optional = false)
    @Column(name = "NUM_EMPRESA")
    private Integer numEmpresa;
    
    @Id
    @Column(name = "ID_MONEDA")
    private String idMoneda;
    
    @Column(name = "DESC_MONEDA")
    private String descripcion;
    
    @Column(name = "SIGNO_MONEDA")
    private String signo;
    
    @Column(name = "COD_MONEDA")
    private String codigo;
    
    @Column(name = "IND_OPERADOR")
    private String operador;
    //</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="STE's & GET's">

    public Integer getNumEmpresa() {
        return numEmpresa;
    }

    public void setNumEmpresa(Integer numEmpresa) {
        this.numEmpresa = numEmpresa;
    }

    public String getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(String idMoneda) {
        this.idMoneda = idMoneda;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSigno() {
        return signo;
    }

    public void setSigno(String signo) {
        this.signo = signo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }
    
    
    
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">

    public MonedaEntity() {
    }
    
    
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="SobreCargas">
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMoneda != null ? idMoneda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategEntity)) {
            return false;
        }
        MonedaEntity other = (MonedaEntity) object;
        if ((this.idMoneda == null && other.idMoneda != null) || (this.idMoneda != null && !this.idMoneda.equals(other.idMoneda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.CategoriaEntity[codCategoria=" + idMoneda + "]";
    }
    
    //</editor-fold>


}
