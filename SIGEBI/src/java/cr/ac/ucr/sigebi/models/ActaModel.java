/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.ActaDao;
import cr.ac.ucr.sigebi.daos.UnidadEjecutoraDao;
import cr.ac.ucr.sigebi.domain.Documento;
import cr.ac.ucr.sigebi.domain.DocumentoActa;
import cr.ac.ucr.sigebi.domain.DocumentoDetalle;
import cr.ac.ucr.sigebi.domain.Estado;
import java.util.List;
import javax.annotation.Resource;

import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.Date;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "actaModel")

public class ActaModel {
    
    @Resource
    private ActaDao actaDao;

    @Resource
    private UnidadEjecutoraDao unidadEjecutoraDao;
    
    public void guardar(DocumentoActa actaEntity){
        actaDao.guardar(actaEntity);
    }
    
    public void eliminarBienes(List<DocumentoDetalle> valores){
        actaDao.eliminarBienes(valores);
    }
    public void guardarBienes(List<DocumentoDetalle> valores){
        actaDao.guardarBienes(valores);
    }
    
    public Documento buscarPorId(Long id) {
        return actaDao.buscarPorId(id);
    }
    
    public List<DocumentoDetalle> traerBienesActa(DocumentoActa acta) {
        return actaDao.traerBienesActa(acta);
    }
    
    public List<DocumentoDetalle> listarDetalles(Estado estado, Long id, String identificacionBien, Date fechaInicio, Date fechaFin, String orden, String orden1, String orden2, String orden3) throws FWExcepcion {
        return actaDao.listarDetalles(estado, id, identificacionBien, fechaInicio, fechaFin, orden, orden1, orden2, orden3);
    }
    public Long consultaCantidadRegistros(Long idUnidadEjecutora, String fltIdActa, String fltAutorizacion, String fltEstado, String fltFecha){
        try{
            if (idUnidadEjecutora.equals(Constantes.DEFAULT_ID)) {
                return actaDao.contarActas(null, fltIdActa, fltAutorizacion, fltEstado, fltFecha);
            }
            else {
                return actaDao.contarActas(unidadEjecutoraDao.buscarPorId(idUnidadEjecutora), fltIdActa, fltAutorizacion, fltEstado, fltFecha);
            }

        }catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.model.Actas.Consultar Cant Reg",
                    "Error al obtener los datos, error generado en la clase " + this.getClass(), ex);
        }
    }
    
    public List<DocumentoActa> listarActas(String fltEstado) {
        return actaDao.listarActas(null, null, null, fltEstado, null,  1, 1);
    }
    
    public List<DocumentoActa> listarActas(Long idUnidadEjecutora,
                                        String fltIdActa,
                                        String fltAutorizacion,
                                        String fltEstado,
                                        String fltFecha,
                                        Integer pPrimerRegistro,
                                        Integer pUltimoRegistro
    ){
        try {
            if (idUnidadEjecutora.equals(Constantes.DEFAULT_ID)) {
                return actaDao.listarActas(null, fltIdActa, fltAutorizacion, fltEstado, fltFecha,  pPrimerRegistro, pUltimoRegistro);
            }
            else {
                return actaDao.listarActas(unidadEjecutoraDao.buscarPorId(idUnidadEjecutora), fltIdActa, fltAutorizacion, fltEstado, fltFecha,  pPrimerRegistro, pUltimoRegistro);
            }
        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.model.Actas.listar",
                    "Error al obtener los datos, error generado en la clase " + this.getClass(), ex);
        }
    }
    
}
