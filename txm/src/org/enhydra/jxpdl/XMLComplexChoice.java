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

/**
 * Class that represents choice of complex elements from XML schema.
 * 
 * @author Sasa Bojanic
 */
public abstract class XMLComplexChoice extends XMLElement {

   /** The list of choices for this element */
   protected ArrayList choices;

   /** The choosen element */
   protected XMLElement choosen;

   /** Flag that indicates whether the caches are initialized. */
   protected transient boolean cachesInitialized = false;

   /**
    * Creates a new instance of element: sets <code>name</code>, <code>parent</code>
    * <code>isRequired</code> properties to specified ones.
    * 
    * @param parent Parent element
    * @param name Name of this attribute.
    * @param isRequired true if this attribute is required by XPDL schema.
    */
   public XMLComplexChoice(XMLComplexElement parent, String name, boolean isRequired) {
      super(parent, name, isRequired);
      fillChoices();
   }

   /**
    * This implementation does nothing.
    * 
    * @param v The value to set.
    */
   public void setValue(String v) {
      // throw new RuntimeException("Can't set value for this type of element!");
   }

   /** Removes XPDL 1.0 support for this element and its choices elements. */
   public void removeXPDL1Support() {
      super.removeXPDL1Support();
      for (int i = 0; i < choices.size(); i++) {
         ((XMLElement) choices.get(i)).removeXPDL1Support();
      }
   }

   public void makeAs(XMLElement el) {
      super.makeAs(el);

      XMLComplexChoice cce = (XMLComplexChoice) el;
      int chsnind = cce.choices.indexOf(cce.getChoosen());
      XMLElement newChsn = (XMLElement) choices.get(chsnind);
      newChsn.makeAs(cce.getChoosen());
      this.setChoosen(newChsn);
      // Iterator it1=this.choices.iterator();
      // Iterator it2=cce.choices.iterator();
      // int chsnind=cce.choices.indexOf(cce.getChoosen());
      //
      // while (it1.hasNext() && it2.hasNext()) {
      // XMLElement e1=(XMLElement)it1.next();
      // XMLElement e2=(XMLElement)it2.next();
      // e1.makeAs(e2);
      // }
      // this.setChoosen((XMLElement)choices.get(chsnind));

   }

   /**
    * Overrides super-method to set this element and all of its choice elements read only
    * value to the one specified.
    * 
    * @param ro true if element will be read-only.
    */
   public void setReadOnly(boolean ro) {
      super.setReadOnly(ro);
      for (int i = 0; i < choices.size(); i++) {
         ((XMLElement) choices.get(i)).setReadOnly(ro);
      }
   }

   public void setNotifyListeners(boolean notify) {
      super.setNotifyListeners(notify);
      for (int i = 0; i < choices.size(); i++) {
         ((XMLElement) choices.get(i)).setNotifyListeners(notify);
      }
   }

   public void setNotifyMainListeners(boolean notify) {
      super.setNotifyMainListeners(notify);
      for (int i = 0; i < choices.size(); i++) {
         ((XMLElement) choices.get(i)).setNotifyMainListeners(notify);
      }
   }

   /**
    * Initializes caches in read-only mode. If mode is not read-only, throws
    * RuntimeException.
    * 
    * @param xmli XMLInterface instance.
    */
   public void initCaches(XMLInterface xmli) {
      if (!isReadOnly) {
         throw new RuntimeException("Caches can be initialized only in read-only mode!");
      }
      clearCaches();
      Iterator it = choices.iterator();
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

   public void clearCaches() {
      Iterator it = choices.iterator();
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

   /**
    * Checks if there is no chosen element.
    * 
    * @return true if there is no chosen element.
    */
   public boolean isEmpty() {
      return (choosen instanceof XMLEmptyChoiceElement);
   }

   /**
    * The possible choices - instances of XMLElement class.
    * 
    * @return The possible choices for this element.
    */
   public ArrayList getChoices() {
      return choices;
   }

   /**
    * Returns the chosen XMLElement.
    * 
    * @return The chosen XMLElement.
    */
   public XMLElement getChoosen() {
      return choosen;
   }

   /**
    * Sets the chosen XMLElement.
    * 
    * @param ch Element to set.
    */
   public void setChoosen(XMLElement ch) {
      if (isReadOnly) {
         throw new RuntimeException("Can't set the value of read only element!");
      }
      if (!choices.contains(ch)) {
         throw new RuntimeException("Incorrect value! The possible values are: "
                                    + choices);
      }
      boolean notify = false;
      XMLElement oldChoosen = choosen;
      if (this.choosen == null || !this.choosen.equals(ch)) {
         notify = true;
      }

      this.choosen = ch;

      if (notify && (notifyMainListeners || notifyListeners)) {
         XMLElementChangeInfo info = createInfo(oldChoosen,
                                                choosen,
                                                null,
                                                XMLElementChangeInfo.UPDATED);
         if (notifyListeners) {
            notifyListeners(info);
         }
         if (notifyMainListeners) {
            notifyMainListeners(info);
         }
      }
   }

   /**
    * Fills the list of possible choices. The classes that extend this class should
    * provide proper implementation of this method.
    */
   protected abstract void fillChoices();

   public Object clone() {
      XMLComplexChoice d = (XMLComplexChoice) super.clone();

      d.choices = new ArrayList();
      d.choosen = null;
      d.cachesInitialized = false;
      Iterator it = choices.iterator();
      while (it.hasNext()) {
         XMLElement c = (XMLElement) it.next();
         XMLElement cloned = (XMLElement) c.clone();
         d.choices.add(cloned);
         cloned.setParent(d);
         if (d.choosen == null && this.choosen != null && this.choosen.equals(c)) {
            d.choosen = cloned;
         }
      }
      return d;
   }

   public boolean equals(Object e) {
      boolean equals = super.equals(e);
      if (equals) {
         XMLComplexChoice el = (XMLComplexChoice) e;
         equals = (this.choices.equals(el.choices));
         // System.out.println("    XMLComplexChoice choices equal - "+equals);
         equals = equals
                  && !((choices == null && el.choices != null) || (choices != null && el.choices == null));
         // System.out.println("    XMLComplexChoice choosen equal - "+equals);
      }
      return equals;
   }

}
