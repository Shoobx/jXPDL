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

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.parsers.DOMParser;
import org.enhydra.jxpdl.elements.Activities;
import org.enhydra.jxpdl.elements.Activity;
import org.enhydra.jxpdl.elements.ActivitySet;
import org.enhydra.jxpdl.elements.ActivitySets;
import org.enhydra.jxpdl.elements.ActualParameter;
import org.enhydra.jxpdl.elements.ActualParameters;
import org.enhydra.jxpdl.elements.Application;
import org.enhydra.jxpdl.elements.Artifact;
import org.enhydra.jxpdl.elements.Association;
import org.enhydra.jxpdl.elements.Associations;
import org.enhydra.jxpdl.elements.BasicType;
import org.enhydra.jxpdl.elements.BlockActivity;
import org.enhydra.jxpdl.elements.Condition;
import org.enhydra.jxpdl.elements.DataField;
import org.enhydra.jxpdl.elements.DataType;
import org.enhydra.jxpdl.elements.DataTypes;
import org.enhydra.jxpdl.elements.Deadline;
import org.enhydra.jxpdl.elements.DeadlineDuration;
import org.enhydra.jxpdl.elements.Deadlines;
import org.enhydra.jxpdl.elements.DeclaredType;
import org.enhydra.jxpdl.elements.EnumerationType;
import org.enhydra.jxpdl.elements.EnumerationValue;
import org.enhydra.jxpdl.elements.ExceptionName;
import org.enhydra.jxpdl.elements.ExpressionType;
import org.enhydra.jxpdl.elements.ExternalPackage;
import org.enhydra.jxpdl.elements.FormalParameter;
import org.enhydra.jxpdl.elements.FormalParameters;
import org.enhydra.jxpdl.elements.Join;
import org.enhydra.jxpdl.elements.Package;
import org.enhydra.jxpdl.elements.Participant;
import org.enhydra.jxpdl.elements.Performer;
import org.enhydra.jxpdl.elements.Priority;
import org.enhydra.jxpdl.elements.Responsible;
import org.enhydra.jxpdl.elements.Split;
import org.enhydra.jxpdl.elements.SubFlow;
import org.enhydra.jxpdl.elements.TaskApplication;
import org.enhydra.jxpdl.elements.Tool;
import org.enhydra.jxpdl.elements.Transition;
import org.enhydra.jxpdl.elements.TransitionRef;
import org.enhydra.jxpdl.elements.TransitionRefs;
import org.enhydra.jxpdl.elements.Transitions;
import org.enhydra.jxpdl.elements.TypeDeclaration;
import org.enhydra.jxpdl.elements.WorkflowProcess;
import org.enhydra.jxpdl.elements.WorkflowProcesses;
import org.enhydra.jxpdl.elements.XPDLVersion;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Standard implementation of XMLValidator interface for validating XPDL model.
 * 
 * @author Sasa Bojanic
 */
public class StandardPackageValidator implements XMLValidator {

   /**
    * Constant for the property setting name that defines if sub-flow references will be
    * validated.
    */
   public static final String VALIDATE_SUBFLOW_REFERENCES = "ValidateSubFlowReferences";

   /**
    * Constant for the property setting name that defines if performer expressions will be
    * validated.
    */
   public static final String VALIDATE_PERFORMER_EXPRESSIONS = "ValidatePerformerExpressions";

   /**
    * Constant for the property setting name that defines if actual parameter expressions
    * will be validated.
    */
   public static final String VALIDATE_ACTUAL_PARAMETER_EXPRESSIONS = "ValidateActualParameterExpressions";

   /**
    * Constant for the property setting name that defines if deadline expressions will be
    * validated.
    */
   public static final String VALIDATE_DEADLINE_EXPRESSIONS = "ValidateDeadlineExpressions";

   /**
    * Constant for the property setting name that defines if transition condition
    * expressions will be validated.
    */
   public static final String VALIDATE_CONDITION_EXPRESSIONS = "ValidateConditionExpressions";

   /**
    * Constant for the property setting name that defines if unused variables will be
    * validated.
    */
   public static final String VALIDATE_UNUSED_VARIABLES = "ValidateUnusedVariables";

   /**
    * Constant for the property setting name that defines if (transition) condition will
    * be validated by type.
    */
   public static final String VALIDATE_CONDITION_BY_TYPE = "ValidateConditionByType";

   /**
    * Constant for the property setting name that defines if existing schema validation
    * errors will be taken into account during schema validation.
    */
   public static final String GET_EXISTING_SCHEMA_VALIDATION_ERRORS = "GetExistingSchemaValidationErrors";

   /**
    * Constant for the property setting name that defines if external packages will also
    * be validated.
    */
   public static final String CHECK_EXTERNAL_PACKAGES = "CheckExternalPackages";

   /**
    * Constant for the property setting name that defines if validation allows undefined
    * start in the process graph.
    */
   public static final String ALLOW_UNDEFINED_START = "AllowUndefinedStart";

   /**
    * Constant for the property setting name that defines if validation allows undefined
    * end in the process graph.
    */
   public static final String ALLOW_UNDEFINED_END = "AllowUndefinedEnd";

   /**
    * Constant for the property setting name that defines the Encoding.
    */
   public static final String ENCODING = "Encoding";

   /**
    * Constant for the property setting name that defines the Locale.
    */
   public static final String LOCALE = "Locale";

   /**
    * Constant that defines a current XPDL version.
    */
   protected static final String CURRENT_XPDL_VERSION = "2.1";

   /**
    * Properties for the validator.
    */
   protected Properties properties;

   /**
    * Instance of XMLInterface for handling XPDL main Package model objects.
    */
   protected XMLInterface xmlInterface;

   /**
    * Map of external package's validation errors.
    */
   protected Map epsValidationErrors = new HashMap();

   /**
    * Map of schema validation errors.
    */
   protected Map schemaValidationErrors = new HashMap();

   /** Settings for the validator. */
   protected Properties settings;

   /**
    * Default constructor. When used, init() method should be called afterwards to
    * initialize validation settings.
    */
   public StandardPackageValidator() {
   }

   /**
    * Constructor with validation settings.
    * 
    * @param settings Validation settings.
    */
   public StandardPackageValidator(Properties settings) {
      this.settings = settings;
   }

   /**
    * Performs initialization of the validator.
    * 
    * @param pXmlInterface Instance of XMLInterface that holds information about all the
    *           relevant Packages which provides ability to check external packages'
    *           content during validation.
    * @param pPkg Package which validation is required.
    * @param pGetExistingSchemaValidationErrors Existing schema validation errors.
    * @param pEncoding Encoding used during parsing of XPDL.
    * @param pLocale Locale used during parsing of XPDL.
    */
   public void init(XMLInterface pXmlInterface,
                    Package pPkg,
                    boolean pGetExistingSchemaValidationErrors,
                    String pEncoding,
                    String pLocale) {
      Properties tempProperties = new Properties();
      tempProperties.putAll(settings);
      tempProperties.put(StandardPackageValidator.GET_EXISTING_SCHEMA_VALIDATION_ERRORS,
                         String.valueOf(pGetExistingSchemaValidationErrors));
      tempProperties.put(StandardPackageValidator.CHECK_EXTERNAL_PACKAGES, "true");
      tempProperties.put(StandardPackageValidator.ENCODING, pEncoding);
      tempProperties.put(StandardPackageValidator.LOCALE, pLocale);
      init(tempProperties, pXmlInterface);
   }

   /**
    * Clears the cache of external package and schema validation errors for the given
    * Package.
    * 
    * @param pkg The package which cache entry will be cleared.
    */
   public void clearCache(Package pkg) {
      epsValidationErrors.remove(pkg);
      schemaValidationErrors.remove(pkg);
   }

   /** Clears the whole external package and schema validation errors cache. */
   public void clearCache() {
      epsValidationErrors.clear();
      schemaValidationErrors.clear();
      xmlInterface = null;
   }

   /**
    * Returns external package validation errors map.
    * 
    * @return The external package validation errors map.
    */
   public Map getExtPkgValidationErrors() {
      return epsValidationErrors;
   }

   /**
    * Initializes validator settings.
    * 
    * @param props The validator settings.
    */
   public void init(Properties props) {
      this.properties = props;
      if (props == null) {
         clearCache();
      }
   }

   /**
    * Initializes validator settings.
    * 
    * @param pProps Validator settings.
    * @param pXmlInterface Instance of XMLInterface that holds information about all the
    *           relevant Packages which provides ability to check external packages'
    *           content during validation.
    */
   public void init(Properties pProps, XMLInterface pXmlInterface) {
      init(pProps);
      this.xmlInterface = pXmlInterface;
   }

