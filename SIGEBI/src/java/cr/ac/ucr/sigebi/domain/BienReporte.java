/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.framework.vista.util.Mensaje;
import java.util.Map;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author alvaro.cascante
 */
public class BienReporte {
    
    private Long id;
    private String descripcion;
    private Integer cantidad;
    private String subCategoria;
    private String subClasificacion;
    private String tipo;
    private String origen;
    private String marca;
    private String modelo;
    private String serie;
    private String unidadEjecutora;
    private String proveedor;
    private String moneda;
    private Double costo;
    private String fechaAdquisicion;
    private String fechaInicioGarantia;
    private String fechaFinGarantia;
    private String descripcionGarantia;
    private String estado;
    private String lote;
    private String capitalizable;
    private String ubicacion;
    private String estadoInterno;
    private String identificacion;    
    private String fechaIngreso;
    private String usuarioRegistro;
    private String usuarioResponsable;
    private String estadoAsignacion;

    public BienReporte(Bien bien) {
        this.id = bien.getId();
        this.descripcion = bien.getDescripcion();
        this.cantidad = bien.getCantidad();
        this.subCategoria = bien.getSubCategoria() == null ? null : bien.getSubCategoria().getDescripcion();
        this.subClasificacion = bien.getSubClasificacion()== null ? null : bien.getSubClasificacion().getNombre();
        this.tipo = bien.getTipo()== null ? null : bien.getTipo().getNombre();
        this.origen = bien.getOrigen() == null ? null : bien.getOrigen().getNombre();
        this.marca = bien.getResumenBien() == null ? null : bien.getResumenBien().getMarca();
        this.modelo = bien.getResumenBien() == null ? null : bien.getResumenBien().getModelo();
        this.serie = bien.getResumenBien() == null ? null : bien.getResumenBien().getSerie();
        this.unidadEjecutora = bien.getUnidadEjecutora().getDescripcion();
        this.proveedor = bien.getProveedor() == null ? null : bien.getProveedor().getNombreCompleto();
        this.moneda = bien.getMoneda() == null ? null : bien.getMoneda().getDescripcion();
        this.costo = bien.getCosto();
        this.fechaAdquisicion = bien.getFechaAdquisicion() == null ? null : bien.getFechaAdquisicion().toString();
        this.fechaInicioGarantia = bien.getInicioGarantia() == null ? null : bien.getInicioGarantia().toString();
        this.fechaFinGarantia = bien.getFinGarantia() == null ? null : bien.getFinGarantia().toString();
        this.descripcionGarantia = bien.getDescripcionGarantia();
        this.estado = bien.getEstado() == null ? null : bien.getEstado().getNombre();
        this.lote = bien.getLote() == null ? null : bien.getLote().getDescripcion();
        this.capitalizable = bien.getCapitalizable()?"SI":"NO";
        this.ubicacion = bien.getUbicacion() == null ? null : bien.getUbicacion().getDescripcionCompleta();
        this.estadoInterno = bien.getEstadoInterno() == null ? null : bien.getEstadoInterno().getNombre();
        this.identificacion = bien.getIdentificacion() == null ? null : bien.getIdentificacion().getIdentificacion();
        this.fechaIngreso = bien.getFechaIngreso() == null ? null : bien.getFechaIngreso().toString();
        this.usuarioRegistro = bien.getUsuarioRegistra() == null ? null : bien.getUsuarioRegistra().getId();
        this.usuarioResponsable = bien.getUsuarioResponsable() == null ? null : bien.getUsuarioResponsable().getId();
        this.estadoAsignacion = bien.getEstadoAsignacion() == null ? null : bien.getEstadoAsignacion().getNombre();
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

    public String getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(String subCategoria) {
        this.subCategoria = subCategoria;
    }

    public String getSubClasificacion() {
        return subClasificacion;
    }

    public void setSubClasificacion(String subClasificacion) {
        this.subClasificacion = subClasificacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(String unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public String getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(String fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public String getFechaInicioGarantia() {
        return fechaInicioGarantia;
    }

    public void setFechaInicioGarantia(String fechaInicioGarantia) {
        this.fechaInicioGarantia = fechaInicioGarantia;
    }

    public String getFechaFinGarantia() {
        return fechaFinGarantia;
    }

    public void setFechaFinGarantia(String fechaFinGarantia) {
        this.fechaFinGarantia = fechaFinGarantia;
    }

    public String getDescripcionGarantia() {
        return descripcionGarantia;
    }

    public void setDescripcionGarantia(String descripcionGarantia) {
        this.descripcionGarantia = descripcionGarantia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getCapitalizable() {
        return capitalizable;
    }

    public void setCapitalizable(String capitalizable) {
        this.capitalizable = capitalizable;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstadoInterno() {
        return estadoInterno;
    }

    public void setEstadoInterno(String estadoInterno) {
        this.estadoInterno = estadoInterno;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public String getUsuarioResponsable() {
        return usuarioResponsable;
    }

    public void setUsuarioResponsable(String usuarioResponsable) {
        this.usuarioResponsable = usuarioResponsable;
    }

    public String getEstadoAsignacion() {
        return estadoAsignacion;
    }

    public void setEstadoAsignacion(String estadoAsignacion) {
        this.estadoAsignacion = estadoAsignacion;
    }
}