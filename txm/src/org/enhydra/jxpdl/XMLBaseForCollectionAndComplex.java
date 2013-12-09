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
 * Base class for implementing XMLComplexElement and XMLCollection classes.
 * 
 * @author Sasa Bojanic
 */
public abstract class XMLBaseForCollectionAndComplex extends XMLElement {

   /**
    * The map of elements of the collection or sub-elements of complex element. In the
    * case of XMLCollection implementation, this map is empty unless read-only attribute
    * of the collection is set to true, the caches are initialized and the collection
    * elements are of the XMLCollectionElement type. In that case, the key of the map is
    * the collection's element 'Id' attribute, and the value is the XMLCollectionElement
    * itself. In the case of XMLComplexElement implementation, the key is the name of the
    * sub-element, and the value is the sub-element itself.
    */
   protected SequencedHashMap elementMap;

   /**
    * The list of elements of the collection or sub-elements of complex element.
    */
   protected ArrayList elements;

   /**
    * Flag that determines if the caches are initialized or not (used only for
    * XMLCollection implementation).
    */
   protected transient boolean cachesInitialized = false;

   /**
    * Creates a new instance of element: sets <code>name</code> to name of concrete class
    * implementation of this abstract class, and <code>parent</code> and
    * <code>isRequired</code> properties to the specified ones.
    * @param parent Parent element
    * @param isRequired true if this attribute is required by XPDL schema.
    */
   public XMLBaseForCollectionAndComplex(XMLElement parent, boolean isRequired) {
      super(parent, isRequired);
      elements = new ArrayList();
      elementMap = new SequencedHashMap();
   }

   /**
    * Creates a new instance of element: sets <code>name</code> to name of concrete class
    * implementation of this abstract class, and <code>parent</code>,
    * <code>isRequired</code> and <code>xpdl1support</code> properties to the specified
    * ones.
    * @param parent Parent element.
    * @param isRequired true if this attribute is required by XPDL schema.
    * @param xpdl1support true if element structure should support XPDL 1 schema.
    */
   public XMLBaseForCollectionAndComplex(XMLElement parent,
                                         boolean isRequired,
                                         boolean xpdl1support) {
      super(parent, isRequired, xpdl1support);
      elements = new ArrayList();
      elementMap = new SequencedHashMap();
   }

   /**
    * Creates a new instance of element: sets <code>name</code>, <code>parent</code>
    * <code>isRequired</code> properties to specified ones.
    * @param parent Parent element
    * @param name Name of this attribute.
    * @param isRequired true if this attribute is required by XPDL schema.
    */
   public XMLBaseForCollectionAndComplex(XMLElement parent,
                                         String name,
                                         boolean isRequired) {
      super(parent, name, isRequired);
      elements = new ArrayList();
      elementMap = new SequencedHashMap();
   }

   /**
    * Throws RuntimeException if not overriden by implementor class.
    */
   public void setValue(String v) {
      throw new RuntimeException("Can't set value for this type of element!");
   }

   /**
    * Removes XPDL 1.0 support for this element, and all contained elements.
    */
   public void removeXPDL1Support() {
      super.removeXPDL1Support();
      Iterator it = elements.iterator();
      while (it.hasNext()) {
         XMLElement el = (XMLElement) it.next();
         el.removeXPDL1Support();
      }
   }

   /**
    * Sets this element, and all contained elements to be read only or not.
    * @param ro if true the element and all sub-elements will be read-only.
    */
   public void setReadOnly(boolean ro) {
      super.setReadOnly(ro);
      Iterator it = elements.iterator();
      while (it.hasNext()) {
         XMLElement el = (XMLElement) it.next();
         el.setReadOnly(ro);
      }
      if (!ro) {
         clearCaches();
      }
   }

   /**
    * Notifies main listeners of this element, and all contained elements.
    * @param notify if true the main listener will be notified on any change to this element or its sub-elements.
    */
   public void setNotifyMainListeners(boolean notify) {
      super.setNotifyMainListeners(notify);
      Iterator it = elements.iterator();
      while (it.hasNext()) {
         XMLElement el = (XMLElement) it.next();
         el.setNotifyMainListeners(notify);
      }
   }

   /**
    * Notifies listeners of this element, and all contained elements.
    * @param notify if true the listeners will be notified on any change to this element or its sub-elements.
    */
   public void setNotifyListeners(boolean notify) {
      super.setNotifyListeners(notify);
      Iterator it = elements.iterator();
      while (it.hasNext()) {
         XMLElement el = (XMLElement) it.next();
         el.setNotifyListeners(notify);
      }
   }

   /**
    * Initializes caches in read-only mode. If mode is not read-only, throws
    * RuntimeException.
    * 
    * @param xmli The instance of XMLInterface class.
    */
   public void initCaches(XMLInterface xmli) {
      if (!isReadOnly) {
         throw new RuntimeException(toName()
                                    + ": Caches can be initialized only in read-only mode!");
      }
      clearCaches();
      Iterator it = elements.iterator();
      while (it.hasNext()) {
         XMLElement el = (XMLElement) it.next();
         if (el instanceof XMLBaseForCollectionAndComplex) {
            ((XMLBaseForCollectionAndComplex) el).initCaches(xmli);
         } else if (el instanceof XMLComplexChoice) {
            ((XMLComplexChoice) el).initCaches(xmli);
         }
      }
      cachesInitialized = true;
   }

   /**
    * Clears caches of this element and all contained elements.
    */
   public void clearCaches() {
      Iterator it = elements.iterator();
      while (it.hasNext()) {
         XMLElement el = (XMLElement) it.next();
         if (el instanceof XMLBaseForCollectionAndComplex) {
            ((XMLBaseForCollectionAndComplex) el).clearCaches();
         } else if (el instanceof XMLComplexChoice) {
            ((XMLComplexChoice) el).clearCaches();
         }
      }
      cachesInitialized = false;
   }

   /** Adds new element.
    * 
    * @param el Element to add.
    */
   protected abstract void add(XMLElement el);

   /** Adds new element to a certain position
    * 
    * @param no The position of element to add.
    * @param el Element to add.
    * @return true if element is added to the structure/collection.
    */
   protected abstract boolean add(int no, XMLElement el);

   /** Returns true if there is such element in collection.
    * 
    * @param el Element to check.
    * @return true if element is contained within collection.
    */
   public boolean contains(XMLElement el) {
      return elements.contains(el);
   }

   /** Gets the element from specified location.
    * 
    * @param no The element location.
    * @return The element at specified location.
    */
   public XMLElement get(int no) {
      if (no < 0 || no >= size())
         throw new RuntimeException("There is no element at position " + no + "!");

      return (XMLElement) elements.get(no);
   }

   /** Returns the number of elements within collection.
    * 
    * @return The number of elements within collection.
    */
   public int size() {
      return elements.size();
   }

   /** Returns the copy of the list all elements within collection.
    * 
    * @return The copy of the list all elements within collection.
    */
   public ArrayList toElements() {
      return new ArrayList(elements);
   }

   /** Returns the copy of the map of all elements within collection.
    * 
    * @return The copy of the map of all elements within collection.
    */
   public SequencedHashMap toElementMap() {
      return new SequencedHashMap(elementMap);
   }

   public boolean equals(Object e) {
      boolean equals = super.equals(e);
      if (equals) {
         XMLBaseForCollectionAndComplex el = (XMLBaseForCollectionAndComplex) e;
         equals = (this.elements.equals(el.elements));
         // System.out.println("The subelements equal - "+equals);
         // return (this.elements.equals(el.elements));
      }
      return equals;
   }

}