   /**
    * Validates any XMLElement by calling the existing method for that particular element
    * type if it exists. First it tries to call the method with concrete class parameter
    * of given element, if not found, it calls the method for the abstract superclass of
    * an element (e.g. XMLAttribute, XMLComplexElement, XMLCollection, ...). If validation
    * error is found it will be added into the existingErrors list. If fullCheck is false,
    * the validation stops as soon the first error has been found. The implementation of
    * the methods is recursive, e.g. when validating XMLCollection, each element from the
    * collection is being validated as well by again calling this method with this element
    * as a parameter, and similar for the XMLComplexElement where this method is called
    * for each sub-element. Thus, calling this validation method for the main XPDL model
    * object-Package, the whole model gets validated.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(XMLElement el, List existingErrors, boolean fullCheck) {
      if (!fullCheck && existingErrors.size() > 0) {
         return;
      }
      if (el.isEmpty() && !(el instanceof XMLCollection) && !el.isRequired()) {
         return;
      }

      try {
         Class cl = el.getClass();
         Method m = null;
         try {
            m = this.getClass().getMethod("validateElement", new Class[] {
                  cl, List.class, boolean.class
            });
         } catch (Exception ex) {
            if (!(cl == XMLSimpleElement.class
                  || cl == XMLAttribute.class || cl == XMLComplexChoice.class
                  || cl == XMLComplexElement.class || cl == XMLCollectionElement.class || cl == XMLCollection.class)) {
               if (XMLComplexChoice.class.isAssignableFrom(cl)) {
                  cl = XMLComplexChoice.class;
               } else if (XMLAttribute.class.isAssignableFrom(cl)) {
                  cl = XMLAttribute.class;
               } else if (XMLSimpleElement.class.isAssignableFrom(cl)) {
                  cl = XMLSimpleElement.class;
               } else if (ExpressionType.class.isAssignableFrom(cl)) {
                  cl = ExpressionType.class;
               } else if (XMLComplexElement.class.isAssignableFrom(cl)) {
                  cl = XMLComplexElement.class;
               } else if (XMLCollection.class.isAssignableFrom(cl)) {
                  cl = XMLCollection.class;
               }
            }
         }

         m = this.getClass().getMethod("validateElement", new Class[] {
               cl, List.class, boolean.class
         });
         // System.err.println("calling "+m.toString()+",
         // el=["+el.toName()+","+el.toValue()+"]");
         m.invoke(this, new Object[] {
               el, existingErrors, new Boolean(fullCheck)
         });
         return;
      } catch (Throwable e) {
         e.printStackTrace();
      }

      validateStandard(el, existingErrors, fullCheck);
   }

   /**
    * Validates XMLAttribute elements. E.g. for Id attributes it checks uniqueness.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(XMLAttribute el, List existingErrors, boolean fullCheck) {
      XMLElement parent = el.getParent();

      boolean isValid = true;
      if (el.toName().equals("Id")) {
         if (parent instanceof SubFlow) {
            checkSubFlowId(el, existingErrors, fullCheck);
         } else if (parent instanceof TaskApplication) {
            checkToolId(el, existingErrors, fullCheck);
         } else if (parent instanceof TransitionRef) {
            checkTransitionRefId(el, existingErrors, fullCheck);
         } else if (parent instanceof DeclaredType) {
            checkDeclaredTypeId(el, existingErrors);
         } else {
            if (!isIdValid(el.toValue())) {
               isValid = false;
               XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                                XMLValidationError.SUB_TYPE_LOGIC,
                                                                XPDLValidationErrorIds.ERROR_INVALID_ID,
                                                                el.toValue(),
                                                                el);
               existingErrors.add(verr);
            }
            if ((parent instanceof XMLCollectionElement) && (fullCheck || isValid)) {
               if (!isIdUnique((XMLCollectionElement) parent)) {
                  isValid = false;
                  XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                                   XMLValidationError.SUB_TYPE_LOGIC,
                                                                   XPDLValidationErrorIds.ERROR_NON_UNIQUE_ID,
                                                                   el.toValue(),
                                                                   el);
                  existingErrors.add(verr);
               }
            }
         }
      } else if (el.toName().equals("href")) {
         if (parent instanceof ExternalPackage) {
            String val = el.toValue();
            Package pkg = XMLUtil.getPackage(el);
            String epId = pkg.getExternalPackageId(val);
            if (epId == null || epId.equals("")) {
               XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                                XMLValidationError.SUB_TYPE_LOGIC,
                                                                XPDLValidationErrorIds.ERROR_NON_EXISTING_EXTERNAL_PACKAGE_REFERENCE,
                                                                val,
                                                                el);
               existingErrors.add(verr);
            }
         }
      } else if (parent instanceof Transition) {
         if (el.toName().equals("From")) {
            checkTransitionFrom(el, existingErrors);
         } else if (el.toName().equals("To")) {
            checkTransitionTo(el, existingErrors);
         }
      } else if (parent instanceof Association) {
         if (el.toName().equals("Source")) {
            checkAssociationSource(el, existingErrors);
         } else if (el.toName().equals("Target")) {
            checkAssociationTarget(el, existingErrors);
         }
      } else if (parent instanceof BlockActivity) {
         checkBlockId(el, existingErrors);
      }

      if (!fullCheck && existingErrors.size() > 0) {
         return;
      }
      if (!isElementLengthOK(el)) {
         XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                          XMLValidationError.SUB_TYPE_LOGIC,
                                                          XPDLValidationErrorIds.ERROR_UNALLOWED_LENGTH,
                                                          el.toName(),
                                                          el);
         existingErrors.add(verr);         
      }
   }

   /**
    * Validates XMLComplexChoice elements by actually validating the 'chosen' element.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(XMLComplexChoice el, List existingErrors, boolean fullCheck) {
      validateElement(el.getChoosen(), existingErrors, fullCheck);
   }

   /**
    * Validates XMLEmptyChoiceElement elements. This implementation does nothing.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(XMLEmptyChoiceElement el,
                               List existingErrors,
                               boolean fullCheck) {
   }

   /**
    * Validates XMLCollection elements by validating each of their members.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(XMLCollection el, List existingErrors, boolean fullCheck) {
      for (Iterator it = el.toElements().iterator(); it.hasNext();) {
         XMLElement cel = (XMLElement) it.next();
         validateElement(cel, existingErrors, fullCheck);
      }
   }

   /**
    * Validates XMLCollectionElement elements by actually calling validation method for
    * validating a super-class (XMLComplexElement).
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(XMLCollectionElement el,
                               List existingErrors,
                               boolean fullCheck) {
      validateElement((XMLComplexElement) el, existingErrors, fullCheck);
   }

   /**
    * Validates XMLComplexElement elements by validating each of sub-elements.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(XMLComplexElement el,
                               List existingErrors,
                               boolean fullCheck) {
      for (Iterator it = el.toElements().iterator(); it.hasNext();) {
         XMLElement cel = (XMLElement) it.next();
         validateElement(cel, existingErrors, fullCheck);
      }
   }

   /**
    * Validates XMLSimpleElement elements. This implementation does nothing.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(XMLSimpleElement el, List existingErrors, boolean fullCheck) {
      if (!isElementLengthOK(el)) {
         XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                          XMLValidationError.SUB_TYPE_LOGIC,
                                                          XPDLValidationErrorIds.ERROR_UNALLOWED_LENGTH,
                                                          el.toName(),
                                                          el);
         existingErrors.add(verr);         
      }      
   }

   /**
    * Performs standard validation by calling appropriate method for the particular super
    * element depending on its type. E.g. if given element is XMLAttribute instance, the
    * method 'validateElement' with XMLAttribute as a first parameter is called.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   protected void validateStandard(XMLElement el, List existingErrors, boolean fullCheck) {
      if (el instanceof XMLAttribute) {
         validateElement((XMLAttribute) el, existingErrors, fullCheck);
      } else if (el instanceof XMLSimpleElement) {
         validateElement((XMLSimpleElement) el, existingErrors, fullCheck);
      } else if (el instanceof XMLCollectionElement) {
         validateElement((XMLCollectionElement) el, existingErrors, fullCheck);
      } else if (el instanceof XMLComplexElement) {
         validateElement((XMLComplexElement) el, existingErrors, fullCheck);
      } else if (el instanceof XMLComplexChoice) {
         validateElement((XMLComplexChoice) el, existingErrors, fullCheck);
      } else if (el instanceof XMLCollection) {
         validateElement((XMLCollection) el, existingErrors, fullCheck);
      }
   }

   /**
    * Validates Activity element.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(Activity el, List existingErrors, boolean fullCheck) {
      validateStandard(el, existingErrors, fullCheck);

      boolean isValid = existingErrors.size() == 0;

      if (!(isValid || fullCheck)) {
         return;
      }

      Set ets = XMLUtil.getExceptionalOutgoingTransitions(el);
      if (el.getDeadlines().size() > 0) {
         if (ets.size() == 0) {

            XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                             XMLValidationError.SUB_TYPE_LOGIC,
                                                             XPDLValidationErrorIds.ERROR_DEADLINES_NOT_PROPERLY_HANDLED_NO_EXCEPTIONAL_TRANSITIONS,
                                                             "",
                                                             el);
            existingErrors.add(verr);
            isValid = false;
         }
      }

      if (!(isValid || fullCheck)) {
         return;
      }

      Set outTrans = XMLUtil.getOutgoingTransitions(el);
      Set inTrans = XMLUtil.getIncomingTransitions(el);

      if (el.getActivityType() == XPDLConstants.ACTIVITY_TYPE_EVENT_START
          || el.getActivityType() == XPDLConstants.ACTIVITY_TYPE_EVENT_END) {
         if (el.getActivityType() == XPDLConstants.ACTIVITY_TYPE_EVENT_START) {
            if (inTrans.size() > 0) {
               XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                                XMLValidationError.SUB_TYPE_CONNECTION,
                                                                XPDLValidationErrorIds.ERROR_CONNECTION_TO_START_EVENT_NOT_ALLOWED,
                                                                "",
                                                                el);
               existingErrors.add(verr);
               isValid = false;
               if (!(isValid || fullCheck)) {
                  return;
               }
            }
            if (outTrans.size() == 0) {
               XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                                XMLValidationError.SUB_TYPE_CONNECTION,
                                                                XPDLValidationErrorIds.ERROR_START_EVENT_NOT_CONNECTED,
                                                                "",
                                                                el);
               existingErrors.add(verr);
               isValid = false;
               if (!(isValid || fullCheck)) {
                  return;
               }
            }
         }
         if (el.getActivityType() == XPDLConstants.ACTIVITY_TYPE_EVENT_END) {
            if (outTrans.size() > 0) {
               XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                                XMLValidationError.SUB_TYPE_CONNECTION,
                                                                XPDLValidationErrorIds.ERROR_CONNECTION_FROM_END_EVENT_NOT_ALLOWED,
                                                                "",
                                                                el);
               existingErrors.add(verr);
               isValid = false;
               if (!(isValid || fullCheck)) {
                  return;
               }
            }
            if (inTrans.size() == 0) {
               XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                                XMLValidationError.SUB_TYPE_CONNECTION,
                                                                XPDLValidationErrorIds.ERROR_END_EVENT_NOT_CONNECTED,
                                                                "",
                                                                el);
               existingErrors.add(verr);
               isValid = false;
               if (!(isValid || fullCheck)) {
                  return;
               }
            }
         }
      } else {
         if (inTrans.size() == 0) {
            XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_WARNING,
                                                             XMLValidationError.SUB_TYPE_CONNECTION,
                                                             XPDLValidationErrorIds.WARNING_CONNECTION_FROM_START_EVENT_IS_MISSING,
                                                             "",
                                                             el);
            existingErrors.add(verr);
            isValid = false;
            if (!(isValid || fullCheck)) {
               return;
            }
         }
         if (outTrans.size() == 0) {
            XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_WARNING,
                                                             XMLValidationError.SUB_TYPE_CONNECTION,
                                                             XPDLValidationErrorIds.WARNING_CONNECTION_TO_END_EVENT_IS_MISSING,
                                                             "",
                                                             el);
            existingErrors.add(verr);
            isValid = false;
            if (!(isValid || fullCheck)) {
               return;
            }
         }

      }

      // Split type and no. of outgoing transitions
      Split split = XMLUtil.getSplit(el);
      if ((split == null || split.getType().length() == 0)
          && (outTrans.size() - ets.size()) > 1) {
         isValid = false;
         XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                          XMLValidationError.SUB_TYPE_LOGIC,
                                                          XPDLValidationErrorIds.ERROR_MULTIPLE_OUTGOING_TRANSITIONS_WITHOUT_SPLIT_TYPE_DEFINED,
                                                          "",
                                                          el);
         existingErrors.add(verr);
      }

      if (!(isValid || fullCheck)) {
         return;
      }

      // Join type and no. of incoming transitions
      Join join = XMLUtil.getJoin(el);
      if ((join == null || join.getType().length() == 0) && inTrans.size() > 1) {
         isValid = false;
         XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                          XMLValidationError.SUB_TYPE_LOGIC,
                                                          XPDLValidationErrorIds.ERROR_MULTIPLE_INCOMING_TRANSITIONS_WITHOUT_JOIN_TYPE_DEFINED,
                                                          "",
                                                          el);
         existingErrors.add(verr);
      }

      if (!(fullCheck || isValid)) {
         return;
      }

      checkMultipleOtherwiseOrDefaultExceptionTransitions(el,
                                                          outTrans,
                                                          existingErrors,
                                                          fullCheck);

   }

   /**
    * Validates ActivitySet element.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(ActivitySet el, List existingErrors, boolean fullCheck) {
      validateStandard(el, existingErrors, fullCheck);
      boolean isValid = true;
      if (existingErrors.size() == 0 || fullCheck) {
         if (el.getActivities().toElements().size() == 0) {
            isValid = false;
            XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                             XMLValidationError.SUB_TYPE_LOGIC,
                                                             XPDLValidationErrorIds.ERROR_ACTIVITY_SET_NOT_DEFINED,
                                                             "",
                                                             el);
            existingErrors.add(verr);
         }
      }
      if (isValid || fullCheck) {
         isValid = checkGraphConnectionsForWpOrAs(el, existingErrors, fullCheck)
                   || fullCheck;
      }
      if (isValid || fullCheck) {
         isValid = checkGraphConformanceForWpOrAs(el, existingErrors, fullCheck)
                   || fullCheck;
      }
   }

   /**
    * Validates Associations element.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(Associations el, List existingErrors, boolean fullCheck) {
      validateStandard(el, existingErrors, fullCheck);
      if (fullCheck || existingErrors.size() == 0) {
         Map actConns = new HashMap();
         Set multipleConnections = new HashSet();
         for (int i = 0; i < el.size(); i++) {
            Association t = (Association) el.get(i);
            String actConn = "[" + t.getSource() + "-" + t.getTarget() + "]";
            if (actConns.containsKey(actConn)) {
               multipleConnections.add(actConns.get(actConn));
               multipleConnections.add(t);

               if (!fullCheck) {
                  break;
               }

            }
            actConns.put(actConn, t);
         }
         if (multipleConnections.size() > 0) {
            Iterator it = multipleConnections.iterator();
            while (it.hasNext()) {
               Association t = (Association) it.next();
               String actConn = "[" + t.getSource() + "-" + t.getTarget() + "]";
               XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                                XMLValidationError.SUB_TYPE_LOGIC,
                                                                XPDLValidationErrorIds.ERROR_MULTIPLE_CONNECTIONS,
                                                                actConn,
                                                                t);
               existingErrors.add(verr);
            }
         }

      }
   }

   /**
    * Validates Condition element.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(Condition el, List existingErrors, boolean fullCheck) {
      validateStandard(el, existingErrors, fullCheck);
      String condType = el.getType();
      String condExpr = el.toValue();
      if (existingErrors.size() > 0 && !fullCheck) {
         return;
      }

      boolean validateCondByType = properties.getProperty(StandardPackageValidator.VALIDATE_CONDITION_BY_TYPE,
                                                          "false")
         .equals("true");
      if (condType.equals(XPDLConstants.CONDITION_TYPE_DEFAULTEXCEPTION)) {
         if (validateCondByType && condExpr.length() > 0) {
            XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_WARNING,
                                                             XMLValidationError.SUB_TYPE_LOGIC,
                                                             XPDLValidationErrorIds.WARNING_DEFAULT_EXCEPTION_TRANSITION_WITH_EXPRESSION,
                                                             condExpr,
                                                             el);
            existingErrors.add(verr);
         }
         return;

      } else if (condType.equals(XPDLConstants.CONDITION_TYPE_OTHERWISE)) {
         if (validateCondByType && condExpr.length() > 0) {
            XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_WARNING,
                                                             XMLValidationError.SUB_TYPE_LOGIC,
                                                             XPDLValidationErrorIds.WARNING_OTHERWISE_TRANSITION_WITH_EXPRESSION,
                                                             condExpr,
                                                             el);
            existingErrors.add(verr);
         }
         return;
      } else if (condType.equals(XPDLConstants.CONDITION_TYPE_NONE)) {
         if (validateCondByType && condExpr.length() > 0) {
            XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_WARNING,
                                                             XMLValidationError.SUB_TYPE_LOGIC,
                                                             XPDLValidationErrorIds.WARNING_UNCONDITIONAL_TRANSITION_WITH_EXPRESSION,
                                                             condExpr,
                                                             el);
            existingErrors.add(verr);
         }
      } else if (condType.equals(XPDLConstants.CONDITION_TYPE_CONDITION)) {
         if (validateCondByType && condExpr.length() <= 0) {
            XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_WARNING,
                                                             XMLValidationError.SUB_TYPE_LOGIC,
                                                             XPDLValidationErrorIds.WARNING_CONDITIONAL_TRANSITION_WITHOUT_EXPRESSION,
                                                             "",
                                                             el);
            existingErrors.add(verr);
         }
      } else if (condType.equals(XPDLConstants.CONDITION_TYPE_EXCEPTION)) {
         if (validateCondByType && condExpr.length() <= 0) {
            XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_WARNING,
                                                             XMLValidationError.SUB_TYPE_LOGIC,
                                                             XPDLValidationErrorIds.WARNING_EXCEPTION_TRANSITION_WITHOUT_EXPRESSION,
                                                             "",
                                                             el);
            existingErrors.add(verr);
         }
         return;
      }

      if ((existingErrors.size() == 0 || fullCheck)
          && condExpr.length() > 0
          && properties.getProperty(StandardPackageValidator.VALIDATE_CONDITION_EXPRESSIONS,
                                    "false")
             .equals("true")) {

         boolean cbe = false;
         if (condExpr.toLowerCase().indexOf("true") >= 0
             || condExpr.toLowerCase().indexOf("false") >= 0
             || condExpr.toLowerCase().indexOf("boolean") >= 0
             || condExpr.toLowerCase().indexOf("equals") >= 0
             || condExpr.toLowerCase().indexOf(">") >= 0
             || condExpr.toLowerCase().indexOf(">=") >= 0
             || condExpr.toLowerCase().indexOf("<") >= 0
             || condExpr.toLowerCase().indexOf("<=") >= 0
             || condExpr.toLowerCase().indexOf("==") >= 0) {
            cbe = true;
         }

         Map vars = getActualParameterOrConditionChoices(el);
         cbe = cbe || canBeExpression(condExpr, vars, false);
         String type = XMLValidationError.TYPE_WARNING;
         if (!cbe || !(type = additionalExpressionCheck(el, condExpr, vars)).equals("")) {
            XMLValidationError verr = new XMLValidationError(type,
                                                             XMLValidationError.SUB_TYPE_LOGIC,
                                                             XPDLValidationErrorIds.WARNING_CONDITION_EXPRESSION_POSSIBLY_INVALID,
                                                             condExpr,
                                                             el);
            existingErrors.add(verr);
         }
      }
   }

   /**
    * Validates DataField element.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(DataField el, List existingErrors, boolean fullCheck) {
      validateStandard(el, existingErrors, fullCheck);
      boolean validateVariableUsage = properties.getProperty(StandardPackageValidator.VALIDATE_UNUSED_VARIABLES,
                                                             "false")
         .equals("true");
      if (validateVariableUsage && (fullCheck || existingErrors.size() == 0)) {
         if (getNoOfReferences((XMLComplexElement) el.getParent().getParent(), el) == 0) {
            XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_WARNING,
                                                             XMLValidationError.SUB_TYPE_LOGIC,
                                                             XPDLValidationErrorIds.WARNING_UNUSED_VARIABLE,
                                                             el.getId(),
                                                             el);
            existingErrors.add(verr);
         }
      }
   }

   /**
    * Validates DeadlineDuration element.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(DeadlineDuration el, List existingErrors, boolean fullCheck) {
      validateStandard(el, existingErrors, fullCheck);
      String condExpr = el.toValue();
      if ((existingErrors.size() == 0 || fullCheck)
          && condExpr.length() > 0
          && properties.getProperty(StandardPackageValidator.VALIDATE_DEADLINE_EXPRESSIONS,
                                    "false")
             .equals("true")) {
         boolean cbe = false;
         if (condExpr.toLowerCase().indexOf("date") >= 0
             || condExpr.toLowerCase().indexOf("calendar") >= 0) {
            cbe = true;
         }
         Map vars = getDeadlineConditionChoices(el);
         cbe = cbe || canBeExpression(condExpr, vars, false);
         String type = XMLValidationError.TYPE_WARNING;
         if (!cbe || !(type = additionalExpressionCheck(el, condExpr, vars)).equals("")) {
            XMLValidationError verr = new XMLValidationError(type,
                                                             XMLValidationError.SUB_TYPE_LOGIC,
                                                             XPDLValidationErrorIds.WARNING_DEADLINE_EXPRESSION_POSSIBLY_INVALID,
                                                             condExpr,
                                                             el);

            existingErrors.add(verr);
         }

      }

   }

   /**
    * Validates the collection of Deadline elements for the Activity.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(Deadlines el, List existingErrors, boolean fullCheck) {
      Iterator dls = el.toElements().iterator();
      int syncCount = 0;
      while (dls.hasNext()) {
         Deadline dl = (Deadline) dls.next();
         if (dl.getExecution().equals(XPDLConstants.EXECUTION_SYNCHR)) {
            syncCount++;
         }
      }

      if (syncCount > 1) {
         XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                          XMLValidationError.SUB_TYPE_LOGIC,
                                                          XPDLValidationErrorIds.ERROR_MULTIPLE_SYNC_DEADLINES_DEFINED,
                                                          "",
                                                          el);
         existingErrors.add(verr);
      }

      if (fullCheck || syncCount > 1) {
         validateStandard(el, existingErrors, fullCheck);
      }
   }

   /**
    * Validates ExceptionName element.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(ExceptionName el, List existingErrors, boolean fullCheck) {
      Activity act = XMLUtil.getActivity(el);
      Set ets = XMLUtil.getExceptionalOutgoingTransitions(act);
      boolean isValid = true;
      if (ets.size() == 0) {
         isValid = false;
      } else {
         String en = el.toValue();
         Iterator it = ets.iterator();
         while (it.hasNext()) {
            Transition t = (Transition) it.next();
            String cond = t.getCondition().toValue();
            String ctype = t.getCondition().getType();
            if (ctype.equals(XPDLConstants.CONDITION_TYPE_DEFAULTEXCEPTION)
                || cond.equals(en) || cond.length() == 0) {
               return;
            }
         }
         isValid = false;
      }
      if (!isValid) {
         XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                          XMLValidationError.SUB_TYPE_LOGIC,
                                                          XPDLValidationErrorIds.ERROR_DEADLINE_EXCEPTION_NOT_PROPERLY_HANDLED_MISSING_SPECIFIED_EXCEPTION_TRANSITION_OR_DEFAULT_EXCEPTION_TRANSITION,
                                                          "",
                                                          el);
         existingErrors.add(verr);
      }
   }

   /**
    * This implementation does nothing.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(ExpressionType el, List existingErrors, boolean fullCheck) {
   }

   /**
    * Validates FormalParameter element.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(FormalParameter el, List existingErrors, boolean fullCheck) {
      validateStandard(el, existingErrors, fullCheck);
      boolean validateVariableUsage = properties.getProperty(StandardPackageValidator.VALIDATE_UNUSED_VARIABLES,
                                                             "false")
         .equals("true");
      if (validateVariableUsage
          && el.getParent().getParent() instanceof WorkflowProcess
          && (fullCheck || existingErrors.size() == 0)) {
         if (getNoOfReferences((WorkflowProcess) el.getParent().getParent(), el) == 0) {
            XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_WARNING,
                                                             XMLValidationError.SUB_TYPE_LOGIC,
                                                             XPDLValidationErrorIds.WARNING_UNUSED_VARIABLE,
                                                             el.getId(),
                                                             el);
            existingErrors.add(verr);
         }
      }
   }

   /**
    * Returns the number of references for the given element 'el' which parent Package or
    * WorkflowProcess element is 'parent'.
    * 
    * @param parent Package or WorkflowProcess instance which is the parent of given
    *           element.
    * @param el XMLComplexElement for which the number of references is retrieved.
    * @return The number of references for the given element.
    */
   protected int getNoOfReferences(XMLComplexElement parent, XMLComplexElement el) {
      return XMLUtil.getReferences(parent, el, null).size();
   }

