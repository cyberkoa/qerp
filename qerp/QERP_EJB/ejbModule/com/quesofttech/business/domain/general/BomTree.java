package com.quesofttech.business.domain.general;

import java.io.Serializable;
//import java.sql.Date;
//import java.sql.Time;
//import java.sql.Timestamp;

/*
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.persistence.Embedded;
//import javax.persistence.SequenceGenerator;
*/

//import com.quesofttech.business.common.exception.BusinessException;
//import com.quesofttech.business.domain.base.BaseEntity;
//import com.quesofttech.business.domain.embeddable.RowInfo;
import com.quesofttech.business.domain.general.BomTreeNodeData;

//import com.quesofttech.business.common.exception.ValueRequiredException;
//import com.quesofttech.business.common.exception.GenericBusinessException;
//import com.quesofttech.util.StringUtil;
import com.quesofttech.util.Tree;
import com.quesofttech.util.TreeNode;

import java.util.ArrayList;
import java.util.List;
//import java.util.TreeSet;

public class BomTree extends Tree<BomTreeNodeData> implements Serializable {

	//private static final long serialVersionUID = 7422574264557894632L;
	public BomTree() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	private Long id;
	
	
	
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
	
}