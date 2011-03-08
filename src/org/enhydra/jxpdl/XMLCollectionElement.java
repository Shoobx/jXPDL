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

/**
 *  Class that represents the member of collection from XML schema
 *  that has unique Id attribute.
 * 
 *  @author Sasa Bojanic
 */
public abstract class XMLCollectionElement extends XMLComplexElement {

   /**
    * Creates a new instance of element: sets <code>name</code> to name of concrete class
    * implementation of this abstract class, and <code>parent</code> and
    * <code>isRequired</code> properties to the specified ones.
    */
   public XMLCollectionElement (XMLCollection parent, boolean isRequired) {
      super(parent, isRequired);
   }
   
   /**
    * Creates a new instance of element: sets <code>name</code> to name of concrete class
    * implementation of this abstract class, and <code>parent</code>,
    * <code>isRequired</code> and <code>xpdl1support</code> properties to the specified
    * ones.
    */
   public XMLCollectionElement (XMLCollection parent, boolean isRequired, boolean xpdl1support) {
      super(parent, isRequired, xpdl1support);
   }

   /**
    * Creates a new instance of element: sets <code>name</code>, <code>parent</code>
    * <code>isRequired</code> properties to specified ones.
    */
   public XMLCollectionElement (XMLCollection parent, String name, boolean isRequired) {
      super(parent, name, isRequired);
   }
   
   /**
    * Fills the structure of the element with an Id attribute and then calls the super method implementation.
    */
   protected void fillStructure () {
      XMLAttribute attrId=new XMLAttribute(this,"Id", true); //required      
      super.add(attrId);
   }

   /** Returns the value of the Id attribute */
   public final String getId () {
      return get("Id").toValue();
   }
   /** Sets the value of the Id attribute */
   public final void setId (String id) {
      set("Id",id);
   }

}
