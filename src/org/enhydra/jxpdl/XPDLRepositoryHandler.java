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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.enhydra.jxpdl.elements.Activities;
import org.enhydra.jxpdl.elements.Activity;
import org.enhydra.jxpdl.elements.ActivitySet;
import org.enhydra.jxpdl.elements.ActivitySets;
import org.enhydra.jxpdl.elements.ActualParameter;
import org.enhydra.jxpdl.elements.ActualParameters;
import org.enhydra.jxpdl.elements.BlockActivity;
import org.enhydra.jxpdl.elements.Condition;
import org.enhydra.jxpdl.elements.Coordinatess;
import org.enhydra.jxpdl.elements.Deadline;
import org.enhydra.jxpdl.elements.DeadlineCondition;
import org.enhydra.jxpdl.elements.Deadlines;
import org.enhydra.jxpdl.elements.ExpressionType;
import org.enhydra.jxpdl.elements.ExtendedAttribute;
import org.enhydra.jxpdl.elements.ExtendedAttributes;
import org.enhydra.jxpdl.elements.Implementation;
import org.enhydra.jxpdl.elements.Join;
import org.enhydra.jxpdl.elements.Namespace;
import org.enhydra.jxpdl.elements.Namespaces;
import org.enhydra.jxpdl.elements.NestedLanes;
import org.enhydra.jxpdl.elements.Package;
import org.enhydra.jxpdl.elements.Performer;
import org.enhydra.jxpdl.elements.SchemaType;
import org.enhydra.jxpdl.elements.Split;
import org.enhydra.jxpdl.elements.TaskApplication;
import org.enhydra.jxpdl.elements.Tool;
import org.enhydra.jxpdl.elements.Tools;
import org.enhydra.jxpdl.elements.Transition;
import org.enhydra.jxpdl.elements.TransitionRestriction;
import org.enhydra.jxpdl.elements.Transitions;
import org.enhydra.jxpdl.elements.WebServiceFaultCatchs;
import org.enhydra.jxpdl.elements.WorkflowProcess;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Provides methods to read XPDL representation from w3c XML Element into the Java Object
 * Model provided with this project, and to write Java Object Model into w3c Document.
 */
public class XPDLRepositoryHandler {

   /** Constant that specifies if the operations will be logged into the console. */
   protected static boolean logging = false;

   /**
    * Specifies the xpdl prefix, if empty no prefixes will be used when Java Object model
    * is transformed into w3c Document.
    */
   protected String xpdlPrefix = "";

   /** Enables/disables xpdl prefix to be written for resulting w3c Document. */
   public void setXPDLPrefixEnabled(boolean enable) {
      if (enable) {
         this.xpdlPrefix = "xpdl:";
      } else {
         this.xpdlPrefix = "";
      }
   }

   /** Returns true if XPDL prefix is to be used. */
   public boolean isXPDLPrefixEnabled() {
      return "xpdl:".equals(this.xpdlPrefix);
   }

   /** Transforms XPDL w3c element into the Java Object model. */
   public void fromXML(Element node, Package pkg) {
      // long t1,t2;
      // t1=System.currentTimeMillis();
      NamedNodeMap attribs = node.getAttributes();
      Namespaces nss = pkg.getNamespaces();
      for (int i = 0; i < attribs.getLength(); i++) {
         Node n = attribs.item(i);
         String nn = n.getNodeName();
         if (nn.startsWith("xmlns:") && !nn.equals("xmlns:xsi")) {
            Namespace ns = (Namespace) nss.generateNewElementWithXPDL1Support();
            ns.setName(nn.substring(6, nn.length()));
            fromXML(n, (XMLAttribute) ns.get("location"));
            nss.add(ns);
         }
      }
      fromXML(node, (XMLComplexElement) pkg);
      // t2=System.currentTimeMillis();
      // System.out.println("MFXML="+(t2-t1));

      // MIGRATION FROM XPDL1
      migrateToXPDL2(pkg);
   }

   /**
    * Transforms XPDL w3c Node into appropriate representation (the instance of class
    * extending XMLCollection) of the Java Object model.
    */
   public void fromXML(Node node, XMLCollection cel) {
      if (node == null || !node.hasChildNodes())
         return;
      String nameSpacePrefix = XMLUtil.getNameSpacePrefix(node);

      XMLElement newOne = cel.generateNewElementWithXPDL1Support();
      String elName = newOne.toName();

      NodeList children = node.getChildNodes();
      int lng = children.getLength();
      if (logging)
         System.out.println("FROMXML for "
                            + cel.toName() + ", c=" + cel.getClass().getName());
      for (int i = 0; i < lng; i++) {
         Node child = children.item(i);
         if (child.getNodeName().equals(nameSpacePrefix + elName)) {
            // System.out.println("I="+i);
            newOne = cel.generateNewElementWithXPDL1Support();
            // System.out.println("Now the collection element of collection
            // "+node.getNodeName()+" will be processed");
            if (newOne instanceof XMLComplexElement) {
               fromXML(children.item(i), (XMLComplexElement) newOne);
            } else {
               fromXML(children.item(i), (XMLSimpleElement) newOne);
            }
            cel.add(newOne);
         } else {
            // System.err.println("Something's wrong with collection "+elName+" parsing -
            // c="+cel.getClass().getName()+" !");
         }
      }
      // System.out.println("The length of collection "+name+" after importing
      // is"+size());
   }

