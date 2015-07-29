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

package org.enhydra.jxpdl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Represents attribute element from XML schema.
 * 
 * @author Sasa Bojanic
 */
public class XMLAttribute extends XMLElement {

   /** The possible choices. */
   protected ArrayList choices;

   /** The index of default choice element. */
   protected int defaultChoiceIndex;

   /**
    * Creates a new instance of element: sets <code>name</code>, <code>parent</code>
    * <code>isRequired</code> properties to specified ones.
    * <p>
    * It also sets the value of this element to an empty String.
    * 
    * @param parent Parent element
    * @param name Name of this attribute.
    * @param isRequired true if this attribute is required by XPDL schema.
    */
   public XMLAttribute(XMLElement parent, String name, boolean isRequired) {
      super(parent, name, isRequired);
   }

   /**
    * Creates a new instance of element: sets <code>name</code>, <code>parent</code>
    * <code>isRequired</code> properties to specified ones, assignes the possible choices
    * for the value of this element and sets the one at <code>choosenIndex</code> position
    * withing the provided <code>choices</code>
    * <p>
    * It also sets the value of this element to an empty String.
    * 
    * @param parent Parent element
    * @param name Name of this attribute.
    * @param isRequired true if this attribute is required by XPDL schema.
    * @param choices A string array of possible choices for the values.
    * @param choosenIndex of current choice.
    */
   public XMLAttribute(XMLElement parent,
                       String name,
                       boolean isRequired,
                       String[] choices,
                       int choosenIndex) {
      super(parent, name, isRequired);
      this.choices = new ArrayList(Arrays.asList(choices));
      this.value = choices[choosenIndex];
      this.defaultChoiceIndex = choosenIndex;
   }

   public void setValue(String v) {
      if (choices != null) {
         if (!choices.contains(v)) {
            throw new RuntimeException("Incorrect value "
                                       + v + " for attribute " + toName()
                                       + "! Possible values are: " + choices);
         }
      }
      super.setValue(v);
   }

   /**
    * The possible String choices.
    * 
    * @return The possible choices for this element.
    */
   public ArrayList getChoices() {
      return choices;
   }

   /**
    * Returns default choice for this element.
    * 
    * @return The default choice for this element.
    */
   public String getDefaultChoice() {
      return (String) choices.get(defaultChoiceIndex);
   }

   /**
    * Returns the index of the default choice for this element.
    * 
    * @return The index of the default choice for this element.
    */
   public int getDefaultChoiceIndex() {
      return defaultChoiceIndex;
   }

   public Object clone() {
      XMLAttribute d = (XMLAttribute) super.clone();

      if (choices != null) {
         d.choices = new ArrayList();
         Iterator it = choices.iterator();
         while (it.hasNext()) {
            d.choices.add(new String(it.next().toString()));
         }
      }
      return d;
   }

   public boolean equals(Object e) {
      boolean equals = super.equals(e);
      if (equals) {
         XMLAttribute el = (XMLAttribute) e;
         equals = (this.choices == null ? el.choices == null
                                       : this.choices.equals(el.choices));
         // System.out.println("          XMLAttribute choices equal - "+equals);
      }
      return equals;
   }

}
