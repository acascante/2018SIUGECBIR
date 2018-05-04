/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.framework.vista.util.PaginacionOracle;
import cr.ac.ucr.sigebi.controllers.BaseController;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.domain.Usuario;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author jorge.serrano
 */
public class ResponsableCommand extends BaseController{

    
    //<editor-fold defaultstate="collapsed" desc="class Usiario Command">
    
    public class UsuarioCommand extends PaginacionOracle{

        private String filtroIdUdsuario;
        private String filtroNombre;
        private String usuarioSeleccionado;

        private Usuario usuario;

        
        private List<Usuario> usuarios;  // Bienes a eliminar
        
        private UsuarioCommand() {
            super();
            this.usuario = new Usuario();
            this.filtroIdUdsuario = "";
            this.filtroNombre = "";
            
            ArrayList<SelectItem> cantPorPaginas = new ArrayList<SelectItem>();
            cantPorPaginas.add(new SelectItem(5, "5"));
            cantPorPaginas.add(new SelectItem(10, "10"));
            this.setListaRegistrosPagina(cantPorPaginas);
        }

        private UsuarioCommand(Usuario usuario) {
            this.usuario = usuario;
            this.usuarioSeleccionado = usuario.getId() + " ~ " + usuario.getNombreCompleto();

            ArrayList<SelectItem> cantPorPaginas = new ArrayList<SelectItem>();
            cantPorPaginas.add(new SelectItem(5, "5"));
            cantPorPaginas.add(new SelectItem(10, "10"));
            this.setListaRegistrosPagina(cantPorPaginas);

        }

        //<editor-fold defaultstate="collapsed" desc="GET's y SET's">

        public List<Usuario> getUsuarios() {
            return usuarios;
        }

        public void setUsuarios(List<Usuario> usuarios) {
            this.usuarios = usuarios;
        }

        
        
        public String getFiltroIdUdsuario() {
            return filtroIdUdsuario;
        }

        public void setFiltroIdUdsuario(String filtroIdUdsuario) {
            this.filtroIdUdsuario = filtroIdUdsuario;
        }

        public String getFiltroNombre() {
            return filtroNombre;
        }

        public void setFiltroNombre(String filtroNombre) {
            this.filtroNombre = filtroNombre;
        }

        public String getUsuarioSeleccionado() {
            return usuarioSeleccionado;
        }

        public void setUsuarioSeleccionado(String usuarioSeleccionado) {
            this.usuarioSeleccionado = usuarioSeleccionado;
        }

        public Usuario getUsuario() {
            return usuario;
        }

        public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
            this.usuarioSeleccionado = usuario.getId() + " ~ " + usuario.getNombreCompleto();
        }
        
        
        
        //</editor-fold>
    }

    //</editor-fold>
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    private UnidadEjecutora unidadEjecutora;
    private UsuarioCommand usuarioCommand; 
    
    private Boolean visiblePanelUsuarios;
    private Boolean visiblePanelConfirmacion;
    
    private Estado estadoAsignacionPendiente;
    private Estado estadoAsignacionAsignado;
    private String mensajeConfirmar;
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    
    public ResponsableCommand() {
        usuarioCommand = new UsuarioCommand();
        visiblePanelUsuarios = false;
        visiblePanelConfirmacion = false;
        
        estadoAsignacionPendiente = this.estadoPorDominioValor(Constantes.DOMINIO_ASIGNAR_RESPONSABLE, Constantes.ESTADO_ASIGNAR_RESPONSABLE_PENDIENTE);
        estadoAsignacionAsignado = this.estadoPorDominioValor(Constantes.DOMINIO_ASIGNAR_RESPONSABLE, Constantes.ESTADO_ASIGNAR_RESPONSABLE_ASIGNADO);
    }
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="SETs & GETs">

    public String getMensajeConfirmar() {
        return mensajeConfirmar;
    }

    public void setMensajeConfirmar(String mensajeConfirmar) {
        this.mensajeConfirmar = mensajeConfirmar;
    }
    
    public Boolean getVisiblePanelConfirmacion() {
        return visiblePanelConfirmacion;
    }

    public void setVisiblePanelConfirmacion(Boolean visiblePanelConfirmacion) {
        this.visiblePanelConfirmacion = visiblePanelConfirmacion;
    }
    
    public Estado getEstadoAsignacionPendiente(){
        return estadoAsignacionPendiente;
    }

    public void setEstadoAsignacionPendiente(Estado estadoAsignacionPendiente) {
        this.estadoAsignacionPendiente = estadoAsignacionPendiente;
    }

    public Estado getEstadoAsignacionAsignado() {
        return estadoAsignacionAsignado;
    }

    public void setEstadoAsignacionAsignado(Estado estadoAsignacionAsignado) {
        this.estadoAsignacionAsignado = estadoAsignacionAsignado;
    }

    public Boolean getVisiblePanelUsuarios() {
        return visiblePanelUsuarios;
    }

    public void setVisiblePanelUsuarios(Boolean visiblePanelUsuarios) {
        this.visiblePanelUsuarios = visiblePanelUsuarios;
    }

    public void limpiarUsuarioCommand() {
        usuarioCommand = new UsuarioCommand();
    }
    
    public UnidadEjecutora getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(UnidadEjecutora unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }

    public UsuarioCommand getUsuarioCommand() {
        return usuarioCommand;
    }

    public void setUsuarioCommand(UsuarioCommand usuarioCommand) {
        this.usuarioCommand = usuarioCommand;
    }
    
    
    
    //</editor-fold>

    
    
}


