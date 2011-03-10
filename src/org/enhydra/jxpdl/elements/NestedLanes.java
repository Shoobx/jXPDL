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

import java.util.Iterator;

import org.enhydra.jxpdl.XMLCollection;
import org.enhydra.jxpdl.XMLElement;

/**
 * Helper class for storing all the NestedLane elements.
 * 
 * @author Sasa Bojanic
 */
public class NestedLanes extends XMLCollection {

   /** Constructs a new object with the given Lane as a parent. */
   public NestedLanes(Lane parent) {
      super(parent, false);
   }

   /**
    * Generates new NestedLane object. This object is not member of the collection yet, it
    * has to be explicitly added to the collection.
    */
   public XMLElement generateNewElement() {
      return new NestedLane(this);
   }

   /**
    * Returns the NestedLane object (the member of this NestedLanes collection) with
    * specified Id.
    */
   public NestedLane getNestedLane(String Id) {
      Iterator it = elements.iterator();
      while (it.hasNext()) {
         NestedLane ce = (NestedLane) it.next();
         if (ce.getLaneId().equals(Id)) {
            return ce;
         }
      }
      return null;
   }

}
