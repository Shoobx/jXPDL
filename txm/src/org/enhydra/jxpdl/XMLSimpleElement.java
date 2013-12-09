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
 *  Class that represents simple element from XML schema.
 *
 *  @author Sasa Bojanic
 */
public abstract class XMLSimpleElement extends XMLElement {

   public XMLSimpleElement (XMLElement parent, boolean isRequired) {
      super(parent, isRequired);
   }

   public XMLSimpleElement (XMLElement parent, String name, boolean isRequired) {
      super(parent, name, isRequired);
   }
}
