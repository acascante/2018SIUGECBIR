/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.entities;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Tipo;
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
 * @author jairo.cisneros
 */
@Entity(name = "DocumentoEntity")
@Table(name = "SIGEBI_OAF.SGB_DOCUMENTO")
@SequenceGenerator(name = "SGB_SQ_DOCUMENTO",  sequenceName = "SIGEBI_OAF.SGB_SQ_DOCUMENTO", initialValue = 1, allocationSize = 1)
public class DocumentoEntity extends ObjetoBase implements Serializable {

    private static final long serialVersionUID = 1L;
 
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_DOCUMENTO")
    @Column(name = "ID_DOCUMENTO")
    private Long idDocumento;

    @ManyToOne
    @JoinColumn(name = "PROCESO", referencedColumnName = "ID_TIPO")
    private Tipo idProceso;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ORDEN")
    private Integer orden;

    @Column(name = "DETALLE")
    private String detalle;
    
    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado idEstado;
       
    //</editor-fold>
 
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    
    public DocumentoEntity() {       
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SET's y GET's ">
     public Long getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Long idDocumento) {
        this.idDocumento = idDocumento;
    }

    public Tipo getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(Tipo idProceso) {
        this.idProceso = idProceso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }
 

    //</editor-fold>
     
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.idDocumento != null ? this.idDocumento.hashCode() : 0);
        hash = 29 * hash + (this.idProceso != null ? this.idProceso.hashCode() : 0);
        hash = 29 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        hash = 29 * hash + (this.orden != null ? this.orden.hashCode() : 0);
        hash = 29 * hash + (this.detalle != null ? this.detalle.hashCode() : 0);
        hash = 29 * hash + (this.idEstado != null ? this.idEstado.hashCode() : 0);
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
        final DocumentoEntity other = (DocumentoEntity) obj;
        return true;
    }
    
    //</editor-fold>

}
