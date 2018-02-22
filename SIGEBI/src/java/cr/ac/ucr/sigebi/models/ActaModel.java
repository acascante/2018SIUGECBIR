/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.ActaDao;
import cr.ac.ucr.sigebi.domain.DocumentoActa;
import cr.ac.ucr.sigebi.domain.DocumentoDetalle;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "actaModel")

public class ActaModel {
    
    @Resource
    private ActaDao actaDao;
    
    
    public void guardar(DocumentoActa actaEntity){
        actaDao.guardar(actaEntity);
    }
    
    public void eliminarBienes(List<DocumentoDetalle> valores){
        actaDao.eliminarBienes(valores);
    }
    public void guardarBienes(List<DocumentoDetalle> valores){
        actaDao.guardarBienes(valores);
    }
    
    public DocumentoActa traerActa(Integer actaId) {
        return actaDao.traerPorId(actaId);
    }
    
    public List<DocumentoDetalle> traerBienesActa(DocumentoActa acta) {
        return actaDao.traerBienesActa(acta);
    }
    
    
    public Long consultaCantidadRegistros(Long unidadEjecutora,
                                        String fltIdActa,
                                        String fltAutorizacion,
                                        String fltEstado ,
                                        String fltFecha         
    ){
        try{
            return actaDao.contarActas(unidadEjecutora
                                    , fltIdActa
                                    , fltAutorizacion
                                    , fltEstado
                                    , fltFecha);
        }catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.model.Actas.Consultar Cant Reg",
                    "Error al obtener los datos, error generado en la clase " + this.getClass(), ex);
        }
    }
    
    public List<DocumentoActa> listarActas(Long unidadEjecutora,
                                        String fltIdActa,
                                        String fltAutorizacion,
                                        String fltEstado,
                                        String fltFecha,
                                        Integer pPrimerRegistro,
                                        Integer pUltimoRegistro
    ){
        try {
            return actaDao.listarActas(unidadEjecutora,
                                         fltIdActa,
                                         fltAutorizacion,
                                         fltEstado,
                                         fltFecha, 
                                         pPrimerRegistro,
                                         pUltimoRegistro
            );
        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.model.Actas.listar",
                    "Error al obtener los datos, error generado en la clase " + this.getClass(), ex);
        }
    }
    
}