   /**
    * Validates Package element.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(org.enhydra.jxpdl.elements.Package el,
                               List existingErrors,
                               boolean fullCheck) {
      validateAgainstXPDLSchema(el, existingErrors, fullCheck);
      if (existingErrors.size() == 0 || fullCheck) {
         validateStandard(el, existingErrors, fullCheck);
      }
      if (existingErrors.size() == 0 || fullCheck) {
         checkExternalPackages(el, existingErrors, fullCheck);
      }
      if (existingErrors.size() == 0 || fullCheck) {
         checkGraphConnectionsForArtifacts(el, existingErrors, fullCheck);
      }
   }

   /**
    * Validates Performer element.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(Performer el, List existingErrors, boolean fullCheck) {
      // check performer
      String performer = el.toValue();
      Activity act = XMLUtil.getActivity(el);
      if (act == null)
         return;
      // if this is not an No or Tool activity, check peformer
      int actType = act.getActivityType();
      boolean toolOrNoAct = true;
      if (actType != XPDLConstants.ACTIVITY_TYPE_NO
          && actType != XPDLConstants.ACTIVITY_TYPE_TASK_APPLICATION) {
         toolOrNoAct = false;
      }
      if (!toolOrNoAct && performer.length() > 0) {
         XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_WARNING,
                                                          XMLValidationError.SUB_TYPE_LOGIC,
                                                          XPDLValidationErrorIds.WARNING_ACTIVITY_CANNOT_HAVE_PERFORMER,
                                                          "",
                                                          el);
         existingErrors.add(verr);
      }
      if (toolOrNoAct
          && properties.getProperty(StandardPackageValidator.VALIDATE_PERFORMER_EXPRESSIONS,
                                    "false")
             .equals("true")) {
         Participant p = XMLUtil.findParticipant(xmlInterface,
                                                 XMLUtil.getWorkflowProcess(act),
                                                 performer);
         if (p == null) {
            if (performer.length() > 0) {
               Map vars = getPerformerChoices(el);
               boolean cbe = canBeExpression(performer, vars, true);
               String type = XMLValidationError.TYPE_WARNING;
               if (!cbe
                   || !(type = additionalExpressionCheck(el, performer, vars)).equals("")) {
                  XMLValidationError verr = new XMLValidationError(type,
                                                                   XMLValidationError.SUB_TYPE_LOGIC,
                                                                   XPDLValidationErrorIds.WARNING_PERFORMER_EXPRESSION_POSSIBLY_INVALID,
                                                                   performer,
                                                                   el);
                  existingErrors.add(verr);

               }
            }
         }
      }
   }

   /**
    * Validates Priority element.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(Priority el, List existingErrors, boolean fullCheck) {
      boolean notInt = false;
      try {
         if (el.toValue().trim().length() > 0) {
            Integer.parseInt(el.toValue());
         }
      } catch (Exception ex) {
         notInt = true;
      }
      if (notInt) {
         XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                          XMLValidationError.SUB_TYPE_LOGIC,
                                                          XPDLValidationErrorIds.ERROR_PRIORITY_INVALID_VALUE,
                                                          el.toValue(),
                                                          el);
         existingErrors.add(verr);
      }
   }

   /**
    * Validates Responsible element.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(Responsible el, List existingErrors, boolean fullCheck) {
      XMLComplexElement pkgOrWp = XMLUtil.getWorkflowProcess(el);
      if (pkgOrWp == null) {
         pkgOrWp = XMLUtil.getPackage(el);
      }
      String rv = el.toValue();
      Participant p;
      if (pkgOrWp instanceof Package) {
         p = XMLUtil.findParticipant(xmlInterface, (Package) pkgOrWp, rv);
      } else {
         p = XMLUtil.findParticipant(xmlInterface, (WorkflowProcess) pkgOrWp, rv);
      }
      if (p == null) {
         XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                          XMLValidationError.SUB_TYPE_LOGIC,
                                                          XPDLValidationErrorIds.ERROR_NON_EXISTING_PARTICIPANT_REFERENCE,
                                                          rv,
                                                          el);
         existingErrors.add(verr);
      }
   }

   /**
    * Validates SubFlow element.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(SubFlow el, List existingErrors, boolean fullCheck) {
      validateStandard(el, existingErrors, fullCheck);
      if (existingErrors.size() == 0 || fullCheck) {
         WorkflowProcess wp = XMLUtil.findWorkflowProcess(xmlInterface,
                                                          XMLUtil.getPackage(el),
                                                          el.getId());
         if (wp != null) {
            ActualParameters aps = el.getActualParameters();
            checkParameterMatching(wp.getFormalParameters(),
                                   aps,
                                   existingErrors,
                                   properties.getProperty(StandardPackageValidator.VALIDATE_ACTUAL_PARAMETER_EXPRESSIONS,
                                                          "false")
                                      .equals("true"),
                                   fullCheck);
         }
      }
   }

   /**
    * Validates TaskApplication element.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(TaskApplication el, List existingErrors, boolean fullCheck) {
      validateStandard(el, existingErrors, fullCheck);
      if (existingErrors.size() == 0 || fullCheck) {
         String toolId = el.getId();
         WorkflowProcess wp = XMLUtil.getWorkflowProcess(el);
         Application app = XMLUtil.findApplication(xmlInterface, wp, toolId);
         if (app != null) {
            XMLElement ch = app.getApplicationTypes().getChoosen();
            if (ch instanceof FormalParameters) {
               ActualParameters aps = el.getActualParameters();
               checkParameterMatching((FormalParameters) ch,
                                      aps,
                                      existingErrors,
                                      properties.getProperty(StandardPackageValidator.VALIDATE_ACTUAL_PARAMETER_EXPRESSIONS,
                                                             "false")
                                         .equals("true"),
                                      fullCheck);
            }
         }
      }

   }

   /**
    * Validates TransitionRefs element.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(TransitionRefs el, List existingErrors, boolean fullCheck) {
      Set outTrans = XMLUtil.getOutgoingTransitions(XMLUtil.getActivity(el));
      Split split = (Split) XMLUtil.getParentElement(Split.class, el);
      boolean isValid = true;
      if ((el.size() != outTrans.size())
          && outTrans.size() > 1
          && !split.getType().equals(XPDLConstants.JOIN_SPLIT_TYPE_PARALLEL)) {
         isValid = false;
         XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                          XMLValidationError.SUB_TYPE_LOGIC,
                                                          XPDLValidationErrorIds.ERROR_TRANSITION_REFS_AND_OUTGOING_TRANSITION_NUMBER_MISSMATCH,
                                                          "",
                                                          el);
         existingErrors.add(verr);
      }
      if (!(fullCheck || isValid)) {
         return;
      }
      validateStandard(el, existingErrors, fullCheck);
   }

   /**
    * Validates Transitions element.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(Transitions el, List existingErrors, boolean fullCheck) {
      validateStandard(el, existingErrors, fullCheck);
      if (fullCheck || existingErrors.size() == 0) {
         Map actConns = new HashMap();
         Set multipleConnections = new HashSet();
         for (int i = 0; i < el.size(); i++) {
            Transition t = (Transition) el.get(i);
            String actConn = "[" + t.getFrom() + "-" + t.getTo() + "]";
            if (actConns.containsKey(actConn)) {
               multipleConnections.add(actConns.get(actConn));
               multipleConnections.add(t);

               if (!fullCheck) {
                  break;
               }

            }
            actConns.put(actConn, t);
         }
         if (multipleConnections.size() > 0) {
            Iterator it = multipleConnections.iterator();
            while (it.hasNext()) {
               Transition t = (Transition) it.next();
               String actConn = "[" + t.getFrom() + "-" + t.getTo() + "]";
               XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                                XMLValidationError.SUB_TYPE_LOGIC,
                                                                XPDLValidationErrorIds.ERROR_MULTIPLE_CONNECTIONS,
                                                                actConn,
                                                                t);
               existingErrors.add(verr);
            }
         }

      }
   }

   /**
    * Validates WorkflowProcess element.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(WorkflowProcess el, List existingErrors, boolean fullCheck) {
      boolean isValid = true;
      if (el.getActivities().toElements().size() == 0) {
         isValid = false;
         XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                          XMLValidationError.SUB_TYPE_LOGIC,
                                                          XPDLValidationErrorIds.ERROR_WORKFLOW_PROCESS_NOT_DEFINED,
                                                          "",
                                                          el);
         existingErrors.add(verr);
      }
      if (fullCheck || isValid) {
         validateStandard(el, existingErrors, fullCheck);
      }
      if (isValid || fullCheck) {
         checkGraphConnectionsForWpOrAs(el, existingErrors, fullCheck);
      }
      if (isValid || fullCheck) {
         checkGraphConformanceForWpOrAs(el, existingErrors, fullCheck);
      }
   }

   /**
    * Validates XPDLVersion element.
    * 
    * @param el Element to validate
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   public void validateElement(XPDLVersion el, List existingErrors, boolean fullCheck) {
      if (!el.toValue().equals(CURRENT_XPDL_VERSION)) {
         XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_WARNING,
                                                          XMLValidationError.SUB_TYPE_LOGIC,
                                                          XPDLValidationErrorIds.WARNING_INVALID_XPDL_VERSION,
                                                          el.toValue(),
                                                          el);
         existingErrors.add(verr);
      }
   }

   // ********************* validation against XPDL schema *********************
   /**
    * Performs schema validation of XPDL model.
    * 
    * @param pkg Package/XPDL model element to validate against XPDL schema.
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   protected void validateAgainstXPDLSchema(Package pkg,
                                            List existingErrors,
                                            boolean fullCheck) {
      List schValidationErrors = (List) schemaValidationErrors.get(pkg);
      if (schValidationErrors != null
          && properties.getProperty(StandardPackageValidator.GET_EXISTING_SCHEMA_VALIDATION_ERRORS,
                                    "false")
             .equals("true")) {
         existingErrors.addAll(schValidationErrors);
         return;
      }
      List errorMessages = new ArrayList();
      try {
         String encoding = properties.getProperty(StandardPackageValidator.ENCODING,
                                                  "UTF-8");

         Document document = null;

         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         DocumentBuilder dbuilder = dbf.newDocumentBuilder();
         document = dbuilder.newDocument();
         ByteArrayOutputStream baos = new ByteArrayOutputStream();

         // Here we get all document elements
         XPDLRepositoryHandler repH = new XPDLRepositoryHandler();
         repH.toXML(document, pkg);

         // Use a Transformer for output
         TransformerFactory tFactory = TransformerFactory.newInstance();
         Transformer transformer = tFactory.newTransformer();
         transformer.setOutputProperty("indent", "yes");
         transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
         transformer.setOutputProperty("encoding", encoding);
         DOMSource source = new DOMSource(document);
         StreamResult result = new StreamResult(baos);
         transformer.transform(source, result);

         // Create a Xerces DOM Parser
         DOMParser parser = new DOMParser();
         // Parse the Document and traverse the DOM
         try {
            String locale = properties.getProperty(StandardPackageValidator.LOCALE);
            Locale l = new Locale("");
            if (locale == null || locale.trim().length() == 0) {
               l = Locale.getDefault();
            } else if (!locale.equals("default")) {
               l = new Locale(locale);
            }
            parser.setLocale(l);
            parser.setFeature("http://apache.org/xml/features/continue-after-fatal-error",
                              true);
            ParsingErrors pErrors = new ParsingErrors();
            parser.setErrorHandler(pErrors);
            parser.setEntityResolver(new XPDLEntityResolver());
            parser.setFeature("http://xml.org/sax/features/validation", true);
            parser.setFeature("http://apache.org/xml/features/validation/schema", true);
            // parser.setFeature("http://apache.org/xml/features/validation/schema-full-checking",true);
            // System.out.println("Parsing from stream");
            parser.parse(new InputSource(new StringReader(baos.toString(encoding))));
            errorMessages = pErrors.getErrorMessages();
         } catch (Exception ex) {
            ex.printStackTrace();
            errorMessages.add("Fatal error while parsing document:" + ex.getMessage());
         }
         baos.close();
      } catch (Exception ex) {
         ex.printStackTrace();
         errorMessages.add("Fatal error while validating schema for package "
                           + pkg.getId() + " :" + ex.getMessage());
      }
      schValidationErrors = new ArrayList();
      if (errorMessages.size() > 0) {
         Iterator it2 = errorMessages.iterator();
         while (it2.hasNext()) {
            String msg = (String) it2.next();
            if (!(msg.contains("SubFlowIdRef.Package")
                  || msg.contains("of attribute 'Id' on element 'SubFlow' is not valid with respect to its type, 'IdRef'") || (msg.contains("'http") && msg.contains("is not a valid value for 'NMTOKEN'")))) {
               XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                                XMLValidationError.SUB_TYPE_SCHEMA,
                                                                "",
                                                                msg,
                                                                pkg);
               schValidationErrors.add(verr);
            }
         }
      }
      existingErrors.addAll(schValidationErrors);
      schemaValidationErrors.put(pkg, schValidationErrors);
   }

   // ********************* Logic checking
   // **************************************

   /**
    * Performs validation of external packages for the given Package object.
    * 
    * @param pkg Package/XPDL model element to validate for external packages.
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   protected void checkExternalPackages(Package pkg,
                                        List existingErrors,
                                        boolean fullCheck) {
      if (properties.getProperty(StandardPackageValidator.CHECK_EXTERNAL_PACKAGES, "true")
         .equals("false")) {
         return;
      }
      Iterator it = XMLUtil.getAllExternalPackageIds(xmlInterface, pkg, new HashSet())
         .iterator();
      while (it.hasNext()) {
         Package p = xmlInterface.getPackageById((String) it.next());
         List epErrors = (List) epsValidationErrors.get(p);
         if (epErrors == null) {
            epErrors = reCheckExternalPackage(p);
         }
         existingErrors.addAll(epErrors);
      }
   }

   /**
    * Re-checks the validation of external packages for the given Package object.
    * 
    * @param p Package which external package's validation will be re-checked.
    * @return A list of validation errors.
    */
   public List reCheckExternalPackage(Package p) {
      List epErrors = (List) epsValidationErrors.get(p);
      if (epErrors != null) {
         epErrors.clear();
      } else {
         epErrors = new ArrayList();
      }

      Properties copy = new Properties();
      Iterator it = properties.entrySet().iterator();
      while (it.hasNext()) {
         Map.Entry me = (Map.Entry) it.next();
         String key = (String) me.getKey();
         String val = (String) me.getValue();
         if (key.equals(CHECK_EXTERNAL_PACKAGES)) {
            copy.setProperty(CHECK_EXTERNAL_PACKAGES, "false");
         } else if (key.equals(GET_EXISTING_SCHEMA_VALIDATION_ERRORS)) {
            copy.setProperty(GET_EXISTING_SCHEMA_VALIDATION_ERRORS, "false");
         } else {
            copy.setProperty(key, val);
         }
      }
      StandardPackageValidator pv = createValidatorInstance();
      pv.init(copy, xmlInterface);
      List l = new ArrayList();
      pv.validateElement(p, l, true);
      epsValidationErrors.put(p, l);
      schemaValidationErrors.remove(p);
      return l;
   }

