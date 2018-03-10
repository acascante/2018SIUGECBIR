/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
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
 * @author jairo.cisneros
 */
@Entity(name = "InterfazBien")
@Table(name = "SIGEBI_OAF.SIGB_INTERFAZ_BIEN")
@SequenceGenerator(name = "SGB_SQ_INTERFAZ_BIEN", sequenceName = "SIGEBI_OAF.SGB_SQ_INTERFAZ_BIEN", initialValue = 1, allocationSize = 1)
public class InterfazBien extends ObjetoBase implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_INTERFAZ_BIEN")
    @Column(name = "ID_INTERFAZ_BIEN")
    private Long id;

    @Column(name = "IDENTIFICACION_ORIGEN")
    private String identificacionOrigen;

    @Column(name = "ID_ORIGEN_TECNICO")
    private Integer idOrigenTecnico;

    @Column(name = "IDENTIFICACION_BIEN")
    private String identificacionBien;

    @Column(name = "IDENTIFICACION_ALTERNATIVA")
    private String identificacionAlternativa;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "TIPO")
    private Integer tipo;

    @Column(name = "ORIGEN")
    private Integer origen;

    @Column(name = "CUENTA_CONTABLE")
    private String cuentaContable;

    @Column(name = "CUENTA_PRESUPUESTARIA")
    private String cuentaPresupuestaria;

    @Column(name = "NUM_UNIDAD_EJEC")
    private Long unidadEjecutora;

    @Column(name = "PROVEEDOR")
    private String proveedor;

    @Column(name = "TIPO_PROVEEDOR")
    private Integer tipoProveedor;

    @Column(name = "FECHA_ADQUISICION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaAdquisicion;

    @Column(name = "MONEDA")
    private String moneda;

    @Column(name = "VALOR_INICIAL")
    private Double valorInicial;

    @Column(name = "DOCUMENTO_ADQUISICION")
    private String documentoAdquisicion;

    @Column(name = "CANTIDAD")
    private Integer cantidad;

    @Column(name = "MARCA")
    private String marca;

    @Column(name = "SERIE")
    private String serie;

    @Column(name = "MODELO")
    private String modelo;

    @Column(name = "FECHA_INICIO_GARANTIA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicioGarantia;

    @Column(name = "FECHA_FIN_GARANTIA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFinGarantia;

    @Column(name = "DESCRIPCION_GARANTIA")
    private String descripcionGarantia;

    @Column(name = "OBSERVACIONES")
    private String observaciones;

    @Column(name = "CONSECUTIVO")
    private Integer consecutivo;

    @Column(name = "FECHA_MIGRACION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaMigracion;

    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne
    private Estado estado;

    @Transient
    private List<InterfazAccesorio> accesorios;

    @Transient
    private List<InterfazAdjunto> adjuntos;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public InterfazBien() {
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getIdentificacionOrigen() {
        return identificacionOrigen;
    }

    public void setIdentificacionOrigen(String identificacionOrigen) {
        this.identificacionOrigen = identificacionOrigen;
    }

    public Integer getIdOrigenTecnico() {
        return idOrigenTecnico;
    }

    public void setIdOrigenTecnico(Integer idOrigenTecnico) {
        this.idOrigenTecnico = idOrigenTecnico;
    }

    public String getIdentificacionBien() {
        return identificacionBien;
    }

    public void setIdentificacionBien(String identificacionBien) {
        this.identificacionBien = identificacionBien;
    }

    public String getIdentificacionAlternativa() {
        return identificacionAlternativa;
    }

    public void setIdentificacionAlternativa(String identificacionAlternativa) {
        this.identificacionAlternativa = identificacionAlternativa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getOrigen() {
        return origen;
    }

    public void setOrigen(Integer origen) {
        this.origen = origen;
    }

    public String getCuentaContable() {
        return cuentaContable;
    }

    public void setCuentaContable(String cuentaContable) {
        this.cuentaContable = cuentaContable;
    }

    public String getCuentaPresupuestaria() {
        return cuentaPresupuestaria;
    }

    public void setCuentaPresupuestaria(String cuentaPresupuestaria) {
        this.cuentaPresupuestaria = cuentaPresupuestaria;
    }

    public Long getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(Long unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public Integer getTipoProveedor() {
        return tipoProveedor;
    }

    public void setTipoProveedor(Integer tipoProveedor) {
        this.tipoProveedor = tipoProveedor;
    }

    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(Date fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Double getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(Double valorInicial) {
        this.valorInicial = valorInicial;
    }

    public String getDocumentoAdquisicion() {
        return documentoAdquisicion;
    }

    public void setDocumentoAdquisicion(String documentoAdquisicion) {
        this.documentoAdquisicion = documentoAdquisicion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Date getFechaInicioGarantia() {
        return fechaInicioGarantia;
    }

    public void setFechaInicioGarantia(Date fechaInicioGarantia) {
        this.fechaInicioGarantia = fechaInicioGarantia;
    }

    public Date getFechaFinGarantia() {
        return fechaFinGarantia;
    }

    public void setFechaFinGarantia(Date fechaFinGarantia) {
        this.fechaFinGarantia = fechaFinGarantia;
    }

    public String getDescripcionGarantia() {
        return descripcionGarantia;
    }

    public void setDescripcionGarantia(String descripcionGarantia) {
        this.descripcionGarantia = descripcionGarantia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Integer consecutivo) {
        this.consecutivo = consecutivo;
    }

    public Date getFechaMigracion() {
        return fechaMigracion;
    }

    public void setFechaMigracion(Date fechaMigracion) {
        this.fechaMigracion = fechaMigracion;
    }

    public List<InterfazAccesorio> getAccesorios() {
        return accesorios;
    }

    public void setAccesorios(List<InterfazAccesorio> accesorios) {
        this.accesorios = accesorios;
    }

    public List<InterfazAdjunto> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<InterfazAdjunto> adjuntos) {
        this.adjuntos = adjuntos;
    }

    //</editor-fold>

}
