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

import java.util.ArrayList;

import org.enhydra.jxpdl.XMLComplexChoice;
import org.enhydra.jxpdl.XMLElement;
import org.enhydra.jxpdl.XMLUtil;

/**
 * Represents the type of implementations for Activity from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class ImplementationTypes extends XMLComplexChoice {

   public ImplementationTypes(Implementation parent) {
      super(parent, "Type", true);
   }

   protected void fillChoices() {
      choices = new ArrayList();
      choices.add(new No(this));
      choices.add(new Task(this));
      choices.add(new SubFlow(this));
//      choices.add(new Reference(this));
      // MIGRATION FROM XPDL1
      if (XMLUtil.getActivity(this).isXPDL1Support()) {
         choices.add(new Tools(this)); 	  
      }
      choosen = (XMLElement) choices.get(0);
   }

   public No getNo() {
      return (No) choices.get(0);
   }

   public void setNo() {
      setChoosen((No) choices.get(0));
   }

   public Task getTask() {
      return (Task) choices.get(1);
   }

   public void setTask() {
      setChoosen((Task) choices.get(1));
   }

   public SubFlow getSubFlow() {
      return (SubFlow) choices.get(2);
   }

   public void setSubFlow() {
      setChoosen((SubFlow) choices.get(2));
   }

//   public Reference getReference() {
//      return (Reference) choices.get(3);
//   }
//
//   public void setReference() {
//      setChoosen((Reference) choices.get(3));
//   }
   
   // MIGRATION FROM XPDL1         
   public void removeXPDL1Support () {
      super.removeXPDL1Support();
      if (choices.size()>3) {
         choices.remove(3);
      }
   }
}
