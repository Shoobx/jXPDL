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
 * Represents corresponding element from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class Lane extends XMLCollectionElement {

   /**
    * Constructs a new object with the given Lanes as a parent.
    */
   public Lane(Lanes parent) {
      super(parent, true);
   }

   protected void fillStructure() {
      XMLAttribute attrName = new XMLAttribute(this, "Name", false);
      NodeGraphicsInfos refNodeGraphicsInfos = new NodeGraphicsInfos(this); // min=0
      Performers refPerformers = new Performers(this);
      NestedLanes refNestedLanes = new NestedLanes(this);

      super.fillStructure();
      add(attrName);
      add(refNodeGraphicsInfos);
      add(refPerformers);
      add(refNestedLanes);
   }

   /** Returns the Name attribute value of this object. */
   public String getName() {
      return get("Name").toValue();
   }

   /** Sets the Name attribute value of this object. */
   public void setName(String name) {
      set("Name", name);
   }

   /** Returns the NodeGraphicsInfos sub-element of this object. */
   public NodeGraphicsInfos getNodeGraphicsInfos() {
      return (NodeGraphicsInfos) get("NodeGraphicsInfos");
   }

   /** Returns the Performers sub-element of this object. */
   public Performers getPerformers() {
      return (Performers) get("Performers");
   }

   /** Returns the NestedLanes sub-element of this object. */
   public NestedLanes getNestedLanes() {
      return (NestedLanes) get("NestedLanes");
   }

   public String toString () {
      return getName();
   }
}
