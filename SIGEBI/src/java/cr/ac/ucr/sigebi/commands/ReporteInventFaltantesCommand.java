/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import java.util.ArrayList;
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
public class ReporteInventFaltantesCommand {
    
    //<editor-fold defaultstate="collapsed" desc="Constantes">
    
    public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getDefault();
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    
    private String identificacion;
    private String descripcion;
    private String marca;
    private String modelo;
    private String serie;
    
    private Long ubicacion;
    private Long tomaFisica;
    private String oficialAsignado;
    private String estado;
    
    String tipoOrden;
    
    int orden1;
    int orden2;
    int orden3;
    
    private Map<Integer, SelectItem> itemsColumnas;
    List<SelectItem> itemsOrdenColumnas1;
    List<SelectItem> itemsOrdenColumnas2;
    List<SelectItem> itemsOrdenColumnas3;
    
    private Map<Integer, SelectItem> itemsColumnas2;
    
    //</editor-fold>

    public ReporteInventFaltantesCommand() {
        
        this.itemsColumnas = new HashMap<Integer, SelectItem>();
        
        itemsColumnas.put(2,new SelectItem(2,"Identificación"));
        itemsColumnas.put(3,new SelectItem(3,"Descripción"));
        
        itemsColumnas.put(4,new SelectItem(4, "Marca"));
        itemsColumnas.put(5,new SelectItem(5, "Modelo"));
        itemsColumnas.put(6,new SelectItem(6,"Serie"));
        
        itemsColumnas.put(7,new SelectItem(7,"Oficial Asignado"));
        itemsColumnas.put(8,new SelectItem(8,"Estado"));
        
        
        itemsColumnas2 = new HashMap<Integer, SelectItem>();
        itemsColumnas2.putAll(getItemsColumnas());

        itemsOrdenColumnas1 = new ArrayList(itemsColumnas2.values());
        int valor1 = (Integer) itemsOrdenColumnas1.get(0).getValue();
        setOrden1(valor1);
        //Elimino valor 1 seleccionado

        itemsOrdenColumnas2 = opcionesListado2(itemsColumnas2);
        setOrden2( (Integer) itemsOrdenColumnas2.get(0).getValue());

        itemsOrdenColumnas3 = opcionesListado3(itemsColumnas2);
        setOrden3( (Integer) itemsOrdenColumnas3.get(0).getValue());
            
            
        
        
    }
    
    private List<SelectItem> opcionesListado2(Map<Integer, SelectItem> columnas){
        columnas.remove(getOrden1());
        return new ArrayList(columnas.values());
    }
    
    private List<SelectItem> opcionesListado3(Map<Integer, SelectItem> columnas){
        columnas.remove(getOrden1());
        columnas.remove(getOrden2());
        return new ArrayList(columnas.values());
    }
    
    
    public Map<Integer, SelectItem> getItemsColumnas() {
        return itemsColumnas;
    }

    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">

    public Long getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Long ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Long getTomaFisica() {
        return tomaFisica;
    }

