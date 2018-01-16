/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.entities;

import cr.ac.ucr.sigebi.utils.Constantes;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author jorge.serrano
 */
@Entity(name = "SincronizarEntity")
@Table(name = "SF_INTERFAZ_SIGEBI")
public class SincronizarEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @Basic(optional = false)
    @Column(name = "NUM_EMPRESA") // NUMBER (3) 
    private int  numEmpresa;
    @Column(name = "ID_ACTIVO") // NUMBER (10) 
    private int  idActivo;
    @Column(name = "DESCRIPCION") // VARCHAR2 (100 Byte) 
    private String descripcion;
    @Column(name = "INDICA_TIPO_ACTIVO") // VARCHAR2 (1 Byte) 
    private char indicaTipoActivo;
    @Column(name = "CODIGO_CATEGORIA") // VARCHAR2 (3 Byte) 
    private String codigoCategoria;
    @Column(name = "CODIGO_SUB_CATEGORIA") // VARCHAR2 (3 Byte) 
    private String codigoSubCategoria;
    @Column(name = "ID_CUSTODIO") // NUMBER (10) 
    private int  idCustodio;
    @Column(name = "ID_PROVEEDOR") // NUMBER (10) 
    private int  idProveedor;
    @Column(name = "ORIGEN") // VARCHAR2 (1 Byte) 
    private char origen;
    @Column(name = "VALOR_INICIAL_COLONES") // NUMBER (18,2) 
    private float valorInicialColones;
    @Column(name = "VALOR_INICIAL_OTRA_MO") // NUMBER (18,2) 
    private float valorInicialOtraMo;
    @Column(name = "TIPO_CAMBIO_COMPRA") // NUMBER (6,2) 
    private float tipoCambioCompra;
    @Column(name = "UBICACION_FISICA") // VARCHAR2 (100 Byte) 
    private String ubicacionFisica;
    @Column(name = "TIENE_IMAGEN") // VARCHAR2 (1 Byte) 
    private char tieneImagen;
    @Column(name = "ESTADO_DEL_ACTIVO") // VARCHAR2 (1 Byte) 
    private char estadoDelActivo;
    @Column(name = "VALOR_MEJORAS") // NUMBER (18,2) 
    private float valorMejoras;
    @Column(name = "VALOR_REVALUACIONES") // NUMBER (18,2) 
    private float valorRevaluaciones;
    @Column(name = "DEPRECACUM") // NUMBER (30,10) 
    private double deprecacum;
    @Column(name = "DEPRECACUM_ADICMEJORA") // NUMBER (30,10) 
    private double deprecacumAdicmejora;
    @Column(name = "DEPRECACUM_REVALUAC") // NUMBER (30,10) 
    private double deprecacumRevaluac;
    @Column(name = "PLACA_TEMP") // VARCHAR2 (1 Byte) 
    private char placaTemp;
    
    
    
    @Column(name = "INDICA_PLACA") // VARCHAR2 (1 Byte) 
    private char indicaPlaca;
    @Column(name = "PLACA") // VARCHAR2 (20 Byte) 
    private String placa;
    @Column(name = "ID_APLICACION_DOC_ADQUISI") // VARCHAR2 (5 Byte) 
    private String idAplicacionDocAdquisi;
    @Column(name = "ID_TIPO_DOCUMENTO_ADQUISI") // NUMBER (5) 
    private int  idTipoDocumentoAdquisi;
    @Column(name = "ID_SUBTIPO_DOCUMENTO_ADQUISI") // NUMBER (5) 
    private int  idSubtipoDocumentoAdquisi;
    @Column(name = "NUM_DOC_ADQUISI") // NUMBER (10) 
    private int  numDocAdquisi;
    @Column(name = "ID_APLICACION_DOC_PAGO") // VARCHAR2 (5 Byte) 
    private String idAplicacionDocPago;
    @Column(name = "ID_TIPO_DOCUMENTO_DOC_PAGO") // NUMBER (5) 
    private int  idTipoDocumentoDocPago;
    @Column(name = "ID_SUBTIPO_DOCUMENTO_DOC_PAGO") // NUMBER (5) 
    private int  idSubtipoDocumentoDocPago;
    @Column(name = "NUM_DOC_PAGO") // NUMBER (10) 
    private int  numDocPago;
    @Column(name = "ID_MONEDA") // NUMBER (5) 
    private int  idMoneda;
    @Column(name = "NUM_UNIDAD_EJEC") // NUMBER (5) 
    private int  numUnidadEjec;
    @Column(name = "PERIODO_COMPRA") // NUMBER (4) 
    private int periodoCompra;
    @Column(name = "CTA_CONTABLE_CAPITALIZAC") // VARCHAR2 (35 Byte) 
    private String ctaContableCapitalizac;
    @Column(name = "CTA_CONTABLE_DEPRECIAC") // VARCHAR2 (35 Byte) 
    private String ctaContableDepreciac;
    @Column(name = "CTA_CONTABLE_REVALUAC") // VARCHAR2 (35 Byte) 
    private String ctaContableRevaluac;
    @Column(name = "CTA_CONTABLE_PATRIMO_COMPRAS") // VARCHAR2 (35 Byte) 
    private String ctaContablePatrimoCompras;
    @Column(name = "CTA_CONTABLE_PATRIMO_DONAC") // VARCHAR2 (35 Byte) 
    private String ctaContablePatrimoDonac;
    @Column(name = "VIDA_UTIL_ANIOS") // NUMBER (5,2) 
    private int vidaUtilAnios;
    @Column(name = "NOTAS") // VARCHAR2 (4000 Byte) 
    private String notas;
    @Column(name = "CTA_CONTABLE_AUX") // VARCHAR2 (35 Byte) 
    private String ctaContableAux;
    @Column(name = "NUM_ACTA_DONAC") // VARCHAR2 (15 Byte) 
    private String numActaDonac;
    @Column(name = "FECHA_ULTIMA_DEPREC") // DATE 
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaUltimaDeprec;
    @Column(name = "FECHA_ULTIMA_REVALUACION") // DATE 
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaUltimaRevaluacion;
    @Column(name = "FECHA_ULTIMA_MEJORA") // DATE 
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaUltimaMejora;
    @Column(name = "CTA_CONTABLE_ADICMEJORA") // VARCHAR2 (35 Byte) 
    private String ctaContableAdicmejora;
    @Column(name = "FECHA_ADQUISICION") // DATE 
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaAdquisicion;
    @Column(name = "PLACA_VIEJA") // VARCHAR2 (12 Byte) 
    private String placaVieja;
    @Column(name = "ESP_TEC_FACTURA") // VARCHAR2 (200 Byte) 
    private String espTecFactura;
    @Column(name = "ESP_TEC_CHEQUE") // VARCHAR2 (200 Byte) 
    private String espTecCheque;
    @Column(name = "ESP_TEC_ORDEN_COMPRA") // VARCHAR2 (200 Byte) 
    private String espTecOrdenCompra;
    @Column(name = "ESP_TEC_OFICIO") // VARCHAR2 (200 Byte) 
    private String espTecOficio;
    @Column(name = "ESP_TEC_FONDO_TRABAJO") // VARCHAR2 (200 Byte) 
    private String espTecFondoTrabajo;
    @Column(name = "ESP_TEC_TEF") // VARCHAR2 (200 Byte) 
    private String espTecTef;
    @Column(name = "ESP_TEC_PROVEEDOR") // VARCHAR2 (200 Byte) 
    private String espTecProveedor;
    @Column(name = "ESP_TEC_GARANTIA") // VARCHAR2 (200 Byte) 
    private String espTecGarantia;
    @Column(name = "ESP_TEC_DONACION") // VARCHAR2 (200 Byte) 
    private String espTecDonacion;
    @Column(name = "FECHA_ADICION") // DATE 
    private Date fechaAdicion;
    @Column(name = "ADICIONADO_POR") // VARCHAR2 (50 Byte) 
    private String adicionadoPor;

    //</editor-fold>
    
    
    
    //<editor-fold defaultstate="collapsed" desc="SET's y GET's ">

    public int getNumEmpresa() {
        return numEmpresa;
    }

    public void setNumEmpresa(int numEmpresa) {
        this.numEmpresa = numEmpresa;
    }

    public int getIdActivo() {
        return idActivo;
    }

    public void setIdActivo(int idActivo) {
        this.idActivo = idActivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public char getIndicaTipoActivo() {
        return indicaTipoActivo;
    }

    public void setIndicaTipoActivo(char indicaTipoActivo) {
        this.indicaTipoActivo = indicaTipoActivo;
    }

    public String getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(String codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    public String getCodigoSubCategoria() {
        return codigoSubCategoria;
    }

    public void setCodigoSubCategoria(String codigoSubCategoria) {
        this.codigoSubCategoria = codigoSubCategoria;
    }

    public int getIdCustodio() {
        return idCustodio;
    }

    public void setIdCustodio(int idCustodio) {
        this.idCustodio = idCustodio;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public char getOrigen() {
        return origen;
    }

    public void setOrigen(char origen) {
        this.origen = origen;
    }

    public float getValorInicialColones() {
        return valorInicialColones;
    }

    public void setValorInicialColones(float valorInicialColones) {
        this.valorInicialColones = valorInicialColones;
    }

    public float getValorInicialOtraMo() {
        return valorInicialOtraMo;
    }

    public void setValorInicialOtraMo(float valorInicialOtraMo) {
        this.valorInicialOtraMo = valorInicialOtraMo;
    }

    public float getTipoCambioCompra() {
        return tipoCambioCompra;
    }

    public void setTipoCambioCompra(float tipoCambioCompra) {
        this.tipoCambioCompra = tipoCambioCompra;
    }

    public String getUbicacionFisica() {
        return ubicacionFisica;
    }

    public void setUbicacionFisica(String ubicacionFisica) {
        this.ubicacionFisica = ubicacionFisica;
    }

    public char getTieneImagen() {
        return tieneImagen;
    }

    public void setTieneImagen(char tieneImagen) {
        this.tieneImagen = tieneImagen;
    }

    public char getEstadoDelActivo() {
        return estadoDelActivo;
    }

    public void setEstadoDelActivo(char estadoDelActivo) {
        this.estadoDelActivo = estadoDelActivo;
    }

    public float getValorMejoras() {
        return valorMejoras;
    }

    public void setValorMejoras(float valorMejoras) {
        this.valorMejoras = valorMejoras;
    }

    public float getValorRevaluaciones() {
        return valorRevaluaciones;
    }

    public void setValorRevaluaciones(float valorRevaluaciones) {
        this.valorRevaluaciones = valorRevaluaciones;
    }

    public double getDeprecacum() {
        return deprecacum;
    }

    public void setDeprecacum(double deprecacum) {
        this.deprecacum = deprecacum;
    }

    public double getDeprecacumAdicmejora() {
        return deprecacumAdicmejora;
    }

    public void setDeprecacumAdicmejora(double deprecacumAdicmejora) {
        this.deprecacumAdicmejora = deprecacumAdicmejora;
    }

    public double getDeprecacumRevaluac() {
        return deprecacumRevaluac;
    }

    public void setDeprecacumRevaluac(double deprecacumRevaluac) {
        this.deprecacumRevaluac = deprecacumRevaluac;
    }

    public char getPlacaTemp() {
        return placaTemp;
    }

    public void setPlacaTemp(char placaTemp) {
        this.placaTemp = placaTemp;
    }

    public char getIndicaPlaca() {
        return indicaPlaca;
    }

    public void setIndicaPlaca(char indicaPlaca) {
        this.indicaPlaca = indicaPlaca;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getIdAplicacionDocAdquisi() {
        return idAplicacionDocAdquisi;
    }

    public void setIdAplicacionDocAdquisi(String idAplicacionDocAdquisi) {
        this.idAplicacionDocAdquisi = idAplicacionDocAdquisi;
    }

    public int getIdTipoDocumentoAdquisi() {
        return idTipoDocumentoAdquisi;
    }

    public void setIdTipoDocumentoAdquisi(int idTipoDocumentoAdquisi) {
        this.idTipoDocumentoAdquisi = idTipoDocumentoAdquisi;
    }

    public int getIdSubtipoDocumentoAdquisi() {
        return idSubtipoDocumentoAdquisi;
    }

    public void setIdSubtipoDocumentoAdquisi(int idSubtipoDocumentoAdquisi) {
        this.idSubtipoDocumentoAdquisi = idSubtipoDocumentoAdquisi;
    }

    public int getNumDocAdquisi() {
        return numDocAdquisi;
    }

    public void setNumDocAdquisi(int numDocAdquisi) {
        this.numDocAdquisi = numDocAdquisi;
    }

    public String getIdAplicacionDocPago() {
        return idAplicacionDocPago;
    }

    public void setIdAplicacionDocPago(String idAplicacionDocPago) {
        this.idAplicacionDocPago = idAplicacionDocPago;
    }

    public int getIdTipoDocumentoDocPago() {
        return idTipoDocumentoDocPago;
    }

    public void setIdTipoDocumentoDocPago(int idTipoDocumentoDocPago) {
        this.idTipoDocumentoDocPago = idTipoDocumentoDocPago;
    }

    public int getIdSubtipoDocumentoDocPago() {
        return idSubtipoDocumentoDocPago;
    }

    public void setIdSubtipoDocumentoDocPago(int idSubtipoDocumentoDocPago) {
        this.idSubtipoDocumentoDocPago = idSubtipoDocumentoDocPago;
    }

    public int getNumDocPago() {
        return numDocPago;
    }

    public void setNumDocPago(int numDocPago) {
        this.numDocPago = numDocPago;
    }

    public int getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(int idMoneda) {
        this.idMoneda = idMoneda;
    }

    public int getNumUnidadEjec() {
        return numUnidadEjec;
    }

    public void setNumUnidadEjec(int numUnidadEjec) {
        this.numUnidadEjec = numUnidadEjec;
    }

    public int getPeriodoCompra() {
        return periodoCompra;
    }

    public void setPeriodoCompra(int periodoCompra) {
        this.periodoCompra = periodoCompra;
    }

    public String getCtaContableCapitalizac() {
        return ctaContableCapitalizac;
    }

    public void setCtaContableCapitalizac(String ctaContableCapitalizac) {
        this.ctaContableCapitalizac = ctaContableCapitalizac;
    }

    public String getCtaContableDepreciac() {
        return ctaContableDepreciac;
    }

    public void setCtaContableDepreciac(String ctaContableDepreciac) {
        this.ctaContableDepreciac = ctaContableDepreciac;
    }

    public String getCtaContableRevaluac() {
        return ctaContableRevaluac;
    }

    public void setCtaContableRevaluac(String ctaContableRevaluac) {
        this.ctaContableRevaluac = ctaContableRevaluac;
    }

    public String getCtaContablePatrimoCompras() {
        return ctaContablePatrimoCompras;
    }

    public void setCtaContablePatrimoCompras(String ctaContablePatrimoCompras) {
        this.ctaContablePatrimoCompras = ctaContablePatrimoCompras;
    }

    public String getCtaContablePatrimoDonac() {
        return ctaContablePatrimoDonac;
    }

    public void setCtaContablePatrimoDonac(String ctaContablePatrimoDonac) {
        this.ctaContablePatrimoDonac = ctaContablePatrimoDonac;
    }

    public int getVidaUtilAnios() {
        return vidaUtilAnios;
    }

    public void setVidaUtilAnios(int vidaUtilAnios) {
        this.vidaUtilAnios = vidaUtilAnios;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getCtaContableAux() {
        return ctaContableAux;
    }

    public void setCtaContableAux(String ctaContableAux) {
        this.ctaContableAux = ctaContableAux;
    }

    public String getNumActaDonac() {
        return numActaDonac;
    }

    public void setNumActaDonac(String numActaDonac) {
        this.numActaDonac = numActaDonac;
    }

    public Date getFechaUltimaDeprec() {
        return fechaUltimaDeprec;
    }

    public void setFechaUltimaDeprec(Date fechaUltimaDeprec) {
        this.fechaUltimaDeprec = fechaUltimaDeprec;
    }

    public Date getFechaUltimaRevaluacion() {
        return fechaUltimaRevaluacion;
    }

    public void setFechaUltimaRevaluacion(Date fechaUltimaRevaluacion) {
        this.fechaUltimaRevaluacion = fechaUltimaRevaluacion;
    }

    public Date getFechaUltimaMejora() {
        return fechaUltimaMejora;
    }

    public void setFechaUltimaMejora(Date fechaUltimaMejora) {
        this.fechaUltimaMejora = fechaUltimaMejora;
    }

    public String getCtaContableAdicmejora() {
        return ctaContableAdicmejora;
    }

    public void setCtaContableAdicmejora(String ctaContableAdicmejora) {
        this.ctaContableAdicmejora = ctaContableAdicmejora;
    }

    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(Date fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public String getPlacaVieja() {
        return placaVieja;
    }

    public void setPlacaVieja(String placaVieja) {
        this.placaVieja = placaVieja;
    }

    public String getEspTecFactura() {
        return espTecFactura;
    }

    public void setEspTecFactura(String espTecFactura) {
        this.espTecFactura = espTecFactura;
    }

    public String getEspTecCheque() {
        return espTecCheque;
    }

    public void setEspTecCheque(String espTecCheque) {
        this.espTecCheque = espTecCheque;
    }

    public String getEspTecOrdenCompra() {
        return espTecOrdenCompra;
    }

    public void setEspTecOrdenCompra(String espTecOrdenCompra) {
        this.espTecOrdenCompra = espTecOrdenCompra;
    }

    public String getEspTecOficio() {
        return espTecOficio;
    }

    public void setEspTecOficio(String espTecOficio) {
        this.espTecOficio = espTecOficio;
    }

    public String getEspTecFondoTrabajo() {
        return espTecFondoTrabajo;
    }

    public void setEspTecFondoTrabajo(String espTecFondoTrabajo) {
        this.espTecFondoTrabajo = espTecFondoTrabajo;
    }

    public String getEspTecTef() {
        return espTecTef;
    }

    public void setEspTecTef(String espTecTef) {
        this.espTecTef = espTecTef;
    }

    public String getEspTecProveedor() {
        return espTecProveedor;
    }

    public void setEspTecProveedor(String espTecProveedor) {
        this.espTecProveedor = espTecProveedor;
    }

    public String getEspTecGarantia() {
        return espTecGarantia;
    }

    public void setEspTecGarantia(String espTecGarantia) {
        this.espTecGarantia = espTecGarantia;
    }

    public String getEspTecDonacion() {
        return espTecDonacion;
    }

    public void setEspTecDonacion(String espTecDonacion) {
        this.espTecDonacion = espTecDonacion;
    }

    public Date getFechaAdicion() {
        return fechaAdicion;
    }

    public void setFechaAdicion(Date fechaAdicion) {
        this.fechaAdicion = fechaAdicion;
    }

    public String getAdicionadoPor() {
        return adicionadoPor;
    }

    public void setAdicionadoPor(String adicionadoPor) {
        this.adicionadoPor = adicionadoPor;
    }
    
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    
    
    public SincronizarEntity() {
    }
    
    public SincronizarEntity(BienEntity bien) {
        numEmpresa = 1;
        idActivo = bien.getIdBien();//FIXME;
//        descripcion = bien.getIdSubClasificacion().toString();//.getDescripcion(); 
        indicaTipoActivo = bien.getTipoBien().toString().charAt(0);
        codigoCategoria = bien.getCodSubCategoria().split("-")[0];
        codigoSubCategoria = bien.getCodSubCategoria().split("-")[1];
        idCustodio = 1000;//bien.getNumUnidadEjec();
        idProveedor = bien.getProveedor();
        origen = bien.getOrigen().toString().charAt(0);
        valorInicialColones = bien.getCosto();
        valorInicialOtraMo = bien.getCosto();
        ubicacionFisica = bien.getDescUbicacion();
//        tipoCambioCompra = 500;
        tieneImagen = 'N';
        estadoDelActivo = Constantes.SINCRONIZAR_ESTADO_TRANSITO_ACTIVACION;
//        valorMejoras = 0;
//        valorRevaluaciones = 0;
//        deprecacum = 0;
//        deprecacumAdicmejora = 0;
//        deprecacumRevaluac = 0;
//        placaTemp = 'N';
        
        // Datos no Obligatorios
        //placa = bien.getIdBien().get;//FIXME
        idMoneda = bien.getIdMoneda();
        numUnidadEjec = bien.getNumUnidadEjec();
        fechaAdicion = bien.getFecAdquisicion();
        //adicionadoPor = "";
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SincronizarEntity)) {
            return false;
        }
        SincronizarEntity other = (SincronizarEntity) object;
        if (this.placa != other.placa) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.ucr.sigebi.entities.SincronizarEntity[ id=" + placa + " ]";
    }
    
    //</editor-fold>
    
    
}
