/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;    
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author jorge.serrano
 */
@Entity(name = "ActaDetalle")
@Table(name = "SIGEBI_OAF.SGB_ACTA_DETALLE")
@SequenceGenerator(name="SGB_SQ_ACTA_DETALLE", sequenceName = "SIGEBI_OAF.SGB_SQ_ACTA_DETALLE", initialValue=1, allocationSize=1)
public class ActaDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SGB_SQ_ACTA_DETALLE")
    @Column(name = "ID_ACTA_DETALLE") // NUMBER 
    private Integer id;

    @Column(name = "ID_ACTA") // NUMBER 
    //@JoinColumn(name = "ID_ACTA", referencedColumnName = "ID_ACTA")
    //@OneToMany(fetch = FetchType.EAGER)
    private Integer idActa;
    
    @Column(name = "ID_BIEN") // NUMBER 
    //@JoinColumn(name = "ID_BIEN", referencedColumnName = "ID_BIEN")
    //@OneToMany(fetch = FetchType.EAGER)
    private Integer idBien;
    
    @Column(name = "ID_ESTADO") // NUMBER 
    private Integer idEstado;
/*
  
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(fetch = FetchType.EAGER)
    private EstadoEntity idEstado;  
    */
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Contructores">

    public ActaDetalle() {
    }

    public ActaDetalle(Integer idActa, Integer idBien, Integer idEstado) {
        this.idActa = idActa;
        this.idBien = idBien;
        this.idEstado = idEstado;
    }
    
    
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="GETs & SETs">

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdActa() {
        return idActa;
    }

    public void setIdActa(Integer idActa) {
        this.idActa = idActa;
    }

    public Integer getIdBien() {
        return idBien;
    }

    public void setIdBien(Integer idBien) {
        this.idBien = idBien;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }
    
    
    
    
    
    
    
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (Integer) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActaDetalle)) {
            return false;
        }
        ActaDetalle other = (ActaDetalle) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.ucr.sigebi.entities.ActaDetalle[ id=" + id + " ]";
    }
    
    
    //</editor-fold>
}
