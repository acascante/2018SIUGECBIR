/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cr.ac.ucr.framework.vista.util;

import com.icesoft.faces.component.menubar.MenuItem;


/**
 *
 * @author jonnathan
 */
public class MenuItemPrincipal extends MenuItem {
    private Integer id_menu;
    private Integer id_modulo;
    private String regla_navegacion;

    public MenuItemPrincipal() {
    }

    public MenuItemPrincipal(Integer id_menu, Integer id_modulo) {
        this.id_menu = id_menu;
        this.id_modulo = id_modulo;
    }


    


    public Integer getId_menu() {
        return id_menu;
    }

    public void setId_menu(Integer id_menu) {
        this.id_menu = id_menu;
    }

    public Integer getId_modulo() {
        return id_modulo;
    }

    public void setId_modulo(Integer id_modulo) {
        this.id_modulo = id_modulo;
    }

    public String getRegla_navegacion() {
        return regla_navegacion;
    }

    public void setRegla_navegacion(String regla_navegacion) {
        this.regla_navegacion = regla_navegacion;
    }
    



}
