package com.quesofttech.business.domain.base;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.quesofttech.business.domain.embeddable.RowInfo;
import com.quesoware.business.common.exception.BusinessException;

//import org.hibernate.LazyInitializationException;
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	//protected abstract String recordStatus;
	
	@Embedded
	protected RowInfo rowInfo;
	
	public BaseEntity()
	{
		this.rowInfo = new RowInfo();
	}
	
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

	public String getRecordStatus() {
		return rowInfo.getRecordStatus();
	}



	public void setRecordStatus(String recordStatus) {
		this.rowInfo.setRecordStatus(recordStatus);
	}



	public String getSessionId() {
		return rowInfo.getSessionId();
	}


	public void setSessionId(String sessionId) {
		this.rowInfo.setSessionId(sessionId);
	}



	public String getCreateLogin() {
		return rowInfo.getCreateLogin();
	}



	public void setCreateLogin(String createLogin) {
		this.rowInfo.setCreateLogin(createLogin);
	}



	public String getCreateApp() {
		return rowInfo.getCreateApp();
	}



	public void setCreateApp(String createApp) {
		this.rowInfo.setCreateApp(createApp);
	}



	public java.sql.Timestamp getCreateTimestamp() {
		return rowInfo.getCreateTimestamp();
	}

	public void setCreateTimestamp(java.sql.Timestamp createTimestamp) {
		this.rowInfo.setCreateTimestamp(createTimestamp);
	}


	public String getModifyLogin() {
		return rowInfo.getModifyLogin();
	}

	public void setModifyLogin(String modifyLogin) {
		this.rowInfo.setModifyLogin(modifyLogin);
	}



	public String getModifyApp() {
		return rowInfo.getModifyApp();
	}



	public void setModifyApp(String modifyApp) {
		this.rowInfo.setModifyApp(modifyApp);
	}


	public java.sql.Timestamp getModifyTimestamp() {
		return rowInfo.getModifyTimestamp();
	}



	public void setModifyTimestamp(java.sql.Timestamp modifyTimestamp) {
		this.rowInfo.setModifyTimestamp(modifyTimestamp);
	}

	/**
	 * @return the rowInfo
	 */
	public RowInfo getRowInfo() {
		return rowInfo;
	}

	/**
	 * @param rowInfo the rowInfo to set
	 */
	public void setRowInfo(RowInfo rowInfo) {
		this.rowInfo = rowInfo;
	}

	
	@PrePersist
	protected void prePersist() throws BusinessException {
		//System.out.println("prePersist of BaseEntity");
	
		validate();
		
		rowInfo.setRecordStatus("A");		
	    java.util.Date today = new java.util.Date();
	    rowInfo.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
	    rowInfo.setCreateTimestamp(rowInfo.getModifyTimestamp());			
		//System.out.println("[PrePersist] " + rowInfo.getCreateLogin());
		
	}

	
	@PreUpdate
	protected void preUpdate() throws BusinessException {
		//System.out.println("preUpdate of BaseEntity");
		
		if(rowInfo.getRecordStatus()!="D")
		{
			validate();
		}
		/*
			java.util.Date today = new java.util.Date();

			rowInfo.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
		*/
		
	}
	// virtual function
    protected void validate() throws BusinessException
    {
    	
    }
	
	
}
