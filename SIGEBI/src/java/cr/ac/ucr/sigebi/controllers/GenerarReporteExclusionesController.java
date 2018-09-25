/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import com.icesoft.faces.context.effects.JavascriptContext;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.sigebi.commands.ReporteExclusionesCommand;
import cr.ac.ucr.sigebi.domain.DocumentoActa;
import cr.ac.ucr.sigebi.domain.DocumentoDetalle;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.reportes.ReporteExclusiones;
import cr.ac.ucr.sigebi.models.ActaModel;
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
@Controller(value = "controllerReporteExclusiones")
@Scope("session")
public class GenerarReporteExclusionesController extends BaseController {
    
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
        private static final String VALOR_NOMBRE_REPORTE = "Reporte de Exclusiones";
    }
    
    @Resource private ActaModel actaModel;
    
    private Map<Long, Tipo> tiposReporte;
    private List<SelectItem> itemsTipoReporte;
    
    private ReporteExclusionesCommand command;

    public GenerarReporteExclusionesController() {
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
        this.command = new ReporteExclusionesCommand();
    }
    
    public void generarReporte() {
        try {
            //reportes/reporteSobrantes.jrxml 

            Tipo orden = this.tipoPorDominioValor(Constantes.DOMINIO_ORDEN_REPORTE, this.command.getIdOrden());
            Tipo orden1 = this.tipoPorDominioValor(Constantes.DOMINIO_COLUMNAS_REPORTE_EXCLUSIONES, this.command.getIdOrden1());
            Tipo orden2 = this.tipoPorDominioValor(Constantes.DOMINIO_COLUMNAS_REPORTE_EXCLUSIONES, this.command.getIdOrden2());
            Tipo orden3 = this.tipoPorDominioValor(Constantes.DOMINIO_COLUMNAS_REPORTE_EXCLUSIONES, this.command.getIdOrden3());
            
            Estado estadoActaAprobada = this.estadoPorDominioValor( Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_APROBADO );
            List<DocumentoDetalle> detalles = this.actaModel.listarDetalles(estadoActaAprobada, this.command.getFltIdSolicitud(), this.command.getFltIdentificaionBien(), this.command.getFltFechaInicio(), this.command.getFltFechaFin(), orden.getNombre(), 
                    orden1 != null ? orden1.getNombre() : null, orden2 != null ? orden3.getNombre() : null, orden3 != null ? orden3.getNombre() : null);
            
            if (!detalles.isEmpty()) {
                String template = cr.ac.ucr.framework.reporte.componente.utilitario.Util.ConvertirRutas("/reportes/reporteExclusiones.jrxml");
                String outputFile = cr.ac.ucr.framework.reporte.componente.utilitario.Util.ConvertirRutas("/reportes/reporteExclusiones");

                ArrayList<ReporteExclusiones> datosReporte = new ArrayList<ReporteExclusiones>();
                for (DocumentoDetalle detalle : detalles) {
                    DocumentoActa documento = (DocumentoActa)actaModel.buscarPorId(detalle.getDocumento().getId());
                    datosReporte.add(new ReporteExclusiones(detalle, documento));
                }

                JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(datosReporte);
                JasperReport jasperReport = JasperCompileManager.compileReport(template);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, generarParametros(), beanColDataSource);

                Tipo tipoReporte = this.tipoPorId(this.command.getIdTipo());
                if(tipoReporte.getNombre().equals(Constantes.TIPO_REPORTE_EXCEL)) {
                    JasperExportManager.exportReportToXmlFile(jasperPrint, outputFile + Constantes.TIPO_REPORTE_XLS_EXTENSION, true);
                    JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "reporte('reporteExclusiones','" + Constantes.TIPO_REPORTE_XLS_EXTENSION + "');");
                } else {
                    JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile + Constantes.TIPO_REPORTE_PDF_EXTENSION);
                    JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "reporte('reporteExclusiones','" + Constantes.TIPO_REPORTE_PDF_EXTENSION + "');");                    
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
    
    public ReporteExclusionesCommand getCommand() {
        return command;
    }

    public void setCommand(ReporteExclusionesCommand command) {
        this.command = command;
    }
    //</editor-fold>
}