/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Exclusion;
import cr.ac.ucr.sigebi.domain.ExclusionDetalle;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
    private Tipo tipo;
    private Date fecha;
    private String observacion;
    private Map<Long, Bien> bienes;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ExclusionCommand() {
        super();
        this.bienes = new HashMap<Long, Bien>();
    }

    public ExclusionCommand(Exclusion exclusion) {
        super();
        this.idExclusion = exclusion.getId();
        this.unidadEjecutora = exclusion.getUnidadEjecutora();
        this.estado = exclusion.getEstado();
        this.fecha = exclusion.getFecha();
        this.tipo = exclusion.getTipoExclusion();
    }
    
    public ExclusionCommand(Exclusion exclusion, List<ExclusionDetalle> detalles) {
        this.idExclusion = exclusion.getId();
        this.unidadEjecutora = exclusion.getUnidadEjecutora();
        this.estado = exclusion.getEstado();
        this.fecha = exclusion.getFecha();
        this.tipo = exclusion.getTipoExclusion();
        
        for (ExclusionDetalle detalle : detalles) {
            this.bienes.put(detalle.getBien().getId(), detalle.getBien());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public Exclusion getExclusion(Estado estado) {
        Exclusion exclusion = new Exclusion();
        exclusion.setUnidadEjecutora(this.unidadEjecutora);
        exclusion.setEstado(this.estado);
        exclusion.setFecha(this.fecha);
        exclusion.setTipoExclusion(this.tipo);
        
        for (Bien bien : this.bienes.values()) {
            exclusion.getDetalles().add(new ExclusionDetalle(exclusion, bien, estado));
        }
        return exclusion;
    }
    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">    
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

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
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
    
    public Map<Long, Bien> getBienes() {
        if (bienes == null){ 
            return new HashMap<Long, Bien>();
        }
        return bienes;
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
    //</editor-fold>
}