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
import org.enhydra.jxpdl.XMLCollectionElement;

/**
 *  Represents corresponding element from XPDL schema.
 *
 *  @author Sasa Bojanic
 */
public class Application extends XMLCollectionElement {

   /** Constructs a new Application object with the given collection as a parent. */
   public Application(Applications aps) {
      super(aps, true);
   }

   protected void fillStructure() {
      Description refDescription = new Description(this); // min=0
      // can be FormalParameters or ExternalReference
      // if fp->must be defined,if er->min=0
      ApplicationTypes refChoice = new ApplicationTypes(this);
      ExtendedAttributes refExtendedAttributes = new ExtendedAttributes(this); // min=0
      XMLAttribute attrName = new XMLAttribute(this, "Name", false);

      super.fillStructure();
      //attrName.setRequired(true);
      add(attrName);
      add(refDescription);
      add(refChoice);
      add(refExtendedAttributes);
   }

   /** Returns the Name attribute value of this object. */
   public String getName() {
      return get("Name").toValue();
   }
   /** Sets the Name attribute value of this object. */
   public void setName(String name) {
      set("Name",name);
   }
   /** Returns the Description attribute value of this object. */
   public String getDescription() {
      return get("Description").toValue();
   }
   /** Sets the Description attribute value of this object. */
   public void setDescription(String description) {
      set("Description",description);
   }
   /** Returns the ApplicationTypes sub-element of this object. */
   public ApplicationTypes getApplicationTypes() {
      return (ApplicationTypes)get("Choice");
   }
   /** Returns the ExtendedAttributes sub-element of this object. */
   public ExtendedAttributes getExtendedAttributes() {
      return (ExtendedAttributes)get("ExtendedAttributes");
   }
}