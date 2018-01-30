/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import com.icesoft.faces.component.inputfile.FileInfo;
import com.icesoft.faces.component.inputfile.InputFile;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.commands.BienCommand;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.BienCaracteristica;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Proveedor;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.Ubicacion;
import cr.ac.ucr.sigebi.entities.AccesoriosEntity;
import cr.ac.ucr.sigebi.domain.Adjunto;
import cr.ac.ucr.sigebi.entities.NotaEntity;
import cr.ac.ucr.sigebi.models.AccesorioModel;
import cr.ac.ucr.sigebi.models.AdjuntoModel;
import cr.ac.ucr.sigebi.models.BienCaracteristicaModel;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.EstadoModel;
import cr.ac.ucr.sigebi.models.NotaModel;
import cr.ac.ucr.sigebi.models.ProveedorModel;
import cr.ac.ucr.sigebi.models.TipoModel;
import cr.ac.ucr.sigebi.models.UbicacionModel;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jorge.serrano
 */
public class BienController_Adicional extends BaseController{

    
    //<editor-fold defaultstate="collapsed" desc="Variables de la Clase">
    
    Bien bien;// = new ProveedorEntity();
    

    protected BienCommand command;

    boolean bienRegistrado;
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Gets & Sets">
    
    public void setBien(Bien bien) {
        this.bien = bien;
    }
    
    public Bien getBien() {
        
        return bien;
        
        
    }

    public boolean isBienRegistrado() {
        return bienRegistrado;
    }

    public void setBienRegistrado(boolean bienRegistrado) {
        this.bienRegistrado = bienRegistrado;
    }

    public List<SelectItem> getUbicacionOptions() {
        return ubicacionOptions;
    }

    public void setUbicacionOptions(List<SelectItem> ubicacionOptions) {
        this.ubicacionOptions = ubicacionOptions;
    }

    
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Inicializa Datos">
    public BienController_Adicional() {
        super();
        bien = new Bien();
    }
    
    protected void iniciaComplementos(){
        
        notas = new ArrayList<NotaEntity>();
        nota = new NotaEntity();

        eliminarNotaVisible = false;

        adjuntoDescripcion = "";
        
        caracteristica = new BienCaracteristica();
        
        cargarOpcionesCaract();
        inicializaUbicaciones();
        cargarCaracteristicasBien();
        inicializaAdjuntos();
                
        command =new BienCommand(bien);
        bienRegistrado = true;
    }
    //</editor-fold>
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Acceso a Datos">

    @Resource private NotaModel notaModel;
    @Resource private ProveedorModel provModel;
    @Resource private UbicacionModel ubicModel;
    @Resource private BienModel bienModel;
    // </editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Proveedores">
    
    List<Proveedor> proveedores;
    String provSelccionado;
    String provId;
    
    
    void inicializaProveedores(){
        
    }
    
    public void cargarProveedores() {
        
        try {
        proveedores = new ArrayList<Proveedor>();
        mensajeProveedores = "";
        proveedores = provModel.listar();
        //mensajeProveedores = "Cargados";
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.ProveedorCarga"));
        }
    }