    public void setTomaFisica(Long tomaFisica) {
        this.tomaFisica = tomaFisica;
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

    public int getOrden3() {
        return orden3;
    }

    public void setOrden3(int orden3) {
        this.orden3 = orden3;
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

    
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getOficialAsignado() {
        return oficialAsignado;
    }

    public void setOficialAsignado(String oficialAsignado) {
        this.oficialAsignado = oficialAsignado;
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

    
    
    //</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="Métodos ">
    
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
            itemsOrdenColumnas3 = opcionesListado3(itemsColumnas2);
            
        }catch(Exception err){
            
        }
    }
    
    public void cambioOrden2(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        try{
            itemsColumnas2 = new HashMap<Integer, SelectItem>();
            itemsColumnas2.putAll(getItemsColumnas());
            itemsOrdenColumnas3 = opcionesListado3(itemsColumnas2);
            
        }catch(Exception err){
            
        }
    }
    
    
    public String getSQL(Long unidadEjecutora, Long tomaFisica, Long ubicacion){
        String sql = "         SELECT  \n" +
"            IDEN.NUMERO_IDENTIFICACION  IDENTIFICACION\n" +
"            , SUB_CLASIF.NOMBRE          SUB_CLASIFICACION\n" +
"            , NVL(CAR.MARCA, '--')                 MARCA\n" +
"            , NVL(CAR.MODELO, '--')               MODELO\n" +
"            , NVL(CAR.SERIE, '--')                 SERIE\n" +
"            \n" +
"            , UNID.DSC_UNIDAD_EJECUTORA	 UNIDAD_EJECUTORA\n" +
"            , UBIC.DETALLE               UBICACION\n" +
"            , 'FUNCIONARIO ASIGNADO'     FUNCIONARIO_ASIGNADO\n" +
"            , SIGEBI_OAF.FC_SIGB_OBTENER_DOCUMENTO_BIEN(BIEN.ID_BIEN) ESTADO\n" +
"            \n" +
"            \n" +
"        FROM\n" +
"        (\n" +
"        SELECT BIEN.* \n" +
"            FROM SIGEBI_OAF.SIGB_BIEN BIEN\n" +
"            WHERE BIEN.ID_UBICACION = "+ubicacion+"  \n" +
"                AND BIEN.ID_ESTADO IN (5, 37) \n" +
"        ) BIEN \n" +
"        LEFT JOIN( \n" +
"            SELECT BIEN.* \n" +
"            FROM SIGEBI_OAF.SIGB_TOMA_FISICA TOM\n" +
"                LEFT JOIN SIGEBI_OAF.SIGB_TOMA_FISICA_UNITARIA TOM_UNI\n" +
"                ON (TOM_UNI.ID_TOMA_FISICA = TOM.ID_TOMA_FISICA)\n" +
"                LEFT JOIN SIGEBI_OAF.SIGB_BIEN BIEN\n" +
"                ON(TOM_UNI.ID_BIEN = BIEN.ID_BIEN)\n" +
"            WHERE TOM.ID_TOMA_FISICA = "+tomaFisica+" \n" +
"        ) TOMA \n" +
"            ON (BIEN.ID_BIEN = TOMA.ID_BIEN)\n" +
"        LEFT JOIN SIGEBI_OAF.V_SIGB_BIEN_CARACTERISTICA CAR\n" +
"            ON (BIEN.ID_BIEN = CAR.ID)\n" +
"        LEFT JOIN SIGEBI_OAF.SIGB_IDENTIFICACION IDEN\n" +
"            ON (BIEN.ID_IDENTIFICACION = IDEN.ID_IDENTIFICACION)\n" +
"        LEFT JOIN SIGEBI_OAF.SIGB_ESTADO EST\n" +
"            ON (BIEN.ID_ESTADO = EST.ID_ESTADO)\n" +
"        LEFT JOIN SIGEBI_OAF.V_SIGB_UNIDAD_EJECUTORA UNID\n" +
"            ON(BIEN.ID_UNIDAD_EJECUTORA = UNID.ID)\n" +
"        LEFT JOIN SIGEBI_OAF.SIGB_UBICACION UBIC\n" +
"            ON(BIEN.ID_UBICACION = UBIC.ID_UBICACION)\n" +
"        LEFT JOIN SIGEBI_OAF.SIGB_SUB_CLASIFICACION SUB_CLASIF \n" +
"            ON (BIEN.ID_SUB_CLASIFICACION = SUB_CLASIF.ID_SUB_CLASIFICACION) \n" +
"	WHERE BIEN.ID_UNIDAD_EJECUTORA = "+unidadEjecutora+"\n" +
"        AND UPPER(IDEN.NUMERO_IDENTIFICACION) LIKE  UPPER('%"+identificacion+"%')\n" +
"        AND UPPER(BIEN.DESCRIPCION) LIKE  UPPER('%"+descripcion+"%')\n" +
"        AND UPPER(NVL(CAR.MARCA, '--')) LIKE  UPPER('%"+marca+"%')\n" +
"        AND UPPER(NVL(CAR.MODELO, '--')) LIKE  UPPER('%"+modelo+"%')\n" +
"        AND UPPER(NVL(CAR.SERIE, '--')) LIKE  UPPER('%"+serie+"%')\n" +
"        AND UPPER(EST.NOMBRE) LIKE  UPPER('%"+estado+"%')\n" +
"	ORDER BY UBIC.DETALLE  "+tipoOrden+",  "+orden1+" "+tipoOrden+", "+orden2+" "+tipoOrden+", "+orden3+" "+tipoOrden;
        return sql;
    }
    

    //</editor-fold>
    
}
