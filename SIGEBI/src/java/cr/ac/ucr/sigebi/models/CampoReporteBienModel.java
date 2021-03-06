/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.CampoReporteBienDao;
import cr.ac.ucr.sigebi.domain.CampoReporteBien;
import cr.ac.ucr.sigebi.domain.ReporteBien;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author alvaro.cascante
 */
@Service("campoReporteBienModel")
public class CampoReporteBienModel {
    
    @Resource private CampoReporteBienDao campoReporteBienDao;
    
    public List<CampoReporteBien> listar() throws FWExcepcion {
        return campoReporteBienDao.listar();
    }
    
    public CampoReporteBien buscarPorId(Long id) throws FWExcepcion {
        return campoReporteBienDao.buscarPorId(id);
    }
    
    public List<CampoReporteBien> listarPorReporte(ReporteBien reporteBien) throws FWExcepcion {
        return campoReporteBienDao.listarPorReporte(reporteBien);
    }
    
    public void salvar(CampoReporteBien campoReporteBien) throws FWExcepcion {
        campoReporteBienDao.salvar(campoReporteBien);
    }

    public void salvar(List<CampoReporteBien> campos) throws FWExcepcion {
        campoReporteBienDao.salvar(campos);
    }
    
    public void eliminar(List<CampoReporteBien> campos) throws FWExcepcion {
        campoReporteBienDao.eliminar(campos);
    }
}
