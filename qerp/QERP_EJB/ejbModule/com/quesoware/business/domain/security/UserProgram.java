package com.quesoware.business.domain.security;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
//import java.sql.*;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PostPersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.TableGenerator;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.persistence.Embedded;
//import javax.persistence.SequenceGenerator;


import com.quesofttech.business.domain.base.BaseEntity;
import com.quesofttech.business.domain.embeddable.RowInfo;
import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.common.exception.ValueRequiredException;
import com.quesoware.util.StringUtil;

import java.util.List;
//import java.util.ArrayList;

@Entity
@Table(name = "UserProgram", uniqueConstraints = { @UniqueConstraint(columnNames = { "fk_Program","fk_User" }) })
@SuppressWarnings("serial")
public class UserProgram extends BaseEntity {
	

	//For Postgresql : @SequenceGenerator(name = "UserProgram_sequence", sequenceName = "UserProgram_id_seq")
	//Generic solution : (Use a table named primary_keys, with 2 fields , key &  value)
	@TableGenerator(  name="UserProgram_id", table="PrimaryKeys", pkColumnName="tableName", pkColumnValue="UserProgram", valueColumnName="keyField")
	@Id
	//For Postgresql : @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserProgram_sequence")
	//For MSSQL      : @GeneratedValue(strategy = GenerationType.IDENTITY)
	//Generic solution :
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "UserProgram_id")	
	@Column(name = "id_UserProgram", nullable = false)
	private Long id;
	
	/*
    @Embeddable
    public static class Id
        implements Serializable
    {
        @Column(name="fk_User")
        private Long userId;

        @Column(name="fk_Program")
        private Long programId;

        public Id() {}

        public Id(Long userId, Long programId)
        {
            this.userId = userId;
            this.programId = programId;
        }

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((programId == null) ? 0 : programId.hashCode());
			result = prime * result
					+ ((userId == null) ? 0 : userId.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final Id other = (Id) obj;
			if (programId == null) {
				if (other.programId != null)
					return false;
			} else if (!programId.equals(other.programId))
				return false;
			if (userId == null) {
				if (other.userId != null)
					return false;
			} else if (!userId.equals(other.userId))
				return false;
			return true;
		}
        

    } 
	
    @EmbeddedId
    private Id id = new Id(); 
	*/
	
    @ManyToOne
    @JoinColumn(
        name="fk_User"//,
        //insertable="false",
        //updatable="false"
    )
    private User user; 
    
    @ManyToOne
    @JoinColumn(
        name="fk_Program"//,
        //insertable="false",
        //updatable="false"
    )
    private Program program; 
	
    
    
	@Version
	@Column(nullable = false)
	private Long version;


	// Example of field	
	@Column(name = "userp_IsAllowed", nullable = false)
	private Boolean isAllowed;
	
	
	//@Embedded
	//RowInfo rowInfo_1;
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


	/*
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="UserProgram", targetEntity=<ForeignTable>.class)
	private List<<ForeignTable>> <foreignTable>s; // = new List<<ForeignTable>>();
	*/
	public UserProgram() {
		super();
		
	}	

	
	// Constructors without the common fields
	public UserProgram(Long id/* all fields */) {
		super();
        //this.userId = user.getId();
        //this.programId = program.getId(); 
		
		/* Example of assignment
		this.id = id;
		this.type = type;
		this.description = description;
		*/
	}


	// Constructors with all fields
	public UserProgram(/* all fields*/
			String recordStatus, String sessionId, String createLogin,
			String createApp, Timestamp createTimestamp,
			String modifyLogin, String modifyApp, Timestamp modifyTimestamp) {
		super();

		
		//this.id = id;
		/* Example of assignment
		this.type = type;
		this.description = description;
		*/


		this.rowInfo.setRecordStatus(recordStatus);
		this.rowInfo.setSessionId(sessionId);
		this.rowInfo.setCreateLogin(createLogin);
		this.rowInfo.setCreateApp(createApp);
		//this.rowInfo.setCreateDate(createDate);
		this.rowInfo.setCreateTimestamp(createTimestamp);
		this.rowInfo.setModifyLogin(modifyLogin);
		this.rowInfo.setModifyApp(modifyApp);
		//this.rowInfo.setModifyDate(modifyDate);
		this.rowInfo.setModifyTimestamp(modifyTimestamp);
	}


	@Override
	public String toString() {
		
		StringBuffer buf = new StringBuffer();
		buf.append("UserProgram: [");
		buf.append("id=" + id + ", ");

		/* All fields
		buf.append("type=" + type + ", ");
		buf.append("description=" + description + ", ");
		buf.append("isProduced=" + isProduced + ", ");
		buf.append("isPurchased=" + isPurchased + ", ");
		buf.append("isForSales=" + isForSale + ", ");
		*/
		// Common fields
		buf.append("record status=" + rowInfo.getRecordStatus() + ", ");
		buf.append("session id=" + rowInfo.getSessionId() + ", ");
		buf.append("create login=" + rowInfo.getCreateLogin() + ", ");
		buf.append("create app=" + rowInfo.getCreateApp() + ", ");
		//buf.append("create date=" + rowInfo.getCreateDate() + ", ");
		buf.append("create timestamp=" + rowInfo.getCreateTimestamp() + ", ");
		buf.append("modify login=" + rowInfo.getModifyLogin() + ", ");
		buf.append("modify app=" + rowInfo.getModifyApp() + ", ");
		//buf.append("modify date=" + rowInfo.getModifyDate() + ", ");
		buf.append("modify time=" + rowInfo.getModifyTimestamp() + ", ");		
		buf.append("version=" + version);
		buf.append("]");
		return buf.toString();		
			
	}

	// The need for an equals() method is discussed at http://www.hibernate.org/109.html
