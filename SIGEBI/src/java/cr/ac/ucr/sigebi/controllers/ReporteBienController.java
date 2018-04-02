/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.sigebi.commands.ReporteBienCommand;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.CampoBien;
import cr.ac.ucr.sigebi.domain.CampoReporteBien;
import cr.ac.ucr.sigebi.domain.ReporteBien;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.CampoBienModel;
import cr.ac.ucr.sigebi.models.CampoReporteBienModel;
import cr.ac.ucr.sigebi.models.ReporteBienModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author alvaro.cascante
 */
@Controller(value = "controllerReporteBien")
@Scope("session")
public class ReporteBienController extends BaseController {
    
    @Resource private BienModel bienModel;
    @Resource private CampoBienModel campoBienModel;
    @Resource private CampoReporteBienModel campoReporteBienModel;
    @Resource private ReporteBienModel reporteBienModel;    
    
    private Map<Long, ReporteBien> reportes;
    private List<SelectItem> itemsReporte;
    
    private Map<Long, Tipo> tiposReporte;
    private List<SelectItem> itemsTipoReporte;
    
    private Map<Long, CampoReporteBien> camposReporte;
    private Map<Long, Boolean> camposSeleccionados;
    
    private ReporteBienCommand command;
    
    private String mensaje;

    private Boolean reporteRegistrado;
    private Boolean visiblePanelNombreReporte;
    private Boolean visibleBotonEliminar;
  
    public ReporteBienController() {
        super();
        this.inicializarDatos();
    }

    @PostConstruct
    public final void inicializar() {
        //Cargar Tipos de Reporte
        List<Tipo> tipos = this.tiposPorDominio(Constantes.DOMINIO_REPORTE);
        if (!tipos.isEmpty()) {
            this.itemsTipoReporte = new ArrayList<SelectItem>();
            this.tiposReporte = new HashMap<Long, Tipo>();
            for (Tipo tipo : tipos) {
                this.tiposReporte.put(tipo.getId(), tipo);
                this.itemsTipoReporte.add(new SelectItem(tipo.getId(), tipo.getNombre()));
            }
        }
        
        cargarReportes();
        
        List<CampoBien> campos = this.campoBienModel.listar();
        if (!campos.isEmpty()) {
            this.camposReporte = new HashMap<Long, CampoReporteBien>();
            for (CampoBien campo : campos) {
                this.camposReporte.put(campo.getId(), new CampoReporteBien(campo));
            }
        }
    }
    
    private void cargarReportes() {
        //Cargar Reportes del Usuario
        
        List<ReporteBien> reportesBien = this.reporteBienModel.listarPorUsuario(usuarioSIGEBI);
        if (!reportesBien.isEmpty()) {
            this.itemsReporte = new ArrayList<SelectItem>();
            this.reportes = new HashMap<Long, ReporteBien>();
            this.itemsReporte.clear();
            this.reportes.clear();
            for (ReporteBien reporteBien : reportesBien) {
                this.itemsReporte.add(new SelectItem(reporteBien.getId(), reporteBien.getNombre()));
                this.reportes.put(reporteBien.getId(), reporteBien);
            }
        }
    }
    
    private void inicializarDatos() {
        this.reporteRegistrado = false;
        this.visiblePanelNombreReporte = false;
        this.visibleBotonEliminar = false;
        this.mensaje = new String();
        this.command = new ReporteBienCommand(usuarioSIGEBI);
        this.camposSeleccionados = new HashMap<Long, Boolean>();
    }
    
