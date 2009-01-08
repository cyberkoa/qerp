
package com.quesofttech.web.components;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.annotations.Property;

import sun.security.action.GetLongAction;

import com.quesofttech.business.common.exception.BusinessException;
import com.quesofttech.business.common.exception.DoesNotExistException;
import com.quesofttech.business.domain.inventory.MaterialType;
import com.quesofttech.business.domain.inventory.iface.IMaterialTypeServiceRemote;
import com.quesofttech.business.domain.security.SecurityFinderService;
import com.quesofttech.business.domain.security.iface.ISecurityFinderServiceRemote;
import com.quesofttech.business.domain.security.Module;
import com.quesofttech.business.domain.security.Role;
import com.quesofttech.business.domain.security.User;
import com.quesofttech.business.domain.security.Program;
import com.quesofttech.web.base.SimpleBasePage;
import com.quesofttech.web.pages.BomDetailMaintenance;
import com.quesofttech.web.pages.Index;
import com.quesofttech.web.state.Visit;
@SupportsInformalParameters
@IncludeStylesheet("css/TreeGrid.css")
@IncludeJavaScriptLibrary( "js/TreeGrid.js")
public class cmp_layout extends SimpleBasePage 
{
	@InjectPage
	private Index _index;
	//private int int_Array = getSubjectSize();
	private String[] _TreeView  = new String[100];
	
	//@Property
	//@Persist
	private List<String> _subjects = new ArrayList<String>();
	/*= {"Inventory Management"
			//, "Bill Of Material"
			, "Production Planning"
			, "Procurement & Sourcing"
			, "Sales & Distribution"
			, "System Administration"
			, "Finance & Controlling"};*/
			
