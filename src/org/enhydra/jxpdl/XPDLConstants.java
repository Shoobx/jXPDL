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

/**
 *  XPDLConstants
 *  @author Sasa Bojanic
 */
public final class XPDLConstants  {
   
   public static final String XSD_BOOLEAN_TRUE = "true";
   public static final String XSD_BOOLEAN_FALSE = "false";

   public static final String POOL_ORIENTATION_HORIZONTAL = "HORIZONTAL";
   public static final String POOL_ORIENTATION_VERTICAL = "VERTICAL";

   public static final String TASK_IMPLEMENTATION_WEBSERVICE = "WebService";
   public static final String TASK_IMPLEMENTATION_OTHER = "Other";
   public static final String TASK_IMPLEMENTATION_UNSPECIFIED = "Unspecified";
   
   public static final String ENDPOINT_TYPE_WSDL = "WSDL";
   public static final String ENDPOINT_TYPE_SERVICE = "Service";
   
   public static final String ROLE_TYPE_MYROLE = "MyRole";
   public static final String ROLE_TYPE_PARTNERROLE = "PartnerRole";

   public static final String EVENT_NONE = "None";

   public static final String BASIC_TYPE_STRING="STRING";
   public static final String BASIC_TYPE_FLOAT="FLOAT";
   public static final String BASIC_TYPE_INTEGER="INTEGER";
   public static final String BASIC_TYPE_REFERENCE="REFERENCE";
   public static final String BASIC_TYPE_DATETIME="DATETIME";
   public static final String BASIC_TYPE_BOOLEAN="BOOLEAN";
   public static final String BASIC_TYPE_PERFORMER="PERFORMER";

   public static final String CONDITION_TYPE_NONE="";
   public static final String CONDITION_TYPE_CONDITION="CONDITION";
   public static final String CONDITION_TYPE_OTHERWISE="OTHERWISE";
   public static final String CONDITION_TYPE_EXCEPTION="EXCEPTION";
   public static final String CONDITION_TYPE_DEFAULTEXCEPTION="DEFAULTEXCEPTION";

   public static final String GRAPH_CONFORMANCE_NONE="";
   public static final String GRAPH_CONFORMANCE_FULL_BLOCKED="FULL_BLOCKED";
   public static final String GRAPH_CONFORMANCE_LOOP_BLOCKED="LOOP_BLOCKED";
   public static final String GRAPH_CONFORMANCE_NON_BLOCKED="NON_BLOCKED";

   public static final String EXECUTION_NONE="";
   public static final String EXECUTION_ASYNCHR="ASYNCHR";
   public static final String EXECUTION_SYNCHR="SYNCHR";

   public static final String FORMAL_PARAMETER_MODE_IN="IN";
   public static final String FORMAL_PARAMETER_MODE_OUT="OUT";
   public static final String FORMAL_PARAMETER_MODE_INOUT="INOUT";

   public static final String JOIN_SPLIT_TYPE_NONE="";
   public static final String JOIN_SPLIT_TYPE_EXCLUSIVE="Exclusive";
   public static final String JOIN_SPLIT_TYPE_INCLUSIVE="Inclusive";
   public static final String JOIN_SPLIT_TYPE_COMPLEX="Complex";
   public static final String JOIN_SPLIT_TYPE_PARALLEL="Parallel";

   public static final String PARTICIPANT_TYPE_RESOURCE_SET="RESOURCE_SET";
   public static final String PARTICIPANT_TYPE_RESOURCE="RESOURCE";
   public static final String PARTICIPANT_TYPE_ROLE="ROLE";
   public static final String PARTICIPANT_TYPE_ORGANIZATIONAL_UNIT="ORGANIZATIONAL_UNIT";
   public static final String PARTICIPANT_TYPE_HUMAN="HUMAN";
   public static final String PARTICIPANT_TYPE_SYSTEM="SYSTEM";

   public static final String DURATION_UNIT_NONE="";
   public static final String DURATION_UNIT_Y="Y";
   public static final String DURATION_UNIT_M="M";
   public static final String DURATION_UNIT_D="D";
   public static final String DURATION_UNIT_h="h";
   public static final String DURATION_UNIT_m="m";
   public static final String DURATION_UNIT_s="s";

   public static final String PUBLICATION_STATUS_NONE="";
   public static final String PUBLICATION_STATUS_UNDER_REVISION="UNDER_REVISION";
   public static final String PUBLICATION_STATUS_RELEASED="RELEASED";
   public static final String PUBLICATION_STATUS_UNDER_TEST="UNDER_TEST";

   public static final String INSTANTIATION_NONE="";
   public static final String INSTANTIATION_ONCE="ONCE";
   public static final String INSTANTIATION_MULTIPLE="MULTIPLE";

   public static final String TOOL_TYPE_NONE="";
   public static final String TOOL_TYPE_APPLICATION="APPLICATION";
   public static final String TOOL_TYPE_PROCEDURE="PROCEDURE";

   public static final String ACCESS_LEVEL_NONE = "";
   public static final String ACCESS_LEVEL_PRIVATE = "PRIVATE";
   public static final String ACCESS_LEVEL_PUBLIC = "PUBLIC";

   public static final String ACTIVITY_MODE_NONE = "";
   public static final String ACTIVITY_MODE_AUTOMATIC = "Automatic";
   public static final String ACTIVITY_MODE_MANUAL = "Manual";
   
   public static final int ACTIVITY_MODE_NO_AUTOMATIC = 0;
   public static final int ACTIVITY_MODE_NO_MANUAL = 1;

   public static final int ACTIVITY_TYPE_ROUTE = 0;
   public static final int ACTIVITY_TYPE_NO = 1;
   public static final int ACTIVITY_TYPE_SUBFLOW = 3;
   public static final int ACTIVITY_TYPE_BLOCK = 4;
   public static final int ACTIVITY_TYPE_REFERENCE = 5;

   public static final int ACTIVITY_TYPE_EVENT_START = 6;
   public static final int ACTIVITY_TYPE_EVENT_END = 7;

   public static final int ACTIVITY_TYPE_TASK_SERVICE = 8;
   public static final int ACTIVITY_TYPE_TASK_RECEIVE = 9;
   public static final int ACTIVITY_TYPE_TASK_MANUAL = 10;
   public static final int ACTIVITY_TYPE_TASK_REFERENCE = 11;
   public static final int ACTIVITY_TYPE_TASK_SCRIPT = 12;
   public static final int ACTIVITY_TYPE_TASK_SEND = 13;
   public static final int ACTIVITY_TYPE_TASK_USER = 14;
   public static final int ACTIVITY_TYPE_TASK_APPLICATION = 2;
}
