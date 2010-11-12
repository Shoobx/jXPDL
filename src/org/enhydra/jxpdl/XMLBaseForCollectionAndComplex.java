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
 *  Base class for implementing XMLComplexElement and XMLCollection classes.
 * 
 *  @author Sasa Bojanic
 */
public abstract class XMLBaseForCollectionAndComplex extends XMLElement {

   protected SequencedHashMap elementMap;
   protected ArrayList elements;
   
   protected transient boolean cachesInitialized=false;
   
   public XMLBaseForCollectionAndComplex (XMLElement parent, boolean isRequired) {
      super(parent, isRequired);
      elements=new ArrayList();
      elementMap=new SequencedHashMap();   
   }

   public XMLBaseForCollectionAndComplex(XMLElement parent, boolean isRequired, boolean xpdl1support) {
      super(parent, isRequired, xpdl1support);
      elements=new ArrayList();
      elementMap=new SequencedHashMap();   
   }

   public XMLBaseForCollectionAndComplex (XMLElement parent, String name, boolean isRequired) {
      super(parent, name, isRequired);
      elements=new ArrayList();
      elementMap=new SequencedHashMap();
   } 
   
   public void setValue(String v) {
      throw new RuntimeException("Can't set value for this type of element!");
   }
         
   /**
    * Sets this element, and all contained elements to be read only or not.
    */
    public void setReadOnly (boolean ro) {
       super.setReadOnly(ro);
       Iterator it=elements.iterator();
       while (it.hasNext()) {
          XMLElement el=(XMLElement)it.next();
          el.setReadOnly(ro);
       }
       if (!ro) {
          clearCaches();
       }
    }

    public void setNotifyMainListeners (boolean notify) {
       super.setNotifyMainListeners(notify);
       Iterator it=elements.iterator();
       while (it.hasNext()) {
          XMLElement el=(XMLElement)it.next();
          el.setNotifyMainListeners(notify);
       }
    }
    
    public void setNotifyListeners (boolean notify) {
       super.setNotifyListeners(notify);
       Iterator it=elements.iterator();
       while (it.hasNext()) {
          XMLElement el=(XMLElement)it.next();
          el.setNotifyListeners(notify);
       }
    }

    /** 
    * Initializes caches in read-only mode. If mode is not read-only,
    * throws RuntimeException.
    * @param xmli TODO
    */
   public void initCaches (XMLInterface xmli) {
      if (!isReadOnly) {
         throw new RuntimeException(toName()+": Caches can be initialized only in read-only mode!");
      }
      clearCaches();
      Iterator it=elements.iterator();
      while (it.hasNext()) {
         XMLElement el=(XMLElement)it.next();
         if (el instanceof XMLBaseForCollectionAndComplex) {
            ((XMLBaseForCollectionAndComplex)el).initCaches(xmli);
         } else if (el instanceof XMLComplexChoice) {
            ((XMLComplexChoice)el).initCaches(xmli);
         }
      }               
      cachesInitialized=true;
   }

   public void clearCaches () {
      Iterator it=elements.iterator();
      while (it.hasNext()) {
         XMLElement el=(XMLElement)it.next();
         if (el instanceof XMLBaseForCollectionAndComplex) {
            ((XMLBaseForCollectionAndComplex)el).clearCaches();
         } else if (el instanceof XMLComplexChoice) {
            ((XMLComplexChoice)el).clearCaches();
         }
      }
      cachesInitialized=false;
   }
   
   /** Adds new element. */
   protected abstract void add (XMLElement el);         

   /** Adds new element to a certain position */
   protected abstract boolean add (int no,XMLElement el);
   
   /** Returns true if there is such element in collection. */
   public boolean contains (XMLElement el) {
      return elements.contains(el);
   }

   /** Gets the element from specified location. */
   public XMLElement get (int no) {
      if (no<0 || no>=size()) throw new RuntimeException("There is no element at position "+no+"!");

      return (XMLElement)elements.get(no);
   }

   /** Returns the number of elements. */
   public int size () {
      return elements.size();
   }

   /** Returns the copy of the list all elements within collection. */
   public ArrayList toElements() {
      return new ArrayList(elements);
   }
   
   /** Returns the copy of the map of all elements within collection. */
   public SequencedHashMap toElementMap () {
      return new SequencedHashMap(elementMap);
   }

   public boolean equals (Object e) {
      boolean equals=super.equals(e);
      if (equals) {
         XMLBaseForCollectionAndComplex el=(XMLBaseForCollectionAndComplex)e;
         equals=(this.elements.equals(el.elements));
//         System.out.println("The subelements equal - "+equals);
         //return (this.elements.equals(el.elements));
      }
      return equals;
   }
   
}