   /**
    * Transforms XPDL w3c Node into appropriate representation (the instance of class
    * extending XMLComplexElement) of the Java Object model.
    */
   public void fromXML(Node node, XMLComplexElement cel) {
      if (node == null || (!node.hasChildNodes() && !node.hasAttributes()))
         return;

      String nameSpacePrefix = node.getPrefix();
      if (nameSpacePrefix != null) {
         nameSpacePrefix += ":";
      } else {
         nameSpacePrefix = "";
      }
      if (logging)
         System.out.println("FROMXML for "
                            + cel.toName() + ", c=" + cel.getClass().getName());

      if (node.hasAttributes()) {
         NamedNodeMap attribs = node.getAttributes();
         for (int i = 0; i < attribs.getLength(); ++i) {
            Node attrib = attribs.item(i);
            try {
               // System.out.println("Getting attrib "+attrib.getNodeName());
               if (!attrib.getNodeName().startsWith("xmlns")
                   && !attrib.getNodeName().startsWith("xsi:")) {
                  fromXML(attrib, (XMLAttribute) cel.get(attrib.getNodeName()));
               }
            } catch (NullPointerException npe) {
               // System.out.println("NPE while processing attrib " +
               // attrib.getNodeName());
               /*
                * System.err.println("Processing attributes for "+ cel.toName() +" element
                * having problems with " + attrib.getNodeName()+" attribute\n" +
                * attrib.getNodeValue().trim());
                */
            } catch (RuntimeException re) {
               System.out.println("RTE while processing attrib " + attrib.getNodeName());
            }
         }
      }
      // getting elements
      if (node.hasChildNodes()) {
         // Specific code for handling Condition element - we don't support Xpression
         // element
         if (cel instanceof Condition) {
            String newVal = node.getChildNodes().item(0).getNodeValue();
            if (newVal != null) {
               cel.setValue(newVal);
            }
         }
         // Specific code for handling ExpressionType element
         if (cel instanceof ExpressionType) {
            String newVal = node.getChildNodes().item(0).getNodeValue();
            if (newVal != null) {
               cel.setValue(newVal);
            }
         }
         // Specific code for handling SchemaType element
         if (cel instanceof SchemaType) {
            NodeList nl = node.getChildNodes();
            for (int j = 0; j < nl.getLength(); j++) {
               Node sn = nl.item(j);
               if (sn instanceof Element) {
                  cel.setValue(XMLUtil.getContent(sn, true));
                  break;
               }
            }
         }
         // Specific code for handling ExtendedAttribute element
         if (cel instanceof ExtendedAttribute) {
            cel.setValue(XMLUtil.getChildNodesContent(node));
         }
         Iterator it = cel.getXMLElements().iterator();
         while (it.hasNext()) {
            XMLElement el = (XMLElement) it.next();
            String elName = el.toName();
            if (el instanceof XMLComplexElement) {
               Node child = XMLUtil.getChildByName(node, nameSpacePrefix + elName);
               fromXML(child, (XMLComplexElement) el);
               // Specific case if element is Deadlines, WebServiceFaultCatch, Coordinates
               // or NestedLanes
            } else if (el instanceof Deadlines
                       || el instanceof WebServiceFaultCatchs
                       || el instanceof Coordinatess || el instanceof NestedLanes) {
               fromXML(node, (XMLCollection) el);
            } else if (el instanceof XMLCollection) {
               Node child = XMLUtil.getChildByName(node, nameSpacePrefix + elName);
               fromXML(child, (XMLCollection) el);
            } else if (el instanceof XMLComplexChoice) {
               fromXML(node, (XMLComplexChoice) el);
            } else if (el instanceof XMLSimpleElement) {
               Node child = XMLUtil.getChildByName(node, nameSpacePrefix + elName);
               fromXML(child, (XMLSimpleElement) el);
            }
         }
      }
   }

