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
public class SubFlow extends XMLComplexElement {

   public SubFlow(ImplementationTypes parent) {
      super(parent, true);
   }

   protected void fillStructure() {
      XMLAttribute attrId = new XMLAttribute(this, "Id", true); // required
      XMLAttribute attrExecution = new XMLAttribute(this,
                                                    "Execution",
                                                    false,
                                                    new String[] {
                                                          XPDLConstants.EXECUTION_NONE,
                                                          XPDLConstants.EXECUTION_ASYNCHR,
                                                          XPDLConstants.EXECUTION_SYNCHR
                                                    },
                                                    0);
      ActualParameters refActualParameters=new ActualParameters(this); // min=0
//      PassingTypes refPassingTypes = new PassingTypes(this);

      add(attrId);
      add(attrExecution);
      add(refActualParameters);
//      add(refPassingTypes);
   }

   public String getId() {
      return get("Id").toValue();
   }

   public void setId(String id) {
      set("Id", id);
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

   public ActualParameters getActualParameters() {
      return (ActualParameters)get("ActualParameters");
   }
   
//   public PassingTypes getPassingTypes() {
//      return (PassingTypes) get("Choice");
//   }

}
