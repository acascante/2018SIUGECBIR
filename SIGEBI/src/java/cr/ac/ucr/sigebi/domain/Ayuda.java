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
@Entity(name = "Ayuda")
@Table(name = "SIGEBI_OAF.SIGB_AYUDA")
@SequenceGenerator(name = "SGB_SQ_AYUDA", sequenceName = "SIGEBI_OAF.SGB_SQ_AYUDA", initialValue = 1, allocationSize = 1)
public class Ayuda extends ObjetoBase implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_AYUDA")
    @Column(name = "ID_AYUDA")
    private Long id;

    @Basic(optional = false)
    @Column(name = "REGLA_NAVEGACION")
    private String reglaNavegacion;

    @Column(name = "TITULO")
    private String titulo;

    @Column(name = "CUERPO")
    private String cuerpo;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReglaNavegacion(){
        return this.reglaNavegacion;
    }
    
    public void setReglaNavegacion(String reglaNavegacion){
        this.reglaNavegacion = reglaNavegacion;
    }
    
    public String getTitulo(){
        return this.titulo;
    }
    
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
    
    public String getCuerpo(){
        return this.cuerpo;
    }
    
    public void setCuerpo(String cuerpo){
        this.cuerpo = cuerpo;
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 71 * hash + (this.reglaNavegacion != null ? this.reglaNavegacion.hashCode() : 0);
        hash = 71 * hash + (this.titulo != null ? this.titulo.hashCode() : 0);
        hash = 71 * hash + (this.cuerpo != null ? this.cuerpo.hashCode() : 0);
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
        final Ayuda other = (Ayuda) obj;
        if ((this.reglaNavegacion == null) ? (other.reglaNavegacion != null) : !this.reglaNavegacion.equals(other.reglaNavegacion)) {
            return false;
        }
        if ((this.titulo == null) ? (other.titulo != null) : !this.titulo.equals(other.titulo)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.cuerpo != other.cuerpo && (this.cuerpo == null || !this.cuerpo.equals(other.cuerpo))) {
            return false;
        }
        return true;
    }
    //</editor-fold>
}
