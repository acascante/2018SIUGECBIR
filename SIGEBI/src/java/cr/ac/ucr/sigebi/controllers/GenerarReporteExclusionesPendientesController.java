/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import com.icesoft.faces.context.effects.JavascriptContext;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.sigebi.commands.ReporteExclusionesPendientesCommand;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.SolicitudDetalle;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.reportes.ReporteExclusionesPendientes;
import cr.ac.ucr.sigebi.models.ExclusionModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.context.FacesContext;
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
@Controller(value = "controllerReporteExclusionesPendientes")
@Scope("session")
public class GenerarReporteExclusionesPendientesController extends BaseController {
    
    
    private static class Parametros {
        private static final String UNIDAD_CUSTODIO = "UNIDAD_CUSTODIO";
	private static final String USUARIO = "USUARIO";
        
        private static final String TIPO = "TIPO";
        private static final String FECHA_INICIO = "FECHA_INICIO";
        private static final String FECHA_FIN = "FECHA_FIN";
        private static final String ESTADO = "ESTADO";
        
        private static final String INSTITUCION = "INSTITUCION";
        private static final String NOMBRE_REPORTE = "NOMBRE_REPORTE";
	private static final String VALOR_INSTITUCION = "UNIVERSIDAD DE COSTA RICA";
        private static final String VALOR_NOMBRE_REPORTE = "Reporte de Exclusiones Pendientes";
    }
    
    @Resource private ExclusionModel exclusionModel;
    
    private Map<Long, Tipo> tiposReporte;
    private List<SelectItem> itemsTipoReporte;
    
    private Map<Long, Tipo> tiposExclusion;
    private List<SelectItem> itemsTipoExclusion;
    
    private Map<Long, Estado> estadosExclusion;
    private List<SelectItem> itemsEstadosExclusion;
    
    private ReporteExclusionesPendientesCommand command;

    public GenerarReporteExclusionesPendientesController() {
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
        
        List<Tipo> tiposE = this.tiposPorDominio(Constantes.DOMINIO_EXCLUSION);
        if (!tiposE.isEmpty()) {
            this.itemsTipoExclusion = new ArrayList<SelectItem>();
            this.tiposExclusion = new HashMap<Long, Tipo>();
            for (Tipo tipo : tiposE) {
                this.tiposExclusion.put(tipo.getId(), tipo);
                this.itemsTipoExclusion.add(new SelectItem(tipo.getId(), tipo.getNombre()));
            }
        }
        
        List<Estado> estadosE = this.estadosPorDominio(Constantes.DOMINIO_EXCLUSION);
        if (!estadosE.isEmpty()) {
            this.itemsEstadosExclusion = new ArrayList<SelectItem>();
            this.estadosExclusion = new HashMap<Long, Estado>();
            for (Estado estado : estadosE) {
                this.estadosExclusion.put(estado.getId(), estado);
                this.itemsEstadosExclusion.add(new SelectItem(estado.getId(), estado.getNombre()));
            }
        }
    }
    
    private void inicializarDatos() {
        this.command = new ReporteExclusionesPendientesCommand();
    }
    
