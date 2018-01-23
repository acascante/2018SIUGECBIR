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
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.BienCaracteristica;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.Ubicacion;
import cr.ac.ucr.sigebi.entities.AccesoriosEntity;
import cr.ac.ucr.sigebi.entities.AdjuntoBienEntity;
import cr.ac.ucr.sigebi.entities.DatoBienEntity;
import cr.ac.ucr.sigebi.entities.NotaEntity;
import cr.ac.ucr.sigebi.entities.PersonaEntity;
import cr.ac.ucr.sigebi.models.AccesorioModel;
import cr.ac.ucr.sigebi.models.AdjuntoBienModel;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.CategoriaModel;
import cr.ac.ucr.sigebi.models.ClasificacionModel;
import cr.ac.ucr.sigebi.models.EstadoModel;
import cr.ac.ucr.sigebi.models.MonedaModel;
import cr.ac.ucr.sigebi.models.NotaModel;
import cr.ac.ucr.sigebi.models.ProveedorModel;
import cr.ac.ucr.sigebi.models.SubCategoriaModel;
import cr.ac.ucr.sigebi.models.SubClasificacionModel;
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
    

    // comboBox subCategorias
    List<SelectItem> provedooresOptions;

    NotaEntity nota;
    List<NotaEntity> notas;

    List<PersonaEntity> proveedores;
    String provSelccionado;
    String provId;
    
    
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Inicializa Datos">
    public BienController_Adicional() {
        
        super();
        
        notas = new ArrayList<NotaEntity>();
        nota = new NotaEntity();

        eliminarNotaVisible = false;

        adjuntoDescripcion = "";

        caracteristica = new DatoBienEntity();
        
    }
    
    //</editor-fold>
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Acceso a Datos">
    @Resource
    private CategoriaModel categoriaMod;

    @Resource
    private ClasificacionModel clasifMod;

    @Resource
    private BienModel bienMod;

    @Resource
    private TipoModel tipoModel;

    @Resource
    private SubClasificacionModel subClasifModel;

    @Resource
    private SubCategoriaModel subCategModel;

    @Resource
    private NotaModel notaModel;

    @Resource
    private ProveedorModel provModel;

    @Resource
    private UbicacionModel ubicModel;

    @Resource
    private EstadoModel estadoModel;

    @Resource
    private MonedaModel monedaModel;

    // </editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Proveedores">
    
    void inicializaProveedores(){
        
    }
    
    public void cargarProveedor(ActionEvent pEvent) {

        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        mensajeProveedores = "";
//        proveedores = provModel.listar();
        mensajeProveedores = "Cargados";
    }

    public void filtroProveedor(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        try {

            proveedores = new ArrayList<PersonaEntity>();
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
        PersonaEntity prov = (PersonaEntity) pEvent.getComponent().getAttributes().get("provSeleccionado");
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

    //</editor-fold>
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Ubicacion">
    
    
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
        ubicaciones = new HashMap<Integer, Ubicacion>();
        ubicacionOptions = new ArrayList<SelectItem>();
        
        List<Ubicacion> ubicacionesList;
        ubicacionesList = ubicModel.listar(unidadEjecutoraId);
        for (Ubicacion item : ubicacionesList) {
            ubicaciones.put(item.getId(), item);
            ubicacionOptions.add(new SelectItem(item.getId(), item.getDetalle()));
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
    
    
    
    //</editor-fold>           

    
    
    //<editor-fold defaultstate="collapsed" desc="Tab Notas del Activo">
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
    
    
    public void guardarCaracteristica() {

        if (descCaracteristica.length() < 5) {
            mensajeCaracteristicas = "Debe registrar al menos 5 caracteristicas.";
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
        registro.setId(bien.getId());
        registro.setDetalle(descCaracteristica);
        registro.setTipo(caract);
        Estado estado = new Estado();
        estado.setId(Constantes.ESTADO_GENERAL_ACTIVO);
        registro.setEstado(estado);
        
        // TODO revisar almacenamiento de caracteristicas, deberian tener su propio model y dao, no tiene por que estar en TIPO
        //mensajeCaracteristicas = tipoModel.guardarCaracteristica(registro);
        if (mensajeCaracteristicas.length() == 0) {
            descCaracteristica = "";
        }
        cargarCaracteristica();
        //mensajeCaracteristicas = "Pendiente de implementar.";
    }

    public void cargarCaracteristica() {
        try{
            modifCaracterVisible = false;

            caracteristicas = new ArrayList<DatoBienEntity>();
            //caracteristicas = tipoModel.traerCaracteristicasRegistradas(constCaracteristicas,
            bien.getId();

            cargarOpcionesCaract();
            caracteristica = new DatoBienEntity();
            //mensajeCaracteristicas = "Debo llenar el select de características";
        }catch(Exception err){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.cargarCaracteristica"));
        }
    }

    private void cargarOpcionesCaract() {
        //List<Tipo> caract = tipoModel.traerCaracteristicas(constCaracteristicas, bien.getId());//FIXME

        caracteristicasOptions = new ArrayList<SelectItem>();
//        for (Tipo item : caract) {
//            caracteristicasOptions.add(new SelectItem("" + item.getIdTipo(), item.getNombre()));
//        }
    }

    public void modificarCaracteristica(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            caracteristica = new DatoBienEntity();
            mensajeCaracteristicas = "";
            caracteristica = (DatoBienEntity) pEvent.getComponent().getAttributes().get("caracteristicaSelccionada");

            modifCaracterVisible = true;
        } catch (Exception err) {
            mensajeCaracteristicas = err.getMessage();
        }
    }

    public void elminimarCaracteristica() {

        //mensajeCaracteristicas = tipoModel.eliminarCaracteristica(caracteristica);

        cargarCaracteristica();
    }

    public void actualizarCaracteristica() {

        //mensajeCaracteristicas = tipoModel.modificarCaracteristica(caracteristica);
        cargarCaracteristica();
    }

    public void cancelarCaracteristica() {

        modifCaracterVisible = false;
    }

    //<editor-fold defaultstate="collapsed" desc="Variables Características">
    String mensajeCaracteristicas;
    String selectCaracteristica;
    String descCaracteristica;
    String constCaracteristicas = "CARACTERISTICA";
    // comboBox subCategorias
    List<SelectItem> caracteristicasOptions;

    List<DatoBienEntity> caracteristicas;
    DatoBienEntity caracteristica;
    boolean modifCaracterVisible;

    public boolean isModifCaracterVisible() {
        return modifCaracterVisible;
    }

    public void setModifCaracterVisible(boolean modifCaracterVisible) {
        this.modifCaracterVisible = modifCaracterVisible;
    }

    public DatoBienEntity getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(DatoBienEntity caracteristica) {
        this.caracteristica = caracteristica;
    }

    public BienModel getBienMod() {
        return bienMod;
    }

    public void setBienMod(BienModel bienMod) {
        this.bienMod = bienMod;
    }

    public List<DatoBienEntity> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(List<DatoBienEntity> caracteristicas) {
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
    AdjuntoBienEntity adjunto;
    List<AdjuntoBienEntity> adjuntos;

    @Resource
    AdjuntoBienModel modelAdjunto;
    
    private String componentStatus;
    private String urlAdjunto;
    private String nombreAdjunto;
    private float tamannoAdjunto;
    private String extencionAdjunto;
    private String tipoAdjunto;
    
    
    private String adjuntoDescargar;
    private String adjuntoMostrar;
    private String adjuntosUbicacion;
    private String adjuntoNombreDescarga;
    
    boolean mostrarAdjunto;
    
    public void checkFileLocation(ActionEvent event){
        InputFile inputFile =(InputFile) event.getSource();
        FileInfo fileInfo = inputFile.getFileInfo();
        //file has been saved
         if (fileInfo.isSaved()) {
             // Path with uniqueFolder attribute default
             if(inputFile.getId().endsWith("2")){
                 urlAdjunto = fileInfo.getPhysicalPath();
                 nombreAdjunto = fileInfo.getFileName();
                 tamannoAdjunto = (fileInfo.getSize() /1024 ); // pasar a bites 
                 tipoAdjunto =  fileInfo.getContentType();
                 String[] extencion = (String[]) nombreAdjunto.split(Pattern.quote("."));
                 int cant = extencion.length;
                 extencionAdjunto = extencion[cant - 1];
                 /*
                 adjuntoDescripcion += "\nNombre: "+nombreAdjunto;
                 adjuntoDescripcion += "\nTamaño: "+tamannoAdjunto;
                 adjuntoDescripcion += "\nTipoArchivo: "+tipoAdjunto;
                 adjuntoDescripcion += "\nExtención: "+extencionAdjunto;
                 */
                 guardarAdjunto();
             }
             
        }
    }

    
    
    public void guardarAdjunto() {
        mensajeAdjuntos = "";
        mensajeAdjuntosExito = "";
        try {
            if (bien.getId() < 1) {
                mensajeAdjuntos = "El bien no ha sido registrado.";
                return;
            }
                adjunto = new AdjuntoBienEntity();
            adjunto.setDetalle(adjuntoDescripcion);
            adjunto.setIdEstado(1);
            adjunto.setIdTipo(1);
            adjunto.setIdBien(Integer.parseInt(bien.getId().toString()));
            adjunto.setUrl(urlAdjunto);
            
            adjunto.setNombre(nombreAdjunto);
            adjunto.setTamano(tamannoAdjunto);
            adjunto.setTipoMime(tipoAdjunto);
            adjunto.setExtension(extencionAdjunto);
            

            //Detalle
            //El Id se registra cuando se guarda;
            String resp = modelAdjunto.guardarAdjunto(adjunto);
            if (resp.length() == 0) {
                mensajeAdjuntosExito = "El Archivo se guardó con éxito.";
            }
            
            cargarAdjuntos();
            
            if (resp.length() > 0) {
                mensajeAdjuntos = resp;
            } else {
                adjunto = new AdjuntoBienEntity();
            }
        } catch (Exception err) {
            mensajeAdjuntos = err.getMessage();
        }
    }

    private void cargarAdjuntos(){
        //FIXME
        adjuntos = modelAdjunto.traerAdjuntos(Integer.parseInt(bien.getId().toString()));
    }
    
    public void adjuntoMostrarDetalle(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            adjuntosUbicacion = "upload/";
            adjunto = new AdjuntoBienEntity();
            mensajeAdjuntos = "";
            mensajeAdjuntosExito = "";
            adjunto = (AdjuntoBienEntity) pEvent.getComponent().getAttributes().get("adjuntoSeleccionado");
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
        
        adjunto = new AdjuntoBienEntity();
        mostrarAdjunto = false;
    }
    
    public void adjuntoEliminarConfirmar(){
        try{
            mensajeAdjuntos = modelAdjunto.eliminarAdjunto(adjunto);
            adjunto = new AdjuntoBienEntity();
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
    
    public AdjuntoBienEntity getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(AdjuntoBienEntity adjunto) {
        this.adjunto = adjunto;
    }

    public List<AdjuntoBienEntity> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<AdjuntoBienEntity> adjuntos) {
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

    public String getComponentStatus() {
        return componentStatus;
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
