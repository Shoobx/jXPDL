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

import org.enhydra.jxpdl.XMLCollection;
import org.enhydra.jxpdl.XMLElement;

/**
 * Represents corresponding element from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class Activities extends XMLCollection {

   /** Constructs a new object with the given WorkflowProcess as a parent. */
   public Activities(WorkflowProcess parent) {
      super(parent, false);
   }

   /** Constructs a new object with the given ActivitySet as a parent. */
   public Activities(ActivitySet parent) {
      super(parent, false);
   }

   /**
    * Generates new Activity object. This object is not member of the collection yet, it
    * has to be explicitly added to the collection.
    */
   public XMLElement generateNewElement() {
      return new Activity(this, false);
   }

   /**
    * Generates new Activity object with XPDL 1 support. This object is not member of the
    * collection yet, it has to be explicitly added to the collection.
    */
   public XMLElement generateNewElementWithXPDL1Support() {
      return new Activity(this, true);
   }

   /**
    * Returns the Activity object (the member of this Activities collection) with
    * specified Id.
    */
   public Activity getActivity(String Id) {
      return (Activity) super.getCollectionElement(Id);
   }

}
