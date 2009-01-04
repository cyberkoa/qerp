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


pc_value = "`User_id` bigint(20) NOT NULL,
  `user_EmailAddress` varchar(50) default NULL,
  `user_ExpiryDate` datetime default NULL,
  `user_FirstName` varchar(30) NOT NULL,
  `user_LastName` varchar(30) NOT NULL,
  `user_Login` varchar(20) NOT NULL,
  `user_Salutation` varchar(10) default NULL,
  `user_Telephone` varchar(15) default NULL,
     `fk_UserPassword` bigint(20) default NULL".
 
  

/*     SalesOrderMaterial */
str_formid = 'User'.
str_subject = 'User'.
str_classname = 'user'.
    str_module = 'security'.





RUN tml_generator.p (INPUT str_formid , 
                     INPUT str_subject,
                     INPUT pc_value).


RUN java_generator.p(INPUT str_formid,
                     INPUT str_module,
                     INPUT str_classname,
                     INPUT pc_value).
