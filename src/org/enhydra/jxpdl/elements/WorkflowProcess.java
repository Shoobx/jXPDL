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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.enhydra.jxpdl.XMLAttribute;
import org.enhydra.jxpdl.XMLCollectionElement;
import org.enhydra.jxpdl.XMLInterface;
import org.enhydra.jxpdl.XMLUtil;
import org.enhydra.jxpdl.XPDLConstants;

/**
 * Represents corresponding element from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class WorkflowProcess extends XMLCollectionElement {

   /** The "cached" list of this WorkflowProcess's starting activities. */
   protected transient ArrayList startingActivities;

   /** The "cached" list of this WorkflowProcess's ending activities. */
   protected transient ArrayList endingActivities;

   /**
    * The "cached" list of this WorkflowProcess's variables (the keys are DataField and
    * FormalParameter Ids, the values are DataField and FormalParameter objects).
    */
   protected transient Map allVariables;

   /**
    * Constructs a new object with the given WorkflowProcesses as a parent.
    */
   public WorkflowProcess(WorkflowProcesses parent) {
      super(parent, true);
   }

   protected void fillStructure() {
      XMLAttribute attrName = new XMLAttribute(this, "Name", false);
      XMLAttribute attrAccessLevel = new XMLAttribute(this,
                                                      "AccessLevel",
                                                      false,
                                                      new String[] {
                                                            XPDLConstants.ACCESS_LEVEL_NONE,
                                                            XPDLConstants.ACCESS_LEVEL_PUBLIC,
                                                            XPDLConstants.ACCESS_LEVEL_PRIVATE
                                                      },
                                                      0);
      ProcessHeader refProcessHeader = new ProcessHeader(this);
      RedefinableHeader refRedefinableHeader = new RedefinableHeader(this); // min=0
      FormalParameters refFormalParameters = new FormalParameters(this);
      Participants refParticipants = new Participants(this); // min=0
      Applications refApplications = new Applications(this); // min=0
      DataFields refDataFields = new DataFields(this); // min=0
      ActivitySets refActivitySets = new ActivitySets(this); // min=0
      Activities refActivities = new Activities(this); // min=0
      Transitions refTransitions = new Transitions(this); // min=0
      ExtendedAttributes refExtendedAttributes = new ExtendedAttributes(this); // min=0

      super.fillStructure();
      add(attrName);
      add(attrAccessLevel);
      add(refProcessHeader);
      add(refRedefinableHeader);
      add(refFormalParameters);
      add(refParticipants);
      add(refApplications);
      add(refDataFields);
      add(refActivitySets);
      add(refActivities);
      add(refTransitions);
      add(refExtendedAttributes);
   }

   public void initCaches(XMLInterface xmli) {
      super.initCaches(xmli);
      getAllVariables();
      Iterator it = getActivities().toElements().iterator();
      while (it.hasNext()) {
         Activity act = (Activity) it.next();
         ArrayList trsI = act.getIncomingTransitions();
         ArrayList trsNEO = act.getNonExceptionalOutgoingTransitions();
         // the activity is starting one if it has no input transitions ...
         if (trsI.size() == 0) {
            startingActivities.add(act);
            // or there is a one input transition, but it is a selfreference
         } else if (trsI.size() == 1) {
            Transition t = (Transition) trsI.get(0);
            if (t.getFrom().equals(t.getTo())) {
               startingActivities.add(act);
            }
         }
         if (trsNEO.size() == 0) {
            endingActivities.add(act);
         } else if (trsNEO.size() == 1) {
            Transition t = (Transition) trsNEO.get(0);
            if (t.getFrom().equals(t.getTo())) {
               endingActivities.add(act);
            }
         }
      }
   }

   public void clearCaches() {
      startingActivities = new ArrayList();
      endingActivities = new ArrayList();
      allVariables = new HashMap();
      super.clearCaches();
   }

   /**
    * Returns the "cached" list of Activity elements which are starting for this
    * WorkflowProcess. It can be used if this element's mode is read-only mode, otherwise
    * it throws RuntimeException.
    */
   public ArrayList getStartingActivities() {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return startingActivities;
   }

   /**
    * Returns the "cached" list of Activity elements which are ending for this
    * WorkflowProcess. It can be used if this element's mode is read-only mode, otherwise
    * it throws RuntimeException.
    */
   public ArrayList getEndingActivities() {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return endingActivities;
   }

   /**
    * Returns a Map of all WorkflowProcess and Package DataFields, as well as all
    * WorkflowProcess FormalParameters. The Map keys are the Ids of DataField and
    * FormalParameter, and values are the DataField and FormalParameter objects.
    */
   public Map getAllVariables() {
      if (allVariables == null || allVariables.size() == 0) {
         allVariables = new HashMap();
         Iterator it = getDataFields().toElements().iterator();
         while (it.hasNext()) {
            DataField df = (DataField) it.next();
            allVariables.put(df.getId(), df);
         }
         it = XMLUtil.getPackage(this).getDataFields().toElements().iterator();
         while (it.hasNext()) {
            DataField df = (DataField) it.next();
            if (!allVariables.containsKey(df.getId())) {
               allVariables.put(df.getId(), df);
            }
         }
         it = getFormalParameters().toElements().iterator();
         while (it.hasNext()) {
            FormalParameter fp = (FormalParameter) it.next();
            if (!allVariables.containsKey(fp.getId())) {
               allVariables.put(fp.getId(), fp);
            }
         }
      }
      Map toRet = new HashMap(allVariables);
      if (!isReadOnly) {
         allVariables.clear();
      }
      return toRet;
   }

   /**
    * Returns the Application object (the member of this element's Applications
    * collection) with specified Id.
    */
   public Application getApplication(String Id) {
      return getApplications().getApplication(Id);
   }

   /**
    * Returns the Participant object (the member of this element's Participants
    * collection) with specified Id.
    */
   public Participant getParticipant(String Id) {
      return getParticipants().getParticipant(Id);
   }

   /**
    * Returns the DataField object (the member of this element's DataFields collection)
    * with specified Id.
    */
   public DataField getDataField(String Id) {
      return getDataFields().getDataField(Id);
   }

   /**
    * Returns the FormalParameter object (the member of this element's FormalParameters
    * collection) with specified Id.
    */
   public FormalParameter getFormalParameter(String Id) {
      return getFormalParameters().getFormalParameter(Id);
   }

   /**
    * Returns the ActivitySet object (the member of this element's ActivitySets
    * collection) with specified Id.
    */
   public ActivitySet getActivitySet(String Id) {
      return getActivitySets().getActivitySet(Id);
   }

   /**
    * Returns the Activity object (the member of this element's Activities collection or
    * the Activities collection of any of this element's ActivitySet) with specified Id.
    */
   public Activity getActivity(String Id) {
      Activity act = getActivities().getActivity(Id);
      if (act == null) {
         Iterator it = getActivitySets().toElements().iterator();
         while (it.hasNext()) {
            ActivitySet as = (ActivitySet) it.next();
            act = as.getActivity(Id);
            if (act != null) {
               break;
            }
         }
      }
      return act;
   }

   /**
    * Returns the Transition object (the member of this element's Transitions collection
    * or the Transitions collection of any of this element's ActivitySet) with specified
    * Id.
    */
   public Transition getTransition(String Id) {
      Transition tra = getTransitions().getTransition(Id);
      if (tra == null) {
         Iterator it = getActivitySets().toElements().iterator();
         while (it.hasNext()) {
            ActivitySet as = (ActivitySet) it.next();
            tra = as.getTransition(Id);
            if (tra != null) {
               break;
            }
         }
      }
      return tra;
   }

   /** Returns the Name attribute value of this object. */
   public String getName() {
      return get("Name").toValue();
   }

   /** Sets the Name attribute value of this object. */
   public void setName(String name) {
      set("Name", name);
   }

   /** Returns the AccessLevel attribute of this object. */
   public XMLAttribute getAccessLevelAttribute() {
      return (XMLAttribute) get("AccessLevel");
   }

   /** Returns the AccessLevel attribute value of this object. */
   public String getAccessLevel() {
      return getAccessLevelAttribute().toValue();
   }

   /** Sets the AccessLevel attribute value of this object to an empty string. */
   public void setAccessLevelNONE() {
      getAccessLevelAttribute().setValue(XPDLConstants.ACCESS_LEVEL_NONE);
   }

   /** Sets the AccessLevel attribute value of this object to PUBLIC. */
   public void setAccessLevelPUBLIC() {
      getAccessLevelAttribute().setValue(XPDLConstants.ACCESS_LEVEL_PUBLIC);
   }

   /** Sets the AccessLevel attribute value of this object to PRIVATE. */
   public void setAccessLevelPRIVATE() {
      getAccessLevelAttribute().setValue(XPDLConstants.ACCESS_LEVEL_PRIVATE);
   }

   /** Returns the Applications sub-element of this object. */
   public Applications getApplications() {
      return (Applications) get("Applications");
   }

   /** Returns the DataFields sub-element of this object. */
   public DataFields getDataFields() {
      return (DataFields) get("DataFields");
   }

   /** Returns the ExtendedAttributes sub-element of this object. */
   public ExtendedAttributes getExtendedAttributes() {
      return (ExtendedAttributes) get("ExtendedAttributes");
   }

   /** Returns the ProcessHeader sub-element of this object. */
   public ProcessHeader getProcessHeader() {
      return (ProcessHeader) get("ProcessHeader");
   }

   /** Returns the Participants sub-element of this object. */
   public Participants getParticipants() {
      return (Participants) get("Participants");
   }

   /** Returns the RedefinableHeader sub-element of this object. */
   public RedefinableHeader getRedefinableHeader() {
      return (RedefinableHeader) get("RedefinableHeader");
   }

   /** Returns the Activities sub-element of this object. */
   public Activities getActivities() {
      return (Activities) get("Activities");
   }

   /** Returns the Transitions sub-element of this object. */
   public Transitions getTransitions() {
      return (Transitions) get("Transitions");
   }

   /** Returns the ActivitySets sub-element of this object. */
   public ActivitySets getActivitySets() {
      return (ActivitySets) get("ActivitySets");
   }

   /** Returns the FormalParameters sub-element of this object. */
   public FormalParameters getFormalParameters() {
      return (FormalParameters) get("FormalParameters");
   }

}
