/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

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
import cr.ac.ucr.sigebi.domain.SubCategoria;
import cr.ac.ucr.sigebi.domain.SubClasificacion;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.EstadoModel;
import cr.ac.ucr.sigebi.models.IdentificacionModel;
import cr.ac.ucr.sigebi.models.LoteModel;
import cr.ac.ucr.sigebi.models.SubCategoriaModel;
import cr.ac.ucr.sigebi.models.SubClasificacionModel;
import cr.ac.ucr.sigebi.models.TipoModel;
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
    private List<SelectItem> tiposBienOptions;
    private List<SelectItem> origenesBienOptions;
    private List<SelectItem> lotesOptions;
    private List<SelectItem> categoriasOptions;
    private List<SelectItem> clasificacionesOptions;
    private List<SelectItem> monedasOptions;
    private List<SelectItem> subClasificacionesOptions;
    private List<SelectItem> subCategoriasOptions;
    
    @Resource private BienModel bienModel;
    @Resource private ClasificacionModel clasificacionModel;
    @Resource private CategoriaModel categoriaModel;
    @Resource private EstadoModel estadoModel;
    @Resource private IdentificacionModel identificacionModel;
    @Resource private LoteModel loteModel;
    @Resource private MonedaModel monedaModel;
    @Resource private SubCategoriaModel subCategoriaModel;
    @Resource private SubClasificacionModel subClasificacionModel;
    @Resource private TipoModel tipoModel;
    
    private BienCommand command;

    private String mensajeExito;
    private String mensaje;
    
    float tipoCambioDollar = 570;
    float tipoCambioEuro = 640;
    
    private boolean enableComboSubCategorias;
    private boolean enableComboClasificaciones;
    private boolean enableComboSubClasificaciones;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Inicializa Datos">
    public AgregarBienController() {
        super();
        inicializarDatos();
    }

    @PostConstruct
    protected void inicializar() {
        cargarCombos();
    }
    
    private void inicializarDatos() {
        this.command = new BienCommand();
        this.mensajeExito = "";
        this.mensaje = "";
        
        enableComboSubCategorias = true;
        enableComboClasificaciones = true;
        enableComboSubClasificaciones = true;
    }
    
    private void inicializarDatos(Bien bien) {
        this.command = new BienCommand(bien);
    }
    
    private void cargarCombos() {
        try {
            List<Tipo> tipos = tipoModel.listarPorDominio(Constantes.DOMINIO_BIEN);
            if (!tipos.isEmpty()) {
                tiposBienOptions = new ArrayList<SelectItem>();
                for (Tipo item : tipos) {
                    tiposBienOptions.add(new SelectItem(item.getId(), item.getNombre()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                }
            }
            
            List<Tipo> tiposOrigen = tipoModel.listarPorDominio(Constantes.DOMINIO_ORIGEN);
            if (!tiposOrigen.isEmpty()) {
                origenesBienOptions = new ArrayList<SelectItem>();
                for (Tipo item : tiposOrigen) {
                    origenesBienOptions.add(new SelectItem(item.getId(), item.getNombre()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                }
            }
            
            List<Lote> lotes = loteModel.listar();
            if (!lotes.isEmpty()) {
                lotesOptions = new ArrayList<SelectItem>();
                for (Lote item : lotes) {
                    lotesOptions.add(new SelectItem(item.getId(), item.getDescripcion()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                }
            }
            
            List<Categoria> categorias = categoriaModel.listar();
            if (!categorias.isEmpty()) {
                categoriasOptions = new ArrayList<SelectItem>();
                for (Categoria item : categorias) {
                    categoriasOptions.add(new SelectItem(item.getId(), item.getDescripcion()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                }
            }
            
            List<Moneda> monedas = monedaModel.listar();
            if (!monedas.isEmpty()) {
                monedasOptions = new ArrayList<SelectItem>();
                for (Moneda item : monedas) {
                    monedasOptions.add(new SelectItem(item.getId(), item.getDescripcion()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
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
            Estado estado = estadoModel.buscarPorDominioNombre(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_DISPONIBLE);
            command.setIdentificacion(identificacionModel.siguienteDisponible(estado));
            Bien bien = command.getBien();
            bienModel.actualizar(bien);
            
            command.getIdentificacion().setEstado(estadoModel.buscarPorDominioNombre(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_OCUPADA));
            identificacionModel.actualizar(command.getIdentificacion());
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

    private void cargarSubCategorias (Categoria categoria) {
        List<SubCategoria> subCategorias = subCategoriaModel.listar(categoria.getCodigoCategoria());
        if (!subCategorias.isEmpty()) {
            subCategoriasOptions = new ArrayList<SelectItem>();
            for (SubCategoria item : subCategorias) {
                subCategoriasOptions.add(new SelectItem(item.getId(), item.getDescripcion()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
            }
            enableComboSubCategorias = true;
            cargarClasificaciones(command.getSubCategoria());
        }

    }
    
    private void cargarClasificaciones(SubCategoria subCategoria) {
        List<Clasificacion> clasificaciones = clasificacionModel.listarPorCodigoSubCategoria(subCategoria.getId().toString());
        if (!clasificaciones.isEmpty()) {
            clasificacionesOptions = new ArrayList<SelectItem>();
            for (Clasificacion item : clasificaciones) {
                clasificacionesOptions.add(new SelectItem(item.getId(), item.getNombre()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
            }
            enableComboClasificaciones = true;
            cargarSubClasificaciones(command.getClasificacion());
        }        
    }

    private void cargarSubClasificaciones (Clasificacion clasificacion) {
        List<SubClasificacion> subClasificaciones = subClasificacionModel.listar(clasificacion.getId());
        if (!subClasificaciones.isEmpty()) {
            subClasificacionesOptions = new ArrayList<SelectItem>();
            for (SubClasificacion item : subClasificaciones) {
                subClasificacionesOptions.add(new SelectItem(item.getId(), item.getNombre()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
            }
            enableComboSubClasificaciones = true;
        }
    }

    public Boolean getRenderBotonSincronizar () {
        return true;
    }
    
    public Boolean getRenderBotonRechazar () {
        return true;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public List<SelectItem> getTiposBienOptions() {
        return tiposBienOptions;
    }

    public void setTiposBienOptions(List<SelectItem> tiposBienOptions) {
        this.tiposBienOptions = tiposBienOptions;
    }

    public List<SelectItem> getOrigenesBienOptions() {
        return origenesBienOptions;
    }

    public void setOrigenesBienOptions(List<SelectItem> origenesBienOptions) {
        this.origenesBienOptions = origenesBienOptions;
    }

    public List<SelectItem> getLotesOptions() {
        return lotesOptions;
    }

    public void setLotesOptions(List<SelectItem> lotesOptions) {
        this.lotesOptions = lotesOptions;
    }

    public List<SelectItem> getCategoriasOptions() {
        return categoriasOptions;
    }

    public void setCategoriasOptions(List<SelectItem> categoriasOptions) {
        this.categoriasOptions = categoriasOptions;
    }

    public List<SelectItem> getClasificacionesOptions() {
        return clasificacionesOptions;
    }

    public void setClasificacionesOptions(List<SelectItem> clasificacionesOptions) {
        this.clasificacionesOptions = clasificacionesOptions;
    }

    public List<SelectItem> getMonedasOptions() {
        return monedasOptions;
    }

    public void setMonedasOptions(List<SelectItem> monedasOptions) {
        this.monedasOptions = monedasOptions;
    }

    public List<SelectItem> getSubClasificacionesOptions() {
        return subClasificacionesOptions;
    }

    public void setSubClasificacionesOptions(List<SelectItem> subClasificacionesOptions) {
        this.subClasificacionesOptions = subClasificacionesOptions;
    }

    public List<SelectItem> getSubCategoriasOptions() {
        return subCategoriasOptions;
    }

    public void setSubCategoriasOptions(List<SelectItem> subCategoriasOptions) {
        this.subCategoriasOptions = subCategoriasOptions;
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

    public boolean isEnableComboSubCategorias() {
        return enableComboSubCategorias;
    }

    public void setEnableComboSubCategorias(boolean enableComboSubCategorias) {
        this.enableComboSubCategorias = enableComboSubCategorias;
    }

    public boolean isEnableComboClasificaciones() {
        return enableComboClasificaciones;
    }

    public void setEnableComboClasificaciones(boolean enableComboClasificaciones) {
        this.enableComboClasificaciones = enableComboClasificaciones;
    }

    public boolean isEnableComboSubClasificaciones() {
        return enableComboSubClasificaciones;
    }

    public void setEnableComboSubClasificaciones(boolean enableComboSubClasificaciones) {
        this.enableComboSubClasificaciones = enableComboSubClasificaciones;
    }

    public boolean isAccionSincronizar() {
        return accionSincronizar;
    }

    public void setAccionSincronizar(boolean accionSincronizar) {
        this.accionSincronizar = accionSincronizar;
    }

    public boolean isAccionRechazar() {
        return accionRechazar;
    }

    public void setAccionRechazar(boolean accionRechazar) {
        this.accionRechazar = accionRechazar;
    }
//</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="Panel Observacion Sincronizar o Rechazar">
    
    boolean panelObservaVisible = false;
    boolean accionSincronizar = false;
    boolean accionRechazar = false;
    String observacionCliente = "";
    
    public void rechazarBien() {
        if (this.observacionCliente == null || this.observacionCliente.isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.rechazarBien.sin.observacion"));
        } else {
            Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;
           // bienMod.cambiaEstadoBien(this.bien, estadoModel.obtenerPorEstado(Constantes.DOMINIO_ESTADO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE), observacionCliente, telefono);
            observacionCliente = "";
            //Se oculta el panel
            this.cerrarPanelObserva();
        }
    }

    public void solicitarSincronizacion() {
        Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;
        //bienMod.cambiaEstadoBien(this.bien, estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_ESTADO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE_SINCRONIZAR), observacionCliente, telefono);
        //Se oculta el panel
        this.cerrarPanelObserva();

    }
    
    public boolean mostrarPanelObserva(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return false;
        }

        //Se presenta el panel de obervacion
        panelObservaVisible = true;
        return true;
    }

    public boolean cerrarPanelObserva() {
        panelObservaVisible = false;
        return false;
    }

    public boolean isPanelObservaVisible() {
        return panelObservaVisible;
    }

    public void setPanelObservaVisible(boolean panelObservaVisible) {
        this.panelObservaVisible = panelObservaVisible;
    }

    public String getObservacionCliente() {
        return observacionCliente;
    }

    public void setObservacionCliente(String observacionCliente) {
        this.observacionCliente = observacionCliente;
    }
    //</editor-fold>

}