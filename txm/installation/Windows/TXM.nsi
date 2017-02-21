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

;----------------------------------------------------------------------------------
; TXM installation script
;	All output message will be written to file ..\..\log_gwx.txt
;----------------------------------------------------------------------------------
;Use only for testing with makensisw.exe
;!define VERSION "9.4" 	; Uncomment this for build with makensisw.exe
;!define RELEASE "1" ; Uncomment this for build with makensisw.exe
;!define OUTPUT_DIR "..\..\distributions\${SHORT_NAME}-${VERSION}-${RELEASE}\${SHORT_NAME}"
;----------------------------------------------------------------------------------

!include "LogicLib.nsh"
!include "WinMessages.nsh"
!include "FileFunc.nsh"   ; for ${GetSize} for EstimatedSize registry entry
;!include "AddRemove.nsh"
!include "MUI.nsh"
!include "Sections.nsh"
!include "InstallOptions.nsh"
!include "x64.nsh"
!include "togSetJava.nsh"
!include "togDirectory.nsh"
!include "togStartOption.nsh"
!include "WordFunc.nsh"

!insertmacro WordFind

;!define SHORT_NAME "txm"
;!define SHORT_UPPER_NAME "TXM"
!define APPLICATION_DIR "..\..\output\${SHORT_NAME}-${VERSION}-${RELEASE}"
;!define NAME "Together XPDL Model"
;Version Information
VIProductVersion "${VERSION}.${RELEASE}.0"
  
Name "${APP_FULL_NAME} ${VERSION}-${RELEASE}" ;Define your own software name here

!define MUI_ICON "tog.ico"
!define MUI_UNICON "tog-uninstall.ico"

RequestExecutionLevel admin

BrandingText "${APP_FULL_NAME} ${VERSION}-${RELEASE}"
;---------------------------------------------------------------------------------------
;Configuration
;---------------------------------------------------------------------------------------
; General


;Remember install folder
InstallDirRegKey HKCU "Software\${APP_FULL_NAME} ${VERSION}-${RELEASE}" ""

!define MUI_HEADERIMAGE
!define MUI_HEADERIMAGE_BITMAP "Header.bmp"
!define MUI_HEADERIMAGE_UNBITMAP "Header.bmp"
!define MUI_ABORTWARNING

;ShowInstDetails show

; Compress
;------------
SetCompress          auto
SetCompressor        bzip2
SetDatablockOptimize on
SetDateSave          on

;--------------------------------
;Modern UI Configuration

  !define MUI_CUSTOMPAGECOMMANDS

  !define MUI_WELCOMEPAGE

; smaller fonts
  !define MUI_WELCOMEPAGE_TITLE_3LINES

  !define MUI_WELCOMEFINISHPAGE_BITMAP "Wizard.bmp"
  !define MUI_LICENSEPAGE
;  !define MUI_DIRECTORYPAGE
  !define MUI_FINISHPAGE

; smaller fonts
	!define MUI_FINISHPAGE_TITLE_3LINES
		
	!define MUI_FINISHPAGE_LINK "Visit Together XPDL Model Homepage"
	!define MUI_FINISHPAGE_LINK_LOCATION "http://www.together.at/prod/database/txm"

	!define MUI_FINISHPAGE_NOAUTOCLOSE ;To allow ShowInstDetails log
	;!define MUI_FINISHPAGE_RUN
	;!define MUI_FINISHPAGE_RUN_FUNCTION "RunApplication"
	;!define MUI_FINISHPAGE_CANCEL_ENABLED

	;!define MUI_TEXT_FINISH_RUN "Start ${APP_FULL_NAME} ${VERSION}-${RELEASE}"

	
	!define MUI_FINISHPAGE_SHOWREADME "$INSTDIR\doc\txm-current.doc.pdf"
	!define MUI_FINISHPAGE_SHOWREADME_TEXT "Show Documentation"
	!define MUI_FINISHPAGE_SHOWREADME_NOTCHECKED

	;!define MUI_UNTEXT_CONFIRM_TITLE $(MUI_UNTEXT_CONFIRM_TITLE)

  !define MUI_UNINSTALLER
  !define MUI_UNCONFIRMPAGE

;--------------------------------
;Variables

  Var STARTMENU_FOLDER
  Var MUI_TEMP
  Var TEMP1
  Var JAVAHOME
  Var DEFAULT_BROWSER
  Var ADD_STARTMENU
  Var ADD_QUICKLAUNCH
  Var ENABLE_QUICKLAUNCH
  Var ADD_DESKTOP
  Var ENABLE_DESKTOP 
  Var SILENT
  Var DefaultDir
  Var CREATE_STARTUP_MENU
  Var CREATE_QUICK_LAUNCH_ICON
  Var CREATE_DESKTOP_ICON

  ;Variables for window registry 
  Var AppID
!define AppUserModelID "Together.XPDL.Model"
!define ProgID "txm.cmd"
; Folder-selection page
;InstallDir "$PROGRAMFILES\${SHORT_NAME}-${VERSION}-${RELEASE}"
;==========================================================================

!ifdef INNER
  !echo "Inner invocation"                  ; just to see what's going on
  OutFile "$%TEMP%\tempinstaller.exe"       ; not really important where this is
  SetCompress off                           ; for speed
!else
  !echo "Outer invocation"
 
  ; Call makensis again, defining INNER.  This writes an installer for us which, when
  ; it is invoked, will just write the uninstaller to some location, and then exit.
  ; Be sure to substitute the name of this script here.
   
  !system "$\"${NSISDIR}\makensis$\" /DINNER /O..\..\log_nsis_inner.txt /DAPP_FULL_NAME=$\"${APP_FULL_NAME}$\" /DSHORT_NAME=$\"${SHORT_NAME}$\" /DSHORT_UPPER_NAME=$\"${SHORT_UPPER_NAME}$\" /DVERSION=${VERSION} /DRELEASE=${RELEASE} /DBUILDID=${BUILDID} /DTARGET_VM=$\"${TARGET_VM}$\" /DCOPYRIGHT_YEAR=${COPYRIGHT_YEAR} TXM.nsi" = 0
 
  ; So now run that installer we just created as %TEMP%\tempinstaller.exe.  Since it
  ; calls quit the return value isn't zero.
 
  !system "$%TEMP%\tempinstaller.exe" = 2
 
  ; That will have written an ler binary for us.  Now we sign it with your
  ; favourite code signing tool.
 !if "${SERVER_TIMEOUT}" == "true"
    ;time out and not found signtool_path  can't sign ->error
      !if "${SIGNTOOL_PATH}" != ""
             !error
     !endif
  !else
     ;normal mode[return success/fail]
	 !if "${SIGN_SETUP_TIMESTAMP}" == "true"
		!if "${SIGNTOOL_PATH}" != ""
		   !echo "Return Value"
			!system "$\"${SIGNTOOL_PATH}$\" sign /f $\"${KEY_PATH}$\" /p ${PASSWORD} /d $\"${FULL_NAME}$\" /du $\"http://www.together.at$\" /t $\"${TIMESTAMP_URL}$\" $\"$%TEMP%\uninstall.exe$\"" = 0
		!endif
	 !endif
	 
	 !if "${SIGN_SETUP_TIMESTAMP}" == "false"
		!if "${SIGNTOOL_PATH}" != "" 
			!system "$\"${SIGNTOOL_PATH}$\" sign /f $\"${KEY_PATH}$\" /p ${PASSWORD} /d $\"${FULL_NAME}$\" /du $\"http://www.together.at$\" $\"$%TEMP%\uninstall.exe$\"" = 0
		!endif
	!endif
 !endif

;  !system "SIGNCODE <signing options> $%TEMP%\uninstaller.exe" = 0
 
  ; Good.  Now we can carry on writing the real installer.
 
  OutFile "${OUTPUT_DIR}\${SHORT_NAME}-${VERSION}-${RELEASE}.exe" ; The file to write
  SetCompressor /SOLID lzma
!endif


;==========================================================================
;----------------------------------------------------------------------------------
# License page
   LicenseLangString license_text ${LANG_ENGLISH} "..\..\licenses\License.txt"
   LicenseForceSelection checkbox
  
;--------------------------------

