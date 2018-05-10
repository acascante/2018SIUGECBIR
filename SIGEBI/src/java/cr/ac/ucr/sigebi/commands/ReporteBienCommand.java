/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.CampoReporteBien;
import cr.ac.ucr.sigebi.domain.ReporteBien;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author alvaro.cascante
 */
public class ReporteBienCommand {
        
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private Long idReporte;
    private String nombre;
    private Usuario usuario;
    private Long idTipoReporte;
    private String descripcion;
    private Integer tamanoFuente;   
    private Map<Long, CampoReporteBien> camposReporte;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ReporteBienCommand() { 
        super();
        this.idReporte = -1L;
        this.idTipoReporte = -1L;
        this.tamanoFuente = 10;
        this.camposReporte = new HashMap<Long, CampoReporteBien>();
    }
    
    public ReporteBienCommand(Usuario usuario) {
        this();
        this.usuario = usuario;
    }    

    public ReporteBienCommand(ReporteBien reporte) {
        super();
        this.idReporte = reporte.getId();
        this.nombre = reporte.getNombre();
        this.usuario = reporte.getUsuario();
        this.idTipoReporte = reporte.getTipoReporte().getId();
        this.descripcion = reporte.getDescripcion();
        this.tamanoFuente = reporte.getTamanoFuente();
        
        this.camposReporte = new HashMap<Long, CampoReporteBien>();
        for (CampoReporteBien campo : reporte.getCamposReporte()) {
            this.camposReporte.put(campo.getCampoBien().getId(), campo);
        }
    }    
    //</editor-fold>
 
    //<editor-fold defaultstate="collapsed" desc="Metodos">
   public ReporteBien getReporteBien(Tipo tipoReporte) { 
        ReporteBien reporteBien = new ReporteBien();
        reporteBien.setId(this.idReporte);
        reporteBien.setNombre(this.nombre);
        reporteBien.setUsuario(this.usuario);
        reporteBien.setTipoReporte(tipoReporte);
        reporteBien.setDescripcion(this.descripcion);
        reporteBien.setTamanoFuente(this.tamanoFuente);
        
        for (Map.Entry<Long, CampoReporteBien> entry : this.camposReporte.entrySet()) {
           Long key = entry.getKey();
           CampoReporteBien value = entry.getValue();
           reporteBien.getCamposReporte().add(value);
       }        
        return reporteBien;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public Long getIdTipoReporte() {
        return idTipoReporte;
    }

    public void setIdTipoReporte(Long idTipoReporte) {
        this.idTipoReporte = idTipoReporte;
    }

    public Long getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(Long idReporte) {
        this.idReporte = idReporte;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getTamanoFuente() {
        return tamanoFuente;
    }

    public void setTamanoFuente(Integer tamanoFuente) {
        this.tamanoFuente = tamanoFuente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Map<Long, CampoReporteBien> getCamposReporte() {
        return camposReporte;
    }
    
    public void setCamposReporte(Map<Long, CampoReporteBien> camposReporte) {
        this.camposReporte = camposReporte;
    }
    //</editor-fold>
}