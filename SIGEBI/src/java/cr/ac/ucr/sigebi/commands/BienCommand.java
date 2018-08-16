/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.framework.vista.util.PaginacionOracle;
import cr.ac.ucr.sigebi.domain.Accesorio;
import cr.ac.ucr.sigebi.domain.Adjunto;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.BienCaracteristica;
import cr.ac.ucr.sigebi.domain.Categoria;
import cr.ac.ucr.sigebi.domain.Clasificacion;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Identificacion;
import cr.ac.ucr.sigebi.domain.InterfazBien;
import cr.ac.ucr.sigebi.domain.Lote;
import cr.ac.ucr.sigebi.domain.Moneda;
import cr.ac.ucr.sigebi.domain.Nota;
import cr.ac.ucr.sigebi.domain.Proveedor;
import cr.ac.ucr.sigebi.domain.Solicitud;
import cr.ac.ucr.sigebi.domain.SubCategoria;
import cr.ac.ucr.sigebi.domain.SubClasificacion;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.Ubicacion;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.domain.Usuario;
import cr.ac.ucr.sigebi.domain.ViewResumenBien;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.model.SelectItem;

/**
 *
 * @author alvaro.cascante
 */
public class BienCommand {

    public class AccesorioCommand {

        private Long id;
        private Bien bien;
        private String detalle;
        private Estado estado;
        private Accesorio itemAccesorio;

        public AccesorioCommand() {
        }

        public AccesorioCommand(Bien bien) {
            this.bien = bien;
        }

        public Accesorio getAccesorio() {
            Accesorio accesorio = new Accesorio();
            accesorio.setBien(this.bien);
            accesorio.setDetalle(this.detalle);
            accesorio.setEstado(this.estado);
            return accesorio;
        }

        //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Bien getBien() {
            return bien;
        }

        public void setBien(Bien bien) {
            this.bien = bien;
        }

        public String getDetalle() {
            return detalle;
        }

        public void setDetalle(String detalle) {
            this.detalle = detalle;
        }

        public Estado getEstado() {
            return estado;
        }

        public void setEstado(Estado estado) {
            this.estado = estado;
        }

        public Accesorio getItemAccesorio() {
            return itemAccesorio;
        }

