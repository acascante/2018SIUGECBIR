/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cr.ac.ucr.framework.reporte.componente.utilitario;

/**
 * @author Adrián Zamora Villalta
 * @version 1.1
 * @since 07/07/2015
 * 
 * Lista de todos los tipos de exportacion de los reportes
 * 
 */
public enum TipoExportacion {

    /**
     * Documentos PDF.
     */
    PDF(1,"PDF",".pdf","application/pdf"),
    /**
     * Documentos Microsoft Excel 2003.
     */
    EXCEL2003(2,"EXCEL 2003",".xls","application/vnd.ms-excel"),
    /**
     * Documentos en formato de texto enriquecido.
     */
    RTF(3,"RTF",".rtf","text/richtext"),
    /**
     * Documentos Microsoft Power Point 2007.
     */
    PPTX(6,"POWER POINT 2007",".pptx","application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    /**
     * Documentos Microsoft Excel 2007.
     */
    EXCEL2007(7,"EXCEL 2007",".xlsx","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    /**
     * Documentos Microsoft Word 2007.
     */
    WORD2007(8,"WORD 2007",".docx","application/vnd.openxmlformats-officedocument.wordprocessingml.document"),;

    /**
     * Identificación del enumerador
     */
    private int id;
    /**
     *Nombre del tipo de reporte.
     */
    private String nombre;
    /**
     *Extension del tipo de reporte.
     */
    private String extension;
    /**
     * El tipo de contenido del reporte.
     */
    private String mimetype;

    /**
     * Contructor del enumerador.
     * @param id identificación del reporte.
     * @param nombre nombre del tipo de reporte.
     * @param extension tipo de extension
     * @param mimetype tipo de contenido.
     */
     TipoExportacion(int id,String nombre,String extension,String mimetype)
    {
        this.id = id;
        this.nombre = nombre;
        this.extension = extension;
        this.mimetype = mimetype;
    }

     /**
      * Obtiene la extensión de acuerdo al tipo de documento.
      * @return Extensión del documento.
      */
    public String getExtension()
    {
        return extension;
    }

    /**
     * Indica el ID unico de cada tipo de documento.
     * @return
     */
    public int getId()
    {
        return id;
    }

    /**
     * Devuelve el nombre del tipo de documento.
     * @return Nombre del Documento.
     */
    public String getNombre()
    {
        return nombre;
    }

    /**
     * Devuelve el tipo de contenido del reporte
     * @return tipo de contenido.
     */
    public String getMimetype()
    {
        return mimetype;
    }
}
