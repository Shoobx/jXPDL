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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import javax.tools.Tool;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.util.XMLChar;
import org.enhydra.jxpdl.elements.Activities;
import org.enhydra.jxpdl.elements.Activity;
import org.enhydra.jxpdl.elements.ActivitySet;
import org.enhydra.jxpdl.elements.ActivitySets;
import org.enhydra.jxpdl.elements.ActualParameter;
import org.enhydra.jxpdl.elements.Application;
import org.enhydra.jxpdl.elements.Applications;
import org.enhydra.jxpdl.elements.ArrayType;
import org.enhydra.jxpdl.elements.Artifact;
import org.enhydra.jxpdl.elements.Artifacts;
import org.enhydra.jxpdl.elements.Association;
import org.enhydra.jxpdl.elements.Associations;
import org.enhydra.jxpdl.elements.BasicType;
import org.enhydra.jxpdl.elements.BlockActivity;
import org.enhydra.jxpdl.elements.DataField;
import org.enhydra.jxpdl.elements.DataFields;
import org.enhydra.jxpdl.elements.DataTypes;
import org.enhydra.jxpdl.elements.Deadline;
import org.enhydra.jxpdl.elements.DeclaredType;
import org.enhydra.jxpdl.elements.ExtendedAttribute;
import org.enhydra.jxpdl.elements.ExtendedAttributes;
import org.enhydra.jxpdl.elements.ExternalPackage;
import org.enhydra.jxpdl.elements.ExternalReference;
import org.enhydra.jxpdl.elements.FormalParameter;
import org.enhydra.jxpdl.elements.FormalParameters;
import org.enhydra.jxpdl.elements.Join;
import org.enhydra.jxpdl.elements.Lane;
import org.enhydra.jxpdl.elements.Lanes;
import org.enhydra.jxpdl.elements.ListType;
import org.enhydra.jxpdl.elements.Member;
import org.enhydra.jxpdl.elements.NestedLane;
import org.enhydra.jxpdl.elements.NestedLanes;
import org.enhydra.jxpdl.elements.NodeGraphicsInfo;
import org.enhydra.jxpdl.elements.Package;
import org.enhydra.jxpdl.elements.Participant;
import org.enhydra.jxpdl.elements.Participants;
import org.enhydra.jxpdl.elements.Performer;
import org.enhydra.jxpdl.elements.Pool;
import org.enhydra.jxpdl.elements.Pools;
import org.enhydra.jxpdl.elements.RecordType;
import org.enhydra.jxpdl.elements.RedefinableHeader;
import org.enhydra.jxpdl.elements.Responsible;
import org.enhydra.jxpdl.elements.Responsibles;
import org.enhydra.jxpdl.elements.SchemaType;
import org.enhydra.jxpdl.elements.Split;
import org.enhydra.jxpdl.elements.SubFlow;
import org.enhydra.jxpdl.elements.TaskApplication;
import org.enhydra.jxpdl.elements.Transition;
import org.enhydra.jxpdl.elements.TransitionRef;
import org.enhydra.jxpdl.elements.TransitionRefs;
import org.enhydra.jxpdl.elements.TransitionRestriction;
import org.enhydra.jxpdl.elements.TransitionRestrictions;
import org.enhydra.jxpdl.elements.Transitions;
import org.enhydra.jxpdl.elements.TypeDeclaration;
import org.enhydra.jxpdl.elements.TypeDeclarations;
import org.enhydra.jxpdl.elements.UnionType;
import org.enhydra.jxpdl.elements.WorkflowProcess;
import org.enhydra.jxpdl.elements.WorkflowProcesses;
import org.enhydra.jxpdl.utilities.SequencedHashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Class with utilities to read/write XPDLs from/to XML.
 * 
 * @author Sasa Bojanic
 * @author Danijel Predarski
 */
public class XMLUtil {

   public final static String XMLNS = "http://www.wfmc.org/2008/XPDL2.1";

   public final static String XMLNS_XPDL = "http://www.wfmc.org/2008/XPDL2.1";

   public final static String XMLNS_XSI = "http://www.w3.org/2001/XMLSchema-instance";

   public final static String XSI_SCHEMA_LOCATION = "http://www.wfmc.org/2008/XPDL2.1 http://www.wfmc.org/standards/docs/bpmnxpdl_31.xsd";

   public static Map basicTypesMap = new HashMap();
   static {
      basicTypesMap.put("BOOLEAN", "java.lang.Boolean");
      basicTypesMap.put("STRING", "java.lang.String");
      basicTypesMap.put("INTEGER", "java.lang.Long");
      basicTypesMap.put("FLOAT", "java.lang.Double");
      basicTypesMap.put("DATETIME", "java.util.Date");
   }

   /**
    * Determines the number of string toFind within string toSearch.
    */
   public static int howManyStringsWithinString(String toSearch, String toFind) {
      try {
         int startAt = 0;
         int howMany = 0;

         int fnd;
         while ((fnd = toSearch.indexOf(toFind, startAt)) != -1) {
            howMany++;
            startAt = (fnd + toFind.length());
         }
         return howMany;
      } catch (Exception ex) {
         return -1;
      }
   }

   public static String getCanonicalPath(String relpath,
                                         String basedir,
                                         boolean canBeDirectory) {
      try {
         File f = null;
         if (basedir == null || basedir.equals("")) {
            f = new File(relpath);
            if (!f.isAbsolute()) {
               f = f.getAbsoluteFile();
               if (!f.exists()) {
                  f = new File(XMLUtil.createPath(basedir, relpath));
               }
            }
         } else {
            f = new File(XMLUtil.createPath(basedir, relpath));
         }
         if (!f.exists() || (f.isDirectory() && !canBeDirectory)) {
            System.err.println("The file " + f.getAbsolutePath() + " does not exist");
            return null;
         }

         return getCanonicalPath(f);
      } catch (Exception ex) {
         ex.printStackTrace();
         return null;
      }
   }

   public static String getCanonicalPath(String path, boolean canBeDirectory) {
      File f = new File(path);
      if (!f.isAbsolute()) {
         f = new File(System.getProperty("user.dir") + File.separator + path);
      }
      if (!f.exists() || (f.isDirectory() && !canBeDirectory)) {
         System.err.println("The file " + f.getAbsolutePath() + " does not exist");
         return null;
      }

      return getCanonicalPath(f);
   }

   private static String getCanonicalPath(File f) {
      try {
         return f.getCanonicalPath();
      } catch (Exception ex) {
         return f.getAbsolutePath();
      }
   }

   public static String getNameSpacePrefix(Node node) {
      String nameSpacePrefix = node.getPrefix();
      if (nameSpacePrefix != null) {
         nameSpacePrefix += ":";
      } else {
         nameSpacePrefix = "";
      }
      return nameSpacePrefix;
   }

   public static Node getChildByName(Node parent, String childName) {
      NodeList children = parent.getChildNodes();
      for (int i = 0; i < children.getLength(); ++i) {
         Node child = children.item(i);
         if (child.getNodeName().equals(childName)) {
            return child;
         }
      }
      return null;
   }

   public static String getId(Node node) {
      try {
         NamedNodeMap nnm = node.getAttributes();
         Node attrib = nnm.getNamedItem("Id");
         Object ID;
         if (attrib.hasChildNodes()) {
            ID = attrib.getChildNodes().item(0).getNodeValue();
         } else {
            ID = attrib.getNodeValue();
         }
         return ID.toString();
      } catch (Exception ex) {
         return "";
      }
   }

   public static synchronized String getIdFromFile(String xmlFile) {
      try {
         // Create parser
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         factory.setValidating(false);
         DocumentBuilder parser = factory.newDocumentBuilder();
         Document document = null;

         // Parse the Document
         try {
            // document=parser.parse(xmlFile);
            File f = new File(xmlFile);
            if (!f.exists()) {
               f = new File(f.getCanonicalPath());
            }
            document = parser.parse(new InputSource(new FileInputStream(f))); // Fixed by
                                                                              // Harald
                                                                              // Meister
         } catch (Exception ex) {
            document = parser.parse(new InputSource(new StringReader(xmlFile)));
         }
         return XMLUtil.getId(document.getDocumentElement());
      } catch (Exception ex) {
         return "";
      } finally {

      }
   }

   public static String getContent(Node node, boolean omitXMLDeclaration) {
      try {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();

         // Use a Transformer for output
         TransformerFactory tFactory = TransformerFactory.newInstance();
         Transformer transformer = tFactory.newTransformer();
         transformer.setOutputProperty("indent", "yes");
         transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
         transformer.setOutputProperty("encoding", "UTF-8");
         if (omitXMLDeclaration) {
            transformer.setOutputProperty("omit-xml-declaration", "yes");
         }

         DOMSource source = new DOMSource(node);
         StreamResult result = new StreamResult(baos);
         transformer.transform(source, result);

         String cont = baos.toString("UTF8");

         baos.close();
         return cont;
      } catch (Exception ex) {
         return "";
      }
   }

   public static String getChildNodesContent(Node node) {
      String txt = "";
      if (node != null) {
         if (node.hasChildNodes()) {
            txt = XMLUtil.getContent(node, true);
            try {
               Node fc = node.getFirstChild();
               String fcnc = XMLUtil.getContent(fc, true);
               String closedTag = "</" + node.getNodeName() + ">";
               if (fcnc.trim().length() > 0) {
                  fcnc = fcnc.trim();
               }

               int i1, i2;
               i1 = txt.indexOf(fcnc);
               i2 = txt.lastIndexOf(closedTag);
               txt = txt.substring(i1, i2).trim();
            } catch (Exception ex) {
               NodeList nl = node.getChildNodes();
               txt = "";
               try {
                  for (int i = 0; i < nl.getLength(); i++) {
                     Node sn = nl.item(i);
                     if (sn instanceof Element) {
                        txt += XMLUtil.getContent(sn, true);
                     } else {
                        String nv = sn.getNodeValue();
                        // trim only the begining of the string
                        if (i > 0) {
                           txt += nv.substring(1);
                        } else if (i == 0 && nv.trim().length() == 0) {
                           continue;
                        } else {
                           txt += nv;
                        }
                     }
                  }
               } catch (Exception ex2) {
               }
            }
         }
      }
      return txt;
   }

   public static String getShortClassName(String fullClassName) {
      int lastDot = fullClassName.lastIndexOf(".");
      if (lastDot >= 0) {
         return fullClassName.substring(lastDot + 1, fullClassName.length());
      }
      return fullClassName;
   }

   public static String getExternalPackageId(String extPkgHref) {
      // System.out.println("EPID1="+extPkgHref);
      int indBSL = extPkgHref.lastIndexOf("\\");
      int indSL = extPkgHref.lastIndexOf("/");
      int indDotXPDL = extPkgHref.lastIndexOf(".xpdl");
      if (indSL != -1 || indBSL != -1) {
         int ind = indSL;
         if (indBSL > indSL) {
            ind = indBSL;
         }
         extPkgHref = extPkgHref.substring(ind + 1);
      }
      if (indDotXPDL != -1) {
         extPkgHref = extPkgHref.substring(0, extPkgHref.length() - 5);
      }
      // System.out.println("EPID2="+extPkgHref);
      return extPkgHref;
   }

   public static Node parseSchemaNode(String toParse, boolean isFile) {
      Document document = null;

      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setValidating(false);

      // Parse the Document and traverse the DOM
      try {
         ParsingErrors pErrors = new ParsingErrors();

         DocumentBuilder parser = factory.newDocumentBuilder();
         parser.setErrorHandler(pErrors);
         // document=parser.parse(refToFile);
         if (isFile) {
            File f = new File(toParse);
            if (!f.exists()) {
               throw new Exception();
            }

            document = parser.parse(new InputSource(new FileInputStream(f))); // Fixed by
                                                                              // Harald
                                                                              // Meister
         } else {
            document = parser.parse(new InputSource(new StringReader(toParse)));
         }

         List errorMessages = pErrors.getErrorMessages();
         if (errorMessages.size() > 0) {
            System.err.println("Errors in schema type");
         }
      } catch (Exception ex) {
         System.err.println("Fatal error while parsing xml schema document");
         return null;
      }
      if (document != null) {
         return document.getDocumentElement();
      }

      return null;
   }

   public static String stringifyExtendedAttributes(ExtendedAttributes extAttribs)
      throws Exception {
      try {
         ExtendedAttributes easclone = (ExtendedAttributes) extAttribs.clone();
         easclone.setParent(null);
         Iterator it = easclone.toElements().iterator();
         while (it.hasNext()) {
            ExtendedAttribute ea = (ExtendedAttribute) it.next();
            ea.setParent(null);
            ea.get("Name").setParent(null);
            ea.get("Value").setParent(null);
         }

         return XMLUtil.getExtendedAttributesString(easclone);
         // byte[] eas=XMLUtil.serialize(easclone);
         // return Base64.encode(eas);
      } catch (Throwable thr) {
         throw new Exception("Can't stringify extended attributes, error="
                             + thr.getMessage() + " !");
      }
   }

