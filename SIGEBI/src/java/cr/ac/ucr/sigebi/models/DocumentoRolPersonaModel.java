/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.sigebi.daos.DocumentoRolPersonaDao;
import cr.ac.ucr.sigebi.entities.DocumentoRolPersonaEntity;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "documentoRolPersonaModel")
@Scope("request")
public class DocumentoRolPersonaModel {

    @Resource
    private DocumentoRolPersonaDao documentoRolPersonaDao;

    public void agregar(DocumentoRolPersonaEntity obj) {
        documentoRolPersonaDao.agregar(obj);
    }

    public void modificar(DocumentoRolPersonaEntity obj) {
        documentoRolPersonaDao.modificar(obj);
    }

    public void eliminar(DocumentoRolPersonaEntity obj) {
        documentoRolPersonaDao.eliminar(obj);
    }

    public List<DocumentoRolPersonaEntity> buscarPorDocumentoRol(Long idDocumento, Long idRol) {
        return documentoRolPersonaDao.buscarPorDocumentoRol(idDocumento, idRol);
    }
    
    public List<DocumentoRolPersonaEntity> buscarUsuaiosPorDocumento(Long idDocumento, Integer numUnidadEjec) {
        return documentoRolPersonaDao.buscarUsuaiosPorDocumento(idDocumento, numUnidadEjec);
    }
    
    public Long contarPorDocumentoRol(Long idDocumento, Long idRol) {
        return documentoRolPersonaDao.contarPorDocumentoRol(idDocumento, idRol);
    }
    
    public DocumentoRolPersonaEntity buscarPorRolDocumentoUsuario(Long idRol, Long idDocumento, String idUsuario) {
        return documentoRolPersonaDao.buscarPorRolDocumentoUsuario(idRol, idDocumento, idUsuario);
    }
    
    public DocumentoRolPersonaEntity buscarAutorizacionDocumentoRolUsuario(String codigoRol, Long idDocumento, String idUsuario){
        return documentoRolPersonaDao.buscarAutorizacionDocumentoRolUsuario(codigoRol, idDocumento, idUsuario);
    }
    
    
    public List<DocumentoRolPersonaEntity> buscarRolesPorDocumentoUsuario( Long idDocumento, String idUsuario, int unidadEjecutora) {
        return documentoRolPersonaDao.buscarRolesProDocumentoUsuario( idDocumento, idUsuario, unidadEjecutora);
    }
}
