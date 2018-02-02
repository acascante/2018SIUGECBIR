/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.utils;

import javax.faces.model.SelectItem;

/**
 *
 * @author alvaro.cascante
 */
public class SelectItemObject extends SelectItem {
    
    private Object item;

    public SelectItemObject(Object item, Object value, String label) {
        super(value, label);
        this.item = item;
    }

    @Override
    public Object getValue() {
        return item;
    }
    
    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }
}
