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
public class WebServiceOperation extends XMLComplexElement {

   /**
    * Constructs a new object with the given element as a parent, and flag that determines
    * if object in this context is required by XPDL schema.
    */
   public WebServiceOperation(XMLComplexElement parent,boolean isRequired) {
      super(parent, isRequired);
   }

   protected void fillStructure() {
      XMLAttribute attrOperationName = new XMLAttribute(this, "OperationName", true);
      WebServiceOperationTypes refChoice = new WebServiceOperationTypes(this);

      add(attrOperationName);
      add(refChoice);
   }

}
