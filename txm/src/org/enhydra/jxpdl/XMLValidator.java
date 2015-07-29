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

import java.util.List;
import java.util.Properties;

/**
 * Interface for validating XML model.
 * 
 * @author Sasa Bojanic
 */
public interface XMLValidator {

   /**
    * Initializes validator with given properties.
    * 
    * @param pProps Validator settings.
    */
   void init(Properties pProps);

   /**
    * Validates given XMLElement. If there is a validation error, it is added to the
    * existingErrors list. If flag fullCheck is false, the validation stops when the first
    * error is found.
    * 
    * @param el Element derived from {@link XMLElement}.
    * @param existingErrors The list of {@link XMLValidationError} elements.
    * @param fullCheck if false, validation stops when the first error is found.
    */
   void validateElement(XMLElement el, List existingErrors, boolean fullCheck);

}
