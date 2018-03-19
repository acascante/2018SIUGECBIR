/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author alvaro.cascante
 */
@Entity(name = "CampoReporteBien")
@Table(name = "SIGEBI_OAF.SIGB_CAMPO_REPORTE_BIEN")
@SequenceGenerator(name="sqCampoReporteBien", sequenceName = "SIGEBI_OAF.SGB_SQ_CAMPO_REPORTE_BIEN", initialValue=1, allocationSize=1)
public class CampoReporteBien implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sqCampoReporteBien")
    @Column(name = "ID_CAMPO_REPORTE_BIEN")
    private Long id;
    
    @Column(name = "ID_REPORTE_BIEN")
    @ManyToOne
    @JoinColumn(name = "REPORTE_BIEN", referencedColumnName = "ID_REPORTE_BIEN")
    private ReporteBien reporteBien;
    
    @ManyToOne
    @JoinColumn(name = "CAMPO_BIEN", referencedColumnName = "ID_CAMPO_BIEN")
    private CampoBien campoBien;
    
    @Column(name = "MOSTRAR")
    private Boolean mostrar;
    
    @Column(name = "VALOR")
    private Object valor;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public CampoReporteBien() {}
    
    public CampoReporteBien(Long id, ReporteBien reporteBien, CampoBien campoBien, Boolean mostrar, Object valor) {
        this.id = id;
        this.reporteBien = reporteBien;
        this.campoBien = campoBien;
        this.mostrar = mostrar;
        this.valor = valor;
    }
    
    public CampoReporteBien(CampoBien campobien) {
        this.campoBien = campobien;
        this.mostrar = false;
        this.valor = null;
    }
    //</editor-fold>  

    //<editor-fold defaultstate="collapsed" desc="GET's Set't">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReporteBien getReporteBien() {
        return reporteBien;
    }

    public void setReporteBien(ReporteBien reporteBien) {
        this.reporteBien = reporteBien;
    }

    public CampoBien getCampoBien() {
        return campoBien;
    }

    public void setCampoBien(CampoBien campoBien) {
        this.campoBien = campoBien;
    }

    public Boolean getMostrar() {
        return mostrar;
    }

    public void setMostrar(Boolean mostrar) {
        this.mostrar = mostrar;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }
    //</editor-fold>
}
