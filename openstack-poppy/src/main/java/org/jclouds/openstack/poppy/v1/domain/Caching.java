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

/**
 * Representation of an OpenStack Poppy Caching Rule.
 */
@AutoValue
public abstract class Caching {

   @AutoValue
   public abstract static class Rule {

      abstract String getName();
      abstract String getRequestURL();

      @SerializedNames({ "name", "request_url" })
      static Rule create(String name, String requestUrl) {
         return new AutoValue_Caching_Rule(name, requestUrl);
      }

      Rule() {
      }

      public static Builder builder() {
         return new AutoValue_Caching_Rule.Builder();
      }

      @AutoValue.Builder
      public interface Builder {
         Builder name(String name);
         Builder requestURL(String requestUrl);
         Rule build();
      }
   }

   abstract String getName();
   abstract int getTtl();
   @Nullable abstract List<Rule> getRules();

   @SerializedNames({ "name", "ttl", "rules" })
   static Caching create(String name, int ttl, List<Rule> rules) {
      return new AutoValue_Caching(name, ttl, rules);
   }

   Caching() {
   }

   public static Builder builder() {
      return new AutoValue_Caching.Builder();
   }

   @AutoValue.Builder
   public interface Builder {
      Builder name(String name);
      Builder ttl(int ttl);
      Builder rules(List<Rule> rules);
      Caching build();
   }

}
