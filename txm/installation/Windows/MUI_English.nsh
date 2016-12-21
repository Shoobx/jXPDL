;    Together XPDL Model
;    Copyright (C) 2011 Together Teamsolutions Co., Ltd.
;
;    This program is free software: you can redistribute it and/or modify
;    it under the terms of the GNU General Public License as published by
;    the Free Software Foundation, either version 3 of the License, or 
;    (at your option) any later version.
; 
;    This program is distributed in the hope that it will be useful, 
;    but WITHOUT ANY WARRANTY; without even the implied warranty of
;    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
;    GNU General Public License for more details.
; 
;    You should have received a copy of the GNU General Public License
;    along with this program. If not, see http://www.gnu.org/licenses
;----------------------------------------------------------------------------------

# Language
# LANG_ENGLISH
  !insertmacro MUI_LANGUAGE "English"

  VIAddVersionKey /LANG=${LANG_ENGLISH} "FileVersion" "${VERSION}.${RELEASE}"
  VIAddVersionKey /LANG=${LANG_ENGLISH} "LegalCopyright" "© ${COPYRIGHT_YEAR} Together Teamsolutions Co., Ltd. All rights reserved."
  VIAddVersionKey /LANG=${LANG_ENGLISH} "FileDescription" "${APP_FULL_NAME}"
  VIAddVersionKey /LANG=${LANG_ENGLISH} "ProductName" "${APP_FULL_NAME}"
  VIAddVersionKey /LANG=${LANG_ENGLISH} "ProductVersion" "${VERSION}-${RELEASE}"
  VIAddVersionKey /LANG=${LANG_ENGLISH} "CompanyName" "Together Teamsolutions Co.. Ltd."
  VIAddVersionKey /LANG=${LANG_ENGLISH} "LegalTrademarks" "Together (R) is a registered trademark of GrECo International Holding AG in Austria/Europe."
  VIAddVersionKey /LANG=${LANG_ENGLISH} "Current version" "${VERSION}-${RELEASE}"
   

  ;---------------------------------
  LangString MUI_UNTEXT_WELCOME_INFO_TITLE ${LANG_ENGLISH} "Welcome to the ${APP_FULL_NAME} Uninstall Wizard"
  LangString MUI_UNTEXT_WELCOME_INFO_TEXT ${LANG_ENGLISH} "This wizard will guide you through the uninstallation of ${APP_FULL_NAME}.$\r$\n$\r$\nBefore starting the uninstallation, make sure ${APP_FULL_NAME} is not running.$\r$\n$\r$\n$_CLICK"
  LangString MUI_UNTEXT_UNINSTALLING_TITLE ${LANG_ENGLISH}  "Uninstalling"
  LangString MUI_UNTEXT_UNINSTALLING_SUBTITLE ${LANG_ENGLISH}  "Please wait while ${APP_FULL_NAME} is being uninstalled."
  LangString MUI_UNTEXT_FINISH_TITLE ${LANG_ENGLISH}  "Uninstallation Complete"
  LangString MUI_UNTEXT_FINISH_SUBTITLE ${LANG_ENGLISH}  "${APP_FULL_NAME} uninstallation completed successfully."
  LangString MUI_UNTEXT_ABORT_TITLE ${LANG_ENGLISH}  "Uninstallation Aborted"
  LangString MUI_UNTEXT_ABORT_SUBTITLE ${LANG_ENGLISH}  "${APP_FULL_NAME} uninstallation is aborted"
  
  LangString INSTALL_FAILED_DETAIL ${LANG_ENGLISH}  "${APP_FULL_NAME} installation failed (output: $0)"
  LangString INSTALL_FAILED_MESSAGE ${LANG_ENGLISH}  "${APP_FULL_NAME} installation failed"
  LangString INSTALL_SUCCED_DETAIL ${LANG_ENGLISH}  "${APP_FULL_NAME} installation succeded (output: $0)"
  ;--------------------------------

  ;LangString javadir_text ${LANG_ENGLISH} "Choose the Java virtual machine to use"
  
  LangString javaversion_text ${LANG_ENGLISH} "Choosing version of java"
  LangString javahome_text ${LANG_ENGLISH} "JAVA_HOME environment variable"
  LangString javadir_text ${LANG_ENGLISH} "Please choose the directory which will be set as JAVA HOME directory"
 
  LangString TEXT_IO_JAVATITLE ${LANG_ENGLISH} "Java Setup"
  LangString TEXT_IO_JAVASUBTITLE ${LANG_ENGLISH} "Java Development Kit"
  LangString TEXT_IO_JVM ${LANG_ENGLISH} "Choosing version of Java virtual machine"
  LangString TEXT_IO_JAVA_HOME ${LANG_ENGLISH} "JAVA_HOME environment variable"
  LangString TEXT_IO_JAVA_HOME_DIR ${LANG_ENGLISH} "Please choose the directory which will be set as JAVA HOME directory"
  LangString TEXT_IO_JAVA_FOLDER ${LANG_ENGLISH} "Browse for folder which will be set as JAVA HOME directory"
   
  LangString javac_not_exist ${LANG_ENGLISH} "File java.exe does not exist in directory $TEMP1\bin"
  LangString jsse_jre_not_exist ${LANG_ENGLISH} "File jsse.jar does not exist in directory $TEMP1\jre\lib"
  LangString jsse_not_exist ${LANG_ENGLISH} "File jsse.jar does not exist in directory $TEMP1\lib"
  LangString inst_opt_error ${LANG_ENGLISH} "InstallOptions error:$\r$\n$TEMP1"
  
  LangString finish_run_text ${LANG_ENGLISH} "Start ${APP_FULL_NAME} Application"
  
  LangString domlfile ${LANG_ENGLISH}  "DOML Together Relation Modeler file"
  
  LangString ABBREVIATION ${LANG_ENGLISH} "${SHORT_UPPER_NAME}"
  LangString Documentation ${LANG_ENGLISH}  "Documentation"
  LangString Manual ${LANG_ENGLISH} "Manual" 
  
  