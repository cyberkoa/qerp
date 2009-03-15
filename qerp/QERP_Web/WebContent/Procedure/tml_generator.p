DEFINE INPUT  PARAMETER str_formid  AS CHARACTER   NO-UNDO.
DEFINE INPUT  PARAMETER str_subject AS CHARACTER   NO-UNDO.
DEFINE INPUT  PARAMETER pc_value AS CHARACTER   NO-UNDO.

DEFINE VARIABLE str_htmlStart AS CHARACTER   NO-UNDO.
DEFINE VARIABLE str_header AS CHARACTER   NO-UNDO.
DEFINE VARIABLE str_body AS CHARACTER   NO-UNDO.
/* DEFINE VARIABLE str_subject AS CHARACTER   NO-UNDO. */
DEFINE VARIABLE str_bodystart AS CHARACTER   NO-UNDO.
/* DEFINE VARIABLE str_formid AS CHARACTER   NO-UNDO. */
DEFINE VARIABLE str_bodyend AS CHARACTER   NO-UNDO.
/* DEFINE VARIABLE pc_value AS CHARACTER   NO-UNDO. */



FUNCTION GetFormContent RETURN CHARACTER
    :

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
   DEFINE VARIABLE c_isIDDisplay AS CHARACTER   NO-UNDO.
   DEFINE VARIABLE c_DIVStart  AS CHARACTER   NO-UNDO.
   DEFINE VARIABLE c_DIVEnd  AS CHARACTER   NO-UNDO.
   
    pc_value = REPLACE(pc_value,"A=Active,D=Logically","A=Active/D=Logically").
    pc_value = REPLACE(pc_value,"Deleted, L=Locked","Deleted/L=Locked").
     i_count = NUM-ENTRIES(pc_value).
/*     pc_value = REPLACE(pc_value,"default NULL",CHR(5)). */
/*     pc_value = REPLACE(pc_value,"NOT NULL",CHR(4)).     */


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

        IF NOT l_null THEN DO:
/*             c_validator = 't:validate="'. */

        END.
        /* GENERATE TABLE */
        IF lc(c_field) = 'id' THEN
            NEXT.
/*         DO:                                                      */
/*             c_divStart  = ' <DIV style="display:none;"> '.       */
/*             c_divEnd = '</DIV> '.                                */
/*             c_isIDDisplay = '$' + CHR(123) + c_field + CHR(125). */
/*         END.                                                     */
/*         ELSE DO:                                                 */
/*             c_divStart  = ''.                                    */
/*             c_divEnd = ''.                                       */
/*             c_isIDDisplay = ''.                                  */
/*         END.                                                     */
        c_divStart  = ''.
        c_divEnd = ''.
        c_isIDDisplay = ''.
        IF l_isDatefield THEN DO:
            MESSAGE '                       <tr>'.
            MESSAGE '                           <th><t:label t:for="' + c_field + '">' + c_comment + '</t:label>:</th>'.
            MESSAGE '                           <td><t:datefield '
                                                + ' t:type="TextField"'
                                                + ' t:id="' + c_field 
                                                + '" t:value="' + c_field 
                                                + c_validator
                                                + '" /></td>'.
            MESSAGE '                       </tr>'.
        END.
        ELSE DO:
            MESSAGE '                       <tr>'.
            MESSAGE '                           <th><t:label t:for="' + c_field + '">' + c_comment + '</t:label>:</th>'.
            MESSAGE '                           <td>  ' + c_isIDDisplay + ' ' + c_divstart + '<input '
                                                + ' t:type="TextField"'
                                                + ' t:id="' + c_field 
                                                + '" t:value="' + c_field 
                                                + '" t:maxLength="' + string(i_size)
                                                + '" t:size="' + string(i_size) 
                                                + c_validator
                                                + '" />'
                                                + c_divend
                                                + ' </td>'.
            MESSAGE '                       </tr>'.
        END.

    END.










END FUNCTION.




OUTPUT TO VALUE(str_formid + "Maintenance.tml").
MESSAGE '<html t:type="cmp_layout" xmlns:t="http://tapestry.apache.org/schema/tapestry_5_0_0.xsd">'.     
MESSAGE '<head> <title>' + str_subject + ' Maintenance</title><link rel="stylesheet" type="text/css"  href="$' + CHR(123) + 'asset:context:css/global.css' + CHR(125) +  '"/></head>'.
MESSAGE '<body>'.
MESSAGE '<table width="100%">'.
MESSAGE '	<tr>'.
MESSAGE '		<td style="background:#EEEEEE;align:center;">'.
MESSAGE '			<center><font color="#001C48" size="3"><b>' + str_subject + ' Maintenance</b></font></center>'.
MESSAGE '		</td>'.
MESSAGE '	</tr>'.
MESSAGE '</table>'.
MESSAGE '<t:zone t:id="zoneFormView" show="slidedown">'.
MESSAGE '    <t:delegate to="block" />'.
MESSAGE '</t:zone>'.
MESSAGE '<t:block id="blockFormView">'.
MESSAGE '   <form t:type="form" t:id="' + str_formid + 'Form" t:zone="zoneFormView" >'.
MESSAGE '       <t:actionlink t:zone="zoneFormView" t:id="toolbarAdd"><button><img src="images/addRecord24.png" /></button></t:actionlink>'.
MESSAGE '       <button type="submit"><img  src="images/saveRecord24.png" /></button>'.
MESSAGE '       <t:actionlink  t:mixins="confirm" t:message="Are you sure you want to delete ID:$' + chr(123) + 'id' + chr(125) + '?"  t:id="toolbardel" t:context="$' + chr(123) + 'id' + chr(125) + '"><button><img src="images/deleteRecord24.png" /></button></t:actionlink>'.
MESSAGE '       <div style="height:300px;width:100%;overflow:auto;background-color:#CCCCFF">'. 
MESSAGE '           <t:GRID'.
MESSAGE '           t:id="Grid"'.
MESSAGE '           t:reorder="id,createLogin,createTimestamp,modifyLogin,modifyTimestamp"'.
MESSAGE '           t:source="' + str_formid + 's"'.
MESSAGE '           t:row="' + str_formid + '"'.
MESSAGE '		    t:rowsPerPage="10"'.
MESSAGE '           t:pagerPosition="both"'.
MESSAGE '           t:exclude="sessionid,recordstatus,version,modifyapp,createapp"'.
MESSAGE '           t:inPlace="literal:true">'.
MESSAGE '           <t:parameter name="idCell">'.
MESSAGE '               <t:actionlink t:id="select"  t:zone="zoneFormView"  t:context="' + str_formid + '.id">'.
MESSAGE '                   select'.
MESSAGE '               </t:actionlink>'.
MESSAGE '           </t:parameter>'.
MESSAGE '           </t:GRID>	'.
MESSAGE '       </div>	'.



MESSAGE '       <table id="main">'.
MESSAGE '           <tr>'.
MESSAGE '               <td>'.
MESSAGE '                   <DIV style="display:none;"> <input  t:type="TextField" t:id="id" t:value="id" t:maxLength="20" t:size="20" /></DIV>'.
                
MESSAGE '                   <table id="input">'.

GetFormContent().

	
MESSAGE '       			</table>'.
MESSAGE '               </td>'.
MESSAGE '           </tr>'.
MESSAGE '       </table>'.
MESSAGE '   <t:errors />'.
MESSAGE '   </form>'.
MESSAGE '</t:block>'.
MESSAGE '</body>'.
MESSAGE '</html>'.

OUTPUT CLOSE.

