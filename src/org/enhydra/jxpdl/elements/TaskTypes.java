/**
* Together XPDL Model
* Copyright (C) 2011 Together Teamsolutions Co., Ltd. 
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
public class TaskTypes extends XMLComplexChoice {

   /**
    * Constructs a new object with the given Task as a parent.
    */
	public TaskTypes(Task parent) {
		super(parent, "Type", false);
	}

	protected void fillChoices() {
		choices = new ArrayList();
		choices.add(new TaskApplication(this));
//		choices.add(new TaskService(this));
//		choices.add(new TaskReceive(this));
//		choices.add(new TaskManual(this));
//		choices.add(new TaskReference(this));
		choices.add(new TaskScript(this));
//		choices.add(new TaskSend(this));
//		choices.add(new TaskUser(this));
		choosen = (XMLElement) choices.get(0);
	}

   /** Returns TaskApplication choice element. */
	public TaskApplication getTaskApplication() {
		return (TaskApplication) choices.get(0);
	}

   /** Sets TaskApplication choice element as the chosen one. */
	public void setTaskApplication() {
		setChoosen((TaskApplication) choices.get(0));
	}
	
//	public TaskService getTaskService() {
//		return (TaskService) choices.get(1);
//	}
//
//	public void setTaskService() {
//		setChoosen((TaskService) choices.get(1));
//	}
//	
//	public TaskReceive getTaskReceive() {
//		return (TaskReceive) choices.get(2);
//	}
//
//	public void setTaskReceive() {
//		setChoosen((TaskReceive) choices.get(2));
//	}
//	
//	public TaskManual getTaskManual() {
//		return (TaskManual) choices.get(3);
//	}
//
//	public void setTaskManual() {
//		setChoosen((TaskManual) choices.get(3));
//	}
//	
//	public TaskReference getTaskReference() {
//		return (TaskReference) choices.get(4);
//	}
//
//	public void setTaskReference() {
//		setChoosen((TaskReference) choices.get(4));
//	}
//	
   /** Returns TaskScript choice element. */
	public TaskScript getTaskScript() {
		return (TaskScript) choices.get(1);
	}

   /** Sets TaskScript choice element as the chosen one. */
	public void setTaskScript() {
		setChoosen((TaskScript) choices.get(1));
	}
//	
//	public TaskSend getTaskSend() {
//		return (TaskSend) choices.get(6);
//	}
//
//	public void setTaskSend() {
//		setChoosen((TaskSend) choices.get(6));
//	}
//
//	public TaskUser getTaskUser() {
//		return (TaskUser) choices.get(7);
//	}
//
//	public void setTaskUser() {
//		setChoosen((TaskUser) choices.get(7));
//	}

}