    public void generarReporte() {
        try {
            //reportes/reporteSobrantes.jrxml 

            Tipo orden = this.tipoPorDominioValor(Constantes.DOMINIO_ORDEN_REPORTE, this.command.getIdOrden());
            Tipo orden1 = this.tipoPorDominioValor(Constantes.DOMINIO_COLUMNAS_REPORTE_EXCLUSIONES, this.command.getIdOrden1());
            Tipo orden2 = this.tipoPorDominioValor(Constantes.DOMINIO_COLUMNAS_REPORTE_EXCLUSIONES, this.command.getIdOrden2());
            Tipo orden3 = this.tipoPorDominioValor(Constantes.DOMINIO_COLUMNAS_REPORTE_EXCLUSIONES, this.command.getIdOrden3());
            
            Tipo tipoExclusion = this.tipoPorId(this.command.getFltIdTipo());
            Estado estado =  this.estadoPorId(this.command.getFltIdEstado());
            
            List<SolicitudDetalle> detalles = this.exclusionModel.listarDetalles(tipoExclusion, estado, this.command.getFltFechaInicio(), this.command.getFltFechaFin(), orden.getNombre(), 
                    orden1 != null ? orden1.getNombre() : null, orden2 != null ? orden3.getNombre() : null, orden3 != null ? orden3.getNombre() : null);
            
            if (!detalles.isEmpty()) {
                String template = cr.ac.ucr.framework.reporte.componente.utilitario.Util.ConvertirRutas("/reportes/reporteExclusionesPendientes.jrxml");
                String outputFile = cr.ac.ucr.framework.reporte.componente.utilitario.Util.ConvertirRutas("/reportes/reporteExclusionesPendientes");

                ArrayList<ReporteExclusionesPendientes> datosReporte = new ArrayList<ReporteExclusionesPendientes>();
                for (SolicitudDetalle detalle : detalles) {
                    datosReporte.add(new ReporteExclusionesPendientes(detalle));
                }

                JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(datosReporte);
                JasperReport jasperReport = JasperCompileManager.compileReport(template);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, generarParametros(), beanColDataSource);

                Tipo tipoReporte = this.tipoPorId(this.command.getIdTipo());
                if(tipoReporte.getNombre().equals(Constantes.TIPO_REPORTE_EXCEL)) {
                    JasperExportManager.exportReportToXmlFile(jasperPrint, outputFile + Constantes.TIPO_REPORTE_XLS_EXTENSION, true);
                    JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "reporte('reporteExclusionesPendientes','" + Constantes.TIPO_REPORTE_XLS_EXTENSION + "');");
                } else {
                    JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile + Constantes.TIPO_REPORTE_PDF_EXTENSION);
                    JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "reporte('reporteExclusionesPendientes','" + Constantes.TIPO_REPORTE_PDF_EXTENSION + "');");                    
                }
                Mensaje.agregarInfo("Reporte generado exitosamente");
            } else {
                Mensaje.agregarErrorAdvertencia("No hay datos para el reporte");
            }            
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia("No se puede generar el reporte en este momento, intente de nuevo");
        }
    }
    
    public Map generarParametros() {
        Map parametros = new HashMap();
        parametros.put(Parametros.INSTITUCION, Parametros.VALOR_INSTITUCION);
        parametros.put(Parametros.NOMBRE_REPORTE, Parametros.VALOR_NOMBRE_REPORTE);
        
        Tipo tipo = this.tipoPorId(this.command.getFltIdTipo());
        Estado estado = this.estadoPorId(this.command.getFltIdEstado());
        
        parametros.put(Parametros.TIPO, tipo);
        parametros.put(Parametros.ESTADO, estado);
        parametros.put(Parametros.FECHA_INICIO, this.command.getFltFechaInicio());
        parametros.put(Parametros.FECHA_FIN, this.command.getFltFechaFin());
        
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
    
    public Map<Long, Tipo> getTiposExclusion() {
        return tiposExclusion;
    }

    public void setTiposExclusion(Map<Long, Tipo> tiposExclusion) {
        this.tiposExclusion = tiposExclusion;
    }

    public List<SelectItem> getItemsTipoExclusion() {
        return itemsTipoExclusion;
    }

    public void setItemsTipoExclusion(List<SelectItem> itemsTipoExclusion) {
        this.itemsTipoExclusion = itemsTipoExclusion;
    }

    public Map<Long, Estado> getEstadosExclusion() {
        return estadosExclusion;
    }

    public void setEstadosExclusion(Map<Long, Estado> estadosExclusion) {
        this.estadosExclusion = estadosExclusion;
    }

    public List<SelectItem> getItemsEstadosExclusion() {
        return itemsEstadosExclusion;
    }

    public void setItemsEstadosExclusion(List<SelectItem> itemsEstadosExclusion) {
        this.itemsEstadosExclusion = itemsEstadosExclusion;
    }

    public ReporteExclusionesPendientesCommand getCommand() {
        return command;
    }

    public void setCommand(ReporteExclusionesPendientesCommand command) {
        this.command = command;
    }
    //</editor-fold>
}