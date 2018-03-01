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

    @Resource
    private BienModel bienModel;
    
    @Resource
    private PrestamoModel prestamoModel;
    
    private PrestamoCommand command;
    
    private List<SelectItem> itemsTipo;
    private List<SelectItem> itemsEntidad;
    private List<Bien> bienes;
    private Map<Long, Boolean> bienesSeleccionados;
    
    private String mensajeExito;
    private String mensaje;
    
    private boolean visiblePanelBienes;
    private boolean visibleBotonSolicitar;
    private boolean disableEntidades;
    
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
        inicializarDatos();
    }
    
    private void inicializarDetalle(SolicitudPrestamo prestamo) {
        this.command = new PrestamoCommand(prestamo, prestamoModel.listarDetalles(prestamo));
        this.visibleBotonSolicitar = true;
        inicializarDatos();
        inicializarBotones();
    }

    private void inicializarDatos() {
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
                this.prestamoModel.salvar(command.getPrestamo(tipo));
                this.bienModel.actualizar(command.getBienes());
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
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            UIViewRoot root = context.getViewRoot();
            UIInput component = new UIInput();
            String messageValidacion = validarForm(root, component);
            if (Constantes.OK.equals(messageValidacion)) {
                Tipo tipo = this.tipoPorId(command.getIdTipoEntidad());
                Estado estadoSolicitar = this.estadoPorDominioValor(Constantes.DOMINIO_EXCLUSION, Constantes.ESTADO_EXCLUSION_SOLICITADA);
                this.command.setEstado(estadoSolicitar);
                this.prestamoModel.salvar(command.getPrestamo(tipo));
                mensajeExito = "Prestamo solicitada.";
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
            Util.navegar(Constantes.VISTA_EXCLUSION_NUEVA);
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
            Estado estado = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_INTERNO_BIEN_NORMAL);
            this.bienes = bienModel.listarPorUnidadEjecutoraEstado(unidadEjecutora, estado);
            
            bienesSeleccionados = new HashMap<Long, Boolean>();
            this.setVisiblePanelBienes(true);
    }

    public void cerrarPanelAgregarBienes() {
        Estado estadoEnSolicitud = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_INTERNO_BIEN_EXCLUSION );
        for (Bien bien : bienes) {
            if (bienesSeleccionados.get(bien.getId())) {
                bien.setEstado(estadoEnSolicitud);
                command.getBienes().add(bien);
            }
        }
        this.setVisiblePanelBienes(false);
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

    public List<Bien> getBienes() {
        return bienes;
    }

    public void setBienes(List<Bien> bienes) {
        this.bienes = bienes;
    }

    public Map<Long, Boolean> getBienesSeleccionados() {
        return bienesSeleccionados;
    }

    public void setBienesSeleccionados(Map<Long, Boolean> bienesSeleccionados) {
        this.bienesSeleccionados = bienesSeleccionados;
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

    public boolean isVisibleBotonSolicitar() {
        return visibleBotonSolicitar;
    }

    public void setVisibleBotonSolicitar(boolean visibleBotonSolicitar) {
        this.visibleBotonSolicitar = visibleBotonSolicitar;
    }
    
    public boolean isDisableEntidades() {
        return disableEntidades;
    }

    public void setDisableEntidades(boolean disableEntidades) {
        this.disableEntidades = disableEntidades;
    }
    //</editor-fold>
}