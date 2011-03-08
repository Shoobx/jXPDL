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

package org.enhydra.jxpdl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Structure representing info for the change of some XMLElement.
 * 
 * @author Sasa Bojanic
 */
public class XMLElementChangeInfo {

   /** The constant that defines the change "action" type UPDATE. */
   public static final int UPDATED = 1;

   /** The constant that defines the change "action" type INSERT. */
   public static final int INSERTED = 3;

   /** The constant that defines the change "action" type REMOVE. */
   public static final int REMOVED = 5;

   /** The constant that defines the change "action" type REPOSITION. */
   public static final int REPOSITIONED = 7;

   /** The map holding the action types (integer value) as keys and action name (String) as values. */
   private static Map actionToNameMap = new HashMap();
   static {
      actionToNameMap.put(new Integer(XMLElementChangeInfo.UPDATED), "UPDATED");
      actionToNameMap.put(new Integer(XMLElementChangeInfo.INSERTED), "INSERTED");
      actionToNameMap.put(new Integer(XMLElementChangeInfo.REMOVED), "REMOVED");
      actionToNameMap.put(new Integer(XMLElementChangeInfo.REPOSITIONED), "REPOSITIONED");
   }

   /** The main element that changed */
   protected XMLElement changedElement;

   /** The old value for the changed element */
   protected Object oldValue;

   /** The new value for the changed element */
   protected Object newValue;

   /** The list of changed sub-elements that changed for the main element */
   protected List changedSubElements = new ArrayList();

   /** The action type for this object */
   protected int action;

   /** Returns the action type for this change. */
   public int getAction() {
      return this.action;
   }

   /** Sets the action type for this change. */
   public void setAction(int action) {
      this.action = action;
   }

   /** Returns the list of changed sub-elements for this change. */
   public List getChangedSubElements() {
      return new ArrayList(this.changedSubElements);
   }

   /** Sets the list of changed sub-elements for this change. */
   public void setChangedSubElements(List changedSubElements) {
      if (changedSubElements != null) {
         this.changedSubElements = new ArrayList(changedSubElements);
      }
   }

   /** Returns the new value for this change. */
   public Object getNewValue() {
      return this.newValue;
   }

   /** Sets the new value for this change. */
   public void setNewValue(Object newValue) {
      this.newValue = newValue;
   }

   /** Returns the old value for this change. */
   public Object getOldValue() {
      return this.oldValue;
   }

   /** Sets the old value for this change. */
   public void setOldValue(Object oldValue) {
      this.oldValue = oldValue;
   }

   /** Returns the changed element for this change. */
   public XMLElement getChangedElement() {
      return this.changedElement;
   }

   /** Sets the changed element for this change. */
   public void setChangedElement(XMLElement changedElement) {
      this.changedElement = changedElement;
   }

   /** Returns the action name for this change. */
   public String getActionName() {
      return (String) XMLElementChangeInfo.actionToNameMap.get(new Integer(action));
   }

   public String toString() {
      String ret = "Action=" + getActionName() + ", Changed element=";
      if (changedElement instanceof XMLCollectionElement) {
         ret += ((XMLCollectionElement) changedElement).getId();
      } else if (changedElement != null) {
         ret += changedElement.toName();
      } else {
         ret += "null";
      }
      if (changedElement != null) {
         if (changedElement.getParent() != null) {
            ret += ", parent=" + changedElement.getParent().getClass().getName();
         } else {
            ret += ", parent=null";
         }
      }
      ret += ", OldV=" + oldValue + ", NewV=" + newValue;
      return ret;
   }
}