   /**
    * Transforms XPDL w3c Node into appropriate representation (the instance of class
    * extending XMLComplexChoice) of the Java Object model.
    */
   public void fromXML(Node node, XMLComplexChoice el) {
      String nameSpacePrefix = XMLUtil.getNameSpacePrefix(node);
      List ch = el.getChoices();
      if (logging)
         System.out.println("FROMXML for "
                            + el.toName() + ", c=" + el.getClass().getName());
      for (int i = 0; i < ch.size(); i++) {
         XMLElement chc = (XMLElement) ch.get(i);
         String chname = chc.toName();
         // Specific code for handling Tools and WebServiceFaultCatch
         if (chname.equals("Tools")) {
            chname = "Tool";
         }
         if (chname.equals("WebServiceFaultCatchs")) {
            chname = "WebServiceFaultCatch";
         }
         Node child = XMLUtil.getChildByName(node, nameSpacePrefix + chname);
         if (child != null) {
            if (chc instanceof XMLComplexElement) {
               fromXML(child, (XMLComplexElement) chc);
            } else { // it is XMLCollection
               // Specific code for handling Tools and WebServiceFaultCache
               if (chc instanceof Tools || chc instanceof WebServiceFaultCatchs) {
                  fromXML(node, (XMLCollection) chc);
               } else {
                  fromXML(child, (XMLCollection) chc);
               }
            }
            el.setChoosen(chc);
            break;
         }
      }
   }

   /**
    * Transforms XPDL w3c Node into appropriate representation (the instance of class
    * extending XMLSimpleElement) of the Java Object model.
    */
   public void fromXML(Node node, XMLSimpleElement el) {
      fromXMLBasic(node, el);
   }

   /**
    * Transforms XPDL w3c Node into appropriate representation (the instance of class
    * XMLAttribute) of the Java Object model.
    */
   public void fromXML(Node node, XMLAttribute el) {
      fromXMLBasic(node, el);
   }

   /**
    * Transforms XPDL w3c Node into appropriate representation (the instance of class
    * extending XMLElement) of the Java Object model.
    */
   public void fromXMLBasic(Node node, XMLElement el) {
      if (node != null) {
         if (logging)
            System.out.println("FROMXML for "
                               + el.toName() + ", c=" + el.getClass().getName());
         String newVal;
         if (node.hasChildNodes()) {
            newVal = node.getChildNodes().item(0).getNodeValue();
            if (logging)
               System.out.println("11111");
            // should never happen
         } else {
            if (logging)
               System.out.println("22222");
            newVal = node.getNodeValue();
         }
         if (logging)
            System.out.println("NV=" + newVal);

         if (newVal != null) {
            el.setValue(newVal);
         }
      }
   }

   /**
    * Transforms XPDL Java Object model into w3c Document element.
    */
   public void toXML(Document parent, Package pkg) {
      Node node = parent.createElement(xpdlPrefix + pkg.toName());
      ((Element) node).setAttribute("xmlns", XMLUtil.XMLNS);
      // ((Element) node).setAttribute("xmlns:xpdl", XMLNS_XPDL);
      // save additional namespaces
      Iterator itNs = pkg.getNamespaces().toElements().iterator();
      while (itNs.hasNext()) {
         Namespace ns = (Namespace) itNs.next();
         ((Element) node).setAttribute("xmlns:" + ns.getName(), ns.getLocation());
      }
      ((Element) node).setAttribute("xmlns:xsi", XMLUtil.XMLNS_XSI);
      ((Element) node).setAttribute("xsi:schemaLocation", XMLUtil.XSI_SCHEMA_LOCATION);

      toXML(node, pkg);
      parent.appendChild(node);
   }

   /**
    * Transforms XPDL Java Object model (the instance of class extending XMLCollection)
    * into w3c Document element.
    */
   public void toXML(Node parent, XMLCollection cel) {
      if (!cel.isEmpty() || cel.isRequired()) {
         if (parent != null) {
            if (logging)
               System.out.println("TOXML for "
                                  + cel.toName() + ", c=" + cel.getClass().getName()
                                  + ", parent=" + cel.getParent() + ", size="
                                  + cel.size());
            String elName = cel.toName();
            Node node = parent;
            // Specific code for handling Deadlines, WebServiceFaultCatch, NestedLanes and
            // Coordinatess
            if (!(elName.equals("Deadlines")
                  || elName.equals("WebServiceFaultCatchs")
                  || elName.equals("NestedLanes") || elName.equals("Coordinatess"))) {
               node = (parent.getOwnerDocument()).createElement(xpdlPrefix + elName);
            }
            for (Iterator it = cel.toElements().iterator(); it.hasNext();) {
               XMLElement el = (XMLElement) it.next();
               if (el instanceof XMLSimpleElement) {
                  toXML(node, (XMLSimpleElement) el);
               } else {
                  toXML(node, (XMLComplexElement) el);
               }
            }
            // If Deadlines, WebServiceFaultCatchs, NestedLanes or Coordinatess are
            // handled, node==parent
            if (node != parent) {
               parent.appendChild(node);
            }
         }
      }
   }