	//@Persist		
	private List<String> _modules  = new ArrayList<String>();
	/*= {"INV"
			//, "BOM"
			, "PP"
			, "PS"
			, "SD"
			, "SYS"
			, "FICO"};*/
	public cmp_layout()
	{
		
	}
	public List<String> getSubjects()
	{
		List<String> test = new ArrayList<String>();
		int i=0;
		try{
			_subjects.clear();
			_modules.clear();
			List<Module> Modules;
			
			Modules = getBusinessServicesLocator().getModuleServiceRemote().findModules();
			for(Module p : Modules)
			{			
				
				System.out.println("This is the output data: " + p.toString());
				_subjects.add(p.getDescription());
				_modules.add(p.getCode());
				//_modules[i] = p.getCode();
				i++;
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}		
		return _subjects;
	}
	
	private int getSubjectSize()
	{
		int i=0;
		try{
			
			List<Module> Modules;
			
			Modules = getBusinessServicesLocator().getModuleServiceRemote().findModules();
			for(Module p : Modules)
			{			
				
				System.out.println("This is the output data: " + p.toString());
				//_subjects[i] = p.getDescription();
				//_modules[i] = p.getCode();
				i++;
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}		
		//i++;
		return i;
	}
	
	
	
	@InjectPage
	private Index IndexPage;
	Object onActionFromactionSignOff()
	{
		getVisit().setLoggedIn(false);
		
		return IndexPage;
	}
	// _ProgramTypeNum is to let the tree view know the Particular
	// Program type should locate at which location base on id.
	
	// TODO: Need to Change to Dynamic Module and Module Description in the Menu

	private String[] _ProgramTypeNum = {"Maintenance", "Report", "Inquiry", "Processing", "Posting" };
	private int getProgramTypeNum(String _input)
	{		
		int _temp = 1; // Assumed input value unknown will recognized it as Maint
		try{
			
			for(int i=0;i<_subjects.size() ;i++)    // Changed From hardcoded to base on _subject 's length
			{
				if(_ProgramTypeNum[i].equals(_input.trim()))
				{
					_temp = i + 1;
					break;				
				}				
			}
		}
		catch (Exception e)
		{
			System.out.println("getProgramTypeNum Exception:" + e.getMessage());
		}
		return _temp;
	}
	
	
	@Persist
	private List<Role> _Roles;
	public List<Role> getRoles() {		
		return _Roles;
	}
	
	@Persist
	private List<User> _Users;
	public List<User> getUsers() {		
		return _Users;
	}
	
	@Persist
	private List<Program> _Programs;
	public List<Program> getPrograms() {		
		return _Programs;
	}
	private String copyright;
	private String getCopyright()
	{
		return "";
	}
	
	private User _user;
	private Role _Role;
	
	private long _id;
	private void GenerateTree() throws BusinessException{
		try{
			String _tempSubj="";
			int _PNum=1;
			int _Loop=0;
			int _TR_ID=0;
			//getArrayValues();
			// TODO: (hardcode potion) this will temporary to do the user id assignment. to avoid development test having the exception when accesing the page without login.
			_id =getVisit().getMyUserId(); // Hard coded to using KoaCG id located at No2.  
			_user = getISecurityFinderServiceRemote().findUser(_id);   
	    	_Programs = getISecurityFinderServiceRemote().findAuthorizedProgramsByUser(_user);
	    	for(int i_Subjects=0 ;i_Subjects< _subjects.size();i_Subjects++){ // To Loop number of Subjects
				_TreeView[i_Subjects] = "<div class='app'>" 
					+ "<div style='background: #eee; border: dashed 1px #000;'>"
					+ "<table width='100%'> " 
					+ "<tr>   <th width='33%'>Program List</th></tr>";
				for (int i_Type=0;i_Type<_ProgramTypeNum.length;i_Type++){ // To Loop Number of Program Type;
					_Loop = 0;
					for(Program p : _Programs)
			    	{    		
						_temp = p.getType();						
			    		if(p.getModule()!=null)
			    		{
				    		_tempSubj = p.getModule().getCode();
				    		if(_ProgramTypeNum[i_Type].equals(_temp) &&  _modules.get(i_Subjects).equals(_tempSubj))
				    		{
				    			// This is to set if Program have only list out the Maint, Posting , etc. to avoid looks messy
								if(_Loop==0)
								{
									_TR_ID++;
									_TreeView[i_Subjects] = _TreeView[i_Subjects] 
					                + "<tr id='" + _TR_ID + "' class='a'>" 
					                + "<td id='p" + _PNum++ + "'>"
					                + "<div id='p" + _PNum++ + "' class='tier1'>"
					                + "<a id='p"  + _PNum++ + "' href='#' onclick='toggleRows(this)' class='folder'>"
					                + "</a><a href='#' onclick='toggleRowsType(this)' >" + _ProgramTypeNum[i_Type]  + "</a></div></td>"
					                + "</tr> ";
								}
				    			_Loop++;
				    			_TreeView[i_Subjects] = _TreeView[i_Subjects]
								 + " <tr id='" + _TR_ID + "-" + _Loop + "' class='a'>"
								 + "<td><div class='tier2'>"
								 + "<div><a href='" + p.getCode()  + "'  target=" 
								 + (char)34 + "_self" +  (char)34 +  "   class='doc'>"
								 + "</a></div><div>" 
								 + "<a href='" + p.getCode()  + "'  target=" 
								 + (char)34 + "_self" +  (char)34 +  "  >"
								 + p.getDescription()  + "</a></div></div></td>  " +
								 "</tr>";		    			
				    		}		    
			    		// TODO: Need to change to Dynamic Server name instead of Localhost.
			    		}
			    	}				
				}
				_TreeView[i_Subjects] = _TreeView[i_Subjects]
				                                  + "</table>"
				                                  + "</div>"
				                                  + "</div>";
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("GenerateTree Exception:" + e.getMessage());
			returnLoginScreen();
			return;
		}
	}
	
	Object returnLoginScreen()
	{
		return _index;
	}
	//public List<String> getSubjects()
	//{		
		//System.out.println("this is the subject: and array is " + int_Array + ", subject: " + _subjects.toString());
	//	return _subjects;
	//}
	
    private String _detail = "A";
    private String _test;
    private String _temp;
    private String _temp1;
    public String[] getDetails() throws BusinessException
    {
    	try{
    		
	    	GenerateTree();
System.out.println("the tree size is :" + _subjects.size());
	    	String[] _Details_output = new String[_subjects.size()];
	    	
	    	for(int i_Loop=0;i_Loop<_subjects.size();i_Loop++)
	    	{
	    		System.out.println("here");
	    		if (! _TreeView[i_Loop].equals(""))
	    		{
	    			_Details_output[i_Loop] = _TreeView[i_Loop];   
	    			System.out.println("the tree contains is :" + _TreeView[i_Loop]);
	    		}
	    		else 
	    		{
	    			System.out.println("the tree contains is blank");
	    			_Details_output[i_Loop] = "<br><br><B><H1>N/A</H1></B>";
	    		}
	    		
	    	}
	    	return _Details_output;	    	
    	}
    	catch(Exception e)
    	{
    		String[] _error = {""};
    		returnLoginScreen(); 
    		return _error;    		
    	} 	
    }
    
    private ISecurityFinderServiceRemote getISecurityFinderServiceRemote()
    {
    	return getBusinessServicesLocator().getSecurityFinderSvcRemote();
    }
    
    public String getUsername()
    {
    	return getVisit().getMyLoginId();
    }

	
	
}