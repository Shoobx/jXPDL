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
 * The class containing various constants related to XPDL validation.
 */
public final class XPDLValidationErrorIds {

   /** Constant representing error for invalid Id. */
   public static final String ERROR_INVALID_ID = "ERROR_INVALID_ID";

   /** Constant representing error for non-unique Id. */
   public static final String ERROR_NON_UNIQUE_ID = "ERROR_NON_UNIQUE_ID";

   /** Constant representing error for non-allowed length of the elements' value. */
   public static final String ERROR_UNALLOWED_LENGTH = "ERROR_UNALLOWED_LENGTH";
   
   /** Constant representing error for non-existing workflow process reference. */
   public static final String ERROR_NON_EXISTING_WORKFLOW_PROCESS_REFERENCE = "ERROR_NON_EXISTING_WORKFLOW_PROCESS_REFERENCE";

   /** Constant representing error for non-existing application reference. */
   public static final String ERROR_NON_EXISTING_APPLICATION_REFERENCE = "ERROR_NON_EXISTING_APPLICATION_REFERENCE";

   /** Constant representing error for non-existing activity set reference. */
   public static final String ERROR_NON_EXISTING_ACTIVITY_SET_REFERENCE = "ERROR_NON_EXISTING_ACTIVITY_SET_REFERENCE";

   /** Constant representing error for non-existing type declaration reference. */
   public static final String ERROR_NON_EXISTING_TYPE_DECLARATION_REFERENCE = "ERROR_NON_EXISTING_TYPE_DECLARATION_REFERENCE";

   /** Constant representing error for non-existing participant reference. */
   public static final String ERROR_NON_EXISTING_PARTICIPANT_REFERENCE = "ERROR_NON_EXISTING_PARTICIPANT_REFERENCE";

   /** Constant representing error for non-existing transition reference. */
   public static final String ERROR_NON_EXISTING_TRANSITION_REFERENCE = "ERROR_NON_EXISTING_TRANSITION_REFERENCE";

   /** Constant representing error for non-existing activity reference. */
   public static final String ERROR_NON_EXISTING_ACTIVITY_REFERENCE = "ERROR_NON_EXISTING_ACTIVITY_REFERENCE";

   /** Constant representing error for non-existing activity or artifact reference. */
   public static final String ERROR_NON_EXISTING_ACTIVITY_OR_ARTIFACT_REFERENCE = "ERROR_NON_EXISTING_ACTIVITY_OR_ARTIFACT_REFERENCE";

   /** Constant representing error for non-existing variable reference. */
   public static final String ERROR_NON_EXISTING_VARIABLE_REFERENCE = "ERROR_NON_EXISTING_VARIABLE_REFERENCE";

   /** Constant representing error for non-existing external package reference. */
   public static final String ERROR_NON_EXISTING_EXTERNAL_PACKAGE_REFERENCE = "ERROR_NON_EXISTING_EXTERNAL_PACKAGE_REFERENCE";

   /** Constant representing error of undefined workflow process. */
   public static final String ERROR_WORKFLOW_PROCESS_NOT_DEFINED = "ERROR_WORKFLOW_PROCESS_NOT_DEFINED";

   /** Constant representing error of undefined activity set. */
   public static final String ERROR_ACTIVITY_SET_NOT_DEFINED = "ERROR_ACTIVITY_SET_NOT_DEFINED";

   /** Constant representing error of undefined tools. */
   public static final String ERROR_NO_TOOLS_DEFINED = "ERROR_NO_TOOLS_DEFINED";

   /**
    * Constant representing error of not properly handling deadline - missing specified
    * exception transition or default exception transition.
    */
   public static final String ERROR_DEADLINE_EXCEPTION_NOT_PROPERLY_HANDLED_MISSING_SPECIFIED_EXCEPTION_TRANSITION_OR_DEFAULT_EXCEPTION_TRANSITION = "ERROR_DEADLINE_EXCEPTION_NOT_PROPERLY_HANDLED_MISSING_SPECIFIED_EXCEPTION_TRANSITION_OR_DEFAULT_EXCEPTION_TRANSITION";

   /**
    * Constant representing error of not properly handling deadline - missing specified
    * exception transition or default exception transition.
    */
   public static final String ERROR_DEADLINES_NOT_PROPERLY_HANDLED_NO_EXCEPTIONAL_TRANSITIONS = "ERROR_DEADLINES_NOT_PROPERLY_HANDLED_NO_EXCEPTIONAL_TRANSITIONS";

   /**
    * Constant representing error of multiple synchronous deadlines defined for the
    * activity.
    */
   public static final String ERROR_MULTIPLE_SYNC_DEADLINES_DEFINED = "ERROR_MULTIPLE_SYNC_DEADLINES_DEFINED";

