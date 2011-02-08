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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Base class for representing elements from XML schema.
 *
 * @author Sasa Bojanic
 */
public abstract class XMLElement implements Serializable, Cloneable {

   protected transient List listeners=new ArrayList();
   
   protected transient boolean notifyMainListeners=false;
   protected transient boolean notifyListeners=false;
   
   protected Integer originalElementHashCode;
   
   /**
    * Equivalent for XML element name. Used when writting instance of this class to XML file.
    */
   private String name;

   /**
    * Indicates if element is required - corresponds to the same XML element definition.
    */
   private boolean isRequired = false;

   /**
    * Supposed to contain the value for XML element. This is true for simple elements and
    * attributes, more complex elements uses it as they need.
    */
   protected String value;

   /**
    * Indicates if an element is read only.
    */
   protected boolean isReadOnly = false;

   /**
    * Reference to parent object in DOM tree.
    */
   protected XMLElement parent;

   protected boolean xpdl1support = false;
   
   /**
    * Creates a new instance of element: sets <code>name</code> to name of concrete class
    * implementation of this abstract class, and <code>parent</code> and <code>isRequired</code>
    * properties to the specified ones.
    * <p>
    * It also sets the value of this element to an empty String.
    */
   public XMLElement(XMLElement parent, boolean isRequired) {
      this.parent = parent;
      this.isRequired = isRequired;
      this.name = getClass().getName();
      this.name = XMLUtil.getShortClassName(name);
      this.value = new String();
      originalElementHashCode=new Integer(this.hashCode());
   }

   public XMLElement(XMLElement parent, boolean isRequired, boolean xpdl1support) {
      this(parent,isRequired);
      this.xpdl1support = xpdl1support;
   }

   /**
    * Creates a new instance of element: sets <code>name</code>,
    * <code>parent</code> <code>isRequired</code> properties to specified ones.
    * <p>
    * It also sets the value of this element to an empty String.
    */
   public XMLElement(XMLElement parent, String name, boolean isRequired) {
      this.parent = parent;
      this.name = name;
      this.isRequired = isRequired;
      this.value = new String();
      originalElementHashCode=new Integer(this.hashCode());
   }

   public boolean isXPDL1Support () {
      return xpdl1support;
   }
   
   public void removeXPDL1Support () {
      xpdl1support = false;
   }
      
   public void makeAs (XMLElement el) {
      if (!(el != null && el.getClass().equals(this.getClass()) && el.name.equals(this.name))) {         
         throw new RuntimeException("Can't perform makeAs! Element["+el+","+el.getClass()+"|"+this.getClass()+","+el.name+"|"+this.name+"]");
      }          
      setValue(el.value);
   }
   
   /**
    * Sets 'read only' property of element to specified value. This enables/disables editing of the
    * element value for the simple elements and attributes, or changes to attributes and elements of
    * complex objects and collections.
    * <p>
    * If element is read only, and one wants to change its property, the RuntimeException will be
    * thrown.
    */
   public void setReadOnly(boolean ro) {
      this.isReadOnly = ro;
   }

   /**
    * Returns the 'read only' status of element.
    * <p>
    * If element is read only, and one wants to change its property, the RuntimeException will be
    * thrown.
    */
   public boolean isReadOnly() {
      return isReadOnly;
   }

   /**
    * Returns if the element is required or not, which is defined by XPDL schema. If element is
    * required, its value must be defined (In the case of complex elements, all the required
    * subelements must be defined). Otherwise, the whole Package won't be valid by the XPDL schema.
    */
   public boolean isRequired() {
      return isRequired;
   }

   /**
    * Indicates if element is empty.
    */
   public boolean isEmpty() {
      return !(value != null && value.trim().length() > 0);
   }

   /**
    * Sets the element value. If it is simple element or an non-choice attribute, this sets the
    * actual value of the element. If it is choice attribute, it sets the choosen value. Only some
    * complex elements (Condition, SchemaType, and ExtendedAttribute) allows you to use this method,
    * while others will throw RuntimeException.
    */
   public void setValue(String v) {
      if (isReadOnly) {
         String elInfo="Name="+toName()+", Val="+toValue();
         if (parent!=null) {
            elInfo+=", Parent name="+parent.toName();
         }
         throw new RuntimeException("Can't set the value of read only element: "+elInfo+"!");
      }
      boolean notify=false;
      String oldValue=value;
      if (!this.value.equals(v)) {
         notify=true;
      }
      this.value = v;

      if (notify && (notifyMainListeners || notifyListeners)) {
         XMLElementChangeInfo info=createInfo(oldValue, value, null, XMLElementChangeInfo.UPDATED);
         if (notifyListeners) {            
            notifyListeners(info);
         }
         if (notifyMainListeners) {
            notifyMainListeners(info);
         }
      }
   }

