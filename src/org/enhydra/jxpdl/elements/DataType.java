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

import java.util.HashMap;
import java.util.Map;

import org.enhydra.jxpdl.XMLComplexElement;
import org.enhydra.jxpdl.XMLElement;
import org.enhydra.jxpdl.XMLInterface;
import org.enhydra.jxpdl.XMLUtil;

/**
 *  Represents coresponding element from XPDL schema.
 *
 *  @author Sasa Bojanic
 */
public class DataType extends XMLComplexElement {

   protected transient String javaType;
   
   public DataType (XMLComplexElement parent) {
      super(parent, true);
   }

   protected void fillStructure () {
      DataTypes refDataTypes=new DataTypes(this);
      refDataTypes.getChoices();
      add(refDataTypes);
   }

   public void initCaches (XMLInterface xmli) {
      super.initCaches(xmli);
      javaType=XMLUtil.getChoosenType(xmli, getDataTypes(), XMLUtil.getPackage(this));
   }
   
   public void clearCaches () {
      javaType=null;
      super.clearCaches();
   }

   public String getJavaType () {
      if (!isReadOnly) {
         throw new RuntimeException("This method can be used only in read-only mode!");
      }      
      return javaType;
   }
   
   public DataTypes getDataTypes() {
      return (DataTypes)get("DataTypes");
   }      
   
}