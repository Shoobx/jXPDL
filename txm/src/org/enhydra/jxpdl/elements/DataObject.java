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

/**
 * Represents corresponding element from XPDL schema.
 * 
 */
public class DataObject extends XMLComplexElement {

   public DataObject(Artifact parent) {
      super(parent, false);
   }

   protected void fillStructure() {
      XMLAttribute attrId = new XMLAttribute(this, "Id", true); // required
      XMLAttribute attrName = new XMLAttribute(this, "Name", false);
      XMLAttribute attrState = new XMLAttribute(this, "State", false);
      DODataFields refDODataFields = new DODataFields(this);
      add(attrId);
      add(attrName);
      add(attrState);
      add(refDODataFields);
   }

   public final String getId() {
      return get("Id").toValue();
   }

   public void setId(String id) {
      set("Id", id);
   }
   
   public final String getName() {
      return get("Name").toValue();
   }

   public void setName(String name) {
      set("Name", name);
   }
   
   public final String getState() {
      return get("State").toValue();
   }

   public void setState(String state) {
      set("State", state);
   }

   public DODataFields getDODataFields () {
      return (DODataFields)get("DataFields");
   }
}
