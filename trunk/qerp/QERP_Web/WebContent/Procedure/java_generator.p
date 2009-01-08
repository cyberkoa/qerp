DEFINE INPUT  PARAMETER str_formid  AS CHARACTER   NO-UNDO.
DEFINE INPUT  PARAMETER str_module AS CHARACTER   NO-UNDO.
DEFINE INPUT  PARAMETER str_classname AS CHARACTER   NO-UNDO.
DEFINE INPUT  PARAMETER pc_value AS CHARACTER   NO-UNDO.

/* DEFINE VARIABLE str_formid AS CHARACTER   NO-UNDO. */
/* DEFINE VARIABLE str_module AS CHARACTER   NO-UNDO. */
DEFINE VARIABLE str_table AS CHARACTER   NO-UNDO.
/* DEFINE VARIABLE str_classname AS CHARACTER   NO-UNDO. */



OUTPUT TO VALUE(str_formid + "Maintenance.java").


DEFINE TEMP-TABLE tttemp
    FIELD bfno AS INTEGER
    FIELD bffieldname AS CHARACTER
    FIELD bfdatatype AS CHARACTER.

EMPTY TEMP-TABLE tttemp.


FUNCTION GetFormContent RETURN CHARACTER:

    DEFINE VARIABLE INT_no AS INTEGER     NO-UNDO.
    DEFINE VARIABLE i_Count AS INTEGER     NO-UNDO.
    DEFINE VARIABLE i AS INTEGER     NO-UNDO.
    DEFINE VARIABLE c_field AS CHARACTER   NO-UNDO.
    DEFINE VARIABLE i_size AS INTEGER     NO-UNDO.
    DEFINE VARIABLE l_null AS LOGICAL     NO-UNDO.
    DEFINE VARIABLE c_comment AS CHARACTER   NO-UNDO.
    DEFINE VARIABLE c_linevalue AS CHARACTER   NO-UNDO.
    DEFINE VARIABLE c_temp AS CHARACTER   NO-UNDO.
    DEFINE VARIABLE c_validator AS CHARACTER   NO-UNDO.
    DEFINE VARIABLE l_isDateField AS LOGICAL     NO-UNDO.
    DEFINE VARIABLE c_datatype AS CHARACTER   NO-UNDO.
  
   
    pc_value = REPLACE(pc_value,"A=Active,D=Logically","A=Active/D=Logically").
    pc_value = REPLACE(pc_value,"Deleted, L=Locked","Deleted/L=Locked").
    i_count = NUM-ENTRIES(pc_value).
    /*     pc_value = REPLACE(pc_value,"default NULL",CHR(5)). */
    /*     pc_value = REPLACE(pc_value,"NOT NULL",CHR(4)).     */
    
    MESSAGE '    private String viewDisplayText="", viewEditText="";'.
    MESSAGE '    public String getViewDisplayText()'.
    MESSAGE '    ' + CHR(123).
    MESSAGE '     return viewDisplayText;'.
    MESSAGE '    ' + CHR(125).
    MESSAGE '    ' + ''.
    MESSAGE '    ' + 'public String getviewEditText()'.
    MESSAGE '    ' + CHR(123).
    MESSAGE '    ' + '     return viewEditText;'.
    MESSAGE '    ' + CHR(125).
    MESSAGE '    ' + ''.
    MESSAGE '    ' + '@Component(id = "' + str_formid + 'Form")'.
    MESSAGE '    ' + 'private Form _form;'.
    MESSAGE '    ' + '@Persist'.
    MESSAGE '    ' + 'private int int_SelectedRow;'.
    MESSAGE '    ' + ''.
    MESSAGE '    ' + '@ApplicationState'.
    MESSAGE '    ' + 'private String myState;'.
    MESSAGE '    ' + ''.
    MESSAGE '    ' + '@Component(id = "GRID")'.
    MESSAGE '    ' + 'private Grid _Grid;'.