   /**
    * Transforms XPDL Java Object model (the instance of class extending
    * XMLComplexElement) into w3c Document element.
    */
   public void toXML(Node parent, XMLComplexElement cel) {
      if (cel.isEmpty() && !cel.isRequired())
         return;
      if (parent != null) {
         if (logging)
            System.out.println("TOXML for "
                               + cel.toName() + ", c=" + cel.getClass().getName()
                               + ", parent=" + cel.getParent());
         Node node = parent;
         // Specific code for handling Package
         if (!(cel instanceof Package)) {
            node = (parent.getOwnerDocument()).createElement(xpdlPrefix + cel.toName());
         }
         if (cel.toValue() != null && cel.toValue().length() > 0) {
            // Specific code for handling Condition
            if (cel instanceof Condition) {
               if (!cel.toValue().equals("")) {
                  Node textNode = node.getOwnerDocument().createTextNode(cel.toValue());
                  node.appendChild(textNode);
               }
            }
            // Specific code for handling ExpressionType
            if (cel instanceof ExpressionType) {
               if (!cel.toValue().equals("")) {
                  Node textNode = node.getOwnerDocument().createTextNode(cel.toValue());
                  node.appendChild(textNode);
               }
            }
            // Specific code for handling SchemaType
            if (cel instanceof SchemaType) {
               Node schema = XMLUtil.parseSchemaNode(cel.toValue(), false);
               if (schema != null) {
                  node.appendChild(node.getOwnerDocument().importNode(schema, true));
               }
            }
            // Specific code for handling ExtendedAttribute
            if (cel instanceof ExtendedAttribute) {
               try {
                  Node n = XMLUtil.parseExtendedAttributeContent(cel.toValue());
                  NodeList nl = n.getChildNodes();
                  for (int i = 0; i < nl.getLength(); i++) {
                     node.appendChild(parent.getOwnerDocument().importNode(nl.item(i),
                                                                           true));
                  }
               } catch (Exception ex) {
               }
            }
         }
         for (Iterator it = cel.toElements().iterator(); it.hasNext();) {
            XMLElement el = (XMLElement) it.next();
            if (el instanceof XMLComplexElement) {
               toXML(node, (XMLComplexElement) el);
            } else if (el instanceof XMLCollection) {
               toXML(node, (XMLCollection) el);
            } else if (el instanceof XMLComplexChoice) {
               toXML(node, (XMLComplexChoice) el);
            } else if (el instanceof XMLSimpleElement) {
               toXML(node, (XMLSimpleElement) el);
            } else { // it's XMLAttribute
               toXML(node, (XMLAttribute) el);
            }
         }
         // If Package is handled, parent==node
         if (node != parent) {
            parent.appendChild(node);
         }
      }
   }

   /**
    * Transforms XPDL Java Object model (the instance of class extending XMLComplexChoice)
    * into w3c Document element.
    */
   public void toXML(Node parent, XMLComplexChoice el) {
      XMLElement choosen = el.getChoosen();
      if (logging)
         System.out.println("TOXML for "
                            + el.toName() + ", c=" + el.getClass().getName()
                            + ", parent=" + el.getParent());
      if (choosen != null) {
         if (choosen instanceof XMLComplexElement) {
            toXML(parent, (XMLComplexElement) choosen);
         } else {
            // if (choosen.toName().equals("Tools") && ((Tools) choosen).size() == 0) {
            // toXML(parent, ((ImplementationTypes) el).getNo());
            // } else {
            toXML(parent, (XMLCollection) choosen);
            // }
         }
      }
   }

   /**
    * Transforms XPDL Java Object model (the instance of class extending XMLSimpleElement)
    * into w3c Document element.
    */
   public void toXML(Node parent, XMLSimpleElement el) {
      if (!el.isEmpty() || el.isRequired()) {
         if (parent != null) {
            if (logging)
               System.out.println("TOXML for "
                                  + el.toName() + ", c=" + el.getClass().getName()
                                  + ", parent=" + el.getParent() + ", val="
                                  + el.toValue());
            Node node = (parent.getOwnerDocument()).createElement(xpdlPrefix
                                                                  + el.toName());
            node.appendChild(parent.getOwnerDocument()
               .createTextNode(el.toValue().trim()));
            parent.appendChild(node);
         }
      }
   }

