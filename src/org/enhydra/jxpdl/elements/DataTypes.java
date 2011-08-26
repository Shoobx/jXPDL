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

import org.enhydra.jxpdl.XMLComplexChoice;
import org.enhydra.jxpdl.XMLComplexElement;
import org.enhydra.jxpdl.XMLElement;
import org.enhydra.jxpdl.XMLElementChangeInfo;
import org.enhydra.jxpdl.XMLInterface;

/**
 *  Represents corresponding element from XPDL schema.
 *
 *  @author Sasa Bojanic
 */
public class DataTypes extends XMLComplexChoice {

   /** Flag that determines if DataTypes structure is initialized. */
   private boolean isInitialized=false;
   
   /**
    * Constructs a new object with the given element as a parent.
    */
   public DataTypes (XMLComplexElement parent) {
      super(parent,"DataTypes", true);
      isInitialized=true;
   }

   protected void fillChoices () {
      if (isInitialized) {
         choices=new ArrayList();
         choices.add(new BasicType(this));
         choices.add(new DeclaredType(this));
         choices.add(new SchemaType(this));
         choices.add(new ExternalReference(this, true));
         choices.add(new RecordType(this));
         choices.add(new UnionType(this));
         choices.add(new EnumerationType(this));
         choices.add(new ArrayType(this));
         choices.add(new ListType(this));
         choosen=(XMLElement)choices.get(0);
         setReadOnly(isReadOnly);
         setNotifyListeners(notifyListeners);
         setNotifyMainListeners(notifyMainListeners);         
         if (cachesInitialized) {
            initCaches(null);
         }
      }
   }

   protected void clear () {
      choices=null;
      choosen=null;
      isInitialized=false;
   }
   
   /** Clears the choices other than the chosen one. */
   protected void clearOtherChoices () {
      //    "kill" other types
      if (!(choosen instanceof BasicType)) {
         getBasicType().setTypeSTRING();
      }
      if (!(choosen instanceof DeclaredType)) {
         getDeclaredType().setId("");
      }
      if (!(choosen instanceof SchemaType)) {
         getSchemaType().setValue("");
      }
      if (!(choosen instanceof ExternalReference)) {
         ExternalReference er=getExternalReference();
         er.setLocation("");
         er.setNamespace("");
         er.setXref("");
      }
      if (!(choosen instanceof RecordType)) {
         getRecordType().clear();
      }
      if (!(choosen instanceof UnionType)) {
         getUnionType().clear();
      }
      if (!(choosen instanceof EnumerationType)) {
         getEnumerationType().clear();
      }
      if (!(choosen instanceof ArrayType)) {
         ArrayType at=getArrayType();
         at.setLowerIndex("");
         at.setUpperIndex("");
         DataTypes dts=at.getDataTypes();
         if (dts.choices!=null) {
            dts.choosen=null;
            dts.clearOtherChoices();
         }
      }
      if (!(choosen instanceof ListType)) {
         DataTypes dts=getListType().getDataTypes();
         if (dts.choices!=null) {
            dts.choosen=null;
            dts.clearOtherChoices();
         }
      }
   }
   
   public void removeXPDL1Support () {
      this.xpdl1support = false;
      if (choices==null) return;
      super.removeXPDL1Support();
   }

   public void setReadOnly (boolean ro) {
      this.isReadOnly=ro;
      if (choices==null) return;
      super.setReadOnly(ro);
   }

   public void makeAs (XMLElement el) {
      if (!(el != null && el.getClass().equals(this.getClass()) && el.toName().equals(this.toName()))) {
         throw new RuntimeException("Can't perform makeAs!");
      }
      
      DataTypes other=(DataTypes)el;

      XMLElement oldChoosen=choosen;
      XMLElement newChoosen=null;
      boolean notify=false;
      if (choices==null && other.choices!=null) {
         isInitialized=true;
         fillChoices();
      } else if (choices!=null && other.choices==null) {
         oldChoosen=choosen;
         clear();
         notify=true;
         Thread.dumpStack();
      } 
      
      if (choices!=null && other.choices!=null) {      
         XMLElement othchsn=other.getChoosen();
         int chind=other.choices.indexOf(othchsn);
         newChoosen=(XMLElement)this.choices.get(chind);
      
         if (!othchsn.equals(oldChoosen)) {
            newChoosen.makeAs(othchsn);
            choosen=newChoosen;
//            clearOtherChoices();
            if (oldChoosen!=choosen) {
               notify=true;
            }
         }
      }
      if (notify) {
         XMLElementChangeInfo info=createInfo(oldChoosen, choosen, null, XMLElementChangeInfo.UPDATED);
         if (notifyListeners) {            
            notifyListeners(info);
         }
         if (notifyMainListeners) {
            notifyMainListeners(info);
         }
      }
   }
   
   public void setNotifyListeners (boolean notify) {
      this.notifyListeners=notify;
      if (choices==null) return;
      super.setNotifyListeners(notify);
   }

   public void setNotifyMainListeners (boolean notify) {
      this.notifyMainListeners=notify;
      if (choices==null) return;
      super.setNotifyMainListeners(notify);
   }

