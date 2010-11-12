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
public class NodeGraphicsInfo extends XMLComplexElement {

   public NodeGraphicsInfo(NodeGraphicsInfos parent) {
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
      XMLAttribute attrLaneId = new XMLAttribute(this, "LaneId", false);
      XMLAttribute attrHeight = new XMLAttribute(this, "Height", false);
      XMLAttribute attrWidth = new XMLAttribute(this, "Width", false);
      XMLAttribute attrBorderColor = new XMLAttribute(this, "BorderColor", false);
      XMLAttribute attrFillColor = new XMLAttribute(this, "FillColor", false);
      XMLAttribute attrShape = new XMLAttribute(this, "Shape", false);

      Coordinates refCoordinates = new Coordinates(this); // min=0

      add(attrToolId);
      add(attrIsVisible);
      add(attrPageId);
      add(attrLaneId);
      add(attrHeight);
      add(attrWidth);
      add(attrBorderColor);
      add(attrFillColor);
      add(attrShape);
      add(refCoordinates);
   }

   public String getToolId() {
      return get("ToolId").toValue();
   }

   public void setToolId(String toolId) {
      set("ToolId", toolId);
   }

   public XMLAttribute getIsVisibleAttribute() {
      return (XMLAttribute) get("IsVisible");
   }

   public boolean isVisible() {
      return Boolean.parseBoolean(getIsVisibleAttribute().toValue());
   }

   public void setIsVisible(boolean isVisible) {
      getIsVisibleAttribute().setValue(String.valueOf(isVisible));
   }

   public String getLaneId() {
      return get("LaneId").toValue();
   }

   public void setLaneId(String laneId) {
      set("LaneId", laneId);
   }

   public int getHeight() {
      String h = get("Height").toValue();
      if (!"".equals(h)) {
         return (int) Double.parseDouble(h);
      }
      return 0;
   }

   public void setHeight(int height) {
      set("Height", String.valueOf(height));
   }

   public int getWidth() {
      String w = get("Width").toValue();
      if (!"".equals(w)) {
         return (int) Double.parseDouble(w);
      }
      return 0;
   }

   public void setWidth(int width) {
      set("Width", String.valueOf(width));
   }

   public String getBorderColor() {
      return get("BorderColor").toValue();
   }

   public void setBorderColor(String borderColor) {
      set("BorderColor", borderColor);
   }

   public String getFillColor() {
      return get("FillColor").toValue();
   }

   public void setFillColor(String fillColor) {
      set("FillColor", fillColor);
   }

   public String getShape() {
      return get("Shape").toValue();
   }

   public void setShape(String shape) {
      set("Shape", shape);
   }

   public Coordinates getCoordinates() {
      return (Coordinates) get("Coordinates");
   }

}
