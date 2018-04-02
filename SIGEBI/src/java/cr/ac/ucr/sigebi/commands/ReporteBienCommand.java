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
import java.util.List;

/**
 *
 * @author alvaro.cascante
 */
public class ReporteBienCommand {
        
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private String nombre;
    private String descripcion;
    private Long idTipoReporte;
    private Long idReporte;
    private Usuario usuario;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ReporteBienCommand() { 
        super();
        this.idReporte = -1L;
        this.idTipoReporte = -1L;
    }
    
    public ReporteBienCommand(Usuario usuario) {
        this();
        this.usuario = usuario;
    }    
    //</editor-fold>
 
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public ReporteBien getReporteBien(Tipo tipoReporte, List<CampoReporteBien> campos) { 
        ReporteBien reporteBien = new ReporteBien();
        reporteBien.setId(this.idReporte);
        reporteBien.setDescripcion(this.descripcion);
        reporteBien.setNombre(this.nombre);
        reporteBien.setTipoReporte(tipoReporte);
        reporteBien.setUsuario(this.usuario);
        reporteBien.setCamposReporte(campos);
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    //</editor-fold>
}