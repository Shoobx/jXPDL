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
 * Interface that defines methods for handling XPDL's XML model in a sense of parsing an
 * XML document into the Java objects, as well as for holding the information about all
 * the Java object Package elements currently used, removing existing ones and adding the
 * new ones.
 * 
 * @author Sasa Bojanic
 */
public interface XMLInterface {

   /**
    * Sets the validation flag. If 'true' XML Schema validation of the package that is
    * being opened will be performed.
    * 
    * @param isActive true if schema validation will be performed.
    */
   void setValidation(boolean isActive);

   /**
    * Clears the parser error messages.
    */
   public void clearParserErrorMessages();

   /**
    * Returns true if package with given Id is already opened.
    * 
    * @param pkgId The Id of opened package.
    */
   public boolean isPackageOpened(String pkgId);

   /**
    * Returns the first package element with the given Id.
    * 
    * @param pkgId Package Id.
    * @return The first package element with the given Id.
    */
   public Package getPackageById(String pkgId);

   /**
    * Returns the package element with given Id and Version.
    * 
    * @param pkgId Package Id.
    * @param version Package Version.
    * @return The first package element with given Id and Version.
    */
   public Package getPackageByIdAndVersion(String pkgId, String version);

   /**
    * Returns the package element that corresponds to the given file name.
    * 
    * @param filename Name of the file.
    * @return The package element that corresponds to the given file name.
    */
   public Package getPackageByFilename(String filename);

   /**
    * Returns the external Package that corresponds to the given path relative to the
    * given root package.
    * 
    * @param relativePathToExtPkg The relative path to the Package on filesystem.
    * @param rootPkg The Package which is referencing external package we search.
    * @return The Package that corresponds to the given path relative to the given root
    *         Package.
    */
   public Package getExternalPackageByRelativeFilePath(String relativePathToExtPkg,
                                                       Package rootPkg);

   /**
    * Returns absolute file-system path to the file parsed to create given package.
    * 
    * @param pkg The package.
    * @return An absolute file-system path of the file parsed to create given package.
    */
   public String getAbsoluteFilePath(Package pkg);

   /**
    * Returns the collection of all the opened Package elements.
    * @return The collection of all the opened Package elements.
    */
   public Collection getAllPackages();

   /**
    * Returns the collection of Ids of all the opened package elements.
    * @return The collection of Ids of all the opened package elements.
    */
   public Collection getAllPackageIds();

   /**
    * Returns the collection of the Package elementf of all the versions for the package with the given Id.
    * @param pkgId Id of Package
    * @return The collection of the Package elementf of all the versions for the package with the given Id.
    */
   public Collection getAllPackageVersions(String pkgId);

   /**
    * Returns the collection with file names of all the files used to create the opened
    * packages.
    * @return The collection with file names of all the files used to create the opened
    * packages.
    */
   public Collection getAllPackageFilenames();

   /**
    * Returns true if there is a file that corresponds to the given path.
    * @param xmlFile The path to the file.
    * @return true if there is a file that corresponds to the given path.
    */
   public boolean doesPackageFileExists(String xmlFile);

   /**
    * Returns the location of the parent folder of the file used to create the package.
    * @param pkg The Package element.
    * @return The location of the parent folder of the file used to create the package.
    */
   public String getParentDirectory(Package pkg);

   /**
    * Creates a package element by parsing it from the file specified by the given
    * location. Flag 'handleExternalPackages' determines if the external packages will
    * also be processed.
    * @param pkgReference The file reference to the Package file.
    * @param handleExternalPackages true if external Packages should be handled.
    * @return  The Package element.
    */
   public Package openPackage(String pkgReference, boolean handleExternalPackages);

   /**
    * Creates a package elements by parsing it from the list of byte[] stream representing
    * either an XML content (if 'isFileStream' flag is true) or serialized Package object.
    * Returns the main package (the first from the list).
    * @param pkgContents The list of the byte[] representation's of Package element.
    * @param isFileStream true if stream represents the XML file content.
    * @return The Package element.
    */
   public Package openPackagesFromStreams(List pkgContents, boolean isFileStream)
      throws Exception;

   /**
    * Creates a package element by parsing it from the byte[] stream representing either
    * an XML content (if 'isFileStream' flag is true) or serialized Package object.
    * Returns the package object parsed from this stream.
    * @param pkgContent The stream content of the Package.
    * @param isFileStream true if stream represents the XML file content.
    * @return The Package element.
    */
   public Package openPackageFromStream(byte[] pkgContent, boolean isFileStream)
      throws Exception;

   /**
    * Creates a package element. If 'isFile' flag is true, 'toParse' represents the
    * location of the XML file on the file-system, otherwise it represents the XML content
    * itself.
    * @param toParse The location of the file or XML content
    * @param isFile true if string represents the location to file
    * @return The Package element. 
    */
   public Package parseDocument(String toParse, boolean isFile);

   /**
    * This method should be called immediately after opening a document, otherwise,
    * messages could be invalid.
    * 
    * @return The map which keys are opened packages, and values are the sets of errors
    *         for corresponding package.
    */
   public Map getParsingErrorMessages();

   /**
    * Closes all the packages with given Id. Returns the list of closed package elements.
    * @param pkgId The package Id.
    * @return The list of the Package elements.
    */
   public List closePackages(String pkgId);

   /**
    * Closes the package with given Id and version. Returns the closed package element.
    * @param pkgId Id of the Package
    * @param version Version of the Package
    * @return The Package element.
    */
   public Package closePackageVersion(String pkgId, String version);

   /**
    * Closes all the opened packages.
    */
   public void closeAllPackages();

   /**
    * Synchronizes opened package cache with another instance of this interface.
    * @param xmlInterface The instance of XMLInterface.
    */
   public void synchronizePackages(XMLInterface xmlInterface);

   /**
    * Sets the Locale for the parser when handling XML file representing the package.
    * @param locale The locale.
    */
   public void setLocale(Locale locale);
}
