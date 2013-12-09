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
public class ProcessHeader extends XMLComplexElement {

   /**
    * Constructs a new object with the given WorkflowProcess as a parent.
    */
   public ProcessHeader(WorkflowProcess parent) {
      super(parent, true);
   }

   protected void fillStructure() {
      XMLAttribute attrDurationUnit = new XMLAttribute(this,
                                                       "DurationUnit",
                                                       false,
                                                       new String[] {
                                                             XPDLConstants.DURATION_UNIT_NONE,
                                                             XPDLConstants.DURATION_UNIT_Y,
                                                             XPDLConstants.DURATION_UNIT_M,
                                                             XPDLConstants.DURATION_UNIT_D,
                                                             XPDLConstants.DURATION_UNIT_h,
                                                             XPDLConstants.DURATION_UNIT_m,
                                                             XPDLConstants.DURATION_UNIT_s
                                                       },
                                                       0);
      Created refCreated = new Created(this); // min=0
      Description refDescription = new Description(this); // min=0
      Priority refPriority = new Priority(this); // min=0
      Limit refLimit = new Limit(this); // min=0
      ValidFrom refValidFrom = new ValidFrom(this); // min=0
      ValidTo refValidTo = new ValidTo(this); // min=0
      TimeEstimation refTimeEstimation = new TimeEstimation(this); // min=0

      add(attrDurationUnit);
      add(refCreated);
      add(refDescription);
      add(refPriority);
      add(refLimit);
      add(refValidFrom);
      add(refValidTo);
      add(refTimeEstimation);
   }

   /** Returns the DurationUnit attribute of this object. */
   public XMLAttribute getDurationUnitAttribute() {
      return (XMLAttribute) get("DurationUnit");
   }

   /** Returns the DurationUnit attribute value of this object. */
   public String getDurationUnit() {
      return getDurationUnitAttribute().toValue();
   }

   /** Sets the DurationUnit attribute value of this object to an empty string. */
   public void setDurationUnitNONE() {
      getDurationUnitAttribute().setValue(XPDLConstants.DURATION_UNIT_NONE);
   }

   /** Sets the DurationUnit attribute value of this object to YEAR. */
   public void setDurationUnitYEAR() {
      getDurationUnitAttribute().setValue(XPDLConstants.DURATION_UNIT_Y);
   }

   /** Sets the DurationUnit attribute value of this object to MONTH. */
   public void setDurationUnitMONTH() {
      getDurationUnitAttribute().setValue(XPDLConstants.DURATION_UNIT_M);
   }

   /** Sets the DurationUnit attribute value of this object to DAY. */
   public void setDurationUnitDAY() {
      getDurationUnitAttribute().setValue(XPDLConstants.DURATION_UNIT_D);
   }

   /** Sets the DurationUnit attribute value of this object to HOUR. */
   public void setDurationUnitHOUR() {
      getDurationUnitAttribute().setValue(XPDLConstants.DURATION_UNIT_h);
   }

   /** Sets the DurationUnit attribute value of this object to MINUTE. */
   public void setDurationUnitMINUTE() {
      getDurationUnitAttribute().setValue(XPDLConstants.DURATION_UNIT_m);
   }

   /** Sets the DurationUnit attribute value of this object to SECOND. */
   public void setDurationUnitSECOND() {
      getDurationUnitAttribute().setValue(XPDLConstants.DURATION_UNIT_s);
   }

   /** Returns the Created attribute value of this object. */
   public String getCreated() {
      return get("Created").toValue();
   }

   /** Sets the Created attribute value of this object. */
   public void setCreated(String created) {
      set("Created", created);
   }

   /** Returns the Description attribute value of this object. */
   public String getDescription() {
      return get("Description").toValue();
   }

   /** Sets the Description attribute value of this object. */
   public void setDescription(String description) {
      set("Description", description);
   }

   /** Returns the Priority attribute value of this object. */
   public String getPriority() {
      return get("Priority").toValue();
   }

   /** Sets the Priority attribute value of this object. */
   public void setPriority(String priority) {
      set("Priority", priority);
   }

   /** Returns the Limit attribute value of this object. */
   public String getLimit() {
      return get("Limit").toValue();
   }

   /** Sets the Limit attribute value of this object. */
   public void setLimit(String limit) {
      set("Limit", limit);
   }

   /** Returns the ValidFrom attribute value of this object. */
   public String getValidFrom() {
      return get("ValidFrom").toValue();
   }

   /** Sets the ValidFrom attribute value of this object. */
   public void setValidFrom(String validFrom) {
      set("ValidFrom", validFrom);
   }

   /** Returns the ValidTo attribute value of this object. */
   public String getValidTo() {
      return get("ValidTo").toValue();
   }

   /** Sets the ValidTo attribute value of this object. */
   public void setValidTo(String validTo) {
      set("ValidTo", validTo);
   }

   /** Returns the TimeEstimation sub-element of this object. */
   public TimeEstimation getTimeEstimation() {
      return (TimeEstimation) get("TimeEstimation");
   }

}
