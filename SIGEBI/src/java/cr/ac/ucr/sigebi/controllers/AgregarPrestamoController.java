/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.models.PrestamoModel;
import cr.ac.ucr.sigebi.commands.PrestamoCommand;
import cr.ac.ucr.sigebi.commands.ListarPrestamosCommand;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.SolicitudPrestamo;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.models.BienModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author alvaro.cascante
 */
@Controller(value = "controllerAgregarPrestamos")
@Scope("session")
public class AgregarPrestamoController extends BaseController {

    ListarBienesAgregarController listadoBienes;
    
    @Resource private BienModel bienModel;
    @Resource private PrestamoModel prestamoModel;
    
    private PrestamoCommand command;
    
    private List<SelectItem> itemsTipo;
    private List<SelectItem> itemsEntidad;
    
    private String mensajeExito;
    private String mensaje;
    
    private boolean visiblePanelBienes;
    private boolean disableEntidades;
    private boolean visibleBotonSolicitar;
    private boolean visibleBotonGuardar;
    private boolean visibleBotonAplicar;
    private boolean visibleBotonAceptar;
    private boolean visibleBotonRevisar;
    private boolean visibleBotonRechazar;
    private boolean visibleBotonAnular;
    
    public AgregarPrestamoController() {
        super();
    }
    
    private void inicializarNuevo() {
        Estado estado = this.estadoPorDominioValor(Constantes.DOMINIO_PRESTAMO, Constantes.ESTADO_PRESTAMO_CREADO);
        this.command = new PrestamoCommand(this.unidadEjecutora, estado);
        this.visibleBotonSolicitar = false;
        this.visibleBotonGuardar = false;
        this.visibleBotonAplicar = false;
        this.visibleBotonAceptar = false;
        this.visibleBotonRevisar = false;
        this.visibleBotonRechazar = false;
        this.visibleBotonAnular = false;
        inicializarDatos();
    }
    
    private void inicializarDetalle(SolicitudPrestamo prestamo) {
        this.command = new PrestamoCommand(prestamo);
        this.visibleBotonSolicitar = true;
        inicializarDatos();
        inicializarBotones();
    }

    private void inicializarDatos() {
        this.mensajeExito = new String();
        this.mensaje = new String();

        List<Tipo> tipos = this.tiposPorDominio(Constantes.DOMINIO_PRESTAMO);
        if (!tipos.isEmpty()) {
            itemsTipo = new ArrayList<SelectItem>();
            for (Tipo item : tipos) {
                this.itemsTipo.add(new SelectItem(item.getId(), item.getNombre()));
            }
        }
    }
    
    private void inicializarBotones() {
        // TODO definir los estados para un prestamo y los botones que deben estar disponibles
        this.visibleBotonGuardar = true;
        this.visibleBotonAplicar = true;
        this.visibleBotonAceptar = true;
        this.visibleBotonRevisar = true;
        this.visibleBotonRechazar = true;
        this.visibleBotonAnular = true;
    }
    
