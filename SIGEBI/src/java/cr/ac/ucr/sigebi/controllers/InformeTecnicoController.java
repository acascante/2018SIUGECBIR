/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import com.icesoft.faces.component.inputfile.FileInfo;
import com.icesoft.faces.component.inputfile.InputFile;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.domain.AutorizacionRolPersona;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.Usuario;
import cr.ac.ucr.sigebi.entities.AdjuntoEntity;
import cr.ac.ucr.sigebi.models.DocumentoRolEstadoModel;
import cr.ac.ucr.sigebi.models.EstadoModel;
import cr.ac.ucr.sigebi.models.InformeTecnicoModel;
import cr.ac.ucr.sigebi.entities.DocumentoRolEstadoEntity;
import cr.ac.ucr.sigebi.entities.InformeTecnicoEntity;
import cr.ac.ucr.sigebi.models.AdjuntoModel;
import cr.ac.ucr.sigebi.models.AutorizacionRolPersonaModel;
import cr.ac.ucr.sigebi.models.TipoModel;
import cr.ac.ucr.sigebi.models.UsuarioModel;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.model.SelectItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author jairo.cisneros
 */
@Controller(value = "controllerInformeTecnico")
@Scope("session")
public class InformeTecnicoController extends BaseController{
    
    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    @Resource
    private InformeTecnicoModel informeTecnicoModel;

    @Resource
    private TipoModel tipoModel;

    @Resource
    private DocumentoRolEstadoModel documentoRolEstadoModel;
    
    @Resource
    private AutorizacionRolPersonaModel autorizacionRolPersonaModel;

    @Resource
    private EstadoModel estadoModel;

    @Resource
    private AdjuntoModel adjuntoModel;
    
    @Resource
    UsuarioModel usuarioModel;      
    
    InformeTecnicoEntity informe;
    
    List<DocumentoRolEstadoEntity> documentosPorRol;
    List<DocumentoRolEstadoEntity> documentosPorRolPorAceptar;
    List<AdjuntoEntity> adjuntos;

    // comboBox tipos
    List<SelectItem> tipoOptions;
    List<Tipo> tipos;
    
    // Se usan en el jsp
    String detalleAdjunto = "";
    boolean aprobacionEnTramite = false;
    boolean rolPermiteModificar = false;
    
    Usuario usr;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">

    public InformeTecnicoModel getInformeTecnicoModel() {
        return informeTecnicoModel;
    }

    public void setInformeTecnicoModel(InformeTecnicoModel informeTecnicoModel) {
        this.informeTecnicoModel = informeTecnicoModel;
    }

    public AutorizacionRolPersonaModel getDocumentoRolPersonaModel() {
        return autorizacionRolPersonaModel;
    }

    public void setDocumentoRolPersonaModel(AutorizacionRolPersonaModel documentoRolPersonaModel) {
        this.autorizacionRolPersonaModel = documentoRolPersonaModel;
    }

    public TipoModel getTipoModel() {
        return tipoModel;
    }

    public void setTipoModel(TipoModel tipoModel) {
        this.tipoModel = tipoModel;
    }

    public EstadoModel getEstadoModel() {
        return estadoModel;
    }

    public void setEstadoModel(EstadoModel estadoModel) {
        this.estadoModel = estadoModel;
    }

    public AdjuntoModel getAdjuntoModel() {
        return adjuntoModel;
    }

    public void setAdjuntoModel(AdjuntoModel adjuntoModel) {
        this.adjuntoModel = adjuntoModel;
    }

    
    public DocumentoRolEstadoModel getDocumentoRolEstadoModel() {
        return documentoRolEstadoModel;
    }

    public void setDocumentoRolEstadoModel(DocumentoRolEstadoModel documentoRolEstadoModel) {
        this.documentoRolEstadoModel = documentoRolEstadoModel;
    }

