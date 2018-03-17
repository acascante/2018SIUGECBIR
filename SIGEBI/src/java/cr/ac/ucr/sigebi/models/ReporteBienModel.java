/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.ReporteBienDao;
import cr.ac.ucr.sigebi.domain.ReporteBien;
import cr.ac.ucr.sigebi.domain.Usuario;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author alvaro.cascante
 */
@Service("reporteBienModel")
public class ReporteBienModel {
    
    @Resource private ReporteBienDao reporteBienDao;

    public List<ReporteBien> listar() throws FWExcepcion {
        return reporteBienDao.listar();
    }
    
    public ReporteBien buscarPorId(Long id) throws FWExcepcion {
        return reporteBienDao.buscarPorId(id);
    }
    
    public List<ReporteBien> listarPorUsuario(Usuario usuario) throws FWExcepcion {
        return reporteBienDao.listarPorUsuario(usuario);
    }
    
    public void salvar(ReporteBien reporteBien) throws FWExcepcion {
        reporteBienDao.salvar(reporteBien);
    }
    
    public void eliminar(ReporteBien reporteBien) throws FWExcepcion {
        reporteBienDao.eliminar(reporteBien);
    }
}