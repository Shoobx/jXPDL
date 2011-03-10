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
public class BlockActivity extends XMLComplexElement {

   /**
    * Constructs a new object with the given ActivityTypes as a parent.
    */
   public BlockActivity(ActivityTypes parent) {
      super(parent, true);
   }

   /**
    * Constructs a new object with the given WebServiceFaultCatchTypes as a parent.
    */
   public BlockActivity(WebServiceFaultCatchTypes parent) {
      super(parent, true);
   }

   protected void fillStructure() {
      XMLAttribute attrActivitySetId = new XMLAttribute(this, "ActivitySetId", true); // required

      add(attrActivitySetId);
   }

   /** Returns the ActivitySetId attribute value of this object. */
   public String getActivitySetId() {
      return get("ActivitySetId").toValue();
   }

   /** Sets the ActivitySetId attribute value of this object. */
   public void setActivitySetId(String activitySetId) {
      set("ActivitySetId", activitySetId);
   }

   // MIGRATION FROM XPDL1
   public XMLElement get(String name) {
      if ("BlockId".equals(name)) {
         name = "ActivitySetId";
      }
      return super.get(name);
   }

}
