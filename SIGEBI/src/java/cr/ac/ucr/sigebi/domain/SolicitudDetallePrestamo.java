/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author alvaro.cascante
 */
@Entity(name = "SolicitudDetallePrestamo")
@Table(name = "SIGEBI_OAF.SIGB_SOLICITUD_DETALLE_PRESTAMO")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "discriminator")
@SequenceGenerator(name = "SGB_SQ_SOLICITUDES_DETA", sequenceName = "SIGEBI_OAF.SGB_SQ_SOLICITUDES_DETA", initialValue = 1, allocationSize = 1)
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
