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

package org.enhydra.jxpdl;

import org.enhydra.jxpdl.elements.Package;

/**
 * Structure representing info about the validation error of XPDL model.
 */
public final class XMLValidationError {

   /** Constant representing type of error ERROR. */
   public static final String TYPE_ERROR = "ERROR";

   /** Constant representing type of error WARNING. */
   public static final String TYPE_WARNING = "WARNING";

   /** Constant representing type of error SCHEMA. */
   public static final String SUB_TYPE_SCHEMA = "SCHEMA";

   /** Constant representing type of error CONNECTION. */
   public static final String SUB_TYPE_CONNECTION = "CONNECTION";

   /** Constant representing type of error CONFORMANCE. */
   public static final String SUB_TYPE_CONFORMANCE = "CONFORMANCE";

   /** Constant representing type of error LOGICAL. */
   public static final String SUB_TYPE_LOGIC = "LOGIC";

   /** The type of error for this object. */
   private String type;

   /** Syb-type of error for this object. */
   private String sub_type;

   /** The id of error for this object. */
   private String id;

   /** The description of error for this object. */
   private String description;

   /** XMLElement that this error object is related to. */
   private XMLElement element;

   /** Constructs new object by passing all the attributes.
    * 
    * @param type The type.
    * @param subType The sub-type.
    * @param id The Id.
    * @param desc The description.
    * @param el The element derived from {@link XMLElement}.
    */
   public XMLValidationError(String type,
                             String subType,
                             String id,
                             String desc,
                             XMLElement el) {
      this.type = type;
      this.sub_type = subType;
      this.id = id;
      this.description = desc;
      this.element = el;
   }

   /** Returns the type of error for this object.
    * 
    * @return the type of error for this object.
    */
   public String getType() {
      return type;
   }

   /** Returns the sub-type of error for this object.
    * 
    * @return the sub-type of error for this object.
    */
   public String getSubType() {
      return sub_type;
   }

   /** Returns the Id of error for this object.
    * 
    * @return the Id of error for this object.
    */
   public String getId() {
      return id;
   }

   /** Returns the Description of error for this object.
    * 
    * @return the Description of error for this object.
    */
   public String getDescription() {
      return description;
   }

   /** Returns the XMLElement this error object is related to.
    * 
    * @return the XMLElement this error object is related to.
    */
   public XMLElement getElement() {
      return element;
   }

   public String toString() {
      String retVal = "";
      if (element != null) {
         retVal += element.toName() + ": ";
         if (element instanceof Package || element instanceof XMLCollectionElement) {
            retVal += "Id=" + ((XMLComplexElement) element).get("Id").toValue();
         }
         if (element instanceof XMLComplexElement) {
            XMLElement nameEl = ((XMLComplexElement) element).get("Name");
            if (nameEl != null) {
               retVal += ", Name=" + nameEl.toValue();
            }
         }

         retVal += ", type=" + type;
         retVal += ", sub-type=" + sub_type;
         retVal += ", " + id;
         if (description != null && description.length() > 0)
            retVal += ", " + description;
      }
      return retVal;
   }

}
