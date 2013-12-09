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
import org.enhydra.jxpdl.XMLCollectionElement;
import org.enhydra.jxpdl.XPDLConstants;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class Tool extends XMLCollectionElement {

   public Tool (Tools parent){
      super(parent, true);
   }

   protected void fillStructure () {
      XMLAttribute attrType=new XMLAttribute(this,"Type",
            false,new String[] {
               XPDLConstants.TOOL_TYPE_NONE,
               XPDLConstants.TOOL_TYPE_APPLICATION,
               XPDLConstants.TOOL_TYPE_PROCEDURE
            }, 0);
      ActualParameters refActualParameters=new ActualParameters(this); // min=0
      Description refDescription=new Description(this); // min=0
      ExtendedAttributes refExtendedAttributes=new ExtendedAttributes(this); // min=0

      super.fillStructure();
      add(attrType);
      add(refActualParameters);
      add(refDescription);      
      add(refExtendedAttributes);      
   }

   public XMLAttribute getTypeAttribute() {
      return (XMLAttribute)get("Type");
   }   
   public String getType () {
      return getTypeAttribute().toValue();
   }
   public void setTypeNONE () {
      getTypeAttribute().setValue(XPDLConstants.TOOL_TYPE_NONE);      
   }
   public void setTypeAPPLICATION () {
      getTypeAttribute().setValue(XPDLConstants.TOOL_TYPE_APPLICATION);
   }
   public void setTypePROCEDURE () {
      getTypeAttribute().setValue(XPDLConstants.TOOL_TYPE_PROCEDURE);      
   }
   public String getDescription() {
      return get("Description").toValue();
   }
   public void setDescription(String description) {
      set("Description",description);
   }
   public ActualParameters getActualParameters() {
      return (ActualParameters)get("ActualParameters");
   }
   public ExtendedAttributes getExtendedAttributes() {
      return (ExtendedAttributes)get("ExtendedAttributes");
   }
}
