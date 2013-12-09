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
public class BasicType extends XMLComplexElement {

   public BasicType (DataTypes parent) {
      super(parent, true);
   }

   protected void fillStructure () {
      // must be one of following: STRING, FLOAT, INTEGER, REFERENCE, DATETIME,
      // BOOLEAN OR PERFORMER
      //required
      XMLAttribute attrType=new XMLAttribute(this,"Type",
         true,new String[] {
            XPDLConstants.BASIC_TYPE_STRING,
            XPDLConstants.BASIC_TYPE_FLOAT,
            XPDLConstants.BASIC_TYPE_INTEGER,
            XPDLConstants.BASIC_TYPE_REFERENCE,
            XPDLConstants.BASIC_TYPE_DATETIME,
            XPDLConstants.BASIC_TYPE_BOOLEAN,
            XPDLConstants.BASIC_TYPE_PERFORMER
         }, 0);

      add(attrType);
   }

   public XMLAttribute getTypeAttribute() {
      return (XMLAttribute)get("Type");
   }
   
   public String getType () {
      return getTypeAttribute().toValue();
   }
   public void setTypeSTRING () {
      getTypeAttribute().setValue(XPDLConstants.BASIC_TYPE_STRING);
   }
   public void setTypeFLOAT () {
      getTypeAttribute().setValue(XPDLConstants.BASIC_TYPE_FLOAT);      
   }
   public void setTypeINTEGER () {
      getTypeAttribute().setValue(XPDLConstants.BASIC_TYPE_INTEGER);      
   }
   public void setTypeREFERENCE () {
      getTypeAttribute().setValue(XPDLConstants.BASIC_TYPE_REFERENCE);      
   }
   public void setTypeDATETIME () {
      getTypeAttribute().setValue(XPDLConstants.BASIC_TYPE_DATETIME);            
   }
   public void setTypeBOOLEAN () {
      getTypeAttribute().setValue(XPDLConstants.BASIC_TYPE_BOOLEAN);
   }
   public void setTypePERFORMER () {
      getTypeAttribute().setValue(XPDLConstants.BASIC_TYPE_PERFORMER);            
   }
      
}
