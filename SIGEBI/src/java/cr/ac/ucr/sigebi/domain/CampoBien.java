/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author alvaro.cascante
 */
@Entity(name = "CampoBien")
@Table(name = "SIGEBI_OAF.SIGB_CAMPO_BIEN")
@SequenceGenerator(name="sqCampoBien", sequenceName = "SIGEBI_OAF.SGB_SQ_CAMPO_BIEN", initialValue=1, allocationSize=1)
public class CampoBien implements Serializable {

    private static final long serialVersionUID = -3637910328919744959L;

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sqCampoBien")
    @Column(name = "ID_CAMPO_BIEN")
    private Long id;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "ID_COLUMNA")
    private String idColumna;
    
    @Column(name = "NOMBRE_HQL")
    private String nombreHQL;
    
    @Column(name = "TAMANO_COLUMNA")
    private Integer tamanoColumna;
    
    @Column(name = "ES_TEXTO")
    private Integer esTexto;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public CampoBien() {}
    
    public CampoBien(Long id, String nombre, String idColumna, String nombreHQL, Integer tamanoColumna, Integer esTexto, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.idColumna = idColumna;
        this.nombreHQL = nombreHQL;
        this.tamanoColumna = tamanoColumna;
        this.esTexto = esTexto;
        this.descripcion = descripcion;
    }
    //</editor-fold>  

    //<editor-fold defaultstate="collapsed" desc="GET's Set't">
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

    public String getIdColumna() {
        return idColumna;
    }

    public void setIdColumna(String idColumna) {
        this.idColumna = idColumna;
    }

    public String getNombreHQL() {
        return nombreHQL;
    }

    public void setNombreHQL(String nombreHQL) {
        this.nombreHQL = nombreHQL;
    }

    public Integer getTamanoColumna() {
        return tamanoColumna;
    }

    public void setTamanoColumna(Integer tamanoColumna) {
        this.tamanoColumna = tamanoColumna;
    }

    public Integer getEsTexto() {
        return esTexto;
    }

    public void setEsTexto(Integer esTexto) {
        this.esTexto = esTexto;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    //</editor-fold>
}