/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import com.icesoft.faces.context.effects.JavascriptContext;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.sigebi.commands.ReporteUsuarioBienesCommand;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.Usuario;
import cr.ac.ucr.sigebi.domain.reportes.ReporteUsuarioBienes;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.UsuarioModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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
@Controller(value = "controllerReporteUsuarioBienes")
@Scope("session")
public class GenerarReporteUsuarioBienesController extends BaseController {
    
    private static class Parametros {
        private static final String UNIDAD_CUSTODIO = "UNIDAD_CUSTODIO";
	private static final String USUARIO = "USUARIO";
        private static final String FLT_USUARIO = "FLT_USUARIO";
        private static final String FLT_BIEN = "FLT_BIEN";
        
        private static final String INSTITUCION = "INSTITUCION";
        private static final String NOMBRE_REPORTE = "NOMBRE_REPORTE";
	private static final String VALOR_INSTITUCION = "UNIVERSIDAD DE COSTA RICA";
        private static final String VALOR_NOMBRE_REPORTE = "Reporte de Usuarios y Bienes";
    }
    
    @Resource private BienModel bienModel;
    @Resource private UsuarioModel usuarioModel;
    
    private Map<Long, Tipo> tiposReporte;
    private List<SelectItem> itemsTipoReporte;
    
    private Map<String, Usuario> usuariosResponsables;
    private List<SelectItem> itemsUsuariosResponsables;
    
    private ReporteUsuarioBienesCommand command;
    
    private String mensaje;

    public GenerarReporteUsuarioBienesController() {
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
        
        List<Usuario> usuarios = this.usuarioModel.listar();
        if (!usuarios.isEmpty()) {
            this.itemsUsuariosResponsables = new ArrayList<SelectItem>();
            this.usuariosResponsables = new HashMap<String, Usuario>();
            for (Usuario usuario : usuarios) {
                this.usuariosResponsables.put(usuario.getId(), usuario);
                this.itemsUsuariosResponsables.add(new SelectItem(usuario.getId(), usuario.getNombreCompleto()));
            }
        }
    }
    
    public void inicializarDatos(ActionEvent event) {
        this.mensaje = new String();
        this.command = new ReporteUsuarioBienesCommand();
    }
    
    public void generarReporte() {
        try {
            //reportes/reporteSobrantes.jrxml 

            Tipo orden = this.tipoPorDominioValor(Constantes.DOMINIO_ORDEN_REPORTE, this.command.getIdOrden());
            Tipo orden1 = this.tipoPorDominioValor(Constantes.DOMINIO_COLUMNAS_REPORTE_USUARIOS_BIENES, this.command.getIdOrden1());
            Tipo orden2 = this.tipoPorDominioValor(Constantes.DOMINIO_COLUMNAS_REPORTE_USUARIOS_BIENES, this.command.getIdOrden2());
            Tipo orden3 = this.tipoPorDominioValor(Constantes.DOMINIO_COLUMNAS_REPORTE_USUARIOS_BIENES, this.command.getIdOrden3());
            
            List<Bien> bienes = this.bienModel.listarPorResponsable(this.command.getIdUsuario(), orden.getNombre(), orden1.getNombre(), orden2.getNombre(), orden3.getNombre());
            if (!bienes.isEmpty()) {
                String template = cr.ac.ucr.framework.reporte.componente.utilitario.Util.ConvertirRutas("/reportes/reporteUsuarioBienes.jrxml");
                String outputFile = cr.ac.ucr.framework.reporte.componente.utilitario.Util.ConvertirRutas("/reportes/reporteUsuarioBienes");

                ArrayList<ReporteUsuarioBienes> datosReporte = new ArrayList<ReporteUsuarioBienes>();
                for (Bien bien : bienes) {
                    datosReporte.add(new ReporteUsuarioBienes(bien));
                }

                JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(datosReporte);
                JasperReport jasperReport = JasperCompileManager.compileReport(template);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, generarParametros(), beanColDataSource);

                Tipo tipoReporte = this.tipoPorId(this.command.getIdTipo());
                if(tipoReporte.getNombre().equals(Constantes.TIPO_REPORTE_EXCELL)) {
                    JasperExportManager.exportReportToXmlFile(jasperPrint, outputFile + Constantes.TIPO_REPORTE_EXCELL_EXTENSION, true);
                    JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "reporte('reporteUsuarioBienes','" + Constantes.TIPO_REPORTE_EXCELL_EXTENSION + "');");
                } else {
                    JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile + Constantes.TIPO_REPORTE_PDF_EXTENSION);
                    JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "reporte('reporteUsuarioBienes','" + Constantes.TIPO_REPORTE_PDF_EXTENSION + "');");                    
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
    
    public Map generarParametros() {
        Map parametros = new HashMap();
        parametros.put(Parametros.INSTITUCION, Parametros.VALOR_INSTITUCION);
        parametros.put(Parametros.NOMBRE_REPORTE, Parametros.VALOR_NOMBRE_REPORTE);
        parametros.put(Parametros.FLT_USUARIO, this.command.getIdUsuario());
        parametros.put(Parametros.FLT_BIEN, this.command.getIdentificacion());
        
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

    public Map<String, Usuario> getUsuariosResponsables() {
        return usuariosResponsables;
    }

    public void setUsuariosResponsables(Map<String, Usuario> usuariosResponsables) {
        this.usuariosResponsables = usuariosResponsables;
    }

    public List<SelectItem> getItemsUsuariosResponsables() {
        return itemsUsuariosResponsables;
    }

    public void setItemsUsuariosResponsables(List<SelectItem> itemsUsuariosResponsables) {
        this.itemsUsuariosResponsables = itemsUsuariosResponsables;
    }

    public ReporteUsuarioBienesCommand getCommand() {
        return command;
    }

    public void setCommand(ReporteUsuarioBienesCommand command) {
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