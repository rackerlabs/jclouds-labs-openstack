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
package org.jclouds.openstack.poppy.v1.domain;

import java.util.List;

import org.jclouds.javax.annotation.Nullable;
import org.jclouds.json.SerializedNames;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

/**
 * Representation of an OpenStack Poppy Origin.
 */
@AutoValue
public abstract class Origin {

   @AutoValue
   public abstract static class Rule {

      abstract String getName();
      abstract String getRequestURL();

      @SerializedNames({ "name", "request_url" })
      static Rule create(String name, String requestUrl) {
         return new AutoValue_Origin_Rule(name, requestUrl);
      }

      Rule() {
      }

      public static Builder builder() {
         return new AutoValue_Origin_Rule.Builder();
      }

      @AutoValue.Builder
      public interface Builder {
         Builder name(String name); // required
         Builder requestURL(String requestURL); // required
         Rule build();
      }
   }

   abstract String origin();
   @Nullable abstract int port();
   @Nullable abstract boolean sslEnabled();
   @Nullable abstract List<Rule> rules();

   @SerializedNames({ "origin", "port", "ssl", "rules" })
   static Origin create(String origin, int port, boolean sslEnabled, List<Rule> rules) {
      return new AutoValue_Origin(origin, port, sslEnabled, rules);
   }

   Origin() {
   }

   public static Builder builder() {
      return new AutoValue_Origin.Builder().sslEnabled(false).rules(ImmutableList.<Rule> of());
   }

   @AutoValue.Builder
   public interface Builder {
      Builder origin(String origin); // required
      Builder port(int port);  // optional
      Builder sslEnabled(boolean ssl); // optional
      Builder rules(List<Rule> rules); // required
      Origin build();
   }

}
