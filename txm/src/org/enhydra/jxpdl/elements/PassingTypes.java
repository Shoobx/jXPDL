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

/**
 * Represents choice of passing types from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class PassingTypes extends XMLComplexChoice {

   public PassingTypes(TaskApplication parent) {
      super(parent, "Choice", true);
   }

   public PassingTypes(SubFlow parent) {
      super(parent, "Choice", true);
   }

   public PassingTypes(MessageType parent) {
      super(parent, "Choice", true);
   }

   protected void fillChoices() {
      choices = new ArrayList();
      choices.add(new ActualParameters(this));
      choices.add(new DataMappings(this));
      choosen = (XMLElement) choices.get(0);
   }

   public ActualParameters getActualParameters() {
      return (ActualParameters) choices.get(0);
   }

   public void setActualParameters() {
      setChoosen((ActualParameters) choices.get(0));
   }

   public DataMappings getDataMappings() {
      return (DataMappings) choices.get(1);
   }

   public void setDataMappings() {
      setChoosen((DataMappings) choices.get(1));
   }

}