/*     MESSAGE pc_value. */
    DO i = 1 TO i_count:
         l_isDateField = NO.
        c_linevalue = ENTRY(i,pc_value).
        c_linevalue = REPLACE(c_linevalue,"COMMENT",CHR(1)).
        c_field =REPLACE( ENTRY(2,c_linevalue,"`"),"`","").
        IF NUM-ENTRIES(c_field,"_") > 1 THEN DO:
            c_field = ENTRY(2,c_field,"_").
        END.
        c_linevalue = REPLACE(c_linevalue,"default NULL",CHR(5)).
        c_linevalue = REPLACE(c_linevalue,"NOT NULL",CHR(4)).
        IF NUM-ENTRIES(c_linevalue,"(") >  1 THEN DO:
            c_temp = entry(1,ENTRY(2,c_linevalue,"("),")").
            i_size = INTEGER(c_temp).
        END.
        ELSE DO:
            c_temp = trim(substr(ENTRY(3,c_linevalue,"`"),1,5)).
            IF c_temp = "date" THEN DO:
                 l_isDateField = YES.
            END.


        END.
       IF NUM-ENTRIES(c_linevalue,CHR(5)) = 1 THEN
            l_null = NO.
        ELSE
            l_null = YES.
            
        IF NUM-ENTRIES(C_LINEVALUE,CHR(1)) > 1 THEN DO:
            c_comment = REPLACE(TRIM(ENTRY(2,c_linevalue,CHR(1))),"'","").
        END.
        ELSE 
        DO:
            C_COMMENT = C_FIELD.
        END.   
        c_datatype = "String".
/*         MESSAGE "c_linevalue: " c_linevalue. */

        IF c_linevalue MATCHES "*` varchar(*" THEN DO:
            c_datatype = 'String'.
        END.
        ELSE IF c_linevalue MATCHES '*` date *' THEN
        DO:
            c_datatype = 'Date'.
        END.
        ELSE DO:
            IF c_linevalue MATCHES '*` datetime *' THEN DO:
                c_datatype = 'Date'.
            END.
            ELSE
            IF c_linevalue MATCHES '*` time *' THEN DO:
                c_datatype = 'Time'.
            END.
            ELSE IF c_linevalue MATCHES '*` int(*' THEN DO:
                 c_datatype = 'Integer'.
            END.
            ELSE IF c_linevalue MATCHES '*` bigint(*' THEN
            DO:
                c_datatype = 'Long'.
            END.
            ELSE IF c_linevalue MATCHES '*` bit(1)' THEN
            DO:
                c_datatype = 'Boolean'.
            END.
        END.
        /* GENERATE TABLE */
        MESSAGE ''.
        MESSAGE '    ' + '//==============================='.
        MESSAGE '    ' + '// Text Component for ' + c_field.
        MESSAGE '    ' + '@Component(id = "' + c_field + '")'.
        MESSAGE '    ' + 'private TextField _' + c_field + ';'.
        MESSAGE '    ' + 'private ' + c_datatype + ' ' + c_field + ';'.
        MESSAGE '    ' + 'public ' + c_datatype + ' ' + 'get' + c_field + '()'.
        MESSAGE '    ' + CHR(123).
        MESSAGE '    ' + '   return ' + c_field + ';'.
        MESSAGE '    ' + CHR(125).
        MESSAGE ''.

        MESSAGE '    ' + 'public void ' + 'set' + c_field + '(' + c_datatype + ' ' + c_field + ')'.
        MESSAGE '    ' + CHR(123).
        MESSAGE '    ' + '   this.' + c_field + ' = ' + c_field + ';'.
        MESSAGE '    ' + CHR(125).
        MESSAGE '    ' + '//==============================='.
        INT_no = INT_no + 1.
        CREATE tttemp.
        bfno = INT_no.
        bffieldname = c_field.
        bfdatatype = c_datatype.
        RELEASE tttemp.
    END.
END FUNCTION.



/*                                    */
/* DEFINE TEMP-TABLE tttemp           */
/*     FIELD bfno AS INTEGER          */
/*     FIELD bffieldname AS CHARACTER */
/*     FIELD bfdatatype AS CHARACTER. */














