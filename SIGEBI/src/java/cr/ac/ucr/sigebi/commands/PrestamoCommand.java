/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.SolicitudPrestamo;
import cr.ac.ucr.sigebi.domain.SolicitudDetallePrestamo;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.domain.Usuario;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 *
 * @author alvaro.cascante
 */
public class PrestamoCommand {

    //<editor-fold defaultstate="collapsed" desc="Constantes">
    public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getDefault();
    //</editor-fold>
    
    public class BienDetalle {
        
        private Bien bien;
        private Date fechaInicio;
        private Date fechaFin;

        public BienDetalle() { }

        public BienDetalle(Bien bien, Date fechaInicio, Date fechaFin) {
            this.bien = bien;
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
        }
        
        public Bien getBien() {
            return bien;
        }

        public void setBien(Bien bien) {
            this.bien = bien;
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
    }
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private Long id;
    private UnidadEjecutora unidadEjecutora;
    private Estado estado;
    private Long idTipoEntidad;
    private Long idEntidad;
    private String entidad;
    private Date fecha;
    private String observacion;
    private Usuario usuario;
    private List<Bien> bienesEliminar;  // Bienes a eliminar
    private List<Bien> bienesAgregar;   // Bienes a agregar
    private List<SolicitudDetallePrestamo> detallesEliminar;
    private Map<Long, BienDetalle> bienes;     // Bienes existenetes en la solicitud
    private Map<Long, SolicitudDetallePrestamo> detalles;
    
    private String observacionConfirmacion;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public PrestamoCommand() {
        super();
        this.fecha = this.getDefaultDate();
        this.bienes = new HashMap<Long, BienDetalle>();
        this.detalles = new HashMap<Long, SolicitudDetallePrestamo>();
    }

    public PrestamoCommand(UnidadEjecutora unidadEjecutora, Estado estado, Usuario usuario) {
        this();
        this.unidadEjecutora = unidadEjecutora;
        this.estado = estado;
        this.usuario = usuario;        
    }

    public PrestamoCommand(SolicitudPrestamo prestamo) {
        super();
        this.id = prestamo.getId();
        this.unidadEjecutora = prestamo.getUnidadEjecutora();
        this.estado = prestamo.getEstado();
        this.fecha = prestamo.getFecha();
        this.idTipoEntidad = prestamo.getTipo().getId();
        this.entidad = prestamo.getEntidad();
        this.usuario = prestamo.getUsuario();
        
        this.bienes = new HashMap<Long, BienDetalle>();
        this.detalles = new HashMap<Long, SolicitudDetallePrestamo>();
        for (SolicitudDetallePrestamo detalle : prestamo.getDetallesPrestamo()) {
            this.bienes.put(detalle.getBien().getId(), new BienDetalle(detalle.getBien(), detalle.getFechaInicio(), detalle.getFechaFin()));
            this.detalles.put(detalle.getBien().getId(), detalle);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public SolicitudPrestamo getPrestamo(Tipo tipo) {
        SolicitudPrestamo prestamo = new SolicitudPrestamo();
        prestamo.setId(this.id);
        prestamo.setUnidadEjecutora(this.unidadEjecutora);
        prestamo.setEstado(this.estado);
        prestamo.setFecha(this.fecha);
        prestamo.setTipo(tipo);
        prestamo.setEntidad(this.entidad);
        prestamo.setObservacion(this.observacion);
        prestamo.setUsuario(this.usuario);
        prestamo.setDiscriminator(Constantes.DISCRIMINATOR_SOLICITUD_PRESTAMO);
        
        List<SolicitudDetallePrestamo> listDetalles = new ArrayList<SolicitudDetallePrestamo>(this.detalles.values());
        for (SolicitudDetallePrestamo detalle : listDetalles) {
            BienDetalle bienDetalle = bienes.get(detalle.getBien().getId());
            if(!bienDetalle.getFechaInicio().equals(detalle.getFechaInicio())) {
                detalle.setFechaInicio(bienDetalle.getFechaInicio());
            }
            if(!bienDetalle.getFechaFin().equals(detalle.getFechaFin())) {
                detalle.setFechaFin(bienDetalle.getFechaFin());
            }
        }
        prestamo.setDetallesPrestamo(listDetalles);
        
        for (Bien bien : this.getBienesAgregar()) {
            BienDetalle bienDetalle = bienes.get(bien.getId());
            prestamo.getDetalles().add(new SolicitudDetallePrestamo(prestamo, bien, this.estado, bienDetalle.getFechaInicio(), bienDetalle.getFechaFin()));
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

    public Long getIdTipoEntidad() {
        return idTipoEntidad;
    }

    public void setIdTipoEntidad(Long idTipoEntidad) {
        this.idTipoEntidad = idTipoEntidad;
    }

    public Long getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Long idEntidad) {
        this.idEntidad = idEntidad;
    }

    
    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public List<Bien> getBienesAgregar() {
        if (this.bienesAgregar == null) {
            this.bienesAgregar = new ArrayList<Bien>();
        }
        return bienesAgregar;
    }

    public void setBienesAgregar(List<Bien> bienesAgregar) {
        this.bienesAgregar = bienesAgregar;
    }

    public List<SolicitudDetallePrestamo> getDetallesEliminar() {
        if (this.detallesEliminar == null) {
            this.detallesEliminar = new ArrayList<SolicitudDetallePrestamo>();
        }
        return detallesEliminar;
    }

    public void setDetallesEliminar(List<SolicitudDetallePrestamo> detallesEliminar) {
        this.detallesEliminar = detallesEliminar;
    }

    public Map<Long, BienDetalle> getBienes() {
        return bienes;
    }
    
    public Bien getBien(Long id) {
        return bienes.get(id).getBien();
    }
    
    public List<Bien> getListBienes() {
        List<Bien> b = new ArrayList<Bien>();
        for (Map.Entry<Long, BienDetalle> entry : bienes.entrySet()) {
            BienDetalle value = entry.getValue();
            b.add(value.getBien());
        }
        return b;        
    }

    public List<BienDetalle> getListBienesDetalle() {
        List<BienDetalle> list = new ArrayList<BienDetalle>(bienes.values());
        return list;
    }

    public void addBien(Bien bien, Date fechaInicio, Date fechaFin) {
        this.bienes.put(bien.getId(), new BienDetalle(bien, fechaInicio, fechaFin));
    }
    
    public void setBienes(Map<Long, BienDetalle> bienes) {
        this.bienes = bienes;
    }

    public String getObservacionConfirmacion() {
        return observacionConfirmacion;
    }

    public void setObservacionConfirmacion(String observacionConfirmacion) {
        this.observacionConfirmacion = observacionConfirmacion;
    }

    public Map<Long, SolicitudDetallePrestamo> getDetalles() {
        return detalles;
    }
    
    public List<SolicitudDetallePrestamo> getListDetalles() {
        List<SolicitudDetallePrestamo> list = new ArrayList<SolicitudDetallePrestamo>(detalles.values());
        return list;
    }


    public void setDetalles(Map<Long, SolicitudDetallePrestamo> detalles) {
        this.detalles = detalles;
    }
    //</editor-fold>
}