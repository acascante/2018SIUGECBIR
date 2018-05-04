/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.commands.GenerarReporteSobrantesCommand;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.ReporteSobrantes;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.TomaFisica;
import cr.ac.ucr.sigebi.domain.TomaFisicaSobrante;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.TomaFisicaModel;
import cr.ac.ucr.sigebi.models.TomaFisicaSobranteModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.component.UIViewRoot;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author alvaro.cascante
 */
@Controller(value = "controllerReporteSobrantes")
@Scope("session")
public class GenerarReporteSobrantesController extends BaseController {
    
    private static class Parametros {
        private static final String IDENTIFICACION = "IDENTIFICACION";
	private static final String DESCRIPCION = "DESCRIPCION";
	private static final String MARCA = "MARCA";
	private static final String MODELO = "MODELO";
	private static final String SERIE = "SERIE";
	private static final String UNIDAD_CUSTODIO = "UNIDAD_CUSTODIO";
	private static final String USUARIO = "USUARIO";
	private static final String TOMA_FISICA = "TOMA_FISICA";
	private static final String UBICACION = "UBICACION";
	private static final String UNIDAD_EJECUTORA = "UNIDAD_EJECUTORA";
        
        private static final String INSTITUCION = "INSTITUCION";
        private static final String NOMBRE_REPORTE = "NOMBRE_REPORTE";
	private static final String VALOR_INSTITUCION = "UNIVERSIDAD DE COSTA RICA";
        private static final String VALOR_NOMBRE_REPORTE = "Reporte de Bienes Sobrantes";
    }
    
    @Resource private BienModel bienModel;
    @Resource private TomaFisicaModel tomaFisicaModel;
    @Resource private TomaFisicaSobranteModel tomaFisicaSobranteModel;
    
    private Map<Long, Tipo> tiposReporte;
    private List<SelectItem> itemsTipoReporte;
    
    private Map<Long, Tipo> ordenReporte;
    private List<SelectItem> itemsOrdenReporte;
    
    private Map<Long, Tipo> columnasOrdenReporte;
    private List<SelectItem> itemsColumnasOrdenReporte;
    
    private Map<Long, Estado> estadosBien;
    private List<SelectItem> itemsEstadoBien;
    
    private GenerarReporteSobrantesCommand command;
    
    private String mensaje;

    public GenerarReporteSobrantesController() {
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
        
        List<Tipo> tiposColumnasOrdenReporte = this.tiposPorDominio(Constantes.DOMINIO_COLUMNAS_REPORTE_SOBRANTES);
        if (!tiposColumnasOrdenReporte.isEmpty()) {
            this.itemsColumnasOrdenReporte = new ArrayList<SelectItem>();
            this.columnasOrdenReporte = new HashMap<Long, Tipo>();
            for (Tipo tipo : tiposColumnasOrdenReporte) {
                this.columnasOrdenReporte.put(tipo.getId(), tipo);
                this.itemsColumnasOrdenReporte.add(new SelectItem(tipo.getId(), tipo.getNombre()));
            }
        }
        
        List<Estado> estados = this.estadosPorDominio(Constantes.DOMINIO_BIEN);
        if (!estados.isEmpty()) {
            this.itemsEstadoBien = new ArrayList<SelectItem>();
            this.estadosBien = new HashMap<Long, Estado>();
            for (Estado estado : estados) {
                this.estadosBien.put(estado.getId(), estado);
                this.itemsEstadoBien.add(new SelectItem(estado.getId(), estado.getNombre()));
            }
        }
    }
    
    public void detalleReporteSobrantes(ActionEvent event) {
        Long id = (Long)event.getComponent().getAttributes().get("idTomaFisica");

        this.mensaje = new String();
        this.command = new GenerarReporteSobrantesCommand(id);

        Util.navegar(Constantes.KEY_REPORTE_SOBRANTES);
    }
    
    public String validarForm(UIViewRoot root) {
        if (this.command.getIdTipo().equals(Constantes.DEFAULT_ID)) {
            return Util.getEtiquetas("sigebi.label.reporteBien.error.tipo");
        }
        return Constantes.OK;
    }
    
