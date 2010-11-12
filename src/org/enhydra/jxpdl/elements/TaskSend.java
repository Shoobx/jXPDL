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
public class TaskSend extends XMLComplexElement {

   public TaskSend(TaskTypes parent) {
      super(parent, true);
   }

   protected void fillStructure() {
      XMLAttribute attrImplementation = new XMLAttribute(this,
                                                         "Implementation",
                                                         true,
                                                         new String[] {
                                                               XPDLConstants.TASK_IMPLEMENTATION_WEBSERVICE,
                                                               XPDLConstants.TASK_IMPLEMENTATION_OTHER,
                                                               XPDLConstants.TASK_IMPLEMENTATION_UNSPECIFIED
                                                         },
                                                         0);

      Message refMessage = new Message(this);
      WebServiceOperation refWebServiceOperation = new WebServiceOperation(this, false);
      WebServiceFaultCatchs refWebServiceFaultCatchs = new WebServiceFaultCatchs(this);

      add(attrImplementation);
      add(refMessage);
      add(refWebServiceOperation);
      add(refWebServiceFaultCatchs);
   }

}