PROCEDURE pimport:
/*     MESSAGE 'package com.quesofttech.business.domain.'  +  str_module. */
    MESSAGE 'package com.quesofttech.web.pages;'.

    MESSAGE 'import java.io.IOException;'.
    MESSAGE 'import java.util.List;'.
    MESSAGE 'import javax.annotation.Resource;'.
    MESSAGE 'import com.quesofttech.business.common.exception.BusinessException;'.    
    MESSAGE 'import com.quesofttech.business.domain.' + str_module  + '.' + str_formid + ';'.
    MESSAGE 'import com.quesofttech.business.domain.' + str_module  + '.iface.I' + str_formid + 'ServiceRemote;'.
    MESSAGE 'import com.quesofttech.business.domain.security.iface.ISecurityFinderServiceRemote;'.
    MESSAGE 'import com.quesofttech.web.base.SimpleBasePage;'.
    MESSAGE 'import com.quesofttech.web.base.SecureBasePage;'.
    MESSAGE 'import com.quesofttech.web.state.Visit;'.
    MESSAGE 'import org.apache.tapestry5.annotations.Component;'.
    MESSAGE 'import org.apache.tapestry5.annotations.Persist;'.
    MESSAGE 'import org.apache.tapestry5.annotations.Retain;'.
    MESSAGE 'import org.apache.tapestry5.annotations.Property;'.
    MESSAGE 'import org.apache.tapestry5.ComponentResources;'.
    MESSAGE 'import org.apache.tapestry5.corelib.components.Form;'.
    MESSAGE 'import org.apache.tapestry5.corelib.components.Submit;'.
    MESSAGE 'import org.apache.tapestry5.corelib.components.TextField;'.
    MESSAGE 'import org.apache.tapestry5.corelib.components.Checkbox;'.
    MESSAGE 'import org.apache.tapestry5.Block;'.
    MESSAGE 'import org.apache.tapestry5.corelib.components.Grid;'.
    MESSAGE 'import org.apache.tapestry5.ioc.annotations.Inject;'.
    MESSAGE 'import org.apache.tapestry5.services.Request;'.
    MESSAGE 'import org.omg.CosTransactions._SubtransactionAwareResourceStub;'.
    MESSAGE 'import org.slf4j.Logger;'.
    MESSAGE 'import org.apache.tapestry5.annotations.ApplicationState;'.
END PROCEDURE.
PROCEDURE getBlock:
    MESSAGE "".
    MESSAGE '    ' + 'public Block getBlock() ' + CHR(123).
    MESSAGE '    ' + '   return blockFormView;'.
    MESSAGE '    ' + CHR(125).    
    MESSAGE "".
END PROCEDURE.

PROCEDURE onSuccess:
    MESSAGE "".
    MESSAGE '    ' + "Object onSuccess()".
    MESSAGE '    ' + CHR(123).
    MESSAGE '    ' + '   _form.clearErrors();'.
    MESSAGE '    ' + '   RefreshRecords();'.
    MESSAGE '    ' + '   return blockFormView;'.
    MESSAGE '    ' + CHR(125).
    MESSAGE "".
END PROCEDURE.

PROCEDURE onFailure:
    MESSAGE "".
    MESSAGE '    ' + "Object onFailure()".
    MESSAGE '    ' + CHR(123).
    MESSAGE '    ' + '   _form.clearErrors();'.
    MESSAGE '    ' + '   _form.recordError(getMessages().get("Record_Save_Error"));'.
    MESSAGE '    ' + '   return blockFormView;'.
    MESSAGE '    ' + CHR(125).
    MESSAGE "".
END PROCEDURE.

PROCEDURE setupRender:
    MESSAGE "".
    MESSAGE '    ' + "void setupRender() " + CHR(123).
    MESSAGE '    ' + '   RefreshRecords();'.
    MESSAGE '    ' + CHR(125).
    MESSAGE "".
END PROCEDURE.