;Pages Define our own pages

  !insertmacro MUI_PAGE_WELCOME
  !insertmacro MUI_PAGE_LICENSE "$(license_text)"
  !insertmacro TOG_CUSTOMPAGE_SETJAVA "${APP_FULL_NAME} ${VERSION}-${RELEASE}" $JAVAHOME "${TARGET_VM}";
  !insertmacro TOG_CUSTOMPAGE_DIRECTORY "${APP_FULL_NAME} ${VERSION}-${RELEASE}" $DefaultDir  ;
  !insertmacro TOG_CUSTOMPAGE_STARTOPTION "${APP_FULL_NAME} ${VERSION}-${RELEASE}" $ADD_STARTMENU $STARTMENU_FOLDER $ENABLE_DESKTOP $ADD_DESKTOP $ENABLE_QUICKLAUNCH $ADD_QUICKLAUNCH  ;
  !insertmacro MUI_PAGE_INSTFILES
  !insertmacro MUI_PAGE_FINISH

  ;--------------------------------
;Languages
# LANG_ENGLISH
  !include "MUI_English.nsh"
  
  ;---------------------------------
# Things that need to be extracted on startup (keep these lines before any File command!)
# Only useful for BZIP2 compression
# Use ReserveFile for your own Install Options ini files too!
  !insertmacro MUI_RESERVEFILE_INSTALLOPTIONS
  ReserveFile "javapage.ini"
  ReserveFile "set-shortcuts.ini"

;---------------------------------------------------------------------------------------
;Modern UI System
;---------------------------------------------------------------------------------------
;Installer Sections
;---------------------------------------------------------------------------------------

Section "Install" Install	

   SectionIn 1 2 3 
   SetOverwrite try
   SetShellVarContext all

   Push $R0
   
   #-----------------setting for silent installation
	${if} $SILENT == "YES"

		${if} $CREATE_STARTUP_MENU == "on"
			StrCpy $ADD_STARTMENU "1"
		${else}
			StrCpy $ADD_STARTMENU "0"
		${endif}
		
		${if} $CREATE_QUICK_LAUNCH_ICON == "on"
				StrCpy $ADD_QUICKLAUNCH "1"
		${else}
				StrCpy $ADD_QUICKLAUNCH "0"
		${endif}
		
		${if} $CREATE_DESKTOP_ICON == "on"
				StrCpy $ADD_DESKTOP "1"
		${else}
				StrCpy $ADD_DESKTOP "0"
		${endif}

	${endif}
#----------------------------------------------------
#-------------- Clear previous version --------------
  ;------------ Finding AppID
  Push $0
  ReadRegStr $0 HKLM "Software\Classes\AppID\${ProgID}" "AppID"
  StrCpy $AppID "$0"
  Pop $0
  
  ;------------- Delete AppID
  DeleteRegKey HKCR "Applications\${ProgID}"
  DeleteRegKey HKCU "Software\Microsoft\Windows\CurrentVersion\App paths\${ProgID}"    
  DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\App paths\${ProgID}" 
  DeleteRegKey HKLM "Software\Classes\AppID\${ProgID}" 
  DeleteRegKey HKLM "Software\Classes\AppID\$AppID"
  ${If} ${RunningX64}
  DeleteRegKey HKLM "Software\Classes\Wow6432Node\AppID\${ProgID}"
  DeleteRegKey HKLM "Software\Classes\Wow6432Node\AppID\$AppID"
  ${EndIf}
#---------------------------------------------------------------------------------
  ;generate GUID
  Push $0
  call CreateGUID
  Pop $0
  #------- Writing Registry --------------------------------#
  StrCpy $AppID "$0"
  Pop $0

  WriteRegStr HKCR "Applications\${ProgID}" "AppUserModelID" "${AppUserModelID}"
  WriteRegStr HKCR "Applications\${ProgID}\shell\open\command" "" '"$INSTDIR\txm.cmd" "%1"'
  WriteRegStr HKCU "Software\Microsoft\Windows\CurrentVersion\App paths\${ProgID}" "" "$INSTDIR\txm.cmd"  
  WriteRegStr HKCU "Software\Microsoft\Windows\CurrentVersion\App paths\${ProgID}" "path" "$INSTDIR"    
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\App paths\${ProgID}" "" "$INSTDIR\txm.cmd"  
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\App paths\${ProgID}" "path" "$INSTDIR"
  WriteRegStr HKLM "Software\Classes\AppID\${ProgID}" "" "${AppUserModelID}" 
  WriteRegStr HKLM "Software\Classes\AppID\${ProgID}" "AppID" "$AppID"
  WriteRegStr HKLM "Software\Classes\AppID\$AppID" "" "${SHORT_NAME}"
  WriteRegStr HKLM "Software\Classes\AppID\$AppID" "RunAs" "Interactive User"
  ;--- if 64bits ---
  ${If} ${RunningX64}
  WriteRegStr HKLM "Software\Classes\Wow6432Node\AppID\${ProgID}" "" "${AppUserModelID}" 
  WriteRegStr HKLM "Software\Classes\Wow6432Node\AppID\${ProgID}" "AppID" "$AppID"        
  WriteRegStr HKLM "Software\Classes\Wow6432Node\AppID\$AppID" "" "${SHORT_NAME}"
  WriteRegStr HKLM "Software\Classes\Wow6432Node\AppID\$AppID" "RunAs" "Interactive User"
  ${EndIf}


  ;SetOutPath "$INSTDIR\components"
    ;File /r "${APPLICATION_DIR}\components\*"
  SetOutPath "$INSTDIR"
	File /r /x api /x example /x lib /x *current.test.pdf "${APPLICATION_DIR}\*"
	File tog.ico
  
  !ifndef INNER
;	  SetOutPath "${INSTDIR}"
	  File $%TEMP%\uninstall.exe
  !endif  

  StrCpy $1 $TEMP1
  ; replace char '\' with char '/' at JDK directory string
  Push "$1"
  Push "\"
  Push "/"
	Call ReplaceChar
	Pop $1

	ClearErrors

  SetOutPath $INSTDIR
	success1:
	DetailPrint "Together XPDL Model installation succeded (output: $0)"
   ${If} ${RunningX64}
    SetRegView 64
   ${EndIf}
  ; Write the installation path into the registry
  WriteRegStr HKLM "SOFTWARE\${APP_FULL_NAME} ${VERSION}-${RELEASE}" "Install_Dir" "$INSTDIR"

 
  ; Write the uninstall keys for Windows
  ; Write the installation path into the registry
  WriteRegStr HKLM "Software\${APP_FULL_NAME} ${VERSION}-${RELEASE}" "InstDir" "$INSTDIR"
  ; Write the silent into the registry
  ;WriteRegStr HKLM "Software\${APP_FULL_NAME} ${VERSION}-${RELEASE}" "silent" "$SILENT"
  
 ${If} $ADD_STARTMENU != '0'
   ;clear
  IfFileExists "$SMPROGRAMS\$STARTMENU_FOLDER\$(ABBREVIATION) ${VERSION}-${RELEASE}.lnk" StartCleanSTARTMENU EndCleanSTARTMENU
  	StartCleanSTARTMENU:
  			Delete "$SMPROGRAMS\$STARTMENU_FOLDER\$(ABBREVIATION) ${VERSION}-${RELEASE}.lnk"
  	EndCleanSTARTMENU:
  CreateDirectory "$SMPROGRAMS\$STARTMENU_FOLDER"
  
  
  #Create shortcuts
  
  #CreateShortCut "$SMPROGRAMS\$STARTMENU_FOLDER\$(ABBREVIATION) ${VERSION}-${RELEASE}.lnk" \
  #               "$JAVAHOME\bin\javaw.exe" \
  #			 "-Xmx800m -jar $\"$INSTDIR\lib\txm-launcher.jar$\"" \
  #              "$INSTDIR\tog.ico"
 CreateDirectory "$SMPROGRAMS\$STARTMENU_FOLDER\$(ABBREVIATION) Documentation"
 CreateShortCut "$SMPROGRAMS\$STARTMENU_FOLDER\$(ABBREVIATION) Documentation\$(ABBREVIATION) Manual HTML.lnk" \
                "$INSTDIR\doc\txm-current.doc.html" \
                "" \
                $DEFAULT_BROWSER
 CreateShortCut "$SMPROGRAMS\$STARTMENU_FOLDER\$(ABBREVIATION) Documentation\$(ABBREVIATION) Manual PDF.lnk" \
                "$INSTDIR\doc\txm-current.doc.pdf" \
                "" \
                ""
 CreateShortCut "$SMPROGRAMS\$STARTMENU_FOLDER\$(ABBREVIATION) Homepage.lnk" \
                "http://www.together.at/prod/database/txm"\
                "" \
                $DEFAULT_BROWSER
 IfSilent createShortCutUninstallSilent
	
 CreateShortCut "$SMPROGRAMS\$STARTMENU_FOLDER\Uninstall.lnk" \
 				 "$INSTDIR\uninstall.exe" \
 				 "" \
 				 "$INSTDIR\uninstall.exe" 0	
   Goto endShortCut
 	createShortCutUninstallSilent:
 CreateShortCut "$SMPROGRAMS\$STARTMENU_FOLDER\Uninstall.lnk" \
 				 "$INSTDIR\uninstall.exe" \
 				 "/SILENT" \
 				 "$INSTDIR\uninstall.exe" 0
 	endShortCut:
 	WinShell::SetLnkAUMI "$SMPROGRAMS\$STARTMENU_FOLDER\$(ABBREVIATION) ${VERSION}-${RELEASE}.lnk" "${AppUserModelID}"
  ${endif}
  
  ${If} $ADD_QUICKLAUNCH != '0'
