/**
* Together XPDL Model
* Copyright (C) 2010 Together Teamsolutions Co., Ltd. 
* 
* This program is free software: you can redistribute it and/or modify 
* it under the terms of the GNU General Public License as published by 
* the Free Software Foundation, either version 3 of the License, or 
* (at your option) any later version. 
*
* This program is distributed in the hope that it will be useful, 
* but WITHOUT ANY WARRANTY; without even the implied warranty of 
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
* GNU General Public License for more details. 
*
* You should have received a copy of the GNU General Public License 
* along with this program. If not, see http://www.gnu.org/licenses
*/

package org.enhydra.jxpdl;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.enhydra.jxpdl.elements.Package;

/**
* Class which purpose is to provide methods which are
* used by classes that represents program apstraction of
* XML elements. These methods offers support for reading or
* writting an XML document.
 *
 *  @author Sasa Bojanic
 */
public interface XMLInterface {

   void setValidation (boolean isActive);

   public void clearParserErrorMessages ();

   public boolean isPackageOpened (String pkgId);

   public Package getPackageById (String pkgId);

   public Package getPackageByIdAndVersion (String pkgId,String version);

   public Package getPackageByFilename (String filename);

   public Package getExternalPackageByRelativeFilePath (
   String relativePathToExtPkg,Package rootPkg);

   public String getAbsoluteFilePath (Package pkg);

   public Collection getAllPackages ();

   public Collection getAllPackageIds ();

   public Collection getAllPackageVersions (String pkgId);

   public Collection getAllPackageFilenames ();

   public boolean doesPackageFileExists (String xmlFile);

   public String getParentDirectory (Package pkg);

   public Package openPackage (String pkgReference,boolean handleExternalPackages);

   public Package openPackagesFromStreams (List pkgContents,boolean isFileStream) throws Exception;

   public Package openPackageFromStream (byte[] pkgContent,boolean isFileStream) throws Exception;

   public Package parseDocument (String toParse,boolean isFile);

   /**
    * This method should be called immediatelly after opening a document,
    * otherwise, messages could be invalid.
    * @return The map which keys are opened packages, and values are the sets
    * of errors for corresponding package.
    */
   public Map getParsingErrorMessages ();

   public List closePackages (String pkgId);

   public Package closePackageVersion (String pkgId,String version);

   public void closeAllPackages ();

   public void synchronizePackages (XMLInterface xmlInterface);
   
   public void setLocale(Locale locale);
}

