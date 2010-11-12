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
import org.enhydra.jxpdl.XMLElement;
import org.enhydra.jxpdl.XPDLConstants;

/**
 * Represents coresponding element from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class Deadline extends XMLComplexElement {

   public Deadline(Deadlines parent, boolean xpdl1support) {
      super(parent, true, xpdl1support);
   }

   protected void fillStructure() {
      DeadlineDuration refDeadlineDuration = new DeadlineDuration(this); // min=1, max=1
      ExceptionName refExceptionName = new ExceptionName(this); // min=1, max=1
      XMLAttribute attrExecution = new XMLAttribute(this,
                                                    "Execution",
                                                    false,
                                                    new String[] {
                                                          XPDLConstants.EXECUTION_NONE,
                                                          XPDLConstants.EXECUTION_ASYNCHR,
                                                          XPDLConstants.EXECUTION_SYNCHR
                                                    },
                                                    0);

      add(attrExecution);

      // MIGRATION FROM XPDL1
      if (xpdl1support) {
         DeadlineCondition refDeadlineCondition = new DeadlineCondition(this); // min=1,
         // max=1
         add(refDeadlineCondition);
      }

      add(refDeadlineDuration);
      add(refExceptionName);
   }

   public XMLAttribute getExecutionAttribute() {
      return (XMLAttribute) get("Execution");
   }

   public String getExecution() {
      return getExecutionAttribute().toValue();
   }

   public void setExecutionNONE() {
      getExecutionAttribute().setValue(XPDLConstants.EXECUTION_NONE);
   }

   public void setExecutionASYNCHR() {
      getExecutionAttribute().setValue(XPDLConstants.EXECUTION_ASYNCHR);
   }

   public void setExecutionSYNCHR() {
      getExecutionAttribute().setValue(XPDLConstants.EXECUTION_SYNCHR);
   }

   public String getDeadlineDuration() {
      return get("DeadlineDuration").toValue();
   }

   public void setDeadlineDuration(String deadlineDuration) {
      set("DeadlineDuration", deadlineDuration);
   }

   public String getExceptionName() {
      return get("ExceptionName").toValue();
   }

   public void setExceptionName(String exceptionName) {
      set("ExceptionName", exceptionName);
   }

   // MIGRATION FROM XPDL1
   public void removeXPDL1Support() {
      super.removeXPDL1Support();
      XMLElement el = get("DeadlineCondition");
      if (el != null) {
         elements.remove(el);
         elementMap.remove("DeadlineCondition");
      }
      xpdl1support = false;
   }
}
