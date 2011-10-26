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

package org.enhydra.jxpdl.elements;

import org.enhydra.jxpdl.XMLAttribute;
import org.enhydra.jxpdl.XMLCollectionElement;
import org.enhydra.jxpdl.XPDLConstants;

/**
 * Represents corresponding element from XPDL schema.
 */
public class Artifact extends XMLCollectionElement {

   /** Constructs a new object with the given Artifacts as a parent. */
   public Artifact(Artifacts parent) {
      super(parent, true);
   }

   protected void fillStructure() {
      XMLAttribute attrName = new XMLAttribute(this, "Name", false);
      XMLAttribute attrArtifactType = new XMLAttribute(this,
                                                       "ArtifactType",
                                                       true,
                                                       new String[] {
                                                             XPDLConstants.ARTIFACT_TYPE_DATAOBJECT,
                                                             /**
                                                              * XPDLConstants.
                                                              * ARTIFACT_TYPE_GROUP,
                                                              **/
                                                             XPDLConstants.ARTIFACT_TYPE_ANNOTATION
                                                       },
                                                       0);
      XMLAttribute attrTextAnnotation = new XMLAttribute(this, "TextAnnotation", false);

      // Group refGroup = new Group(this);
      DataObject refDataObject = new DataObject(this);

      NodeGraphicsInfos refNodeGraphicsInfos = new NodeGraphicsInfos(this); // min=0

      super.fillStructure();
      add(attrName);
      add(attrArtifactType);
      add(attrTextAnnotation);

      // add(refGroup);
      add(refDataObject);
      add(refNodeGraphicsInfos);

   }

   /** Returns the Name attribute value of this object. */
   public String getName() {
      return get("Name").toValue();
   }

   /** Sets the Name attribute value of this object. */
   public void setName(String name) {
      set("Name", name);
   }

   /** Returns the TextAnnotation attribute of this object. */
   public XMLAttribute getTextAnnotationAttribute() {
      return (XMLAttribute) get("TextAnnotation");
   }

   /** Returns the TextAnnotation attribute value of this object. */
   public String getTextAnnotation() {
      return getTextAnnotationAttribute().toValue();
   }

   /** Sets the TextAnnotation attribute value of this object. */
   public void setTextAnnotation(String text) {
      set("TextAnnotation", text);
   }

   /** Sets the ArtifactType attribute value of this object to DataObject. */
   public void setArtifactTypeDataObject() {
      getArtifactTypeAttribute().setValue(XPDLConstants.ARTIFACT_TYPE_DATAOBJECT);
   }

   // public void setArtifactTypeGroup() {
   // getArtifactTypeAttribute().setValue(XPDLConstants.ARTIFACT_TYPE_GROUP);
   // }

   /** Sets the ArtifactType attribute value of this object to Annotation. */
   public void setArtifactTypeAnnotation() {
      getArtifactTypeAttribute().setValue(XPDLConstants.ARTIFACT_TYPE_ANNOTATION);
   }

   // public Group getGroup() {
   // return (Group) get("Group");
   // }

   /** Returns the DataObject sub-element of this object. */
   public DataObject getDataObject() {
      return (DataObject) get("DataObject");
   }

   /** Returns the NodeGraphicsInfos sub-element of this object. */
   public NodeGraphicsInfos getNodeGraphicsInfos() {
      return (NodeGraphicsInfos) get("NodeGraphicsInfos");
   }

   /** Returns the ArtifactType attribute of this object. */
   public XMLAttribute getArtifactTypeAttribute() {
      return (XMLAttribute) get("ArtifactType");
   }

   /** Returns the ArtifactType attribute value of this object. */
   public String getArtifactType() {
      return getArtifactTypeAttribute().toValue();
   }
}
