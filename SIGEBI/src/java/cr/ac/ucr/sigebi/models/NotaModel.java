/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.sigebi.daos.NotaDao;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Nota;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "notaModel")
@Scope("request")
public class NotaModel {

    @Resource
    private NotaDao notaDao;

    public Nota buscarPorId(Long pId) {
        return notaDao.buscarPorId(pId);
    }

    public List<Nota> listar(Bien bien) {
        return notaDao.listar(bien);
    }

    public void guardar(Nota nota) {
        notaDao.guardar(nota);
    }

    public void eliminar(Nota nota){
        notaDao.eliminar(nota);
    }
}
