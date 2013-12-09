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

import org.enhydra.jxpdl.elements.Package;


/**
 * @author Sasa Bojanic
 *
 */
public final class XMLValidationError {

   public static final String TYPE_ERROR="ERROR";
   public static final String TYPE_WARNING="WARNING";
   
   public static final String SUB_TYPE_SCHEMA="SCHEMA";
   public static final String SUB_TYPE_CONNECTION="CONNECTION";
   public static final String SUB_TYPE_CONFORMANCE="CONFORMANCE";
   public static final String SUB_TYPE_LOGIC="LOGIC";
   
   private String type;   
   private String sub_type;
   private String id;
   private String description;
   private XMLElement element;
   
   public XMLValidationError (String type,String subType,String id,String desc,XMLElement el) {
      this.type=type;
      this.sub_type=subType;
      this.id=id;
      this.description=desc;
      this.element=el;
   }
   
   public String getType () {
      return type;      
   }

   public String getSubType () {
      return sub_type;      
   }
   
   public String getId () {
      return id;      
   }

   public String getDescription () {
      return description;      
   }

   public XMLElement getElement () {
      return element;      
   }
   
   public String toString () {
      String retVal=""; 
      if (element != null) {
          retVal += element.toName()+": ";        
          if (element instanceof Package || element instanceof XMLCollectionElement) {
             retVal += "Id="+((XMLComplexElement)element).get("Id").toValue();              
          }
          if (element instanceof XMLComplexElement) {
             XMLElement nameEl=((XMLComplexElement)element).get("Name");
             if (nameEl!=null) {
                retVal+=", Name="+nameEl.toValue();
             }
          }
           
          retVal+=", type="+type;
          retVal+=", sub-type="+sub_type;
          retVal+=", "+id;
          if (description!=null && description.length()>0) retVal+=", "+description;
       }
       return retVal;      
   }
   
}