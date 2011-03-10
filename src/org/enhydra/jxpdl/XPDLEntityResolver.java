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

import java.io.InputStream;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * Replaces the internet location of the XPDL1/XPDL2 schema, with its content read from
 * resources.
 * 
 * @author Sasa Bojanic
 */
public class XPDLEntityResolver implements EntityResolver {

   /** XPDL 1 schema file name. */
   public static final String XPDL1_XSD = "TC-1025_schema_10_xpdl.xsd";

   /** XPDL 2 schema file name. */
   public static final String XPDL2_XSD = "bpmnxpdl_31.xsd";

   /** XPDL 1 schema resource file full location. */
   public static final String XPDL1_SCHEMA = "org/enhydra/jxpdl/resources/" + XPDL1_XSD;

   /** XPDL 2 schema resource file full location. */
   public static final String XPDL2_SCHEMA = "org/enhydra/jxpdl/resources/" + XPDL2_XSD;

   public InputSource resolveEntity(String publicId, String systemId) {
      // System.out.println("pId="+publicId+", sId="+systemId);
      if (systemId != null) {
         String xpdlSchema = XPDL2_SCHEMA;
         if (systemId.endsWith(XPDL1_XSD)) {
            xpdlSchema = XPDL1_SCHEMA;
         }
         return getSchemaInputSource(xpdlSchema);
      }

      // use the default behaviour
      return null;
   }

   /** Returns the XPDL schema determined by given resource location. */
   public static InputSource getSchemaInputSource(String xpdlSchema) {
      InputStream is = null;
      try {
         java.net.URL u = XPDLEntityResolver.class.getClassLoader()
            .getResource(xpdlSchema);
         is = (InputStream) u.getContent();
         return new InputSource(is);
      } catch (Exception ex) {
         return null;
      }
   }
}
