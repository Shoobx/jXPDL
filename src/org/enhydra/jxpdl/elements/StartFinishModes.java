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
import org.enhydra.jxpdl.XMLEmptyChoiceElement;

/**
 * Represents the choice for start and end modes for activity.
 * 
 * @author Sasa Bojanic
 */
public class StartFinishModes extends XMLComplexChoice {

   /**
    * Constructs a new object with the given StartMode as a parent.
    */
   public StartFinishModes(StartMode parent) {
      super(parent, "Mode", false);
      fillChoices();
   }

   /**
    * Constructs a new object with the given FinishMode as a parent.
    */
   public StartFinishModes(FinishMode parent) {
      super(parent, "Mode", false);
      fillChoices();
   }

   public void fillChoices() {
      choices = new ArrayList();
      choices.add(new XMLEmptyChoiceElement(this));
      choices.add(new Automatic(this));
      choices.add(new Manual(this));
      choosen = (XMLElement) choices.get(0);
   }

   /** Returns XMLEmptyChoiceElement choice element. */
   public XMLEmptyChoiceElement getEmptyChoiceElement() {
      return (XMLEmptyChoiceElement) choices.get(0);
   }

   /** Sets XMLEmptyChoiceElement choice element as the chosen one. */
   public void setEmptyChoiceElement() {
      setChoosen((XMLEmptyChoiceElement) choices.get(0));
   }

   /** Returns Automatic choice element. */
   public Automatic getAutomatic() {
      return (Automatic) choices.get(1);
   }

   /** Sets Automatic choice element as the chosen one. */
   public void setAutomatic() {
      setChoosen((Automatic) choices.get(1));
   }

   /** Returns Manual choice element. */
   public Manual getManual() {
      return (Manual) choices.get(2);
   }

   /** Sets Manual choice element as the chosen one. */
   public void setManual() {
      setChoosen((Manual) choices.get(2));
   }

   // MIGRATION FROM XPDL1
   public void setChoosen(XMLElement ch) {
      super.setChoosen(ch);
      Activity act = (Activity) getParent().getParent();
      if (getParent() instanceof FinishMode) {
         if (ch instanceof Automatic) {
            act.setFinishModeAUTOMATIC();
         } else if (ch instanceof Manual) {
            act.setFinishModeMANUAL();
         }
      } else {
         if (ch instanceof Automatic) {
            act.setStartModeAUTOMATIC();
         } else if (ch instanceof Manual) {
            act.setStartModeMANUAL();
         }
      }
   }
}
