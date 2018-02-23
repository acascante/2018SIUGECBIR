/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.SolicitudPrestamo;
import cr.ac.ucr.sigebi.domain.SolicitudDetalle;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 *
 * @author alvaro.cascante
 */
public class PrestamoCommand extends ListarBienesCommand {
    // TODO revisar si se debe heredar de ListarBienesCommand
    //<editor-fold defaultstate="collapsed" desc="Constantes">
    public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getDefault();
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private Long id;
    private UnidadEjecutora unidadEjecutora;
    private Estado estado;
    private Long idTipoEntidad;
    private Date fecha;
    private String observacion;
    private List<Bien> bienes;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public PrestamoCommand() {
        super();
        this.bienes = new ArrayList<Bien>();
    }

    public PrestamoCommand(UnidadEjecutora unidadEjecutora, Estado estado) {
        super();
        this.unidadEjecutora = unidadEjecutora;
        this.estado = estado;
        this.fecha = getDefaultDate();
        this.bienes = new ArrayList<Bien>();
    }

    public PrestamoCommand(SolicitudPrestamo prestamo) {
        super();
        this.id = prestamo.getId();
        this.unidadEjecutora = prestamo.getUnidadEjecutora();
        this.estado = prestamo.getEstado();
        this.fecha = prestamo.getFecha();
        this.idTipoEntidad = prestamo.getTipo().getId();
        for (SolicitudDetalle detalle : prestamo.getDetalles()) {
            this.bienes.add(detalle.getBien());
        }
    }
    
    public PrestamoCommand(SolicitudPrestamo prestamo, List<SolicitudDetalle> detalles) {
        this.id = prestamo.getId();
        this.unidadEjecutora = prestamo.getUnidadEjecutora();
        this.estado = prestamo.getEstado();
        this.fecha = prestamo.getFecha();
        this.idTipoEntidad = prestamo.getTipo().getId();
        
        for (SolicitudDetalle detalle : detalles) {
            this.bienes.add(detalle.getBien());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public SolicitudPrestamo getPrestamo(Tipo tipo) {
        SolicitudPrestamo prestamo = new SolicitudPrestamo();
        prestamo.setUnidadEjecutora(this.unidadEjecutora);
        prestamo.setEstado(this.estado);
        prestamo.setFecha(this.fecha);
        prestamo.setTipo(tipo);
        prestamo.setObservacion(this.observacion);
        prestamo.setDiscriminator(Constantes.DISCRIMINATOR_SOLICITUD_PRESTAMO);
        for (Bien bien : this.bienes) {
            prestamo.getDetalles().add(new SolicitudDetalle(prestamo, bien, estado));
        }
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
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UnidadEjecutora getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(UnidadEjecutora unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }

    public Long getIdTipoEntidad() {
        return idTipoEntidad;
    }

    public void setIdTipoEntidad(Long idTipoEntidad) {
        this.idTipoEntidad = idTipoEntidad;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public List<Bien> getBienes() {
        if (bienes == null){ 
            this.bienes = new ArrayList<Bien>();
        }
        return bienes;
    }

    public void setBienes(List<Bien> bienes) {
        this.bienes = bienes;
    }
    
    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    //</editor-fold>
}