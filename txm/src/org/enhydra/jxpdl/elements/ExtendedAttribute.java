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

/**
 * Represents corresponding element from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class ExtendedAttribute extends XMLComplexElement {

   /**
    * Constructs a new object with the given ExtendedAttributes as a parent.
    */
   public ExtendedAttribute(ExtendedAttributes parent) {
      super(parent, true);
   }

   protected void fillStructure() {
      XMLAttribute attrName = new XMLAttribute(this, "Name", true); // required
      XMLAttribute attrValue = new XMLAttribute(this, "Value", false);

      add(attrName);
      add(attrValue);
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

      this.value = v;

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

   /** Returns the Name attribute value of this object. */
   public String getName() {
      return get("Name").toValue();
   }

   /** Sets the Name attribute value of this object. */
   public void setName(String name) {
      set("Name", name);
   }

   /** Returns the Value attribute value of this object. */
   public String getVValue() {
      return get("Value").toValue();
   }

   /** Returns the Value attribute value of this object. */
   public void setVValue(String value) {
      set("Value", value);
   }

}
