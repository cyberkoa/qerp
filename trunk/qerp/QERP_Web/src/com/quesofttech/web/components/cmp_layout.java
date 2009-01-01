
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
	
	
	//@InjectPage
	//private Index IndexPage;
	void onActionFromactionSignOff()
	{
		getVisit().setLoggedIn(false);
		
		//return IndexPage;
	}
	// _ProgramTypeNum is to let the tree view know the Particular
	// Program type should locate at which location base on id.
	private String[] _TreeView  = new String[100];
	//List<String> _TreeString = new ArrayList<String>();
	//List<String> _SubjectString = new ArrayList<String>();
	//List<String> _ModuleString = new ArrayList<String>();
	//List<Module> _Modules;
	/*
	private void getArrayValues()
	{
		//// System.out.println("here liao");
		_Modules = getISecurityFinderServiceRemote().findModules();
		//_subjects = new String[_Modules.];
		for (Module m : _Modules)
		{
			_ModuleString.add(m.getCode());	
			_SubjectString.add(m.getDescription());
		}
		//// System.out.println("_ModuleString size: " +  _ModuleString.size());
		_subjects = new String[_ModuleString.size()];
		for(int i_loop=0;i_loop<_ModuleString.size();i_loop++)
		{
			_subjects[i_loop] = _SubjectString.get(i_loop);
		}
	}*/
	//@Property
    //private String[] _subjects ;
	
	@Property
	private String[] _subjects = {"Inventory Management"
			//, "Bill Of Material"
			, "Production Planning"
			, "Procurement & Sourcing"
			, "Sales & Distribution"
			, "System Administration"
			, "Finance & Controlling"};
			
			
	private String[] _modules = {"INV"
			//, "BOM"
			, "PP"
			, "PS"
			, "SD"
			, "SYS"
			, "FICO"};
	// TODO: Need to Change to Dynamic Module and Module Description in the Menu

	private String[] _ProgramTypeNum = {"Maintenance", "Report", "Inquiry", "Processing", "Posting" };
	private int getProgramTypeNum(String _input)
	{		
		int _temp = 1; // Assumed input value unknown will recognized it as Maint
		//// System.out.println("input are " + _input);
		for(int i=0;i<_subjects.length ;i++)    // Changed From hardcoded to base on _subject 's length
		{
			//// System.out.println("_ProgramTypeNum locatin:" + i + " and the value is :" + _ProgramTypeNum[i]);
			if(_ProgramTypeNum[i].equals(_input.trim()))
			{
				_temp = i + 1;
				break;				
			}				
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
			//_id = 2;
			////// System.out.println("printing applicatoin stage:" + getVisit().getMyUserId() );
	    	_user = getISecurityFinderServiceRemote().findUser(_id);   
	    	_Programs = getISecurityFinderServiceRemote().findAuthorizedProgramsByUser(_user);
	    	// System.out.println("ID: " + _id);
	    	// System.out.println("USER: " + _user.toString());
			// System.out.println("PROGRAM:" + _Programs.toString());
			for(int i_Subjects=0 ;i_Subjects< _subjects.length;i_Subjects++){ // To Loop number of Subjects
				_TreeView[i_Subjects] = "<div class='app'>" 
					+ "<div style='background: #eee; border: dashed 1px #000;'>"
					+ "<table width='100%'> " 
					+ "<tr>   <th width='33%'>Program List</th></tr>";
				for (int i_Type=0;i_Type<_ProgramTypeNum.length;i_Type++){ // To Loop Number of Program Type;
					/*_TR_ID++;
					_TreeView[i_Subjects] = _TreeView[i_Subjects] 
	                + "<tr id='" + _TR_ID + "' class='a'>" 
	                + "<td id='p" + _PNum++ + "'>"
	                + "<div id='p" + _PNum++ + "' class='tier1'>"
	                + "<a id='p"  + _PNum++ + "' href='#' onclick='toggleRows(this)' class='folder'>"
	                + "</a>" + _ProgramTypeNum[i_Type]  + "</div></td>"
	                + "</tr> ";*/
					_Loop = 0;
					for(Program p : _Programs)
			    	{    		
						_temp = p.getType();
						////// System.out.println(_Programs.toString());
						// System.out.println("Type: " + p.getType());
						// System.out.println("Module: " + p.getModule());
			    		if(p.getModule()!=null)
			    		{
				    		_tempSubj = p.getModule().getCode();
				    		//_modules[i_Subjects].equals
				    		// System.out.println(_ProgramTypeNum[i_Type] + " = " + _temp);
				    		//// System.out.println(_modules[i_Subjects] + " = " + _tempSubj);
				    		if(_ProgramTypeNum[i_Type].equals(_temp) && _modules[i_Subjects].equals(_tempSubj))
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
					                + "</a><a href='#' onclick='toggleRows(this)'>" + _ProgramTypeNum[i_Type]  + "</a></div></td>"
					                + "</tr> ";
								}
				    			
				    			
				    			
				    			
				    			_Loop++;
				    			_TreeView[i_Subjects] = _TreeView[i_Subjects]
								 + " <tr id='" + _TR_ID + "-" + _Loop + "' class='a'>"
								 + "<td><div class='tier2'>"
								 + "<a href='" + p.getCode()  + "'  target=" 
								 + (char)34 + "_self" +  (char)34 +  "   class='doc'>"
								 + "</a>" 
								 + "<a href='" + p.getCode()  + "'  target=" 
								 + (char)34 + "_self" +  (char)34 +  "  >"
								 + p.getDescription()  + "</a></div></td>  " +
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
			returnLoginScreen();
			return;
		}
	}
	
	Object returnLoginScreen()
	{
		return _index;
	}
	/*
    private String _detail1 = "<div class='app'>" + 
	"<div style='background: #eee; border: dashed 1px #000;'>" + 
		"<table width='100%'> " +
		"<tr>   <th width='33%'>Location</th></tr>" + 
		"<tr id='1' class='a'>" + 
		"<td id='p1'><div id='p2' class='tier1'><a id='p3' href='#' onclick='toggleRows(this)' class='folder'></a>By Activity</div></td>" + 
		"</tr> " + 
		" <tr id='1-1' class='a'>" + 
		"<td id='p4'><div id='p5' class='tier2'><a id='p6' href='#' onclick='toggleRows(this)' class='folder'></a>Project Planning</div></td> " +
		"</tr> " +
		"<tr id='1-1-1' class='a'> " +
		"<td><div class='tier3'><a href='http://localhost:8080/QERP/index'  target=" +  
		(char)34 + "maintest" +  (char)34 +  "   class='doc'></a>Overview</div></td>  " +
		"</tr>" +
		"<tr id='1-1-2' class='a'> " +
		"<td><div class='tier3'><a href='http://localhost:8080/QERP/MaterialTypeMaintenance'  target=" +  
		(char)34 + "maintest" +  (char)34 +  "  class='dynamic'>A</a>Overview</div></td>  " +
		"</tr>" +
		"</table>" +
	"</div>" +
	"</div>"; */
    	
	/*
	List<Role> roles = findUserRoles(user.getLogin());
	
	List<Long> roleIds = new ArrayList<Long>(); 
	
	for(Role r : roles)
	{
		roleIds.add(r.getId());
	}
    
    */
    
    //Maint, Report, Inquery, Processing, 
    //Posting 
   
    private String _detail = "A";
    private String _test;
    private String _temp;
    private String _temp1;
    public String[] getDetails() throws BusinessException
    {
    	try{
	    	GenerateTree();
	    	String[] _Details_output = new String[_subjects.length];
	    	
	    	for(int i_Loop=0;i_Loop<_subjects.length;i_Loop++)
	    	{
	    		if (! _TreeView[i_Loop].equals(""))
	    		{
	    			_Details_output[i_Loop] = _TreeView[i_Loop];    			
	    		}
	    		else 
	    		{
	    			_Details_output[i_Loop] = "<br><br><B><H1>N/A</H1></B>";
	    		}
	    		
	    	}
	    	return _Details_output;
	    	
	    	/*
	    	_id = 2;
	    	_temp1 = "Maint";
	    	_user = getISecurityFinderServiceRemote().findUser(_id);
	    	//// System.out.println("_user size:" + _user.getLogin());
	    	_Programs = getISecurityFinderServiceRemote().findAuthorizedProgramsByUser(_user);
	    	
	    	if (_Programs != null)
	    	{
	    		//// System.out.println("not null");
	    	}
	    	else
	    	{
	    		//// System.out.println("cb...null");
	    	}    	
	    	for(Program p : _Programs)
	    	{    		
	    		_temp = p.getType();
	    		//// System.out.println("My output is located at : " + _temp + ", " + getProgramTypeNum(_temp));    		
	    	} */
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