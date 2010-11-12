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

/**
 *  Represents coresponding element from XPDL schema.
 *
 *  @author Sasa Bojanic
 */
public class Participant extends XMLCollectionElement {

   public Participant (Participants parent) {
      super(parent, true);
   }

   protected void fillStructure () {
      XMLAttribute attrName=new XMLAttribute(this,"Name", false);
      ParticipantType refParticipantType=new ParticipantType(this);
      Description refDescription=new Description(this); // min=0
      ExternalReference refExternalReference=new ExternalReference(this, false); // min=0
      ExtendedAttributes refExtendedAttributes=new ExtendedAttributes(this); // min=0

      super.fillStructure();
      add(attrName);
      add(refParticipantType);
      add(refDescription);
      add(refExternalReference);
      add(refExtendedAttributes);
   }

   public String getName() {
      return get("Name").toValue();
   }
   public void setName(String name) {
      set("Name",name);
   }
   public String getDescription() {
      return get("Description").toValue();
   }
   public void setDescription(String description) {
      set("Description",description);
   }
   public ExtendedAttributes getExtendedAttributes() {
      return (ExtendedAttributes)get("ExtendedAttributes");
   }
   public ExternalReference getExternalReference() {
      return (ExternalReference)get("ExternalReference");
   }
   public ParticipantType getParticipantType() {
      return (ParticipantType)get("ParticipantType");
   }
}
