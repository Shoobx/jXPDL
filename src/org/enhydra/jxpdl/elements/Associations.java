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
 */
public class Associations extends XMLCollection {

   /** Constructs a new object with the given Package as a parent. */
   public Associations(Package parent) {
      super(parent, false);
   }

   /**
    * Generates new Association object. This object is not member of the collection yet,
    * it has to be explicitly added to the collection.
    */
   public XMLElement generateNewElement() {
      return new Association(this);
   }

   /**
    * Returns the Association object (the member of this Associations collection) with
    * specified Id.
    */
   public Association getAssociation(String Id) {
      return (Association) super.getCollectionElement(Id);
   }

}