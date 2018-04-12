/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.Autorizacion;
import cr.ac.ucr.sigebi.domain.AutorizacionRol;
import cr.ac.ucr.sigebi.domain.Rol;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.utils.Constantes;

/**
 *
 * @author jairo.cisneros
 */
public class GestionProcesoCommand {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    //Se define la accion a realizar
    Integer accion = 0;
    Boolean presentarPanel = false;

    //Valores de formulario
    AutorizacionRol autorizacionRol;
    Autorizacion autorizacion;
    Tipo tipoProceso;

    Autorizacion autorizacionNew;
    AutorizacionRol autorizacionRolNew;

    //Filtros para el usuario
    String idUsuario;
    String nombreCompleto;
    String correo;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public GestionProcesoCommand() {
        super();
        autorizacion = new Autorizacion();
        tipoProceso = new Tipo();
        autorizacionRol = new AutorizacionRol();
        
        autorizacionNew = new Autorizacion();
        autorizacionRolNew = new AutorizacionRol();
        autorizacionRolNew.setRol(new Rol());
        
        idUsuario = null;
        nombreCompleto = null;
        correo = null;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">
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

    public Tipo getTipoProceso() {
        return tipoProceso;
    }

    public void setTipoProceso(Tipo tipoProceso) {
        this.tipoProceso = tipoProceso;
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

    public AutorizacionRol getAutorizacionRol() {
        return autorizacionRol;
    }

    public void setAutorizacionRol(AutorizacionRol autorizacionRol) {
        this.autorizacionRol = autorizacionRol;
    }

    public Autorizacion getAutorizacionNew() {
        return autorizacionNew;
    }

    public void setAutorizacionNew(Autorizacion autorizacionNew) {
        this.autorizacionNew = autorizacionNew;
    }

    public AutorizacionRol getAutorizacionRolNew() {
        return autorizacionRolNew;
    }

    public void setAutorizacionRolNew(AutorizacionRol autorizacionRolNew) {
        this.autorizacionRolNew = autorizacionRolNew;
    }


    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
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
