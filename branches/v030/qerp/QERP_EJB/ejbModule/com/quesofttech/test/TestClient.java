package com.quesofttech.test;

import java.util.Properties;
import javax.naming.Context;
//import javax.naming.spi.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.*;


import com.quesofttech.business.domain.sales.iface.*;
import com.quesofttech.business.domain.sales.*;
import com.quesofttech.business.domain.security.*;
import com.quesofttech.business.domain.inventory.*;
import com.quesofttech.business.domain.security.iface.*;
import com.quesofttech.business.domain.inventory.iface.*;
import com.quesofttech.business.domain.production.iface.*;
import com.quesofttech.business.domain.production.*;
import com.quesofttech.business.domain.finance.iface.*;
import com.quesofttech.business.domain.finance.*;
import com.quesofttech.business.domain.sales.dto.*;
import com.quesofttech.business.domain.general.*;
import com.quesofttech.business.domain.general.iface.*;


import com.quesofttech.business.domain.embeddable.RowInfo;
import com.quesoware.business.common.exception.*;
import com.quesoware.util.*;

//import com.quesofttech.session.MaterialTestBeanRemote;



public class TestClient {
	/**
	 * @param args
	 */

	static RowInfo rowInfo =null;
	
	public static void main(String[] args) {
		
		String test1 = "do null?";
		String test2 = "null";
		if(test1.contains(test2))
		{
			System.out.println("got");
		}
		else
			System.out.println("don have");
		
		/*
		rowInfo = new RowInfo();
		
		rowInfo.setCreateApp("TestClient");
		rowInfo.setModifyApp("TestClient");
		rowInfo.setCreateLogin("test");
		rowInfo.setModifyLogin("test");
		rowInfo.setSessionId("ABC");
		rowInfo.setRecordStatus("A");
		
	    java.util.Date today = new java.util.Date();

	    rowInfo.setModifyTimestamp(new java.sql.Timestamp(today.getTime()));
	    rowInfo.setCreateTimestamp(rowInfo.getModifyTimestamp());	
		*/
		
		
		/* get a initial context. By default the settings in the file
		 * jndi.properties are used.
		 * You can explicitly set up properties instead of using the file.
Properties properties = new Properties();
properties.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
properties.put("java.naming.factory.url.pkgs","=org.jboss.naming:org.jnp.interfaces");
	properties.put("java.naming.provider.url","localhost:1099");
 */
		//addMaterial();
		//addSalesOrder();
		//testFindRole();
		//addProductionOrder();
		//addSalesOrderMaterial();
	    //updateMaterial((long)1453);
		//testReflectionGetMethods(UOM.class);
	    //testReflectionToString();
		//testBOMExplosion();
		//testConvertSOM2ProdO();
		//testMaterialLazyFetch();
	}
	
	
	
	
	
	
	
	
	private static InitialContext GetContext()
	{
		
        Properties props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.openejb.client.RemoteInitialContextFactory");
        props.setProperty(Context.PROVIDER_URL,"ejbd://127.0.0.1:4201");
        props.setProperty(Context.SECURITY_PRINCIPAL, "system");
        props.setProperty(Context.SECURITY_CREDENTIALS, "manager");
        props.setProperty("openejb.authentication.realmName","geronimo-admin");
        
		try
		{
		 return new InitialContext(props);
		}
		catch(NamingException ne)
		{
			return null;
		}
	}	
	
	private static void testMaterialLazyFetch() {
		
		InitialContext context=null;
		IMaterialServiceRemote beanRemote = null;
		try
		{
			context = GetContext();
			 beanRemote = (IMaterialServiceRemote) context.lookup(MaterialService.class.getSimpleName()+"Remote");
			 if(beanRemote!=null) {
				try  {
					beanRemote.findMaterial((long)100).getBomByType("P");
				}
				catch(BusinessException e) {
				  System.out.println("[Business Exception] " + e.getMessage());
				  e.printStackTrace();
				}
			 }
		} catch (NamingException e)
		{
			e.printStackTrace();
/* I rethrow it as runtime exception as there is really no need to continue if an exception happens and I
* do not want to catch it everywhere.
*/ 
			throw new RuntimeException(e);
		}
		
		
	}
	

