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
import java.util.Collection;
import java.util.Iterator;

import org.enhydra.jxpdl.XMLAttribute;
import org.enhydra.jxpdl.XMLComplexElement;
import org.enhydra.jxpdl.XMLElement;
import org.enhydra.jxpdl.XMLElementChangeInfo;
import org.enhydra.jxpdl.utilities.SequencedHashMap;

/**
 * Represents corresponding element from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class Package extends XMLComplexElement {

   protected Namespaces namespaces;

   protected String internalVersion;

   protected boolean isTransient;

   protected SequencedHashMap extPkgRefsToIds = new SequencedHashMap();

   // transient static int pkgno=0;
   // transient int pno;
   // transient static ArrayList createdPKGs=new ArrayList();
   // transient static ArrayList destroyedPKGs=new ArrayList();

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

   public boolean isTransient() {
      return isTransient;
   }

   public void setTransient(boolean trans) {
      this.isTransient = trans;
   }

   public String getInternalVersion() {
      return internalVersion;
   }

   public void setInternalVersion(String internalVersion) {
      this.internalVersion = internalVersion;
   }

   public void addExternalPackageMapping(String epRef, String epId) {
      extPkgRefsToIds.put(epRef, epId);
   }

   public void removeExternalPackageMapping(String epRef) {
      extPkgRefsToIds.remove(epRef);
   }

   public String getExternalPackageId(String epRef) {
      return (String) extPkgRefsToIds.get(epRef);
   }

   public Collection getExternalPackageIds() {
      return new ArrayList(extPkgRefsToIds.values());
   }

   public ExternalPackage getExternalPackage(String id) {
      ExternalPackage toRet = null;
      Iterator it = getExternalPackages().toElements().iterator();
      while (it.hasNext()) {
         ExternalPackage ep = (ExternalPackage) it.next();
         String href = ep.getHref();
         String epId = getExternalPackageId(href);
         if (epId != null && epId.equals(id)) {
            toRet = ep;
            break;
         }
      }
      return toRet;
   }

   public Artifact getArtifact(String Id) {
      return getArtifacts().getArtifact(Id);
   }

   public Association getAssociation(String Id) {
      return getAssociations().getAssociation(Id);
   }

   public Pool getPool(String Id) {
      return getPools().getPool(Id);
   }

   public WorkflowProcess getWorkflowProcess(String Id) {
      return getWorkflowProcesses().getWorkflowProcess(Id);
   }

   public ActivitySet getActivitySet(String Id) {
      Iterator it = getWorkflowProcesses().toElements().iterator();
      while (it.hasNext()) {
         WorkflowProcess wp = (WorkflowProcess)it.next();
         ActivitySet as = wp.getActivitySet(Id);
         if (as!=null) {
            return as;
         }
      }
      return null;
   }

   public Activity getActivity(String Id) {
      Iterator it = getWorkflowProcesses().toElements().iterator();
      while (it.hasNext()) {
         WorkflowProcess wp = (WorkflowProcess)it.next();
         Activity act = wp.getActivity(Id);
         if (act!=null) {
            return act;
         }
      }
      return null;
   }

   public Application getApplication(String Id) {
      return getApplications().getApplication(Id);
   }

   public Participant getParticipant(String Id) {
      return getParticipants().getParticipant(Id);
   }

   public DataField getDataField(String Id) {
      return getDataFields().getDataField(Id);
   }

   public TypeDeclaration getTypeDeclaration(String Id) {
      return getTypeDeclarations().getTypeDeclaration(Id);
   }

   public String getId() {
      return get("Id").toValue();
   }

   public void setId(String id) {
      set("Id", id);
   }

   public String getName() {
      return get("Name").toValue();
   }

   public void setName(String name) {
      set("Name", name);
   }

   public Applications getApplications() {
      return (Applications) get("Applications");
   }

   public ConformanceClass getConformanceClass() {
      return (ConformanceClass) get("ConformanceClass");
   }

   public DataFields getDataFields() {
      return (DataFields) get("DataFields");
   }

   public ExtendedAttributes getExtendedAttributes() {
      return (ExtendedAttributes) get("ExtendedAttributes");
   }

   public ExternalPackages getExternalPackages() {
      return (ExternalPackages) get("ExternalPackages");
   }

   public PackageHeader getPackageHeader() {
      return (PackageHeader) get("PackageHeader");
   }

   public Participants getParticipants() {
      return (Participants) get("Participants");
   }

   public RedefinableHeader getRedefinableHeader() {
      return (RedefinableHeader) get("RedefinableHeader");
   }

   public Script getScript() {
      return (Script) get("Script");
   }

   public TypeDeclarations getTypeDeclarations() {
      return (TypeDeclarations) get("TypeDeclarations");
   }

   public Associations getAssociations() {
      return (Associations) get("Associations");
   }

   public Artifacts getArtifacts() {
		return (Artifacts) get("Artifacts");
	}
	
   public WorkflowProcesses getWorkflowProcesses() {
      return (WorkflowProcesses) get("WorkflowProcesses");
   }

   public Pools getPools() {
      return (Pools) get("Pools");
   }

   public Namespaces getNamespaces() {
      return namespaces;
   }

   public void setNotifyMainListeners(boolean notify) {
      super.setNotifyMainListeners(notify);
      namespaces.setNotifyMainListeners(notify);
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

   public static void dbg() {
      System.gc();
      // System.err.println("Created pkgs: "+createdPKGs);
      // System.err.println("Destroyed pkgs: "+destroyedPKGs);
      // ArrayList inMem=new ArrayList(createdPKGs);
      // inMem.removeAll(destroyedPKGs);
      // System.err.println("Packages in memory: "+inMem);
   }
}
