package com.quesofttech.web.pages;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import com.quesofttech.web.base.SimpleBasePage;
import com.quesofttech.web.base.SecureBasePage;
import com.quesofttech.web.state.Visit;
import com.quesoware.business.common.exception.BusinessException;
import com.quesoware.business.domain.inventory.MaterialGroup;
import com.quesoware.business.domain.inventory.iface.IMaterialGroupServiceRemote;
import com.quesoware.business.domain.security.iface.ISecurityFinderServiceRemote;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Retain;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Submit;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Checkbox;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;
import org.apache.tapestry5.annotations.ApplicationState;
public class MaterialGroupMaintenance extends SimpleBasePage {
// Default defination.
    private String _strMode = "";
    private MaterialGroup MaterialGroupDetail;
    private MaterialGroup _MaterialGroup;
    @Persist
    private List<MaterialGroup> _MaterialGroups;
    @Inject
    private Logger _logger;
    @Inject
    private Block blockFormView;
    @Persist
    private long lng_CurrentID;
// End of  Default defination.
    private String viewDisplayText="", viewEditText="";
    public String getViewDisplayText()
    {
     return viewDisplayText;
    }

    public String getviewEditText()
    {
         return viewEditText;
    }

    @Component(id = "MaterialGroupForm")
    private Form _form;
    @Persist
    private int int_SelectedRow;

    @ApplicationState
    private String myState;

    @Component(id = "GRID")
    private Grid _Grid;

    //===============================
    // Text Component for id
    @Component(id = "id")
    private TextField _id;
    private Long id;
    public Long getid()
    {
       return id;
    }

    public void setid(Long id)
    {
       this.id = id;
    }
    //===============================

    //===============================
    // Text Component for Description
    @Component(id = "Description")
    private TextField _Description;
    private String Description;
    public String getDescription()
    {
       return Description;
    }

    public void setDescription(String Description)
    {
       this.Description = Description;
    }
    //===============================

    //===============================
    // Text Component for Group
    @Component(id = "Group")
    private TextField _Group;
    private String Group;
    public String getGroup()
    {
       return Group;
    }

    public void setGroup(String Group)
    {
       this.Group = Group;
    }
    //===============================
    void RefreshRecords()
    {
       try
       {
           _MaterialGroups = getMaterialGroupService().findMaterialGroups();
       }
       catch(BusinessException be)
       {

       }
       if(_MaterialGroups!=null && !_MaterialGroups.isEmpty())
       {
           if(int_SelectedRow==0)
           {
               MaterialGroupDetail = _MaterialGroups.get(_MaterialGroups.size() - 1);
           }
           else
           {
               MaterialGroupDetail = _MaterialGroups.get(int_SelectedRow - 1);
           }
           MaterialGroupDetail = _MaterialGroups.get(_MaterialGroups.size() - 1);
           myState="U";
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(MaterialGroupDetail);
       }
             else
            {
                      myState="A"; // If no List then should be in A mode instead of Update mode.
            }
    }
private void refreshDisplay()
    {
        if(myState.equals("U"))
        {
            viewDisplayText="Block";
            viewEditText="none";
        }
        else
        {
                    viewDisplayText="none";
            viewEditText="Block";
        }
    }
    private int getRcdLocation( Long id)  throws BusinessException
    {
      int int_return ,int_i;
      int_i = 0;
      int_return = 0;
      for(MaterialGroup p : _MaterialGroups)
       {
          int_i++;
          if((long)p.getId().intValue()==id)
           {
                      int_return = int_i;
           }
       }
       return int_return;
    }

    public Block getBlock() {
       return blockFormView;
    }


    Object onSuccess()
    {
       _form.clearErrors();
       RefreshRecords();
       return blockFormView;
    }


    Object onFailure()
    {
       _form.clearErrors();
       _form.recordError(getMessages().get("Record_Save_Error"));
       return blockFormView;
    }


    void setupRender() {
       RefreshRecords();
    }


    void onValidateForm() {
       if ("U"== myState)
       {
           _UpdateRecord();
       }
       else
       if ("A" == myState)
       {
           _AddRecord();
       }
    }

