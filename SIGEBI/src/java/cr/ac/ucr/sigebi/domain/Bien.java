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
 * @author jairo.cisneros
 */
@Entity(name = "Bien")
@Table(name = "SIGEBI_OAF.SIGB_BIEN")
@SequenceGenerator(name = "SGB_SQ_BIEN",  sequenceName = "SIGEBI_OAF.SGB_SQ_BIEN", initialValue = 1, allocationSize = 1)
public class Bien extends ObjetoBase implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_BIEN")
    @Column(name = "ID_BIEN")
    private Long id;

    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "CANTIDAD")
    private Integer cantidad;
    
    @ManyToOne
    @JoinColumn(name = "ID_SUB_CATEGORIA", referencedColumnName = "ID")
    private SubCategoria subCategoria;
    
    @ManyToOne
    @JoinColumn(name = "ID_SUB_CLASIFICACION", referencedColumnName = "ID_SUB_CLASIFICACION")
    private SubClasificacion subClasificacion;
    
    @ManyToOne
    @JoinColumn(name = "ID_TIPO_BIEN", referencedColumnName = "ID_TIPO")
    private Tipo tipo;
    
    @ManyToOne
    @JoinColumn(name = "ID_ORIGEN", referencedColumnName = "ID_TIPO")
    private Tipo origen;
    
    @ManyToOne
    @JoinColumn(name = "ID_BIEN", referencedColumnName = "ID", insertable=false, updatable=false)
    private ViewResumenBien resumenBien;
     
    @ManyToOne
    @JoinColumn(name = "ID_UNIDAD_EJECUTORA", referencedColumnName = "ID")
    private UnidadEjecutora unidadEjecutora;
    
    @ManyToOne
    @JoinColumn(name = "ID_PROVEEDOR", referencedColumnName = "ID", nullable=true)
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "ID_MONEDA", referencedColumnName = "ID")
    private Moneda moneda;

    @Column(name = "COSTO")
    private Double costo;
    
    @Column(name = "FECHA_ADQUISICION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaAdquisicion;
    
    @Column(name = "ID_PERSONA", nullable=true)//FIXME objecto persona
    private Integer persona;
    
    @Column(name = "INICIO_GARANTIA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date inicioGarantia;

    @Column(name = "FIN_GARANTIA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date finGarantia;
    
    @Column(name = "DESCRIPCION_GARANTIA", nullable=true)
    private String descripcionGarantia;
    
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne
    private Estado estado;
    
    @JoinColumn(name = "ID_LOTE", referencedColumnName = "ID")
    @ManyToOne
    private Lote lote;
    
    @Column(name = "CAPITALIZABLE")
    private Boolean capitalizable;
    
    @ManyToOne
    @JoinColumn(name = "ID_UBICACION", referencedColumnName = "ID_UBICACION", nullable=true)
    private Ubicacion ubicacion;
     
    @Column(name = "DESCRIPCION_UBICACION")
    private String descripcionUbicacion;
    
    @ManyToOne
    @JoinColumn(name = "ID_ESTADO_INTERNO", referencedColumnName = "ID_ESTADO")
    private Estado estadoInterno;
    
    @Column(name = "REFERENCIA", nullable=true)
    private Integer referencia;
    
    @ManyToOne
    @JoinColumn(name = "ID_IDENTIFICACION", referencedColumnName = "ID_IDENTIFICACION")
    private Identificacion identificacion;    

    @ManyToOne
    @JoinColumn(name = "ID_INTERFAZ", referencedColumnName = "ID_INTERFAZ_BIEN")
    private InterfazBien interfazBien;    
    
    @Column(name = "FECHA_INGRESO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaIngreso;
    
    
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO_REGISTRA", referencedColumnName = "ID_USUARIO")
    private Usuario usuarioRegistra;
    
    @Transient
    private List<Accesorio> accesorios;
    
    @Transient
    private List<BienCaracteristica> caracteristicas;
    
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    public Long getId() {
        return id;
    }

    public InterfazBien getInterfazBien() {
        return interfazBien;
    }

    public void setInterfazBien(InterfazBien interfazBien) {
        this.interfazBien = interfazBien;
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

    public SubCategoria getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }

    public SubClasificacion getSubClasificacion() {
        return subClasificacion;
    }

    public void setSubClasificacion(SubClasificacion subClasificacion) {
        this.subClasificacion = subClasificacion;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public Boolean getCapitalizable() {
        return capitalizable;
    }

    public void setCapitalizable(Boolean capitalizable) {
        this.capitalizable = capitalizable;
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

    public Estado getEstadoInterno() {
        return estadoInterno;
    }

    public void setEstadoInterno(Estado estadoInterno) {
        this.estadoInterno = estadoInterno;
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

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Usuario getUsuarioRegistra() {
        return usuarioRegistra;
    }

    public void setUsuarioRegistra(Usuario usuarioRegistra) {
        this.usuarioRegistra = usuarioRegistra;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos para visibilidad de opciones en los listados">
    public boolean getRechazar() {
        if (Constantes.ESTADO_INTERNO_BIEN_EXCLUSION_SOLICITADO.equals(this.getEstadoInterno().getValor()) ||
            Constantes.ESTADO_INTERNO_BIEN_PRESTAMO_SOLICITADO.equals(this.getEstadoInterno().getValor())) {
            return true;
        }
        return false;
    }
    
    public boolean getSolicitar() {
        if (Constantes.ESTADO_INTERNO_BIEN_EXCLUSION_SOLICITADO.equals(this.getEstadoInterno().getValor()) ||
            Constantes.ESTADO_INTERNO_BIEN_EXCLUSION_APROBADO.equals(this.getEstadoInterno().getValor()) ||
            Constantes.ESTADO_INTERNO_BIEN_PRESTAMO_SOLICITADO.equals(this.getEstadoInterno().getValor()) ||
            Constantes.ESTADO_INTERNO_BIEN_PRESTAMO_APROBADO.equals(this.getEstadoInterno().getValor())) {
            return false;
        }
        return true;
    }
    
    public boolean getAprobar() {
        if (Constantes.ESTADO_INTERNO_BIEN_EXCLUSION_SOLICITADO.equals(this.getEstadoInterno().getValor()) ||
            Constantes.ESTADO_INTERNO_BIEN_PRESTAMO_SOLICITADO.equals(this.getEstadoInterno().getValor())) {
            return true;
        }
        return false;
    }
    
    public boolean getDevolver() {
        if (Constantes.ESTADO_INTERNO_BIEN_PRESTAMO_APROBADO.equals(this.getEstadoInterno().getValor())) {
            return true;
        }
        return false;
    }
    
    public boolean getEliminar() {
        if (Constantes.ESTADO_INTERNO_BIEN_EXCLUSION_SOLICITADO.equals(this.getEstadoInterno().getValor()) ||
            Constantes.ESTADO_INTERNO_BIEN_EXCLUSION_APROBADO.equals(this.getEstadoInterno().getValor()) ||
            Constantes.ESTADO_INTERNO_BIEN_PRESTAMO_SOLICITADO.equals(this.getEstadoInterno().getValor()) ||
            Constantes.ESTADO_INTERNO_BIEN_PRESTAMO_ANULADO.equals(this.getEstadoInterno().getValor()) ||
            Constantes.ESTADO_INTERNO_BIEN_PRESTAMO_APROBADO.equals(this.getEstadoInterno().getValor())) {
            return false;
        }
        return true;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 23 * hash + (this.descripcion != null ? this.descripcion.hashCode() : 0);
        hash = 23 * hash + (this.cantidad != null ? this.cantidad.hashCode() : 0);
        hash = 23 * hash + (this.subCategoria != null ? this.subCategoria.hashCode() : 0);
        hash = 23 * hash + (this.subClasificacion != null ? this.subClasificacion.hashCode() : 0);
        hash = 23 * hash + (this.tipo != null ? this.tipo.hashCode() : 0);
        hash = 23 * hash + (this.origen != null ? this.origen.hashCode() : 0);
        hash = 23 * hash + (this.resumenBien != null ? this.resumenBien.hashCode() : 0);
        hash = 23 * hash + (this.unidadEjecutora != null ? this.unidadEjecutora.hashCode() : 0);
        hash = 23 * hash + (this.proveedor != null ? this.proveedor.hashCode() : 0);
        hash = 23 * hash + (this.moneda != null ? this.moneda.hashCode() : 0);
        hash = 23 * hash + (this.costo != null ? this.costo.hashCode() : 0);
        hash = 23 * hash + (this.fechaAdquisicion != null ? this.fechaAdquisicion.hashCode() : 0);
        hash = 23 * hash + (this.persona != null ? this.persona.hashCode() : 0);
        hash = 23 * hash + (this.inicioGarantia != null ? this.inicioGarantia.hashCode() : 0);
        hash = 23 * hash + (this.finGarantia != null ? this.finGarantia.hashCode() : 0);
        hash = 23 * hash + (this.descripcionGarantia != null ? this.descripcionGarantia.hashCode() : 0);
        hash = 23 * hash + (this.estado != null ? this.estado.hashCode() : 0);
        hash = 23 * hash + (this.lote != null ? this.lote.hashCode() : 0);
        hash = 23 * hash + (this.capitalizable != null ? this.capitalizable.hashCode() : 0);
        hash = 23 * hash + (this.ubicacion != null ? this.ubicacion.hashCode() : 0);
        hash = 23 * hash + (this.descripcionUbicacion != null ? this.descripcionUbicacion.hashCode() : 0);
        hash = 23 * hash + (this.estadoInterno != null ? this.estadoInterno.hashCode() : 0);
        hash = 23 * hash + (this.referencia != null ? this.referencia.hashCode() : 0);
        hash = 23 * hash + (this.identificacion != null ? this.identificacion.hashCode() : 0);
        hash = 23 * hash + (this.accesorios != null ? this.accesorios.hashCode() : 0);
        hash = 23 * hash + (this.caracteristicas != null ? this.caracteristicas.hashCode() : 0);
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
        if ((this.descripcionGarantia == null) ? (other.descripcionGarantia != null) : !this.descripcionGarantia.equals(other.descripcionGarantia)) {
            return false;
        }
        if ((this.descripcionUbicacion == null) ? (other.descripcionUbicacion != null) : !this.descripcionUbicacion.equals(other.descripcionUbicacion)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.cantidad != other.cantidad && (this.cantidad == null || !this.cantidad.equals(other.cantidad))) {
            return false;
        }
        if (this.subCategoria != other.subCategoria && (this.subCategoria == null || !this.subCategoria.equals(other.subCategoria))) {
            return false;
        }
        if (this.subClasificacion != other.subClasificacion && (this.subClasificacion == null || !this.subClasificacion.equals(other.subClasificacion))) {
            return false;
        }
        if (this.tipo != other.tipo && (this.tipo == null || !this.tipo.equals(other.tipo))) {
            return false;
        }
        if (this.origen != other.origen && (this.origen == null || !this.origen.equals(other.origen))) {
            return false;
        }
        if (this.resumenBien != other.resumenBien && (this.resumenBien == null || !this.resumenBien.equals(other.resumenBien))) {
            return false;
        }
        if (this.unidadEjecutora != other.unidadEjecutora && (this.unidadEjecutora == null || !this.unidadEjecutora.equals(other.unidadEjecutora))) {
            return false;
        }
        if (this.proveedor != other.proveedor && (this.proveedor == null || !this.proveedor.equals(other.proveedor))) {
            return false;
        }
        if (this.moneda != other.moneda && (this.moneda == null || !this.moneda.equals(other.moneda))) {
            return false;
        }
        if (this.costo != other.costo && (this.costo == null || !this.costo.equals(other.costo))) {
            return false;
        }
        if (this.fechaAdquisicion != other.fechaAdquisicion && (this.fechaAdquisicion == null || !this.fechaAdquisicion.equals(other.fechaAdquisicion))) {
            return false;
        }
        if (this.persona != other.persona && (this.persona == null || !this.persona.equals(other.persona))) {
            return false;
        }
        if (this.inicioGarantia != other.inicioGarantia && (this.inicioGarantia == null || !this.inicioGarantia.equals(other.inicioGarantia))) {
            return false;
        }
        if (this.finGarantia != other.finGarantia && (this.finGarantia == null || !this.finGarantia.equals(other.finGarantia))) {
            return false;
        }
        if (this.estado != other.estado && (this.estado == null || !this.estado.equals(other.estado))) {
            return false;
        }
        if (this.lote != other.lote && (this.lote == null || !this.lote.equals(other.lote))) {
            return false;
        }
        if (this.capitalizable != other.capitalizable && (this.capitalizable == null || !this.capitalizable.equals(other.capitalizable))) {
            return false;
        }
        if (this.ubicacion != other.ubicacion && (this.ubicacion == null || !this.ubicacion.equals(other.ubicacion))) {
            return false;
        }
        if (this.estadoInterno != other.estadoInterno && (this.estadoInterno == null || !this.estadoInterno.equals(other.estadoInterno))) {
            return false;
        }
        if (this.referencia != other.referencia && (this.referencia == null || !this.referencia.equals(other.referencia))) {
            return false;
        }
        if (this.identificacion != other.identificacion && (this.identificacion == null || !this.identificacion.equals(other.identificacion))) {
            return false;
        }
        if (this.accesorios != other.accesorios && (this.accesorios == null || !this.accesorios.equals(other.accesorios))) {
            return false;
        }
        if (this.caracteristicas != other.caracteristicas && (this.caracteristicas == null || !this.caracteristicas.equals(other.caracteristicas))) {
            return false;
        }
        return true;
    }
    //</editor-fold>

}
