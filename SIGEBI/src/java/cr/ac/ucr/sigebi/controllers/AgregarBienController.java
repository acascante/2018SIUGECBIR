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
import cr.ac.ucr.sigebi.commands.BienCommand.NotaCommand;
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
import cr.ac.ucr.sigebi.utils.JsfUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;
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

    @Resource
    private AccesorioModel modelAccesorio;
    @Resource
    private AdjuntoModel modelAdjunto;
    @Resource
    private BienModel modelBien;
    @Resource
    private BienCaracteristicaModel modelBienCaracteristica;
    @Resource
    private CategoriaModel modelCategoria;
    @Resource
    private ClasificacionModel modelClasificacion;
    @Resource
    private IdentificacionModel modelIdentificacion;
    @Resource
    private LoteModel modelLote;
    @Resource
    private MonedaModel modelMoneda;
    @Resource
    private NotaModel modelNota;
    @Resource
    private ProveedorModel modelProveedor;
    @Resource
    private SubCategoriaModel modelSubCategoria;
    @Resource
    private SubClasificacionModel modelSubClasificacion;
    @Resource
    private UbicacionModel modelUbicacion;

    private List<Proveedor> proveedores;
    private List<Accesorio> accesorios;
    private List<BienCaracteristica> caracteristicas;
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
    private boolean visiblePanelModificarCaracteristica;
    private boolean visiblePanelEliminarNota;
    private boolean visiblePanelAdjunto;
    private boolean visiblePanelEliminarAccesorio;
    private boolean bienRegistrado;

    private Tipo tipoAdjuntoDoc;
    private Estado estadoGeneralActivo;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Inicializa Datos y Modificacion del bien">
    public AgregarBienController() {
        super();
        tipoAdjuntoDoc = this.tipoPorDominioValor(Constantes.DOMINIO_ADJUNTO, Constantes.TIPO_ADJUNTO_BIEN);
        estadoGeneralActivo = this.estadoPorDominioValor(Constantes.DOMINIO_IDENTIFICACION, Constantes.ESTADO_GENERAL_ACTIVO);
    }

    private void inicializarNuevo() {
        this.command = new BienCommand(unidadEjecutora);
        Estado estado = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE);
        this.command.setEstado(estado);
        this.command.setEstadoInterno(estado); // TODO Revisar cual es el estado interno para BIENES
        this.bienRegistrado = false;
        inicializarDatos();
        cargarCombos();
    }

    private void inicializarBanderasBotones(Bien bien) {
        if (bien.getEstado().getValor().equals(Constantes.ESTADO_BIEN_PENDIENTE)) {
            this.setVisibleBotonSincronizar(true);
        } else {
            this.setVisibleBotonSincronizar(false);
        }
        if (bien.getEstado().getValor().equals(Constantes.ESTADO_BIEN_PENDIENTE_SINCRONIZAR)) {
            this.setVisibleBotonRechazar(true);
        } else {
            this.setVisibleBotonRechazar(false);
        }
    }

    private void inicializarDetalle(Bien bien) {
        this.command = new BienCommand(bien);
        this.bienRegistrado = true;

        Estado estado = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE);
        this.command.setEstado(estado);
        this.command.setEstadoInterno(estado); // TODO Revisar cual es el estado interno para BIENES

        inicializarBanderasBotones(bien);

        this.accesorios = modelAccesorio.listarPorBien(bien);
        this.caracteristicas = modelBienCaracteristica.listarPorBien(bien);
        command.setNotas(modelNota.listar(bien));
        notaDetalle = "";
        cargarAdjuntos();

        this.tiposCaracteristica = this.tiposPorDominio(Constantes.DOMINIO_CARACTERISTICA);
        if (!this.tiposCaracteristica.isEmpty()) {
            this.itemsCaracteristica = new ArrayList<SelectItem>();
            for (Tipo item : this.tiposCaracteristica) {
                boolean existe = false;
                for (BienCaracteristica bienCaracteristica : this.caracteristicas) {
                    if (bienCaracteristica.getTipo().equals(item)) {
                        existe = true;
                    }
                }

                if (existe == false) {
                    this.itemsCaracteristica.add(new SelectItem(item.getId(), item.getNombre()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                    this.command.getItemCommand().getItemsCaracteristica().put(item.getId(), item);
                }
            }
        }

        cargarCombos();

        if (command.getIdSubCategoria() != null) {
            cargarSubCategorias(command.getItemCommand().getItemsCategoria().get(command.getIdCategoria()));
        }
        if (command.getIdClasificacion() != null) {
            cargarClasificaciones(command.getItemCommand().getItemsSubCategoria().get(command.getIdSubCategoria()));
        }
        if (command.getIdSubClasificacion() != null) {
            cargarSubClasificaciones(command.getItemCommand().getItemsClasificacion().get(command.getIdClasificacion()));
        }
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
        this.visiblePanelModificarCaracteristica = false;
        this.visiblePanelEliminarNota = false;
        this.visiblePanelAdjunto = false;
        this.visiblePanelEliminarAccesorio = false;

        this.bienRegistrado = false;
    }

    private void cargarCombos() {
        try {
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
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.cargarCombos"));
        }
    }

    public String validarForm(UIViewRoot root, UIInput component) {

        //Se validan los datos requeridos 
        String mensajeUsuario = Constantes.OK;
        if (command.getDescripcion().isEmpty()) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.descripcion.requerido");
        } else if (command.getIdTipo() <= 0) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.tipo.requerido");
        } else if (command.getIdOrigen() <= 0) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.origen.requerido");
        } else if (command.getCantidad() == null || command.getCantidad() <= 0) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.cantidad.requerido");
        } else if (command.getIdCategoria() <= 0) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.categoria.requerido");
        } else if (command.getIdSubCategoria() <= 0) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.subcategoria.requerido");
        } else if (command.getIdClasificacion() <= 0) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.clasificacion.requerido");
        } else if (command.getIdSubClasificacion() <= 0) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.subclasificacion.requerido");
        } else if (command.getUbicacionCommand().getIdUbicacion() == null || (command.getUbicacionCommand().getIdUbicacion() != null && command.getUbicacionCommand().getIdUbicacion() <= 0)) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.ubicacion.requerido");
        } else if (command.getProveedorCommand() == null || (command.getProveedorCommand() != null && command.getProveedorCommand().getProveedor() == null)) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.proveedor.requerido");
        } else if (command.getFechaAdquisicion() == null) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.fechaAdquisicion.requerido");
        } else if (command.getIdMoneda() <= 0) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.moneda.requerido");
        } else if (command.getCosto() == null || command.getCosto() < 0) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.costo.requerido");
        } else if (command.getDescripcion().length() <= 3) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.descripcion.minimo");

        }
        return mensajeUsuario;
    }

    public void agregarBien() {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        UIInput component = new UIInput();
        ReentrantLock reentrantLock = new ReentrantLock();
        String messageValidacion = validarForm(root, component);
        if (Constantes.OK.equals(messageValidacion)) {
            try {
                reentrantLock.lock();
                //Se busca la identificacion del bien
                if (!bienRegistrado) {
                    Estado estadoDisponible = this.estadoPorDominioValor(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_DISPONIBLE);
                    Identificacion identificacion = modelIdentificacion.siguienteDisponible(estadoDisponible);
                    if (identificacion == null) {
                        Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.agregarBienController.identificacion.requerido"));
                    } else {
                        command.setIdentificacion(identificacion);
                        bien = command.getBien(null);

                        Estado estadoOcupado = this.estadoPorDominioValor(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_OCUPADA);
                        bien.getIdentificacion().setEstado(estadoOcupado);
                        modelIdentificacion.actualizar(bien.getIdentificacion());

                        modelBien.almacenar(bien);
                        bienRegistrado = true;
                        inicializarDetalle(bien);
                        Mensaje.agregarInfo(Util.getEtiquetas("sigebi.error.agregarBienController.mensaje.exito"));
                    }
                } else {
                    modelBien.actualizar(command.getBien(bien));
                    inicializarDetalle(bien);
                    Mensaje.agregarInfo(Util.getEtiquetas("sigebi.error.agregarBienController.mensaje.exito"));
                }
            } catch (Exception exception) {
                //Se retorna la identificacion
                if (!bienRegistrado && command.getIdentificacion() != null && command.getIdentificacion().getId() != null && command.getIdentificacion().getId() > 0) {
                    Estado estadoDispo = this.estadoPorDominioValor(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_DISPONIBLE);
                    bien.getIdentificacion().setEstado(estadoDispo);
                    modelIdentificacion.actualizar(bien.getIdentificacion());
                }
                if (exception instanceof FWExcepcion) {
                    Mensaje.agregarErrorAdvertencia(((FWExcepcion) exception).getError_para_usuario());
                } else {
                    Mensaje.agregarErrorAdvertencia(exception, Util.getEtiquetas("sigebi.error.agregarBienController.actualizar"));
                }
            } finally {
                reentrantLock.unlock();
            }
        } else {
            component.setValid(false);
            Mensaje.agregarErrorAdvertencia(messageValidacion);
        }
    }

    public void verificaCapitalizable(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        command.calculaCapitalizable();
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Navegación del MENÚ">
    public void nuevoRegistro(ActionEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        inicializarNuevo();
        this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
        Util.navegar(Constantes.KEY_VISTA_DETALLE_BIEN);
    }

    public void listarActas()
    {
        Util.navegar(Constantes.KEY_VISTA_LISTAR_ACTAS);
    }  
    
    public void listarTraslados()
    {
        Util.navegar(Constantes.KEY_VISTA_TRASLADOS_LISTAR);
    }   
    
    
    public void listarInformes()
    {
        Util.navegar(Constantes.VISTA_INFORME_TECNICO);
    }   
    
    
    public void modificarRegistro(ActionEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        this.bien = (Bien) event.getComponent().getAttributes().get("bienSeleccionado");
        inicializarDetalle(bien);
        this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
        Util.navegar(Constantes.KEY_VISTA_DETALLE_BIEN);
    }

    public void regresarListado() {
        if (vistaOrigen != null) {
            Util.navegar(vistaOrigen);
        } else {
            Util.navegar(Constantes.KEY_VISTA_LISTAR_BIENES);
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
        try {
            //Se limpian subcategoria, clasificacion y  sub clasificacion
            if (itemsSubCategoria != null) {
                itemsSubCategoria.clear();
            }
            if (itemsClasificacion != null) {
                itemsClasificacion.clear();
                this.setDisableClasificaciones(true);
            }
            if (itemsSubClasificacion != null) {
                itemsSubClasificacion.clear();
                this.setDisableSubClasificaciones(true);
            }

            if (Constantes.DEFAULT_ID.equals(command.getIdCategoria())) {
                command.setIdSubCategoria(Constantes.DEFAULT_ID);
                this.setDisableSubCategorias(true);
                cargarClasificaciones(command.getItemCommand().getItemsSubCategoria().get(command.getIdSubCategoria()));
            } else {
                List<SubCategoria> subCategorias = modelSubCategoria.listar(categoria);
                if (!subCategorias.isEmpty()) {
                    this.setDisableSubCategorias(false);
                    itemsSubCategoria = new ArrayList<SelectItem>();
                    command.getItemCommand().getItemsSubCategoria().clear();
                    for (SubCategoria item : subCategorias) {
                        itemsSubCategoria.add(new SelectItem(item.getId(), item.getDescripcion()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                        command.getItemCommand().getItemsSubCategoria().put(item.getId(), item);
                    }
                }
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.cargarSubCategorias"));
        }

    }

    private void cargarClasificaciones(SubCategoria subCategoria) {
        try {
            //Se limpian clasificacion y  sub clasificacion
            if (itemsClasificacion != null) {
                itemsClasificacion.clear();
            }
            if (itemsSubClasificacion != null) {
                itemsSubClasificacion.clear();
                this.setDisableSubClasificaciones(true);
            }
            if (Constantes.DEFAULT_ID.equals(command.getIdSubCategoria())) {
                command.setIdClasificacion(Constantes.DEFAULT_ID);
                this.setDisableClasificaciones(true);
                cargarSubClasificaciones(command.getItemCommand().getItemsClasificacion().get(command.getIdClasificacion()));
            } else {
                List<Clasificacion> clasificaciones = modelClasificacion.listarPorSubCategoria(subCategoria);
                if (!clasificaciones.isEmpty()) {
                    this.setDisableClasificaciones(false);
                    command.getItemCommand().getItemsClasificacion().clear();
                    itemsClasificacion = new ArrayList<SelectItem>();
                    for (Clasificacion item : clasificaciones) {
                        itemsClasificacion.add(new SelectItem(item.getId(), item.getNombre()));
                        command.getItemCommand().getItemsClasificacion().put(item.getId(), item);
                    }
                }
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.cargarClasificaciones"));
        }

    }

    private void cargarSubClasificaciones(Clasificacion clasificacion) {
        try {
            //Se limpian sub clasificacion
            if (itemsSubClasificacion != null) {
                itemsSubClasificacion.clear();
            }
            if (Constantes.DEFAULT_ID.equals(command.getIdClasificacion())) {
                command.setIdSubClasificacion(Constantes.DEFAULT_ID);
                this.setDisableSubClasificaciones(true);
            } else {
                List<SubClasificacion> subClasificaciones = modelSubClasificacion.listar(clasificacion);
                if (!subClasificaciones.isEmpty()) {
                    itemsSubClasificacion = new ArrayList<SelectItem>();
                    command.getItemCommand().getItemsSubClasificacion().clear();
                    for (SubClasificacion item : subClasificaciones) {
                        this.setDisableSubClasificaciones(false);
                        itemsSubClasificacion.add(new SelectItem(item.getId(), item.getNombre()));
                        command.getItemCommand().getItemsSubClasificacion().put(item.getId(), item);
                    }
                }
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.cargarSubClasificaciones"));
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
        if (command.getUbicacionCommand().getIdUbicacion() != null && command.getUbicacionCommand().getIdUbicacion() > 0) {

            command.getUbicacionCommand().setUbicacion(command.getItemCommand().getItemsUbicacion().get(command.getUbicacionCommand().getIdUbicacion()));
        }
        this.setVisiblePanelUbicaciones(false);
    }

    private void cargarUbicaciones() {
        try {
            List<Ubicacion> ubicaciones = modelUbicacion.listar(unidadEjecutora);
            if (!ubicaciones.isEmpty()) {
                itemsUbicacion = new ArrayList<SelectItem>();
                for (Ubicacion item : ubicaciones) {
                    itemsUbicacion.add(new SelectItem(item.getId(), item.getDetalle()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                    command.getItemCommand().getItemsUbicacion().put(item.getId(), item);
                }
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.cargarUbicaciones"));
        }

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Proveedores">
    public void selecionarProveedor(ActionEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            Proveedor proveedor = (Proveedor) event.getComponent().getAttributes().get("proveedorSeleccionado");
            command.getProveedorCommand().setProveedor(proveedor);
            this.setVisiblePanelProveedores(false);
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.selecionarProveedor"));
        }

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
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.filtroProveedor"));
        }
    }
    //</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="Panel Observacion Sincronizar o Rechazar">
    public void rechazarBien() {
        try {
            if (command.getObservacionCliente() == null || command.getObservacionCliente().isEmpty()) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.agregarBienController.rechazarBien.sin.observacion"));
            } else {
                Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;
                bien.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE));
                this.command.setEstado(bien.getEstado());
                modelBien.cambiaEstadoBien(bien, bien.getEstado(), command.getObservacionCliente(), telefono, usuarioSIGEBI);
                command.setObservacionCliente("");
                inicializarBanderasBotones(bien);
                this.cerrarPanelObservaciones();
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.rechazarBien"));
        }
    }

    public void solicitarSincronizacion() {
        try {
            Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;
            bien.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE_SINCRONIZAR));
            this.command.setEstado(bien.getEstado());
            modelBien.cambiaEstadoBien(bien, bien.getEstado(), command.getObservacionCliente(), telefono, usuarioSIGEBI);
            inicializarBanderasBotones(bien);
            this.cerrarPanelObservaciones();
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.solicitarSincronizacion"));
        }
    }

    public boolean mostrarPanelObservaciones(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return false;
        }
        this.setVisiblePanelObservaciones(true);
        return true;
    }

    public boolean cerrarPanelObservaciones() {
        this.setVisiblePanelObservaciones(false);
        return false;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tab Notas del Activo">
    public void guardarNota() {
        try {
            Nota nota = command.getNotaCommand().getNota();
            nota.setDetalle(notaDetalle);
            nota.setEstado(estadoGeneralActivo);
            modelNota.guardar(nota);
            command.setNotas(modelNota.listar(nota.getBien()));
            notaDetalle = "";
            Mensaje.agregarInfo(Util.getEtiquetas("sigebi.Bien.Registro.Exito"));
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.Registro"));
        }
    }

    public void eliminarNota(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            Nota nota = new Nota();
            nota = (Nota) pEvent.getComponent().getAttributes().get("notaSeleccionada");
            visiblePanelEliminarNota = true;
            NotaCommand notaCmd = command.getNotaCommand();
            notaCmd.setId(nota.getId());
            notaCmd.setEstado(nota.getEstado());
            notaCmd.setDetalle(nota.getDetalle());
            command.setNotaCommand(notaCmd);
            //mensajeNota = notaModel.eliminarNota(nota);

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.Registro"));
        }
    }

    public void eliminarNotaConfirmar() {

        try {
            modelNota.eliminar(command.getNotaCommand().getNota());

            Nota nota = new Nota();
            NotaCommand notaCmd = command.getNotaCommand();
            notaCmd.setId(nota.getId());
            notaCmd.setDetalle(nota.getDetalle());
            command.setNotaCommand(notaCmd);

            visiblePanelEliminarNota = false;

            command.setNotas(modelNota.listar(command.getNotaCommand().getBien()));
            notaDetalle = "";
            Mensaje.agregarInfo(Util.getEtiquetas("sigebi.Bien.Exito.Eliminar"));

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.Registro"));
        }

    }

    public void eliminarNotaCancelar() {
        try {

            Nota nota = new Nota();
            NotaCommand notaCmd = command.getNotaCommand();
            notaCmd.setId(nota.getId());
            notaCmd.setDetalle(nota.getDetalle());
            command.setNotaCommand(notaCmd);

            visiblePanelEliminarNota = false;
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.Registro"));
        }
    }

    String notaDetalle;

    public String getNotaDetalle() {
        return notaDetalle;
    }

    public void setNotaDetalle(String notaDetalle) {
        this.notaDetalle = notaDetalle;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Tab Garantías">
    public boolean validarGarantia() {
        return command.getInicioGarantia().compareTo(command.getFinGarantia()) < 0;
    }

    public void guardarGarantia() {
        try {
            if (validarGarantia()) {
                Bien bien2 = modelBien.buscarPorId(command.getId());

                bien2.setInicioGarantia(command.getInicioGarantia());
                bien2.setFinGarantia(command.getFinGarantia());
                bien2.setDescripcionGarantia(command.getDescripcionGarantia());

                modelBien.actualizar(bien2);
                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.error.agregarBienController.mensaje.exito"));
            } else {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.agregarBienController.Fecha1"));
            }
        } catch (NullPointerException e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.agregarBienController.fecha2"));
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.guardarGarantia"));
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tad Archivos-Adjuntos">
    String adjuntoDetalle;

    public void checkFileLocation(ActionEvent event) {
        try {
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

                    adjunto.setTipo(this.tipoAdjuntoDoc);
                    adjunto.setEstado(estadoGeneralActivo);
                    adjunto.setDetalle(adjuntoDetalle);
                    adjunto.setIdReferencia(command.getId());
                    command.setAdjunto(adjunto);
                    guardarAdjunto();
                }
            }
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err, Util.getEtiquetas("sigebi.Bien.Error.Registro"));
        }
    }

    public void guardarAdjunto() {
        try {
            modelAdjunto.agregar(command.getAdjunto());
            command.setAdjuntos(modelAdjunto.buscarPorReferencia(tipoAdjuntoDoc, bien.getId()));
            command.setAdjunto(new Adjunto());
            cargarAdjuntos();
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err, Util.getEtiquetas("sigebi.Bien.Error.Registro"));
        }
    }

    public void adjuntoMostrarDetalle(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            adjuntosUbicacion = "upload/";
            Adjunto adjunto = new Adjunto();

            adjunto = (Adjunto) pEvent.getComponent().getAttributes().get("adjuntoSeleccionado");
            visiblePanelAdjunto = true;

            command.setAdjunto(adjunto);

            adjuntoDescargar = adjuntosUbicacion + adjunto.getNombre();
            adjuntoNombreDescarga = adjunto.getNombre().replace("." + adjunto.getExtension(), "");
            if ((adjunto.getExtension().toUpperCase().equals("JPG"))
                    || (adjunto.getExtension().toUpperCase().equals("PNG"))
                    || (adjunto.getExtension().toUpperCase().equals("GIF"))
                    || (adjunto.getExtension().toUpperCase().equals("JPGE"))) {
                adjuntoMostrar = adjuntoDescargar;

            } else {
                adjuntoMostrar = "imagenes/botones/descargar_SIIAGC.png";
            }

        } catch (Exception err) {
            mensajeAdjunto = err.getMessage();
        }
    }

    String adjuntosUbicacion;
    String adjuntoDescargar;
    String adjuntoNombreDescarga;
    String adjuntoMostrar;

    private void cargarAdjuntos() {
        try {
            adjuntoDetalle = "";
            command.setAdjuntos(modelAdjunto.buscarPorReferencia(tipoAdjuntoDoc, bien.getId()));
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err, Util.getEtiquetas("sigebi.Bien.Error.Consultar.Registros"));
        }
    }

    public void adjuntoEliminarCancelar() {

        command.setAdjunto(new Adjunto());
        visiblePanelAdjunto = false;
    }

    public void adjuntoEliminarConfirmar() {
        try {
            modelAdjunto.eliminar(command.getAdjunto());
            command.setAdjunto(new Adjunto());
            visiblePanelAdjunto = false;
            cargarAdjuntos();
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err, Util.getEtiquetas("sigebi.error.agregarBienController.cargarSubCategorias"));

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
            File file = new File(adjuntoDescargar);
            InputStream fis = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int offset = 0;
            int numRead = 0;

            while ((offset < buf.length) && ((numRead = fis.read(buf, offset, buf.length - offset)) >= 0)) {
                offset += numRead;
            }
            fis.close();
            HttpServletResponse response
                    = (HttpServletResponse) FacesContext.getCurrentInstance()
                            .getExternalContext().getResponse();

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

    public String getAdjuntoDetalle() {
        return adjuntoDetalle;
    }

    public void setAdjuntoDetalle(String adjuntoDetalle) {
        this.adjuntoDetalle = adjuntoDetalle;
    }

    public String getAdjuntosUbicacion() {
        return adjuntosUbicacion;
    }

    public void setAdjuntosUbicacion(String adjuntosUbicacion) {
        this.adjuntosUbicacion = adjuntosUbicacion;
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

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Tab Accesorios">
    public void guardarAccesorio() {
        try {

            if (validarAccesorio()) {
                Estado estado = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_ACTIVO); // TODO revisar dominios para accesorios
                this.command.getAccesorioCommand().setBien(bien);
                this.command.getAccesorioCommand().setEstado(estado);
                Accesorio accesorio = command.getAccesorioCommand().getAccesorio();
                modelAccesorio.almacenar(accesorio);
                this.accesorios.add(accesorio);
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.guardarAccesorio"));
        }
    }

    private boolean validarAccesorio() {
        return true;
    }

    public void mostrarPanelEliminarAccesorio(ActionEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }

            Accesorio accesorio = (Accesorio) event.getComponent().getAttributes().get("accesorioSeleccionado");
            this.command.getAccesorioCommand().setItemAccesorio(accesorio);
            this.command.getAccesorioCommand().setDetalle(accesorio.getDetalle());
            this.setVisiblePanelEliminarAccesorio(true);
        } catch (Exception err) {
            mensajeCaracteristica = err.getMessage();
        }
    }

    public void eliminarAccesorioCancelar() {
        this.setVisiblePanelEliminarAccesorio(false);
    }

    public void eliminarAccesorioConfirmar() {
        try {
            Accesorio accesorio = this.command.getAccesorioCommand().getItemAccesorio();
            modelAccesorio.eliminar(accesorio);
            this.accesorios.remove(accesorio);
            this.setVisiblePanelEliminarAccesorio(false);
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.eliminarAccesorioConfirmar"));
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tab Características">
    public void guardarCaracteristica() {
        try {

            String mensajeError = validarCaracteristica();
            if (mensajeError.equals(Constantes.OK)) {

                //Se asignan los valores a la caracteristica
                BienCaracteristica caracteristica = command.getCaracteristicaCommand().getCaracteristica();

                //Se asigna el estado y el tipo                
                caracteristica.setBien(bien);
                caracteristica.setTipo(this.command.getItemCommand().getItemsCaracteristica().get(this.command.getCaracteristicaCommand().getIdTipo()));
                caracteristica.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO));

                modelBienCaracteristica.almacenar(caracteristica);

                //Se refresca el command
                this.command.getCaracteristicaCommand().setCaracteristica(new BienCaracteristica());

                //Se elimina el tipo de del mapa y de la lista de tipos
                this.command.getItemCommand().getItemsCaracteristica().remove(caracteristica.getTipo().getId());
                this.itemsCaracteristica = JsfUtil.eliminarItem(this.itemsCaracteristica, caracteristica.getTipo().getId());

                //Se agrega a la lista de caracteristicas
                this.caracteristicas.add(caracteristica);

            } else {
                Mensaje.agregarErrorAdvertencia(mensajeError);
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.guardarCaracteristica"));
        }
    }

    public void mostrarPanelModificarCaracteristica(ActionEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }

            //Se asigna la caracteristica consultada
            this.command.getCaracteristicaCommand().setCaracteristica((BienCaracteristica) event.getComponent().getAttributes().get("caracteristicaSelccionada"));
            this.command.getCaracteristicaCommand().setDetalleModificar(command.getCaracteristicaCommand().getCaracteristica().getDetalle());

            //Se presenta el panel
            this.setVisiblePanelModificarCaracteristica(true);
        } catch (Exception err) {

            mensajeCaracteristica = err.getMessage();
        }
    }

    public void panelCaracteristicaCancelar() {
        this.setVisiblePanelModificarCaracteristica(false);
    }

    public void panelCaracteristicaEliminar() {
        try {

            //Se obtiene la caracteristica
            BienCaracteristica caracteristica = this.command.getCaracteristicaCommand().getCaracteristica();

            //Se respalda el tipo
            Tipo tipoCaracteristica = caracteristica.getTipo();

            //Se elimina de la base de datos
            modelBienCaracteristica.eliminar(caracteristica);

            //Se refresca el command
            this.command.getCaracteristicaCommand().setCaracteristica(new BienCaracteristica());

            //Se agrega el tipo almapa y de la lista de tipos
            this.command.getItemCommand().getItemsCaracteristica().put(tipoCaracteristica.getId(), tipoCaracteristica);
            this.itemsCaracteristica.add(new SelectItem(tipoCaracteristica.getId(), tipoCaracteristica.getNombre()));

            //Se refresca la lista de caracteristicas
            this.caracteristicas = modelBienCaracteristica.listarPorBien(bien);

            //Se cierra el panel
            this.setVisiblePanelModificarCaracteristica(false);

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.panelCaracteristicaEliminar"));
        }
    }

    public void panelCaracteristicaModificar() {
        try {

            String mensajeError = validarCaracteristica();
            if (mensajeError.equals(Constantes.OK)) {

                //Se asignan los valores a la caracteristica
                BienCaracteristica caracteristica = command.getCaracteristicaCommand().getCaracteristica();
                caracteristica.setBien(bien);
                caracteristica.setDetalle(command.getCaracteristicaCommand().getDetalleModificar());

                //Se modifica
                modelBienCaracteristica.almacenar(caracteristica);

                //Se refresca el command
                this.command.getCaracteristicaCommand().setCaracteristica(new BienCaracteristica());

                //Se refresca la lista de caracteristicas
                this.caracteristicas = modelBienCaracteristica.listarPorBien(bien);

                //Se cierra el panel
                this.setVisiblePanelModificarCaracteristica(false);

            } else {
                Mensaje.agregarErrorAdvertencia(mensajeError);
            }

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.panelCaracteristicaModificar"));
        }
    }

    public String validarCaracteristica() {

        //Se validan los datos requeridos 
        String mensajeUsuario = Constantes.OK;
        if (command.getCaracteristicaCommand().getCaracteristica().getId() != null
                && command.getCaracteristicaCommand().getCaracteristica().getId() > 0) {
            if (command.getCaracteristicaCommand().getDetalleModificar().isEmpty()) {
                mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.caracteristica.detalle.requerido");
            }
            if (command.getCaracteristicaCommand().getCaracteristica().getTipo() == null) {
                mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.caracteristica.tipo.requerido");
            }
        } else {
            if (command.getCaracteristicaCommand().getIdTipo() <= 0) {
                mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.caracteristica.tipo.requerido");
            }
            if (command.getCaracteristicaCommand().getCaracteristica().getDetalle().isEmpty()) {
                mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.caracteristica.detalle.requerido");
            }
        }
        return mensajeUsuario;
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

    public boolean isVisiblePanelModificarCaracteristica() {
        return visiblePanelModificarCaracteristica;
    }

    public void setVisiblePanelModificarCaracteristica(boolean visiblePanelModificarCaracteristica) {
        this.visiblePanelModificarCaracteristica = visiblePanelModificarCaracteristica;
    }

    public boolean isVisiblePanelEliminarNota() {
        return visiblePanelEliminarNota;
    }

    public void setVisiblePanelEliminarNota(boolean visiblePanelEliminarNota) {
        this.visiblePanelEliminarNota = visiblePanelEliminarNota;
    }

    public boolean isVisiblePanelAdjunto() {
        return visiblePanelAdjunto;
    }

    public void setVisiblePanelAdjunto(boolean visiblePanelAdjunto) {
        this.visiblePanelAdjunto = visiblePanelAdjunto;
    }

    public boolean isVisiblePanelEliminarAccesorio() {
        return visiblePanelEliminarAccesorio;
    }

    public void setVisiblePanelEliminarAccesorio(boolean visiblePanelEliminarAccesorio) {
        this.visiblePanelEliminarAccesorio = visiblePanelEliminarAccesorio;
    }

    public List<Accesorio> getAccesorios() {
        return accesorios;
    }

    public void setAccesorios(List<Accesorio> accesorios) {
        this.accesorios = accesorios;
    }

    public List<BienCaracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(List<BienCaracteristica> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }
    //</editor-fold>

}
