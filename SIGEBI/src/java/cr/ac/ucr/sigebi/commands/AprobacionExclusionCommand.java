/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.DocumentoAprobacionExclusion;
import cr.ac.ucr.sigebi.domain.DocumentoDetalle;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
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
public class AprobacionExclusionCommand {

    //<editor-fold defaultstate="collapsed" desc="Constantes">
    public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getDefault();
    //</editor-fold>
    
    public class BienDetalle {
        
        private Bien bien;

        public BienDetalle() { }

        public BienDetalle(Bien bien) {
            this.bien = bien;
        }
        
        private Date getDefaultDate() {
            Date today = new Date();
            Calendar calendar = Calendar.getInstance(Constantes.DEFAULT_TIME_ZONE);
            calendar.setTime(today);
            return calendar.getTime();
        }

        public Bien getBien() {
            return bien;
        }

        public void setBien(Bien bien) {
            this.bien = bien;
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private Long id;
    private String autorizacion;
    private String cedula;
    private String razonSocial;

    private Date fecha;
    private Estado estado;
    private UnidadEjecutora unidadEjecutora;
    
    private String observacionConfirmacion;
    
    private List<Bien> bienesEliminar;  // Bienes a eliminar
    private Set<Bien> bienesAgregar;   // Bienes a agregar
    private Map<Long, BienDetalle> bienes;     // Bienes existenetes en la solicitud
    private List<DocumentoDetalle> detallesEliminar;
    private Map<Long, DocumentoDetalle> detalles;    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public AprobacionExclusionCommand() {
        super();
        this.fecha = this.getDefaultDate();
        this.bienes = new HashMap<Long, BienDetalle>();
        this.detalles = new HashMap<Long, DocumentoDetalle>();
    }

    public AprobacionExclusionCommand(UnidadEjecutora unidadEjecutora, Estado estado) {
        this();
        this.unidadEjecutora = unidadEjecutora;
        this.estado = estado;
    }

    public AprobacionExclusionCommand(DocumentoAprobacionExclusion documento) {
        super();
        this.id = documento.getId();
        this.autorizacion = documento.getAutorizacion();
        this.cedula = documento.getCedula();
        this.razonSocial = documento.getRazonSocial();
        
        this.unidadEjecutora = documento.getUnidadEjecutora();
        this.estado = documento.getEstado();
        this.fecha = documento.getFecha();
        
        this.bienes = new HashMap<Long, BienDetalle>();
        this.detalles = new HashMap<Long, DocumentoDetalle>();
        for (DocumentoDetalle detalle : documento.getDetallesDocumento()) {
            this.bienes.put(detalle.getBien().getId(), new BienDetalle(detalle.getBien()));
            this.detalles.put(detalle.getBien().getId(), detalle);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public DocumentoAprobacionExclusion getDocumentoAprobacion() {
        DocumentoAprobacionExclusion documento = new DocumentoAprobacionExclusion();
        documento.setId(this.id);
        documento.setAutorizacion(autorizacion);
        documento.setCedula(cedula);
        documento.setRazonSocial(razonSocial);
        
        
        documento.setUnidadEjecutora(this.unidadEjecutora);
        documento.setEstado(this.estado);
        documento.setFecha(this.fecha);
        documento.setDiscriminator(Constantes.DISCRIMINATOR_DOCUMENTO_APROBACION);
        
        List<DocumentoDetalle> listDetalles = new ArrayList<DocumentoDetalle>(this.detalles.values());
        
        for (Bien bien : this.getBienesAgregar()) {
            BienDetalle bienDetalle = this.bienes.get(bien.getId());
            listDetalles.add(new DocumentoDetalle(documento, bienDetalle.getBien()));
        }
         
        for (DocumentoDetalle detalle : listDetalles) { //Actualizo fecha de los detalles en caso que se haya modificado
            if (!this.bienes.containsKey(detalle.getBien().getId())) {
                this.getDetallesEliminar().add(detalle);
                this.detalles.remove(detalle.getBien().getId());
            }
        }
        documento.setDetallesDocumento(listDetalles);
        return documento;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public List<DocumentoDetalle> getDetallesEliminar() {
        if (this.detallesEliminar == null) {
            this.detallesEliminar = new ArrayList<DocumentoDetalle>();
        }
        return detallesEliminar;
    }

    public void setDetallesEliminar(List<DocumentoDetalle> detallesEliminar) {
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
        List<BienDetalle> list = new ArrayList<BienDetalle>(this.bienes.values());
        return list;
    }

    public void addBien(Bien bien, Date fechaInicio, Date fechaFin) {
        this.bienes.put(bien.getId(), new BienDetalle(bien));
    }
    
    public void addBien(Bien bien) {
        this.bienes.put(bien.getId(), new BienDetalle(bien));
    }
    
    public void setBienes(Map<Long, BienDetalle> bienes) {
        this.bienes = bienes;
    }

    public Map<Long, DocumentoDetalle> getDetalles() {
        return detalles;
    }
    
    public List<DocumentoDetalle> getListDetalles() {
        List<DocumentoDetalle> list = new ArrayList<DocumentoDetalle>(detalles.values());
        return list;
    }

    public void setDetalles(Map<Long, DocumentoDetalle> detalles) {
        this.detalles = detalles;
    }
    
    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getObservacionConfirmacion() {
        return observacionConfirmacion;
    }

    public void setObservacionConfirmacion(String observacionConfirmacion) {
        this.observacionConfirmacion = observacionConfirmacion;
    }
    //</editor-fold>
}