/*
	@Override
	public boolean equals(Object obj) {
		return (obj == this) || (obj instanceof UserProgram) && getId() != null && ((UserProgram) obj).getId().equals(this.getId());
	}

	// The need for a hashCode() method is discussed at http://www.hibernate.org/109.html

	@Override
	public int hashCode() {
		return getId() == null ? super.hashCode() : getId().hashCode();
	}	
*/	
	
	
	
	@Override
	public Serializable getIdForMessages() {
		return getId();
	}

/*	
	@PrePersist
	protected void prePersist() throws BusinessException {
		validate();
		
		rowInfo.setRecordStatus("A");
		
	    java.util.Date today = new java.util.Date();
	    //System.out.println(today.getTime());
	    
	    //rowInfo.setModifyDate(new java.sql.Date(today.getTime()));
	    rowInfo.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
	    //rowInfo.setCreateDate(rowInfo.getModifyDate());
	    rowInfo.setCreateTimestamp(rowInfo.getModifyTimestamp());		
				
	}
*/
	@PostPersist
	void postPersist() throws BusinessException {

	}
	
	@PostLoad
	void postLoad() {

	}
/*
	@PreUpdate
	protected void preUpdate() throws BusinessException {
		if(rowInfo.getRecordStatus()!="D")
		{
			validate();
		}
		java.util.Date today = new java.util.Date();

		//rowInfo.setModifyDate(new java.sql.Date(today.getTime()));
		rowInfo.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
		
	}
*/
	@PreRemove
	void preRemove() throws BusinessException {
		// Check business rules here, eg.
		// if (entity.getParts().size() > 0) {
		// throw new CannotDeleteIsNotEmptyException(this, id, "Part");
		// }

		// There's no need to remove me from the parent(s) - that's the caller's
		// responsibility (and for performance, it might not bother)
	}

	public void validate() throws BusinessException  {

		
		// Validate syntax...

		if (user==null) {
			throw new ValueRequiredException(this, "User");		
		}

		if (program==null) {
			throw new ValueRequiredException(this, "Program");		
		}

		if (isAllowed==null) {
			throw new ValueRequiredException(this, "IsAllowed");		
		}

		
		//if (StringUtil.isEmpty(description)) {
		//	throw new ValueRequiredException(this, "MaterialType_Description");
		//}

		/*
		if (StringUtil.isEmpty(lastName)) {
			throw new ValueRequiredException(this, "User_lastName");
		}

		// Validate semantics...

		if (expiryDate != null && loginId.equals(ADMIN_LOGINID)) {
			throw new GenericBusinessException("User_expirydate_not_permitted_for_user", new Object[] { ADMIN_LOGINID });
		}

		 */
		//throw new ValueRequiredException(this, "Please remove this line");
	}
	
	

	// Common EJB field
	/*
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}
*/
	public Long getVersion() {
		return version;
	}



	public void setVersion(Long version) {
		this.version = version;
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


/*
	public java.sql.Date getModifyDate() {
		return rowInfo.getModifyDate();
	}



	public void setModifyDate(java.sql.Date modifyDate) {
		this.rowInfo.setModifyDate(modifyDate);
	}

*/

	public java.sql.Timestamp getModifyTimestamp() {
		return rowInfo.getModifyTimestamp();
	}



	public void setModifyTimestamp(java.sql.Timestamp modifyTimestamp) {
		this.rowInfo.setModifyTimestamp(modifyTimestamp);
	}



  public String getUserNameDesc()
  {
	  return user.getLogin();
  }
  public String getProgramDesc()
  {
	  return program.getDescription();
  }

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}


	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	


	/**
	 * @return the program
	 */
	public Program getProgram() {
		return program;
	}


	/**
	 * @param program the program to set
	 */
	public void setProgram(Program program) {
		this.program = program;
	}


	/**
	 * @return the isAllowed
	 */
	public Boolean getIsAllowed() {
		return isAllowed;
	}


	/**
	 * @param isAllowed the isAllowed to set
	 */
	public void setIsAllowed(Boolean isAllowed) {
		this.isAllowed = isAllowed;
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


	
}
