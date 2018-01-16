package cr.ac.ucr.sigebi.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

public class JsfUtil {
    
    public static List<SelectItem> eliminarItem(List<SelectItem> entities, Object value) {
        List<SelectItem> listaDatos = new ArrayList<SelectItem>();
        for (Iterator<SelectItem> iterator = entities.iterator(); iterator.hasNext();) {
            SelectItem item = iterator.next();
            if(!item.getValue().toString().equals(value.toString())){
                listaDatos.add(item);
            }
        }
        return listaDatos;
    }

    public static void modificarItem(List<SelectItem> entities, Object value, String label, String descripcion) {
        for (Iterator<SelectItem> iterator = entities.iterator(); iterator.hasNext();) {
            SelectItem item = iterator.next();
             if(item.getValue().toString().equals(value.toString())){
                 if(descripcion != null){
                     item.setDescription(descripcion);
                 }
                 if(label != null){
                     item.setLabel(label);
                 }                 
            }
        }
    }
    
    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", "---");
            i++;
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }

    public static void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(msg);
        } else {
            addErrorMessage(defaultMsg);
        }
    }

    public static void addErrorMessages(List<String> messages) {
        for (String message : messages) {
            addErrorMessage(message);
        }
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
        String theId = JsfUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }

}
