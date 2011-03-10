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

import org.enhydra.jxpdl.XMLAttribute;
import org.enhydra.jxpdl.XMLComplexElement;
import org.enhydra.jxpdl.XPDLConstants;

/**
 * Represents corresponding element from XPDL schema.
 * 
 * @author Sasa Bojanic
 */
public class ConformanceClass extends XMLComplexElement {

   /**
    * Constructs a new object with the given Package as a parent.
    */
   public ConformanceClass(Package parent) {
      super(parent, false);
   }

   protected void fillStructure() {
      XMLAttribute attrGraphConformance = new XMLAttribute(this,
                                                           "GraphConformance",
                                                           false,
                                                           new String[] {
                                                                 XPDLConstants.GRAPH_CONFORMANCE_NONE,
                                                                 XPDLConstants.GRAPH_CONFORMANCE_FULL_BLOCKED,
                                                                 XPDLConstants.GRAPH_CONFORMANCE_LOOP_BLOCKED,
                                                                 XPDLConstants.GRAPH_CONFORMANCE_NON_BLOCKED
                                                           },
                                                           0);

      add(attrGraphConformance);
   }

   /** Returns the GraphConformance attribute of this object. */
   public XMLAttribute getGraphConformanceAttribute() {
      return (XMLAttribute) get("GraphConformance");
   }

   /** Returns the GraphConformance attribute value of this object. */
   public String getGraphConformance() {
      return getGraphConformanceAttribute().toValue();
   }

   /** Sets the GraphConformance attribute value of this object to an empty string. */
   public void setGraphConformanceNONE() {
      getGraphConformanceAttribute().setValue(XPDLConstants.GRAPH_CONFORMANCE_NONE);
   }

   /** Sets the GraphConformance attribute value of this object to FULL_BLOCKED. */
   public void setGraphConformanceFULL_BLOCKED() {
      getGraphConformanceAttribute().setValue(XPDLConstants.GRAPH_CONFORMANCE_FULL_BLOCKED);
   }

   /** Sets the GraphConformance attribute value of this object to LOOP_BLOCKED. */
   public void setGraphConformanceLOOP_BLOCKED() {
      getGraphConformanceAttribute().setValue(XPDLConstants.GRAPH_CONFORMANCE_LOOP_BLOCKED);
   }

   /** Sets the GraphConformance attribute value of this object to NON_BLOCKED. */
   public void setGraphConformanceNON_BLOCKED() {
      getGraphConformanceAttribute().setValue(XPDLConstants.GRAPH_CONFORMANCE_NON_BLOCKED);
   }

}
