/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.Accesorio;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.BienCaracteristica;
import cr.ac.ucr.sigebi.domain.Categoria;
import cr.ac.ucr.sigebi.domain.Clasificacion;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Identificacion;
import cr.ac.ucr.sigebi.domain.Lote;
import cr.ac.ucr.sigebi.domain.Moneda;
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
 * @author aocc
 */
public class BienCommand {

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
    private List<BienCaracteristica> caracteristicas;
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

    }

    public BienCommand(Bien bien) {
        this.lote = bien.getLote() != null ? bien.getLote() : new Lote();
        this.categoria = new Categoria();
        this.subCategoria = bien.getSubCategoria() != null ? bien.getSubCategoria() : new SubCategoria();
        this.subClasificacion = bien.getSubClasificacion() != null ? bien.getSubClasificacion() : new SubClasificacion();
        this.clasificacion = new Clasificacion();
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
    public String getKeyVistaOrigen() {
        return Constantes.KEY_VISTA_ORIGEN;
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

    public List<BienCaracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(List<BienCaracteristica> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    //</editor-fold>
}
