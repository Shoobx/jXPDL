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

package org.enhydra.jxpdl;

/**
 * Helper class to allow an empty choice that will not write anything to XML.
 * 
 * @author Sasa Bojanic
 */
public final class XMLEmptyChoiceElement extends XMLComplexElement {

   /**
    * Creates a new instance of element: sets <code>parent</code> property to specified
    * one.
    * @param parent The parent element.
    */
   public XMLEmptyChoiceElement(XMLElement parent) {
      super(parent, false);
   }

   protected void fillStructure() {
   }

   public void setValue(String val) {
      this.value = val;
   }

   // public String toValue() {
   // return "";
   // }

   public String toName() {
      return "";
   }
}
