/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.AdjuntoDao;
import cr.ac.ucr.sigebi.domain.Adjunto;
import cr.ac.ucr.sigebi.domain.Tipo;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author jairo.cisneros
 */
@Service(value = "adjuntoModel")

public class AdjuntoModel {
    
    @Resource
    private AdjuntoDao adjuntoDao;
    
    @Resource
    private ArchivoFtpModel archivoFtpModel;
    
    public void agregar(Adjunto adjunto)  {
        try {
            archivoFtpModel.uploadFile(adjunto.getUrl(), adjunto.getNombre());
        } catch (JSchException jse) {
            throw new FWExcepcion("sigebi.error.dao.adjunto.agregar",
                    "Error al almacenar archivo " + this.getClass(), jse.getCause());
        } catch(SftpException e){
            throw new FWExcepcion("sigebi.error.dao.adjunto.agregar",
                    "Error al almacenar archivo " + this.getClass(), e.getCause());
        }
        adjuntoDao.agregar(adjunto);
    }
    
    public void eliminar(Adjunto adjunto){
        adjuntoDao.eliminar(adjunto);
    }
    
    public List<Adjunto> buscarPorReferencia(Tipo tipoDocumento, Long idReferencia) {
        return adjuntoDao.buscarPorReferencia(tipoDocumento, idReferencia);
    }
}
