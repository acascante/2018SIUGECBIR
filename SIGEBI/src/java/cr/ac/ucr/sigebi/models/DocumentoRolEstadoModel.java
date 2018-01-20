/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.sigebi.daos.FaltaDocumentoRolEstadoDao;
import cr.ac.ucr.sigebi.entities.DocumentoRolEstadoEntity;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "documentoRolEstadoModel")
@Scope("request")
public class DocumentoRolEstadoModel {
    
    @Resource
    private FaltaDocumentoRolEstadoDao documentoRolEstadoDao;

    
    public void agregar(DocumentoRolEstadoEntity documentoRolEstadoEntity){
        documentoRolEstadoDao.agregar(documentoRolEstadoEntity);
    }

    public void modificar(DocumentoRolEstadoEntity documentoRolEstadoEntity){
        documentoRolEstadoDao.modificar(documentoRolEstadoEntity);
    }
    
    public List<DocumentoRolEstadoEntity> buscarDocumentosUsuario(String idUsuario
                                                                , Long numUnidadEjec
                                                                , Long idReferencia
                                                                , Long idDocumento
                                                        ) {
        return documentoRolEstadoDao.buscarDocumentosUsuario(idUsuario
                                                            , numUnidadEjec
                                                            , idReferencia
                                                            , idDocumento);
    }
    
    public List<DocumentoRolEstadoEntity> buscarDocumentosTipoDocumento(Long idReferencia , Long idDocumento) {
        return documentoRolEstadoDao.buscarDocumentosTipoDocumento(idReferencia, idDocumento);
    }
}