   /**
    * Transforms XPDL Java Object model (the instance of XMLAttribute class) into w3c
    * Document element.
    */
   public void toXML(Node parent, XMLAttribute el) {
      if (!el.isEmpty() || el.isRequired()) {
         if (parent != null) {
            if (logging)
               System.out.println("TOXML for "
                                  + el.toName() + ", c=" + el.getClass().getName()
                                  + ", parent=" + el.getParent() + ", val="
                                  + el.toValue());
            Attr node = (parent.getOwnerDocument()).createAttribute(el.toName());
            node.setValue(el.toValue().trim());
            ((Element) parent).setAttributeNode(node);
         }
      }
   }

   /** Migrates Java XPDL Model to XPDL 2 version. */
   public void migrateToXPDL2(Package pkg) {
      List acts = new ArrayList();
      List wps = pkg.getWorkflowProcesses().toElements();
      for (int i = 0; i < wps.size(); i++) {
         WorkflowProcess wp = (WorkflowProcess) wps.get(i);
         acts.addAll(wp.getActivities().toElements());
         List ass = wp.getActivitySets().toElements();
         for (int j = 0; j < ass.size(); j++) {
            ActivitySet as = (ActivitySet) ass.get(j);
            acts.addAll(as.getActivities().toElements());
         }
      }
      List additionalActs = new ArrayList();
      for (int i = 0; i < acts.size(); i++) {
         Activity act = (Activity) acts.get(i);
         additionalActs.addAll(migrateGateways(act));
      }
      acts.addAll(additionalActs);
      additionalActs.clear();
      for (int i = 0; i < acts.size(); i++) {
         Activity act = (Activity) acts.get(i);
         if (act.getActivityTypes().getChoosen() instanceof Implementation
             && act.getActivityTypes()
                .getImplementation()
                .getImplementationTypes()
                .getChoosen() instanceof Tools) {
            additionalActs.addAll(migrateToolAct(act));
         }
      }
      acts.addAll(additionalActs);
      for (int i = 0; i < acts.size(); i++) {
         Activity act = (Activity) acts.get(i);
         act.getActivityTypes()
            .getImplementation()
            .getImplementationTypes()
            .removeXPDL1Support();
         List dls = act.getDeadlines().toElements();
         for (int j = 0; j < dls.size(); j++) {
            Deadline d = (Deadline) dls.get(j);
            DeadlineCondition dc = (DeadlineCondition) d.get("DeadlineCondition");
            if (!"".equals(dc.toValue())) {
               d.setDeadlineDuration(dc.toValue());
            }
            d.removeXPDL1Support();
         }
         String pf = act.get("Performer").toValue();
         if (!"".equals(pf)) {
            Performer perf = (Performer) act.getPerformers().generateNewElement();
            perf.setValue(pf);
            act.getPerformers().add(perf);
         }
         act.removeXPDL1Support();
      }
      List nss = pkg.getNamespaces().toElements();
      for (int i = 0; i < nss.size(); i++) {
         Namespace ns = (Namespace) nss.get(i);
         if ("xpdl".equals(ns.getName())) {
            ns.setLocation(XMLUtil.XMLNS_XPDL);
         }
      }
      migrateActivitySetIds(pkg);
      migrateActivityIds(pkg);
      XMLUtil.correctSplitsAndJoins(pkg);
      pkg.removeXPDL1Support();
   }

   /** Migrates Tool activity from XPDL 1 Model into XPDL 2 model. */
   protected List migrateToolAct(Activity act) {
      List additionalActs = new ArrayList();
      Tools ts = (Tools) act.getActivityTypes()
         .getImplementation()
         .getImplementationTypes()
         .getChoosen();
      if (ts.size() > 1) {
         String fm = act.getFinishMode();
         Set outTras = XMLUtil.getNonExceptionalOutgoingTransitions(act);
         Transition circular = null;
         Iterator it = outTras.iterator();
         while (it.hasNext()) {
            Transition t = (Transition) it.next();
            if (t.getFrom().equals(t.getTo())) {
               circular = t;
               break;
            }
         }
         act.setFinishModeNONE();
         Activity prevAct = act;
         for (int i = 1; i < ts.size(); i++) {
            Activity actN = splitActivity(prevAct, false, true);
            additionalActs.add(actN);
            prevAct = actN;
         }
         if (!fm.equals("")) {
            prevAct.set("FinishMode", fm);
         }
         if (circular != null) {
            circular.setFrom(prevAct.getId());
         }
      }
      for (int i = 0; i < additionalActs.size(); i++) {
         handleTool((Activity) additionalActs.get(i), (Tool) ts.get(i + 1));
      }
      handleTool(act, (Tool) ts.get(0));
      return additionalActs;

   }

