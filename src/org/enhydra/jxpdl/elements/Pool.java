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
import org.enhydra.jxpdl.XMLCollectionElement;
import org.enhydra.jxpdl.XPDLConstants;

/**
 * Represents corresponding element from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class Pool extends XMLCollectionElement {

   public Pool(Pools parent) {
      super(parent, true);
   }

   protected void fillStructure() {
      XMLAttribute attrName = new XMLAttribute(this, "Name", false);
      XMLAttribute attrOrientation = new XMLAttribute(this,
                                                      "Orientation",
                                                      false,
                                                      new String[] {
                                                            XPDLConstants.POOL_ORIENTATION_HORIZONTAL,
                                                            XPDLConstants.POOL_ORIENTATION_VERTICAL
                                                      },
                                                      0);
      XMLAttribute attrProcess = new XMLAttribute(this, "Process", false);
      XMLAttribute attrParticipant = new XMLAttribute(this, "Participant", false);
      XMLAttribute attrBoundaryVisible = new XMLAttribute(this,
                                                          "BoundaryVisible",
                                                          true,
                                                          new String[] {
                                                                XPDLConstants.XSD_BOOLEAN_TRUE,
                                                                XPDLConstants.XSD_BOOLEAN_FALSE
                                                          },
                                                          0);
      XMLAttribute attrMainPool = new XMLAttribute(this, "MainPool", true, new String[] {
            XPDLConstants.XSD_BOOLEAN_TRUE, XPDLConstants.XSD_BOOLEAN_FALSE
      }, 1);

      Lanes refLanes = new Lanes(this); // min=0
      NodeGraphicsInfos refNodeGraphicsInfos = new NodeGraphicsInfos(this); // min=0

      super.fillStructure();
      add(attrName);
      add(attrOrientation);
      add(attrProcess);
      add(attrParticipant);
      add(attrBoundaryVisible);
      add(attrMainPool);
      add(refLanes);
      add(refNodeGraphicsInfos);
   }

   public String getName() {
      return get("Name").toValue();
   }

   public void setName(String name) {
      set("Name", name);
   }

   public String getOrientation() {
      return get("Orientation").toValue();
   }

   public void setOrientation(String orientation) {
      set("Orientation", orientation);
   }

   public String getProcess() {
      return get("Process").toValue();
   }

   public void setProcess(String process) {
      set("Process", process);
   }

   public String getParticipant() {
      return get("Participant").toValue();
   }

   public void setParticipant(String participant) {
      set("Participant", participant);
   }

   public boolean getBoundaryVisible() {
      return Boolean.parseBoolean(get("BoundaryVisible").toValue());
   }

   public void setBoundaryVisible(boolean visible) {
      set("BoundaryVisible", String.valueOf(visible));
   }

   public boolean getMainPool() {
      return Boolean.parseBoolean(get("MainPool").toValue());
   }

   public void setMainPool(boolean isMain) {
      set("MainPool", String.valueOf(isMain));
   }

   public Lanes getLanes() {
      return (Lanes) get("Lanes");
   }

   public NodeGraphicsInfos getNodeGraphicsInfos() {
      return (NodeGraphicsInfos) get("NodeGraphicsInfos");
   }

}