#      IfFileExists "$QUICKLAUNCH\$(ABBREVIATION) ${VERSION}-${RELEASE}.lnk" StartCleanQUICKLAUNCH EndCleanQUICKLAUNCH
#	StartCleanQUICKLAUNCH:
#		Delete "$QUICKLAUNCH\$(ABBREVIATION) ${VERSION}-${RELEASE}.lnk"
#	EndCleanQUICKLAUNCH:
#	
#	CreateShortcut 	"$QUICKLAUNCH\$(ABBREVIATION) ${VERSION}-${RELEASE}.lnk" \
# 					"$JAVAHOME\bin\javaw.exe" \
#				 	"-Xmx800m -jar $\"$INSTDIR\lib\txm-launcher.jar$\"" \
#                 	"$INSTDIR\tog.ico" 0
#	WinShell::SetLnkAUMI "$QUICKLAUNCH\$(ABBREVIATION) ${VERSION}-${RELEASE}.lnk" "${AppUserModelID}"
  ${endif}
  
  ${If} $ADD_DESKTOP != '0'
#      IfFileExists "$DESKTOP\$(ABBREVIATION) ${VERSION}-${RELEASE}.lnk" StartCleanDESKTOP EndCleanDESKTOP
#	StartCleanDESKTOP:
#		  Delete "$DESKTOP\$(ABBREVIATION) ${VERSION}-${RELEASE}.lnk"
#	EndCleanDESKTOP:
	
#	CreateShortCut 	"$DESKTOP\$(ABBREVIATION) ${VERSION}-${RELEASE}.lnk" \
#				"$JAVAHOME\bin\javaw.exe" \
#			 	"-Xmx800m -jar $\"$INSTDIR\lib\txm-launcher.jar$\"" \
#				"$INSTDIR\tog.ico" 0
	
#	WinShell::SetLnkAUMI "$DESKTOP\$(ABBREVIATION) ${VERSION}-${RELEASE}.lnk" "${AppUserModelID}"
  ${endif}

  ; get cumulative size of all files in and under install dir
  ; report the total in KB (decimal)
  ; place the answer into $0  ($1 and $2 get other info we don't care about)
  ${GetSize} "$INSTDIR" "/S=0K" $0 $1 $2
  ; Convert the decimal KB value in $0 to DWORD
  ; put it right back into $0
  IntFmt $0 "0x%08X" $0
  
  ;System::Int64Op $0 * 1024
  ;Pop $0
  
  ; Create/Write the reg key with the dword value
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APP_FULL_NAME} ${VERSION}-${RELEASE}" \ 
                    "EstimatedSize" "$0"

  ; Write the uninstall keys for Windows
;  WriteRegStr HKLM 	"Software\Microsoft\Windows\CurrentVersion\Uninstall\${APP_FULL_NAME} ${VERSION}-${RELEASE}" \
;                    "DisplayName" "${APP_FULL_NAME} ${VERSION}-${RELEASE}"
  WriteRegStr HKLM 	"Software\Microsoft\Windows\CurrentVersion\Uninstall\${APP_FULL_NAME} ${VERSION}-${RELEASE}" \
                    "DisplayName" "${APP_FULL_NAME}"

	IfSilent writeRegStrSilent
	WriteRegStr HKLM 	"Software\Microsoft\Windows\CurrentVersion\Uninstall\${APP_FULL_NAME} ${VERSION}-${RELEASE}" \
  					"UninstallString" '"$INSTDIR\uninstall.exe"'
	Goto continueWriteReg
	writeRegStrSilent:
  WriteRegStr HKLM 	"Software\Microsoft\Windows\CurrentVersion\Uninstall\${APP_FULL_NAME} ${VERSION}-${RELEASE}" \
  					"UninstallString" '"$INSTDIR\uninstall.exe" /SILENT'

	continueWriteReg:

  WriteRegStr HKLM     "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APP_FULL_NAME} ${VERSION}-${RELEASE}" \
                                     "DisplayIcon" "$INSTDIR\tog.ico"
  WriteRegStr HKLM 	"Software\Microsoft\Windows\CurrentVersion\Uninstall\${APP_FULL_NAME} ${VERSION}-${RELEASE}" \
  									"Publisher" "Together Teamsolutions Co., Ltd."
;  WriteRegStr HKLM 	"Software\Microsoft\Windows\CurrentVersion\Uninstall\${APP_FULL_NAME} ${VERSION}-${RELEASE}" \
;  									"DisplayVersion" "${VERSION}-${RELEASE}"
  WriteRegStr HKLM 	"Software\Microsoft\Windows\CurrentVersion\Uninstall\${APP_FULL_NAME} ${VERSION}-${RELEASE}" \
  									"DisplayVersion" "${VERSION}-${RELEASE}-${BUILDID}"
									
  WriteRegStr HKLM 	"Software\Microsoft\Windows\CurrentVersion\Uninstall\${APP_FULL_NAME} ${VERSION}-${RELEASE}" \
  									"URLInfoAbout" "http://www.together.at/services"
  WriteRegStr HKLM 	"Software\Microsoft\Windows\CurrentVersion\Uninstall\${APP_FULL_NAME} ${VERSION}-${RELEASE}" \
  									"URLUpdateInfo" "http://www.together.at/prod/database/txm"
  WriteRegStr HKLM 	"Software\Microsoft\Windows\CurrentVersion\Uninstall\${APP_FULL_NAME} ${VERSION}-${RELEASE}" \
  									"HelpLink" "http://www.together.at/prod/database/txm"
  WriteRegStr HKLM 	"Software\Microsoft\Windows\CurrentVersion\Uninstall\${APP_FULL_NAME} ${VERSION}-${RELEASE}" \
  									"StartMenuFolder" "$STARTMENU_FOLDER"																	
 ; WriteUninstaller "uninstall.exe"
  end:
 ;---------------------------------
 
Push "set JAVA_HOME=$JAVAHOME" ;text to write to file 
Push "$INSTDIR\javapath.cmd" ;file to write to 
Call WriteToFile

 # Make the directory "$INSTDIR" read write accessible by all users
 AccessControl::GrantOnFile "$INSTDIR" "(S-1-1-0)" "FullAccess"
	
SectionEnd
;---------------------------------------------------------------------------------------
;Installer Functions
;---------------------------------------------------------------------------------------
Function .onInit
!ifdef INNER
 
  ; If INNER is defined, then we aren't supposed to do anything except write out
  ; the installer.  This is better than processing a command line option as it means
  ; this entire code path is not present in the final (real) installer.
 
  WriteUninstaller "$%TEMP%\uninstall.exe"
  Quit  ; just bail out quickly when running the "inner" installer
!endif

    ${If} ${RunningX64}
          StrCpy $INSTDIR "$PROGRAMFILES64\${SHORT_NAME}-${VERSION}-${RELEASE}"
    ${else}
          StrCpy $INSTDIR "$PROGRAMFILES\${SHORT_NAME}-${VERSION}-${RELEASE}"
    ${EndIf}
	StrCpy $DefaultDir $INSTDIR

  ;Extract Install Options INI Files
  !insertmacro MUI_INSTALLOPTIONS_EXTRACT "javapage.ini"
  !insertmacro MUI_INSTALLOPTIONS_EXTRACT "set-shortcuts.ini"

  StrCpy $JAVAHOME ""

