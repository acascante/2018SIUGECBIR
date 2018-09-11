/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import com.icesoft.faces.context.effects.JavascriptContext;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.sigebi.commands.GenerarReporteRolesUsuariosCommand;
import cr.ac.ucr.sigebi.domain.AutorizacionRol;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.ViewAutorizacionRolUsuarioUnidad;
import cr.ac.ucr.sigebi.domain.reportes.ReporteRolesUsuarios;
import cr.ac.ucr.sigebi.models.AutorizacionRolModel;
import cr.ac.ucr.sigebi.models.UsuarioModel;
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
    
    @Resource private AutorizacionRolModel autorizacionRolModel;
    @Resource private UsuarioModel usuarioModel;
    
    private Map<Long, Tipo> tiposReporte;
    private List<SelectItem> itemsTipoReporte;
    
    private GenerarReporteRolesUsuariosCommand command;

    public GenerarReporteRolesUsuariosController() {
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
        this.command = new GenerarReporteRolesUsuariosCommand();
    }
    
    public void generarReporte() {
        try {
            //reportes/reporteSobrantes.jrxml 
            Tipo grupo = this.tipoPorDominioValor(Constantes.DOMINIO_GRUPO_REPORTE_ROLES_USUARIOS, this.command.getIdGrupo());
            Tipo orden = this.tipoPorDominioValor(Constantes.DOMINIO_ORDEN_REPORTE, this.command.getIdOrden());
            Tipo orden1 = this.tipoPorDominioValor(Constantes.DOMINIO_COLUMNAS_REPORTE_ROLES_USUARIOS, this.command.getIdOrden1());
            Tipo orden2 = this.tipoPorDominioValor(Constantes.DOMINIO_COLUMNAS_REPORTE_ROLES_USUARIOS, this.command.getIdOrden2());
            Tipo orden3 = this.tipoPorDominioValor(Constantes.DOMINIO_COLUMNAS_REPORTE_ROLES_USUARIOS, this.command.getIdOrden3());
            
            List<ViewAutorizacionRolUsuarioUnidad> usuarios = this.usuarioModel.listarUsuariosGestionProceso(grupo.getNombre(), orden.getNombre(), 
                    orden1 != null ? orden1.getNombre() : null, orden2 != null ? orden3.getNombre() : null, orden3 != null ? orden3.getNombre() : null);
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
                if(tipoReporte.getNombre().equals(Constantes.TIPO_REPORTE_EXCELL)) {
                    JasperExportManager.exportReportToXmlFile(jasperPrint, outputFile + Constantes.TIPO_REPORTE_EXCELL_EXTENSION, true);
                    JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "reporte('reporteRolesUsuarios','" + Constantes.TIPO_REPORTE_EXCELL_EXTENSION + "');");
                } else {
                    JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile + Constantes.TIPO_REPORTE_PDF_EXTENSION);
                    JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "reporte('reporteRolesUsuarios','" + Constantes.TIPO_REPORTE_PDF_EXTENSION + "');");                    
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

    public GenerarReporteRolesUsuariosCommand getCommand() {
        return command;
    }

    public void setCommand(GenerarReporteRolesUsuariosCommand command) {
        this.command = command;
    }
    //</editor-fold>
}