   /** Constant representing warning for invalid XPDL version. */
   public static final String WARNING_INVALID_XPDL_VERSION = "WARNING_INVALID_XPDL_VERSION";

   /** Constant representing warning for activity that can't have performer. */
   public static final String WARNING_ACTIVITY_CANNOT_HAVE_PERFORMER = "WARNING_ACTIVITY_CANNOT_HAVE_PERFORMER";

   /**
    * Constant representing warning for activity which performer expression is possibly
    * invalid.
    */
   public static final String WARNING_PERFORMER_EXPRESSION_POSSIBLY_INVALID = "WARNING_PERFORMER_EXPRESSION_POSSIBLY_INVALID";

   /**
    * Constant representing warning for actual parameter which expression is possibly
    * invalid.
    */
   public static final String WARNING_ACTUAL_PARAMETER_EXPRESSION_POSSIBLY_INVALID = "WARNING_ACTUAL_PARAMETER_EXPRESSION_POSSIBLY_INVALID";

   /**
    * Constant representing warning for Initial Value which expression is possibly
    * invalid.
    */
   public static final String WARNING_INITIAL_VALUE_EXPRESSION_POSSIBLY_INVALID = "WARNING_INITIAL_VALUE_EXPRESSION_POSSIBLY_INVALID";

   /**
    * Constant representing warning for conditional transition that does not define an
    * expression.
    */
   public static final String WARNING_CONDITIONAL_TRANSITION_WITHOUT_EXPRESSION = "WARNING_CONDITIONAL_TRANSITION_WITHOUT_EXPRESSION";

   /**
    * Constant representing warning for exception transition that does not define an
    * expression.
    */
   public static final String WARNING_EXCEPTION_TRANSITION_WITHOUT_EXPRESSION = "WARNING_EXCEPTION_TRANSITION_WITHOUT_EXPRESSION";

   /**
    * Constant representing warning for unconditional transition that has expression
    * defined.
    */
   public static final String WARNING_UNCONDITIONAL_TRANSITION_WITH_EXPRESSION = "WARNING_UNCONDITIONAL_TRANSITION_WITH_EXPRESSION";

   /**
    * Constant representing warning for default exception transition that has expression
    * defined.
    */
   public static final String WARNING_DEFAULT_EXCEPTION_TRANSITION_WITH_EXPRESSION = "WARNING_DEFAULT_EXCEPTION_TRANSITION_WITH_EXPRESSION";

   /** Constant representing warning for otherwise transition that has expression defined. */
   public static final String WARNING_OTHERWISE_TRANSITION_WITH_EXPRESSION = "WARNING_OTHERWISE_TRANSITION_WITH_EXPRESSION";

   /**
    * Constant representing warning for transition's condition which expression is
    * possibly invalid.
    */
   public static final String WARNING_CONDITION_EXPRESSION_POSSIBLY_INVALID = "WARNING_CONDITION_EXPRESSION_POSSIBLY_INVALID";

   /** Constant representing warning for deadline which expression is possibly invalid. */
   public static final String WARNING_DEADLINE_EXPRESSION_POSSIBLY_INVALID = "WARNING_DEADLINE_EXPRESSION_POSSIBLY_INVALID";

   /** Constant representing warning for possibly invalid external package. */
   public static final String WARNING_POSSIBLY_INVALID_EXTERNAL_PACKAGE = "WARNING_POSSIBLY_INVALID_EXTERNAL_PACKAGE";

   /** Constant representing warning for unused variable. */
   public static final String WARNING_UNUSED_VARIABLE = "WARNING_UNUSED_VARIABLE";

   /** Constant representing warning for unused application. */
   public static final String WARNING_UNUSED_APPLICATION = "WARNING_UNUSED_APPLICATION";

   /** Constant representing warning for unused participant. */
   public static final String WARNING_UNUSED_PARTICIPANT = "WARNING_UNUSED_PARTICIPANT";

   /** Constant representing error for actual/formal parameter number miss-match. */
   public static final String ERROR_FORMAL_AND_ACTUAL_PARAMETERS_NUMBER_MISSMATCH = "ERROR_FORMAL_AND_ACTUAL_PARAMETERS_NUMBER_MISSMATCH";

   /**
    * Constant representing error for transiton refs and outgoing transition number
    * miss-match.
    */
   public static final String ERROR_TRANSITION_REFS_AND_OUTGOING_TRANSITION_NUMBER_MISSMATCH = "ERROR_TRANSITION_REFS_AND_OUTGOING_TRANSITION_NUMBER_MISSMATCH";

   /** Constant representing error for invalid actual parameter variable type. */
   public static final String ERROR_INVALID_ACTUAL_PARAMETER_VARIABLE_TYPE = "ERROR_INVALID_ACTUAL_PARAMETER_VARIABLE_TYPE";

