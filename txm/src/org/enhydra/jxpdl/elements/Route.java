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

import java.util.Set;

import org.enhydra.jxpdl.XMLAttribute;
import org.enhydra.jxpdl.XMLComplexElement;
import org.enhydra.jxpdl.XMLUtil;
import org.enhydra.jxpdl.XPDLConstants;

/**
 * Represents corresponding element from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class Route extends XMLComplexElement {

   /**
    * Constructs a new object with the given ActivityTypes as a parent.
    */
   public Route(ActivityTypes parent) {
      super(parent, true);
   }

   /**
    * Overrides super-class method to indicate that element is never empty, so it's tag will always be written into XML file.
    * 
    * @return <tt>false</tt>
    */
   public boolean isEmpty() {
      return false;
   }

   protected void fillStructure() {
      XMLAttribute attrGatewayType = new XMLAttribute(this, "GatewayType", false, new String[] {
            // XPDLConstants.JOIN_SPLIT_TYPE_NONE,
            XPDLConstants.JOIN_SPLIT_TYPE_EXCLUSIVE,
            // XPDLConstants.JOIN_SPLIT_TYPE_INCLUSIVE,
            // XPDLConstants.JOIN_SPLIT_TYPE_COMPLEX,
            XPDLConstants.JOIN_SPLIT_TYPE_PARALLEL
      }, 0) {
         public void setValue(String v) {
            super.setValue(v);
            Activity act = XMLUtil.getActivity(this);
            Set ogt = XMLUtil.getOutgoingTransitions(act);
            Set inct = XMLUtil.getIncomingTransitions(act);

            Split s = XMLUtil.getSplit(XMLUtil.getActivity(this));
            if (s != null) {
               String oldt = s.getType();
               if (!(oldt.equals(XPDLConstants.JOIN_SPLIT_TYPE_NONE) && ogt.size() <= 1)) {
                  if (!oldt.equals(v)) {
                     s.set("Type", v);
                  }
               }
            }
            Join j = XMLUtil.getJoin(XMLUtil.getActivity(this));
            if (j != null) {
               String oldt = j.getType();
               if (!(oldt.equals(XPDLConstants.JOIN_SPLIT_TYPE_NONE) && inct.size() <= 1)) {
                  if (!oldt.equals(v)) {
                     j.set("Type", v);
                  }
               }
            }
         }
      };
      add(attrGatewayType);
   }

   /** Returns the GatewayType attribute of this object. */
   public XMLAttribute getGatewayTypeAttribute() {
      return (XMLAttribute) get("GatewayType");
   }

   /** Returns the GatewayType attribute value of this object. */
   public String getGatewayType() {
      return getGatewayTypeAttribute().toValue();
   }

   /** Sets the GatewayType attribute value of this object to Parallel. */
   public void setGatewayTypeParallel() {
      getGatewayTypeAttribute().setValue(XPDLConstants.JOIN_SPLIT_TYPE_PARALLEL);
   }

   /** Sets the GatewayType attribute value of this object to Exclusive. */
   public void setGatewayTypeExclusive() {
      getGatewayTypeAttribute().setValue(XPDLConstants.JOIN_SPLIT_TYPE_EXCLUSIVE);
   }

   // public void setGatewayTypeInclusive() {
   // getGatewayTypeAttribute().setValue(XPDLConstants.JOIN_SPLIT_TYPE_INCLUSIVE);
   // }

}
