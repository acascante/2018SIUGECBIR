/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import java.util.Date;
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
 * SIGB_PRELIMINAR
 * @author jorge.serrano
 */
@Entity(name = "Preliminar")
@Table(name = "SIGEBI_OAF.SIGB_PRELIMINAR")
@SequenceGenerator(name="sqPreliminar", sequenceName = "SIGEBI_OAF.SGB_SQ_PRELIMINAR", initialValue=1, allocationSize=1)

public class Preliminar extends ObjetoBase implements Serializable {
    
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sqPreliminar")
    @Column(name = "ID_PREINGESO")
    private Long id;
    
    @Column(name = "ID_IDENTIFICACION")
    private String identificacion;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @ManyToOne
    @JoinColumn(name = "ID_UNIDAD_EJECUTORA", referencedColumnName = "ID")
    private UnidadEjecutora unidadEjecutora;
    
    @Column(name = "MARCA")
    private String marca;
    
    @Column(name = "MODELO")
    private String modelo;
    
    @Column(name = "SERIE")
    private String serie;
    
    @Column(name = "NUM_ORDEN")
    private String orden;
    
    @Column(name = "NUM_FACTURA")
    private String factura;
    
    @Column(name = "FECHA_INGRESO")
    private Date fechaIngreso;
    
//    @Column(name = "ID_USUARIO_REGISTRA")
//    private String usuarioRegistra;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO_REGISTRA", referencedColumnName = "ID_USUARIO")
    private Usuario usuarioRegistra;
    //</editor-fold>
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public Preliminar() {
        fechaIngreso = new Date();
        unidadEjecutora = new UnidadEjecutora();
    }
    
    //</editor-fold>
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UnidadEjecutora getUnidad() {
        return unidadEjecutora;
    }

    public void setUnidad(UnidadEjecutora unidad) {
        this.unidadEjecutora = unidad;
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

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Usuario getUsuarioRegistra() {
        return usuarioRegistra;
    }

    public void setUsuarioRegistra(Usuario usuarioRegistra) {
        this.usuarioRegistra = usuarioRegistra;
    }

    
    //</editor-fold>


    //<editor-fold defaultstate="collapsed" desc="Metodos">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Preliminar)) {
            return false;
        }
        return this.id.equals(((Preliminar)object).id);
    }

    @Override
    public String toString() {
        return "entidades.Preliminar[id=" + this.id + "]";
    }
    //</editor-fold>

}
