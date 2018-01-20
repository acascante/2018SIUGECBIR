/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.entities.InformeTecnicoEntity;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jairo.cisneros
 */
@Repository(value = "informeTecnicoDao")
@Scope("request")
public class FaltaInformeTecnicoDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional
    public void agregar(InformeTecnicoEntity informeTecnicoEntity) {
        this.persist(informeTecnicoEntity);
    }

    @Transactional
    public void modificar(InformeTecnicoEntity informeTecnicoEntity) {
        this.persist(informeTecnicoEntity);
    }

    @Transactional(readOnly = true)
    public List<InformeTecnicoEntity> listarInformes(Long unidadEjecutora,
            Integer fltIdTipo,
            String fltIdBien,
            String fltDescripcion,
            Integer fltEstado,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            //Se genera el query para la busqueda
            Query q = this.creaQueryListarInformes(unidadEjecutora, fltIdTipo, fltIdBien, fltDescripcion, fltEstado, false, session);

            //Paginacion
            if (!(pPrimerRegistro.equals(1) && pUltimoRegistro.equals(1))) {
                q.setFirstResult(pPrimerRegistro);
                q.setMaxResults(pUltimoRegistro - pPrimerRegistro);
            }
            //Se obtienen los resutltados
            return (List<InformeTecnicoEntity>) q.list();

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.informeTecnico.listarInformes",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Long contarInformes(Long unidadEjecutora,
            Integer fltIdTipo,
            String fltIdBien,
            String fltDescripcion,
            Integer fltEstado
    ) {
        Session session = dao.getSessionFactory().openSession();
        try {

            //Se genera el query para la busqueda de los bienes
            Query q = this.creaQueryListarInformes(unidadEjecutora, fltIdTipo, fltIdBien, fltDescripcion, fltEstado, true, session);

            //Se obtienen los resutltados
            return (Long) q.uniqueResult();

        } catch (Exception e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.dao.informeTecnico.contarInformes",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    private Query creaQueryListarInformes(Long unidadEjecutora,
            Integer fltTipo,
            String fltIdBien,
            String fltDescripcion,
            Integer fltEstado,
            Boolean contar,
            Session session
    ) {
        String sql;
        if (contar) {
            sql = "SELECT count(s) FROM InformeTecnicoEntity s ";
        } else {
            sql = "SELECT s FROM InformeTecnicoEntity s ";
        }
        //Select
        sql = sql + " WHERE s.idBien.numUnidadEjec = :pnumUnidadEjec ";
        if (fltTipo != null && fltTipo> 0) {
            sql = sql + " AND s.idTipo.idTipo = :fltTipo ";
        }
        if (fltIdBien != null && fltIdBien.length() > 0) {
            sql = sql + " AND upper(s.idBien.idPlaca.placa) like upper(:fltIdBien) ";
        }
        if (fltDescripcion != null && fltDescripcion.length() > 0) {
            sql = sql + " AND upper(s.idBien.descripcion) like upper(:fltDescripcion) ";
        }
        if (fltEstado != null && fltEstado > 0) {
            sql = sql + " AND s.idEstado.idEstado = :fltEstado";
        }

        Query q = session.createQuery(sql);
        q.setParameter("pnumUnidadEjec", unidadEjecutora);
        if (fltTipo != null && fltTipo> 0) {
            q.setParameter("fltTipo", fltTipo);
        }
        if (fltIdBien != null && fltIdBien.length() > 0) {
            q.setParameter("fltIdBien", '%' + fltIdBien + '%');
        }
        if (fltDescripcion != null && fltDescripcion.length() > 0) {
            q.setParameter("fltDescripcion", '%' + fltDescripcion + '%');
        }
        if (fltEstado != null && fltEstado > 0) {
            q.setParameter("fltEstado", fltEstado);
        }
        return q;
    }
}
