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
import cr.ac.ucr.sigebi.domain.Proveedor;
import cr.ac.ucr.sigebi.domain.SubCategoria;
import cr.ac.ucr.sigebi.domain.SubClasificacion;
import cr.ac.ucr.sigebi.domain.Ubicacion;
import cr.ac.ucr.sigebi.entities.NotaEntity;
import cr.ac.ucr.sigebi.models.AccesorioModel;
import cr.ac.ucr.sigebi.models.AdjuntoModel;
import cr.ac.ucr.sigebi.models.BienCaracteristicaModel;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.EstadoModel;
import cr.ac.ucr.sigebi.models.IdentificacionModel;
import cr.ac.ucr.sigebi.models.LoteModel;
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
    private List<SelectItem> tiposBienOptions;
    private List<SelectItem> origenesBienOptions;
    private List<SelectItem> lotesOptions;
    private List<SelectItem> categoriasOptions;
    private List<SelectItem> clasificacionesOptions;
    private List<SelectItem> monedasOptions;
    private List<SelectItem> subClasificacionesOptions;
    private List<SelectItem> subCategoriasOptions;

    @Resource
    private BienModel bienModel;
    @Resource
    private ClasificacionModel clasificacionModel;
    @Resource
    private CategoriaModel categoriaModel;
    @Resource
    private EstadoModel estadoModel;
    @Resource
    private IdentificacionModel identificacionModel;
    @Resource
    private LoteModel loteModel;
    @Resource
    private MonedaModel monedaModel;
    @Resource
    private SubCategoriaModel subCategoriaModel;
    @Resource
    private SubClasificacionModel subClasificacionModel;
    @Resource
    private TipoModel tipoModel;
    @Resource
    private NotaModel notaModel;
    @Resource
    private ProveedorModel provModel;
    @Resource
    private UbicacionModel ubicModel;
    @Resource
    private BienCaracteristicaModel bienCaracModel;
    @Resource
    AccesorioModel modelAccesorio;
    @Resource
    AdjuntoModel modelAdjunto;

    Bien bien;
    protected BienCommand command;
    boolean bienRegistrado;

    private String mensajeExito;
    private String mensajeError;

    float tipoCambioDollar = 570;
    float tipoCambioEuro = 640;
    private boolean enableComboSubCategorias;
    private boolean enableComboClasificaciones;
    private boolean enableComboSubClasificaciones;

    //Notas del bien
    NotaEntity nota;
    List<NotaEntity> notas;
    boolean eliminarNotaVisible;
    String notaDetalle;

    //Proveedores
    List<Proveedor> proveedores;
    String provSelccionado;
    String provId;

    // Ubicaciones
    List<SelectItem> ubicacionOptions;
    boolean ubicacionVisible;
    
    //Caracteristicas
    String mensajeCaracteristicas;
    String selectCaracteristica;
    String descCaracteristica;
    String constCaracteristicas = "CARACTERISTICA";

    // Sub Categorias
    List<SelectItem> caracteristicasOptions;
    List<BienCaracteristica> caracteristicas;
    List<Tipo> caracteristicasObjOptions;
    Map<Integer, BienCaracteristica> caracteristicasRegistradas;
    BienCaracteristica caracteristica;
    boolean modifCaracterVisible;

    //Garantia
    String garantiaMensajeError;
    String garantiaMensajeExito;
    Date garantiaFecIni;
    Date garantiaFecFin;
    String garantiaDesc;

    //Adjunto
    String mensajeAdjuntos;
    String mensajeAdjuntosExito;
    String adjuntoDescripcion;
    Adjunto adjunto;
    List<Adjunto> adjuntos;
    private String adjuntoDescargar;
    private String adjuntoMostrar;
    private String adjuntosUbicacion;
    private String adjuntoNombreDescarga;
    boolean mostrarAdjunto;
    private Tipo tipoAdjuntoDoc;
    boolean eliminarAccesorioVisible;

    //Accesorio
    String mensajeAccesExito;
    String mensajeAccesError;
    Accesorio accesorio;
    List<Accesorio> accesorios;

    //Sincronizacion
    boolean panelObservaVisible = false;
    boolean accionSincronizar = false;
    boolean accionRechazar = false;
    String observacionCliente = "";
    boolean renderBotonSincronizar;
    boolean renderBotonRechazar;

    //Proveedores
    boolean proveedoresVisible;
    String provIdentificacion;
    String provNombre;
    String mensajeProveedores;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public void setRenderBotonRechazar(boolean renderBotonRechazar) {
        this.renderBotonRechazar = renderBotonRechazar;
    }

    public boolean isRenderBotonRechazar() {
        return renderBotonRechazar;
    }

    public boolean isRenderBotonSincronizar() {
        return renderBotonSincronizar;
    }

    public void setRenderBotonSincronizar(boolean renderBotonSincronizar) {
        this.renderBotonSincronizar = renderBotonSincronizar;
    }

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

    public BienModel getBienModel() {
        return bienModel;
    }

    public void setBienModel(BienModel bienModel) {
        this.bienModel = bienModel;
    }

    public ClasificacionModel getClasificacionModel() {
        return clasificacionModel;
    }

    public void setClasificacionModel(ClasificacionModel clasificacionModel) {
        this.clasificacionModel = clasificacionModel;
    }

    public CategoriaModel getCategoriaModel() {
        return categoriaModel;
    }

    public void setCategoriaModel(CategoriaModel categoriaModel) {
        this.categoriaModel = categoriaModel;
    }

    public EstadoModel getEstadoModel() {
        return estadoModel;
    }

    public void setEstadoModel(EstadoModel estadoModel) {
        this.estadoModel = estadoModel;
    }

    public IdentificacionModel getIdentificacionModel() {
        return identificacionModel;
    }

    public void setIdentificacionModel(IdentificacionModel identificacionModel) {
        this.identificacionModel = identificacionModel;
    }

    public LoteModel getLoteModel() {
        return loteModel;
    }

    public void setLoteModel(LoteModel loteModel) {
        this.loteModel = loteModel;
    }

    public MonedaModel getMonedaModel() {
        return monedaModel;
    }

    public void setMonedaModel(MonedaModel monedaModel) {
        this.monedaModel = monedaModel;
    }

    public SubCategoriaModel getSubCategoriaModel() {
        return subCategoriaModel;
    }

    public void setSubCategoriaModel(SubCategoriaModel subCategoriaModel) {
        this.subCategoriaModel = subCategoriaModel;
    }

    public SubClasificacionModel getSubClasificacionModel() {
        return subClasificacionModel;
    }

    public void setSubClasificacionModel(SubClasificacionModel subClasificacionModel) {
        this.subClasificacionModel = subClasificacionModel;
    }

    public TipoModel getTipoModel() {
        return tipoModel;
    }

    public void setTipoModel(TipoModel tipoModel) {
        this.tipoModel = tipoModel;
    }

    public NotaModel getNotaModel() {
        return notaModel;
    }

    public void setNotaModel(NotaModel notaModel) {
        this.notaModel = notaModel;
    }

    public ProveedorModel getProvModel() {
        return provModel;
    }

    public void setProvModel(ProveedorModel provModel) {
        this.provModel = provModel;
    }

    public UbicacionModel getUbicModel() {
        return ubicModel;
    }

    public void setUbicModel(UbicacionModel ubicModel) {
        this.ubicModel = ubicModel;
    }

    public BienCaracteristicaModel getBienCaracModel() {
        return bienCaracModel;
    }

    public void setBienCaracModel(BienCaracteristicaModel bienCaracModel) {
        this.bienCaracModel = bienCaracModel;
    }

    public AccesorioModel getModelAccesorio() {
        return modelAccesorio;
    }

    public void setModelAccesorio(AccesorioModel modelAccesorio) {
        this.modelAccesorio = modelAccesorio;
    }

    public AdjuntoModel getModelAdjunto() {
        return modelAdjunto;
    }

    public void setModelAdjunto(AdjuntoModel modelAdjunto) {
        this.modelAdjunto = modelAdjunto;
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

    public boolean isBienRegistrado() {
        return bienRegistrado;
    }

    public void setBienRegistrado(boolean bienRegistrado) {
        this.bienRegistrado = bienRegistrado;
    }

    public String getMensajeExito() {
        return mensajeExito;
    }

    public void setMensajeExito(String mensajeExito) {
        this.mensajeExito = mensajeExito;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
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

    public boolean isEliminarNotaVisible() {
        return eliminarNotaVisible;
    }

    public void setEliminarNotaVisible(boolean eliminarNotaVisible) {
        this.eliminarNotaVisible = eliminarNotaVisible;
    }

    public String getNotaDetalle() {
        return notaDetalle;
    }

    public void setNotaDetalle(String notaDetalle) {
        this.notaDetalle = notaDetalle;
    }

    public List<Proveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }

    public String getProvSelccionado() {
        return provSelccionado;
    }

    public void setProvSelccionado(String provSelccionado) {
        this.provSelccionado = provSelccionado;
    }

    public String getProvId() {
        return provId;
    }

    public void setProvId(String provId) {
        this.provId = provId;
    }

    public List<SelectItem> getUbicacionOptions() {
        return ubicacionOptions;
    }

    public void setUbicacionOptions(List<SelectItem> ubicacionOptions) {
        this.ubicacionOptions = ubicacionOptions;
    }

    public boolean isUbicacionVisible() {
        return ubicacionVisible;
    }

    public void setUbicacionVisible(boolean ubicacionVisible) {
        this.ubicacionVisible = ubicacionVisible;
    }

    public String getConstCaracteristicas() {
        return constCaracteristicas;
    }

    public void setConstCaracteristicas(String constCaracteristicas) {
        this.constCaracteristicas = constCaracteristicas;
    }

    public List<Tipo> getCaracteristicasObjOptions() {
        return caracteristicasObjOptions;
    }

    public void setCaracteristicasObjOptions(List<Tipo> caracteristicasObjOptions) {
        this.caracteristicasObjOptions = caracteristicasObjOptions;
    }

    public Map<Integer, BienCaracteristica> getCaracteristicasRegistradas() {
        return caracteristicasRegistradas;
    }

    public void setCaracteristicasRegistradas(Map<Integer, BienCaracteristica> caracteristicasRegistradas) {
        this.caracteristicasRegistradas = caracteristicasRegistradas;
    }

    public String getMensajeAdjuntos() {
        return mensajeAdjuntos;
    }

    public void setMensajeAdjuntos(String mensajeAdjuntos) {
        this.mensajeAdjuntos = mensajeAdjuntos;
    }

    public String getMensajeAdjuntosExito() {
        return mensajeAdjuntosExito;
    }

    public void setMensajeAdjuntosExito(String mensajeAdjuntosExito) {
        this.mensajeAdjuntosExito = mensajeAdjuntosExito;
    }

    public String getAdjuntoDescripcion() {
        return adjuntoDescripcion;
    }

    public void setAdjuntoDescripcion(String adjuntoDescripcion) {
        this.adjuntoDescripcion = adjuntoDescripcion;
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

    public String getAdjuntoDescargar() {
        return adjuntoDescargar;
    }

    public void setAdjuntoDescargar(String adjuntoDescargar) {
        this.adjuntoDescargar = adjuntoDescargar;
    }

    public String getAdjuntoMostrar() {
        return adjuntoMostrar;
    }

    public void setAdjuntoMostrar(String adjuntoMostrar) {
        this.adjuntoMostrar = adjuntoMostrar;
    }

    public String getAdjuntosUbicacion() {
        return adjuntosUbicacion;
    }

    public void setAdjuntosUbicacion(String adjuntosUbicacion) {
        this.adjuntosUbicacion = adjuntosUbicacion;
    }

    public String getAdjuntoNombreDescarga() {
        return adjuntoNombreDescarga;
    }

    public void setAdjuntoNombreDescarga(String adjuntoNombreDescarga) {
        this.adjuntoNombreDescarga = adjuntoNombreDescarga;
    }

    public boolean isMostrarAdjunto() {
        return mostrarAdjunto;
    }

    public void setMostrarAdjunto(boolean mostrarAdjunto) {
        this.mostrarAdjunto = mostrarAdjunto;
    }

    public Tipo getTipoAdjuntoDoc() {
        return tipoAdjuntoDoc;
    }

    public void setTipoAdjuntoDoc(Tipo tipoAdjuntoDoc) {
        this.tipoAdjuntoDoc = tipoAdjuntoDoc;
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

    public Accesorio getAccesorio() {
        return accesorio;
    }

    public void setAccesorio(Accesorio accesorio) {
        this.accesorio = accesorio;
    }

    public List<Accesorio> getAccesorios() {
        return accesorios;
    }

    public void setAccesorios(List<Accesorio> accesorios) {
        this.accesorios = accesorios;
    }

    public boolean isPanelObservaVisible() {
        return panelObservaVisible;
    }

    public void setPanelObservaVisible(boolean panelObservaVisible) {
        this.panelObservaVisible = panelObservaVisible;
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

    public String getObservacionCliente() {
        return observacionCliente;
    }

    public void setObservacionCliente(String observacionCliente) {
        this.observacionCliente = observacionCliente;
    }

    public boolean isProveedoresVisible() {
        return proveedoresVisible;
    }

    public void setProveedoresVisible(boolean proveedoresVisible) {
        this.proveedoresVisible = proveedoresVisible;
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

    public String getMensajeProveedores() {
        return mensajeProveedores;
    }

    public void setMensajeProveedores(String mensajeProveedores) {
        this.mensajeProveedores = mensajeProveedores;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructuras y inicializador de datos">
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
        this.mensajeError = "";

        enableComboSubCategorias = true;
        enableComboClasificaciones = true;
        enableComboSubClasificaciones = true;
        Util.navegar(Constantes.KEY_VISTA_DETALLE_BIEN);

    }

    private void inicializarDatos(Bien bien) {
        this.command = new BienCommand(bien);
        Util.navegar(Constantes.KEY_VISTA_DETALLE_BIEN);
    }

    protected void iniciaComplementos() {

        notas = new ArrayList<NotaEntity>();
        nota = new NotaEntity();

        eliminarNotaVisible = false;
        adjuntoDescripcion = "";
        caracteristica = new BienCaracteristica();
        inicializaUbicaciones();
        inicializaAdjuntos();
        cargarCaracteristicasBien();
        cargarOpcionesCaract();

        command = new BienCommand(bien);
        bienRegistrado = true;
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
            mensajeError = err.getMessage();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Navegación del MENÚ">
    public void nuevoRegistro(ActionEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            inicializarDatos();
            iniciaComplementos();

            this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
        } catch (Exception err) {
            mensajeError = err.getMessage();
        }
    }

    public void abrirDetalle(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            bien = (Bien) pEvent.getComponent().getAttributes().get("bienSeleccionado");
            this.vistaOrigen = (String) pEvent.getComponent().getAttributes().get("vistaOrigenParam");

            inicializarDatos(bien);

        } catch (Exception err) {
            mensajeError = err.getMessage();
        }
    }

    public void regresarListado() {
        if (vistaOrigen != null) {
            Util.navegar(vistaOrigen);
        } else {
            Util.navegar("reg_manual");
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos Generales">
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
            mensajeError = err.getMessage();
        }
        return command.getCosto();
    }

    public String validarForm(UIViewRoot root, UIInput component) {
        if (command.getDescripcion().length() < 4) {
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
        UIInput component = new UIInput();
        String messageValidacion = validarForm(root, component);
        if (Constantes.OK.equals(messageValidacion)) {
            Estado estado = estadoModel.buscarPorDominioNombre(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_DISPONIBLE);
            command.setIdentificacion(identificacionModel.siguienteDisponible(estado));
            bien = command.getBien();
            bienModel.actualizar(bien);

            command.getIdentificacion().setEstado(estadoModel.buscarPorDominioNombre(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_OCUPADA));
            identificacionModel.actualizar(command.getIdentificacion());
        } else {
            component.setValid(false);
            Mensaje.agregarErrorAdvertencia(messageValidacion);
        }
    }

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

    private void cargarSubClasificaciones(Clasificacion clasificacion) {
        List<SubClasificacion> subClasificaciones = subClasificacionModel.listar(clasificacion.getId());
        if (!subClasificaciones.isEmpty()) {
            subClasificacionesOptions = new ArrayList<SelectItem>();
            for (SubClasificacion item : subClasificaciones) {
                subClasificacionesOptions.add(new SelectItem(item.getId(), item.getNombre()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
            }
            enableComboSubClasificaciones = true;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Panel Observacion Sincronizar o Rechazar">
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

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Proveedores">
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
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Ubicacion">

    public void ubicacionMostrar() {
        try {
            ubicacionVisible = true;
            command.getUbicacion().setIdTemporal(command.getUbicacion().getIdTemporal());            
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e.getMessage());
        }
    }

    public void ubicacionLimpiar() {
        try {
            command.setUbicacion(new Ubicacion());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e.getMessage());
        }
    }

    public void cerrarUbicacion() {
        try {
            ubicacionVisible = false;
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e.getMessage());
        }
    }

    public void ubicacionSelectCambio(ValueChangeEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            
            Long valor = command.getUbicacion().getIdTemporal();
            if(valor > 0){
                //Se asigna la ubicacion
                command.setUbicacion(ubicModel.buscarPorId(valor));
                command.getUbicacion().setIdTemporal(valor);
            }
        } catch (FWExcepcion err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e.getMessage());
        }
    }

    protected void inicializaUbicaciones() {
        try {
            ubicacionOptions = new ArrayList<SelectItem>();
            List<Ubicacion> ubicacionesList = ubicModel.listar(unidadEjecutora.getId());
            for (Ubicacion item : ubicacionesList) {
                ubicacionOptions.add(new SelectItem(item.getId(), item.getDetalle()));
            }            
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e.getMessage());
        }
    }

    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="Tab Notas del Activo">
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
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tab Características">
    public void guardarCaracteristica() {
        try {

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
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.cargarCaracteristica"));
        }
    }

    public void cargarCaracteristicasBien() {
        try {
            modifCaracterVisible = false;

            caracteristicas = new ArrayList<BienCaracteristica>();
            caracteristicas = bienCaracModel.listarPorBien(bien);

            caracteristica = new BienCaracteristica();

            caracteristicasRegistradas = new HashMap<Integer, BienCaracteristica>();
            for (BienCaracteristica item : caracteristicas) {
                caracteristicasRegistradas.put(item.getTipo().getId(), item);
            }

            actualizarOpcionesCaracteristicas();
            //mensajeCaracteristicas = "Debo llenar el select de características";
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Bien.Error.registrarCaracteristica"));
        }
    }

    protected void cargarOpcionesCaract() {
        caracteristicasObjOptions = tipoModel.listarPorDominio(constCaracteristicas);//FIXME

    }

    protected void actualizarOpcionesCaracteristicas() {
        caracteristicasOptions = new ArrayList<SelectItem>();
        for (Tipo item : caracteristicasObjOptions) {
            if (bien.getId() > 0) {
                if (!caracteristicasRegistradas.containsKey(item.getId())) {
                    caracteristicasOptions.add(new SelectItem("" + item.getId(), item.getNombre()));
                }
            }
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
    
    //<editor-fold defaultstate="collapsed" desc="Tab Garantías">
    public void guardarGarantia() {
        try {
            int resp = garantiaFecIni.compareTo(garantiaFecFin);
            //Validamos los datos
            if (resp == -1) {

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                String iniGarantia = df.format(garantiaFecIni);
                String finGarantia = df.format(garantiaFecFin);

//                garantiaMensajeError = bienMod.guardarGarantia(bien.getId(),
//                        iniGarantia,
//                        finGarantia,
//                        garantiaDesc);
                if (garantiaMensajeError.equals("")) {
                    garantiaMensajeExito = "Garantía guardada.";
                }

            } else {
                garantiaMensajeError = "El inicio de la garantia debe ser menor al final de la garantía";

            }

        } catch (NullPointerException e) {
            garantiaMensajeError = "Favor registrar ambas fecha con formato (DD/MM/YYYY)";
        } catch (Exception err) {
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
    public void checkFileLocation(ActionEvent event) {
        InputFile inputFile = (InputFile) event.getSource();
        FileInfo fileInfo = inputFile.getFileInfo();
        adjunto = new Adjunto();
        //file has been saved
        if (fileInfo.isSaved()) {
            // Path with uniqueFolder attribute default
            if (inputFile.getId().endsWith("2")) {
                adjunto.setUrl(fileInfo.getPhysicalPath());
                adjunto.setNombre(fileInfo.getFileName());
                adjunto.setTamano(fileInfo.getSize() / 1024); // pasar a bites 
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

    private void inicializaAdjuntos() {
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

    private void cargarAdjuntos() {
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
            adjuntoNombreDescarga = adjunto.getNombre().replace("." + adjunto.getExtension(), "");
            if ((adjunto.getExtension().toUpperCase().equals("JPG"))
                    || (adjunto.getExtension().toUpperCase().equals("PNG"))
                    || (adjunto.getExtension().toUpperCase().equals("GIF"))
                    || (adjunto.getExtension().toUpperCase().equals("JPGE"))) {
                adjuntoMostrar = adjuntoDescargar;

            } else {
                adjuntoMostrar = "imagenes/botones/descargar_SIIAGC.png";
            }
            //mensajeNota = notaModel.eliminarNota(nota);

        } catch (Exception err) {
            mensajeAdjuntos = err.getMessage();
        }

    }

    public void adjuntoEliminarCancelar() {

        adjunto = new Adjunto();
        mostrarAdjunto = false;
    }

    public void adjuntoEliminarConfirmar() {
        try {
            modelAdjunto.eliminar(adjunto);
            adjunto = new Adjunto();
            mostrarAdjunto = false;
            cargarAdjuntos();
        } catch (Exception err) {
            mensajeAdjuntos = err.getMessage();
        }
    }

    public void downloadFile() throws FileNotFoundException, IOException {
        try {

            String dir = "C:\\SIGEBI_V2\\build\\web\\upload\\";// System.getProperty("user.dir");
            mensajeAdjuntos = "current dir = " + dir;

            File file = new File(dir + adjunto.getNombre());
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

            response.setHeader("Content-Disposition", "attachment;filename=" + adjunto.getNombre());
            response.getOutputStream().write(buf);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception err) {

            mensajeAdjuntos = err.getMessage();
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

            response.setHeader("Content-Disposition", "attachment;filename=" + adjunto.getNombre());
            response.getOutputStream().write(buf);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception err) {
            mensajeAdjuntos = err.getMessage();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tab Notas del Activo">
    public void guardarAccesorio() {
        mensajeAccesExito = "";
        mensajeAccesError = "";
        try {
            if (bien.getId() < 1) {
                mensajeAccesError = "El bien no ha sido registrado.";
                return;
            }
            accesorio.setBien(bien);
            //accesorio.setEstado(1);

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
            accesorio = new Accesorio();
            mensajeAccesExito = "";
            mensajeAccesError = "";
            accesorio = (Accesorio) pEvent.getComponent().getAttributes().get("accesorioSeleccionado");
            eliminarAccesorioVisible = true;
            //mensajeNota = notaModel.eliminarNota(nota);

        } catch (Exception err) {
            mensajeAccesError = err.getMessage();
        }
    }

    public void eliminarAccesorioCancelar() {
        try {
            accesorio = new Accesorio();
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

    //</editor-fold>
}