    public void filtroProveedor(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        try {

            proveedores = new ArrayList<Proveedor>();
 //           proveedores = provModel.filtroProveedores(provIdentificacion, provNombre);

            mensajeProveedores = "Filtros Busqueda";
        } catch (Exception err) {
            mensajeProveedores = err.getMessage();
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Modal Proveedores">
    boolean proveedoresVisible;

    public void selecProveedor(ActionEvent pEvent) {

        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        Proveedor prov = (Proveedor) pEvent.getComponent().getAttributes().get("provSeleccionado");
        provId = prov.getNumPersona().toString();
        provSelccionado = prov.getNombre() + " " + prov.getPrimerApellido();

        proveedoresVisible = false;
    }

    public boolean mostrarProveedores() {
        proveedoresVisible = true;
        return true;
    }

    public void proveedoresLimpiar() {
        provId = "";
        provSelccionado = "";
    }

    public boolean cerrarProveedores() {
        proveedoresVisible = false;
        return false;
    }

    public boolean isProveedoresVisible() {
        return proveedoresVisible;
    }

    public void setProveedoresVisible(boolean proveedoresVisible) {
        this.proveedoresVisible = proveedoresVisible;
    }

    //</editor-fold>
    
    String provIdentificacion;
    String provNombre;
    String mensajeProveedores;

    public String getMensajeProveedores() {
        return mensajeProveedores;
    }

    public void setMensajeProveedores(String mensajeProveedores) {
        this.mensajeProveedores = mensajeProveedores;
    }

    public String getProvIdentificacion() {
        return provIdentificacion;
    }

    public void setProvIdentificacion(String provIdentificacion) {
        this.provIdentificacion = provIdentificacion;
    }

    public String getProvNombre() {
        return provNombre;
    }

    public void setProvNombre(String provNombre) {
        this.provNombre = provNombre;
    }

    public List<Proveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }
    //</editor-fold>
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Ubicacion">
    
    String selectionUbicacion;
    
    // comboBox subCategorias
    List<SelectItem> ubicacionOptions;
    
    String ubicacionId;
    String ubicacionNombre;
    
    boolean ubicacionVisible;
    
    public void ubicacionMostrar() {
        try{
            ubicacionVisible = true;
        }
        catch(Exception err){
            Mensaje.agregarErrorAdvertencia("Error");
        }
    }
    
    
    public void ubicacionLimpiar() {
        try{
            ubicacionId = "";
            ubicacionNombre = "";
        }
        catch(Exception err){
            Mensaje.agregarErrorAdvertencia("Error");
        }
    }
    
    
    public void cerrarUbicacion() {
        try{
            ubicacionVisible = false;
        }
        catch(Exception err){
            Mensaje.agregarErrorAdvertencia("Error");
        }
    }
    
    public void ubicacionSelectCambio(ValueChangeEvent event) {
        try{
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }

            // Obtyengo el valor seleccionado
            String valor = event.getNewValue().toString();
            String[] valores = valor.split("#");
            if(valores.length > 1){
                ubicacionId = valores[0];
                ubicacionNombre = valores[1];
            }
            else{
                ubicacionId = "";
                ubicacionNombre = "";
            }
        }
        catch(Exception err){
            Mensaje.agregarErrorAdvertencia("Error");
        }
        
    }

    
    Map<Integer, Ubicacion> ubicaciones = new HashMap<Integer, Ubicacion>();
    
    protected void inicializaUbicaciones(){
        //ubicaciones = ;
        try{
        ubicaciones = new HashMap<Integer, Ubicacion>();
        ubicacionOptions = new ArrayList<SelectItem>();
        
        List<Ubicacion> ubicacionesList;
        ubicacionesList = ubicModel.listar(unidadEjecutora.getId());
        for (Ubicacion item : ubicacionesList) {
            ubicaciones.put(item.getId(), item);
            ubicacionOptions.add(new SelectItem(item.getId(), item.getDetalle()));
        }
        
        //Carga un bien 21 para pruebas 
        bien = bienModel.buscarPorId(21L);
        
        cargarCaracteristicasBien();
        
        Mensaje.agregarInfo("Se cargo el bien: "+bien.getDescripcion());
        }catch(Exception err){
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }
    
    
    public String getUbicacionId() {
        return ubicacionId;
    }

    public void setUbicacionId(String ubicacionId) {
        this.ubicacionId = ubicacionId;
    }

    public String getUbicacionNombre() {
        return ubicacionNombre;
    }

    public void setUbicacionNombre(String ubicacionNombre) {
        this.ubicacionNombre = ubicacionNombre;
    }

    public boolean isUbicacionVisible() {
        return ubicacionVisible;
    }

    public void setUbicacionVisible(boolean ubicacionVisible) {
        this.ubicacionVisible = ubicacionVisible;
    }

    public String getSelectionUbicacion() {
        return selectionUbicacion;
    }

    public void setSelectionUbicacion(String selectionUbicacion) {
        this.selectionUbicacion = selectionUbicacion;
    }
    
    
    
    //</editor-fold>           

    
    
    //<editor-fold defaultstate="collapsed" desc="Tab Notas del Activo">
    
    
    NotaEntity nota;
    List<NotaEntity> notas;
    
    boolean eliminarNotaVisible;

    String notaDetalle;

    public void guardarNota() {

        if (bien.getId() < 1) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.BienNoRegistrado"));
            return;
        }
        nota.setIdBien(Integer.parseInt(bien.getId().toString()));
        nota.setIdEstado(1);
        nota.setDetalle(notaDetalle);

        if (nota.getDetalle().trim().length() < 5) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.NotaInvalida"));
            return;
        }