    public void guardarDatos() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            UIViewRoot root = context.getViewRoot();
            UIInput component =  new UIInput();
            String messageValidacion = validarForm(root, component);
            if (Constantes.OK.equals(messageValidacion)) {
                Tipo tipo = this.tipoPorId(command.getIdTipoEntidad());
                
                // Almaceno o actualizo Solicitud
                this.prestamoModel.salvar(command.getPrestamo(tipo));
                this.prestamoModel.eliminarDetalles(command.getDetallesEliminar());
                
                List<Bien> listBienes = new ArrayList<Bien>(command.getBienes().values());
                List<Bien> listBienesEliminar = new ArrayList<Bien>(command.getBienesEliminar());
                this.bienModel.actualizar(listBienes);
                this.bienModel.actualizar(listBienesEliminar);
                
                if (command.getId() == null || command.getId() == 0) {
                    mensajeExito = "Los datos se salvaron con éxito.";
                } else {
                    mensajeExito = "Los datos se actualizaron con éxito.";
                }
            } else {
                component.setValid(false);
                Mensaje.agregarErrorAdvertencia(messageValidacion);
            }
        } catch (FWExcepcion err) {
            mensaje = err.getMessage();
        } catch (Exception ex) {
            Logger.getLogger(AgregarPrestamoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void solicitarPrestamo() {
        
    }
    
    public void aceptarPrestamo() {

    }
    
    public void revisarDatos() {

    }
    
    public void rechazarPrestamo() {

    }
    
    public void anularPrestamo() {

    }
    
    public void nuevoRegistro(ActionEvent event) {
        try{
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            inicializarNuevo();
            this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
            Util.navegar(Constantes.VISTA_PRESTAMO_NUEVO);
        } catch (FWExcepcion err) {
            mensaje = err.getMessage();
        }
    }
    
    public void detalleRegistro(ActionEvent event) {
        try{
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            
            Long id = (Long)event.getComponent().getAttributes().get(ListarPrestamosCommand.KEY_PRESTAMO);
            SolicitudPrestamo prestamo = prestamoModel.buscarPorId(id);
            prestamo.setDetalles(prestamoModel.listarDetalles(prestamo));
            inicializarDetalle(prestamo);
            this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
            Util.navegar(Constantes.VISTA_EXCLUSION_NUEVA);
        } catch (FWExcepcion err) {
            mensaje = err.getMessage();
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Validaciones">
    public String validarForm(UIViewRoot root, UIInput component) {
        if (command.getBienes().isEmpty()) {
            component = (UIInput) root. findComponent("frmDetallePrestamo:lstBienes");
            return Util.getEtiquetas("sigebi.error.controllerAgregarPrestamos.error.bienes.nulo");
        }
        return Constantes.OK;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Bienes">
    public void mostarPanelAgregarBienes() {
        this.setVisiblePanelBienes(true);
    }

    public void cerrarPanelAgregarBienes() {
        this.setVisiblePanelBienes(false);
        Estado estadoEnSolicitud = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_PRESTAMO );        
        for (Map.Entry<Long, Boolean> entry : this.listadoBienes.getBienesSeleccionados().entrySet()) {
            if (entry.getValue()) {
                Bien bien = this.listadoBienes.getBienes().get(entry.getKey());
                bien.setEstadoInterno(estadoEnSolicitud);
                this.command.getBienes().put(bien.getId(), bien);
                this.command.getBienesAgregar().add(bien);
            }
        }
    }
    
    public void eliminarBien(ActionEvent event) {
        Long idBien = (Long) event.getComponent().getAttributes().get("bienSeleccionado");
        Bien bien = this.command.getBienes().get(idBien);
        
        Estado estadoInternoNormal = estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_NORMAL);
        bien.setEstadoInterno(estadoInternoNormal);
        this.command.getBienesEliminar().add(bien); // Lo agrego a la lista de bienes a eliminar
        this.command.getBienes().remove(idBien);    // Lo saco de la lista de bienes que se muestran en pantalla
        if (this.command.getDetalles().containsKey(bien.getId())) { // Si esta en la lista de detalles, es xq se trata de un detalle existente en la BD
            this.command.getDetallesEliminar().add(this.command.getDetalles().get(bien.getId()));   // Lo agrego a la lista de detalles a eliminar
            this.command.getDetalles().remove(bien.getId());    // Lo elimino de la lista de detalles, esto ahorita no sirve
        }                                                       // xq la version de hibernate no permite actualizar colecciones
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Cargar Entidades">
    public void cambioTipoEntidad(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        cargarEntidades(this.tipoPorId(command.getIdTipoEntidad()));
    }
    
    private void cargarEntidades(Tipo tipo) {
        try {
            if (Constantes.DEFAULT_ID.equals(command.getId())) {
                itemsEntidad.clear();
                this.setDisableEntidades(true);
            } else {
                switch(tipo.getValor()) {
                    case Constantes.ESTADO_PRESTAMO_TIPO_ENTIDAD_UCR:
                        itemsEntidad = new ArrayList<SelectItem>();
                        // TODO consultar diferentes entidades
                        break;
                        
                }
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.cargarSubCategorias"));
        }

    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public ListarBienesAgregarController getListadoBienes() {
        return listadoBienes;
    }

    public void setListadoBienes(ListarBienesAgregarController listadoBienes) {
        this.listadoBienes = listadoBienes;
    }

    public PrestamoCommand getCommand() {
        return command;
    }

    public void setCommand(PrestamoCommand command) {
        this.command = command;
    }

    public List<SelectItem> getItemsTipo() {
        return itemsTipo;
    }

    public void setItemsTipo(List<SelectItem> itemsTipo) {
        this.itemsTipo = itemsTipo;
    }

    public List<SelectItem> getItemsEntidad() {
        return itemsEntidad;
    }

    public void setItemsEntidad(List<SelectItem> itemsEntidad) {
        this.itemsEntidad = itemsEntidad;
    }

    public String getMensajeExito() {
        return mensajeExito;
    }

    public void setMensajeExito(String mensajeExito) {
        this.mensajeExito = mensajeExito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isVisiblePanelBienes() {
        return visiblePanelBienes;
    }

    public void setVisiblePanelBienes(boolean visiblePanelBienes) {
        this.visiblePanelBienes = visiblePanelBienes;
    }

    public boolean isDisableEntidades() {
        return disableEntidades;
    }

    public void setDisableEntidades(boolean disableEntidades) {
        this.disableEntidades = disableEntidades;
    }

    public boolean isVisibleBotonSolicitar() {
        return visibleBotonSolicitar;
    }

    public void setVisibleBotonSolicitar(boolean visibleBotonSolicitar) {
        this.visibleBotonSolicitar = visibleBotonSolicitar;
    }

    public boolean isVisibleBotonGuardar() {
        return visibleBotonGuardar;
    }

    public void setVisibleBotonGuardar(boolean visibleBotonGuardar) {
        this.visibleBotonGuardar = visibleBotonGuardar;
    }

    public boolean isVisibleBotonAplicar() {
        return visibleBotonAplicar;
    }

    public void setVisibleBotonAplicar(boolean visibleBotonAplicar) {
        this.visibleBotonAplicar = visibleBotonAplicar;
    }

    public boolean isVisibleBotonAceptar() {
        return visibleBotonAceptar;
    }

    public void setVisibleBotonAceptar(boolean visibleBotonAceptar) {
        this.visibleBotonAceptar = visibleBotonAceptar;
    }

    public boolean isVisibleBotonRevisar() {
        return visibleBotonRevisar;
    }

    public void setVisibleBotonRevisar(boolean visibleBotonRevisar) {
        this.visibleBotonRevisar = visibleBotonRevisar;
    }

    public boolean isVisibleBotonRechazar() {
        return visibleBotonRechazar;
    }

    public void setVisibleBotonRechazar(boolean visibleBotonRechazar) {
        this.visibleBotonRechazar = visibleBotonRechazar;
    }

    public boolean isVisibleBotonAnular() {
        return visibleBotonAnular;
    }

    public void setVisibleBotonAnular(boolean visibleBotonAnular) {
        this.visibleBotonAnular = visibleBotonAnular;
    }
    //</editor-fold>
}