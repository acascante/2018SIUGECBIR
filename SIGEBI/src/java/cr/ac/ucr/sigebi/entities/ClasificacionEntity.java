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
@Entity(name = "ClasificacionEntity")
@Table(name = "SIGEBI_OAF.SGB_CLASIFICACION")
@NamedQueries({ 
    @NamedQuery(name = "ClasificacionEntity.findAll", query = "SELECT s FROM ClasificacionEntity s WHERE s.codSubCateg = :PCODIGO_SUB_CATEGORIA"),
    @NamedQuery(name = "ClasificacionEntity.findById", query = "SELECT s FROM ClasificacionEntity s WHERE s.idClasificacion = :PID_CLASIFICACION")
}
)
public class ClasificacionEntity extends ObjetoBase implements Serializable {
    
    private static final long serialVersionUID = 1L;

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CLASIFICACION")
    private Integer idClasificacion;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "CODIGO_SUB_CATEGORIA")
    private String codSubCateg;
    
    @Column(name = "ID_ESTADO")
    private Integer idEstado;
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    
    public ClasificacionEntity() {
    }

    public ClasificacionEntity(Integer idClasificacion) {
        this.idClasificacion = idClasificacion;
    }

    public ClasificacionEntity(Integer idClasificacion, String nombre, String codSubCateg, Integer idEstado) {
        this.idClasificacion = idClasificacion;
        this.nombre = nombre;
        this.codSubCateg = codSubCateg;
        this.idEstado = idEstado;
    }

    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="SET's y GET's ">
    public Integer getIdClasificacion() {
        return idClasificacion;
    }

    public void setIdClasificacion(Integer idClasificacion) {
        this.idClasificacion = idClasificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodSubCateg() {
        return codSubCateg;
    }

    public void setCodSubCateg(String codSubCateg) {
        this.codSubCateg = codSubCateg;
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
        hash += (idClasificacion != null ? idClasificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClasificacionEntity)) {
            return false;
        }
        ClasificacionEntity other = (ClasificacionEntity) object;
        if ((this.idClasificacion == null && other.idClasificacion != null) || (this.idClasificacion != null && !this.idClasificacion.equals(other.idClasificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.ClasificacionEntity[idTipo=" + idClasificacion + "]";
    }
    //</editor-fold>
    

}