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
public class BasicType extends XMLComplexElement {

   /**
    * Constructs a new object with the given DataTypes as a parent.
    */
   public BasicType(DataTypes parent) {
      super(parent, true);
   }

   protected void fillStructure() {
      // must be one of following: STRING, FLOAT, INTEGER, REFERENCE, DATETIME,
      // BOOLEAN OR PERFORMER
      // required
      XMLAttribute attrType = new XMLAttribute(this, "Type", true, new String[] {
            XPDLConstants.BASIC_TYPE_STRING,
            XPDLConstants.BASIC_TYPE_FLOAT,
            XPDLConstants.BASIC_TYPE_INTEGER,
            XPDLConstants.BASIC_TYPE_REFERENCE,
            XPDLConstants.BASIC_TYPE_DATETIME,
            XPDLConstants.BASIC_TYPE_DATE,
            XPDLConstants.BASIC_TYPE_TIME,
            XPDLConstants.BASIC_TYPE_BOOLEAN,
            XPDLConstants.BASIC_TYPE_PERFORMER
      }, 0);

      add(attrType);
   }

   /** Returns the Type attribute of this object. */
   public XMLAttribute getTypeAttribute() {
      return (XMLAttribute) get("Type");
   }

   /** Returns the Type attribute value of this object. */
   public String getType() {
      return getTypeAttribute().toValue();
   }

   /** Sets the Type attribute value of this object to STRING. */
   public void setTypeSTRING() {
      getTypeAttribute().setValue(XPDLConstants.BASIC_TYPE_STRING);
   }

   /** Sets the Type attribute value of this object to FLOAT. */
   public void setTypeFLOAT() {
      getTypeAttribute().setValue(XPDLConstants.BASIC_TYPE_FLOAT);
   }

   /** Sets the Type attribute value of this object to INTEGER. */
   public void setTypeINTEGER() {
      getTypeAttribute().setValue(XPDLConstants.BASIC_TYPE_INTEGER);
   }

   /** Sets the Type attribute value of this object to REFERENCE. */
   public void setTypeREFERENCE() {
      getTypeAttribute().setValue(XPDLConstants.BASIC_TYPE_REFERENCE);
   }

   /** Sets the Type attribute value of this object to DATETIME. */
   public void setTypeDATETIME() {
      getTypeAttribute().setValue(XPDLConstants.BASIC_TYPE_DATETIME);
   }

   /** Sets the Type attribute value of this object to DATE. */
   public void setTypeDATE() {
      getTypeAttribute().setValue(XPDLConstants.BASIC_TYPE_DATE);
   }

   /** Sets the Type attribute value of this object to TIME. */
   public void setTypeTIME() {
      getTypeAttribute().setValue(XPDLConstants.BASIC_TYPE_TIME);
   }

   /** Sets the Type attribute value of this object to BOOLEAN. */
   public void setTypeBOOLEAN() {
      getTypeAttribute().setValue(XPDLConstants.BASIC_TYPE_BOOLEAN);
   }

   /** Sets the Type attribute value of this object to PERFORMER. */
   public void setTypePERFORMER() {
      getTypeAttribute().setValue(XPDLConstants.BASIC_TYPE_PERFORMER);
   }

}
