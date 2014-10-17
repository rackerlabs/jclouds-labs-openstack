/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.openstack.heat.v1.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jclouds.openstack.v2_0.domain.Link;
import org.jclouds.rest.annotations.AutoGson;

import com.google.auto.value.AutoValue;
import com.google.common.base.CaseFormat;

/**
 * Representation of an OpenStack Heat Stack using AutoValue.
 */
@AutoValue
@AutoGson
public abstract class AutoStack {

   public static AutoStack create(String id, String name,
      Set<Link> links,
      String description,
      String owner,
      Set<String> capabilities,
      Map<String, String> parameters,
      List<String> outputs,
      List<String> notificationTopics,
      String templateDescription,
      AutoStack.Status status,
      String statusReason,
      Date created,
      Date updated,
      int timeoutMins,
      boolean disableRollback) {

      return new AutoValue_AutoStack(id, name, links, description, owner, capabilities, parameters, outputs,
            notificationTopics, templateDescription, status, statusReason, created, updated, timeoutMins, disableRollback);
   }

   // need to figure out how map the snakey case params...
   //
   public abstract String id();
   public abstract String stack_name();
   public abstract Set<Link> links();
   public abstract String description();
   public abstract String owner();
   public abstract Set<String> capabilities();
   public abstract Map<String, String> parameters();
   public abstract List<String> outputs();
   public abstract List<String> notificationTopics();
   public abstract String templateDescription();
   public abstract Status stackStatus();
   public abstract String statusReason();
   public abstract Date created_time();
   public abstract Date updated_time();
   public abstract int timeout_mins();
   public abstract boolean disable_rollback();

   public enum Status {
      CREATE_IN_PROGRESS, CREATE_COMPLETE, CREATE_FAILED,
      UPDATE_IN_PROGRESS, UPDATE_COMPLETE, UPDATE_FAILED,
      DELETE_IN_PROGRESS, DELETE_COMPLETE, DELETE_FAILED,
      ROLLBACK_IN_PROGRESS, ROLLBACK_COMPLETE, ROLLBACK_FAILED,
      SUSPEND_IN_PROGRESS, SUSPEND_COMPLETE, SUSPEND_FAILED,
      RESUME_IN_PROGRESS, RESUME_COMPLETE, RESUME_FAILED,
      UNRECOGNIZED;

      public String value() {
         return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, name());
      }

      @Override
      public String toString() {
         return value();
      }

      /**
       * This provides GSON enum support in jclouds.
       * @param name The string representation of this enum value.
       * @return The corresponding enum value.
       */

      public static Status fromValue(String status) {
         try {
            return valueOf(checkNotNull(status, "status"));
         }
         catch (IllegalArgumentException e) {
            return UNRECOGNIZED;
         }
      }
   }

}