        public void setItemAccesorio(Accesorio itemAccesorio) {
            this.itemAccesorio = itemAccesorio;
        }
        //</editor-fold>
    }

    public class AdjuntoCommand {

        private Integer id;
        private Tipo tipo;
        private Long idDocumento;
        private String detalle;
        private String url;
        private Estado estado;
        private String tipoMime;
        private float tamano;
        private String extension;
        private String nombre;
        private Long idReferencia;

        public Adjunto getAdjunto() {
            Adjunto adjunto = new Adjunto();
            adjunto.setTipo(this.tipo);
            adjunto.setIdReferencia(idReferencia);
            adjunto.setDetalle(this.detalle);
            adjunto.setUrl(this.url);
            adjunto.setEstado(this.estado);
            adjunto.setTipoMime(this.tipoMime);
            adjunto.setTamano(this.tamano);
            adjunto.setExtension(this.extension);
            adjunto.setNombre(this.nombre);
            adjunto.setIdReferencia(this.idReferencia);
            return adjunto;
        }

        //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Tipo getTipo() {
            return tipo;
        }

        public void setTipo(Tipo tipo) {
            this.tipo = tipo;
        }

        public Long getIdDocumento() {
            return idDocumento;
        }

        public void setIdDocumento(Long idDocumento) {
            this.idDocumento = idDocumento;
        }

        public String getDetalle() {
            return detalle;
        }

        public void setDetalle(String detalle) {
            this.detalle = detalle;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Estado getEstado() {
            return estado;
        }

        public void setEstado(Estado estado) {
            this.estado = estado;
        }

        public String getTipoMime() {
            return tipoMime;
        }

        public void setTipoMime(String tipoMime) {
            this.tipoMime = tipoMime;
        }

        public float getTamano() {
            return tamano;
        }

        public void setTamano(float tamano) {
            this.tamano = tamano;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public Long getIdReferencia() {
            return idReferencia;
        }

        public void setIdReferencia(Long idReferencia) {
            this.idReferencia = idReferencia;
        }
        //</editor-fold>    
    }

    public class CaracteristicaCommand {

        private BienCaracteristica caracteristica;
        private Long idTipo;
        private String detalleModificar;

        public CaracteristicaCommand() {
            this.caracteristica = new BienCaracteristica();
        }

        public CaracteristicaCommand(BienCaracteristica bienCaracteristica) {
            this.caracteristica = bienCaracteristica;
        }

        public String getDetalleModificar() {
            return detalleModificar;
        }

        public void setDetalleModificar(String detalleModificar) {
            this.detalleModificar = detalleModificar;
        }

        public BienCaracteristica getCaracteristica() {
            return caracteristica;
        }

        public void setCaracteristica(BienCaracteristica caracteristica) {
            this.caracteristica = caracteristica;
        }

        public Long getIdTipo() {
            return idTipo;
        }

        public void setIdTipo(Long idTipo) {
            this.idTipo = idTipo;
        }
        //</editor-fold>

    }

    public class ItemCommand {

        private Map<Long, Accesorio> itemsAccesorio;
        private Map<Long, Tipo> itemsCaracteristica;
        private Map<Long, Categoria> itemsCategoria;
        private Map<Long, Clasificacion> itemsClasificacion;
        private Map<Long, SubCategoria> itemsSubCategoria;
        private Map<Long, SubClasificacion> itemsSubClasificacion;
        private Map<Long, Lote> itemsLote;
        private Map<Long, Moneda> itemsMoneda;
        private Map<Long, UnidadEjecutora> itemsUnidad;
        private Map<Long, Tipo> itemsOrigen;
        private Map<Long, Tipo> itemsTipo;
        private Map<Long, Ubicacion> itemsUbicacion;

        public ItemCommand() {
        }
        
        public ItemCommand(Moneda moneda, Tipo origen, Tipo tipo) {
            this.itemsAccesorio = new HashMap<Long, Accesorio>();
            this.itemsCaracteristica = new HashMap<Long, Tipo>();
            this.itemsCategoria = new HashMap<Long, Categoria>();
            this.itemsClasificacion = new HashMap<Long, Clasificacion>();
            this.itemsLote = new HashMap<Long, Lote>();
            this.itemsMoneda = new HashMap<Long, Moneda>();
            this.itemsUnidad = new HashMap<Long, UnidadEjecutora>();
            this.itemsOrigen = new HashMap<Long, Tipo>();
            this.itemsSubCategoria = new HashMap<Long, SubCategoria>();
            this.itemsSubClasificacion = new HashMap<Long, SubClasificacion>();
            this.itemsTipo = new HashMap<Long, Tipo>();
            this.itemsUbicacion = new HashMap<Long, Ubicacion>();
            
            itemsMoneda.put(moneda.getId(), moneda);
            itemsOrigen.put(origen.getId(), origen);
            itemsTipo.put(tipo.getId(), tipo);
        }
        
        public ItemCommand(Bien bien) {
            this.itemsAccesorio = new HashMap<Long, Accesorio>();
            this.itemsCaracteristica = new HashMap<Long, Tipo>();
            this.itemsCategoria = new HashMap<Long, Categoria>();
            this.itemsClasificacion = new HashMap<Long, Clasificacion>();
            this.itemsLote = new HashMap<Long, Lote>();
            this.itemsMoneda = new HashMap<Long, Moneda>();
            this.itemsUnidad = new HashMap<Long, UnidadEjecutora>();
            this.itemsOrigen = new HashMap<Long, Tipo>();
            this.itemsSubCategoria = new HashMap<Long, SubCategoria>();
            this.itemsSubClasificacion = new HashMap<Long, SubClasificacion>();
            this.itemsTipo = new HashMap<Long, Tipo>();
            this.itemsUbicacion = new HashMap<Long, Ubicacion>();

            this.initMaps(bien);
        }

        private void initMaps(Bien bien) {
            if (bien.getAccesorios() != null) {
                for (Accesorio item : bien.getAccesorios()) {
                    this.itemsAccesorio.put(item.getId(), item);
                }
            }

            itemsCategoria.put(bien.getSubCategoria().getCategoria().getId(), bien.getSubCategoria().getCategoria());
            itemsClasificacion.put(bien.getSubClasificacion().getClasificacion().getId(), bien.getSubClasificacion().getClasificacion());
            itemsMoneda.put(bien.getMoneda().getId(), bien.getMoneda());
            itemsOrigen.put(bien.getOrigen().getId(), bien.getOrigen());
            itemsSubCategoria.put(bien.getSubCategoria().getId(), bien.getSubCategoria());
            itemsSubClasificacion.put(bien.getSubClasificacion().getId(), bien.getSubClasificacion());
            itemsTipo.put(bien.getTipo().getId(), bien.getTipo());
            itemsUbicacion.put(bien.getUbicacion().getId(), bien.getUbicacion());
            if (bien.getLote() != null) {
                itemsLote.put(bien.getLote().getId(), bien.getLote());
            }
        }

        //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
        public Map<Long, Accesorio> getItemsAccesorio() {
            if (this.itemsAccesorio == null) {
                itemsAccesorio = new HashMap<Long, Accesorio>();
            }
            return itemsAccesorio;
        }

        public void setItemsAccesorio(Map<Long, Accesorio> itemsAccesorio) {
            this.itemsAccesorio = itemsAccesorio;
        }

        public Map<Long, Categoria> getItemsCategoria() {
            if (this.itemsCategoria == null) {
                itemsCategoria = new HashMap<Long, Categoria>();
            }
            return itemsCategoria;
        }

        public void setItemsCategoria(Map<Long, Categoria> itemsCategoria) {
            this.itemsCategoria = itemsCategoria;
        }

        public Map<Long, Tipo> getItemsCaracteristica() {
            if (this.itemsCaracteristica == null) {
                itemsCaracteristica = new HashMap<Long, Tipo>();
            }
            return itemsCaracteristica;
        }

        public void setItemsCaracteristica(Map<Long, Tipo> itemsCaracteristica) {
            this.itemsCaracteristica = itemsCaracteristica;
        }

        public Map<Long, Clasificacion> getItemsClasificacion() {
            if (this.itemsClasificacion == null) {
                itemsClasificacion = new HashMap<Long, Clasificacion>();
            }
            return itemsClasificacion;
        }

        public void setItemsClasificacion(Map<Long, Clasificacion> itemsClasificacion) {
            this.itemsClasificacion = itemsClasificacion;
        }

        public Map<Long, Lote> getItemsLote() {
            if (this.itemsLote == null) {
                itemsLote = new HashMap<Long, Lote>();
            }
            return itemsLote;
        }

        public void setItemsLote(Map<Long, Lote> itemsLote) {
            this.itemsLote = itemsLote;
        }

        public Map<Long, Moneda> getItemsMoneda() {
            if (this.itemsMoneda == null) {
                itemsMoneda = new HashMap<Long, Moneda>();
            }
            return itemsMoneda;
        }

        public void setItemsMoneda(Map<Long, Moneda> itemsMoneda) {
            this.itemsMoneda = itemsMoneda;
        }

        public Map<Long, UnidadEjecutora> getItemsUnidad() {
            if (this.itemsUnidad == null) {
                itemsUnidad = new HashMap<Long, UnidadEjecutora>();
            }
            return itemsUnidad;
        }

        public void setItemsUnidad(Map<Long, UnidadEjecutora> itemsUnidad) {
            this.itemsUnidad = itemsUnidad;
        }
        
        public Map<Long, Tipo> getItemsOrigen() {
            if (this.itemsOrigen == null) {
                itemsOrigen = new HashMap<Long, Tipo>();
            }
            return itemsOrigen;
        }

        public void setItemsOrigen(Map<Long, Tipo> itemsOrigen) {
            this.itemsOrigen = itemsOrigen;
        }

        public Map<Long, SubCategoria> getItemsSubCategoria() {
            if (this.itemsSubCategoria == null) {
                itemsSubCategoria = new HashMap<Long, SubCategoria>();
            }
            return itemsSubCategoria;
        }

        public void setItemsSubCategoria(Map<Long, SubCategoria> itemsSubCategoria) {
            this.itemsSubCategoria = itemsSubCategoria;
        }

        public Map<Long, SubClasificacion> getItemsSubClasificacion() {
            if (this.itemsSubClasificacion == null) {
                itemsSubClasificacion = new HashMap<Long, SubClasificacion>();
            }
            return itemsSubClasificacion;
        }

        public void setItemsSubClasificacion(Map<Long, SubClasificacion> itemsSubClasificacion) {
            this.itemsSubClasificacion = itemsSubClasificacion;
        }

        public Map<Long, Tipo> getItemsTipo() {
            if (this.itemsTipo == null) {
                itemsTipo = new HashMap<Long, Tipo>();
            }
            return itemsTipo;
        }

        public void setItemsTipo(Map<Long, Tipo> itemsTipo) {
            this.itemsTipo = itemsTipo;
        }

        public Map<Long, Ubicacion> getItemsUbicacion() {
            if (this.itemsUbicacion == null) {
                itemsUbicacion = new HashMap<Long, Ubicacion>();
            }
            return itemsUbicacion;
        }

        public void setItemsUbicacion(Map<Long, Ubicacion> itemsUbicacion) {
            this.itemsUbicacion = itemsUbicacion;
        }
        //</editor-fold>

    }

    public class NotaCommand {

        private Long id;
        private Bien bien;
        private String detalle;
        private Estado estado;

        private NotaCommand() {
        }

        private NotaCommand(Bien bien) {
            this.bien = bien;
        }

        public Nota getNota() {
            Nota nota = new Nota();
            if ((this.id != null) && (this.id > 0)) {
                nota.setId(this.id);
            }
            nota.setBien(this.bien);
            nota.setDetalle(this.detalle);
            nota.setEstado(this.estado);
            return nota;
        }

//        public void resetNota(){
//            Nota = new Nota();
//        }
        
        //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Bien getBien() {
            return bien;
        }

        public void setBien(Bien bien) {
            this.bien = bien;
        }

        public String getDetalle() {
            return detalle;
        }

        public void setDetalle(String detalle) {
            this.detalle = detalle;
        }

        public Estado getEstado() {
            return estado;
        }

        public void setEstado(Estado estado) {
            this.estado = estado;
        }
        //</editor-fold>
    }

    public class ProveedorCommand extends PaginacionOracle{

        private String filtroIdentificacion;
        private String filtroNombre;
        private String descripcion;

        private Proveedor proveedor;

        
        private ProveedorCommand() {
            super();
            this.proveedor = new Proveedor();
            this.filtroIdentificacion = "";
            this.filtroNombre = "";
            
            ArrayList<SelectItem> cantPorPaginas = new ArrayList<SelectItem>();
            cantPorPaginas.add(new SelectItem(5, "5"));
            cantPorPaginas.add(new SelectItem(10, "10"));
            this.setListaRegistrosPagina(cantPorPaginas);
        }

        private ProveedorCommand(Proveedor proveedor) {
            this.proveedor = proveedor;
            this.descripcion = proveedor.getNombreCompleto();

            ArrayList<SelectItem> cantPorPaginas = new ArrayList<SelectItem>();
            cantPorPaginas.add(new SelectItem(5, "5"));
            cantPorPaginas.add(new SelectItem(10, "10"));
            this.setListaRegistrosPagina(cantPorPaginas);

        }

        //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
        public String getFiltroIdentificacion() {
            return filtroIdentificacion;
        }

        public void setFiltroIdentificacion(String filtroIdentificacion) {
            this.filtroIdentificacion = filtroIdentificacion;
        }

        public String getFiltroNombre() {
            return filtroNombre;
        }

        public void setFiltroNombre(String filtroNombre) {
            this.filtroNombre = filtroNombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public Proveedor getProveedor() {
            return proveedor;
        }

        public void setProveedor(Proveedor proveedor) {
            this.proveedor = proveedor;
            this.descripcion = proveedor.getNombreCompleto();
        }
        //</editor-fold>
    }

    public class IdentificacionCommand {

        private String filtroIdentificacion;


        private IdentificacionCommand() {
        }

        //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
        public String getFiltroIdentificacion() {
            return filtroIdentificacion;
        }

        public void setFiltroIdentificacion(String filtroIdentificacion) {
            this.filtroIdentificacion = filtroIdentificacion;
        }        
        //</editor-fold>

    }
    
    public class UbicacionCommand {

        private Long idUbicacion;
        private String descripcion;

        private Ubicacion ubicacion;

        private UbicacionCommand() {
            this.ubicacion = new Ubicacion();
        }

        private UbicacionCommand(Ubicacion ubicacion) {
            this.ubicacion = ubicacion;
            this.idUbicacion = ubicacion.getId();
            this.descripcion = ubicacion.getDescripcionCompleta();
        }

        //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
        public Long getIdUbicacion() {
            return idUbicacion;
        }

        public void setIdUbicacion(Long idUbicacion) {
            this.idUbicacion = idUbicacion;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public Ubicacion getUbicacion() {
            return ubicacion;
        }

        public void setUbicacion(Ubicacion ubicacion) {
            this.ubicacion = ubicacion;
            this.descripcion = ubicacion.getDescripcionCompleta();
        }
        //</editor-fold>
    }

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private Long id;
    private String descripcion;
    private Integer cantidad;
    private Boolean capitalizable;
    private Double costo;
    private String numFactura;
    private String numOrden;
    private Date fechaAdquisicion;
    private Integer persona;
    private Date inicioGarantia;
    private Date finGarantia;
    private String descripcionGarantia;
    private String descripcionUbicacion;
    private Integer referencia;
    private String observacionCliente;
    private String detalleNota;
    private String descripcionAdjunto;
    private String capitalizableStr; 
    private Date fechaIngreso;
    private Usuario usuarioRegistra; 
    
    private Long idCategoria;
    private Long idClasificacion;
    private Long idLote;
    private Long idMoneda;
    private Long idOrigen;
    private Long idSubCategoria;
    private Long idSubClasificacion;
    private Long idTipo;

    private Estado estado;
    private Estado estadoInterno;
    private Identificacion identificacion;
    private ViewResumenBien resumenBien;
    private UnidadEjecutora unidadEjecutora;

    private List<Adjunto> adjuntos;
    private List<BienCaracteristica> caracteristicas;
    private List<Nota> notas;
    private List<Solicitud> movimientos;
    private Integer cantMovimientos;

    private AccesorioCommand accesorioCommand;
    private AdjuntoCommand adjuntoCommand;
    private CaracteristicaCommand caracteristicaCommand;
    private ItemCommand itemCommand;
    private NotaCommand notaCommand;
    private ProveedorCommand proveedorCommand;
    private IdentificacionCommand identificacionCommand;
    private UbicacionCommand ubicacionCommand;
    private Adjunto adjunto;
    
    private boolean cantidadActivo;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public BienCommand() {
        this.estado = new Estado();
        this.estadoInterno = new Estado();
        this.identificacion = new Identificacion();
        this.unidadEjecutora = new UnidadEjecutora();
        
        this.accesorioCommand = new AccesorioCommand();
        this.adjuntoCommand = new AdjuntoCommand();
        this.caracteristicaCommand = new CaracteristicaCommand();
        this.itemCommand = new ItemCommand();
        this.notaCommand = new NotaCommand();
        this.proveedorCommand = new ProveedorCommand();
        this.identificacionCommand = new IdentificacionCommand();
        this.ubicacionCommand = new UbicacionCommand();
        
        this.fechaIngreso = new Date();
        
        adjunto = new Adjunto();
        
        cantidadActivo = true;
        cantidad = 1;
    }

    public BienCommand(UnidadEjecutora unidadEjecutora) {
        this(); // Llama al constructor sin parametros
        this.unidadEjecutora = unidadEjecutora;
        cantidadActivo = true;
        cantidad = 1;
    }

    public BienCommand(Bien bien) {
        this.caracteristicas = bien.getCaracteristicas() != null ? bien.getCaracteristicas() : new ArrayList<BienCaracteristica>();

        this.idCategoria = bien.getSubCategoria() != null && bien.getSubCategoria().getCategoria() != null ? bien.getSubCategoria().getCategoria().getId() : -1L;
        this.idClasificacion = bien.getSubClasificacion() != null && bien.getSubClasificacion().getClasificacion() != null ? bien.getSubClasificacion().getClasificacion().getId() : -1L;
        this.idLote = bien.getLote() != null ? bien.getLote().getId() : -1L;
        
        //
        cantidadActivo = idLote < 0;
        
        this.idMoneda = bien.getMoneda() != null ? bien.getMoneda().getId() : -1L;
        this.idOrigen = bien.getOrigen() != null ? bien.getOrigen().getId() : -1L;
        this.idSubCategoria = bien.getSubCategoria() != null ? bien.getSubCategoria().getId() : -1L;
        this.idSubClasificacion = bien.getSubClasificacion() != null ? bien.getSubClasificacion().getId() : -1L;
        this.idTipo = bien.getTipo() != null ? bien.getTipo().getId() : -1L;

        this.estado = bien.getEstado() != null ? bien.getEstado() : new Estado();
        this.estadoInterno = bien.getEstadoInterno() != null ? bien.getEstadoInterno() : new Estado();
        this.identificacion = bien.getIdentificacion() != null ? bien.getIdentificacion() : new Identificacion();
        this.unidadEjecutora = bien.getUnidadEjecutora() != null ? bien.getUnidadEjecutora() : new UnidadEjecutora();

        this.id = bien.getId();
        this.descripcion = bien.getDescripcion();
        this.cantidad = bien.getCantidad();
        this.capitalizable = bien.getCapitalizable();
        this.resumenBien = bien.getResumenBien();
        this.costo = bien.getCosto();
        this.numFactura = bien.getNumFactura();
        this.numOrden = bien.getNumOrden();
        this.fechaAdquisicion = bien.getFechaAdquisicion();
        this.persona = bien.getPersona();
        this.inicioGarantia = bien.getInicioGarantia();
        this.finGarantia = bien.getFinGarantia();
        this.descripcionGarantia = bien.getDescripcionGarantia();
        this.descripcionUbicacion = bien.getDescripcionUbicacion();
        this.referencia = bien.getReferencia();

        this.accesorioCommand = new AccesorioCommand(bien);
        this.adjuntoCommand = new AdjuntoCommand();
        this.caracteristicaCommand = new CaracteristicaCommand();
        this.itemCommand = new ItemCommand(bien);
        this.notaCommand = new NotaCommand(bien);
        this.proveedorCommand = new ProveedorCommand(bien.getProveedor());
        this.ubicacionCommand = new UbicacionCommand(bien.getUbicacion());
        this.identificacionCommand = new IdentificacionCommand();

        this.fechaIngreso = bien.getFechaIngreso();
        this.usuarioRegistra = bien.getUsuarioRegistra();
        
        movimientos = new ArrayList<Solicitud>();
        
        adjunto = new Adjunto();
        this.calculaCapitalizable();

    }
    
     public BienCommand(InterfazBien interfazBien, UnidadEjecutora unidadEjecutora, Tipo tipoBien, Tipo tipoOrigen, Estado estadoBien, Proveedor proveedor, Moneda moneda, Identificacion identificacion) {
         
        this(); // Llama al constructor sin parametros
        this.itemCommand = new ItemCommand(moneda, tipoOrigen, tipoBien);
        this.descripcion = interfazBien.getDescripcion();
        this.idTipo = tipoBien.getId() != null ? tipoBien.getId() : -1L;
        this.idOrigen = tipoOrigen.getId() != null ? tipoOrigen.getId() : -1L;     
        this.estado = estadoBien;
        this.estadoInterno = estadoBien;
        this.idLote = -1L;
        cantidadActivo = idLote < 0;
        this.cantidad = interfazBien.getCantidad();

        this.idCategoria = -1L;
        this.idSubCategoria = -1L;
        this.idClasificacion = -1L;
        this.idSubClasificacion = -1L;

        this.ubicacionCommand = new UbicacionCommand();        
        this.proveedorCommand = new ProveedorCommand(proveedor);
        this.identificacionCommand = new IdentificacionCommand();
        this.persona = 0;
        this.fechaAdquisicion = interfazBien.getFechaAdquisicion();
        this.idMoneda = moneda.getId() != null ? moneda.getId() : -1L;        
        this.costo = interfazBien.getValorInicial();
        this.unidadEjecutora = unidadEjecutora;
        this.unidadEjecutora.setIdTemporal(unidadEjecutora.getId()); 

        //Caracteristicas
        this.caracteristicas = new ArrayList<BienCaracteristica>();

        //Caracteristicas
        this.adjuntos = new ArrayList<Adjunto>();
        
        //Datos garantia
        this.inicioGarantia = interfazBien.getFechaInicioGarantia();
        this.finGarantia = interfazBien.getFechaFinGarantia();
        this.descripcionGarantia = interfazBien.getDescripcionGarantia();
        this.identificacion = identificacion;
        this.calculaCapitalizable();
    }
     
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public Bien getBien(Bien bienModi) {
        
        Bien bien = bienModi == null ? new Bien(): bienModi;

        bien.setLote(this.idLote > 0 ? this.itemCommand.itemsLote.get(this.idLote) : null);
        bien.setMoneda(this.itemCommand.itemsMoneda.get(this.idMoneda));
        bien.setOrigen(this.itemCommand.itemsOrigen.get(this.idOrigen));
        bien.setSubCategoria(this.itemCommand.itemsSubCategoria.get(this.idSubCategoria));
        bien.setSubClasificacion(this.itemCommand.itemsSubClasificacion.get(this.idSubClasificacion));
        bien.setTipo(this.itemCommand.itemsTipo.get(this.idTipo));

        bien.setProveedor(this.proveedorCommand.getProveedor());
        bien.setUbicacion(this.ubicacionCommand.getUbicacion());

        bien.setId(this.id);
        bien.setDescripcion(this.descripcion);
        bien.setCantidad(this.cantidad);
        bien.setResumenBien(this.resumenBien);
        bien.setUnidadEjecutora(this.unidadEjecutora);
        bien.setCosto(this.costo);
        bien.setNumOrden(this.numOrden);
        bien.setNumFactura(this.numFactura);
        bien.setFechaAdquisicion(this.fechaAdquisicion);
        bien.setPersona(this.persona);
        bien.setInicioGarantia(this.inicioGarantia);
        bien.setFinGarantia(this.finGarantia);
        bien.setDescripcionGarantia(this.descripcionGarantia);
        bien.setDescripcionUbicacion(this.descripcionUbicacion);
        bien.setIdentificacion(this.identificacion);
        bien.setEstadoInterno(this.estadoInterno);
        bien.setEstado(this.estado);
        bien.setCaracteristicas(this.caracteristicas);
        bien.setCapitalizable(this.esCapitalizable());
        
        bien.setFechaIngreso(this.fechaIngreso);
        bien.setUsuarioRegistra(this.usuarioRegistra);
        
        return bien;
    }
    
    public void inicializarComplementos(){
        
        this.accesorioCommand = new AccesorioCommand();
        this.adjuntoCommand = new AdjuntoCommand();
        this.caracteristicaCommand = new CaracteristicaCommand();
        this.notaCommand = new NotaCommand();
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">

    public Usuario getUsuarioRegistra() {
        return usuarioRegistra;
    }

    public void setUsuarioRegistra(Usuario usuarioRegistra) {
        this.usuarioRegistra = usuarioRegistra;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getCantMovimientos() {
        cantMovimientos = movimientos.size();
        return cantMovimientos;
    }
    
    public List<Solicitud> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Solicitud> movimientos) {
        this.movimientos = movimientos;
    }
    
    public String getKeyVistaOrigen() {
        return Constantes.KEY_VISTA_ORIGEN;
    }

    public void calculaCapitalizable() {
        if (esCapitalizable()) {
            this.capitalizableStr = "SI";
        } else {
            this.capitalizableStr = "NO";
        }   
    }

    private Boolean esCapitalizable() {
        Boolean resultado = false;
        if (this.idMoneda != null && this.idMoneda > 0) {
            Moneda moneda = this.itemCommand.itemsMoneda.get(this.idMoneda);
            if (moneda != null && moneda.getId() != null && moneda.getId() > 0 && costo != null && costo > 0) {
                Double costoColones = moneda.getTipoCambio() * costo;
                if (costoColones > moneda.getMontoCapitalizable()) {
                    resultado = true;
                    this.capitalizable = true;
                }
            }
        }
        return resultado;
    }

    public String getCapitalizableSTR() {

        if (this.esCapitalizable()) {
            return "SI";
        } else {
            return "NO";
        }
    }

    public Long getId() {
        return id;
    }

    public String getCapitalizableStr() {
        return capitalizableStr;
    }

    public void setCapitalizableStr(String capitalizableStr) {
        this.capitalizableStr = capitalizableStr;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Boolean getCapitalizable() {
        return capitalizable;
    }

    public void setCapitalizable(Boolean capitalizable) {
        this.capitalizable = capitalizable;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }

    public String getNumOrden() {
        return numOrden;
    }

    public void setNumOrden(String numOrden) {
        this.numOrden = numOrden;
    }

    
    
    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(Date fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public Integer getPersona() {
        return persona;
    }

    public void setPersona(Integer persona) {
        this.persona = persona;
    }

    public Date getInicioGarantia() {
        return inicioGarantia;
    }

    public void setInicioGarantia(Date inicioGarantia) {
        this.inicioGarantia = inicioGarantia;
    }

    public Date getFinGarantia() {
        return finGarantia;
    }

    public void setFinGarantia(Date finGarantia) {
        this.finGarantia = finGarantia;
    }

    public String getDescripcionGarantia() {
        return descripcionGarantia;
    }

    public void setDescripcionGarantia(String descripcionGarantia) {
        this.descripcionGarantia = descripcionGarantia;
    }

    public String getDescripcionUbicacion() {
        return descripcionUbicacion;
    }

    public void setDescripcionUbicacion(String descripcionUbicacion) {
        this.descripcionUbicacion = descripcionUbicacion;
    }

    public Integer getReferencia() {
        return referencia;
    }

    public void setReferencia(Integer referencia) {
        this.referencia = referencia;
    }

    public String getObservacionCliente() {
        return observacionCliente;
    }

    public void setObservacionCliente(String observacionCliente) {
        this.observacionCliente = observacionCliente;
    }

    public String getDetalleNota() {
        return detalleNota;
    }

    public void setDetalleNota(String detalleNota) {
        this.detalleNota = detalleNota;
    }

    public String getDescripcionAdjunto() {
        return descripcionAdjunto;
    }

    public void setDescripcionAdjunto(String descripcionAdjunto) {
        this.descripcionAdjunto = descripcionAdjunto;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Long getIdClasificacion() {
        return idClasificacion;
    }

    public void setIdClasificacion(Long idClasificacion) {
        this.idClasificacion = idClasificacion;
    }

    public Long getIdLote() {
        return idLote;
    }

    public void setIdLote(Long idLote) {
        this.idLote = idLote;
    }

    public Long getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(Long idMoneda) {
        this.idMoneda = idMoneda;
    }

    public Long getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(Long idOrigen) {
        this.idOrigen = idOrigen;
    }

    public Long getIdSubCategoria() {
        return idSubCategoria;
    }

    public void setIdSubCategoria(Long idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
    }

    public Long getIdSubClasificacion() {
        return idSubClasificacion;
    }

    public void setIdSubClasificacion(Long idSubClasificacion) {
        this.idSubClasificacion = idSubClasificacion;
    }

    public Long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Long idTipo) {
        this.idTipo = idTipo;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Estado getEstadoInterno() {
        return estadoInterno;
    }

    public void setEstadoInterno(Estado estadoInterno) {
        this.estadoInterno = estadoInterno;
    }

    public Identificacion getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(Identificacion identificacion) {
        this.identificacion = identificacion;
    }

    public ViewResumenBien getResumenBien() {
        return resumenBien;
    }

    public void setResumenBien(ViewResumenBien resumenBien) {
        this.resumenBien = resumenBien;
    }

    public UnidadEjecutora getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(UnidadEjecutora unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }

    public List<Adjunto> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<Adjunto> adjuntos) {
        this.adjuntos = adjuntos;
    }

    public Adjunto getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(Adjunto adjunto) {
        this.adjunto = adjunto;
    }

    public List<BienCaracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(List<BienCaracteristica> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public AccesorioCommand getAccesorioCommand() {
        return accesorioCommand;
    }

    public void setAccesorioCommand(AccesorioCommand accesorioCommand) {
        this.accesorioCommand = accesorioCommand;
    }

    public AdjuntoCommand getAdjuntoCommand() {
        return adjuntoCommand;
    }

    public void setAdjuntoCommand(AdjuntoCommand adjuntoCommand) {
        this.adjuntoCommand = adjuntoCommand;
    }

    public CaracteristicaCommand getCaracteristicaCommand() {
        return caracteristicaCommand;
    }

    public void setCaracteristicaCommand(CaracteristicaCommand caracteristicaCommand) {
        this.caracteristicaCommand = caracteristicaCommand;
    }

    public ItemCommand getItemCommand() {
        return itemCommand;
    }

    public void setItemCommand(ItemCommand itemCommand) {
        this.itemCommand = itemCommand;
    }

    public NotaCommand getNotaCommand() {
        return notaCommand;
    }

    public void setNotaCommand(NotaCommand notaCommand) {
        this.notaCommand = notaCommand;
    }

    public ProveedorCommand getProveedorCommand() {
        return proveedorCommand;
    }

    public void setProveedorCommand(ProveedorCommand proveedorCommand) {
        this.proveedorCommand = proveedorCommand;
    }

    public IdentificacionCommand getIdentificacionCommand() {
        return identificacionCommand;
    }

    public void setIdentificacionCommand(IdentificacionCommand identificacionCommand) {
        this.identificacionCommand = identificacionCommand;
    }

    public UbicacionCommand getUbicacionCommand() {
        return ubicacionCommand;
    }

    public void setUbicacionCommand(UbicacionCommand ubicacionCommand) {
        this.ubicacionCommand = ubicacionCommand;
    }
    
    public boolean isCantidadActivo() {
        return cantidadActivo;
    }

    public void setCantidadActivo(boolean cantidadActivo) {
        this.cantidadActivo = cantidadActivo;
    }

    //</editor-fold>

    
}