; Read default browser path
  ReadRegStr $R9 HKCR "HTTP\shell\open\command" ""
	
  Push $R9 ;original string
  Push '"' ;needs to be replaced
  Push '' ;will replace wrong characters
  Call StrReplace
  
  Push "-"
  Call GetFirstPartRest
  Pop $DEFAULT_BROWSER
    #------- seting silent installation -----------------#
  IfFileExists $EXEDIR\txm-${VERSION}-${RELEASE}.silent.properties silent normal
  silent:
  SetSilent silent
  StrCpy $SILENT "YES"
  StrCpy $STARTMENU_FOLDER "${APP_FULL_NAME} ${VERSION}-${RELEASE}" 
  Goto start_silent_initialization
  normal:
  SetSilent normal
  StrCpy $SILENT "NO"
  Goto start_initialization
  
  start_silent_initialization:
  IfFileExists $EXEDIR\txm-${VERSION}-${RELEASE}.silent.properties "" continue
  continue:
  FileOpen $9 $EXEDIR\txm-${VERSION}-${RELEASE}.silent.properties r
  
  loop:
    FileRead $9 $8 
    IfErrors error_handle
    Push $8
    Call TrimNewlines
    Push "="
    Call GetFirstPartRest
    Pop $R0 ;1st part 
    Pop $R1 ;rest 
    StrCmp $R0 "jdk.dir" setjdkdir
    StrCmp $R0 "inst.dir" setinstdir
	StrCmp $R0 "startup.menu.name" setapplicationname
	StrCmp $R0 "create.quick.launch.icon" setquicklaunchicon
	StrCmp $R0 "create.start.menu.entry" setstartmenuentry
	StrCmp $R0 "create.desktop.icon" setdesktopicon


  Goto loop
  setjdkdir:
	IfFileExists $R1\bin\javaw.exe followJDK recallJava
  recallJava:
        call GetJavaVersion
		Pop $R1
  followJDK:
		StrCpy $JAVAHOME $R1
    Goto loop
	setinstdir:
	StrCmp $R1 "" setDefInstdir setNewInstdir
	setDefInstdir:
		Goto loop
	setNewInstdir:
		StrCpy $INSTDIR $R1
		Goto loop
	setapplicationname:
  	StrCmp $R1 "" setDefAppname setAppname
	setDefAppname:
		StrCpy $STARTMENU_FOLDER "${APP_FULL_NAME} ${VERSION}-${RELEASE}"
		Goto loop
	setAppname:
		StrCpy $STARTMENU_FOLDER $R1
		Goto loop
	setquicklaunchicon:
	    StrCpy $CREATE_QUICK_LAUNCH_ICON $R1
		Goto loop	
	setstartmenuentry:
		StrCpy $CREATE_STARTUP_MENU $R1
		Goto loop	
	setdesktopicon:
		StrCpy $CREATE_DESKTOP_ICON $R1
		Goto loop	

  error_handle:
	  Goto loopend
  loopend:
    FileClose $9
  end:
  
#------- seting silent installation -----------------#
  start_initialization:
  IfSilent end_splash_screen
  
  normal_default:
  StrCpy $ADD_STARTMENU   '1'
  StrCpy $ENABLE_DESKTOP   'off'
  StrCpy $ADD_DESKTOP   '1'
  StrCpy $ENABLE_QUICKLAUNCH  'off'
  StrCpy $ADD_QUICKLAUNCH  '0'
  
# start splash screen
# the plugins dir is automatically deleted when the installer exits
		InitPluginsDir
		File /oname=$PLUGINSDIR\splash.bmp "Splash.bmp"
#optional
#File /oname=$PLUGINSDIR\splash.wav "C:\myprog\sound.wav"
		advsplash::show 1000 600 400 -1 $PLUGINSDIR\splash
		Pop $0 ; $0 has '1' if the user closed the splash screen early,
		                ; '0' if everything closed normal, and '-1' if some error occured.
# end splash screen
end_splash_screen:
  
FunctionEnd
;---------------------------------------------------------------------------------
 ; ConvertOptionToDigit
 ; input, top of stack  (e.g. whatever$\r$\n)
 ; output, top of stack (replaces, with e.g. whatever)
 ; modifies "on" to 1 and "off" to 2.
Function ConvertOptionToDigit
  ClearErrors
							  ; Stack: <value>
  Exch $0                     ; Stack: $0
  ;$0=value
  StrCmp $0 "on" setOne setZero
  setOne:
  StrCpy $0 '1'
  Goto endset
  setZero:
  StrCpy $0 '0'
  endset:
  Exch $0
FunctionEnd

;---------------------------------------------------------------------------------
 ; TrimNewlines
 ; input, top of stack  (e.g. whatever$\r$\n)
 ; output, top of stack (replaces, with e.g. whatever)
 ; modifies no other variables.

 Function TrimNewlines
   Exch $R0
   Push $R1
   Push $R2
   StrCpy $R1 0
 
 loop:
   IntOp $R1 $R1 - 1
   StrCpy $R2 $R0 1 $R1
   StrCmp $R2 "$\r" loop
   StrCmp $R2 "$\n" loop
   IntOp $R1 $R1 + 1
   IntCmp $R1 0 no_trim_needed
   StrCpy $R0 $R0 $R1
 
 no_trim_needed:
   Pop $R2
   Pop $R1
   Exch $R0
 FunctionEnd

;---------------------------------------------------------------------------------------
;Descriptions
;---------------------------------------------------------------------------------------
!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES
;---------------------------------------------------------------------------------------
;Uninstaller Section
;---------------------------------------------------------------------------------------
!ifdef INNER
Section "Uninstall"

SetShellVarContext all


  ; remove registry keys
  DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APP_FULL_NAME} ${VERSION}-${RELEASE}"
  DeleteRegKey HKLM "SOFTWARE\${APP_FULL_NAME} ${VERSION}-${RELEASE}"

  ; MUST REMOVE UNINSTALLER, too
  Delete $INSTDIR\uninstall.exe

  RMDir /r "$INSTDIR"

  ; remove shortcuts, if any.
  Delete "$SMPROGRAMS\$STARTMENU_FOLDER\$(ABBREVIATION) Homepage.lnk"
  Delete "$SMPROGRAMS\$STARTMENU_FOLDER\$(ABBREVIATION) ${VERSION}-${RELEASE}.lnk"
  Delete "$SMPROGRAMS\$STARTMENU_FOLDER\$(ABBREVIATION) Documentation\$(ABBREVIATION) Manual HTML.lnk"
  Delete "$SMPROGRAMS\$STARTMENU_FOLDER\$(ABBREVIATION) Documentation\$(ABBREVIATION) Manual PDF.lnk"
  Delete "$SMPROGRAMS\$STARTMENU_FOLDER\Uninstall.lnk"

  Delete "$DESKTOP\$(ABBREVIATION) ${VERSION}-${RELEASE}.lnk"
  Delete "$QUICKLAUNCH\$(ABBREVIATION) ${VERSION}-${RELEASE}.lnk"

  RMDir "$SMPROGRAMS\$STARTMENU_FOLDER\$(ABBREVIATION) Documentation"
  RMDir "$SMPROGRAMS\$STARTMENU_FOLDER"

  ;Delete empty start menu parent diretories
  StrCpy $MUI_TEMP "$SMPROGRAMS\$MUI_TEMP"

  startMenuDeleteLoop:
    RMDir $MUI_TEMP
    GetFullPathName $MUI_TEMP "$MUI_TEMP\.."

    IfErrors startMenuDeleteLoopDone

    StrCmp $MUI_TEMP $SMPROGRAMS startMenuDeleteLoopDone startMenuDeleteLoop
  startMenuDeleteLoopDone:

  ; remove registry keys
  DeleteRegKey HKCU "Software\${APP_FULL_NAME} ${VERSION}-${RELEASE}"
  DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APP_FULL_NAME} ${VERSION}-${RELEASE}"
  DeleteRegKey HKLM "Software\${APP_FULL_NAME} ${VERSION}-${RELEASE}"
  ;------------ Finding AppID
  Push $0
  ReadRegStr $0 HKLM "Software\Classes\AppID\${ProgID}" "AppID"
  StrCpy $AppID "$0"
  Pop $0
  
  ;------------- Delete AppID
  DeleteRegKey HKCR "Applications\${ProgID}"
  DeleteRegKey HKCU "Software\Microsoft\Windows\CurrentVersion\App paths\${ProgID}"    
  DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\App paths\${ProgID}" 
  DeleteRegKey HKLM "Software\Classes\AppID\${ProgID}" 
  DeleteRegKey HKLM "Software\Classes\AppID\$AppID"
  ${If} ${RunningX64}
  DeleteRegKey HKLM "Software\Classes\Wow6432Node\AppID\${ProgID}"
  DeleteRegKey HKLM "Software\Classes\Wow6432Node\AppID\$AppID"
  ${EndIf}
