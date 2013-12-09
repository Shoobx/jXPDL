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
import java.util.Iterator;

import org.enhydra.jxpdl.utilities.SequencedHashMap;

/**
 * Class that represents complex element from XML schema.
 * 
 * @author Sasa Bojanic
 */
public abstract class XMLComplexElement extends XMLBaseForCollectionAndComplex {

   /**
    * Creates a new instance of element: sets <code>name</code> to name of concrete class
    * implementation of this abstract class, and <code>parent</code> and
    * <code>isRequired</code> properties to the specified ones.
    * 
    * @param parent Parent element
    * @param isRequired true if this attribute is required by XPDL schema.
    */
   public XMLComplexElement(XMLElement parent, boolean isRequired) {
      super(parent, isRequired);
      fillStructure();
   }

   /**
    * Creates a new instance of element: sets <code>name</code> to name of concrete class
    * implementation of this abstract class, and <code>parent</code>,
    * <code>isRequired</code> and <code>xpdl1support</code> properties to the specified
    * ones.
    * 
    * @param parent Parent element.
    * @param isRequired true if this attribute is required by XPDL schema.
    * @param xpdl1support true if element structure should support XPDL 1 schema.
    */
   public XMLComplexElement(XMLElement parent, boolean isRequired, boolean xpdl1support) {
      super(parent, isRequired, xpdl1support);
      fillStructure();
   }

   /**
    * Creates a new instance of element: sets <code>name</code>, <code>parent</code>
    * <code>isRequired</code> properties to specified ones.
    * 
    * @param parent Parent element
    * @param name Name of this attribute.
    * @param isRequired true if this attribute is required by XPDL schema.
    */
   public XMLComplexElement(XMLElement parent, String name, boolean isRequired) {
      super(parent, name, isRequired);
      fillStructure();
   }

   public void makeAs(XMLElement el) {
      if (!(el != null && el.getClass().equals(this.getClass()) && el.toName()
         .equals(this.toName()))) {
         throw new RuntimeException("Can't perform makeAs! Element["
                                    + el + "," + el.getClass() + "|" + this.getClass()
                                    + "," + el.toName() + "|" + this.toName() + "]");
      }
      this.xpdl1support = ((XMLComplexElement) el).xpdl1support;
      // System.err.println("Performing make as for element "+el.toName()+", pkg="+getMainElement().hashCode()+", mapkg="+el.getMainElement().hashCode()+", notifml="+notifyMainListeners);
      XMLComplexElement ce = (XMLComplexElement) el;
      Iterator it1 = this.elements.iterator();
      Iterator it2 = ce.elements.iterator();
      while (it1.hasNext() && it2.hasNext()) {
         XMLElement e1 = (XMLElement) it1.next();
         XMLElement e2 = (XMLElement) it2.next();
         e1.makeAs(e2);
      }

   }

   protected void add(XMLElement el) {
      elements.add(el);
      elementMap.put(el.toName(), el);
   }

   protected boolean add(int no, XMLElement el) {
      if (no < 0 || no > size())
         return false;
      elements.add(no, el);
      // here, we don't care about position
      elementMap.put(el.toName(), el);
      return true;
   }

   /**
    * This element is empty if its value is not set, and if all elements in the structure
    * are empty.
    * 
    * @return true if element is empty.
    */
   public boolean isEmpty() {
      boolean isEmpty = true;
      Iterator it = elements.iterator();
      while (it.hasNext()) {
         XMLElement el = (XMLElement) it.next();
         isEmpty = isEmpty && el.isEmpty();
      }
      isEmpty = isEmpty && value.trim().length() == 0;
      return isEmpty;
   }

   /**
    * Returns the collection of XMLElement instances this element is made of.
    * 
    * @return The collection of XMLElement instances this element is made of.
    */
   public ArrayList getXMLElements() {
      ArrayList els = new ArrayList();
      Iterator it = elements.iterator();
      while (it.hasNext()) {
         Object el = it.next();
         if (!(el instanceof XMLAttribute)) {
            els.add(el);
         }
      }
      return els;
   }

   /**
    * Returns the collection of XMLAttribute instances this element is made of.
    * 
    * @return The collection of XMLAttribute instances this element is made of.
    */
   public ArrayList getXMLAttributes() {
      ArrayList attribs = new ArrayList();
      Iterator it = elements.iterator();
      while (it.hasNext()) {
         Object el = it.next();
         if (el instanceof XMLAttribute) {
            attribs.add(el);
         }
      }
      return attribs;
   }

   /**
    * Sets the element from structure with specified name to the specified value.
    * 
    * @param name The name of the element within the structure.
    * @param value The value to set for the element.
    */
   public void set(String name, String value) {
      if (isReadOnly) {
         throw new RuntimeException("Can't change element from read only structure!");
      }
      XMLElement el = get(name);
      if (el != null) {
         el.setValue(value);
      } else {
         throw new RuntimeException("No such element!");
      }
   }

   /**
    * Sets the element that is placed at specified location within structure to the
    * specified value.
    * 
    * @param no The position of element within the structure.
    * @param value The value to set for the element.
    */
   public boolean set(int no, String value) {
      if (no < 0 || no >= size())
         return false;
      if (isReadOnly) {
         throw new RuntimeException("Can't change element from read only structure!");
      }

      XMLElement el = get(no);
      el.setValue(value);
      return true;
   }

   /**
    * Gets the element with specified name from structure.
    * 
    * @param name Name of the element.
    * @return The element with specified name.
    */
   public XMLElement get(String name) {
      return (XMLElement) elementMap.get(name);
   }

   /**
    * Returns true if there is an element with such element in structure.
    * 
    * @param name The name of the element.
    * @return true if there is an element in the structure.
    */
   public boolean containsName(String name) {
      return elementMap.containsKey(name);
   }

   /**
    * The classes that are derived from this class has to give its definition for this
    * method. It is used to insert all members of those classes that are derived from
    * XMLElement.
    * <p>
    * NOTE: The order of inserted elements is relevant for XML to be valid (members of
    * classes derived from this class must be inserted into first mentioned list in the
    * same order that they are within a corresponding tag for those classes within WfMC
    * XML).
    */
   protected abstract void fillStructure();

   public Object clone() {
      XMLComplexElement d = (XMLComplexElement) super.clone();
      d.elements = new ArrayList();
      d.elementMap = new SequencedHashMap();
      d.cachesInitialized = false;
      Iterator it = elements.iterator();
      while (it.hasNext()) {
         XMLElement el = (XMLElement) it.next();
         XMLElement cl = (XMLElement) el.clone();
         cl.setParent(d);
         d.elements.add(cl);
         d.elementMap.put(cl.toName(), cl);
      }
      return d;
   }

}
