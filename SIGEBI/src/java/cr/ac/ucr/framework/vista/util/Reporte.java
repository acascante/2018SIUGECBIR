package cr.ac.ucr.framework.vista.util;

import com.icesoft.faces.context.ByteArrayResource;

import java.io.ByteArrayOutputStream;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;

public class Reporte {

    private JasperPrint eaJasperPrint = null;
    private String eaExtension = "";
    private String eaurl = "";
    private String eanombreArchivo = "";
    private String earutaAbsoluta = "";
    private ByteArrayOutputStream outputStream;
    private ByteArrayResource resource;
    private int eaarchivo;
    private JRExporter exporter = null;
    String barra = "/";

    public enum Imagenes {
        ruta_imagen_ucr("/imagenes/sippres/LogosReporte/UCR.png");
        public final String ruta;

        Imagenes(String ruta) {
            this.ruta = ruta;
        }
    }

    public enum Modulo {
        estudiantesAdmitidos("reportes/rptEstudiantesAdmitidos.jasper"),
        cursoSaeVsSigecu("reportes/formulacion/ReporteCursoSaeVsSigecu.jasper"),
        bloqueOptSaeVsSigecu("reportes/formulacion/BloquesOptativosSaeVsSigecu.jasper"),
	reporteHorario("reportes/formulacion/reporteHorarios.jasper"),
        plantilla_propuesta("reportes/PlantillaGeneral.jasper"),
        categoria_cambio("reportes/evaluacion/CategoriaCambio.jasper"),
        conclusiones("reportes/evaluacion/Conclusiones.jasper"),
        recomendaciones("reportes/evaluacion/Recomendaciones.jasper"),
        memo("reportes/evaluacion/Memo.jasper"),
        juicio("reportes/evaluacion/PlantillaGeneralJuicio.jasper"),
        resolucion("reportes/evaluacion/ResolucionCurricular.jasper"),
        resolucionAdministrativa("reportes/evaluacion/ResolucionAdministrativa.jasper"),
        informe("reportes/evaluacion/InformeGeneral.jasper"),
        categoriaCambioInformacion("reportes/evaluacion/CategoriaCambioInformacion.jasper"),
        informeInformacion("reportes/evaluacion/InformeInformacion.jasper"),
        mallas("reportes/formulacion/ReporteMalla.jasper"),
        mallaAfectada("reportes/evaluacion/MallaReporteGeneral.jasper");
        public final String ruta;

        private Modulo(String ruta) {
            this.ruta = ruta;
        }
    }

    public String ConvertirRutas(String url) {
        String resultado = "";
        try {
            FacesContext tempFacesContext = FacesContext.getCurrentInstance();
            ServletContext tempServletContext = (ServletContext) tempFacesContext.getExternalContext().getContext();
            resultado = tempServletContext.getRealPath(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public void setParametros(JasperPrint jasperPrint, String url, String nuevoNombreArchivo, int ind_Archivo) {
        setEAJasperPrint(jasperPrint);
        setEAArchivo(ind_Archivo);
        setEAUrl(url);
        setEARutaAbsoluta("reportes/rptEstudiantesAdmitidos.jasper");
        setEANombreArchivo(nuevoNombreArchivo);
    }

    public void getEAExportar() {
        try {
            switch (getEAArchivo()) {
                case 1:
                    exporter = new JRPdfExporter();
                    setEAExtension(".pdf");
                    break;
                case 2:
                    exporter = new JExcelApiExporter();
                    setEAExtension(".xls");
                    break;
                case 3:
                    exporter = new JRRtfExporter();
                    setEAExtension(".rtf");
                    break;
            }
            outputStream = new ByteArrayOutputStream();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, getEAJasperPrint());
            //setEARutaAbsoluta(getEAUrl()+getEANombreArchivo()+getEAExtension());
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            if (getEAExtension().equalsIgnoreCase(".xls")) {
                exporter.setParameter(JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
//              exporter.setParameter(JExcelApiExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
                exporter.setParameter(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                exporter.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
                exporter.setParameter(JExcelApiExporterParameter.IS_IGNORE_CELL_BACKGROUND, Boolean.FALSE);
                exporter.setParameter(JExcelApiExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.FALSE);
            }

            exporter.exportReport();
            resource = new ByteArrayResource(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String generarRutaServlet() {
        String ruta = "";
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            String urlPadre = facesContext.getExternalContext().getRequestContextPath();
            ruta = urlPadre + "/DescargarReporte?url=" + this.getEARutaAbsoluta() + "&nombre=" + this.getEANombreArchivo();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return ruta;
        }
    }

    public List<SelectItem> getobtenerTiposExportar() {
        List<SelectItem> tipos = new ArrayList<SelectItem>();
        tipos.add(new SelectItem("1", "PDF"));
        tipos.add(new SelectItem("2", "Excel"));

        return tipos;
    }

    public String getEAExtension() {
        return eaExtension;
    }

    private void setEAExtension(String eaExtension) {
        this.eaExtension = eaExtension;
    }

    private int getEAArchivo() {
        return eaarchivo;
    }

    private void setEAArchivo(int Archivo) {
        this.eaarchivo = Archivo;
    }

    private JasperPrint getEAJasperPrint() {
        return eaJasperPrint;
    }

    private void setEAJasperPrint(JasperPrint eaJasperPrint) {
        this.eaJasperPrint = eaJasperPrint;
    }

    public String getEAUrl() {
        return eaurl;
    }

    private void setEAUrl(String eaurl) {
        this.eaurl = eaurl;
    }

    public String getEANombreArchivo() {
        return eanombreArchivo;
    }

    private void setEANombreArchivo(String nombreArchivo) {
        this.eanombreArchivo = nombreArchivo;
    }

    public String getEARutaAbsoluta() {
        return earutaAbsoluta;
    }

    private void setEARutaAbsoluta(String rutaAbsoluta) {
        this.earutaAbsoluta = this.ConvertirRutas(rutaAbsoluta);
    }

    public ByteArrayOutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(ByteArrayOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public ByteArrayResource getResource() {
        return resource;
    }

    public void setResource(ByteArrayResource resource) {
        this.resource = resource;
    }
    //Con extension

    public String getNombreCompletoReporte() {
        String nombreCompleto = eanombreArchivo;
        switch (getEAArchivo()) {
            case 1:
                nombreCompleto += ".pdf";
                break;
            case 2:
                nombreCompleto += ".xls";
                break;
            case 3:
                nombreCompleto += ".rtf";
                break;
        }
        return nombreCompleto;
    }
}
