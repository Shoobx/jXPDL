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
 * Helper class for storing all the Deadline elements.
 * 
 * @author Sasa Bojanic
 */
public class Deadlines extends XMLCollection {

   // min=0, max=unbounded
   public Deadlines(Activity parent, boolean xpdl1support) {
      super(parent, false, xpdl1support);
   }

   public XMLElement generateNewElement() {
      return new Deadline(this, false);
   }

   public XMLElement generateNewElementWithXPDL1Support() {
      return new Deadline(this, true);
   }

}
