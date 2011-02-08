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
   public static final int UPDATED=1;
   public static final int INSERTED=3;
   public static final int REMOVED=5;
   public static final int REPOSITIONED=7;

   private static Map actionToNameMap=new HashMap();
   static {
      actionToNameMap.put(new Integer(XMLElementChangeInfo.UPDATED), "UPDATED");
      actionToNameMap.put(new Integer(XMLElementChangeInfo.INSERTED), "INSERTED");
      actionToNameMap.put(new Integer(XMLElementChangeInfo.REMOVED), "REMOVED");
      actionToNameMap.put(new Integer(XMLElementChangeInfo.REPOSITIONED), "REPOSITIONED");
   }

   protected XMLElement changedElement;
   protected Object oldValue;
   protected Object newValue;
   protected List changedSubElements=new ArrayList();
   protected int action;
   
   public int getAction() {
      return this.action;
   }
   public void setAction(int action) {
      this.action = action;
   }
   
   public List getChangedSubElements() {
      return new ArrayList(this.changedSubElements);
   }
   public void setChangedSubElements(List changedSubElements) {
      if (changedSubElements!=null) {
         this.changedSubElements = new ArrayList(changedSubElements);
      }
   }
   
   public Object getNewValue() {
      return this.newValue;
   }
   public void setNewValue(Object newValue) {
      this.newValue = newValue;
   }
   
   public Object getOldValue() {
      return this.oldValue;
   }
   public void setOldValue(Object oldValue) {
      this.oldValue = oldValue;
   }
   
   public XMLElement getChangedElement() {
      return this.changedElement;
   }
   public void setChangedElement(XMLElement changedElement) {
      this.changedElement = changedElement;
   }

   public String getActionName () {
      return (String)XMLElementChangeInfo.actionToNameMap.get(new Integer(action));
   }
      
   public String toString (){
      String ret="Action="+getActionName()+", Changed element=";
      if (changedElement instanceof XMLCollectionElement) {
         ret+=((XMLCollectionElement)changedElement).getId();
      } else if (changedElement!=null) {
         ret+=changedElement.toName();
      } else {
         ret+="null";
      }
      if (changedElement!=null) {
         if (changedElement.getParent()!=null) {
            ret+=", parent="+changedElement.getParent().getClass().getName();
         } else {
            ret+=", parent=null";
         }
      }
      ret+=", OldV="+oldValue+", NewV="+newValue;
      return ret;      
   }
}

