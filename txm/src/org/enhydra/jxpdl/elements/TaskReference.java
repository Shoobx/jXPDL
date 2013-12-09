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
 * @author Sasa Bojanic
 */
public class TaskReference extends XMLComplexElement {

   public TaskReference(TaskTypes parent) {
      super(parent, true);
   }

   protected void fillStructure() {
      XMLAttribute attrTaskRef = new XMLAttribute(this, "TaskRef", true);

      add(attrTaskRef);
   }

   public String getTaskRef() {
      return get("TaskRef").toValue();
   }

   public void setTaskRef(String refId) {
      set("TaskRef", refId);
   }

}
