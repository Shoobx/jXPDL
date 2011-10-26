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
 * The class containing various constants, mostly related to XPDL schema.
 */
public final class XPDLConstants {

   /** Constant representing XSD boolean value 'true'. */
   public static final String XSD_BOOLEAN_TRUE = "true";

   /** Constant representing XSD boolean value 'false'. */
   public static final String XSD_BOOLEAN_FALSE = "false";

   /** Constant representing horizontal pool orientation. */
   public static final String POOL_ORIENTATION_HORIZONTAL = "HORIZONTAL";

   /** Constant representing vertical pool orientation. */
   public static final String POOL_ORIENTATION_VERTICAL = "VERTICAL";

   /** Constant representing task' web service implementation. */
   public static final String TASK_IMPLEMENTATION_WEBSERVICE = "WebService";

   /** Constant representing task' other implementation. */
   public static final String TASK_IMPLEMENTATION_OTHER = "Other";

   /** Constant representing task' web unspecified implementation. */
   public static final String TASK_IMPLEMENTATION_UNSPECIFIED = "Unspecified";

   /** Constant representing end point's wsdl type. */
   public static final String ENDPOINT_TYPE_WSDL = "WSDL";

   /** Constant representing end point's service type. */
   public static final String ENDPOINT_TYPE_SERVICE = "Service";

   /** Constant representing role's type my role. */
   public static final String ROLE_TYPE_MYROLE = "MyRole";

   /** Constant representing role's type parent role. */
   public static final String ROLE_TYPE_PARTNERROLE = "PartnerRole";

   /** Constant representing event's type none. */
   public static final String EVENT_NONE = "None";

   /** Constant representing basic type string. */
   public static final String BASIC_TYPE_STRING = "STRING";

   /** Constant representing basic type float. */
   public static final String BASIC_TYPE_FLOAT = "FLOAT";

   /** Constant representing basic type integer. */
   public static final String BASIC_TYPE_INTEGER = "INTEGER";

   /** Constant representing basic type reference. */
   public static final String BASIC_TYPE_REFERENCE = "REFERENCE";

   /** Constant representing basic type datetime. */
   public static final String BASIC_TYPE_DATETIME = "DATETIME";

   /** Constant representing basic type boolean. */
   public static final String BASIC_TYPE_BOOLEAN = "BOOLEAN";

   /** Constant representing basic type performer. */
   public static final String BASIC_TYPE_PERFORMER = "PERFORMER";

   /** Constant representing condition type none. */
   public static final String CONDITION_TYPE_NONE = "";

   /** Constant representing condition type condition. */
   public static final String CONDITION_TYPE_CONDITION = "CONDITION";

   /** Constant representing condition type otherwise. */
   public static final String CONDITION_TYPE_OTHERWISE = "OTHERWISE";

   /** Constant representing condition type exception. */
   public static final String CONDITION_TYPE_EXCEPTION = "EXCEPTION";

   /** Constant representing condition type default exception. */
   public static final String CONDITION_TYPE_DEFAULTEXCEPTION = "DEFAULTEXCEPTION";

   /** Constant representing graph conformance type none. */
   public static final String GRAPH_CONFORMANCE_NONE = "";

   /** Constant representing graph conformance type full-blocked. */
   public static final String GRAPH_CONFORMANCE_FULL_BLOCKED = "FULL_BLOCKED";

   /** Constant representing graph conformance loop-blocked. */
   public static final String GRAPH_CONFORMANCE_LOOP_BLOCKED = "LOOP_BLOCKED";

   /** Constant representing graph conformance non-blocked. */
   public static final String GRAPH_CONFORMANCE_NON_BLOCKED = "NON_BLOCKED";

   /** Constant representing execution type none. */
   public static final String EXECUTION_NONE = "";

   /** Constant representing execution type asynchronous. */
   public static final String EXECUTION_ASYNCHR = "ASYNCHR";

   /** Constant representing execution type synchronous. */
   public static final String EXECUTION_SYNCHR = "SYNCHR";

   /** Constant representing formal parameter's mode in. */
   public static final String FORMAL_PARAMETER_MODE_IN = "IN";

   /** Constant representing formal parameter's mode out. */
   public static final String FORMAL_PARAMETER_MODE_OUT = "OUT";

   /** Constant representing formal parameter's mode inout. */
   public static final String FORMAL_PARAMETER_MODE_INOUT = "INOUT";

   /** Constant representing join or split type none. */
   public static final String JOIN_SPLIT_TYPE_NONE = "";

   /** Constant representing join or split type exclusive. */
   public static final String JOIN_SPLIT_TYPE_EXCLUSIVE = "Exclusive";

   /** Constant representing join or split type inclusive. */
   public static final String JOIN_SPLIT_TYPE_INCLUSIVE = "Inclusive";

   /** Constant representing join or split type complex. */
   public static final String JOIN_SPLIT_TYPE_COMPLEX = "Complex";

   /** Constant representing join or split type parallel. */
   public static final String JOIN_SPLIT_TYPE_PARALLEL = "Parallel";

   /** Constant representing participant's type resource set. */
   public static final String PARTICIPANT_TYPE_RESOURCE_SET = "RESOURCE_SET";

   /** Constant representing participant's type resource. */
   public static final String PARTICIPANT_TYPE_RESOURCE = "RESOURCE";

   /** Constant representing participant's type role. */
   public static final String PARTICIPANT_TYPE_ROLE = "ROLE";

   /** Constant representing participant's type organizational unit. */
   public static final String PARTICIPANT_TYPE_ORGANIZATIONAL_UNIT = "ORGANIZATIONAL_UNIT";