   /**
    * Migrates Route activity, and activities with inappropriate Split/Join types into
    * from XPDL 1 Model into Route (with appropriate gateway attribute) activity from XPDL
    * 2 model.
    */
   protected List migrateGateways(Activity act) {
      List additionalActs = new ArrayList();
      int actType = act.getActivityType();
      Join j = XMLUtil.getJoin(act);
      String jType = j != null ? j.getType() : XPDLConstants.JOIN_SPLIT_TYPE_NONE;
      Split s = XMLUtil.getSplit(act);

      Set outTras = XMLUtil.getNonExceptionalOutgoingTransitions(act);
      String sType = (s != null && outTras.size() > 1) ? s.getType()
                                                      : XPDLConstants.JOIN_SPLIT_TYPE_NONE;

      if (actType == XPDLConstants.ACTIVITY_TYPE_ROUTE) {
         if (!sType.equals(jType)
             && !sType.equals(XPDLConstants.JOIN_SPLIT_TYPE_NONE)
             && !jType.equals(XPDLConstants.JOIN_SPLIT_TYPE_NONE)) {
            // split route activity into two activities
            Activity actN = splitActivity(act, true, true);
            additionalActs.add(actN);
         }
      } else {
         Transition circular = null;
         Iterator it = outTras.iterator();
         while (it.hasNext()) {
            Transition t = (Transition) it.next();
            if (t.getFrom().equals(t.getTo())) {
               circular = t;
               break;
            }
         }
         String cf = null;
         String ct = null;
         if (jType.equals(XPDLConstants.JOIN_SPLIT_TYPE_PARALLEL)) {
            Activity actN = splitActivity(act, true, false);
            additionalActs.add(actN);
            ct = actN.getId();

         }
         if (sType.equals(XPDLConstants.JOIN_SPLIT_TYPE_EXCLUSIVE)) {
            Activity actN = splitActivity(act, true, true);
            additionalActs.add(actN);
            cf = actN.getId();
         }
         if (circular != null) {
            if (cf != null) {
               circular.setFrom(cf);
            }
            if (ct != null) {
               circular.setTo(ct);
            }
         }
      }
      return additionalActs;

   }

   /** Handles Tool element of the from XPDL 1 Model into XPDL 2 model. */
   protected void handleTool(Activity act, Tool t) {
      act.getActivityTypes().getImplementation().getImplementationTypes().setTask();
      act.getActivityTypes()
         .getImplementation()
         .getImplementationTypes()
         .getTask()
         .getTaskTypes()
         .setTaskApplication();
      TaskApplication ta = act.getActivityTypes()
         .getImplementation()
         .getImplementationTypes()
         .getTask()
         .getTaskTypes()
         .getTaskApplication();
      ta.setId(t.getId());
      ta.setDescription(t.getDescription());
      ActualParameters aps = ta.getActualParameters();
      List apsList = t.getActualParameters().toElements();
      for (int j = 0; j < apsList.size(); j++) {
         ActualParameter ap = (ActualParameter) apsList.get(j);
         ActualParameter apn = (ActualParameter) aps.generateNewElement();
         apn.setValue(ap.toValue());
         aps.add(apn);
      }
   }

   /**
    * Migrates the Ids of ActivitySet elements from XPDL 1 Model into XPDL 2 model (have
    * to be unique within the whole Package).
    */
   protected void migrateActivitySetIds(Package pkg) {
      List ass = new ArrayList();
      Iterator wps = pkg.getWorkflowProcesses().toElements().iterator();
      while (wps.hasNext()) {
         WorkflowProcess wp = (WorkflowProcess) wps.next();
         ass.addAll(wp.getActivitySets().toElements());
      }
      for (int i = 0; i < ass.size(); i++) {
         ActivitySet as = (ActivitySet) ass.get(i);
         boolean isUnique = XMLUtil.isIdUnique(as, as.getId());
         if (!isUnique) {
            String newId = XMLUtil.generateSimilarOrIdenticalUniqueId((ActivitySets) as.getParent(),
                                                                      new HashSet(),
                                                                      as.getId());
            List refs = XMLUtil.getReferences(XMLUtil.getWorkflowProcess(as), as);
            for (int j = 0; j < refs.size(); j++) {
               BlockActivity ba = (BlockActivity) refs.get(j);
               ba.setActivitySetId(newId);
            }
            ExtendedAttributes eas = XMLUtil.getWorkflowProcess(as)
               .getExtendedAttributes();
            String eaname = "JaWE_GRAPH_START_OF_BLOCK";
            List l = eas.getElementsForName(eaname);
            eaname = "JaWE_GRAPH_END_OF_BLOCK";
            l.addAll(eas.getElementsForName(eaname));
            // System.out.println("Found " + l.size() + " eas with name " + eaname +
            // " for wp "
            // + wp.getId() + ", as=" + asId);
            Iterator it = l.iterator();
            while (it.hasNext()) {
               ExtendedAttribute ea = (ExtendedAttribute) it.next();
               String sedstr = ea.getVValue();
               // System.out.println("EA for BA = " + sedstr);
               String[] startOrEndD = XMLUtil.tokenize(sedstr, ",");
               int ind = startOrEndD[0].indexOf("ACTIVITY_SET_ID=");
               String asetId = startOrEndD[0].substring(ind
                                                        + ("ACTIVITY_SET_ID=").length());
               if (!asetId.equals(as.getId()))
                  continue;
               sedstr = "ACTIVITY_SET_ID="
                        + newId + sedstr.substring(sedstr.indexOf(","));
               ea.setVValue(sedstr);
            }
            l.clear();
            eaname = "JaWE_GRAPH_BLOCK_PARTICIPANT_ORDER";
            l.addAll(eas.getElementsForName(eaname));
            it = l.iterator();
            while (it.hasNext()) {
               ExtendedAttribute ea = (ExtendedAttribute) it.next();
               String sedstr = ea.getVValue();
               // System.out.println("EA for BA = " + sedstr);
               String[] vord = XMLUtil.tokenize(sedstr, ";");

               String asetId = vord[0];
               if (!asetId.equals(as.getId()))
                  continue;
               sedstr = newId + sedstr.substring(sedstr.indexOf(";"));
               ea.setVValue(sedstr);
            }
            as.setId(newId);
         }
      }
   }

