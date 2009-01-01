package com.quesofttech.business.domain.base;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//import org.hibernate.LazyInitializationException;

public abstract class BaseEntity implements Serializable {

	//protected abstract String recordStatus;
	
	public abstract Serializable getIdForMessages();

	/**
	 * Useful when referencing objects that may cause LazyInitializationExceptions that you don't really care about. For
	 * example, if you replace this: <code>this.customer.getId().toString()</code>, with this
	 * <code>toStringLazy(customer, "getId")</code>, then exceptions will be handled and one of these values
	 * returned:
	 * <ul>
	 * <li>The toString() value is returned if no exception occurs.</li>
	 * <li>"&lt;lazy&gt;" is returned if LazyInitializationException occurs.</li>
	 * <li>"&lt;null&gt;" is returned if the specified getter returns null.</li>
	 * <li>"&lt;null&gt;" is returned if the specified object is null.</li>
	 * </ul>
	 * 
	 * @param obj
	 *            An object eg. customer.
	 * @param getterName
	 *            A method to execute on the object, eg. "getId".
	 * @return "&lt;null&gt;", "&lt;lazy&gt;", or the result, eg. of customer.getId().toString().
	 */
	public String toStringLazy(Object obj, String getterName) {
		if (obj == null) {
			return "null";
		}

		try {
			Class c = obj.getClass();
			Method getterMethod = c.getMethod(getterName, (Class[]) null);
			Object result = getterMethod.invoke(obj, (Object[]) null);
			if (result == null) {
				return "<null>";
			}
			return result.toString();
		}
		catch (InvocationTargetException e) {
			//Throwable t = e.getTargetException();
/*			if (t instanceof LazyInitializationException) {
				return "<lazy>";
			}
			else {*/
				throw new IllegalStateException("Exception in method lazy(..) with obj=" + obj + ", getterName="
						+ getterName + ".", e);
			//}
		}
		catch (Exception e) {
			throw new IllegalStateException("Exception in method lazy(..) with obj=" + obj + ", getterName="
					+ getterName + ".", e);
		}
	}

}