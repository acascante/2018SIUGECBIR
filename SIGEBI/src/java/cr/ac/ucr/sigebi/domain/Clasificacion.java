/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author jairo.cisnreos
 */
@Entity(name = "Clasificacion")
@Table(name = "SIGEBI_OAF.SIGB_CLASIFICACION")
@SequenceGenerator(name = "SGB_SQ_CLASIFICACION",  sequenceName = "SIGEBI_OAF.SGB_SQ_CLASIFICACION", initialValue = 1, allocationSize = 1)
public class Clasificacion extends ObjetoBase implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_CLASIFICACION")
    @Column(name = "ID_CLASIFICACION")
    private Long id;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @ManyToOne
    @JoinColumn(name = "ID_SUB_CATEGORIA", referencedColumnName = "ID")
    private SubCategoria subCategoria;
    
    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;
    
    @Transient
    private List<SubClasificacion> subClasificaciones;
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public Clasificacion() {}
    
    public Clasificacion(Long id) {
        this.id = id;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<SubClasificacion> getSubClasificaciones() {
        return subClasificaciones;
    }

    public void setSubClasificaciones(List<SubClasificacion> subClasificaciones) {
        this.subClasificaciones = subClasificaciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public SubCategoria getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 67 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        hash = 67 * hash + (this.estado != null ? this.estado.hashCode() : 0);
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
        final Clasificacion other = (Clasificacion) obj;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.estado != other.estado && (this.estado == null || !this.estado.equals(other.estado))) {
            return false;
        }
        return true;
    }
    //</editor-fold>
}
        
