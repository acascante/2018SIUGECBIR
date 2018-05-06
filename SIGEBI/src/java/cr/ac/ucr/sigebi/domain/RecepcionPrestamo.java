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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author alvaro.cascante
 */
@Entity(name = "RecepcionPrestamo")
@Table(name = "SIGEBI_OAF.SIGB_RECEPCION_PRESTAMO")
@SequenceGenerator(name="sqRecepcionPrestamo", sequenceName = "SIGEBI_OAF.SGB_SQ_RECEPCION_PRESTAMO", initialValue=1, allocationSize=1)
public class RecepcionPrestamo extends ObjetoBase implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sqRecepcionPrestamo")
    @Column(name = "ID_RECEPCION_PRESTAMO")
    private Long id;
    
    @JoinColumn(name = "ID_CONVENIO", referencedColumnName = "ID_CONVENIO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Convenio convenio;
    
    @Column(name = "IDENTIFICACION")
    private String identificacion;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "MARCA")
    private String marca;
    
    @Column(name = "MODELO")
    private String modelo;
    
    @Column(name = "SERIE")
    private String serie;
    
    @Column(name = "MOTIVO")
    private String motivo;
    
    @Column(name = "FECHA_INGRESO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaIngreso;
    
    @Column(name = "FECHA_DEVOLUCION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaDevolucion;
    
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Estado estado;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public RecepcionPrestamo() {}
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public Long getId() {    
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
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

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {    
        this.estado = estado;
    }
    //</editor-fold>
}