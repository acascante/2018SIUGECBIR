/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.Adjunto;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Factura;
import cr.ac.ucr.sigebi.domain.Pais;
import cr.ac.ucr.sigebi.domain.SolicitudAutorizacion;
import cr.ac.ucr.sigebi.domain.SolicitudDetalle;
import cr.ac.ucr.sigebi.domain.SolicitudDonacion;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.model.SelectItem;

/**
 *
 * @author jairo.cisneros
 */
public class SolicitudDonacionCommand {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    //---------------------------------------------------------
    //Datos de la pantalla
    //---------------------------------------------------------
    SolicitudDonacion solicitudDonacion = null;
    Bien bien = null;
    Factura factura = null;
    Adjunto adjunto = null;
    String mensajeConfirmacion = "";

    //Mapa para la lista de autorizaciones para la solicitud
    HashMap<Long, SolicitudDetalle> bienesDonacion = null;

    //Mapa para la lista de facturas para la solicitud
    HashMap<Long, Factura> facturasDonacion = null;

    //Mapa para la lista de adjuntos para la solicitud
    HashMap<Long, Adjunto> adjuntosDonacion = null;

    //Mapa para la lista de solicitudes
    HashMap<String, SolicitudAutorizacion> solicitudesAutorizacionesPorRol = null;

    String observacion = "";
    
    //---------------------------------------------------------
    //Mapas y selectItem para la busqueda de los datos
    //---------------------------------------------------------
    //Paises
    private Map<Long, Pais> paisesDonacion;
    List<SelectItem> paisOptions;

    // Tipo de donacion
    List<SelectItem> tipoDonacionOptions;

    //Unidades receptoras
    List<UnidadEjecutora> unidadEjecutoras;

    //Se define la accion a realizar
    Integer accion = 0;
    Boolean presentarPanel = false;

    //---------------------------------------------------------
    //Constantes
    //---------------------------------------------------------
    Tipo tipoAdjunto;

    //---------------------------------------------------------
    //Filtros
    //---------------------------------------------------------
    String fltIdUnidad = "";
    String fltDescripcionUnidad = "";

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public SolicitudDonacionCommand(UnidadEjecutora unidadEjecutora) {
        super();
        this.initGeneral();

        this.solicitudDonacion = new SolicitudDonacion(unidadEjecutora);
        this.solicitudDonacion.setTipoDonacion(new Tipo());
        this.solicitudDonacion.setUnidadReceptora(new UnidadEjecutora());
        this.solicitudDonacion.setPais(new Pais());
    }

    public SolicitudDonacionCommand(SolicitudDonacion solicitudDonacion) {
        super();
        this.initGeneral();

        this.solicitudDonacion = solicitudDonacion;
        this.solicitudDonacion.getTipoDonacion().setIdTemporal(solicitudDonacion.getTipoDonacion().getId());
        this.solicitudDonacion.getUnidadReceptora().setIdTemporal(solicitudDonacion.getUnidadReceptora().getIdTemporal());
        this.solicitudDonacion.getPais().setIdTemporal(solicitudDonacion.getPais().getId());
    }

    private void initGeneral() {

        //Para incluir nuevos registros en las listas
        this.bien = new Bien();
        this.factura = new Factura();
        this.adjunto = new Adjunto();

        //Se crean los mapas
        this.bienesDonacion = new HashMap<Long, SolicitudDetalle>();
        this.facturasDonacion = new HashMap<Long, Factura>();
        this.adjuntosDonacion = new HashMap<Long, Adjunto>();
        this.paisesDonacion = new HashMap<Long, Pais>();
        this.solicitudesAutorizacionesPorRol = new HashMap<String, SolicitudAutorizacion>();

        //Se crean las lista para las opciones
        this.tipoDonacionOptions = new ArrayList<SelectItem>();
        this.paisOptions = new ArrayList<SelectItem>();
    }

    public String getFltIdUnidad() {
        return fltIdUnidad;
    }

    public void setFltIdUnidad(String fltIdUnidad) {
        this.fltIdUnidad = fltIdUnidad;
    }

