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

import org.enhydra.jxpdl.XMLAttribute;
import org.enhydra.jxpdl.XMLCollectionElement;
import org.enhydra.jxpdl.XMLInterface;

/**
 * Represents corresponding element from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class Transition extends XMLCollectionElement {

   /** Activity referenced by 'To' attribute of this transition object. */
   protected transient Activity toActivity;

   /** Activity referenced by 'From' attribute of this transition object. */
   protected transient Activity fromActivity;

   /**
    * Constructs a new object with the given Transitions as a parent.
    */
   public Transition(Transitions parent) {
      super(parent, true);
   }

   protected void fillStructure() {
      XMLAttribute attrFrom = new XMLAttribute(this, "From", true); // required
      XMLAttribute attrTo = new XMLAttribute(this, "To", true); // required
      XMLAttribute attrName = new XMLAttribute(this, "Name", false);
      Condition refCondition = new Condition(this); // min==0
      Description refDescription = new Description(this); // min=0
      ExtendedAttributes refExtendedAttributes = new ExtendedAttributes(this); // min=0

      ConnectorGraphicsInfos refConnectorGraphicsInfos = new ConnectorGraphicsInfos(this); // min=0

      super.fillStructure();
      add(attrName);
      add(attrFrom);
      add(attrTo);
      add(refCondition);
      add(refDescription);
      add(refExtendedAttributes);
      add(refConnectorGraphicsInfos);
   }

   public void initCaches(XMLInterface xmli) {
      super.initCaches(xmli);
      Activities acts;
      if (getParent().getParent() instanceof WorkflowProcess) {
         acts = ((WorkflowProcess) getParent().getParent()).getActivities();
      } else {
         acts = ((ActivitySet) getParent().getParent()).getActivities();
      }
      toActivity = acts.getActivity(getTo());
      fromActivity = acts.getActivity(getFrom());
   }

   public void clearCaches() {
      toActivity = null;
      fromActivity = null;
      super.clearCaches();
   }

   /**
    * Returns Activity referenced by 'To' attribute of this transition object. This method
    * can be used only when element's mode is read-only, otherwise RuntimeException will
    * be thrown.
    */
   public Activity getToActivity() {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return toActivity;
   }

   /**
    * Returns Activity referenced by 'From' attribute of this transition object. This method
    * can be used only when element's mode is read-only, otherwise RuntimeException will
    * be thrown.
    */
   public Activity getFromActivity() {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      return fromActivity;
   }

   /** Returns the From attribute value of this object. */
   public String getFrom() {
      return get("From").toValue();
   }

   /** Sets the From attribute value of this object. */
   public void setFrom(String from) {
      set("From", from);
   }

   /** Returns the To attribute value of this object. */
   public String getTo() {
      return get("To").toValue();
   }

   /** Sets the To attribute value of this object. */
   public void setTo(String to) {
      set("To", to);
   }

   /** Returns the Name attribute value of this object. */
   public String getName() {
      return get("Name").toValue();
   }

   /** Sets the Name attribute value of this object. */
   public void setName(String name) {
      set("Name", name);
   }

   /** Returns the Description attribute value of this object. */
   public String getDescription() {
      return get("Description").toValue();
   }

   /** Sets the Description attribute value of this object. */
   public void setDescription(String description) {
      set("Description", description);
   }

   /** Returns the Condition sub-element of this object. */
   public Condition getCondition() {
      return (Condition) get("Condition");
   }

   /** Returns the ExtendedAttributes sub-element of this object. */
   public ExtendedAttributes getExtendedAttributes() {
      return (ExtendedAttributes) get("ExtendedAttributes");
   }

   /** Returns the ConnectorGraphicsInfos sub-element of this object. */
   public ConnectorGraphicsInfos getConnectorGraphicsInfos() {
      return (ConnectorGraphicsInfos) get("ConnectorGraphicsInfos");
   }
}
