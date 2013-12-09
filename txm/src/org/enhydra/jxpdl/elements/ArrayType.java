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

/**
 * Represents corresponding element from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class ArrayType extends XMLComplexElement {

   /**
    * Constructs a new object with the given DataTypes as a parent.
    */
   public ArrayType(DataTypes parent) {
      super(parent, true);
   }

   protected void fillStructure() {
      XMLAttribute attrLowerIndex = new XMLAttribute(this, "LowerIndex", true); // required
      XMLAttribute attrUpperIndex = new XMLAttribute(this, "UpperIndex", true); // required
      DataTypes refType = new DataTypes(this);

      add(attrLowerIndex);
      add(attrUpperIndex);
      add(refType);
   }

   /** Returns the DataTypes sub-element of this object. */
   public DataTypes getDataTypes() {
      return (DataTypes) get("DataTypes");
   }

   /** Returns the LowerIndex attribute value of this object. */
   public String getLowerIndex() {
      return get("LowerIndex").toValue();
   }

   /** Sets the LowerIndex attribute value of this object. */
   public void setLowerIndex(String li) {
      set("LowerIndex", li);
   }

   /** Returns the UpperIndex attribute value of this object. */
   public String getUpperIndex() {
      return get("UpperIndex").toValue();
   }

   /** Sets the UpperIndex attribute value of this object. */
   public void setUpperIndex(String ui) {
      set("UpperIndex", ui);
   }

}
