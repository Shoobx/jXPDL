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
public class ConnectorGraphicsInfo extends XMLComplexElement {

   /**
    * Constructs a new object with the given ConnectorGraphicsInfos as a parent.
    */
   public ConnectorGraphicsInfo(ConnectorGraphicsInfos parent) {
      super(parent, true);
   }

   protected void fillStructure() {
      XMLAttribute attrToolId = new XMLAttribute(this, "ToolId", false);
      XMLAttribute attrIsVisible = new XMLAttribute(this,
                                                    "IsVisible",
                                                    false,
                                                    new String[] {
                                                          XPDLConstants.XSD_BOOLEAN_TRUE,
                                                          XPDLConstants.XSD_BOOLEAN_FALSE
                                                    },
                                                    0);
      XMLAttribute attrPageId = new XMLAttribute(this, "PageId", false);
      XMLAttribute attrStyle = new XMLAttribute(this, "Style", false);
      XMLAttribute attrBorderColor = new XMLAttribute(this, "BorderColor", false);
      XMLAttribute attrFillColor = new XMLAttribute(this, "FillColor", false);

      Coordinatess refCoordinatess = new Coordinatess(this);

      add(attrToolId);
      add(attrIsVisible);
      add(attrPageId);
      add(attrStyle);
      add(attrBorderColor);
      add(attrFillColor);
      add(refCoordinatess);
   }

   /** Returns the ToolId attribute value of this object. */
   public String getToolId() {
      return get("ToolId").toValue();
   }

   /** Sets the ToolId attribute value of this object. */
   public void setToolId(String toolId) {
      set("ToolId", toolId);
   }

   /** Returns the BorderStyle attribute value of this object. */
   public String getStyle() {
      return get("Style").toValue();
   }

   /** Sets the BorderStyle attribute value of this object. */
   public void setStyle(String style) {
      set("Style", style);
   }

   /** Sets the BorderColor attribute value of this object. */
   public void setBorderColor(String bc) {
      set("BorderColor", bc);
   }

   /** Returns the BorderColor attribute value of this object. */
   public String getBorderColor() {
      return get("BorderColor").toValue();
   }

   /** Sets the FillColor attribute value of this object. */
   public void setFillColor(String fc) {
      set("FillColor", fc);
   }

   /** Returns the FillColor attribute value of this object. */
   public String getFillColor() {
      return get("FillColor").toValue();
   }

   /** Returns the Coordinatess sub-element of this object. */
   public Coordinatess getCoordinatess() {
      return (Coordinatess) get("Coordinatess");
   }

}
