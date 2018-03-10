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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "InterfazAdjunto")
@Table(name = "SIGEBI_OAF.SIGB_INTERFAZ_ADJUNTO")
@SequenceGenerator(name = "SGB_SQ_INTERFAZ_ADJUNTO", sequenceName = "SIGEBI_OAF.SGB_SQ_INTERFAZ_ADJUNTO", initialValue = 1, allocationSize = 1)
public class InterfazAdjunto extends ObjetoBase implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_INTERFAZ_ADJUNTO")
    @Column(name = "ID_INTERFAZ_ADJUNTO")
    private Long id;

    @Column(name = "ID_ORIGEN_TECNICO")
    private Integer idOrigenTecnico;

    @Column(name = "IDENTIFICACION_BIEN")
    private String identificacionBien;

    @Column(name = "IDENTIFICACION_ORIGEN")
    private String identificacionOrigen;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "TIPO")
    private Integer tipo;

    @Column(name = "URL")
    private String url;

    @Column(name = "CONSECUTIVO")
    private Integer consecutivo;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public InterfazAdjunto() {
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificacionBien() {
        return identificacionBien;
    }

    public void setIdentificacionBien(String identificacionBien) {
        this.identificacionBien = identificacionBien;
    }

    public Integer getIdOrigenTecnico() {
        return idOrigenTecnico;
    }

    public void setIdOrigenTecnico(Integer idOrigenTecnico) {
        this.idOrigenTecnico = idOrigenTecnico;
    }

    public String getIdentificacionOrigen() {
        return identificacionOrigen;
    }

    public void setIdentificacionOrigen(String identificacionOrigen) {
        this.identificacionOrigen = identificacionOrigen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Integer consecutivo) {
        this.consecutivo = consecutivo;
    }

    //</editor-fold>
}