	private static void testConvertSOM2ProdO() {
		
		
		InitialContext context=null;
		ISalesOrderServiceRemote beanRemote = null;
		//BomTree bomTree = null;
		//ResourceBundle      bundle = null;

	     // bundle = ResourceBundle.getBundle("materialType", Locale.getDefault(), TestClient.class.getClassLoader());
	     // String jndiName = bundle.getString("jndi.session.ejb");
		try
		{
			context = GetContext();
			 beanRemote = (ISalesOrderServiceRemote) context.lookup(SalesOrderService.class.getSimpleName()+"Remote");
			 if(beanRemote!=null) {
				try  {
					
					beanRemote.convertOrderMaterialToProductionOrder(rowInfo,findSalesOrderMaterial((long)1));
				}
				catch(Exception e) {
				  System.out.println("[Business Exception] " + e.getMessage());
				  e.printStackTrace();
				}
			 }
		} catch (NamingException e)
		{
			e.printStackTrace();
/* I rethrow it as runtime exception as there is really no need to continue if an exception happens and I
* do not want to catch it everywhere.
*/ 
			throw new RuntimeException(e);
		}
	}
	
	private static void testBOMExplosion() {
		
		
		InitialContext context=null;
		IBomServiceRemote beanRemote = null;
		BomTree bomTree = null;
		//ResourceBundle      bundle = null;

	     // bundle = ResourceBundle.getBundle("materialType", Locale.getDefault(), TestClient.class.getClassLoader());
	     // String jndiName = bundle.getString("jndi.session.ejb");
		try
		{
			context = GetContext();
			 beanRemote = (IBomServiceRemote) context.lookup(BomService.class.getSimpleName()+"Remote");
			 if(beanRemote!=null) {
				try  {
					bomTree = beanRemote.buildBomTree(findMaterial((long)100), "P");
				}
				catch(BusinessException e) {
				  System.out.println("[Business Exception] " + e.getMessage());
				  e.printStackTrace();
				}
			 }
		} catch (NamingException e)
		{
			e.printStackTrace();
/* I rethrow it as runtime exception as there is really no need to continue if an exception happens and I
* do not want to catch it everywhere.
*/ 
			throw new RuntimeException(e);
		}
		
		if(bomTree==null) 
		{
			System.out.println("bomTree is null");
			return;
		}
		if(bomTree.toList().size() > 0)
		{
			System.out.println("size : " + bomTree.toList().size());
			
			for(TreeNode<BomTreeNodeData> tn : bomTree.toList())
			{
				System.out.println("[Level] : " + tn.level);
				if(tn.data==null) 
					 System.out.println("data is null");
				else
				  System.out.println("[TestBomExplosion] Material : " + tn.data.getBomDetail().getMaterial().getCodeDescription() + " Qty Required : " + tn.data.getTreeOriginalQuantityRequired());
			}
			
		}
	}
	
	
	
	
	
	
	
	
	private static void testReflectionGetMethods(Class cls)
	{
        try {
            //Class cls = SalesOrderSearchFields.class;
        
            Method methlist[] = cls.getMethods();
                  for (int i = 0; i < methlist.length;
                     i++) {  
                     Method m = methlist[i];
                     System.out.println("name  = " + m.getName());
                     System.out.println("decl class = " +
                                    m.getDeclaringClass());
                     Class pvec[] = m.getParameterTypes();
                     for (int j = 0; j < pvec.length; j++)
                        System.out.println("param #" + j + " " + pvec[j]);
                     Class evec[] = m.getExceptionTypes();
                     for (int j = 0; j < evec.length; j++)
                        System.out.println("exc #" + j 
                          + " " + evec[j]);
                     System.out.println("return type = " +
                                        m.getReturnType());
                     System.out.println("-----");
                  }

          }
          catch (Throwable e) {
             System.err.println(e);
          }
	}
	
	private static void testReflection(Class cls)
	{
        try {
            //Class cls = SalesOrderSearchFields.class;
        
            Method methlist[] = cls.getDeclaredMethods();
                  for (int i = 0; i < methlist.length;
                     i++) {  
                     Method m = methlist[i];
                     System.out.println("name  = " + m.getName());
                     System.out.println("decl class = " +
                                    m.getDeclaringClass());
                     Class pvec[] = m.getParameterTypes();
                     for (int j = 0; j < pvec.length; j++)
                        System.out.println("param #" + j + " " + pvec[j]);
                     Class evec[] = m.getExceptionTypes();
                     for (int j = 0; j < evec.length; j++)
                        System.out.println("exc #" + j 
                          + " " + evec[j]);
                     System.out.println("return type = " +
                                        m.getReturnType());
                     System.out.println("-----");
                  }

          }
          catch (Throwable e) {
             System.err.println(e);
          }

	}
	
