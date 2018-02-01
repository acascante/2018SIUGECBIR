/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.commands.BienCommand;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Clasificacion;
import cr.ac.ucr.sigebi.domain.Lote;
import cr.ac.ucr.sigebi.domain.Moneda;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.models.CategoriaModel;
import cr.ac.ucr.sigebi.models.ClasificacionModel;
import cr.ac.ucr.sigebi.models.MonedaModel;
import cr.ac.ucr.sigebi.domain.Categoria;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Proveedor;
import cr.ac.ucr.sigebi.domain.SubCategoria;
import cr.ac.ucr.sigebi.domain.SubClasificacion;
import cr.ac.ucr.sigebi.domain.Ubicacion;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.EstadoModel;
import cr.ac.ucr.sigebi.models.IdentificacionModel;
import cr.ac.ucr.sigebi.models.LoteModel;
import cr.ac.ucr.sigebi.models.SubCategoriaModel;
import cr.ac.ucr.sigebi.models.SubClasificacionModel;
import cr.ac.ucr.sigebi.models.TipoModel;
import cr.ac.ucr.sigebi.models.UbicacionModel;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
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
    private List<SelectItem> itemsClasificacion;
    private List<SelectItem> itemsLote;
    private List<SelectItem> itemsMoneda;
    private List<SelectItem> itemsOrigen;
    private List<SelectItem> itemsSubCategoria;
    private List<SelectItem> itemsSubClasificacion;
    private List<SelectItem> itemsTipo;
    private List<SelectItem> itemsUbicacion;
    
    @Resource private BienModel modelBien;
    @Resource private CategoriaModel modelCategoria;
    @Resource private ClasificacionModel modelClasificacion;
    @Resource private EstadoModel modelEstado;
    @Resource private IdentificacionModel modelIdentificacion;
    @Resource private LoteModel modelLote;
    @Resource private MonedaModel modelMoneda;
    @Resource private SubCategoriaModel modelSubCategoria;
    @Resource private SubClasificacionModel modelSubClasificacion;
    @Resource private TipoModel modelTipo;
    @Resource private UbicacionModel modelUbicacion;
    
    private BienCommand command;
    private Bien bien;
    
    private String mensajeExito;
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
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Inicializa Datos">
    public AgregarBienController() {
        super();
        inicializarDatos();
    }

    @PostConstruct
    protected void inicializar() {
        cargarCombos();
        
        command.setEstado(modelEstado.buscarPorDominioEstado(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE));
    }
    
    private void inicializarDatos() {
        this.command = new BienCommand();
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
    }
    
    private void inicializarDatos(Bien bien) {
        this.command = new BienCommand(bien);
    }
    
    private void cargarCombos() {
        try {
            List<Tipo> tipos = modelTipo.listarPorDominio(Constantes.DOMINIO_BIEN);
            if (!tipos.isEmpty()) {
                itemsTipo = new ArrayList<SelectItem>();
                for (Tipo item : tipos) {
                    itemsTipo.add(new SelectItem(item, item.getNombre()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                }
            }
            
            List<Tipo> tiposOrigen = modelTipo.listarPorDominio(Constantes.DOMINIO_ORIGEN);
            if (!tiposOrigen.isEmpty()) {
                itemsOrigen = new ArrayList<SelectItem>();
                for (Tipo item : tiposOrigen) {
                    itemsOrigen.add(new SelectItem(item, item.getNombre()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                }
            }
            
            List<Lote> lotes = modelLote.listar();
            if (!lotes.isEmpty()) {
                itemsLote = new ArrayList<SelectItem>();
                for (Lote item : lotes) {
                    itemsLote.add(new SelectItem(item, item.getDescripcion()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                }
            }
            
            List<Categoria> categorias = modelCategoria.listar();
            if (!categorias.isEmpty()) {
                itemsCategoria = new ArrayList<SelectItem>();
                itemsCategoria.add(new SelectItem(new Categoria(Constantes.DEFAULT_ID, Constantes.DEFAULT_COMBO_MESSAGE), Constantes.DEFAULT_COMBO_MESSAGE));
                for (Categoria item : categorias) {
                    
                    itemsCategoria.add(new SelectItem(item, item.getDescripcion()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                }
            }
            
            List<Moneda> monedas = modelMoneda.listar();
            if (!monedas.isEmpty()) {
                itemsMoneda = new ArrayList<SelectItem>();
                for (Moneda item : monedas) {
                    itemsMoneda.add(new SelectItem(item, item.getDescripcion()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                }
            }
        } catch (Exception err) {
            mensaje = err.getMessage();
        }
    }
    
    private Double getValorColones() {
        //TODO como se va a manejar el tipo de cambio?
        try {
            if (command.getMoneda().getId() == 2L) {
                return command.getCosto() * tipoCambioDollar;
            }
            if (command.getMoneda().getId() == 3L) {
                return command.getCosto() * tipoCambioEuro;
            }
        } catch (Exception err) {
            mensaje = err.getMessage();
        }
        return command.getCosto();
    }

    public String validarForm(UIViewRoot root, UIInput component) {
        if(command.getDescripcion().length() < 4){
            component = (UIInput) root.findComponent("frmDetalleNotificacion:txtDescripcion");
            return Util.getEtiquetas("sigebi.Bien.Error.IngresoDescripcion");
        }
        if (!(command.getCantidad() > 0)) {
            component = (UIInput) root.findComponent("frmDetalleNotificacion:txtDescripcion");
            return Util.getEtiquetas("sigebi.Bien.Error.Cantidad");
        }
        
        return Constantes.OK;
    }
    
    public void agregarBien() {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        UIInput component =  new UIInput();
        String messageValidacion = validarForm(root, component);
        if (Constantes.OK.equals(messageValidacion)) {
            Estado estado = modelEstado.buscarPorDominioNombre(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_DISPONIBLE);
            command.setIdentificacion(modelIdentificacion.siguienteDisponible(estado));
            Bien bien = command.getBien();
            modelBien.actualizar(bien);
            
            command.getIdentificacion().setEstado(modelEstado.buscarPorDominioNombre(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_OCUPADA));
            modelIdentificacion.actualizar(command.getIdentificacion());
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
            inicializarDatos();
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
        cargarSubCategorias(command.getCategoria());
    }

    public void cambioSubCategoria(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        cargarClasificaciones(command.getSubCategoria());
    }

    public void cambioClasificacion(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        cargarSubClasificaciones(command.getClasificacion());
    }

    private void cargarSubCategorias(Categoria categoria) {
        if (Constantes.DEFAULT_ID.equals(command.getCategoria().getId())) {
            command.setSubCategoria(new SubCategoria(Constantes.DEFAULT_ID));
            itemsSubCategoria.clear();
            this.setDisableSubCategorias(true);
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
        cargarClasificaciones(command.getSubCategoria());
    }
    
    private void cargarClasificaciones(SubCategoria subCategoria) {
        if (Constantes.DEFAULT_ID.equals(command.getSubCategoria().getId())) {
            command.setClasificacion(new Clasificacion(Constantes.DEFAULT_ID));
            itemsClasificacion.clear();
            this.setDisableClasificaciones(true);
        } else {
            List<Clasificacion> clasificaciones = modelClasificacion.listarPorSubCategoria(subCategoria);
            if (!clasificaciones.isEmpty()) {
                itemsClasificacion = new ArrayList<SelectItem>();
                for (Clasificacion item : clasificaciones) {
                    this.setDisableClasificaciones(false);
                    itemsClasificacion.add(new SelectItem(item.getId(), item.getNombre()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                }
            }
            cargarSubClasificaciones(command.getClasificacion());
        }
    }

    private void cargarSubClasificaciones(Clasificacion clasificacion) {
        if (Constantes.DEFAULT_ID.equals(command.getClasificacion().getId())) {
            command.setSubClasificacion(new SubClasificacion(Constantes.DEFAULT_ID));
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
        command.setUbicacion(new Ubicacion());
    }

    public void cerrarPanelUbicaciones() {
        this.setVisiblePanelUbicaciones(false);
    }
    
    private void cargarUbicaciones() {
        if (itemsUbicacion.isEmpty()) {
            List<Ubicacion> ubicaciones = modelUbicacion.listar(unidadEjecutora);
            if (!ubicaciones.isEmpty()) {
                itemsUbicacion = new ArrayList<SelectItem>();
                for (Ubicacion item : ubicaciones) {
                    itemsUbicacion.add(new SelectItem(item, item.getDetalle()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                }
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Proveedores">
    public void mostrarPanelProveedores() {
        this.setVisiblePanelProveedores(true);
    }

    public void limpiarProveedor() {
        command.setProveedor(new Proveedor());
    }

    public void cerrarPanelProveedores() {
        this.setVisiblePanelProveedores(false);
    }
    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public List<SelectItem> getItemsCategoria() {
        return itemsCategoria;
    }

    public void setItemsCategoria(List<SelectItem> itemsCategoria) {
        this.itemsCategoria = itemsCategoria;
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

    public Bien getBien() {
        return bien;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
    }

    public BienCommand getCommand() {
        return command;
    }

    public void setCommand(BienCommand command) {
        this.command = command;
    }

    public String getMensajeExito() {
        return mensajeExito;
    }

    public void setMensajeExito(String mensajeExito) {
        this.mensajeExito = mensajeExito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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

    public boolean isVisiblePanelObservaciones() {
        return visiblePanelObservaciones;
    }

    public void setVisiblePanelObservaciones(boolean visiblePanelObservaciones) {
        this.visiblePanelObservaciones = visiblePanelObservaciones;
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

        //Se presenta el panel de obervacion
        this.setVisiblePanelUbicaciones(true);
        return true;
    }

    public boolean cerrarPanelObservacion() {
        this.setVisiblePanelUbicaciones(false);
        return false;
    }
    //</editor-fold>
}