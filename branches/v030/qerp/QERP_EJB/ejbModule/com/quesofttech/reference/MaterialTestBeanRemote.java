package com.quesofttech.reference;

import javax.ejb.Remote;

@Remote
public interface MaterialTestBeanRemote {
	public void testMaterial();
	public void testMaterialType();
	public void testRelation();	
}
