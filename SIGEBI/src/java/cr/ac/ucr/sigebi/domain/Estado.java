/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "Estado")
@Table(name = "SIGEBI_OAF.SIGB_ESTADO")
@SequenceGenerator(name = "SGB_SQ_ESTADO", sequenceName = "SIGEBI_OAF.SGB_SQ_ESTADO", initialValue = 1, allocationSize = 1)
public class Estado extends ObjetoBase implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_ESTADO")
    @Column(name = "ID_ESTADO")
    private Long id;

    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DOMINIO")
    private String dominio;

    @Column(name = "ESTADO")
    private Integer valor;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 71 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        hash = 71 * hash + (this.dominio != null ? this.dominio.hashCode() : 0);
        hash = 71 * hash + (this.valor != null ? this.valor.hashCode() : 0);
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
        final Estado other = (Estado) obj;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        if ((this.dominio == null) ? (other.dominio != null) : !this.dominio.equals(other.dominio)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.valor != other.valor && (this.valor == null || !this.valor.equals(other.valor))) {
            return false;
        }
        return true;
    }
    //</editor-fold>
}
