/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author alvaro.cascante
 */
public class BienReporte {
    
//    <field name="descripcion" class="java.lang.String"/>
//	<field name="cantidad" class="java.lang.Integer"/>
//	<field name="subCategoria" class="java.lang.String"/>
//	<field name="subClasificacion" class="java.lang.String"/>
//	<field name="tipo" class="java.lang.String"/>
//	<field name="origen" class="java.lang.String"/>
//	<field name="marca" class="java.lang.String"/>
//	<field name="modelo" class="java.lang.String"/>
//	<field name="serie" class="java.lang.String"/>
//	<field name="unidadEjecutora" class="java.lang.String"/>
//	<field name="proveedor" class="java.lang.String"/>
//	<field name="moneda" class="java.lang.String"/>
//	<field name="costo" class="java.lang.Double"/>
//	<field name="fechaAdquisicion" class="java.lang.String"/>
//	<field name="fechaInicioGarantia" class="java.lang.String"/>
//	<field name="fechaFinGarantia" class="java.lang.String"/>
//	<field name="descripcionGarantia" class="java.lang.String"/>
//	<field name="estado" class="java.lang.String"/>
//	<field name="lote" class="java.lang.String"/>
//	<field name="capitalizable" class="java.lang.String"/>
//	<field name="ubicacion" class="java.lang.String"/>
//	<field name="estadoInterno" class="java.lang.String"/>
//	<field name="identificacion" class="java.lang.String"/>
//	<field name="fechaIngreso" class="java.lang.String"/>
//	<field name="usuarioRegistro" class="java.lang.String"/>
//	<field name="usuarioResponsable" class="java.lang.String"/>
//	<field name="estadoAsignacion" class="java.lang.String"/>
    //<editor-fold defaultstate="collapsed" desc="Atributos">
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
        this.descripcion = bien.getDescripcion();
        this.cantidad = bien.getCantidad();
        this.subCategoria = bien.getSubCategoria().getDescripcion();
        this.subClasificacion = bien.getSubClasificacion().getNombre();
        this.tipo = bien.getTipo().getNombre();
        this.origen = bien.getOrigen().getNombre();
        this.marca = bien.getResumenBien().getMarca();
        this.modelo = bien.getResumenBien().getModelo();
        this.serie = bien.getResumenBien().getSerie();
        this.unidadEjecutora = bien.getUnidadEjecutora().getDescripcion();
        this.proveedor = bien.getProveedor().getNombreCompleto();
        this.moneda = bien.getMoneda().getDescripcion();
        this.costo = bien.getCosto();
        this.fechaAdquisicion = bien.getFechaAdquisicion().toString();
        this.fechaInicioGarantia = bien.getInicioGarantia().toString();
        this.fechaFinGarantia = bien.getFinGarantia().toString();
        this.descripcionGarantia = bien.getDescripcionGarantia();
        this.estado = bien.getEstado().getNombre();
        this.lote = bien.getLote().getDescripcion();
        this.capitalizable = bien.getCapitalizable()?"SI":"NO";
        this.ubicacion = bien.getUbicacion().getDescripcionCompleta();
        this.estadoInterno = bien.getEstadoInterno().getNombre();
        this.identificacion = bien.getIdentificacion().getIdentificacion();
        this.fechaIngreso = bien.getFechaIngreso().toString();
        this.usuarioRegistro = bien.getUsuarioRegistra().getId();
        this.usuarioResponsable = bien.getUsuarioResponsable().getId();
        this.estadoAsignacion = bien.getEstadoAsignacion().getNombre();
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