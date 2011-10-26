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

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Class that serves as an ErrorHandler for DOM parser.
 * 
 * @author Sasa Bojanic
 */
public class ParsingErrors implements ErrorHandler {

   /** Constant representing Error notification message. */
   public static String ERROR = "[Error]";

   /** Constant representing Warning notification message. */
   public static String WARNING = "[Warning]";

   /** Constant representing Fatal Error notification message. */
   public static String FATAL_ERROR = "[Fatal Error]";

   /** Constant representing at line number notification message. */
   public static String AT_LINE_NO_STRING = " at line number ";

   /** The list of error messages. */
   List errorMessages = new ArrayList();

   /** Creates a new object. */
   public ParsingErrors() {
      super();
   }

   public void warning(SAXParseException ex) {
      store(ex, WARNING);
   }

   public void error(SAXParseException ex) {
      store(ex, ERROR);
   }

   public void fatalError(SAXParseException ex) throws SAXException {
      store(ex, FATAL_ERROR);
   }

   /**
    * Returns a list of error messages.
    * 
    * @return A list of error messages.
    */
   public List getErrorMessages() {
      return errorMessages;
   }

   public void clearErrors() {
      errorMessages.clear();
   }

   void store(SAXParseException ex, String type) {
      // build error text
      String errorString = type
                           + AT_LINE_NO_STRING + ex.getLineNumber() + ": "
                           + ex.getMessage() + "\n";
      errorMessages.add(errorString);
   }
}
