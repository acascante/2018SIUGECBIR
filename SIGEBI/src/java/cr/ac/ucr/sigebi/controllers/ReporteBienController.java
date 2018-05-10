/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import com.sun.javafx.scene.control.skin.VirtualFlow;
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
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
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
    
    private Map<Long, Tipo> tiposReporte;
    private List<SelectItem> itemsTipoReporte;
    
    private Map<Long, CampoBien> camposReporte;
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
            this.tiposReporte = new HashMap<Long, Tipo>();
            for (Tipo tipo : tipos) {
                this.tiposReporte.put(tipo.getId(), tipo);
                this.itemsTipoReporte.add(new SelectItem(tipo.getId(), tipo.getNombre()));
            }
        }
        
        inicializarReportes();
    }
    
    private void inicializarReportes() {    
        //Cargar Reportes del Usuario
        List<ReporteBien> reportesBien = this.reporteBienModel.listarPorUsuario(usuarioSIGEBI);
        if (!reportesBien.isEmpty()) {
            this.itemsReporte = new ArrayList<SelectItem>();
            this.reportes = new HashMap<Long, ReporteBien>();
            this.itemsReporte.clear();
            this.reportes.clear();
            for (ReporteBien reporteBien : reportesBien) {
                this.itemsReporte.add(new SelectItem(reporteBien.getId(), reporteBien.getNombre()));
                this.reportes.put(reporteBien.getId(), reporteBien);
            }
        }
    }
    
    private void inicializarCampos() {
        //Cargar Campos a seleccionar
        List<CampoBien> campos = this.campoBienModel.listar();
        if (!campos.isEmpty()) {
            this.camposSeleccionados = new HashMap<Long, Boolean>();
            this.camposReporte = new HashMap<Long, CampoBien>();
            for (CampoBien campo : campos) {
                if (!this.command.getCamposReporte().containsKey(campo.getId())) {
                    this.camposReporte.put(campo.getId(), campo);
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
                this.reporteBienModel.salvar(reporteBien);
                this.campoReporteBienModel.salvar(reporteBien.getCamposReporte());
                
                this.visibleBotonEliminar = true;
                this.visiblePanelNombreReporte = false;
                
                inicializarReportes();
                this.command.setIdReporte(reporteBien.getId());
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
        
        if (!this.camposSeleccionados.isEmpty()) {
            for (Map.Entry<Long, Boolean> row : this.camposSeleccionados.entrySet()) {
                CampoBien campo = this.camposReporte.get(row.getKey());
                if (row.getValue()) {
                    this.command.getCamposReporte().put(campo.getId(), new CampoReporteBien(campo));
                }
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
        Map<String, Object> parametros = new HashMap<String, Object>();
        List<CampoReporteBien> campos = new ArrayList<CampoReporteBien>(this.command.getCamposReporte().values());
        StringBuilder sql = new StringBuilder();
        String campoOrden = null;
        int tipoOrden = 0;
        for (CampoReporteBien campo : campos) {
            sql.append("SELECT b FROM Bien b WHERE 1=1 ");
            if(campo.getValor() != null && !campo.getValor().isEmpty()) {
                sql.append(" AND b.");
                sql.append(campo.getCampoBien().getNombreHQL());
                sql.append("= :");
                sql.append(campo.getCampoBien().getNombreHQL());
                parametros.put(campo.getCampoBien().getNombreHQL(), campo.getValor());
            }
            if (campo.getOrden() != 0) {
                tipoOrden = campo.getOrden();
                campoOrden = campo.getCampoBien().getNombreHQL();
            }
        }
        if (tipoOrden != 0) {
            sql.append(" ORDER BY b.");
            sql.append(campoOrden);
            sql.append(tipoOrden>0?" ASC":" DESC");
        } else {
            sql.append(" ORDER BY b.id_bien ASC");
        }
        
        try {
            String sourceFileName = "C:\\Users\\aocc\\Documents\\NetBeansProjects\\Reportes\\web\\reportes\\reporte.jrxml";
            String pdfFileName = "C:\\Users\\aocc\\Documents\\NetBeansProjects\\Reportes\\web\\reportes\\reporte.pdf";
            
            this.modifyXML(sourceFileName, campos);
            
            List<Bien> bienes = this.bienModel.listar(sql.toString(), parametros);
            List<BienReporte> bienesReporte = new ArrayList<BienReporte>();
            for (Bien bien : bienes) {
                bienesReporte.add(new BienReporte(bien));
            }
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(bienesReporte);
                        
            Map parameters = new HashMap();
            JasperReport jasperReport = JasperCompileManager.compileReport(sourceFileName);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFileName);      
            
            Mensaje.agregarInfo("Reporte generado exitosamente");
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
            this.mensaje = err.getMessage();
        }
    }
    
    public void modifyXML (String filepath, List<CampoReporteBien> campos) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Map<String, CampoReporteBien> mapCampos = new HashMap<String, CampoReporteBien>();
        for (CampoReporteBien campo : campos) {
            mapCampos.put(campo.getCampoBien().getIdColumna(), campo);
        }

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(filepath);
        Element parentElement = (Element)document.getFirstChild();
        System.out.println(parentElement.getNodeName());
        reorderNodes(parentElement, "columnHeader", "staticText", mapCampos);
        reorderNodes(parentElement, "detail", "textField", mapCampos);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(filepath));
        transformer.transform(source, result);
    }
    
    private void reorderNodes(Element jasperReport, String section, String nodeName, Map<String, CampoReporteBien> mapCampos) {
        int x = 0;

        // <columnHeader>
        Element sectionNode = (Element)jasperReport.getElementsByTagName(section).item(0);
        System.out.println(sectionNode.getNodeName());
        
        // <band height="50" splitType="Stretch">
        Element bandNode = (Element)sectionNode.getElementsByTagName("band").item(0);
        System.out.println(bandNode.getNodeName());
        /*  <staticText>
                <reportElement key="keyColumnaId" x="0" y="0" width="100" height="20" uuid="07118efc-0dc3-4eb2-937e-a2bde68919d5"/>
                <text><![CDATA[ID]]></text>
            </staticText> 
            ... */
        NodeList nodeList = bandNode.getElementsByTagName(nodeName);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element node = (Element)nodeList.item(i);
            Element reportElement = (Element)node.getElementsByTagName("reportElement").item(0);
            
            String key = reportElement.getAttribute("key");
            if (mapCampos.containsKey(key)){
                CampoReporteBien campo = mapCampos.get(key);
                reportElement.setAttribute("x", x+"");
                reportElement.setAttribute("width", campo.getTamanoColumna()+"");
                x += campo.getTamanoColumna();
            } else {
                bandNode.removeChild(node);
            }
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

    public Map<Long, Tipo> getTiposReporte() {
        return tiposReporte;
    }

    public void setTiposReporte(Map<Long, Tipo> tiposReporte) {
        this.tiposReporte = tiposReporte;
    }

    public Map<Long, CampoBien> getCamposReporte() {
        return camposReporte;
    }
    
    public List<CampoBien> getListCamposReporte() {
        List<CampoBien> list = new ArrayList<CampoBien>(camposReporte.values());
        return list;
    }

    public void setCamposReporte(Map<Long, CampoBien> camposReporte) {
        this.camposReporte = camposReporte;
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
}