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
 * Represents choice of web service fault catch types from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class WebServiceFaultCatchTypes extends XMLComplexChoice {

   /**
    * Constructs a new object with the given WebServiceDefaultCatch as a parent.
    */
   public WebServiceFaultCatchTypes(WebServiceFaultCatch parent) {
      super(parent, "Choice", true);
   }

   protected void fillChoices() {
      choices = new ArrayList();
      choices.add(new BlockActivity(this));
      choices.add(new TransitionRef(this));
      choosen = (XMLElement) choices.get(0);
   }

   /** Returns BlockActivity choice element. */
   public BlockActivity getBlockActivity() {
      return (BlockActivity) choices.get(0);
   }

   /** Sets BlockActivity choice element as the chosen one. */
   public void setBlockActivity() {
      setChoosen((BlockActivity) choices.get(0));
   }

   /** Returns TransitionRef choice element. */
   public TransitionRef getTransitionRef() {
      return (TransitionRef) choices.get(1);
   }

   /** Sets TransitionRef choice element as the chosen one. */
   public void setTransitionRef() {
      setChoosen((TransitionRef) choices.get(1));
   }

}
