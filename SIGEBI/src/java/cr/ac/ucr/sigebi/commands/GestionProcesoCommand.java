/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.entities.DocumentoEntity;
import cr.ac.ucr.sigebi.entities.DocumentoRolEntity;
import cr.ac.ucr.sigebi.entities.RolEntity;
import cr.ac.ucr.sigebi.utils.Constantes;

/**
 *
 * @author jairo.cisneros
 */
public class GestionProcesoCommand {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    Integer idTipoProceso;
    Long idDocumentoTipoProceso;
    Long idRol;

    //Busqueda listado de usuarios
    String idUsuario;
    String nombreCompleto;
    String correo;

    //Se define la accion a realizar
    Integer accion = 0;
    Boolean presentarPanel = false;

    //Valores de formulario
    DocumentoEntity documentoEntity;
    RolEntity rolEntity;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public GestionProcesoCommand() {
        super();
        idTipoProceso = -1;
        idDocumentoTipoProceso = -1L;
        idRol = -1L;
        idUsuario = null;
        nombreCompleto = null;
        correo = null;
        documentoEntity= new DocumentoEntity();
        rolEntity = new RolEntity();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">

    public DocumentoEntity getDocumentoEntity() {
        return documentoEntity;
    }

    public void setDocumentoEntity(DocumentoEntity documentoEntity) {
        this.documentoEntity = documentoEntity;
    }

    public RolEntity getRolEntity() {
        return rolEntity;
    }

    public void setRolEntity(RolEntity rolEntity) {
        this.rolEntity = rolEntity;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public Integer getIdTipoProceso() {
        return idTipoProceso;
    }

    public void setIdTipoProceso(Integer idTipoProceso) {
        this.idTipoProceso = idTipoProceso;
    }

    public Long getIdDocumentoTipoProceso() {
        return idDocumentoTipoProceso;
    }

    public void setIdDocumentoTipoProceso(Long idDocumentoTipoProceso) {
        this.idDocumentoTipoProceso = idDocumentoTipoProceso;
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

    public Boolean getPresentarPanelAgregarDocumento() {
        return this.presentarPanel && this.accion.equals(Constantes.ACCION_PROCESO_AGREGA_DOCUMENTO);
    }

    public Boolean getPresentarPanelModificarDocumento() {
        return this.presentarPanel && this.accion.equals(Constantes.ACCION_PROCESO_MODIFICAR_DOCUMENTO);
    }

    public Boolean getPresentarPanelAgregarRol() {
        return this.presentarPanel && this.accion.equals(Constantes.ACCION_PROCESO_AGREGA_ROl);
    }

    public Boolean getPresentarPanelModificarRol() {
        return this.presentarPanel && this.accion.equals(Constantes.ACCION_PROCESO_MODIFICAR_ROL);
    }

    //</editor-fold>
}
