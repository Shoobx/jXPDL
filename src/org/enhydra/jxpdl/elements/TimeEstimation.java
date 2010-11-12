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

import org.enhydra.jxpdl.XMLComplexElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class TimeEstimation extends XMLComplexElement {

   public TimeEstimation (SimulationInformation parent) {
      super(parent, true);
   }

   public TimeEstimation (ProcessHeader parent) {
      super(parent, false);
   }

   protected void fillStructure () {
      WaitingTime refWaitingTime=new WaitingTime(this); // min=0
      WorkingTime refWorkingTime=new WorkingTime(this); // min=0
      Duration refDuration=new Duration(this); // min=0

      add(refWaitingTime);
      add(refWorkingTime);
      add(refDuration);      
   }

   public String getDuration() {
      return get("Duration").toValue();
   }
   public void setDuration(String duration) {
      set("Duration",duration);
   }
   public String getWaitingTime() {
      return get("WaitingTime").toValue();
   }
   public void setWaitingTime(String waitingTime) {
      set("WaitingTime",waitingTime);
   }
   public String getWorkingTime() {
      return get("WorkingTime").toValue();
   }
   public void setWorkingTime(String workingTime) {
      set("WorkingTime",workingTime);
   }
}
