/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import com.icesoft.faces.context.effects.JavascriptContext;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.commands.GenerarReporteRolesUsuariosCommand;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.ViewAutorizacionRolUsuarioUnidad;
import cr.ac.ucr.sigebi.domain.reportes.ReporteRolesUsuarios;
import cr.ac.ucr.sigebi.models.UsuarioModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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
@Controller(value = "controllerReporteRolesUsuarios")
@Scope("session")
public class GenerarReporteRolesUsuariosController extends BaseController {
    
    private static class Parametros {
        private static final String UNIDAD_CUSTODIO = "UNIDAD_CUSTODIO";
	private static final String USUARIO = "USUARIO";
        
        private static final String INSTITUCION = "INSTITUCION";
        private static final String NOMBRE_REPORTE = "NOMBRE_REPORTE";
	private static final String VALOR_INSTITUCION = "UNIVERSIDAD DE COSTA RICA";
        private static final String VALOR_NOMBRE_REPORTE = "Reporte de Roles y Usuarios";
    }
    
    @Resource
    private UsuarioModel usuarioModel;
    
    private Map<Long, Tipo> tiposReporte;
    private List<SelectItem> itemsTipoReporte;
    
    private Map<Long, Tipo> ordenReporte;
    private List<SelectItem> itemsOrdenReporte;
    
    private Map<Long, Tipo> columnasOrdenReporte;
    private List<SelectItem> itemsColumnasOrdenReporte;
    
    private GenerarReporteRolesUsuariosCommand command;
    
    private String mensaje;

    public GenerarReporteRolesUsuariosController() {
        super();
    }

    @PostConstruct
    public final void inicializar() {
        //Cargar Tipos 
        List<Tipo> tipos = this.tiposPorDominio(Constantes.DOMINIO_REPORTE);
        if (!tipos.isEmpty()) {
            this.itemsTipoReporte = new ArrayList<SelectItem>();
            this.tiposReporte = new HashMap<Long, Tipo>();
            for (Tipo tipo : tipos) {
                this.tiposReporte.put(tipo.getId(), tipo);
                this.itemsTipoReporte.add(new SelectItem(tipo.getId(), tipo.getNombre()));
            }
        }
        
        List<Tipo> tiposOrdenReporte = this.tiposPorDominio(Constantes.DOMINIO_ORDEN_REPORTE);
        if (!tiposOrdenReporte.isEmpty()) {
            this.itemsOrdenReporte = new ArrayList<SelectItem>();
            this.ordenReporte = new HashMap<Long, Tipo>();
            for (Tipo tipo : tiposOrdenReporte) {
                this.ordenReporte.put(tipo.getId(), tipo);
                this.itemsOrdenReporte.add(new SelectItem(tipo.getId(), tipo.getNombre()));
            }
        }
        
        List<Tipo> tiposColumnasOrdenReporte = this.tiposPorDominio(Constantes.DOMINIO_COLUMNAS_REPORTE_ROLES_USUARIOS);
        if (!tiposColumnasOrdenReporte.isEmpty()) {
            this.itemsColumnasOrdenReporte = new ArrayList<SelectItem>();
            this.columnasOrdenReporte = new HashMap<Long, Tipo>();
            for (Tipo tipo : tiposColumnasOrdenReporte) {
                this.columnasOrdenReporte.put(tipo.getId(), tipo);
                this.itemsColumnasOrdenReporte.add(new SelectItem(tipo.getId(), tipo.getNombre()));
            }
        }
    }
    
    public void inicializarDatos(ActionEvent event) {
        this.mensaje = new String();
        this.command = new GenerarReporteRolesUsuariosCommand();
    }
    
    public String validarForm(UIViewRoot root) {
        if (this.command.getIdTipo().equals(Constantes.DEFAULT_ID)) {
            return Util.getEtiquetas("sigebi.label.reporteBien.error.tipo");
        }
        return Constantes.OK;
    }
    
