/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.sigebi.daos.DocumentoDao;
import cr.ac.ucr.sigebi.entities.DocumentoEntity;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "documentoModel")
@Scope("request")
public class DocumentoModel {

    @Resource
    private DocumentoDao documentoDao;

    public void agregar(DocumentoEntity obj) {
        documentoDao.agregar(obj);
    }

    public void modificar(DocumentoEntity obj) {
        documentoDao.modificar(obj);
    }

    public void eliminar(DocumentoEntity obj) {
        documentoDao.eliminar(obj);
    }

    public DocumentoEntity buscarPorId(Long idDocumento) {
        return documentoDao.buscarPorId(idDocumento);
    }

    public List<DocumentoEntity> buscarPorTipoProceso(Integer idTipoProceso) {
        return documentoDao.buscarPorTipoProceso(idTipoProceso);
    }

    public Long contarDocumentosValidator(Long idDocumentoDiferente, Integer idProceso, Integer orden, String nombre
    ) {
        return documentoDao.contarDocumentosValidator(idDocumentoDiferente, idProceso, orden, nombre);
    }

}
