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
 *  Represents coresponding element from XPDL schema.
 *
 *  @author Sasa Bojanic
 */
public class ParticipantType extends XMLComplexElement {

   public ParticipantType (Participant parent) {
      super(parent, true);
   }

   protected void fillStructure () {
      // required
      XMLAttribute attrType=new XMLAttribute(this,"Type",
         true,
         new String[] {
            XPDLConstants.PARTICIPANT_TYPE_RESOURCE_SET,
            XPDLConstants.PARTICIPANT_TYPE_RESOURCE,
            XPDLConstants.PARTICIPANT_TYPE_ROLE,
            XPDLConstants.PARTICIPANT_TYPE_ORGANIZATIONAL_UNIT,
            XPDLConstants.PARTICIPANT_TYPE_HUMAN,
            XPDLConstants.PARTICIPANT_TYPE_SYSTEM
         }, 2);

      add(attrType);
   }

   public XMLAttribute getTypeAttribute() {
      return (XMLAttribute)get("Type");
   }

   public String getType () {
      return getTypeAttribute().toValue();
   }
   public void setTypeRESOURCE_SET () {
      getTypeAttribute().setValue(XPDLConstants.PARTICIPANT_TYPE_RESOURCE_SET);
   }
   public void setTypeRESOURCE () {
      getTypeAttribute().setValue(XPDLConstants.PARTICIPANT_TYPE_RESOURCE);
   }
   public void setTypeROLE () {
      getTypeAttribute().setValue(XPDLConstants.PARTICIPANT_TYPE_ROLE);
   }
   public void setTypeORGANIZATIONAL_UNIT () {
      getTypeAttribute().setValue(XPDLConstants.PARTICIPANT_TYPE_ORGANIZATIONAL_UNIT);
   }
   public void setTypeHUMAN () {
      getTypeAttribute().setValue(XPDLConstants.PARTICIPANT_TYPE_HUMAN);
   }
   public void setTypeSYSTEM () {
      getTypeAttribute().setValue(XPDLConstants.PARTICIPANT_TYPE_SYSTEM);
   }
}