   /**
    * Constant representing error for multiple outgoing transitions of activity without
    * split type defined.
    */
   public static final String ERROR_MULTIPLE_OUTGOING_TRANSITIONS_WITHOUT_SPLIT_TYPE_DEFINED = "ERROR_MULTIPLE_OUTGOING_TRANSITIONS_WITHOUT_SPLIT_TYPE_DEFINED";

   /**
    * Constant representing error for multiple incoming transitions of activity without
    * split type defined.
    */
   public static final String ERROR_MULTIPLE_INCOMING_TRANSITIONS_WITHOUT_JOIN_TYPE_DEFINED = "ERROR_MULTIPLE_INCOMING_TRANSITIONS_WITHOUT_JOIN_TYPE_DEFINED";

   /**
    * Constant representing error for activity that has more than one otherwise
    * transitions.
    */
   public static final String ERROR_MORE_THAN_ONE_OTHERWISE_TRANSITION = "ERROR_MORE_THAN_ONE_OTHERWISE_TRANSITION";

   /**
    * Constant representing error for activity that has more than one default exception
    * transitions.
    */
   public static final String ERROR_MORE_THAN_ONE_DEFAULT_EXCEPTION_TRANSITION = "ERROR_MORE_THAN_ONE_DEFAULT_EXCEPTION_TRANSITION";

   /**
    * Constant representing error for activity that is the part of the loop in
    * loop-blocked graph conformance mode.
    */
   public static final String ERROR_LOOP_CONTAINED_ACTIVITY_IN_LOOP_BLOCKED_MODE = "ERROR_LOOP_CONTAINED_ACTIVITY_IN_LOOP_BLOCKED_MODE";

   /**
    * Constant representing error for cyclic activity graph in loop-blocked graph
    * conformance mode.
    */
   public static final String ERROR_CYCLIC_GRAPH_IN_LOOP_BLOCKED_MODE = "ERROR_CYCLIC_GRAPH_IN_LOOP_BLOCKED_MODE";

   /**
    * Constant representing error for multiple starting activities in full-blocked graph
    * conformance mode.
    */
   public static final String ERROR_MULTIPLE_STARTING_ACTIVITIES_IN_FULL_BLOCKED_MODE = "ERROR_MULTIPLE_STARTING_ACTIVITIES_IN_FULL_BLOCKED_MODE";

   /**
    * Constant representing error for multiple ending activities in full-blocked graph
    * conformance mode.
    */
   public static final String ERROR_MULTIPLE_ENDING_ACTIVITIES_IN_FULL_BLOCKED_MODE = "ERROR_MULTIPLE_ENDING_ACTIVITIES_IN_FULL_BLOCKED_MODE";

   /**
    * Constant representing error for split/join miss-match (more splits than joins) in
    * full-blocked graph conformance mode.
    */
   public static final String ERROR_SPLIT_JOIN_MISSMATCH_IN_FULL_BLOCKED_MODE_MORE_SPLITS = "ERROR_SPLIT_JOIN_MISSMATCH_IN_FULL_BLOCKED_MODE_MORE_SPLITS";

   /**
    * Constant representing error for split/join miss-match (more joins than splits) in
    * full-blocked graph conformance mode.
    */
   public static final String ERROR_SPLIT_JOIN_MISSMATCH_IN_FULL_BLOCKED_MODE_MORE_JOINS = "ERROR_SPLIT_JOIN_MISSMATCH_IN_FULL_BLOCKED_MODE_MORE_JOINS";

   /**
    * Constant representing error for split/join type miss-match in full-blocked graph
    * conformance mode.
    */
   public static final String ERROR_SPLIT_JOIN_MISSMATCH_IN_FULL_BLOCKED_MODE_DIFFERENT_TYPES = "ERROR_SPLIT_JOIN_MISSMATCH_IN_FULL_BLOCKED_MODE_DIFFERENT_TYPES";

   /**
    * Constant representing error for conditional transition for and split in full-blocked
    * graph conformance mode.
    */
   public static final String ERROR_CONDITIONAL_TRANSITION_FOR_AND_SPLIT_IN_FULL_BLOCKED_MODE = "ERROR_CONDITIONAL_TRANSITION_FOR_AND_SPLIT_IN_FULL_BLOCKED_MODE";

   /**
    * Constant representing error for missing otherwise transition for Exclusive split in
    * full-blocked graph conformance mode.
    */
   public static final String ERROR_NO_OTHERWISE_TRANSITION_FOR_XOR_SPLIT_IN_FULL_BLOCKED_MODE = "ERROR_NO_OTHERWISE_TRANSITION_FOR_XOR_SPLIT_IN_FULL_BLOCKED_MODE";