	private static void addMaterial() {
		Material material = new Material();
		material.setCode("MATERIAL_B");
		material.setDescription("Material A");
		//material.setMaterialType();
		
		InitialContext context=null;
		IMaterialServiceRemote beanRemote = null;
		//ResourceBundle      bundle = null;

	     // bundle = ResourceBundle.getBundle("materialType", Locale.getDefault(), TestClient.class.getClassLoader());
	     // String jndiName = bundle.getString("jndi.session.ejb");
		try
		{
			context = GetContext();
			 beanRemote = (IMaterialServiceRemote) context.lookup(MaterialService.class.getSimpleName()+"Remote");
			 if(beanRemote!=null) {
				try  {
					beanRemote.addMaterial(material);
				}
				catch(BusinessException e) {
				  System.out.println("[Business Exception] " + e.getMessage());
				  e.printStackTrace();
				}
			 }
		} catch (NamingException e)
		{
			e.printStackTrace();
/* I rethrow it as runtime exception as there is really no need to continue if an exception happens and I
* do not want to catch it everywhere.
*/ 
			throw new RuntimeException(e);
		}
		
		
	}
	
	private static void addSalesOrderMaterial()
	{
		
		InitialContext context=null;
		ISalesOrderServiceRemote beanRemote = null;
		//ResourceBundle      bundle = null;
		System.out.println("Yes in");
	     // bundle = ResourceBundle.getBundle("materialType", Locale.getDefault(), TestClient.class.getClassLoader());
	     // String jndiName = bundle.getString("jndi.session.ejb");
		try
		{
			 context = GetContext();
			 beanRemote = (ISalesOrderServiceRemote) context.lookup(SalesOrderService.class.getSimpleName()+"Remote");
			 System.out.println((beanRemote==null)? "is null":"not null");
			 
			 if(beanRemote!=null) {
				 try  {
					 SalesOrder s = beanRemote.findSalesOrder((long)1);
					 if(s!=null)	 {
						 
							SalesOrderMaterial salesOrderMaterial = new SalesOrderMaterial();
							
							//salesOrderLine1.setSalesOrder(salesOrder);
							salesOrderMaterial.setLine((long)5);
							salesOrderMaterial.setQuantityOrder((double)100);
							salesOrderMaterial.setQuantityShipped((double)0);
							
							salesOrderMaterial.setCustomerPOLine((long)5);
							salesOrderMaterial.setMaterial(null);
							
							s.addSalesOrderMaterial(salesOrderMaterial);
							beanRemote.updateSalesOrder(s);
							
						 }
				 } 				
				catch(BusinessException e) {
					  System.out.println("[Business Exception] " + e.getMessage());
					  e.printStackTrace();
					}
				 }
			} catch (NamingException e)
			{
				e.printStackTrace();
	/* I rethrow it as runtime exception as there is really no need to continue if an exception happens and I
	* do not want to catch it everywhere.
	*/ 
				throw new RuntimeException(e);
			}
						 
	 
	}
	
	
	private static void addSalesOrder()
	{
		SalesOrder salesOrder = new SalesOrder();
		
		//salesOrder.setDocNo("123");
		salesOrder.setDocType("S");
		//salesOrder.setCustomer(new Customer());
		salesOrder.setCustomerPO("ABC123");
		
		salesOrder.setCustomer(null);
		
		
		//List<SalesOrderLine> salesOrderLines = new ArrayList<SalesOrderLine>();
		//salesOrder.setSalesOrderLines(salesOrderLines);
		
		SalesOrderMaterial salesOrderLine1 = new SalesOrderMaterial();
		
		//salesOrderLine1.setSalesOrder(salesOrder);
		salesOrderLine1.setLine((long)1);
		salesOrderLine1.setQuantityOrder((double)50);
		salesOrderLine1.setQuantityShipped((double)0);
		
		salesOrderLine1.setCustomerPOLine((long)2);
		salesOrderLine1.setMaterial(null);
		
		
		/*
		SalesOrderLine salesOrderLine2 = new SalesOrderLine();
		
		//salesOrderLine2.setSalesOrder(salesOrder);
		salesOrderLine2.setLine((long)2);
		salesOrderLine2.setQuantityOrder((long)5);
		salesOrderLine2.setQuantityShipped((long)0);
		
		salesOrderLine2.setCustomerPOLine((long)4);
		
		Material material = null;
		try{
		   material = findMaterial((long)1355);
		}
		catch(DoesNotExistException e)
		{
		  System.out.println("Cannot find Material");
		}
		
		salesOrderLine2.setMaterial(material);
		*/
		salesOrder.addSalesOrderMaterial(salesOrderLine1);
		
		//salesOrder.addSalesOrderLine(salesOrderLine2);
		
		InitialContext context=null;
		ISalesOrderServiceRemote beanRemote = null;
		//ResourceBundle      bundle = null;

	     // bundle = ResourceBundle.getBundle("materialType", Locale.getDefault(), TestClient.class.getClassLoader());
	     // String jndiName = bundle.getString("jndi.session.ejb");
		try
		{
			context = GetContext();
			 beanRemote = (ISalesOrderServiceRemote) context.lookup(SalesOrderService.class.getSimpleName()+"Remote");
			 if(beanRemote!=null) {
				try  {
					beanRemote.addSalesOrder(salesOrder);
				}
				catch(BusinessException e) {
				  System.out.println("[Business Exception] " + e.getMessage());
				  e.printStackTrace();
				}
			 }
		} catch (NamingException e)
		{
			e.printStackTrace();
/* I rethrow it as runtime exception as there is really no need to continue if an exception happens and I
* do not want to catch it everywhere.
*/ 
			throw new RuntimeException(e);
		}
	}
	
	
	private static void testFindRole()
	{
		InitialContext context=null;
		ISecurityFinderServiceRemote beanRemote = null;
		//ResourceBundle      bundle = null;
		System.out.println("Yes in");
	     // bundle = ResourceBundle.getBundle("materialType", Locale.getDefault(), TestClient.class.getClassLoader());
	     // String jndiName = bundle.getString("jndi.session.ejb");
		try
		{
			 context = GetContext();
			 beanRemote = (ISecurityFinderServiceRemote) context.lookup(SecurityFinderService.class.getSimpleName()+"Remote");
			 System.out.println((beanRemote==null)? "is null":"not null");
			 
			 if(beanRemote!=null) {
				 try  {
					 User user = beanRemote.findUserByLogin("admin");
					 if(user!=null)	 {
						 
					  //List<Program> programs = beanRemote.findAuthorizedProgramsOfUserByUser(user);
						 //List<Program> programs = beanRemote.findAuthorizedProgramsOfRolesByUser(user);
						 List<Program> programs = beanRemote.findAuthorizedProgramsByUser(user);
						 if(programs != null)	 {
							 System.out.println("Count : " + programs.size() + "\n");
						  for(Program p : programs)
						     System.out.println(p.toString()); 
						 }
						 else  {
						  System.out.println("roles is null lah");
						 }
					 }
					 else	 {
					  System.out.println("siam lah");
					  return;
					 }
				}
				catch(DoesNotExistException e) {
					System.out.println("[Business Exception] " + e.getMessage());
				}
			 }
		} catch (NamingException e)
		{
			e.printStackTrace();
/* I rethrow it as runtime exception as there is really no need to continue if an exception happens and I
* do not want to catch it everywhere.
*/ 
			throw new RuntimeException(e);
		}
	}
	
	
	
