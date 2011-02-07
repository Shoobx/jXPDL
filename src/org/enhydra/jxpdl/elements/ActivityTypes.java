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
 *  Represents Activity types from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class ActivityTypes extends XMLComplexChoice {

   public ActivityTypes (Activity parent) {
      super(parent,"Type", true);
   }

   protected void fillChoices () {
      choices=new ArrayList();
      choices.add(new Route(this)); 
      choices.add(new Implementation(this)); 
      choices.add(new BlockActivity(this));
      choices.add(new Event(this));
      choosen=(XMLElement)choices.get(1);      
   }
   
   public Route getRoute () {
      return (Route)choices.get(0);
   }

   public void setRoute () {
      setChoosen((Route)choices.get(0));
   }
   
   public Implementation getImplementation () {
      return (Implementation)choices.get(1);
   }

   public void setImplementation () {
      setChoosen((Implementation)choices.get(1));
   }

   public BlockActivity getBlockActivity () {
      return (BlockActivity)choices.get(2);
   }

   public void setBlockActivity () {
      setChoosen((BlockActivity)choices.get(2));
   }

   public Event getEvent () {
      return (Event)choices.get(3);
   }

   public void setEvent () {
      setChoosen((Event)choices.get(3));
   }
}

