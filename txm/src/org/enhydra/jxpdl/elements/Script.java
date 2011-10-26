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
public class Script extends XMLComplexElement {

   /**
    * Constructs a new object with the given Package as a parent.
    */
   public Script(Package parent) {
      super(parent, false);
   }

   protected void fillStructure() {
      XMLAttribute attrType = new XMLAttribute(this, "Type", true); // required
      XMLAttribute attrVersion = new XMLAttribute(this, "Version", false);
      XMLAttribute attrGrammar = new XMLAttribute(this, "Grammar", false);

      add(attrType);
      add(attrVersion);
      add(attrGrammar);
   }

   /** Returns the Grammar attribute value of this object. */
   public String getGrammar() {
      return get("Grammar").toValue();
   }

   /** Sets the Grammar attribute value of this object. */
   public void setGrammar(String grammar) {
      set("Grammar", grammar);
   }

   /** Returns the Type attribute value of this object. */
   public String getType() {
      return get("Type").toValue();
   }

   /** Sets the Type attribute value of this object. */
   public void setType(String type) {
      set("Type", type);
   }

   /** Returns the Version attribute value of this object. */
   public String getVersion() {
      return get("Version").toValue();
   }

   /** Sets the Version attribute value of this object. */
   public void setVersion(String version) {
      set("Version", version);
   }
}
