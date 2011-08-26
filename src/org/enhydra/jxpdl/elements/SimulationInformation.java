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
import org.enhydra.jxpdl.XPDLConstants;

/**
 * Represents corresponding element from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class SimulationInformation extends XMLComplexElement {

   /**
    * Constructs a new object with the given Activity as a parent.
    */
   public SimulationInformation(Activity parent) {
      super(parent, false);
   }

   protected void fillStructure() {
      XMLAttribute attrInstantiation = new XMLAttribute(this,
                                                        "Instantiation",
                                                        false,
                                                        new String[] {
                                                              XPDLConstants.INSTANTIATION_NONE,
                                                              XPDLConstants.INSTANTIATION_ONCE,
                                                              XPDLConstants.INSTANTIATION_MULTIPLE
                                                        },
                                                        0);
      Cost refCost = new Cost(this);
      TimeEstimation refTimeEstimation = new TimeEstimation(this);

      add(attrInstantiation);
      add(refCost);
      add(refTimeEstimation);
   }

   /** Returns the Instantiation attribute of this object. */
   public XMLAttribute getInstantiationAttribute() {
      return (XMLAttribute) get("Instantiation");
   }

   /** Returns the Instantiation attribute value of this object. */
   public String getInstantiation() {
      return getInstantiationAttribute().toValue();
   }

   /** Sets the Instantiation attribute value of this object to an empty string. */
   public void setInstantiationNONE() {
      getInstantiationAttribute().setValue(XPDLConstants.INSTANTIATION_NONE);
   }

   /** Sets the Instantiation attribute value of this object to ONCE. */
   public void setInstantiationONCE() {
      getInstantiationAttribute().setValue(XPDLConstants.INSTANTIATION_ONCE);
   }

   /** Sets the Instantiation attribute value of this object to MULTIPLE. */
   public void setInstantiationMULTIPLE() {
      getInstantiationAttribute().setValue(XPDLConstants.INSTANTIATION_MULTIPLE);
   }

   /** Returns the Cost attribute value of this object. */
   public String getCost() {
      return get("Cost").toValue();
   }

   /** Sets the Cost attribute value of this object. */
   public void setCost(String cost) {
      set("Cost", cost);
   }

   /** Returns the TimeEstimation sub-element of this object. */
   public TimeEstimation getTimeEstimation() {
      return (TimeEstimation) get("TimeEstimation");
   }
}
