/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.Accesorio;
import cr.ac.ucr.sigebi.domain.Adjunto;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.BienCaracteristica;
import cr.ac.ucr.sigebi.domain.Categoria;
import cr.ac.ucr.sigebi.domain.Clasificacion;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Identificacion;
import cr.ac.ucr.sigebi.domain.Lote;
import cr.ac.ucr.sigebi.domain.Moneda;
import cr.ac.ucr.sigebi.domain.Nota;
import cr.ac.ucr.sigebi.domain.Proveedor;
import cr.ac.ucr.sigebi.domain.SubCategoria;
import cr.ac.ucr.sigebi.domain.SubClasificacion;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.Ubicacion;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.domain.ViewResumenBien;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        public AccesorioCommand() {}

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
            adjunto.setIdDocumento(this.idDocumento);
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
        
        private Long id;
        private Tipo tipo;
        private Bien bien;
        private Estado estado;
        private String detalle;

        public CaracteristicaCommand() {}

        public CaracteristicaCommand(Bien bien) {
            this.bien = bien;
        }
        public BienCaracteristica getBienCaracteristica() {
            BienCaracteristica bienCaracteristica = new BienCaracteristica();
            bienCaracteristica.setTipo(this.tipo);
            bienCaracteristica.setBien(this.bien);
            bienCaracteristica.setEstado(this.estado);
            bienCaracteristica.setDetalle(this.detalle);
            return bienCaracteristica;
        }
        
        //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Tipo getTipo() {
            return tipo;
        }

        public void setTipo(Tipo tipo) {
            this.tipo = tipo;
        }

        public Bien getBien() {
            return bien;
        }

        public void setBien(Bien bien) {
            this.bien = bien;
        }

        public Estado getEstado() {
            return estado;
        }

        public void setEstado(Estado estado) {
            this.estado = estado;
        }

        public String getDetalle() {
            return detalle;
        }

        public void setDetalle(String detalle) {
            this.detalle = detalle;
        }
        //</editor-fold>
        
    }
    
    public class ItemCommand {
        
        private Map<Long, Accesorio> itemsAccesorio;
        private Map<Long, BienCaracteristica> itemsCaracteristica;
        private Map<Long, Categoria> itemsCategoria;
        private Map<Long, Clasificacion> itemsClasificacion;
        private Map<Long, Lote> itemsLote;
        private Map<Long, Moneda> itemsMoneda;
        private Map<Long, Tipo> itemsOrigen;
        private Map<Long, SubCategoria> itemsSubCategoria;
        private Map<Long, SubClasificacion> itemsSubClasificacion;
        private Map<Long, Tipo> itemsTipo;
        private Map<Long, Ubicacion> itemsUbicacion;

        public ItemCommand() {}
        
        public ItemCommand(Bien bien) {
            this.itemsAccesorio = new HashMap<Long, Accesorio>();
            this.itemsCaracteristica = new HashMap<Long, BienCaracteristica>();
            this.itemsCategoria= new HashMap<Long, Categoria>();
            this.itemsClasificacion= new HashMap<Long, Clasificacion>();
            this.itemsLote= new HashMap<Long, Lote>();
            this.itemsMoneda= new HashMap<Long, Moneda>();
            this.itemsOrigen= new HashMap<Long, Tipo>();
            this.itemsSubCategoria= new HashMap<Long, SubCategoria>();
            this.itemsSubClasificacion= new HashMap<Long, SubClasificacion>();
            this.itemsTipo= new HashMap<Long, Tipo>();
            this.itemsUbicacion= new HashMap<Long, Ubicacion>();
            
            this.initMaps(bien);
        }
        
        private void initMaps(Bien bien) {
            if (bien.getAccesorios() != null) {
                for (Accesorio item : bien.getAccesorios()) {
                    this.itemsAccesorio.put(item.getId(), item);
                }
            }
            
            if (bien.getCaracteristicas() != null) {
                for (BienCaracteristica item : bien.getCaracteristicas()) {
                    this.itemsCaracteristica.put(item.getId(), item);
                }
            }
            
            itemsCategoria.put(bien.getSubCategoria().getCategoria().getId(), bien.getSubCategoria().getCategoria());
            itemsClasificacion.put(bien.getSubClasificacion().getClasificacion().getId(), bien.getSubClasificacion().getClasificacion());
            itemsLote.put(bien.getLote().getId(), bien.getLote());
            itemsMoneda.put(bien.getMoneda().getId(), bien.getMoneda());
            itemsOrigen.put(bien.getOrigen().getId(), bien.getOrigen());
            itemsSubCategoria.put(bien.getSubCategoria().getId(), bien.getSubCategoria());
            itemsSubClasificacion.put(bien.getSubClasificacion().getId(), bien.getSubClasificacion());
            itemsTipo.put(bien.getTipo().getId(), bien.getTipo());
            itemsUbicacion.put(bien.getUbicacion().getId(), bien.getUbicacion());
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

        public Map<Long, BienCaracteristica> getItemsCaracteristica() {
            if (this.itemsCaracteristica == null) {
                itemsCaracteristica = new HashMap<Long, BienCaracteristica>();
            }
            return itemsCaracteristica;
        }

        public void setItemsCaracteristica(Map<Long, BienCaracteristica> itemsCaracteristica) {
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
        
        private NotaCommand() {}
        
        private NotaCommand(Bien bien) {
            this.bien = bien;
        }
        
        public Nota getNota() {
            Nota nota = new Nota();
            nota.setBien(this.bien);
            nota.setDetalle(this.detalle);
            nota.setEstado(this.estado);
            return nota;
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
        //</editor-fold>
    }
    
    public class ProveedorCommand {
        
        private String filtroIdentificacion;
        private String filtroNombre;
        private String descripcion;
        
        private Proveedor proveedor;

        private ProveedorCommand() {
            //this.proveedor = new Proveedor();
        }
        
        private ProveedorCommand(Bien bien) {
            this.proveedor = bien.getProveedor();
            this.descripcion = this.proveedor.getNombreCompleto();
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
    
    public class UbicacionCommand {
        
        private Long idUbicacion;
        private String descripcion;
        
        private Ubicacion ubicacion;

        private UbicacionCommand() {
            this.ubicacion = new Ubicacion();
        }
        
        private UbicacionCommand(Bien bien) {
            this.ubicacion = bien.getUbicacion();
            this.idUbicacion = this.ubicacion.getId();
            this.descripcion = this.ubicacion.getDetalle();
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
            this.descripcion = ubicacion.getDetalle();
        }
        //</editor-fold>
    }

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private Long id;
    private String descripcion;
    private Integer cantidad;
    private Boolean capitalizable;
    private Double costo;
    private Date fechaAdquisicion;
    private Integer persona;
    private Date inicioGarantia;
    private Date finGarantia;
    private String descripcionGarantia;
    private String descripcionUbicacion;
    private Integer referencia;
    private Double montoCapitalizable;
    private String observacionCliente;
    private String detalleNota;
    private String descripcionAdjunto;

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
    
    private List<Accesorio> accesorios;
    private List<Adjunto> adjuntos;
    private List<BienCaracteristica> caracteristicas;
    private List<Nota> notas;
        
    private AccesorioCommand accesorioCommand;
    private AdjuntoCommand adjuntoCommand;
    private CaracteristicaCommand caracteristicaCommand;
    private ItemCommand itemCommand;
    private NotaCommand notaCommand;
    private ProveedorCommand proveedorCommand;
    private UbicacionCommand ubicacionCommand;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public BienCommand() {
        //this.accesorios = new ArrayList<Accesorio>();
        //this.adjuntos = new ArrayList<Adjunto>();
        //this.caracteristicas = new ArrayList<BienCaracteristica>();
        //this.notas = new ArrayList<Nota>();
        
        this.estado = new Estado();
        this.estadoInterno = new Estado();
        this.identificacion = new Identificacion();
        this.unidadEjecutora = new UnidadEjecutora();
        this.montoCapitalizable = 250000D;
        
        this.accesorioCommand = new AccesorioCommand();
        this.adjuntoCommand = new AdjuntoCommand();
        this.caracteristicaCommand = new CaracteristicaCommand();
        this.itemCommand = new ItemCommand();
        this.notaCommand = new NotaCommand();
        this.proveedorCommand = new ProveedorCommand();
        this.ubicacionCommand = new UbicacionCommand();
    }
    
    public BienCommand(UnidadEjecutora unidadEjecutora) {
        this(); // Llama al constructor sin parametros
        this.unidadEjecutora = unidadEjecutora;   
    }

    public BienCommand(Bien bien) {
        this.accesorios = bien.getAccesorios() != null ? bien.getAccesorios() : new ArrayList<Accesorio>();
        this.caracteristicas = bien.getCaracteristicas() != null ? bien.getCaracteristicas() : new ArrayList<BienCaracteristica>();
        
        this.idCategoria = bien.getSubCategoria() != null && bien.getSubCategoria().getCategoria() != null ? bien.getSubCategoria().getCategoria().getId() : -1L;
        this.idClasificacion = bien.getSubClasificacion() != null && bien.getSubClasificacion().getClasificacion() != null ? bien.getSubClasificacion().getClasificacion().getId() : -1L;
        this.idLote = bien.getLote() != null ? bien.getLote().getId() : -1L;
        this.idMoneda = bien.getMoneda() != null ? bien.getMoneda().getId() : -1L;
        this.idOrigen = bien.getOrigen() != null ? bien.getOrigen().getId() : -1L;
        this.idSubCategoria = bien.getSubCategoria() != null ? bien.getSubCategoria().getId() : -1L;
        this.idSubClasificacion = bien.getSubClasificacion() != null ? bien.getSubClasificacion().getId() : -1L;
        this.idTipo =  bien.getTipo() != null ? bien.getTipo().getId() : -1L;
        
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
        this.fechaAdquisicion = bien.getFechaAdquisicion();
        this.persona = bien.getPersona();
        this.inicioGarantia = bien.getInicioGarantia();
        this.finGarantia = bien.getFinGarantia();
        this.descripcionGarantia = bien.getDescripcionGarantia();
        this.descripcionUbicacion = bien.getDescripcionUbicacion();
        this.referencia = bien.getReferencia();
        this.montoCapitalizable = 250000D;
        
        this.accesorioCommand = new AccesorioCommand(bien);
        this.adjuntoCommand = new AdjuntoCommand();
        this.caracteristicaCommand = new CaracteristicaCommand(bien);
        this.itemCommand = new ItemCommand(bien);
        this.notaCommand = new NotaCommand(bien);
        this.proveedorCommand = new ProveedorCommand(bien);
        this.ubicacionCommand = new UbicacionCommand(bien);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public Bien getBien() {
        Bien bien = new Bien();
        
        bien.setLote(this.itemCommand.itemsLote.get(this.idLote));
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
        bien.setCapitalizable(this.esCapitalizable());
        bien.setResumenBien(this.resumenBien);
        bien.setUnidadEjecutora(this.unidadEjecutora);
        bien.setCosto(this.costo);
        bien.setFechaAdquisicion(this.fechaAdquisicion);
        bien.setPersona(this.persona);
        bien.setInicioGarantia(this.inicioGarantia);
        bien.setFinGarantia(this.finGarantia);
        bien.setDescripcionGarantia(this.descripcionGarantia);
        bien.setDescripcionUbicacion(this.descripcionUbicacion);
        bien.setIdentificacion(this.identificacion);
        bien.setEstadoInterno(this.estadoInterno);
        bien.setEstado(this.estado);
        bien.setAccesorios(this.accesorios);
        bien.setCaracteristicas(this.caracteristicas);
        
        return bien;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    public String getKeyVistaOrigen() {
        return Constantes.KEY_VISTA_ORIGEN;
    }
    
    public String getCapitalizableStr() {
        if(esCapitalizable()){
            return "SI";
        }else{
            return "NO";
        }
    }
    
    private Boolean esCapitalizable(){
        Boolean resultado = false;
        Moneda moneda = this.itemCommand.itemsMoneda.get(this.idMoneda);
        if(moneda != null && moneda.getId() != null && moneda.getId() > 0 && costo != null && costo > 0  ){
           Double costoColones = moneda.getTipoCambio() * costo;
           if(costoColones > montoCapitalizable){
               resultado = true;
               this.capitalizable = true;
           }
        }
        return resultado;
    }

    public Long getId() {
        return id;
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

    public Double getMontoCapitalizable() {
        return montoCapitalizable;
    }

    public void setMontoCapitalizable(Double montoCapitalizable) {
        this.montoCapitalizable = montoCapitalizable;
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

    public List<Accesorio> getAccesorios() {
        return accesorios;
    }

    public void setAccesorios(List<Accesorio> accesorios) {
        this.accesorios = accesorios;
    }

    public List<Adjunto> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<Adjunto> adjuntos) {
        this.adjuntos = adjuntos;
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

    public UbicacionCommand getUbicacionCommand() {
        return ubicacionCommand;
    }

    public void setUbicacionCommand(UbicacionCommand ubicacionCommand) {
        this.ubicacionCommand = ubicacionCommand;
    }
    //</editor-fold>
}