   public void initCaches (XMLInterface xmli) {
      if (choices!=null) {
         super.initCaches(xmli);
      } else {
         cachesInitialized=true;
      }
   }

   public void clearCaches () {
      if (choices!=null) {
         super.clearCaches();
      } else {
         cachesInitialized=false;
      }
   }
   
   public ArrayList getChoices () {
      if (choices==null) {
         fillChoices();
      }
      return choices;
   }
   
   public XMLElement getChoosen () {
      if (choosen==null) {
         fillChoices();
      }
      return choosen;
   }
   
   public void setChoosen (XMLElement ch) {
      if (ch==null) {
         choosen=null;
         clearOtherChoices();
      } else {
         super.setChoosen(ch);
      }
   }
   
   /** Returns BasicType choice element. */
   public BasicType getBasicType () {
      return (BasicType)getChoices().get(0);
   }

   /** Sets BasicType choice element as the chosen one. */
   public void setBasicType () {
//      choosen=(BasicType)getChoices().get(0);
//      clearOtherChoices();
      setChoosen((BasicType)getChoices().get(0));
   }
   
   /** Returns DeclaredType choice element. */
   public DeclaredType getDeclaredType () {
      return (DeclaredType)getChoices().get(1);
   }

   /** Sets DeclaredType choice element as the chosen one. */
   public void setDeclaredType () {
//      choosen=(DeclaredType)getChoices().get(1);
//      clearOtherChoices();
      setChoosen((DeclaredType)getChoices().get(1));
   }

   /** Returns SchemaType choice element. */
   public SchemaType getSchemaType () {
      return (SchemaType)getChoices().get(2);
   }

   /** Sets SchemaType choice element as the chosen one. */
   public void setSchemaType () {
//      choosen=(SchemaType)getChoices().get(2);
//      clearOtherChoices();
      setChoosen((SchemaType)getChoices().get(2));
   }

   /** Returns ExternalReference choice element. */
   public ExternalReference getExternalReference () {
      return (ExternalReference)getChoices().get(3);
   }

   /** Sets ExternalReference choice element as the chosen one. */
   public void setExternalReference () {
//      choosen=(ExternalReference)getChoices().get(3);
//      clearOtherChoices();
      setChoosen((ExternalReference)getChoices().get(3));
   }
   
   /** Returns RecordType choice element. */
   public RecordType getRecordType () {
      return (RecordType)getChoices().get(4);
   }

   /** Sets RecordType choice element as the chosen one. */
   public void setRecordType () {
//      choosen=(RecordType)getChoices().get(4);
//      clearOtherChoices();
      setChoosen((RecordType)getChoices().get(4));
   }

   /** Returns UnionType choice element. */
   public UnionType getUnionType () {
      return (UnionType)getChoices().get(5);
   }

   /** Sets UnionType choice element as the chosen one. */
   public void setUnionType () {
//      choosen=(UnionType)getChoices().get(5);
//      clearOtherChoices();
      setChoosen((UnionType)getChoices().get(5));
   }

   /** Returns EnumerationType choice element. */
   public EnumerationType getEnumerationType () {
      return (EnumerationType)getChoices().get(6);
   }

   /** Sets EnumerationType choice element as the chosen one. */
   public void setEnumerationType () {
//      choosen=(EnumerationType)getChoices().get(6);
//      clearOtherChoices();
      setChoosen((EnumerationType)getChoices().get(6));
   }
   
   /** Returns ArrayType choice element. */
   public ArrayType getArrayType () {
      return (ArrayType)getChoices().get(7);
   }

   /** Sets ArrayType choice element as the chosen one. */
   public void setArrayType () {
//      choosen=(ArrayType)getChoices().get(7);
//      clearOtherChoices();
      setChoosen((ArrayType)getChoices().get(7));
   }

   /** Returns ListType choice element. */
   public ListType getListType () {
      return (ListType)getChoices().get(8);
   }

   /** Sets ListType choice element as the chosen one. */
   public void setListType () {
//      choosen=(ListType)getChoices().get(8);
//      clearOtherChoices();
      setChoosen((ListType)getChoices().get(8));
   }
   
   public Object clone() {
      DataTypes d = null;
      if (choices!=null) {
         d = (DataTypes) super.clone();
         d.isInitialized = true;
      } else {
         d = new DataTypes((XMLComplexElement)this.parent);
         d.isReadOnly = this.isReadOnly;
      }
      return d;
   }
   
   public boolean equals (Object e) {
//System.out.println("Checking eq for DataTypes "+e+" with el "+this);
      if (this == e) {
//         System.out.println("       Elements are identical");         
         return true;
      }
      boolean equals=false;
      if (!(e == null || !(e instanceof DataTypes))) {
         DataTypes dt=(DataTypes)e;
         equals=!((choices==null && dt.choices!=null) || (choices!=null && dt.choices==null));
//         System.out.println("       DataTypes choices equal - "+equals);
         if (choices!=null) {
            equals=equals && super.equals(e);
//            System.out.println("       DataTypes choices equal by supe method - "+equals);
         }
      } else {
//         System.out.println("          DataTypes not equal because of null or different class");         
      }
      return equals;
   }
   
}

