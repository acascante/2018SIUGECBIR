package cr.ac.ucr.framework.seguridad.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * Almacena código HTML que va a ser usado constantemente por el sistema.
 * @author Pious
 * @version 1.0 - Adam M. Gamboa González - 27/04/2010
 * @since 27/04/2010
 */
public class UTF8 {
	/**
	 * No queremos que se pueda instanciar la clase
	 * @since /2009
	 */
	private UTF8() {
	}

	/**
	 * Convierte las tildes y Ñ a codificación UTF8 para los String UTF16.
	 * Todos los caracteres de los String son codificados en UTF16, por lo que para
	 * su despliegue, se requiere convertirlo a UTF8.
	 * @param original String
	 * @return String con caracteres UTF8
	 * @see java.lang.String
	 */
	public static String aUTF8(String original) {
		original = original.replaceAll("á", "\u00E1");
		original = original.replaceAll("é", "\u00E9");
		original = original.replaceAll("í", "\u00ED");
		original = original.replaceAll("ó", "\u00F3");
		original = original.replaceAll("ú", "\u00FA");

		original = original.replaceAll("Á", "\u00C1");
		original = original.replaceAll("É", "\u00C9");
		original = original.replaceAll("Í", "\u00CD");
		original = original.replaceAll("Ó", "\u00D3");
		original = original.replaceAll("Ú", "\u00DA");

		original = original.replaceAll("ñ", "\u00D1");
		original = original.replaceAll("Ñ", "\u00F1");
		return original;
	} //aUTF8

	/**
	 * Convierte la fecha a un formato de dd-MM-yyyy
	 * @param fecha Date
	 * @return String con el formato de fecha adecuado
	 * @deprecated Es mejor usar el método formatearFecha(Date) pues este devuelve
	 * la hora.
	 */
	@Deprecated
	public static String pasarDateString(Date fecha) {
		if (fecha == null) {
			fecha = new Date();
		}
		DateFormat fecha_formato = new SimpleDateFormat("dd-MM-yyyy");
		return fecha_formato.format(fecha);
	} //pasarDateString

	/**
	 * Convierte la fecha a un formato adecuado para la presentación, agregando incluso la hora.
	 * @param d Date
	 * @return String
	 */
	public static String formatearFecha(Date d) {
		if (d == null) {
			d = Calendar.getInstance(new Locale("es", "CR")).getTime();
		} //if
		DateFormat df =
				  DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT,
												 new Locale("es", "CR"));
		String temp = df.format(d);
		return temp.substring(0, (temp.length() - 3));
	} //formatearFecha

}   //fin de la clase CodigoHTML