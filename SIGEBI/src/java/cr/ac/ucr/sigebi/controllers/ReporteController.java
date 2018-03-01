/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.commands.ReporteMovimientoCommand;
import cr.ac.ucr.sigebi.commands.ReporteTrasladoCommand;
import cr.ac.ucr.sigebi.daos.ReporteDao;
import cr.ac.ucr.sigebi.domain.DocumentoTraslado;
import cr.ac.ucr.sigebi.models.TrasladoModel;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.model.SelectItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author jorge.serrano
 */
@Controller(value = "controllerReporte")
@Scope("session")
public class ReporteController extends BaseController {

    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    
    @Resource private ReporteDao reporteDao;
    @Resource private TrasladoModel trasladoModel;
    
    List<DocumentoTraslado> detalle;
    
    String tipoReporteSelec;
    List<SelectItem> itemsTiposReporte;
    List<SelectItem> itemsOrdenColumnas1;
    List<SelectItem> itemsOrdenColumnas2;
    List<SelectItem> itemsOrdenColumnas3;
    
    ReporteTrasladoCommand commandTraslado;
    ReporteMovimientoCommand commandMovimiento;
    
    String orden1;
    String orden2;
    String orden3;
    
    String path;
    
    //</editor-fold>

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    
    

    //<editor-fold defaultstate="collapsed" desc="Gets & Sets">
    
    
    public void setItemsTiposReporte(List<SelectItem> itemsTiposReporte) {
        this.itemsTiposReporte = itemsTiposReporte;
    }

    public ReporteMovimientoCommand getCommandMovimiento() {
        return commandMovimiento;
    }

    public void setCommandMovimiento(ReporteMovimientoCommand commandMovimiento) {
        this.commandMovimiento = commandMovimiento;
    }
    
    public String getTipoReporteSelec() {
        return tipoReporteSelec;
    }

    public void setTipoReporteSelec(String tipoReporteSelec) {
        this.tipoReporteSelec = tipoReporteSelec;
    }

    public List<SelectItem> getItemsTiposReporte() {
        return itemsTiposReporte;
    }
    
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Inicializa datos">
    public ReporteController() {
        super();
    }

