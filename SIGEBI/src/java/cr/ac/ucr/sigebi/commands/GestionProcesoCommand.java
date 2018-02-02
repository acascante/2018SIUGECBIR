/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.Autorizacion;
import cr.ac.ucr.sigebi.domain.Rol;
import cr.ac.ucr.sigebi.utils.Constantes;

/**
 *
 * @author jairo.cisneros
 */
public class GestionProcesoCommand {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    Long idTipoProceso;
    Long idAutorizacionTipoProceso;
    Long idRol;

    //Busqueda listado de usuarios
    String idUsuario;
    String nombreCompleto;
    String correo;

    //Se define la accion a realizar
    Integer accion = 0;
    Boolean presentarPanel = false;

    //Valores de formulario
    Autorizacion autorizacion;
    Rol rol;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public GestionProcesoCommand() {
        super();
        idTipoProceso = -1L;
        idAutorizacionTipoProceso = -1L;
        idRol = -1L;
        idUsuario = null;
        nombreCompleto = null;
        correo = null;
        autorizacion = new Autorizacion();
        rol = new Rol();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">
    public Long getIdTipoProceso() {
        return idTipoProceso;
    }

    public void setIdTipoProceso(Long idTipoProceso) {
        this.idTipoProceso = idTipoProceso;
    }

    public Long getIdAutorizacionTipoProceso() {
        return idAutorizacionTipoProceso;
    }

    public void setIdAutorizacionTipoProceso(Long idAutorizacionTipoProceso) {
        this.idAutorizacionTipoProceso = idAutorizacionTipoProceso;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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

    public Autorizacion getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(Autorizacion autorizacion) {
        this.autorizacion = autorizacion;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Boolean getPresentarPanelAgregarAutorizacion() {
        return this.presentarPanel && this.accion.equals(Constantes.ACCION_PROCESO_AGREGA_DOCUMENTO);
    }

    public Boolean getPresentarPanelModificarAutorizacion() {
        return this.presentarPanel && this.accion.equals(Constantes.ACCION_PROCESO_MODIFICAR_DOCUMENTO);
    }

    public Boolean getPresentarPanelAgregarRol() {
        return this.presentarPanel && this.accion.equals(Constantes.ACCION_PROCESO_AGREGA_ROL);
    }

    public Boolean getPresentarPanelModificarRol() {
        return this.presentarPanel && this.accion.equals(Constantes.ACCION_PROCESO_MODIFICAR_ROL);
    }

    //</editor-fold>
}
