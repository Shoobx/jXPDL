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

public class WebServiceOperationTypes extends XMLComplexChoice {

   /**
    * Constructs a new object with the given WebServiceOperation as a parent.
    */
   public WebServiceOperationTypes(WebServiceOperation parent) {
      super(parent, "Choice", false);
   }

   protected void fillChoices() {
      choices = new ArrayList();
      choices.add(new Partner(this));
      choices.add(new Service(this));
      choosen = (XMLElement) choices.get(0);
   }

   /** Returns Partner choice element. */
   public Partner getPartner() {
      return (Partner) choices.get(0);
   }

   /** Sets Partner choice element as the chosen one. */
   public void setPartner() {
      setChoosen((Partner) choices.get(0));
   }

   /** Returns Service choice element. */
   public Service getService() {
      return (Service) choices.get(1);
   }

   /** Sets Service choice element as the chosen one. */
   public void setService() {
      setChoosen((Service) choices.get(1));
   }

}
