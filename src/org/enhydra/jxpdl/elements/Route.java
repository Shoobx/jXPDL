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
import org.enhydra.jxpdl.XMLUtil;
import org.enhydra.jxpdl.XPDLConstants;

/**
 * Represents coresponding element from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class Route extends XMLComplexElement {

   public Route(ActivityTypes parent) {
      super(parent, true);
   }

   /**
    * Overrides super-class method to indicate that element is never empty, so it's tag
    * will always be written into XML file.
    * 
    * @return <tt>false</tt>
    */
   public boolean isEmpty() {
      return false;
   }

   protected void fillStructure() {
      XMLAttribute attrGatewayType = new XMLAttribute(this,
                                                      "GatewayType",
                                                      false,
                                                      new String[] {
//                                                            XPDLConstants.JOIN_SPLIT_TYPE_NONE,
                                                            XPDLConstants.JOIN_SPLIT_TYPE_EXCLUSIVE,
                                                            XPDLConstants.JOIN_SPLIT_TYPE_INCLUSIVE,
                                                            // XPDLConstants.JOIN_SPLIT_TYPE_COMPLEX,
                                                            XPDLConstants.JOIN_SPLIT_TYPE_PARALLEL
                                                      },
                                                      0) {
         public void setValue (String v) {
            super.setValue(v);
            Split s = XMLUtil.getSplit(XMLUtil.getActivity(this));
            if (s!=null) {
               s.set("Type", v);
            }
            Join j = XMLUtil.getJoin(XMLUtil.getActivity(this));
            if (j!=null) {
               j.set("Type", v);
            }            
         }
      };
      add(attrGatewayType);
   }

   public XMLAttribute getGatewayTypeAttribute() {
      return (XMLAttribute) get("GatewayType");
   }

   public String getGatewayType() {
      return getGatewayTypeAttribute().toValue();
   }

   public void setGatewayTypeParallel() {
      getGatewayTypeAttribute().setValue(XPDLConstants.JOIN_SPLIT_TYPE_PARALLEL);
   }

   public void setGatewayTypeExclusive() {
      getGatewayTypeAttribute().setValue(XPDLConstants.JOIN_SPLIT_TYPE_EXCLUSIVE);
   }

   public void setGatewayTypeInclusive() {
      getGatewayTypeAttribute().setValue(XPDLConstants.JOIN_SPLIT_TYPE_INCLUSIVE);
   }

}
