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
public class ActualParameters extends XMLCollection {

   /**
    * Constructs a new object with the given {@link Tool} as a parent. This constructor is
    * used only when reading XPDL 1 files.
    */
   public ActualParameters(Tool parent) {
      super(parent, false);
   }

   /** Constructs a new object with the given {@link SubFlow} as a parent. */
   public ActualParameters(SubFlow parent) {
      super(parent, false);
   }

   /** Constructs a new object with the given {@link TaskApplication} as a parent. */
   public ActualParameters(TaskApplication parent) {
      super(parent, false);
   }

   /** Constructs a new object with the given {@link PassingTypes} as a parent. */
   public ActualParameters(PassingTypes parent) {
      super(parent, false);
   }

   /**
    * Generates new {@link ActualParameter} object. This object is not member of the
    * collection yet, it has to be explicitly added to the collection.
    */
   public XMLElement generateNewElement() {
      return new ActualParameter(this);
   }

}
