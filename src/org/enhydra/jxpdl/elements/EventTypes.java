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
 * Represents corresponding element from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class EventTypes extends XMLComplexChoice {

   /**
    * Constructs a new object with the given Event as a parent.
    */
   public EventTypes(Event parent) {
      super(parent, "Event", true);
   }

   protected void fillChoices() {
      choices = new ArrayList();
      choices.add(new StartEvent(this));
      choices.add(new EndEvent(this));
      choosen = (XMLElement) choices.get(0);
   }

   /** Returns the StartEvent choice of this object. */
   public StartEvent getStartEvent() {
      return (StartEvent) choices.get(0);
   }

   /** Sets the StartEvent to be the chosen element of this object. */
   public void setStartEvent() {
      setChoosen((StartEvent) choices.get(0));
   }

   /** Returns the EndEvent choice of this object. */
   public EndEvent getEndEvent() {
      return (EndEvent) choices.get(1);
   }

   /** Sets the EndEvent to be the chosen element of this object. */
   public void setEndEvent() {
      setChoosen((EndEvent) choices.get(1));
   }
}