   /**
    * Returns the element value.
    */
   public String toValue() {
      return value;
   }

   /**
    * Returns the name of element.
    */
   public String toName() {
      return name;
   }

   /** Gets the parent element in DOM tree. */
   public XMLElement getParent() {
      return parent;
   }

   /**
    * Sets the parent element in DOM tree.
    * <p>
    * This method is used when collection, complex element or complex choice is cloned, to set new
    * parent element of the cloned sub-elements.
    */
   public void setParent(XMLElement el) {
      this.parent = el;
   }

   /**
    * Used to create exact copy of the element.
    */
   public Object clone() {
      // NOTE: DO NOT MAKE A originalHashCode as new Integer(this.hasCode())
      // CHECK: is the above case for the "name" and "value" ????????
      XMLElement d = null;
      try {
//         System.out.println("Cloning XMLELement "+this);
         d = (XMLElement) super.clone();
         d.parent=this.parent;
         d.name = new String(this.name);
         d.value = new String(this.value);
         d.isRequired = this.isRequired;
         d.isReadOnly = this.isReadOnly;
         d.listeners=new ArrayList();
         d.notifyListeners=false;
         d.notifyMainListeners=false;
         //d.parent = this.parent;
      } catch (CloneNotSupportedException ex) {
         // Won't happen because we implement Cloneable
         throw new Error(ex.toString());
      }
//      System.out.println("return cloned XMLELement "+d);
      return d;
   }

   public Integer getOriginalElementHashCode() {
      return originalElementHashCode;
   }
   
   public boolean equals(Object e) {
//System.out.println("Checking eq for el "+e+" with el "+this);
      if (this == e) {
//System.out.println("       Elements are identical");         
         return true;
      }
      boolean equals=false;
      if (e != null && e instanceof XMLElement && e.getClass().equals(this.getClass())) {
         XMLElement el = (XMLElement) e;
         // TODO: do we need to check isReadOnly for equality?
         equals=this.name.equals(el.name);
//System.out.println("       Element names equal - "+equals);
         equals=equals && this.value.equals(el.value);
//System.out.println("       Element values equal - "+equals);
         equals=equals && (this.isRequired == el.isRequired);
//System.out.println("       Element required equal - "+equals);
         //&& (this.parent == null ? el.parent == null : this.parent.equals(el.parent)));
      } else {
//         System.out.println("    Els are not the same class: el="+e+",  this="+this);

      }
//System.out.println("       equals final - "+equals);      
      return equals;
   }

   
   public List getListeners () {
      if (listeners==null) {
         listeners=new ArrayList();
      }
      return new ArrayList(listeners);
   }
   
   public void addListener (XMLElementChangeListener listener) {
      if (listeners==null) {
         listeners=new ArrayList();
      }
      listeners.add(listener);
//      System.out.println("ADDED listener for element "+this+", hc="+this.hashCode());
   }
   
   public boolean removeListener (XMLElementChangeListener listener) {
      if (listeners==null) {
         listeners=new ArrayList();
      }
//      System.out.println("REMOVED listener for element "+this+", hc="+this.hashCode());
      return listeners.remove(listener);
   }   
   
   protected void notifyListeners (XMLElementChangeInfo info) {      
      Iterator it=getListeners().iterator();
      while (it.hasNext()) {
         XMLElementChangeListener listener=(XMLElementChangeListener)it.next();         
         listener.xmlElementChanged(info);
      }      
   }
   
   protected void notifyMainListeners (XMLElementChangeInfo info) {
      XMLElement main=getMainElement();
      if (main!=null) {
         Iterator it=main.getListeners().iterator();
         while (it.hasNext()) {
            XMLElementChangeListener listener=(XMLElementChangeListener)it.next();
            listener.xmlElementChanged(info);
         }
      }      
   }
      
   protected XMLElement getMainElement () {
      XMLElement el=this;
      while (!el.isMainElement()) {
         el = el.getParent();
         if (el == null)
            break;
      }
      return el;      
   }
   
   protected boolean isMainElement () {
      return false;
   }
   
   public void setNotifyMainListeners (boolean notify) {
      this.notifyMainListeners=notify;
   }

   public void setNotifyListeners (boolean notify) {
      this.notifyListeners=notify;
   }

   protected XMLElementChangeInfo createInfo (Object oldVal,Object newVal,List changedSubElements,int action) {
      XMLElementChangeInfo info=new XMLElementChangeInfo();
      info.setChangedElement(this);
      info.setOldValue(oldVal);
      info.setNewValue(newVal);
      info.setChangedSubElements(changedSubElements);
      info.setAction(action);
      System.out.println("INFO="+info);
      return info;
   }
      
}

