/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author alvaro.cascante
 */
public class ReporteDonacionesCommand {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private Long idTipo;
    private Integer idOrden;
    private Integer idOrden1;
    private Integer idOrden2;
    private Integer idOrden3;
    
    private String fltIdentificaionBien;
    private Date fltFechaInicio;
    private Date fltFechaFin;
    private Long fltIdSolicitud;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ReporteDonacionesCommand() { 
        super();
        this.idTipo = -1L;
        this.idOrden = -1;
        this.idOrden1 = -1;
        this.idOrden2 = -1;
        this.idOrden3 = -1;
        this.fltFechaInicio = null;
        this.fltFechaFin = null;
    }
    //</editor-fold>
       
    public String getDatePattern() {
        return Constantes.DEFAULT_DATE_PATTERN;
    }
    
    public TimeZone getTimeZone() {
        return Constantes.DEFAULT_TIME_ZONE;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public Long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Long idTipo) {
        this.idTipo = idTipo;
    }

    public Integer getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Integer idOrden) {
        this.idOrden = idOrden;
    }

    public Integer getIdOrden1() {
        return idOrden1;
    }

    public void setIdOrden1(Integer idOrden1) {
        this.idOrden1 = idOrden1;
    }

    public Integer getIdOrden2() {
        return idOrden2;
    }

    public void setIdOrden2(Integer idOrden2) {
        this.idOrden2 = idOrden2;
    }

    public Integer getIdOrden3() {
        return idOrden3;
    }

    public void setIdOrden3(Integer idOrden3) {
        this.idOrden3 = idOrden3;
    }
    
    public String getFltIdentificaionBien() {
        return fltIdentificaionBien;
    }

    public void setFltIdentificaionBien(String fltIdentificaionBien) {
        this.fltIdentificaionBien = fltIdentificaionBien;
    }

    public Date getFltFechaInicio() {
        return fltFechaInicio;
    }

    public void setFltFechaInicio(Date fltFechaInicio) {
        this.fltFechaInicio = fltFechaInicio;
    }

    public Date getFltFechaFin() {
        return fltFechaFin;
    }

    public void setFltFechaFin(Date fltFechaFin) {
        this.fltFechaFin = fltFechaFin;
    }

    public Long getFltIdSolicitud() {
        return fltIdSolicitud;
    }

    public void setFltIdSolicitud(Long fltIdSolicitud) {
        this.fltIdSolicitud = fltIdSolicitud;
    }
    //</editor-fold>
}