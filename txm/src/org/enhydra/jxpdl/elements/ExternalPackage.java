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
public class ExternalPackage extends XMLComplexElement {

   /** Constructs a new object with the given ExternalPackages as a parent. */
   public ExternalPackage(ExternalPackages parent) {
      super(parent, true);
   }

   protected void fillStructure() {
      XMLAttribute attrHref = new XMLAttribute(this, "href", true);
      XMLAttribute attrId = new XMLAttribute(this, "Id", true);
      XMLAttribute attrName = new XMLAttribute(this, "Name", false);
      ExtendedAttributes refExtendedAttributes = new ExtendedAttributes(this); // min=0

      add(attrHref);
      add(attrId);
      add(attrName);
      add(refExtendedAttributes);
   }

   /** Returns the href attribute value of this object. */
   public String getHref() {
      return get("href").toValue();
   }

   /** Sets the href attribute value of this object. */
   public void setHref(String href) {
      set("href", href);
   }

   /** Returns the Id attribute value of this object. */
   public String getId() {
      return get("Id").toValue();
   }

   /** Sets the Id attribute value of this object. */
   public void setId(String id) {
      set("Id", id);
   }

   /** Returns the Name attribute value of this object. */
   public String getName() {
      return get("Name").toValue();
   }

   /** Sets the Name attribute value of this object. */
   public void setName(String name) {
      set("Name", name);
   }

   /** Returns the ExtendedAttributes sub-element of this object. */
   public ExtendedAttributes getExtendedAttributes() {
      return (ExtendedAttributes) get("ExtendedAttributes");
   }
}