   /**
    * Constant representing error for no corresponding join activity in full-blocked graph
    * conformance mode.
    */
   public static final String ERROR_NO_CORRESPONDING_JOIN_ACTIVITY_IN_FULL_BLOCKED_MODE = "ERROR_NO_CORRESPONDING_JOIN_ACTIVITY_IN_FULL_BLOCKED_MODE";

   /**
    * Constant representing error for no corresponding join activity type (Exclusive used
    * instead of Parallel) in full-blocked graph conformance mode.
    */
   public static final String ERROR_NO_CORRESPONDING_JOIN_ACTIVITY_TYPE_IN_FULL_BLOCKED_MODE_AND_XOR = "ERROR_NO_CORRESPONDING_JOIN_ACTIVITY_TYPE_IN_FULL_BLOCKED_MODE_AND_XOR";

   /**
    * Constant representing error for no corresponding join activity type (Parallel used
    * instead of Exclusive) in full-blocked graph conformance mode.
    */
   public static final String ERROR_NO_CORRESPONDING_JOIN_ACTIVITY_TYPE_IN_FULL_BLOCKED_MODE_XOR_AND = "ERROR_NO_CORRESPONDING_JOIN_ACTIVITY_TYPE_IN_FULL_BLOCKED_MODE_XOR_AND";

   /** Constant representing error for non existence of starting activity. */
   public static final String ERROR_NO_STARTING_ACTIVITY = "ERROR_NO_STARTING_ACTIVITY";

   /** Constant representing error for non existence of ending activity. */
   public static final String ERROR_NO_ENDING_ACTIVITY = "ERROR_NO_ENDING_ACTIVITY";

   /**
    * Constant representing error for improperly connected activity which has multiple
    * incoming transitions and only one is allowed.
    */
   public static final String ERROR_IMPROPERLY_CONNECTED_ACTIVITY_MULTIPLE_INCOMING_TRANSITIONS = "ERROR_IMPROPERLY_CONNECTED_ACTIVITY_MULTIPLE_INCOMING_TRANSITIONS";

   /**
    * Constant representing error for improperly connected activity which has multiple
    * incoming transitions and only one is allowed.
    */
   public static final String ERROR_IMPROPERLY_CONNECTED_ACTIVITY_MULTIPLE_OUTGOING_TRANSITIONS = "ERROR_IMPROPERLY_CONNECTED_ACTIVITY_MULTIPLE_OUTGOING_TRANSITIONS";

   /** Constant representing error for improperly connected artifact. */
   public static final String ERROR_IMPROPERLY_CONNECTED_ARTIFACT_NO_ASSOCIATION = "ERROR_IMPROPERLY_CONNECTED_ARTIFACT_NO_ASSOCIATION";

   /** Constant representing error for multiple connection between two objects. */
   public static final String ERROR_MULTIPLE_CONNECTIONS = "ERROR_MULTIPLE_CONNECTIONS";

   /** Constant representing error for invalid value for priority. */
   public static final String ERROR_PRIORITY_INVALID_VALUE = "ERROR_PRIORITY_INVALID_VALUE";

   /** Constant representing error for invalid value for limit. */
   public static final String ERROR_LIMIT_INVALID_VALUE = "ERROR_LIMIT_INVALID_VALUE";

   /** Constant representing error for the start event having incoming transition. */
   public static final String ERROR_CONNECTION_TO_START_EVENT_NOT_ALLOWED = "ERROR_CONNECTION_TO_START_EVENT_NOT_ALLOWED";

   /** Constant representing error for the end event having outgoing transition. */
   public static final String ERROR_CONNECTION_FROM_END_EVENT_NOT_ALLOWED = "ERROR_CONNECTION_FROM_END_EVENT_NOT_ALLOWED";

   /** Constant representing error for the start event not having outgoing transition. */
   public static final String ERROR_START_EVENT_NOT_CONNECTED = "ERROR_START_EVENT_NOT_CONNECTED";

   /** Constant representing error for the end event not having incoming transition. */
   public static final String ERROR_END_EVENT_NOT_CONNECTED = "ERROR_END_EVENT_NOT_CONNECTED";

   /**
    * Constant representing error warning that connection from the start event to the
    * activity is missing.
    */
   public static final String WARNING_CONNECTION_FROM_START_EVENT_IS_MISSING = "WARNING_CONNECTION_FROM_START_EVENT_IS_MISSING";

   /**
    * Constant representing error warning that connection from activity to the end event
    * is missing.
    */
   public static final String WARNING_CONNECTION_TO_END_EVENT_IS_MISSING = "WARNING_CONNECTION_TO_END_EVENT_IS_MISSING";

}
