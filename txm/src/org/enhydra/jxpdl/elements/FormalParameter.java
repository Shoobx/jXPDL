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
public class FormalParameter extends XMLCollectionElement {

   public FormalParameter(FormalParameters fps) {
      super(fps, true);
   }

   protected void fillStructure() {
      DataType refDataType = new DataType(this);
      InitialValue refInitialValue = new InitialValue(this); // min=0
      Length refLength = new Length(this); // min=0
      Description refDescription = new Description(this); // min=0
      // default="IN"
      XMLAttribute attrMode = new XMLAttribute(this, "Mode", true, new String[] {
            XPDLConstants.FORMAL_PARAMETER_MODE_IN,
            XPDLConstants.FORMAL_PARAMETER_MODE_OUT,
            XPDLConstants.FORMAL_PARAMETER_MODE_INOUT
      }, 0);
      XMLAttribute attrName = new XMLAttribute(this, "Name", false);
      // default="FALSE"
      XMLAttribute attrIsArray = new XMLAttribute(this, "IsArray", false, new String[] {
            XPDLConstants.XSD_BOOLEAN_TRUE, XPDLConstants.XSD_BOOLEAN_FALSE
      }, 1);

      super.fillStructure();
      add(attrMode);
      add(attrName);
      add(attrIsArray);
      add(refDataType);
      add(refInitialValue);
      add(refDescription);
      add(refLength);
   }

   public XMLAttribute getIsArrayAttribute() {
      return (XMLAttribute) get("IsArray");
   }

   public boolean getIsArray() {
      return new Boolean(get("IsArray").toValue()).booleanValue();
   }

   public void setIsArray(boolean isArray) {
      set("IsArray", String.valueOf(isArray));
   }

   public String getName() {
      return get("Name").toValue();
   }

   public void setName(String name) {
      set("Name", name);
   }

   public DataType getDataType() {
      return (DataType) get("DataType");
   }

   public String getDescription() {
      return get("Description").toValue();
   }

   public void setDescription(String description) {
      set("Description", description);
   }

   public String getInitialValue() {
      return get("InitialValue").toValue();
   }

   public void setInitialValue(String initialValue) {
      set("InitialValue", initialValue);
   }

   public String getLength() {
      return get("Length").toValue();
   }

   public void setLength(String length) {
      set("Length", length);
   }

   public XMLAttribute getModeAttribute() {
      return (XMLAttribute) get("Mode");
   }

   public String getMode() {
      return getModeAttribute().toValue();
   }

   public void setModeIN() {
      getModeAttribute().setValue(XPDLConstants.FORMAL_PARAMETER_MODE_IN);
   }

   public void setModeOUT() {
      getModeAttribute().setValue(XPDLConstants.FORMAL_PARAMETER_MODE_OUT);
   }

   public void setModeINOUT() {
      getModeAttribute().setValue(XPDLConstants.FORMAL_PARAMETER_MODE_INOUT);
   }

}
