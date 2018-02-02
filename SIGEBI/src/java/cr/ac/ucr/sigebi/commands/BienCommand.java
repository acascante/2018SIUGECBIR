/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import static com.lowagie.text.pdf.PdfFileSpecification.url;
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
import java.util.Date;
import java.util.List;

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
    
    public class NotaCommand {
        
        private Long id;
        private Bien bien;
        private String detalle;
        private Estado estado;
        
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
        private String identificacion;
        private String nombre;

        public String getIdentificacion() {
            return identificacion;
        }

        public void setIdentificacion(String identificacion) {
            this.identificacion = identificacion;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private Long id;
    private String descripcion;
    private Integer cantidad;
    private Boolean capitalizable;
    private Lote lote;
    private Categoria categoria;
    private SubCategoria subCategoria;
    private Clasificacion clasificacion;
    private SubClasificacion subClasificacion;
    private ViewResumenBien resumenBien;
    private UnidadEjecutora unidadEjecutora;
    private Proveedor proveedor;
    private Moneda moneda;
    private Double costo;
    private Date fechaAdquisicion;
    private Integer persona;
    private Date inicioGarantia;
    private Date finGarantia;
    private String descripcionGarantia;
    private Ubicacion ubicacion;
    private String descripcionUbicacion;
    private Integer referencia;
    private Identificacion identificacion;

    private Tipo tipo;
    private Tipo origen;

    private Estado estadoInterno;
    private Estado estado;

    private List<Accesorio> accesorios;
    private List<Adjunto> adjuntos;
    private List<BienCaracteristica> caracteristicas;
    private List<Nota> notas;
    
    private String observacionCliente;
    
    private String detalleNota;
    
    private String descripcionAdjunto;
    
    private AccesorioCommand accesorioCommand;
    private AdjuntoCommand adjuntoCommand;
    private CaracteristicaCommand caracteristicaCommand;
    private NotaCommand notaCommand;
    private ProveedorCommand proveedorCommand;
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public BienCommand() {
        this.lote = new Lote();
        this.categoria = new Categoria();
        this.subCategoria = new SubCategoria();
        this.clasificacion = new Clasificacion();
        this.subClasificacion = new SubClasificacion();
        this.unidadEjecutora = new UnidadEjecutora();
        this.proveedor = new Proveedor();
        this.moneda = new Moneda();
        this.ubicacion = new Ubicacion();
        this.identificacion = new Identificacion();
        this.origen = new Tipo();
        this.tipo = new Tipo();
        this.estadoInterno = new Estado();
        this.estado = new Estado();
        
        this.accesorioCommand = new AccesorioCommand();
        this.adjuntoCommand = new AdjuntoCommand();
    }

    public BienCommand(Bien bien) {
        this.lote = bien.getLote() != null ? bien.getLote() : new Lote();
        this.categoria = bien.getSubCategoria() != null && bien.getSubCategoria().getCategoria() != null ? bien.getSubCategoria().getCategoria() : new Categoria();
        this.subCategoria = bien.getSubCategoria() != null ? bien.getSubCategoria() : new SubCategoria();
        this.subClasificacion = bien.getSubClasificacion() != null ? bien.getSubClasificacion() : new SubClasificacion();
        this.clasificacion = bien.getSubClasificacion() != null && bien.getSubClasificacion().getClasificacion() != null ? bien.getSubClasificacion().getClasificacion() : new Clasificacion();
        this.unidadEjecutora = bien.getUnidadEjecutora() != null ? bien.getUnidadEjecutora() : new UnidadEjecutora();
        this.proveedor = bien.getProveedor() != null ? bien.getProveedor() : new Proveedor();
        this.moneda = bien.getMoneda() != null ? bien.getMoneda() : new Moneda();
        this.ubicacion = bien.getUbicacion() != null ? bien.getUbicacion() : new Ubicacion();
        this.identificacion = bien.getIdentificacion() != null ? bien.getIdentificacion() : new Identificacion();
        this.origen = bien.getOrigen() != null ? bien.getOrigen() : new Tipo();
        this.estadoInterno = bien.getEstadoInterno() != null ? bien.getEstadoInterno() : new Estado();
        this.estado = bien.getEstado() != null ? bien.getEstado() : new Estado();
        this.tipo =  bien.getTipo() != null ? bien.getTipo() : new Tipo();

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
        this.tipo = bien.getTipo();
        this.origen = bien.getOrigen();
        this.estadoInterno = bien.getEstadoInterno();
        this.estado = bien.getEstado();
        this.accesorios = bien.getAccesorios();
        this.caracteristicas = bien.getCaracteristicas();
        
        this.accesorioCommand = new AccesorioCommand();
        this.adjuntoCommand = new AdjuntoCommand();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public Bien getBien() {
        Bien bien = new Bien();
        bien.setId(this.id);
        bien.setDescripcion(this.descripcion);
        bien.setCantidad(this.cantidad);
        bien.setCapitalizable(this.capitalizable);
        bien.setLote(this.lote);
        bien.setSubCategoria(this.subCategoria);
        bien.setSubClasificacion(this.subClasificacion);
        bien.setResumenBien(this.resumenBien);
        bien.setUnidadEjecutora(this.unidadEjecutora);
        bien.setProveedor(this.proveedor);
        bien.setMoneda(this.moneda);
        bien.setCosto(this.costo);
        bien.setFechaAdquisicion(this.fechaAdquisicion);
        bien.setPersona(this.persona);
        bien.setInicioGarantia(this.inicioGarantia);
        bien.setFinGarantia(this.finGarantia);
        bien.setDescripcionGarantia(this.descripcionGarantia);
        bien.setUbicacion(this.ubicacion);
        bien.setDescripcionUbicacion(this.descripcionUbicacion);
        bien.setIdentificacion(this.identificacion);
        bien.setTipo(this.tipo);
        bien.setOrigen(this.origen);
        bien.setEstadoInterno(this.estadoInterno);
        bien.setEstado(this.estado);
        bien.setAccesorios(this.accesorios);
        bien.setCaracteristicas(this.caracteristicas);
        return bien;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
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

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public SubCategoria getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }

    public Clasificacion getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(Clasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }

    public SubClasificacion getSubClasificacion() {
        return subClasificacion;
    }

    public void setSubClasificacion(SubClasificacion subClasificacion) {
        this.subClasificacion = subClasificacion;
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

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
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

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
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

    public Identificacion getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(Identificacion identificacion) {
        this.identificacion = identificacion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Tipo getOrigen() {
        return origen;
    }

    public void setOrigen(Tipo origen) {
        this.origen = origen;
    }

    public Estado getEstadoInterno() {
        return estadoInterno;
    }

    public void setEstadoInterno(Estado estadoInterno) {
        this.estadoInterno = estadoInterno;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
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

    public NotaCommand getNotaCommand() {
        return notaCommand;
    }

    public void setNotaCommand(NotaCommand notaCommand) {
        this.notaCommand = notaCommand;
    }
    
    public CaracteristicaCommand getCaracteristicaCommand() {
        return caracteristicaCommand;
    }

    public void setCaracteristicaCommand(CaracteristicaCommand caracteristicaCommand) {
        this.caracteristicaCommand = caracteristicaCommand;
    }

    public ProveedorCommand getProveedorCommand() {
        return proveedorCommand;
    }

    public void setProveedorCommand(ProveedorCommand proveedorCommand) {
        this.proveedorCommand = proveedorCommand;
    }
    //</editor-fold>
}