SectionEnd
!endif
;---------------------------------------------------------------------------------------
;Uninstaller Functions
;---------------------------------------------------------------------------------------
Function un.onInit
  Push $0 
  Push $1
  
   ${If} ${RunningX64}
    SetRegView 64
   ${EndIf}
   
    ClearErrors
	${GetParameters} $0 ; read options
	${GetOptions} "$0" "/SILENT" $1 ; check the option by non-case sensitive
	IfErrors init_cmd_normal ; if error, the option not found
	SetSilent silent
	
	init_cmd_normal:

  ;Get language from registry
  ReadRegStr $LANGUAGE HKCU "Software\${APP_FULL_NAME}" "Installer Language"
  ReadRegStr $STARTMENU_FOLDER HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APP_FULL_NAME} ${VERSION}-${RELEASE}" "StartMenuFolder"
  Pop $1
  Pop $0

FunctionEnd
;------------------------------------------------------------------------------
; Call on installation failed
;------------------------------------------------------------------------------
Function .onInstFailed
	Call Cleanup
FunctionEnd
;------------------------------------------------------------------------------
; Call when installation failed or when uninstall started
;------------------------------------------------------------------------------
Function Cleanup
 SetShellVarContext all
  ; remove registry keys
  DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APP_FULL_NAME} ${VERSION}-${RELEASE}"
  DeleteRegKey HKLM "SOFTWARE\${APP_FULL_NAME} ${VERSION}-${RELEASE}"

  ; MUST REMOVE UNINSTALLER, too
  Delete $INSTDIR\uninstall.exe

  RMDir /r "$INSTDIR"

  ; remove shortcuts, if any.
  Delete "$SMPROGRAMS\$STARTMENU_FOLDER\$(ABBREVIATION) Documentation.lnk"
  Delete "$SMPROGRAMS\$STARTMENU_FOLDER\$(ABBREVIATION) ${VERSION}-${RELEASE}.lnk"
  Delete "$SMPROGRAMS\$STARTMENU_FOLDER\Uninstall.lnk"
  
  Delete "$DESKTOP\$(ABBREVIATION) ${VERSION}-${RELEASE}.lnk"
  Delete "$QUICKLAUNCH\$(ABBREVIATION) ${VERSION}-${RELEASE}.lnk"

  RMDir "$SMPROGRAMS\$STARTMENU_FOLDER"

  ;Delete empty start menu parent diretories
  StrCpy $MUI_TEMP "$SMPROGRAMS\$MUI_TEMP"

  startMenuDeleteLoop:
    RMDir $MUI_TEMP
    GetFullPathName $MUI_TEMP "$MUI_TEMP\.."

    IfErrors startMenuDeleteLoopDone

    StrCmp $MUI_TEMP $SMPROGRAMS startMenuDeleteLoopDone startMenuDeleteLoop
  startMenuDeleteLoopDone:

  ; remove registry keys
  DeleteRegKey HKCU "Software\${APP_FULL_NAME} ${VERSION}-${RELEASE}"
  DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APP_FULL_NAME} ${VERSION}-${RELEASE}"
  DeleteRegKey HKLM "Software\${APP_FULL_NAME} ${VERSION}-${RELEASE}"

FunctionEnd
;-------------------------------------------------------------------------------
; input, top of stack = string for replace string to search for
;        top of stack-1 = string to search for
;        top of stack-2 = string to search in
; output, top of stack string to search in replaces with the string for replace
; modifies no other variables.
;
; Usage:
;   Push "Start @JAVA_DIR@\bin\java.exe"
;   Push "@JAVA_DIR@"
;   Push "C:\j2sdk1.4.0"
;   Call StrReplace
;   Pop $R0
;  ($R0 at this point is "Start C:\j2sdk1.4.0\bin\java.exe")
;-------------------------------------------------------------------------------
Function StrReplace
  Exch $0 ;this will replace wrong characters
  Exch
  Exch $1 ;needs to be replaced
  Exch
  Exch 2
  Exch $2 ;the orginal string
  Push $3 ;counter
  Push $4 ;temp character
  Push $5 ;temp string
  Push $6 ;length of string that need to be replaced
  Push $7 ;length of string that will replace
  Push $R0 ;tempstring
  Push $R1 ;tempstring
  Push $R2 ;tempstring
  StrCpy $3 "-1"
  StrCpy $5 ""
  StrLen $6 $1
  StrLen $7 $0
  Loop:
 IntOp $3 $3 + 1
  StrCpy $4 $2 $6 $3
  StrCmp $4 "" ExitLoop
  StrCmp $4 $1 Replace
  Goto Loop
  Replace:
  StrCpy $R0 $2 $3
  IntOp $R2 $3 + $6
  StrCpy $R1 $2 "" $R2
  StrCpy $2 $R0$0$R1
  IntOp $3 $3 + $7
  Goto Loop
  ExitLoop:
  StrCpy $0 $2
  Pop $R2
  Pop $R1
  Pop $R0
  Pop $7
  Pop $6
  Pop $5
  Pop $4
  Pop $3
  Pop $2
  Pop $1
  Exch $0
FunctionEnd
;------------------------------------------------------------------------------
; ReplaceChar
; input, input string  (C:\JBuilder\jdk1.3.1)
;        char to need replaced (\)
;        char to with replaced (/)
; output, top of stack (C:/JBuilder/jdk1.3.1)
; modifies no other variables.
;
; Usage:
;   Push $1 ; "C:\JBuilder\jdk1.3.1"
;   Push "\"
;   Push "/"
;   Call ReplaceChar
;   Pop $0
;   ; at this point $0 will equal "C:/JBuilder/jdk1.3.1"
;------------------------------------------------------------------------------
Function ReplaceChar
					; stack="/","\","C:\JBuilder\jdk1.3.1", ... (from top to down)
  Exch $2 ; $2="/", stack="2-gi param","\","C:\JBuilder\jdk1.3.1", ...
  Exch  	; stack="\","2-ti param","C:\JBuilder\jdk1.3.1", ...
  Exch $1 ; $1="\", stack="1-vi param","2-gi param","C:\JBuilder\jdk1.3.1", ...
  Exch    ; stack="2-gi param","1-vi param","C:\JBuilder\jdk1.3.1", ...
  Exch 2	; stack="C:\JBuilder\jdk1.3.1","1-vi param","2-gi param", ...
  Exch $0 ; $0="C:\JBuilder\jdk1.3.1", stack="0-ti param","1-vi param","2-gi param", ...
  Exch 2  ; stack="2-gi param","1-vi param","0-ti param", ...

  Push $3	; Len("C:\JBuilder\jdk1.3.1")
  Push $4	; index of string
  Push $5 ; tmp - form strat to found char witch need to change
  Push $6 ; tmp - from found char witch need to change + 1 to end ($3)
  Push $7 ; tmp - position where found char witch need to change + 1
  StrLen $3 $0
  StrCpy $4 1
  loop:
    StrCpy $5 $0 1 $4
    StrCmp $5 "" exit
    StrCmp $5 $1 change
    IntOp $4 $4 + 1
  Goto loop
  change:
  	StrCpy $6 $4				; pozition where found char witch need to change
  	StrCpy $5 $0 $6   	; $5="C:"
  	StrCpy $5 "$5$2"   	; $5="C:/"
  	StrCpy $7 $4				; pozition where found char witch need to change
  	IntOp $7 $7 + 1 		; next pozition
  	StrCpy $6 $0 $3 $7  ; $6="JBuilder\jdk1.3.1"
  	StrCpy $0 $5				; $0="C:/"
  	StrCpy $0 "$0$6" 		; $0="C:/JBuilder\jdk1.3.1"
  Goto loop
  exit:
    Pop $7
    Pop $6
    Pop $5
    Pop $4
    Pop $3
    Pop $2
    Pop $1
    Exch $0 ; put $0 on top of stack, restore $0 to original value