    public List<AdjuntoEntity> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<AdjuntoEntity> adjuntos) {
        this.adjuntos = adjuntos;
    }

    public InformeTecnicoEntity getInforme() {
        return informe;
    }

    public void setInforme(InformeTecnicoEntity informe) {
        this.informe = informe;
    }

    public List<SelectItem> getTipoOptions() {
        return tipoOptions;
    }

    public void setTipoOptions(List<SelectItem> tipoOptions) {
        this.tipoOptions = tipoOptions;
    }

    public List<Tipo> getTipos() {
        return tipos;
    }

    public void setTipos(List<Tipo> tipos) {
        this.tipos = tipos;
    }

    public List<DocumentoRolEstadoEntity> getDocumentosPorRol() {
        return documentosPorRol;
    }

    public void setDocumentosPorRol(List<DocumentoRolEstadoEntity> documentosPorRol) {
        this.documentosPorRol = documentosPorRol;
    }

    public List<DocumentoRolEstadoEntity> getDocumentosPorRolPorAceptar() {
        return documentosPorRolPorAceptar;
    }

    public void setDocumentosPorRolPorAceptar(List<DocumentoRolEstadoEntity> documentosPorRolPorAceptar) {
        this.documentosPorRolPorAceptar = documentosPorRolPorAceptar;
    }

    public String getDetalleAdjunto() {
        return detalleAdjunto;
    }

    public void setDetalleAdjunto(String detalleAdjunto) {
        this.detalleAdjunto = detalleAdjunto;
    }

    public boolean isAprobacionEnTramite() {
        return aprobacionEnTramite;
    }

    public void setAprobacionEnTramite(boolean aprobacionEnTramite) {
        this.aprobacionEnTramite = aprobacionEnTramite;
    }

    public boolean isRolPermiteModificar() {
        return rolPermiteModificar;
    }

    public void setRolPermiteModificar(boolean rolPermiteModificar) {
        this.rolPermiteModificar = rolPermiteModificar;
    }


    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public InformeTecnicoController() {
        super();
    }
    
    @PostConstruct
    public final void inicializar() {

          //FIXME Jairo se deve verificar la consulta
          usr = usuarioModel.buscarPorId(lVistaUsuario.getgUsuarioActual().getIdUsuario());
          //Se consultan los tipos por dominio
          tipos = tipoModel.listarPorDominio(Constantes.DOMINIO_INFORME_TECNICO);
          tipoOptions = new ArrayList<SelectItem>();
          for (Tipo item : tipos) {
              tipoOptions.add(new SelectItem(item.getId().toString(), item.getNombre()));
          }    
      }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    /**
     * Abre la pantalla de detalle
     * @param pEvent
     */    
    public void verDetalleInforme(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            
            //Se obtiene el informe que se desea presentar 
            informe = (InformeTecnicoEntity) pEvent.getComponent().getAttributes().get("informeSel");
            
            //Se buscan los roles que deben aceptar el informe,
            documentosPorRol = documentoRolEstadoModel.buscarDocumentosTipoDocumento(informe.getIdInformeTecnico(), Constantes.ID_DOCUMENTO_INFORME_TECNICO);
            
            //Se buscan cuales documentos se pueden aceptar segun el usuario y rol 
            documentosPorRolPorAceptar = documentoRolEstadoModel.buscarDocumentosUsuario(lVistaUsuario.getgUsuarioActual().getIdUsuario(), 
                    unidadEjecutoraId, informe.getIdInformeTecnico(), Constantes.ID_DOCUMENTO_INFORME_TECNICO);
            for(DocumentoRolEstadoEntity documento : documentosPorRol){
                documento.setMarcado(this.permiteAceptarRechazar(documento.getIdDocumento().getIdDocumento(), documento.getIdRol().getIdRol()));
            }
            
            //Se actuaizan banderas
            cambiaEstadoInforme();
            
            //Se buscan los adjuntos del informe
            adjuntos = adjuntoModel.buscarPorReferencia(informe.getIdInformeTecnico());
            
            Util.navegar("informe_detalle");

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }  
    
    /**
     * Aprueba el informe
     * @param pEvent
     */    
    public void aprobar(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            
            //Se obtiene el documento a modificar
            DocumentoRolEstadoEntity documento = (DocumentoRolEstadoEntity) pEvent.getComponent().getAttributes().get("documentoSelApro");            
            documento.setIdEstado(estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_INFORME_TECNICO, Constantes.ESTADO_INFORME_TECNICO_APROBADO));
            documento.setFecha(new Date());
            
            //FIXME Jairo Verificar el usuario que se esta asignando
            //documento.setIdUsuarioSeguridad(usr);
            
            //Se modifica el documento
            documentoRolEstadoModel.modificar(documento);
            
            //Se modifica en el informe en los casos que aplique
            cambiaEstadoInforme();

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }
    
    /**
     * Rechaza el informe
     * @param pEvent
     */    
    public void rechazar(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            
            //Se obtiene el documento a modificar
            DocumentoRolEstadoEntity documento = (DocumentoRolEstadoEntity) pEvent.getComponent().getAttributes().get("documentoSelRech");            
            documento.setIdEstado(estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_INFORME_TECNICO, Constantes.ESTADO_INFORME_TECNICO_RECHAZADO));
            documento.setFecha(new Date());
            
            //FIXME Jairo verificar el usuario que se esta asignando
            //documento.setIdUsuarioSeguridad(usr);

            //Se modifica el documento
            documentoRolEstadoModel.modificar(documento);
            
            //Se modifica en el informe en los casos que aplique
            cambiaEstadoInforme();
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }
    
    public boolean permiteAceptarRechazar(Long idDocumento, Long idRol){
        if(!informe.getIdEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_APROBADO)
            && !informe.getIdEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_ANULADO)){
            for(DocumentoRolEstadoEntity documento : documentosPorRolPorAceptar){
                if(documento.getIdDocumento().getIdDocumento().equals(idDocumento) 
                        && documento.getIdRol().getIdRol().equals(idRol)){
                    return true;
                }
            }            
        }        
        return false;
    }  
    
    private void cambiaEstadoInforme(){
        boolean rechazar = true;
        boolean aprobar  = true;
        aprobacionEnTramite = false;
        rolPermiteModificar = false;
        
        //Solo si el informe no esta aprobado
        if(!informe.getIdEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_APROBADO)
            && !informe.getIdEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_ANULADO)){
            for (DocumentoRolEstadoEntity documento : documentosPorRol) {
                if(!documento.getIdEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_APROBADO)){
                    aprobar = false;
                }
                if(!documento.getIdEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_RECHAZADO)){
                    rechazar = false;
                }
                if (documento.getIdEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_APROBADO)) {
                    aprobacionEnTramite = true;
                }
            }

            if(aprobar){
                informe.setIdEstado(estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_INFORME_TECNICO, Constantes.ESTADO_INFORME_TECNICO_APROBADO));
                informeTecnicoModel.modificar(informe);
            }
            if(rechazar){
                informe.setIdEstado(estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_INFORME_TECNICO, Constantes.ESTADO_INFORME_TECNICO_RECHAZADO));
                informeTecnicoModel.modificar(informe);
            }    

            if (aprobacionEnTramite) {
                //Solo se permite modificar si el usuario tiene un rol de tecnico asociado al documento
                AutorizacionRolPersona autorizado = autorizacionRolPersonaModel.buscarAutorizacionPorAutorizacionRolUsuario(Constantes.CODIGO_ROL_TECNICO, 
                        Constantes.ID_DOCUMENTO_INFORME_TECNICO, lVistaUsuario.getgUsuarioActual().getIdUsuario());
                rolPermiteModificar = autorizado != null;
            } else {
                //Cualquiera puede modificar no existe ninguna aprobacion
                rolPermiteModificar = true;
            }
        }
    }
    
    /**
     * Abre la pantalla de detalle
     * @param pEvent
     */    
    public void agregarAdjunto(ActionEvent pEvent) {
        try {
            InputFile inputFile =(InputFile) pEvent.getSource();  
            FileInfo fileInfo = inputFile.getFileInfo();                
            if (fileInfo.getFileName() == null) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.informeTecnicoController.adjunto.requerido"));
            }else {
                AdjuntoEntity adjunto = new AdjuntoEntity();
                adjunto.setIdEstado(estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO));
                adjunto.setIdReferencia(informe.getIdInformeTecnico());                
                //adjunto.setIdTipo(tipoModel.buscarPorDominioTipo(Constantes.DOMINIO_TIPO_ADJUNTO, Constantes.TIPO_ADJUNTO_INFORME_TECNICO));
                //adjunto.setIdTipoDocumento(tipoModel.buscarPorDominioTipo(Constantes.DOMINIO_TIPO_DOCUMENTO, Constantes.TIPO_DOCUMENTO_INFORME_TECNICO));
                adjunto.setUrl("upload/informesTecnicos/" + fileInfo.getFileName());
                if(detalleAdjunto != null && detalleAdjunto.length() > 0){
                   adjunto.setDetalle(detalleAdjunto);
                }else{
                   adjunto.setDetalle(fileInfo.getFileName());
                }

                adjuntoModel.agregar(adjunto);
                detalleAdjunto = "";
                adjuntos.add(adjunto);
                
                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.error.informeTecnicoController.adjunto.agregar.exitosamente"));
            }            
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }
    
    /**
     * Eliminar adjunto
     * @param pEvent
     */    
    public void eliminarAdjunto(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            
            //Se obtiene el adjunto
            AdjuntoEntity adjunto = (AdjuntoEntity) pEvent.getComponent().getAttributes().get("adjuntoEliminarSel");            
            
            //Se Busca el archivo en disco
            File file = new File(adjunto.getUrl());
            file.delete();
            
            //Se elimina de la base de datos 
            adjuntoModel.eliminar(adjunto);
            
            //Se buscan los adjuntos del informe
            adjuntos = adjuntoModel.buscarPorReferencia(informe.getIdInformeTecnico());
            
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }
    /**
     * Almacen la informacion del informe
     */      
    public void guardarDatos() {
        try {
            if(validarForm()){
                
                //Se almacena la informacion
                informeTecnicoModel.modificar(informe);
                
                //Si existe alguna aprobacion en tramite se debe eliminar ya que se cambiaron los valores
                if(aprobacionEnTramite){
                    aprobacionEnTramite = false; 
                    Estado estadoPendiente = estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_INFORME_TECNICO, Constantes.ESTADO_INFORME_TECNICO_PROCESO);
                    for(DocumentoRolEstadoEntity documento : documentosPorRol){
                        if(!documento.getIdEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_PROCESO)){
                            documento.setIdEstado(estadoPendiente);
                            documento.setFecha(null);
                            //documento.setIdUsuarioSeguridad("");
                            documentoRolEstadoModel.modificar(documento);
                        }
                        documento.setMarcado(this.permiteAceptarRechazar(documento.getIdDocumento().getIdDocumento(), documento.getIdRol().getIdRol()));
                    }
                }
                
                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.informeTecnicoController.modificado.exitosamente"));
            }
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }

    public void regresarListado() {
        if(vistaOrigen != null){
            Util.navegar(vistaOrigen);
        }else{
            Util.navegar("informe");
        }
    }

    public boolean validarForm(){
        
        if (informe.getEvaluacion().isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.informeTecnicoController.evaluacion.requerido"));
            return false;
        }
        if (informe.getIdTipo() == null || informe.getIdTipo().getId() <= 0 ) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.informeTecnicoController.tipo.requerido"));
            return false;
        }
        return true;        
    }
        
    
    //</editor-fold>

}