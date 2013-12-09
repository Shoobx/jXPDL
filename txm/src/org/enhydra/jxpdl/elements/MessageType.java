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
 * Represents corresponding element from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public abstract class MessageType extends XMLComplexElement {

   /**
    * Constructs a new object with the given element as a parent, and flag that specifies
    * if this element is required in the given context.
    */
   public MessageType(XMLElement parent, boolean isRequired) {
      super(parent, isRequired);
   }

   /**
    * Constructs a new object with the given element as a parent, specified name, and flag
    * that specifies if this element is required in the given context.
    */
   public MessageType(XMLElement parent, String name, boolean isRequired) {
      super(parent, name, isRequired);
   }

   protected void fillStructure() {
      XMLAttribute attrId = new XMLAttribute(this, "Id", true); // required
      XMLAttribute attrName = new XMLAttribute(this, "Name", false);
      XMLAttribute attrFrom = new XMLAttribute(this, "From", false);
      XMLAttribute attrTo = new XMLAttribute(this, "To", false);
      XMLAttribute attrFaultName = new XMLAttribute(this, "FaultName", false);

      PassingTypes refPassingTypes = new PassingTypes(this);

      add(attrId);
      add(attrName);
      add(attrFrom);
      add(attrTo);
      add(attrFaultName);

      add(refPassingTypes);

   }

   /** Returns the PassingTypes attribute of this object. */
   public PassingTypes getPassingTypes() {
      return (PassingTypes) get("Choice");
   }

   /** Returns the Id attribute value of this object. */
   public final String getId() {
      return get("Id").toValue();
   }

   /** Sets the Id attribute value of this object. */
   public final void setId(String id) {
      set("Id", id);
   }

   /** Returns the Name attribute value of this object. */
   public final String getName() {
      return get("Name").toValue();
   }

   /** Sets the Name attribute value of this object. */
   public void setName(String name) {
      set("Name", name);
   }

   /** Returns the From attribute value of this object. */
   public final String getFrom() {
      return get("From").toValue();
   }

   /** Sets the From attribute value of this object. */
   public void setFrom(String from) {
      set("From", from);
   }

   /** Returns the To attribute value of this object. */
   public final String getTo() {
      return get("To").toValue();
   }

   /** Sets the To attribute value of this object. */
   public void setTo(String to) {
      set("To", to);
   }
}
