/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author alvaro.cascante
 */
@Entity(name = "ExclusionDetalle")
@Table(name = "SIGEBI_OAF.SIGB_EXCLUSION_DETALLE")
public class ExclusionDetalle extends ObjetoBase implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue
    @Column(name = "ID_EXCLUSION_DETALLE")
    private Long id;
    
    @JoinColumn(name = "ID_SOLICITUD", referencedColumnName = "ID_SOLICITUD")
    @ManyToOne(fetch = FetchType.EAGER)
    private Exclusion exclusion;
    
    @JoinColumn(name = "ID_BIEN", referencedColumnName = "ID_BIEN")
    @ManyToOne(fetch = FetchType.EAGER)
    private Bien bien;
    
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Estado estado;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ExclusionDetalle() {}

    public ExclusionDetalle(Exclusion exclusion, Bien bien, Estado estado) {
        this.exclusion = exclusion;
        this.bien = bien;
        this.estado = estado;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Exclusion getExclusion() {
        return exclusion;
    }

    public void setExclusion(Exclusion exclusion) {
        this.exclusion = exclusion;
    }

    public Bien getBien() {
        return bien;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    //</editor-fold>
}
