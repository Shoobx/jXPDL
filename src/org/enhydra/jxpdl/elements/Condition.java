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
import org.enhydra.jxpdl.XMLElement;
import org.enhydra.jxpdl.XMLElementChangeInfo;
import org.enhydra.jxpdl.XPDLConstants;

/**
 * Represents corresponding element from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class Condition extends XMLComplexElement {

   /**
    * Constructs a new object with the given Transition as a parent.
    */
   public Condition(Transition parent) {
      super(parent, false);
   }

   protected void fillStructure() {
      XMLAttribute attrType = new XMLAttribute(this, "Type", false, new String[] {
            XPDLConstants.CONDITION_TYPE_NONE,
            XPDLConstants.CONDITION_TYPE_CONDITION,
            XPDLConstants.CONDITION_TYPE_OTHERWISE,
            XPDLConstants.CONDITION_TYPE_EXCEPTION,
            XPDLConstants.CONDITION_TYPE_DEFAULTEXCEPTION
      }, 0);
      Expression refExpression = new Expression(this);
      
      add(attrType);
      add(refExpression);
   }

   public void makeAs(XMLElement el) {
      super.makeAs(el);
      setValue(el.toValue());
   }

   public void setValue(String v) {
      if (isReadOnly) {
         throw new RuntimeException("Can't set the value of read only element!");
      }
      boolean notify = false;
      String oldValue = value;
      if (!this.value.equals(v)) {
         notify = true;
      }

      this.value = v.trim();

      if (notify && (notifyMainListeners || notifyListeners)) {
         XMLElementChangeInfo info = createInfo(oldValue,
                                                value,
                                                null,
                                                XMLElementChangeInfo.UPDATED);
         if (notifyListeners) {
            notifyListeners(info);
         }
         if (notifyMainListeners) {
            notifyMainListeners(info);
         }
      }
   }

   /** Returns the Expression element of this object. */
   public Expression getExpresssion() {
      return (Expression) get("Expression");
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
      getTypeAttribute().setValue(XPDLConstants.CONDITION_TYPE_NONE);
   }

   /** Sets the Type attribute value of this object to CONDITION. */
   public void setTypeCONDITION() {
      getTypeAttribute().setValue(XPDLConstants.CONDITION_TYPE_CONDITION);
   }

   /** Sets the Type attribute value of this object to OTHERWISE. */
   public void setTypeOTHERWISE() {
      getTypeAttribute().setValue(XPDLConstants.CONDITION_TYPE_OTHERWISE);
   }

   /** Sets the Type attribute value of this object to EXCEPTION. */
   public void setTypeEXCEPTION() {
      getTypeAttribute().setValue(XPDLConstants.CONDITION_TYPE_EXCEPTION);
   }

   /** Sets the Type attribute value of this object to DEFAULTEXCEPTION. */
   public void setTypeDEFAULTEXCEPTION() {
      getTypeAttribute().setValue(XPDLConstants.CONDITION_TYPE_DEFAULTEXCEPTION);
   }

}