   /**
    * Migrates the Ids of Activity elements from XPDL 1 Model into XPDL 2 model (have to
    * be unique within the whole Package).
    */
   protected void migrateActivityIds(Package pkg) {
      List acts = new ArrayList();
      Iterator wps = pkg.getWorkflowProcesses().toElements().iterator();
      while (wps.hasNext()) {
         WorkflowProcess wp = (WorkflowProcess) wps.next();
         acts.addAll(wp.getActivities().toElements());
         ActivitySets actSets = wp.getActivitySets();
         for (int y = 0; y < actSets.size(); y++) {
            ActivitySet actSet = (ActivitySet) actSets.get(y);
            acts.addAll(actSet.getActivities().toElements());
         }

      }
      for (int i = 0; i < acts.size(); i++) {
         Activity act = (Activity) acts.get(i);
         boolean isUnique = XMLUtil.isIdUnique(act, act.getId());
         if (!isUnique) {
            String newId = XMLUtil.generateSimilarOrIdenticalUniqueId((Activities) act.getParent(),
                                                                      new HashSet(),
                                                                      act.getId());
            List refs = XMLUtil.getReferences(act);
            for (int j = 0; j < refs.size(); j++) {
               XMLAttribute toOrFrom = (XMLAttribute) refs.get(j);
               toOrFrom.setValue(newId);
            }
            ExtendedAttributes eas = XMLUtil.getWorkflowProcess(act)
               .getExtendedAttributes();
            List l = new ArrayList();
            if (act.getParent().getParent() instanceof ActivitySet) {
               String eaname = "JaWE_GRAPH_START_OF_BLOCK";
               l.addAll(eas.getElementsForName(eaname));
               eaname = "JaWE_GRAPH_END_OF_BLOCK";
               l.addAll(eas.getElementsForName(eaname));
            } else {
               String eaname = "JaWE_GRAPH_START_OF_WORKFLOW";
               l.addAll(eas.getElementsForName(eaname));
               eaname = "JaWE_GRAPH_END_OF_WORKFLOW";
               l.addAll(eas.getElementsForName(eaname));
            }
            // System.out.println("Found " + l.size() + " eas with name " + eaname +
            // " for wp "
            // + wp.getId() + ", as=" + asId);
            int ca = (act.getParent().getParent() instanceof ActivitySet) ? 2 : 1;
            Iterator it = l.iterator();
            while (it.hasNext()) {
               ExtendedAttribute ea = (ExtendedAttribute) it.next();
               String sedstr = ea.getVValue();
               // System.out.println("EA for BA = " + sedstr);
               String[] startOrEndD = XMLUtil.tokenize(sedstr, ",");
               int ind = startOrEndD[ca].indexOf("CONNECTING_ACTIVITY_ID=");
               String actId = startOrEndD[ca].substring(ind
                                                        + ("CONNECTING_ACTIVITY_ID=").length());
               if (!actId.equals(act.getId()))
                  continue;
               String newsedstr = "";
               for (int j = 0; j < startOrEndD.length; j++) {
                  if (j != ca) {
                     newsedstr += startOrEndD[j];
                  } else {
                     newsedstr += "CONNECTING_ACTIVITY_ID=" + newId;
                  }
                  if (j < startOrEndD.length - 1) {
                     newsedstr += ",";
                  }
               }
               ea.setVValue(newsedstr);
            }
            act.setId(newId);
         }
      }
   }

