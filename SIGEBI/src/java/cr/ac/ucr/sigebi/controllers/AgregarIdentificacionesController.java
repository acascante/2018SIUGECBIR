/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.commands.IdentificacionCommand;
import cr.ac.ucr.sigebi.domain.AutorizacionRolPersona;
import cr.ac.ucr.sigebi.domain.Identificacion;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.models.AutorizacionRolPersonaModel;
import cr.ac.ucr.sigebi.models.IdentificacionModel;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author alvaro.cascante
 */
@Controller(value = "controllerAgregarIdentificaciones")
@Scope("session")
public class AgregarIdentificacionesController extends BaseController {
    
    @Resource private AutorizacionRolPersonaModel autorizacionRolPersonaModel;
    @Resource private IdentificacionModel identificacionModel;
    
    private IdentificacionCommand command;
    
    private String mensaje;

    private boolean visiblePanelConfirmacion;
    private boolean visibleListaIdentificaciones;
    private boolean usuarioAdministrador;
  
    public AgregarIdentificacionesController() {
        super();
        this.inicializarDatos();
    }

    @PostConstruct
    public final void inicializar() {
        //Cargar Tipos
        List<Tipo> tipos = this.tiposPorDominio(Constantes.DOMINIO_IDENTIFICACION);
        if (!tipos.isEmpty()) {
            for (Tipo tipo : tipos) {
                this.command.getItemsTipo().add(new SelectItem(tipo.getId(), tipo.getNombre()));
                if (tipo.getNombre().equals(Constantes.IDENTIFICACION_TIPO_ID_UNIDAD)) {
                    this.command.setIdTipo(tipo.getId());
                }
            }
        }
        
        this.command.setEstadoNuevo(this.estadoPorDominioValor(Constantes.DOMINIO_IDENTIFICACION, Constantes.IDENTIFICACION_ESTADO_RESERVADA_UNIDAD));
        
        this.buscarUltimoRegistro();
        
        AutorizacionRolPersona administrador = autorizacionRolPersonaModel.buscar(Constantes.CODIGO_AUTORIZACION_ADMINISTRADOR, Constantes.CODIGO_ROL_ADMINISTRADOR_AUTORIZACION_ADMINISTRADOR, usuarioSIGEBI, unidadEjecutora);
        usuarioAdministrador = administrador == null ? false : true;
    }
    
    public void buscarUltimoRegistroListener(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        buscarUltimoRegistro();
    }
    
    private void buscarUltimoRegistro() {
        this.command.setUltimoRegistro(identificacionModel.buscarUltimoRegistro(this.command.getIdTipo()));
    }
    
    private void inicializarDatos() {
        this.mensaje = new String();
        this.command = new IdentificacionCommand();
    }
        
    public void agregarIdentificaciones() {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        String messageValidacion = validarForm(root);
        if (Constantes.OK.equals(messageValidacion)) {
            this.visiblePanelConfirmacion = true;
        } else {
            Mensaje.agregarErrorAdvertencia(messageValidacion);
        }
    }
    
    public void agregarIdentificacionesAceptar() {
        try {
            this.command.getIdentificacionesExistentes().clear();
            Tipo tipo = this.tipoPorId(command.getIdTipo());
            List<Identificacion> identificaciones = this.command.getIdentificaciones(tipo, unidadEjecutora);
            Identificacion ultimaIdentificacion = null;
            for (Identificacion identificacion : identificaciones) {
                if (!identificacionModel.almacenar(identificacion)) {
                    this.command.getIdentificacionesExistentes().add(identificacion);
                }
                ultimaIdentificacion = identificacion;
            }
            this.visiblePanelConfirmacion = false;
            if (this.command.getIdentificacionesExistentes().isEmpty()) {
                Mensaje.agregarInfo("Datos almacenados exitosamente");
                visibleListaIdentificaciones = false;
            } else {
                Mensaje.agregarInfo("Proceso finalizado exitosamente, verifique los datos almacenados");
                visibleListaIdentificaciones = true;
            }
            if (ultimaIdentificacion != null) {
                this.command.setUltimoRegistro(ultimaIdentificacion);
            }
        } catch (FWExcepcion err) {
            this.mensaje = err.getMessage();
        }
    }
    
    public void agregarIdentificacionesCancelar() {
        this.visiblePanelConfirmacion = false;
    }
    
    public String validarForm(UIViewRoot root) {
        if (this.command.getRangoInicial() == null || this.command.getRangoFinal() == null) {
            return Util.getEtiquetas("sigebi.label.identificaciones.error.rango.nulo");
        } else if (this.command.getRangoInicial() > this.command.getRangoFinal()) {
            return Util.getEtiquetas("sigebi.label.identificaciones.error.rango.invalido");
        }
        return Constantes.OK;
    }
     
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public IdentificacionCommand getCommand() {
        return command;
    }

    public void setCommand(IdentificacionCommand command) {
        this.command = command;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isVisiblePanelConfirmacion() {
        return visiblePanelConfirmacion;
    }

    public void setVisiblePanelConfirmacion(boolean visiblePanelConfirmacion) {
        this.visiblePanelConfirmacion = visiblePanelConfirmacion;
    }

    public boolean isVisibleListaIdentificaciones() {
        return visibleListaIdentificaciones;
    }

    public void setVisibleListaIdentificaciones(boolean visibleListaIdentificaciones) {
        this.visibleListaIdentificaciones = visibleListaIdentificaciones;
    }
    
    public boolean isUsuarioAdministrador() {
        return usuarioAdministrador;
    }

    public void setUsuarioAdministrador(boolean usuarioAdministrador) {
        this.usuarioAdministrador = usuarioAdministrador;
    }
    //</editor-fold>
}