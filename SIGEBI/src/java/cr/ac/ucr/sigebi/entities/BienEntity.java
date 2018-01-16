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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author jorge.serrano
 */
@Entity(name = "BienEntity")
@Table(name = "SIGEBI_OAF.SGB_BIEN")
@NamedQueries({ 
    @NamedQuery(name = "BienEntity.traeTodo", query = "SELECT s FROM BienEntity s WHERE s.numUnidadEjec = :pnumUnidadEjec")
    ,@NamedQuery(name = "BienEntity.traerPorId", query = "SELECT s FROM BienEntity s WHERE s.idBien = :pIdBien")
    ,@NamedQuery(name = "BienEntity.traerConFiltros", query = "SELECT s FROM BienEntity s WHERE s.numUnidadEjec = :pnumUnidadEjec "
                                                                                            + " AND str(s.idBien) like :fltIdBien "
                                                                                            + " AND upper(s.descripcion) like upper(:fltDescripcion) "
                                                                                            + " AND str(s.idEstado) like :fltEstado "                                                
    )
        
}
)
public class BienEntity extends ObjetoBase implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    @Id
    @Basic(optional = false)
    @Column(name = "ID_BIEN")
    private Integer idBien;
    
    
    @JoinColumn(name = "ID_IDENTIFICACION", referencedColumnName = "ID_IDENTIFICACION")
    @ManyToOne(fetch = FetchType.EAGER)
    private PlacasEntity idPlaca;
    
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "CANTIDAD")
    private Integer cantidad;//
    
    @Column(name = "CODIGO_SUB_CATEGORIA")
    private String codSubCategoria;
    
    @Column(name = "ID_SUB_CLASIFICACION")
    private Integer idSubClasificacion;

    @Column(name = "TIPO_BIEN")
    private Integer tipoBien;
    
    @Column(name = "ORIGEN")
    private Integer origen;
    
    @Column(name = "NUM_UNIDAD_EJEC")
    private Integer numUnidadEjec;
    
    @Column(name = "PROVEEDOR")
    private Integer proveedor;
    
    @Column(name = "ID_MONEDA")
    private Integer idMoneda;

    @Column(name = "COSTO")
    private float costo;
    
    @Column(name = "FECHA_ADQUISICION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecAdquisicion;
    
    @Column(name = "ID_PERSONA")
    private Integer numPersona;
    
    @Column(name = "INICIO_GARANTIA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date inicioGarantia;

    @Column(name = "FIN_GARANTIA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date finGarantia;

    @Column(name = "DESCRIPCION_GARANTIA")
    private String descGarantia;
    
    
    @Column(name = "NUM_LOTE")
    private String numLote;
    
    
    @Column(name = "CAPITALIZABLE")
    private Integer capitalizable;
    
    @Column(name = "ID_UBICACION")
    private Integer idUbicacion;
    
    @Column(name = "DESCRIPCION_UBICACION")
    private String descUbicacion;
    
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(fetch = FetchType.EAGER)
    private EstadoEntity idEstado;
    
    @Column(name = "ESTADO_INTERNO")
    private Integer estadoInterno;
    
    @Column(name = "REFERENCIA")
    private Integer referencia;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    
    
    public BienEntity(int idBien
            , String descripcion
            , int cantidad
            , String codSubCategoria
            , int idSubClasificacion
            , int tipoBien
            //., String tipoBienSelec
            , int origen
            , int numUnidadEjec
            , int proveedor
            , int idMoneda
            , float costo
            , Date fecAdquisicion
            , int numPersona
            , Date inicioGarantia
            , Date finGarantia
            , String descGarantia
            , int idEstado
    ) {   
        
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.codSubCategoria = codSubCategoria;
        this.idSubClasificacion = idSubClasificacion;
        this.tipoBien = tipoBien;
        this.origen = origen;
        this.numUnidadEjec = numUnidadEjec;
        this.proveedor = proveedor;
        this.idMoneda = idMoneda;
        this.costo = costo;
        this.fecAdquisicion = fecAdquisicion;
        this.numPersona = numPersona;
        this.inicioGarantia = inicioGarantia;
        this.finGarantia = finGarantia;
        this.descGarantia = descGarantia;
    }

    public BienEntity() {
        idEstado = new EstadoEntity(1);
    }

    
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
    
    public Integer getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Integer idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getDescUbicacion() {
        return descUbicacion;
    }

    public void setDescUbicacion(String descUbicacion) {
        this.descUbicacion = descUbicacion;
    }

    public Integer getCapitalizable() {
        return capitalizable;
    }

    public void setCapitalizable(Integer capitalizable) {
        this.capitalizable = capitalizable;
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

    public Integer getNumUnidadEjec() {
        return numUnidadEjec;
    }

    public void setNumUnidadEjec(Integer numUnidadEjec) {
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

    public EstadoEntity getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(EstadoEntity idEstado) {
        this.idEstado = idEstado;
    }

    public String getNumLote() {
        return numLote;
    }

    public void setNumLote(String numLote) {
        this.numLote = numLote;
    }

    public Integer getEstadoInterno() {
        return estadoInterno;
    }

    public void setEstadoInterno(Integer estadoInterno) {
        this.estadoInterno = estadoInterno;
    }

    public Integer getReferencia() {
        return referencia;
    }

    public void setReferencia(Integer referencia) {
        this.referencia = referencia;
    }
    //</editor-fold>
     
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBien != null ? idBien.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BienEntity)) {
            return false;
        }
        BienEntity other = (BienEntity) object;
        if ((this.idBien == null && other.idBien != null) || (this.idBien != null && !this.idBien.equals(other.idBien))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.BienEntity[idBien=" + idBien + "]";
    }
    //</editor-fold>
}