FunctionEnd
;-----------------------------------------------------------------------
Function SetJavaPage

  start:
  SetRegView 32
  call GetJavaVersion
  Pop $JAVAHOME
  !insertmacro MUI_HEADER_TEXT "Java Configuration" "Java Development Kit"
  WriteINIStr "$PLUGINSDIR\javapage.ini" "Field 1" "Text" "Choose the version of Java virtual machine"
  WriteINIStr "$PLUGINSDIR\javapage.ini" "Field 2" "Text" "Path from JAVA_HOME environment variable"
  WriteINIStr "$PLUGINSDIR\javapage.ini" "Field 3" "Text" "Path :"
  WriteINIStr "$PLUGINSDIR\javapage.ini" "Field 5" "Text" "Choose the Java virtual machine to use"
  WriteINIStr "$PLUGINSDIR\javapage.ini" "Field 7" "Text" "Browse for folder of Java virtual machine to use"
  !insertmacro MUI_INSTALLOPTIONS_DISPLAY_RETURN "javapage.ini"
  Pop $R0

  StrCmp $R0 "cancel" end
  StrCmp $R0 "back" end
  StrCmp $R0 "success" "" error

  ; check Browse for folder
  !insertmacro MUI_INSTALLOPTIONS_READ $TEMP1 "javapage.ini" "Field 8" "State"
  StrCmp $TEMP1 "" checkdir
  Goto check

	checkdir:
  ; check JAVA_HOME environment variable & choose the directory which will be set as JAVA HOME directory
  !insertmacro MUI_INSTALLOPTIONS_READ $TEMP1 "javapage.ini" "Field 6" "State" ; read choosed java
  StrCmp $TEMP1 "" "" check
  !insertmacro MUI_INSTALLOPTIONS_READ $TEMP1 "javapage.ini" "Field 4" "State" ; read JAVA_HOME enviroment variable
  StrCmp $TEMP1 "Not Defined" "" check
	MessageBox MB_OK $(javadir_text)
  Goto start

  check:
	IfFileExists $TEMP1\bin\java.exe continue	; check if java.exe exist
	MessageBox MB_OK $(javac_not_exist)
	Goto start
	continue:
	IfFileExists '$TEMP1\jre\lib\*.*' continue1	checkJDK ; check if JDK 1.4.x
	continue1:
	IfFileExists '$TEMP1\jre\lib\jsse.jar' javaFound	; check if JDK 1.4.x
	MessageBox MB_OK $(jsse_jre_not_exist)
	Goto start
	checkJDK:
	IfFileExists '$TEMP1\lib\jsse.jar' javaFound	; check if JDK 1.4.x
	MessageBox MB_OK $(jsse_not_exist)
	Goto start
  error:
  MessageBox MB_OK|MB_ICONSTOP $(inst_opt_error)
  Goto end

  javaFound:
  StrCpy $JAVAHOME $TEMP1
  end:
FunctionEnd
;---------------------------------------------------
Function APPInstallDirectoryPage_Pre
	Push $R0
	Push $R1
	Push $R2
	Push $R3
	Push $R4
	Push $R5
	;call TimeStamp
	;Pop $R4
	StrCpy $R0 "$JAVAHOME\bin\javaw.exe"
	IfFileExists $R0 0 exit
	StrCpy $R0 '/c "$JAVAHOME\bin\javaw.exe" -version 2> $TEMP\${SHORT_NAME}-${VERSION}-${RELEASE}.txt'
	ExecShell "" "cmd.exe" $R0 SW_HIDE
	wait:
	sleep 250
	IfFileExists "$TEMP\${SHORT_NAME}-${VERSION}-${RELEASE}.txt" 0 wait
	StrCpy $R5 "0"
	Push "$TEMP\${SHORT_NAME}-${VERSION}-${RELEASE}.txt"
	Call FileSizeNew
	Pop $R5
	;messageBox MB_OK "Files size is $R5"
	StrCmp $R5 "$TEMP\${SHORT_NAME}-${VERSION}-${RELEASE}.txt" wait 0
	Push 3
	Push "$TEMP\${SHORT_NAME}-${VERSION}-${RELEASE}.txt"
	call ReadFileLine
	Pop $R1
	${WordFind} $R1 "64" "#" $R3
	${if} $R3 > 0
		StrCpy $INSTDIR "$PROGRAMFILES64\${SHORT_NAME}-${VERSION}-${RELEASE}"
		SetRegView 64
	${else}
		StrCpy $INSTDIR "$PROGRAMFILES\${SHORT_NAME}-${VERSION}-${RELEASE}"
		SetRegView 32
    ${endif}
		Delete "$TEMP\${SHORT_NAME}-${VERSION}-${RELEASE}.txt"
	;MessageBox MB_ICONEXCLAMATION|MB_OK " $R4 "
    ;MessageBox MB_ICONEXCLAMATION|MB_OK " $JAVAHOME AND $TEMP\${SHORT_NAME}-${VERSION}-${RELEASE} "
	;InstallDir "$PROGRAMFILES\${SHORT_NAME}-${VERSION}-${RELEASE}-test"
	exit:
	Pop $R5
	Pop $R4
	Pop $R3
	Pop $R2
	Pop $R1
	Pop $R0
FunctionEnd
;-------------------------------------
; FileSizeNew
;
; Script to find the size of a file.
; Much more efficient than the
; ExtensionDLL FileSize :-)
;
; by Hendri Adriaens
;    HendriAdriaens@hotmail.com
Function FileSizeNew 
 
  Exch $0
  Push $1
  FileOpen $1 $0 "r"
  FileSeek $1 0 END $0
  FileClose $1
  Pop $1
  Exch $0
 
FunctionEnd
;---------------------------------------------------
; WriteToFile
; Push Push "hello$\r$\n" ;text to write to file 
; Push "$INSTDIR\log.txt" ;file to write to 
Function WriteToFile
 Exch $0 ;file to write to
 Exch
 Exch $1 ;text to write
 
  FileOpen $0 $0 a #open file
   FileSeek $0 0 END #go to end
   FileWrite $0 $1 #write to file
  FileClose $0
 
 Pop $1
 Pop $0
FunctionEnd
;-------------------------------------------
;Push number ;line number to read from
;Push file.txt ;text file to read
;Call ReadFileLine
;Pop $0 ;output string (read from file.txt)
;-------------------------------------------
Function ReadFileLine
Exch $0 ;file
Exch
Exch $1 ;line number
Push $2
Push $3
 
  FileOpen $2 $0 r
 StrCpy $3 0
 
Loop:
 IntOp $3 $3 + 1
  ClearErrors
  FileRead $2 $0
  IfErrors +2
 StrCmp $3 $1 0 loop
  FileClose $2
 
Pop $3
Pop $2
Pop $1
Exch $0
FunctionEnd
;-----------------------------------------------------------------------
; GetJavaVersion 64/32 bits
Function GetJavaVersion
 Push $R0
 Push $R1
 Push $R2
 Push $R9
 Push $1
 Push $2
 Push $3
 Push $4
 Push $5
 ;No value by default
 StrCpy $1 ""
 StrCpy $2 ""
 StrCpy $3 ""
 StrCpy $4 ""
 StrCpy $5 ""

 ClearErrors
 ;**********************************************************************
 ;1.)READ JAVA_HOME from all users or Read the registry Environment  "JAVA_HOME"
 ;**********************************************************************
    ReadEnvStr $4 "JAVA_HOME"
    ${if} $4 != ""
                IfFileExists $4\bin\javaw.exe  FoundAllUserJavaHome   ; found it all user
                    FoundAllUserJavaHome:
                        WriteINIStr "$PLUGINSDIR\javapage.ini" "Field 4" "State" $4
                        WriteINIStr "$PLUGINSDIR\javapage.ini" "Field 6" "State" $4
                        WriteINIStr "$PLUGINSDIR\javapage.ini" "Field 6" "ListItems" $4
                        StrCpy $5 $4
    ${else}
                ClearErrors
                ReadRegStr $4 HKCU "Environment" "JAVA_HOME"
                ${if} $4 != ""
                    IfFileExists $4\bin\javaw.exe  FoundUserJavaHome  NotFoundUserJavaHome
                    FoundUserJavaHome:
                        WriteINIStr "$PLUGINSDIR\javapage.ini" "Field 4" "State" $4
                        WriteINIStr "$PLUGINSDIR\javapage.ini" "Field 6" "State" $4
                        WriteINIStr "$PLUGINSDIR\javapage.ini" "Field 6" "ListItems" $4
                        StrCpy $5 $4
                    NotFoundUserJavaHome:
                ${endif}   
    ${endif}
   
   ClearErrors
