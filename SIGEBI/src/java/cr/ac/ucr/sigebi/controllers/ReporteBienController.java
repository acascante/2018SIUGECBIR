/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import com.icesoft.faces.context.effects.JavascriptContext;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.sigebi.commands.ReporteBienCommand;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.BienReporte;
import cr.ac.ucr.sigebi.domain.CampoBien;
import cr.ac.ucr.sigebi.domain.CampoReporteBien;
import cr.ac.ucr.sigebi.domain.ReporteBien;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.CampoBienModel;
import cr.ac.ucr.sigebi.models.CampoReporteBienModel;
import cr.ac.ucr.sigebi.models.ReporteBienModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
    
    private List<SelectItem> itemsTipoReporte;
    
    private Map<Long, CampoBien> campos;
    private Map<Long, CampoBien> allCampos;
    private Map<Long, Boolean> camposSeleccionados;
    
    private ReporteBienCommand command;
    
    private String mensaje;

    private Boolean reporteRegistrado;
    private Boolean visiblePanelNombreReporte;
    private Boolean visiblePanelAgregarCampos;
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
            for (Tipo tipo : tipos) {
                this.itemsTipoReporte.add(new SelectItem(tipo.getId(), tipo.getNombre()));
            }
        }
        
        inicializarReportes();
    }
    
    private void inicializarReportes() {    
        //Cargar Reportes del Usuario
        List<ReporteBien> reportesBien = this.reporteBienModel.listarPorUsuario(usuarioSIGEBI);
        if (!reportesBien.isEmpty()) {
            this.itemsReporte.clear();
            this.reportes.clear();
            for (ReporteBien reporteBien : reportesBien) {
                this.itemsReporte.add(new SelectItem(reporteBien.getId(), reporteBien.getNombre()));
                this.reportes.put(reporteBien.getId(), reporteBien);
            }
        }
    }
    
    private void inicializarCampos() {
        this.setPrimerRegistro(1);
        this.contarCampos();
        this.listarCampos();
    }
    
    private void contarCampos() {
        try {
            Long contador = campoBienModel.contar();
            this.setCantidadRegistros(contador.intValue());
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        }
    }
    
    private void listarCampos() {
        //Cargar Campos a seleccionar
        List<CampoBien> listCampos = this.campoBienModel.listar(this.getPrimerRegistro()-1, this.getUltimoRegistro());
        if (!listCampos.isEmpty()) {
            this.campos.clear();
            for (CampoBien campo : listCampos) {
                this.allCampos.put(campo.getId(), campo);
                if (!this.command.getCamposReporte().containsKey(campo.getId())) {
                    this.campos.put(campo.getId(), campo);
                }
            }
        }
    }
    
    private void inicializarDatos() {
        this.reporteRegistrado = false;
        this.visiblePanelNombreReporte = false;
        this.visiblePanelAgregarCampos = false;
        this.visibleBotonEliminar = false;
        this.mensaje = "";
        this.command = new ReporteBienCommand(usuarioSIGEBI);
        this.campos = new HashMap<Long, CampoBien>();
        this.allCampos = new HashMap<Long, CampoBien>();
        this.itemsReporte = new ArrayList<SelectItem>();
        this.reportes = new HashMap<Long, ReporteBien>();
        this.camposSeleccionados = new HashMap<Long, Boolean>();
    }
    
    private void inicializarReporte() {
        ReporteBien reporteBien = reportes.get(this.command.getIdReporte());
        reporteBien.setCamposReporte(this.campoReporteBienModel.listarPorReporte(reporteBien));
        
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
                ReporteBien reporteBien = this.command.getReporteBien(tipo);
                logMessage("-- Salvar Reporte --");
                this.reporteBienModel.salvar(reporteBien);
                logMessage("-- Salvar Campos Reporte --");
                this.command.setIdReporte(reporteBien.getId());
                
                this.campoReporteBienModel.salvar(reporteBien.getCamposReporte());
                
                this.visibleBotonEliminar = true;
                this.visiblePanelNombreReporte = false;
                
                this.itemsReporte.add(new SelectItem(reporteBien.getId(), reporteBien.getNombre()));
                this.reportes.put(reporteBien.getId(), reporteBien);
                logMessage("-- Done --");
                Mensaje.agregarInfo("Reporte guardado exitosamente");
            } else {
                Mensaje.agregarErrorAdvertencia(messageValidacion);
            }
        } catch (Exception err) {
            logMessage("-- Error: " + err.getMessage());
            this.mensaje = err.getMessage();
        }
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
  
    public void mostarPanelAgregarCampos() {
        this.inicializarCampos();
        this.visiblePanelAgregarCampos = true;
    }
    
    public void cerrarPanelAgregarCampos() {
        this.visiblePanelAgregarCampos = false;
        
        for (Map.Entry<Long, Boolean> row : this.camposSeleccionados.entrySet()) {
            if (row.getValue()) {
                CampoBien campo = this.allCampos.get(row.getKey());
                this.command.getCamposReporte().put(campo.getId(), new CampoReporteBien(campo));
            }
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
            Tipo tipo = this.tipoPorId(this.command.getIdTipoReporte());
            ReporteBien reporteBien = this.command.getReporteBien(tipo);            
            this.campoReporteBienModel.eliminar(reporteBien.getCamposReporte());
            this.reporteBienModel.eliminar(reporteBien);
            
            inicializarReportes();
            inicializarDatos();
            Mensaje.agregarInfo("Reporte eliminado exitosamente");
        } catch (FWExcepcion err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
            this.mensaje = err.getMessage();
        }
    }
    
    public void generarReporte() {
                
        if (this.command.getIdTipoReporte().equals(Constantes.DEFAULT_ID)) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.label.reporteBien.error.tipo"));
        } else {
        
            Map<String, Object> parametros = new HashMap<String, Object>();
            List<CampoReporteBien> listCamposReporte = this.command.getListCamposReporte();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT b FROM Bien b WHERE 1=1 ");
            for (CampoReporteBien campo : listCamposReporte) {
                if(campo.getValor() != null && !campo.getValor().isEmpty()) {
                    sql.append(" AND b.");
                    sql.append(campo.getCampoBien().getNombreHQL());
                    sql.append("= :");
                    sql.append(campo.getCampoBien().getNombreHQL());
                    parametros.put(campo.getCampoBien().getNombreHQL(), campo.getValor());
                }
            }
            sql.append(" ORDER BY b.id ASC");

            try {
                String sourceFileName = cr.ac.ucr.framework.reporte.componente.utilitario.Util.ConvertirRutas("/reportes/reporteBienes.jrxml");
                String tempFileName = cr.ac.ucr.framework.reporte.componente.utilitario.Util.ConvertirRutas("/reportes/reporteBienesTemp.jrxml");
                String reportFileName = cr.ac.ucr.framework.reporte.componente.utilitario.Util.ConvertirRutas("/reportes/reporteBienes");

                this.actualizarXMLReporte(sourceFileName, tempFileName, listCamposReporte);

                List<Bien> bienes = this.bienModel.listar(sql.toString(), parametros);
                List<BienReporte> bienesReporte = new ArrayList<BienReporte>();
                for (Bien bien : bienes) {
                    bienesReporte.add(new BienReporte(bien));
                }

                JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(bienesReporte);

                Map parameters = new HashMap();
                JasperReport jasperReport = JasperCompileManager.compileReport(tempFileName);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);

                Tipo tipoReporte = this.tipoPorId(this.command.getIdTipoReporte());
                logMessage("Id Reporte " + this.command.getIdTipoReporte());
                logMessage("No Reporte " + tipoReporte.getNombre());
                if(tipoReporte.getNombre().equals(Constantes.TIPO_REPORTE_PDF)) {
                    JasperExportManager.exportReportToPdfFile(jasperPrint, reportFileName + Constantes.TIPO_REPORTE_PDF_EXTENSION);
                    JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "reporte('" + "reporteBienes" + "','" + Constantes.TIPO_REPORTE_PDF_EXTENSION + "');");
                } else {
                    JasperExportManager.exportReportToXmlFile(jasperPrint, reportFileName + Constantes.TIPO_REPORTE_EXCELL_EXTENSION, true);
                    JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "reporte(' " + "reporteBienes" + "','" + Constantes.TIPO_REPORTE_EXCELL_EXTENSION + "');");
                }
                
                Mensaje.agregarInfo("Reporte generado exitosamente");
             } catch (Exception err) {
                Mensaje.agregarErrorAdvertencia(err.getMessage());
                this.mensaje = err.getMessage();
            }
        }
    }
    
    public void actualizarXMLReporte (String filePath, String tempFilePath, List<CampoReporteBien> campos) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Map<String, CampoReporteBien> mapCampos = new HashMap<String, CampoReporteBien>();
        for (CampoReporteBien campo : campos) {
            mapCampos.put(campo.getCampoBien().getIdColumna(), campo);
        }

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(filePath);
        Element parentElement = (Element)document.getFirstChild();
        eliminarColumnas(parentElement, "columnHeader", "staticText", mapCampos);
        eliminarColumnas(parentElement, "detail", "textField", mapCampos);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(tempFilePath));
        transformer.transform(source, result);
    }
    
    private void eliminarColumnas(Element jasperReport, String section, String nodeName, Map<String, CampoReporteBien> mapCampos) {
        int x = 0;

        Element sectionNode = (Element)jasperReport.getElementsByTagName(section).item(0);  // <columnHeader> <detail>
        Element bandNode = (Element)sectionNode.getElementsByTagName("band").item(0);   // <band ...>
        NodeList nodeList = bandNode.getElementsByTagName(nodeName);                    // <staticText ...>
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element node = (Element)nodeList.item(i);
            Element reportElement = (Element)node.getElementsByTagName("reportElement").item(0);    //<reportElement ...>
            
            String key = reportElement.getAttribute("key");
            if (mapCampos.containsKey(key)){
                CampoReporteBien campo = mapCampos.get(key);
                reportElement.setAttribute("x", x + "");
                reportElement.setAttribute("width", campo.getTamanoColumna()+"");
                x += campo.getTamanoColumna();
            } else {
                i--;
                bandNode.removeChild(node);
            }
        }
    }
    
    private void logMessage(String message) {
        System.out.println(message);
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

    public Map<Long, CampoBien> getCampos() {
        return campos;
    }
    
    public List<CampoBien> getListCampos() {
        return new ArrayList<CampoBien>(campos.values());
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
    
    public Boolean getVisiblePanelAgregarCampos() {
        return visiblePanelAgregarCampos;
    }

    public void setVisiblePanelAgregarCampos(Boolean visiblePanelAgregarCampos) {
        this.visiblePanelAgregarCampos = visiblePanelAgregarCampos;
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Paginacion">
    public void irPagina(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        int numeroPagina = Integer.parseInt(Util.getRequestParameter("numPag"));
        this.getPrimerRegistroPagina(numeroPagina);
        this.listarCampos();
    }

    public void siguiente(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getSiguientePagina();
        this.listarCampos();
    }

    public void anterior(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getPaginaAnterior();
        this.listarCampos();
    }

    public void primero(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.setPrimerRegistro(1);
        this.listarCampos();
    }

    public void ultimo(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getPrimerRegistroUltimaPagina();
        this.listarCampos();
    }

    public void cambioRegistrosPorPagina(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.setCantRegistroPorPagina(Integer.parseInt(pEvent.getNewValue().toString()));          
        this.setPrimerRegistro(1);
        this.listarCampos();
    }
    // </editor-fold>
}