   public static ExtendedAttributes destringyfyExtendedAttributes(String extAttribs)
      throws Exception {
      ExtendedAttributes extAttr = null;
      if (extAttribs != null && !extAttribs.trim().equals("")) {
         try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);

            DocumentBuilder parser = factory.newDocumentBuilder();

            Document document = null;
            document = parser.parse(new InputSource(new StringReader(extAttribs)));

            extAttr = new ExtendedAttributes(null);
            if (document != null) {
               XPDLRepositoryHandler rep = new XPDLRepositoryHandler();
               rep.fromXML(document.getDocumentElement(), extAttr);
            }

            // byte[] eas=Base64.decode(extAttribs);
            // extAttr=(ExtendedAttributes)XMLUtil.deserialize(eas);
            return extAttr;
         } catch (Throwable thr) {
            thr.printStackTrace();
            throw new Exception("Failed to destringify extended attributes, error="
                                + thr.getMessage() + " !");
         }
      }

      return extAttr;
   }

   public static Node parseExtendedAttributeContent(String toParse) {
      Document document = null;

      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setValidating(false);

      try {
         ParsingErrors pErrors = new ParsingErrors();

         DocumentBuilder parser = factory.newDocumentBuilder();
         parser.setErrorHandler(pErrors);
         // adding helper tag, so after parsing, all its children
         // will be taken into account
         toParse = "<ExtAttribsAddition>" + toParse + "</ExtAttribsAddition>";
         document = parser.parse(new InputSource(new StringReader(toParse)));
         List errorMessages = pErrors.getErrorMessages();
         if (errorMessages.size() > 0) {
            System.err.println("Errors in ext attribs complex content");
         }
      } catch (Exception ex) {
         System.err.println("Fatal error while parsing ext. attributes complex content "
                            + toParse);
         return null;
      }
      if (document != null) {
         return document.getDocumentElement();
      }

      return null;
   }

   public static String getExtendedAttributeValue(String[][] extendedAttributes,
                                                  String extAttrName) {
      if (extendedAttributes != null) {
         for (int i = 0; i < extendedAttributes.length; i++) {
            if (extendedAttributes[i][0].equals(extAttrName)) {
               return extendedAttributes[i][1];
            }
         }
      }
      return null;
   }

   public static String getJavaType(XMLInterface xmli, XMLCollectionElement dfOrFP) {
      DataTypes dtypes = null;
      if (dfOrFP instanceof DataField) {
         dtypes = ((DataField) dfOrFP).getDataType().getDataTypes();
      } else {
         dtypes = ((FormalParameter) dfOrFP).getDataType().getDataTypes();
      }
      return getChoosenType(xmli, dtypes, XMLUtil.getPackage(dfOrFP));
   }

   public static String getChoosenType(XMLInterface xmli, DataTypes types, Package pkg) {
      XMLElement choosenType = types.getChoosen();
      if (choosenType instanceof BasicType) {
         String subTypeName = ((BasicType) choosenType).getType();
         if (XMLUtil.basicTypesMap.containsKey(subTypeName)) {
            return (String) XMLUtil.basicTypesMap.get(subTypeName);
         }
      } else if (choosenType instanceof DeclaredType) {
         TypeDeclaration td = XMLUtil.getTypeDeclaration(xmli,
                                                         pkg,
                                                         (((DeclaredType) choosenType).getId()));
         DataTypes dtypes = td.getDataTypes();
         return getChoosenType(xmli, dtypes, pkg);
      } else if (choosenType instanceof SchemaType) {
         return "org.w3c.dom.Node";
      } else if (choosenType instanceof ExternalReference) {
         return ((ExternalReference) choosenType).getLocation();
      }
      return "java.lang.Object";

   }

   public static Package getPackage(XMLElement el) {
      if (el == null)
         return null;
      while (!(el instanceof Package)) {
         el = el.getParent();
         if (el == null)
            break;
      }
      return (Package) el;
   }

   public static WorkflowProcess getWorkflowProcess(XMLElement el) {
      if (el == null)
         return null;
      while (!(el instanceof WorkflowProcess)) {
         el = el.getParent();
         if (el == null)
            break;
      }
      return (WorkflowProcess) el;
   }

   public static Pool getPool(XMLElement el) {
      if (el == null)
         return null;
      while (!(el instanceof Pool)) {
         el = el.getParent();
         if (el == null)
            break;
      }
      return (Pool) el;
   }

   public static ActivitySet getActivitySet(XMLElement el) {
      if (el == null)
         return null;
      while (!(el instanceof ActivitySet)) {
         el = el.getParent();
         if (el == null)
            break;
      }
      return (ActivitySet) el;
   }

   public static Activity getActivity(XMLElement el) {
      if (el == null)
         return null;
      while (!(el instanceof Activity)) {
         el = el.getParent();
         if (el == null)
            break;
      }
      return (Activity) el;
   }

   public static Artifact getArtifact(XMLElement el) {
      if (el == null)
         return null;
      while (!(el instanceof Artifact)) {
         el = el.getParent();
         if (el == null)
            break;
      }
      return (Artifact) el;
   }

   public static Transition getTransition(XMLElement el) {
      if (el == null)
         return null;
      while (!(el instanceof Transition)) {
         el = el.getParent();
         if (el == null)
            break;
      }
      return (Transition) el;
   }

   public static Association getAssociation(XMLElement el) {
      if (el == null)
         return null;
      while (!(el instanceof Association)) {
         el = el.getParent();
         if (el == null)
            break;
      }
      return (Association) el;
   }

   public static Participant getParticipant(XMLElement el) {
      if (el == null)
         return null;
      while (!(el instanceof Participant)) {
         el = el.getParent();
         if (el == null)
            break;
      }
      return (Participant) el;
   }

   public static Lane getLane(XMLElement el) {
      if (el == null)
         return null;
      while (!(el instanceof Lane)) {
         el = el.getParent();
         if (el == null)
            break;
      }
      return (Lane) el;
   }

   public static XMLElement getParentElement(Class type, XMLElement el) {
      if (el == null || type == null)
         return null;
      while (!(el.getClass() == type)) {
         el = el.getParent();
         if (el == null)
            break;
      }
      return el;
   }

   public static XMLElement getParentElementByAssignableType(Class assignableFrom,
                                                             XMLElement el) {
      if (el == null || assignableFrom == null)
         return null;
      while (!assignableFrom.isAssignableFrom(el.getClass())) {
         el = el.getParent();
         if (el == null)
            break;
      }
      return el;
   }

   public static boolean isParentsChild(XMLElement parent, XMLElement el) {
      if (el == null || parent == null)
         return false;
      el = el.getParent();
      if (el == null)
         return false;
      while (el != parent) {
         el = el.getParent();
         if (el == null)
            break;
      }
      return (el != null);
   }

   public static boolean isChildsParent(XMLElement parent, XMLElement mayBeChild) {
      if (mayBeChild == null || parent == null)
         return false;
      XMLElement el = mayBeChild.getParent();
      if (el == null)
         return false;
      while (el != parent) {
         el = el.getParent();
         if (el == null)
            break;
      }
      return (el != null);
   }

   public static Set getAllParents(XMLElement el) {
      Set parents = new HashSet();

      while (el != null) {
         el = el.getParent();
         parents.add(el);
      }

      return parents;
   }

   public static WorkflowProcess findWorkflowProcess(XMLInterface xmlInterface,
                                                     Package toBegin,
                                                     String wpId) {
      WorkflowProcess wp = toBegin.getWorkflowProcess(wpId);
      if (wp == null) {
         List l = XMLUtil.getAllExternalPackageIds(xmlInterface, toBegin, new HashSet());
         Iterator it = l.iterator();
         while (it.hasNext()) {
            Package p = xmlInterface.getPackageById((String) it.next());
            if (p != null) {
               wp = p.getWorkflowProcess(wpId);
               if (wp != null) {
                  break;
               }
            }
         }
      }
      return wp;
   }

   public static Participant findParticipant(XMLInterface xmlInterface,
                                             WorkflowProcess toBegin,
                                             String perf) {
      Participant p = toBegin.getParticipant(perf);
      if (p == null) {
         Package pkg = XMLUtil.getPackage(toBegin);
         p = XMLUtil.findParticipant(xmlInterface, pkg, perf);
      }
      return p;
   }

   public static Participant findParticipant(XMLInterface xmlInterface,
                                             Package toBegin,
                                             String perf) {
      Participant p = toBegin.getParticipant(perf);
      if (p == null) {
         List l = XMLUtil.getAllExternalPackageIds(xmlInterface, toBegin, new HashSet());
         Iterator ita = l.iterator();
         while (ita.hasNext()) {
            Package pk = xmlInterface.getPackageById((String) ita.next());
            if (pk != null) {
               p = pk.getParticipant(perf);
               if (p != null) {
                  break;
               }
            }
         }
      }
      return p;
   }

   public static Application findApplication(XMLInterface xmlInterface,
                                             WorkflowProcess toBegin,
                                             String id) {
      Application a = toBegin.getApplication(id);
      if (a == null) {
         Package pkg = XMLUtil.getPackage(toBegin);
         a = XMLUtil.getApplication(xmlInterface, pkg, id);
      }
      return a;
   }

   public static Application getApplication(XMLInterface xmlInterface,
                                            Package toBegin,
                                            String id) {
      Application a = toBegin.getApplication(id);
      if (a == null) {
         List l = XMLUtil.getAllExternalPackageIds(xmlInterface, toBegin, new HashSet());
         Iterator ita = l.iterator();
         while (ita.hasNext()) {
            Package pk = xmlInterface.getPackageById((String) ita.next());
            if (pk != null) {
               a = pk.getApplication(id);
               if (a != null) {
                  break;
               }
            }
         }
      }
      return a;
   }

   public static TypeDeclaration getTypeDeclaration(XMLInterface xmlInterface,
                                                    Package toBegin,
                                                    String id) {
      TypeDeclaration td = toBegin.getTypeDeclaration(id);
      if (td == null) {
         List l = XMLUtil.getAllExternalPackageIds(xmlInterface, toBegin, new HashSet());
         Iterator ita = l.iterator();
         while (ita.hasNext()) {
            Package pk = xmlInterface.getPackageById((String) ita.next());
            if (pk != null) {
               td = pk.getTypeDeclaration(id);
               if (td != null) {
                  break;
               }
            }
         }
      }
      return td;
   }

   public static boolean canBeExpression(String expr,
                                         Map allVars,
                                         boolean evaluateToString) {
      String exprToParse = new String(expr);

      boolean canBeExpression = false;

      if (evaluateToString
          && (expr.startsWith("\"") && expr.endsWith("\""))
          || (expr.startsWith("'") && expr.endsWith("'"))) {
         canBeExpression = true;
      }
      // System.err.println("CBE1="+canBeExpression);
      if (!canBeExpression) {
         boolean validVarId = XMLUtil.isIdValid(exprToParse);
         if (validVarId && allVars.containsKey(exprToParse)) {
            canBeExpression = true;
         }
      }
      // System.err.println("CBE2="+canBeExpression);

      if (!canBeExpression) {
         Iterator it = allVars.keySet().iterator();
         while (it.hasNext()) {
            String varId = (String) it.next();
            if (XMLUtil.getUsingPositions(exprToParse, varId, allVars).size() > 0) {
               // System.err.println("CBE2.5 - can be expr because var "+varId+" is possibly used");
               canBeExpression = true;
               break;
            }
         }
      }
      // System.err.println("CBE3="+canBeExpression);

      return canBeExpression;
   }

   public static List getUsingPositions(String expr, String dfOrFpId, Map allVars) {
      List positions = new ArrayList();
      if (expr.trim().equals("") || dfOrFpId.trim().equals(""))
         return positions;
      String exprToParse = new String(expr);
      int foundAt = -1;
      while ((foundAt = exprToParse.indexOf(dfOrFpId)) >= 0) {
         // System.out.println("Searching for using positions of variable "+dfOrFpId+" in expression "+exprToParse+" -> found "+foundAt);
         if (foundAt < 0)
            break;
         if (exprToParse.equals(dfOrFpId)) {
            int pos = foundAt;
            if (positions.size() > 0) {
               pos += ((Integer) positions.get(positions.size() - 1)).intValue()
                      + dfOrFpId.length();
            }
            positions.add(new Integer(pos));
            break;
         }
         boolean prevOK, nextOK;
         char prev, next;
         // if given Id is found within expression string
         // check if Id string is part of some other Id name

         // check if char previous to the position of found Id is OK
         if (foundAt == 0) {
            prevOK = true;
         } else {
            prev = exprToParse.charAt(foundAt - 1);
            prevOK = !XMLUtil.isIdValid(String.valueOf(prev)) || prev == ':';
            // System.out.println("Is prev char "+prev+" ok = "+prevOK);
         }

         // check if char after found ID string is OK
         if (foundAt + dfOrFpId.length() == exprToParse.length()) {
            nextOK = true;
         } else {
            next = exprToParse.charAt(foundAt + dfOrFpId.length());
            nextOK = !XMLUtil.isIdValid(String.valueOf(next));
            if (!nextOK && (next == '-' || next == '.')) {
               nextOK = true;
               List varIdsWithChar = new ArrayList(allVars.keySet());
               Iterator li = varIdsWithChar.iterator();
               while (li.hasNext()) {
                  String vid = (String) li.next();
                  if (vid.indexOf(next) <= 0) {
                     li.remove();
                  }
               }
               if (varIdsWithChar.size() > 0) {
                  li = varIdsWithChar.iterator();
                  while (li.hasNext()) {
                     String vid = (String) li.next();
                     int ovp = exprToParse.indexOf(vid);
                     if (ovp == foundAt) {
                        nextOK = false;
                        break;
                     }
                  }
               }
            }
            // System.out.println("Is next char "+next+" ok = "+nextOK);
         }

         // if this is really the Id, add its position in expression
         if (prevOK && nextOK) {
            int pos = foundAt;
            if (positions.size() > 0) {
               pos += ((Integer) positions.get(positions.size() - 1)).intValue()
                      + dfOrFpId.length();
            }
            positions.add(new Integer(pos));
         }
         exprToParse = exprToParse.substring(foundAt + dfOrFpId.length());
         // System.out.println("New expr to parse is "+exprToParse);
      }
      // System.out.println("Using positions of variable "+dfOrFpId+" for expression: "+expr+", "+positions);
      return positions;
   }

   public static Join getJoin(Activity act) {
      Join j = null;
      TransitionRestrictions trs = act.getTransitionRestrictions();
      if (trs.size() > 0) {
         j = ((TransitionRestriction) trs.get(0)).getJoin();
      }
      return j;
   }

   public static Split getSplit(Activity act) {
      Split s = null;
      TransitionRestrictions trs = act.getTransitionRestrictions();
      if (trs.size() > 0) {
         s = ((TransitionRestriction) trs.get(0)).getSplit();
      }
      return s;
   }

   public static Set getOutgoingTransitions(Activity act) {
      return XMLUtil.getOutgoingTransitions(act,
                                            ((Transitions) ((XMLCollectionElement) act.getParent()
                                               .getParent()).get("Transitions")));
   }

   public static Set getOutgoingTransitions(Activity act, Transitions tras) {
      Set s = new HashSet();
      Iterator it = tras.toElements().iterator();
      while (it.hasNext()) {
         Transition t = (Transition) it.next();
         if (t.getFrom().equals(act.getId())) {
            s.add(t);
         }
      }
      return s;
   }

   public static Set getExceptionalOutgoingTransitions(Activity act) {
      Transitions tras = (Transitions) ((XMLCollectionElement) act.getParent()
         .getParent()).get("Transitions");
      return XMLUtil.getExceptionalOutgoingTransitions(act, tras);
   }

   public static Set getExceptionalOutgoingTransitions(Activity act, Transitions tras) {
      Set s = new HashSet();
      Iterator it = tras.toElements().iterator();
      while (it.hasNext()) {
         Transition t = (Transition) it.next();
         if (t.getFrom().equals(act.getId())) {
            if (XMLUtil.isExceptionalTransition(t)) {
               s.add(t);
            }
         }
      }
      return s;
   }

   public static Set getNonExceptionalOutgoingTransitions(Activity act) {
      Transitions tras = (Transitions) ((XMLCollectionElement) act.getParent()
         .getParent()).get("Transitions");
      return XMLUtil.getNonExceptionalOutgoingTransitions(act, tras);
   }

   public static Set getNonExceptionalOutgoingTransitions(Activity act, Transitions tras) {
      Set s = new HashSet();
      Iterator it = tras.toElements().iterator();
      while (it.hasNext()) {
         Transition t = (Transition) it.next();
         if (t.getFrom().equals(act.getId())) {
            if (!XMLUtil.isExceptionalTransition(t)) {
               s.add(t);
            }
         }
      }
      return s;
   }

   public static Set getIncomingTransitions(Activity act) {
      return XMLUtil.getIncomingTransitions(act,
                                            ((Transitions) ((XMLCollectionElement) act.getParent()
                                               .getParent()).get("Transitions")));
   }

   public static Set getIncomingTransitions(Activity act, Transitions tras) {
      Set s = new HashSet();
      Iterator it = tras.toElements().iterator();
      while (it.hasNext()) {
         Transition t = (Transition) it.next();
         if (t.getTo().equals(act.getId())) {
            s.add(t);
         }
      }
      return s;
   }

   public static boolean isExceptionalTransition(Transition tra) {
      boolean isExcTra = false;
      if (tra != null) {
         String tt = tra.getCondition().getType();
         if (tt.equals(XPDLConstants.CONDITION_TYPE_EXCEPTION)
             || tt.equals(XPDLConstants.CONDITION_TYPE_DEFAULTEXCEPTION)) {
            isExcTra = true;
         }
      }
      return isExcTra;
   }

   public static Activity getFromActivity(Transition t) {
      return ((Activities) ((XMLCollectionElement) t.getParent().getParent()).get("Activities")).getActivity(t.getFrom());
   }

   public static Activity getToActivity(Transition t) {
      return ((Activities) ((XMLCollectionElement) t.getParent().getParent()).get("Activities")).getActivity(t.getTo());
   }

   public static Set getOutgoingAssociations(XMLCollectionElement actOrArt) {
      Set ret = new HashSet();
      Iterator it = XMLUtil.getPackage(actOrArt)
         .getAssociations()
         .toElements()
         .iterator();
      String actId = actOrArt.getId();
      while (it.hasNext()) {
         Association a = (Association) it.next();
         if (a.getSource().equals(actId)) {
            ret.add(a);
         }
      }
      return ret;
   }

   public static Set getIncomingAssociations(XMLCollectionElement actOrArt) {
      Set ret = new HashSet();
      Iterator it = XMLUtil.getPackage(actOrArt)
         .getAssociations()
         .toElements()
         .iterator();
      String actId = actOrArt.getId();
      while (it.hasNext()) {
         Association a = (Association) it.next();
         if (a.getTarget().equals(actId)) {
            ret.add(a);
         }
      }
      return ret;
   }

   public static XMLCollectionElement getAssociationSource(Association t) {
      return getAssociationSourceOrTarget(t, true);
   }

   public static XMLCollectionElement getAssociationTarget(Association t) {
      return getAssociationSourceOrTarget(t, false);
   }

   protected static XMLCollectionElement getAssociationSourceOrTarget(Association t,
                                                                      boolean source) {
      String st = "Source";
      if (!source) {
         st = "Target";
      }
      String stId = t.get(st).toValue();
      XMLCollectionElement a = XMLUtil.getPackage(t).getArtifact(stId);
      if (a == null) {
         Iterator it = XMLUtil.getPackage(t)
            .getWorkflowProcesses()
            .toElements()
            .iterator();
         while (it.hasNext()) {
            WorkflowProcess wp = (WorkflowProcess) it.next();
            a = wp.getActivities().getActivity(stId);
            if (a == null) {
               Iterator it2 = wp.getActivitySets().toElements().iterator();
               while (it2.hasNext()) {
                  ActivitySet as = (ActivitySet) it2.next();
                  a = as.getActivities().getActivity(stId);
                  if (a != null) {
                     break;
                  }
               }
            }
         }
      }
      return a;
   }

   public static List getAllArtifactsAndAssociationsForWorkflowProcessOrActivitySet(XMLCollectionElement wpOrAs) {
      List ret = new ArrayList();

      Iterator it = XMLUtil.getPoolForProcessOrActivitySet(wpOrAs)
         .getLanes()
         .toElements()
         .iterator();
      List laneIds = new ArrayList();
      while (it.hasNext()) {
         Lane l = (Lane) it.next();
         laneIds.add(l.getId());
      }
      Package pkg = XMLUtil.getPackage(wpOrAs);
      it = pkg.getArtifacts().toElements().iterator();
      while (it.hasNext()) {
         Artifact a = (Artifact) it.next();
         if (a.getNodeGraphicsInfos().size() > 0) {
            Iterator ngit = a.getNodeGraphicsInfos().toElements().iterator();
            boolean processed = false;
            while (ngit.hasNext()) {
               NodeGraphicsInfo ngi = (NodeGraphicsInfo) ngit.next();
               if (laneIds.contains(ngi.getLaneId())) {
                  if (!processed) {
                     ret.add(a);
                     List arefs = getArtifactReferences(pkg, a.getId());
                     for (int i = 0; i < arefs.size(); i++) {
                        Association asoc = XMLUtil.getAssociation((XMLElement) arefs.get(i));
                        ret.add(asoc);
                     }
                     processed = true;
                  }
               }
            }
         }
      }
      return ret;
   }

   /**
    * Checks if Id is valid NMTOKEN string.
    */
   public static boolean isIdValid(String id) {
      return id != null && XMLChar.isValidNmtoken(id) && !id.trim().equals("");
   }

   public static int cntIds(XMLCollection xmlCol, String id) {
      int idCnt = 0;
      Iterator it = xmlCol.toElements().iterator();
      while (it.hasNext()) {
         XMLElement el = (XMLElement) it.next();
         if (el instanceof XMLCollectionElement) {
            XMLCollectionElement xmlce = (XMLCollectionElement) el;
            String cId = xmlce.getId();
            if (cId.equals(id)) {
               idCnt++;
            }
         } else {
            return 0;
         }
      }
      return idCnt;
   }

   public static WorkflowProcess getSubflowProcess(XMLInterface xmlInterface,
                                                   Activity sbflwAct) {
      if (sbflwAct.getActivityType() != XPDLConstants.ACTIVITY_TYPE_SUBFLOW)
         return null;
      Package pkg = XMLUtil.getPackage(sbflwAct);
      SubFlow s = sbflwAct.getActivityTypes()
         .getImplementation()
         .getImplementationTypes()
         .getSubFlow();
      String subflowID = s.getId();

      WorkflowProcess wp = pkg.getWorkflowProcess(subflowID);
      if (wp == null) {
         List l = XMLUtil.getAllExternalPackageIds(xmlInterface, pkg, new HashSet());
         Iterator it = l.iterator();
         while (it.hasNext()) {
            Package p = xmlInterface.getPackageById((String) it.next());
            if (p != null) {
               wp = p.getWorkflowProcess(subflowID);
               if (wp != null) {
                  break;
               }
            }
         }
      }
      // System.out.println("Found Subprocess is "+((wp!=null) ? wp.getId() : "null"));
      return wp;
   }

   public static ActivitySet getBlockActivitySet(Activity blockAct) {
      if (blockAct.getActivityType() != XPDLConstants.ACTIVITY_TYPE_BLOCK)
         return null;

      String blockID = blockAct.getActivityTypes().getBlockActivity().getActivitySetId();
      ActivitySet as = XMLUtil.getWorkflowProcess(blockAct).getActivitySet(blockID);

      return as;
   }

   public static List getAllExternalPackageIds(XMLInterface xmli,
                                               Package pkg,
                                               Set alreadyGathered) {
      List l = new ArrayList();
      List workingList = new ArrayList(pkg.getExternalPackageIds());
      workingList.removeAll(alreadyGathered);
      Iterator it = workingList.iterator();
      while (it.hasNext()) {
         String pkgId = (String) it.next();
         Package p = xmli.getPackageById(pkgId);
         if (p != null && !l.contains(p.getId())) {
            l.add(pkgId);
            alreadyGathered.addAll(l);
            l.addAll(getAllExternalPackageIds(xmli, p, alreadyGathered));
         }
      }
      return l;
   }

   public static List getImmediateExternalPackages(XMLInterface xmli, Package pkg) {
      List l = new ArrayList();
      Iterator it = pkg.getExternalPackageIds().iterator();
      while (it.hasNext()) {
         String pkgId = (String) it.next();
         Package p = xmli.getPackageById(pkgId);
         if (p != null && !l.contains(p.getId())) {
            l.add(p);
         }
      }
      return l;
   }

   /**
    * Returns the set of (XML) activities that have split or join.
    * 
    * @param acts The activities graph objects that are checked if their XML object have
    *           split or join, depending on the second parameter.
    * @param sOrJ if 0, activity is checked for split, otherwise it is checked for join
    */
   public static Set getSplitOrJoinActivities(Collection acts, int sOrJ) {
      Set sOrJactivities = new HashSet();
      if (acts == null)
         return sOrJactivities;
      Iterator it = acts.iterator();
      while (it.hasNext()) {
         Activity act = (Activity) it.next();
         int noOfTrans = 0;
         if (sOrJ == 0) {
            noOfTrans = getOutgoingTransitions(act).size();
         } else {
            noOfTrans = getIncomingTransitions(act).size();
         }
         if (noOfTrans > 1) {
            sOrJactivities.add(act);
         }
      }

      return sOrJactivities;
   }

   /**
    * Returns the set of BlockActivity objects contained within given process or block
    * activity. If the BlockActivity objects contains other BlockActivity objects, and the
    * second parameter is set to true, these are also returned, and so on - which means
    * that implementation is recursive.
    */
   public static Set getBlockActivities(XMLComplexElement wpOrAs, boolean recursivly) {
      Collection allActs = ((Activities) wpOrAs.get("Activities")).toElements();
      Set bas = new HashSet();
      Iterator it = allActs.iterator();
      Activity act;
      while (it.hasNext()) {
         act = (Activity) it.next();
         BlockActivity ba = act.getActivityTypes().getBlockActivity();
         if (ba != null) {
            bas.add(act);
            if (!recursivly)
               continue;
            ActivitySets ass = getWorkflowProcess(act).getActivitySets();
            String asId = ba.getActivitySetId();
            ActivitySet as = ass.getActivitySet(asId);
            if (as != null) {
               bas.addAll(getBlockActivities(as, true));
            }
         }
      }
      return bas;
   }

   /**
    * Returns predefined conformanceClass number.
    * 
    * @param conformanceClass The conformance class we are looking for number
    * @return 0 if conformance class is NON_BLOCKED, 1 if conformance class is
    *         LOOP_BLOCKED, 2 if conformance class is FULL_BLOCKED, and -1 otherwise
    */
   public static int getConformanceClassNo(String conformanceClass) {
      if (conformanceClass.equals(XPDLConstants.GRAPH_CONFORMANCE_NON_BLOCKED)) {
         return 0;
      } else if (conformanceClass.equals(XPDLConstants.GRAPH_CONFORMANCE_LOOP_BLOCKED)) {
         return 1;
      } else if (conformanceClass.equals(XPDLConstants.GRAPH_CONFORMANCE_FULL_BLOCKED)) {
         return 2;
      } else {
         return -1;
      }
   }

   /**
    * Converts a file specified by the path, to the String.
    */
   public static String fileToString(String fileName) {
      if (fileName != null) {
         // String sLine;
         byte[] utf8Bytes;
         String sFile = new String();
         // Reading input by lines:
         FileInputStream fis = null;
         try {
            fis = new FileInputStream(fileName);
            int noOfBytes = fis.available();
            if (noOfBytes > 0) {
               utf8Bytes = new byte[noOfBytes];
               fis.read(utf8Bytes);
               sFile = new String(utf8Bytes, "UTF8");
            }
         } catch (Exception ex) {
            return null;
         } finally {
            if (fis != null) {
               try {
                  fis.close();
               } catch (Exception ex) {
               }
            }
         }
         return sFile;
      }
      return null;
   }

   // ******** END OF CREATING SCROLLPANE AND EDITOR COMPONENT(PEJGRAPH) **********

   /** Gets the current date and time string in yyyy-MM-dd HH:mm:ss format. */
   public static String getCurrentDateAndTime() {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      return sdf.format(new Date());
   }

   public static String replaceBackslashesWithSlashes(String repBS) {
      if (repBS != null) {
         int ind = -1;
         while ((ind = repBS.indexOf("\\")) != -1) {
            repBS = repBS.substring(0, ind) + "/" + repBS.substring(ind + 1);
         }
      }
      return repBS;
   }

   public static String getExtendedAttributesString(ExtendedAttributes eas)
      throws Exception {
      Document document = null;

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder dbuilder = dbf.newDocumentBuilder();
      document = dbuilder.newDocument();
      Node eac = document.createElement("EAC");
      XPDLRepositoryHandler repH = new XPDLRepositoryHandler();
      repH.toXML(eac, eas);

      return XMLUtil.getContent(eac.getFirstChild(), true);
   }

   public static String getExtendedAttributesString(Node node) {
      String nameSpacePrefix = node.getPrefix();
      if (nameSpacePrefix != null) {
         nameSpacePrefix += ":";
      } else {
         nameSpacePrefix = "";
      }
      Node eas = getChildByName(node, nameSpacePrefix + "ExtendedAttributes");
      return XMLUtil.getContent(eas, true);
   }

   public static Set getStartingActivities(XMLCollectionElement procOrASDef) {
      Activities acts = ((Activities) procOrASDef.get("Activities"));
      Set starts = new HashSet();
      Iterator it = acts.toElements().iterator();
      while (it.hasNext()) {
         Activity act = (Activity) it.next();
         Set trs = getIncomingTransitions(act);
         // the activity is starting one if it has no input transitions ...
         if (trs.size() == 0) {
            starts.add(act);
            // or there is a one input transition, but it is a selfreference
         } else if (trs.size() == 1) {
            Transition t = (Transition) trs.toArray()[0];
            if (t.getFrom().equals(t.getTo())) {
               starts.add(act);
            }
         }
      }
      return starts;
   }

   public static Set getEndingActivities(XMLCollectionElement procOrASDef) {
      Activities acts = ((Activities) procOrASDef.get("Activities"));
      Set ends = new HashSet();
      Iterator it = acts.toElements().iterator();
      while (it.hasNext()) {
         Activity act = (Activity) it.next();
         Set trs = getNonExceptionalOutgoingTransitions(act);
         // the activity is ending one if it has no output transitions ...
         if (trs.size() == 0) {
            ends.add(act);
            // or there is a one output transition, but it is a selfreference
         } else if (trs.size() == 1) {
            Transition t = (Transition) trs.toArray()[0];
            if (t.getFrom().equals(t.getTo())) {
               ends.add(act);
            }
         }
      }

      return ends;
   }

   /**
    * Returns the list of responsibles for the process, and responsibles for whole
    * package.
    */
   public static List getResponsibles(WorkflowProcess wp) {
      List resp = new ArrayList();
      RedefinableHeader rh = wp.getRedefinableHeader();
      Responsibles rsps = rh.getResponsibles();
      Iterator it = rsps.toElements().iterator();
      while (it.hasNext()) {
         Responsible rsp = (Responsible) it.next();
         if (!resp.contains(rsp)) {
            resp.add(rsp);
         }
      }
      // from package
      rh = getPackage(wp).getRedefinableHeader();
      rsps = rh.getResponsibles();
      it = rsps.toElements().iterator();
      while (it.hasNext()) {
         Responsible rsp = (Responsible) it.next();
         if (!resp.contains(rsp)) {
            resp.add(rsp);
         }
      }
      return resp;
   }

   public static SequencedHashMap getPossibleParticipants(Package forPkg,
                                                          XMLInterface xmlInterface) {
      SequencedHashMap pars = new SequencedHashMap();
      List l = XMLUtil.getAllExternalPackageIds(xmlInterface, forPkg, new HashSet());
      Iterator itpkg = l.iterator();
      while (itpkg.hasNext()) {
         Package pkg = xmlInterface.getPackageById((String) itpkg.next());
         if (pkg != null) {
            Iterator it = pkg.getParticipants().toElements().iterator();
            while (it.hasNext()) {
               Participant p = (Participant) it.next();
               pars.put(p.getId(), p);
            }
         }
      }
      Iterator it = forPkg.getParticipants().toElements().iterator();
      while (it.hasNext()) {
         Participant p = (Participant) it.next();
         pars.put(p.getId(), p);
      }
      return pars;
   }

   public static SequencedHashMap getPossibleParticipants(WorkflowProcess forWP,
                                                          XMLInterface xmlInterface) {
      List ps = forWP.getParticipants().toElements();
      SequencedHashMap pars = XMLUtil.getPossibleParticipants(XMLUtil.getPackage(forWP),
                                                              xmlInterface);
      Iterator it = ps.iterator();
      while (it.hasNext()) {
         Participant p = (Participant) it.next();
         pars.put(p.getId(), p);
      }
      return pars;
   }

   public static SequencedHashMap getPossibleApplications(Package forPkg,
                                                          XMLInterface xmlInterface) {
      SequencedHashMap aps = new SequencedHashMap();
      List l = XMLUtil.getAllExternalPackageIds(xmlInterface, forPkg, new HashSet());
      Iterator itpkg = l.iterator();
      while (itpkg.hasNext()) {
         Package pkg = xmlInterface.getPackageById((String) itpkg.next());
         if (pkg != null) {
            Iterator it = pkg.getApplications().toElements().iterator();
            while (it.hasNext()) {
               Application app = (Application) it.next();
               aps.put(app.getId(), app);
            }
         }
      }
      Iterator it = forPkg.getApplications().toElements().iterator();
      while (it.hasNext()) {
         Application app = (Application) it.next();
         aps.put(app.getId(), app);
      }
      return aps;
   }

   public static SequencedHashMap getPossibleApplications(WorkflowProcess forWP,
                                                          XMLInterface xmlInterface) {
      List as = forWP.getApplications().toElements();
      SequencedHashMap apps = XMLUtil.getPossibleApplications(XMLUtil.getPackage(forWP),
                                                              xmlInterface);
      Iterator it = as.iterator();
      while (it.hasNext()) {
         Application app = (Application) it.next();
         apps.put(app.getId(), app);
      }

      return apps;
   }

   public static SequencedHashMap getPossibleDataFields(Package forPkg) {
      SequencedHashMap dfs = new SequencedHashMap();
      Iterator it = forPkg.getDataFields().toElements().iterator();
      while (it.hasNext()) {
         DataField df = (DataField) it.next();
         dfs.put(df.getId(), df);
      }
      return dfs;
   }

   public static SequencedHashMap getPossibleDataFields(WorkflowProcess forWP) {
      List ds = forWP.getDataFields().toElements();
      SequencedHashMap dfs = getPossibleDataFields(XMLUtil.getPackage(forWP));
      Iterator it = ds.iterator();
      while (it.hasNext()) {
         DataField df = (DataField) it.next();
         dfs.put(df.getId(), df);
      }
      return dfs;
   }

   public static SequencedHashMap getPossibleVariables(WorkflowProcess forWP) {
      SequencedHashMap vars = getPossibleDataFields(forWP);
      Iterator it = forWP.getFormalParameters().toElements().iterator();
      while (it.hasNext()) {
         FormalParameter fp = (FormalParameter) it.next();
         if (!vars.containsKey(fp.getId())) {
            vars.put(fp.getId(), fp);
         }
      }
      return vars;
   }

   public static SequencedHashMap getPossibleSubflowProcesses(SubFlow sbflw,
                                                              XMLInterface xmlInterface) {
      SequencedHashMap wps = new SequencedHashMap();
      List l = XMLUtil.getAllExternalPackageIds(xmlInterface,
                                                XMLUtil.getPackage(sbflw),
                                                new HashSet());
      Iterator itpkg = l.iterator();
      while (itpkg.hasNext()) {
         Package p = xmlInterface.getPackageById((String) itpkg.next());
         if (p != null) {
            Iterator it = p.getWorkflowProcesses().toElements().iterator();
            while (it.hasNext()) {
               WorkflowProcess wp = (WorkflowProcess) it.next();
               wps.put(wp.getId(), wp);
            }
         }
      }
      Iterator it = ((WorkflowProcesses) XMLUtil.getParentElement(WorkflowProcesses.class,
                                                                  sbflw)).toElements()
         .iterator();
      while (it.hasNext()) {
         WorkflowProcess wp = (WorkflowProcess) it.next();
         wps.put(wp.getId(), wp);
      }
      return wps;
   }

   public static SequencedHashMap getPossibleTypeDeclarations(Package forPkg,
                                                              XMLInterface xmlInterface) {
      SequencedHashMap tds = new SequencedHashMap();
      List l = XMLUtil.getAllExternalPackageIds(xmlInterface, forPkg, new HashSet());
      Iterator itpkg = l.iterator();
      while (itpkg.hasNext()) {
         Package pkg = xmlInterface.getPackageById((String) itpkg.next());
         if (pkg != null) {
            Iterator it = pkg.getTypeDeclarations().toElements().iterator();
            while (it.hasNext()) {
               TypeDeclaration td = (TypeDeclaration) it.next();
               tds.put(td.getId(), td);
            }
         }
      }
      Iterator it = forPkg.getTypeDeclarations().toElements().iterator();
      while (it.hasNext()) {
         TypeDeclaration td = (TypeDeclaration) it.next();
         tds.put(td.getId(), td);
      }
      return tds;
   }

   /**
    * Returns if given activity has AND type split or join.
    * 
    * @param act The activity that is checked if it has a AND type split or join,
    *           depending on the second parameter.
    * @param sOrJ if 0, activity is checked for AND type split, otherwise it is checked
    *           for AND type join
    * @return true if given activity has AND type split or join
    */
   public static boolean isANDTypeSplitOrJoin(Activity act, int sOrJ) {
      String sjType = XPDLConstants.JOIN_SPLIT_TYPE_EXCLUSIVE; // default type is XOR

      if (sOrJ == 0) { // it is split that we search for
         Split s = getSplit(act);
         if (s != null) {
            sjType = s.getType();
         }
      } else { // it is join that we search for
         Join j = getJoin(act);
         if (j != null) {
            sjType = j.getType();
         }
      }

      if (sjType.equals(XPDLConstants.JOIN_SPLIT_TYPE_PARALLEL)) {
         return true;
      }

      return false;
   }

   public static boolean isSubflowSynchronous(Activity sbflwActivityDefinition) {
      String type = XPDLConstants.EXECUTION_SYNCHR;
      // Determine subflow type, if it is SYNCHR, terminate it
      SubFlow subflow = sbflwActivityDefinition.getActivityTypes()
         .getImplementation()
         .getImplementationTypes()
         .getSubFlow();
      type = subflow.getExecution();

      if (type.equals(XPDLConstants.EXECUTION_ASYNCHR)) {
         return false;
      }

      return true;
   }

   public static int getStartMode(Activity act) {
      int ret = XPDLConstants.ACTIVITY_MODE_NO_AUTOMATIC;
      String startMode = act.getStartMode();
      if (XPDLConstants.ACTIVITY_MODE_MANUAL.equals(startMode)) {
         ret = XPDLConstants.ACTIVITY_MODE_NO_MANUAL;
      }
      return ret;
   }

   public static int getFinishMode(Activity act) {
      int ret = XPDLConstants.ACTIVITY_MODE_NO_AUTOMATIC;
      String finishMode = act.getFinishMode();
      if (XPDLConstants.ACTIVITY_MODE_MANUAL.equals(finishMode)) {
         ret = XPDLConstants.ACTIVITY_MODE_NO_MANUAL;
      }
      return ret;
   }

   public static int removeXMLElementFromList(List l, XMLElement el) {
      int pos = XMLUtil.indexOfXMLElementWithinList(l, el);
      if (pos != -1) {
         l.remove(pos);
      }
      return pos;
   }

   public static int indexOfXMLElementWithinList(List l, XMLElement el) {
      int pos = -1;
      for (int i = 0; i < l.size(); i++) {
         Object o = l.get(i);
         if (o == el) {
            pos = i;
            break;
         }
      }
      return pos;
   }

   public static OutputStream packageToStream(org.enhydra.jxpdl.elements.Package pkg,
                                              OutputStream os) {
      try {
         Document document = null;

         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         DocumentBuilder dbuilder = dbf.newDocumentBuilder();
         document = dbuilder.newDocument();

         // Here we get all document elements set
         XPDLRepositoryHandler repH = new XPDLRepositoryHandler();
         repH.toXML(document, pkg);

         // Use a Transformer for output
         TransformerFactory tFactory = TransformerFactory.newInstance();
         Transformer transformer = tFactory.newTransformer();
         transformer.setOutputProperty("indent", "yes");
         transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
         transformer.setOutputProperty("encoding", "UTF-8");
         DOMSource source = new DOMSource(document);
         StreamResult result = new StreamResult(os);
         transformer.transform(source, result);
         return os;
      } catch (Exception ex) {
         ex.printStackTrace();
         return null;
      }
   }

   public static List getOrderedOutgoingTransitions(Activity fromActDef,
                                                    Set outTransitions) {
      // the iteration should be done on TransitionReferences collection,
      // in order to make ordered transition's list
      Set otCopy = new HashSet(outTransitions);
      List orderedOutTransitions = new ArrayList();
      Map trIdToTr = new HashMap();
      Iterator it = outTransitions.iterator();
      while (it.hasNext()) {
         Transition trans = (Transition) it.next();
         trIdToTr.put(trans.getId(), trans);
      }
      Split s = getSplit(fromActDef);
      if (s != null) {
         TransitionRefs trfs = s.getTransitionRefs();
         Iterator trefs = trfs.toElements().iterator();
         while (trefs.hasNext()) {
            TransitionRef tref = (TransitionRef) trefs.next();
            Transition trans = (Transition) trIdToTr.get(tref.getId());
            if (trans == null)
               continue;
            orderedOutTransitions.add(trans);
            otCopy.remove(trans);
         }
      }
      // if some of the transitions haven't been within transition refs
      // collection, put them into ordered transition list at the end
      orderedOutTransitions.addAll(otCopy);
      return orderedOutTransitions;
   }

   public static String createPath(String basedir, String relpath) {
      basedir = XMLUtil.convertToSystemPath(basedir);
      relpath = XMLUtil.convertToSystemPath(relpath);
      return (basedir + File.separator + relpath);
   }

   public static String convertToSystemPath(String path) {
      char separatorChar = File.separatorChar;
      char charToReplace;
      if (separatorChar == '\\') {
         charToReplace = '/';
      } else {
         charToReplace = '\\';
      }

      String systemPath = path.replace(charToReplace, separatorChar);
      return systemPath;
   }

   public static void main(String[] args) throws Throwable {
      if (ShowLicense.showLicense(args))
         return;
      if ("convert".equals(args[0])) {
         testConvert(args[1], true);
         return;
      } else if ("create".equals(args[0])) {
         testCreate(args[1]);
         return;
      }
      long t1, t2, t3, ts, te;
      ts = System.currentTimeMillis();
      boolean readExt = false;
      readExt = new Boolean(args[0]).booleanValue();
      XMLElementChangeListener list = new XMLElementChangeListener() {
         public void xmlElementChanged(XMLElementChangeInfo info) {
            System.out.println(info.toString());
         }
      };

      Package first = null;
      String firstIF = null;
      Package pkg = null;
      XMLInterface xmli = new XMLInterfaceForJDK13();
      for (int i = 1; i < args.length; i++) {
         String inputFile = args[i];
         if (i == 1)
            firstIF = inputFile;
         System.out.println("Handling file " + inputFile);
         t1 = System.currentTimeMillis();

         pkg = readFromFile(xmli, inputFile, readExt);
         if (i == 1) {
            first = pkg;
         }
         pkg.addListener(list);
         // testCloning(pkg);
         System.out.println("PKGL1=" + pkg.getListeners());
         Package p1 = pkg;
         StandardPackageValidator pv = new StandardPackageValidator();
         pv.init(new Properties(), xmli);
         System.out.println("VALIDATING ...");
         List verrs = new ArrayList();
         pv.validateElement(pkg, verrs, true);
         System.out.println("VALID=" + (verrs.size() == 0));
         if (verrs.size() > 0) {
            System.out.println("VERRS=" + verrs);
         }
         t2 = System.currentTimeMillis();
         // pkg.setReadOnly(true);
         writeToFile(inputFile + "r", pkg);
         t3 = System.currentTimeMillis();
         pkg = clonePackage(xmli, pkg);
         System.out.println("PKGL2=" + pkg.getListeners());
         // System.out.println(pkg.getExtendedAttributes().eaMap);
         // pkg.initCaches();
         // System.out.println(pkg.getExtendedAttributes().eaMap);
         Package p2 = pkg;
         long t4 = System.currentTimeMillis();
         writeToFile(inputFile + "rr", pkg);
         long t5 = System.currentTimeMillis();
         pkg = clonePackageBySerialization(pkg);
         System.out.println("PKGL3=" + pkg.getListeners());
         System.out.println("P1=P2=" + p1.equals(p2) + ", P2=P3=" + p2.equals(pkg));
         long t6 = System.currentTimeMillis();
         writeToFile(inputFile + "rrr", pkg);
         long t7 = System.currentTimeMillis();
         System.out.println("TOverall   =" + (t6 - t1));
         System.out.println("TOpenPKG   " + (t2 - t1));
         System.out.println("TSavePKG1  =" + (t3 - t2));
         System.out.println("TClonePkg1 =" + (t4 - t3));
         System.out.println("TSavePKG2  =" + (t5 - t3));
         System.out.println("TClonePkg2 =" + (t6 - t5));
         System.out.println("TSavePKG4  =" + (t7 - t6));
      }
      if (!first.equals(pkg)) {
         // testMakeAs(first, pkg);
         writeToFile(firstIF + "testma", pkg);
      }
      te = System.currentTimeMillis();
      System.out.println("Handling of "
                         + (args.length - 1) + " XPDLs lasted " + ((te - ts) / 1000)
                         + " sec");
   }

   public static void testConvert(String inputFile, boolean readExt) throws Exception {
      XMLElementChangeListener list = new XMLElementChangeListener() {
         public void xmlElementChanged(XMLElementChangeInfo info) {
            System.out.println(info.toString());
         }
      };

      Package pkg = null;
      XMLInterface xmli = new XMLInterfaceForJDK13();

      File f = new File(inputFile);
      inputFile = f.getCanonicalPath();

      System.out.println("Converting XPDL model from file \"" + inputFile + "\".\n");

      System.out.println("...reading file and creating XPDL model");
      pkg = readFromFile(xmli, inputFile, readExt);
      pkg.addListener(list);
      StandardPackageValidator pv = new StandardPackageValidator();
      pv.init(new Properties(), xmli);
      System.out.println("...validating XPDL");
      List verrs = new ArrayList();
      pv.validateElement(pkg, verrs, true);
      if (verrs.size() > 0) {
         if (pv.hasErrors(verrs)) {
            System.out.println("...XPDL is NOT valid");
            System.out.println("...errors=" + verrs);
         } else {
            System.out.println("...XPDL is valid ");
            System.out.println("...There are following warnings:" + verrs);
         }
      } else {
         System.out.println("...XPDL is valid");
      }
      int ind = inputFile.lastIndexOf(".");
      String outF = null;
      if (ind >= 0) {
         outF = inputFile.substring(0, ind) + "-out" + inputFile.substring(ind);
      } else {
         outF = inputFile + "-out";
      }
      System.out.println("\nWritting converted XPDL model into file \"" + outF + "\".");
      writeToFile(outF, pkg);
   }

   public static void testCreate(String outputFile) throws Exception {

      if (!outputFile.endsWith(".xpdl")) {
         outputFile += ".xpdl";
      }
      File f = new File(outputFile);
      outputFile = f.getCanonicalPath();
      String name = f.getName().substring(0, f.getName().lastIndexOf("."));

      System.out.println("Creating XPDL Model.\n");

      String id = name;
      if (!XMLUtil.isIdValid(id)) {
         id = "test";
      }

      System.out.println("...creating Package [Id="
                         + id + ",Name=" + name + ",Script-type=text/javascript]");
      Package pkg = new Package();
      pkg.setId(id);
      pkg.setName(name);
      pkg.getPackageHeader().setXPDLVersion("2.1");
      pkg.getPackageHeader().setVendor("(c) Together Teamsolutions Co., Ltd.");
      pkg.getPackageHeader().setCreated(XMLUtil.getCurrentDateAndTime());

      pkg.getScript().setType("text/javascript");

      System.out.println("......creating Participant[Id=manager,Name=Manager,Type=ROLE]");
      Participant p1 = (Participant) pkg.getParticipants().generateNewElement();
      p1.setId("manager");
      p1.setName("Manager");
      p1.getParticipantType().setTypeROLE();
      System.out.println("......creating Participant[Id=programmer,Name=Programmer,Type=ROLE]");
      Participant p2 = (Participant) pkg.getParticipants().generateNewElement();
      p2.setId("programmer");
      p2.setName("Programmer");
      p2.getParticipantType().setTypeROLE();
      System.out.println("......creating Participant[Id=secretary,Name=Secretary,Type=ROLE]");
      Participant p3 = (Participant) pkg.getParticipants().generateNewElement();
      p3.setId("secretary");
      p3.setName("Secretary");
      p3.getParticipantType().setTypeROLE();

      System.out.println("......creating Pool [Id="
                         + id + ",Name=" + name + ",Process=" + id + "]");
      Pool p = (Pool) pkg.getPools().generateNewElement();
      p.setId(id);
      p.setName(name);
      p.setProcess(id);

      System.out.println(".........creating Lane[Id=lane1,Name=Manager]");
      Lane l1 = (Lane) p.getLanes().generateNewElement();
      l1.setId("lane1");
      l1.setName("Manager");
      System.out.println("............creating Performer[manager]");
      Performer perf1 = (Performer) l1.getPerformers().generateNewElement();
      perf1.setValue("manager");
      l1.getPerformers().add(perf1);

      System.out.println(".........creating Lane[Id=lane2,Name=Programmer]");
      Lane l2 = (Lane) p.getLanes().generateNewElement();
      l2.setId("lane2");
      l2.setName("Programmer");
      System.out.println("............creating Performer[programmer]");
      Performer perf2 = (Performer) l2.getPerformers().generateNewElement();
      perf2.setValue("programmer");
      l2.getPerformers().add(perf2);

      System.out.println(".........creating Lane[Id=lane3,Name=Secretary]");
      Lane l3 = (Lane) p.getLanes().generateNewElement();
      l3.setId("lane3");
      l3.setName("Secretary");
      System.out.println("............creating Performer[secretary]");
      Performer perf3 = (Performer) l3.getPerformers().generateNewElement();
      perf3.setValue("secretary");
      l3.getPerformers().add(perf3);

      p.getLanes().add(l1);
      p.getLanes().add(l2);
      p.getLanes().add(l3);

      System.out.println("......creating WorkflowProcess [Id="
                         + id + ",Name=" + name + "]");
      WorkflowProcess wp = (WorkflowProcess) pkg.getWorkflowProcesses()
         .generateNewElement();
      wp.setId(id);
      wp.setName(name);

      System.out.println(".........creating DataField[Id=decision,Name=Decision,Type=Basic-BOOLEAN]");
      DataField df = (DataField) wp.getDataFields().generateNewElement();
      df.setId("decision");
      df.setName("Decision");
      df.getDataType().getDataTypes().getBasicType().setTypeBOOLEAN();
      wp.getDataFields().add(df);

      System.out.println(".........creating Activity[Id=act1,Name=First,Type=NO,Split-type=Exclusive]");
      Activity act1 = (Activity) wp.getActivities().generateNewElement();
      act1.setId("act1");
      act1.setName("First");
      act1.getActivityTypes().setImplementation();
      act1.getActivityTypes().getImplementation().getImplementationTypes().setNo();
      System.out.println("............creating TransitionRestriction");
      TransitionRestriction tre = (TransitionRestriction) act1.getTransitionRestrictions()
         .generateNewElement();
      System.out.println("...............creating TransitionRef[Id=tra1]");
      TransitionRef tref1 = (TransitionRef) tre.getSplit()
         .getTransitionRefs()
         .generateNewElement();
      tref1.setId("tra1");
      System.out.println("...............creating TransitionRef[Id=tra2]");
      TransitionRef tref2 = (TransitionRef) tre.getSplit()
         .getTransitionRefs()
         .generateNewElement();
      tref2.setId("tra2");
      tre.getSplit().getTransitionRefs().add(tref1);
      tre.getSplit().getTransitionRefs().add(tref2);
      tre.getSplit().setTypeExclusive();
      act1.getTransitionRestrictions().add(tre);
      System.out.println("............creating NodeGraphicsInfo[LaneId=lane1,Coordinates=150,50]");
      NodeGraphicsInfo ngi1 = (NodeGraphicsInfo) act1.getNodeGraphicsInfos()
         .generateNewElement();
      ngi1.setLaneId("lane1");
      ngi1.getCoordinates().setXCoordinate("150");
      ngi1.getCoordinates().setYCoordinate("50");
      act1.getNodeGraphicsInfos().add(ngi1);

      System.out.println(".........creating Activity[Id=act2,Name=Second,Type=NO]");
      Activity act2 = (Activity) wp.getActivities().generateNewElement();
      act2.setId("act2");
      act2.setName("Second");
      act2.getActivityTypes().setImplementation();
      act2.getActivityTypes().getImplementation().getImplementationTypes().setNo();
      System.out.println("............creating NodeGraphicsInfo[LaneId=lane2,Coordinates=350,50]");
      NodeGraphicsInfo ngi2 = (NodeGraphicsInfo) act2.getNodeGraphicsInfos()
         .generateNewElement();
      ngi2.setLaneId("lane2");
      ngi2.getCoordinates().setXCoordinate("350");
      ngi2.getCoordinates().setYCoordinate("50");
      act2.getNodeGraphicsInfos().add(ngi2);

      System.out.println(".........creating Activity[Id=act3,Name=Third,Type=NO]");
      Activity act3 = (Activity) wp.getActivities().generateNewElement();
      act3.setId("act3");
      act3.setName("Third");
      act3.getActivityTypes().setImplementation();
      act3.getActivityTypes().getImplementation().getImplementationTypes().setNo();
      System.out.println("............creating NodeGraphicsInfo[LaneId=lane3,Coordinates=350,50]");
      NodeGraphicsInfo ngi3 = (NodeGraphicsInfo) act3.getNodeGraphicsInfos()
         .generateNewElement();
      ngi3.setLaneId("lane3");
      ngi3.getCoordinates().setXCoordinate("350");
      ngi3.getCoordinates().setYCoordinate("50");
      act3.getNodeGraphicsInfos().add(ngi3);

      System.out.println(".........creating Transition[Id=tra1,From=act1,To=act2,Type=CONDITION,Condition=decision]");
      Transition tra1 = (Transition) wp.getTransitions().generateNewElement();
      tra1.setId("tra1");
      tra1.setFrom("act1");
      tra1.setTo("act2");
      tra1.getCondition().setTypeCONDITION();
      tra1.getCondition().setValue("decision");
      System.out.println(".........creating Transition[Id=tra1,From=act1,To=act3,Type=OTHERWISE]");
      Transition tra2 = (Transition) wp.getTransitions().generateNewElement();
      tra2.setId("tra2");
      tra2.setFrom("act1");
      tra2.setTo("act3");
      tra2.getCondition().setTypeOTHERWISE();

      wp.getActivities().add(act1);
      wp.getActivities().add(act2);
      wp.getActivities().add(act3);
      wp.getTransitions().add(tra1);
      wp.getTransitions().add(tra2);

      pkg.getWorkflowProcesses().add(wp);
      pkg.getPools().add(p);
      pkg.getParticipants().add(p1);
      pkg.getParticipants().add(p2);
      pkg.getParticipants().add(p3);

      writeToFile(outputFile, pkg);
      System.out.println("\nWritting XPDL model into file \"" + outputFile + "\".");
   }

   public static Package readFromFile(XMLInterface xmli, String inputFile, boolean readExt)
      throws Exception {
      return xmli.openPackage(inputFile, readExt);
   }

   public static void writeToFile(String outputFile, Package pkg) throws Exception {
      // System.out.println("PKGEPS=" + pkg.getExternalPackageIds());
      Document document = null;
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder dbuilder = dbf.newDocumentBuilder();
      document = dbuilder.newDocument();
      // output stream will either be the FileOutputStream in the
      // case of save as, or the ByteArrayOutputStream if we are
      // saving an existing file
      FileOutputStream os;
      // try to open random access file as rw, if it fails
      // the saving shouldn't occur
      os = new FileOutputStream(outputFile);

      // Here we get all document elements set
      XPDLRepositoryHandler repH = new XPDLRepositoryHandler();
      repH.setXPDLPrefixEnabled(true);
      repH.toXML(document, pkg);

      // Use a Transformer for output
      TransformerFactory tFactory = TransformerFactory.newInstance();
      Transformer transformer = tFactory.newTransformer();
      transformer.setOutputProperty("indent", "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
      transformer.setOutputProperty("encoding", "UTF8");
      DOMSource source = new DOMSource(document);
      StreamResult result = new StreamResult(os);
      transformer.transform(source, result);

      os.close();
   }

   public static Package clonePackage(XMLInterface xmli, Package pkg) throws Exception {
      Package cloned = (Package) pkg.clone();
      if (cloned.isReadOnly()) {
         cloned.initCaches(xmli);
      }
      return cloned;
   }

   public static Package clonePackageBySerialization(Package pkg) throws Exception {
      byte[] ser = serialize(pkg);
      Package cloned = (Package) deserialize(ser);
      return cloned;
   }

   public static byte[] serialize(Object obj) throws Exception {
      // System.err.println(" ser ##"+obj);
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      ObjectOutputStream oout = new ObjectOutputStream(bout);
      oout.writeObject(obj);
      oout.flush();
      byte array[] = bout.toByteArray();
      oout.close();
      bout.close();
      // System.err.println(" ser #"+new String(array));
      return array;
   }

   public static Object deserialize(byte[] array) throws Exception {
      // System.err.println("neser#"+new String(array));
      ObjectInputStream rin = new ObjectInputStream(new ByteArrayInputStream(array));
      Object obj = rin.readObject();
      rin.close();
      // System.err.println("neser##"+obj);
      return obj;
   }

   /**
    * Take the given string and chop it up into a series of strings on given boundries.
    * This is useful for trying to get an array of strings out of the resource file.
    */
   public static String[] tokenize(String input, String boundary) {
      if (input == null)
         input = "";
      StringTokenizer t = new StringTokenizer(input, boundary);
      String cmd[] = new String[t.countTokens()];
      int j = 0;
      while (t.hasMoreTokens())
         cmd[j++] = t.nextToken();

      return cmd;
   }

   // unique Ids
   public static String generateUniqueId(XMLCollection cel, Set skipIds) {
      String id;
      long nextId = 0;
      String prefix = prefix = XMLUtil.getPackage(cel).getId() + "_";
      if (XMLUtil.getWorkflowProcess(cel) != null) {
         prefix = XMLUtil.getWorkflowProcess(cel).getId() + "_";
      } else if (XMLUtil.getPool(cel) != null) {
         prefix = XMLUtil.getPool(cel).getId() + "_";
      }
      if (cel instanceof Activities) {
         prefix += "act";
      } else if (cel instanceof ActivitySets) {
         prefix += "ase";
      } else if (cel instanceof Applications) {
         prefix += "app";
      } else if (cel instanceof Artifacts) {
         prefix += "art";
      } else if (cel instanceof Associations) {
         prefix += "ass";
      } else if (cel instanceof DataFields) {
         prefix += "df";
      } else if (cel instanceof FormalParameters) {
         prefix += "fp";
      } else if (cel instanceof Lanes) {
         prefix += "lan";
      } else if (cel instanceof Participants) {
         prefix += "par";
      } else if (cel instanceof Pools) {
         prefix += "pool";
      } else if (cel instanceof Transitions) {
         prefix += "tra";
      } else if (cel instanceof TypeDeclarations) {
         prefix += "td";
      } else if (cel instanceof WorkflowProcesses) {
         prefix = XMLUtil.getPackage(cel).getId() + "_wp";
      }

      XMLCollectionElement cl = (XMLCollectionElement) cel.generateNewElement();
      do {
         id = prefix + new Long(++nextId).toString();
      } while (skipIds.contains(id) || !XMLUtil.isIdUnique(cl, id));
      return id;
   }

   public static String generateSimilarOrIdenticalUniqueId(XMLCollection cel,
                                                           Set skipIds,
                                                           String origId) {
      String id = origId;
      long nextId = 0;

      XMLCollectionElement cl = (XMLCollectionElement) cel.generateNewElement();
      while (id.equals("") || skipIds.contains(id) || !XMLUtil.isIdUnique(cl, id)) {
         id = origId + new Long(++nextId).toString();
      }
      return id;
   }

   public static boolean isIdUnique(XMLCollectionElement el, String newId) {

      XMLElement parent = el.getParent();
      if (el instanceof Tool)
         return true;
      else if (el instanceof Activity)
         return XMLUtil.checkActivityId((Activity) el, newId);
      else if (el instanceof Artifact)
         return XMLUtil.checkArtifactId((Artifact) el, newId);
      else if (el instanceof Transition)
         return XMLUtil.checkTransitionId((Transition) el, newId);
      else if (el instanceof ActivitySet)
         return XMLUtil.checkActivitySetId((ActivitySet) el, newId);
      else if (el instanceof Lane)
         return XMLUtil.checkLaneId((Lane) el, newId);
      else if (parent instanceof XMLCollection) {
         List elsWithId = XMLUtil.getElementsForId((XMLCollection) parent, newId);
         if (elsWithId.size() == 0 || (elsWithId.size() == 1 && elsWithId.contains(el))) {
            return true;
         }
         return false;
      } else {
         return true;
      }
   }

   public static boolean checkActivityId(Activity newEl, String newId) {
      return checkActivityOrArtifactId(newEl, newId);
   }

   public static boolean checkArtifactId(Artifact newEl, String newId) {
      return checkActivityOrArtifactId(newEl, newId);
   }

   protected static boolean checkActivityOrArtifactId(XMLCollectionElement newEl,
                                                      String newId) {
      List elsWithId = new ArrayList();
      elsWithId.addAll(XMLUtil.getElementsForId(XMLUtil.getPackage(newEl).getArtifacts(),
                                                newId));
      Iterator it = XMLUtil.getPackage(newEl)
         .getWorkflowProcesses()
         .toElements()
         .iterator();
      while (it.hasNext()) {
         WorkflowProcess proc = (WorkflowProcess) it.next();
         elsWithId.addAll(XMLUtil.getElementsForId(proc.getActivities(), newId));
         ActivitySets actSets = proc.getActivitySets();
         for (int y = 0; y < actSets.size(); y++) {
            ActivitySet actSet = (ActivitySet) actSets.get(y);
            elsWithId.addAll(XMLUtil.getElementsForId(actSet.getActivities(), newId));
         }
      }
      if (elsWithId.size() == 0 || (elsWithId.size() == 1 && elsWithId.contains(newEl))) {
         return true;
      }
      return false;
   }

   public static boolean checkTransitionId(Transition newEl, String newId) {
      WorkflowProcess proc = XMLUtil.getWorkflowProcess(newEl);
      List elsWithId = XMLUtil.getElementsForId(proc.getTransitions(), newId);
      ActivitySets actSets = proc.getActivitySets();
      for (int y = 0; y < actSets.size(); y++) {
         ActivitySet actSet = (ActivitySet) actSets.get(y);
         elsWithId.addAll(XMLUtil.getElementsForId(actSet.getTransitions(), newId));
      }
      if (elsWithId.size() == 0 || (elsWithId.size() == 1 && elsWithId.contains(newEl))) {
         return true;
      }
      return false;
   }

   public static boolean checkActivitySetId(ActivitySet newEl, String newId) {
      WorkflowProcesses wps = XMLUtil.getPackage(newEl).getWorkflowProcesses();
      Iterator it = wps.toElements().iterator();
      List elsWithId = getElementsForId(wps, newId);
      while (it.hasNext()) {
         WorkflowProcess wp = (WorkflowProcess) it.next();
         elsWithId.addAll(getElementsForId(wp.getActivitySets(), newId));
      }
      if (elsWithId.size() == 0 || (elsWithId.size() == 1 && elsWithId.contains(newEl))) {
         return true;
      }
      return false;
   }

   public static boolean checkLaneId(Lane newEl, String newId) {
      Iterator it = XMLUtil.getPackage(newEl).getPools().toElements().iterator();
      List elsWithId = new ArrayList();
      while (it.hasNext()) {
         Pool p = (Pool) it.next();
         elsWithId.addAll(getElementsForId(p.getLanes(), newId));
      }
      if (elsWithId.size() == 0 || (elsWithId.size() == 1 && elsWithId.contains(newEl))) {
         return true;
      }
      return false;
   }

   public static List getElementsForId(XMLCollection col, String id) {
      List elsWithId = new ArrayList();
      Iterator it = col.toElements().iterator();
      if (col.generateNewElement() instanceof XMLCollectionElement) {
         while (it.hasNext()) {
            XMLCollectionElement ce = (XMLCollectionElement) it.next();
            if (ce.getId().equals(id)) {
               elsWithId.add(ce);
            }
         }
      }

      return elsWithId;
   }

   // XPDL Utilities
   public static boolean hasCircularTransitions(Set transitions) {
      Iterator it = transitions.iterator();
      while (it.hasNext()) {
         Transition t = (Transition) it.next();
         if (t.getFrom().equals(t.getTo()))
            return true;
      }
      return false;
   }

   public static boolean isStartingActivity(Activity act) {
      Set trans = XMLUtil.getIncomingTransitions(act);
      if (trans.size() == 0 || (trans.size() == 1 && hasCircularTransitions(trans))) {
         return true;
      }
      return false;
   }

   public static boolean isEndingActivity(Activity act) {
      Set trans = XMLUtil.getNonExceptionalOutgoingTransitions(act);
      if (trans.size() == 0 || (trans.size() == 1 && hasCircularTransitions(trans))) {
         return true;
      }
      return false;
   }

   public static List getReferences(XMLComplexElement pkgOrWp,
                                    XMLComplexElement referenced,
                                    XMLInterface xmli) {
      if (pkgOrWp instanceof Package) {
         return getReferences((Package) pkgOrWp, referenced, xmli);
      } else if (pkgOrWp instanceof WorkflowProcess) {
         return getReferences((WorkflowProcess) pkgOrWp, referenced);
      }
      return new ArrayList();
   }

   public static List getReferences(Package pkg,
                                    XMLComplexElement referenced,
                                    XMLInterface xmli) {
      if (referenced instanceof Package) {
         return getReferences((Package) referenced, xmli);
      } else if (referenced instanceof TypeDeclaration) {
         return getReferences(pkg, (TypeDeclaration) referenced);
      } else if (referenced instanceof Participant) {
         return getReferences(pkg, (Participant) referenced);
      } else if (referenced instanceof Application) {
         return getReferences(pkg, (Application) referenced);
      } else if (referenced instanceof DataField) {
         return getReferences(pkg, (DataField) referenced);
      } else if (referenced instanceof WorkflowProcess) {
         return getReferences(pkg, (WorkflowProcess) referenced);
      } else if (referenced instanceof Lane) {
         return getReferences(pkg, (Lane) referenced);
      }
      return new ArrayList();
   }

   public static List getReferences(WorkflowProcess wp, XMLComplexElement referenced) {
      if (referenced instanceof Participant) {
         return getReferences(wp, (Participant) referenced);
      } else if (referenced instanceof Application) {
         return getReferences(wp, (Application) referenced);
      } else if (referenced instanceof DataField) {
         return getReferences(wp, (DataField) referenced);
      } else if (referenced instanceof WorkflowProcess) {
         return getReferences(wp, (WorkflowProcess) referenced);
      } else if (referenced instanceof FormalParameter) {
         return getReferences(wp, (FormalParameter) referenced);
      } else if (referenced instanceof ActivitySet) {
         return getReferences(wp, (ActivitySet) referenced);
      } else if (referenced instanceof Lane) {
         return getReferences(wp, (Lane) referenced);
      }

      return new ArrayList();
   }

   public static List getReferences(Package pkg, XMLInterface xmli) {
      List references = new ArrayList();
      String pkgId = pkg.getId();
      Iterator it = xmli.getAllPackages().iterator();
      while (it.hasNext()) {
         Package p = (Package) it.next();
         if (p.getExternalPackageIds().contains(pkgId)) {
            references.add(p);
         }
      }

      return references;

   }

   public static List getAllExternalPackageReferences(Package pkg, Package referenced) {
      List references = new ArrayList();

      if (referenced != null) {
         Iterator it = referenced.getApplications().toElements().iterator();
         while (it.hasNext()) {
            Application app = (Application) it.next();
            references.addAll(getReferences(pkg, app));
         }
         it = referenced.getParticipants().toElements().iterator();
         while (it.hasNext()) {
            Participant par = (Participant) it.next();
            references.addAll(getReferences(pkg, par));
         }
         it = referenced.getWorkflowProcesses().toElements().iterator();
         while (it.hasNext()) {
            WorkflowProcess wp = (WorkflowProcess) it.next();
            references.addAll(getReferences(pkg, wp));
         }
      }

      return references;
   }

   public static List getReferences(Package pkg, TypeDeclaration referenced) {
      return getTypeDeclarationReferences(pkg, referenced.getId());
   }

   public static List getReferences(WorkflowProcess wp, TypeDeclaration referenced) {
      return tGetTypeDeclarationReferences(wp, referenced.getId());
   }

   public static List getReferences(TypeDeclaration td, TypeDeclaration referenced) {
      if (td.getId().equals(referenced.getId()))
         return new ArrayList();
      return getReferencingDeclaredTypes(td.getDataTypes(), referenced.getId());
   }

   public static List getTypeDeclarationReferences(Package pkg, String referencedId) {
      List references = new ArrayList();
      if (referencedId.equals("")) {
         return references;
      }
      Iterator it = pkg.getTypeDeclarations().toElements().iterator();
      while (it.hasNext()) {
         TypeDeclaration td = (TypeDeclaration) it.next();
         if (td.getId().equals(referencedId))
            continue;
         references.addAll(getReferencingDeclaredTypes(td.getDataTypes(), referencedId));
      }

      references.addAll(tGetTypeDeclarationReferences(pkg, referencedId));

      it = pkg.getWorkflowProcesses().toElements().iterator();
      while (it.hasNext()) {
         WorkflowProcess wp = (WorkflowProcess) it.next();
         references.addAll(tGetTypeDeclarationReferences(wp, referencedId));
      }

      return references;
   }

   protected static List tGetTypeDeclarationReferences(XMLComplexElement pkgOrWp,
                                                       String referencedId) {
      List references = new ArrayList();
      if (referencedId.equals("")) {
         return references;
      }

      Iterator it = ((Applications) pkgOrWp.get("Applications")).toElements().iterator();
      while (it.hasNext()) {
         Application app = (Application) it.next();
         Iterator fps = app.getApplicationTypes()
            .getFormalParameters()
            .toElements()
            .iterator();
         while (fps.hasNext()) {
            Object obj = fps.next();
            // System.err.println(obj.getClass().getName());
            FormalParameter fp = (FormalParameter) obj;
            references.addAll(getReferencingDeclaredTypes(fp.getDataType().getDataTypes(),
                                                          referencedId));
         }
      }
      it = ((DataFields) pkgOrWp.get("DataFields")).toElements().iterator();
      while (it.hasNext()) {
         DataField df = (DataField) it.next();
         references.addAll(getReferencingDeclaredTypes(df.getDataType().getDataTypes(),
                                                       referencedId));
      }
      if (pkgOrWp instanceof WorkflowProcess) {
         it = ((WorkflowProcess) pkgOrWp).getFormalParameters().toElements().iterator();
         while (it.hasNext()) {
            FormalParameter fp = (FormalParameter) it.next();
            references.addAll(getReferencingDeclaredTypes(fp.getDataType().getDataTypes(),
                                                          referencedId));
         }
      }

      return references;
   }

   public static List getReferencingDeclaredTypes(DataTypes dts, String typeDeclarationId) {
      List toRet = new ArrayList();
      if (typeDeclarationId.equals("")) {
         return toRet;
      }

      XMLElement choosen = dts.getChoosen();
      if (choosen instanceof DeclaredType) {
         if (((DeclaredType) choosen).getId().equals(typeDeclarationId)) {
            toRet.add(choosen);
         }
      } else if (choosen instanceof ArrayType) {
         return getReferencingDeclaredTypes(((ArrayType) choosen).getDataTypes(),
                                            typeDeclarationId);
      } else if (choosen instanceof ListType) {
         return getReferencingDeclaredTypes(((ListType) choosen).getDataTypes(),
                                            typeDeclarationId);
      } else if (choosen instanceof RecordType || choosen instanceof UnionType) {
         Iterator it = ((XMLCollection) choosen).toElements().iterator();
         while (it.hasNext()) {
            Member m = (Member) it.next();
            toRet.addAll(getReferencingDeclaredTypes(m.getDataTypes(), typeDeclarationId));
         }
      }

      return toRet;
   }

   public static List getReferences(Artifact referenced) {
      return getArtifactReferences(XMLUtil.getPackage(referenced), referenced.getId());
   }

   public static List getArtifactReferences(Package pkg, String referencedId) {
      List references = new ArrayList();
      if (referencedId.equals("")) {
         return references;
      }

      Iterator it = pkg.getAssociations().toElements().iterator();
      while (it.hasNext()) {
         Association asoc = (Association) it.next();
         if (asoc.getSource().equals(referencedId)) {
            references.add(asoc.get("Source"));
         } else if (asoc.getTarget().equals(referencedId)) {
            references.add(asoc.get("Target"));
         }
      }

      return references;
   }

   public static List getReferences(Package pkg, Association referenced) {
      List references = new ArrayList();
      if (referenced.getId().equals("")) {
         return references;
      }

      references.addAll(tGetAssociationReferences(pkg, referenced));

      Iterator it = pkg.getWorkflowProcesses().toElements().iterator();
      while (it.hasNext()) {
         WorkflowProcess wp = (WorkflowProcess) it.next();
         references.addAll(getReferences(wp, referenced));
      }

      return references;
   }

   public static List getReferences(WorkflowProcess wp, Association referenced) {
      List references = new ArrayList();
      if (referenced.getId().equals("")) {
         return references;
      }
      references.addAll(tGetAssociationReferences(wp, referenced));

      Iterator it = wp.getActivitySets().toElements().iterator();
      while (it.hasNext()) {
         ActivitySet as = (ActivitySet) it.next();
         references.addAll(tGetAssociationReferences(as, referenced));
      }

      return references;
   }

   public static List getReferences(ActivitySet as, Association referenced) {
      return tGetAssociationReferences(as, referenced);
   }

   protected static List tGetAssociationReferences(XMLComplexElement pkgOrWpOrAs,
                                                   Association referenced) {
      List references = new ArrayList();
      if (referenced.getId().equals("")) {
         return references;
      }

      if (pkgOrWpOrAs instanceof Package) {
         Iterator it = ((Package) pkgOrWpOrAs).getArtifacts().toElements().iterator();
         while (it.hasNext()) {
            Artifact art = (Artifact) it.next();
            if (referenced.getSource().equals(art.getId())
                || referenced.getTarget().equals(art.getId())) {
               references.add(referenced);
            }
         }
      } else {
         Iterator it = ((Activities) pkgOrWpOrAs.get("Activities")).toElements()
            .iterator();
         while (it.hasNext()) {
            Activity act = (Activity) it.next();
            if (referenced.getSource().equals(act.getId())
                || referenced.getTarget().equals(act.getId())) {
               references.add(referenced);
            }
         }
      }

      return references;
   }

   public static List getParticipantReferences(XMLComplexElement pkgOrWp,
                                               String referencedId) {
      if (referencedId.equals("")) {
         return new ArrayList();
      }
      if (pkgOrWp instanceof Package) {
         return getParticipantReferences((Package) pkgOrWp, referencedId);
      }

      return getParticipantReferences((WorkflowProcess) pkgOrWp, referencedId);
   }

   public static List getReferences(Package pkg, Participant referenced) {
      if (XMLUtil.getPackage(referenced) != pkg
          && pkg.getParticipant(referenced.getId()) != null) {
         return new ArrayList();
      }

      return getParticipantReferences(pkg, referenced.getId());
   }

   public static List getParticipantReferences(Package pkg, String referencedId) {
      List references = new ArrayList();
      if (referencedId.equals("")) {
         return references;
      }

      references.addAll(tGetParticipantReferences(pkg, referencedId));

      Iterator it = pkg.getWorkflowProcesses().toElements().iterator();
      while (it.hasNext()) {
         WorkflowProcess wp = (WorkflowProcess) it.next();
         if (wp.getParticipant(referencedId) == null) {
            references.addAll(getParticipantReferences(wp, referencedId));
         }
      }

      return references;
   }

   public static List getReferences(WorkflowProcess wp, Participant referenced) {
      if (XMLUtil.getWorkflowProcess(referenced) == null
          && wp.getParticipant(referenced.getId()) != null) {
         return new ArrayList();
      }
      Package pkg = XMLUtil.getPackage(wp);
      if (XMLUtil.getPackage(referenced) != pkg
          && pkg.getParticipant(referenced.getId()) != null) {
         return new ArrayList();
      }

      return getParticipantReferences(wp, referenced.getId());
   }

   public static List getParticipantReferences(WorkflowProcess wp, String referencedId) {
      List references = new ArrayList();
      if (referencedId.equals("")) {
         return references;
      }
      references.addAll(tGetParticipantReferences(wp, referencedId));

      Iterator it = wp.getActivitySets().toElements().iterator();
      while (it.hasNext()) {
         ActivitySet as = (ActivitySet) it.next();
         references.addAll(tGetParticipantReferences(as, referencedId));
      }

      return references;
   }

   protected static List tGetParticipantReferences(XMLComplexElement pkgOrWpOrAs,
                                                   String referencedId) {
      List references = new ArrayList();
      if (referencedId.equals("")) {
         return references;
      }

      Iterator pi = XMLUtil.getPackage(pkgOrWpOrAs).getPools().toElements().iterator();
      while (pi.hasNext()) {
         Pool p = (Pool) pi.next();
         Iterator li = p.getLanes().toElements().iterator();
         while (li.hasNext()) {
            Lane l = (Lane) li.next();
            Iterator pri = l.getPerformers().toElements().iterator();
            while (pri.hasNext()) {
               Performer perf = (Performer) pri.next();
               if (perf.toValue().equals(referencedId)) {
                  if (!references.contains(perf)) {
                     references.add(perf);
                  }
               }
            }
         }
      }

      if (!(pkgOrWpOrAs instanceof ActivitySet)) {
         Iterator it = ((RedefinableHeader) pkgOrWpOrAs.get("RedefinableHeader")).getResponsibles()
            .toElements()
            .iterator();
         while (it.hasNext()) {
            Responsible rs = (Responsible) it.next();
            if (rs.toValue().equals(referencedId)) {
               references.add(rs);
            }
         }
      }
      if (!(pkgOrWpOrAs instanceof Package)) {
         Iterator it = ((Activities) pkgOrWpOrAs.get("Activities")).toElements()
            .iterator();
         while (it.hasNext()) {
            Activity act = (Activity) it.next();
            String perf = act.getFirstPerformer();
            if (perf.equals(referencedId)) {
               references.add(act.getFirstPerformerObj());
            }
         }
      }

      return references;
   }

   public static List getApplicationReferences(XMLComplexElement pkgOrWp,
                                               String referencedId) {
      if (referencedId.equals("")) {
         return new ArrayList();
      }
      if (pkgOrWp instanceof Package) {
         return getApplicationReferences((Package) pkgOrWp, referencedId);
      }

      return getApplicationReferences((WorkflowProcess) pkgOrWp, referencedId);
   }

   public static List getReferences(Package pkg, Application referenced) {
      if (XMLUtil.getPackage(referenced) != pkg
          && pkg.getApplication(referenced.getId()) != null) {
         return new ArrayList();
      }

      return getApplicationReferences(pkg, referenced.getId());
   }

   public static List getApplicationReferences(Package pkg, String referencedId) {
      List references = new ArrayList();
      if (referencedId.equals("")) {
         return references;
      }

      Iterator it = pkg.getWorkflowProcesses().toElements().iterator();
      while (it.hasNext()) {
         WorkflowProcess wp = (WorkflowProcess) it.next();
         if (wp.getApplication(referencedId) == null) {
            references.addAll(getApplicationReferences(wp, referencedId));
         }
      }

      return references;
   }

   public static List getReferences(WorkflowProcess wp, Application referenced) {
      if (XMLUtil.getWorkflowProcess(referenced) == null
          && wp.getApplication(referenced.getId()) != null) {
         return new ArrayList();
      }
      Package pkg = XMLUtil.getPackage(wp);
      if (XMLUtil.getPackage(referenced) != pkg
          && pkg.getParticipant(referenced.getId()) != null) {
         return new ArrayList();
      }

      return getApplicationReferences(wp, referenced.getId());
   }

   public static List getApplicationReferences(WorkflowProcess wp, String referencedId) {
      List references = new ArrayList();
      if (referencedId.equals("")) {
         return references;
      }

      references.addAll(tGetApplicationReferences(wp, referencedId));

      Iterator it = wp.getActivitySets().toElements().iterator();
      while (it.hasNext()) {
         ActivitySet as = (ActivitySet) it.next();
         references.addAll(tGetApplicationReferences(as, referencedId));
      }

      return references;
   }

   protected static List tGetApplicationReferences(XMLCollectionElement wpOrAs,
                                                   String referencedId) {
      List references = new ArrayList();
      if (referencedId.equals("")) {
         return references;
      }

      List types = new ArrayList();
      types.add(new Integer(XPDLConstants.ACTIVITY_TYPE_TASK_APPLICATION));
      Iterator it = getActivities((Activities) wpOrAs.get("Activities"), types).iterator();
      while (it.hasNext()) {
         Activity act = (Activity) it.next();
         TaskApplication ta = act.getActivityTypes()
            .getImplementation()
            .getImplementationTypes()
            .getTask()
            .getTaskTypes()
            .getTaskApplication();
         if (ta.getId().equals(referencedId)) {
            references.add(ta);
         }
      }

      return references;
   }

   public static List getLaneReferences(XMLComplexElement pkgOrWp, String referencedId) {
      if (referencedId.equals("")) {
         return new ArrayList();
      }
      if (pkgOrWp instanceof Package) {
         return getLaneReferences((Package) pkgOrWp, referencedId);
      }

      return getLaneReferences((WorkflowProcess) pkgOrWp, referencedId);
   }

   public static List getReferences(Package pkg, Lane referenced) {
      return getLaneReferences(pkg, referenced.getId());
   }

   public static List getLaneReferences(Package pkg, String referencedId) {
      List references = new ArrayList();
      if (referencedId.equals("")) {
         return references;
      }

      references.addAll(tGetLaneReferences(pkg, referencedId));

      List plsWithLane = new ArrayList();
      Iterator it = pkg.getPools().toElements().iterator();
      while (it.hasNext()) {
         Pool p = (Pool) it.next();
         if (p.getLanes().getLane(referencedId) != null) {
            plsWithLane.add(p);
         }
      }
      it = plsWithLane.iterator();
      while (it.hasNext()) {
         Pool p = (Pool) it.next();
         WorkflowProcess wp = getProcessForPool(p);
         if (wp != null) {
            references.addAll(tGetLaneReferences(wp, referencedId));
         } else {
            ActivitySet as = getActivitySet(p);
            if (as != null) {
               references.addAll(tGetLaneReferences(as, referencedId));
            }
         }
      }

      return references;
   }

   public static List getReferences(WorkflowProcess wp, Lane referenced) {
      return getLaneReferences(wp, referenced.getId());
   }

   public static List getLaneReferences(WorkflowProcess wp, String referencedId) {
      List references = new ArrayList();
      if (referencedId.equals("")) {
         return references;
      }

      references.addAll(tGetLaneReferences(wp, referencedId));

      Iterator it = wp.getActivitySets().toElements().iterator();
      while (it.hasNext()) {
         ActivitySet as = (ActivitySet) it.next();
         references.addAll(tGetLaneReferences(as, referencedId));
      }

      return references;
   }

   public static List tGetLaneReferences(XMLComplexElement pkgOrWpOrAs,
                                         String referencedId) {
      List references = new ArrayList();
      if (referencedId.equals("")) {
         return references;
      }

      if (pkgOrWpOrAs instanceof Package) {
         Iterator pi = ((Package) pkgOrWpOrAs).getPools().toElements().iterator();
         while (pi.hasNext()) {
            Pool p = (Pool) pi.next();
            if (p.getLanes().getLane(referencedId) != null) {
               Iterator li = p.getLanes().toElements().iterator();
               while (li.hasNext()) {
                  Lane l = (Lane) li.next();
                  Iterator nli = l.getNestedLanes().toElements().iterator();
                  while (nli.hasNext()) {
                     NestedLane nl = (NestedLane) nli.next();
                     if (nl.getLaneId().equals(referencedId)) {
                        references.add(nl);
                     }
                  }
               }
               Iterator ait = ((Package) pkgOrWpOrAs).getArtifacts()
                  .toElements()
                  .iterator();
               while (ait.hasNext()) {
                  Artifact a = (Artifact) ait.next();
                  Iterator ngit = a.getNodeGraphicsInfos().toElements().iterator();
                  while (ngit.hasNext()) {
                     NodeGraphicsInfo ngi = (NodeGraphicsInfo) ngit.next();
                     if (referencedId.equals(ngi.getLaneId())) {
                        references.add(ngi);
                     }
                  }
               }
            }
         }
      } else {
         Iterator it = ((Activities) pkgOrWpOrAs.get("Activities")).toElements()
            .iterator();
         while (it.hasNext()) {
            Activity act = (Activity) it.next();
            Iterator itn = act.getNodeGraphicsInfos().toElements().iterator();
            while (itn.hasNext()) {
               NodeGraphicsInfo ngi = (NodeGraphicsInfo) itn.next();
               if (ngi.getLaneId().equals(referencedId)) {
                  references.add(ngi);
               }
            }
         }
      }

      return references;
   }

   public static List getDataFieldReferences(XMLComplexElement pkgOrWp,
                                             String referencedId) {
      if (referencedId.equals("")) {
         return new ArrayList();
      }
      if (pkgOrWp instanceof Package) {
         return getDataFieldReferences((Package) pkgOrWp, referencedId);
      }

      return getDataFieldReferences((WorkflowProcess) pkgOrWp, referencedId);
   }

   public static List getReferences(Package pkg, DataField referenced) {
      return getDataFieldReferences(pkg, referenced.getId());
   }

   public static List getDataFieldReferences(Package pkg, String referencedId) {
      List references = new ArrayList();
      if (referencedId.equals("")) {
         return references;
      }

      Iterator it = pkg.getWorkflowProcesses().toElements().iterator();
      while (it.hasNext()) {
         WorkflowProcess wp = (WorkflowProcess) it.next();
         if (wp.getDataField(referencedId) == null) {
            references.addAll(getDataFieldReferences(wp, referencedId));
         }
      }

      return references;
   }

   public static List getReferences(WorkflowProcess wp, DataField referenced) {
      if (XMLUtil.getWorkflowProcess(referenced) == null
          && (wp.getDataField(referenced.getId()) != null || wp.getFormalParameter(referenced.getId()) != null)) {
         return new ArrayList();
      }

      return getDataFieldReferences(wp, referenced.getId());
   }

   public static List getDataFieldReferences(WorkflowProcess wp, String referencedId) {
      List references = new ArrayList();
      if (referencedId.equals("")) {
         return references;
      }

      references.addAll(getVariableReferences(wp, referencedId));

      Iterator it = wp.getActivitySets().toElements().iterator();
      while (it.hasNext()) {
         ActivitySet as = (ActivitySet) it.next();
         references.addAll(getVariableReferences(as, referencedId));
      }

      return references;
   }

   public static List getReferences(Package pkg, WorkflowProcess referenced) {
      List references = new ArrayList();
      if (XMLUtil.getPackage(referenced) != pkg
          && pkg.getWorkflowProcess(referenced.getId()) != null) {
         return references;
      }

      Iterator it = pkg.getPools().toElements().iterator();
      while (it.hasNext()) {
         Pool p = (Pool) it.next();
         if (p.getProcess().equals(referenced.getId())) {
            references.add(p);
         }
      }

      it = pkg.getWorkflowProcesses().toElements().iterator();
      while (it.hasNext()) {
         WorkflowProcess wp = (WorkflowProcess) it.next();
         references.addAll(getReferences(wp, referenced));
      }

      return references;
   }

   public static List getWorkflowProcessReferences(Package pkg, String referencedId) {
      List references = new ArrayList();
      if (referencedId.equals("")) {
         return references;
      }

      Iterator it = pkg.getPools().toElements().iterator();
      while (it.hasNext()) {
         Pool p = (Pool) it.next();
         if (p.getProcess().equals(referencedId)) {
            references.add(p);
         }
      }

      it = pkg.getWorkflowProcesses().toElements().iterator();
      while (it.hasNext()) {
         WorkflowProcess wp = (WorkflowProcess) it.next();
         references.addAll(getWorkflowProcessReferences(wp, referencedId));
      }

      return references;
   }

   public static List getReferences(WorkflowProcess wp, WorkflowProcess referenced) {
      return getWorkflowProcessReferences(wp, referenced.getId());
   }

   public static List getWorkflowProcessReferences(WorkflowProcess wp, String referencedId) {
      List references = new ArrayList();
      if (referencedId.equals("")) {
         return references;
      }

      references.addAll(tGetWorkflowProcessReferences(wp, referencedId));

      Iterator it = wp.getActivitySets().toElements().iterator();
      while (it.hasNext()) {
         ActivitySet as = (ActivitySet) it.next();
         references.addAll(tGetWorkflowProcessReferences(as, referencedId));
      }

      return references;
   }

   protected static List tGetWorkflowProcessReferences(XMLCollectionElement wpOrAs,
                                                       String referencedId) {
      List references = new ArrayList();
      if (referencedId.equals("")) {
         return references;
      }

      List types = new ArrayList();
      types.add(new Integer(XPDLConstants.ACTIVITY_TYPE_SUBFLOW));
      Iterator it = getActivities((Activities) wpOrAs.get("Activities"), types).iterator();
      while (it.hasNext()) {
         Activity act = (Activity) it.next();
         SubFlow s = act.getActivityTypes()
            .getImplementation()
            .getImplementationTypes()
            .getSubFlow();
         if (s.getId().equals(referencedId)) {
            references.add(s);
         }
      }

      return references;
   }

   public static List getReferences(WorkflowProcess wp, FormalParameter referenced) {
      List references = new ArrayList();
      if (!(referenced.getParent().getParent() instanceof WorkflowProcess)) {
         return references;
      }

      return getFormalParameterReferences(wp, referenced.getId());
   }

   public static List getFormalParameterReferences(WorkflowProcess wp, String referencedId) {
      List references = new ArrayList();
      if (referencedId.equals("")) {
         return references;
      }
      if (wp.getDataField(referencedId) != null) {
         return references;
      }

      references.addAll(getVariableReferences(wp, referencedId));

      Iterator it = wp.getActivitySets().toElements().iterator();
      while (it.hasNext()) {
         ActivitySet as = (ActivitySet) it.next();
         references.addAll(getVariableReferences(as, referencedId));
      }

      return references;
   }

   public static List getVariableReferences(XMLCollectionElement wpOrAs, String dfOrFpId) {
      List references = new ArrayList();
      if (dfOrFpId.equals("")) {
         return references;
      }

      Map allVars = XMLUtil.getWorkflowProcess(wpOrAs).getAllVariables();
      Iterator it = ((Activities) wpOrAs.get("Activities")).toElements().iterator();
      while (it.hasNext()) {
         Activity act = (Activity) it.next();
         int type = act.getActivityType();
         // actual parameter (can be expression containing variable, or direct variable
         // reference)
         List aps = new ArrayList();
         if (type == XPDLConstants.ACTIVITY_TYPE_SUBFLOW) {
            aps.addAll(act.getActivityTypes()
               .getImplementation()
               .getImplementationTypes()
               .getSubFlow()
               .getActualParameters()
               .toElements());
         } else if (type == XPDLConstants.ACTIVITY_TYPE_TASK_APPLICATION) {
            TaskApplication ta = act.getActivityTypes()
               .getImplementation()
               .getImplementationTypes()
               .getTask()
               .getTaskTypes()
               .getTaskApplication();
            aps.addAll(ta.getActualParameters().toElements());
         }
         Iterator itap = aps.iterator();
         while (itap.hasNext()) {
            ActualParameter ap = (ActualParameter) itap.next();
            if (XMLUtil.getUsingPositions(ap.toValue(), dfOrFpId, allVars).size() > 0) {
               references.add(ap);
            }
         }

         Iterator itdls = act.getDeadlines().toElements().iterator();
         while (itdls.hasNext()) {
            Deadline dl = (Deadline) itdls.next();
            String dcond = dl.getDeadlineDuration();
            if (XMLUtil.getUsingPositions(dcond, dfOrFpId, allVars).size() > 0) {
               references.add(dl.get("DeadlineCondition"));
            }
         }

         // performer (can be expression containing variable, or direct variable
         // reference)
         String perf = act.getFirstPerformer();
         if (XMLUtil.getUsingPositions(perf, dfOrFpId, allVars).size() > 0) {
            references.add(act.getFirstPerformerObj());
         }
      }

      // transition condition (can be expression containing variable, or direct variable
      // reference)
      it = ((Transitions) wpOrAs.get("Transitions")).toElements().iterator();
      while (it.hasNext()) {
         Transition t = (Transition) it.next();
         if (XMLUtil.getUsingPositions(t.getCondition().toValue(), dfOrFpId, allVars)
            .size() > 0) {
            references.add(t.getCondition());
         }
      }

      return references;
   }

   public static List getReferences(WorkflowProcess wp, ActivitySet referenced) {
      return getActivitySetReferences(wp, referenced.getId());
   }

   public static List getActivitySetReferences(WorkflowProcess wp, String referencedId) {
      List references = new ArrayList();
      if (referencedId.equals("")) {
         return references;
      }

      references.addAll(tGetActivitySetReferences(wp, referencedId));

      Iterator it = wp.getActivitySets().toElements().iterator();
      while (it.hasNext()) {
         ActivitySet as = (ActivitySet) it.next();
         references.addAll(tGetActivitySetReferences(as, referencedId));
      }

      return references;
   }

   public static List getReferences(ActivitySet as, ActivitySet referenced) {
      return tGetActivitySetReferences(as, referenced.getId());
   }

   public static List getReferences(ActivitySet as, String referencedId) {
      return tGetActivitySetReferences(as, referencedId);
   }

   protected static List tGetActivitySetReferences(XMLCollectionElement wpOrAs,
                                                   String referencedId) {
      List references = new ArrayList();
      if (referencedId.equals("")) {
         return references;
      }

      List types = new ArrayList();
      types.add(new Integer(XPDLConstants.ACTIVITY_TYPE_BLOCK));
      Iterator it = getActivities((Activities) wpOrAs.get("Activities"), types).iterator();
      while (it.hasNext()) {
         Activity act = (Activity) it.next();
         BlockActivity ba = act.getActivityTypes().getBlockActivity();
         if (ba.getActivitySetId().equals(referencedId)) {
            references.add(ba);
         }
      }

      return references;
   }

   public static List getReferences(Activity act) {
      return getActivityReferences((XMLCollectionElement) act.getParent().getParent(),
                                   act.getId());
   }

   public static List getActivityReferences(XMLCollectionElement wpOrAs,
                                            String referencedId) {
      List refs = new ArrayList();
      Transitions tras = ((Transitions) wpOrAs.get("Transitions"));
      Iterator it = getTransitions(tras, referencedId, true).iterator();
      while (it.hasNext()) {
         refs.add(((Transition) it.next()).get("To"));
      }
      it = getTransitions(tras, referencedId, false).iterator();
      while (it.hasNext()) {
         refs.add(((Transition) it.next()).get("From"));
      }
      Associations asocs = XMLUtil.getPackage(wpOrAs).getAssociations();
      it = getAssociations(asocs, referencedId, true).iterator();
      while (it.hasNext()) {
         refs.add(((Association) it.next()).get("Target"));
      }
      it = getAssociations(asocs, referencedId, false).iterator();
      while (it.hasNext()) {
         refs.add(((Association) it.next()).get("Source"));
      }
      return new ArrayList(refs);
   }

   public static List getReferences(Transition tra) {
      Activities acts = (Activities) ((XMLCollectionElement) tra.getParent().getParent()).get("Activities");
      Activity from = acts.getActivity(tra.getFrom());
      Activity to = acts.getActivity(tra.getTo());
      Set refs = new HashSet();
      if (from != null) {
         refs.add(from);
      }
      if (to != null && to != from) {
         refs.add(to);
      }
      return new ArrayList(refs);
   }

   public static boolean correctSplitsAndJoins(Package pkg) {
      boolean changed = false;

      Iterator it = pkg.getWorkflowProcesses().toElements().iterator();
      while (it.hasNext()) {
         WorkflowProcess wp = (WorkflowProcess) it.next();
         changed = correctSplitsAndJoins(wp) || changed;
      }
      return changed;
   }

   public static boolean correctSplitsAndJoins(WorkflowProcess wp) {
      boolean changed = correctSplitsAndJoins(wp.getActivities().toElements());
      Iterator it = wp.getActivitySets().toElements().iterator();
      while (it.hasNext()) {
         ActivitySet as = (ActivitySet) it.next();
         changed = correctSplitsAndJoins(as.getActivities().toElements()) || changed;
      }
      return changed;
   }

   public static boolean correctSplitsAndJoins(List acts) {
      boolean changed = false;
      Iterator it = acts.iterator();
      while (it.hasNext()) {
         Activity act = (Activity) it.next();
         changed = correctSplitAndJoin(act) || changed;
      }
      return changed;
   }

   public static boolean correctSplitAndJoin(Activity act) {
      Set ogt = XMLUtil.getOutgoingTransitions(act);
      Set inct = XMLUtil.getIncomingTransitions(act);
      TransitionRestrictions tres = act.getTransitionRestrictions();
      TransitionRestriction tr = null;
      boolean newTres = false;
      boolean changed = false;
      // LoggingManager lm=JaWEManager.getInstance().getLoggingManager();
      // lm.debug("Correcting split and join for activity "+act.getId()+",
      // ogts="+ogt.size()+", incts="+inct.size());
      if (tres.size() == 0) {
         if (ogt.size() > 1 || inct.size() > 1) {
            tr = (TransitionRestriction) tres.generateNewElement();
            newTres = true;
         } else {
            return false;
         }
      } else {
         tr = (TransitionRestriction) tres.get(0);
      }

      Split s = tr.getSplit();
      Join j = tr.getJoin();

      TransitionRefs trefs = s.getTransitionRefs();
      if (ogt.size() <= 1) {
         if (trefs.size() > 0) {
            trefs.clear();
            changed = true;
         }
         if (!s.getType().equals(XPDLConstants.JOIN_SPLIT_TYPE_NONE)) {
            s.setTypeNONE();
            changed = true;
         }
      }
      if (ogt.size() > 1) {

         if (s.getType().equals(XPDLConstants.JOIN_SPLIT_TYPE_NONE)) {
            if (act.getActivityType() != XPDLConstants.ACTIVITY_TYPE_ROUTE) {
               s.setTypeParallel();
            } else {
               s.set("Type", act.getActivityTypes().getRoute().getGatewayType());
            }
            // lm.debug("--------------------- st set to xor");
            changed = true;
         }

         List trefIds = new ArrayList();
         for (int i = 0; i < trefs.size(); i++) {
            TransitionRef tref = (TransitionRef) trefs.get(i);
            trefIds.add(tref.getId());
         }

         List transitionIds = new ArrayList();
         Iterator it = ogt.iterator();
         while (it.hasNext()) {
            Transition t = (Transition) it.next();
            transitionIds.add(t.getId());
         }

         List toRem = new ArrayList(trefIds);
         toRem.removeAll(transitionIds);
         List toAdd = new ArrayList(transitionIds);
         toAdd.removeAll(trefIds);

         for (int i = 0; i < toRem.size(); i++) {
            TransitionRef tref = trefs.getTransitionRef((String) toRem.get(i));
            trefs.remove(tref);
            changed = true;
         }
         for (int i = 0; i < toAdd.size(); i++) {
            TransitionRef tref = (TransitionRef) trefs.generateNewElement();
            tref.setId((String) toAdd.get(i));
            trefs.add(tref);
            changed = true;
         }
      }

      if (inct.size() <= 1) {
         if (!j.getType().equals(XPDLConstants.JOIN_SPLIT_TYPE_NONE)) {
            j.setTypeNONE();
            changed = true;
         }
      } else {
         if (j.getType().equals(XPDLConstants.JOIN_SPLIT_TYPE_NONE)) {
            if (act.getActivityType() != XPDLConstants.ACTIVITY_TYPE_ROUTE) {
               j.setTypeExclusive();
            } else {
               j.set("Type", act.getActivityTypes().getRoute().getGatewayType());
            }
            changed = true;
         }
      }
      // lm.debug("--------------------- st="+s.getType()+", jt="+j.getType()+",
      // trefss="+trefs.size());

      if (s.getType().equals(XPDLConstants.JOIN_SPLIT_TYPE_NONE)
          && j.getType().equals(XPDLConstants.JOIN_SPLIT_TYPE_NONE)) {
         if (!newTres) {
            tres.remove(tr);
            changed = true;
         }
      } else if (newTres) {
         tres.add(tr);
      }

      return changed;
   }

   public static void updateActivityReferences(List refsTrasAndAsocsToFromTargetSource,
                                               String oldActId,
                                               String newActId) {
      Iterator it = refsTrasAndAsocsToFromTargetSource.iterator();
      while (it.hasNext()) {
         ((XMLElement) it.next()).setValue(newActId);
      }
   }

   public static void updateArtifactReferences(List refsAsocsTargetSource,
                                               String oldArtId,
                                               String newArtId) {
      Iterator it = refsAsocsTargetSource.iterator();
      while (it.hasNext()) {
         ((XMLElement) it.next()).setValue(newArtId);
      }
   }

   public static void updateActivityOnTransitionIdChange(Activities acts,
                                                         String actFromId,
                                                         String oldTraId,
                                                         String newTraId) {
      Activity act = acts.getActivity(actFromId);
      updateActivityOnTransitionIdChange(act, oldTraId, newTraId);
   }

   public static void updateActivityOnTransitionIdChange(Activity act,
                                                         String oldTraId,
                                                         String newTraId) {
      if (act != null) {
         Split s = XMLUtil.getSplit(act);
         if (s != null) {
            TransitionRef tref = s.getTransitionRefs().getTransitionRef(oldTraId);
            if (tref != null) {
               tref.setId(newTraId);
            }
         }
      }
   }

   public static void updateActivitiesOnTransitionFromChange(Activities acts,
                                                             String traId,
                                                             String traOldFromId,
                                                             String traNewFromId) {
      if (traOldFromId != null) {
         Activity act = acts.getActivity(traOldFromId);
         if (act != null) {
            correctSplitAndJoin(act);
         }
      }
      if (traNewFromId != null) {
         Activity act = acts.getActivity(traNewFromId);
         if (act != null) {
            correctSplitAndJoin(act);
         }
      }
   }

   public static void updateActivitiesOnTransitionToChange(Activities acts,
                                                           String traId,
                                                           String traOldToId,
                                                           String traNewToId) {
      if (traOldToId != null) {
         Activity act = acts.getActivity(traOldToId);
         if (act != null) {
            correctSplitAndJoin(act);
         }
      }
      if (traNewToId != null) {
         Activity act = acts.getActivity(traNewToId);
         if (act != null) {
            correctSplitAndJoin(act);
         }
      }
   }

   public static void removeTransitionsForActivity(Activity act) {
      Set trasToRemove = getTransitionsForActivity(act);
      Transitions trs = (Transitions) ((XMLCollectionElement) act.getParent().getParent()).get("Transitions");
      Activities acs = (Activities) act.getParent();

      if (trasToRemove.size() > 0) {
         Iterator itt = trasToRemove.iterator();
         while (itt.hasNext()) {
            Transition t = (Transition) itt.next();
            Activity from = acs.getActivity(t.getFrom());
            Activity to = acs.getActivity(t.getTo());
            if (from != act && from != null) {
               correctSplitAndJoin(from);
            }
            if (to != act && to != null) {
               correctSplitAndJoin(to);
            }
         }
         trs.removeAll(new ArrayList(trasToRemove));
      }

   }

   public static void removeTransitionsForActivities(List acts) {
      if (acts.size() == 0)
         return;
      Activities acs = (Activities) ((Activity) acts.get(0)).getParent();
      Set trasToRemove = new HashSet();
      Iterator it = acts.iterator();
      while (it.hasNext()) {
         Activity act = (Activity) it.next();
         trasToRemove.addAll(getTransitionsForActivity(act));
      }
      if (trasToRemove.size() > 0) {
         Transitions trs = (Transitions) ((XMLCollectionElement) trasToRemove.toArray()[0]).getParent();
         trs.removeAll(new ArrayList(trasToRemove));
         Iterator itt = trasToRemove.iterator();
         while (itt.hasNext()) {
            Transition t = (Transition) itt.next();
            Activity from = acs.getActivity(t.getFrom());
            Activity to = acs.getActivity(t.getTo());
            if (from != null && !acts.contains(from)) {
               correctSplitAndJoin(from);
            }
            if (to != null && !acts.contains(to)) {
               correctSplitAndJoin(to);
            }
         }
      }
   }

   protected static Set getTransitionsForActivity(Activity act) {
      Set trasToRemove = XMLUtil.getIncomingTransitions(act);
      trasToRemove.addAll(XMLUtil.getOutgoingTransitions(act));
      return trasToRemove;
   }

   public static List getTransitions(Transitions tras, String actId, boolean isToAct) {
      List l = new ArrayList();
      Iterator it = tras.toElements().iterator();
      while (it.hasNext()) {
         Transition t = (Transition) it.next();
         if (isToAct) {
            if (t.getTo().equals(actId)) {
               l.add(t);
            }
         } else {
            if (t.getFrom().equals(actId)) {
               l.add(t);
            }
         }
      }
      return l;
   }

   public static void removeAssociationsForActivityOrArtifact(XMLCollectionElement a) {
      Set asocsToRemove = getAssociationsForActivityOrArtifact(a);
      Associations asocs = XMLUtil.getPackage(a).getAssociations();
      asocs.removeAll(new ArrayList(asocsToRemove));
   }

   public static void removeAssociationsForActivitiesOrArtifacts(List actsOrArts) {
      if (actsOrArts.size() == 0)
         return;
      Set asocsToRemove = new HashSet();
      Iterator it = actsOrArts.iterator();
      while (it.hasNext()) {
         XMLCollectionElement a = (XMLCollectionElement) it.next();
         asocsToRemove.addAll(getAssociationsForActivityOrArtifact(a));
      }
      Associations asocs = XMLUtil.getPackage((XMLElement) actsOrArts.toArray()[0])
         .getAssociations();
      asocs.removeAll(new ArrayList(asocsToRemove));
   }

   protected static Set getAssociationsForActivityOrArtifact(XMLCollectionElement a) {
      Set trasToRemove = XMLUtil.getIncomingAssociations(a);
      trasToRemove.addAll(XMLUtil.getOutgoingAssociations(a));
      return trasToRemove;
   }

   public static List getAssociations(Associations asocs, String aId, boolean isToA) {
      List l = new ArrayList();
      Iterator it = asocs.toElements().iterator();
      while (it.hasNext()) {
         Association t = (Association) it.next();
         if (isToA) {
            if (t.getTarget().equals(aId)) {
               l.add(t);
            }
         } else {
            if (t.getSource().equals(aId)) {
               l.add(t);
            }
         }
      }
      return l;
   }

   public static void removeNestedLanesForLanes(List lanes) {
      Iterator it = lanes.iterator();
      while (it.hasNext()) {
         Lane lane = (Lane) it.next();
         removeNestedLanesForLane(lane);
      }
   }

   public static void removeNestedLanesForLane(Lane lane) {
      Set nls = getNestedLanesForLane(lane);
      Iterator it = nls.iterator();
      while (it.hasNext()) {
         NestedLane nl = (NestedLane) it.next();
         ((NestedLanes) nl.getParent()).remove(nl);
      }
   }

   protected static Set getNestedLanesForLane(Lane lane) {
      Set nlsForLane = new HashSet();
      Pool p = XMLUtil.getPool(lane);
      Iterator it = p.getLanes().toElements().iterator();
      while (it.hasNext()) {
         Lane l = (Lane) it.next();
         if (l == lane)
            continue;
         Iterator it2 = l.getNestedLanes().toElements().iterator();
         while (it2.hasNext()) {
            NestedLane nl = (NestedLane) it2.next();
            if (nl.getLaneId().equals(lane.getId())) {
               nlsForLane.add(nl);
            }
         }
      }
      return nlsForLane;
   }

   public static void createPoolsForProcesses(List wps) {
      Iterator it = wps.iterator();
      while (it.hasNext()) {
         WorkflowProcess wp = (WorkflowProcess) it.next();
         if (getPoolForProcess(wp) == null) {
            createPoolForProcess(wp);
         }
      }
   }

   public static Pool createPoolForProcess(WorkflowProcess wp) {
      Pools ps = XMLUtil.getPackage(wp).getPools();
      Pool p = (Pool) ps.generateNewElement();
      p.setId(XMLUtil.generateUniqueId(ps, new HashSet()));
      p.setProcess(wp.getId());
      p.setMainPool(true);
      p.setName(wp.getName().equals("") ? wp.getId() : wp.getName());
      ps.add(p);
      return p;
   }

   public static void removePoolsForProcesses(List wps) {
      Iterator it = wps.iterator();
      while (it.hasNext()) {
         WorkflowProcess wp = (WorkflowProcess) it.next();
         removePoolForProcess(wp);
      }
   }

   public static Pool removePoolForProcess(WorkflowProcess wp) {
      Pool p = getPoolForProcess(wp);
      ((Pools) p.getParent()).remove(p);
      return p;
   }

   public static Pool getPoolForProcess(WorkflowProcess wp) {
      Iterator it = XMLUtil.getPackage(wp).getPools().toElements().iterator();
      while (it.hasNext()) {
         Pool p = (Pool) it.next();
         if (p.getProcess().equals(wp.getId())) {
            return p;
         }
      }
      return null;
   }

   public static WorkflowProcess getProcessForPool(Pool pool) {
      return XMLUtil.getPackage(pool).getWorkflowProcess(pool.getProcess());
   }

   public static void createPoolsForActivitySets(List ass) {
      Iterator it = ass.iterator();
      while (it.hasNext()) {
         ActivitySet as = (ActivitySet) it.next();
         if (getPoolForActivitySet(as) == null) {
            createPoolForActivitySet(as);
         }
      }
   }

   public static Pool createPoolForActivitySet(ActivitySet as) {
      Pools ps = XMLUtil.getPackage(as).getPools();
      Pool p = (Pool) ps.generateNewElement();
      p.setId(XMLUtil.generateUniqueId(ps, new HashSet()));
      p.setProcess(as.getId());
      p.setMainPool(true);
      p.setName(as.getName().equals("") ? as.getId() : as.getName());
      ps.add(p);
      return p;
   }

   public static void removePoolsForActivitySets(List ass) {
      Iterator it = ass.iterator();
      while (it.hasNext()) {
         ActivitySet as = (ActivitySet) it.next();
         removePoolForActivitySet(as);
      }
   }

   public static Pool removePoolForActivitySet(ActivitySet as) {
      Pool p = getPoolForActivitySet(as);
      ((Pools) p.getParent()).remove(p);
      return p;
   }

   public static Pool getPoolForActivitySet(ActivitySet as) {
      Iterator it = XMLUtil.getPackage(as).getPools().toElements().iterator();
      while (it.hasNext()) {
         Pool p = (Pool) it.next();
         if (p.getProcess().equals(as.getId())) {
            return p;
         }
      }
      return null;
   }

   public static ActivitySet getActivitySetForPool(Pool pool) {
      return XMLUtil.getPackage(pool).getActivitySet(pool.getProcess());
   }

   public static Pool getPoolForProcessOrActivitySet(XMLCollectionElement wpOrAs) {
      if (wpOrAs instanceof WorkflowProcess) {
         return getPoolForProcess((WorkflowProcess) wpOrAs);
      }
      return getPoolForActivitySet((ActivitySet) wpOrAs);
   }

   public static void removeArtifactAndAssociationsForProcessesOrActivitySets(List wpsOrAss) {
      Iterator it = wpsOrAss.iterator();
      while (it.hasNext()) {
         XMLCollectionElement wpOrAs = (XMLCollectionElement) it.next();
         removeArtifactAndAssociationsForProcessOrActivitySet(wpOrAs);
      }
   }

   public static List removeArtifactAndAssociationsForProcessOrActivitySet(XMLCollectionElement wpOrAs) {
      List artsAndAss = getAllArtifactsAndAssociationsForWorkflowProcessOrActivitySet(wpOrAs);
      Iterator it = artsAndAss.iterator();
      while (it.hasNext()) {
         XMLElement el = (XMLElement) it.next();
         ((XMLCollection) el.getParent()).remove(el);
      }
      return artsAndAss;
   }

   public static void updateTypeDeclarationReferences(List refDeclaredTypes,
                                                      String newTdId) {
      Iterator it = refDeclaredTypes.iterator();
      while (it.hasNext()) {
         DeclaredType dt = (DeclaredType) it.next();
         dt.setId(newTdId);
      }
   }

   public static void updateApplicationReferences(List refTools, String newAppId) {
      Iterator it = refTools.iterator();
      while (it.hasNext()) {
         TaskApplication t = (TaskApplication) it.next();
         t.setId(newAppId);
      }
   }

   public static void updateLaneReferences(List refNGIsAndNestedLanes, String newLaneId) {
      Iterator it = refNGIsAndNestedLanes.iterator();
      while (it.hasNext()) {
         XMLComplexElement NGIOrNestedLane = (XMLComplexElement) it.next();
         NGIOrNestedLane.set("LaneId", newLaneId);
      }
   }

   public static void updateParticipantReferences(List refPerfsAndResps, String newParId) {
      Iterator it = refPerfsAndResps.iterator();
      while (it.hasNext()) {
         XMLElement pOrR = (XMLElement) it.next();
         pOrR.setValue(newParId);
      }
   }

   public static void updateWorkflowProcessReferences(List refSbflwsOrPools,
                                                      String newWpId) {
      Iterator it = refSbflwsOrPools.iterator();
      while (it.hasNext()) {
         Object sorp = it.next();
         if (sorp instanceof SubFlow) {
            ((SubFlow) sorp).setId(newWpId);
         } else {
            ((Pool) sorp).setProcess(newWpId);
         }
      }
   }

   public static void updateActivitySetReferences(List refBlocks, String newAsId) {
      Iterator it = refBlocks.iterator();
      while (it.hasNext()) {
         BlockActivity ba = (BlockActivity) it.next();
         ba.setActivitySetId(newAsId);
      }
   }

   public static void updateVariableReferences(List refAPsOrPerfsOrCondsOrDlConds,
                                               String oldDfOrFpId,
                                               String newDfOrFpId) {
      Iterator it = refAPsOrPerfsOrCondsOrDlConds.iterator();
      int varLengthDiff = newDfOrFpId.length() - oldDfOrFpId.length();
      while (it.hasNext()) {
         XMLElement apOrPerfOrCondOrDlCond = (XMLElement) it.next();
         String expr = apOrPerfOrCondOrDlCond.toValue();
         List positions = XMLUtil.getUsingPositions(expr,
                                                    oldDfOrFpId,
                                                    XMLUtil.getWorkflowProcess(apOrPerfOrCondOrDlCond)
                                                       .getAllVariables());
         for (int i = 0; i < positions.size(); i++) {
            int pos = ((Integer) positions.get(i)).intValue();
            int realPos = pos + varLengthDiff * i;
            String pref = expr.substring(0, realPos);
            String suff = expr.substring(realPos + oldDfOrFpId.length());
            expr = pref + newDfOrFpId + suff;
            // System.out.println("Pref="+pref+", suff="+suff+", expr="+expr);
         }
         apOrPerfOrCondOrDlCond.setValue(expr);
      }
   }

   public static List getActivities(Package pkg, List types) {
      List l = new ArrayList();
      Iterator it = pkg.getWorkflowProcesses().toElements().iterator();
      while (it.hasNext()) {
         WorkflowProcess wp = (WorkflowProcess) it.next();
         l.addAll(getActivities(wp, types));
      }
      return l;
   }

   public static List getActivities(WorkflowProcess wp, List types) {
      List l = new ArrayList();
      l.addAll(getActivities(wp.getActivities(), types));
      Iterator it = wp.getActivitySets().toElements().iterator();
      while (it.hasNext()) {
         ActivitySet as = (ActivitySet) it.next();
         l.addAll(getActivities(as.getActivities(), types));
      }
      return l;
   }

   public static List getActivities(Activities acts, List types) {
      List l = new ArrayList();

      Iterator it = acts.toElements().iterator();
      while (it.hasNext()) {
         Activity act = (Activity) it.next();
         int type = act.getActivityType();
         if (types.contains(new Integer(type))) {
            l.add(act);
         }
      }

      return l;
   }

   public static SequencedHashMap getPossibleResponsibles(Responsibles resp,
                                                          Responsible rsp,
                                                          XMLInterface xmli) {
      SequencedHashMap choices = null;
      if (XMLUtil.getWorkflowProcess(resp) != null) {
         choices = XMLUtil.getPossibleParticipants(XMLUtil.getWorkflowProcess(resp), xmli);
      } else {
         choices = XMLUtil.getPossibleParticipants(XMLUtil.getPackage(resp), xmli);
      }
      // filter choices: exclude already existing performers
      Iterator it = resp.toElements().iterator();
      while (it.hasNext()) {
         Responsible r = (Responsible) it.next();
         if (r != rsp) {
            choices.remove(r.toValue());
         }
      }
      return choices;
   }

   public static boolean doesCrossreferenceExist(Package pkg, XMLInterface xmli) {
      boolean crossRefs = false;

      Iterator epids = pkg.getExternalPackageIds().iterator();
      while (epids.hasNext()) {
         try {
            Package extP = xmli.getPackageById((String) epids.next());
            if (XMLUtil.getAllExternalPackageIds(xmli, extP, new HashSet())
               .contains(pkg.getId())) {
               crossRefs = true;
               break;
            }
         } catch (Exception ex) {
         }
      }
      return crossRefs;
   }

   public static Set getAllExtendedAttributeNames(XMLComplexElement cel,
                                                  XMLInterface xpdlh) {
      Set extAttribNames = new HashSet();

      Iterator it = xpdlh.getAllPackages().iterator();
      while (it.hasNext()) {
         extAttribNames.addAll(getAllExtendedAttributeNames((Package) it.next(), cel));
      }
      return extAttribNames;
   }

   public static Set getAllExtendedAttributeNames(Package pkg, XMLComplexElement cel) {
      Set extAttribNames = new HashSet();
      if (cel instanceof Activity) {
         Iterator it = pkg.getWorkflowProcesses().toElements().iterator();
         while (it.hasNext()) {
            WorkflowProcess wp = (WorkflowProcess) it.next();
            extAttribNames.addAll(getAllExtendedAttributeNamesForElements(wp.getActivities()
               .toElements()));

            Iterator asets = wp.getActivitySets().toElements().iterator();
            while (asets.hasNext()) {
               ActivitySet as = (ActivitySet) asets.next();
               extAttribNames.addAll(getAllExtendedAttributeNamesForElements(as.getActivities()
                  .toElements()));
            }
         }
      } else if (cel instanceof Application) {
         extAttribNames.addAll(getAllExtendedAttributeNamesForElements(pkg.getApplications()
            .toElements()));
         Iterator it = pkg.getWorkflowProcesses().toElements().iterator();
         while (it.hasNext()) {
            WorkflowProcess wp = (WorkflowProcess) it.next();
            extAttribNames.addAll(getAllExtendedAttributeNamesForElements(wp.getApplications()
               .toElements()));
         }
      } else if (cel instanceof DataField) {
         extAttribNames.addAll(getAllExtendedAttributeNamesForElements(pkg.getDataFields()
            .toElements()));
         Iterator it = pkg.getWorkflowProcesses().toElements().iterator();
         while (it.hasNext()) {
            WorkflowProcess wp = (WorkflowProcess) it.next();
            extAttribNames.addAll(getAllExtendedAttributeNamesForElements(wp.getDataFields()
               .toElements()));
         }
      } else if (cel instanceof ExternalPackage) {
         extAttribNames.addAll(getAllExtendedAttributeNamesForElements(pkg.getExternalPackages()
            .toElements()));
      } else if (cel instanceof Package) {
         extAttribNames.addAll(getAllExtendedAttributeNames(pkg.getExtendedAttributes()
            .toElements()));
      } else if (cel instanceof Participant) {
         extAttribNames.addAll(getAllExtendedAttributeNamesForElements(pkg.getParticipants()
            .toElements()));
         Iterator it = pkg.getWorkflowProcesses().toElements().iterator();
         while (it.hasNext()) {
            WorkflowProcess wp = (WorkflowProcess) it.next();
            extAttribNames.addAll(getAllExtendedAttributeNamesForElements(wp.getParticipants()
               .toElements()));
         }
      } else if (cel instanceof Transition) {
         Iterator it = pkg.getWorkflowProcesses().toElements().iterator();
         while (it.hasNext()) {
            WorkflowProcess wp = (WorkflowProcess) it.next();
            extAttribNames.addAll(getAllExtendedAttributeNamesForElements(wp.getTransitions()
               .toElements()));
            Iterator asets = wp.getActivitySets().toElements().iterator();
            while (asets.hasNext()) {
               ActivitySet as = (ActivitySet) asets.next();
               extAttribNames.addAll(getAllExtendedAttributeNamesForElements(as.getTransitions()
                  .toElements()));
            }
         }
      } else if (cel instanceof TypeDeclaration) {
         extAttribNames.addAll(getAllExtendedAttributeNamesForElements(pkg.getTypeDeclarations()
            .toElements()));
      } else if (cel instanceof WorkflowProcess) {
         extAttribNames.addAll(getAllExtendedAttributeNamesForElements(pkg.getWorkflowProcesses()
            .toElements()));
      }
      return extAttribNames;
   }

   public static Set getAllExtendedAttributeNamesForElements(Collection elements) {
      Set s = new HashSet();
      Iterator it = elements.iterator();
      while (it.hasNext()) {
         XMLComplexElement cel = (XMLComplexElement) it.next();
         s.addAll(((ExtendedAttributes) cel.get("ExtendedAttributes")).toElements());
      }
      return getAllExtendedAttributeNames(s);
   }

   public static Set getAllExtendedAttributeNames(Collection extAttribs) {
      Set eaNames = new HashSet();
      Iterator it = extAttribs.iterator();
      while (it.hasNext()) {
         ExtendedAttribute ea = (ExtendedAttribute) it.next();
         String eaName = ea.getName();

         eaNames.add(eaName);
      }
      return eaNames;
   }

}
