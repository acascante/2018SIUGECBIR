/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.entities;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "ViewBienEntity")
@Table(name = "SIGEBI_OAF.V_SGB_BIEN")
public class ViewBienEntity extends ObjetoBase implements Serializable {

    private static final long serialVersionUID = 1L;

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @Basic(optional = false)
    @Column(name = "ID_BIEN")
    private Integer idBien;
    
    @JoinColumn(name = "ID_PLACA", referencedColumnName = "ID_IDENTIFICACION")
    @ManyToOne(fetch = FetchType.EAGER)
    private PlacasEntity idPlaca;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "CANTIDAD")
    private Integer cantidad;
    @Column(name = "CODIGO_SUB_CATEGORIA")
    private String codSubCategoria;
    @Column(name = "ID_SUB_CLASIFICACION")
    private Integer idSubClasificacion;
    @Column(name = "TIPO_BIEN")
    private Integer tipoBien;
    @Column(name = "ORIGEN")
    private Integer origen;
    @Column(name = "NUM_UNIDAD_EJEC")
    private Long numUnidadEjec;
    @Column(name = "PROVEEDOR")
    private Integer proveedor;
    @Column(name = "ID_MONEDA")
    private Integer idMoneda;
    @Column(name = "COSTO")
    private float costo;
    @Column(name = "FECHA_ADQUISICION")
    @Temporal(TemporalType.DATE)
    private Date fecAdquisicion;
    @Column(name = "ID_PERSONA")
    private Integer numPersona;
    @Column(name = "INICIO_GARANTIA")
    @Temporal(TemporalType.DATE)
    private Date inicioGarantia;
    @Column(name = "FIN_GARANTIA")
    @Temporal(TemporalType.DATE)
    private Date finGarantia;
    @Column(name = "DESCRIPCION_GARANTIA")
    private String descGarantia;
    @Column(name = "NUM_LOTE")
    private String numLote;
    @Column(name = "CAPITALIZABLE")
    private Integer capitalizable;
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(fetch = FetchType.EAGER)
    private EstadoEntity idEstado;
    @Column(name = "DET_MARCA")
    private String marca;
    @Column(name = "DET_MODELO")
    private String modelo;
    @Column(name = "DET_SERIE")
    private String serie;
    @Column(name = "ID_IDENTIFICACION")
    private String idIdentificacion;
    @Column(name = "IDENTIFICACION")
    private String identificacion;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SET's y GET's ">
    public Integer getIdBien() {
        return idBien;
    }

    public void setIdBien(Integer idBien) {
        this.idBien = idBien;
    }

    public PlacasEntity getIdPlaca() {
        return idPlaca;
    }

    public void setIdPlaca(PlacasEntity idPlaca) {
        this.idPlaca = idPlaca;
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

    public String getCodSubCategoria() {
        return codSubCategoria;
    }

    public void setCodSubCategoria(String codSubCategoria) {
        this.codSubCategoria = codSubCategoria;
    }

    public Integer getIdSubClasificacion() {
        return idSubClasificacion;
    }

    public void setIdSubClasificacion(Integer idSubClasificacion) {
        this.idSubClasificacion = idSubClasificacion;
    }

    public Integer getTipoBien() {
        return tipoBien;
    }

    public void setTipoBien(Integer tipoBien) {
        this.tipoBien = tipoBien;
    }

    public Integer getOrigen() {
        return origen;
    }

    public void setOrigen(Integer origen) {
        this.origen = origen;
    }

    public Long getNumUnidadEjec() {
        return numUnidadEjec;
    }

    public void setNumUnidadEjec(Long numUnidadEjec) {
        this.numUnidadEjec = numUnidadEjec;
    }

    public Integer getProveedor() {
        return proveedor;
    }

    public void setProveedor(Integer proveedor) {
        this.proveedor = proveedor;
    }

    public Integer getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(Integer idMoneda) {
        this.idMoneda = idMoneda;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public Date getFecAdquisicion() {
        return fecAdquisicion;
    }

    public void setFecAdquisicion(Date fecAdquisicion) {
        this.fecAdquisicion = fecAdquisicion;
    }

    public Integer getNumPersona() {
        return numPersona;
    }

    public void setNumPersona(Integer numPersona) {
        this.numPersona = numPersona;
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

    public String getDescGarantia() {
        return descGarantia;
    }

    public void setDescGarantia(String descGarantia) {
        this.descGarantia = descGarantia;
    }

    public String getNumLote() {
        return numLote;
    }

    public void setNumLote(String numLote) {
        this.numLote = numLote;
    }

    public Integer getCapitalizable() {
        return capitalizable;
    }

    public void setCapitalizable(Integer capitalizable) {
        this.capitalizable = capitalizable;
    }

    public EstadoEntity getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(EstadoEntity idEstado) {
        this.idEstado = idEstado;
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

    public String getIdIdentificacion() {
        return idIdentificacion;
    }

    public void setIdIdentificacion(String idIdentificacion) {
        this.idIdentificacion = idIdentificacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    //</editor-fold>
}
