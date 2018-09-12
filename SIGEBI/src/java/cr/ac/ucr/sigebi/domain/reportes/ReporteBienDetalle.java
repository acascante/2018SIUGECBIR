/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain.reportes;

import cr.ac.ucr.sigebi.commands.PrestamoCommand;
import cr.ac.ucr.sigebi.domain.SolicitudDetalle;
import cr.ac.ucr.sigebi.domain.SolicitudDetalleSalida;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author alvaro.cascante
 */
public class ReporteBienDetalle implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private String identificacion;
    private String marca;
    private String modelo;
    private String serie;
    private Date fechaInicio;
    private Date fechaFin;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ReporteBienDetalle() {
        super();
    }

    public ReporteBienDetalle(PrestamoCommand.BienDetalle detalle) {
        this.identificacion = detalle.getBien().getIdentificacion().getIdentificacion();
        this.marca = detalle.getBien().getResumenBien().getMarca();
        this.modelo = detalle.getBien().getResumenBien().getModelo();
        this.serie = detalle.getBien().getResumenBien().getSerie();
        this.fechaInicio = detalle.getFechaInicio();
        this.fechaFin = detalle.getFechaFin();
    }

    public ReporteBienDetalle(SolicitudDetalleSalida detalle) {
        this.identificacion = detalle.getBien().getIdentificacion().getIdentificacion();
        this.marca = detalle.getBien().getResumenBien().getMarca();
        this.modelo = detalle.getBien().getResumenBien().getModelo();
        this.serie = detalle.getBien().getResumenBien().getSerie();
        this.fechaInicio = detalle.getFechaSalida();
        this.fechaFin = detalle.getFechaIngreso();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
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