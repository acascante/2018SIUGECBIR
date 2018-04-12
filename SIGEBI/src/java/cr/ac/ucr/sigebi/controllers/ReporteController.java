/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Reporte;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.commands.ReporteInventFaltantesCommand;
import cr.ac.ucr.sigebi.commands.ReporteMovimientoCommand;
import cr.ac.ucr.sigebi.commands.ReporteTrasladoCommand;
import cr.ac.ucr.sigebi.daos.ReporteDao;
import cr.ac.ucr.sigebi.utils.Constantes;
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
    
    
    String tipoReporteSelec;
    List<SelectItem> itemsTiposReporte;
    
    ReporteTrasladoCommand commandTraslado;
    ReporteMovimientoCommand commandMovimiento;
    ReporteInventFaltantesCommand commandInventarioFaltantes;
    
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

    public ReporteInventFaltantesCommand getCommandInventarioFaltantes() {
        return commandInventarioFaltantes;
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
            commandInventarioFaltantes = new ReporteInventFaltantesCommand();
            
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

    
    //<editor-fold defaultstate="collapsed" desc="Navegar Reportes">

    
    public void reporteFaltantes() {
        try {
            //inicializaDatos();
            Util.navegar(Constantes.KEY_REPORTE_INVENT_FALTANTES);

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getCause().getMessage());
        }
    }

    public void reporteSobrantes() {
        Util.navegar(Constantes.KEY_REPORTE_SOBRANTES);
    }
    // </editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Mostrar Reporte">
    
    
    public void mostrarReporteTraslados() {
        try{
            Reporte reporte = new Reporte();
            String directorioRelativa = "reportes/trasladosReporte.jasper";
            //Dentro de reporteDao.ejecutarReporte busca la ruta absoluta.
            
            String ubicReporte = reporte.ConvertirRutas(directorioRelativa);
            //String ubicReporteTrans = directorioRelativa; //dir + "\\reportes\\trasladosReporte.jasper"; 
            
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
            
            reporteDao.ejecutarReporte( exportReporte, ubicReporte, parametetros, tipoReporteSelec);
            
        }catch(Exception e){
            //Mensaje.agregarErrorAdvertencia( e, Util.getEtiquetas("sigebi.Traslado.Err.Reporte"));
            Mensaje.agregarErrorAdvertencia( e.getCause().getMessage() );
        }
    }
    
    public void mostrarReporteMovimientos(){
        try{
            Reporte reporte = new Reporte();
            String directorioRelativa = "reportes/movimientosReporte.jasper";
            //Dentro de reporteDao.ejecutarReporte busca la ruta absoluta.
            String ubicReporte = reporte.ConvertirRutas(directorioRelativa);
            //String ubicReporte = directorioRelativa;// directorioRaiz + rutaRelativa; 
            
            String exportReporte = "movimientos";
            
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
            commandMovimiento.setError(e.getCause().getMessage());
        }
    }
    
    
    public void mostrarInventarioFaltantes(){
        try{
            Reporte reporte = new Reporte();
            
            
            commandInventarioFaltantes.setTomaFisica(1l);
            commandInventarioFaltantes.setUbicacion(1l);
            
            
            
            
            String directorioRelativa = "reportes/fatlInventReporte.jasper";
            //Dentro de reporteDao.ejecutarReporte busca la ruta absoluta.
            String ubicReporte = reporte.ConvertirRutas(directorioRelativa);// directorioRaiz + rutaRelativa; 
            
            String exportReporte = "inventFaltantesReporte";
            
            Map parametros = new HashMap();
            
            
            parametros.put("numTomaFisica", commandInventarioFaltantes.getTomaFisica());
            parametros.put("ubicacion", commandInventarioFaltantes.getUbicacion());
            
            parametros.put("unidadEjecutora", unidadEjecutora.getId());
            parametros.put("identificacion", commandInventarioFaltantes.getIdentificacion());
            parametros.put("descripcion", commandInventarioFaltantes.getDescripcion());
            
            parametros.put("marca", commandInventarioFaltantes.getMarca());
            parametros.put("modelo", commandInventarioFaltantes.getModelo());
            parametros.put("serie", commandInventarioFaltantes.getSerie());
            
            parametros.put("nomEstado", commandInventarioFaltantes.getEstado());
           
            String consulta = commandInventarioFaltantes.getSQL(unidadEjecutora.getId(), 1L, 1L);
            parametros.put("consulta", consulta);
            
            parametros.put("usuarioGenera", usuarioSIGEBI.getNombreCompleto());
            parametros.put("nomUnidadCustodio", unidadEjecutora.getDescripcion());
            
            
            reporteDao.ejecutarReporte( exportReporte, ubicReporte, parametros, tipoReporteSelec);
        }catch(Exception e){
            Mensaje.agregarErrorAdvertencia( e, Util.getEtiquetas("sigebi.Traslado.Err.Reporte"));
            commandMovimiento.setError(e.getCause().getMessage());
        }
    }
    

    // </editor-fold>
    
    
}
