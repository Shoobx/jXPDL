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

package org.enhydra.jxpdl.elements;

import org.enhydra.jxpdl.XMLAttribute;
import org.enhydra.jxpdl.XMLComplexElement;
import org.enhydra.jxpdl.XPDLConstants;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class RedefinableHeader extends XMLComplexElement {

   public RedefinableHeader (Package parent) {
      super(parent, false);
   }

   public RedefinableHeader (WorkflowProcess parent) {
      super(parent, false);
   }

   protected void fillStructure () {
      XMLAttribute attrPublicationStatus=new XMLAttribute(this,"PublicationStatus",
            false,new String[] {
               XPDLConstants.PUBLICATION_STATUS_NONE,
               XPDLConstants.PUBLICATION_STATUS_UNDER_REVISION,
               XPDLConstants.PUBLICATION_STATUS_RELEASED,
               XPDLConstants.PUBLICATION_STATUS_UNDER_TEST
            }, 0);
      Author refAuthor=new Author(this); // min=0
      Version refVersion=new Version(this); // min=0
      Codepage refCodepage=new Codepage(this); // min=0
      Countrykey refCountrykey=new Countrykey(this); // min=0
      Responsibles refResponsibles=new Responsibles(this); // min=0

      add(attrPublicationStatus);
      add(refAuthor);
      add(refVersion);
      add(refCodepage);
      add(refCountrykey);
      add(refResponsibles);
   }

   public XMLAttribute getPublicationStatusAttribute() {
      return (XMLAttribute)get("PublicationStatus");
   }
   public String getPublicationStatus() {
      return getPublicationStatusAttribute().toValue();
   }
   public void setPublicationStatusNONE() {
      getPublicationStatusAttribute().setValue(XPDLConstants.PUBLICATION_STATUS_NONE);
   }
   public void setPublicationStatusUNDER_REVISION() {
      getPublicationStatusAttribute().setValue(XPDLConstants.PUBLICATION_STATUS_UNDER_REVISION);
   }
   public void setPublicationStatusRELEASED() {
      getPublicationStatusAttribute().setValue(XPDLConstants.PUBLICATION_STATUS_RELEASED);
   }
   public void setPublicationStatusUNDER_TEST() {
      getPublicationStatusAttribute().setValue(XPDLConstants.PUBLICATION_STATUS_UNDER_TEST);
   }
   public String getAuthor() {
      return get("Author").toValue();
   }
   public void setAuthor(String author) {
      set("Author",author);
   }
   public String getVersion() {
      return get("Version").toValue();
   }
   public void setVersion(String version) {
      set("Version",version);
   }
   public String getCodepage() {
      return get("Codepage").toValue();
   }
   public void setCodepage(String codepage) {
      set("Codepage",codepage);
   }
   public String getCountrykey() {
      return get("Countrykey").toValue();
   }
   public void setCountrykey(String countrykey) {
      set("Countrykey",countrykey);
   }
   public Responsibles getResponsibles() {
      return (Responsibles)get("Responsibles");
   }
   
}
