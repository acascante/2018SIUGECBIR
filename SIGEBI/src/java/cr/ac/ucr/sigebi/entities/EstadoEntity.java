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
@Entity(name = "EstadoEntity")
@Table(name = "SIGEBI_OAF.SGB_ESTADO")
@NamedQueries({ 
      @NamedQuery(name = "EstadoEntity.findAll", query =  "SELECT s FROM EstadoEntity s")
    , @NamedQuery(name = "EstadoEntity.findById", query = "SELECT s FROM EstadoEntity s WHERE s.idEstado = :pidEstado")
    , @NamedQuery(name = "EstadoEntity.findByEstadoDominio", query = "SELECT s FROM EstadoEntity s WHERE s.estado = :pEstado and upper(s.dominio) = :pDominio")
    , @NamedQuery(name = "EstadoEntity.findByNombreDominio", query = "SELECT s FROM EstadoEntity s WHERE upper(s.nombre) = upper(:pNombre) and upper(s.dominio) = upper(:pDominio)")
    , @NamedQuery(name = "EstadoEntity.findByDominio", query = "SELECT s FROM EstadoEntity s WHERE upper(s.dominio) = :pDominio")
}
)
public class EstadoEntity extends ObjetoBase implements Serializable  {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ID_ESTADO")
    private Integer idEstado;

    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DOMINIO")
    private String dominio;

    @Column(name = "ESTADO")
    private Integer estado;
    
    
    //<editor-fold defaultstate="collapsed" desc="Contructor">
    
    
    public EstadoEntity() {
    }
    
    //</editor-fold>
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">
    public EstadoEntity(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public EstadoEntity(Integer idEstado, String nombre) {
        this.idEstado = idEstado;
        this.nombre = nombre;
    }
    /**
     * @return the idEstado
     */
    public Integer getIdEstado() {
        return idEstado;
    }

    /**
     * @param idEstado the idEstado to set
     */
    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the dominio
     */
    public String getDominio() {
        return dominio;
    }

    /**
     * @param dominio the dominio to set
     */
    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    /**
     * @return the estado
     */
    public Integer getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    //</editor-fold>
    
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstado != null ? idEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoEntity)) {
            return false;
        }
        EstadoEntity other = (EstadoEntity) object;
        if ((this.idEstado == null && other.idEstado != null) || (this.idEstado != null && !this.idEstado.equals(other.idEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.tipoEntity[idTipo=" + idEstado + "]";
    }
    //</editor-fold>
}
