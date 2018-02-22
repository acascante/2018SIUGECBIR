/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.SolicitudExclusion;
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
public class ExclusionCommand extends ListarBienesCommand {
    
    //<editor-fold defaultstate="collapsed" desc="Constantes">
    public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getDefault();
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private Long idExclusion;
    private UnidadEjecutora unidadEjecutora;
    private Estado estado;
    private Long idTipo;
    private Date fecha;
    private String observacion;
    private List<Bien> bienes;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ExclusionCommand() {
        super();
        this.bienes = new ArrayList<Bien>();
    }

    public ExclusionCommand(UnidadEjecutora unidadEjecutora, Estado estado) {
        super();
        this.unidadEjecutora = unidadEjecutora;
        this.estado = estado;
        this.fecha = getDefaultDate();
        this.bienes = new ArrayList<Bien>();
    }

    public ExclusionCommand(SolicitudExclusion exclusion) {
        super();
        this.idExclusion = exclusion.getId();
        this.unidadEjecutora = exclusion.getUnidadEjecutora();
        this.estado = exclusion.getEstado();
        this.fecha = exclusion.getFecha();
        this.idTipo = exclusion.getTipoExclusion().getId();
        for (SolicitudDetalle detalle : exclusion.getDetalles()) {
            this.bienes.add(detalle.getBien());
        }
    }
    
    public ExclusionCommand(SolicitudExclusion exclusion, List<SolicitudDetalle> detalles) {
        this.idExclusion = exclusion.getId();
        this.unidadEjecutora = exclusion.getUnidadEjecutora();
        this.estado = exclusion.getEstado();
        this.fecha = exclusion.getFecha();
        this.idTipo = exclusion.getTipoExclusion().getId();
        
        for (SolicitudDetalle detalle : detalles) {
            this.bienes.add(detalle.getBien());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public SolicitudExclusion getExclusion(Tipo tipo) {
        SolicitudExclusion exclusion = new SolicitudExclusion();
        exclusion.setUnidadEjecutora(this.unidadEjecutora);
        exclusion.setEstado(this.estado);
        exclusion.setFecha(this.fecha);
        exclusion.setTipoExclusion(tipo);
        exclusion.setDiscriminator(Constantes.DISCRIMINATOR_SOLICITUD_EXCLUSION);
        for (Bien bien : this.bienes) {
            exclusion.getDetalles().add(new SolicitudDetalle(exclusion, bien, estado));
        }
        return exclusion;
    }
    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">    
    private Date getDefaultDate() {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance(Constantes.DEFAULT_TIME_ZONE);
        calendar.setTime(today);
        return calendar.getTime();
    }
    
    public Long getIdExclusion() {
        return idExclusion;
    }

    public void setIdExclusion(Long idExclusion) {
        this.idExclusion = idExclusion;
    }

    public UnidadEjecutora getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(UnidadEjecutora unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }

    public Long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Long idTipo) {
        this.idTipo = idTipo;
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