   /**
    * Splits activity with inappropriate Split/Join combination from XPDL 1 Model into 2
    * activities from XPDL 2 model (the new activity is Route activity with appropriate
    * Gateway attribute).
    */
   protected Activity splitActivity(Activity act,
                                    boolean createRouteActivity,
                                    boolean createAfter) {
      Activities acs = (Activities) act.getParent();
      Activity actN = null;
      if (createRouteActivity) {
         actN = (Activity) acs.generateNewElementWithXPDL1Support();
         actN.getActivityTypes().setRoute();
         for (int i = 0; i < act.getTransitionRestrictions().size(); i++) {
            TransitionRestriction tr = (TransitionRestriction) act.getTransitionRestrictions()
               .get(i);
            TransitionRestriction trr = (TransitionRestriction) actN.getTransitionRestrictions()
               .generateNewElementWithXPDL1Support();
            trr.makeAs(tr);
            actN.getTransitionRestrictions().add(trr);
         }
         for (int i = 0; i < act.getExtendedAttributes().size(); i++) {
            ExtendedAttribute ea = (ExtendedAttribute) act.getExtendedAttributes().get(i);
            if (ea.getName().equals("JaWE_GRAPH_OFFSET")
                || ea.getName().equals("JaWE_GRAPH_PARTICIPANT_ID")) {
               ExtendedAttribute ear = (ExtendedAttribute) actN.getExtendedAttributes()
                  .generateNewElementWithXPDL1Support();
               ear.makeAs(ea);
               actN.getExtendedAttributes().add(ear);
            }
         }
      } else {
         actN = (Activity) acs.generateNewElementWithXPDL1Support();
         actN.makeAs(act);
         actN.getDeadlines().clear();
         actN.setStartModeNONE();
         actN.setFinishModeNONE();
      }
      actN.setId(XMLUtil.generateSimilarOrIdenticalUniqueId(acs,
                                                            new HashSet(),
                                                            act.getId()));
      if (createAfter) {
         Join j = XMLUtil.getJoin(actN);
         Split s = XMLUtil.getSplit(act);
         if (j != null) {
            j.setTypeNONE();
            if (act.getActivityType() == XPDLConstants.ACTIVITY_TYPE_ROUTE) {
               act.getActivityTypes()
                  .getRoute()
                  .getGatewayTypeAttribute()
                  .setValue(XMLUtil.getJoin(act).getType());
            }
         }
         if (s != null) {
            s.setTypeNONE();
         }
         if (actN.getActivityType() == XPDLConstants.ACTIVITY_TYPE_ROUTE) {
            actN.getActivityTypes()
               .getRoute()
               .getGatewayTypeAttribute()
               .setValue(XMLUtil.getSplit(actN).getType());
         }
      } else {
         Split s = XMLUtil.getSplit(actN);
         Join j = XMLUtil.getJoin(act);
         if (s != null) {
            s.setTypeNONE();
            if (act.getActivityType() == XPDLConstants.ACTIVITY_TYPE_ROUTE) {
               act.getActivityTypes()
                  .getRoute()
                  .getGatewayTypeAttribute()
                  .setValue(XMLUtil.getSplit(act).getType());
            }
         }
         if (j != null) {
            j.setTypeNONE();
         }
         if (actN.getActivityType() == XPDLConstants.ACTIVITY_TYPE_ROUTE) {
            actN.getActivityTypes()
               .getRoute()
               .getGatewayTypeAttribute()
               .setValue(XMLUtil.getJoin(actN).getType());
         }
      }
      acs.add(actN);

      Iterator it = null;
      if (createAfter) {
         it = XMLUtil.getNonExceptionalOutgoingTransitions(act).iterator();
      } else {
         it = XMLUtil.getIncomingTransitions(act).iterator();
      }
      while (it.hasNext()) {
         Transition t = (Transition) it.next();
         if (!t.getFrom().equals(t.getTo())) {
            if (createAfter) {
               t.setFrom(actN.getId());
            } else {
               t.setTo(actN.getId());
            }
         }
      }
      Transitions tras = (Transitions) ((XMLComplexElement) acs.getParent()).get("Transitions");
      Transition tra = (Transition) tras.generateNewElement();
      tra.setId(XMLUtil.generateUniqueId(tras, new HashSet()));
      if (createAfter) {
         tra.setFrom(act.getId());
         tra.setTo(actN.getId());
      } else {
         tra.setFrom(actN.getId());
         tra.setTo(act.getId());
      }
      tras.add(tra);

      return actN;
   }
}
