/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author alvaro.cascante
 */
@Entity(name = "SolicitudDetallePrestamo")
@Table(name = "SIGEBI_OAF.SIGB_PRESTAMO_DETALLE")
@PrimaryKeyJoinColumn(name = "ID_SOLICITUD_DETALLE", referencedColumnName = "ID_SOLICITUD_DETALLE")
@DiscriminatorValue("1")
public class SolicitudDetallePrestamo extends SolicitudDetalle {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Column(name = "FECHA_INICIO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicio;
    
    @Column(name = "FECHA_FIN")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFin;    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public SolicitudDetallePrestamo() {}
    
    public SolicitudDetallePrestamo(Solicitud solicitud, Bien bien, Estado estado, Date fechaInicio, Date fechaFin) {
        super(solicitud, bien, estado);
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;        
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
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
    //</editor-fold>
}
