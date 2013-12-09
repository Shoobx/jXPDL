/**
 * Together XPDL Model
 * Copyright (C) 2011 Together Teamsolutions Co., Ltd. 
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

package org.enhydra.jxpdl.elements;

import org.enhydra.jxpdl.XMLAttribute;
import org.enhydra.jxpdl.XMLComplexElement;
import org.enhydra.jxpdl.XPDLConstants;

/**
 * Represents corresponding element from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class RedefinableHeader extends XMLComplexElement {

   /**
    * Constructs a new object with the given Package as a parent.
    */
   public RedefinableHeader(Package parent) {
      super(parent, false);
   }

   /**
    * Constructs a new object with the given WorkflowProcess as a parent.
    */
   public RedefinableHeader(WorkflowProcess parent) {
      super(parent, false);
   }

   protected void fillStructure() {
      XMLAttribute attrPublicationStatus = new XMLAttribute(this,
                                                            "PublicationStatus",
                                                            false,
                                                            new String[] {
                                                                  XPDLConstants.PUBLICATION_STATUS_NONE,
                                                                  XPDLConstants.PUBLICATION_STATUS_UNDER_REVISION,
                                                                  XPDLConstants.PUBLICATION_STATUS_RELEASED,
                                                                  XPDLConstants.PUBLICATION_STATUS_UNDER_TEST
                                                            },
                                                            0);
      Author refAuthor = new Author(this); // min=0
      Version refVersion = new Version(this); // min=0
      Codepage refCodepage = new Codepage(this); // min=0
      Countrykey refCountrykey = new Countrykey(this); // min=0
      Responsibles refResponsibles = new Responsibles(this); // min=0

      add(attrPublicationStatus);
      add(refAuthor);
      add(refVersion);
      add(refCodepage);
      add(refCountrykey);
      add(refResponsibles);
   }

   /** Returns the PublicationStatus attribute of this object. */
   public XMLAttribute getPublicationStatusAttribute() {
      return (XMLAttribute) get("PublicationStatus");
   }

   /** Returns the PublicationStatus attribute value of this object. */
   public String getPublicationStatus() {
      return getPublicationStatusAttribute().toValue();
   }

   /** Sets the PublicationStatus attribute value of this object to an empty string. */
   public void setPublicationStatusNONE() {
      getPublicationStatusAttribute().setValue(XPDLConstants.PUBLICATION_STATUS_NONE);
   }

   /** Sets the PublicationStatus attribute value of this object to UNDER_REVISION. */
   public void setPublicationStatusUNDER_REVISION() {
      getPublicationStatusAttribute().setValue(XPDLConstants.PUBLICATION_STATUS_UNDER_REVISION);
   }

   /** Sets the PublicationStatus attribute value of this object to RELEASED. */
   public void setPublicationStatusRELEASED() {
      getPublicationStatusAttribute().setValue(XPDLConstants.PUBLICATION_STATUS_RELEASED);
   }

   /** Sets the PublicationStatus attribute value of this object to UNDER_TEST. */
   public void setPublicationStatusUNDER_TEST() {
      getPublicationStatusAttribute().setValue(XPDLConstants.PUBLICATION_STATUS_UNDER_TEST);
   }

   /** Returns the Author attribute value of this object. */
   public String getAuthor() {
      return get("Author").toValue();
   }

   /** Sets the Author attribute value of this object. */
   public void setAuthor(String author) {
      set("Author", author);
   }

   /** Returns the Version attribute value of this object. */
   public String getVersion() {
      return get("Version").toValue();
   }

   /** Sets the Version attribute value of this object. */
   public void setVersion(String version) {
      set("Version", version);
   }

   /** Returns the Codepage attribute value of this object. */
   public String getCodepage() {
      return get("Codepage").toValue();
   }

   /** Sets the Codepage attribute value of this object. */
   public void setCodepage(String codepage) {
      set("Codepage", codepage);
   }

   /** Returns the CountryKey attribute value of this object. */
   public String getCountrykey() {
      return get("Countrykey").toValue();
   }

   /** Sets the CountryKey attribute value of this object. */
   public void setCountrykey(String countrykey) {
      set("Countrykey", countrykey);
   }

   /** Returns the Responsibles sub-element of this object. */
   public Responsibles getResponsibles() {
      return (Responsibles) get("Responsibles");
   }

}
