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
 * Represents corresponding element from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class Tool extends XMLCollectionElement {

   /**
    * Constructs a new object with the given Tools as a parent.
    */
   public Tool(Tools parent) {
      super(parent, true);
   }

   protected void fillStructure() {
      XMLAttribute attrType = new XMLAttribute(this, "Type", false, new String[] {
            XPDLConstants.TOOL_TYPE_NONE,
            XPDLConstants.TOOL_TYPE_APPLICATION,
            XPDLConstants.TOOL_TYPE_PROCEDURE
      }, 0);
      ActualParameters refActualParameters = new ActualParameters(this); // min=0
      Description refDescription = new Description(this); // min=0
      ExtendedAttributes refExtendedAttributes = new ExtendedAttributes(this); // min=0

      super.fillStructure();
      add(attrType);
      add(refActualParameters);
      add(refDescription);
      add(refExtendedAttributes);
   }

   /** Returns the Type attribute of this object. */
   public XMLAttribute getTypeAttribute() {
      return (XMLAttribute) get("Type");
   }

   /** Returns the Type attribute value of this object. */
   public String getType() {
      return getTypeAttribute().toValue();
   }

   /** Sets the Type attribute value of this object to an empty string. */
   public void setTypeNONE() {
      getTypeAttribute().setValue(XPDLConstants.TOOL_TYPE_NONE);
   }

   /** Sets the Type attribute value of this object to APPLICATION. */
   public void setTypeAPPLICATION() {
      getTypeAttribute().setValue(XPDLConstants.TOOL_TYPE_APPLICATION);
   }

   /** Sets the Type attribute value of this object to PROCEDURE. */
   public void setTypePROCEDURE() {
      getTypeAttribute().setValue(XPDLConstants.TOOL_TYPE_PROCEDURE);
   }

   /** Returns the Description attribute value of this object. */
   public String getDescription() {
      return get("Description").toValue();
   }

   /** Sets the Description attribute value of this object. */
   public void setDescription(String description) {
      set("Description", description);
   }

   /** Returns the ActualParameters sub-element of this object. */
   public ActualParameters getActualParameters() {
      return (ActualParameters) get("ActualParameters");
   }

   /** Returns the ExtendedAttributes sub-element of this object. */
   public ExtendedAttributes getExtendedAttributes() {
      return (ExtendedAttributes) get("ExtendedAttributes");
   }
}
