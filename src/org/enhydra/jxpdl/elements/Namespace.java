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
 *  Helper class to properly write namespaces in XML.
 * 
 *  @author Sasa Bojanic
 */
public class Namespace extends XMLComplexElement {

   public Namespace (Namespaces parent) {
      super(parent, true);
   }

   protected void fillStructure () {
      XMLAttribute attrName=new XMLAttribute(this,"Name", true); // required
      XMLAttribute attrLocation=new XMLAttribute(this,"location", true); //required

      add(attrName);
      add(attrLocation);
   }

   public String getName() {
      return get("Name").toValue();
   }
   public void setName(String name) {
      set("Name",name);
   }
   public String getLocation() {
      return get("location").toValue();
   }
   public void setLocation(String location) {
      set("location",location);
   }
   
}
