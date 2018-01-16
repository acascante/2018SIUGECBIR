package cr.ac.ucr.framework.seguridad.utils;

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//import sun.misc.BASE64Encoder; - DEMP 19 MAR 2013
import org.apache.commons.codec.binary.Base64;// DEMP 19 MAR 2013


/**
 *
 * @author Pious
 * @version 1
 * @since 07/2009
 */
public class SHA256 {
		private SHA256() {
	}

	public synchronized String encriptar(String texto){
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256"); //paso 1
		} catch (NoSuchAlgorithmException e) {
			throw new ExcepcionSeguridad(UTF8.aUTF8("Error encriptando la contraseña"), e.getMessage());
		} //catch
		try {
			md.update(texto.getBytes("UTF-8")); //paso 2
		} catch (UnsupportedEncodingException e) {
			throw new ExcepcionSeguridad(UTF8.aUTF8("Error encriptando la contraseña"),e.getMessage());
		} //catch
		byte raw[] = md.digest(); //paso 3
		//String hash = (new BASE64Encoder()).encode(raw); //paso 4 - DEMP 19 MAR 2013
                String hash = new String((new Base64()).encode(raw)); //paso 4 - DEMP 19 MAR 2013
		return hash; //paso 5

            //return texto;
	} //encriptar


	/**
	 * Retorna la única instancia de este gestor
	 * @return
	 * @since 07/2009
	 */
	public static SHA256 getInstancia() {
		return SHA256.Singleton.instancia;
	} //getInstancia

	/**
	 * Clase de gestión para el patrón singleton, con validación a prueba de dobles instancias.
	 * @author Pious
	 * @version 1
	 * @since 07/2009
	 */
	private static class Singleton {
		private static final SHA256 instancia = new SHA256();
	} //Singleton
}   //SHA256