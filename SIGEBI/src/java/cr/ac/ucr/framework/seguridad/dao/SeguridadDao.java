/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.framework.seguridad.dao;

import cr.ac.ucr.framework.seguridad.entidades.SegElemento;
import cr.ac.ucr.framework.seguridad.entidades.SegPerfil;
import cr.ac.ucr.framework.seguridad.entidades.SegUnidadEjecutora;
import cr.ac.ucr.framework.seguridad.entidades.SegUnidadEjecutoraLlave;
import cr.ac.ucr.framework.seguridad.entidades.SegUnidadUsuario;
import cr.ac.ucr.framework.seguridad.entidades.SegUnidadUsuarioLlave;
import cr.ac.ucr.framework.seguridad.entidades.SegUsuario;
import cr.ac.ucr.framework.seguridad.utils.ExcepcionSeguridad;
import cr.ac.ucr.framework.seguridad.utils.Ldap;
import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Util;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.faces.model.SelectItem;
import oracle.jdbc.OracleTypes;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = "seguridadDao")
@Scope("request")
public class SeguridadDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>><<>><<>>  METODOS   <<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/
    /**
     * El metodo valida si el usuario con esa contraseña estan autorizados para
     * ingresar al sistema
     *
     * @param nombre_usuario UserName del usuario
     * @param contraseña Contraseña [desencriptada]
     * @return true-> esta autorizado, false-> No esta autorizado (usuario o
     * contraseña invalida)
     * @throws ExcepcionSeguridad si hubo un error con la conexion con el LDAP.
     */
    public boolean autorizarLoginUCR(String nombre_usuario, String contrasena) {
        return new Ldap().autenticarUsuario(nombre_usuario, contrasena);
    }

    /**
     * El metodo valida si el nombre del usuario, es un usuario de la UCR que se
     * encuentra registrado en el LDAP.
     *
     * @param nombre_usuario UserName del usuario a validar si existe
     * @param usuario_adm Nombre del usuario con el que se conectara al LDAP
     * para realizar la consulta
     * @param pass_adm Contraseña del usuario con el que se conectara al LDAP
     * para realizar la consulta
     * @return true-> Existe false-> No existe
     * @throws ExcepcionSeguridad si hubo un error con la conexion con el LDAP.
     */
    public boolean validarExisteUCR(String nombre_usuario, String usuario_adm, String pass_adm) {
        return new Ldap().existeUsuario(nombre_usuario, usuario_adm, pass_adm);
    }

    /**
     *
     * @param id_usuario UserName/nombre del usuario
     * @param contraseña contraseña para ingresar [encriptada]
     * @return true -> Esta autorizado. false -> No esta autorizado (usuario o
     * contraseña invalida)
     * @throws ExcepcionSeguridad si hubo un error con la conexión a base de
     * datos.
     */
    @Transactional
    public boolean autorizarLoginSeguridad(String id_usuario, String contrasena) {

        boolean result = false;
        try {
            String[] nameParams = new String[2];
            nameParams[0] = "idUsuario";
            nameParams[1] = "codClave";
            Object[] params = new Object[2];
            params[0] = id_usuario;
            params[1] = contrasena;
            List<SegUsuario> usuario = dao.getHibernateTemplate().findByNamedQueryAndNamedParam("SegUsuario.findByIdUsuarioAndCodClave", nameParams, params);

            if (!usuario.isEmpty()) {
                result = true;
            }

        } catch (Exception e) {
            throw new ExcepcionSeguridad("Error autorizando la validación del usuario " + id_usuario + " por medio del sistema de seguridad",
                    "Error autorizando el acceso del usuario en la clase " + getClass() + ", en el metodo autorizarLoginSeguridad(String id_usuario, String contraseña)",
                    3, e);
        }
        return result;
    }

    /**
     * Actualiza la informacion del usuario en la capa de datos, con la
     * informacion que contiene dicho usuario en el objeto que recibe como
     * parámetro.
     *
     * @param usuario Usuario a actualizar
     */
    @Transactional
    //No ponerlo en nada que sea solo de consulta
    public void actualizarUsuario(SegUsuario usuario) {
        try {
            dao.getHibernateTemplate().merge(usuario);
        } catch (Exception e) {

            throw new ExcepcionSeguridad("No se pudo guardar el Usuario",
                    "Error guardando un usuario , en la clase " + this.getClass() + " en el metodo actualizarUsuario()",
                    69, e);
        }
    }

    /**
     * Busca el usuario que se indica en el objeto user, en la base de datos.
     * encaso de encontrarlo devuelte el objeto del usuario encontrado, hace la
     * comparacion del usuario objeto recibido y el de la BD en lowerCase.
     *
     * @param user Usuario a buscar en la capa de datos.
     * @return
     */
    @Transactional
    public SegUsuario buscarUsuarioLowerCase(SegUsuario user) throws FWExcepcion {

        try {

            return (SegUsuario) dao.getHibernateTemplate().findByNamedQueryAndNamedParam("SegUsuario.findByIdUsuarioLower", "idUsuario", user.getIdUsuario().toLowerCase()).get(0);

        } catch (Exception e) {
            throw new FWExcepcion("error.carga.usuario",
                    "Error cargando el usuario en la clase " + this.getClass() + " en el metodo buscarUsuario()", e);
        }
    }

    /**
     * Busca los usuarios que pueden ser jefes
     *
     * @param pFiltroCodUsuario Codigo del usuario
     * @param pFiltroNombreUsuario Nombre del usuario
     * @param pPrimerRegistro Para paginación
     * @param pUltimoRegistro Para paginación
     * @return Lista de usuarios
     */
    @Transactional
    public List<SegUsuario> usuariosJefes() throws FWExcepcion {
        try {
            return (List<SegUsuario>) dao.getHibernateTemplate().findByNamedQuery("SegUnidadUsuario.usuariosJefes");
        } catch (Exception e) {
            throw new FWExcepcion("error.carga.usuario",
                    "Error cargando el usuario en la clase " + this.getClass() + " en el metodo usuariosEjecutores()", e);
        }
    }

    /**
     * Busca los usuarios que pueden ser ejecutores
     *
     * @param pFiltroCodUsuario Codigo del usuario
     * @param pFiltroNombreUsuario Nombre del usuario
     * @param pPrimerRegistro Para paginación
     * @param pUltimoRegistro Para paginación
     * @return Lista de usuarios
     */
    @Transactional
    public List<SegUsuario> usuariosEvaluadores(String pFiltroCodUsuario,
            String pFiltroNombreUsuario,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro) throws FWExcepcion {
        try {
            String[] lNombresParam = new String[4];
            Object[] lValoresParam = new Object[4];
            //Nombres de los parametros
            lNombresParam[0] = "pFiltroCodUsuario";
            lNombresParam[1] = "pFiltroNombreUsuario";
            lNombresParam[2] = "pPrimerRegistro";
            lNombresParam[3] = "pUltimoRegistro";

            //Valores de los parámetros
            lValoresParam[0] = pFiltroCodUsuario;
            lValoresParam[1] = pFiltroNombreUsuario;
            lValoresParam[2] = pPrimerRegistro;
            lValoresParam[3] = pUltimoRegistro;
            return (List<SegUsuario>) dao.getHibernateTemplate().
                    findByNamedQueryAndNamedParam("SegUnidadUsuario.usuariosEvaluadores", lNombresParam, lValoresParam);
        } catch (Exception e) {
            throw new FWExcepcion("error.carga.usuario",
                    "Error cargando el usuario en la clase " + this.getClass() + " en el metodo usuariosEjecutores()", e);
        }
    }

    /**
     * Busca los usuarios que pueden ser ejecutores
     *
     * @param pFiltroCodUsuario Codigo del usuario
     * @param pFiltroNombreUsuario Nombre del usuario
     * @param pPrimerRegistro Para paginación
     * @param pUltimoRegistro Para paginación
     * @return Lista de usuarios
     */
    @Transactional
    public List<SegUsuario> usuariosAplicadores(String pFiltroCodUsuario,
            String pFiltroNombreUsuario,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro) throws FWExcepcion {
        try {
            String[] lNombresParam = new String[4];
            Object[] lValoresParam = new Object[4];
            //Nombres de los parametros
            lNombresParam[0] = "pFiltroCodUsuario";
            lNombresParam[1] = "pFiltroNombreUsuario";
            lNombresParam[2] = "pPrimerRegistro";
            lNombresParam[3] = "pUltimoRegistro";

            //Valores de los parámetros
            lValoresParam[0] = pFiltroCodUsuario;
            lValoresParam[1] = pFiltroNombreUsuario;
            lValoresParam[2] = pPrimerRegistro;
            lValoresParam[3] = pUltimoRegistro;
            return (List<SegUsuario>) dao.getHibernateTemplate().
                    findByNamedQueryAndNamedParam("SegUnidadUsuario.usuariosAplicadores", lNombresParam, lValoresParam);
        } catch (Exception e) {
            throw new FWExcepcion("error.carga.usuario",
                    "Error cargando el usuario en la clase " + this.getClass() + " en el metodo usuariosEjecutores()", e);
        }
    }

    /**
     * Busca la cantidad de usuarios que pueden ser aplicadores
     *
     * @param pFiltroCodUsuario Codigo del usuario
     * @param pFiltroNombreUsuario Nombre del usuario
     * @return Cantidad de usuarios
     */
    @Transactional
    public Integer cantidadUsuariosEvaluadores(String pFiltroCodUsuario,
            String pFiltroNombreUsuario) throws FWExcepcion {
        try {
            String[] lNombresParam = new String[2];
            Object[] lValoresParam = new Object[2];
            //Nombres de los parametros
            lNombresParam[0] = "pFiltroCodUsuario";
            lNombresParam[1] = "pFiltroNombreUsuario";

            //Valores de los parámetros
            lValoresParam[0] = pFiltroCodUsuario;
            lValoresParam[1] = pFiltroNombreUsuario;
            String val = ((BigDecimal) dao.getHibernateTemplate().
                    findByNamedQueryAndNamedParam("SegUnidadUsuario.cantidadUsuariosEvaluadores", lNombresParam, lValoresParam).get(0)).toPlainString();
            return Integer.valueOf(val);
        } catch (Exception e) {
            throw new FWExcepcion("error.carga.usuario",
                    "Error cargando el usuario en la clase " + this.getClass() + " en el metodo cantidadUsuariosEjecutores()", e);
        }
    }

    /**
     * Busca la cantidad de usuarios que pueden ser aplicadores
     *
     * @param pFiltroCodUsuario Codigo del usuario
     * @param pFiltroNombreUsuario Nombre del usuario
     * @return Cantidad de usuarios
     */
    @Transactional
    public Integer cantidadUsuariosAplicadores(String pFiltroCodUsuario,
            String pFiltroNombreUsuario) throws FWExcepcion {
        try {
            String[] lNombresParam = new String[2];
            Object[] lValoresParam = new Object[2];
            //Nombres de los parametros
            lNombresParam[0] = "pFiltroCodUsuario";
            lNombresParam[1] = "pFiltroNombreUsuario";

            //Valores de los parámetros
            lValoresParam[0] = pFiltroCodUsuario;
            lValoresParam[1] = pFiltroNombreUsuario;
            String val = ((BigDecimal) dao.getHibernateTemplate().
                    findByNamedQueryAndNamedParam("SegUnidadUsuario.cantidadUsuariosAplicadores", lNombresParam, lValoresParam).get(0)).toPlainString();
            return Integer.valueOf(val);
        } catch (Exception e) {
            throw new FWExcepcion("error.carga.usuario",
                    "Error cargando el usuario en la clase " + this.getClass() + " en el metodo cantidadUsuariosAplicadores()", e);
        }
    }
    //<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
    //<<>><<>><<>><<>> INGRESO
    //<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>

    /**
     * Obtiene todas las unidades Ejecutoras que tiene el usuario, que tienen
     * acceso al sistema que se indica como parametro
     *
     * @param usuario Usuario a obtener unidades
     * @param sistema SegSistema al cual se desea acceder.
     * @return Lista de Objetos unidades usuario.
     */
    //@Transactional
    @SuppressWarnings("unchecked")
    public List<SegUnidadUsuario> obtenerUnidadesUsuarioPorSistema(final String usuario) {
        List<SegUnidadUsuario> unidades = new ArrayList<SegUnidadUsuario>();
        try {
            //Nombres de los parametros
            String[] lNombresParam = new String[2];
            lNombresParam[0] = "pIdUsuario";
            lNombresParam[1] = "pIdSistema";

            //Valores de los parámetros
            Object[] lValoresParam = new Object[2];
            lValoresParam[0] = usuario;
            lValoresParam[1] = Util.getSistema();

            /*Query query = dao.getHibernateTemplate().getSessionFactory().getCurrentSession()
             .getNamedQuery("SegUnidadUsuario.findUnidadesUsuario")
             .setParameter(1, usuario).setParameter(2, Util.getSistema());
             List result = query.list();
             for (int i = 0; i < result.size(); i++) {
             SegUnidadUsuarioLlave llave = (SegUnidadUsuarioLlave) result.get(i);
             unidades.add(new SegUnidadUsuario(llave));
             }*/

            /*List<Object[]> postAndComments = dao.getHibernateTemplate().
             findByNamedQuery("SegUnidadUsuario.findUnidadesUsuario", usuario, Util.getSistema());
             .createNamedQuery("SegUnidadUsuario.findUnidadesUsuario").setParameter(1, 1L).getResultList();
             Object[] postAndComment = postAndComments.get(0);
             SegUnidadUsuario post = (SegUnidadUsuario) postAndComment[0];*/
            /*java.sql.Connection con = dao.getHibernateTemplate().getSessionFactory().getCurrentSession().connection();
             CallableStatement statement = con.prepareCall("SELECT SEGURIDAD.FC_SEG_UNIDADES_POR_USUARIO(?, ?) FROM DUAL");
             statement.setString(1, usuario);
             statement.setString(2, Util.getSistema());
             statement.execute();
             ResultSet rs = statement.executeQuery();*/
            
            
            unidades = (List<SegUnidadUsuario>) dao.getHibernateTemplate().execute(new HibernateCallback() {
                public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                    return session.getNamedQuery("SegUnidadUsuario.findUnidadesUsuario")
                            .setParameter("pIdUsuario", usuario)
                            .setParameter("pIdSistema", Util.getSistema())
                            .list();
                }
            });
            
            /*unidades = (List<SegUnidadUsuario>) dao.getHibernateTemplate()
                    .findByNamedQueryAndNamedParam("SegUnidadUsuario.findUnidadesUsuario", lNombresParam, lValoresParam);*/

            /*unidades = dao.getHibernateTemplate().findByNamedQueryAndNamedParam("SegUnidadUsuario.findUnidadesUsuario", lNombresParam, lValoresParam);*/
        } catch (Exception ex) {
            throw new ExcepcionSeguridad("Error encontrando las unidades ejecutoras a las que pertenece el usuario: " + usuario,
                    "Error encontrando las unidades ejecutoras a las que pertenece el usuario: " + usuario + " en la clase " + getClass() + " en el obtenerUnidadesUsuarioPorSistema(Usuario usuario, String sistema)",
                    4, ex);
        }
        return unidades;
    }

    /**
     * Este metodo es el encargado de consultar a la base de datos, por todos
     * aquellos elementos a los cuales puede acceder un usuario, con una unidad
     * ejecutora especifica y un sistema. El procedimiento consulta por todos
     * los perfiles, que tiene ese usuario con dicha unidad ejecutora y a cada
     * perfil le carga los elementos que son padre.
     *
     * @param usuario Usuario a loguearse
     * @param unidad Unidad por la que loguear
     * @param SegSistema SegSistema actual a trabajar
     * @return Lista de elementos para ese usuario con la unidad ejecutora
     * seleccionada.
     */
    @Transactional
    public List<SegElemento> obtenerElementosPorUsuarioUnidadEjecutoraSistema(String usuario, SegUnidadEjecutora unidad, int[] tipo_elemento, int tipoModulo[]) throws FWExcepcion {
        List<SegElemento> elementos = new ArrayList<SegElemento>();
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(Util.getFechaActual());
            String formato_fecha = "dd-MM-yyyy";
            String fecha = Util.obtenerFecha(formato_fecha, c);
            elementos = dao.getHibernateTemplate().findByNamedQuery("SegElemento.findElementosSistemaUnidad", usuario, unidad.getSisUnidadEjecutoraPK().getIdUnidadEjecutora(), unidad.getSisUnidadEjecutoraPK().getIdEmpresa(), Util.getSistema(), fecha, formato_fecha, fecha, formato_fecha, tipo_elemento[0], tipo_elemento[1], tipoModulo[0], tipoModulo[1]);

        } catch (Exception ex) {
            throw new FWExcepcion("error.opciones.menu",
                    "Error encontrando los elementos para las opciones del menu,"
                    + " que se le cargaran al usuario : " + usuario + ". Error en la clase "
                    + getClass() + " en el metodo "
                    + "obtenerElementosUsuarioUnidadEjecutoraSistema(Usuario usuario, "
                    + "UnidadEjecutora unidad, String sistema_actual)", ex);
        }//fin catch//fin catch//fin catch//fin catch//fin catch//fin catch//fin catch//fin catch
        return elementos;
    }

    /**
     * *****************************************************************************
     * Native Query Obtener Perfiles de usuario
     * *****************************************************************************
     */
    /**
     * obtine los elementos asociados a un perfil de un ususario para el menu
     * principal
     *
     * @param id_perfil, tipo_elementos si es 4 es para el menu principal si es
     * algun otro es para el menu secundariod
     *
     * @return
     */
    @Transactional
    public List<SegElemento> obtenerElementosSegunPerfil(String id_perfil, Object[] tipo_elementos) {
        List<SegElemento> elementos = new ArrayList<SegElemento>();
        try {
            elementos = dao.getHibernateTemplate().findByNamedQuery("SegElemento.findElementosPerfil", id_perfil, tipo_elementos[0], tipo_elementos[1]);
        } catch (Exception ex) {
            throw new ExcepcionSeguridad("Error cargando las opciones de menú para el usuario actual",
                    "Error encontrando los elementos para las opciones del menu, que se le cargaran al perfil : " + id_perfil + ". Error en la clase " + getClass() + " en el metodo obtenerElementosSegunPerfil(String id_perfil)",
                    5, ex);
        }
        return elementos;
    }

    /**
     * Devuelve los perfiles asociados a un usuario
     */
    @Transactional
    public List<SegPerfil> obtenerPerfilesUsuario(String usuario, SegUnidadEjecutora unidad) {
        List<SegPerfil> perfiles = new ArrayList<SegPerfil>();
        try {
            perfiles = dao.getHibernateTemplate().findByNamedQuery("SegPerfil.findPerfilesUsuario", usuario, unidad.getSisUnidadEjecutoraPK().getIdUnidadEjecutora(), Util.getSistema());

        } catch (Exception ex) {
            throw new ExcepcionSeguridad("Error obteniendo perfiles de usuario",
                    "Error encontrando los perfiles que se le cargaran al usuario : " + usuario + ". Error en la clase " + getClass() + " en el metodo obtenerIdentificadorPerfilEvaluador(Usuario usuario, UnidadEjecutora unidad)",
                    5, ex);
        }
        return perfiles;
    }

    @Transactional
    public String obtenerUnidadEjecutora(String unidad, String pTipoUnidad) throws FWExcepcion {
        List temp;
        try {
            String[] lNombresParam = new String[2];
            Object[] lValoresParam = new Object[2];
            //Nombres de los parametros
            lNombresParam[0] = "pUnidadEjecutora";
            lNombresParam[1] = "pTipoUnidad";
            //Valores de los parámetros
            lValoresParam[0] = unidad;
            lValoresParam[1] = pTipoUnidad;
            //Resalizo la consulta
            temp = dao.getHibernateTemplate().
                    findByNamedQueryAndNamedParam(
                            "SegUnidadEjecutora.findEscuelaByIdUnidadEjecutora",
                            lNombresParam,
                            lValoresParam);
        } catch (Exception ex) {
            throw new FWExcepcion("error.escuela", "Error mientras se cargaban la escuela para el usuario. Clase:" + this.getClass() + " método: obtenerFacultadUA", ex);
        }
        if (temp.size() <= 0) {
            throw new FWExcepcion("error.escuela.asociada");
        } else {
            return temp.get(0).toString();
        }//fin del else
    }//fin obtenerEscuelaUA

    /**
     * Obtiene las Unidaddes Ejecutoras a travez de su escuela.
     *
     * @param pEscuela
     * @return Lista de unidades de la escuela
     */
    @Transactional
    public ArrayList<SegUnidadEjecutora> obtenerUnidadesPorEscuela(String pEscuela) throws FWExcepcion {
        ArrayList<SegUnidadEjecutora> elementos = new ArrayList<SegUnidadEjecutora>();
        try {
            elementos = (ArrayList<SegUnidadEjecutora>) dao.getHibernateTemplate().findByNamedQueryAndNamedParam("SegUnidadEjecutora.findByNumUnidadEjecSiaf", "numUnidadEjecSiaf", pEscuela);
        } catch (Exception ex) {
            throw new FWExcepcion("error.opciones.menu",
                    "Error en el metodo "
                    + "obtenerUnidadesPorEscuela", ex);
        }//fin catch//fin catch//fin catch//fin catch//fin catch//fin catch//fin catch//fin catch
        return elementos;
    }

    /**
     * Obtiene las Unidaddes Ejecutoras a travez de su escuela.
     *
     * @param pEscuela
     * @return Lista de unidades de la escuela
     */
    @Transactional
    public String obtenerUnidadesInfoPorId(Integer pIdUnidad) throws FWExcepcion {
        List<Object[]> elementos = new ArrayList<Object[]>();
        try {
            elementos = dao.getHibernateTemplate().findByNamedQueryAndNamedParam("SegUnidadEjecutora.nombreTipoUnidad", "pIdUnidadEjecutora", pIdUnidad);
            if (elementos.isEmpty()) {
                return null;
            }
            /**
             * 0: Nombre de la unidad 1: Tipo de unidad
             */
            String tipo = ((String) elementos.get(0)[1]);
            String unidad = ((String) elementos.get(0)[0]).replaceAll(((String) elementos.get(0)[1]), "");
            //Se devuelve el nombre completo concatenado con el tipo de unidad, si el nombre de la unidad contenía el tipo se quita del nombre
            if (unidad.startsWith(" ")) {
                return tipo + unidad;
            } else {
                return tipo + " " + unidad;
            }
        } catch (Exception ex) {
            throw new FWExcepcion("Error encontrando las unidades ejecutoras",
                    "Error en el metodo "
                    + "obtenerUnidadesInfoPorId", ex);
        }//fin catch//fin catch//fin catch//fin catch//fin catch//fin catch//fin catch//fin catch
    }

    /**
     * Obtiene las Unidaddes Ejecutoras padre a travez de su escuela.
     *
     * @param pEscuela
     * @return Lista de unidades de la escuela
     */
    @Transactional
    public String obtenerUnidadesPadreInfoPorEscuela(Integer pEscuela) throws FWExcepcion {
        List<Object[]> elementos = new ArrayList<Object[]>();
        try {
            elementos = dao.getHibernateTemplate().findByNamedQueryAndNamedParam("SegUnidadEjecutora.nombreTipoUnidadPadre", "pEscuela", pEscuela);
            if (elementos.isEmpty()) {
                return null;
            }
            /**
             * 0: Nombre de la unidad 1: Tipo de unidad
             */
            String tipo = ((String) elementos.get(0)[1]);
            String unidad = ((String) elementos.get(0)[0]).replaceAll(((String) elementos.get(0)[1]), "");
            //Se devuelve el nombre completo concatenado con el tipo de unidad, si el nombre de la unidad contenía el tipo se quita del nombre
            if (unidad.startsWith(" ")) {
                return tipo + unidad;
            } else {
                return tipo + " " + unidad;
            }
        } catch (Exception ex) {
            throw new FWExcepcion("Error encontrando las unidades ejecutoras",
                    "Error en el metodo "
                    + "obtenerUnidadesPadreInfoPorId", ex);
        }//fin catch//fin catch//fin catch//fin catch//fin catch//fin catch//fin catch//fin catch
    }

    /**
     * Obtiene las Unidaddes Ejecutoras a travez de su escuela.
     *
     * @param pEscuela
     * @return Lista de unidades de la escuela
     */
    @Transactional
    public String obtenerUnidadesInfoPorEscuela(Integer pEscuela) throws FWExcepcion {
        List<Object[]> elementos = new ArrayList<Object[]>();
        try {
            elementos = dao.getHibernateTemplate().findByNamedQueryAndNamedParam("SegUnidadEjecutora.nombreTipoEscuela", "pEscuela", pEscuela);
            if (elementos.isEmpty()) {
                return null;
            }
            /**
             * 0: Nombre de la unidad 1: Tipo de unidad
             */
            String tipo = ((String) elementos.get(0)[1]);
            String unidad = ((String) elementos.get(0)[0]).replaceAll(((String) elementos.get(0)[1]), "");
            //Se devuelve el nombre completo concatenado con el tipo de unidad, si el nombre de la unidad contenía el tipo se quita del nombre
            if (unidad.startsWith(" ")) {
                return tipo + unidad;
            } else {
                return tipo + " " + unidad;
            }
        } catch (Exception ex) {
            throw new FWExcepcion("Error encontrando las unidades ejecutoras",
                    "Error en el metodo "
                    + "obtenerUnidadesPadreInfoPorId", ex);
        }//fin catch//fin catch//fin catch//fin catch//fin catch//fin catch//fin catch//fin catch
    }

    /**
     * Busca la cantidad de unidades ejecutoras para una propuesta
     * administrativa
     *
     * @return Cantidad de unidades
     */
    @Transactional
    public Integer cantidadUnidadesParaPropAdmin(String pFiltroNombre) throws FWExcepcion {
        try {
            String val = ((BigDecimal) dao.getHibernateTemplate().findByNamedQueryAndNamedParam("SegUnidadEjecutora.cantidadUnidadesParaPropAdmin", "pFiltroNombre", pFiltroNombre).get(0)).toPlainString();
            return Integer.valueOf(val);
        } catch (Exception e) {
            throw new FWExcepcion("error.carga.usuario",
                    "Error cargando el usuario en la clase " + this.getClass() + " en el metodo cantidadUnidadesParaPropAdmin()", e);
        }
    }

    /**
     * Obtiene todas las unidades Ejecutoras que tiene el usuario, que tienen
     * acceso al sistema que se indica como parametro. Este este caso el sistema
     * 'Sippres'
     *
     * @param pPrimerRegistro Para listado
     * @param pUltimoRegistro Para listado
     * @return Lista de Objetos de unidades ejecutoras
     */
    @Transactional
    public List<SegUnidadEjecutora> unidadesParaPropAdmin(Integer pPrimerRegistro,
            Integer pUltimoRegistro,
            String pFiltroNombre) {
        try {
            String[] lNombresParam = new String[3];
            Object[] lValoresParam = new Object[3];
            //Nombres de los parametros
            lNombresParam[0] = "pPrimerRegistro";
            lNombresParam[1] = "pUltimoRegistro";
            lNombresParam[2] = "pFiltroNombre";
            //Valores de los parámetros
            lValoresParam[0] = pPrimerRegistro;
            lValoresParam[1] = pUltimoRegistro;
            lValoresParam[2] = pFiltroNombre;
            //Resalizo la consulta
            return (List<SegUnidadEjecutora>) dao.getHibernateTemplate().
                    findByNamedQueryAndNamedParam(
                            "SegUnidadEjecutora.unidadesParaPropAdmin",
                            lNombresParam,
                            lValoresParam);
        } catch (Exception e) {
            throw new FWExcepcion("error.carga.usuario",
                    "Error cargando el usuario en la clase " + this.getClass() + " en el metodo unidadesParaPropAdmin()", e);
        }
    }

    /**
     * Obtiene todas las unidades Ejecutoras que son escuelas
     *
     * @param pPrimerRegistro Para listado
     * @param pUltimoRegistro Para listado
     * @return Lista de Objetos de unidades ejecutoras
     */
    @Transactional(readOnly = true)
    public List<SegUnidadEjecutora> obtieneEscuelas() {
        try {
            //Resalizo la consulta
            return (List<SegUnidadEjecutora>) dao.getHibernateTemplate().
                    findByNamedQuery(
                            "SegUnidadEjecutora.findEscuelas");
        } catch (Exception e) {
            throw new FWExcepcion("error.carga.usuario",
                    "Error cargando el usuario en la clase " + this.getClass() + " en el metodo obtieneEscuelas()", e);
        }
    }

    /**
     * Busca la cantidad de unidades ejecutoras asignadas a una propuesta
     *
     * @param pNumeroPropuesta Numero de propuesta
     * @param pVersionPropuesta Versión de propuesta
     * @param pFiltroNombre Filtro con el nombre de unidad
     * @return Cantidad de unidades
     */
    public Integer cantidadUnidadesAsociadasAProp(Integer pNumeroPropuesta,
            Integer pVersionPropuesta,
            String pFiltroNombre) throws FWExcepcion {
        try {
            String[] lNombresParam = new String[3];
            Object[] lValoresParam = new Object[3];
            //Nombres de los parametros
            lNombresParam[0] = "pFiltroNombre";
            lNombresParam[1] = "pNumeroPropuesta";
            lNombresParam[2] = "pVersionPropuesta";
            //Valores de los parámetros           
            lValoresParam[0] = pFiltroNombre;
            lValoresParam[1] = pNumeroPropuesta;
            lValoresParam[2] = pVersionPropuesta;
            //Resalizo la consulta
            String val = ((BigDecimal) dao.getHibernateTemplate().findByNamedQueryAndNamedParam("SegUnidadEjecutora.cantidadUnidadesAsociadasAProp",
                    lNombresParam,
                    lValoresParam).get(0)).toPlainString();
            return Integer.valueOf(val);
        } catch (Exception e) {
            throw new FWExcepcion("error.carga.usuario",
                    "Error cargando el usuario en la clase " + this.getClass() + " en el metodo cantidadUnidadesParaPropAdmin()", e);
        }
    }

       /**
     * Busca si la escuela es una sede regional
     *
     * @param pEscuela Escuela de la sede
     * @return Dice si es sede o no
     */
    public boolean esSedeRegional(String pEscuela) throws FWExcepcion {
        try {
            //Resalizo la consulta
            String val = ((BigDecimal) dao.getHibernateTemplate().findByNamedQueryAndNamedParam("SegUnidadEjecutora.esSedeRegional",
                    "pEscuela",
                    pEscuela).get(0)).toPlainString();
            return (Integer.valueOf(val) > 0);
        } catch (Exception e) {
            throw new FWExcepcion("error.carga.usuario",
                    "Error cargando el usuario en la clase " + this.getClass() + " en el metodo cantidadUnidadesParaPropAdmin()", e);
        }
    }

    public SegUnidadEjecutora obtenerUnidadEjecByCodReferencia(String pCodReferencia) {
        DetachedCriteria URjectoraCriteria
                = DetachedCriteria.forClass(SegUnidadEjecutora.class);
        URjectoraCriteria.add(Restrictions.eq("codigoReferencia", pCodReferencia));
        List temp = dao.getHibernateTemplate().findByCriteria(URjectoraCriteria);
        if (temp.isEmpty()) {
            return new SegUnidadEjecutora();
        } else {
            return (SegUnidadEjecutora) temp.get(0);
        }
    }
    

    /**
     * Método que permite obtener la unidad ejecutora padre
     * @author Manuel Sanabria
     * @fecha 05/04/2017
     * @param idUnidadEjecutoraPadre
     * @return 
     */
    public SegUnidadEjecutora obtenerUnidadEjecutoraPadre(Integer idUnidadEjecutoraPadre){
        try {
            String parametros[] = new String[1];            
            parametros[0] = "idUnidadEjecutoraPadre";            
            Object valores[] = new Object[1];
            valores[0] = idUnidadEjecutoraPadre;
            return (SegUnidadEjecutora)dao.getHibernateTemplate().findByNamedQueryAndNamedParam("SegUnidadEjecutora.findUnidadEjecutoraPadre", parametros, valores).get(0);
        } catch (Exception e) {
            throw new FWExcepcion("error.login.getRecinto", "Error al obtener el recinto asociado del login en:  getRecinto(Integer idUnidadEjecutoraPadre). En la clase: "+ this.getClass());
        }
    }
}//fin clase