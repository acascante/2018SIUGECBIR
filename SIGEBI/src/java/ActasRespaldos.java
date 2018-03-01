/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jorge.serrano
 */
public class ActasRespaldos {
    
    
//    
//    //<editor-fold defaultstate="collapsed" desc="Metodos Listado Roles Aprobacion por Documento">
//    
//
//    @Resource
//    private DocumentoAutorizacionModel documRolModel;
//    
//    //Los Roles de la tabla
//    List<AutorizacionRol> documentoRoles;
//    
//    //DocumentoAutorizacion documentoRoles;
//    
//    Map<Integer, ViewDocumAprobEntity> rolesIncluidos;
//
//    public void listarRolesAprobacion() {
//        try {
//            
//
//        } catch (Exception err) {
//            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Acta.MsnError.ActaError"));
//            //Mensaje.agregarErrorAdvertencia(err.getCause().getMessage());
//        }
//    }
//valorDonacion
//
//    @Resource
//    DocumentoRolEstadoModel docRolEstModel;
//    /**
//     * Aprueba el informe
//     *
//     * @param pEvent
//     */
//    public void aprobar(ActionEvent pEvent) {
//        try {
//            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
//                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
//                pEvent.queue();
//                return;
//            }
//            // Inicializo el ViewDocumAprobEntity para registrarlo en base de datos
//            ViewDocumAprobEntity docum = (ViewDocumAprobEntity) pEvent.getComponent().getAttributes().get("documentoAprobar");
//            DocumentoEntity documentoEntity = new DocumentoEntity();
//            documentoEntity.setIdDocumento( Long.parseLong(""+docum.getIdDocumento() ) );
//            Rol rolEntity = new Rol();
////            rolEntity.setId() Long.parseLong(""+docum.getRol() ) );
//            //Inicializo el registro DocumentoRolEstadoEntity
////            DocumentoRolEstadoEntity registro = new DocumentoRolEstadoEntity( Long.parseLong(""+acta.getId())
////                                                                            , documentoEntity
////                                                                            , rolEntity
////                                                                            , estadoGeneralAprobado);
//            //Completo los datos
////            registro.setFecha(new Date());
////            registro.setIdUsuarioSeguridad(usuarioRegistrado);
////            //Envío a guardar
////            docRolEstModel.agregar(registro);
////            
//            listarRolesAprobacion();
//            if(actaAprobada()){
////                acta.setEstado(estadoGeneralAprobado);
//                actaModel.guardar(acta);
//            }
//                
//            
//        } catch (Exception err) {
//            Mensaje.agregarErrorAdvertencia(err.getMessage());
//        }
//    }
//    
//    private boolean actaAprobada(){
////        for(AutorizacionRol valor : documentoRoles){
////            if( (valor.get() == null) || (valor.getIdEstado().getIdEstado().equals( estadoGeneralRechazado.getId() ) ))
////                return false;
////        }
//        return true;
//    }
//
//    /**
//     * Rechaza el informe
//     *
//     * @param pEvent
//     */
//    public void rechazar(ActionEvent pEvent) {
//        try {
//            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
//                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
//                pEvent.queue();
//                return;
//            }
//            
//            // Inicializo el ViewDocumAprobEntity para registrarlo en base de datos
//            ViewDocumAprobEntity docum = (ViewDocumAprobEntity) pEvent.getComponent().getAttributes().get("documentoRechazar");
//            DocumentoEntity documentoEntity = new DocumentoEntity();
//            documentoEntity.setIdDocumento( Long.parseLong(""+docum.getIdDocumento() ) );
////            RolEntity rolEntity = new RolEntity();
////             rolEntity.setIdRol( Long.parseLong(""+docum.getRolId() ) );
//            //Inicializo el registro DocumentoRolEstadoEntity
////            DocumentoRolEstadoEntity registro = new DocumentoRolEstadoEntity( Long.parseLong(""+acta.getId())
////                                                                            , documentoEntity
////                                                                            , rolEntity
////                                                                            , estadoGeneralRechazado);
//            //Completo los datos
////            registro.setFecha(new Date());
////            registro.setIdUsuarioSeguridad(usuarioRegistrado);
//            //Envío a guardar
////            docRolEstModel.agregar(registro);
//            
//  //          acta.setEstado(estadoGeneralRechazado);
//            actaModel.guardar(acta);
//                
//            listarRolesAprobacion();
//            
//        } catch (Exception err) {
//            Mensaje.agregarErrorAdvertencia(err.getMessage());
//        }
//    }
//
//    public List<AutorizacionRol> getDocumentoRoles() {
//        return documentoRoles;
//    }
//
//    public void setDocumentoRoles(List<AutorizacionRol> documentoRoles) {
//        this.documentoRoles = documentoRoles;
//    }
//
//    //</editor-fold>
    
    
    
}