        //mensajeNota = detalle;
        nota.setIdNota(notaModel.obtenerId());

        String resp = notaModel.guardarNuevo(nota);

        notas = notaModel.traerTodo(Integer.parseInt(bien.getId().toString()));//FIXME

        if (resp.length() > 0) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.Nota"));
        } else {
            nota = new NotaEntity();
        }
    }

    public void eliminarNota(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            nota = new NotaEntity();
            nota = (NotaEntity) pEvent.getComponent().getAttributes().get("notaSeleccionada");
            eliminarNotaVisible = true;
            //mensajeNota = notaModel.eliminarNota(nota);

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.Nota"));
        }
    }

    public void eliminarNotaCancelar() {
        try {
            nota = new NotaEntity();
            eliminarNotaVisible = false;

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.Nota"));
        }
    }

    public void eliminarNotaConfirmar() {
        try {
            notaModel.eliminarNota(nota);

            nota = new NotaEntity();
            notas = new ArrayList<NotaEntity>();
            notas = notaModel.traerTodo(Integer.parseInt(bien.getId().toString()));
            eliminarNotaVisible = false;

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.NotaEliminar"));
        }
    }

    public String getNotaDetalle() {
        return notaDetalle;
    }

    public void setNotaDetalle(String notaDetalle) {
        this.notaDetalle = notaDetalle;
    }

    public boolean isEliminarNotaVisible() {
        return eliminarNotaVisible;
    }

    public void setEliminarNotaVisible(boolean eliminarNotaVisible) {
        this.eliminarNotaVisible = eliminarNotaVisible;
    }

    
    public NotaEntity getNota() {
        return nota;
    }

    public void setNota(NotaEntity nota) {
        this.nota = nota;
    }
    
    public List<NotaEntity> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaEntity> notas) {
        this.notas = notas;
    }

    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Tab Características">
    String mensajeCaracteristicas;
    String selectCaracteristica;
    String descCaracteristica;
    String constCaracteristicas = "CARACTERISTICA";
    // comboBox subCategorias
    List<SelectItem> caracteristicasOptions;

    List<BienCaracteristica> caracteristicas;
    List<Tipo> caracteristicasObjOptions;
    Map<Integer, BienCaracteristica> caracteristicasRegistradas;
    BienCaracteristica caracteristica;
    boolean modifCaracterVisible;

    @Resource private TipoModel tipoModel;
    @Resource private EstadoModel estadoModel;
    @Resource private BienCaracteristicaModel bienCaracModel;
    
    public void guardarCaracteristica() {
        try{

            if (descCaracteristica.length() < 3) {
                mensajeCaracteristicas = "Debe registrar al menos 4 caracteristicas.";
                return;
            }
            if (selectCaracteristica.equals("-1")) {
                mensajeCaracteristicas = "Seleccione Caracteristica.";
                return;
            }
            BienCaracteristica registro = new BienCaracteristica();

            Tipo caract = new Tipo();
            caract.setId(Integer.parseInt(selectCaracteristica));

            //FIXME
            registro.setBien(bien);
            registro.setDetalle(descCaracteristica);
            registro.setTipo(caract);
            Estado estado = estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO);

            registro.setEstado(estado);

            // TODO revisar almacenamiento de caracteristicas, deberian tener su propio model y dao, no tiene por que estar en TIPO
            bienCaracModel.almacenar(registro);
            descCaracteristica = "";
            cargarCaracteristicasBien();
            actualizarOpcionesCaracteristicas();
        }catch(Exception err){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.cargarCaracteristica"));
        }
    }

    public void cargarCaracteristicasBien() {
        try{
            modifCaracterVisible = false;

            caracteristicas = new ArrayList<BienCaracteristica>();
            caracteristicas = bienCaracModel.listarPorBien(bien);

            caracteristica = new BienCaracteristica();
            
            caracteristicasRegistradas  = new HashMap<Integer, BienCaracteristica>();
            for (BienCaracteristica item : caracteristicas) {
                caracteristicasRegistradas.put(item.getTipo().getId(), item);
            }
            
            actualizarOpcionesCaracteristicas();
            //mensajeCaracteristicas = "Debo llenar el select de características";
        }catch(Exception err){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.registrarCaracteristica"));
        }
    }

    protected void cargarOpcionesCaract() {
        caracteristicasObjOptions = tipoModel.listarPorDominio(constCaracteristicas);//FIXME

    }
    
    protected void actualizarOpcionesCaracteristicas(){
        caracteristicasOptions = new ArrayList<SelectItem>();
        for (Tipo item : caracteristicasObjOptions) {
            if(bien.getId() > 0)
                if( ! caracteristicasRegistradas.containsKey(item.getId()) )
                    caracteristicasOptions.add(new SelectItem("" + item.getId(), item.getNombre()));
        }
    }
    
    public void modificarCaracteristica(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            caracteristica = new BienCaracteristica();
            mensajeCaracteristicas = "";
            caracteristica = (BienCaracteristica) pEvent.getComponent().getAttributes().get("caracteristicaSelccionada");

            modifCaracterVisible = true;
        } catch (Exception err) {
            mensajeCaracteristicas = err.getMessage();
        }
    }

    public void elminimarCaracteristica() {

        //mensajeCaracteristicas = tipoModel.eliminarCaracteristica(caracteristica);

        actualizarOpcionesCaracteristicas();
    }

    public void actualizarCaracteristica() {

        //mensajeCaracteristicas = tipoModel.modificarCaracteristica(caracteristica);
        actualizarOpcionesCaracteristicas();
    }

    public void cancelarCaracteristica() {

        modifCaracterVisible = false;
    }

    //<editor-fold defaultstate="collapsed" desc="Variables Características">
   
    public boolean isModifCaracterVisible() {
        return modifCaracterVisible;
    }

    public void setModifCaracterVisible(boolean modifCaracterVisible) {
        this.modifCaracterVisible = modifCaracterVisible;
    }

    public BienCaracteristica getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(BienCaracteristica caracteristica) {
        this.caracteristica = caracteristica;
    }

    public List<BienCaracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(List<BienCaracteristica> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public List<SelectItem> getCaracteristicasOptions() {
        return caracteristicasOptions;
    }

    public void setCaracteristicasOptions(List<SelectItem> caracteristicasOptions) {
        this.caracteristicasOptions = caracteristicasOptions;
    }

    public String getSelectCaracteristica() {
        return selectCaracteristica;
    }

    public void setSelectCaracteristica(String selectCaracteristica) {
        this.selectCaracteristica = selectCaracteristica;
    }

    public String getMensajeCaracteristicas() {
        return mensajeCaracteristicas;
    }

    public void setMensajeCaracteristicas(String mensajeCaracteristicas) {
        this.mensajeCaracteristicas = mensajeCaracteristicas;
    }

    public String getDescCaracteristica() {
        return descCaracteristica;
    }

    public void setDescCaracteristica(String descCaracteristica) {
        this.descCaracteristica = descCaracteristica;
    }

    //</editor-fold>
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Tad Garantías">
    String garantiaMensajeError;
    String garantiaMensajeExito;

    Date garantiaFecIni;
    Date garantiaFecFin;
    String garantiaDesc;

    public void guardarGarantia() {
        try {
            int resp = garantiaFecIni.compareTo(garantiaFecFin);
            //Validamos los datos
            if(resp == -1){
                
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                String iniGarantia = df.format(garantiaFecIni);
                String finGarantia = df.format(garantiaFecFin);

//                garantiaMensajeError = bienMod.guardarGarantia(bien.getId(),
//                        iniGarantia,
//                        finGarantia,
//                        garantiaDesc);

                if(garantiaMensajeError.equals(""))
                    garantiaMensajeExito = "Garantía guardada.";

            }
            else{
                garantiaMensajeError = "El inicio de la garantia debe ser menor al final de la garantía";
                
            }
            
        }
        catch(NullPointerException e){
            garantiaMensajeError = "Favor registrar ambas fecha con formato (DD/MM/YYYY)";
        }
        catch (Exception err) {
            garantiaMensajeError = err.getMessage();
        }
    }

    public void cargarGarantia() {
        garantiaFecIni = bien.getInicioGarantia();
        garantiaFecFin = bien.getFinGarantia();
        garantiaDesc = bien.getDescripcionGarantia();
    }

    public String getGarantiaMensajeError() {
        return garantiaMensajeError;
    }

    public void setGarantiaMensajeError(String garantiaMensajeError) {
        this.garantiaMensajeError = garantiaMensajeError;
    }

    public String getGarantiaMensajeExito() {
        return garantiaMensajeExito;
    }

    public void setGarantiaMensajeExito(String garantiaMensajeExito) {
        this.garantiaMensajeExito = garantiaMensajeExito;
    }

    public Date getGarantiaFecIni() {
        return garantiaFecIni;
    }

    public void setGarantiaFecIni(Date garantiaFecIni) {
        this.garantiaFecIni = garantiaFecIni;
    }

    public Date getGarantiaFecFin() {
        return garantiaFecFin;
    }

    public void setGarantiaFecFin(Date garantiaFecFin) {
        this.garantiaFecFin = garantiaFecFin;
    }

    public String getGarantiaDesc() {
        return garantiaDesc;
    }

    public void setGarantiaDesc(String garantiaDesc) {
        this.garantiaDesc = garantiaDesc;
    }
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Tad Archivos-Adjuntos">
    String mensajeAdjuntos;
    String mensajeAdjuntosExito;

    String adjuntoDescripcion;
    Adjunto adjunto;
    List<Adjunto> adjuntos;

    @Resource
    AdjuntoModel modelAdjunto;
    
    private String adjuntoDescargar;
    private String adjuntoMostrar;
    private String adjuntosUbicacion;
    private String adjuntoNombreDescarga;
    
    boolean mostrarAdjunto;
    private Tipo tipoAdjuntoDoc;
            
    public void checkFileLocation(ActionEvent event){
        InputFile inputFile =(InputFile) event.getSource();
        FileInfo fileInfo = inputFile.getFileInfo();
        adjunto = new Adjunto();
        //file has been saved
         if (fileInfo.isSaved()) {
             // Path with uniqueFolder attribute default
             if(inputFile.getId().endsWith("2")){
                 adjunto.setUrl(fileInfo.getPhysicalPath());
                 adjunto.setNombre(fileInfo.getFileName());
                 adjunto.setTamano(fileInfo.getSize() /1024 ); // pasar a bites 
                 adjunto.setTipoMime(fileInfo.getContentType());
                 String[] extencion = (String[]) adjunto.getNombre().split(Pattern.quote("."));
                 int cant = extencion.length;
                 adjunto.setExtension(extencion[cant - 1]);
                 
                 
                 adjunto.setDetalle(adjuntoDescripcion);
                 adjunto.setIdDocumento(bien.getId());
                 
                 guardarAdjunto();
             }
             
        }
    }

    private void inicializaAdjuntos(){
        tipoAdjuntoDoc = tipoModel.buscarPorDominioNombre(Constantes.DOMINIO_BIEN, Constantes.TIPO_NOMBRE_ADJUNTO);
        cargarAdjuntos();
    }
    
    
    public void guardarAdjunto() {
        try {
            if (bien.getId() < 1) {
                mensajeAdjuntos = "El bien no ha sido registrado.";
                return;
            }
            
            Estado estadoAdjunto = estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO);
            
            adjunto.setIdEstado(estadoAdjunto);
            adjunto.setIdTipo(tipoAdjuntoDoc);

            //Detalle
            //El Id se registra cuando se guarda;
            modelAdjunto.agregar(adjunto);
            
            adjunto = new Adjunto();
            cargarAdjuntos();
            
        } catch (Exception err) {
            mensajeAdjuntos = err.getMessage();
        }
    }

    private void cargarAdjuntos(){
        //FIXME
        adjuntos = modelAdjunto.buscarPorDocumento(tipoAdjuntoDoc, bien.getId());
    }
    
    public void adjuntoMostrarDetalle(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            adjuntosUbicacion = "upload/";
            adjunto = new Adjunto();
            mensajeAdjuntos = "";
            mensajeAdjuntosExito = "";
            adjunto = (Adjunto) pEvent.getComponent().getAttributes().get("adjuntoSeleccionado");
            mostrarAdjunto = true;
            
            adjuntoDescargar = adjuntosUbicacion + adjunto.getNombre();
            adjuntoNombreDescarga = adjunto.getNombre().replace("."+adjunto.getExtension(), "");
            if( (adjunto.getExtension().toUpperCase().equals("JPG"))
                    ||(adjunto.getExtension().toUpperCase().equals("PNG"))
                    ||(adjunto.getExtension().toUpperCase().equals("GIF"))
                    ||(adjunto.getExtension().toUpperCase().equals("JPGE"))
                    ){
                adjuntoMostrar = adjuntoDescargar;
                
            }else{
                adjuntoMostrar = "imagenes/botones/descargar_SIIAGC.png";
            }
            //mensajeNota = notaModel.eliminarNota(nota);

        } catch (Exception err) {
            mensajeAdjuntos = err.getMessage();
        }
        
    }
    
    public void adjuntoEliminarCancelar(){
        
        adjunto = new Adjunto();
        mostrarAdjunto = false;
    }
    
    public void adjuntoEliminarConfirmar(){
        try{
            modelAdjunto.eliminar(adjunto);
            adjunto = new Adjunto();
            mostrarAdjunto = false;
            cargarAdjuntos();
        }catch(Exception err){
            mensajeAdjuntos = err.getMessage();
        }
    }

    
    public void downloadFile() throws FileNotFoundException, IOException {
        try{
            
            
            String dir =  "C:\\SIGEBI_V2\\build\\web\\upload\\";// System.getProperty("user.dir");
            mensajeAdjuntos = "current dir = " + dir;
            
            File file = new File(dir + adjunto.getNombre());
            InputStream fis = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int offset = 0;
            int numRead = 0;

             while ((offset < buf.length) && ((numRead = fis.read(buf, offset, buf.length - offset)) >= 0)) 
            {
              offset += numRead;
            }
            fis.close();
              HttpServletResponse response =
            (HttpServletResponse) FacesContext.getCurrentInstance()
                .getExternalContext().getResponse();

                  response.setContentType("application/octet-stream");

            response.setHeader("Content-Disposition", "attachment;filename="+adjunto.getNombre());
            response.getOutputStream().write(buf);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();
        }catch(Exception err){
            
            mensajeAdjuntos = err.getMessage();
        }
    }
    
    
    
    public void downloadFile(ActionEvent event) throws FileNotFoundException, IOException {
        try{
            File file = new File(adjuntoDescargar);
            InputStream fis = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int offset = 0;
            int numRead = 0;

             while ((offset < buf.length) && ((numRead = fis.read(buf, offset, buf.length - offset)) >= 0)) 
            {
              offset += numRead;
            }
            fis.close();
              HttpServletResponse response =
            (HttpServletResponse) FacesContext.getCurrentInstance()
                .getExternalContext().getResponse();

                  response.setContentType("application/octet-stream");

            response.setHeader("Content-Disposition", "attachment;filename="+adjunto.getNombre());
            response.getOutputStream().write(buf);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();
        }catch(Exception err){
            mensajeAdjuntos = err.getMessage();
        }
    }

    
    
    
    
    
    public String getAdjuntoDescargar() {
        return adjuntoDescargar;
    }

    public void setAdjuntoDescargar(String adjuntoDescargar) {
        this.adjuntoDescargar = adjuntoDescargar;
    }

    public String getAdjuntoNombreDescarga() {
        return adjuntoNombreDescarga;
    }

    public void setAdjuntoNombreDescarga(String adjuntoNombreDescarga) {
        this.adjuntoNombreDescarga = adjuntoNombreDescarga;
    }
    
    public String getAdjuntoMostrar() {
        return adjuntoMostrar;
    }

    public void setAdjuntoMostrar(String adjuntoMostrar) {
        this.adjuntoMostrar = adjuntoMostrar;
    }
    
    public Adjunto getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(Adjunto adjunto) {
        this.adjunto = adjunto;
    }

    public List<Adjunto> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<Adjunto> adjuntos) {
        this.adjuntos = adjuntos;
    }

    public String getMensajeAdjuntosExito() {
        return mensajeAdjuntosExito;
    }

    public void setMensajeAdjuntosExito(String mensajeAdjuntosExito) {
        this.mensajeAdjuntosExito = mensajeAdjuntosExito;
    }

    public String getMensajeAdjuntos() {
        return mensajeAdjuntos;
    }

    public void setMensajeAdjuntos(String mensajeAdjuntos) {
        this.mensajeAdjuntos = mensajeAdjuntos;
    }

    public String getAdjuntoDescripcion() {
        return adjuntoDescripcion;
    }

    public void setAdjuntoDescripcion(String adjuntoDescripcion) {
        this.adjuntoDescripcion = adjuntoDescripcion;
    }

    public boolean isMostrarAdjunto() {
        return mostrarAdjunto;
    }

    public void setMostrarAdjunto(boolean mostrarAdjunto) {
        this.mostrarAdjunto = mostrarAdjunto;
    }

    
    
    
    
    
    
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Tab Notas del Activo">
    boolean eliminarAccesorioVisible;
    String mensajeAccesExito;
    String mensajeAccesError;
    AccesoriosEntity accesorio;
    List<AccesoriosEntity> accesorios;

    @Resource
    AccesorioModel modelAccesorio;

    public void guardarAccesorio() {
        mensajeAccesExito = "";
        mensajeAccesError = "";
        try {
            if (bien.getId() < 1) {
                mensajeAccesError = "El bien no ha sido registrado.";
                return;
            }
            accesorio.setIdBien(Integer.parseInt(bien.getId().toString()));
            accesorio.setIdEstado(1);

            String detalle = accesorio.getDetalle();
            if (accesorio.getDetalle().length() < 5) {
                mensajeAccesError = "EL accesorio debe tener al menos 5 caracteres.";
            }

            //MEl Id se registra coando se guarda;
//            String resp = modelAccesorio.guardarAccesorio(accesorio);
//            if (resp.length() == 0) {
//                mensajeAccesExito = "El registro se guardó con éxito.";
//            }//FIXME
//            accesorios = modelAccesorio.traerAccesorios(bien.getId());

//            if (resp.length() > 0) {
//                mensajeAccesError = resp;
//            } else {
//                accesorio = new AccesoriosEntity();
//            }
        } catch (Exception err) {
            mensajeAccesError = err.getMessage();
        }
    }

    public void eliminarAccesorio(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            accesorio = new AccesoriosEntity();
            mensajeAccesExito = "";
            mensajeAccesError = "";
            accesorio = (AccesoriosEntity) pEvent.getComponent().getAttributes().get("accesorioSeleccionado");
            eliminarAccesorioVisible = true;
            //mensajeNota = notaModel.eliminarNota(nota);

        } catch (Exception err) {
            mensajeAccesError = err.getMessage();
        }
    }

    public void eliminarAccesorioCancelar() {
        try {
            accesorio = new AccesoriosEntity();
            mensajeAccesExito = "";
            mensajeAccesError = "";
            //mensajeNota = notaModel.eliminarNota(nota);
            eliminarAccesorioVisible = false;
            //notas = new 

        } catch (Exception err) {
            mensajeAccesError = err.getMessage();
        }
    }

    public void eliminarAccesorioConfirmar() {
        try {
//            mensajeAccesError = "";
//            mensajeAccesError = modelAccesorio.eliminarAccesorio(accesorio);
//
//            accesorio = new AccesoriosEntity();
//            accesorios = new ArrayList<AccesoriosEntity>();
//            //FIXME
//            accesorios = modelAccesorio.traerAccesorios(bien.getId());
//            eliminarAccesorioVisible = false;

        } catch (Exception err) {
            mensajeAccesError = err.getMessage();
        }
    }

    public boolean isEliminarAccesorioVisible() {
        return eliminarAccesorioVisible;
    }

    public void setEliminarAccesorioVisible(boolean eliminarAccesorioVisible) {
        this.eliminarAccesorioVisible = eliminarAccesorioVisible;
    }

    public String getMensajeAccesExito() {
        return mensajeAccesExito;
    }

    public void setMensajeAccesExito(String mensajeAccesExito) {
        this.mensajeAccesExito = mensajeAccesExito;
    }

    public String getMensajeAccesError() {
        return mensajeAccesError;
    }

    public void setMensajeAccesError(String mensajeAccesError) {
        this.mensajeAccesError = mensajeAccesError;
    }

    public AccesoriosEntity getAccesorio() {
        return accesorio;
    }

    public void setAccesorio(AccesoriosEntity accesorio) {
        this.accesorio = accesorio;
    }

    public List<AccesoriosEntity> getAccesorios() {
        return accesorios;
    }

    public void setAccesorios(List<AccesoriosEntity> accesorios) {
        this.accesorios = accesorios;
    }

    //</editor-fold>
    
    
}