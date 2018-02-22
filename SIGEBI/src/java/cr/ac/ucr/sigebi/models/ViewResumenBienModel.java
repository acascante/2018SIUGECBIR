/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.ViewResumenBienDao;
import cr.ac.ucr.sigebi.domain.ViewResumenBien;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author alvaro.cascante
 */
@Service(value = "viewResumenBienModel")

public class ViewResumenBienModel {
    
    @Resource
    private ViewResumenBienDao viewResumenBienDao;
    
    public List<ViewResumenBien> listar() throws FWExcepcion {
        return viewResumenBienDao.listar();
    }
    
    public ViewResumenBien buscarPorId(Long id) throws FWExcepcion {
        return viewResumenBienDao.buscarPorId(id);
    }
}