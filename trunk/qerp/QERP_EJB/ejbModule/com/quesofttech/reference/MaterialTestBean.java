package com.quesofttech.reference;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List; 
import java.util.Iterator;

import com.quesofttech.business.domain.*;
import com.quesofttech.business.domain.inventory.Material;
import com.quesofttech.business.domain.inventory.MaterialType;

@Stateless
public class MaterialTestBean implements MaterialTestBeanLocal,
		MaterialTestBeanRemote {
	@PersistenceContext
	EntityManager em;
	public static final String RemoteJNDIName =  MaterialTestBean.class.getSimpleName() + "Remote";
	public static final String LocalJNDIName =  MaterialTestBean.class.getSimpleName() + "Local";
	
	
	public void testMaterial() {
		// TODO Auto-generated method stub
		Material material1 = new Material(null, "RawMaterial1", "Raw Material");
		em.persist(material1);
		Material material2 = new Material(null, "RawMaterial2", "Raw Material");
		em.persist(material2);
		Material material3 = new Material(null, "FinishedGood1", "FinishedGood1");		
		em.persist(material3);

		System.out.println("List some materials");
		List<Material> someMaterials = (List<Material>)em.createQuery("SELECT m FROM Material m WHERE m.mat_Description=:desc1").setParameter("desc1", "Raw Material").getResultList();
		
		for (Iterator<Material> iter = someMaterials.iterator(); iter.hasNext();)
		{
			Material element = (Material) iter.next();
			System.out.println(element);
		}
		System.out.println("List all materials");
		List<Material> allMaterials = (List<Material>)em.createQuery("select m from Material m").getResultList();		
		
		for (Iterator<Material> iter = allMaterials.iterator(); iter.hasNext();)
		{
			Material element = (Material) iter.next();
			System.out.println(element);
		}
		System.out.println("Delete a material");
		em.remove(material2);
		System.out.println("List all materials");
		allMaterials = (List<Material>)em.createQuery("select m from Material m").getResultList();
		for (Iterator<Material> iter = allMaterials.iterator(); iter.hasNext();)
		{
			Material element = (Material) iter.next();
			System.out.println(element);
		}
	}

	
	public void testMaterialType() {
		// TODO Auto-generated method stub
		MaterialType materialType1 = new MaterialType(null,"R","Raw Material Type",false,true,false);
		em.persist(materialType1);

		MaterialType materialType2 = new MaterialType(null,"S","SubAssembly Type",true,false,false);
		em.persist(materialType2);

		MaterialType materialType3 = new MaterialType(null,"F","Finished Goods Type",true,false,true);
		em.persist(materialType3);

		System.out.println("List some material types");
		List<MaterialType> someMaterialTypes = em.createQuery(
				"select mt from MaterialType mt where mt.matt_isProduced=:flag1").setParameter("flag1",true).getResultList();
	
		for (Iterator<MaterialType> iter = someMaterialTypes.iterator(); iter.hasNext();)
		{
			MaterialType element = (MaterialType) iter.next();
			System.out.println(element);
		}
		System.out.println("List all materialTypes");
		List<MaterialType> allMaterialTypes = em.createQuery("select mt from MaterialType mt").getResultList();
		for (Iterator<MaterialType> iter = allMaterialTypes.iterator(); iter.hasNext();)
		{
			MaterialType element = (MaterialType) iter.next();
			System.out.println(element);
		}

		System.out.println("Delete a MaterialType");
		em.remove(materialType2);
		System.out.println("List all MaterialTypes");
		allMaterialTypes = em.createQuery("select mt from MaterialType mt").getResultList();
		for (Iterator<MaterialType> iter = allMaterialTypes.iterator(); iter.hasNext();)
		{
			MaterialType element = (MaterialType) iter.next();
			System.out.println(element);
		}
		
	}

	
	public void testRelation() {
		// TODO Auto-generated method stub

	}

	
}
