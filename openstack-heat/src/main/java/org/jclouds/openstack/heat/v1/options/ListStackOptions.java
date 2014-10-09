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
package org.jclouds.openstack.heat.v1.options;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import org.jclouds.openstack.heat.v1.domain.Stack.Status;
import org.jclouds.openstack.v2_0.options.PaginationOptions;

import com.google.common.base.CaseFormat;

/**
 * Options used to control the amount of detail in the request.
 *
 * @see PaginationOptions
 */
public class ListStackOptions extends PaginationOptions {

   public static final ListStackOptions NONE = new ListStackOptions();

   public enum SortDirection {
      ASCENDING("asc"),
      DESCENDING("desc"),
      UNRECOGNIZED("unrecognized");

      private String name;

      private SortDirection(String name) {
         this.name = name;
      }

      @Override
      public String toString() {
         return name;
      }

      /**
       * This provides GSON enum support in jclouds.
       * @param name The string representation of this enum value.
       * @return The corresponding enum value.
       */
      public static SortDirection fromValue(String direction) {
         if (direction != null) {
            for (SortDirection value : SortDirection.values()) {
               if (direction.equalsIgnoreCase(value.name)) {
                  return value;
               }
            }
         }
         return UNRECOGNIZED;
      }

      public static boolean contains(SortDirection direction) {
         for (SortDirection dir : SortDirection.values()) {
             if (dir.equals(direction)) {
                 return true;
             }
         }
         return false;
     }
   }

   public enum SortKey {
      NAME, STATUS, CREATED_AT, UPDATED_AT,
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

      public static SortKey fromValue(String sortKey) {
         try {
            return valueOf(checkNotNull(sortKey, "sortKey"));
         }
         catch (IllegalArgumentException e) {
            return UNRECOGNIZED;
         }
      }

      public static boolean contains(String sortKey) {
         for (SortKey key : SortKey.values()) {
             if (key.value().equals(sortKey)) {
                 return true;
             }
         }
         return false;
      }
      public static boolean contains(SortKey sortKey) {
         for (SortKey key : SortKey.values()) {
             if (key.equals(sortKey)) {
                 return true;
             }
         }
         return false;
      }
      }

   /**
    *
    * {@inheritDoc}
    */
   @Override
   public ListStackOptions limit(int limit) {
      super.limit(limit);
      return this;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public ListStackOptions marker(String marker) {
      super.marker(marker);
      return this;
   }

   /**
    * Filters the stack list by the specified status. You can use this filter multiple
    * times to filter by multiple statuses.
    */
   public ListStackOptions status(Status status) {
      this.queryParameters.put("status", checkNotNull(status.toString(), "status"));
      return this;
   }

   /**
    * Filters the stack list by the specified name.
    */
   public ListStackOptions name(String name) {
      this.queryParameters.put("name", checkNotNull(name, "name"));
      return this;
   }

   /**
    * Sorts the stack list by one of these attributes:
    *    name, status, created_at, or updated_at
    */
   public ListStackOptions sortKey(SortKey key) {
      checkState(SortKey.contains(key), "invalid sort key");
      this.queryParameters.put("sort_keys", checkNotNull(key.toString(), "key"));
      return this;
   }

   /**
    * The sort direction of the stack list. Either asc (ascending) or desc (descending).
    */
   public ListStackOptions sortDirection(SortDirection direction) {
      checkState(SortDirection.contains(direction), "direction is either asc or desc");
      this.queryParameters.put("sort_dir", checkNotNull(direction.toString(), "direction"));
      return this;
   }

   public static Builder builder() {
      return new Builder();
   }

   public static class Builder {

      /**
       * @see PaginationOptions#limit(int)
       */
      public static ListStackOptions limit(int limit) {
         return new ListStackOptions().limit(limit);
      }

      /**
       * @see PaginationOptions#marker(String)
       */
      public static ListStackOptions marker(String marker) {
         return new ListStackOptions().marker(marker);
      }

      /**
       * @see ListStackOptions#status(String)
       */
      public static ListStackOptions status(Status status) {
         return new ListStackOptions().status(status);
      }

      /**
       * @see ListStackOptions#name(String)
       */
      public static ListStackOptions name(String name) {
         return new ListStackOptions().name(name);
      }

      /**
       * @see ListStackOptions#sortKey(String)
       */
      public static ListStackOptions sortKey(SortKey key) {
         return new ListStackOptions().sortKey(key);
      }

      /**
       * @see ListStackOptions#sortDirection(String)
       */
      public static ListStackOptions sortDirection(SortDirection direction) {
         return new ListStackOptions().sortDirection(direction);
      }

   }
}