    public void generarReporte() {
        try {
            //reportes/reporteSobrantes.jrxml
            TomaFisica tomaFisica = this.tomaFisicaModel.buscarPorId(this.command.getIdTomaFisica());
            List<TomaFisicaSobrante> sobrantes = this.tomaFisicaSobranteModel.listarReporte(tomaFisica, this.command.getIdentificacion(),  
                    this.command.getUbicacion(), this.command.getDescripcion(), this.command.getSerie(), this.command.getMarca(), this.command.getModelo(),
                    this.command.getIdOrden().equals(-1L)?null:this.tipoPorId(this.command.getIdOrden()).getNombre(),
                    this.command.getIdOrden1().equals(-1L)?null:this.tipoPorId(this.command.getIdOrden1()).getNombre(),
                    this.command.getIdOrden2().equals(-1L)?null:this.tipoPorId(this.command.getIdOrden2()).getNombre(),
                    this.command.getIdOrden3().equals(-1L)?null:this.tipoPorId(this.command.getIdOrden3()).getNombre());
            if (!sobrantes.isEmpty()) {
                String template = cr.ac.ucr.framework.reporte.componente.utilitario.Util.ConvertirRutas("/reportes/reporteSobrantes.jrxml");
                String jasperFile = cr.ac.ucr.framework.reporte.componente.utilitario.Util.ConvertirRutas("/reportes/reporteSobrantes.jasper");
                String outputFile = cr.ac.ucr.framework.reporte.componente.utilitario.Util.ConvertirRutas("/reportes/reporteSobrantes.pdf");
                
                JasperCompileManager.compileReportToFile(template, jasperFile);
                ArrayList<ReporteSobrantes> datosReporte = new ArrayList<ReporteSobrantes>();
                for (TomaFisicaSobrante bienSobrante : sobrantes) {
                    ReporteSobrantes dato;
                    if (!bienSobrante.getIdentificacion().isEmpty()) {
                        Bien bien = this.bienModel.buscarPorIdentificacion(bienSobrante.getIdentificacion());
                        if (bien != null) {
                            dato = new ReporteSobrantes(bienSobrante, bien.getEstado());
                        } else {
                            dato = new ReporteSobrantes(bienSobrante, null);
                        }
                    } else {
                        dato = new ReporteSobrantes(bienSobrante, null);
                    }
                    datosReporte.add(dato);
                }
                
                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(datosReporte);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFile, generarParametros(tomaFisica), dataSource);
                JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);
                Mensaje.agregarInfo("Reporte generado exitosamente");
            } else {
                Mensaje.agregarErrorAdvertencia("No hay sobrantes en esta Toma");
            }            
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
            this.mensaje = err.getMessage();
        }
    }
    
    public Map generarParametros(TomaFisica tomaFisica) {
        Map parametros = new HashMap();
        parametros.put(Parametros.INSTITUCION, Parametros.VALOR_INSTITUCION);
        parametros.put(Parametros.NOMBRE_REPORTE, Parametros.VALOR_NOMBRE_REPORTE);
        
        parametros.put(Parametros.IDENTIFICACION, this.command.getIdentificacion());
        parametros.put(Parametros.DESCRIPCION, this.command.getDescripcion());
        parametros.put(Parametros.MARCA, this.command.getMarca());
        parametros.put(Parametros.MODELO, this.command.getModelo());
        parametros.put(Parametros.SERIE, this.command.getSerie());
        parametros.put(Parametros.UNIDAD_CUSTODIO, unidadEjecutora.getDescripcion());
        parametros.put(Parametros.USUARIO, this.usuarioSIGEBI.getNombreCompleto());
        parametros.put(Parametros.TOMA_FISICA, tomaFisica.getId());
        parametros.put(Parametros.UBICACION, null);
        parametros.put(Parametros.UNIDAD_EJECUTORA, tomaFisica.getUnidadEjecutora().getDescripcion());
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

    public Map<Long, Estado> getEstadosBien() {
        return estadosBien;
    }

    public void setEstadosBien(Map<Long, Estado> estadosBien) {
        this.estadosBien = estadosBien;
    }

    public List<SelectItem> getItemsEstadoBien() {
        return itemsEstadoBien;
    }

    public void setItemsEstadoBien(List<SelectItem> itemsEstadoBien) {
        this.itemsEstadoBien = itemsEstadoBien;
    }

    public GenerarReporteSobrantesCommand getCommand() {
        return command;
    }

    public void setCommand(GenerarReporteSobrantesCommand command) {
        this.command = command;
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

    public void setItemsOrdenReporte(List<SelectItem> itemsOrdenReporte) {
        this.itemsOrdenReporte = itemsOrdenReporte;
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

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    //</editor-fold>
}