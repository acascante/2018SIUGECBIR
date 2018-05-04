/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author jorge.serrano
 */
@Entity(name = "AsignarResponsableHistorico")
@Table(name = "SIGEBI_OAF.SIGB_HISTORICO_ASIGNACION")
@SequenceGenerator(name = "SGB_SQ_HIST_ASIG_BIEN"
                , sequenceName = "SIGEBI_OAF.SGB_SQ_HIST_ASIG_BIEN"
                , initialValue = 1
                , allocationSize = 1)
public class AsignarResponsableHistorico extends ObjetoBase implements Serializable{
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_HIST_ASIG_BIEN")
    @Column(name = "ID_HISTORICO")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ID_BIEN", referencedColumnName = "ID_BIEN")
    private Bien bien;
    
    @ManyToOne
    @JoinColumn(name = "ID_RESPONSABLE", referencedColumnName = "ID_USUARIO")
    private Usuario responsable;
    
    @Column(name = "OBSERVACION")
    private String observacion;
    
    
    @Column(name = "FECHA_DESDE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaDesde;
    
    
    @Column(name = "FECHA_HASTA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaHasta;
    
    
    @ManyToOne
    @JoinColumn(name = "ID_UNIDAD_EJECUTORA", referencedColumnName = "ID")
    private UnidadEjecutora unidadEjecutora;
    
    
    
    @ManyToOne
    @JoinColumn(name = "FUNCIONARIO_LIBERA", referencedColumnName = "ID_USUARIO")
    private Usuario funcionarioLibera;
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">

    public AsignarResponsableHistorico() {
    }
    
    //</editor-fold>
    
    
    
    //<editor-fold defaultstate="collapsed" desc="SETs & GETs">

    public Usuario getFuncionarioLibera() {
        return funcionarioLibera;
    }

    public void setFuncionarioLibera(Usuario funcionarioLibera) {
        this.funcionarioLibera = funcionarioLibera;
    }
    
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Bien getBien() {
        return bien;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
    }

    public Usuario getResponsable() {
        return responsable;
    }

    public void setResponsable(Usuario responsable) {
        this.responsable = responsable;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public UnidadEjecutora getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(UnidadEjecutora unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }
   
    //</editor-fold>
    

    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    
    @Override
    public String toString() {
        return "entidades.AsignarResponsableHistorico[id=" + id + "]";
    }
    
    //</editor-fold>
}
