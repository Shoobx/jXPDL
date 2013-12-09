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
 *  Represents choice of application types from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class ApplicationTypes extends XMLComplexChoice {

   /**
    * Constructs a new object with the given Application as a parent.
    */
   public ApplicationTypes (Application parent) {
      super(parent,"Choice", true);
   }

   protected void fillChoices () {
      choices=new ArrayList();
      choices.add(new FormalParameters(this)); 
      choices.add(new ExternalReference(this, false)); 
      choosen=(XMLElement)choices.get(0);      
   }
   
   /** Returns the FormalParameters choice of this element. */
   public FormalParameters getFormalParameters () {
      return (FormalParameters)choices.get(0);
   }

   /** Sets FormalParameters choice element as the chosen one. */
   public void setFormalParameters () {
      setChoosen((FormalParameters)choices.get(0));
   }
   
   /** Returns the ExternalReference choice of this element. */
   public ExternalReference getExternalReference () {
      return (ExternalReference)choices.get(1);
   }

   /** Sets ExternalReference choice element as the chosen one. */
   public void setExternalReference () {
      setChoosen((ExternalReference)choices.get(1));
   }

}

