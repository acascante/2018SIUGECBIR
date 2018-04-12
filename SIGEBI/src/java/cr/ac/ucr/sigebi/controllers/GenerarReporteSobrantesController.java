/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Reporte;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.commands.GenerarReporteSobrantesCommand;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.models.BienModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.component.UIViewRoot;
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
@Controller(value = "controllerReporteSobrantes")
@Scope("session")
public class GenerarReporteSobrantesController extends BaseController {
    
    @Resource private BienModel bienModel;
    
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
        this.inicializarDatos();
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
    
    private void cargarTipos(String dominio, List<SelectItem> itemsList, Map<Long, Tipo> mapTipos) {
        List<Tipo> tipos = this.tiposPorDominio(dominio);
        if (!tipos.isEmpty()) {
            itemsList = new ArrayList<SelectItem>();
            mapTipos = new HashMap<Long, Tipo>();
            for (Tipo tipo : tipos) {
                mapTipos.put(tipo.getId(), tipo);
                itemsList.add(new SelectItem(tipo.getId(), tipo.getNombre()));
            }
        }
    }
    
    private void inicializarDatos() {
        this.mensaje = new String();
        this.command = new GenerarReporteSobrantesCommand();    
    }
    
    public String validarForm(UIViewRoot root) {
        if (this.command.getIdTipo().equals(Constantes.DEFAULT_ID)) {
            return Util.getEtiquetas("sigebi.label.reporteBien.error.tipo");
        }
        
        return Constantes.OK;
    }
    
    public void generarReporte() {
        try{
            Map parametros = new HashMap();
            parametros.put("institucion", Constantes.REPORTE_SOBRANTES_PARAMETRO_INSTITUCION);
            parametros.put("nombreReporte", Constantes.REPORTE_SOBRANTES_PARAMETRO_NOMBRE);
            parametros.put("unidadEjecutora", this.unidadEjecutora.getDescripcion());
            parametros.put("identificacion", this.command.getIdentificacion());
            parametros.put("descripcion", this.command.getDescripcion());
            parametros.put("marca", this.command.getMarca());
            parametros.put("modelo", this.command.getModelo());
            parametros.put("serie", this.command.getSerie());
            parametros.put("nomEstado", this.estadosBien.get(this.command.getIdEstado()).getNombre());
            parametros.put("usuarioGenera", this.usuarioSIGEBI.getNombreCompleto());
            parametros.put("unidadCustodio", unidadEjecutora.getDescripcion());
        } catch(Exception e) {
            Mensaje.agregarErrorAdvertencia( e, Util.getEtiquetas("sigebi.reporte.sobrantes.error"));
        }
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