   /** Constant representing participant's type human. */
   public static final String PARTICIPANT_TYPE_HUMAN = "HUMAN";

   /** Constant representing participant's type system. */
   public static final String PARTICIPANT_TYPE_SYSTEM = "SYSTEM";

   /** Constant representing duration unit type none. */
   public static final String DURATION_UNIT_NONE = "";

   /** Constant representing duration unit type year. */
   public static final String DURATION_UNIT_Y = "Y";

   /** Constant representing duration unit type month. */
   public static final String DURATION_UNIT_M = "M";

   /** Constant representing duration unit type day. */
   public static final String DURATION_UNIT_D = "D";

   /** Constant representing duration unit type hour. */
   public static final String DURATION_UNIT_h = "h";

   /** Constant representing duration unit type minute. */
   public static final String DURATION_UNIT_m = "m";

   /** Constant representing duration unit type second. */
   public static final String DURATION_UNIT_s = "s";

   /** Constant representing publication status type none. */
   public static final String PUBLICATION_STATUS_NONE = "";

   /** Constant representing publication status type under revision. */
   public static final String PUBLICATION_STATUS_UNDER_REVISION = "UNDER_REVISION";

   /** Constant representing publication status type released. */
   public static final String PUBLICATION_STATUS_RELEASED = "RELEASED";

   /** Constant representing publication status type under test. */
   public static final String PUBLICATION_STATUS_UNDER_TEST = "UNDER_TEST";

   /** Constant representing instantiation type none. */
   public static final String INSTANTIATION_NONE = "";

   /** Constant representing instantiation type once. */
   public static final String INSTANTIATION_ONCE = "ONCE";

   /** Constant representing instantiation type multiple. */
   public static final String INSTANTIATION_MULTIPLE = "MULTIPLE";

   /** Constant representing tool type none. */
   public static final String TOOL_TYPE_NONE = "";

   /** Constant representing tool type application. */
   public static final String TOOL_TYPE_APPLICATION = "APPLICATION";

   /** Constant representing tool type procedure. */
   public static final String TOOL_TYPE_PROCEDURE = "PROCEDURE";

   /** Constant representing access level type none. */
   public static final String ACCESS_LEVEL_NONE = "";

   /** Constant representing access level type private. */
   public static final String ACCESS_LEVEL_PRIVATE = "PRIVATE";

   /** Constant representing access level type public. */
   public static final String ACCESS_LEVEL_PUBLIC = "PUBLIC";

   /** Constant representing activity mode none. */
   public static final String ACTIVITY_MODE_NONE = "";

   /** Constant representing activity mode automatic. */
   public static final String ACTIVITY_MODE_AUTOMATIC = "Automatic";

   /** Constant representing activity mode manual. */
   public static final String ACTIVITY_MODE_MANUAL = "Manual";

   /** Constant representing activity mode number for automatic mode. */
   public static final int ACTIVITY_MODE_NO_AUTOMATIC = 0;

   /** Constant representing activity mode number for manual mode. */
   public static final int ACTIVITY_MODE_NO_MANUAL = 1;

   /** Constant representing activity type route. */
   public static final int ACTIVITY_TYPE_ROUTE = 0;

   /** Constant representing activity type no. */
   public static final int ACTIVITY_TYPE_NO = 1;

   /** Constant representing activity type subflow. */
   public static final int ACTIVITY_TYPE_SUBFLOW = 3;

   /** Constant representing activity type block. */
   public static final int ACTIVITY_TYPE_BLOCK = 4;

   /** Constant representing activity type reference. */
   public static final int ACTIVITY_TYPE_REFERENCE = 5;

   /** Constant representing activity type event start. */
   public static final int ACTIVITY_TYPE_EVENT_START = 6;

   /** Constant representing activity event end. */
   public static final int ACTIVITY_TYPE_EVENT_END = 7;

   /** Constant representing activity type task service. */
   public static final int ACTIVITY_TYPE_TASK_SERVICE = 8;

   /** Constant representing activity type task receive. */
   public static final int ACTIVITY_TYPE_TASK_RECEIVE = 9;

   /** Constant representing activity type task manual. */
   public static final int ACTIVITY_TYPE_TASK_MANUAL = 10;

   /** Constant representing activity type task reference. */
   public static final int ACTIVITY_TYPE_TASK_REFERENCE = 11;

   /** Constant representing activity type task script. */
   public static final int ACTIVITY_TYPE_TASK_SCRIPT = 12;

   /** Constant representing activity type task send. */
   public static final int ACTIVITY_TYPE_TASK_SEND = 13;

   /** Constant representing activity type task user. */
   public static final int ACTIVITY_TYPE_TASK_USER = 14;

   /** Constant representing activity type task application. */
   public static final int ACTIVITY_TYPE_TASK_APPLICATION = 2;

   /** Constant representing artifact type data object. */
   public static final String ARTIFACT_TYPE_DATAOBJECT = "DataObject";

   /** Constant representing artifact type group. */
   public static final String ARTIFACT_TYPE_GROUP = "Group";

   /** Constant representing artifact type annotation. */
   public static final String ARTIFACT_TYPE_ANNOTATION = "Annotation";

   /** Constant representing association direction type none. */
   public static final String ASSOCIATION_DIRECTION_NONE = "None";

   /** Constant representing association direction type to. */
   public static final String ASSOCIATION_DIRECTION_TO = "To";

   /** Constant representing association direction type from. */
   public static final String ASSOCIATION_DIRECTION_FROM = "From";

   /** Constant representing association direction type both. */
   public static final String ASSOCIATION_DIRECTION_BOTH = "Both";

}
