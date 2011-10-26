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

/**
 * Represents corresponding element from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class Role extends XMLComplexElement {

   /**
    * Constructs a new object with the given Roles as a parent.
    */
   public Role(Roles parent) {
      super(parent, true);
   }

   protected void fillStructure() {

      XMLAttribute attrPortType = new XMLAttribute(this, "portType", true);
      XMLAttribute attrName = new XMLAttribute(this, "Name", true);

      add(attrPortType);
      add(attrName);
   }

   /** Returns the Name attribute value of this object. */
   public String getName() {
      return get("Name").toValue();
   }

   /** Sets the Name attribute value of this object. */
   public void setName(String name) {
      set("Name", name);
   }

   /** Returns the portType attribute value of this object. */
   public String getPortType() {
      return get("portType").toValue();
   }

   /** Sets the portType attribute value of this object. */
   public void setPortType(String portType) {
      set("portType", portType);
   }
}