    public void generarReporte() {
        if (this.command.getIdTipo().equals(Constantes.DEFAULT_ID)) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.label.reporteBien.error.tipo"));
        } else {
            try {
                //reportes/reporteSobrantes.jrxml 
                
                List<ViewAutorizacionRolUsuarioUnidad> usuarios = this.usuarioModel.listarUsuariosGestionProceso();
                if (!usuarios.isEmpty()) {
                    String template;
                    String outputFile = cr.ac.ucr.framework.reporte.componente.utilitario.Util.ConvertirRutas("/reportes/reporteRolesUsuarios");

                    if (this.command.getIdGrupo().equals(1)) { //USUARIO
                        template = cr.ac.ucr.framework.reporte.componente.utilitario.Util.ConvertirRutas("/reportes/reporteUsuarioRoles.jrxml");
                    } else {
                        template = cr.ac.ucr.framework.reporte.componente.utilitario.Util.ConvertirRutas("/reportes/reporteRolUsuarios.jrxml");
                    }
                    ArrayList<ReporteRolesUsuarios> datosReporte = new ArrayList<ReporteRolesUsuarios>();
                    for (ViewAutorizacionRolUsuarioUnidad usuario : usuarios) {
                        datosReporte.add(new ReporteRolesUsuarios(usuario));
                    }
                    
                    JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(datosReporte);
                    JasperReport jasperReport = JasperCompileManager.compileReport(template);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, generarParametros(), beanColDataSource);

                    Tipo tipoReporte = this.tipoPorId(this.command.getIdTipo());
                    if(tipoReporte.getNombre().equals(Constantes.TIPO_REPORTE_PDF)) {
                        JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile + Constantes.TIPO_REPORTE_PDF_EXTENSION);
                        JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "reporte('reporteRolesUsuarios','" + Constantes.TIPO_REPORTE_PDF_EXTENSION + "');");
                    } else {
                        JasperExportManager.exportReportToXmlFile(jasperPrint, outputFile + Constantes.TIPO_REPORTE_EXCELL_EXTENSION, true);
                        JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "reporte('reporteRolesUsuarios','" + Constantes.TIPO_REPORTE_EXCELL_EXTENSION + "');");
                    }

                    Mensaje.agregarInfo("Reporte generado exitosamente");
                } else {
                    Mensaje.agregarErrorAdvertencia("No hay datos para el reporte");
                }            
            } catch (Exception err) {
                Mensaje.agregarErrorAdvertencia(err.getMessage());
                this.mensaje = err.getMessage();
            }
        }
    }
    
    public Map generarParametros() {
        Map parametros = new HashMap();
        parametros.put(Parametros.INSTITUCION, Parametros.VALOR_INSTITUCION);
        parametros.put(Parametros.NOMBRE_REPORTE, Parametros.VALOR_NOMBRE_REPORTE);
        
        parametros.put(Parametros.UNIDAD_CUSTODIO, unidadEjecutora.getDescripcion());
        parametros.put(Parametros.USUARIO, this.usuarioSIGEBI.getNombreCompleto());
        return parametros;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public Map<Long, Tipo> getTiposReporte() {
        return tiposReporte;
    }

    public void setTiposReporte(Map<Long, Tipo> tiposReporte) {
        this.tiposReporte = tiposReporte;
    }

    public List<SelectItem> getItemsTipoReporte() {
        return itemsTipoReporte;
    }

    public void setItemsTipoReporte(List<SelectItem> itemsTipoReporte) {
        this.itemsTipoReporte = itemsTipoReporte;
    }

    public Map<Long, Tipo> getOrdenReporte() {
        return ordenReporte;
    }

    public void setOrdenReporte(Map<Long, Tipo> ordenReporte) {
        this.ordenReporte = ordenReporte;
    }

    public List<SelectItem> getItemsOrdenReporte() {
        return itemsOrdenReporte;
    }
    
    public Map<Long, Tipo> getColumnasOrdenReporte() {
        return columnasOrdenReporte;
    }

    public void setColumnasOrdenReporte(Map<Long, Tipo> columnasOrdenReporte) {
        this.columnasOrdenReporte = columnasOrdenReporte;
    }

    public List<SelectItem> getItemsColumnasOrdenReporte() {
        return itemsColumnasOrdenReporte;
    }

    public void setItemsColumnasOrdenReporte(List<SelectItem> itemsColumnasOrdenReporte) {
        this.itemsColumnasOrdenReporte = itemsColumnasOrdenReporte;
    }

    public GenerarReporteRolesUsuariosCommand getCommand() {
        return command;
    }

    public void setCommand(GenerarReporteRolesUsuariosCommand command) {
        this.command = command;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    //</editor-fold>
}