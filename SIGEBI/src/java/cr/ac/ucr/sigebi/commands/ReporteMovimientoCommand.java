/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.utils.Constantes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author jorge.serrano
 */
public class ReporteMovimientoCommand {
    
    //<editor-fold defaultstate="collapsed" desc="Constantes">
    
    public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getDefault();
    
    //</editor-fold>
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    private Date fechaInicio;
    private Date fechaFin;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    
    private String identificacion;
    private String descripcion;
    
    private String estado;
    private String tipoMovimiento;
    
    String tipoOrden;
    
    int orden1;
    int orden2;
    
    private Map<Integer, SelectItem> itemsColumnas;
    
    List<SelectItem> itemsOrdenColumnas1;
    List<SelectItem> itemsOrdenColumnas2;
    
    private Map<Integer, SelectItem> itemsColumnas2;
    
    String directorioRaiz;
    String rutaReporte;
    
    String error;
    
    //</editor-fold>

    public ReporteMovimientoCommand() {
        
        fechaInicio = getDefaultDate();
        fechaFin = getDefaultDate();
        
        this.itemsColumnas = new HashMap<Integer, SelectItem>();
        itemsColumnas.put(1,new SelectItem(1,"Identificación"));
        itemsColumnas.put(2,new SelectItem(2,"Descripción"));
        itemsColumnas.put(3,new SelectItem(3,"Estado"));
        itemsColumnas.put(4,new SelectItem(4,"Fecha"));
        itemsColumnas.put(5,new SelectItem(5,"Tipo Movimiento"));
        
        
        itemsColumnas2 = new HashMap<Integer, SelectItem>();
        itemsColumnas2.putAll(getItemsColumnas());
        
        itemsOrdenColumnas1 = new ArrayList(itemsColumnas2.values());
        int valor1 = (Integer) itemsOrdenColumnas1.get(0).getValue();
        setOrden1(valor1);
        //Elimino valor 1 seleccionado

        itemsOrdenColumnas2 = opcionesListado2(itemsColumnas2);
        setOrden2( (Integer) itemsOrdenColumnas2.get(0).getValue());
        
        
    }
    
    
    private List<SelectItem> opcionesListado2(Map<Integer, SelectItem> columnas){
        columnas.remove(getOrden1());
        return new ArrayList(columnas.values());
    }
    
    
    public void cambioOrden1(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        try{
            itemsColumnas2 = new HashMap<Integer, SelectItem>();
            itemsColumnas2.putAll(getItemsColumnas());
            itemsOrdenColumnas2 = opcionesListado2(itemsColumnas2);
            setOrden2( (Integer) itemsOrdenColumnas2.get(0).getValue());
            
        }catch(Exception err){
            
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">    

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    
    public String getDirectorioRaiz() {
        return directorioRaiz;
    }

    public void setDirectorioRaiz(String directorioRaiz) {
        this.directorioRaiz = directorioRaiz;
    }

    public String getRutaReporte() {
        return rutaReporte;
    }

    public void setRutaReporte(String rutaReporte) {
        this.rutaReporte = rutaReporte;
    }

    
    
    
    private Date getDefaultDate() {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance(Constantes.DEFAULT_TIME_ZONE);
        calendar.setTime(today);
        return calendar.getTime();
    }
    
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getTipoOrden() {
        return tipoOrden;
    }

    public void setTipoOrden(String tipoOrden) {
        this.tipoOrden = tipoOrden;
    }

    public int getOrden1() {
        return orden1;
    }

    public void setOrden1(int orden1) {
        this.orden1 = orden1;
    }

    public int getOrden2() {
        return orden2;
    }

    public void setOrden2(int orden2) {
        this.orden2 = orden2;
    }

    public Map<Integer, SelectItem> getItemsColumnas() {
        return itemsColumnas;
    }

    public void setItemsColumnas(Map<Integer, SelectItem> itemsColumnas) {
        this.itemsColumnas = itemsColumnas;
    }
    
    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
    
    
    
    
    //</editor-fold>

    
    
    public String getSQL(Long unidadEjecutora){
        String sql = "SELECT DISTINCT \n" +
"            IDENTIFICACION \n" +
"            , DESCRIPCION \n" +
"            , ESTADO \n" +
"            , FECHA \n" +
"            , MOVIMIENTO \n" +
"            FROM \n" +
"            ("
                + "SELECT\n" +
"              IDEN.NUMERO_IDENTIFICACION IDENTIFICACION\n" +
"            , BIEN.DESCRIPCION DESCRIPCION\n" +
"            , ESTADO.NOMBRE     ESTADO\n" +
"            , SOL.FECHA\n" +
"            , CASE  SOL.DISCRIMINATOR\n" +
"               WHEN 1 THEN 'SOLICITUD EXCLUSION'\n" +
"               WHEN 2 THEN 'SOLICITUD DONACION'\n" +
"               WHEN 3 THEN 'SOLICITUD TRASLADO'\n" +
"               WHEN 4 THEN 'SOLICITUD PRESTAMO'\n" +
"               ELSE 'MOVIMIENTO NO REGISTRADO'\n" +
"            END MOVIMIENTO\n" +
"        FROM SIGEBI_OAF.SIGB_SOLICITUD_DETALLE SDET\n" +
"            LEFT JOIN SIGEBI_OAF.SIGB_SOLICITUD SOL\n" +
"            ON(SDET.ID_SOLICITUD = SOL.ID_SOLICITUD)\n" +
"            LEFT JOIN SIGEBI_OAF.SIGB_BIEN BIEN\n" +
"            ON(SDET.ID_BIEN = BIEN.ID_BIEN)\n" +
"            LEFT JOIN SIGEBI_OAF.SIGB_IDENTIFICACION IDEN\n" +
"            ON (BIEN.ID_IDENTIFICACION = IDEN.ID_IDENTIFICACION)\n" +
"            LEFT JOIN SIGEBI_OAF.SIGB_ESTADO ESTADO\n" +
"            ON (SOL.ID_ESTADO = ESTADO.ID_ESTADO)\n" +
                
"	WHERE SOL.FECHA >= TO_DATE('"+ df.format(fechaInicio) +"', 'DD/MM/YYYY')\n" +
"        AND   SOL.FECHA <= TO_DATE('"+ df.format(fechaFin) +"', 'DD/MM/YYYY')\n" +
"        AND ( SOL.ID_UNIDAD_EJECUTORA = "+unidadEjecutora+" )\n" +
"        AND UPPER(IDEN.NUMERO_IDENTIFICACION) LIKE  UPPER('%"+identificacion+"%')\n" +
"        AND UPPER(BIEN.DESCRIPCION) LIKE  UPPER('%"+descripcion+"%')\n" +
"        AND UPPER(ESTADO.NOMBRE) LIKE  UPPER('%"+estado+"%')\n"
                + ")\n" +
"	WHERE UPPER(MOVIMIENTO) LIKE UPPER('%"+tipoMovimiento+"%')\n" +
"	ORDER BY "+orden1+" "+tipoOrden+", "+orden2+" "+tipoOrden;
        return sql;
    }
    
    

}
