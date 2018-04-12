/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.sigebi.daos.ReporteDao;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author alvaro.cascante
 */
@Service("reporteModel")
public class ReporteModel {
    
    @Resource private ReporteDao reporteDao;

    public void ejecutarReporte(String idReporte, String direccionReporte, Map parametros, String formatoReporte){
        try{
            reporteDao.ejecutarReporte(idReporte, direccionReporte, parametros, formatoReporte);
        }catch(Exception err){
            
        }
    }
}