	private static void addProductionOrder()
	{
		ProductionOrder productionOrder = new ProductionOrder();
		
		//productionOrder.setDocNo("789");
		productionOrder.setMaterial(null);
		
		productionOrder.setQuantityOrder((double)30);
		productionOrder.setQuantityReported((double)0);
		
		
		//productionOrder.setCustomer(null);
		
		
		ProductionOrderOperation productionOrderOperation = new ProductionOrderOperation();
		
		//productionOrderOperation.setOperation(10);
		productionOrderOperation.setQuantityOrder((Double)30.0);
		productionOrderOperation.setQuantityReported((Double)0.0);
		
		productionOrder.addProductionOrderOperation(productionOrderOperation);
		

		

		//salesOrderLine1.setSalesOrder(salesOrder);		
		ProductionOrderMaterial productionOrderMaterial = new ProductionOrderMaterial();
		
		Material material = null;
		try{
		   material = findMaterial((long)1355);
		}
		catch(DoesNotExistException e)
		{
		  System.out.println("Cannot find Material");
		}
		
		productionOrderMaterial.setMaterial(material);
		productionOrderMaterial.setQuantityRequired((double)60);
		productionOrderMaterial.setQuantityConsumed((double)0);
		
		productionOrder.addProductionOrderMaterial(productionOrderMaterial);

		
		InitialContext context=null;
		IProductionOrderServiceRemote beanRemote = null;
		//ResourceBundle      bundle = null;

	     // bundle = ResourceBundle.getBundle("materialType", Locale.getDefault(), TestClient.class.getClassLoader());
	     // String jndiName = bundle.getString("jndi.session.ejb");
		try
		{
			context = GetContext();
			 beanRemote = (IProductionOrderServiceRemote) context.lookup(ProductionOrderService.class.getSimpleName()+"Remote");
			 if(beanRemote!=null) {
				try  {
					beanRemote.addProductionOrder(productionOrder);
				}
				catch(BusinessException e) {
				  System.out.println("[Business Exception] " + e.getMessage());
				  e.printStackTrace();
				}
			 }
		} catch (NamingException e)
		{
			e.printStackTrace();
/* I rethrow it as runtime exception as there is really no need to continue if an exception happens and I
* do not want to catch it everywhere.
*/ 
			throw new RuntimeException(e);
		}
	}
	
	
	private static Material findMaterial(Long id) throws DoesNotExistException  {
		//material.setMaterialType();
		
		InitialContext context=null;
		IMaterialServiceRemote beanRemote = null;
		//ResourceBundle      bundle = null;

	     // bundle = ResourceBundle.getBundle("materialType", Locale.getDefault(), TestClient.class.getClassLoader());
	     // String jndiName = bundle.getString("jndi.session.ejb");
		try
		{
			context = GetContext();
			 beanRemote = (IMaterialServiceRemote) context.lookup(MaterialService.class.getSimpleName()+"Remote");
			 if(beanRemote!=null) {
				try  {
					return beanRemote.findMaterial(id);
				}
				catch(BusinessException e) {
				  System.out.println("[Business Exception] " + e.getMessage());
				  e.printStackTrace();
				  return null;
				}
			 }
		} catch (NamingException e)
		{
			e.printStackTrace();
/* I rethrow it as runtime exception as there is really no need to continue if an exception happens and I
* do not want to catch it everywhere.
*/ 
			throw new RuntimeException(e);
			
		}
		return null;
		
	}
	