    @PostConstruct
    public final void inicializar() {
        
        try{
        
//            path = new File(".").getCanonicalPath();
            
            
            //path = System.getProperty("user.dir");
            
            //path = System.getProperty("user.home");
            
            path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath().replace('/', '&');
            
//            Path currentRelativePath = Paths.get("");
//            path = currentRelativePath.toAbsolutePath().toString();
            //System.out.println("Current relative path is: " + s);


            //detalle = trasladoModel.traerTodo(unidadEjecutora);
            itemsTiposReporte = new ArrayList<SelectItem>();
            itemsTiposReporte.add(new SelectItem("PDF", "PDF"));
            itemsTiposReporte.add(new SelectItem("MSEXCEL", "EXCEL"));
            commandTraslado = new ReporteTrasladoCommand();
            commandMovimiento = new ReporteMovimientoCommand();

            itemsOrdenColumnas1 = new ArrayList(commandTraslado.getItemsColumnas().values());
            itemsOrdenColumnas2 = new ArrayList(commandTraslado.getItemsColumnas().values());
            itemsOrdenColumnas3 = new ArrayList(commandTraslado.getItemsColumnas().values());
        
        }catch(Exception err){
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }


    // </editor-fold>
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    /**
     * Lista los informes
     */
    private void listarDatosReporte() {
    
    }
    
    
    //</editor-fold>

    
    public ReporteTrasladoCommand getCommandTraslado(){
        return commandTraslado;
    }

    //<editor-fold defaultstate="collapsed" desc="Sets & Gets">
    public void setCommandTraslado(ReporteTrasladoCommand commandTraslado) {
        this.commandTraslado = commandTraslado;
    }

    public List<SelectItem> getItemsOrdenColumnas1() {
        return itemsOrdenColumnas1;
    }

    public void setItemsOrdenColumnas1(List<SelectItem> itemsOrdenColumnas1) {
        this.itemsOrdenColumnas1 = itemsOrdenColumnas1;
    }

    public List<SelectItem> getItemsOrdenColumnas2() {
        return itemsOrdenColumnas2;
    }

    public void setItemsOrdenColumnas2(List<SelectItem> itemsOrdenColumnas2) {
        this.itemsOrdenColumnas2 = itemsOrdenColumnas2;
    }

    public List<SelectItem> getItemsOrdenColumnas3() {
        return itemsOrdenColumnas3;
    }

    public void setItemsOrdenColumnas3(List<SelectItem> itemsOrdenColumnas3) {
        this.itemsOrdenColumnas3 = itemsOrdenColumnas3;
    }

    public String getOrden1() {
        return orden1;
    }

    public void setOrden1(String orden1) {
        this.orden1 = orden1;
    }

    public String getOrden2() {
        return orden2;
    }

    public void setOrden2(String orden2) {
        this.orden2 = orden2;
    }

    public String getOrden3() {
        return orden3;
    }

    public void setOrden3(String orden3) {
        this.orden3 = orden3;
    }

    
    
    
    
    // </editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Mostrar Reporte">
    
    
    public void mostrarReporteTraslados() {
        try{
            
            
            String[] directorioWeB = path.split("&");
            
            String dir = directorioWeB[1].concat("\\") 
                    + directorioWeB[2].concat("\\") 
                    + directorioWeB[3].concat("\\") 
                    + directorioWeB[4];
            
            String ubicReporteTrans = dir + "\\reportes\\trasladosReporte.jasper"; 
            
            //ubicReporteTrans = "../../reportes/trasladosReporte.jasper";
            String exportReporte = "traslados";
            
            Map parametetros = new HashMap();
            parametetros.put("fechaInicial", commandTraslado.getFechaInicio());
            parametetros.put("fechaFin", commandTraslado.getFechaFin());
            parametetros.put("unidadEjecutora", unidadEjecutora.getId());
            parametetros.put("identificacion", "%"+commandTraslado.getIdentificacion()+"%");
            parametetros.put("descripcion", "%"+commandTraslado.getDescripcion()+"%");
            
            parametetros.put("marca", "%"+commandTraslado.getMarca()+"%");
            parametetros.put("modelo", "%"+commandTraslado.getModelo()+"%");
            parametetros.put("serie", "%"+commandTraslado.getSerie()+"%");
            
            parametetros.put("nomUnidadOrigen", "%"+commandTraslado.getUnidadOrigen()+"%");
            parametetros.put("nomUnidadDestino", "%"+commandTraslado.getUnidadDestino()+"%");
            parametetros.put("nomEstado", "%"+commandTraslado.getEstado()+"%");
            
            
            reporteDao.ejecutarReporte( exportReporte, ubicReporteTrans, parametetros, tipoReporteSelec);
            
        }catch(Exception e){
            Mensaje.agregarErrorAdvertencia( e, Util.getEtiquetas("sigebi.Traslado.Err.Reporte"));
        }
    }
    
    public void mostrarReporteMovimientos(){
        try{
            
            String[] directorioWeB = path.split("&");
            
            String dir = directorioWeB[0].concat(directorioWeB[1].concat(directorioWeB[2]));
            
            String ubicReporteMovim = dir+"\\reportes\\movimientosReporte.jasper"; 
            String exportReporte = "traslados";
            
            Map parameter = new HashMap();
            reporteDao.ejecutarReporte( exportReporte, ubicReporteMovim, parameter, tipoReporteSelec);
        }catch(Exception e){
            Mensaje.agregarErrorAdvertencia( e, Util.getEtiquetas("sigebi.Traslado.Err.Reporte"));
        }
    }
    

    // </editor-fold>
    
    
}
