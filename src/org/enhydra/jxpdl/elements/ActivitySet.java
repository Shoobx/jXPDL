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
import java.util.Iterator;

import org.enhydra.jxpdl.XMLAttribute;
import org.enhydra.jxpdl.XMLCollectionElement;
import org.enhydra.jxpdl.XMLInterface;

/**
 *  Represents corresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class ActivitySet extends XMLCollectionElement {

   protected transient ArrayList startingActivities;
   protected transient ArrayList endingActivities;
   
   public ActivitySet (ActivitySets parent) {
      super (parent, true);
   }

   protected void fillStructure () {
      XMLAttribute attrName = new XMLAttribute(this, "Name", false);
      Activities refActivities=new Activities(this);
      Transitions refTransitions=new Transitions(this);
      
      super.fillStructure();
      add(attrName);
      add(refActivities);      
      add(refTransitions);
      
   }

   public void initCaches (XMLInterface xmli) {
      super.initCaches(xmli);
      Iterator it=getActivities().toElements().iterator();
      while (it.hasNext()) {
         Activity act=(Activity)it.next();
         ArrayList trsI=act.getIncomingTransitions();
         ArrayList trsNEO=act.getNonExceptionalOutgoingTransitions();
         // the activity is starting one if it has no input transitions ...
         if (trsI.size()==0) {
            startingActivities.add(act);
            // or there is a one input transition, but it is a selfreference
         } else if (trsI.size()==1) {
            Transition t=(Transition)trsI.get(0);
            if (t.getFrom().equals(t.getTo())) {
               startingActivities.add(act);
            }
         }
         if (trsNEO.size()==0) {
            endingActivities.add(act);
         } else if (trsNEO.size()==1) {
            Transition t=(Transition)trsNEO.get(0);
            if (t.getFrom().equals(t.getTo())) {
               endingActivities.add(act);
            }            
         }
      }            
   }

   public void clearCaches () {
      startingActivities=new ArrayList();
      endingActivities=new ArrayList();
      super.clearCaches();
   }

   public ArrayList getStartingActivities () {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return startingActivities;
   }

   public ArrayList getEndingActivities () {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return endingActivities;
   }

   public Activity getActivity (String Id) {
      return getActivities().getActivity(Id);
   }

   public Transition getTransition (String Id) {
      return getTransitions().getTransition(Id);
   }
   
   public String getName() {
      return get("Name").toValue();
   }

   public void setName(String name) {
      set("Name", name);
   }

   public Activities getActivities() {
      return (Activities)get("Activities");
   }
   public Transitions getTransitions() {
      return (Transitions)get("Transitions");
   }
}
