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
public class ParticipantType extends XMLComplexElement {

   /**
    * Constructs a new object with the Participant as a parent.
    */
   public ParticipantType(Participant parent) {
      super(parent, true);
   }

   protected void fillStructure() {
      XMLAttribute attrType = new XMLAttribute(this, "Type", true, new String[] {
            XPDLConstants.PARTICIPANT_TYPE_RESOURCE_SET,
            XPDLConstants.PARTICIPANT_TYPE_RESOURCE,
            XPDLConstants.PARTICIPANT_TYPE_ROLE,
            XPDLConstants.PARTICIPANT_TYPE_ORGANIZATIONAL_UNIT,
            XPDLConstants.PARTICIPANT_TYPE_HUMAN,
            XPDLConstants.PARTICIPANT_TYPE_SYSTEM
      }, 2);

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

   /** Sets the Type attribute value of this object to RESOURCE_SET. */
   public void setTypeRESOURCE_SET() {
      getTypeAttribute().setValue(XPDLConstants.PARTICIPANT_TYPE_RESOURCE_SET);
   }

   /** Sets the Type attribute value of this object to RESOURCE. */
   public void setTypeRESOURCE() {
      getTypeAttribute().setValue(XPDLConstants.PARTICIPANT_TYPE_RESOURCE);
   }

   /** Sets the Type attribute value of this object to ROLE. */
   public void setTypeROLE() {
      getTypeAttribute().setValue(XPDLConstants.PARTICIPANT_TYPE_ROLE);
   }

   /** Sets the Type attribute value of this object to ORGANIZATIONAL_UNIT. */
   public void setTypeORGANIZATIONAL_UNIT() {
      getTypeAttribute().setValue(XPDLConstants.PARTICIPANT_TYPE_ORGANIZATIONAL_UNIT);
   }

   /** Sets the Type attribute value of this object to HUMAN. */
   public void setTypeHUMAN() {
      getTypeAttribute().setValue(XPDLConstants.PARTICIPANT_TYPE_HUMAN);
   }

   /** Sets the Type attribute value of this object to SYSTEM. */
   public void setTypeSYSTEM() {
      getTypeAttribute().setValue(XPDLConstants.PARTICIPANT_TYPE_SYSTEM);
   }
}
