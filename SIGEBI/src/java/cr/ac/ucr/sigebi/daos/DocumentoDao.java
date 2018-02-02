/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Documento;
import cr.ac.ucr.sigebi.domain.DocumentoDetalle;
import java.util.List;
import org.hibernate.HibernateException;
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
@Repository(value = "documentoDao")
@Scope("request")
public class DocumentoDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional
    public void agregar(Documento documento) throws FWExcepcion {
        try {
            this.persist(documento);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.documentoDao.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void agregarDetalle(DocumentoDetalle documentoDetalle) throws FWExcepcion {
        try {
            this.persist(documentoDetalle);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.documentoDao.agregarDetalle", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void modificar(Documento documento) throws FWExcepcion {
        try {
            this.persist(documento);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.documentoDao.salvar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public List<Documento> listar(Long idUnidadEjecutora,
            Integer idTipoInforme,
            String identificacion,
            String descripcionBien,
            String marcaBien,
            String modeloBien,
            Integer idEstado,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            //Se genera el query para la busqueda
            Query q = this.creaQueryListarInformes(idUnidadEjecutora, idTipoInforme, identificacion, descripcionBien, marcaBien, modeloBien, idEstado, false, session);

            //Paginacion
            if (!(pPrimerRegistro.equals(1) && pUltimoRegistro.equals(1))) {
                q.setFirstResult(pPrimerRegistro);
                q.setMaxResults(pUltimoRegistro - pPrimerRegistro);
            }
            //Se obtienen los resutltados
            return (List<Documento>) q.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.documentoDao.listarInformes",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Long contar(Long idUnidadEjecutora,
            Integer idTipoInforme,
            String identificacion,
            String descripcionBien,
            String marcaBien,
            String modeloBien,
            Integer idEstado
    ) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {

            //Se genera el query para la busqueda de los bienes
            Query q = this.creaQueryListarInformes(idUnidadEjecutora, idTipoInforme, identificacion, descripcionBien, marcaBien, modeloBien, idEstado, true, session);

            //Se obtienen los resutltados
            return (Long) q.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.documentoDao.contarInformes",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    private Query creaQueryListarInformes(Long idUnidadEjecutora,
            Integer idTipoInforme,
            String identificacionBien,
            String descripcionBien,
            String marcaBien,
            String modeloBien,
            Integer idEstado,
            Boolean contar,
            Session session
    ) {

        StringBuilder sql = new StringBuilder(" ");
        if (contar) {
            sql.append("SELECT count(s) FROM Documento docu ");
        } else {
            sql.append("SELECT s FROM Documento docu");
        }

        //Select
        sql.append(" WHERE docu.bien.unidadEjecutora.id = :idUnidadEjecutora ");
        if (idTipoInforme != null && idTipoInforme > 0) {
            sql.append(" AND docu.tipoInforme.id = :idTipoInforme ");
        }
        if (idEstado != null && idEstado > 0) {
            sql.append(" AND docu.estado.id = :idEstado");
        }

        //Filtro de bienes en el detalle
        if ((identificacionBien != null && identificacionBien.length() > 0)
                || (descripcionBien != null && descripcionBien.length() > 0)
                || (marcaBien != null && marcaBien.length() > 0)
                || (modeloBien != null && modeloBien.length() > 0)) {
            sql.append(" AND (select count(1) from DocumentoDetalle deta ");
            sql.append(" where deta.documento.id = docu.id");
            if (identificacionBien != null && identificacionBien.length() > 0) {
                sql.append(" and deta.bien.identificacion.identificacion = like upper(:identificacionBien)");
            }
            if (descripcionBien != null && descripcionBien.length() > 0) {
                sql.append("and upper(deta.bien.descripcion) like upper(:descripcionBien) ");
            }
            if (marcaBien != null && marcaBien.length() > 0) {
                sql.append("and upper(deta.bien.resumenBien.marca) LIKE UPPER(:marcaBien) ");
            }
            if (modeloBien != null && modeloBien.length() > 0) {
                sql.append("and upper(deta.bien.resumenBien.modelo) LIKE UPPER(:modeloBien) ");
            }
            sql.append(" ) > 0");
        }
        sql.append(" ORDER BY docu.id desc ");

        Query q = session.createQuery(sql.toString());
        q.setParameter("idUnidadEjecutora", idUnidadEjecutora);
        if (idTipoInforme != null && idTipoInforme > 0) {
            q.setParameter("idTipoInforme", idTipoInforme);
        }
        if (identificacionBien != null && identificacionBien.length() > 0) {
            q.setParameter("identificacionBien", '%' + identificacionBien + '%');
        }
        if (descripcionBien != null && descripcionBien.length() > 0) {
            q.setParameter("descripcionBien", '%' + descripcionBien + '%');
        }
        if (marcaBien != null && marcaBien.length() > 0) {
            q.setParameter("marcaBien", '%' + marcaBien + '%');
        }
        if (modeloBien != null && modeloBien.length() > 0) {
            q.setParameter("modeloBien", '%' + modeloBien + '%');
        }
        if (idEstado != null && idEstado > 0) {
            q.setParameter("idEstado", idEstado);
        }
        return q;
    }
}