PROCEDURE RefreshRecords:
    MESSAGE '    ' + 'void RefreshRecords()'.
    MESSAGE '    ' + CHR(123).
    MESSAGE '    ' + '   try'.
    MESSAGE '    ' + '   ' + CHR(123).
    MESSAGE '    ' + '       _' + str_formid + 's = get' + str_formid + 'Service().find' + str_formid + 's();'.
    MESSAGE '    ' + '   ' + CHR(125).
    MESSAGE '    ' + '   catch(BusinessException be)'.
    MESSAGE '    ' + '   ' + CHR(123).
    MESSAGE '    ' + "".
    MESSAGE '    ' + '   ' + CHR(125).
    MESSAGE '    ' + '   if(_' + str_formid + 's!=null && !_' + str_formid + 's.isEmpty())'.
    MESSAGE '    ' + '   ' + CHR(123).
    MESSAGE '    ' + '       if(int_SelectedRow==0)'.
    MESSAGE '    ' + '       ' + CHR(123).
    MESSAGE '    ' + '           ' + str_formid + 'Detail = _' + str_formid + 's.get(_' + str_formid + 's.size() - 1);'.
    MESSAGE '    ' + '       ' + CHR(125).
    MESSAGE '    ' + '       else'.
    MESSAGE '    ' + '       ' + CHR(123).
    MESSAGE '    ' + '           ' + str_formid + 'Detail = _' + str_formid + 's.get(int_SelectedRow - 1);'.
    MESSAGE '    ' + '       ' + CHR(125).
    MESSAGE '    ' + '       ' + str_formid + 'Detail = _' + str_formid + 's.get(_' + str_formid + 's.size() - 1);'.
    MESSAGE '    ' + '       myState="U";'.
    MESSAGE '    ' + '       viewDisplayText="Block";'.
    MESSAGE '    ' + '       viewEditText="none";'.
    MESSAGE '    ' + '       assignToLocalVariable(' + str_formid + 'Detail);'.
    MESSAGE '    ' + '   ' + CHR(125).
    
    MESSAGE '             else'.
    MESSAGE '            ' + CHR(123).
    MESSAGE '           	   myState="A"; // If no List then should be in A mode instead of Update mode.'.
    MESSAGE '            ' + CHR(125).
    MESSAGE '    ' + CHR(125).
END PROCEDURE.

PROCEDURE getRefreshDisplay:
    MESSAGE 'private void refreshDisplay()'.
    MESSAGE '    ' + CHR(123).
    

   
    MESSAGE '        if(myState.equals("U"))'.
	MESSAGE '        ' + CHR(123).
    MESSAGE '            viewDisplayText="Block";'.
    MESSAGE '            viewEditText="none";'.
	MESSAGE '        ' + CHR(125).
    MESSAGE '        else'.
	MESSAGE '        ' + CHR(123).
    MESSAGE '   		 viewDisplayText="none";'.
    MESSAGE '	         viewEditText="Block";'.
	MESSAGE '        ' + CHR(125).
    MESSAGE '    ' + CHR(125).
END PROCEDURE.


PROCEDURE getRcdLocation:
    MESSAGE '    ' + 'private int getRcdLocation( Long id)  throws BusinessException'.
    MESSAGE '    ' + CHR(123).
    MESSAGE '    ' + '	int int_return ,int_i;'.
    MESSAGE '    ' + '	int_i = 0;'.
    MESSAGE '    ' + '	int_return = 0;'.
/*     MESSAGE '    ' + '  _' + str_formid + 's = get' + str_formid + 'Service().find' + str_formid + 's();'. */
    MESSAGE '    ' + '	for(' + str_formid + ' p : _' + str_formid + 's)'.
    MESSAGE '    ' + '   ' + CHR(123).
    MESSAGE '    ' + '	    int_i++;'.
    MESSAGE '    ' + '	    if((long)p.getId().intValue()==id)'.
    MESSAGE '    ' + '       ' + CHR(123).
    MESSAGE '    ' + '			int_return = int_i; '.
    MESSAGE '    ' + '       ' + CHR(125).
    MESSAGE '    ' + '   ' + CHR(125).
    MESSAGE '    ' + '   return int_return;'.
    MESSAGE '    ' + CHR(125).
END PROCEDURE.

PROCEDURE onValidateForm:
    MESSAGE "".
    MESSAGE '    ' + "void onValidateForm() " + CHR(123).
    MESSAGE '    ' + '   if ("U"== myState)'.
    MESSAGE '    ' + '   ' + CHR(123).
    MESSAGE '    ' + '       _UpdateRecord();'.
    MESSAGE '    ' + '   ' + CHR(125).
    MESSAGE '    ' + '   else'.
    MESSAGE '    ' + '   if ("A" == myState)'.
    MESSAGE '    ' + '   ' + CHR(123).
    MESSAGE '    ' + '       _AddRecord();'.
    MESSAGE '    ' + '   ' + CHR(125).
    MESSAGE '    ' + CHR(125).
    MESSAGE "".    
END PROCEDURE.


PROCEDURE assignToDatabase:
    MESSAGE '    ' + 'void assignToDatabase(' + str_formid + ' ' + str_classname + ')' + CHR(123).
    FOR EACH tttemp BY bfno.
        MESSAGE '    ' + '   ' + str_classname + '.set' + bffield + '(' + bffield + ');'.
    END.
