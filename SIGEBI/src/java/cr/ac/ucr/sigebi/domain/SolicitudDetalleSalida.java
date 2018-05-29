/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "SolicitudDetalleSalida")
@Table(name = "SIGEBI_OAF.SIGB_SALIDA_DETALLE")
@PrimaryKeyJoinColumn(name = "ID_SOLICITUD_DETALLE", referencedColumnName = "ID_SOLICITUD_DETALLE")
@DiscriminatorValue("3")
public class SolicitudDetalleSalida  extends SolicitudDetalle implements Serializable  {
    
    private static final long serialVersionUID = 1L;
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    @Column(name = "FECHA_SALIDA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaSalida;

    @Column(name = "FECHA_INGRESO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaIngreso;
    
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">

    public SolicitudDetalleSalida() {
    }
    
    public SolicitudDetalleSalida(Solicitud solicitud, Bien bien, Estado estado) {
        super(solicitud, bien, estado);
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">
    
    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    //</editor-fold>       
}
