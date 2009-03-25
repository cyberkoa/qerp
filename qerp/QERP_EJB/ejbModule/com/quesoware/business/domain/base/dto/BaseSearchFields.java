package com.quesoware.business.domain.base.dto;

import java.io.Serializable;
import java.lang.reflect.*;
//import java.util.Date;

@SuppressWarnings("serial")
public class BaseSearchFields implements Serializable {


	public String toString() {
		StringBuffer buf = new StringBuffer();

        try {
        	
            Class cls = this.getClass();
        
            Field fieldlist[] 
              = cls.getDeclaredFields();
            for (int i= 0; i < fieldlist.length; i++) {
               Field fld = fieldlist[i];
               buf.append("\n" + fld.getName()+ ":" + fld.get(cls).toString());
            }
          }
          catch (Throwable e) {
             System.err.println(e);
          }

		
		return buf.toString();
	}


}