/*     MESSAGE '   mt.setType(type);'.               */
/*     MESSAGE '   mt.setDescription(description);'. */
/*     MESSAGE '   mt.setProduced(isProduced);'.     */
/*     MESSAGE '   mt.setPurchased(isPurchased);'.   */
/*     MESSAGE '   mt.setForSale(isForSale);'.       */
    MESSAGE '    ' + '   ' + str_classname + '.setRecordStatus("A");'.
    MESSAGE '    ' + CHR(125).
END PROCEDURE.



PROCEDURE AddRecord:
    MESSAGE '    ' + 'void _AddRecord()'.
    MESSAGE '    ' + CHR(123).
    MESSAGE '    ' + '   ' + str_formid + ' ' + str_classname + ' = new ' + str_formid + '();'.
    MESSAGE '    ' + '   try ' + CHR(123).
    MESSAGE '    ' + '           ' + str_classname + '.setModifyLogin(getVisit().getMyLoginId());'.
    MESSAGE '    ' + '           ' + str_classname + '.setCreateLogin(getVisit().getMyLoginId());'.
    MESSAGE '    ' + '       assignToDatabase(' + str_classname + ');'.
    MESSAGE '    ' + '       get' + str_formid + 'Service().add' + str_formid + '(' + str_classname + ');'.
    MESSAGE '    ' + '   ' + CHR(125).	
    MESSAGE '    ' + '   catch (Exception e) ' + CHR(123).
    MESSAGE '    ' + '       _logger.info("' + str_formid + '_Add_problem");'.
    MESSAGE '    ' + '       e.printStackTrace();'.
    MESSAGE '    ' + '       _form.recordError(getMessages().get("' + str_formid + '_add_problem"));'.
    MESSAGE '    ' + '   ' + CHR(125).
    MESSAGE '    ' + CHR(125).	
END PROCEDURE.


PROCEDURE UpdateRecord:
    MESSAGE "".
    MESSAGE '    ' + 'void _UpdateRecord()' + CHR(123).
    MESSAGE '    ' + '   ' + str_formid + ' ' + str_classname + ' = new ' + str_formid + '();'.
    MESSAGE '    ' + '   try'.
    MESSAGE '    ' + '   '  + CHR(123).
    MESSAGE '    ' + '       ' + str_classname + ' = get' + str_formid + 'Service().find' + str_formid + '(id);'.
    MESSAGE '    ' + '   ' + CHR(125).
    MESSAGE '    ' + '   catch(BusinessException be)'.
    MESSAGE '    ' + '   ' + CHR(123).
    MESSAGE '    ' + ''.
    MESSAGE '    ' + '   ' + CHR(125).
    MESSAGE '    ' + '   if(' + str_classname + ' !=null)'.
    MESSAGE '    ' + '   ' + CHR(123).
    MESSAGE '    ' + '       try ' + CHR(123).
    MESSAGE '    ' + '           ' + str_classname + '.setModifyLogin(getVisit().getMyLoginId());'.
    MESSAGE '    ' + '           assignToDatabase(' + str_classname + ');'.
    MESSAGE '    ' + '           get' + str_formid + 'Service().update' + str_formid + '(' + str_classname + ');'.
    MESSAGE '    ' + '       ' + CHR(125).
    MESSAGE '    ' + '       catch (BusinessException e) ' + CHR(123).
    MESSAGE '    ' + '           _form.recordError(_typeField, e.getLocalizedMessage());'.
    MESSAGE '    ' + '       ' + CHR(125).
    MESSAGE '       catch (Exception e) ' + CHR(123).
    MESSAGE '    ' + '           _logger.info("' + str_formid + '_update_problem");'.
    MESSAGE '    ' + '           e.printStackTrace();'.
    MESSAGE '    ' + '           _form.recordError(getMessages().get("' + str_formid + '_update_problem"));'.
    MESSAGE '    ' + "       " + CHR(125).
    MESSAGE '    ' + "   " + CHR(125).
    MESSAGE '    ' + CHR(125).
    MESSAGE "".
END PROCEDURE.

