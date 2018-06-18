/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Evento;
import cr.ac.ucr.sigebi.domain.SolicitudDetalle;
import cr.ac.ucr.sigebi.domain.SolicitudMantenimiento;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.domain.Usuario;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

/**
 *
 * @author alvaro.cascante
 */
public class MantenimientoCommand {
    
    //<editor-fold defaultstate="collapsed" desc="Constantes">
    public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getDefault();
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private Long id;
    private UnidadEjecutora unidadEjecutora;
    private Estado estado;
    private Date fecha;
    private String observacion;
    private String observacionConfirmacion;
    private Usuario usuario;
    private List<Bien> bienesEliminar;  // Bienes a eliminar
    private Set<Bien> bienesAgregar;    // Bienes a agregar
    private Map<Long, Bien> bienes;     // Bienes existentes en la solicitud
    private List<SolicitudDetalle> detallesEliminar;
    private Map<Long, SolicitudDetalle> detalles;
    
    private String eventoDescripcion;
    private Double eventoCosto;
    private Date eventoFecha;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public MantenimientoCommand() {
        super();
        this.fecha = getDefaultDate();
        this.bienes = new HashMap<Long, Bien>();
        this.detalles = new HashMap<Long, SolicitudDetalle>();
    }

    public MantenimientoCommand(UnidadEjecutora unidadEjecutora, Estado estado, Usuario usuario) {
        this();
        this.unidadEjecutora = unidadEjecutora;
        this.estado = estado;        
        this.usuario = usuario;
    }

    public MantenimientoCommand(SolicitudMantenimiento solicitud) {
        super();
        this.id = solicitud.getId();
        this.unidadEjecutora = solicitud.getUnidadEjecutora();
        this.estado = solicitud.getEstado();
        this.fecha = solicitud.getFecha();
        this.usuario = solicitud.getUsuario();
        this.observacion = solicitud.getObservacion();

        this.bienes = new HashMap<Long, Bien>();
        this.detalles = new HashMap<Long, SolicitudDetalle>();
        for (SolicitudDetalle detalle : solicitud.getDetalles()) {
            this.bienes.put(detalle.getBien().getId(), detalle.getBien());
            this.detalles.put(detalle.getBien().getId(), detalle);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public SolicitudMantenimiento getSolicitud() {
        SolicitudMantenimiento solicitud = new SolicitudMantenimiento();
        solicitud.setId(this.id);
        solicitud.setUnidadEjecutora(this.unidadEjecutora);
        solicitud.setEstado(this.estado);
        solicitud.setFecha(this.fecha);
        solicitud.setUsuario(this.usuario);
        solicitud.setDiscriminator(Constantes.DISCRIMINATOR_SOLICITUD_MANTENIMIENTO);
        solicitud.setObservacion(this.observacion);
        
        List<SolicitudDetalle> listDetalles = new ArrayList<SolicitudDetalle>(this.detalles.values());
        for (Bien bienAgregar : this.getBienesAgregar()) {
            listDetalles.add(new SolicitudDetalle(solicitud, bienAgregar, this.estado));
        }
        
        for (SolicitudDetalle detalle : listDetalles) { //Actualizo fecha de los detalles en caso que se haya modificado
            if (!this.bienes.containsKey(detalle.getBien().getId())) {
                this.getDetallesEliminar().add(detalle);
                this.detalles.remove(detalle.getBien().getId());
            }
        }        
        solicitud.setDetalles(listDetalles);        
        return solicitud;
    }
    
    public Evento getEvento(Long idBien) {
        Evento evento = new Evento();
        evento.setDescripcion(this.eventoDescripcion);
        evento.setCosto(this.eventoCosto);
        evento.setFecha(this.eventoFecha);
        evento.setDetalle(this.detalles.get(idBien));
        return evento;        
    }
    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">    
    private Date getDefaultDate() {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance(getTimeZone());
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

    public UnidadEjecutora getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(UnidadEjecutora unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Map<Long, Bien> getBienes() {
        return bienes;
    }

    public List<Bien> getListBienes() {
        List<Bien> list = new ArrayList<Bien>(bienes.values());
        return list;
    }

    public void setBienes(Map<Long, Bien> bienes) {
        this.bienes = bienes;
    }
    
    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    
    public List<Bien> getBienesEliminar() {
        if (this.bienesEliminar == null) {
            this.bienesEliminar = new ArrayList<Bien>();
        }
        return bienesEliminar;
    }

    public void setBienesEliminar(List<Bien> bienesEliminar) {
        this.bienesEliminar = bienesEliminar;
    }
    
    public Set<Bien> getBienesAgregar() {
        if (this.bienesAgregar == null) {
            this.bienesAgregar = new HashSet<Bien>();
        }
        return bienesAgregar;
    }

    public void setBienesAgregar(Set<Bien> bienesAgregar) {
        this.bienesAgregar = bienesAgregar;
    }
    
    public List<SolicitudDetalle> getDetallesEliminar() {
        if (this.detallesEliminar == null) {
            this.detallesEliminar = new ArrayList<SolicitudDetalle>();
        }
        return detallesEliminar;
    }

    public void setDetallesEliminar(List<SolicitudDetalle> detallesEliminar) {
        this.detallesEliminar = detallesEliminar;
    }
    
    public Map<Long, SolicitudDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(Map<Long, SolicitudDetalle> detalles) {
        this.detalles = detalles;
    }
    
    public void addBien(Bien bien) {
        this.bienes.put(bien.getId(), bien);
    }    
    
    public Bien getBien(Long id) {
        return bienes.get(id);
    }

    public String getEventoDescripcion() {
        return eventoDescripcion;
    }

    public void setEventoDescripcion(String eventoDescripcion) {
        this.eventoDescripcion = eventoDescripcion;
    }

    public Double getEventoCosto() {
        return eventoCosto;
    }

    public void setEventoCosto(Double eventoCosto) {
        this.eventoCosto = eventoCosto;
    }

    public Date getEventoFecha() {
        return eventoFecha;
    }

    public void setEventoFecha(Date eventoFecha) {
        this.eventoFecha = eventoFecha;
    }
    
    public String getObservacionConfirmacion() {
        return observacionConfirmacion;
    }

    public void setObservacionConfirmacion(String observacionConfirmacion) {
        this.observacionConfirmacion = observacionConfirmacion;
    }
    //</editor-fold>
}