    public String getFltDescripcionUnidad() {
        return fltDescripcionUnidad;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">
    public void setFltDescripcionUnidad(String fltDescripcionUnidad) {
        this.fltDescripcionUnidad = fltDescripcionUnidad;
    }

    public SolicitudDonacion getSolicitudDonacion() {
        return solicitudDonacion;
    }

    public void setSolicitudDonacion(SolicitudDonacion solicitudDonacion) {
        this.solicitudDonacion = solicitudDonacion;
    }

    public Bien getBien() {
        return bien;
    }

    public String getMensajeConfirmacion() {
        return mensajeConfirmacion;
    }

    public void setMensajeConfirmacion(String mensajeConfirmacion) {
        this.mensajeConfirmacion = mensajeConfirmacion;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<UnidadEjecutora> getUnidadEjecutoras() {
        return unidadEjecutoras;
    }

    public void setUnidadEjecutoras(List<UnidadEjecutora> unidadEjecutoras) {
        this.unidadEjecutoras = unidadEjecutoras;
    }

    public Tipo getTipoAdjunto() {
        return tipoAdjunto;
    }

    public void setTipoAdjunto(Tipo tipoAdjunto) {
        this.tipoAdjunto = tipoAdjunto;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Adjunto getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(Adjunto adjunto) {
        this.adjunto = adjunto;
    }

    public HashMap<Long, SolicitudDetalle> getBienesDonacion() {
        return bienesDonacion;
    }

    public void setBienesDonacion(HashMap<Long, SolicitudDetalle> bienesDonacion) {
        this.bienesDonacion = bienesDonacion;
    }

    public HashMap<Long, Factura> getFacturasDonacion() {
        return facturasDonacion;
    }

    public void setFacturasDonacion(HashMap<Long, Factura> facturasDonacion) {
        this.facturasDonacion = facturasDonacion;
    }

    public HashMap<Long, Adjunto> getAdjuntosDonacion() {
        return adjuntosDonacion;
    }

    public void setAdjuntosDonacion(HashMap<Long, Adjunto> adjuntosDonacion) {
        this.adjuntosDonacion = adjuntosDonacion;
    }

    public HashMap<String, SolicitudAutorizacion> getSolicitudesAutorizacionesPorRol() {
        return solicitudesAutorizacionesPorRol;
    }

    public void setSolicitudesAutorizacionesPorRol(HashMap<String, SolicitudAutorizacion> solicitudesAutorizacionesPorRol) {
        this.solicitudesAutorizacionesPorRol = solicitudesAutorizacionesPorRol;
    }

    public Map<Long, Pais> getPaisesDonacion() {
        return paisesDonacion;
    }

    public void setPaisesDonacion(Map<Long, Pais> paisesDonacion) {
        this.paisesDonacion = paisesDonacion;
    }

    public List<SelectItem> getPaisOptions() {
        return paisOptions;
    }

    public void setPaisOptions(List<SelectItem> paisOptions) {
        this.paisOptions = paisOptions;
    }

    public List<SelectItem> getTipoDonacionOptions() {
        return tipoDonacionOptions;
    }

    public void setTipoDonacionOptions(List<SelectItem> tipoDonacionOptions) {
        this.tipoDonacionOptions = tipoDonacionOptions;
    }

    public Integer getAccion() {
        return accion;
    }

    public void setAccion(Integer accion) {
        this.accion = accion;
    }

    public Boolean getPresentarPanel() {
        return presentarPanel;
    }

    public void setPresentarPanel(Boolean presentarPanel) {
        this.presentarPanel = presentarPanel;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public Boolean getPresentarPanelBuscarReceptor() {
        return this.presentarPanel && this.accion.equals(Constantes.ACCION_DONACION_BUSCAR_RECEPTOR);
    }

    public Boolean getPresentarPanelAnularConfirmar() {
        return this.presentarPanel && this.accion.equals(Constantes.ACCION_DONACION_ANULAR);
    }
    
    public ArrayList<Adjunto> getAdjuntos() {
        return new ArrayList<Adjunto>(this.adjuntosDonacion.values());
    }

    public ArrayList<SolicitudDetalle> getBienesDetalles() {
        return new ArrayList<SolicitudDetalle>(this.bienesDonacion.values());
    }

    public ArrayList<SolicitudAutorizacion> getSolicitesAprobaciones() {
        return new ArrayList<SolicitudAutorizacion>(this.solicitudesAutorizacionesPorRol.values());
    }

    public ArrayList<Factura> getFacturas() {
        return new ArrayList<Factura>(this.facturasDonacion.values());
    }

    //</editor-fold>
}