PROCEDURE DeleteRecord:
MESSAGE "".
MESSAGE '    ' + 'void _DeleteRecord(Long id) ' + CHR(123).
MESSAGE '    ' + '   ' + str_formid + ' ' + str_classname + ' = new ' + str_formid + '();'.
MESSAGE '    ' + '   try'.
MESSAGE '    ' + '   ' + CHR(123).
MESSAGE '    ' + '       ' + str_classname + ' = get' + str_formid + 'Service().find' + str_formid + '(id);'.
MESSAGE '    ' + '   ' + CHR(125).
MESSAGE '    ' + '   catch(BusinessException be)'.
MESSAGE '    ' + '   ' + CHR(123).
MESSAGE "".
MESSAGE '    ' + '   ' + CHR(125).
MESSAGE '    ' + '   if(' + str_classname + '!=null)'.
MESSAGE '    ' + '   ' + CHR(123).
MESSAGE '    ' + '       try ' + CHR(123).
MESSAGE '    ' + '           ' + str_classname + '.setModifyLogin(getVisit().getMyLoginId());'.
MESSAGE '    ' + '           get' + str_formid + 'Service().logicalDelete' + str_formid + '(' + str_classname + ');'.
MESSAGE '    ' + '           if(int_SelectedRow!=0)'.
MESSAGE '    ' + '           ' + CHR(123).
MESSAGE '    ' + '               int_SelectedRow--;'.
MESSAGE '    ' + '           ' + CHR(125).
MESSAGE '    ' + '           RefreshRecords();'.
MESSAGE '    ' + '       ' + CHR(125).
MESSAGE '    ' + '           catch (BusinessException e) ' + CHR(123).
MESSAGE '    ' + '           _form.recordError(_typeField, e.getLocalizedMessage());'.
MESSAGE '    ' + '   ' + CHR(125).
MESSAGE '    ' + '       catch (Exception e) ' + CHR(123).
MESSAGE '    ' + '           _logger.info("' + str_formid + '_Delete_problem");'.
MESSAGE '    ' + '           e.printStackTrace();'.
MESSAGE '    ' + '           _form.recordError(getMessages().get("' + str_formid + '_Delete_problem"));'.
MESSAGE '    ' + '       ' + CHR(125).
MESSAGE '    ' + '   ' + CHR(125).
MESSAGE '    ' + CHR(125).
MESSAGE "".
END PROCEDURE.

PROCEDURE defaultAction:
    MESSAGE '    ' + 'void onActionFromtoolbarDel(Long id)'.
    MESSAGE '    ' + CHR(123).
    MESSAGE '            if (id!=null)' + CHR(123).
    MESSAGE '    ' + '   _form.clearErrors();'.
    MESSAGE '    ' + '   myState = "D";'.
    MESSAGE '    ' + '   _strMode = "D";'.
    MESSAGE '    ' + '   _DeleteRecord(id);'.
    MESSAGE '            ' + CHR(125).
    MESSAGE '    ' + CHR(125).
    MESSAGE "".
    MESSAGE '    ' + 'Object onActionFromToolbarAdd ()'.
    MESSAGE '    ' + CHR(123).
    MESSAGE '    ' + '   _form.clearErrors();'.
    MESSAGE '    ' + '   myState = "A";'.
    MESSAGE '    ' + '   _strMode = "A";'.
    MESSAGE '    ' + '   return blockFormView;'.
    MESSAGE '    ' + CHR(125).
    MESSAGE "".
    MESSAGE '    ' + 'Object onActionFromSelect(long id)'.
    MESSAGE '    ' + CHR(123).
    MESSAGE '    ' + '   _form.clearErrors();'.
    MESSAGE '    ' + '   myState = "U";'.
    MESSAGE '    ' + '   _strMode = "U";'.
    MESSAGE '    ' + '   lng_CurrentID = id;'.
    MESSAGE '    ' + '   try'.
    MESSAGE '    ' + '   ' + CHR(123).
    MESSAGE '    ' + '       ' + str_formid + 'Detail = get' + str_formid + 'Service().find' + str_formid + '(id);'.
    MESSAGE '    ' + '       int_SelectedRow = getRcdLocation(id);'.
    MESSAGE '    ' + '   ' + CHR(125).
    MESSAGE '    ' + '   catch(BusinessException be)'.
    MESSAGE '    ' + '   ' + CHR(123).
    MESSAGE "".
    MESSAGE '    ' + '   ' + CHR(125).
    MESSAGE "".
    MESSAGE '    ' + '   if(' + str_formid + 'Detail!=null)' + CHR(123).
    MESSAGE '    ' + '       viewDisplayText="Block";'.
    MESSAGE '    ' + '       viewEditText="none";'.
    MESSAGE '    ' + '       assignToLocalVariable(' + str_formid + 'Detail);'.
    MESSAGE '    ' + '       return blockFormView;'.
    MESSAGE '    ' + '   ' + CHR(125).
    MESSAGE '    ' + '   return null;'.
    MESSAGE '    ' + CHR(125).
		
