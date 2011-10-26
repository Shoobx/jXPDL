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
import org.enhydra.jxpdl.XMLUtil;
import org.enhydra.jxpdl.XPDLConstants;

/**
 * Represents corresponding element from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class Join extends XMLComplexElement {

   /**
    * Constructs a new object with the given TransitionRestriction as a parent.
    */
   public Join(TransitionRestriction parent) {
      super(parent, false);
   }

   protected void fillStructure() {
      XMLAttribute attrType = new XMLAttribute(this, "Type", false, new String[] {
            XPDLConstants.JOIN_SPLIT_TYPE_NONE,
            XPDLConstants.JOIN_SPLIT_TYPE_EXCLUSIVE,
//            XPDLConstants.JOIN_SPLIT_TYPE_INCLUSIVE,
            // XPDLConstants.JOIN_SPLIT_TYPE_COMPLEX,
            XPDLConstants.JOIN_SPLIT_TYPE_PARALLEL
      }, 0) {
         // MIGRATION FROM XPDL1
         public void setValue(String v) {
            String vNew = v;
            if ("AND".equals(v)) {
               vNew = XPDLConstants.JOIN_SPLIT_TYPE_PARALLEL;
            } else if ("XOR".equals(v)) {
               vNew = XPDLConstants.JOIN_SPLIT_TYPE_EXCLUSIVE;
            }
            super.setValue(vNew);
            if (!vNew.equals(v)) {
               Activity act = XMLUtil.getActivity(this);
               if (act.getActivityType() == XPDLConstants.ACTIVITY_TYPE_ROUTE) {
                  act.getActivityTypes().getRoute().getGatewayTypeAttribute().setValue(vNew);
               }
            }
         }
      };

      add(attrType);
   }

   /** Returns the Type attribute of this object. */
   public XMLAttribute getTypeAttribute() {
      return (XMLAttribute) get("Type");
   }

   /** Returns the Type attribute value of this object. */
   public String getType() {
      return getTypeAttribute().toValue();
   }

   /** Sets the Type attribute value of this object to an empty string. */
   public void setTypeNONE() {
      getTypeAttribute().setValue(XPDLConstants.JOIN_SPLIT_TYPE_NONE);
   }

   /** Sets the Type attribute value of this object to Parallel. */
   public void setTypeParallel() {
      getTypeAttribute().setValue(XPDLConstants.JOIN_SPLIT_TYPE_PARALLEL);
   }

   /** Sets the Type attribute value of this object to Exclusive. */
   public void setTypeExclusive() {
      getTypeAttribute().setValue(XPDLConstants.JOIN_SPLIT_TYPE_EXCLUSIVE);
   }

   // public void setTypeComplex() {
   // getTypeAttribute().setValue(XPDLConstants.JOIN_SPLIT_TYPE_COMPLEX);
   // }
   //
//   public void setTypeInclusive() {
//      getTypeAttribute().setValue(XPDLConstants.JOIN_SPLIT_TYPE_INCLUSIVE);
//   }

}
