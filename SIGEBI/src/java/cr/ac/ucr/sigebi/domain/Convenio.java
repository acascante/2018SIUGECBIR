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
@Entity(name = "Convenio")
@Table(name = "SIGEBI_OAF.SIGB_CONVENIO")
@SequenceGenerator(name="sqConvenios", sequenceName = "SIGEBI_OAF.SGB_SQ_CONVENIOS", initialValue=1, allocationSize=1)
public class Convenio extends ObjetoBase implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sqConvenios")
    @Column(name = "ID_CONVENIO")
    private Long id;
    
    @Column(name = "INSTITUCION")
    private String institucion;
    
    @Column(name = "RESPONSABLE")
    private String responsable;
    
    @Column(name = "OBJETIVO")
    private String objetivo;
    
    @Column(name = "OFICIO")
    private String oficio;
    
    @Column(name = "PRESTAR")
    private Boolean prestar;
    
    @Column(name = "RECIBIR_PRESTAMO")
    private Boolean recibirPrestamo;
    
    @Column(name = "SOLO_UNIDAD")
    private Boolean soloEstaUnidad;
    
    @Column(name = "FECHA_INICIO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicio;
    
    @Column(name = "FECHA_FIN")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFin;
    
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Estado estado;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public Convenio() {}
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getOficio() {
        return oficio;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public Boolean getPrestar() {
        return prestar;
    }

    public void setPrestar(Boolean prestar) {
        this.prestar = prestar;
    }

    public Boolean getRecibirPrestamo() {
        return recibirPrestamo;
    }

    public void setRecibirPrestamo(Boolean recibirPrestamo) {
        this.recibirPrestamo = recibirPrestamo;
    }

    public Boolean getSoloEstaUnidad() {
        return soloEstaUnidad;
    }

    public void setSoloEstaUnidad(Boolean soloEstaUnidad) {
        this.soloEstaUnidad = soloEstaUnidad;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
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
        if (!(object instanceof Convenio)) {
            return false;
        }
        return this.id.equals(((Convenio)object).id);
    }

    @Override
    public String toString() {
        return "entidades.Convenio[id=" + this.id + "]";
    }
    //</editor-fold>
}