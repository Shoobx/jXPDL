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
import org.enhydra.jxpdl.XPDLConstants;

/**
 * Represents corresponding element from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class NodeGraphicsInfo extends XMLComplexElement {

   /**
    * Constructs a new object with the given NodeGraphicsInfos as a parent.
    */
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

   /** Returns the ToolId attribute value of this object. */
   public String getToolId() {
      return get("ToolId").toValue();
   }

   /** Sets the ToolId attribute value of this object. */
   public void setToolId(String toolId) {
      set("ToolId", toolId);
   }


   /** Returns the IsVisible attribute of this object. */
   public XMLAttribute getIsVisibleAttribute() {
      return (XMLAttribute) get("IsVisible");
   }

   /** Returns true if IsVisible attribute value is equal to 'true'. */
   public boolean isVisible() {
      return Boolean.parseBoolean(getIsVisibleAttribute().toValue());
   }

   /** Sets IsVisible attribute value to "true" or "false". */
   public void setIsVisible(boolean isVisible) {
      getIsVisibleAttribute().setValue(String.valueOf(isVisible));
   }

   /** Returns the LaneId attribute value of this object. */
   public String getLaneId() {
      return get("LaneId").toValue();
   }

   /** Sets the LaneId attribute value of this object. */
   public void setLaneId(String laneId) {
      set("LaneId", laneId);
   }

   /** Returns the Height attribute value of this object converted to integer, if empty or can't be converted returns zero. */
   public int getHeight() {
      String h = get("Height").toValue();
      if (!"".equals(h)) {
         try {
            return (int) Double.parseDouble(h);
         } catch (Exception ex) {            
         }
      }
      return 0;
   }

   /** Sets the Height attribute value of this object. */
   public void setHeight(int height) {
      set("Height", String.valueOf(height));
   }

   /** Returns the Width attribute value of this object converted to integer, if empty or can't be converted returns zero. */
   public int getWidth() {
      String w = get("Width").toValue();
      if (!"".equals(w)) {
         try {
            return (int) Double.parseDouble(w);
         } catch (Exception ex) {            
         }
      }
      return 0;
   }

   /** Sets the Width attribute value of this object. */
   public void setWidth(int width) {
      set("Width", String.valueOf(width));
   }

   /** Returns the BorderColor attribute value of this object. */
   public String getBorderColor() {
      return get("BorderColor").toValue();
   }

   /** Sets the BorderColor attribute value of this object. */
   public void setBorderColor(String bc) {
      set("BorderColor", bc);
   }

   /** Returns the FillColor attribute value of this object. */
   public String getFillColor() {
      return get("FillColor").toValue();
   }

   /** Sets the FillColor attribute value of this object. */
   public void setFillColor(String fc) {
      set("FillColor", fc);
   }

   /** Returns the Shape attribute value of this object. */
   public String getShape() {
      return get("Shape").toValue();
   }

   /** Sets the Shape attribute value of this object. */
   public void setShape(String shape) {
      set("Shape", shape);
   }

   /** Returns the Coordinatess sub-element of this object. */
   public Coordinates getCoordinates() {
      return (Coordinates) get("Coordinates");
   }

}
