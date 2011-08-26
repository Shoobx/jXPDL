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

   /** Constructs a new object with the given Associations as a parent. */
   public Association(Associations parent) {
      super(parent, true);
   }

   protected void fillStructure() {
      XMLAttribute attrSource = new XMLAttribute(this, "Source", true);
      XMLAttribute attrTarget = new XMLAttribute(this, "Target", true);
      XMLAttribute attrName = new XMLAttribute(this, "Name", false);
      XMLAttribute attrAssociationDirection = new XMLAttribute(this,
                                                               "AssociationDirection",
                                                               true,
                                                               new String[] {
                                                                     XPDLConstants.ASSOCIATION_DIRECTION_NONE,
                                                                     XPDLConstants.ASSOCIATION_DIRECTION_TO,
                                                                     XPDLConstants.ASSOCIATION_DIRECTION_FROM,
                                                                     XPDLConstants.ASSOCIATION_DIRECTION_BOTH
                                                               },
                                                               0);

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

   /** Returns the Name attribute value of this object. */
   public String getName() {
      return get("Name").toValue();
   }

   /** Sets the name attribute value of this object. */
   public void setName(String name) {
      set("Name", name);
   }

   /** Returns the Source attribute value of this object. */
   public String getSource() {
      return get("Source").toValue();
   }

   /** Sets the Source attribute value of this object. */
   public void setSource(String source) {
      set("Source", source);
   }

   /** Returns the Target attribute value of this object. */
   public String getTarget() {
      return get("Target").toValue();
   }

   /** Sets the Target attribute value of this object. */
   public void setTarget(String target) {
      set("Target", target);
   }

   /** Returns the AssociationDirection attribute of this object. */
   public XMLAttribute getAssociationDirectionAttribute() {
      return (XMLAttribute) get("AssociationDirection");
   }

   /** Returns the AssociationDirection attribute value of this object. */
   public String getAssociationDirection() {
      return getAssociationDirectionAttribute().toValue();
   }

   /** Sets the AssociationDirection attribute value of this object to None. */
   public void setAssociationDirectionNONE() {
      getAssociationDirectionAttribute().setValue(XPDLConstants.ASSOCIATION_DIRECTION_NONE);
   }

   /** Sets the AssociationDirection attribute value of this object to To. */
   public void setAssociationDirectionTO() {
      getAssociationDirectionAttribute().setValue(XPDLConstants.ASSOCIATION_DIRECTION_TO);
   }

   /** Sets the AssociationDirection attribute value of this object to From. */
   public void setAssociationDirectionFROM() {
      getAssociationDirectionAttribute().setValue(XPDLConstants.ASSOCIATION_DIRECTION_FROM);
   }

   /** Sets the AssociationDirection attribute value of this object to Both. */
   public void setAssociationDirectionBOTH() {
      getAssociationDirectionAttribute().setValue(XPDLConstants.ASSOCIATION_DIRECTION_BOTH);
   }

   /** Returns the ConnectorGraphicsInfos sub-element of this object. */
   public ConnectorGraphicsInfos getConnectorGraphicsInfos() {
      return (ConnectorGraphicsInfos) get("ConnectorGraphicsInfos");
   }

}
