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
import cr.ac.ucr.sigebi.domain.AutorizacionRolPersona;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.BienCaracteristica;
import cr.ac.ucr.sigebi.domain.Lote;
import cr.ac.ucr.sigebi.domain.Moneda;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.models.CategoriaModel;
import cr.ac.ucr.sigebi.models.ClasificacionModel;
import cr.ac.ucr.sigebi.models.MonedaModel;
import cr.ac.ucr.sigebi.domain.Categoria;
import cr.ac.ucr.sigebi.domain.Clasificacion;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Identificacion;
import cr.ac.ucr.sigebi.domain.InterfazAccesorio;
import cr.ac.ucr.sigebi.domain.InterfazAdjunto;
import cr.ac.ucr.sigebi.domain.InterfazBien;
import cr.ac.ucr.sigebi.domain.Nota;
import cr.ac.ucr.sigebi.domain.Proveedor;
import cr.ac.ucr.sigebi.domain.Solicitud;
import cr.ac.ucr.sigebi.domain.SolicitudDetalle;
import cr.ac.ucr.sigebi.domain.SolicitudDonacion;
import cr.ac.ucr.sigebi.domain.SolicitudExclusion;
import cr.ac.ucr.sigebi.domain.SolicitudTraslado;
import cr.ac.ucr.sigebi.domain.SubCategoria;
import cr.ac.ucr.sigebi.domain.SubClasificacion;
import cr.ac.ucr.sigebi.domain.Ubicacion;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.models.AccesorioModel;
import cr.ac.ucr.sigebi.models.AdjuntoModel;
import cr.ac.ucr.sigebi.models.AutorizacionRolPersonaModel;
import cr.ac.ucr.sigebi.models.BienCaracteristicaModel;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.IdentificacionModel;
import cr.ac.ucr.sigebi.models.InterfazBienModel;
import cr.ac.ucr.sigebi.models.LoteModel;
import cr.ac.ucr.sigebi.models.NotaModel;
import cr.ac.ucr.sigebi.models.ProveedorModel;
import cr.ac.ucr.sigebi.models.RegistroMovimientoModel;
import cr.ac.ucr.sigebi.models.SolicitudModel;
import cr.ac.ucr.sigebi.models.SubCategoriaModel;
import cr.ac.ucr.sigebi.models.SubClasificacionModel;
import cr.ac.ucr.sigebi.models.UbicacionModel;
import cr.ac.ucr.sigebi.models.UnidadEjecutoraModel;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.beans.factory.annotation.Autowired;
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
    private List<SelectItem> itemsUnidades;
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
    @Resource
    private RegistroMovimientoModel modelMovimientos;
    @Resource
    private SolicitudModel solicitudModel;
    @Resource
    private UnidadEjecutoraModel unidadEjecutoraModel;
    @Resource
    private InterfazBienModel interfazBienModel;
    @Resource 
    private AutorizacionRolPersonaModel autorizacionRolPersonaModel; 
            
    private List<Proveedor> proveedores;
    private List<Identificacion> identificaciones;
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
    private boolean visiblePanelIdentificador;
    private boolean visiblePanelProveedores;
    private boolean visibleBotonSincronizar;
    private boolean visibleBotonRechazar;
    private boolean visibleBotonActualizarIdentificacion;
    private boolean visiblePanelObservaciones;
    private boolean visiblePanelModificarCaracteristica;
    private boolean visiblePanelEliminarNota;
    private boolean visiblePanelAdjunto;
    private boolean visiblePanelEliminarAccesorio;
    private boolean bienRegistrado;
    private boolean donacion;
    private boolean interfaz;

    private Tipo tipoAdjuntoDoc;
    private Estado estadoGeneralActivo;
    private SolicitudDonacion solicitudDonacion;
    private InterfazBien interfazBien;

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
        
        //Verifica si puede cambiar la identificacion del bien
        AutorizacionRolPersona autorizado = autorizacionRolPersonaModel.buscar(Constantes.CODIGO_AUTORIZACION_ADMINISTRADOR, Constantes.CODIGO_ROL_ADMINISTRADOR_AUTORIZACION_ADMINISTRADOR, usuarioSIGEBI, unidadEjecutora);
        this.setVisibleBotonActualizarIdentificacion(autorizado != null && !(command.getIdentificacion().getId() != null && command.getIdentificacion().getId() > 0));
    }

    private void inicializarDetalle(Bien bien) {
        this.command = new BienCommand(bien);
        this.bienRegistrado = true;

        Estado estado = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE);
        this.command.setEstado(estado);
        this.command.setEstadoInterno(estado); // TODO Revisar cual es el estado interno para BIENES

        inicializarBanderasBotones(bien);

        this.accesorios = modelAccesorio.listarPorBien(bien);
        listarCaracteristicas();
        command.setNotas(modelNota.listar(bien));
        notaDetalle = "";
        cargarAdjuntos();

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
        
        List<Solicitud> movs = new ArrayList<Solicitud>();
        
        Solicitud ingreso;
        ingreso = new Solicitud(){};
        ingreso.setEstado(estadoGeneralActivo);
        ingreso.setUsuario(usuarioSIGEBI);
        ingreso.setFecha(command.getFechaIngreso());
        ingreso.setDiscriminator(0);
        movs.add(ingreso);
        
        movs.addAll( modelMovimientos.movimientosPorBien(bien) );
        command.setMovimientos(movs);
    }

    private void inicializarDatos() {
        this.mensajeExito = "";
        this.mensaje = "";

        this.disableSubCategorias = true;
        this.disableClasificaciones = true;
        this.disableSubClasificaciones = true;

        this.visiblePanelObservaciones = false;
        this.visiblePanelIdentificador = false;
        this.visiblePanelProveedores = false;        
        this.visiblePanelUbicaciones = false;
        this.visibleBotonSincronizar = false;
        this.visibleBotonRechazar = false;
        this.visibleBotonActualizarIdentificacion = false;
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
            if (donacion) {
                Tipo item = this.tipoPorDominioValor(Constantes.DOMINIO_ORIGEN, Constantes.TIPO_ORIGEN_EXTERNA);
                itemsOrigen = new ArrayList<SelectItem>();
                itemsOrigen.add(new SelectItem(item.getId(), item.getNombre()));
                command.getItemCommand().getItemsOrigen().put(item.getId(), item);
            } else {

                List<Tipo> tiposOrigen = this.tiposPorDominio(Constantes.DOMINIO_ORIGEN);
                if (!tiposOrigen.isEmpty()) {
                    itemsOrigen = new ArrayList<SelectItem>();
                    for (Tipo item : tiposOrigen) {
                        itemsOrigen.add(new SelectItem(item.getId(), item.getNombre()));
                        command.getItemCommand().getItemsOrigen().put(item.getId(), item);
                    }
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
        try{
        //Se validan los datos requeridos 
        String mensajeUsuario = Constantes.OK;
        if (command.getDescripcion().isEmpty()) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.descripcion.requerido");
        } else if (command.getIdTipo() == null || command.getIdTipo() <= 0) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.tipo.requerido");
        } else if (command.getIdOrigen() == null || command.getIdOrigen() <= 0) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.origen.requerido");
        } else if (command.getCantidad() == null || command.getCantidad() <= 0) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.cantidad.requerido");
        } else if (command.getIdCategoria() == null || command.getIdCategoria() <= 0) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.categoria.requerido");
        } else if (command.getIdSubCategoria() == null || command.getIdSubCategoria() <= 0) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.subcategoria.requerido");
        } else if (command.getIdClasificacion() == null || command.getIdClasificacion() <= 0) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.clasificacion.requerido");
        } else if (command.getIdSubClasificacion() == null || command.getIdSubClasificacion() <= 0) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.subclasificacion.requerido");
        } else if (command.getUbicacionCommand().getIdUbicacion() == null || (command.getUbicacionCommand().getIdUbicacion() != null && command.getUbicacionCommand().getIdUbicacion() <= 0)) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.ubicacion.requerido");
        } else if (command.getProveedorCommand() == null || (command.getProveedorCommand() != null && command.getProveedorCommand().getProveedor() == null)) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.proveedor.requerido");
        } else if (command.getFechaAdquisicion() == null) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.fechaAdquisicion.requerido");
        } else if (command.getIdMoneda() == null || command.getIdMoneda() <= 0) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.moneda.requerido");
        } else if (command.getCosto() == null || command.getCosto() < 0) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.costo.requerido");
        } else if (command.getDescripcion().length() <= 3) {
            mensajeUsuario = Util.getEtiquetas("sigebi.error.agregarBienController.descripcion.minimo");

        }
        return mensajeUsuario;
        }catch(Exception err){
            return Util.getEtiquetas("sigebi.error.agregarBienController.Error.Validacion");
            
        }
    }

    public void agregarBien() {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        UIInput component = new UIInput();
        String messageValidacion = validarForm(root, component);
        if (Constantes.OK.equals(messageValidacion)) {
            try {

                //Se crea el bien
                this.procesarBien();


            } catch (Exception exception) {
                if (exception instanceof FWExcepcion) {
                    Mensaje.agregarErrorAdvertencia(((FWExcepcion) exception).getError_para_usuario());
                } else {
                    Mensaje.agregarErrorAdvertencia(exception, Util.getEtiquetas("sigebi.error.agregarBienController.actualizar"));
                }
            }
        } else {
            component.setValid(false);
            Mensaje.agregarErrorAdvertencia(messageValidacion);
        }
    }

    private void procesarBien() throws Exception {
        ReentrantLock reentrantLock = new ReentrantLock();
        Boolean actualizacionSinIdentificacion = false;
        Identificacion identificacion = null; 
        
        try {
            
            reentrantLock.lock();
         
            if (!bienRegistrado) {
                //Se busca la identificacion del bien
                Estado estadoDisponible = this.estadoPorDominioValor(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_DISPONIBLE);
                identificacion = modelIdentificacion.siguienteDisponible(estadoDisponible, unidadEjecutora);
                if (identificacion == null) {
                    Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.agregarBienController.identificacion.requerido"));
                    return;
                } else {
                    command.setIdentificacion(identificacion);
                    command.setUsuarioRegistra(this.usuarioSIGEBI);
                    command.setFechaIngreso(new Date());
                    bien = command.getBien(null);

                    Estado estadoOcupado = this.estadoPorDominioValor(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_OCUPADA);
                    bien.getIdentificacion().setEstado(estadoOcupado);
                    
                    modelBien.almacenar(bien);

                    modelIdentificacion.actualizar(bien.getIdentificacion());

                    bienRegistrado = true;
                    inicializarDetalle(bien);

                    //Se crea el detalle para la solicitud
                    if (donacion) {
                        SolicitudDetalle solicitudDetalle = new SolicitudDetalle(solicitudDonacion, bien, estadoGeneralActivo);
                        solicitudModel.agregarDetalleSolicitud(solicitudDetalle);
                    }
                }
            } else {
                identificacion = command.getIdentificacion();
                if (identificacion.getIdentificacion() == null) {
                    Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.agregarBienController.identificacion.requerido"));
                    return;
                }else{
                    
                    //Si la identificacion se encuentra disponible se debe cambiar su estado
                    if(command.getIdentificacion().getEstado().getValor().equals(Constantes.IDENTIFICACION_ESTADO_DISPONIBLE)){
                        
                        //Se maca para rollback
                        actualizacionSinIdentificacion = true;
                                
                        //Se asigna al bien
                        bien.setIdentificacion(command.getIdentificacion());

                        //Se cambia el estado
                        Estado estadoOcupado = this.estadoPorDominioValor(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_OCUPADA);
                        bien.getIdentificacion().setEstado(estadoOcupado);

                        //Se actualiza el bien
                        modelBien.actualizar(command.getBien(bien));

                        //Se cambia el estado a la identificacion
                        modelIdentificacion.actualizar(bien.getIdentificacion());                    
                    }else{
                        
                        //Solo se actualiza el bien
                        modelBien.actualizar(command.getBien(bien));
                    }

                    //Se inicializan los datos de la pantalla
                    inicializarDetalle(bien);
                } 
            }
            
            
            
            // Mensaje Éxito
            Mensaje.agregarInfo(Util.getEtiquetas("sigebi.error.agregarBienController.mensaje.exito"));
            
        } catch (Exception exception) {
            
            //Se retorna la identificacion
            if (!bienRegistrado && command.getIdentificacion() != null && command.getIdentificacion().getId() != null && command.getIdentificacion().getId() > 0) {
                Estado estadoDispo = this.estadoPorDominioValor(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_DISPONIBLE);
                bien.getIdentificacion().setEstado(estadoDispo);
                modelIdentificacion.actualizar(bien.getIdentificacion());
                bien.setIdentificacion(null);                
            }

            if (bienRegistrado && actualizacionSinIdentificacion && command.getIdentificacion() != null && command.getIdentificacion().getId() != null && command.getIdentificacion().getId() > 0) {
                Estado estadoDispo = this.estadoPorDominioValor(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_DISPONIBLE);
                bien.getIdentificacion().setEstado(estadoDispo);
                modelIdentificacion.actualizar(bien.getIdentificacion());
                bien.setIdentificacion(null);                
            }
            throw exception;
        } finally {
            reentrantLock.unlock();
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
    
    //<editor-fold defaultstate="collapsed" desc="Funciones Lote">
    public void cambioLote(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        Long valor = command.getIdLote();
        if (valor != -1L) {
            command.setCantidadActivo(false);
        } else {
            command.setCantidadActivo(true);
            command.setCantidad(1);
        }
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Navegación del MENÚ">
    public void nuevoRegistro(ActionEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        this.donacion = false;
        this.interfaz = false;
        inicializarNuevo();
        this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
        Util.navegar(Constantes.KEY_VISTA_DETALLE_BIEN);
    }

    public void modificarRegistro(ActionEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        this.donacion = false;
        this.interfaz = false;
        Bien bienSecc = (Bien) event.getComponent().getAttributes().get("bienSeleccionado");
        bien = modelBien.buscarPorId(bienSecc.getId());
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
    
    //<editor-fold defaultstate="collapsed" desc="Donacion">
    public void guardarBienDonacion() {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        UIInput component = new UIInput();
        String messageValidacion = validarForm(root, component);
        if (Constantes.OK.equals(messageValidacion)) {
            try {
                //Se crea el bien
                this.procesarBien();

                context.getExternalContext().getSessionMap().remove("controllerSolicitudDonacion");
                Util.agregarVariableSession("idDonacion", solicitudDonacion.getId());
                Util.navegar(vistaOrigen);
                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.error.agregarBienController.mensaje.exito"));

            } catch (Exception exception) {
                if (exception instanceof FWExcepcion) {
                    Mensaje.agregarErrorAdvertencia(((FWExcepcion) exception).getError_para_usuario());
                } else {
                    Mensaje.agregarErrorAdvertencia(exception, Util.getEtiquetas("sigebi.error.agregarBienController.actualizar"));
                }
            }
        } else {
            component.setValid(false);
            Mensaje.agregarErrorAdvertencia(messageValidacion);
        }
    }

    public void nuevoRegistroDonacion(ActionEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        this.donacion = true;
        this.interfaz = false;
        inicializarNuevo();
        this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
        solicitudDonacion = (SolicitudDonacion) event.getComponent().getAttributes().get("solicitudDonacion");
        Util.navegar(Constantes.KEY_VISTA_DETALLE_BIEN_DONACION);
    }

    public void modificarRegistroDonacion(ActionEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }

        this.donacion = true;
        this.interfaz = false;
        Bien bienSecc = (Bien) event.getComponent().getAttributes().get("bienSeleccionado");
        bien = modelBien.buscarPorId(bienSecc.getId());
        inicializarDetalle(bien);
        solicitudDonacion = (SolicitudDonacion) event.getComponent().getAttributes().get("solicitudDonacion");

        this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
        Util.navegar(Constantes.KEY_VISTA_DETALLE_BIEN_DONACION);
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Interfaz Bien">
    public void nuevoRegistroInterfaz(ActionEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }

        //Se obtiene la interfaz        
        interfazBien = (InterfazBien) event.getComponent().getAttributes().get("interfazBien");
        try {
            this.donacion = false;
            this.interfaz = true;

            //Se carga la pantalla de acuerdo a los datos en la interfaz
            inicializarBienInterfaz();

            this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
            Util.navegar(Constantes.KEY_VISTA_DETALLE_BIEN_INTERFAZ);

        } catch (Exception exception) {
            if (exception instanceof FWExcepcion) {
                Mensaje.agregarErrorAdvertencia(((FWExcepcion) exception).getError_para_usuario());
            } else {
                Mensaje.agregarErrorAdvertencia(exception, Util.getEtiquetas("sigebi.error.agregarBienController.actualizar"));
            }
        }        
    }

    private void inicializarBienInterfaz() {

        Moneda moneda = modelMoneda.buscarPorCodigoISO(interfazBien.getMoneda());
        moneda = moneda == null ? new Moneda() : moneda;

        Tipo tipoBien = this.tipoPorDominioValor(Constantes.DOMINIO_BIEN, interfazBien.getTipo());
        tipoBien = tipoBien == null ? new Tipo() : tipoBien;

        Tipo tipoOrigen = this.tipoPorDominioValor(Constantes.DOMINIO_ORIGEN, interfazBien.getOrigen());
        tipoOrigen = tipoOrigen == null ? new Tipo() : tipoOrigen;

        Proveedor proveedor = modelProveedor.buscarPorCedula(interfazBien.getProveedor());
        proveedor = proveedor == null ? new Proveedor() : proveedor;

        //Estado del bien
        Estado estado = estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE);

        //Se busca la identificacion del bien en el sistema
        Identificacion identificacion = null;
        if (interfazBien.getIdentificacionBien() != null && interfazBien.getIdentificacionBien().length() > 0) {
            
            //Se busca la identificacion
            identificacion = modelIdentificacion.buscarPorIdentificacion(interfazBien.getIdentificacionBien());
            Estado estadoDispo = this.estadoPorDominioValor(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_DISPONIBLE);
            
            //Si no se encuentra disponible se coloca en null
            if (identificacion != null && !identificacion.getEstado().getValor().equals(estadoDispo.getValor())) {
                identificacion = null;
            }            
        }

        //Se busca la unidad ejecutora enviada y si no se debe solicitar por pantalla
        UnidadEjecutora unidadBien = null;
        if (interfazBien.getUnidadEjecutora() != null && interfazBien.getUnidadEjecutora() > 0L) {
            unidadBien = unidadEjecutoraModel.buscarPorId(interfazBien.getUnidadEjecutora());
        }
        unidadBien = unidadBien == null ? new UnidadEjecutora() : unidadBien;

        //Se inicializa el command
        command = new BienCommand(interfazBien, unidadBien, tipoBien, tipoOrigen, estado, proveedor, moneda, identificacion);
        inicializarDatos();
        this.bienRegistrado = false;
        cargarCombos();

        //Se buscan las unidades
        List<UnidadEjecutora> unidades = unidadEjecutoraModel.listar();
        if (!unidades.isEmpty()) {
            itemsUnidades = new ArrayList<SelectItem>();
            for (UnidadEjecutora item : unidades) {
                itemsUnidades.add(new SelectItem(item.getId(), item.getDescripcion()));
                command.getItemCommand().getItemsUnidad().put(item.getId(), item);
            }
        }

        //Se cargan las caracteristicas        
        BienCaracteristica bienCaracteristica;
        if (interfazBien.getMarca() != null && interfazBien.getMarca().length() > 0) {
            bienCaracteristica = new BienCaracteristica();
            bienCaracteristica.setTipo(this.tipoPorDominioValor(Constantes.DOMINIO_CARACTERISTICA, Constantes.TIPO_CARACTERISTICA_MARCA));
            bienCaracteristica.setDetalle(interfazBien.getMarca());
            bienCaracteristica.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO));
            command.getCaracteristicas().add(bienCaracteristica);
        }
        if (interfazBien.getModelo() != null && interfazBien.getModelo().length() > 0) {
            bienCaracteristica = new BienCaracteristica();
            bienCaracteristica.setTipo(this.tipoPorDominioValor(Constantes.DOMINIO_CARACTERISTICA, Constantes.TIPO_CARACTERISTICA_MODELO));
            bienCaracteristica.setDetalle(interfazBien.getModelo());
            bienCaracteristica.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO));
            command.getCaracteristicas().add(bienCaracteristica);
        }
        if (interfazBien.getSerie() != null && interfazBien.getSerie().length() > 0) {
            bienCaracteristica = new BienCaracteristica();
            bienCaracteristica.setTipo(this.tipoPorDominioValor(Constantes.DOMINIO_CARACTERISTICA, Constantes.TIPO_CARACTERISTICA_SERIE));
            bienCaracteristica.setDetalle(interfazBien.getSerie());
            bienCaracteristica.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO));
            command.getCaracteristicas().add(bienCaracteristica);
        }
        this.caracteristicas = command.getCaracteristicas();

        //Se cargan los accesorios
        this.accesorios = new ArrayList<Accesorio>();

        Accesorio accesorio;
        List<InterfazAccesorio> accesoriosInterfaz = interfazBienModel.listarInterfazAccesorios(interfazBien.getIdentificacionOrigen(), interfazBien.getIdOrigenTecnico());
        for (InterfazAccesorio interfazAccesorio : accesoriosInterfaz) {
            accesorio = new Accesorio();
            accesorio.setDetalle(interfazAccesorio.getDescripcion());
            accesorio.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO));
            this.accesorios.add(accesorio);
        }

        //Se cargan los adjuntos
        Adjunto adjunto;
        Tipo tipoAdjunto = this.tipoPorDominioValor(Constantes.DOMINIO_ADJUNTO, Constantes.TIPO_ADJUNTO_BIEN);
        List<InterfazAdjunto> adjuntosInterfaz = interfazBienModel.listarInterfazAdjuntos(interfazBien.getIdentificacionOrigen(), interfazBien.getIdOrigenTecnico());
        for (InterfazAdjunto interfazAdjunto : adjuntosInterfaz) {
            adjunto = new Adjunto();
            adjunto.setNombre(interfazAdjunto.getDescripcion());
            adjunto.setTipo(tipoAdjunto);
            adjunto.setUrl(interfazAdjunto.getUrl());
            adjunto.setDetalle(interfazAdjunto.getDescripcion());
            adjunto.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO));
            command.getAdjuntos().add(adjunto);
        }

    }

    public void guardarBienInterfaz() {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        UIInput component = new UIInput();
        Identificacion identificacion = null;
        String messageValidacion = validarForm(root, component);
        if (Constantes.OK.equals(messageValidacion)) {
            try {

                ReentrantLock reentrantLock = new ReentrantLock();
                try {

                    reentrantLock.lock();

                    //Se forma el bien
                    bien = command.getBien(null);
                    bien.setInterfazBien(interfazBien);

                    //Se determina la identificacion
                    identificacion = command.getIdentificacion();   
                    Boolean validaIdentificacion = true;
                    if (identificacion == null && interfazBien.getIdentificacionBien() != null && interfazBien.getIdentificacionBien().length() > 0) {
                        //Caso 1: Se envia la identificacion pero no se encuentraba disponible o no existe
                        //El bien se deja en estado de pre-ingreso para que el administrador le asigne la identificacion
                        bien.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_PRE_INGRESO));
                        validaIdentificacion = false;

                    }else if (identificacion == null && interfazBien.getIdentificacionBien().isEmpty()) {
                        //Caso 2: No se envia la identificacion, Se busca la siguiente identificacion disponible
                        Estado estadoDispo = this.estadoPorDominioValor(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_DISPONIBLE);
                        identificacion = modelIdentificacion.siguienteDisponible(estadoDispo, unidadEjecutora);
                        
                        if (identificacion == null) {
                            //El bien se deja en estado de pre-ingreso para que el administrador le asigne la identificacion
                            bien.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_PRE_INGRESO));
                            validaIdentificacion = false;
                        }else{
                            bien.setIdentificacion(identificacion);
                            Estado estadoOcupado = this.estadoPorDominioValor(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_OCUPADA);
                            bien.getIdentificacion().setEstado(estadoOcupado);
                        }
                    }
                    
                    modelBien.almacenar(bien);
                    bienRegistrado = true;

                    //Se actualiza la identificacion
                    if(identificacion != null){
                        modelIdentificacion.actualizar(bien.getIdentificacion());
                    }

                    //Se crea actualiza la interfaz
                    interfazBien.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_INTERFAZ_BIEN, Constantes.ESTADO_INTERFAZ_BIEN_PROCESADO));
                    interfazBienModel.modificar(interfazBien);

                    //Se crean las caracteristicas
                    if (command.getCaracteristicas() != null) {
                        for (BienCaracteristica caracteristica : command.getCaracteristicas()) {
                            caracteristica.setBien(bien);
                            modelBienCaracteristica.almacenar(caracteristica);
                        }
                    }

                    //Se crean los accesorios
                    if (this.getAccesorios() != null) {
                        for (Accesorio accesorio : this.getAccesorios()) {
                            accesorio.setBien(bien);
                            modelAccesorio.almacenar(accesorio);
                        }
                    }

                    //Se crean los adjuntos
                    if (command.getAdjuntos() != null) {
                        for (Adjunto adjunto : command.getAdjuntos()) {
                            adjunto.setIdReferencia(bien.getId());
                            modelAdjunto.agregar(adjunto);
                        }
                    }

                    //Se retorna al listado
                    Util.navegar(vistaOrigen);
                    if (!validaIdentificacion) {
                        Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.agregarBienController.mensaje.exito.sin.identificacion"));
                    }else{
                        Mensaje.agregarInfo(Util.getEtiquetas("sigebi.error.agregarBienController.mensaje.exito"));                        
                    }
                } catch (Exception exception) {
                    if(identificacion != null){
                        //Se retorna la identificacion
                        Estado estadoDispo = this.estadoPorDominioValor(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_DISPONIBLE);
                        identificacion.setEstado(estadoDispo);
                        modelIdentificacion.actualizar(identificacion);                        
                        bien.setIdentificacion(null);
                    }
                    throw exception;
                } finally {
                    reentrantLock.unlock();
                }
            } catch (Exception exception) {
                if (exception instanceof FWExcepcion) {
                    Mensaje.agregarErrorAdvertencia(((FWExcepcion) exception).getError_para_usuario());
                } else {
                    Mensaje.agregarErrorAdvertencia(exception, Util.getEtiquetas("sigebi.error.agregarBienController.actualizar"));
                }
            }
        } else {
            component.setValid(false);
            Mensaje.agregarErrorAdvertencia(messageValidacion);
        }
    }

    /**
     * Metodo que busca y asigna al command la unidad
     *
     * @param event
     */
    public void cambiarUnidad(ValueChangeEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }

            // Se obtiene el id del tipoDonacion
            Long valor = command.getUnidadEjecutora().getIdTemporal();
            if (valor > 0) {
                command.setUnidadEjecutora(command.getItemCommand().getItemsUnidad().get(valor));
                command.getUnidadEjecutora().setIdTemporal(valor);
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.cambiarUnidad"));
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
        inicialListadoProveedores();
        //this.proveedores = modelProveedor.listar(command.getProveedorCommand().getFiltroIdentificacion(), command.getProveedorCommand().getFiltroNombre());
        this.setVisiblePanelProveedores(true);
    }

    public void limpiarProveedor() {
        command.getProveedorCommand().setProveedor(new Proveedor());
        command.getProveedorCommand().setDescripcion(new String());
    }
    public void cerrarPanelProveedores() {
        this.setVisiblePanelProveedores(false);
    }    
    private void inicialListadoProveedores(){
        command.getProveedorCommand().setPrimerRegistro(1);
        Long cantReg = modelProveedor.contar(command.getProveedorCommand().getFiltroIdentificacion()
                                           , command.getProveedorCommand().getFiltroNombre());
        command.getProveedorCommand().setCantidadRegistros(cantReg.intValue());
        listarProveedores();
    }
    
    private void listarProveedores(){
        this.proveedores = modelProveedor.listar( command.getProveedorCommand().getFiltroIdentificacion()
                                                , command.getProveedorCommand().getFiltroNombre()
                                                , command.getProveedorCommand().getPrimerRegistro()-1
                                                , command.getProveedorCommand().getCantRegistroPorPagina());
    }
    
    public void filtroProveedor(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                 pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                 pEvent.queue();
                 return;
            }
            inicialListadoProveedores();
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.filtroProveedor"));
        }
    }
     
    /**
     * Cambia la cantidad de registros por página
     *
     * @param pEvent
     */
    public void cambioRegistrosPorPaginaProvedores(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        int cantReg = Integer.parseInt(pEvent.getNewValue().toString());
        command.getProveedorCommand().setCantRegistroPorPagina(cantReg);        
        command.getProveedorCommand().setPrimerRegistro(1);
        this.inicialListadoProveedores();

    }
    
    public void irPaginaProvedores(ActionEvent pEvent)  {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            int numeroPagina = Integer.parseInt(Util.getRequestParameter("numPag"));
            command.getProveedorCommand().getPrimerRegistroPagina(numeroPagina);
            this.listarProveedores();
        }

    public void siguienteProvedores(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        //command.getProveedorCommand().getSiguientePagina();
        command.getProveedorCommand().getSiguientePagina();
        this.listarProveedores();
    }

    public void anteriorProvedores(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        command.getProveedorCommand().getPaginaAnterior();
        this.listarProveedores();
    }

    public void primeroProvedores(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        command.getProveedorCommand().setPrimerRegistro(1);
        this.listarProveedores();
    }

        public void ultimoProvedores(ActionEvent pEvent) {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            command.getProveedorCommand().getPrimerRegistroUltimaPagina();
            this.listarProveedores();
        }

    
    //</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="Identificacion">
    public void selecionarIdentificacion(ActionEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            Identificacion identificacion = (Identificacion) event.getComponent().getAttributes().get("identificacionSeleccionado");
            command.setIdentificacion(identificacion);
            this.setVisiblePanelIdentificador(false);
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.selecionarProveedor"));
        }

    }

    public void mostrarPanelIdentificacion() {
        this.identificaciones = modelIdentificacion.listar(this.estadoPorDominioValor(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_DISPONIBLE), unidadEjecutora, command.getIdentificacionCommand().getFiltroIdentificacion());
        this.setVisiblePanelIdentificador(true);
    }

    public void cerrarPanelIdentificacion() {
        this.setVisiblePanelIdentificador(false);
    }

    public void filtroIdentificacion(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            this.identificaciones = modelIdentificacion.listar(this.estadoPorDominioValor(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_DISPONIBLE), unidadEjecutora, command.getIdentificacionCommand().getFiltroIdentificacion());
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
                modelBien.cambiaEstadoBien(bien, bien.getEstado(), command.getObservacionCliente(), telefono, usuarioSIGEBI, this.tipoPorDominioValor(Constantes.DOMINIO_REGISTRO_MOVIMIENTO, Constantes.TIPO_REGISTRO_MOVIMIENTO_CAMBIO_ESTADO_BIEN));
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
            modelBien.cambiaEstadoBien(bien, bien.getEstado(), command.getObservacionCliente(), telefono, usuarioSIGEBI, this.tipoPorDominioValor(Constantes.DOMINIO_REGISTRO_MOVIMIENTO, Constantes.TIPO_REGISTRO_MOVIMIENTO_CAMBIO_ESTADO_BIEN));
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
            Nota nota = new Nota();
            nota.setDetalle(notaDetalle);
            nota.setEstado(estadoGeneralActivo);
            nota.setBien(command.getBien(bien));
            modelNota.guardar(nota);
            command.setNotas(modelNota.listar(nota.getBien()));
            notaDetalle = "";
            
            command.inicializarComplementos();
            
            Mensaje.agregarInfo(Util.getEtiquetas("sigebi.Bien.Registro.Exito"));
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.Registro"));
        }
    }

    public void eliminarNota(ActionEvent pEvent) 
    {
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
            
            command.inicializarComplementos();
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
                
                command.inicializarComplementos();
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
    public void listarCaracteristicas() {

        this.caracteristicas = modelBienCaracteristica.listarPorBien(bien);

        this.itemsCaracteristica = new ArrayList<SelectItem>();
        this.tiposCaracteristica = this.tiposPorDominio(Constantes.DOMINIO_CARACTERISTICA);
        if (!this.tiposCaracteristica.isEmpty()) {
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
    }

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

                listarCaracteristicas();
                
                command.inicializarComplementos();

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

    public SolicitudDonacion getSolicitudDonacion() {
        return solicitudDonacion;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public void setSolicitudDonacion(SolicitudDonacion solicitudDonacion) {
        this.solicitudDonacion = solicitudDonacion;
    }

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

    public List<SelectItem> getItemsUnidades() {
        return itemsUnidades;
    }

    public void setItemsUnidades(List<SelectItem> itemsUnidades) {
        this.itemsUnidades = itemsUnidades;
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

    public List<Identificacion> getIdentificaciones() {
        return identificaciones;
    }

    public void setIdentificaciones(List<Identificacion> identificaciones) {
        this.identificaciones = identificaciones;
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

    public boolean isVisiblePanelIdentificador() {
        return visiblePanelIdentificador;
    }

    public void setVisiblePanelIdentificador(boolean visiblePanelIdentificador) {
        this.visiblePanelIdentificador = visiblePanelIdentificador;
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

    public boolean isVisibleBotonActualizarIdentificacion() {
        return visibleBotonActualizarIdentificacion;
    }

    public void setVisibleBotonActualizarIdentificacion(boolean visibleBotonActualizarIdentificacion) {
        this.visibleBotonActualizarIdentificacion = visibleBotonActualizarIdentificacion;
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

    public boolean isDonacion() {
        return donacion;
    }

    public void setDonacion(boolean donacion) {
        this.donacion = donacion;
    }

    public boolean isInterfaz() {
        return interfaz;
    }

    public void setInterfaz(boolean interfaz) {
        this.interfaz = interfaz;
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

    //<editor-fold defaultstate="collapsed" desc="Tab Movimientos">
    
    @Autowired
    AgregarExclusionController exc;
    @Autowired
    SolicitudDonacionController don;
    @Autowired
    TrasladoController tra;
    
    public void consultarMovimiento(ActionEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }

            FacesContext context = FacesContext.getCurrentInstance();
            UIViewRoot root = context.getViewRoot();
            
            Solicitud verMovimiento = (Solicitud) event.getComponent().getAttributes().get("movimientoSelccionado");
            switch (verMovimiento.getDiscriminator()){
                case 1:
                    exc.verDetalle( (SolicitudExclusion) verMovimiento, Constantes.KEY_VISTA_DETALLE_BIEN );
                    break;
                case 2:
                    don.verDetalle( (SolicitudDonacion) verMovimiento, Constantes.KEY_VISTA_DETALLE_BIEN );
                    break;
                case 3:
                    tra.verDetalle( (SolicitudTraslado) verMovimiento, Constantes.KEY_VISTA_DETALLE_BIEN );
                    break;                    
            }
            
        } catch (Exception err) {

            Mensaje.agregarErrorAdvertencia(err.getCause().getMessage());
        }
    }

    
    //</editor-fold>
    
    
}
