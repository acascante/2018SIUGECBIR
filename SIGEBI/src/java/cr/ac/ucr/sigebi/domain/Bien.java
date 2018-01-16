/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.sigebi.entities.*;
import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "Bien")
@Table(name = "SIGEBI_OAF.SIGB_BIEN")
@SequenceGenerator(name = "SGB_SQ_BIEN",  sequenceName = "SIGEBI_OAF.SGB_SQ_BIEN", initialValue = 1, allocationSize = 1)
public class Bien extends ObjetoBase implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_BIEN")
    @Column(name = "ID_BIEN")
    private Long id;

    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "CANTIDAD")
    private Integer cantidad;
    
    @ManyToOne
    @JoinColumn(name = "CODIGO_SUB_CATEGORIA", referencedColumnName = "ID")
    private SubCategoria idSubCategoria;
    
    @ManyToOne
    @JoinColumn(name = "ID_SUB_CLASIFICACION", referencedColumnName = "ID_SUB_CLASIFICACION")
    private SubClasificacion idSubClasificacion;
    
    @ManyToOne
    @JoinColumn(name = "ID_TIPO_BIEN", referencedColumnName = "ID_TIPO")
    private Tipo idTipoBien;
    
    @ManyToOne
    @JoinColumn(name = "ID_ORIGEN", referencedColumnName = "ID_TIPO")
    private Tipo idOrigen;
    
    @ManyToOne
    @JoinColumn(name = "ID_UNIDAD_EJECUTORA", referencedColumnName = "ID")
    private UnidadEjecutora idUnidadEjecutora;
    
    @ManyToOne
    @JoinColumn(name = "ID_PROVEEDOR", referencedColumnName = "ID")
    private Proveedor idProveedor;

    @ManyToOne
    @JoinColumn(name = "ID_MONEDA", referencedColumnName = "ID")
    private Moneda idMoneda;

    @Column(name = "COSTO")
    private Double costo;
    
    @Column(name = "FECHA_ADQUISICION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecAdquisicion;
    
    @Column(name = "ID_PERSONA")//FIXME objecto persona
    private Integer numPersona;
    
    @Column(name = "INICIO_GARANTIA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date inicioGarantia;

    @Column(name = "FIN_GARANTIA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date finGarantia;

    @Column(name = "DESCRIPCION_GARANTIA")
    private String descGarantia;
    
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Estado idEstado;
    
    @Column(name = "NUMERO_LOTE")
    private String numLote;
    
    @Column(name = "CAPITALIZABLE")
    private Integer capitalizable;
    
    @ManyToOne
    @JoinColumn(name = "ID_UBICACION", referencedColumnName = "ID_UBICACION")
    private Ubicacion idUbicacion;
     
    @Column(name = "DESCRIPCION_UBICACION")
    private String descUbicacionBien;
    
    @ManyToOne
    @JoinColumn(name = "ID_ESTADO_INTERNO", referencedColumnName = "ID_ESTADO")
    private Estado idEstadoInterno;
    
    @Column(name = "REFERENCIA")
    private Integer referencia;
    
    @JoinColumn(name = "ID_IDENTIFICACION", referencedColumnName = "ID_IDENTIFICACION")
    @ManyToOne(fetch = FetchType.EAGER)
    private Identificacion idIdentificacion;

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

    public SubCategoria getIdSubCategoria() {
        return idSubCategoria;
    }

    public void setIdSubCategoria(SubCategoria idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
    }

    public SubClasificacion getIdSubClasificacion() {
        return idSubClasificacion;
    }

    public void setIdSubClasificacion(SubClasificacion idSubClasificacion) {
        this.idSubClasificacion = idSubClasificacion;
    }

    public Tipo getIdTipoBien() {
        return idTipoBien;
    }

    public void setIdTipoBien(Tipo idTipoBien) {
        this.idTipoBien = idTipoBien;
    }

    public Tipo getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(Tipo idOrigen) {
        this.idOrigen = idOrigen;
    }

    public UnidadEjecutora getIdUnidadEjecutora() {
        return idUnidadEjecutora;
    }

    public void setIdUnidadEjecutora(UnidadEjecutora idUnidadEjecutora) {
        this.idUnidadEjecutora = idUnidadEjecutora;
    }

    public Proveedor getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Proveedor idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Moneda getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(Moneda idMoneda) {
        this.idMoneda = idMoneda;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
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

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
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

    public Ubicacion getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Ubicacion idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getDescUbicacionBien() {
        return descUbicacionBien;
    }

    public void setDescUbicacionBien(String descUbicacionBien) {
        this.descUbicacionBien = descUbicacionBien;
    }

    public Estado getIdEstadoInterno() {
        return idEstadoInterno;
    }

    public void setIdEstadoInterno(Estado idEstadoInterno) {
        this.idEstadoInterno = idEstadoInterno;
    }

    public Integer getReferencia() {
        return referencia;
    }

    public void setReferencia(Integer referencia) {
        this.referencia = referencia;
    }

    public Identificacion getIdIdentificacion() {
        return idIdentificacion;
    }

    public void setIdIdentificacion(Identificacion idIdentificacion) {
        this.idIdentificacion = idIdentificacion;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 79 * hash + (this.descripcion != null ? this.descripcion.hashCode() : 0);
        hash = 79 * hash + (this.cantidad != null ? this.cantidad.hashCode() : 0);
        hash = 79 * hash + (this.idSubCategoria != null ? this.idSubCategoria.hashCode() : 0);
        hash = 79 * hash + (this.idSubClasificacion != null ? this.idSubClasificacion.hashCode() : 0);
        hash = 79 * hash + (this.idTipoBien != null ? this.idTipoBien.hashCode() : 0);
        hash = 79 * hash + (this.idOrigen != null ? this.idOrigen.hashCode() : 0);
        hash = 79 * hash + (this.idUnidadEjecutora != null ? this.idUnidadEjecutora.hashCode() : 0);
        hash = 79 * hash + (this.idProveedor != null ? this.idProveedor.hashCode() : 0);
        hash = 79 * hash + (this.idMoneda != null ? this.idMoneda.hashCode() : 0);
        hash = 79 * hash + (this.costo != null ? this.costo.hashCode() : 0);
        hash = 79 * hash + (this.fecAdquisicion != null ? this.fecAdquisicion.hashCode() : 0);
        hash = 79 * hash + (this.numPersona != null ? this.numPersona.hashCode() : 0);
        hash = 79 * hash + (this.inicioGarantia != null ? this.inicioGarantia.hashCode() : 0);
        hash = 79 * hash + (this.finGarantia != null ? this.finGarantia.hashCode() : 0);
        hash = 79 * hash + (this.descGarantia != null ? this.descGarantia.hashCode() : 0);
        hash = 79 * hash + (this.idEstado != null ? this.idEstado.hashCode() : 0);
        hash = 79 * hash + (this.numLote != null ? this.numLote.hashCode() : 0);
        hash = 79 * hash + (this.capitalizable != null ? this.capitalizable.hashCode() : 0);
        hash = 79 * hash + (this.idUbicacion != null ? this.idUbicacion.hashCode() : 0);
        hash = 79 * hash + (this.descUbicacionBien != null ? this.descUbicacionBien.hashCode() : 0);
        hash = 79 * hash + (this.idEstadoInterno != null ? this.idEstadoInterno.hashCode() : 0);
        hash = 79 * hash + (this.referencia != null ? this.referencia.hashCode() : 0);
        hash = 79 * hash + (this.idIdentificacion != null ? this.idIdentificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bien other = (Bien) obj;
        if ((this.descripcion == null) ? (other.descripcion != null) : !this.descripcion.equals(other.descripcion)) {
            return false;
        }
        if ((this.descGarantia == null) ? (other.descGarantia != null) : !this.descGarantia.equals(other.descGarantia)) {
            return false;
        }
        if ((this.numLote == null) ? (other.numLote != null) : !this.numLote.equals(other.numLote)) {
            return false;
        }
        if ((this.descUbicacionBien == null) ? (other.descUbicacionBien != null) : !this.descUbicacionBien.equals(other.descUbicacionBien)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.cantidad != other.cantidad && (this.cantidad == null || !this.cantidad.equals(other.cantidad))) {
            return false;
        }
        if (this.idSubCategoria != other.idSubCategoria && (this.idSubCategoria == null || !this.idSubCategoria.equals(other.idSubCategoria))) {
            return false;
        }
        if (this.idSubClasificacion != other.idSubClasificacion && (this.idSubClasificacion == null || !this.idSubClasificacion.equals(other.idSubClasificacion))) {
            return false;
        }
        if (this.idTipoBien != other.idTipoBien && (this.idTipoBien == null || !this.idTipoBien.equals(other.idTipoBien))) {
            return false;
        }
        if (this.idOrigen != other.idOrigen && (this.idOrigen == null || !this.idOrigen.equals(other.idOrigen))) {
            return false;
        }
        if (this.idUnidadEjecutora != other.idUnidadEjecutora && (this.idUnidadEjecutora == null || !this.idUnidadEjecutora.equals(other.idUnidadEjecutora))) {
            return false;
        }
        if (this.idProveedor != other.idProveedor && (this.idProveedor == null || !this.idProveedor.equals(other.idProveedor))) {
            return false;
        }
        if (this.idMoneda != other.idMoneda && (this.idMoneda == null || !this.idMoneda.equals(other.idMoneda))) {
            return false;
        }
        if (this.costo != other.costo && (this.costo == null || !this.costo.equals(other.costo))) {
            return false;
        }
        if (this.fecAdquisicion != other.fecAdquisicion && (this.fecAdquisicion == null || !this.fecAdquisicion.equals(other.fecAdquisicion))) {
            return false;
        }
        if (this.numPersona != other.numPersona && (this.numPersona == null || !this.numPersona.equals(other.numPersona))) {
            return false;
        }
        if (this.inicioGarantia != other.inicioGarantia && (this.inicioGarantia == null || !this.inicioGarantia.equals(other.inicioGarantia))) {
            return false;
        }
        if (this.finGarantia != other.finGarantia && (this.finGarantia == null || !this.finGarantia.equals(other.finGarantia))) {
            return false;
        }
        if (this.idEstado != other.idEstado && (this.idEstado == null || !this.idEstado.equals(other.idEstado))) {
            return false;
        }
        if (this.capitalizable != other.capitalizable && (this.capitalizable == null || !this.capitalizable.equals(other.capitalizable))) {
            return false;
        }
        if (this.idUbicacion != other.idUbicacion && (this.idUbicacion == null || !this.idUbicacion.equals(other.idUbicacion))) {
            return false;
        }
        if (this.idEstadoInterno != other.idEstadoInterno && (this.idEstadoInterno == null || !this.idEstadoInterno.equals(other.idEstadoInterno))) {
            return false;
        }
         if (this.referencia != other.referencia && (this.referencia == null || !this.referencia.equals(other.referencia))) {
            return false;
        }
        if (this.idIdentificacion != other.idIdentificacion && (this.idIdentificacion == null || !this.idIdentificacion.equals(other.idIdentificacion))) {
            return false;
        }
        return true;
    }
    
    
}
