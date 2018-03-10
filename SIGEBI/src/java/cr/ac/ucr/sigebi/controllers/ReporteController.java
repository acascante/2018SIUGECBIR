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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    
    List<DocumentoTraslado> detalle;
    
    String tipoReporteSelec;
    List<SelectItem> itemsTiposReporte;
    
    ReporteTrasladoCommand commandTraslado;
    ReporteMovimientoCommand commandMovimiento;
    
    List<SelectItem> itemsTipoOrden;
    
    String path;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    
    //</editor-fold>

    

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
            path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath().replace('/', '&');
            
            //detalle = trasladoModel.traerTodo(unidadEjecutora);
            itemsTiposReporte = new ArrayList<SelectItem>();
            itemsTiposReporte.add(new SelectItem("PDF", "PDF"));
            itemsTiposReporte.add(new SelectItem("MSEXCEL", "EXCEL"));
            
            itemsTipoOrden = new ArrayList<SelectItem>();
            itemsTipoOrden.add(new SelectItem("ASC", "ASC"));
            itemsTipoOrden.add(new SelectItem("DESC", "DESC"));
                    
            
            commandTraslado = new ReporteTrasladoCommand();
            commandMovimiento = new ReporteMovimientoCommand();

            
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

    //<editor-fold defaultstate="collapsed" desc="Sets & Gets">

    public List<SelectItem> getItemsTipoOrden() {
        return itemsTipoOrden;
    }

    public void setItemsTipoOrden(List<SelectItem> itemsTipoOrden) {
        this.itemsTipoOrden = itemsTipoOrden;
    }
    
    
    
    public ReporteTrasladoCommand getCommandTraslado(){
        return commandTraslado;
    }

    public void setCommandTraslado(ReporteTrasladoCommand commandTraslado) {
        this.commandTraslado = commandTraslado;
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
            parametetros.put("identificacion", commandTraslado.getIdentificacion());
            parametetros.put("descripcion", commandTraslado.getDescripcion());
            
            parametetros.put("marca", commandTraslado.getMarca());
            parametetros.put("modelo", commandTraslado.getModelo());
            parametetros.put("serie", commandTraslado.getSerie());
            
            parametetros.put("nomUnidadOrigen", commandTraslado.getUnidadOrigen());
            parametetros.put("nomUnidadDestino", commandTraslado.getUnidadDestino());
            parametetros.put("nomEstado", commandTraslado.getEstado());
           
            String consulta = commandTraslado.getSQL(unidadEjecutora.getId());
            parametetros.put("consulta", consulta);
            
            parametetros.put("usuarioGenera", usuarioSIGEBI.getNombreCompleto());
            parametetros.put("nomUnidadCustodio", unidadEjecutora.getDescripcion());
            
            reporteDao.ejecutarReporte( exportReporte, ubicReporteTrans, parametetros, tipoReporteSelec);
            
        }catch(Exception e){
            Mensaje.agregarErrorAdvertencia( e, Util.getEtiquetas("sigebi.Traslado.Err.Reporte"));
        }
    }
    
    public void mostrarReporteMovimientos(){
        try{
            
            String[] directorioWeB = path.split("&");
            
            String dir = directorioWeB[1].concat("\\") 
                    + directorioWeB[2].concat("\\") 
                    + directorioWeB[3].concat("\\") 
                    + directorioWeB[4];
            
            String ubicReporte = dir + "\\reportes\\movimientosReporte.jasper"; 
            String exportReporte = "traslados";
            
            Map parameter = new HashMap();
            
            parameter.put("nomUnidadCustodio", unidadEjecutora.getDescripcion());
            parameter.put("usuarioGenera", usuarioSIGEBI.getNombreCompleto());
            
            parameter.put("fechaInicial", df.format(commandMovimiento.getFechaInicio()) );
            parameter.put("fechaFin", df.format(commandMovimiento.getFechaFin()) );
            parameter.put("identificacion", commandMovimiento.getIdentificacion());
            parameter.put("descripcion", commandMovimiento.getDescripcion());
            parameter.put("estado", commandMovimiento.getEstado());
            parameter.put("tipoMovimiento", commandMovimiento.getTipoMovimiento());
            
            String consulta = commandMovimiento.getSQL(unidadEjecutora.getId());
            parameter.put("sql", consulta);
            
            reporteDao.ejecutarReporte( exportReporte, ubicReporte, parameter, tipoReporteSelec);
        }catch(Exception e){
            Mensaje.agregarErrorAdvertencia( e, Util.getEtiquetas("sigebi.Traslado.Err.Reporte"));
        }
    }
    

    // </editor-fold>
    
    
}
