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
import java.util.Collection;
import java.util.Iterator;

import org.enhydra.jxpdl.XMLAttribute;
import org.enhydra.jxpdl.XMLComplexElement;
import org.enhydra.jxpdl.XMLElement;
import org.enhydra.jxpdl.XMLElementChangeInfo;
import org.enhydra.jxpdl.utilities.SequencedHashMap;

/**
 * Represents corresponding (main) element of XPDL model from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class Package extends XMLComplexElement {

   /** Instance holding information about the namespaces. */
   protected Namespaces namespaces;

   /** Internal package version. */
   protected String internalVersion;

   /** Flag that indicates if Package is transient. */
   protected boolean isTransient;

   /**
    * Map holding information about the mapping between references to the external
    * packages and their Ids.
    */
   protected SequencedHashMap extPkgRefsToIds = new SequencedHashMap();

   // transient static int pkgno=0;
   // transient int pno;
   // transient static ArrayList createdPKGs=new ArrayList();
   // transient static ArrayList destroyedPKGs=new ArrayList();

   /**
    * Constructs a new object representing XPDL model.
    */
   public Package() {
      super(null, true);
      // pno=++pkgno;
      // System.err.println("PKG "+(pno)+" created, hc="+hashCode());
      // createdPKGs.add(new Integer(pno));
      namespaces = new Namespaces(this);
      internalVersion = "-1";
      isTransient = false;
   }

   public void makeAs(XMLElement el) {
      super.makeAs(el);
      this.namespaces.makeAs(((Package) el).namespaces);
      extPkgRefsToIds = new SequencedHashMap(((Package) el).extPkgRefsToIds);
      this.isTransient = ((Package) el).isTransient;
   }

   protected void fillStructure() {
      XMLAttribute attrId = new XMLAttribute(this, "Id", true); // required
      XMLAttribute attrName = new XMLAttribute(this, "Name", false);
      PackageHeader refPackageHeader = new PackageHeader(this);
      RedefinableHeader refRedefinableHeader = new RedefinableHeader(this); // min=0
      ConformanceClass refConformanceClass = new ConformanceClass(this); // min=0
      Script refScript = new Script(this); // min=0
      ExternalPackages refExternalPackages = new ExternalPackages(this); // min=0
      TypeDeclarations refTypeDeclarations = new TypeDeclarations(this); // min=0
      Participants refParticipants = new Participants(this);
      Applications refApplications = new Applications(this); // min=0
      DataFields refDataFields = new DataFields(this); // min=0
      Pools refPools = new Pools(this); // min=0
      Associations refAssociations = new Associations(this); // min=0
      Artifacts refArtifacts = new Artifacts(this); // min=0
      WorkflowProcesses refWorkflowProcesses = new WorkflowProcesses(this); // min=0
      ExtendedAttributes refExtendedAttributes = new ExtendedAttributes(this);

      add(attrId);
      add(attrName);
      add(refPackageHeader);
      add(refRedefinableHeader);
      add(refConformanceClass);
      add(refScript);
      add(refExternalPackages);
      add(refTypeDeclarations);
      add(refParticipants);
      add(refApplications);
      add(refDataFields);
      add(refPools);
      add(refAssociations);
      add(refArtifacts);
      add(refWorkflowProcesses);
      add(refExtendedAttributes);
   }

   /** Returns true if Package is transient. */
   public boolean isTransient() {
      return isTransient;
   }

   /** Sets flag that indicates if Package is transient or not. */
   public void setTransient(boolean trans) {
      this.isTransient = trans;
   }

   /** Returns Package's internal version. */
   public String getInternalVersion() {
      return internalVersion;
   }

   /** Sets Package's internal version. */
   public void setInternalVersion(String internalVersion) {
      this.internalVersion = internalVersion;
   }

   /** Adds new mapping for an external package (reference to Id). */
   public void addExternalPackageMapping(String epRef, String epId) {
      extPkgRefsToIds.put(epRef, epId);
   }

   /** Removes an existing mapping for an external package by providing its reference. */
   public void removeExternalPackageMapping(String epRef) {
      extPkgRefsToIds.remove(epRef);
   }

   /** Gets an external package Id based on its reference. */
   public String getExternalPackageId(String epRef) {
      return (String) extPkgRefsToIds.get(epRef);
   }

   /** Returns collection of external package Ids. */
   public Collection getExternalPackageIds() {
      return new ArrayList(extPkgRefsToIds.values());
   }

   /**
    * Returns ExternalPackage object (the member of Package's ExternalPackages collection)
    * with specified Id.
    */
   public ExternalPackage getExternalPackage(String id) {
      ExternalPackage toRet = null;
      Iterator it = getExternalPackages().toElements().iterator();
      while (it.hasNext()) {
         ExternalPackage ep = (ExternalPackage) it.next();
         String epId = ep.getId();
         if (epId.equals("")) {
            String href = ep.getHref();
            epId = getExternalPackageId(href);
         }
         if (epId != null && epId.equals(id)) {
            toRet = ep;
            break;
         }
      }
      return toRet;
   }

   /**
    * Returns Artifact object (the member of Package's Artifacts collection) with
    * specified Id.
    */
   public Artifact getArtifact(String Id) {
      return getArtifacts().getArtifact(Id);
   }

   /**
    * Returns Association object (the member of Package's Associations collection) with
    * specified Id.
    */
   public Association getAssociation(String Id) {
      return getAssociations().getAssociation(Id);
   }

   /**
    * Returns Pool object (the member of Package's Pools collection) with
    * specified Id.
    */
   public Pool getPool(String Id) {
      return getPools().getPool(Id);
   }

   /**
    * Returns WorkflowProcess object (the member of Package's WorkflowProcesses collection) with
    * specified Id.
    */
   public WorkflowProcess getWorkflowProcess(String Id) {
      return getWorkflowProcesses().getWorkflowProcess(Id);
   }

   /**
    * Returns ActivitySet object (the member of Package's WorkflowProcess's ActivitySets collection) with
    * specified Id.
    */
   public ActivitySet getActivitySet(String Id) {
      Iterator it = getWorkflowProcesses().toElements().iterator();
      while (it.hasNext()) {
         WorkflowProcess wp = (WorkflowProcess) it.next();
         ActivitySet as = wp.getActivitySet(Id);
         if (as != null) {
            return as;
         }
      }
      return null;
   }

   /**
    * Returns Activity object (the member of Package's WorkflowProcess's or their ActivitySet' Activities collection) with
    * specified Id.
    */
   public Activity getActivity(String Id) {
      Iterator it = getWorkflowProcesses().toElements().iterator();
      while (it.hasNext()) {
         WorkflowProcess wp = (WorkflowProcess) it.next();
         Activity act = wp.getActivity(Id);
         if (act != null) {
            return act;
         }
      }
      return null;
   }

   /**
    * Returns Application object (the member of Package's Applications collection) with
    * specified Id.
    */
   public Application getApplication(String Id) {
      return getApplications().getApplication(Id);
   }

   /**
    * Returns Application object (the member of Package's Participants collection) with
    * specified Id.
    */
   public Participant getParticipant(String Id) {
      return getParticipants().getParticipant(Id);
   }

   /**
    * Returns DataField object (the member of Package's DataFields collection) with
    * specified Id.
    */
   public DataField getDataField(String Id) {
      return getDataFields().getDataField(Id);
   }

   /**
    * Returns TypeDeclaration object (the member of Package's TypeDeclaration's collection) with
    * specified Id.
    */
   public TypeDeclaration getTypeDeclaration(String Id) {
      return getTypeDeclarations().getTypeDeclaration(Id);
   }

   /** Returns the Id attribute value of this object. */
   public String getId() {
      return get("Id").toValue();
   }

   /** Sets the Id attribute value of this object. */
   public void setId(String id) {
      set("Id", id);
   }

   /** Returns the Name attribute value of this object. */
   public String getName() {
      return get("Name").toValue();
   }

   /** Sets the Name attribute value of this object. */
   public void setName(String name) {
      set("Name", name);
   }

   /** Returns the Applications sub-element of this object. */
   public Applications getApplications() {
      return (Applications) get("Applications");
   }

   /** Returns the ConformanceClass sub-element of this object. */
   public ConformanceClass getConformanceClass() {
      return (ConformanceClass) get("ConformanceClass");
   }

   /** Returns the DataFields sub-element of this object. */
   public DataFields getDataFields() {
      return (DataFields) get("DataFields");
   }

   /** Returns the ExtendedAttributes sub-element of this object. */
   public ExtendedAttributes getExtendedAttributes() {
      return (ExtendedAttributes) get("ExtendedAttributes");
   }

   /** Returns the ExternalPackages sub-element of this object. */
   public ExternalPackages getExternalPackages() {
      return (ExternalPackages) get("ExternalPackages");
   }

   /** Returns the PackageHeader sub-element of this object. */
   public PackageHeader getPackageHeader() {
      return (PackageHeader) get("PackageHeader");
   }

   /** Returns the Participants sub-element of this object. */
   public Participants getParticipants() {
      return (Participants) get("Participants");
   }

   /** Returns the RedefinableHeader sub-element of this object. */
   public RedefinableHeader getRedefinableHeader() {
      return (RedefinableHeader) get("RedefinableHeader");
   }

   /** Returns the Script sub-element of this object. */
   public Script getScript() {
      return (Script) get("Script");
   }

   /** Returns the TypeDeclarations sub-element of this object. */
   public TypeDeclarations getTypeDeclarations() {
      return (TypeDeclarations) get("TypeDeclarations");
   }

   /** Returns the Associations sub-element of this object. */
   public Associations getAssociations() {
      return (Associations) get("Associations");
   }

   /** Returns the Artifacts sub-element of this object. */
   public Artifacts getArtifacts() {
      return (Artifacts) get("Artifacts");
   }

   /** Returns the WorkflowProcesses sub-element of this object. */
   public WorkflowProcesses getWorkflowProcesses() {
      return (WorkflowProcesses) get("WorkflowProcesses");
   }

   /** Returns the Pools sub-element of this object. */
   public Pools getPools() {
      return (Pools) get("Pools");
   }

   /** Returns the Namespaces sub-element of this object. */
   public Namespaces getNamespaces() {
      return namespaces;
   }

   public void setNotifyMainListeners(boolean notify) {
      super.setNotifyMainListeners(notify);
      namespaces.setNotifyMainListeners(notify);
   }

   public void removeXPDL1Support() {
      super.removeXPDL1Support();
      namespaces.removeXPDL1Support();
   }

   public void setReadOnly(boolean ro) {
      super.setReadOnly(ro);
      namespaces.setReadOnly(ro);
   }

   public Object clone() {
      // System.out.println("Cloning package "+this.hashCode()+", listeners="+listeners);
      Package d = (Package) super.clone();
      // System.out.println("cloned pkg="+d.hashCode()+", list="+listeners);
      d.namespaces = (Namespaces) this.namespaces.clone();
      d.namespaces.setParent(d);
      d.extPkgRefsToIds = new SequencedHashMap(extPkgRefsToIds);
      d.isTransient = isTransient;
      d.clearCaches();
      return d;
   }

   public boolean equals(Object e) {
      boolean equals = super.equals(e);
      if (equals) {
         Package el = (Package) e;
         equals = (this.namespaces.equals(el.namespaces) && this.internalVersion.equals(el.internalVersion));
         // System.out.println("Package's ns or int ver equal - "+equals);
      }
      return equals;
   }

   /** Returns true. */
   protected boolean isMainElement() {
      return true;
   }

   protected void notifyMainListeners(XMLElementChangeInfo info) {
      if (parent == null)
         return;
      super.notifyMainListeners(info);
   }

   // public void finalize () throws Throwable {
   // super.finalize();
   // destroyedPKGs.add(new Integer(pno));
   // System.err.println("PKG "+(pno)+" destroyed, hc="+hashCode());
   // }

//   public static void dbg() {
//      System.gc();
      // System.err.println("Created pkgs: "+createdPKGs);
      // System.err.println("Destroyed pkgs: "+destroyedPKGs);
      // ArrayList inMem=new ArrayList(createdPKGs);
      // inMem.removeAll(destroyedPKGs);
      // System.err.println("Packages in memory: "+inMem);
//   }
}
