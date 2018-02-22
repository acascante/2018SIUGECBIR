/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import com.icesoft.faces.component.inputfile.FileInfo;
import com.icesoft.faces.component.inputfile.InputFile;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.domain.DocumentoInformeTecnico;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.Usuario;
import cr.ac.ucr.sigebi.domain.Adjunto;
import cr.ac.ucr.sigebi.domain.AutorizacionRolPersona;
import cr.ac.ucr.sigebi.domain.DocumentoAutorizacion;
import cr.ac.ucr.sigebi.models.EstadoModel;
import cr.ac.ucr.sigebi.models.DocumentoModel;
import cr.ac.ucr.sigebi.models.AdjuntoModel;
import cr.ac.ucr.sigebi.models.AutorizacionRolPersonaModel;
import cr.ac.ucr.sigebi.models.DocumentoAutorizacionModel;
import cr.ac.ucr.sigebi.models.UsuarioModel;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
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
    private DocumentoAutorizacionModel documentoAutorizacionModel;

    @Resource
    private AutorizacionRolPersonaModel autorizacionRolPersonaModel;

    @Resource
    private EstadoModel estadoModel;

    @Resource
    private AdjuntoModel adjuntoModel;

    @Resource
    UsuarioModel usuarioModel;

    DocumentoInformeTecnico informe;

    // adjuntos del documento
    List<Adjunto> adjuntos;

    // comboBox tipos
    List<SelectItem> tipoOptions;

    //Mapa para la lista de autorizaciones para el documento
    HashMap<String, DocumentoAutorizacion> documentosAutorizacionesPorRol;

    // Se usan en el jsp
    String detalleAdjunto = "";
    boolean aprobacionRealizada = false;
    boolean rolPermiteModificar = false;
    Usuario usuario;

    Tipo tipoAdjunto;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Tipo getTipoAdjunto() {
        return tipoAdjunto;
    }

    public void setTipoAdjunto(Tipo tipoAdjunto) {
        this.tipoAdjunto = tipoAdjunto;
    }

    public DocumentoModel getDocumentoModel() {
        return documentoModel;
    }

    public void setDocumentoModel(DocumentoModel documentoModel) {
        this.documentoModel = documentoModel;
    }

    public DocumentoAutorizacionModel getDocumentoAutorizacionModel() {
        return documentoAutorizacionModel;
    }

    public void setDocumentoAutorizacionModel(DocumentoAutorizacionModel documentoAutorizacionModel) {
        this.documentoAutorizacionModel = documentoAutorizacionModel;
    }

    public AutorizacionRolPersonaModel getAutorizacionRolPersonaModel() {
        return autorizacionRolPersonaModel;
    }

    public void setAutorizacionRolPersonaModel(AutorizacionRolPersonaModel autorizacionRolPersonaModel) {
        this.autorizacionRolPersonaModel = autorizacionRolPersonaModel;
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

    public UsuarioModel getUsuarioModel() {
        return usuarioModel;
    }

    public void setUsuarioModel(UsuarioModel usuarioModel) {
        this.usuarioModel = usuarioModel;
    }

    public DocumentoInformeTecnico getInforme() {
        return informe;
    }

    public void setInforme(DocumentoInformeTecnico informe) {
        this.informe = informe;
    }

    public List<Adjunto> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<Adjunto> adjuntos) {
        this.adjuntos = adjuntos;
    }

    public List<SelectItem> getTipoOptions() {
        return tipoOptions;
    }

    public void setTipoOptions(List<SelectItem> tipoOptions) {
        this.tipoOptions = tipoOptions;
    }

    public HashMap<String, DocumentoAutorizacion> getDocumentosAutorizacionesPorRol() {
        return documentosAutorizacionesPorRol;
    }

    public void setDocumentosAutorizacionesPorRol(HashMap<String, DocumentoAutorizacion> documentosAutorizacionesPorRol) {
        this.documentosAutorizacionesPorRol = documentosAutorizacionesPorRol;
    }

    public String getDetalleAdjunto() {
        return detalleAdjunto;
    }

    public void setDetalleAdjunto(String detalleAdjunto) {
        this.detalleAdjunto = detalleAdjunto;
    }

    public boolean isAprobacionRealizada() {
        return aprobacionRealizada;
    }

    public void setAprobacionRealizada(boolean aprobacionRealizada) {
        this.aprobacionRealizada = aprobacionRealizada;
    }

    public boolean isRolPermiteModificar() {
        return rolPermiteModificar;
    }

    public void setRolPermiteModificar(boolean rolPermiteModificar) {
        this.rolPermiteModificar = rolPermiteModificar;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public InformeTecnicoController() {
        super();
    }

    @PostConstruct
    public final void inicializar() {
        try {
            //Se consulta el usuario logueado   
            usuario = usuarioModel.buscarPorId(lVistaUsuario.getgUsuarioActual().getIdUsuario());

            //Se consultan los tipos de informes de exclusion por por dominio
            tipoOptions = new ArrayList<SelectItem>();
            for (Tipo item : this.tiposPorDominio(Constantes.DOMINIO_INFORME_TECNICO)) {
                tipoOptions.add(new SelectItem(item.getId().toString(), item.getNombre()));
            }

            tipoAdjunto = this.tipoPorDominioValor(Constantes.DOMINIO_ADJUNTO, Constantes.TIPO_ADJUNTO_DOCUMENTO);

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.informeTecnicoController.inicializar"));
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Metodos Informe Tecnico">
    public Collection<DocumentoAutorizacion> getListaDocumentosAutorizacionesPorRol() {
        return documentosAutorizacionesPorRol != null ? new ArrayList(documentosAutorizacionesPorRol.values()) : new ArrayList();
    }

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
            if (informe.getTipoInforme() == null) {
                informe.setTipoInforme(new Tipo());
            } else {
                informe.getTipoInforme().setIdTemporal(informe.getTipoInforme().getId());
            }
            this.cargaDatos();

            Util.navegar(Constantes.VISTA_INFORME_TECNICO_DET);
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.informeTecnicoController.verDetalleInforme"));
        }
    }

    private void cargaDatos() {
        try {

            consultaAutorizaciones();

            //Se actuaizan banderas
            cambiaEstadoInforme();

            //Se buscan los adjuntos del informe
            adjuntos = adjuntoModel.buscarPorReferencia(tipoAdjunto, informe.getId());
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.informeTecnicoController.cargaDatos"));
        }
    }

    private void consultaAutorizaciones() {
        try {
            //Se listan las autorizaciones permitidas para el documento, se agrupan por rol. 
            documentosAutorizacionesPorRol = documentoModel.obtenerDocumentosAutorizacionPorRol(Constantes.CODIGO_AUTORIZACION_INFORME_TECNICO, informe, usuario, unidadEjecutora);

            //Elimina la marca de los documentos, por reglas internas 
            eliminaMarcaDocumentosAceptarRechazar();

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.informeTecnicoController.cargaDatos"));
        }
    }

    public void regresarListado() {
        if (vistaOrigen != null) {
            Util.navegar(vistaOrigen);
        } else {
            Util.navegar("informe");
        }
    }

    private void eliminaMarcaDocumentosAceptarRechazar() {
        try {
            for (Iterator<String> iterator = documentosAutorizacionesPorRol.keySet().iterator(); iterator.hasNext();) {
                DocumentoAutorizacion documentoAutorizacion = documentosAutorizacionesPorRol.get((String) iterator.next());

                //Si el informe no esta aprobado o anulado se permite modificarlo si es que tiene permisos, solo para los casos marcados en true
                if (documentoAutorizacion != null && documentoAutorizacion.isMarcado()
                        && (informe.getEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_APROBADO)
                        || informe.getEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_ANULADO)
                        || informe.getEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_NUEVO))) {
                    documentoAutorizacion.setMarcado(false);
                }
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.informeTecnicoController.eliminaMarcaDocumentosAceptarRechazar"));
        }
    }

    public void cambiarTipoInforme(ValueChangeEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }

            // Se obtiene el id del tipoInforme
            Long valor = informe.getTipoInforme().getIdTemporal();
            if (valor > 0) {
                informe.setTipoInforme(this.tipoPorId(valor));
                informe.getTipoInforme().setIdTemporal(valor);
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.informeTecnicoController.cambiarTipoInforme"));
        }

    }

    /**
     * Almacena la informacion del informe
     */
    public void guardarDatos() {
        try {
            if (validarForm()) {

                //Se actualizan todas las autorizaciones asociadas al documento
                Estado estadoPendienteModi = this.estadoPorDominioValor(Constantes.DOMINIO_INFORME_TECNICO, Constantes.ESTADO_INFORME_TECNICO_PROCESO);

                //Se almacena la informacion
                informe.setEstado(estadoPendienteModi);
                documentoModel.modificar(informe);

                //Si existe alguna aprobacion en tramite se debe eliminar las aprobaciones ya que se cambio el informe
                if (aprobacionRealizada) {

                    for (Iterator<String> iterator = documentosAutorizacionesPorRol.keySet().iterator(); iterator.hasNext();) {
                        DocumentoAutorizacion documentoAutorizacion = documentosAutorizacionesPorRol.get((String) iterator.next());

                        if (documentoAutorizacion != null && documentoAutorizacion.getId() != null && documentoAutorizacion.getId() > 0) {
                            documentoAutorizacion.setEstado(estadoPendienteModi);
                            documentoAutorizacion.setFecha(new Date());
                            documentoAutorizacionModel.modificar(documentoAutorizacion);
                        }
                    }
                }

                //Se cargan los datos nuevamente
                this.cargaDatos();

                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.informeTecnicoController.modificado.exitosamente"));
            }

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.informeTecnicoController.guardarDatos"));
        }
    }

    public boolean validarForm() {
        if (informe.getEvaluacion().isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.informeTecnicoController.evaluacion.requerido"));
            return false;
        }
        if (informe.getTipoInforme() == null || informe.getTipoInforme().getId() == null || informe.getTipoInforme().getId() <= 0) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.informeTecnicoController.tipo.requerido"));
            return false;
        }
        return true;
    }

    //</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="Aprobaciones y rechazos">
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
            DocumentoAutorizacion documento = (DocumentoAutorizacion) pEvent.getComponent().getAttributes().get("documentoSelApro");
            documento.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_INFORME_TECNICO, Constantes.ESTADO_INFORME_TECNICO_APROBADO));
            documento.setFecha(new Date());
            documento.setUsuarioSeguridad(usuario);
            if (documento.getId() != null && documento.getId() > 0) {
                documentoAutorizacionModel.modificar(documento);
            } else {
                documentoAutorizacionModel.agregar(documento);
            }

            //Se modifica en el informe en los casos que aplique
            cambiaEstadoInforme();

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e, e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.informeTecnicoController.aprobar"));
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
            DocumentoAutorizacion documento = (DocumentoAutorizacion) pEvent.getComponent().getAttributes().get("documentoSelRech");
            documento.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_INFORME_TECNICO, Constantes.ESTADO_INFORME_TECNICO_RECHAZADO));
            documento.setFecha(new Date());
            documento.setUsuarioSeguridad(usuario);
            if (documento.getId() != null && documento.getId() > 0) {
                documentoAutorizacionModel.modificar(documento);
            } else {
                documentoAutorizacionModel.agregar(documento);
            }

            //Se modifica en el informe en los casos que aplique
            cambiaEstadoInforme();
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.informeTecnicoController.rechazar"));
        }

    }

    /**
     * Metodo que cambia el estado del informe para los casos en que todos sus
     * autorizaciones se aprueben o se rechacen
     */
    private void cambiaEstadoInforme() {
        try {
            boolean rechazar = true;
            boolean aprobar = true;
            aprobacionRealizada = false;
            rolPermiteModificar = false;

            //Solo si el informe no esta aprobado
            if (!informe.getEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_APROBADO)
                    && !informe.getEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_ANULADO)) {

                for (Iterator<String> iterator = documentosAutorizacionesPorRol.keySet().iterator(); iterator.hasNext();) {

                    DocumentoAutorizacion documentoAutorizacion = documentosAutorizacionesPorRol.get((String) iterator.next());

                    //Si el informe no esta aprobado o anulado se permite modificarlo si es que tiene permisos
                    if (documentoAutorizacion != null) {

                        //Se verifica si alguno no esta aprobado
                        if (!documentoAutorizacion.getEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_APROBADO)) {
                            aprobar = false;
                        }
                        //Se verifica si alguno no esta rechazado
                        if (!documentoAutorizacion.getEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_RECHAZADO)) {
                            rechazar = false;
                        }
                        //Se verifica si alguno se encuentra pendiente de aprobar
                        if (documentoAutorizacion.getEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_APROBADO)) {
                            aprobacionRealizada = true;
                        }
                    }
                }

                //Si todos estan aprobados se aprueba todo el informe
                if (aprobar) {
                    informe.setEstado(estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_INFORME_TECNICO, Constantes.ESTADO_INFORME_TECNICO_APROBADO));
                    documentoModel.modificar(informe);
                    this.consultaAutorizaciones();

                } else {
                    //Si todos estan rechazadas se rechaza todo el informes
                    if (rechazar) {
                        informe.setEstado(estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_INFORME_TECNICO, Constantes.ESTADO_INFORME_TECNICO_RECHAZADO));
                        documentoModel.modificar(informe);
                    }

                    //Se marca bandera para permitir modificar el informes, solo para los usuarios que tienen un rol de tecnico, asociado al documento. 
                    //Solo aplica si el documento tiene al menos una aprobacion 
                    if (aprobacionRealizada) {
                        AutorizacionRolPersona autorizado = autorizacionRolPersonaModel.buscar(Constantes.CODIGO_AUTORIZACION_INFORME_TECNICO, Constantes.CODIGO_ROL_TECNICO, usuario, unidadEjecutora);
                        rolPermiteModificar = autorizado != null;
                    } else {
                        //Cualquiera puede modificar no existe ninguna aprobacion
                        rolPermiteModificar = true;
                    }
                }
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.informeTecnicoController.cambiaEstadoInforme"));
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Adjuntos del documento">
    /**
     * Agrega un adjunto al documento
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
                adjunto.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO));
                adjunto.setTipo(tipoAdjunto);
                adjunto.setIdReferencia(informe.getId());
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
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.informeTecnicoController.agregarAdjunto"));
        }
    }

    /**
     * Eliminar adjunto al documento
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
            adjuntos = adjuntoModel.buscarPorReferencia(tipoAdjunto, informe.getId());

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.informeTecnicoController.eliminarAdjunto"));
        }
    }

    //</editor-fold>
}
