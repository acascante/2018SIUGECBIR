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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author huevo
 */
@Entity(name = "TipoEntity")
@Table(name = "SIGEBI_OAF.SGB_TIPO")
@NamedQueries({ 
    @NamedQuery(name = "TipoEntity.findAll", query = "SELECT te FROM TipoEntity te WHERE upper(te.dominio) = upper(:pTipo)"),
    @NamedQuery(name = "TipoEntity.findByDominio", query = "SELECT te FROM TipoEntity te WHERE upper(te.dominio) = upper(:dominio)"),
    @NamedQuery(name = "TipoEntity.findByNombreDominio", query = "SELECT te FROM TipoEntity te WHERE upper(te.nombre) = upper(:nombre) and upper(te.dominio) = upper(:dominio)"),
    @NamedQuery(name = "TipoEntity.findByValorDominio", query = "SELECT te FROM TipoEntity te WHERE te.valor = :valor and upper(te.dominio) = upper(:dominio)"),
    @NamedQuery(name = "TipoEntity.findById", query = "SELECT te FROM TipoEntity te WHERE te.idTipo = :pIdTipo")})
public class TipoEntity extends ObjetoBase implements Serializable {

    private static final long serialVersionUID = 1L;

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TIPO")
    private Integer idTipo;

    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DOMINIO")
    private String dominio;

    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(fetch = FetchType.EAGER)
    private EstadoEntity estado;
    
    @Column(name = "VALOR")
    private Integer valor;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public TipoEntity() {
    }

    public TipoEntity(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public TipoEntity(Integer idTipo, String nombre) {
        this.idTipo = idTipo;
        this.nombre = nombre;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="SET's y GET's ">
    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public EstadoEntity getEstado() {
        return estado;
    }

    public void setEstado(EstadoEntity estado) {
        this.estado = estado;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }
    //</editor-fold>
   
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipo != null ? idTipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoEntity)) {
            return false;
        }
        TipoEntity other = (TipoEntity) object;
        return !((this.idTipo == null && other.idTipo != null) || (this.idTipo != null && !this.idTipo.equals(other.idTipo)));
    }

    @Override
    public String toString() {
        return "entidades.tipoEntity[idTipo=" + idTipo + "]";
    }
    //</editor-fold>
}