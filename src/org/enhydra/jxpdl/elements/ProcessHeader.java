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
public class ProcessHeader extends XMLComplexElement {

   public ProcessHeader (WorkflowProcess parent) {
      super(parent, true);
   }

   protected void fillStructure () {
      XMLAttribute attrDurationUnit=new XMLAttribute(this,"DurationUnit",
            false,new String[] {
               XPDLConstants.DURATION_UNIT_NONE,
               XPDLConstants.DURATION_UNIT_Y,
               XPDLConstants.DURATION_UNIT_M,
               XPDLConstants.DURATION_UNIT_D,
               XPDLConstants.DURATION_UNIT_h,
               XPDLConstants.DURATION_UNIT_m,
               XPDLConstants.DURATION_UNIT_s
            }, 0);
      Created refCreated=new Created(this); // min=0
      Description refDescription=new Description(this); // min=0
      Priority refPriority=new Priority(this); // min=0
      Limit refLimit=new Limit(this); // min=0
      ValidFrom refValidFrom=new ValidFrom(this); // min=0
      ValidTo refValidTo=new ValidTo(this); // min=0
      TimeEstimation refTimeEstimation=new TimeEstimation(this); // min=0

      add(attrDurationUnit);
      add(refCreated);
      add(refDescription);
      add(refPriority);
      add(refLimit);
      add(refValidFrom);
      add(refValidTo);
      add(refTimeEstimation);
   }

   public XMLAttribute getDurationUnitAttribute() {
      return (XMLAttribute)get("DurationUnit");
   }
   public String getDurationUnit() {
      return getDurationUnitAttribute().toValue();
   }
   public void setDurationUnitNONE() {
      getDurationUnitAttribute().setValue(XPDLConstants.DURATION_UNIT_NONE);
   }
   public void setDurationUnitYEAR() {
      getDurationUnitAttribute().setValue(XPDLConstants.DURATION_UNIT_Y);
   }
   public void setDurationUnitMONTH() {
      getDurationUnitAttribute().setValue(XPDLConstants.DURATION_UNIT_M);
   }
   public void setDurationUnitDAY() {
      getDurationUnitAttribute().setValue(XPDLConstants.DURATION_UNIT_D);
   }
   public void setDurationUnitHOUR() {
      getDurationUnitAttribute().setValue(XPDLConstants.DURATION_UNIT_h);
   }
   public void setDurationUnitMINUTE() {
      getDurationUnitAttribute().setValue(XPDLConstants.DURATION_UNIT_m);
   }
   public void setDurationUnitSECOND() {
      getDurationUnitAttribute().setValue(XPDLConstants.DURATION_UNIT_s);
   }
   public String getCreated() {
      return get("Created").toValue();
   }
   public void setCreated(String created) {
      set("Created",created);
   }
   public String getDescription() {
      return get("Description").toValue();
   }
   public void setDescription(String description) {
      set("Description",description);
   }
   public String getPriority() {
      return get("Priority").toValue();
   }
   public void setPriority(String priority) {
      set("Priority",priority);
   }
   public String getLimit() {
      return get("Limit").toValue();
   }
   public void setLimit(String limit) {
      set("Limit",limit);
   }
   public String getValidFrom() {
      return get("ValidFrom").toValue();
   }
   public void setValidFrom(String validFrom) {
      set("ValidFrom",validFrom);
   }
   public String getValidTo() {
      return get("ValidTo").toValue();
   }
   public void setValidTo(String validTo) {
      set("ValidTo",validTo);
   }
   public TimeEstimation getTimeEstimation() {
      return (TimeEstimation)get("TimeEstimation");
   }
   
}