	private static void updateMaterial(Long id) {
		
		//material.setMaterialType();
		
		InitialContext context=null;
		IMaterialServiceRemote beanRemote = null;
		//ResourceBundle      bundle = null;

	     // bundle = ResourceBundle.getBundle("materialType", Locale.getDefault(), TestClient.class.getClassLoader());
	     // String jndiName = bundle.getString("jndi.session.ejb");
		try
		{
			context = GetContext();
			 beanRemote = (IMaterialServiceRemote) context.lookup(MaterialService.class.getSimpleName()+"Remote");
			 if(beanRemote!=null) {
				try  {
					Material material = beanRemote.findMaterial(id);
					
					material.setDescription("New description");
					
					beanRemote.updateMaterial(material);
					
					
				}
				catch(BusinessException e) {
				  System.out.println("[Business Exception] " + e.getMessage());
				  e.printStackTrace();
				  return ;
				}
			 }
		} catch (NamingException e)
		{
			e.printStackTrace();
/* I rethrow it as runtime exception as there is really no need to continue if an exception happens and I
* do not want to catch it everywhere.
*/ 
			throw new RuntimeException(e);
			
		}
		return;


		
	}
	
	private static SalesOrderMaterial findSalesOrderMaterial(Long id) throws DoesNotExistException  {
		//material.setMaterialType();
		
		InitialContext context=null;
		ISalesOrderServiceRemote beanRemote = null;
		//ResourceBundle      bundle = null;

	     // bundle = ResourceBundle.getBundle("materialType", Locale.getDefault(), TestClient.class.getClassLoader());
	     // String jndiName = bundle.getString("jndi.session.ejb");
		try
		{
			context = GetContext();
			 beanRemote = (ISalesOrderServiceRemote) context.lookup(SalesOrderService.class.getSimpleName()+"Remote");
			 if(beanRemote!=null) {
				try  {
					return beanRemote.findSalesOrderMaterial(id);
				}
				catch(BusinessException e) {
				  System.out.println("[Business Exception] " + e.getMessage());
				  e.printStackTrace();
				  return null;
				}
			 }
		} catch (NamingException e)
		{
			e.printStackTrace();
/* I rethrow it as runtime exception as there is really no need to continue if an exception happens and I
* do not want to catch it everywhere.
*/ 
			throw new RuntimeException(e);
			
		}
		return null;
		
	}
	
	
}