    void assignToDatabase(MaterialGroup materialGroup){
       materialGroup.setId(id);
       materialGroup.setDescription(Description);
       materialGroup.setGroup(Group);
       materialGroup.setRecordStatus("A");
    }
    void assignToLocalVariable(MaterialGroup materialGroup)
    {
       this.id = materialGroup.getId();
       this.Description = materialGroup.getDescription();
       this.Group = materialGroup.getGroup();
    }
    void _AddRecord()
    {
       MaterialGroup materialGroup = new MaterialGroup();
       try {
               materialGroup.setModifyLogin(getVisit().getMyLoginId());
               materialGroup.setCreateLogin(getVisit().getMyLoginId());
               materialGroup.setCreateApp(this.getClass().getSimpleName());
               materialGroup.setModifyApp(this.getClass().getSimpleName());
           assignToDatabase(materialGroup);
           getMaterialGroupService().addMaterialGroup(materialGroup);
       }
       catch (Exception e) {
           _form.recordError(e.getMessage());
       }
    }

    void _UpdateRecord(){
       MaterialGroup materialGroup = new MaterialGroup();
       try
       {
           materialGroup = getMaterialGroupService().findMaterialGroup(id);
       }
       catch(BusinessException be)
       {

       }
       if(materialGroup !=null)
       {
           try {
               materialGroup.setModifyLogin(getVisit().getMyLoginId());
               assignToDatabase(materialGroup);
               getMaterialGroupService().updateMaterialGroup(materialGroup);
           }
           catch (BusinessException e) {
               _form.recordError(e.getMessage());
           }
       catch (Exception e) {
               _logger.info("MaterialGroup_update_problem");
               e.printStackTrace();
               _form.recordError(getMessages().get("MaterialGroup_update_problem"));
           }
       }
    }


    void _DeleteRecord(Long id) {
       MaterialGroup materialGroup = new MaterialGroup();
       try
       {
           materialGroup = getMaterialGroupService().findMaterialGroup(id);
       }
       catch(BusinessException be)
       {

       }
       if(materialGroup!=null)
       {
           try {
               materialGroup.setModifyLogin(getVisit().getMyLoginId());
               getMaterialGroupService().logicalDeleteMaterialGroup(materialGroup);
               if(int_SelectedRow!=0)
               {
                   int_SelectedRow--;
               }
               RefreshRecords();
           }
               catch (BusinessException e) {
               _form.recordError(e.getMessage());
       }
           catch (Exception e) {
               _logger.info("MaterialGroup_Delete_problem");
               e.printStackTrace();
               _form.recordError(getMessages().get("MaterialGroup_Delete_problem"));
           }
       }
    }

    void onActionFromtoolbarDel(Long id)
    {
            if (id!=null){
       _form.clearErrors();
       myState = "D";
       _strMode = "D";
       _DeleteRecord(id);
            }
    }

    Object onActionFromToolbarAdd ()
    {
       _form.clearErrors();
       myState = "A";
       _strMode = "A";
       return blockFormView;
    }

    Object onActionFromSelect(long id)
    {
       _form.clearErrors();
       myState = "U";
       _strMode = "U";
       lng_CurrentID = id;
       try
       {
           MaterialGroupDetail = getMaterialGroupService().findMaterialGroup(id);
           int_SelectedRow = getRcdLocation(id);
       }
       catch(BusinessException be)
       {

       }

       if(MaterialGroupDetail!=null){
           viewDisplayText="Block";
           viewEditText="none";
           assignToLocalVariable(MaterialGroupDetail);
           return blockFormView;
       }
       return null;
    }

    private IMaterialGroupServiceRemote getMaterialGroupService() {
       return getBusinessServicesLocator().getMaterialGroupServiceRemote();
    }


    public List<MaterialGroup> getMaterialGroups() {
       return _MaterialGroups;
    }


    public MaterialGroup getMaterialGroup() throws BusinessException{
       return _MaterialGroup;
    }


     public void setMaterialGroup(MaterialGroup tb) {
       _MaterialGroup = tb;
    }

}
