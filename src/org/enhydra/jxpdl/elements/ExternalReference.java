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


/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class ExternalReference extends XMLComplexElement {

   public ExternalReference (XMLElement parent, boolean isRequired) {
      super(parent, isRequired);
      //isRequired=true;
   }

   protected void fillStructure () {
      XMLAttribute attrXref=new XMLAttribute(this,"xref", false); // optional
      XMLAttribute attrLocation=new XMLAttribute(this,"location", true); // required
      XMLAttribute attrNamespace=new XMLAttribute(this,"namespace", false); // optional

      add(attrXref);
      add(attrLocation);
      add(attrNamespace);
   }

   public String getLocation() {
      return get("location").toValue();
   }
   public void setLocation(String location) {
      set("location",location);
   }
   public String getNamespace() {
      return get("namespace").toValue();
   }
   public void setNamespace(String namespace) {
      set("namespace",namespace);
   }
   public String getXref() {
      return get("xref").toValue();
   }
   public void setXref(String xref) {
      set("xref",xref);
   }
}
