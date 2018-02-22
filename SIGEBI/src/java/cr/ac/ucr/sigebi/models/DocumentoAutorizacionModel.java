/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.DocumentoAutorizacionDao;
import cr.ac.ucr.sigebi.domain.AutorizacionRol;
import cr.ac.ucr.sigebi.domain.Documento;
import cr.ac.ucr.sigebi.domain.DocumentoAutorizacion;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "documentoAutorizacionModel")

public class DocumentoAutorizacionModel {

    @Resource
    private DocumentoAutorizacionDao documentoAutorizacionDao;

    public void agregar(DocumentoAutorizacion obj) throws FWExcepcion {
        documentoAutorizacionDao.agregar(obj);
    }
    
    public void modificar(DocumentoAutorizacion obj) throws FWExcepcion {
        documentoAutorizacionDao.modificar(obj);
    }

    public DocumentoAutorizacion buscar(AutorizacionRol autorizacionRol, Documento documento) throws FWExcepcion {
        return documentoAutorizacionDao.buscar(autorizacionRol, documento);
    }

}
