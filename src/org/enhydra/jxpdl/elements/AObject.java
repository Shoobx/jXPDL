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
 *  Represents corresponding element from XPDL schema.
 *
 *  @author Sasa Bojanic
 */
public class AObject extends XMLComplexElement {

   public AObject (Association parent) {
      super(parent, "Object", true);
   }

   protected void fillStructure () {
      XMLAttribute attrId=new XMLAttribute(this,"Id", true);
      XMLAttribute attrName=new XMLAttribute(this,"Name", true); // required

      attrId.setValue("0");
      add(attrId);
      add(attrName);
   }

   public String getId() {
      return get("Id").toValue();
   }
   public void setId(String Id) {
      set("Id",Id);
   }
   public String getName() {
      return get("Name").toValue();
   }
   public void setName(String name) {
      set("Name",name);
   }

}
