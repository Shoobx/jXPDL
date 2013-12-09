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

   protected transient ArrayList outgoingTransitions;

   protected transient ArrayList incomingTransitions;

   protected transient ArrayList exceptionalOutgoingTransitions;

   protected transient ArrayList nonExceptionalOutgoingTransitions;

   protected transient ArrayList exceptionalIncomingTransitions;

   protected transient ArrayList nonExceptionalIncomingTransitions;

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

   protected void clearInternalCaches() {
      outgoingTransitions = new ArrayList();
      incomingTransitions = new ArrayList();
      exceptionalOutgoingTransitions = new ArrayList();
      nonExceptionalOutgoingTransitions = new ArrayList();
      exceptionalIncomingTransitions = new ArrayList();
      nonExceptionalIncomingTransitions = new ArrayList();
   }

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

   public ArrayList getOutgoingTransitions() {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return outgoingTransitions;
   }

   public ArrayList getIncomingTransitions() {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return incomingTransitions;
   }

   public ArrayList getNonExceptionalOutgoingTransitions() {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return nonExceptionalOutgoingTransitions;
   }

   public ArrayList getExceptionalOutgoingTransitions() {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return exceptionalOutgoingTransitions;
   }

   public ArrayList getNonExceptionalIncomingTransitions() {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return nonExceptionalIncomingTransitions;
   }

   public ArrayList getExceptionalIncomingTransitions() {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return exceptionalIncomingTransitions;
   }

   public boolean isAndTypeSplit() {
      return XMLUtil.isANDTypeSplitOrJoin(this, 0);
   }

   public boolean isAndTypeJoin() {
      return XMLUtil.isANDTypeSplitOrJoin(this, 1);
   }

   public int getActivityStartMode() {
      return XMLUtil.getStartMode(this);
   }

   public int getActivityFinishMode() {
      return XMLUtil.getFinishMode(this);
   }

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

   public boolean isSubflowSynchronous() {
      if (getActivityType() != XPDLConstants.ACTIVITY_TYPE_SUBFLOW) {
         throw new RuntimeException("The activity type is not SubFlow!");
      }
      return XMLUtil.isSubflowSynchronous(this);
   }

   public String getName() {
      return get("Name").toValue();
   }

   public void setName(String name) {
      set("Name", name);
   }

   public String getStartMode() {
      return get("StartMode").toValue();
   }

   public void setStartModeNONE() {
      get("StartMode").setValue(XPDLConstants.ACTIVITY_MODE_NONE);
   }

   public void setStartModeAUTOMATIC() {
      get("StartMode").setValue(XPDLConstants.ACTIVITY_MODE_AUTOMATIC);
   }

   public void setStartModeMANUAL() {
      get("StartMode").setValue(XPDLConstants.ACTIVITY_MODE_MANUAL);
   }

   public String getFinishMode() {
      return get("FinishMode").toValue();
   }

   public void setFinishModeNONE() {
      get("FinishMode").setValue(XPDLConstants.ACTIVITY_MODE_NONE);
   }

   public void setFinishModeAUTOMATIC() {
      get("FinishMode").setValue(XPDLConstants.ACTIVITY_MODE_AUTOMATIC);
   }

   public void setFinishModeMANUAL() {
      get("FinishMode").setValue(XPDLConstants.ACTIVITY_MODE_MANUAL);
   }

   public Deadlines getDeadlines() {
      return (Deadlines) get("Deadlines");
   }

   public String getDescription() {
      return get("Description").toValue();
   }

   public void setDescription(String description) {
      set("Description", description);
   }

   public String getDocumentation() {
      return get("Documentation").toValue();
   }

   public void setDocumentation(String documentation) {
      set("Documentation", documentation);
   }

   public ExtendedAttributes getExtendedAttributes() {
      return (ExtendedAttributes) get("ExtendedAttributes");
   }

   public NodeGraphicsInfos getNodeGraphicsInfos() {
      return (NodeGraphicsInfos) get("NodeGraphicsInfos");
   }

   public String getIcon() {
      return get("Icon").toValue();
   }

   public void setIcon(String icon) {
      set("Icon", icon);
   }

   public String getLimit() {
      return get("Limit").toValue();
   }

   public void setLimit(String limit) {
      set("Limit", limit);
   }

   public Performers getPerformers() {
      return (Performers) get("Performers");
   }

   public String getFirstPerformer() {
      Iterator it = getPerformers().toElements().iterator();
      while (it.hasNext()) {
         Performer perf = (Performer) it.next();
         return perf.toValue();
      }
      return "";
   }

   public Performer getFirstPerformerObj() {
      Iterator it = getPerformers().toElements().iterator();
      while (it.hasNext()) {
         Performer perf = (Performer) it.next();
         return perf;
      }
      return null;
   }

   public Performer createFirstPerformerObj() {
      Performer perf = (Performer) getPerformers().generateNewElement();
      getPerformers().add(perf);
      return perf;
   }

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

   public String getPriority() {
      return get("Priority").toValue();
   }

   public void setPriority(String priority) {
      set("Priority", priority);
   }

   public SimulationInformation getSimulationInformation() {
      return (SimulationInformation) get("SimulationInformation");
   }

   public TransitionRestrictions getTransitionRestrictions() {
      return (TransitionRestrictions) get("TransitionRestrictions");
   }

   public ActivityTypes getActivityTypes() {
      return (ActivityTypes) get("Type");
   }

   // MIGRATION FROM XPDL1
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
