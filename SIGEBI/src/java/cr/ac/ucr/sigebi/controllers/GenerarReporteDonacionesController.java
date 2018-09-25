/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import com.icesoft.faces.context.effects.JavascriptContext;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.sigebi.commands.ReporteDonacionesCommand;
import cr.ac.ucr.sigebi.domain.SolicitudDetalle;
import cr.ac.ucr.sigebi.domain.SolicitudDonacion;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.reportes.ReporteDonaciones;
import cr.ac.ucr.sigebi.models.SolicitudModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.jnlp.FileContents;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author alvaro.cascante
 */
@Controller(value = "controllerReporteDonaciones")
@Scope("session")
public class GenerarReporteDonacionesController extends BaseController {
    
    private static class Parametros {
        private static final String UNIDAD_CUSTODIO = "UNIDAD_CUSTODIO";
	private static final String USUARIO = "USUARIO";
        
        private static final String IDENTIFICACION_BIEN = "IDENTIFICACION_BIEN";
        private static final String FECHA_INICIO = "FECHA_INICIO";
        private static final String FECHA_FIN = "FECHA_FIN";
        private static final String ID_SOLICITUD = "ID_SOLICITUD";
        
        private static final String INSTITUCION = "INSTITUCION";
        private static final String NOMBRE_REPORTE = "NOMBRE_REPORTE";
	private static final String VALOR_INSTITUCION = "UNIVERSIDAD DE COSTA RICA";
        private static final String VALOR_NOMBRE_REPORTE = "Reporte de Donaciones";
    }
    
    @Resource private SolicitudModel solicitudModel;
    
    private Map<Long, Tipo> tiposReporte;
    private List<SelectItem> itemsTipoReporte;
    
    private ReporteDonacionesCommand command;

    public GenerarReporteDonacionesController() {
        super();
        inicializarDatos();
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
    }
    
    private void inicializarDatos() {
        this.command = new ReporteDonacionesCommand();
    }
    
    public void generarReporte() {
        try {
            //reportes/reporteSobrantes.jrxml 

            Tipo orden = this.tipoPorDominioValor(Constantes.DOMINIO_ORDEN_REPORTE, this.command.getIdOrden());
            Tipo orden1 = this.tipoPorDominioValor(Constantes.DOMINIO_COLUMNAS_REPORTE_DONACIONES, this.command.getIdOrden1());
            Tipo orden2 = this.tipoPorDominioValor(Constantes.DOMINIO_COLUMNAS_REPORTE_DONACIONES, this.command.getIdOrden2());
            Tipo orden3 = this.tipoPorDominioValor(Constantes.DOMINIO_COLUMNAS_REPORTE_DONACIONES, this.command.getIdOrden3());
            
            List<SolicitudDetalle> detalles = this.solicitudModel.listarDetallesSalidas(this.command.getFltIdSolicitud(), this.command.getFltIdentificaionBien(), this.command.getFltFechaInicio(), this.command.getFltFechaFin(), orden.getNombre(), 
                    orden1 != null ? orden1.getNombre() : null, orden2 != null ? orden3.getNombre() : null, orden3 != null ? orden3.getNombre() : null);
            if (!detalles.isEmpty()) {
                String template = cr.ac.ucr.framework.reporte.componente.utilitario.Util.ConvertirRutas("/reportes/reporteDonaciones.jrxml");
                String outputFile = cr.ac.ucr.framework.reporte.componente.utilitario.Util.ConvertirRutas("/reportes/reporteDonaciones");
                ArrayList<ReporteDonaciones> datosReporte = new ArrayList<ReporteDonaciones>();
                for (SolicitudDetalle detalle : detalles) {
                    SolicitudDonacion solicitudDonacion = (SolicitudDonacion)solicitudModel.buscarPorId(detalle.getSolicitud().getId());
                    datosReporte.add(new ReporteDonaciones(detalle, solicitudDonacion));
                }
                
                JasperReport jasperReport = JasperCompileManager.compileReport(template);
                JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(datosReporte);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, generarParametros(), beanColDataSource);
                
                Tipo tipoReporte = this.tipoPorId(this.command.getIdTipo());
                
                if(tipoReporte.getNombre().equals(Constantes.TIPO_REPORTE_EXCEL)) {
                    generarReporteXls(jasperPrint, outputFile + Constantes.TIPO_REPORTE_XLS_EXTENSION);
                    JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "reporte('reporteDonaciones','" + Constantes.TIPO_REPORTE_XLS_EXTENSION + "');");
                } else {
                    generarReportePdf(jasperPrint, outputFile + Constantes.TIPO_REPORTE_PDF_EXTENSION);
                    JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "reporte('reporteDonaciones','" + Constantes.TIPO_REPORTE_PDF_EXTENSION + "');");
                }
                
                Mensaje.agregarInfo("Reporte generado exitosamente");
            } else {
                Mensaje.agregarErrorAdvertencia("No hay datos para el reporte");
            }            
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia("No se puede generar el reporte en este momento, intente de nuevo");
        }
    }
    
    private void generarReportePdf(JasperPrint print, String file) throws JRException  {
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(print));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
        
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        exporter.setConfiguration(configuration);
        exporter.exportReport();
    }
    
    private void generarReporteXls(JasperPrint print, String file) throws JRException  {
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(print));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
        
        SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
        configuration.setOnePagePerSheet(Boolean.TRUE);
        configuration.setDetectCellType(Boolean.TRUE);
        configuration.setCollapseRowSpan(Boolean.FALSE);
        exporter.setConfiguration(configuration);
        exporter.exportReport();
    }
    
    public Map generarParametros() {
        Map parametros = new HashMap();
        parametros.put(Parametros.INSTITUCION, Parametros.VALOR_INSTITUCION);
        parametros.put(Parametros.NOMBRE_REPORTE, Parametros.VALOR_NOMBRE_REPORTE);
        
        parametros.put(Parametros.IDENTIFICACION_BIEN, this.command.getFltIdentificaionBien());
        parametros.put(Parametros.FECHA_INICIO, this.command.getFltFechaInicio());
        parametros.put(Parametros.FECHA_FIN, this.command.getFltFechaFin());
        parametros.put(Parametros.ID_SOLICITUD, this.command.getFltIdSolicitud());
        
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
    
    public ReporteDonacionesCommand getCommand() {
        return command;
    }

    public void setCommand(ReporteDonacionesCommand command) {
        this.command = command;
    }
    //</editor-fold>
}