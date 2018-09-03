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
import cr.ac.ucr.sigebi.domain.DocumentoAprobacionExclusion;
import cr.ac.ucr.sigebi.domain.DocumentoDetalle;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jairo.cisneros
 */
@Repository(value = "documentoDao")

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
    public List<DocumentoDetalle> listarDetalles(Documento documento) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {

            StringBuilder sql = new StringBuilder("select obj from DocumentoDetalle obj ");
            sql.append(" where obj.documento = :documento");

            Query query = session.createQuery(sql.toString());
            query.setParameter("documento", documento);
            return (List<DocumentoDetalle>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.documentoDao.listarDetalles", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public List<Documento> listar(UnidadEjecutora unidadEjecutora,
            Tipo tipoInforme,
            String identificacion,
            String descripcionBien,
            String marcaBien,
            String modeloBien,
            Estado estado,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro,
            Integer tipoDocumento
    ) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            //Se genera el query para la busqueda
            Query q = this.creaQueryListarInformes(unidadEjecutora, tipoInforme, identificacion, descripcionBien, marcaBien, modeloBien, estado, tipoDocumento, false, session);

            //Paginacion
            if (!(pPrimerRegistro.equals(1) && pUltimoRegistro.equals(1))) {
                q.setFirstResult(pPrimerRegistro);
                q.setMaxResults(pUltimoRegistro - pPrimerRegistro);
            }
            //Se obtienen los resutltados
            return (List<Documento>) q.list();

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new FWExcepcion("sigebi.error.documentoDao.listarInformes",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Long contar(UnidadEjecutora unidadEjecutora,
            Tipo tipoInforme,
            String identificacion,
            String descripcionBien,
            String marcaBien,
            String modeloBien,
            Estado estado,
            Integer tipoDocumento
    ) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {

            //Se genera el query para la busqueda de los bienes
            Query q = this.creaQueryListarInformes(unidadEjecutora, tipoInforme, identificacion, descripcionBien, marcaBien, modeloBien, estado, tipoDocumento, true, session);

            //Se obtienen los resutltados
            return (Long) q.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.documentoDao.contarInformes",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    private Query creaQueryListarInformes(UnidadEjecutora unidadEjecutora,
            Tipo tipoInforme,
            String identificacionBien,
            String descripcionBien,
            String marcaBien,
            String modeloBien,
            Estado estado,
            Integer tipoDocumento,
            Boolean contar,
            Session session
    ) {

        StringBuilder sql = new StringBuilder(" ");
        if (contar) {
            sql.append("SELECT count(docu) FROM Documento docu ");
        } else {
            sql.append("SELECT docu FROM Documento docu");
        }

        //Select
        sql.append(" WHERE docu.discriminator = :tipoDocumento ");

        if (tipoInforme != null) {
            sql.append(" AND docu.tipoInforme = :tipoInforme ");
        }
        if (estado != null) {
            sql.append(" AND docu.estado = :estado");
        }
        if (unidadEjecutora != null) {
            sql.append(" and docu.unidadEjecutora = :unidadEjecutora");
        }

        //Filtro de bienes en el detalle
        if ((identificacionBien != null && identificacionBien.length() > 0)
                || (descripcionBien != null && descripcionBien.length() > 0)
                || (marcaBien != null && marcaBien.length() > 0)
                || (modeloBien != null && modeloBien.length() > 0)) {
            sql.append(" AND (select count(deta) from DocumentoDetalle deta ");
            sql.append(" where deta.documento.id = docu.id");
            if (identificacionBien != null && identificacionBien.length() > 0) {
                sql.append(" and upper(deta.bien.identificacion.identificacion) like upper(:identificacionBien)");
            }
            if (descripcionBien != null && descripcionBien.length() > 0) {
                sql.append(" and upper(deta.bien.descripcion) like upper(:descripcionBien) ");
            }
            if (marcaBien != null && marcaBien.length() > 0) {
                sql.append(" and upper(deta.bien.resumenBien.marca) like upper(:marcaBien) ");
            }
            if (modeloBien != null && modeloBien.length() > 0) {
                sql.append(" and upper(deta.bien.resumenBien.modelo) like upper(:modeloBien) ");
            }
            sql.append(" ) > 0");
        }
        sql.append(" ORDER BY docu.id desc ");

        Query q = session.createQuery(sql.toString());
        q.setParameter("tipoDocumento", tipoDocumento);

        if (unidadEjecutora != null) {
            q.setParameter("unidadEjecutora", unidadEjecutora);
        }
        if (tipoInforme != null) {
            q.setParameter("tipoInforme", tipoInforme);
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
        if (estado != null) {
            q.setParameter("estado", estado);
        }
        return q;
    }

    @Transactional(readOnly = true)
    public Long contarAprobacionesExclusion(UnidadEjecutora unidadEjecutora, Long id, String autorizacion, Date fecha, Long idEstado) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query q = this.creaQueryAprobacionesExclusion(unidadEjecutora, id, autorizacion, fecha, idEstado, true, session);
            return (Long) q.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.aprobacion.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public List<Documento> listarAprobacionesExclusion(UnidadEjecutora unidadEjecutora, Long id, String autorizacion, Date fecha, Long idEstado, Integer primerRegistro, Integer ultimoRegistro) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQueryAprobacionesExclusion(unidadEjecutora, id, autorizacion, fecha, idEstado, false, session);
            if (!(primerRegistro.equals(1) && ultimoRegistro.equals(1))) {
                query.setFirstResult(primerRegistro);
                query.setMaxResults(ultimoRegistro - primerRegistro);
            }
            return (List<Documento>) query.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.aprobacion.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    private Query creaQueryAprobacionesExclusion(UnidadEjecutora unidadEjecutora, Long id, String autorizacion, Date fecha, Long idEstado, Boolean contar, Session session) {
        StringBuilder sql = new StringBuilder("SELECT ");
        if (contar) {
            sql.append("count(entity) ");
        } else {
            sql.append("entity ");
        }
        sql.append("FROM DocumentoAprobacionExclusion entity ");
        sql.append("WHERE 1=1 ");

        if (id != null) {
            sql.append("AND entity.id = :id ");
        } else {
            if (unidadEjecutora != null) {
                sql.append("AND entity.unidadEjecutora = :unidadEjecutora ");
            }
            if (autorizacion != null) {
                sql.append("AND entity.autorizacion = :autorizacion ");
            }
            if (fecha != null) {
                sql.append("AND entity.fecha = :fecha ");
            }
            if (idEstado != null) {
                sql.append("AND entity.estado.id = :idEstado ");
            }
        }

        sql.append("ORDER BY entity.id desc");

        Query query = session.createQuery(sql.toString());

        if (id != null) {
            query.setParameter("id", id);
        } else {
            if (unidadEjecutora != null) {
                query.setParameter("unidadEjecutora", unidadEjecutora);
            }
            if (autorizacion != null) {
                query.setParameter("autorizacion", autorizacion);
            }
            if (fecha != null) {
                query.setParameter("fecha", fecha);
            }
            if (idEstado != null) {
                query.setParameter("idEstado", idEstado);
            }
        }
        return query;
    }
    
    @Transactional
    public void eliminarDetalles(List<DocumentoDetalle> detalles) throws FWExcepcion {
        try {
            delete(detalles.toArray());
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.label.aprobacion.error.eliminar", "Error eliminado registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public DocumentoAprobacionExclusion buscarPorId(Long id) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT entity FROM DocumentoAprobacionExclusion entity WHERE entity.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);

            return (DocumentoAprobacionExclusion) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.label.aprobacion.error.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

}
