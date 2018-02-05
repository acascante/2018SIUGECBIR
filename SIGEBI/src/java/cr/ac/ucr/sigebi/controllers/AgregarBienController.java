/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import com.icesoft.faces.component.inputfile.FileInfo;
import com.icesoft.faces.component.inputfile.InputFile;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.commands.BienCommand;
import cr.ac.ucr.sigebi.domain.Accesorio;
import cr.ac.ucr.sigebi.domain.Adjunto;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.BienCaracteristica;
import cr.ac.ucr.sigebi.domain.Clasificacion;
import cr.ac.ucr.sigebi.domain.Lote;
import cr.ac.ucr.sigebi.domain.Moneda;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.models.CategoriaModel;
import cr.ac.ucr.sigebi.models.ClasificacionModel;
import cr.ac.ucr.sigebi.models.MonedaModel;
import cr.ac.ucr.sigebi.domain.Categoria;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Identificacion;
import cr.ac.ucr.sigebi.domain.Nota;
import cr.ac.ucr.sigebi.domain.Proveedor;
import cr.ac.ucr.sigebi.domain.SubCategoria;
import cr.ac.ucr.sigebi.domain.SubClasificacion;
import cr.ac.ucr.sigebi.domain.Ubicacion;
import cr.ac.ucr.sigebi.models.AccesorioModel;
import cr.ac.ucr.sigebi.models.AdjuntoModel;
import cr.ac.ucr.sigebi.models.BienCaracteristicaModel;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.IdentificacionModel;
import cr.ac.ucr.sigebi.models.LoteModel;
import cr.ac.ucr.sigebi.models.NotaModel;
import cr.ac.ucr.sigebi.models.ProveedorModel;
import cr.ac.ucr.sigebi.models.SubCategoriaModel;
import cr.ac.ucr.sigebi.models.SubClasificacionModel;
import cr.ac.ucr.sigebi.models.UbicacionModel;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author jorge.serrano
 */
@Controller(value = "controllerAgregarBienes")
@Scope("session")
public class AgregarBienController extends BaseController {
    
    //<editor-fold defaultstate="collapsed" desc="Variables de la Clase">
    private List<SelectItem> itemsCategoria;
    private List<SelectItem> itemsCaracteristica;
    private List<SelectItem> itemsClasificacion;
    private List<SelectItem> itemsLote;
    private List<SelectItem> itemsMoneda;
    private List<SelectItem> itemsOrigen;
    private List<SelectItem> itemsSubCategoria;
    private List<SelectItem> itemsSubClasificacion;
    private List<SelectItem> itemsTipo;
    private List<SelectItem> itemsUbicacion;
    
    @Resource private AccesorioModel modelAccesorio;
    @Resource private AdjuntoModel modelAdjunto;
    @Resource private BienModel modelBien;
    @Resource private BienCaracteristicaModel modelBienCaracteristica;
    @Resource private CategoriaModel modelCategoria;
    @Resource private ClasificacionModel modelClasificacion;
    @Resource private IdentificacionModel modelIdentificacion;
    @Resource private LoteModel modelLote;
    @Resource private MonedaModel modelMoneda;
    @Resource private NotaModel modelNota;
    @Resource private ProveedorModel modelProveedor;
    @Resource private SubCategoriaModel modelSubCategoria;
    @Resource private SubClasificacionModel modelSubClasificacion;
    @Resource private UbicacionModel modelUbicacion;
    
    private List<Proveedor> proveedores;
    private List<Tipo> tiposCaracteristica;
    private BienCommand command;
    private Bien bien;
    
    private String mensajeExito;
    private String mensajeAdjunto;
    private String mensajeCaracteristica;
    private String mensaje;
    
    float tipoCambioDollar = 570;
    float tipoCambioEuro = 640;
    
    private boolean disableSubCategorias;
    private boolean disableClasificaciones;
    private boolean disableSubClasificaciones;
    
    private boolean visiblePanelUbicaciones;
    private boolean visiblePanelProveedores;
    private boolean visibleBotonSincronizar;
    private boolean visibleBotonRechazar;
    private boolean visiblePanelObservaciones;
    
    private boolean bienRegistrado;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Inicializa Datos">
    public AgregarBienController() {
        super();
        inicializarNuevo();
    }

    @PostConstruct
    protected void inicializar() {
        cargarCombos();
    }
    
