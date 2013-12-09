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
import org.enhydra.jxpdl.XMLElement;
import org.enhydra.jxpdl.XMLInterface;
import org.enhydra.jxpdl.XMLUtil;
import org.enhydra.jxpdl.XPDLConstants;

/**
 * Represents corresponding element from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class Activity extends XMLCollectionElement {

   /** The "cached" list of this activity's outgoing transitions. */
   protected transient ArrayList outgoingTransitions;

   /** The "cached" list of this activity's incoming transitions. */
   protected transient ArrayList incomingTransitions;

   /** The "cached" list of this activity's exceptional outgoing transitions. */
   protected transient ArrayList exceptionalOutgoingTransitions;

   /** The "cached" list of this activity's non-exceptional outgoing transitions. */
   protected transient ArrayList nonExceptionalOutgoingTransitions;

   /** The "cached" list of this activity's exceptional incoming transitions. */
   protected transient ArrayList exceptionalIncomingTransitions;

   /** The "cached" list of this activity's non-exceptional incoming transitions. */
   protected transient ArrayList nonExceptionalIncomingTransitions;

   /**
    * Constructs a new object with the given Activities as a parent. It can be specified
    * if object will have XPDL 1 support or not.
    * 
    * @param acts {@link Activities} instance.
    * @param xpdl1support true if object will have XPDL 1 support.
    */
   public Activity(Activities acts, boolean xpdl1support) {
      super(acts, true, xpdl1support);
   }

   protected void fillStructure() {
      XMLAttribute attrName = new XMLAttribute(this, "Name", false);
      XMLAttribute attrStartMode = new XMLAttribute(this,
                                                    "StartMode",
                                                    false,
                                                    new String[] {
                                                          XPDLConstants.ACTIVITY_MODE_NONE,
                                                          XPDLConstants.ACTIVITY_MODE_AUTOMATIC,
                                                          XPDLConstants.ACTIVITY_MODE_MANUAL
                                                    },
                                                    0);
      XMLAttribute attrFinishMode = new XMLAttribute(this,
                                                     "FinishMode",
                                                     false,
                                                     new String[] {
                                                           XPDLConstants.ACTIVITY_MODE_NONE,
                                                           XPDLConstants.ACTIVITY_MODE_AUTOMATIC,
                                                           XPDLConstants.ACTIVITY_MODE_MANUAL
                                                     },
                                                     0);
      Description refDescription = new Description(this); // min=0
      Limit refLimit = new Limit(this); // min=0
      // can be Route, BlockActivity or Implementation
      ActivityTypes refType = new ActivityTypes(this);
      Performers refPerformers = new Performers(this);// min=0
      Performer refPerformer = new Performer(this);// min=0
      Priority refPriority = new Priority(this); // min=0
      // we use Deadlines instead of Deadline
      Deadlines refDeadlines = new Deadlines(this, xpdl1support); // min=0
      SimulationInformation refSimulationInformation = new SimulationInformation(this); // min=0
      Icon refIcon = new Icon(this); // min=0
      Documentation refDocumentation = new Documentation(this); // min=0
      TransitionRestrictions refTransitionRestrictions = new TransitionRestrictions(this); // min=0
      ExtendedAttributes refExtendedAttributes = new ExtendedAttributes(this); // min=0
      NodeGraphicsInfos refNodeGraphicInfos = new NodeGraphicsInfos(this); // min=0

      // MIGRATION FROM XPDL1, put it before any other elements so elementMap gets
      // overriden by corresponding attributes
      if (xpdl1support) {
         StartMode refStartMode = new StartMode(this); // min=0
         FinishMode refFinishMode = new FinishMode(this); // min=0
         add(refStartMode);
         add(refFinishMode);
      }
      super.fillStructure();
      add(attrName);
      add(attrStartMode);
      add(attrFinishMode);
      add(refDescription);
      add(refLimit);
      add(refType);
      add(refPerformers);
      if (xpdl1support) {
         add(refPerformer);
      }
      add(refPriority);
      add(refDeadlines);
      add(refSimulationInformation);
      add(refIcon);
      add(refDocumentation);
      add(refTransitionRestrictions);
      add(refExtendedAttributes);
      add(refNodeGraphicInfos);
   }

   public void initCaches(XMLInterface xmli) {
      super.initCaches(xmli);
      Transitions ts;
      if (getParent().getParent() instanceof WorkflowProcess) {
         ts = ((WorkflowProcess) getParent().getParent()).getTransitions();
      } else {
         ts = ((ActivitySet) getParent().getParent()).getTransitions();
      }
      TransitionRestrictions trs = getTransitionRestrictions();
      ArrayList trefs = null;
      if (trs.size() > 0) {
         trefs = ((TransitionRestriction) trs.get(0)).getSplit()
            .getTransitionRefs()
            .toElements();
      } else {
         trefs = new ArrayList();
      }

      Iterator it = trefs.iterator();
      while (it.hasNext()) {
         TransitionRef tref = (TransitionRef) it.next();
         Transition t = ts.getTransition(tref.getId());
         outgoingTransitions.add(t);
         putTransitionInTheRightList(t, true);
      }
      it = ts.toElements().iterator();
      while (it.hasNext()) {
         Transition t = (Transition) it.next();
         if (!outgoingTransitions.contains(t) && t.getFrom().equals(getId())) {
            outgoingTransitions.add(t);
            putTransitionInTheRightList(t, true);
         }
         if (t.getTo().equals(getId())) {
            incomingTransitions.add(t);
            putTransitionInTheRightList(t, false);
         }
      }
   }

   public void clearCaches() {
      clearInternalCaches();
      super.clearCaches();
   }

   /** Clears the "cached" lists of various transitions for this activity. */
   protected void clearInternalCaches() {
      outgoingTransitions = new ArrayList();
      incomingTransitions = new ArrayList();
      exceptionalOutgoingTransitions = new ArrayList();
      nonExceptionalOutgoingTransitions = new ArrayList();
      exceptionalIncomingTransitions = new ArrayList();
      nonExceptionalIncomingTransitions = new ArrayList();
   }

   /**
    * Puts the given Transition into the right "cache" list.
    * 
    * @param t {@link Transition} instance.
    * @param outg true if this is outgoing transition.
    */
   protected void putTransitionInTheRightList(Transition t, boolean outg) {
      Condition condition = t.getCondition();
      String condType = condition.getType();
      if (condType.equals(XPDLConstants.CONDITION_TYPE_EXCEPTION)
          || condType.equals(XPDLConstants.CONDITION_TYPE_DEFAULTEXCEPTION)) {
         if (outg) {
            exceptionalOutgoingTransitions.add(t);
         } else {
            exceptionalIncomingTransitions.add(t);
         }
      } else {
         if (outg) {
            nonExceptionalOutgoingTransitions.add(t);
         } else {
            nonExceptionalIncomingTransitions.add(t);
         }
      }
   }

   /**
    * Returns the list of {@link Transition} elements which are outgoing for this
    * activity. It can be used if this element's mode is read-only mode, otherwise it
    * throws RuntimeException.
    * 
    * @return The list of {@link Transition} elements.
    */
   public ArrayList getOutgoingTransitions() {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return outgoingTransitions;
   }

   /**
    * Returns the list of {@link Transition} elements which are incoming for this
    * activity. It can be used if this element's mode is read-only mode, otherwise it
    * throws RuntimeException.
    * 
    * @return The list of {@link Transition} elements.
    */
   public ArrayList getIncomingTransitions() {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return incomingTransitions;
   }

   /**
    * Returns the list of {@link Transition} elements which are non-exceptional and
    * outgoing for this activity. It can be used if this element's mode is read-only mode,
    * otherwise it throws RuntimeException.
    * 
    * @return The list of {@link Transition} elements.
    */
   public ArrayList getNonExceptionalOutgoingTransitions() {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return nonExceptionalOutgoingTransitions;
   }

   /**
    * Returns the list of {@link Transition} elements which are exceptional and outgoing
    * for this activity. It can be used if this element's mode is read-only mode,
    * otherwise it throws RuntimeException.
    * 
    * @return The list of {@link Transition} elements.
    */
   public ArrayList getExceptionalOutgoingTransitions() {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return exceptionalOutgoingTransitions;
   }

   /**
    * Returns the list of {@link Transition} elements which are non-exceptional and
    * incoming for this activity. It can be used if this element's mode is read-only mode,
    * otherwise it throws RuntimeException.
    * 
    * @return The list of {@link Transition} elements.
    */
   public ArrayList getNonExceptionalIncomingTransitions() {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return nonExceptionalIncomingTransitions;
   }

   /**
    * Returns the list of {@link Transition} elements which are exceptional and outgoing
    * for this activity. It can be used if this element's mode is read-only mode,
    * otherwise it throws RuntimeException.
    * 
    * @return The list of {@link Transition} elements.
    */
   public ArrayList getExceptionalIncomingTransitions() {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return exceptionalIncomingTransitions;
   }

   /**
    * Returns true if this Activity's Split type is Parallel, or if there is no Split at
    * all.
    * 
    * @return true if {@link Split} type is Parallel.
    */
   public boolean isAndTypeSplit() {
      return XMLUtil.isANDTypeSplitOrJoin(this, 0);
   }

   /**
    * Returns true if this Activity's Join type is Parallel, or if there is no Join at
    * all.
    * 
    * @return true if {@link Join} type is Parallel.
    */
   public boolean isAndTypeJoin() {
      return XMLUtil.isANDTypeSplitOrJoin(this, 1);
   }

   /**
    * Returns the number representing the start mode of the activity (1-Manual,
    * 0-Automatic or no mode defined).
    * 
    * @return the number representing start mode.
    */
   public int getActivityStartMode() {
      return XMLUtil.getStartMode(this);
   }

   /**
    * Returns the number representing the finish mode of the activity (1-Manual,
    * 0-Automatic or no mode defined).
    * 
    * @return the number representing finish mode.
    */
   public int getActivityFinishMode() {
      return XMLUtil.getFinishMode(this);
   }

   /**
    * Returns the number representing the activity type (see XPDLConstants class for
    * different activity type constants).
    * 
    * @return the number representing type of activity.
    */
   public int getActivityType() {
      XMLElement ch = getActivityTypes().getChoosen();
      if (ch instanceof Route) {
         return XPDLConstants.ACTIVITY_TYPE_ROUTE;
      } else if (ch instanceof Implementation) {
         ch = ((Implementation) ch).getImplementationTypes().getChoosen();
         if (ch instanceof SubFlow) {
            return XPDLConstants.ACTIVITY_TYPE_SUBFLOW;
         } else if (ch instanceof Task) {
            Object cht = ((Task) ch).getTaskTypes().getChoosen();
            if (cht instanceof TaskService) {
               return XPDLConstants.ACTIVITY_TYPE_TASK_SERVICE;
            } else if (cht instanceof TaskReceive) {
               return XPDLConstants.ACTIVITY_TYPE_TASK_RECEIVE;
            } else if (cht instanceof TaskManual) {
               return XPDLConstants.ACTIVITY_TYPE_TASK_MANUAL;
            } else if (cht instanceof TaskReference) {
               return XPDLConstants.ACTIVITY_TYPE_TASK_REFERENCE;
            } else if (cht instanceof TaskScript) {
               return XPDLConstants.ACTIVITY_TYPE_TASK_SCRIPT;
            } else if (cht instanceof TaskSend) {
               return XPDLConstants.ACTIVITY_TYPE_TASK_SEND;
            } else if (cht instanceof TaskUser) {
               return XPDLConstants.ACTIVITY_TYPE_TASK_USER;
            } else {
               return XPDLConstants.ACTIVITY_TYPE_TASK_APPLICATION;
            }
         } else if (ch instanceof Reference) {
            return XPDLConstants.ACTIVITY_TYPE_REFERENCE;
         } else {
            return XPDLConstants.ACTIVITY_TYPE_NO;
         }
      } else if (ch instanceof Event) {
         Object che = ((Event) ch).getEventTypes().getChoosen();
         if (che instanceof StartEvent) {
            return XPDLConstants.ACTIVITY_TYPE_EVENT_START;
         } else {
            return XPDLConstants.ACTIVITY_TYPE_EVENT_END;
         }
      } else {
         return XPDLConstants.ACTIVITY_TYPE_BLOCK;
      }

   }

   /**
    * Returns true if this is sub-flow activity type and its execution is Synchronous. If
    * it is not a sub-flow activity, RuntimeException is thrown.
    * 
    * @return true if this is sub-flow activity and {@link SubFlow} execution is
    *         Synchronous.
    */
   public boolean isSubflowSynchronous() {
      if (getActivityType() != XPDLConstants.ACTIVITY_TYPE_SUBFLOW) {
         throw new RuntimeException("The activity type is not SubFlow!");
      }
      return XMLUtil.isSubflowSynchronous(this);
   }

   /**
    * Returns the Name attribute value of this object.
    * 
    * @return The name.
    */
   public String getName() {
      return get("Name").toValue();
   }

   /**
    * Sets the Name attribute value of this object.
    * 
    * @param name The name
    */
   public void setName(String name) {
      set("Name", name);
   }

   /**
    * Returns the StartMode attribute value of this object.
    * 
    * @return The StartMode.
    */
   public String getStartMode() {
      return get("StartMode").toValue();
   }

   /**
    * Sets the StartMode attribute value of this object to an empty string.
    */
   public void setStartModeNONE() {
      get("StartMode").setValue(XPDLConstants.ACTIVITY_MODE_NONE);
   }

   /** Sets the StartMode attribute value of this object to Automatic. */
   public void setStartModeAUTOMATIC() {
      get("StartMode").setValue(XPDLConstants.ACTIVITY_MODE_AUTOMATIC);
   }

   /** Sets the StartMode attribute value of this object to Manual. */
   public void setStartModeMANUAL() {
      get("StartMode").setValue(XPDLConstants.ACTIVITY_MODE_MANUAL);
   }

   /** Returns the FinishMode attribute value of this object.
    * 
    * @return The FinishMode.
    */
   public String getFinishMode() {
      return get("FinishMode").toValue();
   }

   /** Sets the FinishMode attribute value of this object to an empty string. */
   public void setFinishModeNONE() {
      get("FinishMode").setValue(XPDLConstants.ACTIVITY_MODE_NONE);
   }

   /** Sets the FinishMode attribute value of this object to Automatic. */
   public void setFinishModeAUTOMATIC() {
      get("FinishMode").setValue(XPDLConstants.ACTIVITY_MODE_AUTOMATIC);
   }

   /** Sets the FinishMode attribute value of this object to Manual. */
   public void setFinishModeMANUAL() {
      get("FinishMode").setValue(XPDLConstants.ACTIVITY_MODE_MANUAL);
   }

   /** Returns the Deadlines sub-element of this object. */
   public Deadlines getDeadlines() {
      return (Deadlines) get("Deadlines");
   }

   /** Returns the Description attribute value of this object. */
   public String getDescription() {
      return get("Description").toValue();
   }

   /** Sets the Description attribute value of this object. */
   public void setDescription(String description) {
      set("Description", description);
   }

   /** Returns the Documentation attribute value of this object. */
   public String getDocumentation() {
      return get("Documentation").toValue();
   }

   /** Sets the Documentation attribute value of this object. */
   public void setDocumentation(String documentation) {
      set("Documentation", documentation);
   }

   /** Returns the ExtendedAttributes sub-element of this object. */
   public ExtendedAttributes getExtendedAttributes() {
      return (ExtendedAttributes) get("ExtendedAttributes");
   }

   /** Returns the NodeGraphicsInfos sub-element of this object. */
   public NodeGraphicsInfos getNodeGraphicsInfos() {
      return (NodeGraphicsInfos) get("NodeGraphicsInfos");
   }

   /** Returns the Icon attribute value of this object. */
   public String getIcon() {
      return get("Icon").toValue();
   }

   /** Sets the Icon attribute value of this object. */
   public void setIcon(String icon) {
      set("Icon", icon);
   }

   /** Returns the Limit attribute value of this object. */
   public String getLimit() {
      return get("Limit").toValue();
   }

   /** Sets the Limit attribute value of this object. */
   public void setLimit(String limit) {
      set("Limit", limit);
   }

   /** Returns the Performers sub-element of this object. */
   public Performers getPerformers() {
      return (Performers) get("Performers");
   }

   /** Returns the value of the first Performer object from the Performers collection. */
   public String getFirstPerformer() {
      Iterator it = getPerformers().toElements().iterator();
      while (it.hasNext()) {
         Performer perf = (Performer) it.next();
         return perf.toValue();
      }
      return "";
   }

   /** Returns the first Performer object from the Performers collection. */
   public Performer getFirstPerformerObj() {
      Iterator it = getPerformers().toElements().iterator();
      while (it.hasNext()) {
         Performer perf = (Performer) it.next();
         return perf;
      }
      return null;
   }

   /** Creates the first Performer object and puts it into the Performers collection. */
   public Performer createFirstPerformerObj() {
      Performer perf = (Performer) getPerformers().generateNewElement();
      getPerformers().add(perf);
      return perf;
   }

   /** Sets the value of the first Performer object from the Performers collection. */
   public void setFirstPerformer(String performer) {
      // if ("".equals(performer)) {
      // getPerformers().clear();
      // } else {
      Performer perf = getFirstPerformerObj();
      if (perf == null) {
         perf = createFirstPerformerObj();
      }
      perf.setValue(performer);
      // }
   }

   /** Returns the Priority attribute value of this object. */
   public String getPriority() {
      return get("Priority").toValue();
   }

   /** Sets the Priority attribute value of this object. */
   public void setPriority(String priority) {
      set("Priority", priority);
   }

   /** Returns the SimulationInformation sub-element of this object. */
   public SimulationInformation getSimulationInformation() {
      return (SimulationInformation) get("SimulationInformation");
   }

   /** Returns the TransitionRestriction sub-element of this object. */
   public TransitionRestrictions getTransitionRestrictions() {
      return (TransitionRestrictions) get("TransitionRestrictions");
   }

   /** Returns the ActivityTypes sub-element of this object. */
   public ActivityTypes getActivityTypes() {
      return (ActivityTypes) get("Type");
   }

   /**
    * Removes StartMode and FinishMode elements from this element's structure due to
    * migration to XPDL 2 where these elements are now attributes of the Activity.
    */
   protected void removeStartFinishModes() {
      XMLElement sm = null;
      XMLElement fm = null;
      for (int j = 0; j < elements.size(); j++) {
         Object el = elements.get(j);
         if (el instanceof StartMode) {
            sm = (StartMode) el;
         } else if (el instanceof FinishMode) {
            fm = (FinishMode) el;
         }
      }
      if (sm != null) {
         int ind = XMLUtil.indexOfXMLElementWithinList(elements, sm);
         if (ind >= 0) {
            elements.remove(ind);
         }
      }
      if (fm != null) {
         int ind = XMLUtil.indexOfXMLElementWithinList(elements, fm);
         if (ind >= 0) {
            elements.remove(ind);
         }
      }
   }

   /**
    * Removes Performer element from this element's structure due to migration to XPDL 2
    * where this element is deprecated..
    */
   protected void removePerformer() {
      XMLElement perf = get("Performer");
      if (perf != null) {
         elements.remove(perf);
         elementMap.remove("Performer");
      }
   }

   public void removeXPDL1Support() {
      super.removeXPDL1Support();
      removePerformer();
      removeStartFinishModes();
   }
}
