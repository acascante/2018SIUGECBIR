/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Convenio;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author alvaro.cascante
 */
public class ConvenioCommand {
        
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private Long id;
    private String institucion;
    private String responsable;
    private String oficio;
    private String objetivo;
    private Boolean prestar;
    private Boolean recibirPrestamo;
    private Boolean soloEstaUnidad;
    private Date fechaInicio;
    private Date fechaFin;
    private Estado estado;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ConvenioCommand() {
        super();
        this.fechaInicio = getDefaultDate();
        this.fechaFin = getDefaultDate();
    }

    public ConvenioCommand(Convenio convenio) {
        this.id = convenio.getId();
        this.institucion = convenio.getInstitucion();
        this.responsable = convenio.getResponsable();
        this.oficio = convenio.getOficio();
        this.objetivo = convenio.getObjetivo();
        this.prestar = convenio.getPrestar();
        this.recibirPrestamo = convenio.getRecibirPrestamo();
        this.soloEstaUnidad = convenio.getSoloEstaUnidad();
        this.fechaInicio = convenio.getFechaInicio();
        this.fechaFin = convenio.getFechaFin();
        this.estado = convenio.getEstado();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public Convenio getConvenio() {
        Convenio convenio = new Convenio();
        convenio.setInstitucion(this.institucion);
        convenio.setResponsable(this.responsable);
        convenio.setOficio(this.oficio);
        convenio.setObjetivo(this.objetivo);
        convenio.setPrestar(this.prestar);
        convenio.setRecibirPrestamo(this.recibirPrestamo);
        convenio.setSoloEstaUnidad(this.soloEstaUnidad);
        convenio.setFechaInicio(this.fechaInicio);
        convenio.setFechaFin(this.fechaFin);
        convenio.setEstado(this.estado);
        return convenio;
    }
    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    private Date getDefaultDate() {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance(Constantes.DEFAULT_TIME_ZONE);
        calendar.setTime(today);
        return calendar.getTime();
    }
    
    public TimeZone getTimeZone() {
        return Constantes.DEFAULT_TIME_ZONE;
    }
    
    public String getDatePattern() {
        return Constantes.DEFAULT_DATE_PATTERN;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public Boolean getSoloEstaUnidad() {
        return soloEstaUnidad;
    }

    public void setSoloEstaUnidad(Boolean soloEstaUnidad) {
        this.soloEstaUnidad = soloEstaUnidad;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    //</editor-fold>
}