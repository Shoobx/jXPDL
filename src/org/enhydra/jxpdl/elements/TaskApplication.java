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
public class TaskApplication extends XMLComplexElement {

   /**
    * Constructs a new object with the given TaskTypes as a parent.
    */
   public TaskApplication(TaskTypes parent) {
      super(parent, true);
   }

   protected void fillStructure() {
      XMLAttribute attrId = new XMLAttribute(this, "Id", true); // required
      XMLAttribute attrName = new XMLAttribute(this, "Name", false);
      XMLAttribute attrPackageRef = new XMLAttribute(this, "PackageRef", false);

//      PassingTypes refPassingTypes = new PassingTypes(this);
      ActualParameters refActualParameters = new ActualParameters(this);
      Description refDescription = new Description(this); // min=0

      add(attrId);
      add(attrName);
      add(attrPackageRef);
      add(refActualParameters);
//      add(refPassingTypes);
      add(refDescription);
   }

   /** Returns the ActualParameters sub-element of this object. */
   public ActualParameters getActualParameters () {
      return (ActualParameters)get("ActualParameters");
   }
//   public PassingTypes getPassingTypes() {
//      return (PassingTypes) get("Choice");
//   }

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

   /** Returns the PackageRef attribute value of this object. */
   public String getPackageRef() {
      return get("PackageRef").toValue();
   }

   /** Sets the PackageRef attribute value of this object. */
   public void setPackageRef(String packageRef) {
      set("PackageRef", packageRef);
   }

   /** Returns the Description attribute value of this object. */
   public String getDescription() {
      return get("Description").toValue();
   }

   /** Sets the Description attribute value of this object. */
   public void setDescription(String description) {
      set("Description", description);
   }

}