;**********************************************************************
;2.) Read the Sun JDK value from the registry 32bit and 64 bit
;**********************************************************************
;===========================================
; A.)Read the registry JDK 32bit
;===========================================
   ReadRegStr $2 HKLM "SOFTWARE\JavaSoft\Java Development Kit" "CurrentVersion"
   ReadRegStr $1 HKLM "SOFTWARE\JavaSoft\Java Development Kit\$2" "JavaHome"
    ${if} $1 != ""   ;found jdk 32bit.
            StrCpy $R9 0   ; count EnumRegKey from "SOFTWARE\JavaSoft\Java Development Kit"
    ${endif}
loopJDK32bit:
    EnumRegKey $R1 HKLM "SOFTWARE\JavaSoft\Java Development Kit" $R9    
    ReadRegStr $R2 HKLM "SOFTWARE\JavaSoft\Java Development Kit\$R1" "JavaHome"
    ${if} $R2 != ""
                ; check if java version >= 1.4.x
                StrCpy $1 $R1 1 ; major version
                StrCpy $2 $R1 1 2 ; minor version
                IntFmt $3 "%u" $1
                IntFmt $4 "%u" $2
                IntCmp $4 4 okJDK32bit 0 okJDK32bit
                goto incrementJDK32bit
    ${else}
       goto noSunJDK32bit
    ${endif}
   okJDK32bit:
   ; Read item from combo
   ReadINIStr $3 "$PLUGINSDIR\javapage.ini" "Field 6" "State"
   ReadINIStr $4 "$PLUGINSDIR\javapage.ini" "Field 6" "ListItems"
   ; add or append to existing value ?
   ${if} $3 == ""
            ${if} $5 == ""
                StrCpy $5 $R2
            ${endif}
            WriteINIStr "$PLUGINSDIR\javapage.ini" "Field 6" "State" $R2
            WriteINIStr "$PLUGINSDIR\javapage.ini" "Field 6" "ListItems" $R2
            goto incrementJDK32bit
    ${else}
        ; check if path exist
        Push $4
        Push $R2
        Call StrStr
        Pop $R0
        StrCmp $R0 -1 0 incrementJDK32bit
        WriteINIStr "$PLUGINSDIR\javapage.ini" "Field 6" "ListItems" $4|$R2
       
    ${endif}
    incrementJDK32bit:
        IntOp $R9 $R9 + 1
    goto loopJDK32bit
   
   noSunJDK32bit:
            ClearErrors
;===========================================
; B.)Read the registry JDK 64bit
;===========================================
${if} ${RunningX64}
            SetRegView 64
                ReadRegStr $2 HKLM "SOFTWARE\JavaSoft\Java Development Kit" "CurrentVersion"
                ReadRegStr $1 HKLM "SOFTWARE\JavaSoft\Java Development Kit\$2" "JavaHome"
                ${if} $1 != ""   ;found jdk 64bit.
                        StrCpy $R9 0   ; count EnumRegKey from "SOFTWARE\JavaSoft\Java Development Kit"
                ${endif}
             loopJDK64bit:
                SetRegView 64
                EnumRegKey $R1 HKLM "SOFTWARE\JavaSoft\Java Development Kit" $R9    
                ReadRegStr $R2 HKLM "SOFTWARE\JavaSoft\Java Development Kit\$R1" "JavaHome"
                ${if} $R2 != ""
                            ; check if java version >= 1.4.x
                            StrCpy $1 $R1 1 ; major version
                            StrCpy $2 $R1 1 2 ; minor version
                            IntFmt $3 "%u" $1
                            IntFmt $4 "%u" $2
                            IntCmp $4 4 okJDK64bit 0 okJDK64bit
                            goto incrementJDK64bit
                ${else}
                   goto noSunJDK64bit
                ${endif}
            okJDK64bit:
                ; Read item from combo
                ReadINIStr $3 "$PLUGINSDIR\javapage.ini" "Field 6" "State"
                ReadINIStr $4 "$PLUGINSDIR\javapage.ini" "Field 6" "ListItems"
                ; add or append to existing value ?
                ${if} $3 == ""
                        ${if} $5 == ""
                            StrCpy $5 $R2
                        ${endif}
                        WriteINIStr "$PLUGINSDIR\javapage.ini" "Field 6" "State" $R2
                        WriteINIStr "$PLUGINSDIR\javapage.ini" "Field 6" "ListItems" $R2
                        goto incrementJDK64bit
                ${else}
                    ; check if path exist
                    Push $4
                    Push $R2
                    Call StrStr
                    Pop $R0
                    StrCmp $R0 -1 0 incrementJDK64bit
                    WriteINIStr "$PLUGINSDIR\javapage.ini" "Field 6" "ListItems" $4|$R2
                   
                ${endif}
            incrementJDK64bit:
                     IntOp $R9 $R9 + 1
                goto loopJDK64bit
               
            noSunJDK64bit:
                        SetRegView 32
                        ClearErrors
${endif}
;**********************************************************************
;3.) Read the Sun JRE value from the registry 32bit and 64 bit
;**********************************************************************
;===========================================
; A.)Read the registry JRE 32bit
;===========================================
   ReadRegStr $2 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment" "CurrentVersion"
   ReadRegStr $1 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment\$2" "JavaHome"
   
    ${if} $1 != ""   ;found JRE 32bit.
            StrCpy $R9 0   ; count EnumRegKey from "SOFTWARE\JavaSoft\Java Development Kit"
    ${endif}
loopJRE32bit:
        EnumRegKey $R1 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment" $R9
        ReadRegStr $R2 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment\$R1" "JavaHome"    
    ${if} $R2 != ""
                ; check if java version >= 1.4.x
                StrCpy $1 $R1 1 ; major version
                StrCpy $2 $R1 1 2 ; minor version
                IntFmt $3 "%u" $1
                IntFmt $4 "%u" $2
                IntCmp $4 4 okJRE32bit 0 okJRE32bit
                goto incrementJRE32bit
    ${else}
       goto noSunJRE32bit
    ${endif}
   okJRE32bit:
   ; Read item from combo
   ReadINIStr $3 "$PLUGINSDIR\javapage.ini" "Field 6" "State"
   ReadINIStr $4 "$PLUGINSDIR\javapage.ini" "Field 6" "ListItems"
   ; add or append to existing value ?
   ${if} $3 == ""
            ${if} $5 == ""
                StrCpy $5 $R2
            ${endif}
            WriteINIStr "$PLUGINSDIR\javapage.ini" "Field 6" "State" $R2
            WriteINIStr "$PLUGINSDIR\javapage.ini" "Field 6" "ListItems" $R2
            goto incrementJRE32bit
    ${else}
        ; check if path exist
        Push $4
        Push $R2
        Call StrStr
        Pop $R0
        StrCmp $R0 -1 0 incrementJRE32bit
        WriteINIStr "$PLUGINSDIR\javapage.ini" "Field 6" "ListItems" $4|$R2
       
    ${endif}
    incrementJRE32bit:
        IntOp $R9 $R9 + 1
    goto loopJRE32bit
   
   noSunJRE32bit:
            ClearErrors
;===========================================
; B.)Read the registry JRE 64bit
;===========================================
${if} ${RunningX64}
            SetRegView 64
                ReadRegStr $2 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment" "CurrentVersion"
                ReadRegStr $1 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment\$2" "JavaHome"
                ${if} $1 != ""   ;found JRE 64bit.
                        StrCpy $R9 0   ; count EnumRegKey from "SOFTWARE\JavaSoft\Java Development Kit"
                ${endif}
             loopJRE64bit:
                SetRegView 64
                    EnumRegKey $R1 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment" $R9
                    ReadRegStr $R2 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment\$R1" "JavaHome"    
                ${if} $R2 != ""
                            ; check if java version >= 1.4.x
                            StrCpy $1 $R1 1 ; major version
                            StrCpy $2 $R1 1 2 ; minor version
                            IntFmt $3 "%u" $1
                            IntFmt $4 "%u" $2
                            IntCmp $4 4 okJRE64bit 0 okJRE64bit
                            goto incrementJRE64bit
                ${else}
                   goto noSunJRE64bit
                ${endif}
            okJRE64bit:
                ; Read item from combo
                ReadINIStr $3 "$PLUGINSDIR\javapage.ini" "Field 6" "State"
                ReadINIStr $4 "$PLUGINSDIR\javapage.ini" "Field 6" "ListItems"
                ; add or append to existing value ?
                ${if} $3 == ""
                        ${if} $5 == ""
                            StrCpy $5 $R2
                        ${endif}
                        WriteINIStr "$PLUGINSDIR\javapage.ini" "Field 6" "State" $R2
                        WriteINIStr "$PLUGINSDIR\javapage.ini" "Field 6" "ListItems" $R2
                        goto incrementJRE64bit
                ${else}
                    ; check if path exist
                    Push $4
                    Push $R2
                    Call StrStr
                    Pop $R0
                    StrCmp $R0 -1 0 incrementJRE64bit
                    WriteINIStr "$PLUGINSDIR\javapage.ini" "Field 6" "ListItems" $4|$R2
                   
                ${endif}
            incrementJRE64bit:
                     IntOp $R9 $R9 + 1
                goto loopJRE64bit
               
            noSunJRE64bit:
                        SetRegView 32
                        ClearErrors
