/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.CampoBien;
import cr.ac.ucr.sigebi.domain.CampoReporteBien;
import cr.ac.ucr.sigebi.domain.ReporteBien;
import cr.ac.ucr.sigebi.domain.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alvaro.cascante
 */
public class ReporteBienCommand {
        
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private String nombre;
    private Long idTipoReporte;
    private Long idReporte;
    private Usuario usuario;
    private List<CampoReporteBien> camposReporte;
    private Map<Long, Boolean> camposSeleccionados;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ReporteBienCommand() { 
        super();
        this.idTipoReporte = -1L;
        this.usuario = new Usuario();
        this.camposSeleccionados = new HashMap<Long, Boolean>();
    }
    
    public ReporteBienCommand(Usuario usuario, List<CampoBien> camposBien) {
        this();
        this.usuario = usuario;
        this.camposReporte = new ArrayList<CampoReporteBien>();
        for (CampoBien campoBien : camposBien) {
            CampoReporteBien campoReporteBien = new CampoReporteBien(campoBien);
            this.camposReporte.add(campoReporteBien);
        }        
    }
    
    public ReporteBienCommand(Long idReporte, Usuario usuario, List<CampoReporteBien> camposReporte) {
        this();
        this.idReporte = idReporte;
        this.usuario = usuario;
        this.camposReporte = camposReporte;
        
        for (CampoReporteBien campoReporteBien : this.camposReporte) {
            if (campoReporteBien.getMostrar()) {
                this.camposSeleccionados.put(campoReporteBien.getId(), Boolean.TRUE);
            }
        }  
    }
    //</editor-fold>
 
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public ReporteBien getReporteBien() { 
        ReporteBien reporteBien = new ReporteBien();
        reporteBien.setNombre(this.nombre);
        //reporteBien.setTipoReporte(tipoReporte);
        reporteBien.setUsuario(this.usuario);
        reporteBien.setCamposReporte(this.camposReporte);
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Map<Long, Boolean> getCamposSeleccionados() {
        return camposSeleccionados;
    }

    public void setCamposSeleccionados(Map<Long, Boolean> camposSeleccionados) {
        this.camposSeleccionados = camposSeleccionados;
    }

    public List<CampoReporteBien> getCamposReporte() {
        return camposReporte;
    }

    public void setCamposReporte(List<CampoReporteBien> camposReporte) {
        this.camposReporte = camposReporte;
    }
    //</editor-fold>
}