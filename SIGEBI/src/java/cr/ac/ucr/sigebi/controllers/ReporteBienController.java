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
import cr.ac.ucr.sigebi.domain.CampoReporteBien;
import cr.ac.ucr.sigebi.domain.ReporteBien;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.models.CampoBienModel;
import cr.ac.ucr.sigebi.models.CampoReporteBienModel;
import cr.ac.ucr.sigebi.models.ReporteBienModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.model.SelectItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author alvaro.cascante
 */
@Controller(value = "controllerReporteBien")
@Scope("session")
public class ReporteBienController extends BaseController {
    
    @Resource private CampoBienModel campoBienModel;
    @Resource private CampoReporteBienModel campoReporteBienModel;
    @Resource private ReporteBienModel reporteBienModel;
    
    private Map<Long, ReporteBien> reportes;
    private List<SelectItem> itemsTipoReporte;
    private List<SelectItem> itemsReporte;
    private ReporteBienCommand command;
    
    private String mensaje;

    private Boolean reporteRegistrado;
    private Boolean visiblePanelNombreReporte;
    private Boolean visibleBotonCopiar;
    private Boolean visibleBotonEliminar;
  
    public ReporteBienController() {
        super();
    }

    private void inicializarNuevo() {
        this.command = new ReporteBienCommand(this.usuarioSIGEBI, this.campoBienModel.listar());
        this.reporteRegistrado = false;
        this.visiblePanelNombreReporte = false;
        this.visibleBotonCopiar = false;
        this.visibleBotonEliminar = false;
        inicializarDatos();
    }
    
    private void inicializarReporte() {
        ReporteBien reporteBien = reportes.get(this.command.getIdReporte());
        this.command = new ReporteBienCommand(this.command.getIdReporte(), usuarioSIGEBI, campoReporteBienModel.listarPorUsuario(this.usuarioSIGEBI, reporteBien));
        this.reporteRegistrado = true;
    }
      
    private void inicializarDatos() {
        this.mensaje = new String();

        List<Tipo> tipos = this.tiposPorDominio(Constantes.DOMINIO_REPORTE);
        if (!tipos.isEmpty()) {
            this.itemsTipoReporte = new ArrayList<SelectItem>();

            for (Tipo item : tipos) {
                this.itemsTipoReporte.add(new SelectItem(item.getId(), item.getNombre()));
            }
        }
        
        List<ReporteBien> reportesBien = this.reporteBienModel.listarPorUsuario(usuarioSIGEBI);
        if (!reportesBien.isEmpty()) {
            this.itemsReporte = new ArrayList<SelectItem>();
            this.reportes = new HashMap<Long, ReporteBien>();
            for (ReporteBien reporteBien : reportesBien) {
                this.itemsReporte.add(new SelectItem(reporteBien.getId(), reporteBien.getNombre()));
                this.reportes.put(reporteBien.getId(), reporteBien);
            }
        }
    }
    
    public void guardarReporte() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            UIViewRoot root = context.getViewRoot();
            String messageValidacion = validarForm(root);
            if (Constantes.OK.equals(messageValidacion)) {
                ReporteBien reporteBien = this.command.getReporteBien();
                this.reporteBienModel.salvar(reporteBien);
                this.campoReporteBienModel.salvarCampos(reporteBien.getCamposReporte());
                this.visibleBotonCopiar = true;
                this.visibleBotonEliminar = true;
                Mensaje.agregarInfo("Reporte guardado exitosamente");
            } else {
                Mensaje.agregarErrorAdvertencia(messageValidacion);
            }
        } catch (FWExcepcion err) {
            this.mensaje = err.getMessage();
        }
    }
    
    public String validarForm(UIViewRoot root) {
        if (this.command.getNombre().isEmpty()) {
            return Util.getEtiquetas("sigebi.label.notificaciones.error.asunto.nulo");
        }
        
        if (this.command.getIdTipoReporte().equals(Constantes.DEFAULT_ID)) {
            return Util.getEtiquetas("sigebi.label.notificaciones.error.correo.nulo");
        }
        
        if(!validarCampos()) {
            return Util.getEtiquetas("sigebi.label.notificaciones.error.correo.nulo");
        }
        
        return Constantes.OK;
    }
    
    public boolean validarCampos() {
        for (CampoReporteBien campoReporteBien : this.command.getCamposReporte()) {
            if (campoReporteBien.getMostrar()) {
                return true;
            }
        }
        return false;
    }
    
    public void nuevoRegistro(ActionEvent event) {
        try{
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            inicializarNuevo();
            this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
            Util.navegar(Constantes.VISTA_REPORTE_BIEN);
        } catch (FWExcepcion err) {
            this.mensaje = err.getMessage();
        }
    }
  
    public void seleccionarReporte(ActionEvent event) {
        try{
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            inicializarReporte();
        } catch (FWExcepcion err) {
            this.mensaje = err.getMessage();
        }
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
            ReporteBien reporteBien = this.command.getReporteBien();
            this.campoReporteBienModel.eliminar(reporteBien.getCamposReporte());
            this.reporteBienModel.eliminar(reporteBien);
            Mensaje.agregarInfo("Reporte guardado exitosamente");
        } catch (FWExcepcion err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
            this.mensaje = err.getMessage();
        }
    }
    
    public void generarReporte() {
        try {
            ReporteBien reporteBien = this.command.getReporteBien();
            
            Mensaje.agregarInfo("Reporte generado exitosamente");
        } catch (FWExcepcion err) {
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

    public Boolean getVisiblePanelNombreReporte() {
        return visiblePanelNombreReporte;
    }

    public void setVisiblePanelNombreReporte(Boolean visiblePanelNombreReporte) {
        this.visiblePanelNombreReporte = visiblePanelNombreReporte;
    }

    public Boolean getVisibleBotonCopiar() {
        return visibleBotonCopiar;
    }

    public void setVisibleBotonCopiar(Boolean visibleBotonCopiar) {
        this.visibleBotonCopiar = visibleBotonCopiar;
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
