/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.FaltaViewBienDao;
import cr.ac.ucr.sigebi.entities.ViewBienEntity;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "viewBienModel")
@Scope("request")
public class ViewBienModel {
    
    @Resource
    private FaltaViewBienDao viewBienDao;
    
    

    public List<ViewBienEntity> listarBienes(Long unidadEjecutora,
             String fltIdBien,
             String fltDescripcion,
             String fltMarca,
             String fltModelo,
             String fltSerie,
             Integer[] fltEstadosBienes,
             Integer pPrimerRegistro,
             Integer pUltimoRegistro
    ) {

        try {
            return viewBienDao.listarBienes(unidadEjecutora, 
                    fltIdBien, 
                    fltDescripcion, 
                    fltMarca, 
                    fltModelo, 
                    fltSerie, 
                    fltEstadosBienes, 
                    pPrimerRegistro, 
                    pUltimoRegistro);

        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {

            throw new FWExcepcion("sigebi.error.model.bien.listarBienes",
                    "Error obtener los datos de un tipo, error generado en la clase " + this.getClass()
                    + " en el método obtenerTipo(Integer pIdTipo)", ex);
        }
    }

    public Long contarBienes(Long unidadEjecutora,
             String fltIdBien,
             String fltDescripcion,
             String fltMarca,
             String fltModelo,
             String fltSerie,
             Integer[] fltEstadosBienes
    ) {

        try {
            return viewBienDao.contarBienes(unidadEjecutora, fltIdBien, fltDescripcion, fltMarca, fltModelo, fltSerie, fltEstadosBienes);

        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {

            throw new FWExcepcion("sigebi.error.model.bien.contarBienes",
                    "Error obtener los datos de un tipo, error generado en la clase " + this.getClass()
                    + " en el método obtenerTipo(Integer pIdTipo)", ex);
        }
    }
    
    
    
}
