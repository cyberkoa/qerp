package com.quesoware.web.model.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.internal.OptionGroupModelImpl;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.PropertyAdapter;
import org.apache.tapestry5.util.AbstractSelectModel;

import com.quesoware.web.services.IBusinessServicesLocator;

/** Generic selection model for a list of Objects.
 * use:
 * <pre>@Inject private PropertyAccess _access;</pre>
 * in your page to ge the {@link PropertyAccess} service.<br>
 * !Notice: you must set the created instance both as model and encoder parameter for the {@link Select} component.*/
public class GenericSelectModel<T> extends AbstractSelectModel implements ValueEncoder<T> {
	
	
    private PropertyAdapter labelFieldAdapter = null;
    private PropertyAdapter idFieldAdapter = null;
    private List<T>         list;
     

    public GenericSelectModel(List<T> list, Class<T> clazz, String labelField, String idField, PropertyAccess access) {
        this.list = list;
        if (idField != null)
            this.idFieldAdapter = access.getAdapter(clazz).getPropertyAdapter(idField);
        if (labelField != null)
            this.labelFieldAdapter = access.getAdapter(clazz).getPropertyAdapter(labelField);
    }

    public void addOptionGroup(String label, boolean disabled, List<T> options) {
        List<OptionModel> optionModels = new ArrayList<OptionModel>();
        List<OptionGroupModel> optionGroups = null;
        
        if (labelFieldAdapter == null) {
            for (T obj : options) {
                optionModels.add(new OptionModelImpl(handleNull(obj), obj));
            }
        } else {
            for (T obj : options) {
                optionModels.add(new OptionModelImpl(handleNull(labelFieldAdapter.get(obj)), obj));
            }
        }

        /*
        if (optionGroups == null) {
            optionGroups = new ArrayList<OptionGroupModel>();
        }

        optionGroups.add(new OptionGroupModelImpl(label, disabled, optionModels, new String[0]));
        */
    }

    public List<OptionGroupModel> getOptionGroups() {
        return null;
    	//return optionGroups;
    }

    public List<OptionModel> getOptions() {
        List<OptionModel> optionModelList = new ArrayList<OptionModel>();
        if (labelFieldAdapter == null) {
            for (T obj : list) {
                optionModelList.add(new OptionModelImpl(handleNull(obj)));
            }
        } else {
            for (T obj : list) {
                optionModelList.add(new OptionModelImpl(handleNull(labelFieldAdapter.get(obj)), obj));
            }
        }
        return optionModelList;
    }

    // ValueEncoder functions
    public String toClient(T obj) {
        if (idFieldAdapter == null) {
            return obj + "";
        } else {
        	//System.out.println("[toClient] obj : " + obj.toString());
            return idFieldAdapter.get(obj) + "";
        }
    }

    public T toValue(String string) {
    	//System.out.println("[toValue] string : " + string);
        if (idFieldAdapter == null) {
            for (T obj : list) {
                if (handleNull(obj).equals(string)) return obj;
            }
        } else {
            for (T obj : list) {
                if (handleNull(idFieldAdapter.get(obj)).equals(string)) return obj;
            }
        }
        return null;
    }

    private String handleNull(Object o) {
        if (o == null)
            return "";
        else
            return o.toString();
    }
    
	public List<T> getList() {
		return this.list;
	}
    
}

