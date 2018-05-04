/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
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
@Entity(name = "TomaFisicaSobrante")
@Table(name = "SIGEBI_OAF.SIGB_TOMA_FISICA_SOBRANTE")
@SequenceGenerator(name = "SGB_SQ_TOMA_FISICA_SOBRANTE",  sequenceName = "SIGEBI_OAF.SGB_SQ_TOMA_FISICA_SOBRANTE", initialValue = 1, allocationSize = 1)
public class TomaFisicaSobrante extends ObjetoBase implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_TOMA_FISICA_SOBRANTE")
    @Column(name = "ID_TOMA_FISICA_SOBRANTE")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_TOMA_FISICA", referencedColumnName = "ID_TOMA_FISICA")
    private TomaFisica tomaFisica;

    @Column(name = "IDENTIFICACION")
    private String identificacion;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "ID_SUB_CATEGORIA", referencedColumnName = "ID")
    private SubCategoria subCategoria;
    
    @ManyToOne
    @JoinColumn(name = "ID_SUB_CLASIFICACION", referencedColumnName = "ID_SUB_CLASIFICACION")
    private SubClasificacion subClasificacion;

    @Column(name = "UBICACION")
    private String ubicacion;
    
    @Column(name = "MARCA")
    private String marca;

    @Column(name = "MODELO")
    private String modelo;
    
    @Column(name = "SERIE")
    private String serie;
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TomaFisica getTomaFisica() {
        return tomaFisica;
    }

    public void setTomaFisica(TomaFisica tomaFisica) {
        this.tomaFisica = tomaFisica;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public SubCategoria getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }

    public SubClasificacion getSubClasificacion() {
        return subClasificacion;
    }

    public void setSubClasificacion(SubClasificacion subClasificacion) {
        this.subClasificacion = subClasificacion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }
      
    //</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
   
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 79 * hash + (this.tomaFisica != null ? this.tomaFisica.hashCode() : 0);
        hash = 79 * hash + (this.identificacion != null ? this.identificacion.hashCode() : 0);
        hash = 79 * hash + (this.descripcion != null ? this.descripcion.hashCode() : 0);
        hash = 79 * hash + (this.subCategoria != null ? this.subCategoria.hashCode() : 0);
        hash = 79 * hash + (this.subClasificacion != null ? this.subClasificacion.hashCode() : 0);
        hash = 79 * hash + (this.ubicacion != null ? this.ubicacion.hashCode() : 0);
        hash = 79 * hash + (this.marca != null ? this.marca.hashCode() : 0);
        hash = 79 * hash + (this.modelo != null ? this.modelo.hashCode() : 0);
        hash = 79 * hash + (this.serie != null ? this.serie.hashCode() : 0);
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
        final TomaFisicaSobrante other = (TomaFisicaSobrante) obj;
        if ((this.identificacion == null) ? (other.identificacion != null) : !this.identificacion.equals(other.identificacion)) {
            return false;
        }
        if ((this.descripcion == null) ? (other.descripcion != null) : !this.descripcion.equals(other.descripcion)) {
            return false;
        }
        if ((this.marca == null) ? (other.marca != null) : !this.marca.equals(other.marca)) {
            return false;
        }
        if ((this.modelo == null) ? (other.modelo != null) : !this.modelo.equals(other.modelo)) {
            return false;
        }
        if ((this.serie == null) ? (other.serie != null) : !this.serie.equals(other.serie)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.tomaFisica != other.tomaFisica && (this.tomaFisica == null || !this.tomaFisica.equals(other.tomaFisica))) {
            return false;
        }
        if (this.subCategoria != other.subCategoria && (this.subCategoria == null || !this.subCategoria.equals(other.subCategoria))) {
            return false;
        }
        if (this.subClasificacion != other.subClasificacion && (this.subClasificacion == null || !this.subClasificacion.equals(other.subClasificacion))) {
            return false;
        }
        if (this.ubicacion != other.ubicacion && (this.ubicacion == null || !this.ubicacion.equals(other.ubicacion))) {
            return false;
        }
        return true;
    }

    //</editor-fold>

}