    private void inicializarNuevo() {
        this.command = new BienCommand(unidadEjecutora);
        this.bienRegistrado = false;
        inicializarDatos();
    }
    
    private void inicializarDatos() {
        this.mensajeExito = "";
        this.mensaje = "";
        
        this.disableSubCategorias = true;
        this.disableClasificaciones = true;
        this.disableSubClasificaciones = true;
        
        
        this.visiblePanelObservaciones = false;
        this.visiblePanelProveedores = false;
        this.visiblePanelUbicaciones = false;
        this.visibleBotonSincronizar = false;
        this.visibleBotonRechazar = false;
        
        this.bienRegistrado = false;
    }
    
    private void inicializarDetalle(Bien bien) {
        this.command = new BienCommand(bien);
        this.bienRegistrado = true;
        
        if (bien.getEstado().getValor().equals(Constantes.ESTADO_BIEN_PENDIENTE)) {
            this.setVisibleBotonSincronizar(true);
        } else {
            this.setVisibleBotonSincronizar(false);
        }
        if(bien.getEstado().getValor().equals(Constantes.ESTADO_BIEN_PENDIENTE_SINCRONIZAR)) {
            this.setVisibleBotonRechazar(true);
        }else{
            this.setVisibleBotonRechazar(false);
        }
    }
    
