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
import cr.ac.ucr.sigebi.domain.Documento;
import cr.ac.ucr.sigebi.domain.DocumentoInformeTecnico;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.Usuario;
import cr.ac.ucr.sigebi.domain.Adjunto;
import cr.ac.ucr.sigebi.models.DocumentoRolEstadoModel;
import cr.ac.ucr.sigebi.models.EstadoModel;
import cr.ac.ucr.sigebi.models.DocumentoModel;
import cr.ac.ucr.sigebi.entities.DocumentoRolEstadoEntity;
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
public class InformeTecnicoController extends BaseController {

    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    @Resource
    private DocumentoModel documentoModel;

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

    DocumentoInformeTecnico informe;
    List<DocumentoRolEstadoEntity> documentosPorRol;
    List<DocumentoRolEstadoEntity> documentosPorRolPorAceptar;
    List<Adjunto> adjuntos;

    // comboBox tipos
    List<SelectItem> tipoOptions;

    // Se usan en el jsp
    String detalleAdjunto = "";
    boolean aprobacionEnTramite = false;
    boolean rolPermiteModificar = false;
    Usuario usuario;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public InformeTecnicoController() {
        super();
    }

    @PostConstruct
    public final void inicializar() {

        //Se consulta el usuario logueado   
        usuario = usuarioModel.buscarPorId(lVistaUsuario.getgUsuarioActual().getIdUsuario());

        //Se consultan los tipos por dominio
        tipoOptions = new ArrayList<SelectItem>();
        for (Tipo item : this.tiposPorDominio(Constantes.DOMINIO_INFORME_TECNICO)) {
            tipoOptions.add(new SelectItem(item.getId().toString(), item.getNombre()));
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    /**
     * Abre la pantalla de detalle
     *
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
            informe = (DocumentoInformeTecnico) pEvent.getComponent().getAttributes().get("informeSel");

            //Se buscan los roles que deben aceptar el informe,
            documentosPorRol = documentoRolEstadoModel.buscarDocumentosTipoDocumento(informe.getId(), Long.parseLong(Constantes.CODIGO_AUTORIZACION_INFORME_TECNICO.toString()));

            //Se buscan cuales documentos se pueden aceptar segun el usuario y rol 
            documentosPorRolPorAceptar = documentoRolEstadoModel.buscarDocumentosUsuario(lVistaUsuario.getgUsuarioActual().getIdUsuario(),
                    unidadEjecutora.getId(), informe.getId(), Long.parseLong(Constantes.CODIGO_AUTORIZACION_INFORME_TECNICO.toString()));
            for (DocumentoRolEstadoEntity documento : documentosPorRol) {
                documento.setMarcado(this.permiteAceptarRechazar(documento.getIdDocumento().getIdDocumento(), documento.getIdRol().getIdRol()));
            }

            //Se actuaizan banderas
            cambiaEstadoInforme();

            //Se buscan los adjuntos del informe
            adjuntos = adjuntoModel.buscarPorReferencia(informe.getId());

            Util.navegar("informe_detalle");

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }

    /**
     * Aprueba el informe
     *
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
     *
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

    public boolean permiteAceptarRechazar(Long idDocumento, Long idRol) {
        if (!informe.getEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_APROBADO)
                && !informe.getEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_ANULADO)) {
            for (DocumentoRolEstadoEntity documento : documentosPorRolPorAceptar) {
                if (documento.getIdDocumento().getIdDocumento().equals(idDocumento)
                        && documento.getIdRol().getIdRol().equals(idRol)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void cambiaEstadoInforme() {
        boolean rechazar = true;
        boolean aprobar = true;
        aprobacionEnTramite = false;
        rolPermiteModificar = false;

        //Solo si el informe no esta aprobado
        if (!informe.getEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_APROBADO)
                && !informe.getEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_ANULADO)) {
            for (DocumentoRolEstadoEntity documento : documentosPorRol) {
                if (!documento.getIdEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_APROBADO)) {
                    aprobar = false;
                }
                if (!documento.getIdEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_RECHAZADO)) {
                    rechazar = false;
                }
                if (documento.getIdEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_APROBADO)) {
                    aprobacionEnTramite = true;
                }
            }

            if (aprobar) {
                informe.setEstado(estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_INFORME_TECNICO, Constantes.ESTADO_INFORME_TECNICO_APROBADO));
                documentoModel.modificar(informe);
            }
            if (rechazar) {
                informe.setEstado(estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_INFORME_TECNICO, Constantes.ESTADO_INFORME_TECNICO_RECHAZADO));
                documentoModel.modificar(informe);
            }

            if (aprobacionEnTramite) {
                //Solo se permite modificar si el usuario tiene un rol de tecnico asociado al documento
                //AutorizacionRolPersona autorizado = autorizacionRolPersonaModel.buscarAutorizacionPorAutorizacionRolUsuario(Constantes.CODIGO_ROL_TECNICO, 
                //        Constantes.ID_DOCUMENTO_INFORME_TECNICO, lVistaUsuario.getgUsuarioActual().getIdUsuario());
                //rolPermiteModificar = autorizado != null;
            } else {
                //Cualquiera puede modificar no existe ninguna aprobacion
                rolPermiteModificar = true;
            }
        }
    }

    /**
     * Abre la pantalla de detalle
     *
     * @param pEvent
     */
    public void agregarAdjunto(ActionEvent pEvent) {
        try {
            InputFile inputFile = (InputFile) pEvent.getSource();
            FileInfo fileInfo = inputFile.getFileInfo();
            if (fileInfo.getFileName() == null) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.informeTecnicoController.adjunto.requerido"));
            } else {
                Adjunto adjunto = new Adjunto();
                adjunto.setIdEstado(estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO));
                adjunto.setIdReferencia(informe.getId());
                //adjunto.setIdTipo(tipoModel.buscarPorDominioTipo(Constantes.DOMINIO_TIPO_ADJUNTO, Constantes.TIPO_ADJUNTO_INFORME_TECNICO));
                //adjunto.setIdTipoDocumento(tipoModel.buscarPorDominioTipo(Constantes.DOMINIO_TIPO_DOCUMENTO, Constantes.TIPO_DOCUMENTO_INFORME_TECNICO));
                adjunto.setUrl("upload/informesTecnicos/" + fileInfo.getFileName());
                if (detalleAdjunto != null && detalleAdjunto.length() > 0) {
                    adjunto.setDetalle(detalleAdjunto);
                } else {
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
     *
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
            Adjunto adjunto = (Adjunto) pEvent.getComponent().getAttributes().get("adjuntoEliminarSel");

            //Se Busca el archivo en disco
            File file = new File(adjunto.getUrl());
            file.delete();

            //Se elimina de la base de datos 
            adjuntoModel.eliminar(adjunto);

            //Se buscan los adjuntos del informe
            adjuntos = adjuntoModel.buscarPorReferencia(informe.getId());

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }

    /**
     * Almacen la informacion del informe
     */
    public void guardarDatos() {
        try {
            if (validarForm()) {

                //Se almacena la informacion
                documentoModel.modificar(informe);

                //Si existe alguna aprobacion en tramite se debe eliminar ya que se cambiaron los valores
                if (aprobacionEnTramite) {
                    aprobacionEnTramite = false;
                    Estado estadoPendiente = estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_INFORME_TECNICO, Constantes.ESTADO_INFORME_TECNICO_PROCESO);
                    for (DocumentoRolEstadoEntity documento : documentosPorRol) {
                        if (!documento.getIdEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_PROCESO)) {
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
        if (vistaOrigen != null) {
            Util.navegar(vistaOrigen);
        } else {
            Util.navegar("informe");
        }
    }

    public boolean validarForm() {

        if (informe.getEvaluacion().isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.informeTecnicoController.evaluacion.requerido"));
            return false;
        }
        if (informe.getTipoInforme() == null || informe.getTipoInforme().getId() <= 0) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.informeTecnicoController.tipo.requerido"));
            return false;
        }
        return true;
    }

    //</editor-fold>
}
