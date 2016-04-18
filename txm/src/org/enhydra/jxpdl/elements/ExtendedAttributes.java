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
import java.util.Iterator;

import org.enhydra.jxpdl.XMLCollection;
import org.enhydra.jxpdl.XMLComplexElement;
import org.enhydra.jxpdl.XMLElement;
import org.enhydra.jxpdl.XMLInterface;
import org.enhydra.jxpdl.XMLUtil;
import org.enhydra.jxpdl.utilities.SequencedHashMap;

/**
 * Represents corresponding element from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class ExtendedAttributes extends XMLCollection {

   /**
    * Map that holds the information about all the ExtendedAttribute elements within
    * collection with a different names (keys represent the names, and values the list of
    * ExtendedAttribute elements). This map can be used only if this element's mode is
    * read-only and when caches are initialized.
    */
   public transient SequencedHashMap eaMap;

   /**
    * String representing an XML part defining this collection.
    */
   protected String extAttribsString;

   /** Constructs a new object with the given {@link XMLComplexElement} as a parent. */
   public ExtendedAttributes(XMLComplexElement parent) {
      super(parent, false);
   }

   public void makeAs(XMLElement el) {
      super.makeAs(el);
      this.extAttribsString = ((ExtendedAttributes) el).extAttribsString;
   }

   /**
    * Generates new {@link ExtendedAttribute} object. This object is not member of the collection
    * yet, it has to be explicitly added to the collection.
    */
   public XMLElement generateNewElement() {
      return new ExtendedAttribute(this);
   }

   /**
    * Returns the first {@link ExtendedAttribute} from this collection that has the given name, or
    * null if there is no one.
    */
   public ExtendedAttribute getFirstExtendedAttributeForName(String name) {
      ExtendedAttribute ea = null;
      ArrayList l = getElementsForName(name);
      if (l != null && l.size() > 0) {
         ea = (ExtendedAttribute) l.get(0);
      }
      return ea;
   }

   /**
    * Initializes the caches for this collection.
    */
   public void initCaches(XMLInterface xmli) {
      super.initCaches(xmli);
      Iterator it = elements.iterator();
      while (it.hasNext()) {
         ExtendedAttribute ea = (ExtendedAttribute) it.next();
         String eaName = ea.getName();
         ArrayList l = (ArrayList) eaMap.get(eaName);
         if (l == null) {
            l = new ArrayList();
         }
         l.add(ea);
         eaMap.put(eaName, l);
      }
      if (parent instanceof Application) {
         getExtendedAttributesString();
      }
   }

   /** Initializes string representing XML part of this element. */
   public void initExtAttribString() {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      if (this.size() > 0) {
         try {
            extAttribsString = XMLUtil.stringifyExtendedAttributes(this);
         } catch (Throwable thr) {
            throw new RuntimeException("Can't stringify extended attributes!");
         }
      } else {
         extAttribsString = "";
      }
   }

   /** Sets the value of string representing XML part of this element to null. */
   public void clearExtAttribString() {
      extAttribsString = null;
   }

   /**
    * Returns the string representation of XML part of this element. If this element's
    * mode is not read-only, RuntimeException is thrown.
    */
   public String getExtendedAttributesString() {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }
      if (extAttribsString == null) {
         initExtAttribString();
      }
      return extAttribsString;
   }

   public void clearCaches() {
      eaMap = new SequencedHashMap();
      super.clearCaches();
   }

   public void clear() {
      if (eaMap != null) {
         eaMap.clear();
      }
      super.clear();
   }

   /**
    * Returns true if there is at least one ExtendedAttribute with given name.
    */
   public boolean containsElement(String name) {
      if (isReadOnly && cachesInitialized) {
         return eaMap.containsKey(name);
      }

      Iterator it = elements.iterator();
      while (it.hasNext()) {
         ExtendedAttribute ea = (ExtendedAttribute) it.next();
         if (ea.getName().equals(name)) {
            return true;
         }
      }
      return false;
   }

   /**
    * Returns true if there is at least one ExtendedAttribute with given value.
    */
   public boolean containsValue(String val) {
      Iterator it = elements.iterator();
      while (it.hasNext()) {
         ExtendedAttribute ea = (ExtendedAttribute) it.next();
         if (ea.getVValue().equals(val)) {
            return true;
         }
      }
      return false;
   }

   /**
    * Returns a list of all ExtendedAttribute elements with specified name.
    */
   public ArrayList getElementsForName(String name) {
      if (isReadOnly && cachesInitialized) {
         return (ArrayList) eaMap.get(name);
      }

      ArrayList l = new ArrayList();
      Iterator it = elements.iterator();
      while (it.hasNext()) {
         ExtendedAttribute ea = (ExtendedAttribute) it.next();
         if (ea.getName().equals(name)) {
            l.add(ea);
         }
      }
      return l;
   }

   public Object clone() {
      ExtendedAttributes d = (ExtendedAttributes) super.clone();
      d.extAttribsString = this.extAttribsString;

      return d;
   }

}
