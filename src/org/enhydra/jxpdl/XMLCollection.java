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
import java.util.List;

import org.enhydra.jxpdl.utilities.SequencedHashMap;

/**
 * Class that represents collection of elements from XML schema.
 * 
 * @author Sasa Bojanic
 */
public abstract class XMLCollection extends XMLBaseForCollectionAndComplex {

   /**
    * Creates a new instance of element: sets <code>name</code> to name of concrete class
    * implementation of this abstract class, and <code>parent</code> and
    * <code>isRequired</code> properties to the specified ones.
    * 
    * @param parent Parent element
    * @param isRequired true if this attribute is required by XPDL schema.
    */
   public XMLCollection(XMLComplexElement parent, boolean isRequired) {
      super(parent, isRequired);
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
   public XMLCollection(XMLComplexElement parent, boolean isRequired, boolean xpdl1support) {
      super(parent, isRequired, xpdl1support);
   }

   /**
    * Creates a new instance of element: sets <code>name</code>, <code>parent</code>
    * <code>isRequired</code> properties to specified ones.
    * 
    * @param parent Parent element
    * @param name Name of this attribute.
    * @param isRequired true if this attribute is required by XPDL schema.
    */
   public XMLCollection(XMLComplexElement parent, String name, boolean isRequired) {
      super(parent, name, isRequired);
   }

   /**
    * Creates a new instance of element: sets <code>name</code> to name of concrete class
    * implementation of this abstract class, and <code>parent</code> and
    * <code>isRequired</code> properties to the specified ones.
    * 
    * @param parent Parent element.
    * @param isRequired true if this attribute is required by XPDL schema.
    */
   public XMLCollection(XMLComplexChoice parent, boolean isRequired) {
      super(parent, isRequired);
   }

   /**
    * Creates a new instance of element: sets <code>name</code>, <code>parent</code>
    * <code>isRequired</code> properties to specified ones.
    * 
    * @param parent Parent element
    * @param name Name of this attribute.
    * @param isRequired true if this attribute is required by XPDL schema.
    */
   public XMLCollection(XMLComplexChoice parent, String name, boolean isRequired) {
      super(parent, name, isRequired);
   }

   /**
    * Creates a new instance of element: sets <code>name</code> to name of concrete class
    * implementation of this abstract class, and <code>parent</code> and
    * <code>isRequired</code> properties to the specified ones.
    * 
    * @param parent Parent element
    * @param isRequired true if this attribute is required by XPDL schema.
    */
   public XMLCollection(XMLCollection parent, boolean isRequired) {
      super(parent, isRequired);
   }

   /**
    * Creates a new instance of element: sets <code>name</code>, <code>parent</code>
    * <code>isRequired</code> properties to specified ones.
    * 
    * @param parent Parent element
    * @param name Name of this attribute.
    * @param isRequired true if this attribute is required by XPDL schema.
    */
   public XMLCollection(XMLCollection parent, String name, boolean isRequired) {
      super(parent, name, isRequired);
   }

   public void makeAs(XMLElement el) {
      if (!(el != null && el.getClass().equals(this.getClass()) && el.toName()
         .equals(this.toName()))) {
         throw new RuntimeException("Can't perform makeAs!");
      }
      this.xpdl1support = ((XMLCollection) el).xpdl1support;
      // System.err.println("Performing make as for element "+el.toName()+", pkg="+getMainElement().hashCode()+", mapkg="+el.getMainElement().hashCode()+", notifml="+notifyMainListeners);
      SequencedHashMap oldElsMap = new SequencedHashMap();
      SequencedHashMap newElsMap = new SequencedHashMap();
      Iterator it = toElements().iterator();
      while (it.hasNext()) {
         XMLElement o = (XMLElement) it.next();
         oldElsMap.put(o.getOriginalElementHashCode(), o);
      }
      // System.err.println("OLDELSMAP="+oldElsMap);
      it = ((XMLCollection) el).toElements().iterator();
      while (it.hasNext()) {
         XMLElement o = (XMLElement) it.next();
         if (!oldElsMap.containsKey(o.getOriginalElementHashCode())) {
            XMLElement temp = null;
            if (xpdl1support) {
               temp = generateNewElementWithXPDL1Support();
            } else {
               temp = generateNewElement();
            }
            // XMLElement temp = (XMLElement)o.clone();
            temp.makeAs(o);
            newElsMap.put(temp.getOriginalElementHashCode(), temp);
         } else {
            newElsMap.put(o.getOriginalElementHashCode(), o);
         }
      }
      // if (this instanceof ExtendedAttributes) System.err.println("NEWSMAP="+oldElsMap);

      List toAdd = new ArrayList(newElsMap.keySet());
      toAdd.removeAll(oldElsMap.keySet());
      // System.err.println("TOADD="+toAdd);
      List toRemove = new ArrayList(oldElsMap.keySet());
      toRemove.removeAll(newElsMap.keySet());
      // System.err.println("TOREM="+toRemove);
      List toRetain = new ArrayList(oldElsMap.keySet());
      toRetain.retainAll(newElsMap.keySet());
      // System.err.println("TORET="+toRetain);
      List objectsToRemove = new ArrayList();
      it = toRemove.iterator();
      while (it.hasNext()) {
         XMLElement elToRemove = (XMLElement) oldElsMap.get(it.next());
         objectsToRemove.add(elToRemove);
      }
      List objectsToAdd = new ArrayList();
      it = toAdd.iterator();
      while (it.hasNext()) {
         objectsToAdd.add(newElsMap.get(it.next()));
      }

      it = toRetain.iterator();
      while (it.hasNext()) {
         Object next = it.next();
         XMLElement oldEl = (XMLElement) oldElsMap.get(next);
         XMLElement newEl = (XMLElement) newElsMap.get(next);
         oldEl.makeAs(newEl);
      }

      for (int i = 0; i < objectsToRemove.size(); i++) {
         remove((XMLElement) objectsToRemove.get(i));
      }
      for (int i = 0; i < objectsToAdd.size(); i++) {
         add((XMLElement) objectsToAdd.get(i));
      }

      oldElsMap.clear();
      it = toElements().iterator();
      while (it.hasNext()) {
         XMLElement o = (XMLElement) it.next();
         oldElsMap.put(o.getOriginalElementHashCode(), o);
      }
      checkRepositioning(oldElsMap, newElsMap);

   }

   public void initCaches(XMLInterface xmli) {
      super.initCaches(xmli);
      if (elements.size() > 0 && elements.get(0) instanceof XMLCollectionElement) {
         elementMap.clear();
         Iterator it = elements.iterator();
         while (it.hasNext()) {
            XMLCollectionElement el = (XMLCollectionElement) it.next();
            elementMap.put(el.getId(), el);
         }
      }
      cachesInitialized = true;
   }

   public void clearCaches() {
      super.clearCaches();
      elementMap.clear();
   }

   /**
    * Adds new element. NOTE: Method signature changed to public.
    * 
    * @param el Element to add.
    */
   public void add(XMLElement el) {
      if (isReadOnly) {
         throw new RuntimeException("Can't set the value of read only element!");
      }
      if (el.getClass() != generateNewElement().getClass()) {
         throw new RuntimeException("Can't add element "
                                    + el.getClass().getName() + " into "
                                    + getClass().getName() + " collection!");
      }
      List changedSubElements = new ArrayList();
      changedSubElements.add(el);

      elements.add(el);
      el.setParent(this);
      // el.originalElementHashCode=null;
      el.setNotifyListeners(this.notifyListeners);
      el.setNotifyMainListeners(this.notifyMainListeners);

      XMLElementChangeInfo info = createInfo(value,
                                             value,
                                             changedSubElements,
                                             XMLElementChangeInfo.INSERTED);
      if (notifyListeners) {
         notifyListeners(info);
      }
      if (notifyMainListeners) {
         notifyMainListeners(info);
      }
   }

   /**
    * Adds new element to a certain position. NOTE: Method signature changed to public.
    * 
    * @param no The position of element to add.
    * @param el Element to add.
    */
   public boolean add(int no, XMLElement el) {
      if (isReadOnly) {
         throw new RuntimeException("Can't set the value of read only element!");
      }
      if (el.getClass() != generateNewElement().getClass()) {
         throw new RuntimeException("Can't add element "
                                    + el.getClass().getName() + " into "
                                    + getClass().getName() + " collection!");
      }
      if (no < 0 || no > size())
         throw new RuntimeException("Can't add element to position " + no + "!");
      List changedSubElements = new ArrayList();
      changedSubElements.add(el);

      elements.add(el);
      el.setParent(this);
      // el.originalElementHashCode=null;
      el.setNotifyListeners(this.notifyListeners);
      el.setNotifyMainListeners(this.notifyMainListeners);

      XMLElementChangeInfo info = createInfo(value,
                                             value,
                                             changedSubElements,
                                             XMLElementChangeInfo.INSERTED);
      if (notifyListeners) {
         notifyListeners(info);
      }
      if (notifyMainListeners) {
         notifyMainListeners(info);
      }

      reposition(el, no);

      return true;
   }

   /**
    * Adds given elements to the collection.
    * 
    * @param els The list of elements to add.
    * @return true if elements are added to collection.
    */
   public boolean addAll(List els) {
      if (isReadOnly) {
         throw new RuntimeException("Can't set the value of read only element!");
      }
      Class gec = generateNewElement().getClass();
      if (els != null && els.size() > 0) {
         Iterator it = els.iterator();
         while (it.hasNext()) {
            XMLElement el = (XMLElement) it.next();
            if (el.getClass() != gec) {
               throw new RuntimeException("Can't add element "
                                          + el.getClass().getName() + " into "
                                          + getClass().getName() + " collection!");
            }
            elements.add(el);
            el.setParent(this);
            // el.originalElementHashCode=null;
            el.setNotifyListeners(this.notifyListeners);
            el.setNotifyMainListeners(this.notifyMainListeners);
         }
         List changedSubElements = new ArrayList(els);
         XMLElementChangeInfo info = createInfo(value,
                                                value,
                                                changedSubElements,
                                                XMLElementChangeInfo.INSERTED);
         if (notifyListeners) {
            notifyListeners(info);
         }
         if (notifyMainListeners) {
            notifyMainListeners(info);
         }
         return true;
      }

      return false;
   }

   /**
    * Removes given element from the collection.
    * 
    * @param el Element to remove
    * @return The elements' position within collection before removal.
    */
   public int remove(XMLElement el) {
      if (isReadOnly) {
         throw new RuntimeException("Can't remove element from read only structure!");
      }
      int ind = indexOf(el);
      if (ind >= 0) {
         removeElement(ind);
      }
      return ind;
   }

   /**
    * Removes element at given position from the collection.
    * 
    * @param no The position of element to remove.
    * @return The element that has been removed.
    */
   public XMLElement remove(int no) {
      if (isReadOnly) {
         throw new RuntimeException("Can't remove element from read only structure!");
      }
      if (no < 0 || no >= size())
         throw new RuntimeException("There is no element at position " + no + "!");

      return removeElement(no);
   }

   /**
    * Removes element at given position from the collection.
    * 
    * @param no The position of element to remove.
    * @return The element that has been removed.
    */
   protected XMLElement removeElement(int no) {
      XMLElement el = (XMLElement) elements.remove(no);

      if (el != null) {
         List oldPositions = new ArrayList();
         oldPositions.add(new Integer(no));
         List newPositions = new ArrayList();
         newPositions.add(new Integer(no));
         List changedPosition = new ArrayList();
         changedPosition.add(el);

         if (changedPosition.size() > 0) {
            XMLElementChangeInfo info = createInfo(oldPositions,
                                                   newPositions,
                                                   changedPosition,
                                                   XMLElementChangeInfo.REPOSITIONED);
            if (notifyListeners) {
               notifyListeners(info);
            }
            if (notifyMainListeners) {
               notifyMainListeners(info);
            }
         }

         el.setNotifyListeners(false);
         el.setNotifyMainListeners(false);
         List changedSubElements = new ArrayList();
         changedSubElements.add(el);

         elementMap.remove(el);

         XMLElementChangeInfo info = createInfo(value,
                                                value,
                                                changedSubElements,
                                                XMLElementChangeInfo.REMOVED);
         if (notifyListeners) {
            notifyListeners(info);
         }
         if (notifyMainListeners) {
            notifyMainListeners(info);
         }
      }

      return el;
   }

   /**
    * Removes given elements from the collection.
    * 
    * @param els The list of elements to remove from collection.
    * @return true if elements are removed.
    */
   public boolean removeAll(List els) {
      if (isReadOnly) {
         throw new RuntimeException("Can't remove element from read only structure!");
      }
      if (els != null && els.size() > 0) {
         List changedSubElements = new ArrayList(els);

         List oldPositions = new ArrayList();
         List newPositions = new ArrayList();

         Iterator it = els.iterator();
         while (it.hasNext()) {
            XMLElement el = (XMLElement) it.next();
            int oldPos = indexOf(el);
            if (oldPos < 0) {
               XMLUtil.removeXMLElementFromList(changedSubElements, el);
            } else {
               oldPositions.add(new Integer(oldPos));
               newPositions.add(new Integer(oldPos));
            }
         }

         if (changedSubElements.size() > 0) {
            XMLElementChangeInfo info = createInfo(oldPositions,
                                                   newPositions,
                                                   changedSubElements,
                                                   XMLElementChangeInfo.REPOSITIONED);
            if (notifyListeners) {
               notifyListeners(info);
            }
            if (notifyMainListeners) {
               notifyMainListeners(info);
            }
         }

         it = changedSubElements.iterator();
         while (it.hasNext()) {
            XMLElement el = (XMLElement) it.next();
            el.setNotifyListeners(false);
            el.setNotifyMainListeners(false);
            elements.remove(indexOf(el));
            elementMap.remove(el);
         }

         XMLElementChangeInfo info = createInfo(value,
                                                value,
                                                changedSubElements,
                                                XMLElementChangeInfo.REMOVED);
         if (notifyListeners) {
            notifyListeners(info);
         }
         if (notifyMainListeners) {
            notifyMainListeners(info);
         }
         return true;
      }

      return false;
   }

   /**
    * Repositions given element to new position.
    * 
    * @param el Element to reposition.
    * @param newPos New position of the element.
    * @return true if element is repositioned.
    */
   public boolean reposition(XMLElement el, int newPos) {
      if (isReadOnly) {
         throw new RuntimeException("Can't reposition element from read only structure!");
      }
      int oldPos = indexOf(el);
      if (newPos < 0 || newPos > size() || oldPos < 0 || oldPos == newPos)
         return false;

      List oldPositions = new ArrayList();
      oldPositions.add(new Integer(indexOf(el)));
      List newPositions = new ArrayList();
      newPositions.add(new Integer(newPos));
      List changedPosition = new ArrayList();
      changedPosition.add(el);

      elements.remove(((Integer) oldPositions.get(0)).intValue());
      elements.add(newPos, el);

      if (changedPosition.size() > 0) {
         XMLElementChangeInfo info = createInfo(oldPositions,
                                                newPositions,
                                                changedPosition,
                                                XMLElementChangeInfo.REPOSITIONED);
         if (notifyListeners) {
            notifyListeners(info);
         }
         if (notifyMainListeners) {
            notifyMainListeners(info);
         }
      }

      return true;
   }

   /**
    * Checks if there are no elements within collection.
    * 
    * @return true if there are no elements within collection.
    */
   public boolean isEmpty() {
      return size() == 0;
   }

   /**
    * Returns the element specified by Id attribute. Use only if this is collection of
    * XMLCollectionElements.
    * 
    * @param id The Id of XMLCollectionElement from this XMLCollection.
    * @return XMLCollection element that is removed.
    */
   public XMLCollectionElement getCollectionElement(String id) {
      if (isReadOnly && cachesInitialized) {
         return (XMLCollectionElement) elementMap.get(id);
      }

      Iterator it = elements.iterator();
      while (it.hasNext()) {
         XMLCollectionElement ce = (XMLCollectionElement) it.next();
         if (ce.getId().equals(id)) {
            return ce;
         }
      }
      return null;
   }

   /**
    * Checks if element with given Id exists in collection.
    * 
    * @param id The Id of element.
    * @return true if element with given Id exists in collection
    */
   public boolean containsElement(String id) {
      return getCollectionElement(id) != null;
   }

   /**
    * Returns the index of element within the elements' list.
    * 
    * @param el The element to check.
    * @return The index of element within the elements' list.
    */
   public int indexOf(XMLElement el) {
      return XMLUtil.indexOfXMLElementWithinList(elements, el);
   }

   /** Clears the collection. */
   public void clear() {
      int size = elements.size();

      List changedSubElements = new ArrayList(elements);

      Iterator it = elements.iterator();
      while (it.hasNext()) {
         XMLElement el = (XMLElement) it.next();
         el.setNotifyListeners(false);
         el.setNotifyMainListeners(false);
      }
      elements.clear();
      elementMap.clear();

      if (size > 0) {
         XMLElementChangeInfo info = createInfo(value,
                                                value,
                                                changedSubElements,
                                                XMLElementChangeInfo.REMOVED);
         if (notifyListeners) {
            notifyListeners(info);
         }
         if (notifyMainListeners) {
            notifyMainListeners(info);
         }
      }
   }

   /**
    * Generates the new element for concrete type of collection. Derived classes has to
    * implement this method to create it's collection element.
    * 
    * @return Newly created element.
    */
   public abstract XMLElement generateNewElement();

   /**
    * Generates the new element with XPDL 1.0 support for concrete type of collection.
    * Derived classes has to implement this method to create it's collection element.
    * 
    * @return Newly created element.
    */
   public XMLElement generateNewElementWithXPDL1Support() {
      return generateNewElement();
   }

   /**
    * Corrects repositioning and sends the change information to the listeners.
    * 
    * @param oldEls The ordered map of elements with their old positions (keys-original element's hash code as retrieved by calling the method getOriginalElementHashCode(), values->XMLElement instances).
    * @param newEls The ordered map of elements with their new positions (keys-original element's hash code as retrieved by calling the method getOriginalElementHashCode(), values->XMLElement instances)
    */
   protected void checkRepositioning(SequencedHashMap oldEls, SequencedHashMap newEls) {
      // System.err.println("CHECKREPFOR "+this);
      List oldPositions = new ArrayList();
      List newPositions = new ArrayList();
      List changedPosition = new ArrayList();
      List toReposition = new ArrayList(newEls.sequence());
      // toRetain.retainAll(newEls.keySet());

      // System.err.println("Start Repositioning, oldEls= "+oldEls+", newEls="+newEls);

      for (int i = 0; i < toReposition.size(); i++) {
         int oldpos = oldEls.indexOf(toReposition.get(i));
         // System.err.println("OLDPOS="+oldpos);
         int newpos = newEls.indexOf(toReposition.get(i));
         // System.err.println("NEWPOS="+newpos);
         if (oldpos != newpos) {
            Object e = elements.get(oldpos);
            changedPosition.add(e);
            oldPositions.add(new Integer(oldpos));
            newPositions.add(new Integer(newpos));
         }
      }

      if (changedPosition.size() > 0) {
         for (int i = 0; i < changedPosition.size(); i++) {
            XMLElement e = (XMLElement) changedPosition.get(i);
            elements.remove(indexOf(e));
            elements.add(((Integer) newPositions.get(i)).intValue(), e);
         }

         XMLElementChangeInfo info = createInfo(oldPositions,
                                                newPositions,
                                                changedPosition,
                                                XMLElementChangeInfo.REPOSITIONED);
         if (notifyListeners) {
            notifyListeners(info);
         }
         if (notifyMainListeners) {
            notifyMainListeners(info);
         }
      }
   }

   public Object clone() {
      XMLCollection d = (XMLCollection) super.clone();
      d.elements = new ArrayList();
      d.elementMap = new SequencedHashMap();
      d.cachesInitialized = false;
      Iterator it = this.elements.iterator();
      while (it.hasNext()) {
         Object obj = it.next();
         XMLElement el = (XMLElement) obj;
         XMLElement cl = (XMLElement) el.clone();
         cl.setParent(d);
         d.elements.add(cl);
      }
      return d;
   }

}
