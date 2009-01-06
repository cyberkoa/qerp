DEFINE VARIABLE str_formid AS CHARACTER   NO-UNDO.
DEFINE VARIABLE str_subject AS CHARACTER   NO-UNDO.
DEFINE VARIABLE str_module AS CHARACTER   NO-UNDO.
DEFINE VARIABLE pc_value AS CHARACTER   NO-UNDO.
DEFINE VARIABLE  str_classname AS CHARACTER   NO-UNDO.


/*   pc_value = "`soh_id` bigint(20) NOT NULL,  */
/*       `soh_CustomerPO` varchar(20) NOT NULL, */
/*   `soh_Number` varchar(10) NOT NULL,         */
/*   `soh_DocType` varchar(5) NOT NULL,         */
/*   `version` int(11) NOT NULL,                */
/*   `fk_Customer` bigint(20) default NULL".    */


pc_value = "  `v_id` bigint(20) NOT NULL,
  `bomd_endDate` datetime NOT NULL,
  `bomd_Location` varchar(255) NOT NULL,
  `bomd_quantityRequired` double NOT NULL,
  `bomd_scrapFactor` double NOT NULL,
  `bomd_startDate` datetime NOT NULL,
     `fk_BOM` bigint(20) default NULL,
  `fk_Material` bigint(20) default NULL".
 
  

/*     SalesOrderMaterial */
str_formid = 'BomDetail'.
str_subject = 'BomDetail'.
str_classname = 'bomDetail'.
    str_module = 'general'.





RUN tml_generator.p (INPUT str_formid , 
                     INPUT str_subject,
                     INPUT pc_value).


RUN java_generator.p(INPUT str_formid,
                     INPUT str_module,
                     INPUT str_classname,
                     INPUT pc_value).
