/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.entities;

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
@Entity(name = "SubClasificacionEntity")
@Table(name = "SIGEBI_OAF.SGB_SUB_CLASIFICACION")
@NamedQueries({ 
    @NamedQuery(name = "SubClasificacionEntity.findAll", query = "SELECT s FROM SubClasificacionEntity s WHERE s.idClasificacion = :PID_CLASIFICACION"),
    @NamedQuery(name = "SubClasificacionEntity.findById", query = "SELECT s FROM SubClasificacionEntity s WHERE s.idSubClasificacion = :PID_SUB_CLASIFICACION")
}
)
public class SubClasificacionEntity {
     
    private static final long serialVersionUID = 1L;

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @Basic(optional = false)
    @Column(name = "ID_SUB_CLASIFICACION")
    private Integer idSubClasificacion;
    
    @Column(name = "ID_CLASIFICACION")
    private Integer idClasificacion;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "ID_ESTADO")
    private Integer idEstado;
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">

    public SubClasificacionEntity() {
    }
    
    public SubClasificacionEntity(Integer idSubClasificacion, Integer idClasificacion, String nombre, Integer idEstado) {
        this.idSubClasificacion = idSubClasificacion;
        this.idClasificacion = idClasificacion;
        this.nombre = nombre;
        this.idEstado = idEstado;
    }
    
  

    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="SET's y GET's ">

    
    
    public Integer getIdSubClasificacion() {
        return idSubClasificacion;
    }

    public void setIdSubClasificacion(Integer idSubClasificacion) {
        this.idSubClasificacion = idSubClasificacion;
    }

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
        hash += (idSubClasificacion != null ? idSubClasificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubClasificacionEntity)) {
            return false;
        }
        SubClasificacionEntity other = (SubClasificacionEntity) object;
        if ((this.idSubClasificacion == null && other.idSubClasificacion != null) || (this.idSubClasificacion != null && !this.idSubClasificacion.equals(other.idSubClasificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.ClasificacionEntity[idTipo=" + idSubClasificacion + "]";
    }
    //</editor-fold>
    

}