    private void cargarCombos() {
        try {
            Estado estado = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE);
            command.setEstado(estado);
            command.setEstadoInterno(estado); // TODO Revisar cual es el estado interno para BIENES
        
            List<Tipo> tipos = this.tiposPorDominio(Constantes.DOMINIO_BIEN);
            if (!tipos.isEmpty()) {
                itemsTipo = new ArrayList<SelectItem>();
                for (Tipo item : tipos) {
                    itemsTipo.add(new SelectItem(item.getId(), item.getNombre()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                    command.getItemCommand().getItemsTipo().put(item.getId(), item);
                }
            }
            
            List<Tipo> tiposOrigen = this.tiposPorDominio(Constantes.DOMINIO_ORIGEN);
            if (!tiposOrigen.isEmpty()) {
                itemsOrigen = new ArrayList<SelectItem>();
                for (Tipo item : tiposOrigen) {
                    itemsOrigen.add(new SelectItem(item.getId(), item.getNombre()));  
                    command.getItemCommand().getItemsOrigen().put(item.getId(), item);
                }
            }
            
            List<Lote> lotes = modelLote.listar();
            if (!lotes.isEmpty()) {
                itemsLote = new ArrayList<SelectItem>();
                for (Lote item : lotes) {
                    itemsLote.add(new SelectItem(item.getId(), item.getDescripcion()));  
                    command.getItemCommand().getItemsLote().put(item.getId(), item);
                }
            }
            
            List<Categoria> categorias = modelCategoria.listar();
            if (!categorias.isEmpty()) {
                itemsCategoria = new ArrayList<SelectItem>();
                for (Categoria item : categorias) {
                    itemsCategoria.add(new SelectItem(item.getId(), item.getDescripcion()));
                    command.getItemCommand().getItemsCategoria().put(item.getId(), item);
                }
            }
            
            List<Moneda> monedas = modelMoneda.listar();
            if (!monedas.isEmpty()) {
                itemsMoneda = new ArrayList<SelectItem>();
                for (Moneda item : monedas) {
                    itemsMoneda.add(new SelectItem(item.getId(), item.getDescripcion()));
                    command.getItemCommand().getItemsMoneda().put(item.getId(), item);
                }
            }
            
            if (command.getIdSubCategoria()!= null) {
                cargarSubCategorias(command.getItemCommand().getItemsCategoria().get(command.getIdCategoria()));
            }
            if (command.getIdClasificacion() != null) {
                cargarClasificaciones(command.getItemCommand().getItemsSubCategoria().get(command.getIdSubCategoria()));
            }
            if (command.getIdSubClasificacion() != null) {
                cargarSubClasificaciones(command.getItemCommand().getItemsClasificacion().get(command.getIdClasificacion()));
            }
        } catch (Exception err) {
            mensaje = err.getMessage();
        }
    }

    public String validarForm(UIViewRoot root, UIInput component) {
        // TODO implementar validaciones
        
        return Constantes.OK;
    }
    
    public void agregarBien() {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        UIInput component = new UIInput();
        String messageValidacion = validarForm(root, component);
        ReentrantLock reentrantLock = new ReentrantLock();
        if (Constantes.OK.equals(messageValidacion)) {
            try {                
                reentrantLock.lock();                
                //Se busca la identificacion del bien
                if(!bienRegistrado){
                    Estado estadoDisponible = this.estadoPorDominioValor(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_DISPONIBLE);
                    Identificacion identificacion = modelIdentificacion.siguienteDisponible(estadoDisponible);
                    if (identificacion == null) {
                        Mensaje.agregarErrorAdvertencia("No Hay Identificaciones Disponibles");
                    } else {
                        command.setIdentificacion(identificacion);
                        bien = command.getBien();

                        Estado estadoOcupado = this.estadoPorDominioValor(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_OCUPADA);
                        bien.getIdentificacion().setEstado(estadoOcupado);
                        modelIdentificacion.actualizar(bien.getIdentificacion());

                        modelBien.almacenar(bien);
                        bienRegistrado = true;
                        mensajeExito = "Los datos se salvaron con éxito.";
                    }
                }else{
                    modelBien.actualizar(command.getBien());
                }                
            } catch(Exception exception){
                //Se retorna la identificacion
                if(!bienRegistrado && command.getIdentificacion() != null && command.getIdentificacion().getId() != null && command.getIdentificacion().getId() > 0){
                    Estado estadoDispo = this.estadoPorDominioValor(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_DISPONIBLE);
                    bien.getIdentificacion().setEstado(estadoDispo);
                    modelIdentificacion.actualizar(bien.getIdentificacion());
                }
                if (exception instanceof FWExcepcion) {
                    Mensaje.agregarErrorAdvertencia(((FWExcepcion)exception).getError_para_usuario());
                } else  {
                    Mensaje.agregarErrorAdvertencia(exception.getMessage());
                }
            }
            finally {
                reentrantLock.unlock();
            }
        } else {
            component.setValid(false);
            Mensaje.agregarErrorAdvertencia(messageValidacion);
        }
    }
    //</editor-fold>
     
    //<editor-fold defaultstate="collapsed" desc="Navegación del MENÚ">
    public void nuevoRegistro(ActionEvent event) {
        try{
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
            Util.navegar(Constantes.KEY_VISTA_DETALLE_BIEN);
        } catch (Exception err) {
            mensaje = err.getMessage();
        }
    }
    
    public void modificarRegistro(ActionEvent event) {
        try{
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            inicializarDetalle((Bien) event.getComponent().getAttributes().get("bienSeleccionado"));
            this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
            Util.navegar(Constantes.KEY_VISTA_DETALLE_BIEN);
        } catch (Exception err) {
            mensaje = err.getMessage();
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Categorias y Clasificaciones">
    public void cambioCategoria(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        cargarSubCategorias(command.getItemCommand().getItemsCategoria().get(command.getIdCategoria()));
    }

    public void cambioSubCategoria(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        cargarClasificaciones(command.getItemCommand().getItemsSubCategoria().get(command.getIdSubCategoria()));
    }

    public void cambioClasificacion(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        cargarSubClasificaciones(command.getItemCommand().getItemsClasificacion().get(command.getIdClasificacion()));
    }

    private void cargarSubCategorias(Categoria categoria) {
        if (Constantes.DEFAULT_ID.equals(command.getIdCategoria())) {
            command.setIdSubCategoria(Constantes.DEFAULT_ID);
            itemsSubCategoria.clear();
            this.setDisableSubCategorias(true);
            cargarClasificaciones(command.getItemCommand().getItemsSubCategoria().get(command.getIdSubCategoria()));
        } else {
            List<SubCategoria> subCategorias = modelSubCategoria.listar(categoria);
            if (!subCategorias.isEmpty()) {
                this.setDisableSubCategorias(false);
                itemsSubCategoria = new ArrayList<SelectItem>();
                for (SubCategoria item : subCategorias) {
                    itemsSubCategoria.add(new SelectItem(item.getId(), item.getDescripcion()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                }
            }
        }
    }
    
    private void cargarClasificaciones(SubCategoria subCategoria) {
        if (Constantes.DEFAULT_ID.equals(command.getIdSubCategoria())) {
            command.setIdClasificacion(Constantes.DEFAULT_ID);
            itemsClasificacion.clear();
            this.setDisableClasificaciones(true);
            cargarSubClasificaciones(command.getItemCommand().getItemsClasificacion().get(command.getIdClasificacion()));
        } else {
            List<Clasificacion> clasificaciones = modelClasificacion.listarPorSubCategoria(subCategoria);
            if (!clasificaciones.isEmpty()) {
                itemsClasificacion = new ArrayList<SelectItem>();
                for (Clasificacion item : clasificaciones) {
                    this.setDisableClasificaciones(false);
                    itemsClasificacion.add(new SelectItem(item.getId(), item.getNombre()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                }
            }
        }
    }

    private void cargarSubClasificaciones(Clasificacion clasificacion) {
        if (Constantes.DEFAULT_ID.equals(command.getIdClasificacion())) {
            command.setIdSubClasificacion(Constantes.DEFAULT_ID);
            itemsSubClasificacion.clear();
            this.setDisableSubClasificaciones(true);
        } else {
            List<SubClasificacion> subClasificaciones = modelSubClasificacion.listar(clasificacion);
            if (!subClasificaciones.isEmpty()) {
                itemsSubClasificacion = new ArrayList<SelectItem>();
                for (SubClasificacion item : subClasificaciones) {
                    this.setDisableSubClasificaciones(false);
                    itemsSubClasificacion.add(new SelectItem(item.getId(), item.getNombre()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                }
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Ubicaciones">
    public void mostrarPanelUbicaciones() {
        cargarUbicaciones();
        this.setVisiblePanelUbicaciones(true);
    }

    public void limpiarUbicacion() {
        command.getUbicacionCommand().setIdUbicacion(Constantes.DEFAULT_ID);
        command.getUbicacionCommand().setDescripcion(new String());
    }

    public void cerrarPanelUbicaciones() {
        this.setVisiblePanelUbicaciones(false);
        command.getUbicacionCommand().setUbicacion(command.getItemCommand().getItemsUbicacion().get(command.getUbicacionCommand().getIdUbicacion()));
    }
    
    private void cargarUbicaciones() {
        if (itemsUbicacion == null) {
            List<Ubicacion> ubicaciones = modelUbicacion.listar(unidadEjecutora);
            if (!ubicaciones.isEmpty()) {
                itemsUbicacion = new ArrayList<SelectItem>();
                for (Ubicacion item : ubicaciones) {
                    itemsUbicacion.add(new SelectItem(item.getId(), item.getDetalle()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                    command.getItemCommand().getItemsUbicacion().put(item.getId(), item);
                }
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Proveedores">
    public void selecionarProveedor(ActionEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        Proveedor proveedor = (Proveedor) event.getComponent().getAttributes().get("proveedorSeleccionado");
        command.getProveedorCommand().setProveedor(proveedor);
        this.setVisiblePanelProveedores(false);
    }
    
    public void mostrarPanelProveedores() {
        this.proveedores = modelProveedor.listar();
        this.setVisiblePanelProveedores(true);
    }

    public void limpiarProveedor() {
        command.getProveedorCommand().setProveedor(new Proveedor());
        command.getProveedorCommand().setDescripcion(new String());
    }

    public void cerrarPanelProveedores() {
        this.setVisiblePanelProveedores(false);
    }

    public void filtroProveedor(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        try {
            this.proveedores = modelProveedor.listar(command.getProveedorCommand().getFiltroIdentificacion(), command.getProveedorCommand().getFiltroNombre());
        }  catch (FWExcepcion err) {
            Mensaje.agregarErrorAdvertencia(err.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e.getMessage());
        }
    }
    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="Panel Observacion Sincronizar o Rechazar">
    public void rechazarBien() {
        try {
            if (command.getObservacionCliente() == null || command.getObservacionCliente().isEmpty()) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.rechazarBien.sin.observacion"));
            } else {
                Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;
                modelBien.cambiaEstadoBien(bien, this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE), command.getObservacionCliente(), telefono);
                command.setObservacionCliente("");
                this.setVisibleBotonRechazar(false);
                this.cerrarPanelObservacion();
            }
        } catch (FWExcepcion err) {
            Mensaje.agregarErrorAdvertencia(err.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e.getMessage());
        }
    }

    public void solicitarSincronizacion() {
        try {
            Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;
            modelBien.cambiaEstadoBien(bien, this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE_SINCRONIZAR), command.getObservacionCliente(), telefono);
            this.setVisibleBotonSincronizar(false);
            this.cerrarPanelObservacion();
        } catch (FWExcepcion err) {
            Mensaje.agregarErrorAdvertencia(err.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e.getMessage());
        }
    }
    
    public boolean mostrarPanelObservacion(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return false;
        }
        this.setVisiblePanelUbicaciones(true);
        return true;
    }

    public boolean cerrarPanelObservacion() {
        this.setVisiblePanelUbicaciones(false);
        return false;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tab Notas del Activo">
    public void guardarNota() {
        Nota nota = command.getNotaCommand().getNota();
        modelNota.guardar(nota);
    }

    public void eliminarNota(ActionEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            modelNota.eliminar((Nota) event.getComponent().getAttributes().get("notaSeleccionada"));
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.Nota"));
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tab Garantías">
    public boolean validarGarantia() {
        return command.getInicioGarantia().compareTo(command.getFinGarantia()) < 0;
    }
    
    public void guardarGarantia() {
        try {
            if (validarGarantia()) {
                modelBien.actualizar(command.getBien());
                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.Bien.Exito.Guardar"));
            } else {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.Fecha1"));
            }

        } catch (NullPointerException e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.Fecha2"));
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.Complemento"));
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Tad Archivos-Adjuntos">
    public void checkFileLocation(ActionEvent event) {
        InputFile inputFile = (InputFile) event.getSource();
        FileInfo fileInfo = inputFile.getFileInfo();
        Adjunto adjunto = new Adjunto();
        if (fileInfo.isSaved()) {
            if (inputFile.getId().endsWith("2")) {
                adjunto.setUrl(fileInfo.getPhysicalPath());
                adjunto.setNombre(fileInfo.getFileName());
                adjunto.setTamano(fileInfo.getSize() / 1024); // pasar a bites 
                adjunto.setTipoMime(fileInfo.getContentType());
                String[] extencion = (String[]) adjunto.getNombre().split(Pattern.quote("."));
                int cant = extencion.length;
                adjunto.setExtension(extencion[cant - 1]);

                adjunto.setDetalle(command.getDescripcionAdjunto());
                adjunto.setIdDocumento(bien.getId());
                guardarAdjunto();
            }
        }
    }

    private void inicializarAdjuntos() {
        //TODO revisar
//        Tipo tipo = this.tipoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.TIPO_NOMBRE_ADJUNTO);
//        List<Adjunto> adjuntos = modelAdjunto.buscarPorDocumento(tipoAdjunto, bien.getId());
    }

    public void guardarAdjunto() {
        try {
            Adjunto adjunto = command.getAdjuntoCommand().getAdjunto();
            modelAdjunto.agregar(adjunto);
        } catch (Exception err) {
            mensajeAdjunto = err.getMessage();
        }
    }

    public void adjuntoMostrarDetalle(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
//            adjuntosUbicacion = "upload/";
//            adjunto = new Adjunto();
//            mensajeAdjuntos = "";
//            mensajeAdjuntosExito = "";
//            adjunto = (Adjunto) pEvent.getComponent().getAttributes().get("adjuntoSeleccionado");
//            mostrarAdjunto = true;
//
//            adjuntoDescargar = adjuntosUbicacion + adjunto.getNombre();
//            adjuntoNombreDescarga = adjunto.getNombre().replace("." + adjunto.getExtension(), "");
//            if ((adjunto.getExtension().toUpperCase().equals("JPG"))
//                    || (adjunto.getExtension().toUpperCase().equals("PNG"))
//                    || (adjunto.getExtension().toUpperCase().equals("GIF"))
//                    || (adjunto.getExtension().toUpperCase().equals("JPGE"))) {
//                adjuntoMostrar = adjuntoDescargar;
//
//            } else {
//                adjuntoMostrar = "imagenes/botones/descargar_SIIAGC.png";
//            }
            //mensajeNota = notaModel.eliminarNota(nota);

        } catch (Exception err) {
            mensajeAdjunto = err.getMessage();
        }

    }

    public void downloadFile() throws FileNotFoundException, IOException {
        try {

            String dir = "C:\\SIGEBI_V2\\build\\web\\upload\\";// System.getProperty("user.dir");
            mensajeAdjunto = "current dir = " + dir;

            File file = new File(dir + command.getAdjuntoCommand().getNombre());
            InputStream fis = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int offset = 0;
            int numRead = 0;

            while ((offset < buf.length) && ((numRead = fis.read(buf, offset, buf.length - offset)) >= 0)) {
                offset += numRead;
            }
            fis.close();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            response.setContentType("application/octet-stream");

            response.setHeader("Content-Disposition", "attachment;filename=" + command.getAdjuntoCommand().getNombre());
            response.getOutputStream().write(buf);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception err) {

            mensajeAdjunto = err.getMessage();
        }
    }

    public void downloadFile(ActionEvent event) throws FileNotFoundException, IOException {
        try {
//            File file = new File(adjuntoDescargar);
//            InputStream fis = new FileInputStream(file);
//            byte[] buf = new byte[1024];
//            int offset = 0;
//            int numRead = 0;
//
//            while ((offset < buf.length) && ((numRead = fis.read(buf, offset, buf.length - offset)) >= 0)) {
//                offset += numRead;
//            }
//            fis.close();
//            HttpServletResponse response
//                    = (HttpServletResponse) FacesContext.getCurrentInstance()
//                            .getExternalContext().getResponse();
//
//            response.setContentType("application/octet-stream");
//
//            response.setHeader("Content-Disposition", "attachment;filename=" + command.getAdjuntoCommand().getNombre());
//            response.getOutputStream().write(buf);
//            response.getOutputStream().flush();
//            response.getOutputStream().close();
//            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception err) {
            mensajeAdjunto = err.getMessage();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tab Accesorios">
    public void guardarAccesorio() {
        try {
            Accesorio accesorio = command.getAccesorioCommand().getAccesorio();
            modelAccesorio.almacenar(accesorio);
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.Complemento"));
        }
    }

    public void eliminarAccesorio(ActionEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            modelAccesorio.eliminar((Accesorio) event.getComponent().getAttributes().get("accesorioSeleccionado"));
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.Complemento.Eliminar"));
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tab Características">
    public void guardarCaracteristica() {
        try {
            if (validarCaracteristica()) {
                modelBienCaracteristica.almacenar(command.getCaracteristicaCommand().getBienCaracteristica());
            }
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.cargarCaracteristica"));
        }
    }
    
    public void modificarCaracteristica(ActionEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            if (validarCaracteristica()) {
                modelBienCaracteristica.almacenar((BienCaracteristica)event.getComponent().getAttributes().get("caracteristicaSelccionada"));
            }
        } catch (Exception err) {
            mensajeCaracteristica = err.getMessage();
        }
    }

    private boolean validarCaracteristica() {
        
        //TODO Agragar validaciones de caracteristicas
        return true;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public List<SelectItem> getItemsCategoria() {
        return itemsCategoria;
    }

    public void setItemsCategoria(List<SelectItem> itemsCategoria) {
        this.itemsCategoria = itemsCategoria;
    }

    public List<SelectItem> getItemsCaracteristica() {
        return itemsCaracteristica;
    }

    public void setItemsCaracteristica(List<SelectItem> itemsCaracteristica) {
        this.itemsCaracteristica = itemsCaracteristica;
    }

    public List<SelectItem> getItemsClasificacion() {
        return itemsClasificacion;
    }

    public void setItemsClasificacion(List<SelectItem> itemsClasificacion) {
        this.itemsClasificacion = itemsClasificacion;
    }

    public List<SelectItem> getItemsLote() {
        return itemsLote;
    }

    public void setItemsLote(List<SelectItem> itemsLote) {
        this.itemsLote = itemsLote;
    }

    public List<SelectItem> getItemsMoneda() {
        return itemsMoneda;
    }

    public void setItemsMoneda(List<SelectItem> itemsMoneda) {
        this.itemsMoneda = itemsMoneda;
    }

    public List<SelectItem> getItemsOrigen() {
        return itemsOrigen;
    }

    public void setItemsOrigen(List<SelectItem> itemsOrigen) {
        this.itemsOrigen = itemsOrigen;
    }

    public List<SelectItem> getItemsSubCategoria() {
        return itemsSubCategoria;
    }

    public void setItemsSubCategoria(List<SelectItem> itemsSubCategoria) {
        this.itemsSubCategoria = itemsSubCategoria;
    }

    public List<SelectItem> getItemsSubClasificacion() {
        return itemsSubClasificacion;
    }

    public void setItemsSubClasificacion(List<SelectItem> itemsSubClasificacion) {
        this.itemsSubClasificacion = itemsSubClasificacion;
    }

    public List<SelectItem> getItemsTipo() {
        return itemsTipo;
    }

    public void setItemsTipo(List<SelectItem> itemsTipo) {
        this.itemsTipo = itemsTipo;
    }

    public List<SelectItem> getItemsUbicacion() {
        return itemsUbicacion;
    }

    public void setItemsUbicacion(List<SelectItem> itemsUbicacion) {
        this.itemsUbicacion = itemsUbicacion;
    }

    public List<Proveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }

    public List<Tipo> getTiposCaracteristica() {
        return tiposCaracteristica;
    }

    public void setTiposCaracteristica(List<Tipo> tiposCaracteristica) {
        this.tiposCaracteristica = tiposCaracteristica;
    }

    public BienCommand getCommand() {
        return command;
    }

    public void setCommand(BienCommand command) {
        this.command = command;
    }

    public Bien getBien() {
        return bien;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
    }

    public String getMensajeExito() {
        return mensajeExito;
    }

    public void setMensajeExito(String mensajeExito) {
        this.mensajeExito = mensajeExito;
    }

    public String getMensajeAdjunto() {
        return mensajeAdjunto;
    }

    public void setMensajeAdjunto(String mensajeAdjunto) {
        this.mensajeAdjunto = mensajeAdjunto;
    }

    public String getMensajeCaracteristica() {
        return mensajeCaracteristica;
    }

    public void setMensajeCaracteristica(String mensajeCaracteristica) {
        this.mensajeCaracteristica = mensajeCaracteristica;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public float getTipoCambioDollar() {
        return tipoCambioDollar;
    }

    public void setTipoCambioDollar(float tipoCambioDollar) {
        this.tipoCambioDollar = tipoCambioDollar;
    }

    public float getTipoCambioEuro() {
        return tipoCambioEuro;
    }

    public void setTipoCambioEuro(float tipoCambioEuro) {
        this.tipoCambioEuro = tipoCambioEuro;
    }

    public boolean isDisableSubCategorias() {
        return disableSubCategorias;
    }

    public void setDisableSubCategorias(boolean disableSubCategorias) {
        this.disableSubCategorias = disableSubCategorias;
    }

    public boolean isDisableClasificaciones() {
        return disableClasificaciones;
    }

    public void setDisableClasificaciones(boolean disableClasificaciones) {
        this.disableClasificaciones = disableClasificaciones;
    }

    public boolean isDisableSubClasificaciones() {
        return disableSubClasificaciones;
    }

    public void setDisableSubClasificaciones(boolean disableSubClasificaciones) {
        this.disableSubClasificaciones = disableSubClasificaciones;
    }

    public boolean isVisiblePanelUbicaciones() {
        return visiblePanelUbicaciones;
    }

    public void setVisiblePanelUbicaciones(boolean visiblePanelUbicaciones) {
        this.visiblePanelUbicaciones = visiblePanelUbicaciones;
    }

    public boolean isVisiblePanelProveedores() {
        return visiblePanelProveedores;
    }

    public void setVisiblePanelProveedores(boolean visiblePanelProveedores) {
        this.visiblePanelProveedores = visiblePanelProveedores;
    }

    public boolean isVisibleBotonSincronizar() {
        return visibleBotonSincronizar;
    }

    public void setVisibleBotonSincronizar(boolean visibleBotonSincronizar) {
        this.visibleBotonSincronizar = visibleBotonSincronizar;
    }

    public boolean isVisibleBotonRechazar() {
        return visibleBotonRechazar;
    }

    public void setVisibleBotonRechazar(boolean visibleBotonRechazar) {
        this.visibleBotonRechazar = visibleBotonRechazar;
    }

    public boolean isVisiblePanelObservaciones() {
        return visiblePanelObservaciones;
    }

    public void setVisiblePanelObservaciones(boolean visiblePanelObservaciones) {
        this.visiblePanelObservaciones = visiblePanelObservaciones;
    }

    public boolean isBienRegistrado() {
        return bienRegistrado;
    }

    public void setBienRegistrado(boolean bienRegistrado) {
        this.bienRegistrado = bienRegistrado;
    }
    //</editor-fold>
}