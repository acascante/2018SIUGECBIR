
package cr.ac.ucr.framework.seguridad.utils;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;

/**
 * Clase encargada de la conexion por LDAP, para la autentificacion de
 * los usuarios.
 * @author Cristian Moreira
 * @version 1.5 Adam M. Gamboa González 12 Octubre
 * @since 30-Abril-2010
 */
public class Ldap {

    private String INITCTX = "com.sun.jndi.ldap.LdapCtxFactory";
    private String HOST_LDAP = "ldaps://ldap2.ucr.ac.cr";
//    private String HOST_LDAP = "t3://localhost:7001";
    private DirContext ctx;

    public Ldap() {
    }

    /**
     * verifica que el usuario y contrasenna esten corretos en el Ldap
     * @return                  String si el usuario y clave esta correcto
     *                          "" si el usuario o clave esta incorrecto o no existe
     *                          null si no existe coneccion con el Ldap
     * @fechaCreacion           2010-03-11
     * @fechaUltimaModificacion 2010-03-12
     */
    public boolean autenticarUsuario(String user, String pass)
    {
        if (pass.compareTo("") == 0 || user.compareTo("") == 0)
            return false;

        this.probarConexionLDAP();//Lanza excepcion en caso de no existir conexion con el LDAP

        try{
            this.conectarLDAP(user, pass); //Si logra conectar el usuario existe con la contraseña.
            return true;
        }catch(NamingException nex){
            return false;
        }
    }

    /**
     *
     * @param usuario_buscar Identificador del usuario a buscar
     * @param usuario_adm Identificador del usuario logueado para conectarse al LDAP
     * @param pass_adm contraseña del usuario que se conectara para administrar el LDAP
     * @return
     */
    public boolean existeUsuario(String usuario_buscar, String usuario_adm, String pass_adm){
        if (usuario_buscar.compareTo("") == 0) {
            return false;
        }

        this.probarConexionLDAP();
        try{
            this.conectarLDAP(usuario_adm, pass_adm);
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration results = ctx.search("ou=Special Users,dc=ucr,dc=ac,dc=cr", "uid="+usuario_buscar , constraints);

            if(results.hasMore()){
                return true;
            }
            return false;
        }catch(NamingException nex){
            cerrarConexionLDAP();
            return false;
        }
    }

    /**
     * Prueba que exista una conexion con el servidor LDAP
     */
    private void probarConexionLDAP(){
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, INITCTX);
        props.put(Context.SECURITY_AUTHENTICATION,"simple");
        props.put(Context.PROVIDER_URL, HOST_LDAP);
        try {
            System.out.println("va a probar conexion al ldap");
            ctx = new InitialDirContext(props);
            System.out.println("conexion al ldap bien");
            this.cerrarConexionLDAP();
        }
        catch (NamingException e) {
            StackTraceElement[] ste =  e.getStackTrace();
            throw new ExcepcionSeguridad("Error con la conexión LDAP",
            "Error tratando de conectarse por LDAP, en: "+getClass()+".probarConexionLDAP",
            1,e);
        }
    }

    /**
     * Abre una conexion al LDAP
     * @param usuario usuario a conectarse
     * @param pass contraseña del usuario.
     * @throws NamingException Lanza exception si no hay conexion con el LDAP
     */
    private void conectarLDAP(String usuario, String pass) throws NamingException{
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, INITCTX);
        props.put(Context.SECURITY_AUTHENTICATION,"simple");
        props.put(Context.PROVIDER_URL, HOST_LDAP);
        props.put(Context.SECURITY_PROTOCOL, "ssl");
        props.put(Context.SECURITY_PRINCIPAL, "uid="+usuario+",cn=users,cn=accounts,dc=ucr,dc=ac,dc=cr");
        props.put(Context.SECURITY_CREDENTIALS, pass);

        ctx = new InitialDirContext(props);
    }

    /**
     * Cierra la conexion existente con el LDAP
     */
    private void cerrarConexionLDAP(){
        try{
            if(ctx != null){
                ctx.close();
            }
        }catch(NamingException nex){

        }
    }
}