   /**
    * Returns true if there is an Application definition within this or external packages
    * with the Id given by the 'tlId' XMLAttribute.
    * 
    * @param tlId The Id of Application definition to search.
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    * @return true if there is an Application definition within this or external packages
    *         with the Id given by the 'tlId' XMLAttribute.
    */
   protected boolean checkToolId(XMLAttribute tlId, List existingErrors, boolean fullCheck) {
      XMLValidationError verr = null;

      String toolId = tlId.toValue();
      WorkflowProcess wp = XMLUtil.getWorkflowProcess(tlId);
      Application app = XMLUtil.findApplication(xmlInterface, wp, toolId);
      if (app == null) {
         verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                       XMLValidationError.SUB_TYPE_LOGIC,
                                       XPDLValidationErrorIds.ERROR_NON_EXISTING_APPLICATION_REFERENCE,
                                       toolId,
                                       tlId);
         existingErrors.add(verr);
      }

      return (verr != null);
   }

   /**
    * Returns true if there is an WorkflowProcess definition within this or external
    * packages with the Id given by the 'sbflwId' XMLAttribute.
    * 
    * @param sbflwId The Id of WorkflowProcess definition to search.
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    * @return true if there is an WorkflowProcess definition within this or external
    *         packages with the Id given by the 'sbflwId' XMLAttribute.
    */
   protected boolean checkSubFlowId(XMLAttribute sbflwId,
                                    List existingErrors,
                                    boolean fullCheck) {
      XMLValidationError verr = null;
      String subflowId = sbflwId.toValue();
      if (subflowId.trim().equals("")) {
         verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                       XMLValidationError.SUB_TYPE_LOGIC,
                                       XPDLValidationErrorIds.ERROR_NON_EXISTING_WORKFLOW_PROCESS_REFERENCE,
                                       subflowId,
                                       sbflwId);
      }

      Package pkg = XMLUtil.getPackage(sbflwId);
      WorkflowProcess wp = null;
      if (verr == null) {
         wp = XMLUtil.findWorkflowProcess(xmlInterface, pkg, subflowId);
         if (wp == null
             && !isRemoteSubflowIdOK(subflowId)
             && properties.getProperty(StandardPackageValidator.VALIDATE_SUBFLOW_REFERENCES,
                                       "true")
                .equals("true")) {
            verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                          XMLValidationError.SUB_TYPE_LOGIC,
                                          XPDLValidationErrorIds.ERROR_NON_EXISTING_WORKFLOW_PROCESS_REFERENCE,
                                          subflowId,
                                          sbflwId);
         }
      }

      if (verr != null) {
         existingErrors.add(verr);
      }

      return (verr == null);
   }

   /**
    * Checks if there are transitions with the Id given by the 'trfId' XMLAttribute.
    * 
    * @param trfId XMLAttribute representing Id of TransitionRef element.
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   protected void checkTransitionRefId(XMLAttribute trfId,
                                       List existingErrors,
                                       boolean fullCheck) {
      Set outTrans = XMLUtil.getOutgoingTransitions(XMLUtil.getActivity(trfId));
      String transitionId = trfId.toValue();
      if (!containsTransitionWithId(outTrans, transitionId)) {
         XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                          XMLValidationError.SUB_TYPE_LOGIC,
                                                          XPDLValidationErrorIds.ERROR_NON_EXISTING_TRANSITION_REFERENCE,
                                                          transitionId,
                                                          trfId);
         existingErrors.add(verr);
      }
   }

   /**
    * Returns true if there is Transition element with given Id within the set of given
    * transitions.
    * 
    * @param trans The set of Transition elements.
    * @param id Id of transition to search within the set.
    * @return true if there is Transition element with given Id within the set of given
    *         transitions.
    */
   protected boolean containsTransitionWithId(Set trans, String id) {
      Iterator it = trans.iterator();
      while (it.hasNext()) {
         Transition t = (Transition) it.next();
         if (t.getId().equals(id)) {
            return true;
         }
      }
      return false;
   }

   /**
    * Returns true if remote sub-flow Id is correct. This implementation always returns
    * false. This method is meant to be overridden when implementing particular workflow
    * engine validator.
    * 
    * @param subflowId Id of remote sub-flow process.
    * @return false
    */
   protected boolean isRemoteSubflowIdOK(String subflowId) {
      return false;
   }

   /**
    * Checks if there are multiple 'otherwise' or 'default exception' transitions for the
    * given activity. If this is the case, new error is added to the list since this is
    * not allowed by XPDL spec.
    * 
    * @param act Activity which is checked for multiple transitions.
    * @param outTrans The set of Transition elements.
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   protected void checkMultipleOtherwiseOrDefaultExceptionTransitions(Activity act,
                                                                      Set outTrans,
                                                                      List existingErrors,
                                                                      boolean fullCheck) {
      boolean foundOtherwise = false;
      boolean foundMultipleOtherwise = false;
      boolean foundDefaultException = false;
      boolean foundMultipleDefaultException = false;
      Iterator ts = outTrans.iterator();
      while (ts.hasNext()) {
         Transition t = (Transition) ts.next();
         String ct = t.getCondition().getType();
         if (ct.equals(XPDLConstants.CONDITION_TYPE_OTHERWISE)) {
            if (foundOtherwise) {
               foundMultipleOtherwise = true;
               if (foundMultipleDefaultException || !fullCheck)
                  break;
            } else {
               foundOtherwise = true;
            }
         } else if (ct.equals(XPDLConstants.CONDITION_TYPE_DEFAULTEXCEPTION)) {
            if (foundDefaultException) {
               foundMultipleDefaultException = true;
               if (foundMultipleOtherwise || !fullCheck)
                  break;
            } else {
               foundDefaultException = true;
            }
         }
      }

      if (foundMultipleOtherwise) {
         XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                          XMLValidationError.SUB_TYPE_LOGIC,
                                                          XPDLValidationErrorIds.ERROR_MORE_THAN_ONE_OTHERWISE_TRANSITION,
                                                          "",
                                                          act);
         existingErrors.add(verr);
      }
      if (foundMultipleDefaultException) {
         XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                          XMLValidationError.SUB_TYPE_LOGIC,
                                                          XPDLValidationErrorIds.ERROR_MORE_THAN_ONE_DEFAULT_EXCEPTION_TRANSITION,
                                                          "",
                                                          act);
         existingErrors.add(verr);
      }
   }

   /**
    * Checks if activity referenced by the Transition's from attribute exists in the
    * model.
    * 
    * @param from XMLAttribute element representing transition's 'From' attribute.
    * @param existingErrors List of existing errors.
    */
   protected void checkTransitionFrom(XMLAttribute from, List existingErrors) {
      if (XMLUtil.getFromActivity(XMLUtil.getTransition(from)) == null) {
         XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                          XMLValidationError.SUB_TYPE_LOGIC,
                                                          XPDLValidationErrorIds.ERROR_NON_EXISTING_ACTIVITY_REFERENCE,
                                                          from.toValue(),
                                                          from);
         existingErrors.add(verr);
      }
   }

   /**
    * Checks if activity referenced by the Transition's to attribute exists in the model.
    * 
    * @param to XMLAttribute element representing transition's 'To' attribute.
    * @param existingErrors List of existing errors.
    */
   protected void checkTransitionTo(XMLAttribute to, List existingErrors) {
      if (XMLUtil.getToActivity(XMLUtil.getTransition(to)) == null) {
         XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                          XMLValidationError.SUB_TYPE_LOGIC,
                                                          XPDLValidationErrorIds.ERROR_NON_EXISTING_ACTIVITY_REFERENCE,
                                                          to.toValue(),
                                                          to);
         existingErrors.add(verr);
      }
   }

   /**
    * Checks if artifact or activity referenced by the Associations's source attribute
    * exists in the model.
    * 
    * @param source XMLAttribute element representing association's 'Source' attribute.
    * @param existingErrors List of existing errors.
    */
   protected void checkAssociationSource(XMLAttribute source, List existingErrors) {
      if (XMLUtil.getAssociationSource(XMLUtil.getAssociation(source)) == null) {
         XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                          XMLValidationError.SUB_TYPE_LOGIC,
                                                          XPDLValidationErrorIds.ERROR_NON_EXISTING_ACTIVITY_OR_ARTIFACT_REFERENCE,
                                                          source.toValue(),
                                                          source);
         existingErrors.add(verr);
      }
   }

   /**
    * Checks if artifact or activity referenced by the Associations's target attribute
    * exists in the model.
    * 
    * @param target XMLAttribute element representing association's 'Target' attribute.
    * @param existingErrors List of existing errors.
    */
   protected void checkAssociationTarget(XMLAttribute target, List existingErrors) {
      if (XMLUtil.getAssociationTarget(XMLUtil.getAssociation(target)) == null) {
         XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                          XMLValidationError.SUB_TYPE_LOGIC,
                                                          XPDLValidationErrorIds.ERROR_NON_EXISTING_ACTIVITY_OR_ARTIFACT_REFERENCE,
                                                          target.toValue(),
                                                          target);
         existingErrors.add(verr);
      }
   }

   /**
    * Checks if there is a TypeDeclaration definition within this or external packages
    * with the Id given by the 'dtId' XMLAttribute.
    * 
    * @param dtId XMLAttribute element representing DeclaredType's 'Id' attribute.
    * @param existingErrors List of existing errors.
    */
   protected void checkDeclaredTypeId(XMLAttribute dtId, List existingErrors) {
      String tdId = dtId.toValue();
      TypeDeclaration td = XMLUtil.getTypeDeclaration(xmlInterface,
                                                      XMLUtil.getPackage(dtId),
                                                      tdId);
      if (td == null) {
         XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                          XMLValidationError.SUB_TYPE_LOGIC,
                                                          XPDLValidationErrorIds.ERROR_NON_EXISTING_TYPE_DECLARATION_REFERENCE,
                                                          tdId,
                                                          dtId);
         existingErrors.add(verr);
      }
   }

   /**
    * Checks if there is an ActivitySet definition within the WorkflowProcess with the Id
    * given by the 'bId' XMLAttribute.
    * 
    * @param bId XMLAttribute element representing BlockActivity's 'ActivitySetId'
    *           attribute.
    * @param existingErrors List of existing errors.
    */
   protected void checkBlockId(XMLAttribute bId, List existingErrors) {
      String blockId = bId.toValue();
      // check if the activity set exists
      ActivitySet as = XMLUtil.getWorkflowProcess(bId).getActivitySet(blockId);
      if (as == null) {
         XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                          XMLValidationError.SUB_TYPE_LOGIC,
                                                          XPDLValidationErrorIds.ERROR_NON_EXISTING_ACTIVITY_SET_REFERENCE,
                                                          blockId,
                                                          bId);
         existingErrors.add(verr);
      }
   }

   /**
    * Returns true if given string is null or it is an empty string.
    * 
    * @param str String to check.
    * @return true if given string is null or it is an empty string.
    */
   public static boolean isEmpty(String str) {
      if (str == null || str.trim().length() == 0) {
         return true;
      }

      return false;
   }

   public boolean isElementLengthOK(XMLElement el) {
      return true;
   }
   
   /**
    * Returns true if the Id is valid according to XML restrictions for xsd:NMTOKEN type
    * attributes.
    * 
    * @param id Id to check.
    * @return true if the Id is valid according to XML restrictions for xsd:NMTOKEN type
    *         attributes.
    */
   protected boolean isIdValid(String id) {
      return XMLUtil.isIdValid(id);
   }

   /**
    * Returns true if the Id of the given XMLCollectionElement is unique according to XPDL
    * spec rules.
    * 
    * @param newEl Element to check.
    * @return true if the Id of the given XMLCollectionElement is unique according to XPDL
    *         spec rules.
    */
   protected boolean isIdUnique(XMLCollectionElement newEl) {

      XMLElement parent = newEl.getParent();
      if (newEl instanceof Tool || newEl instanceof TransitionRef)
         return true;
      else if (newEl instanceof Activity)
         return checkActivityId((Activity) newEl);
      else if (newEl instanceof Transition)
         return checkTransitionId((Transition) newEl);
      else if (newEl instanceof ActivitySet)
         return checkActivitySetId((ActivitySet) newEl);
      else if (parent instanceof XMLCollection) {
         return XMLUtil.cntIds((XMLCollection) parent, newEl.getId()) <= 1;
      } else {
         return true;
      }
   }

   /**
    * Returns true if the Id of the given Activity is unique according to XPDL spec rules.
    * 
    * @param newEl Activity to check.
    * @return true if the Id of the given Activity is unique according to XPDL spec rules.
    */
   protected boolean checkActivityId(Activity newEl) {
      int idCnt = 0;
      Iterator it = XMLUtil.getPackage(newEl)
         .getWorkflowProcesses()
         .toElements()
         .iterator();
      while (it.hasNext()) {
         WorkflowProcess proc = (WorkflowProcess) it.next();

         String newId = newEl.getId();
         Activities acts = proc.getActivities();
         idCnt += XMLUtil.cntIds(acts, newId);
         ActivitySets actSets = proc.getActivitySets();
         for (int y = 0; y < actSets.size(); y++) {
            ActivitySet actSet = (ActivitySet) actSets.get(y);
            acts = actSet.getActivities();
            idCnt = idCnt + XMLUtil.cntIds(acts, newId);
         }
      }
      return idCnt <= 1;
   }

   /**
    * Returns true if the Id of the given Transition is unique according to XPDL spec
    * rules.
    * 
    * @param newEl Transition to check.
    * @return true if the Id of the given Transition is unique according to XPDL spec
    *         rules.
    */
   protected boolean checkTransitionId(Transition newEl) {
      int idCnt = 0;
      WorkflowProcess proc = XMLUtil.getWorkflowProcess(newEl);

      String newId = newEl.getId();
      Transitions trans = proc.getTransitions();
      idCnt += XMLUtil.cntIds(trans, newId);
      ActivitySets actSets = proc.getActivitySets();
      for (int y = 0; y < actSets.size(); y++) {
         ActivitySet actSet = (ActivitySet) actSets.get(y);
         trans = actSet.getTransitions();
         idCnt += XMLUtil.cntIds(trans, newId);
      }
      return idCnt <= 1;
   }

   /**
    * Returns true if the Id of the given ActivitySet is unique according to XPDL spec
    * rules.
    * 
    * @param newEl ActivitySet to check.
    * @return true if the Id of the given ActivitySet is unique according to XPDL spec
    *         rules.
    */
   protected boolean checkActivitySetId(ActivitySet newEl) {
      int idCnt = 0;
      WorkflowProcesses wps = XMLUtil.getPackage(newEl).getWorkflowProcesses();
      idCnt += XMLUtil.cntIds(wps, newEl.getId());
      Iterator it = wps.toElements().iterator();
      while (it.hasNext()) {
         WorkflowProcess proc = (WorkflowProcess) it.next();

         String newId = newEl.getId();
         ActivitySets ass = proc.getActivitySets();
         idCnt += XMLUtil.cntIds(ass, newId);
      }
      return idCnt <= 1;
   }

   // public static void printIM(boolean[][] im, java.util.List acts) {
   // if (im != null) {
   // for (int i = 0; i < im.length; i++) {
   // for (int j = 0; j < im[i].length; j++) {
   // System.out.print(acts.get(i) + "->" + acts.get(j) + "=" + im[i][j] + " ");
   // }
   // System.out.println();
   // }
   // } else {
   // System.out.println("Passed array is null !!!");
   // }
   // }

   // public static void printIM2(boolean[][] im, java.util.List acts) {
   // System.out.println("Activities are" + acts);
   // if (im != null) {
   // for (int i = 0; i < im.length; i++) {
   // for (int j = 0; j < im[i].length; j++) {
   // System.out.print(((im[i][j]) ? "1" : "0") + " ");
   // }
   // System.out.println();
   // }
   // } else {
   // System.out.println("Passed array is null !!!");
   // }
   // }

   // ************************** GRAPH CONFORMANCE CHECKING
   // ****************************

   /**
    * Checks if graph conforms to the given conformance class.
    * 
    * @param wpOrAs WorkflowProcess or ActivitySet element to check for the conformance.
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    * @return true if graph is conformant, false otherwise.
    */
   protected boolean checkGraphConformanceForWpOrAs(XMLCollectionElement wpOrAs,
                                                    List existingErrors,
                                                    boolean fullCheck) {

      Package pkg = XMLUtil.getPackage(wpOrAs);
      String conformanceClass = pkg.getConformanceClass().getGraphConformance();
      // ct=0->NON_BLOCKED, ct=1->LOOP_BLOCKED, ct=2->FULL_BLOCKED,
      // ct=-1->default NON_BLOCKED
      int ct = XMLUtil.getConformanceClassNo(conformanceClass);
      Activities acts = (Activities) wpOrAs.get("Activities");
      List activities = acts.toElements();

      if (activities.size() == 0) {
         return true;
      }
      boolean isGraphConformant = true;

      Set splitActs = XMLUtil.getSplitOrJoinActivities(activities, 0);
      Set joinActs = XMLUtil.getSplitOrJoinActivities(activities, 1);

      Set noSplitActs = new HashSet(activities);
      noSplitActs.removeAll(splitActs);

      GraphChecker gc = null;
      if (ct > 0 && (isGraphConformant || fullCheck)) {
         boolean[][] incidenceMatrix = createIncidenceMatrix(acts);
         if (incidenceMatrix == null) {
            XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                             XMLValidationError.SUB_TYPE_CONFORMANCE,
                                                             "Unexpected error while checking graph conformance!",
                                                             "",
                                                             wpOrAs);
            existingErrors.add(verr);

            return false;
         }

         gc = new GraphChecker(incidenceMatrix);

         // call method to check loop cycling
         boolean loopError = false;
         if (fullCheck) {
            int[] loopNodes = gc.getCyclicNodes();
            if (loopNodes != null) {
               isGraphConformant = false;
               loopError = true;
               for (int i = 0; i < loopNodes.length; i++) {
                  Activity act = (Activity) activities.get(loopNodes[i]);
                  XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                                   XMLValidationError.SUB_TYPE_CONFORMANCE,
                                                                   XPDLValidationErrorIds.ERROR_LOOP_CONTAINED_ACTIVITY_IN_LOOP_BLOCKED_MODE,
                                                                   "",
                                                                   act);
                  existingErrors.add(verr);
               }
            }
         } else {
            loopError = gc.isGraphCyclic();
            if (loopError) {
               isGraphConformant = false;
               XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                                XMLValidationError.SUB_TYPE_CONFORMANCE,
                                                                XPDLValidationErrorIds.ERROR_CYCLIC_GRAPH_IN_LOOP_BLOCKED_MODE,
                                                                "",
                                                                wpOrAs);
               existingErrors.add(verr);
            }
         }
      }
      // Here we check FULL_BLOCK conformance
      if (ct == 2 && (isGraphConformant || fullCheck)) {
         // check if there is more then one starting activity
         if (XMLUtil.getStartingActivities(wpOrAs).size() != 1) {
            isGraphConformant = false;
            XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                             XMLValidationError.SUB_TYPE_CONFORMANCE,
                                                             XPDLValidationErrorIds.ERROR_MULTIPLE_STARTING_ACTIVITIES_IN_FULL_BLOCKED_MODE,
                                                             "",
                                                             wpOrAs);
            existingErrors.add(verr);
         }
         // check if there is more then one ending activity
         if ((isGraphConformant || fullCheck)
             && XMLUtil.getEndingActivities(wpOrAs).size() != 1) {
            isGraphConformant = false;
            XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                             XMLValidationError.SUB_TYPE_CONFORMANCE,
                                                             XPDLValidationErrorIds.ERROR_MULTIPLE_ENDING_ACTIVITIES_IN_FULL_BLOCKED_MODE,
                                                             "",
                                                             wpOrAs);
            existingErrors.add(verr);
         }

         // check if the number of splits and joins matches
         boolean smerr = false;
         if ((isGraphConformant || fullCheck) && splitActs.size() != joinActs.size()) {
            if (splitActs.size() > joinActs.size()) {
               XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                                XMLValidationError.SUB_TYPE_CONFORMANCE,
                                                                XPDLValidationErrorIds.ERROR_SPLIT_JOIN_MISSMATCH_IN_FULL_BLOCKED_MODE_MORE_SPLITS,
                                                                "",
                                                                wpOrAs);
               existingErrors.add(verr);
            } else {
               XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                                XMLValidationError.SUB_TYPE_CONFORMANCE,
                                                                XPDLValidationErrorIds.ERROR_SPLIT_JOIN_MISSMATCH_IN_FULL_BLOCKED_MODE_MORE_JOINS,
                                                                "",
                                                                wpOrAs);
               existingErrors.add(verr);
            }
            isGraphConformant = false;
            smerr = true;
         }

         // check for split/join type mismatch
         if ((isGraphConformant || fullCheck) && !smerr) {
            if (getNoOfANDSplitsOrJoins(splitActs, 0) != getNoOfANDSplitsOrJoins(joinActs,
                                                                                 1)) {
               XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                                XMLValidationError.SUB_TYPE_CONFORMANCE,
                                                                XPDLValidationErrorIds.ERROR_SPLIT_JOIN_MISSMATCH_IN_FULL_BLOCKED_MODE_DIFFERENT_TYPES,
                                                                "",
                                                                wpOrAs);
               existingErrors.add(verr);
               isGraphConformant = false;
            }
         }
         // first check for correct outgoing transitions
         if (isGraphConformant || fullCheck) {
            Iterator it = splitActs.iterator();
            // boolean andSplitError = false;
            // boolean xorSplitError = false;
            while (it.hasNext()) {
               Activity act = (Activity) it.next();
               if (XMLUtil.isANDTypeSplitOrJoin(act, 0)) {
                  if (!checkANDSplit(act)) {
                     isGraphConformant = false;
                     // andSplitError = true;

                     XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                                      XMLValidationError.SUB_TYPE_CONFORMANCE,
                                                                      XPDLValidationErrorIds.ERROR_CONDITIONAL_TRANSITION_FOR_AND_SPLIT_IN_FULL_BLOCKED_MODE,
                                                                      "",
                                                                      act);
                     existingErrors.add(verr);

                     if (!fullCheck) {
                        break;
                     }
                  }
               } else {
                  if (!checkXORSplit(act)) {
                     isGraphConformant = false;
                     // xorSplitError = true;

                     XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                                      XMLValidationError.SUB_TYPE_CONFORMANCE,
                                                                      XPDLValidationErrorIds.ERROR_NO_OTHERWISE_TRANSITION_FOR_XOR_SPLIT_IN_FULL_BLOCKED_MODE,
                                                                      "",
                                                                      act);
                     existingErrors.add(verr);

                     if (!fullCheck) {
                        break;
                     }
                  }
               }
            }

            // check activities that has only one outgoing transition, if
            // there is condition on it -> report XOR split with conditional
            // transition error
            it = noSplitActs.iterator();
            while (it.hasNext()) {
               Activity act = (Activity) it.next();
               if (!checkXORSplit(act)) {
                  isGraphConformant = false;
                  // xorSplitError = true;

                  XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                                   XMLValidationError.SUB_TYPE_CONFORMANCE,
                                                                   XPDLValidationErrorIds.ERROR_NO_OTHERWISE_TRANSITION_FOR_XOR_SPLIT_IN_FULL_BLOCKED_MODE,
                                                                   "",
                                                                   act);
                  existingErrors.add(verr);

                  if (!fullCheck) {
                     break;
                  }
               }
            }
         }

         // now perform search on every split activity for corresponding join
         // activity
         if (isGraphConformant || fullCheck) {
            // boolean noCorrespondingJoinError = false;
            Iterator it = splitActs.iterator();
            while (it.hasNext()) {
               Activity act = (Activity) it.next();
               int splitIndex = activities.indexOf(act);
               if (splitIndex == -1) {
                  XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                                   XMLValidationError.SUB_TYPE_CONFORMANCE,
                                                                   "Unexpected error while searching for split/join matching for graph conformance!",
                                                                   "",
                                                                   wpOrAs);
                  existingErrors.add(verr);

                  isGraphConformant = false;
                  if (!fullCheck) {
                     break;
                  }

                  continue;
               }
               int ji = gc.getJoinIndex(splitIndex);
               // The correspondin join can't be found
               if (ji < 0) {
                  isGraphConformant = false;
                  // noCorrespondingJoinError = true;

                  XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                                   XMLValidationError.SUB_TYPE_CONFORMANCE,
                                                                   XPDLValidationErrorIds.ERROR_NO_CORRESPONDING_JOIN_ACTIVITY_IN_FULL_BLOCKED_MODE,
                                                                   "",
                                                                   act);
                  existingErrors.add(verr);

                  if (!fullCheck) {
                     break;
                  }
                  // if the join is found and their types are different
                  // the graph is not conformant
               } else {
                  if (XMLUtil.isANDTypeSplitOrJoin(act, 0) != XMLUtil.isANDTypeSplitOrJoin((Activity) activities.get(ji),
                                                                                           1)) {
                     isGraphConformant = false;
                     // noCorrespondingJoinError = true;

                     if (XMLUtil.isANDTypeSplitOrJoin(act, ji)) {
                        XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                                         XMLValidationError.SUB_TYPE_CONFORMANCE,
                                                                         XPDLValidationErrorIds.ERROR_NO_CORRESPONDING_JOIN_ACTIVITY_TYPE_IN_FULL_BLOCKED_MODE_AND_XOR,
                                                                         "",
                                                                         act);
                        existingErrors.add(verr);

                     } else {
                        XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                                         XMLValidationError.SUB_TYPE_CONFORMANCE,
                                                                         XPDLValidationErrorIds.ERROR_NO_CORRESPONDING_JOIN_ACTIVITY_TYPE_IN_FULL_BLOCKED_MODE_XOR_AND,
                                                                         "",
                                                                         act);
                        existingErrors.add(verr);
                     }
                     if (!fullCheck) {
                        break;
                     }
                  }
               }
            }
         }
      }

      return isGraphConformant;
   }

   /**
    * Returns incidence matrix for the given activities in the graph.
    * 
    * @param activities Activities collection.
    * @return Incidence matrix for the given activities in the graph.
    */
   protected boolean[][] createIncidenceMatrix(Activities activities) {
      int size = activities.size();
      boolean[][] incidenceMatrix = new boolean[size][size];
      for (int indAct = 0; indAct < size; indAct++) {
         Activity a = (Activity) activities.get(indAct);
         Iterator trs = XMLUtil.getOutgoingTransitions(a).iterator();
         while (trs.hasNext()) {
            Transition t = (Transition) trs.next();
            String aOut = t.getTo();
            Activity toAct = activities.getActivity(aOut);
            if (toAct == null)
               return null;
            int indOut = activities.indexOf(toAct);
            incidenceMatrix[indAct][indOut] = true;
         }
      }
      return incidenceMatrix;
   }

   /**
    * Returns the number of activities in the given set that have split or join, depending
    * on second parameter.
    * 
    * @param acts The set of activities that are searched for split or join
    * @param sOrJ 0 -> searching for split, otherwise, searching for join
    * @return The number of activities in the given set that have split or join, depending
    *         on second parameter.
    */
   protected int getNoOfANDSplitsOrJoins(Set acts, int sOrJ) {
      int no = 0;
      Iterator it = acts.iterator();
      while (it.hasNext()) {
         Activity act = (Activity) it.next();
         if (sOrJ == 0 && XMLUtil.isANDTypeSplitOrJoin(act, 0)) {
            no++;
         } else if (sOrJ == 1 && XMLUtil.isANDTypeSplitOrJoin(act, 0)) {
            no++;
         }
      }
      return no;
   }

   /**
    * Returns true if AND (Parallel) split of the activity is correct corresponding to
    * XPDL spec rules.
    * 
    * @param act Activity to check for AND/Parallel split.
    * @return true if AND (Parallel) split of the activity is correct corresponding to
    *         XPDL spec rules.
    */
   protected boolean checkANDSplit(Activity act) {
      return !hasAnyPostcondition(act);
   }

   /**
    * Returns true if XOR (Exclusive) split of the activity is correct corresponding to
    * XPDL spec rules.
    * 
    * @param act Activity to check for XOR/Exclusive split.
    * @return true if XOR (Exclusive) split of the activity is correct corresponding to
    *         XPDL spec rules.
    */
   protected boolean checkXORSplit(Activity act) {
      // if activity has any postcondition, it must have an otherwise transition
      if (hasAnyPostcondition(act)) {
         Set ots = XMLUtil.getOutgoingTransitions(act);
         Iterator trs = ots.iterator();
         while (trs.hasNext()) {
            Transition t = (Transition) trs.next();
            if (t.getCondition().getType().equals(XPDLConstants.CONDITION_TYPE_OTHERWISE)) {
               return true;
            }
         }
         return false;
      }

      return true;
   }

   /**
    * Returns true if given activity has at least one outgoing transition with condition
    * expression defined.
    * 
    * @param act Activity to check for postconditions.
    * @return true if given activity has at least one outgoing transition with condition
    *         expression defined.
    */
   protected boolean hasAnyPostcondition(Activity act) {
      Set outL = XMLUtil.getOutgoingTransitions(act);
      Iterator it = outL.iterator();
      while (it.hasNext()) {
         if (!((Transition) it.next()).getCondition().toValue().equals("")) {
            return true;
         }
      }
      return false;
   }

   // ************************** GRAPH CONNECTIONS CHECKING
   // ****************************

   /**
    * Returns true if graph connections for the given WorkflowProcess or ActivitySet
    * element are correct.
    * 
    * @param wpOrAs WorkflowProcess or ActivitySet instance which graph connections are
    *           checked.
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    * @return true if graph connections for the given WorkflowProcess or ActivitySet
    *         element are correct.
    */
   protected boolean checkGraphConnectionsForWpOrAs(XMLCollectionElement wpOrAs,
                                                    List existingErrors,
                                                    boolean fullCheck) {
      if (wpOrAs == null)
         return false;

      boolean isWellConnected = true;

      Collection acts = ((Activities) wpOrAs.get("Activities")).toElements();
      if (acts == null || acts.size() == 0) {
         return true;
      }

      Set startActs = null;
      Set endActs = null;
      if (fullCheck || isWellConnected) {
         startActs = XMLUtil.getStartingActivities(wpOrAs);
         boolean allowUndefinedStart = properties.getProperty(StandardPackageValidator.ALLOW_UNDEFINED_START,
                                                              "true")
            .equals("true");
         if (startActs.size() == 0
             && (!allowUndefinedStart || (wpOrAs instanceof ActivitySet))) {
            isWellConnected = false;
            XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                             XMLValidationError.SUB_TYPE_LOGIC,
                                                             XPDLValidationErrorIds.ERROR_NO_STARTING_ACTIVITY,
                                                             "",
                                                             wpOrAs);
            existingErrors.add(verr);
         }
      }
      if (fullCheck || isWellConnected) {
         endActs = XMLUtil.getEndingActivities(wpOrAs);
         boolean allowUndefinedEnd = properties.getProperty(StandardPackageValidator.ALLOW_UNDEFINED_END,
                                                            "true")
            .equals("true");
         if (endActs.size() == 0
             && (!allowUndefinedEnd || (wpOrAs instanceof ActivitySet))) {
            isWellConnected = false;
            XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                             XMLValidationError.SUB_TYPE_LOGIC,
                                                             XPDLValidationErrorIds.ERROR_NO_ENDING_ACTIVITY,
                                                             "",
                                                             wpOrAs);
            existingErrors.add(verr);
         }
      }
      if (fullCheck || isWellConnected) {
         Iterator it = acts.iterator();
         while (it.hasNext()) {
            Activity act = (Activity) it.next();
            boolean wc = checkActivityConnection(act, existingErrors, fullCheck);
            if (!wc) {
               isWellConnected = false;
               if (!fullCheck) {
                  break;
               }
            }
         }
      }

      return isWellConnected;
   }

   /**
    * Checks if given activity is well connected.
    * 
    * @param act Activity to check.
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    * @return String describing the error, or empty string if there is no connection error
    *         for giving activity.
    */
   protected boolean checkActivityConnection(Activity act,
                                             List existingErrors,
                                             boolean fullCheck) {
      return true;
   }

   /**
    * Returns true if graph connections for the artifact elements are correct.
    * 
    * @param pkg Package to check.
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    * @return true if graph connections for the artifact elements are correct.
    */
   protected boolean checkGraphConnectionsForArtifacts(Package pkg,
                                                       List existingErrors,
                                                       boolean fullCheck) {
      if (pkg == null)
         return false;

      boolean isWellConnected = true;

      Collection arts = pkg.getArtifacts().toElements();
      if (arts == null || arts.size() == 0) {
         return true;
      }

      Iterator it = arts.iterator();
      while (it.hasNext()) {
         Artifact art = (Artifact) it.next();
         boolean wc = checkArtifactConnection(art, existingErrors, fullCheck);
         if (!wc) {
            isWellConnected = false;
            if (!fullCheck) {
               break;
            }
         }
      }

      return isWellConnected;
   }

   /**
    * Returns true if given artifact is properly connected.
    * 
    * @param art Artifact to check.
    * @param existingErrors List of existing errors.
    * @param fullCheck If false, validation will stop after the first error is found.
    * @return true if given artifact is properly connected.
    */
   protected boolean checkArtifactConnection(Artifact art,
                                             List existingErrors,
                                             boolean fullCheck) {
      if (XMLUtil.getOutgoingAssociations(art).size() == 0
          && XMLUtil.getIncomingAssociations(art).size() == 0) {
         XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                          XMLValidationError.SUB_TYPE_CONNECTION,
                                                          XPDLValidationErrorIds.ERROR_IMPROPERLY_CONNECTED_ARTIFACT_NO_ASSOCIATION,
                                                          "",
                                                          art);
         existingErrors.add(verr);
         return false;
      }
      return true;
   }

   /**
    * Checks if Formal and Actual parameters are matching.
    * 
    * @param fps FormalParameters to check.
    * @param aps ActualParameters to check.
    * @param existingErrors List of existing errors.
    * @param checkExpression true if actual parameter expression for corresponding IN type
    *           formal parameter should be checked.
    * @param fullCheck If false, validation will stop after the first error is found.
    */
   protected void checkParameterMatching(FormalParameters fps,
                                         ActualParameters aps,
                                         List existingErrors,
                                         boolean checkExpression,
                                         boolean fullCheck) {

      if (fps.size() != aps.size()) {
         XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                          XMLValidationError.SUB_TYPE_LOGIC,
                                                          XPDLValidationErrorIds.ERROR_FORMAL_AND_ACTUAL_PARAMETERS_NUMBER_MISSMATCH,
                                                          "",
                                                          aps);
         existingErrors.add(verr);
      }

      if (!(fullCheck || existingErrors.size() == 0)) {
         return;
      }

      for (int i = 0; i < fps.size(); i++) {
         FormalParameter fp = (FormalParameter) fps.get(i);
         if (aps.size() - 1 < i) {
            return;
         }
         ActualParameter ap = (ActualParameter) aps.get(i);

         String fpMode = fp.getMode();
         if (fpMode.equals(XPDLConstants.FORMAL_PARAMETER_MODE_IN) && !checkExpression) {
            continue;
         }

         // find the type of formal param.
         DataType fpdt = fp.getDataType();
         DataTypes fpdtt = fpdt.getDataTypes();
         XMLElement fpType = fpdtt.getChoosen();

         // find the type of actual param.
         Map idToDFOrFP = getActualParameterOrConditionChoices(aps);
         String apWRD = ap.toValue();
         XMLCollectionElement ce = (XMLCollectionElement) idToDFOrFP.get(apWRD);

         // if the actual parameter is an expression, and the mode is not
         // IN, return 2, which signals that parameter types don't match
         if (ce == null) {
            if (!fpMode.equals(XPDLConstants.FORMAL_PARAMETER_MODE_IN)) {
               XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                                XMLValidationError.SUB_TYPE_LOGIC,
                                                                XPDLValidationErrorIds.ERROR_NON_EXISTING_VARIABLE_REFERENCE,
                                                                apWRD,
                                                                ap);
               existingErrors.add(verr);
               continue;
            }

            boolean evaluateToString = false;
            if (fpType instanceof BasicType) {
               String fpAT = ((BasicType) fpType).getType();
               if (fpAT.equals(XPDLConstants.BASIC_TYPE_STRING) && !fp.getIsArray()) {
                  evaluateToString = true;
               }
            }
            // if the formal parameter mode is IN, do not check validity if not
            // neccessary, because
            // that could be expression written in any scripting language
            Map vars = getActualParameterOrConditionChoices(aps);
            boolean cbe = apWRD.equalsIgnoreCase("null")
                          || canBeExpression(apWRD, vars, evaluateToString);

            if (!cbe) {
               if (fpType instanceof BasicType && !fp.getIsArray()) {
                  String fpAT = ((BasicType) fpType).getType();
                  if (fpAT.equals(XPDLConstants.BASIC_TYPE_INTEGER)) {
                     try {
                        new Integer(apWRD);
                        cbe = true;
                     } catch (Exception ex) {
                        if (apWRD.toLowerCase().indexOf("short") >= 0
                            || apWRD.toLowerCase().indexOf("integer") >= 0
                            || apWRD.toLowerCase().indexOf("long") >= 0) {
                           cbe = true;
                        }
                     }
                  } else if (fpAT.equals(XPDLConstants.BASIC_TYPE_FLOAT)) {
                     try {
                        new Double(apWRD);
                        cbe = true;
                     } catch (Exception ex) {
                        if (apWRD.toLowerCase().indexOf("short") >= 0
                            || apWRD.toLowerCase().indexOf("integer") >= 0
                            || apWRD.toLowerCase().indexOf("long") >= 0
                            || apWRD.toLowerCase().indexOf("float") >= 0
                            || apWRD.toLowerCase().indexOf("double") >= 0) {
                           cbe = true;
                        }
                     }
                  } else if (fpAT.equals(XPDLConstants.BASIC_TYPE_BOOLEAN)) {
                     if (apWRD.equals("false")
                         || apWRD.equals("true")
                         || apWRD.toLowerCase().indexOf("boolean") >= 0) {
                        cbe = true;
                     }
                  }
               }
            }
            String type = XMLValidationError.TYPE_WARNING;
            if (!cbe || !(type = additionalExpressionCheck(ap, apWRD, vars)).equals("")) {
               XMLValidationError verr = new XMLValidationError(type,
                                                                XMLValidationError.SUB_TYPE_LOGIC,
                                                                XPDLValidationErrorIds.WARNING_ACTUAL_PARAMETER_EXPRESSION_POSSIBLY_INVALID,
                                                                apWRD,
                                                                ap);
               existingErrors.add(verr);
               if (!fullCheck)
                  return;
            }

            continue;
         }

         // if AP is a reference to a variable, check data types
         XMLElement apType = null;
         DataType apdt = (DataType) ce.get("DataType");
         DataTypes apdtt = apdt.getDataTypes();
         apType = apdtt.getChoosen();
         boolean invalidType = false;
         boolean apIsArray = new Boolean(ce.get("IsArray").toValue()).booleanValue();
         if (fpType.getClass().equals(apType.getClass()) && apIsArray==fp.getIsArray()) {
            // if this is BasicType check for subtype matching
            if (fpType instanceof BasicType) {
               String fpAT = ((BasicType) fpType).getType();
               String apAT = ((BasicType) apType).getType();
               if (!fpAT.equals(apAT)) {
                  invalidType = true;
               }
            }
            // if this is EnumerationType check for Enumeration values matching
            else if (fpType instanceof EnumerationType) {
               // first check the size of enums
               if (((EnumerationType) fpType).size() != ((EnumerationType) apType).size()) {
                  invalidType = true;
               } else {
                  // check the enum elements values
                  for (int j = 0; j < ((EnumerationType) fpType).size(); j++) {
                     EnumerationValue evFP = (EnumerationValue) ((EnumerationType) fpType).get(j);
                     EnumerationValue evAP = (EnumerationValue) ((EnumerationType) apType).get(j);
                     if (!evFP.getName().equals(evAP.getName())) {
                        invalidType = true;
                     }
                  }
               }
            }
            // if this is DeclaredType check if their IDs are the same
            else if (fpType instanceof DeclaredType) {
               if (!((DeclaredType) fpType).getId()
                  .equals(((DeclaredType) apType).getId())) {
                  invalidType = true;
               }
            }
         } else {
            invalidType = true;
         }
         if (invalidType) {
            XMLValidationError verr = new XMLValidationError(XMLValidationError.TYPE_ERROR,
                                                             XMLValidationError.SUB_TYPE_LOGIC,
                                                             XPDLValidationErrorIds.ERROR_INVALID_ACTUAL_PARAMETER_VARIABLE_TYPE,
                                                             "",
                                                             ap);
            existingErrors.add(verr);
            if (!fullCheck)
               return;
         }
      }
   }

   /**
    * Utility method that prepares message string.
    * 
    * @param msg Input message.
    * @return Empty string is msg parameter is null, the msg parameter with appended "; "
    *         otherwise.
    */
   public String prepareMessageString(String msg) {
      if (msg != null) {
         msg = msg + "; ";
      } else {
         msg = "";
      }
      return msg;
   }

   /**
    * Returns true if the given list with XMLValidationElement elements contains at least
    * one XMLValidationElement which type is ERROR.
    * 
    * @param l List of XMLValidationError elements.
    * @return true if the given list with XMLValidationElement elements contains at least
    *         one XMLValidationElement which type is ERROR.
    */
   public boolean hasErrors(List l) {
      for (int i = 0; i < l.size(); i++) {
         XMLValidationError verr = (XMLValidationError) l.get(i);
         if (verr.getType().equals(XMLValidationError.TYPE_ERROR)) {
            return true;
         }
      }
      return false;
   }

   /**
    * Returns true if the given 'expr' string can be a valid expression.
    * 
    * @param expr String to check.
    * @param allVars All the possible variables that can exist in the expression.
    * @param evaluateToString true if the expression result should result in String.
    * @return true if the given 'expr' string can be a valid expression.
    */
   public boolean canBeExpression(String expr, Map allVars, boolean evaluateToString) {
      String exprToParse = new String(expr);

      boolean canBeExpression = expr.indexOf(".") >= 0;

      if (evaluateToString
          && (expr.startsWith("\"") && expr.endsWith("\""))
          || (expr.startsWith("'") && expr.endsWith("'"))) {
         canBeExpression = true;
      }
      // System.err.println("CBE1="+canBeExpression);
      if (!canBeExpression) {
         boolean validVarId = XMLUtil.isIdValid(exprToParse);
         if (validVarId && allVars.containsKey(exprToParse)) {
            canBeExpression = true;
         }
      }
      // System.err.println("CBE2="+canBeExpression);

      if (!canBeExpression) {
         Iterator it = allVars.keySet().iterator();
         while (it.hasNext()) {
            String varId = (String) it.next();
            if (XMLUtil.getUsingPositions(exprToParse, varId, allVars).size() > 0) {
               // System.err.println("CBE2.5 - can be expr because var "+varId+" is
               // possibly used");
               canBeExpression = true;
               break;
            }
         }
      }
      // System.err.println("CBE3="+canBeExpression);

      return canBeExpression;
   }

   /**
    * Additionally checks expressions. This implementation does nothing.
    * 
    * @param el Element to check.
    * @param expr String to check.
    * @param vars All the possible variables that can exist in the expression.
    * @return An empty string.
    */
   public String additionalExpressionCheck(XMLElement el, String expr, Map vars) {
      return "";
   }

   /**
    * Creates new StandardPackageValidator instance. The classes that extend this
    * validator should provide their own implementation of this method.
    * 
    * @return New StandardPackageValidator instance.
    */
   protected StandardPackageValidator createValidatorInstance() {
      return new StandardPackageValidator();
   }

   /**
    * Returns the map of variables that can be used within actual parameter or condition
    * expressions.
    * 
    * @param el Element for which the choices are retrieved.
    * @return The map of variables that can be used within actual parameter or condition
    *         expressions.
    */
   protected Map getActualParameterOrConditionChoices(XMLElement el) {
      return XMLUtil.getPossibleVariables(XMLUtil.getWorkflowProcess(el));
   }

   /**
    * Returns the map of variables that can be used within deadline condition expressions.
    * 
    * @param el Element for which the choices are retrieved.
    * @return the map of variables that can be used within deadline condition expressions.
    */
   protected Map getDeadlineConditionChoices(XMLElement el) {
      return XMLUtil.getPossibleVariables(XMLUtil.getWorkflowProcess(el));
   }

   /**
    * Returns the map of variables that can be used within performer expressions.
    * 
    * @param el Element for which the choices are retrieved.
    * @return the map of variables that can be used within performer expressions.
    */
   protected Map getPerformerChoices(XMLElement el) {
      return XMLUtil.getPossibleVariables(XMLUtil.getWorkflowProcess(el));
   }

}
