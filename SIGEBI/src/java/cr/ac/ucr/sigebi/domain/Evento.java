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
@Entity(name = "Evento")
@Table(name = "SIGEBI_OAF.SIGB_BIEN_EVENTO")
@SequenceGenerator(name="sqEventos", sequenceName = "SIGEBI_OAF.SGB_SQ_BIEN_EVENTO", initialValue=1, allocationSize=1)
public class Evento extends ObjetoBase implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sqEventos")
    @Column(name = "ID_EVENTO")
    private Long id;
    
    @JoinColumn(name = "ID_DETALLE", referencedColumnName = "ID_SOLICITUD_DETALLE")
    @ManyToOne(fetch = FetchType.EAGER)
    private SolicitudDetalle detalle;
    
    @Column(name = "COSTO")
    private Double costo;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "FECHA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public Evento() {}

    public Evento(SolicitudDetalle detalle, Double costo, String descripcion, Date fecha) {
        this.detalle = detalle;
        this.costo = costo;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SolicitudDetalle getDetalle() {
        return detalle;
    }

    public void setDetalle(SolicitudDetalle detalle) {
        this.detalle = detalle;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    //</editor-fold>
}