${endif}
;**********************************************************************
 Exch  
 Pop $4
 Exch
 Pop $3
 Exch
 Pop $2
 Exch
 Pop $1
 Exch
 Pop $R9
 Exch
 Pop $R2
 Exch
 Pop $R1
 Exch
 Pop $R0
 Exch $5
 
FunctionEnd
;------------------------------------------------------------------------------
Function SetShortcuts

  Push $R0
  !insertmacro MUI_HEADER_TEXT "Choose Start Options" "Select from the following start options."

  !insertmacro MUI_INSTALLOPTIONS_WRITE "set-shortcuts.ini" "Field 1" "State" "${APP_FULL_NAME} ${VERSION}-${RELEASE}"
  
  !insertmacro MUI_INSTALLOPTIONS_DISPLAY_RETURN "set-shortcuts.ini"
	Pop $R0
  StrCmp $R0 "cancel" end
  StrCmp $R0 "back" end
  StrCmp $R0 "success" "" error
  Goto end

  error:
  MessageBox MB_OK|MB_ICONSTOP "Shortcuts Error: $R0$\r$\n"
  Goto end

  end:
  Pop $R0
  !insertmacro MUI_INSTALLOPTIONS_WRITE "ioSpecial.ini" "Settings" "BackEnabled" "0"
FunctionEnd
;------------------------------------------------------------------------------
Function CheckShortcuts

  Push $R0
  Push $R1
  
  
  !insertmacro MUI_INSTALLOPTIONS_READ $STARTMENU_FOLDER "set-shortcuts.ini" "Field 1" "State"
  
  SetOutPath $INSTDIR
  !insertmacro MUI_INSTALLOPTIONS_READ $R0 "set-shortcuts.ini" "Field 2" "State"
   StrCpy $ADD_STARTMENU $R0		 
  
  !insertmacro MUI_INSTALLOPTIONS_READ $R0 "set-shortcuts.ini" "Field 3" "State"
  StrCpy $ADD_QUICKLAUNCH $R0
				
  !insertmacro MUI_INSTALLOPTIONS_READ $R0 "set-shortcuts.ini" "Field 4" "State"
  StrCpy $ADD_DESKTOP $R0
                 
  Pop $R1
  Pop $R0
FunctionEnd
;====================================================
; StrStr - Finds a given string in another given string.
;               Returns -1 if not found and the pos if found.
;          Input: head of the stack - string to find
;                      second in the stack - string to find in
;          Output: head of the stack
;====================================================
Function StrStr
  Push $0
  Exch
  Pop $0 ; $0 now have the string to find
  Push $1
  Exch 2
  Pop $1 ; $1 now have the string to find in
  Exch
  Push $2
  Push $3
  Push $4
  Push $5

  StrCpy $2 -1
  StrLen $3 $0
  StrLen $4 $1
  IntOp $4 $4 - $3

  unStrStr_loop:
    IntOp $2 $2 + 1
    IntCmp $2 $4 0 0 unStrStrReturn_notFound
    StrCpy $5 $1 $3 $2
    StrCmp $5 $0 unStrStr_done unStrStr_loop

  unStrStrReturn_notFound:
    StrCpy $2 -1

  unStrStr_done:
    Pop $5
    Pop $4
    Pop $3
    Exch $2
    Exch 2
    Pop $0
    Pop $1
FunctionEnd
;----------------------------------------------      
; input:
; Push "HelloAll I am Afrow UK, and I love NSIS" ; Input string 
; Push " " ; search string 
; Call GetFirstStrPart 
; output:
; Pop "$R0" ; first part
; Pop "$R1" ; rest of string
;----------------------------------------------      
Function GetFirstPartRest
  Exch $R1  ; search string
#  Dumpstate::debug
  Exch
#  Dumpstate::debug
  Exch $R0   ; Input string
#  Dumpstate::debug
  Exch
  Push $R2
  Push $R3
  Push $R4
  StrCpy $R4 $R0
  StrLen $R2 $R0
  IntOp $R2 $R2 + 1
#  Dumpstate::debug
  loop:
#     MessageBox MB_OK "loop:-1"
#     Dumpstate::debug
    IntOp $R2 $R2 - 1
#   StrCpy user_var(destination) str [maxlen] [start_offset]
    StrCpy $R3 $R0 1 -$R2
#     MessageBox MB_OK "loop:-2"
#     Dumpstate::debug
    StrCmp $R2 0 exit0
    StrCmp $R3 $R1 exit1 loop ; check if find (go to exit1 otherwise loop)

  exit0:
#  MessageBox MB_OK "exit0"
#  Dumpstate::debug
#  StrCpy $R2 ""
  StrCpy $R1 ""
  StrCpy $R0 ""
  Goto exit2

  exit1:
#  MessageBox MB_OK "exit1"
#  Dumpstate::debug
    IntOp $R2 $R2 - 1
    StrCpy $R3 $R0 "" -$R2
    IntOp $R2 $R2 + 1
    StrCpy $R0 $R0 -$R2
#  MessageBox MB_OK "exit1-1"
#  Dumpstate::debug
     StrLen $R2 $R0
    IntOp $R2 $R2 + 1
    StrCpy $R3 $R4 "" $R2
#  MessageBox MB_OK "exit1-2"
#  Dumpstate::debug
    StrCpy $R1 $R3

  exit2:
#  MessageBox MB_OK "exit2"
#  Dumpstate::debug
    Pop $R4
    Pop $R3
    Pop $R2
#  Dumpstate::debug
#    Exch  
    Exch $R1
    Exch
#  Dumpstate::debug
    Exch $R0
#  Dumpstate::debug
FunctionEnd
;------------------------------------------------------------------------------
Function RunApplication
  ;Exec '"$JAVAHOME\bin\javaw.exe" -Xmx800m -jar $\"$INSTDIR\lib\txm-launcher.jar$\"'
FunctionEnd

;----------------------------------------------------------------------------------
;Call CreateGUID
;Pop $0 ;contains GUID
Function CreateGUID
  ; GUID has 128 bit = 16 byte = 32 hex characters
  Push $R0
  Push $R1
  Push $R2
  Push $R3
  Push $R4
  ;allocate space for character array
  System::Alloc 16
  ;get pointer to new space
  Pop $R1
  StrCpy $R0 "" ; init
  ;call the CoCreateGuid api in the ole32.dll
  System::Call 'ole32::CoCreateGuid(i R1) i .R2'
  ;if 0 then continue
  IntCmp $R2 0 continue 
  ; set error flag
  SetErrors
  goto done
continue:
  ;byte counter = 0
  StrCpy $R3 0
loop:
    System::Call "*$R1(&v$R3, &i1 .R2)"
    ;now $R2 is byte at offset $R3
    ;convert to hex
    IntFmt $R4 "%X" $R2
    StrCpy $R4 "00$R4"
    StrLen $R2 $R4
    IntOp $R2 $R2 - 2
    StrCpy $R4 $R4 2 $R2
    ;append to result
    StrCpy $R0 "$R0$R4"
    ;increment byte counter
    IntOp $R3 $R3 + 1
    ;if less than 16 then continue
    IntCmp $R3 16 0 loop
done:
  ;cleanup
  System::Free $R1
  ; BEGIN format GUID
  StrCpy $R1 "$R0"
  StrCpy $R0 "{"
  StrCpy $R2 $R1 8
  StrCpy $R0 "$R0$R2-"
  StrCpy $R2 $R1 4 8
  StrCpy $R0 "$R0$R2-"
  StrCpy $R2 $R1 4 12
  StrCpy $R0 "$R0$R2-"
  StrCpy $R2 $R1 4 16
  StrCpy $R0 "$R0$R2-"
  StrCpy $R2 $R1 12 20
  StrCpy $R0 "$R0$R2}"
  ; END format GUID
  Pop $R4
  Pop $R3
  Pop $R2
  Pop $R1
  Exch $R0
FunctionEnd