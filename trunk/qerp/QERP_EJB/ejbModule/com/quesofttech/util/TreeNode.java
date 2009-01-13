package com.quesofttech.util;

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
//import com.quesofttech.business.domain.general.BomTreeNodeData;
/*
import com.quesofttech.business.common.exception.ValueRequiredException;
import com.quesofttech.business.common.exception.GenericBusinessException;
import com.quesofttech.util.StringUtil;
*/
import java.util.List;
import java.util.TreeSet;
import java.util.ArrayList;


public class TreeNode<T> implements Serializable {
	
	
	public int level;
	
	public T data;
	public TreeNode<T> parent;
	
	public List<TreeNode<T>> children;
	
	public TreeNode(){
		super();
	}
	
	public TreeNode(T data){
		this();
		
	}


	
	
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * @return the parent
	 */
	public TreeNode<T> getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(TreeNode<T> parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	public List<TreeNode<T>> getChildren() {

		// 
		if (this.children == null) {
            return new ArrayList<TreeNode<T>>();
        }
        

		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<TreeNode<T>> children) {
		this.children = children;
	}


	
	public void addChild(TreeNode<T> node) {
		if(children==null) {
			children = new ArrayList<TreeNode<T>>();			
		}
		node.setParent(this);
		node.setLevel(this.level+1);
		children.add(node);
	}
	
	
	public void addChildren(List<TreeNode<T>> nodes) {
		for(TreeNode<T> n : nodes) {
			this.addChild(n);
		}
	}
	
    public void insertChildAt(int index, TreeNode<T> child) throws IndexOutOfBoundsException {
        if (index == getNumberOfChildren()) {
            // this is really an append
            addChild(child);
            return;
        } else {
            children.get(index); // will throw exception if index out of bound
            children.add(index, child);
        }
    }

	
	
	
	public void removeChild(TreeNode<T> node) {
		
		Object nodes[] = children.toArray();
		
		for(int i=0;i<nodes.length;++i){
			if(nodes[i].equals(node)){
				children.remove(node);
				return;
			}
				
		}
		
	}
	
    public void removeChildAt(int index) throws IndexOutOfBoundsException {
        children.remove(index);
    }

	
	
    /**
     * Returns the number of immediate children of this TreeNode<T>.
     * @return the number of immediate children.
     */
    public int getNumberOfChildren() {
        if (children == null) {
            return 0;
        }
        return children.size();
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TreeNode other = (TreeNode) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}

    
    
    
    
    
    
    
}