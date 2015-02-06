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
 * Representation of an OpenStack Poppy Access Restriction.
 */
@AutoValue
public abstract class Restriction {

   @AutoValue
   public abstract static class Rule {

      abstract String getName();
      @Nullable abstract String getReferrer();

      @SerializedNames({ "name", "referrer" })
      static Rule create(String name, String referrer) {
         return new AutoValue_Restriction_Rule(name, referrer);
      }

      Rule() {
      }

      public static Builder builder() {
         return new AutoValue_Restriction_Rule.Builder().referrer(null);
      }

      @AutoValue.Builder
      public interface Builder {
         Builder name(String name);
         Builder referrer(String referrer);
         Rule build();
      }

   }

   abstract String getName();
   @Nullable abstract List<Rule> getRules();

   @SerializedNames({ "name", "rules" })
   static Restriction create(String name, List<Rule> rules) {
      return new AutoValue_Restriction(name, rules);
   }

   Restriction() {
   }

   public static Builder builder() {
      return new AutoValue_Restriction.Builder().rules(ImmutableList.<Rule> of());
   }

   @AutoValue.Builder
   public interface Builder {
      Builder name(String name);
      Builder rules(List<Rule> rules);
      Restriction build();
   }

}
