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

/**
 * This interface should be implemented by the class tend to be a listener for XPDL model
 * changes.
 * 
 * @author Sasa Bojanic
 */
public interface XMLElementChangeListener {

   /**
    * Notifies the listener about the XML element changes.
    * 
    * @param info The change info.
    */
   void xmlElementChanged(XMLElementChangeInfo info);

}
