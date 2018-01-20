/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.entities;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import cr.ac.ucr.sigebi.domain.Estado;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author jorge.serrano
 */
@Entity(name = "TrasladoDetalleEntity")
@Table(name = "SIGEBI_OAF.SGB_TRASLADO_DETALLE")
@SequenceGenerator(name="SGB_SQ_TRASLADO_DETALLE", sequenceName = "SIGEBI_OAF.SGB_SQ_TRASLADO_DETALLE", initialValue=1, allocationSize=1)
public class TrasladoDetalleEntity  extends ObjetoBase implements Serializable  {
    
    private static final long serialVersionUID = 1L;
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SGB_SQ_TRASLADO_DETALLE")
    @Column(name = "ID_TRASLADO_DETALLE") // NUMBER SGB_SQ_TRASLADO
    private Integer idTrasladoDetalle;
    
    @ManyToOne
    @JoinColumn(name = "ID_TRASLADO", referencedColumnName = "ID_TRASLADO")
    private TrasladoEntity idTraslado;
    
    
    @ManyToOne
    @JoinColumn(name = "ID_BIEN", referencedColumnName = "ID_BIEN")
    private ViewBienEntity idBien;
    
    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private  Estado idEstado;
    
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">

    public TrasladoDetalleEntity() {
    }

    public TrasladoDetalleEntity( TrasladoEntity idTraslado
                                , ViewBienEntity idBien
                                ,  Estado idEstado) {
        this.idTraslado = idTraslado;
        this.idBien = idBien;
        this.idEstado = idEstado;
    }
    
    
    
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">

    public Integer getIdTrasladoDetalle() {
        return idTrasladoDetalle;
    }

    public void setIdTrasladoDetalle(Integer idTrasladoDetalle) {
        this.idTrasladoDetalle = idTrasladoDetalle;
    }

    public TrasladoEntity getIdTraslado() {
        return idTraslado;
    }

    public void setIdTraslado(TrasladoEntity idTraslado) {
        this.idTraslado = idTraslado;
    }

    public ViewBienEntity getIdBien() {
        return idBien;
    } 

    public void setIdBien(ViewBienEntity idBien) {
        this.idBien = idBien;
    }

    public  Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado( Estado idEstado) {
        this.idEstado = idEstado;
    }
    
    
    
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
     @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTrasladoDetalle != null ? idTrasladoDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrasladoDetalleEntity)) {
            return false;
        }
        TrasladoDetalleEntity other = (TrasladoDetalleEntity) object;
        if ((this.idTrasladoDetalle == null && other.idTrasladoDetalle != null) || (this.idTrasladoDetalle != null && !this.idTrasladoDetalle.equals(other.idTrasladoDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.ucr.sigebi.entities.TrasladoDetalle[ id=" + idTrasladoDetalle + " ]";
    }
    //</editor-fold>
    
}
