/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Convenio;
import cr.ac.ucr.sigebi.domain.RecepcionPrestamo;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author alvaro.cascante
 */
public class RecepcionPrestamoCommand {
        
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private Long id;
    private String identificacion;
    private String descripcion;
    private String marca;
    private String modelo;
    private String serie;
    private String motivo;
    private String observacion;
    private Date fechaIngreso;
    private Date fechaDevolucion;
    private Estado estado;
    private Long idConvenio;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public RecepcionPrestamoCommand() {
        super();
        this.fechaIngreso = getDefaultDate();
        this.fechaDevolucion = getDefaultDate();
    }
    
    public RecepcionPrestamoCommand(RecepcionPrestamo prestamo) {
        this.id = prestamo.getId();
        this.identificacion = prestamo.getIdentificacion();
        this.descripcion = prestamo.getDescripcion();
        this.marca = prestamo.getMarca();
        this.modelo = prestamo.getModelo();
        this.serie = prestamo.getSerie();
        this.serie = prestamo.getMotivo();
        this.fechaIngreso = prestamo.getFechaIngreso();
        this.fechaDevolucion = prestamo.getFechaDevolucion();
        this.estado = prestamo.getEstado();
        this.idConvenio = prestamo.getConvenio().getId();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public RecepcionPrestamo getRecepcionPrestamo(Convenio convenio) {
        RecepcionPrestamo prestamo = new RecepcionPrestamo();
        prestamo.setId(this.id);
        prestamo.setIdentificacion(this.identificacion);
        prestamo.setDescripcion(this.descripcion);
        prestamo.setMarca(this.marca);
        prestamo.setModelo(this.modelo);
        prestamo.setSerie(this.serie);
        prestamo.setMotivo(this.motivo);
        prestamo.setFechaIngreso(this.fechaIngreso);
        prestamo.setFechaDevolucion(this.fechaDevolucion);
        prestamo.setEstado(this.estado);
        prestamo.setConvenio(convenio);
        return prestamo;
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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public Long getIdConvenio() {
        return idConvenio;
    }

    public void setIdConvenio(Long idConvenio) {
        this.idConvenio = idConvenio;
    }
    //</editor-fold>49
}