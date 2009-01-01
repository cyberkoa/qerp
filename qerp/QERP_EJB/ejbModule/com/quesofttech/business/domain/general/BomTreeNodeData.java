package com.quesofttech.business.domain.general;

import java.io.Serializable;
//import java.sql.Date;
//import java.sql.Time;
import java.sql.Timestamp;

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


import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.domain.base.BaseEntity;
import com.quesofttech.business.domain.embeddable.RowInfo;
import com.quesofttech.business.domain.inventory.Material;
import com.quesofttech.business.domain.general.BomDetail;

import com.quesofttech.business.common.exception.ValueRequiredException;
import com.quesofttech.business.common.exception.GenericBusinessException;
import com.quesofttech.util.StringUtil;

import java.util.List;
//import java.util.ArrayList;

public class BomTreeNodeData implements Serializable {
	
	private BomDetail bomDetail = null;
	
	
	
	//private Material material = null;

	// single level
	
	//private double scrapFactor;
	
	private Double actualQuantityRequired;
	
	private Double originalQuantityRequired;
	
	
	// Whole Tree level
	
	private double treeScrapFactor = 1.0;
	
	private Double treeActualQuantityRequired = 0.0;

	private Double treeOriginalQuantityRequired = 0.0;
	
	private Double treeActualValue = 0.0;

	private Double treeOriginalValue = 0.0;

	
	
	/* Methods */

	/**
	 * @return the bomDetail
	 */
	public BomDetail getBomDetail() {
		return bomDetail;
	}

	/**
	 * @param bomDetail the bomDetail to set
	 */
	public void setBomDetail(BomDetail bomDetail) {
		this.bomDetail = bomDetail;
	}
	
	
	/**
	 * @return the treeActualQuantityRequired
	 */
	public Double getTreeActualQuantityRequired() {
		return treeActualQuantityRequired;
	}

	/**
	 * @param treeActualQuantityRequired the treeActualQuantityRequired to set
	 */
	public void setTreeActualQuantityRequired(Double treeActualQuantityRequired) {
		this.treeActualQuantityRequired = treeActualQuantityRequired;
	}

	/**
	 * @return the treeOriginalQuantityRequired
	 */
	public Double getTreeOriginalQuantityRequired() {
		return treeOriginalQuantityRequired;
	}

	/**
	 * @param treeOriginalQuantityRequired the treeOriginalQuantityRequired to set
	 */
	public void setTreeOriginalQuantityRequired(Double treeOriginalQuantityRequired) {
		this.treeOriginalQuantityRequired = treeOriginalQuantityRequired;
	}

	/**
	 * @return the scrapFactor
	 */
//	public double getScrapFactor() {
//		return scrapFactor;
//	}

	/**
	 * @param scrapFactor the scrapFactor to set
	 */
//	public void setScrapFactor(double scrapFactor) {
//		this.scrapFactor = scrapFactor;
//	}

	/**
	 * @return the treeActualValue
	 */
	public Double getTreeActualValue() {
		return treeActualValue;
	}

	/**
	 * @param treeActualValue the treeActualValue to set
	 */
	public void setTreeActualValue(Double treeActualValue) {
		this.treeActualValue = treeActualValue;
	}

	/**
	 * @return the treeOriginalValue
	 */
	public Double getTreeOriginalValue() {
		return treeOriginalValue;
	}

	/**
	 * @param treeOriginalValue the treeOriginalValue to set
	 */
	public void setTreeOriginalValue(Double treeOriginalValue) {
		this.treeOriginalValue = treeOriginalValue;
	}
	
	
}