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

import org.enhydra.jxpdl.XMLComplexElement;

/**
 *  Represents coresponding element from XPDL schema.
 * 
 *  @author Sasa Bojanic
 */
public class PackageHeader extends XMLComplexElement {

   public PackageHeader (Package parent) {
      super(parent, false);
   }

   protected void fillStructure () {
      XPDLVersion refXPDLVersion=new XPDLVersion(this);
      Vendor refVendor=new Vendor(this);
      Created refCreated=new Created(this);
      Description refDescription=new Description(this); // min=0
      Documentation refDocumentation=new Documentation(this); // min=0
      PriorityUnit refPriorityUnit=new PriorityUnit(this); // min=0
      CostUnit refCostUnit=new CostUnit(this); // min=0

      add(refXPDLVersion);
      add(refVendor);
      add(refCreated);
      add(refDescription);
      add(refDocumentation);
      add(refPriorityUnit);
      add(refCostUnit);
   }

   public String getCostUnit() {
      return get("CostUnit").toValue();
   }
   public void setCostUnit(String costUnit) {
      set("CostUnit",costUnit);
   }
   public String getCreated() {
      return get("Created").toValue();
   }
   public void setCreated(String created) {
      set("Created",created);
   }
   public String getDescription() {
      return get("Description").toValue();
   }
   public void setDescription(String description) {
      set("Description",description);
   }
   public String getDocumentation() {
      return get("Documentation").toValue();
   }
   public void setDocumentation(String documentation) {
      set("Documentation",documentation);
   }
   public String getPriorityUnit() {
      return get("PriorityUnit").toValue();
   }
   public void setPriorityUnit(String priorityUnit) {
      set("PriorityUnit",priorityUnit);
   }
   public String getVendor() {
      return get("Vendor").toValue();
   }
   public void setVendor(String vendor) {
      set("Vendor",vendor);
   }
   public String getXPDLVersion() {
      return get("XPDLVersion").toValue();
   }
   public void setXPDLVersion(String xpdlVersion) {
      set("XPDLVersion",xpdlVersion);
   }
}
