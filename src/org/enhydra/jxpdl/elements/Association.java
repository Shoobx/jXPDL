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
 */
public class Association extends XMLCollectionElement {

   public Association(Associations parent) {
      super(parent, true);
   }

   protected void fillStructure() {
      XMLAttribute attrSource = new XMLAttribute(this, "Source", true);
      XMLAttribute attrTarget = new XMLAttribute(this, "Target", true);
      XMLAttribute attrName = new XMLAttribute(this, "Name", false);
      XMLAttribute attrAssociationDirection = new XMLAttribute(this, "AssociationDirection", true, new String[] { XPDLConstants.ASSOCIATION_DIRECTION_NONE,
            XPDLConstants.ASSOCIATION_DIRECTION_TO, XPDLConstants.ASSOCIATION_DIRECTION_FROM, XPDLConstants.ASSOCIATION_DIRECTION_BOTH }, 0);

      AObject refObject = new AObject(this);
      ConnectorGraphicsInfos refConnectorGraphicsInfos = new ConnectorGraphicsInfos(this); // min=0

      super.fillStructure();
      add(attrName);
      add(attrSource);
      add(attrTarget);
      add(attrAssociationDirection);

      add(refObject);
      add(refConnectorGraphicsInfos);

   }

   public String getName() {
      return get("Name").toValue();
   }

   public void setName(String name) {
      set("Name", name);
   }

   public String getSource() {
      return get("Source").toValue();
   }

   public void setSource(String source) {
      set("Source", source);
   }

   public String getTarget() {
      return get("Target").toValue();
   }

   public void setTarget(String target) {
      set("Target", target);
   }

   public XMLAttribute getAssociationDirectionAttribute() {
      return (XMLAttribute) get("AssociationDirection");
   }

   public String getAssociationDirection() {
      return getAssociationDirectionAttribute().toValue();
   }

   public void setAssociationDirectionNONE() {
      getAssociationDirectionAttribute().setValue(XPDLConstants.ASSOCIATION_DIRECTION_NONE);
   }

   public void setAssociationDirectionTO() {
      getAssociationDirectionAttribute().setValue(XPDLConstants.ASSOCIATION_DIRECTION_TO);
   }

   public void setAssociationDirectionFROM() {
      getAssociationDirectionAttribute().setValue(XPDLConstants.ASSOCIATION_DIRECTION_FROM);
   }

   public void setAssociationDirectionBOTH() {
      getAssociationDirectionAttribute().setValue(XPDLConstants.ASSOCIATION_DIRECTION_BOTH);
   }
   
   public ConnectorGraphicsInfos getConnectorGraphicsInfos() {
      return (ConnectorGraphicsInfos) get("ConnectorGraphicsInfos");
   }

}