    private void inicializarReporte() {
        ReporteBien reporteBien = reportes.get(this.command.getIdReporte());
        this.command.setNombre(reporteBien.getNombre());
        this.command.setIdReporte(reporteBien.getId());
        this.command.setIdTipoReporte(reporteBien.getTipoReporte().getId());
        this.command.setDescripcion(reporteBien.getDescripcion());
        
        List<CampoReporteBien> campos = this.campoReporteBienModel.listarPorReporte(reporteBien);
        if (!campos.isEmpty()) {
            this.camposReporte.clear();
            for (CampoReporteBien campo : campos) {
                this.camposReporte.put(campo.getCampoBien().getId(), campo);
                this.camposSeleccionados.put(campo.getCampoBien().getId(), campo.getMostrar());
            }
        }
        
        this.reporteRegistrado = true;
        this.visibleBotonEliminar = true;
    }      
    
    public void guardarReporte() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            UIViewRoot root = context.getViewRoot();
            String messageValidacion = validarForm(root);
            if (Constantes.OK.equals(messageValidacion)) {
                Tipo tipo = this.tipoPorId(this.command.getIdTipoReporte());
                ReporteBien reporteBien = this.command.getReporteBien(tipo, null);
                this.reporteBienModel.salvar(reporteBien);
                
                List<CampoReporteBien> campos = getCamposReporteBien(reporteBien);
                this.campoReporteBienModel.salvar(campos);
                
                this.visibleBotonEliminar = true;
                this.visiblePanelNombreReporte = false;
                
                cargarReportes();
                this.command.setIdReporte(reporteBien.getId());
                Mensaje.agregarInfo("Reporte guardado exitosamente");
            } else {
                Mensaje.agregarErrorAdvertencia(messageValidacion);
            }
        } catch (FWExcepcion err) {
            this.mensaje = err.getMessage();
        }
    }
    
    private List<CampoReporteBien> getCamposReporteBien(ReporteBien reporte) {
        List<CampoReporteBien> campos = new ArrayList<CampoReporteBien>();
        for (CampoReporteBien campo : this.camposReporte.values()) {
            if (this.camposSeleccionados.get(campo.getCampoBien().getId())) {
                campo.setMostrar(true);
            } else {
                campo.setMostrar(false);
            }
            campo.setReporteBien(reporte);
            campos.add(campo);
        }
        return campos;
    }
    
    public String validarForm(UIViewRoot root) {
        if (this.command.getNombre().isEmpty()) {
            return Util.getEtiquetas("sigebi.label.reporteBien.error.nombre");
        }
        
        if (this.command.getIdTipoReporte().equals(Constantes.DEFAULT_ID)) {
            return Util.getEtiquetas("sigebi.label.reporteBien.error.tipo");
        }
        
        if(!validarCampos()) {
            return Util.getEtiquetas("sigebi.label.reporteBien.error.campos");
        }
        
        return Constantes.OK;
    }
    
    public boolean validarCampos() {
        if (!this.camposSeleccionados.isEmpty()) {
            for (Map.Entry<Long, Boolean> entry : this.camposSeleccionados.entrySet()) {
                if (entry.getValue()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void seleccionarReporte(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        inicializarReporte();
    }
  
    public void abrirPanelNombreReporte() {
        this.visiblePanelNombreReporte = true;
    }
    
    public void abrirPanelNombreReporteCopiar() {
        this.command.setIdReporte(null);
        this.visiblePanelNombreReporte = true;
    }
    
    public void cerrarPanelNombreReporte() {
        this.visiblePanelNombreReporte = false;
    }
    
    public void eliminarReporte() {
        try {
            Tipo tipo = this.tipoPorId(this.command.getIdTipoReporte());
            ReporteBien reporteBien = this.command.getReporteBien(tipo, null);            
            List<CampoReporteBien> campos = this.campoReporteBienModel.listarPorReporte(reporteBien);
            this.campoReporteBienModel.eliminar(campos);
            this.reporteBienModel.eliminar(reporteBien);
            
            cargarReportes();
            inicializarDatos();
            Mensaje.agregarInfo("Reporte eliminado exitosamente");
        } catch (FWExcepcion err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
            this.mensaje = err.getMessage();
        }
    }
    
    public void generarReporte() {
        try {
            //reportes/reporteBienes.jrxml
            List<Bien> bienes = this.bienModel.listar();
            if (!bienes.isEmpty()) {
                String template = cr.ac.ucr.framework.reporte.componente.utilitario.Util.ConvertirRutas("/reportes/reporteBienes.jrxml");
                String jasperFile = cr.ac.ucr.framework.reporte.componente.utilitario.Util.ConvertirRutas("/reportes/reporteBienes.jasper");
                String outputFile = cr.ac.ucr.framework.reporte.componente.utilitario.Util.ConvertirRutas("/reportes/reporteBienes.pdf");
                
                JasperReport jasperReport = JasperCompileManager.compileReport(template);
                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(bienes);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
                JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);
                //String outputFile = "C://reportes/reporteBienes.pdf";
//                JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(jasperFile);
//                if (reporte == null) {
//                    String templateFile = JRLoader.loadObjectFromFile(template).toString();
//                    JasperCompileManager.compileReport(templateFile);                    
//                    jasperFile = cr.ac.ucr.framework.reporte.componente.utilitario.Util.ConvertirRutas("/reportes/reporteBienes.jasper");
//                }
//                Map parameters = new HashMap();
//                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(bienes);
//                String jasperPrint = JasperFillManager.fillReportToFile(jasperFile, parameters, dataSource);
                
            }
            Mensaje.agregarInfo("Reporte generado exitosamente");
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
            this.mensaje = err.getMessage();
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public Map<Long, ReporteBien> getReportes() {
        return reportes;
    }

    public void setReportes(Map<Long, ReporteBien> reportes) {
        this.reportes = reportes;
    }

    public List<SelectItem> getItemsTipoReporte() {
        return itemsTipoReporte;
    }

    public void setItemsTipoReporte(List<SelectItem> itemsTipoReporte) {
        this.itemsTipoReporte = itemsTipoReporte;
    }

    public List<SelectItem> getItemsReporte() {
        return itemsReporte;
    }

    public void setItemsReporte(List<SelectItem> itemsReporte) {
        this.itemsReporte = itemsReporte;
    }

    public ReporteBienCommand getCommand() {
        return command;
    }

    public void setCommand(ReporteBienCommand command) {
        this.command = command;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Map<Long, Tipo> getTiposReporte() {
        return tiposReporte;
    }

    public void setTiposReporte(Map<Long, Tipo> tiposReporte) {
        this.tiposReporte = tiposReporte;
    }

    public Map<Long, CampoReporteBien> getCamposReporte() {
        return camposReporte;
    }
    
    public List<CampoReporteBien> getListCamposReporte() {
        List<CampoReporteBien> list = new ArrayList<CampoReporteBien>(camposReporte.values());
        return list;
    }

    public void setCamposReporte(Map<Long, CampoReporteBien> camposReporte) {
        this.camposReporte = camposReporte;
    }

    public Map<Long, Boolean> getCamposSeleccionados() {
        return camposSeleccionados;
    }

    public void setCamposSeleccionados(Map<Long, Boolean> camposSeleccionados) {
        this.camposSeleccionados = camposSeleccionados;
    }

    public Boolean getVisiblePanelNombreReporte() {
        return visiblePanelNombreReporte;
    }

    public void setVisiblePanelNombreReporte(Boolean visiblePanelNombreReporte) {
        this.visiblePanelNombreReporte = visiblePanelNombreReporte;
    }

    public Boolean getReporteRegistrado() {
        return reporteRegistrado;
    }

    public Boolean getVisibleBotonEliminar() {
        return visibleBotonEliminar;
    }

    public void setVisibleBotonEliminar(Boolean visibleBotonEliminar) {
        this.visibleBotonEliminar = visibleBotonEliminar;
    }

    public void setReporteRegistrado(Boolean reporteRegistrado) {
        this.reporteRegistrado = reporteRegistrado;
    }
    //</editor-fold>
}
