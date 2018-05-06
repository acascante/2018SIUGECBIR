/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import com.icesoft.faces.context.effects.JavascriptContext;
import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Util;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jorge.serrano
 */
@Repository(value = "reporteDao")
public class ReporteDao extends GenericDaoImpl {

    // <editor-fold defaultstate="collapsed" desc="Atributos">
    @Autowired
    private DaoHelper dao;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Metodos">
    @Transactional
    public void ejecutarReporte(String idReporte
                                , String direccionReporte
                                , Map parametros
                                , String formatoReporte) throws JRException, Exception {

        JasperReport reporte;
        JasperPrint jasperPrint = null;
        JRExporter exporter = null;
        String extension = "";
//        FacesContext tempFacesContext = FacesContext.getCurrentInstance();
//        ServletContext tempServletContext = (ServletContext) tempFacesContext.getExternalContext().getContext();
//        String realPath = tempServletContext.getRealPath(direccionReporte);

        JRProperties.setProperty("net.sf.jasperreports.query.executer.factory.plsql",
                "com.jaspersoft.jrx.query.PlSqlQueryExecuterFactory");
        reporte = (JasperReport) JRLoader.loadObjectFromFile(direccionReporte);//realPath);

        jasperPrint = JasperFillManager.fillReport(reporte, parametros, dao.getHibernateTemplate().getSessionFactory().getCurrentSession().connection());


        if (formatoReporte.equalsIgnoreCase("PDF")) {
            exporter = new JRPdfExporter(); //PDF
            extension = ".pdf";
        } else if (formatoReporte.equalsIgnoreCase("MSEXCEL")) {
            exporter = new JExcelApiExporter(); //Excel
            extension = ".xlsx";
            /*
            exporter.setParameter(JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporter.setParameter(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporter.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
            exporter.setParameter(JExcelApiExporterParameter.IS_IGNORE_CELL_BACKGROUND, Boolean.FALSE);
            exporter.setParameter(JExcelApiExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.FALSE);
            */
        } else if (formatoReporte.equalsIgnoreCase("HTML")) {
            exporter = new JRHtmlExporter(); //Html
            extension = ".html";
        } else if (formatoReporte.equalsIgnoreCase("RTF")) {
            exporter = new JRRtfExporter(); //Rtf
            extension = ".rtf";
        } else if (formatoReporte.equalsIgnoreCase("XML")) {
            exporter = new JRXmlExporter(); //Xml
            extension = ".xml";
        }
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        String direccion_exportacion = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("reportes/" + idReporte + extension);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(direccion_exportacion));
//        try {
            exporter.exportReport();
//        } catch (JRException ex) {
//            throw new Exception(ex);
////Logger.getLogger(ReporteDao.class.getName()).log(Level.SEVERE, null, ex);
//        }

        JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "reporte('" + idReporte + "','" + extension + "');");
//        } catch (Exception e) {
//            throw new FWExcepcion(Util.getEtiquetas("siiagc.error.reporte.generacion"),
//                    "Error al generar un reporte en la clase " + this.getClass()
//                    + " en el método ejecutarReporte(String idReporte, String direccionReporte, Map parametros, String formatoReporte)", e);
//        }

        // </editor-fold>
    }
}
