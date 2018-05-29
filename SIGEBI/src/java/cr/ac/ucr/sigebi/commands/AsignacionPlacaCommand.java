/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.AsignacionPlaca;
import cr.ac.ucr.sigebi.utils.Constantes;

/**
 *
 * @author jairo.cisneros
 */
public class AsignacionPlacaCommand {

    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    private AsignacionPlaca asignacionPlaca;
    private Long cantidadDisponible;
    
    //Se define la accion a realizar
    Integer accion = 0;
    Boolean presentarPanel = false;
    String mensajeConfirmacion;
    String observacionConfirmacion;
    boolean permiteModificar = false;
    boolean yaRegistrada = false;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public AsignacionPlacaCommand(Long cantidadDisponible) {
        this.asignacionPlaca = new AsignacionPlaca();
        this.cantidadDisponible = cantidadDisponible;
    }

    public AsignacionPlacaCommand(AsignacionPlaca asignacionPlaca, Long cantidadDisponible) {
        this.asignacionPlaca = asignacionPlaca; 
        this.cantidadDisponible = cantidadDisponible;
    }
    
    public String getObservacionConfirmacion() {
        return observacionConfirmacion;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get and Set">

    public boolean isPermiteModificar() {
        return permiteModificar;
    }

    public void setPermiteModificar(boolean permiteModificar) {
        this.permiteModificar = permiteModificar;
    }
    
    public boolean isYaRegistrada() {
        return yaRegistrada;
    }

    public void setYaRegistrada(boolean yaRegistrada) {
        this.yaRegistrada = yaRegistrada;
    }
    
    public void setObservacionConfirmacion(String observacionConfirmacion) {    
        this.observacionConfirmacion = observacionConfirmacion;
    }

    public AsignacionPlaca getAsignacionPlaca() {
        return asignacionPlaca;
    }

    public Integer getAccion() {
        return accion;
    }

    public String getMensajeConfirmacion() {
        return mensajeConfirmacion;
    }

    public void setMensajeConfirmacion(String mensajeConfirmacion) {
        this.mensajeConfirmacion = mensajeConfirmacion;
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

    public void setAsignacionPlaca(AsignacionPlaca asignacionPlaca) {
        this.asignacionPlaca = asignacionPlaca;
    }

    public Long getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(Long cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public Boolean getPresentarPanelRechazar() {
        return this.presentarPanel && this.accion.equals(Constantes.ACCION_ASIGNACION_PLACA_RECHAZAR);
    }

    public Boolean getPresentarPanelConfirmar() {
        return this.presentarPanel && this.accion.equals(Constantes.ACCION_ASIGNACION_PLACA_ACEPTAR);
    }
    
    public Boolean getPermiteAprobarRechazar() {
        return this.yaRegistrada && this.permiteModificar;
    }

    public Boolean getPermiteReporte() {
        return this.yaRegistrada && this.getAsignacionPlaca().getEstado().getValor().equals(Constantes.ESTADO_ASIGNACION_PLACA_FINALIZADA);        
    }

    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
}
