/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.sigebi.daos.DocumentoRolDao;
import cr.ac.ucr.sigebi.entities.DocumentoRolEntity;
import cr.ac.ucr.sigebi.entities.ViewDocumAprobEntity;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "documentoRolModel")
@Scope("request")
public class DocumentoRolModel {
    
    @Resource
    private DocumentoRolDao documentoRolDao;

    public void agregar(DocumentoRolEntity obj) {
        documentoRolDao.agregar(obj);
    }

    public void modificar(DocumentoRolEntity obj) {   
        documentoRolDao.modificar(obj);
    }
    
    public void eliminar(DocumentoRolEntity obj) { 
        documentoRolDao.eliminar(obj);
    }
    
    public List<DocumentoRolEntity> buscarPorDocumento(Long idDocumento) {
        return documentoRolDao.buscarPorDocumento(idDocumento);
    }    
    
    public Long contarPorDocumento(Long idDocumento) {
        return documentoRolDao.contarPorDocumento(idDocumento);
    } 
    
    public DocumentoRolEntity buscarPorRolDocumento(Long idRol, Long idDocumento){
        return documentoRolDao.buscarPorRolDocumento(idRol, idDocumento);
    }
    
    public List<ViewDocumAprobEntity> buscarRolDocumId(int idTipoDocumento, int idDocumento){
        return documentoRolDao.buscarRolDocumId( idTipoDocumento, idDocumento );
    }
    
    
    
}