END PROCEDURE.

PROCEDURE getRemoteService:
    MESSAGE ''.
    MESSAGE '    ' + 'private I' + str_formid + 'ServiceRemote get' + str_formid + 'Service() ' + CHR(123).
    MESSAGE '    ' + '   return getBusinessServicesLocator().get' + str_formid + 'ServiceRemote();'.
    MESSAGE '    ' + CHR(125).	
    MESSAGE ''.
END PROCEDURE.


PROCEDURE getTables:
    MESSAGE ''.
    MESSAGE '    ' + 'public List<' + str_formid + '> get' + str_formid + 's() ' + CHR(123).
    MESSAGE '    ' + '   return _' + str_formid + 's;'.
    MESSAGE '    ' + CHR(125).
    MESSAGE ''.
END PROCEDURE.
RUN pimport.
MESSAGE 'public class ' + str_formid + 'Maintenance extends SimpleBasePage ' + CHR(123) .

PROCEDURE getTable:
    MESSAGE "".
    MESSAGE '    ' + 'public ' + str_formid + ' get' + str_formid + '() throws BusinessException' + CHR(123).
    MESSAGE '    ' + '   return _' + str_formid + ';'.
    MESSAGE '    ' + CHR(125).
    MESSAGE ''.
END PROCEDURE.
    
PROCEDURE setTable:
    MESSAGE ''.
    MESSAGE '    ' + ' public void set' + str_formid + '(' + str_formid + ' tb) ' + CHR(123).
    MESSAGE '    ' + '   _' + str_formid + ' = tb;'.
    MESSAGE '    ' + CHR(125).
    MESSAGE ''.
END PROCEDURE.

PROCEDURE getRowDetail:
    MESSAGE ''.
     MESSAGE '    ' + 'public ' + str_formid + ' get' + str_formid + 'Detail() ' + CHR(123).
     MESSAGE '    ' + '  return ' + str_formid + 'Detail;'. 
     MESSAGE '    ' + CHR(125).
     MESSAGE ''.
END PROCEDURE.

PROCEDURE assignToLocal:
    MESSAGE '    ' + 'void assignToLocalVariable(' + str_formid + ' ' + str_classname + ')'.
    MESSAGE '    ' + CHR(123).
    FOR EACH tttemp BY bfno.
        MESSAGE '    ' + '   this.' + bffield + ' = ' + str_classname + '.get' + bffield + '();'.
    END.
    MESSAGE '    ' + CHR(125).
END PROCEDURE.
MESSAGE "// Default defination. ".
MESSAGE '    ' + 'private String _strMode = "";'.
MESSAGE '    ' + 'private ' + str_formid + ' ' + str_formid + 'Detail;'.
MESSAGE '    ' + 'private ' + str_formid + ' _' + str_formid + ';'.
MESSAGE '    ' + '@Persist'.
MESSAGE '    ' + 'private List<' + str_formid + '> _' + str_formid + 's;'.
MESSAGE '    ' + '@Inject'.
MESSAGE '    ' + 'private Logger _logger;'.
MESSAGE '    ' + '@Inject'.
MESSAGE '    ' + 'private Block blockFormView;'.
MESSAGE '    ' + '@Persist'.
MESSAGE '    ' + 'private long lng_CurrentID;'.
MESSAGE "// End of  Default defination. ".
GetFormContent().
RUN RefreshRecords.
RUN getRefreshDisplay.
RUN getRcdLocation.
RUN getBlock.
RUN onSuccess.
RUN onFailure.
RUN setupRender.
RUN onValidateForm.
RUN assignToDatabase.
RUN assignToLocal.
RUN AddRecord.
RUN UpdateRecord.
RUN DeleteRecord.
RUN defaultAction.
RUN getRemoteService.
RUN getTables.
RUN getTable.
RUN setTable.
MESSAGE CHR(125).








OUTPUT CLOSE.

