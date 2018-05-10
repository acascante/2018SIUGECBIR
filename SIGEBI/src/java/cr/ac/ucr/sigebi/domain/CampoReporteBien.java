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
    @Column(name = "ID")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ID_REPORTE_BIEN", referencedColumnName = "ID_REPORTE_BIEN")
    private ReporteBien reporteBien;
    
    @ManyToOne
    @JoinColumn(name = "ID_CAMPO_BIEN", referencedColumnName = "ID_CAMPO_BIEN")
    private CampoBien campoBien;
    
    @Column(name = "VALOR")
    private String valor;
    
    @Column(name = "TAMANO_COLUMNA")
    private Integer tamanoColumna;
    
    @Column(name = "CAMPO_ORDEN")
    private Integer orden;    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public CampoReporteBien() {}

    public CampoReporteBien(CampoBien campoBien) {
        this.campoBien = campoBien;
    }
    
    public CampoReporteBien(ReporteBien reporteBien, CampoBien campoBien) {
        this.reporteBien = reporteBien;
        this.campoBien = campoBien;
    }
    
    public CampoReporteBien(Long id, ReporteBien reporteBien, CampoBien campoBien, String valor, Integer tamanoColumna, Integer orden) {
        this.id = id;
        this.reporteBien = reporteBien;
        this.campoBien = campoBien;
        this.valor = valor;
        this.tamanoColumna = tamanoColumna;
        this.orden = orden;
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

    public Integer getTamanoColumna() {
        return tamanoColumna;
    }

    public void setTamanoColumna(Integer tamanoColumna) {
        this.tamanoColumna = tamanoColumna;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }
    
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    //</editor-fold>
}